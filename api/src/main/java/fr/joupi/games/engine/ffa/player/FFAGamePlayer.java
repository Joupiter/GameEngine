package fr.joupi.games.engine.ffa.player;

import fr.joupi.games.engine.player.SimpleGamePlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class FFAGamePlayer extends SimpleGamePlayer {

    protected int killStreak, bestKillStreak;
    protected FFAGamePlayerState state;

    public FFAGamePlayer(UUID uuid, int kills, int deaths, int killStreak, int bestKillStreak, boolean spectator) {
        super(uuid, kills, deaths, spectator);
        this.killStreak = killStreak;
        this.bestKillStreak = bestKillStreak;
        this.state = FFAGamePlayerState.IN_LOBBY;
    }

    public void addKillStreak() {
        this.killStreak++;
        this.bestKillStreak = Math.max(killStreak, bestKillStreak);
    }

    public void resetKillStreak() {
        this.killStreak = 0;
    }

}