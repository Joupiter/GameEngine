package fr.joupi.games.engine.utils;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@UtilityClass
public class CollectionUtils {

    public <T> Optional<T> random(Random random, Collection<T> collection) {
        return collection.stream()
                .skip(collection.isEmpty() ? 0 : random.nextInt(collection.size()))
                .findFirst();
    }

    public <T> Optional<T> random(Collection<T> collection) {
        return random(ThreadLocalRandom.current(), collection);
    }

    public <T> Optional<T> random(Random random, T[] arrays) {
        return Arrays.stream(arrays)
                .skip(arrays.length == 0 ? 0 : random.nextInt(arrays.length))
                .findFirst();
    }

    public <T> Optional<T> random(T[] arrays) {
        return random(ThreadLocalRandom.current(), arrays);
    }

    public static <T> Optional<T> oldRandom(Random random, Collection<T> collection) {
        int randomIndex = random.nextInt(collection.size());
        int index = 0;

        for (T element : collection) {
            if (index == randomIndex)
                return Optional.ofNullable(element);

            index++;
        }

        return Optional.empty();
    }

    public <T> Optional<T> oldRandom(Collection<T> collection) {
        return oldRandom(ThreadLocalRandom.current(), collection);
    }

    public <T> Optional<T> oldRandom(Random random, T[] arrays) {
        return Optional.ofNullable(arrays[random.nextInt(arrays.length)]);
    }

    public <T> Optional<T> oldRandom(T[] arrays) {
        return oldRandom(ThreadLocalRandom.current(), arrays);
    }

    public <T> void putInEmptyIndex(int iterate, int maxSize, Map<Integer, T> map, T object) {
        IntStream.range(0, iterate)
                .mapToObj(i -> findEmptyIndex(maxSize, map))
                .takeWhile(index -> index != -1)
                .forEach(index -> map.put(index, object));
    }

    public int findEmptyIndex(int maxSize, Map<Integer, ?> map) {
        return IntStream.range(0, maxSize)
                .filter(i -> !map.containsKey(i))
                .findFirst()
                .orElse(-1);
    }

}