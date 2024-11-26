package fr.joupi.games.api.pool;

import fr.joupi.games.api.DirectConnectStrategy;
import fr.joupi.games.api.pool.core.GamePoolHeartBeat;
import fr.joupi.games.engine.MiniGame;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public interface Pool {

    <M extends MiniGame> Class<M> getGameClass();

    String getName();

     int getMinPoolSize();
     int getMaxPoolSize();

     DirectConnectStrategy getStrategy();

    <M extends MiniGame> GameManager<M> getGamesManager();
    <M extends MiniGame> Map<UUID, M> getGameCreationCache();
    <M extends MiniGame> GamePoolHeartBeat<M> getGamePoolHeartBeat();

    void setup();
    void shutdown();

    void loadDefaultGames();

    void addGame();

    void addGame(int number);

    void addGame(Object... objects);
    void addGame(int number, Object... objects);

    void addGame(UUID uuid, Object object);
    void addGame(int number, UUID uuid, Object object);

    void addNecessaryGame();

    void sendDebugMessage();
    String getDebugMessage();

    default void useCustomStrategy(Player player) {
        throw new NoSuchMethodError();
    }

}