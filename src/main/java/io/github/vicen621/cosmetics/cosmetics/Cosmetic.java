package io.github.vicen621.cosmetics.cosmetics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.vicen621.cosmetics.cosmetics.effects.BasicParticleEffect;
import io.github.vicen621.cosmetics.cosmetics.effects.HelixParticleEffect;
import io.github.vicen621.cosmetics.cosmetics.effects.ParticleData;
import io.github.vicen621.cosmetics.cosmetics.effects.ParticleEffect;
import io.github.vicen621.cosmetics.cosmetics.serializers.ParticleDataSerializer;
import io.github.vicen621.cosmetics.cosmetics.serializers.RuntimeTypeAdapterFactory;
import org.bukkit.event.Listener;

import java.util.Objects;

/**
 * Represents a cosmetic.
 * @param <T> the type of entity the cosmetic applies to
 */
public abstract class Cosmetic<T> implements Listener {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ParticleData.class, new ParticleDataSerializer())
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(ParticleEffect.class)
                    .registerSubtype(BasicParticleEffect.class, "basic")
                    .registerSubtype(HelixParticleEffect.class, "helix")
            )
            .setPrettyPrinting()
            .create();

    private final int id;
    private final String name;
    private final String permission;
    private final int cost;

    public Cosmetic(int id, String name, String permission, int cost) {
        this.id = id;
        this.name = name;
        this.permission = permission;
        this.cost = cost;
    }

    /**
     * Applies the cosmetic to the entity.
     * @param t the entity
     */
    public abstract void apply(T t);

    /**
     * Checks if the entity has the cosmetic applied.
     * @param t the entity
     * @return true if the entity has the cosmetic applied, false otherwise
     */
    public abstract boolean hasCosmetic(T t);

    public static Gson getGson() {
        return GSON;
    }

    /**
     * Gets the id of the cosmetic.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the cosmetic.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the permission required to use the cosmetic.
     * @return the permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Gets the cost of the cosmetic.
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cosmetic<?> cosmetic = (Cosmetic<?>) o;
        return id == cosmetic.id && cost == cosmetic.cost && Objects.equals(name, cosmetic.name) && Objects.equals(permission, cosmetic.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, permission, cost);
    }
}
