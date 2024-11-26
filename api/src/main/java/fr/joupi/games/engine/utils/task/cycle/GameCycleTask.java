package fr.joupi.games.engine.utils.task.cycle;

import fr.joupi.games.engine.MiniGame;
import lombok.Getter;

@Getter
public abstract class GameCycleTask <M extends MiniGame> extends CycleTask {

    protected final M game;

    public GameCycleTask(int duration, boolean virtual, M game) {
        super(duration, virtual);
        this.game = game;
    }

    public GameCycleTask(int duration, M game) {
        this(duration, true, game);
    }

}