package fr.joupi.games.engine.utils.json.adapter;

import com.google.gson.*;
import fr.joupi.games.engine.point.SinglePoint;
import fr.joupi.games.engine.utils.json.JsonObjectBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class SpawnPointAdapter implements JsonSerializer<SinglePoint>, JsonDeserializer<SinglePoint> {

    @Override
    public JsonElement serialize(SinglePoint point, Type type, JsonSerializationContext context) {
        Location location = point.getLocation();

        return new JsonObjectBuilder()
                .add("name", point.getName())
                .add("world", location.getWorld().getName())
                .add("x", location.getX())
                .add("y", location.getY())
                .add("z", location.getZ())
                .add("yaw", location.getYaw())
                .add("pitch", location.getPitch())
                .build();
    }

    @Override
    public SinglePoint deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Location location = new Location(Bukkit.getWorld(jsonObject.get("world").getAsString()), jsonObject.get("x").getAsDouble(), jsonObject.get("y").getAsDouble(), jsonObject.get("z").getAsDouble(), jsonObject.get("yaw").getAsFloat(), jsonObject.get("pitch").getAsFloat());

        return new SinglePoint(
                jsonObject.get("name").getAsString(),
                location
        );
    }

}