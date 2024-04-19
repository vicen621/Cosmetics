package io.github.vicen621.cosmetics.cosmetics.arrowtrails;

import io.github.vicen621.cosmetics.cosmetics.Cosmetic;
import io.github.vicen621.cosmetics.cosmetics.Updatable;
import io.github.vicen621.cosmetics.cosmetics.effects.ParticleEffect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents an arrow trail.
 */
public final class ArrowTrail extends Cosmetic<Arrow> implements Updatable {
    private final ParticleEffect particleEffect;
    private transient final Set<Arrow> projectiles;

    public ArrowTrail(int id, String name, String permission, int cost, ParticleEffect particleEffect) {
        super(id, name, permission, cost);
        this.particleEffect = particleEffect;
        this.projectiles = new HashSet<>();
    }

    @Override
    public void apply(Arrow arrow) {
        getGson().toJson(this);
        projectiles.add(arrow);
    }

    @Override
    public boolean hasCosmetic(Arrow arrow) {
        return projectiles.contains(arrow);
    }

    @Override
    public void onUpdate() {
        for (Projectile projectile : projectiles)
            getParticleEffect().display(projectile);
    }

    /**
     * Gets the particles of the arrow trail.
     * @return the particles
     */
    public ParticleEffect getParticleEffect() {
        return particleEffect;
    }

    public String toJson() {
        return getGson().toJson(this);
    }

    /**
     * parse an arrow trail from the given file
     * @param file the file
     * @return the arrow trail
     * @throws FileNotFoundException if the file not exists
     */
    public static ArrowTrail load(File file) throws FileNotFoundException {
        return getGson().fromJson(new FileReader(file), ArrowTrail.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ArrowTrail that = (ArrowTrail) o;
        return Objects.equals(particleEffect, that.particleEffect) && Objects.equals(projectiles, that.projectiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), particleEffect, projectiles);
    }
}
