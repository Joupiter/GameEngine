package fr.joupi.games.engine.map.slime;

import fr.joupi.games.engine.point.SinglePoint;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

@Getter
@Setter
public class SlimeGameMap implements SlimeMap {

    private final String name;
    private final String templateName;

    @Accessors(chain = true)
    private World world;

    private SinglePoint spawn;

    public SlimeGameMap(String name, String templateName) {
        this.name = name;
        this.templateName = templateName;
    }

    @Override
    public void setSpawn(double x, double y, double z, float yaw, float pitch) {
        this.spawn = new SinglePoint(new Location(world, x, y, z, yaw, pitch));
    }

    @Override
    public void setSpawn(double x, double y, double z) {
        this.spawn = new SinglePoint(new Location(world, x, y, z, 0f, 0f));
    }

    @Override
    public void sendDebug(CommandSender sender) {
        sender.sendMessage("Map name: " + name);
        sender.sendMessage("Template name: " + templateName);
    }

}