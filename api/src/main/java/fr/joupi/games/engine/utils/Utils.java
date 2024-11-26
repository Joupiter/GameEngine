package fr.joupi.games.engine.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.joupi.games.engine.point.SinglePoint;
import fr.joupi.games.engine.utils.func.ThrowingRunnable;
import fr.joupi.games.engine.utils.json.adapter.LocationAdapter;
import fr.joupi.games.engine.utils.json.adapter.SpawnPointAdapter;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

@UtilityClass
public class Utils {

    public final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public final DecimalFormat complexDecimalFormat = new DecimalFormat("#,##0.##");

    public final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Location.class, new LocationAdapter())
            .registerTypeAdapter(SinglePoint.class, new SpawnPointAdapter())
            .disableHtmlEscaping().setPrettyPrinting().create();

    public <T> void ifTrue(T object, Predicate<T> predicate, Consumer<T> consumer) {
        if (predicate.test(object))
            consumer.accept(object);
    }

    public <T> void ifFalse(T object, Predicate<T> predicate, Consumer<T> consumer) {
        if (!predicate.test(object))
            consumer.accept(object);
    }

    public <T> void range(int start, int end, T object, BiConsumer<T, Integer> consumer) {
        for (int i = start; i < end; i++)
            consumer.accept(object, i);
    }

    public void range(int start, int end, IntConsumer consumer) {
        for (int i = start; i < end; i++)
            consumer.accept(i);
    }

    public <T> void range(int start, int end, T object, Consumer<T> consumer) {
        for (int i = start; i < end; i++)
            consumer.accept(object);
    }

    public void range(int start, int end, Runnable runnable) {
        for (int i = start; i < end; i++)
            runnable.run();
    }

    public <T> void biRange(int iterate, T object, BiConsumer<T, Integer> consumer) {
        range(0, iterate, object, consumer);
    }

    public void range(int iterate, IntConsumer consumer) {
        range(0, iterate, consumer);
    }

    public <T> void range(int iterate, T object, Consumer<T> consumer) {
        range(0, iterate, object, consumer);
    }

    public void range(int iterate, Runnable runnable) {
        range(0, iterate, runnable);
    }

    public void awaitTerminationAfterShutdown(ExecutorService threadPool, long timeOut, TimeUnit timeUnit) {
        threadPool.shutdown();
        ThrowingRunnable.of(() -> {
            if (!threadPool.awaitTermination(timeOut, timeUnit))
                threadPool.shutdownNow();
        }, exception -> {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        });
    }

}