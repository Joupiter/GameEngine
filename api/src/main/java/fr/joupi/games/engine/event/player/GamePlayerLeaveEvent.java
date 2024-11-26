package fr.joupi.games.engine.event.player;

import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.event.GamePlayerEvent;
import fr.joupi.games.engine.player.BaseGamePlayer;

public class GamePlayerLeaveEvent<M extends MiniGame, G extends BaseGamePlayer> extends GamePlayerEvent<M, G> {

    public GamePlayerLeaveEvent(M game, G gamePlayer) {
        super(game, gamePlayer);
    }

    public void sendLeaveMessage(String message) {
        game.broadcast(message);
    }

    public void sendLeaveMessage(String... messages) {
        game.broadcast(messages);
    }

}