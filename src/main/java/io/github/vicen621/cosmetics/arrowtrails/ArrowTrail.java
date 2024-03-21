package io.github.vicen621.cosmetics.arrowtrails;

import io.github.vicen621.cosmetics.Cosmetic;
import io.github.vicen621.cosmetics.Main;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;

/**
 * Represents an arrow trail.
 */
public final class ArrowTrail extends Cosmetic<Arrow> {

    private final Particle[] particles;

    public ArrowTrail(int id, String name, String permission, int cost, Particle... particles) {
        super(id, name, permission, cost);
        this.particles = particles;
    }

    @Override
    public void apply(Arrow arrow) {
        arrow.setMetadata("trail", new FixedMetadataValue(Main.getInstance(), getId()));
    }

    @Override
    public boolean hasCosmetic(Arrow arrow) {
        return arrow.hasMetadata("trail") && arrow.getMetadata("trail").get(0).asInt() == getId();
    }

    /**
     * Gets the particles of the arrow trail.
     * @return the particles
     */
    public Particle[] getParticles() {
        return particles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ArrowTrail that = (ArrowTrail) o;
        return Arrays.equals(particles, that.particles);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(particles);
        return result;
    }
}
