package fr.joupi.games.engine.event;

import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.player.BaseGamePlayer;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
public abstract class GamePlayerEvent<M extends MiniGame, G extends BaseGamePlayer> extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    protected final M game;
    protected final G gamePlayer;

    public GamePlayerEvent(M game, G gamePlayer) {
        super(gamePlayer.getPlayer());
        this.game = game;
        this.gamePlayer = gamePlayer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}