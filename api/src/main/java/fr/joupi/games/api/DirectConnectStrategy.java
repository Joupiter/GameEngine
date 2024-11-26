package fr.joupi.games.api;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DirectConnectStrategy {

    FILL_GAME,
    RANDOM,
    CUSTOM,
    DISABLED;

}