package fr.joupi.games.api.pool;

import fr.joupi.games.api.DirectConnectStrategy;
import fr.joupi.games.engine.MiniGame;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public interface GamePoolManager {

    List<Pool> getGamePools();

    void addPool(Pool gamePool);
    void removePool(Pool gamePool);

    void shutdown();

    void leaveGame(Player player);

    Optional<Pool> getGamePool(String name);

    Optional<MiniGame> getGame(Player player);
    Optional<MiniGame> getGameByID(String id);

    MiniGame getNullableGame(Player player);
    MiniGame getNullableGameByID(String id);

    <M extends MiniGame> Pool getGamePool(Class<M> clazz);

    Pool getGamePoolByClass(Class<Pool> clazz);

    List<MiniGame> getAllGames();

    List<Pool> getGamePoolByStrategy(DirectConnectStrategy strategy);
    List<Pool> getGamePoolByStrategy(EnumSet<DirectConnectStrategy> strategies);

    List<Pool> getGamePoolWithoutStrategy(DirectConnectStrategy strategy);
    List<Pool> getGamePoolWithoutStrategy(EnumSet<DirectConnectStrategy> strategies);

    int getGamesCount();

    String getDebugMessage();

}