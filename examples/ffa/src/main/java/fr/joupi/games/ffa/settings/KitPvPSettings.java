package fr.joupi.games.ffa.settings;

import fr.joupi.games.engine.GameSettings;
import fr.joupi.games.engine.GameSize;
import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.board.GameBoard;
import fr.joupi.games.engine.player.BaseGamePlayer;
import fr.joupi.games.ffa.KitPvPGame;
import fr.joupi.games.ffa.board.KitPvPBoard;
import fr.joupi.games.ffa.player.KitPvPPlayer;

public class KitPvPSettings extends GameSettings<KitPvPMapManager> {

    public KitPvPSettings(GameSize gameSize) {
        super(gameSize);
    }

    @Override
    public GameBoard<?, ?> defaultBoard(MiniGame game, BaseGamePlayer gamePlayer) {
        return new KitPvPBoard((KitPvPGame) game, (KitPvPPlayer) gamePlayer);
    }

}