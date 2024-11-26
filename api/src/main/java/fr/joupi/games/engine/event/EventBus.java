package fr.joupi.games.engine.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface EventBus extends Listener {

    Set<Listener> getListeners();
    Map<Class<?>, Set<Listener>> getRegisteredListeners();

    <T extends Event> void subscribe(Class<T> eventClass, Consumer<T> consumer);
    <T extends Event> void subscribe(Class<T> eventClass, EventSettings settings, Consumer<T> consumer);

    <T extends PlayerEvent> void subscribe(Class<T> eventClass, Predicate<Player> predicate, Consumer<T> consumer);

    default EventSettings getDefaultSettings() {
        return new EventSettings(EventPriority.NORMAL, false);
    }

    void registerListener(Listener listener);
    void registerListeners(Listener... listeners);

    void registerListener(Class<?> clazz, Listener listener);
    void registerListeners(Class<?> clazz, Listener... listeners);

    void unregisterListener(Class<?> clazz, Listener listener);
    void unregisterListeners(Class<?> clazz, Listener... listeners);

    void unregisterAllListeners();
    void unregisterAllListeners(Class<?> clazz);

    void unregisterAllEvents();

}