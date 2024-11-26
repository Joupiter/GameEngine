package fr.joupi.games.engine.map;

import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.player.BaseGamePlayer;
import fr.joupi.games.engine.point.SinglePoint;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
public abstract class GameMapManager<G extends MiniGame, M extends Map, L extends GameMapLoader<M>> implements MapManager<M> {

    protected final G game;

    protected final L mapLoader;
    protected GameMapCleaner<?> mapCleaner;

    protected final Set<M> maps;
    protected final Set<String> templates;

    public GameMapManager(G game, L mapLoader) {
        this.game = game;
        this.mapLoader = mapLoader;
        this.maps = new HashSet<>();
        this.templates = new HashSet<>();
    }

    @Override
    public Optional<M> getMap(String mapName) {
        return maps.stream().filter(gameMap -> gameMap.getName().equals(mapName)).findFirst();
    }

    @Override
    public Optional<SinglePoint> getMapSpawnPoint(String mapName) {
        return getMap(mapName).map(Map::getSpawn);
    }

    @Override
    public void addMap(M map, boolean load) {
        maps.add(map);
        if (load) load(map);
    }

    @Override
    public void addMap(M map) {
        addMap(map, false);
    }

    @Override
    @SafeVarargs
    public final void addMaps(boolean load, M... maps) {
        Arrays.asList(maps)
                .forEach(map -> addMap(map, load));
    }

    @Override
    public void removeMap(M map, boolean delete) {
        maps.remove(map);
        unload(map);
        if (delete) delete(map);
    }

    @Override
    public void removeMap(M map) {
        removeMap(map, false);
    }

    @Override
    public void removeAllMap(boolean delete) {
        maps.forEach(map -> removeMap(map, delete));
    }

    @Override
    public CompletableFuture<M> load(M map) {
        maps.add(map);
        return mapLoader.load(map);
    }

    @Override
    public void unload(M map) {
        mapLoader.unload(map);
    }

    @Override
    public CompletableFuture<M> delete(M map) {
        removeMap(map, false);
        return mapLoader.delete(map);
    }

    @Override
    public CompletableFuture<M> delete(M map, Duration duration) {
        removeMap(map, false);
        return mapLoader.delete(map, duration);
    }

    @Override
    public void teleport(M map, Collection<BaseGamePlayer> collection) {
        Optional.ofNullable(map.getSpawn())
                .ifPresent(singlePoint -> collection.forEach(singlePoint::teleport));
    }

    @Override
    public void teleport(String mapName, Collection<BaseGamePlayer> collection) {
        getMapSpawnPoint(mapName)
                .ifPresent(singlePoint -> collection.forEach(singlePoint::teleport));
    }

    @Override
    public String getFormattedTypeMapName(String mapName) {
        return game.getFullName() + "-" + mapName;
    }

    @Override
    public String getFormattedMapTemplateName(String name) {
        return name + "-template";
    }

    @Override
    public boolean containsMap(M map) {
        return maps.contains(map);
    }

    @Override
    public boolean containsMap(String mapName) {
        return maps.stream().anyMatch(map -> map.getName().equals(mapName));
    }

    @Override
    public int getSize() {
        return maps.size();
    }

}