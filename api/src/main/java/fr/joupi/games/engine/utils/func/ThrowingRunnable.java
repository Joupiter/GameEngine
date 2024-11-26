package fr.joupi.games.engine.utils.func;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public interface ThrowingRunnable extends Runnable {

    void runWithException() throws Exception;

    @Override
    default void run() {
        run(RuntimeException::new);
    }

    default void run(BiFunction<String, Exception, RuntimeException> exceptionWrapper) {
        try {
            runWithException();
        } catch (RuntimeException runtimeException) {
            throw runtimeException;
        } catch (Exception exception) {
            throw exceptionWrapper.apply(exception.getMessage(), exception);
        }
    }

    default ThrowingRunnable throwing(BiFunction<String, Exception, RuntimeException> exceptionWrapper) {
        return new ThrowingRunnable() {
            @Override
            public void runWithException() throws Exception {
                ThrowingRunnable.this.runWithException();
            }

            @Override
            public void run() {
                run(exceptionWrapper);
            }
        };
    }

    static ThrowingRunnable of(ThrowingRunnable runnable) {
        return runnable;
    }

    static ThrowingRunnable of(ThrowingRunnable runnable, BiFunction<String, Exception, RuntimeException> exceptionWrapper) {
        return runnable.throwing(exceptionWrapper);
    }

    static void of(ThrowingRunnable runnable, Consumer<Exception> exceptionConsumer) {
        try {
            runnable.runWithException();
        } catch (Exception exception) {
            exceptionConsumer.accept(exception);
        }
    }

}