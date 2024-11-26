package fr.joupi.games.engine.utils.json.adapter;

import com.google.gson.*;
import fr.joupi.games.engine.utils.json.JsonObjectBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Type;

public class LocationAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {

    @Override
    public JsonElement serialize(Location location, Type type, JsonSerializationContext context) {
        return new JsonObjectBuilder()
                .add("world", location.getWorld().getName())
                .add("x", location.getX())
                .add("y", location.getY())
                .add("z", location.getZ())
                .add("yaw", location.getYaw())
                .add("pitch", location.getPitch())
                .build();
    }

    @Override
    public Location deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return new Location(
                Bukkit.getWorld(jsonObject.get("world").getAsString()),
                jsonObject.get("x").getAsDouble(),
                jsonObject.get("y").getAsDouble(),
                jsonObject.get("z").getAsDouble(),
                jsonObject.get("yaw").getAsFloat(),
                jsonObject.get("pitch").getAsFloat()
        );
    }

}