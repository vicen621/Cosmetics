package io.github.vicen621.cosmetics.arrowtrails;

import io.github.vicen621.cosmetics.Cosmetics;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.metadata.FixedMetadataValue;

public abstract class AbstractArrowTrail {

    private final int id;
    private final String name;
    private final String permission;
    private final int cost;
    private final Particle[] particles;

    public AbstractArrowTrail(int id, String name, String permission, int cost, Particle[] particles) {
        this.id = id;
        this.name = name;
        this.permission = permission;
        this.cost = cost;
        this.particles = particles;
    }

    public void addTrail(Arrow arrow) {
        arrow.setMetadata("trail", new FixedMetadataValue(Cosmetics.getInstance(), getId()));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public int getCost() {
        return cost;
    }

    public Particle[] getParticles() {
        return particles;
    }
}
