package fr.joupi.games.engine.event.game;

import fr.joupi.games.api.pool.GamePool;
import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.event.GameEvent;
import lombok.Getter;

@Getter
public class GameAddedEvent<M extends MiniGame> extends GameEvent<M> {

    private final GamePool<M> gamePool;

    public GameAddedEvent(M game, GamePool<M> gamePool) {
        super(game);
        this.gamePool = gamePool;
    }

}