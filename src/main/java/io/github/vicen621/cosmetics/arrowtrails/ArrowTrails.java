package io.github.vicen621.cosmetics.arrowtrails;

import io.github.vicen621.cosmetics.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Optional;

/**
 * List of arrow trails.
 */
public final class ArrowTrails {

    public static final ArrowTrail FLAME = new ArrowTrail(0, "Flame", "cosmetics.trail.flame", 100, Particle.FLAME);
    public static final ArrowTrail HEART = new ArrowTrail(1, "Heart", "cosmetics.trail.heart", 100, Particle.HEART);
    public static final ArrowTrail CLOUD = new ArrowTrail(2, "Cloud", "cosmetics.trail.cloud", 100, Particle.CLOUD);
    public static final ArrowTrail REDSTONE = new ArrowTrail(3, "Redstone", "cosmetics.trail.redstone", 100, Particle.REDSTONE);
    public static final ArrowTrail CRIT = new ArrowTrail(4, "Crit", "cosmetics.trail.crit", 100, Particle.CRIT);
    public static final ArrowTrail WATER = new ArrowTrail(5, "Water", "cosmetics.trail.water", 100, Particle.DRIP_WATER);
    public static final ArrowTrail LAVA = new ArrowTrail(6, "Lava", "cosmetics.trail.lava", 100, Particle.DRIP_LAVA);

    private static final ArrowTrail[] TRAILS = new ArrowTrail[] {FLAME, HEART, CLOUD, REDSTONE, CRIT, WATER, LAVA};

    /**
     * Gets the arrow trail with the specified id.
     * @param id the id
     * @return the arrow trail if found, an empty Optional otherwise
     */
    public static Optional<ArrowTrail> getTrail(int id) {
        return Arrays.stream(TRAILS).filter(trail -> trail.getId() == id).findFirst();
    }

    /**
     * Gets the arrow trail with the specified name.
     * @param name the name
     * @return the arrow trail if found, an empty Optional otherwise
     */
    public static Optional<ArrowTrail> getTrail(String name) {
        return Arrays.stream(TRAILS).filter(trail -> trail.getName().equalsIgnoreCase(name)).findFirst();
    }

    /**
     * Gets the arrow trail that the player have.
     * @param player the player
     * @return the arrow trail if found, an empty Optional otherwise
     */
    public static Optional<ArrowTrail> getTrail(Player player) {
        return getTrail(player.getPersistentDataContainer().getOrDefault(
                new NamespacedKey(Main.getInstance(), "arrow_trail"),
                PersistentDataType.INTEGER,
                -1
        ));
    }
}
