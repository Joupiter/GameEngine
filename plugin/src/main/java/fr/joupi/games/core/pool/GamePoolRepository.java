package fr.joupi.games.core.pool;

import fr.joupi.games.api.DirectConnectStrategy;
import fr.joupi.games.api.pool.GamePool;
import fr.joupi.games.api.pool.GamePoolManager;
import fr.joupi.games.api.pool.Pool;
import fr.joupi.games.engine.MiniGame;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Slf4j
public class GamePoolRepository implements GamePoolManager {

    private final List<Pool> gamePools;

    public GamePoolRepository() {
        this.gamePools = new ArrayList<>(5);
    }

    @Override
    public void addPool(Pool gamePool) {
        gamePools.add(gamePool);
    }

    @Override
    public void removePool(Pool gamePool) {
        gamePools.removeIf(gamePool::equals);
    }

    @Override
    public void shutdown() {
        gamePools.forEach(Pool::shutdown);
    }

    @Override
    public void leaveGame(Player player) {
        gamePools.forEach(gamePool -> gamePool.getGamesManager().leaveGame(player));
    }

    @Override
    public Optional<Pool> getGamePool(String name) {
        return gamePools.stream()
                .filter(gamePool -> gamePool.getName().contains(name)).findFirst();
    }

    @Override
    public Optional<MiniGame> getGame(Player player) {
        for (Pool gamePool : gamePools)
            return gamePool.getGamesManager().getGame(player);
        return Optional.empty();
    }

    @Override
    public Optional<MiniGame> getGameByID(String id) {
        for (Pool gamePool : gamePools)
            return gamePool.getGamesManager().getGameByID(id);
        return Optional.empty();
    }

    @Override
    public MiniGame getNullableGame(Player player) {
        for (Pool gamePool : gamePools)
            return gamePool.getGamesManager().getGame(player).orElse(null);
        return null;
    }

    @Override
    public MiniGame getNullableGameByID(String id) {
        for (Pool gamePool : gamePools)
            return gamePool.getGamesManager().getGameByID(id).orElse(null);
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <M extends MiniGame> GamePool<M> getGamePool(Class<M> clazz) {
        return gamePools.stream()
                .filter(gamePool -> clazz.isAssignableFrom(gamePool.getGameClass()))
                .map(gamePool -> (GamePool<M>) gamePool)
                .findFirst().orElse(null);
    }

    @Override
    public Pool getGamePoolByClass(Class<Pool> clazz) {
        return gamePools.stream()
                .filter(gamePool -> gamePool.getClass().isInstance(clazz))
                .map(clazz::cast)
                .findFirst().orElse(null);
    }

    @Override
    public List<MiniGame> getAllGames() {
        return gamePools.stream()
                .flatMap(pool -> pool.getGamesManager().getGames().stream())
                .toList();
    }

    @Override
    public List<Pool> getGamePoolByStrategy(DirectConnectStrategy strategy) {
        return gamePools.stream()
                .filter(gamePool -> gamePool.getStrategy().equals(strategy))
                .toList();
    }

    @Override
    public List<Pool> getGamePoolByStrategy(EnumSet<DirectConnectStrategy> strategies) {
        return gamePools.stream()
                .filter(gamePool -> strategies.contains(gamePool.getStrategy()))
                .toList();
    }

    @Override
    public List<Pool> getGamePoolWithoutStrategy(DirectConnectStrategy strategy) {
        return gamePools.stream()
                .filter(gamePool -> !gamePool.getStrategy().equals(strategy))
                .toList();
    }

    @Override
    public List<Pool> getGamePoolWithoutStrategy(EnumSet<DirectConnectStrategy> strategies) {
        return gamePools.stream()
                .filter(gamePool -> !strategies.contains(gamePool.getStrategy()))
                .toList();
    }

    @Override
    public int getGamesCount() {
        return gamePools.stream()
                .mapToInt(gamePool -> gamePool.getGamesManager().getSize()).sum();
    }

    @Override
    public String getDebugMessage() {
        return gamePools.stream()
                .map(Pool::getDebugMessage)
                .collect(Collectors.joining(" || "));
    }

}