package fr.joupi.games.engine.config;

public interface DoubleConfiguration {

    double getValue();

    double getMin();
    double getMax();

    void setValue(double i);

    default void add(double n) {
        updateValue(getValue() + n);
    }

    default void add() {
        add(1);
    }

    default void remove(double n) {
        updateValue(getValue() - n);
    }

    default void remove() {
        remove(1);
    }

    default void updateValue(double value) {
        setValue(Math.max(getMin(), Math.min(value, getMax())));
    }

}