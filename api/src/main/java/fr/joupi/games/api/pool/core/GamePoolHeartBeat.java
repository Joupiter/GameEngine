package fr.joupi.games.api.pool.core;

import fr.joupi.games.api.pool.GamePool;
import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.utils.concurrent.VirtualThreading;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class GamePoolHeartBeat<M extends MiniGame> implements Runnable {

    private final List<M> games;

    public GamePoolHeartBeat(GamePool<M> gamePool) {
        this.games = gamePool.getGamesManager().getGames();
        VirtualThreading.scheduledPool.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void run() {

    }

    public void forceUpdate() {

    }

    public void shutdown() {

    }

}
