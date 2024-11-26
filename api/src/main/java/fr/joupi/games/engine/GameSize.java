package fr.joupi.games.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameSize {

    private String name;
    private int minPlayer, maxPlayer, teamNeeded, teamMaxPlayer;

    public int calculateMapCapacity() {
        return maxPlayer == 999 ? 30 : maxPlayer;
    }

}