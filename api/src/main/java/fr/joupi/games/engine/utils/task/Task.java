package fr.joupi.games.engine.utils.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public interface Task<T> extends Runnable {

    Logger log = LoggerFactory.getLogger(Task.class);

    ScheduledExecutorService getExecutor();
    ScheduledFuture<?> getFuture();

    AtomicInteger getSecondsLeft();

    AtomicBoolean getStarted();
    AtomicBoolean getCancelled();
    AtomicBoolean getFinished();

    void onStart();
    void onNext(T task);
    void onComplete();
    void onCancel();

    void end();
    void cancel();
    void reset();

    void setSecondsLeft(int seconds);

    default boolean isStarted() {
        return getStarted().get();
    }

    default boolean isCancelled() {
        return getCancelled().get();
    }

    default boolean isFinished() {
        return getFinished().get();
    }

}