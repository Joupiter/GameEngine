package fr.joupi.games.engine.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public class IntegerConfigurationValue implements IntegerConfiguration {

    private final Consumer<Integer> valueConsumer;

    private int value, min, max;

    @Override
    public void setValue(int value) {
        this.value = value;
        valueConsumer.accept(value);
    }

}