package fr.joupi.games.core;

import fr.joupi.games.api.GameApi;
import fr.joupi.games.api.pool.GamePoolManager;
import fr.joupi.games.core.event.GameEventBus;
import fr.joupi.games.core.pool.GamePoolRepository;
import fr.joupi.games.engine.event.EventBus;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

@Getter
@Setter
public class GameApiProvider implements GameApi {

    private final EventBus eventBus;
    private final GamePoolManager gamePoolManager;

    private boolean devMode;
    private boolean shutdown;

    public GameApiProvider(JavaPlugin plugin) {
        this.eventBus = new GameEventBus(plugin);
        this.gamePoolManager = new GamePoolRepository();
        this.devMode = false;
        this.shutdown = false;
    }

    @Override
    public void sendDebug(Logger logger) {
        logger.info("""
                
                -----------[ Information ]-----------
                isDevMode: {}
                --------------------------------------""", devMode);
    }

    @Override
    public void sendAscii(Logger logger) {
        logger.info("""
                
                   ___                ___           _         \s
                  / __|__ _ _ __  ___| __|_ _  __ _(_)_ _  ___\s
                 | (_ / _` | '  \\/ -_) _|| ' \\/ _` | | ' \\/ -_)
                  \\___\\__,_|_|_|_\\___|___|_||_\\__, |_|_||_\\___|   enabled v1.0.0
                                              |___/           \s
                """);
    }

}