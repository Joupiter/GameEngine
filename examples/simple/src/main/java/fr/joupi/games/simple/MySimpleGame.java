package fr.joupi.games.simple;

import fr.joupi.games.engine.SimpleGame;
import fr.joupi.games.engine.utils.GameSizeTemplate;
import fr.joupi.games.simple.player.MySimplePlayer;
import fr.joupi.games.simple.settings.MySimpleGameSettings;
import fr.joupi.games.simple.waiting.MySimpleWaitingRoom;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MySimpleGame extends SimpleGame<MySimplePlayer, MySimpleGameSettings> {

    private final MySimpleWaitingRoom waitingRoom;

    public MySimpleGame(String name) {
        super(name, new MySimpleGameSettings(GameSizeTemplate.SIZE_1V1.toGameSize()));
        this.waitingRoom = new MySimpleWaitingRoom(this);
    }

    @Override
    public MySimplePlayer defaultGamePlayer(UUID uuid, boolean spectator) {
        return new MySimplePlayer(uuid, 0, 0, spectator);
    }

    @Override
    public void load() {
        settings.getGameMapManager().setup();
    }

}