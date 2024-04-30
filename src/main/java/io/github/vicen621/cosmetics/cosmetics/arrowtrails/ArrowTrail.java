package io.github.vicen621.cosmetics.cosmetics.arrowtrails;

import com.google.gson.annotations.Expose;
import io.github.vicen621.cosmetics.Main;
import io.github.vicen621.cosmetics.cosmetics.Cosmetic;
import io.github.vicen621.cosmetics.cosmetics.Updatable;
import io.github.vicen621.cosmetics.cosmetics.effects.ParticleEffect;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * Represents an arrow trail.
 */
//TODO: find why the arrow trail is not displayed
// also when equipped, unequipped and equipped again it throws an error in console
public final class ArrowTrail extends Cosmetic<Arrow> implements Updatable {
    @Expose
    private final ParticleEffect particleEffect;
    private Set<Arrow> projectiles;

    public ArrowTrail(int id, String name, String permission, int cost, ParticleEffect particleEffect) {
        super(id, name, permission, cost);
        this.particleEffect = particleEffect;
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event) {
        //TODO: Remove debug messages
        if (!(event.getEntity().getShooter() instanceof Player p)) {
            System.out.println("not a player");
            return;
        }

        if (!isEquipped(p)) {
            p.sendRichMessage("not equipped");
            return;
        }
        if (!(event.getEntity() instanceof Arrow arrow)) {
            p.sendRichMessage("not an arrow");
            return;
        }

        projectiles.add(arrow);
    }

    @Override
    public void onUpdate() {
        Iterator<Arrow> iter = projectiles.iterator();

        while (iter.hasNext()) {
            Arrow projectile = iter.next();

            if (projectile.isDead() || projectile.isOnGround() || projectile.isInBlock()) {
                iter.remove();
                continue;
            }

            System.out.println("displaying particle");
            getParticleEffect().display(projectile);
        }
    }

    @Override
    protected void onEquip(Player player) {
        player.getPersistentDataContainer().set(
                new NamespacedKey(Main.getInstance(), "arrow_trail"),
                PersistentDataType.INTEGER, getId()
        );
    }

    @Override
    protected void onUnequip(Player player) {
        player.getPersistentDataContainer().set(
                new NamespacedKey(Main.getInstance(), "arrow_trail"),
                PersistentDataType.INTEGER, -1
        );
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
    protected void scheduleTask() {
        runTaskTimerAsynchronously(getPlugin(), 0, getParticleEffect().getRepeatDelay());
    }

    /**
     * Gets the particles of the arrow trail.
     * @return the particles
     */
    public ParticleEffect getParticleEffect() {
        return particleEffect;
    }

    /**
     * parse an arrow trail from the given file
     * @param file the file
     * @return the arrow trail
     * @throws FileNotFoundException if the file not exists
     */
    public static ArrowTrail load(File file) throws FileNotFoundException {
        ArrowTrail trail = getGson().fromJson(new FileReader(file), ArrowTrail.class);
        // Hay que llamar a este metodo porque Gson no llama al constructor y de no llamarlo queda en null
        trail.initializeProjectiles();
        return trail;
    }

    private void initializeProjectiles() {
        this.projectiles = new HashSet<>();
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
