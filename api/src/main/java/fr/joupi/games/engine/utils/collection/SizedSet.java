package fr.joupi.games.engine.utils.collection;

import java.util.Set;

public interface SizedSet<E> extends Set<E> {

    int getMaxSize();

    void setMaxSize(int maxSize);

}