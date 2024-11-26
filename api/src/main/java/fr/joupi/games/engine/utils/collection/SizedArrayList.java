package fr.joupi.games.engine.utils.collection;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class SizedArrayList<E> extends ArrayList<E> implements SizedList<E> {

    private int maxSize;

    public SizedArrayList(int maxSize) {
        super(maxSize);
        this.maxSize = maxSize;
    }

    @SafeVarargs
    public SizedArrayList(E... array) {
        this(array.length);
        this.addAll(List.of(array));
    }

    public SizedArrayList() {
        this(50);
    }

    @Override
    public boolean add(E element) {
        return (size() < maxSize) && super.add(element);
    }

    @Override
    public void add(int index, E element) {
        if (size() < maxSize)
            super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return (size() + collection.size() <= maxSize) && super.addAll(collection);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        return (size() + collection.size() <= maxSize) && super.addAll(index, collection);
    }

}