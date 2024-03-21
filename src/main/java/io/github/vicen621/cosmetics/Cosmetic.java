package io.github.vicen621.cosmetics;

import java.util.Objects;

/**
 * Represents a cosmetic.
 * @param <T> the type of entity the cosmetic applies to
 */
public abstract class Cosmetic<T> {
    private final int id;
    private final String name;
    private final String permission;
    private final int cost;

    public Cosmetic(int id, String name, String permission, int cost) {
        this.id = id;
        this.name = name;
        this.permission = permission;
        this.cost = cost;
    }

    /**
     * Applies the cosmetic to the entity.
     * @param t the entity
     */
    public abstract void apply(T t);

    /**
     * Checks if the entity has the cosmetic applied.
     * @param t the entity
     * @return true if the entity has the cosmetic applied, false otherwise
     */
    public abstract boolean hasCosmetic(T t);

    /**
     * Gets the id of the cosmetic.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the cosmetic.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the permission required to use the cosmetic.
     * @return the permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Gets the cost of the cosmetic.
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cosmetic<?> cosmetic = (Cosmetic<?>) o;
        return id == cosmetic.id && cost == cosmetic.cost && Objects.equals(name, cosmetic.name) && Objects.equals(permission, cosmetic.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, permission, cost);
    }
}
