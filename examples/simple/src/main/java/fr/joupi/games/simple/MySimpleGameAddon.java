package fr.joupi.games.simple;

import fr.joupi.games.api.addon.GameAddon;
import fr.joupi.games.simple.listener.PlayerConnectionListener;
import fr.joupi.games.simple.listener.ExampleListener;
import fr.joupi.games.simple.pool.MySimplePool;

public class MySimpleGameAddon extends GameAddon<MySimplePool> {

    public MySimpleGameAddon() {
        super(new MySimplePool());
    }

    @Override
    public void enable() {
        pool.loadDefaultGames();

        registerListener(
                // Register your listeners here
                new PlayerConnectionListener(),
                new ExampleListener(pool.getGamesManager())
        );
    }

    @Override
    public void disable() {
        // GAMES ARE AUTOMATICALLY STOPPED
    }

}