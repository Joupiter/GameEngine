package fr.joupi.games.engine.utils.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import fr.joupi.games.engine.utils.concurrent.MultiThreading;
import lombok.Getter;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Getter
public class TimedCache<K, V> implements DynamicCache<K, V> {

    private final Cache<K, V> cache;

    public TimedCache(int defaultDuration, TimeUnit timeUnit) {
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(defaultDuration, timeUnit)
                .executor(MultiThreading.pool)
                .scheduler(Scheduler.forScheduledExecutorService(MultiThreading.scheduledPool)).build();
    }

    public TimedCache() {
        this(1, TimeUnit.HOURS);
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void put(K key, V value, long duration, TimeUnit unit) {
        cache.put(key, value);
        MultiThreading.schedule(() -> cache.invalidate(key), duration, unit);
    }

    @Override
    public void putIfAbsent(K key, V value) {
        if (cache.getIfPresent(key) == null)
            cache.put(key, value);
    }

    @Override
    public void putIfAbsent(K key, V value, long duration, TimeUnit unit) {
        if (cache.getIfPresent(key) == null)
            put(key, value, duration, unit);
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(cache.getIfPresent(key));
    }

    @Override
    public V getIfPresent(K key) {
        return cache.getIfPresent(key);
    }

    @Override
    public V getOrDefault(K key, V defaultValue) {
        return get(key).orElse(defaultValue);
    }

    @Override
    public void get(K key, Consumer<V> consumer) {
        V value = cache.getIfPresent(key);

        if (value != null) consumer.accept(value);
    }

    @Override
    public ConcurrentMap<K, V> asMap() {
        return cache.asMap();
    }

    @Override
    public void invalidate(K key) {
        if (cache.getIfPresent(key) != null)
            cache.invalidate(key);
    }

    @Override
    public void invalidateAll() {
        cache.invalidateAll();
    }

    @Override
    public void invalidateAll(Collection<K> collection) {
        cache.invalidateAll(collection);
    }

    @Override
    public boolean contains(K key) {
        return cache.getIfPresent(key) != null;
    }

    @Override
    public String getDebugMessage() {
        return "MAP: " + cache.asMap();
    }

}