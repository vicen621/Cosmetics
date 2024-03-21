package io.github.vicen621.cosmetics;

import org.bukkit.plugin.java.JavaPlugin;

public final class Cosmetics extends JavaPlugin {

    private static Cosmetics instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Cosmetics getInstance() {
        return instance;
    }
}
