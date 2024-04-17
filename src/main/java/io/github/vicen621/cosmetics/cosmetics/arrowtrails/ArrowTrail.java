package io.github.vicen621.cosmetics.cosmetics.arrowtrails;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.vicen621.cosmetics.cosmetics.Cosmetic;
import io.github.vicen621.cosmetics.Main;
import io.github.vicen621.cosmetics.cosmetics.Updatable;
import io.github.vicen621.cosmetics.cosmetics.effects.ParticleEffect;
import io.github.vicen621.cosmetics.cosmetics.serializers.ArrowTrailSerializer;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents an arrow trail.
 */
public final class ArrowTrail extends Cosmetic<Arrow> implements Updatable {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(ArrowTrail.class, new ArrowTrailSerializer())
            .create();
    private final ParticleEffect effect;
    private final Set<Arrow> projectiles;

    public ArrowTrail(int id, String name, String permission, int cost, ParticleEffect effect) {
        super(id, name, permission, cost);
        this.effect = effect;
        this.projectiles = new HashSet<>();
    }

    @Override
    public void apply(Arrow arrow) {
        projectiles.add(arrow);
    }

    @Override
    public boolean hasCosmetic(Arrow arrow) {
        return projectiles.contains(arrow);
    }

    @Override
    public void onUpdate() {
        for (Projectile projectile : projectiles)
            getEffect().display(projectile);
    }

    /**
     * Gets the particles of the arrow trail.
     * @return the particles
     */
    public ParticleEffect getEffect() {
        return effect;
    }

    /**
     * parse an arrow trail from the given file
     * @param file the file
     * @return the arrow trail
     * @throws FileNotFoundException if the file not exists
     */
    public static ArrowTrail load(File file) throws FileNotFoundException {
        return GSON.fromJson(new FileReader(file), ArrowTrail.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ArrowTrail that = (ArrowTrail) o;
        return Objects.equals(effect, that.effect) && Objects.equals(projectiles, that.projectiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), effect, projectiles);
    }
}
