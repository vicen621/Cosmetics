package io.github.vicen621.cosmetics.cosmetics.serializers;

import com.google.gson.*;
import io.github.vicen621.cosmetics.cosmetics.effects.ParticleEffect;

import java.lang.reflect.Type;

public class ParticleEffectSerializer implements JsonSerializer<ParticleEffect> {

    @Override
    public JsonElement serialize(ParticleEffect src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("repeatDelay", new JsonPrimitive(src.getRepeatDelay()));
        object.add("particleData", context.serialize(src.getParticleData()));

        return null;
    }
}
