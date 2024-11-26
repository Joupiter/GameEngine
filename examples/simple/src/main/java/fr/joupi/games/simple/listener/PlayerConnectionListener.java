package fr.joupi.games.simple.listener;

import fr.joupi.games.engine.event.player.GamePlayerJoinEvent;
import fr.joupi.games.simple.MySimpleGame;
import fr.joupi.games.simple.player.MySimplePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onJoin(GamePlayerJoinEvent<MySimpleGame, MySimplePlayer> event) {
        MySimpleGame game = event.getGame();
        Player player = event.getPlayer();
        MySimplePlayer gamePlayer = event.getGamePlayer();

        switch (game.getState()) {
            case WAIT, STARTING -> game.getWaitingRoom().processJoin(gamePlayer);
            case IN_GAME, END -> System.out.println("do something");
        }
    }

    @EventHandler
    public void onLeave(GamePlayerJoinEvent<MySimpleGame, MySimplePlayer> event) {
        MySimpleGame game = event.getGame();
        Player player = event.getPlayer();
        MySimplePlayer gamePlayer = event.getGamePlayer();

        switch (game.getState()) {
            case WAIT, STARTING -> game.getWaitingRoom().processLeave(gamePlayer);
            case IN_GAME, END -> System.out.println("do something");
        }
    }

}