package fr.joupi.games.engine.ffa;

import fr.joupi.games.engine.GameSettings;
import fr.joupi.games.engine.SimpleGame;
import fr.joupi.games.engine.event.player.GamePlayerJoinEvent;
import fr.joupi.games.engine.event.player.GamePlayerSpectateEvent;
import fr.joupi.games.engine.ffa.lobby.FFALobby;
import fr.joupi.games.engine.ffa.player.FFAGamePlayer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
public abstract class FFAGame<G extends FFAGamePlayer, S extends GameSettings<?>> extends SimpleGame<G, S> {

    protected final FFALobby lobby;

    public FFAGame(String name, S settings, FFALobby ffaLobby) {
        super(name, settings);
        this.lobby = ffaLobby;
    }

    @Override
    public void joinGame(Player player, boolean spectator) {
        final var uuid = player.getUniqueId();

        if (!players.containsKey(uuid)) {
            final var gamePlayer = defaultGamePlayer(uuid, spectator);
            this.playerCount += 1;

            players.put(uuid, gamePlayer);
            settings.addBoardViewer(this, gamePlayer);
            lobby.onJoin(this, gamePlayer);

            if (spectator)
                Bukkit.getPluginManager().callEvent(new GamePlayerSpectateEvent<>(this, gamePlayer));
            else
                Bukkit.getPluginManager().callEvent(new GamePlayerJoinEvent<>(this, gamePlayer));

            log.info("[{}] {} {} game.", getFullName(), player.getName(), spectator ? "spectate" : "joined");
        }
    }

    public void play(G gamePlayer) {
        lobby.onPlay(this, gamePlayer);
    }

}