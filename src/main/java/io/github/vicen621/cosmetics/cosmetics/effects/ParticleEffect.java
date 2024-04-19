package io.github.vicen621.cosmetics.cosmetics.effects;

import com.google.gson.annotations.JsonAdapter;
import io.github.vicen621.cosmetics.cosmetics.serializers.ParticleDataSerializer;
import org.bukkit.entity.Entity;

import java.util.Objects;

/**
 * Represents a particle effect.
 */
public abstract class ParticleEffect {
    @JsonAdapter(ParticleDataSerializer.class)
    private final ParticleData particleData;
    private final int repeatDelay;

    protected ParticleEffect(ParticleData particleData, int repeatDelay) {
        this.particleData = particleData;
        this.repeatDelay = repeatDelay;
    }

    /**
     * Displays the particle effect at the specified entity
     * @param entity the entity
     */
    public abstract void display(Entity entity);

    /**
     * Gets the particle effect
     * @return the particle effect
     */
    public ParticleData getParticleData() {
        return particleData;
    }

    /**
     * Gets the repeat delay of the particle effect
     * @return the repeat delay
     */
    public int getRepeatDelay() {
        return repeatDelay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticleEffect that)) return false;
        return getRepeatDelay() == that.getRepeatDelay() && Objects.equals(getParticleData(), that.getParticleData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParticleData(), getRepeatDelay());
    }
}
