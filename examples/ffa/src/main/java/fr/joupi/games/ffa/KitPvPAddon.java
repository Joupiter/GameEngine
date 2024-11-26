package fr.joupi.games.ffa;

import fr.joupi.games.api.addon.GameAddon;
import fr.joupi.games.ffa.pool.KitPvPPool;

public class KitPvPAddon extends GameAddon<KitPvPPool> {

    public KitPvPAddon() {
        super(new KitPvPPool());
    }

    @Override
    public void enable() {
        pool.loadDefaultGames(); // Create default game

        registerListener(
                // Registers your listeners here
        );
    }

    @Override
    public void disable() {

    }

}