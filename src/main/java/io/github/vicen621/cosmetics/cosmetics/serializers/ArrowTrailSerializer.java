package io.github.vicen621.cosmetics.cosmetics.serializers;

import com.google.gson.*;
import io.github.vicen621.cosmetics.cosmetics.arrowtrails.ArrowTrail;

import java.lang.reflect.Type;

public class ArrowTrailSerializer implements JsonSerializer<ArrowTrail>, JsonDeserializer<ArrowTrail> {
    @Override
    public ArrowTrail deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        /*JsonObject object = json.getAsJsonObject();
        int id = object.get("id").getAsInt();
        String name = object.get("name").getAsString();
        String permission = object.get("permission").getAsString();
        int cost = object.get("cost").getAsInt();

        JsonArray jsonParticles = object.getAsJsonArray("particles");
        Particle[] particles = new Particle[jsonParticles.size()];
        for (int i = 0; i < particles.length; i++)
            particles[i] = Particle.valueOf(jsonParticles.get(i).getAsString());*/

        return null;
    }

    @Override
    public JsonElement serialize(ArrowTrail src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();

        object.add("id", new JsonPrimitive(src.getId()));
        object.add("name", new JsonPrimitive(src.getName()));
        object.add("permission", new JsonPrimitive(src.getPermission()));
        object.add("cost", new JsonPrimitive(src.getCost()));
        /*object.add("particles", particles);*/
        return object;
    }
}
