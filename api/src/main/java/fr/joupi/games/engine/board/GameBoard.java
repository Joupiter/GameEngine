package fr.joupi.games.engine.board;

import fr.joupi.games.engine.SimpleGame;
import fr.joupi.games.engine.player.BaseGamePlayer;
import fr.joupi.games.engine.utils.scoreboard.PersonalScoreboard;

public abstract class GameBoard<M extends SimpleGame<G, ?>, G extends BaseGamePlayer> extends PersonalScoreboard {

    protected final M game;
    protected final G gamePlayer;

    public GameBoard(M game, G gamePlayer) {
        super(gamePlayer.getPlayer());
        this.game = game;
        this.gamePlayer = gamePlayer;
    }

}