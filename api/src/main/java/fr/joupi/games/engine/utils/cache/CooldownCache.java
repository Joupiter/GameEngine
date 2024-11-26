package fr.joupi.games.engine.utils.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.github.benmanes.caffeine.cache.Scheduler;
import fr.joupi.games.engine.utils.concurrent.MultiThreading;
import lombok.Getter;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Getter
public class CooldownCache implements Cooldown {

    private final Duration duration;

    private final Cache<UUID, Long> cache;

    public CooldownCache(Duration duration) {
        this.duration = duration;
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(duration)
                .executor(MultiThreading.pool)
                .scheduler(Scheduler.forScheduledExecutorService(MultiThreading.scheduledPool)).build();
    }

    public CooldownCache(Duration duration, RemovalListener<UUID, Long> removalListener) {
        this.duration = duration;
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(duration)
                .evictionListener(removalListener)
                .executor(MultiThreading.pool)
                .scheduler(Scheduler.forScheduledExecutorService(MultiThreading.scheduledPool)).build();
    }

    public CooldownCache(int duration, TimeUnit timeUnit) {
        this(Duration.of(duration, timeUnit.toChronoUnit()));
    }

    public CooldownCache(int duration, TimeUnit timeUnit, RemovalListener<UUID, Long> removalListener) {
        this(Duration.of(duration, timeUnit.toChronoUnit()), removalListener);
    }

    @Override
    public void put(UUID uuid) {
        put(uuid, System.currentTimeMillis() + duration.toMillis());
    }

    @Override
    public void put(UUID uuid, Long time) {
        cache.put(uuid, time);
    }

    @Override
    public void put(UUID uuid, Long time, long duration, TimeUnit unit) {
        cache.put(uuid, time);
    }

    @Override
    public void putIfAbsent(UUID uuid, Long time) {
        if (contains(uuid))
            cache.put(uuid, time);
    }

    @Override
    public void putIfAbsent(UUID uuid, Long time, long duration, TimeUnit unit) {
        if (contains(uuid))
            put(uuid, time, duration, unit);
    }

    @Override
    public Optional<Long> get(UUID uuid) {
        return Optional.ofNullable(getIfPresent(uuid));
    }

    @Override
    public Long getIfPresent(UUID uuid) {
        return cache.getIfPresent(uuid);
    }

    @Override
    public Long getOrDefault(UUID uuid, Long defaultTime) {
        return get(uuid).orElse(defaultTime);
    }

    @Override
    public void get(UUID uuid, Consumer<Long> consumer) {
        get(uuid).ifPresent(consumer);
    }

    @Override
    public long getTimeLeft(UUID uuid) {
        return Optional.ofNullable(getIfPresent(uuid))
                .map(expirationTime -> Math.max(0, expirationTime - System.currentTimeMillis()))
                .orElse(0L);
    }

    @Override
    public long getTimeLeftInSeconds(UUID uuid) {
        return getTimeLeft(uuid) / 1000;
    }

    @Override
    public long getTimeLeft(UUID uuid, TemporalUnit unit) {
        return unit.getDuration().toMillis() == 0 ? 0 : getTimeLeft(uuid) / unit.getDuration().toMillis();
    }

    @Override
    public ConcurrentMap<UUID, Long> asMap() {
        return cache.asMap();
    }

    @Override
    public void invalidate(UUID uuid) {
        if (contains(uuid))
            cache.invalidate(uuid);
    }

    @Override
    public void invalidateAll() {
        cache.invalidateAll();
    }

    @Override
    public void invalidateAll(Collection<UUID> collection) {
        cache.invalidateAll(collection);
    }

    @Override
    public boolean contains(UUID uuid) {
        return getIfPresent(uuid) != null;
    }

    @Override
    public String getDebugMessage() {
        return "MAP: " + cache.asMap();
    }

}