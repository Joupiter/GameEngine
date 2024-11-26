package fr.joupi.games.engine.event.player;

import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.event.GamePlayerEvent;
import fr.joupi.games.engine.player.BaseGamePlayer;

public class GamePlayerSpectateEvent<M extends MiniGame, G extends BaseGamePlayer> extends GamePlayerEvent<M, G> {

    public GamePlayerSpectateEvent(M game, G gamePlayer) {
        super(game, gamePlayer);
    }

}