package fr.joupi.games.engine.region;

import com.avaje.ebean.validation.NotNull;
import fr.joupi.games.engine.map.Map;
import fr.joupi.games.engine.map.slime.SlimeMap;
import fr.joupi.games.engine.player.BaseGamePlayer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public interface Region extends Iterable<Block> {

    String getName();

    Location getMinLocation();
    Location getMaxLocation();
    Location getCenter();

    World getWorld();

    double getMinX();
    double getMinY();
    double getMinZ();

    double getMaxX();
    double getMaxY();
    double getMaxZ();

    int getWidth();
    int getHeight();
    int getLength();

    int getVolume();

    boolean contains(Location location);
    boolean contains(BaseGamePlayer gamePlayer);
    boolean contains(int x, int y, int z);

    Region setWorld(World world);

    Region setWorld(Map map);
    Region setWorld(SlimeMap map);

    Region setMinLocation(Location location);
    Region setMaxLocation(Location location);

    Region copy(Region region);

    @NotNull
    @Override
    Iterator<Block> iterator();

    @Override
    default Spliterator<Block> spliterator() {
        return Iterable.super.spliterator();
    }

    @Override
    default void forEach(Consumer<? super Block> action) {
        Iterable.super.forEach(action);
    }

}