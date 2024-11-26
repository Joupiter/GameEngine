package fr.joupi.games.engine.point;

import fr.joupi.games.engine.map.Map;
import fr.joupi.games.engine.player.BaseGamePlayer;
import fr.joupi.games.engine.utils.concurrent.BukkitThreading;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class SinglePoint implements SpawnPoint {

    private final String name;
    private Location location;

    public SinglePoint(Location location) {
        this(RandomStringUtils.randomAlphanumeric(8), location);
    }

    public SinglePoint(String worldName, double x, double y, double z, float yaw, float pitch) {
        this(new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch));
    }

    public SinglePoint(String worldName, double x, double y, double z) {
        this(worldName, x, y, z, 0f, 0f);
    }

    public SinglePoint(Map map, double x, double y, double z, float yaw, float pitch) {
        this(map.getName(), x, y, z, yaw, pitch);
    }

    public SinglePoint(Map map, double x, double y, double z) {
        this(map, x, y, z, 0f, 0f);
    }

    public SinglePoint setWorld(String world) {
        location.setWorld(Bukkit.getWorld(world));
        return this;
    }

    public SinglePoint setWorld(Map map) {
        return setWorld(map.getName());
    }

    public void teleport(Player player) {
        player.teleport(location);
    }

    public void teleport(BaseGamePlayer gamePlayer) {
        gamePlayer.teleport(location);
    }

    public <G extends BaseGamePlayer> void teleport(Collection<G> collection) {
        collection.forEach(this::teleport);
    }

    public void syncTeleport(Player player) {
        BukkitThreading.runTask(() -> player.teleport(location));
    }

    public void syncTeleport(BaseGamePlayer gamePlayer) {
        BukkitThreading.runTask(() -> gamePlayer.teleport(location));
    }

    public <G extends BaseGamePlayer> void syncTeleport(Collection<G> collection) {
        BukkitThreading.runTask(() -> collection.forEach(this::teleport));
    }

    @Override
    public String getDebugMessage() {
        return name + ": " + location.toString();
    }

}