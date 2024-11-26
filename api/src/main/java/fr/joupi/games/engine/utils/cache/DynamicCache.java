package fr.joupi.games.engine.utils.cache;

import com.github.benmanes.caffeine.cache.Cache;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public interface DynamicCache<K, V> {

    Cache<K, V> getCache();

    void put(K key, V value);
    void put(K key, V value, long duration, TimeUnit unit);

    void putIfAbsent(K key, V value);
    void putIfAbsent(K key, V value, long duration, TimeUnit unit);

    Optional<V> get(K key);

    V getIfPresent(K key);
    V getOrDefault(K key, V defaultValue);

    void get(K key, Consumer<V> consumer);

    ConcurrentMap<K, V> asMap();

    void invalidate(K key);

    void invalidateAll();
    void invalidateAll(Collection<K> collection);

    boolean contains(K key);

    String getDebugMessage();
    
}