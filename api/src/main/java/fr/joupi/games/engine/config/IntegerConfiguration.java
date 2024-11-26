package fr.joupi.games.engine.config;

public interface IntegerConfiguration {

    int getValue();

    int getMin();
    int getMax();

    void setValue(int i);

    default void add(int n) {
        updateValue(getValue() + n);
    }

    default void add() {
        add(1);
    }

    default void remove(int n) {
        updateValue(getValue() - n);
    }

    default void remove() {
        remove(1);
    }

    default void updateValue(int integer) {
        setValue(Math.max(getMin(), Math.min(integer, getMax())));
    }

}
