package fr.joupi.games.engine.event;

import org.bukkit.event.EventPriority;

public record EventSettings(EventPriority priority, boolean ignoreCancelled) {
}