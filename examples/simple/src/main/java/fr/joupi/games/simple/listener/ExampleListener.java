package fr.joupi.games.simple.listener;

import fr.joupi.games.api.pool.GameManager;
import fr.joupi.games.simple.MySimpleGame;
import fr.joupi.games.simple.player.MySimplePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public record ExampleListener(GameManager<MySimpleGame> gameManager) implements Listener {

    // JUST AN LITTLE EXAMPLE OF HOW TO USE
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        MySimpleGame game = gameManager.getNullableGame(player);

        if (game == null) return;

        MySimplePlayer gamePlayer = game.getNullablePlayer(player);

        if (gamePlayer == null) return;

        gamePlayer.sendMessage("You speak in chat!");
    }

}