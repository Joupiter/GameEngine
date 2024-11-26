package fr.joupi.games.engine;

import fr.joupi.games.engine.player.BaseGamePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public interface MiniGame {

    Logger log = LoggerFactory.getLogger(MiniGame.class);

    String getName();
    String getId();

    GameState getState();
    GameSize getGameSize();

    <G extends BaseGamePlayer> ConcurrentMap<UUID, G> getPlayers();
    <S extends GameSettings<?>> S getSettings();

    void preload();
    void load();

    void unload();
    void endGame();

    void joinGame(Player player, boolean spectator);

    default void joinGame(Player player) {
        joinGame(player, false);
    }

    void leaveGame(UUID uuid);

    boolean containsPlayer(UUID uuid);

    default boolean containsPlayer(Player player) {
        return containsPlayer(player.getUniqueId());
    }

    void broadcast(String message);
    void broadcast(String... message);

    void setState(GameState gameState);

    boolean canStart();
    boolean isFull();
    boolean canJoin();

    int getAlivePlayersCount();
    int getSpectatorsCount();
    int getSize();

    void sendDebugInfoMessage(CommandSender sender);

    default String getFullName() {
        return getName() + "-" + getId();
    }

}