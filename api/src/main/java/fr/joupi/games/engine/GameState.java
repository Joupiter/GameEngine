package fr.joupi.games.engine;

import lombok.Getter;

@Getter
public enum GameState {

    LOADING,
    LOADING_IN_PROGRESS,

    WAIT,
    STARTING,
    ALWAYS_PLAYING,
    IN_GAME,
    END;

    public boolean is(GameState state) {
        return this == state;
    }

    public boolean is(GameState... states) {
        for (GameState state : states)
            if (this == state) return true;

        return false;
    }

}