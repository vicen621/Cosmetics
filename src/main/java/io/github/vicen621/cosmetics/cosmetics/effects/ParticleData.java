package io.github.vicen621.cosmetics.cosmetics.effects;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Color;
import org.bukkit.Particle;

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
}
