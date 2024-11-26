package fr.joupi.games.simple.waiting;

import fr.joupi.games.engine.waitingroom.GameWaitingRoom;
import fr.joupi.games.simple.MySimpleGame;
import fr.joupi.games.simple.player.MySimplePlayer;
import fr.joupi.games.simple.tasks.StartingTask;

public class MySimpleWaitingRoom extends GameWaitingRoom<MySimpleGame, MySimplePlayer, MySimpleWaitingItems> {

    public MySimpleWaitingRoom(MySimpleGame game) {
        super(game, MySimpleWaitingItems.class);
        this.countdownTask = new StartingTask(10, game);
    }

    @Override
    public void onJoin(MySimplePlayer gamePlayer) {
        // ITEMS ALREADY GIVENS AND PLAYER ARE CLEARED
        // THE START TASK IS AUTOMATICALLY STARTED IF ENOUGH PLAYER
    }

    @Override
    public void onLeave(MySimplePlayer gamePlayer) {
        // THE START TASK IS AUTOMATICALLY SHUTDOWN IF NOT ENOUGH PLAYER
    }

}