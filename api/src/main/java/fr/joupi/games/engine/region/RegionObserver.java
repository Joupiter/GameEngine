package fr.joupi.games.engine.region;

import fr.joupi.games.engine.player.BaseGamePlayer;

public interface RegionObserver<G extends BaseGamePlayer> {

    void onEnter(G gamePlayer);

    void onExit(G gamePlayer);

}