package fr.joupi.games.api.processor;

import fr.joupi.games.api.pool.GamePool;
import fr.joupi.games.engine.MiniGame;

public interface GamePoolProcessor<M extends MiniGame> {

    GamePool<M> getGamePool();

    void process(M game);

}