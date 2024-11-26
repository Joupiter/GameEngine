package fr.joupi.games.engine.utils.collection;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
public class SizedHashMap<K, V> extends HashMap<K, V> implements SizedMap<K, V> {

    private int maxSize;

    public SizedHashMap(int maxSize) {
        super(maxSize);
        this.maxSize = maxSize;
    }

    public SizedHashMap() {
        this(50);
    }

    @Override
    public V put(K key, V value) {
        return size() < maxSize ? super.put(key, value) : null;
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return size() < maxSize ? super.putIfAbsent(key, value) : null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (size() + m.size() <= maxSize)
            super.putAll(m);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return size() < maxSize ? super.compute(key, remappingFunction) : null;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return size() < maxSize ? super.computeIfAbsent(key, mappingFunction) : null;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return size() < maxSize ? super.computeIfPresent(key, remappingFunction) : null;
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return size() < maxSize ? super.merge(key, value, remappingFunction) : null;
    }

}
