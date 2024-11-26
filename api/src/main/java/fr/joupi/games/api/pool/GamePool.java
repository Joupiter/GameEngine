package fr.joupi.games.api.pool;

import fr.joupi.games.api.DirectConnectStrategy;
import fr.joupi.games.api.pool.core.GamePoolHeartBeat;
import fr.joupi.games.api.pool.core.GameRepository;
import fr.joupi.games.engine.MiniGame;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

@Getter
@Setter
@Slf4j
public abstract class GamePool<M extends MiniGame> implements Pool {

    protected final Class<M> gameClass;
    protected final String name;

    protected int minPoolSize, maxPoolSize;
    protected DirectConnectStrategy strategy;

    protected final GameManager<M> gamesManager;
    protected final Map<UUID, M> gameCreationCache;

    protected GamePoolHeartBeat<M> gamePoolHeartBeat;

    public GamePool(Class<M> gameClass, String name, int minPoolSize, int maxPoolSize, DirectConnectStrategy strategy) {
        this.gameClass = gameClass;
        this.name = name;
        this.minPoolSize = minPoolSize;
        this.maxPoolSize = maxPoolSize;
        this.strategy = strategy;
        this.gamesManager = new GameRepository<>(this);
        this.gameCreationCache = new HashMap<>(maxPoolSize);
    }

    public abstract Supplier<M> newGame();

    @Override
    public void setup() {
        this.gamePoolHeartBeat = new GamePoolHeartBeat<>(this);
    }

    @Override
    public void loadDefaultGames() {
        gamesManager.addGame(minPoolSize, newGame());
    }

    @Override
    public void addGame() {
        gamesManager.addGame(newGame().get());
    }

    @Override
    public void addGame(int number) {
        gamesManager.addGame(number, newGame());
    }

    @Override
    public void addGame(Object... objects) {
        gamesManager.addGame(newGame(objects).get());
    }

    @Override
    public void addGame(int number, Object... objects) {
        gamesManager.addGame(number, newGame(objects));
    }

    @Override
    public void addGame(UUID uuid, Object object) {
        gamesManager.addGame(newGame(uuid, object).get());
    }

    @Override
    public void addGame(int number, UUID uuid, Object object) {
        gamesManager.addGame(number, newGame(uuid, object));
    }

    @Override
    public void addNecessaryGame() {
        gamesManager.addGame(minPoolSize - gamesManager.getSize(), newGame());
    }

    @Override
    public void shutdown() {
        gamesManager.shutdown();
        gamePoolHeartBeat.shutdown();
        log.info("[GamePool] {}: shutdowned", name);
    }

    public Supplier<M> newGame(Object... objects) {
        throw new RuntimeException();
    }

    public Supplier<M> newGame(UUID uuid, Object... objects) {
        throw new RuntimeException();
    }

    public <T> T getArg(Object[] objects, Class<T> clazz) {
        return clazz.cast(objects[0]);
    }

    public <T> T getArg(int i, Object[] objects, Class<T> clazz) {
        return clazz.cast(objects[i]);
    }

    @Override
    public void sendDebugMessage() {
        log.info("[GamePool] --------[ {} ]--------", name);
        log.info("[GamePool] minPoolSize: {}", minPoolSize);
        log.info("[GamePool] maxPoolSize: {}", maxPoolSize);
        log.info("[GamePool] availableGames: {}", gamesManager.getReachableGames().size());
        log.info("[GamePool] directConnectStrategy: {}", strategy);
    }

    @Override
    public String getDebugMessage() {
        return String.format("name: %s minPoolSize: %d maxPoolSize: %d availableGames: %d directConnectStrategy: %s", name, minPoolSize, maxPoolSize, gamesManager.getReachableGames().size(), strategy);
    }

}