package fr.joupi.games.engine.board;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import fr.joupi.games.engine.player.BaseGamePlayer;
import fr.joupi.games.engine.utils.scoreboard.PersonalScoreboard;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Getter
public class GameBoardManager {

    private final static ScheduledExecutorService executorMonoThread = Executors.newScheduledThreadPool(1, new ThreadFactoryBuilder().setNameFormat("board-mono-thread-%d").build());
    private final static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5, new ThreadFactoryBuilder().setNameFormat("board-thread-%d").build());

    private final static String serverIp = "play.example.fr";

    private final Map<UUID, PersonalScoreboard> scoreboards;

    private final ScheduledFuture<?> glowingTask, reloadingTask;

    private int ipCharIndex, cooldown;

    public GameBoardManager() {
        this.scoreboards = new HashMap<>();
        this.ipCharIndex = 0;
        this.cooldown = 0;

        this.glowingTask = scheduledExecutorService.scheduleAtFixedRate(() -> {
            final var ip = colorIpAt();
            for (PersonalScoreboard scoreboard : scoreboards.values())
                executorMonoThread.execute(() -> scoreboard.setLines(ip));
        }, 80, 80, TimeUnit.MILLISECONDS);

        this.reloadingTask = scheduledExecutorService.scheduleAtFixedRate(() -> {
            for (PersonalScoreboard scoreboard : scoreboards.values())
                executorMonoThread.execute(scoreboard::reloadData);
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void onDisable() {
        scoreboards.values().forEach(PersonalScoreboard::onLogout);
        scoreboards.clear();
        glowingTask.cancel(false);
        reloadingTask.cancel(false);
    }

    public void onLogin(UUID uuid, PersonalScoreboard scoreboard) {
        scoreboards.putIfAbsent(uuid, scoreboard);
    }

    public void onLogin(BaseGamePlayer gamePlayer, PersonalScoreboard scoreboard) {
        onLogin(gamePlayer.getUuid(), scoreboard);
    }

    public void onLogout(UUID uuid) {
        final var board = scoreboards.get(uuid);

        if (board != null) {
            board.onLogout();
            scoreboards.remove(uuid);
        }
    }

    public void onLogout(BaseGamePlayer gamePlayer) {
        onLogout(gamePlayer.getUuid());
    }

    public void update(UUID uuid) {
        final var board = scoreboards.get(uuid);

        if (board != null)
            board.reloadData();
    }

    public void update(BaseGamePlayer gamePlayer) {
        update(gamePlayer.getUuid());
    }

    private String colorIpAt() {
        var ip = serverIp;

        if (cooldown > 0) {
            cooldown--;
            return "§6" + ip;
        }

        var formattedIp = new StringBuilder();

        if (ipCharIndex > 0) {
            formattedIp.append(ip, 0, ipCharIndex - 1);
            formattedIp.append("§e").append(ip.charAt(ipCharIndex - 1));
        } else {
            formattedIp.append(ip, 0, ipCharIndex);
        }

        formattedIp.append("§f").append(ip.charAt(ipCharIndex));

        if (ipCharIndex + 1 < ip.length()) {
            formattedIp.append("§e").append(ip.charAt(ipCharIndex + 1));

            if (ipCharIndex + 2 < ip.length())
                formattedIp.append("§6").append(ip.substring(ipCharIndex + 2));

            ipCharIndex++;
        } else {
            ipCharIndex = 0;
            cooldown = 50;
        }

        return "§6" + formattedIp;
    }

}