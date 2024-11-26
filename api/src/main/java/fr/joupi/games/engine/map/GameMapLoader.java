package fr.joupi.games.engine.map;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public interface GameMapLoader<M extends Map> {

    CompletableFuture<M> load(M gameMap);
    void unload(M gameMap);

    CompletableFuture<M> delete(M map);
    CompletableFuture<M> delete(M map, Duration duration);

}