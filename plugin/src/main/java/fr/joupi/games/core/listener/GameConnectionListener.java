package fr.joupi.games.core.listener;

import fr.joupi.games.api.pool.GamePoolManager;
import fr.joupi.games.engine.utils.CacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@Slf4j
public record GameConnectionListener(GamePoolManager gamePoolManager) implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        var player = event.getPlayer();

        CacheUtils.cooldowns
                .values()
                .forEach(cooldown -> cooldown.invalidate(player.getUniqueId()));

        gamePoolManager.leaveGame(player);
    }

}