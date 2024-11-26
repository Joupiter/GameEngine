package fr.joupi.games.core.event;

import fr.joupi.games.engine.event.EventBus;
import fr.joupi.games.engine.event.EventSettings;
import fr.joupi.games.engine.utils.Utils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Getter
@Slf4j
public class GameEventBus implements EventBus {

    private final JavaPlugin plugin;

    private final Set<Listener> listeners;
    private final Map<Class<?>, Set<Listener>> registeredListeners;

    public GameEventBus(JavaPlugin plugin) {
        this.plugin = plugin;
        this.listeners = new HashSet<>();
        this.registeredListeners = new HashMap<>();
    }

    @Override
    public <T extends Event> void subscribe(Class<T> eventClass, Consumer<T> consumer) {
        subscribe(eventClass, getDefaultSettings(), consumer);
    }

    @Override
    public <T extends Event> void subscribe(Class<T> eventClass, EventSettings settings, Consumer<T> consumer) {
        plugin.getServer().getPluginManager().registerEvent(eventClass, this, settings.priority(), getEventExecutor(eventClass, consumer), plugin, settings.ignoreCancelled());
    }

    @Override
    public <T extends PlayerEvent> void subscribe(Class<T> eventClass, Predicate<Player> predicate, Consumer<T> consumer) {
        subscribe(eventClass, getDefaultSettings(), event -> {
            if (predicate.test(event.getPlayer()))
                consumer.accept(event);
        });
    }

    @Override
    public void registerListener(Listener listener) {
        if (listeners.add(listener)) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
            log.info("[GameEventBus] Register new listener value: {}", listener.getClass().getSimpleName());
        }
    }

    @Override
    public void registerListeners(Listener... listeners) {
        Arrays.asList(listeners)
                .forEach(this::registerListener);
    }

    @Override
    public void registerListener(Class<?> clazz, Listener listener) {
        if (registeredListeners.computeIfAbsent(clazz, k -> new HashSet<>()).add(listener)) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
            log.info("[GameEventBus] Register new listener key: {} | value: {}", clazz.getSimpleName(), listener.getClass().getSimpleName());
        }
    }

    @Override
    public void registerListeners(Class<?> clazz, Listener... listeners) {
        Arrays.asList(listeners)
                .forEach(listener -> registerListener(clazz, listener));
    }

    @Override
    public void unregisterListener(Class<?> clazz, Listener listener) {
        if (registeredListeners.get(clazz).remove(listener))
            HandlerList.unregisterAll(listener);
    }

    @Override
    public void unregisterListeners(Class<?> clazz, Listener... listeners) {
        Arrays.asList(listeners)
                .forEach(listener -> unregisterListener(clazz, listener));
    }

    @Override
    public void unregisterAllListeners() {
        registeredListeners.keySet().forEach(this::unregisterAllListeners);
        registeredListeners.clear();
    }

    @Override
    public void unregisterAllListeners(Class<?> clazz) {
        registeredListeners.entrySet().stream()
                .filter(entry -> entry.getKey().equals(clazz))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .forEach(listener -> unregisterListeners(clazz, listener));
    }

    @Override
    public void unregisterAllEvents() {
        HandlerList.unregisterAll(this);
    }

    private <T extends Event> EventExecutor getEventExecutor(Class<T> eventClass, Consumer<T> consumer) {
        return (listener, event) -> Utils.ifTrue(event,
                eventClass::isInstance,
                ignored -> consumer.accept(eventClass.cast(event)));
    }

}