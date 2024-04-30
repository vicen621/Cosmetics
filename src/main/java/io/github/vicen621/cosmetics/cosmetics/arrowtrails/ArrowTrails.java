package io.github.vicen621.cosmetics.cosmetics.arrowtrails;

import com.google.common.collect.ImmutableList;
import com.google.common.io.PatternFilenameFilter;
import io.github.vicen621.cosmetics.cosmetics.DataLoader;
import io.github.vicen621.cosmetics.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Optional;

/**
 * List of arrow trails.
 */
public final class ArrowTrails implements DataLoader<ArrowTrail> {
    private static ImmutableList<ArrowTrail> trails;

    public ArrowTrails() {
        trails = load(Main.getCosmeticFolder("arrow_trails"));
    }

    /**
     * Loads the arrow trails from the specified directory.
     * @param directory the directory
     * @return the arrow trails
     */
    //TODO: Remove debug message
    public ImmutableList<ArrowTrail> load(File directory) {
        if (directory.isFile())
            throw new IllegalArgumentException("The file must be a directory");

        File[] files = directory.listFiles(new PatternFilenameFilter(".*\\.json"));
        if (files == null) {
            Main.getInstance().getLogger().info("files are null");
            return ImmutableList.of();
        }

        ImmutableList.Builder<ArrowTrail> builder = ImmutableList.builder();
        Arrays.stream(files).forEach(file -> {
            try {
                ArrowTrail trail = ArrowTrail.load(file);
                builder.add(trail);
                Main.getInstance().getLogger().info("Loadded arrow trail: " + trail.getName());
            } catch (FileNotFoundException ignored) { }
        });

        return builder.build();
    }

    /**
     * Gets the arrow trail with the specified id.
     * @param id the id
     * @return the arrow trail if found, an empty Optional otherwise
     */
    public static Optional<ArrowTrail> getTrail(int id) {
        return trails.stream().filter(trail -> trail.getId() == id).findFirst();
    }

    /**
     * Gets the arrow trail with the specified name.
     * @param name the name
     * @return the arrow trail if found, an empty Optional otherwise
     */
    public static Optional<ArrowTrail> getTrail(String name) {
        return trails.stream().filter(trail -> trail.getName().equalsIgnoreCase(name)).findFirst();
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
