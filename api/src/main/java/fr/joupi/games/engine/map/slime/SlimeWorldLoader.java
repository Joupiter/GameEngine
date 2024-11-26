package fr.joupi.games.engine.map.slime;

import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.exceptions.UnknownWorldException;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.properties.SlimeProperties;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import fr.joupi.games.engine.map.GameMapLoader;
import fr.joupi.games.engine.utils.concurrent.BukkitThreading;
import fr.joupi.games.engine.utils.concurrent.MultiThreading;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
@AllArgsConstructor
public class SlimeWorldLoader implements GameMapLoader<SlimeMap> {

    private static final SlimePlugin slimePlugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");

    private final SlimeLoader slimeLoader;

    public SlimeWorldLoader(String slimeLoaderName) {
        this.slimeLoader = slimePlugin.getLoader(slimeLoaderName);
    }

    public SlimeWorldLoader() {
        this("file");
    }

    public File getWorldDir(String dirName) {
        return new File(Bukkit.getWorldContainer().getAbsolutePath() + File.separator + dirName);
    }

    public CompletableFuture<SlimeMap> load(SlimeMap map) {
        CompletableFuture<SlimeMap> future = new CompletableFuture<>();

        BukkitThreading.runTaskAsynchronously(() -> {
            try {
                var start = System.currentTimeMillis();
                var properties = new SlimePropertyMap();

                properties.setString(SlimeProperties.DIFFICULTY, "normal");
                properties.setBoolean(SlimeProperties.PVP, true);

                var slimeWorld = slimePlugin.loadWorld(slimeLoader, map.getTemplateName(), true, properties).clone(map.getName());

                BukkitThreading.runTask(() -> {
                    try {
                        slimePlugin.generateWorld(slimeWorld);
                    } catch (Exception exception) {
                        log.error("[SlimeWorldLoader] Failed to generate world {}: {}", map.getName(), exception.getMessage());
                    }

                    //log.info("[SlimeWorldLoader] World {} generated in {}ms", map.getName(), System.currentTimeMillis() - start);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            var world = Bukkit.getWorld(map.getName());

                            if (world != null) {
                                log.info("[SlimeWorldLoader] World {} fully loaded in {}ms", map.getName(), System.currentTimeMillis() - start);
                                map.setWorld(world);
                                future.complete(map);
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(BukkitThreading.plugin, 2, 2);

                });
            } catch (Exception exception) {
                log.error("[SlimeWorldLoader] Failed to generate world {}: {}", map.getName(), exception.getMessage());
            }
        });

        return future;
    }

    @Override
    public void unload(SlimeMap map) {
        var world = Bukkit.getWorld(map.getName());
        var firstWorld = Bukkit.getWorlds().get(0);

        if (world == null) return;

        if (firstWorld != null)
            BukkitThreading.runTask(() -> world.getPlayers().forEach(player -> player.teleport(firstWorld.getSpawnLocation())));

        Bukkit.unloadWorld(world, false);
    }

    @Override
    public CompletableFuture<SlimeMap> delete(SlimeMap map) {
        return CompletableFuture
                .supplyAsync(() -> {
                    try {
                        slimeLoader.deleteWorld(map.getName());
                        return map;
                    } catch (UnknownWorldException | IOException exception) {
                        throw new RuntimeException(exception);
                    }
                })
                .whenCompleteAsync((slimeMap, throwable) -> {
                    if (throwable != null)
                        log.error("[SlimeWorldLoader] ERROR WHEN DELETE FILE: {}", throwable.getMessage());
                    else
                        log.info("[SlimeWorldLoader] DELETE COMPLETE FOR: {}", slimeMap.getName());
                });
    }

    @Override
    public CompletableFuture<SlimeMap> delete(SlimeMap map, Duration duration) {
        CompletableFuture<SlimeMap> future = new CompletableFuture<>();

        MultiThreading.schedule(() -> delete(map)
                .whenCompleteAsync((result, throwable) -> {
                    if (throwable != null)
                        future.completeExceptionally(throwable);
                    else
                        future.complete(result);
                }), duration.getSeconds(), TimeUnit.SECONDS);

        return future;
    }

}