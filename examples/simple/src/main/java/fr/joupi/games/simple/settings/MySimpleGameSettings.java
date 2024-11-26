package fr.joupi.games.simple.settings;

import fr.joupi.games.engine.GameSettings;
import fr.joupi.games.engine.GameSize;
import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.board.GameBoard;
import fr.joupi.games.engine.player.BaseGamePlayer;

public class MySimpleGameSettings extends GameSettings<MySimpleGameMapManager> {

    public MySimpleGameSettings(GameSize gameSize) {
        super(gameSize);
    }

    @Override
    public GameBoard<?, ?> defaultBoard(MiniGame game, BaseGamePlayer gamePlayer) {
        return super.defaultBoard(game, gamePlayer);
    }

}