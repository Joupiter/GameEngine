package fr.joupi.games.engine.map;

import fr.joupi.games.engine.point.SinglePoint;
import org.bukkit.command.CommandSender;

public interface Map {

    String getName();
    String getTemplateName();

    SinglePoint getSpawn();

    void setSpawn(SinglePoint singlePoint);

    void setSpawn(double x, double y, double z);
    void setSpawn(double x, double y, double z, float yaw, float pitch);

    void sendDebug(CommandSender sender);

}