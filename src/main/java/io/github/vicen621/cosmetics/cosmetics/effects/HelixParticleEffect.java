package io.github.vicen621.cosmetics.cosmetics.effects;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class HelixParticleEffect extends ParticleEffect {
    private static final Vector Y_AXIS = new Vector(0, 1, 0);
    private static final double STEP = Math.PI / 8;
    private transient double angle = 0;

    public HelixParticleEffect(ParticleData effect, int repeatDelay) {
        super(effect, repeatDelay);
    }

    @Override
    public void display(Entity entity) {
        angle += STEP;
        if (angle >= Math.PI * 2) angle %= Math.PI * 2;
        Vector velocity = entity.getVelocity();
        Vector distanceFromCenter = velocity.clone().crossProduct(Y_AXIS).normalize().rotateAroundAxis(velocity, angle);
        showHelix(entity, distanceFromCenter);
    }

    public void showHelix(Entity entity, Vector distance) {
        World world = entity.getWorld();
        getParticleData().createParticleBuilder().location(entity.getLocation().add(distance)).spawn();
        getParticleData().createParticleBuilder().location(entity.getLocation().subtract(distance)).spawn();
    }
}
