package fr.joupi.games.engine.utils.collection;

import lombok.Getter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.function.IntUnaryOperator;

/*
    FROM: https://github.com/HSGamer/Java-Probability-Collection/
 */

@Getter
public final class ProbabilityLinkedList<E> {

    private final List<ProbabilitySetElement<E>> collection;
    private final IntUnaryOperator randomOperator;

    private int totalProbability;

    public ProbabilityLinkedList(IntUnaryOperator randomNumberGenerator) {
        this.collection = new LinkedList<>();
        this.randomOperator = randomNumberGenerator;
        this.totalProbability = 0;
    }

    private ProbabilityLinkedList(SplittableRandom random) {
        this(random::nextInt);
    }

    public ProbabilityLinkedList() {
        this(new SplittableRandom());
    }

    public ProbabilityLinkedList(long seed) {
        this(new SplittableRandom(seed));
    }

    public int size() {
        return collection.size();
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public boolean contains(E object) {
        if (object == null)
            throw new IllegalArgumentException("Cannot check if null object is contained in this collection");

        for (ProbabilitySetElement<E> entry : collection) {
            if (entry.object().equals(object))
                return true;
        }

        return false;
    }

    public Iterator<ProbabilitySetElement<E>> iterator() {
        return collection.iterator();
    }

    public void add(E object, int probability) {
        if (object == null)
            throw new IllegalArgumentException("Cannot add null object");

        if (probability <= 0)
            throw new IllegalArgumentException("Probability must be greater than 0");

        var entry = new ProbabilitySetElement<>(object, probability);

        collection.add(entry);
        totalProbability += probability;
    }

    public boolean remove(E object) {
        if (object == null)
            throw new IllegalArgumentException("Cannot remove null object");

        Iterator<ProbabilitySetElement<E>> it = collection.iterator();
        var removed = false;

        // Remove all instances of the object
        while (it.hasNext()) {
            var entry = it.next();

            if (entry.object().equals(object)) {
                totalProbability -= entry.probability();
                it.remove();
                removed = true;
            }
        }

        return removed;
    }

    public void clear() {
        collection.clear();
        totalProbability = 0;
    }

    public E get() {
        if (collection.isEmpty())
            throw new IllegalStateException("Cannot get an object out of a empty collection");

        int random = randomOperator.applyAsInt(totalProbability);
        int index = 0;

        for (ProbabilitySetElement<E> entry : collection) {
            index += entry.probability();

            if (random < index)
                return entry.object();
        }

        throw new IllegalStateException("Failed to get an object from the collection");
    }

    public record ProbabilitySetElement<T>(T object, int probability) {}

}