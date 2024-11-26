package fr.joupi.games.simple.settings;

import fr.joupi.games.engine.GameState;
import fr.joupi.games.engine.map.GameMapManager;
import fr.joupi.games.engine.map.slime.SlimeGameMap;
import fr.joupi.games.engine.map.slime.SlimeMap;
import fr.joupi.games.engine.map.slime.SlimeWorldLoader;
import fr.joupi.games.simple.MySimpleGame;

public class MySimpleGameMapManager extends GameMapManager<MySimpleGame, SlimeMap, SlimeWorldLoader> {

    private final SlimeMap waiting;

    public MySimpleGameMapManager(MySimpleGame game) {
        super(game, new SlimeWorldLoader());
        // getFormattedTypeMapName gonna add the name and the id of the game before the name you chosen like 'mygame-A0Fljf-waiting'
        this.waiting = new SlimeGameMap(getFormattedTypeMapName("waiting"), "waitingroom-template");
    }

    @Override
    public void setup() {
        addMap(waiting);

        mapLoader.load(waiting).whenComplete((slimeMap, throwable) -> {
            game.getWaitingRoom().setMap(slimeMap); // IMPORTANT
        });

        game.setState(GameState.WAIT);
    }

    @Override
    public void end() {
        delete(waiting);
    }

}