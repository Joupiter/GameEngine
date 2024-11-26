package fr.joupi.games.api.addon;

import fr.joupi.games.api.GameApi;
import fr.joupi.games.api.pool.Pool;
import fr.joupi.games.engine.utils.Pair;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public abstract class GameAddon<P extends Pool> extends JavaPlugin implements Addon {

    protected final P pool;

    protected GameApi api;

    @Override
    public void onEnable() {
        this.api = GameApi.getInstance();

        api.getGamePoolManager().addPool(pool);
        pool.setup();

        enable();
        pool.sendDebugMessage();
    }

    @Override
    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void registerListener(Listener... listeners) {
        Arrays.asList(listeners)
                .forEach(this::registerListener);
    }

    @Override
    public void registerCommand(String command, CommandExecutor executor) {
        getCommand(command).setExecutor(executor);
    }

    @SafeVarargs
    @Override
    public final void registerCommand(Pair<String, CommandExecutor>... commands) {
        for (Pair<String, CommandExecutor> pair : commands) {
            // SECURITY CHECK
            if (pair.bothIsNull() || pair.getValue() == null) return;

            getCommand(pair.getKey()).setExecutor(pair.getValue());
        }
    }

    @Override
    public void onDisable() {
        this.disable();
    }

    @Override
    public void sendDebug() {

    }

}