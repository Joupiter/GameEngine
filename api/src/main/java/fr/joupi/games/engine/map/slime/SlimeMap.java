package fr.joupi.games.engine.map.slime;

import fr.joupi.games.engine.map.Map;
import org.bukkit.World;

public interface SlimeMap extends Map {

    World getWorld();

    SlimeMap setWorld(World world);

}