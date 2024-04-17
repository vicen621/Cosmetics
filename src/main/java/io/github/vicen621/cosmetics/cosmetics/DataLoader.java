package io.github.vicen621.cosmetics.cosmetics;

import com.google.common.collect.ImmutableList;

import java.io.File;

public interface DataLoader<T extends Cosmetic<?>> {
    /**
     * Loads data from the specified directory.
     * @param directory the directory
     * @return the data
     */
    ImmutableList<T> load(File directory);
}
