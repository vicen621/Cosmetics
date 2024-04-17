package io.github.vicen621.cosmetics;

import io.github.vicen621.cosmetics.cosmetics.arrowtrails.ArrowTrail;
import io.github.vicen621.cosmetics.cosmetics.effects.HelixParticleEffect;
import io.github.vicen621.cosmetics.cosmetics.effects.ParticleData;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        ArrowTrail trail = new ArrowTrail(
                0,
                "Flame Helix",
                "cosmetics.trail.flame_helix",
                100,
                new HelixParticleEffect(new ParticleData(Particle.SOUL_FIRE_FLAME, Color.fromRGB(100, 20, 150)), 1)
        );

        getLogger().info("Loaded arrow trail: " + trail.toJson());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return instance;
    }
}
