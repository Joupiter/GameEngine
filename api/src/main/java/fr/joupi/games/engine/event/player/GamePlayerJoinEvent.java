package fr.joupi.games.engine.event.player;

import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.event.GamePlayerEvent;
import fr.joupi.games.engine.player.BaseGamePlayer;

public class GamePlayerJoinEvent<M extends MiniGame, G extends BaseGamePlayer> extends GamePlayerEvent<M, G> {

    public GamePlayerJoinEvent(M game, G gamePlayer) {
        super(game, gamePlayer);
    }

    public void sendJoinMessage(String message) {
        game.broadcast(message);
    }

    public void sendJoinMessage(String... messages) {
        game.broadcast(messages);
    }

}