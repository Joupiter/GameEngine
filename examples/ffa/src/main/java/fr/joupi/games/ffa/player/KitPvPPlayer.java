package fr.joupi.games.ffa.player;

import fr.joupi.games.engine.ffa.player.FFAGamePlayer;

import java.util.UUID;

public class KitPvPPlayer extends FFAGamePlayer {

    public KitPvPPlayer(UUID uuid, int kills, int deaths, int killStreak, int bestKillStreak, boolean spectator) {
        super(uuid, kills, deaths, killStreak, bestKillStreak, spectator);
    }

}