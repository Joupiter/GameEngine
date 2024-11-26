package fr.joupi.games.engine.event;

import fr.joupi.games.engine.MiniGame;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@AllArgsConstructor
public abstract class GameEvent<M extends MiniGame> extends Event {

    private static final HandlerList handlers = new HandlerList();

    protected final M game;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}