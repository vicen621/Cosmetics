package io.github.vicen621.cosmetics.cosmetics.effects;

import org.bukkit.entity.Entity;

public class BasicParticleEffect extends ParticleEffect {

    public BasicParticleEffect(ParticleData particleData, int repeatDelay) {
        super(particleData, repeatDelay);
    }

    @Override
    public void display(Entity entity) {
        getParticleData().createParticleBuilder().location(entity.getLocation()).extra(0).spawn();
    }
}
