package fr.joupi.games.engine.ffa.lobby;

import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.ffa.player.FFAGamePlayer;
import fr.joupi.games.engine.point.SinglePoint;

public interface FFALobby {

    void onJoin(MiniGame game, FFAGamePlayer gamePlayer);

    void onSetup(MiniGame game, FFAGamePlayer gamePlayer);

    void onPlay(MiniGame game, FFAGamePlayer gamePlayer);

    void setSpawnPoint(SinglePoint point);

}