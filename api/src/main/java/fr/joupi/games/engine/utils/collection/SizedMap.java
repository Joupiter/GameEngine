package fr.joupi.games.engine.utils.collection;

import java.util.Map;

public interface SizedMap<K, V> extends Map<K, V> {

    int getMaxSize();

    void setMaxSize(int maxSize);

}