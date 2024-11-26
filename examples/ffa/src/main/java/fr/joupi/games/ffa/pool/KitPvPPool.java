package fr.joupi.games.ffa.pool;

import fr.joupi.games.api.DirectConnectStrategy;
import fr.joupi.games.api.pool.GamePool;
import fr.joupi.games.ffa.KitPvPGame;

import java.util.function.Supplier;

public class KitPvPPool extends GamePool<KitPvPGame> {

    public KitPvPPool() {
        super(KitPvPGame.class, "KitPvP Pool", 1, 1, DirectConnectStrategy.FILL_GAME);
    }

    @Override
    public Supplier<KitPvPGame> newGame() {
        return KitPvPGame::new;
    }

}