package fr.joupi.games.engine.player;

import fr.joupi.games.engine.team.GameTeam;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public abstract class GamePlayer<T extends GameTeam<?>> extends SimpleGamePlayer {

    protected T team;

    public GamePlayer(UUID uuid, int kills, int deaths, T team, boolean spectator) {
        super(uuid, kills, deaths, spectator);
        this.team = team;
    }

    public GamePlayer(UUID uuid, int kills, int deaths, boolean spectator) {
        this(uuid, kills, deaths, null, spectator);
    }

    public <G extends GamePlayer<?>> void setTeam(GameTeam<G> team) {
        this.team = (T) team;
    }

    public <G extends GamePlayer<?>> boolean sameTeam(G gamePlayer) {
        var team = gamePlayer.getTeam();

        return team != null && team.equals(this.team);
    }

    public boolean hasTeam() {
        return team != null;
    }

    public boolean hasNoTeam() {
        return team == null;
    }

}