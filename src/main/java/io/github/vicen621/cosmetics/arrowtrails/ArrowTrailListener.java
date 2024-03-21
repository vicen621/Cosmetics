package io.github.vicen621.cosmetics.arrowtrails;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import io.github.vicen621.cosmetics.Main;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Listens for arrow trail events.
 */
public final class ArrowTrailListener implements Listener {

    @EventHandler
    public void onArrowShoot(PlayerLaunchProjectileEvent e) {
        if (!(e.getProjectile() instanceof Arrow arrow))
            return;

        ArrowTrails.getTrail(e.getPlayer()).ifPresent(trail -> {
            trail.apply(arrow);

            //TODO: Testear, si no funciona no puede ser async
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (arrow.isDead() || arrow.isInBlock()) {
                        cancel();
                        return;
                    }

                    for (Particle particle : trail.getParticles())
                        arrow.getWorld().spawnParticle(particle, arrow.getLocation(), 1, 0, 0, 0, 0);
                }
            }.runTaskTimerAsynchronously(Main.getInstance(), 0, 1);
        });
    }
}
