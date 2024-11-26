package fr.joupi.games.engine.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public class DoubleConfigurationValue implements DoubleConfiguration {

    private final Consumer<Double> valueConsumer;

    private double value, min, max;

    @Override
    public void setValue(double value) {
        this.value = value;
        valueConsumer.accept(value);
    }

}