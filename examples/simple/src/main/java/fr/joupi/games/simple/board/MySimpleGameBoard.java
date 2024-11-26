package fr.joupi.games.simple.board;

import fr.joupi.games.engine.board.GameBoard;
import fr.joupi.games.simple.MySimpleGame;
import fr.joupi.games.simple.player.MySimplePlayer;

public class MySimpleGameBoard extends GameBoard<MySimpleGame, MySimplePlayer> {

    public MySimpleGameBoard(MySimpleGame game, MySimplePlayer gamePlayer) {
        super(game, gamePlayer);
    }

    @Override
    public void reloadData() {

    }

    @Override
    public void setLines(String ip) {
        objectiveSign.clearScores();
        objectiveSign.setDisplayName("Exemple");

        switch (game.getState()) {
            case WAIT -> {
                objectiveSign.setLine(0, "§a");
                objectiveSign.setLine(1, "§eWaiting for players...");
                objectiveSign.setLine(2, "§b");
            }
            case STARTING -> {
                objectiveSign.setLine(0, "§a");
                objectiveSign.setLine(1, "§eStarting!");
                objectiveSign.setLine(2, "§b");
            }
            case IN_GAME -> {
                objectiveSign.setLine(0, "§a");
                objectiveSign.setLine(1, "§eIN GAME !");
                objectiveSign.setLine(2, "§b");
            }
            case END -> {
                objectiveSign.setLine(0, "§a");
                objectiveSign.setLine(1, "§eIS THE END!");
                objectiveSign.setLine(2, "§b");
            }
        }

        objectiveSign.updateLines();
    }

}