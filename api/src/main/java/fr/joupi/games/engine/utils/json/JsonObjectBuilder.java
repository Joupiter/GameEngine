package fr.joupi.games.engine.utils.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonObjectBuilder {

    private final JsonObject jsonObject;

    public JsonObjectBuilder() {
        this.jsonObject = new JsonObject();
    }

    public JsonObjectBuilder(JsonElement jsonElement) {
        this.jsonObject = jsonElement.getAsJsonObject();
    }

    public JsonObjectBuilder add(String key, String value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    public JsonObjectBuilder add(String key, boolean value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    public JsonObjectBuilder add(String key, char value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    public JsonObjectBuilder add(String key, Number value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    public JsonObjectBuilder add(String key, JsonElement value) {
        jsonObject.add(key, value);
        return this;
    }

    public JsonObjectBuilder remove(String key) {
        jsonObject.remove(key);
        return this;
    }

    public JsonObject build() {
        return jsonObject;
    }

}