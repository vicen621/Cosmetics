package io.github.vicen621.cosmetics;
import io.github.vicen621.cosmetics.cosmetics.Cosmetic;
import io.github.vicen621.cosmetics.cosmetics.arrowtrails.ArrowTrail;
import io.github.vicen621.cosmetics.cosmetics.arrowtrails.ArrowTrails;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Optional;

public final class Main extends JavaPlugin implements Listener {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        getServer().getPluginManager().registerEvents(this, this);
        new ArrowTrails();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    //TODO: Remove debug events
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getClickedBlock() == null) return;

        switch (e.getClickedBlock().getType()) {
            case DIAMOND_BLOCK -> {
                Optional<ArrowTrail> arrowTrail = ArrowTrails.getTrail("test");
                arrowTrail.ifPresent(trail -> {
                    trail.equip(e.getPlayer());
                    e.getPlayer().sendRichMessage("Arrow trail equipped!");
                });
            }

            case REDSTONE_BLOCK -> {
                Optional<ArrowTrail> arrowTrail = ArrowTrails.getTrail("test");
                arrowTrail.ifPresent(trail -> {
                    trail.unequip(e.getPlayer());
                    e.getPlayer().sendRichMessage("Arrow trail unequipped!");
                });
            }
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public static File getCosmeticFolder(String cosmetic) {
        /*File cosmeticsFolder = new File(getInstance().getDataFolder(), "cosmetics");
        if (!cosmeticsFolder.exists())
            cosmeticsFolder.mkdir();*/

        File cosmeticFolder = new File(getInstance().getDataFolder(), cosmetic);
        if (!cosmeticFolder.exists())
            cosmeticFolder.mkdir();

        return cosmeticFolder;
    }
}
