package fr.joupi.games.engine.utils.location;

import fr.joupi.games.engine.map.Map;
import fr.joupi.games.engine.point.SinglePoint;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public record LocationCoordinates(double x, double y, double z, float yaw, float pitch) {

    public SinglePoint toSinglePoint(String worldName) {
        return new SinglePoint(worldName, x, y, z, yaw, pitch);
    }

    public SinglePoint toSinglePoint(Map map) {
        return toSinglePoint(map.getName());
    }

    public Location toLocation(String worldName) {
        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }

    public Location toLocation(Map map) {
        return toLocation(map.getName());
    }

}