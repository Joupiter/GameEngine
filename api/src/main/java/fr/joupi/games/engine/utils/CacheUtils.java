package fr.joupi.games.engine.utils;

import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.utils.cache.Cooldown;
import fr.joupi.games.engine.utils.cache.DynamicCache;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Slf4j
@UtilityClass
public class CacheUtils {

    public final Map<String, DynamicCache<?, ?>> dynamicCacheMap = new HashMap<>();
    public final Map<String, Cooldown> cooldowns = new HashMap<>();

    public void addCache(String name, DynamicCache<?, ?> cache) {
        dynamicCacheMap.putIfAbsent(name, cache);
    }

    public void addCooldown(String name, Cooldown cooldown) {
        cooldowns.putIfAbsent(name, cooldown);
    }

    public void removeCache(String name) {
        dynamicCacheMap.remove(name);
    }

    public void removeCooldown(String name) {
        cooldowns.remove(name);
    }

    public DynamicCache<?, ?> getCache(String name) {
        return dynamicCacheMap.get(name);
    }

    public Cooldown getCooldown(String name) {
        return cooldowns.get(name);
    }

    @SuppressWarnings("unchecked")
    public <K, V> DynamicCache<K, V> getCache(String name, DynamicCache<K, V> cache) {
        return (DynamicCache<K, V>) dynamicCacheMap.computeIfAbsent(name, s -> cache);
    }

    public Cooldown getCooldown(String name, Cooldown cooldown) {
        return cooldowns.computeIfAbsent(name, s -> cooldown);
    }

    public void removeCacheIf(Predicate<String> predicate) {
        dynamicCacheMap.keySet().stream()
                .filter(predicate)
                .forEach(dynamicCacheMap::remove);
    }

    public void removeCooldownIf(Predicate<String> predicate) {
        cooldowns.keySet().stream()
                .filter(predicate)
                .forEach(cooldowns::remove);
    }

    public void removeAll() {
        dynamicCacheMap.clear();
        cooldowns.clear();
    }

    public void cleanRemoveAll() {
        cooldowns.values().forEach(CacheUtils::cleanRemove);
        dynamicCacheMap.values().forEach(CacheUtils::cleanRemove);
        dynamicCacheMap.clear();
        cooldowns.clear();
    }

    public void cleanRemove(DynamicCache<?, ?> cache) {
        cache.getCache().invalidateAll();
        cache.getCache().cleanUp();
    }

    public void removeAll(MiniGame game){
        removeCacheIf(name -> name.startsWith(game.getFullName()));
        removeCooldownIf(name -> name.startsWith(game.getFullName()));
    }

    public void sendDebug() {
        dynamicCacheMap.forEach((name, cache) -> log.info("{} : {}", name, cache.getDebugMessage()));
        cooldowns.forEach((name, cooldown) -> log.info("{} : {}", name, cooldown.getDebugMessage()));
    }

}