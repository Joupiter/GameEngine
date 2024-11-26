package fr.joupi.games.ffa.settings;

import fr.joupi.games.engine.GameState;
import fr.joupi.games.engine.map.GameMapManager;
import fr.joupi.games.engine.map.slime.SlimeGameMap;
import fr.joupi.games.engine.map.slime.SlimeMap;
import fr.joupi.games.engine.map.slime.SlimeWorldLoader;
import fr.joupi.games.ffa.KitPvPGame;
import lombok.Getter;

@Getter
public class KitPvPMapManager extends GameMapManager<KitPvPGame, SlimeMap, SlimeWorldLoader> {

    private final SlimeMap waitingMap;

    public KitPvPMapManager(KitPvPGame game) {
        super(game, new SlimeWorldLoader());
        // getFormattedTypeMapName gonna add the name and the id of the game before the name you chosen like 'mygame-A0Fljf-waiting'
        this.waitingMap = new SlimeGameMap(getFormattedTypeMapName("waiting"), "waitingroom-template");
    }

    @Override
    public void setup() {
        addMap(waitingMap);

        mapLoader.load(waitingMap).whenComplete((slimeMap, throwable) -> {
            slimeMap.setSpawn(0, 100, 0, 180f, 0f);

            // SET THE SPAWN OF THE LOBBY
            game.getLobby().setSpawnPoint(slimeMap.getSpawn());
        });

        // VERY IMPORTANT U NEED TO SET STATE TO ALWAYS PLAYING OR WAIT OR THE GAME IS CONSIDERING NOT COMPLETELY LOADED
        game.setState(GameState.ALWAYS_PLAYING);
    }

    @Override
    public void end() {
        // Delete the map when the game is finish
        delete(waitingMap);
    }

}