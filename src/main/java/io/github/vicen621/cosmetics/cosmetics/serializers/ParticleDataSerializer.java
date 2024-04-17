package io.github.vicen621.cosmetics.cosmetics.serializers;

import com.google.gson.*;
import io.github.vicen621.cosmetics.cosmetics.effects.ParticleData;
import org.bukkit.Color;
import org.bukkit.Particle;

import java.lang.reflect.Type;

public class ParticleDataSerializer implements JsonSerializer<ParticleData>, JsonDeserializer<ParticleData> {
    @Override
    public ParticleData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonParticleData = json.getAsJsonObject();
        Particle particle = Particle.valueOf(jsonParticleData.get("particle").getAsString().toUpperCase());
        String hexColor = jsonParticleData.get("color").getAsString();
        Color color = Color.fromRGB(java.awt.Color.decode(hexColor).getRGB());
        return new ParticleData(particle, color);
    }

    @Override
    public JsonElement serialize(ParticleData src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonParticleData = new JsonObject();
        jsonParticleData.add("particle", new JsonPrimitive(src.getParticle().name()));
        if (src.getColor() != null)
            jsonParticleData.add("color", new JsonPrimitive(String.format("#%06X", (0xFFFFFF & src.getColor().asRGB()))));
        return null;
    }
}
