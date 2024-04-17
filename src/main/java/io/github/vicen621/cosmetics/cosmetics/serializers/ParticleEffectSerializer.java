package io.github.vicen621.cosmetics.cosmetics.serializers;

import com.google.gson.*;
import io.github.vicen621.cosmetics.cosmetics.effects.BasicParticleEffect;
import io.github.vicen621.cosmetics.cosmetics.effects.HelixParticleEffect;
import io.github.vicen621.cosmetics.cosmetics.effects.ParticleData;
import io.github.vicen621.cosmetics.cosmetics.effects.ParticleEffect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public class ParticleEffectSerializer implements JsonSerializer<ParticleEffect>, JsonDeserializer<ParticleEffect> {

    @Override
    public ParticleEffect deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonParticleEffect = json.getAsJsonObject();
        int repeatDelay = jsonParticleEffect.get("repeatDelay").getAsInt();
        Gson gson = new GsonBuilder().registerTypeAdapter(ParticleData.class, new ParticleDataSerializer()).create();
        ParticleData particleData = gson.fromJson(jsonParticleEffect.get("particleData"), ParticleData.class);
        ParticleEffectType type = ParticleEffectType.valueOf(jsonParticleEffect.get("type").getAsString().toUpperCase());
        try {
            return type.getInstance(particleData, repeatDelay);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonElement serialize(ParticleEffect src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonParticleEffect = new JsonObject();
        jsonParticleEffect.add("type", new JsonPrimitive(src.getClass().getSuperclass().getSimpleName().replace("ParticleEffect", "").toUpperCase()));
        jsonParticleEffect.add("repeatDelay", new JsonPrimitive(src.getRepeatDelay()));

        Gson gson = new GsonBuilder().registerTypeAdapter(ParticleData.class, new ParticleDataSerializer()).setPrettyPrinting().create();
        jsonParticleEffect.add("particleData", gson.toJsonTree(src.getParticleData()));
        return jsonParticleEffect;
    }

    /**
     * Represents a particle effect type
     */
    enum ParticleEffectType {
        BASIC(BasicParticleEffect.class),
        HELIX(HelixParticleEffect.class);

        private final Class<? extends ParticleEffect> clazz;
        ParticleEffectType(Class<? extends ParticleEffect> clazz) {
            this.clazz = clazz;
        }

        /**
         * Gets an instance of the particle effect
         * @param effect the particle data of the effect
         * @param repeatDelay the repeat delay
         * @return the particle effect instance
         * @throws NoSuchMethodException if the constructor does not exist
         * @throws InvocationTargetException if the constructor throws an exception
         * @throws InstantiationException if the class cannot be instantiated
         * @throws IllegalAccessException if the class cannot be accessed
         */
        public ParticleEffect getInstance(ParticleData effect, int repeatDelay) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            return clazz.getDeclaredConstructor(ParticleData.class, int.class).newInstance(effect, repeatDelay);
        }
    }
}
