package io.github.vicen621.cosmetics.cosmetics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import io.github.vicen621.cosmetics.Main;
import io.github.vicen621.cosmetics.cosmetics.effects.BasicParticleEffect;
import io.github.vicen621.cosmetics.cosmetics.effects.HelixParticleEffect;
import io.github.vicen621.cosmetics.cosmetics.effects.ParticleData;
import io.github.vicen621.cosmetics.cosmetics.effects.ParticleEffect;
import io.github.vicen621.cosmetics.cosmetics.serializers.ParticleDataSerializer;
import io.github.vicen621.cosmetics.cosmetics.serializers.RuntimeTypeAdapterFactory;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

/**
 * Represents a cosmetic.
 * @param <T> the type of entity the cosmetic applies to
 */
public abstract class Cosmetic<T> extends BukkitRunnable implements Listener {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ParticleData.class, new ParticleDataSerializer())
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(ParticleEffect.class)
                    .registerSubtype(BasicParticleEffect.class, "basic")
                    .registerSubtype(HelixParticleEffect.class, "helix")
            )
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();
    @Expose
    private final int id;
    @Expose
    private final String name;
    @Expose
    private final String permission;
    @Expose
    private final int cost;

    public Cosmetic(int id, String name, String permission, int cost) {
        this.id = id;
        this.name = name;
        this.permission = permission;
        this.cost = cost;
    }

    public void equip(Player player) {
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());

        if (this instanceof Updatable)
            scheduleTask();

        onEquip(player);
    }

    // FIXME: No cancela la task, al hacer equip otra vez tira error
    public void unequip(Player player) {
        cancel();
        HandlerList.unregisterAll(this);

        onUnequip(player);
    }

    protected void scheduleTask() {
        runTaskTimer(getPlugin(), 0, 1);
    }

    @Override
    public void run() {
        ((Updatable) this).onUpdate();
    }

    //TODO: make this method abstract and set the correct namespace key
    public boolean isEquipped(Player player) {
        return player.getPersistentDataContainer().getOrDefault(
                new NamespacedKey(Main.getInstance(), "arrow_trail"),
                PersistentDataType.INTEGER,
                -1
        ) == getId();
    }

    public boolean canEquip(Player player) {
        return player.hasPermission(permission);
    }

    public String toJson() {
        return getGson().toJson(this);
    }

    /**
     * Called when the cosmetic is equipped.
     */
    protected abstract void onEquip(Player player);

    /**
     * Called when the cosmetic is unequipped.
     */
    protected abstract void onUnequip(Player player);

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

    public static Gson getGson() {
        return GSON;
    }

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

    public Main getPlugin() {
        return Main.getInstance();
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
