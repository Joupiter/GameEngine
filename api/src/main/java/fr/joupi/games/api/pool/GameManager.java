package fr.joupi.games.api.pool;

import fr.joupi.games.api.processor.GameProcessor;
import fr.joupi.games.engine.GameState;
import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.player.BaseGamePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface GameManager<M extends MiniGame> {

    List<M> getGames();

    GameProcessor<M> getGameProcessor();

    void findGame(Player player);

    void joinGame(M game, Player player);
    void joinGame(String id, Player player);

    void spectateGame(String id, Player player);

    void leaveGame(Player player);

    boolean addGame(M game);
    void addGame(int number, Supplier<M> supplier);

    void removeGame(M game);

    void shutdown();

    Optional<M> getGame(Player player);

    M getNullableGame(Player player);

    void getGame(Player player, Consumer<M> consumer);
    <G extends BaseGamePlayer> void getGame(Player player, Class<G> clazz, BiConsumer<M, G> biConsumer);

    Optional<M> getGameByID(String id);

    void getGameByID(String id, Consumer<M> consumer);
    void getGameByID(String id, Player player, Consumer<M> consumer);

    Optional<M> getGameWithMorePlayers();
    Optional<M> getGameWithLessPlayers();

    Optional<M> getReachableGameWithMorePlayers();
    Optional<M> getReachableGameWithLessPlayers();

    List<M> getGames(GameState state);

    void getGames(Consumer<M> consumer);
    void getGames(Predicate<M> filter, Consumer<M> consumer);

    List<M> getReachableGames();

    List<M> getReachableGamesWithMorePlayers();

    List<M> getReachableGamesWithLessPlayers();

    List<M> getEmptyGames();

    List<M> getGamesWithMorePlayers();
    List<M> getGamesWithMorePlayers(GameState state);

    List<M> getGamesWithLessPlayers();
    List<M> getGamesWithLessPlayers(GameState state);

    List<Player> getInGamePlayers();

    boolean isInGame(Player player);

    default boolean isNotInGame(Player player) {
        return !isInGame(player);
    }

    int getPlayersCount();
    int getPlayersCountById(String id);

    int getSize();

}