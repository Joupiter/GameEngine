package fr.joupi.games.simple.player;

import fr.joupi.games.engine.player.SimpleGamePlayer;

import java.util.UUID;

public class MySimplePlayer extends SimpleGamePlayer {

    public MySimplePlayer(UUID uuid, int kills, int deaths, boolean spectator) {
        super(uuid, kills, deaths, spectator);
    }

}