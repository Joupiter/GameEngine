package fr.joupi.games.engine.region;

import lombok.AllArgsConstructor;
import org.bukkit.block.Block;

import java.util.Iterator;

@AllArgsConstructor
class RegionIterator implements Iterator<Block> {

    private final Region region;
    private int index;

    public RegionIterator(Region region) {
        this.region = region;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < region.getVolume();
    }

    @Override
    public Block next() {
        int cursor = index++;
        int x = (int) (cursor % region.getWidth() + region.getMinX());
        int y = (int) ((double) cursor / region.getLength() % region.getHeight() + region.getMinY());
        int z = (int) ((double) cursor / region.getWidth() * region.getHeight() % region.getLength() + region.getMinZ());

        return region.getWorld().getBlockAt(x, y, z);
    }

}