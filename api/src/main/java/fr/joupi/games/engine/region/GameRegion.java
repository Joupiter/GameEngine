package fr.joupi.games.engine.region;

import com.avaje.ebean.validation.NotNull;
import fr.joupi.games.engine.MiniGame;
import fr.joupi.games.engine.map.Map;
import fr.joupi.games.engine.map.slime.SlimeMap;
import fr.joupi.games.engine.player.BaseGamePlayer;
import fr.joupi.games.engine.point.SinglePoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Iterator;

@Getter
@Setter
@AllArgsConstructor
public abstract class GameRegion<M extends MiniGame> implements Region {

    protected M game;

    protected String name;

    protected World world;
    protected double minX, minY, minZ;
    protected double maxX, maxY, maxZ;

    public GameRegion(M game, String name, World world, Location minLocation, Location maxLocation) {
        this(game, name, world,
                Math.min(minLocation.getBlockX(), maxLocation.getBlockX()),
                Math.min(minLocation.getBlockY(), maxLocation.getBlockY()),
                Math.min(minLocation.getBlockZ(), maxLocation.getBlockZ()),
                Math.max(minLocation.getBlockX(), maxLocation.getBlockX()),
                Math.max(minLocation.getBlockY(), maxLocation.getBlockY()),
                Math.max(minLocation.getBlockZ(), maxLocation.getBlockZ()));
    }

    public GameRegion(M game, String name, Location minLocation, Location maxLocation) {
        this(game, name, minLocation.getWorld(), minLocation, maxLocation);
    }

    public GameRegion(M game, String name, World world, SinglePoint maxPoint, SinglePoint minPoint) {
        this(game, name, world, maxPoint.getLocation(), minPoint.getLocation());
    }

    public GameRegion(M game, String name, SinglePoint maxPoint, SinglePoint minPoint) {
        this(game, name, maxPoint.getLocation().getWorld(), maxPoint.getLocation(), minPoint.getLocation());
    }

    @Override
    public Location getMinLocation() {
        return new Location(world, minX, minY, minZ);
    }

    @Override
    public Location getMaxLocation() {
        return new Location(world, maxX, maxY, maxZ);
    }

    @Override
    public Location getCenter() {
        return new Location(world,
                (getMinLocation().getBlockX() + getMaxLocation().getBlockX()) / 2.0,
                (getMinLocation().getBlockY() + getMaxLocation().getBlockY()) / 2.0,
                (getMinLocation().getBlockZ() + getMaxLocation().getBlockZ()) / 2.0);
    }

    @Override
    public GameRegion<M> setWorld(World world) {
        this.world = world;
        return this;
    }

    @Override
    public GameRegion<M> setWorld(Map map) {
        this.world = Bukkit.getWorld(map.getName());
        return this;
    }

    @Override
    public GameRegion<M> setWorld(SlimeMap slimeMap) {
        this.world = slimeMap.getWorld();
        return this;
    }

    @Override
    public GameRegion<M> setMinLocation(Location location) {
        this.minX = Math.min(location.getBlockX(), maxX);
        this.minY = Math.min(location.getBlockY(), maxX);
        this.minZ = Math.min(location.getBlockZ(), maxX);
        return this;
    }

    @Override
    public GameRegion<M> setMaxLocation(Location location) {
        this.maxX = Math.max(minX, location.getBlockX());
        this.maxY = Math.max(minY, location.getBlockY());
        this.maxZ = Math.max(minZ, location.getBlockZ());
        return this;
    }

    @Override
    public GameRegion<M> copy(Region region) {
        this.name = region.getName();
        this.world = region.getWorld();
        this.minX = region.getMinX();
        this.minY = region.getMinY();
        this.minZ = region.getMinZ();
        this.maxX = region.getMaxX();
        this.maxY = region.getMaxY();
        this.maxZ = region.getMaxZ();
        return this;
    }

    @Override
    public int getWidth() {
        return Math.abs(getMaxLocation().getBlockX() - getMinLocation().getBlockX()) + 1;
    }

    @Override
    public int getHeight() {
        return Math.abs(getMaxLocation().getBlockY() - getMinLocation().getBlockY()) + 1;
    }

    @Override
    public int getLength() {
        return Math.abs(getMaxLocation().getBlockZ() - getMinLocation().getBlockZ()) + 1;
    }

    @Override
    public int getVolume() {
        return getWidth() * getHeight() * getLength();
    }

    @Override
    public boolean contains(Location location) {
        return location.getWorld().equals(world) && contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public boolean contains(BaseGamePlayer gamePlayer) {
        return contains(gamePlayer.getPlayer().getLocation());
    }

    @Override
    public boolean contains(int x, int y, int z) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }

    @Override
    public @NotNull Iterator<Block> iterator() {
        return new RegionIterator(this);
    }

}