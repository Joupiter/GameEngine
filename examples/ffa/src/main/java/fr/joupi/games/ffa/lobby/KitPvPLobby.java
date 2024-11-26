package fr.joupi.games.ffa.lobby;

import fr.joupi.games.engine.ffa.lobby.FFAGameLobby;
import fr.joupi.games.ffa.KitPvPGame;
import fr.joupi.games.ffa.player.KitPvPPlayer;

public class KitPvPLobby extends FFAGameLobby<KitPvPGame, KitPvPPlayer, KitPvPLobbyItems> {

    public KitPvPLobby() {
        super(KitPvPLobbyItems.class);
    }

    // TRIGGER WHEN THE PLAYER JOIN THE SERVER
    @Override
    protected void processJoin(KitPvPGame game, KitPvPPlayer gamePlayer) {
        // ITEMS ALREADY GIVENS AND PLAYER ARE CLEARED

    }

    // TRIGGER WHEN THE PLAYER WAS RESEND TO THE LOBBY
    @Override
    protected void processSetup(KitPvPGame game, KitPvPPlayer gamePlayer) {
        // ITEMS ALREADY GIVENS AND PLAYER ARE CLEARED

    }

    // TRIGGER WHEN THE PLAYER WANT TO ENTER THE ARENA (exit the lobby)
    @Override
    protected void processPlay(KitPvPGame game, KitPvPPlayer gamePlayer) {

    }

}