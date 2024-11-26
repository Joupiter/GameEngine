package fr.joupi.games.ffa;

import fr.joupi.games.engine.ffa.FFAGame;
import fr.joupi.games.engine.utils.GameSizeTemplate;
import fr.joupi.games.ffa.lobby.KitPvPLobby;
import fr.joupi.games.ffa.player.KitPvPPlayer;
import fr.joupi.games.ffa.settings.KitPvPSettings;

import java.util.UUID;

public class KitPvPGame extends FFAGame<KitPvPPlayer, KitPvPSettings> {

    public KitPvPGame() {
        super("KitPvP", new KitPvPSettings(GameSizeTemplate.FFA.toGameSize()), new KitPvPLobby());
    }

    @Override
    public KitPvPPlayer defaultGamePlayer(UUID uuid, boolean spectator) {
        return new KitPvPPlayer(uuid, 0, 0, 0, 0, spectator);
    }

    @Override
    public void load() {
        settings.getGameMapManager().setup();
    }

}