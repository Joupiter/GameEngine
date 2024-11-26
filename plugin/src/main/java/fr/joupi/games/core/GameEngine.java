package fr.joupi.games.core;

import fr.joupi.games.api.GameApi;
import fr.joupi.games.core.listener.GameConnectionListener;
import fr.joupi.games.engine.utils.CacheUtils;
import fr.joupi.games.engine.utils.concurrent.BukkitThreading;
import fr.joupi.games.engine.utils.concurrent.MultiThreading;
import fr.joupi.games.engine.utils.concurrent.VirtualThreading;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.plugin.java.JavaPlugin;

@Slf4j
@Getter
public class GameEngine extends JavaPlugin {

    private GameApi gameApi;

    @Override
    public void onEnable() {
        this.gameApi = GameApi.setProvider(new GameApiProvider(this));
        BukkitThreading.setPlugin(this);

        gameApi.sendAscii(log);

        if (getServer().getPluginManager().getPlugin("SlimeWorldManager") == null)
            log.warn("[GameEngine] Care plugin 'SlimeWorldManager' not founded.");

        var gamePoolManager = gameApi.getGamePoolManager();

        gameApi.getEventBus().registerListeners(
                new GameConnectionListener(gamePoolManager)
        );
    }

    @Override
    public void onDisable() {
        gameApi.setShutdown(true);
        gameApi.getGamePoolManager().shutdown();
        CacheUtils.cleanRemoveAll();
        VirtualThreading.shutdown();
        MultiThreading.shutdown();
        log.info("[GameEngine] GAME ENGINE BYE BYE.");
    }

}