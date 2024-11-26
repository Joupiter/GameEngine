package fr.joupi.games.simple.settings;

import fr.joupi.games.engine.GameState;
import fr.joupi.games.engine.map.GameMapManager;
import fr.joupi.games.engine.map.slime.SlimeMap;
import fr.joupi.games.engine.map.slime.SlimeWorldLoader;
import fr.joupi.games.simple.MySimpleGame;

public class MySimpleGameMapManager extends GameMapManager<MySimpleGame, SlimeMap, SlimeWorldLoader> {

    public MySimpleGameMapManager(MySimpleGame game) {
        super(game, new SlimeWorldLoader());
    }

    @Override
    public void setup() {
        game.setState(GameState.WAIT);
    }

    @Override
    public void end() {

    }

}