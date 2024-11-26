package fr.joupi.games.engine.player;

import fr.joupi.games.engine.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class SimpleGamePlayer extends BaseGamePlayer {

    protected int kills, deaths;

    public SimpleGamePlayer(UUID uuid, int kills, int deaths, boolean spectator) {
        super(uuid, spectator);
        this.kills = kills;
        this.deaths = deaths;
    }

    public SimpleGamePlayer(UUID uuid, boolean spectator) {
        this(uuid, 0, 0, spectator);
    }

    public void addKill() {
        this.kills++;
    }

    public void addDeath() {
        this.deaths++;
    }

    public String getRatio() {
        return Utils.decimalFormat.format(deaths == 0 ? (double) kills : (double) kills / deaths);
    }

}