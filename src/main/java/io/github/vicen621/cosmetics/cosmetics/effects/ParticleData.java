package io.github.vicen621.cosmetics.cosmetics.effects;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Color;
import org.bukkit.Particle;

import java.util.Objects;

public class ParticleData {
    private final Particle particle;
    private final Color color;

    public ParticleData(Particle particle) {
        this(particle, null);
    }

    public ParticleData(Particle particle, Color color) {
        this.particle = particle;
        this.color = color;
    }

    public ParticleBuilder createParticleBuilder() {
        ParticleBuilder particleBuilder = new ParticleBuilder(particle);
        if (color != null)
            particleBuilder.color(color);

        return particleBuilder;
    }

    public Particle getParticle() {
        return particle;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticleData that)) return false;
        return getParticle() == that.getParticle() && Objects.equals(getColor(), that.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParticle(), getColor());
    }
}
