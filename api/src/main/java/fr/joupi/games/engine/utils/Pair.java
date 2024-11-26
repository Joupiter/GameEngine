package fr.joupi.games.engine.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pair<K, V> {

    private K key;
    private V value;

    public void setKeyAndValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void setBothNull() {
        this.key = null;
        this.value = null;
    }

    public boolean keyIsNull() {
        return key == null;
    }

    public boolean valueIsNull() {
        return value == null;
    }

    public boolean bothIsNull() {
        return key == null && value == null;
    }

}