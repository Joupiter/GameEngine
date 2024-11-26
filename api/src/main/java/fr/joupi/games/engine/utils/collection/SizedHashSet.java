package fr.joupi.games.engine.utils.collection;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
public class SizedHashSet<E> extends HashSet<E> implements SizedSet<E> {

    private int maxSize;

    public SizedHashSet(int maxSize) {
        super(maxSize);
        this.maxSize = maxSize;
    }

    @SafeVarargs
    public SizedHashSet(E... array) {
        this(array.length);
        this.addAll(List.of(array));
    }

    public SizedHashSet() {
        this(50);
    }

    @Override
    public boolean add(E element) {
        return size() < maxSize && super.add(element);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return (size() + collection.size() <= maxSize) && super.addAll(collection);
    }

}