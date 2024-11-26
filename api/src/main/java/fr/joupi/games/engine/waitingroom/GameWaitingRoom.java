package fr.joupi.games.engine.waitingroom;

import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.map.Map;
import fr.joupi.games.engine.player.BaseGamePlayer;
import fr.joupi.games.engine.utils.task.countdown.CountdownTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;

@Getter
@Setter
public abstract class GameWaitingRoom<M extends MiniGame, G extends BaseGamePlayer, I extends Enum<I> & WaitingRoomItems> {

    protected final M game;
    protected final I[] items;

    protected Map map;
    protected CountdownTask countdownTask;

    public GameWaitingRoom(M game, Class<I> itemsClass) {
        this.game = game;
        this.items = itemsClass.getEnumConstants();
    }

    public abstract void onJoin(G gamePlayer);
    public abstract void onLeave(G gamePlayer);

    public void processJoin(G gamePlayer) {
        var spawn = map.getSpawn();
        cleanUpPlayer(gamePlayer);

        if (map != null && spawn != null)
            spawn.syncTeleport(gamePlayer);

        giveItems(gamePlayer);
        tryToStartTimer();
        onJoin(gamePlayer);
    }

    public void processLeave(G gamePlayer) {
        onLeave(gamePlayer);
        tryToCancelTimer();
    }

    public void giveItems(G gamePlayer) {
        for (I item : items) {
            gamePlayer.getInventory().setItem(item.getSlot(), item.getItemStack());
        }
    }

    public void cleanUpPlayer(G gamePlayer) {
        var player = gamePlayer.getPlayer();

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getActivePotionEffects().clear();

        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0f);
    }

    public void teleport(G gamePlayer) {
        var spawn = map.getSpawn();

        if (spawn != null)
            spawn.syncTeleport(gamePlayer);
    }

    public void tryToStartTimer() {
        if (countdownTask != null && game.canStart())
            countdownTask.run();
    }

    public void tryToCancelTimer() {
        if (countdownTask != null && !game.canStart() && countdownTask.isStarted())
            countdownTask.cancel();
    }

}