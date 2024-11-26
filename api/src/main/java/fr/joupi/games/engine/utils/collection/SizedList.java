package fr.joupi.games.engine.utils.collection;

import java.util.List;

public interface SizedList<E> extends List<E> {

    int getMaxSize();

    void setMaxSize(int maxSize);

}