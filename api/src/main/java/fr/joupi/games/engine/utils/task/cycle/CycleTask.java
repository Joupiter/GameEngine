package fr.joupi.games.engine.utils.task.cycle;

import fr.joupi.games.engine.utils.concurrent.MultiThreading;
import fr.joupi.games.engine.utils.concurrent.VirtualThreading;
import fr.joupi.games.engine.utils.task.Task;
import lombok.Getter;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public abstract class CycleTask implements Task<CycleTask> {

    private final ScheduledExecutorService executor;
    private ScheduledFuture<?> future;

    protected final int duration;

    protected final AtomicInteger secondsLeft;
    protected final AtomicBoolean started, cancelled, finished;

    public CycleTask(int duration, boolean virtual) {
        this.executor = virtual ? VirtualThreading.scheduledPool : MultiThreading.scheduledPool;
        this.duration = duration;
        this.secondsLeft = new AtomicInteger(duration);
        this.started = new AtomicBoolean(false);
        this.cancelled = new AtomicBoolean(false);
        this.finished = new AtomicBoolean(true);
    }

    public CycleTask(int duration) {
        this(duration, true);
    }

    @Override
    public void run() {
        if (future != null && !future.isDone()
                && (started.get() || !finished.get() || cancelled.get())) return;

        started.set(true);
        cancelled.set(false);
        finished.set(false);
        secondsLeft.set(duration);
        onStart();

        future = executor.scheduleAtFixedRate(() -> {
            if (secondsLeft.get() <= 0 || cancelled.get()) {

                if (finished.get()) {

                    if (cancelled.get()) onCancel();
                    else onComplete();

                    finished.set(true);
                    started.set(false);
                    future.cancel(false);
                    return;
                }

                onNext(this);
                secondsLeft.set(duration);
                return;
            }

            secondsLeft.decrementAndGet();
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void end() {
        if (future != null)
            future.cancel(false);

        started.set(false);
        cancelled.set(false);
        finished.set(true);
        onComplete();
    }

    @Override
    public void cancel() {
        if (future != null)
            future.cancel(false);

        started.set(false);
        cancelled.set(true);
        finished.set(true);
        onCancel();
    }

    @Override
    public void reset() {
        secondsLeft.set(duration);
        started.set(false);
    }

    @Override
    public void setSecondsLeft(int seconds) {
        this.secondsLeft.set(seconds);
    }

}