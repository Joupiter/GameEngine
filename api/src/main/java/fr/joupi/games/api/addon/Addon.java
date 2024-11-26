package fr.joupi.games.api.addon;

import fr.joupi.games.api.GameApi;
import fr.joupi.games.api.pool.Pool;
import fr.joupi.games.engine.utils.Pair;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Addon {

    Logger log = LoggerFactory.getLogger(Addon.class);

    void enable();
    void disable();

    Pool getPool();

    GameApi getApi();

    void registerListener(Listener listener);
    void registerListener(Listener... listeners);

    void registerCommand(String command, CommandExecutor executor);
    void registerCommand(Pair<String, CommandExecutor>... commands);

    void sendDebug();

}