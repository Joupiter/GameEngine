package fr.joupi.games.simple.tasks;

import fr.joupi.games.engine.GameState;
import fr.joupi.games.engine.utils.task.countdown.CountdownTask;
import fr.joupi.games.engine.utils.task.countdown.GameCountdownTask;
import fr.joupi.games.simple.MySimpleGame;

public class StartingTask extends GameCountdownTask<MySimpleGame> {

    // DURATION IN SECOND
    public StartingTask(int duration, MySimpleGame game) {
        super(duration, game);
    }

    @Override
    public void onStart() {
        game.broadcast("THE GAME IS ABOUT TO START");
    }

    @Override
    public void onNext(CountdownTask task) {
        game.broadcast("STARTING IN " + task.getSecondsLeft().get() + " seconds!");
    }

    @Override
    public void onComplete() {
        game.setState(GameState.IN_GAME);
        game.broadcast("GOOD LUCK PLAYERS!");
    }

    @Override
    public void onCancel() {
        game.broadcast("STARTING CANCELLED!");
    }

}