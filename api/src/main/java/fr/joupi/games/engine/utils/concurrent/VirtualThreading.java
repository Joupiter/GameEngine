package fr.joupi.games.engine.utils.concurrent;

import fr.joupi.games.engine.utils.Utils;
import lombok.experimental.UtilityClass;

import java.util.concurrent.*;

@UtilityClass
public class VirtualThreading {

    public final ExecutorService pool = Executors.newFixedThreadPool(20, Thread.ofVirtual().name("virtual-thread-", 0).factory());
    public final ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(20, Thread.ofVirtual().name("virtual-thread-", 0).factory());

    public ScheduledFuture<?> schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
        return scheduledPool.schedule(runnable, delay, timeUnit);
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long initialDelay, long period, TimeUnit timeUnit) {
        return scheduledPool.scheduleAtFixedRate(runnable, initialDelay, period, timeUnit);
    }

    public void execute(Runnable runnable) {
        pool.execute(runnable);
    }

    public CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, pool);
    }

    public Future<?> submit(Runnable runnable) {
        return pool.submit(runnable);
    }

    public void shutdown() {
        Utils.awaitTerminationAfterShutdown(pool, 3, TimeUnit.SECONDS);
        Utils.awaitTerminationAfterShutdown(scheduledPool, 3, TimeUnit.SECONDS);
    }

}