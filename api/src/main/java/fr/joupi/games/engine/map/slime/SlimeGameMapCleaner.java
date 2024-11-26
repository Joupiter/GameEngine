package fr.joupi.games.engine.map.slime;

import fr.joupi.games.engine.map.GameMapCleaner;
import fr.joupi.games.engine.map.MapManager;
import fr.joupi.games.engine.utils.func.ThrowingRunnable;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class SlimeGameMapCleaner extends GameMapCleaner<SlimeWorldLoader> {

    public SlimeGameMapCleaner(MapManager<?> mapManager, SlimeWorldLoader mapLoader) {
        super(mapLoader.getWorldDir("slime_worlds"), mapManager, mapLoader);
    }

    @Override
    public void cleanUp() {
        while (!queue.isEmpty()) {
            queue.poll()
                    .thenAcceptAsync(file -> ThrowingRunnable.of(() -> Files.deleteIfExists(file.toPath())).run())
                    .whenComplete((unused, throwable) -> {
                        if (throwable != null)
                            log.error("ERROR WHEN DELETED OLD FILE", throwable);
                    });
        }
    }

    @Override
    public void scan() {
        Arrays.stream(Objects.requireNonNull(mapDir.listFiles()))
                .filter(file -> !file.getName().contains("template")
                        && !mapManager.containsMap(file.getName().split("\\.")[0]))
                .forEach(this::addFile);
    }

}