package fr.joupi.games.engine.map;

import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.player.BaseGamePlayer;
import fr.joupi.games.engine.point.SinglePoint;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface MapManager<M extends Map> {

    void setup();
    void end();

    <G extends MiniGame> G getGame();
    <L extends GameMapLoader<?>> L getMapLoader();

    GameMapCleaner<?> getMapCleaner();

    Set<? extends M> getMaps();
    Set<String> getTemplates();

    Optional<? extends M> getMap(String mapName);

    Optional<SinglePoint> getMapSpawnPoint(String mapName);

    void addMap(M map);
    void addMap(M map, boolean load);
    void addMaps(boolean load, M... maps);

    void removeMap(M map);
    void removeMap(M map, boolean delete);

    void removeAllMap(boolean delete);

    CompletableFuture<M> load(M map);
    void unload(M map);

    CompletableFuture<M> delete(M map);
    CompletableFuture<M> delete(M map, Duration duration);

    void teleport(M map, Collection<BaseGamePlayer> collection);
    void teleport(String mapName, Collection<BaseGamePlayer> collection);

    String getFormattedTypeMapName(String mapName);
    String getFormattedMapTemplateName(String name);

    boolean containsMap(M map);
    boolean containsMap(String mapName);

    int getSize();

}