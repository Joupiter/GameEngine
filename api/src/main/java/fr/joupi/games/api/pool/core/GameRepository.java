package fr.joupi.games.api.pool.core;

import fr.joupi.games.api.GameApi;
import fr.joupi.games.api.pool.GameManager;
import fr.joupi.games.api.pool.GamePool;
import fr.joupi.games.api.processor.GameLoaderProcessor;
import fr.joupi.games.api.processor.GameProcessor;
import fr.joupi.games.engine.GameState;
import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.event.game.GameAddedEvent;
import fr.joupi.games.engine.event.game.GameRemovedEvent;
import fr.joupi.games.engine.player.BaseGamePlayer;
import fr.joupi.games.engine.utils.CollectionUtils;
import fr.joupi.games.engine.utils.Utils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Getter
@Slf4j
public class GameRepository<M extends MiniGame> implements GameManager<M> {

    private final GamePool<M> gamePool;
    private final GameProcessor<M> gameProcessor;

    private final List<M> games;

    public GameRepository(GamePool<M> gamePool) {
        this.gamePool = gamePool;
        this.gameProcessor = new GameLoaderProcessor<>(gamePool);
        this.games = new ArrayList<>(gamePool.getMaxPoolSize());
    }

    @Override
    public void findGame(Player player) {
        switch (gamePool.getStrategy()) {
            case FILL_GAME ->
                    getReachableGameWithMorePlayers().ifPresentOrElse(
                            game -> joinGame(game, player),
                            () -> player.sendMessage("[GameManager] NO GAMES AVAILABLE PLEASE COME BACK LATER (FILLGAME STRATEGY)"));
            case RANDOM ->
                    CollectionUtils.random(getReachableGames()).ifPresentOrElse(
                            game -> joinGame(game, player),
                            () -> player.sendMessage("[GameManager] NO GAMES AVAILABLE PLEASE COME BACK LATER (RANDOM STRATEGY)"));
            case CUSTOM -> gamePool.useCustomStrategy(player);
            case DISABLED -> {}
        }
    }

    @Override
    public void joinGame(M game, Player player) {
        leaveGame(player);
        game.joinGame(player);
    }

    @Override
    public void joinGame(String id, Player player) {
        leaveGame(player);
        getGameByID(id, game -> game.joinGame(player));
    }

    @Override
    public void spectateGame(String id, Player player) {
        leaveGame(player);
        getGameByID(id, game -> game.joinGame(player, true));
    }

    @Override
    public void leaveGame(Player player) {
        getGame(player, game -> game.leaveGame(player.getUniqueId()));
    }

    @Override
    public boolean addGame(M game) {
        if (gamePool.getMaxPoolSize() == games.size()) {
            log.warn("[GameManager] GAME POOL MAX SIZE ALREADY REACHED !");
            return false;
        }

        var id = gamePool.getGameCreationCache().entrySet().stream()
                .filter(entry -> game.equals(entry.getValue()))
                .map(Map.Entry::getKey).findFirst().orElse(null);

        gameProcessor.addGame(id, game);
        games.add(game);
        Bukkit.getPluginManager().callEvent(new GameAddedEvent<>(game, gamePool));
        log.info("[GameManager] GAME {} ADDED TO PROCESSOR QUEUE", game.getFullName());
        return true;
    }

    @Override
    public void addGame(int number, Supplier<M> supplier) {
        Utils.range(number, () -> addGame(supplier.get()));
    }

    @Override
    public void removeGame(M game) {
        if (games.removeIf(game::equals)) {
            Bukkit.getPluginManager().callEvent(new GameRemovedEvent<>(game, gamePool));
            log.info("[GameManager] REMOVED GAME: {}", game.getFullName());
            log.info("[GameManager] AVAILABLE GAMES: {}", games.size());

            if (!GameApi.getInstance().isShutdown()
                    && games.size() < gamePool.getMinPoolSize())
                gamePool.addNecessaryGame();
        }
    }

    @Override
    public void shutdown() {
        gameProcessor.shutdown();
        games.forEach(MiniGame::endGame);
    }

    @Override
    public Optional<M> getGame(Player player) {
        return games.stream()
                .filter(game -> game.containsPlayer(player)).findFirst();
    }

    @Override
    public M getNullableGame(Player player) {
        for (M game : games)
            if (game.containsPlayer(player)) return game;
        return null;
    }

    @Override
    public void getGame(Player player, Consumer<M> consumer) {
        games.stream()
                .filter(game -> game.containsPlayer(player))
                .findFirst().ifPresent(consumer);
    }

    @Override
    public <G extends BaseGamePlayer> void getGame(Player player, Class<G> clazz, BiConsumer<M, G> biConsumer) {
        games.stream()
                .filter(game -> game.containsPlayer(player))
                .findFirst().ifPresent(game -> biConsumer.accept(game, clazz.cast(game.getPlayers().get(player.getUniqueId()))));
    }

    @Override
    public Optional<M> getGameByID(String id) {
        return games.stream()
                .filter(game -> game.getFullName().equals(id)).findFirst();
    }

    @Override
    public void getGameByID(String id, Consumer<M> consumer) {
        games.stream()
                .filter(game -> game.getFullName().equals(id))
                .findFirst().ifPresent(consumer);
    }

    @Override
    public void getGameByID(String id, Player player, Consumer<M> consumer) {
        games.stream()
                .filter(game -> game.getFullName().equals(id)
                        && game.containsPlayer(player))
                .findFirst().ifPresent(consumer);
    }

    @Override
    public Optional<M> getGameWithMorePlayers() {
        return getGamesWithMorePlayers().stream().findFirst();
    }

    @Override
    public Optional<M> getGameWithLessPlayers() {
        return getGamesWithLessPlayers().stream().findFirst();
    }

    @Override
    public Optional<M> getReachableGameWithMorePlayers() {
        return getReachableGamesWithMorePlayers().stream().findFirst();
    }

    @Override
    public Optional<M> getReachableGameWithLessPlayers() {
        return getReachableGamesWithLessPlayers().stream().findFirst();
    }

    @Override
    public List<M> getGames(GameState state) {
        return games.stream()
                .filter(game -> game.getState() == state)
                .toList();
    }

    @Override
    public void getGames(Consumer<M> consumer) {
        games.forEach(consumer);
    }

    @Override
    public void getGames(Predicate<M> filter, Consumer<M> consumer) {
        games.stream()
                .filter(filter)
                .forEach(consumer);
    }

    @Override
    public List<M> getReachableGames() {
        return games.stream()
                .filter(game -> game.getState().is(GameState.WAIT, GameState.STARTING, GameState.ALWAYS_PLAYING)
                        && game.canJoin()
                        && !game.isFull())
               .toList();
    }

    @Override
    public List<M> getReachableGamesWithMorePlayers() {
        return getReachableGames().stream()
                .sorted(Comparator.comparingInt(MiniGame::getSize).reversed())
                .toList();
    }

    @Override
    public List<M> getReachableGamesWithLessPlayers() {
        return getReachableGames().stream()
                .sorted(Comparator.comparingInt(MiniGame::getSize))
                .toList();
    }

    @Override
    public List<M> getEmptyGames() {
        return games.stream()
                .filter(game -> game.getAlivePlayersCount() == 0)
                .toList();
    }

    @Override
    public List<M> getGamesWithMorePlayers() {
        return games.stream()
                .sorted(Comparator.comparingInt(MiniGame::getSize).reversed())
                .toList();
    }

    @Override
    public List<M> getGamesWithMorePlayers(GameState state) {
        return List.of();
    }

    @Override
    public List<M> getGamesWithLessPlayers() {
        return games.stream()
                .sorted(Comparator.comparingInt(MiniGame::getSize))
                .toList();
    }

    @Override
    public List<M> getGamesWithLessPlayers(GameState state) {
        return getGamesWithLessPlayers().stream()
                .filter(game -> game.getState() == state)
                .toList();
    }

    @Override
    public List<Player> getInGamePlayers() {
        return games.stream()
                .flatMap(game -> game.getPlayers().values().stream())
                .map(BaseGamePlayer::getPlayer)
                .toList();
    }

    @Override
    public boolean isInGame(Player player) {
        return getGame(player).isPresent();
    }

    @Override
    public int getPlayersCount() {
        return games.stream()
                .mapToInt(MiniGame::getSize).sum();
    }

    @Override
    public int getPlayersCountById(String id) {
        return getGameByID(id).map(MiniGame::getSize).orElse(0);
    }

    @Override
    public int getSize() {
        return games.size();
    }

}