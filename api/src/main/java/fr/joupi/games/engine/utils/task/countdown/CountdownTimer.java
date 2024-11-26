package fr.joupi.games.engine.utils.task.countdown;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.function.Consumer;

@Getter
@Setter
@Accessors(chain = true)
public class CountdownTimer extends CountdownTask {

    private Consumer<CountdownTask> nextConsumer;
    private Runnable startRunnable, completeRunnable, cancelRunnable;

    public CountdownTimer(int duration, Runnable startRunnable, Runnable completeRunnable, Runnable cancelRunnable, Consumer<CountdownTask> nextConsumer) {
        super(duration);
        this.startRunnable = startRunnable;
        this.completeRunnable = completeRunnable;
        this.cancelRunnable = cancelRunnable;
        this.nextConsumer = nextConsumer;
    }

    public CountdownTimer(int duration, Runnable startRunnable, Runnable completeRunnable, Consumer<CountdownTask> nextConsumer) {
        this(duration, startRunnable, completeRunnable, null, nextConsumer);
    }

    public CountdownTimer(int duration) {
        this(duration, null, null, null, null);
    }

    @Override
    public void onStart() {
        if (startRunnable != null)
            startRunnable.run();
    }

    @Override
    public void onNext(CountdownTask task) {
        if (nextConsumer != null)
            nextConsumer.accept(task);
    }

    @Override
    public void onComplete() {
        if (completeRunnable != null)
            completeRunnable.run();
    }

    @Override
    public void onCancel() {
        if (cancelRunnable != null)
             cancelRunnable.run();
    }

}