import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.vicen621.cosmetics.cosmetics.arrowtrails.ArrowTrail;
import io.github.vicen621.cosmetics.cosmetics.effects.BasicParticleEffect;
import io.github.vicen621.cosmetics.cosmetics.effects.HelixParticleEffect;
import io.github.vicen621.cosmetics.cosmetics.effects.ParticleData;
import io.github.vicen621.cosmetics.cosmetics.effects.ParticleEffect;
import io.github.vicen621.cosmetics.cosmetics.serializers.ParticleDataSerializer;
import io.github.vicen621.cosmetics.cosmetics.serializers.RuntimeTypeAdapterFactory;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GsonSerializerTest {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(ParticleData.class, new ParticleDataSerializer())
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(ParticleEffect.class)
                    .registerSubtype(BasicParticleEffect.class, "basic")
                    .registerSubtype(HelixParticleEffect.class, "helix")
            )
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    @Test
    void test_helix_particle_effect_serialization() {
        HelixParticleEffect helix = new HelixParticleEffect(new ParticleData(Particle.REDSTONE, Color.fromRGB(255, 254, 253)), 1);
        assertEquals(
                "{\"type\":\"helix\",\"particleData\":{\"particle\":\"REDSTONE\",\"color\":\"#FFFEFD\"},\"repeatDelay\":1}",
                gson.toJson(helix, ParticleEffect.class)
        );
    }

    @Test
    void test_helix_particle_effect_deserialization() {
        HelixParticleEffect helix = new HelixParticleEffect(new ParticleData(Particle.REDSTONE, Color.fromRGB(255, 254, 253)), 1);
        ParticleEffect jsonHelix = gson.fromJson("{\"type\":\"helix\",\"repeatDelay\":1,\"particleData\":{\"particle\":\"REDSTONE\",\"color\":\"#FFFEFD\"}}", ParticleEffect.class);
        assertEquals(jsonHelix.getRepeatDelay(), helix.getRepeatDelay());
        assertEquals(helix.getParticleData().getParticle().name(), jsonHelix.getParticleData().getParticle().name());
        assertEquals(helix.getParticleData().getColor(), jsonHelix.getParticleData().getColor());
        assertInstanceOf(HelixParticleEffect.class, jsonHelix);
    }

    @Test
    void test_arrow_trail_serialization() {
        BasicParticleEffect basic = new BasicParticleEffect(new ParticleData(Particle.VILLAGER_ANGRY), 1);
        ArrowTrail arrowTrail = new ArrowTrail(1, "test", "cosmetics.trail.test", 100, basic);
        assertEquals(
                "{\"particleEffect\":{\"type\":\"basic\",\"particleData\":{\"particle\":\"VILLAGER_ANGRY\"},\"repeatDelay\":1},\"id\":1,\"name\":\"test\",\"permission\":\"cosmetics.trail.test\",\"cost\":100}",
                gson.toJson(arrowTrail)
        );
    }

    @Test
    void test_arrow_trail_deserialization() {
        BasicParticleEffect basic = new BasicParticleEffect(new ParticleData(Particle.VILLAGER_HAPPY), 2);
        ArrowTrail arrowTrail = new ArrowTrail(2, "test", "cosmetics.trail.test", 10, basic);
        ArrowTrail jsonArrowTrail = gson.fromJson("{\"id\":2,\"name\":\"test\",\"permission\":\"cosmetics.trail.test\",\"cost\":10,\"particleEffect\":{\"type\":\"basic\",\"repeatDelay\":2,\"particleData\":{\"particle\":\"VILLAGER_HAPPY\"}}}", ArrowTrail.class);
        assertTrue(equals(arrowTrail, jsonArrowTrail));
        assertInstanceOf(BasicParticleEffect.class, jsonArrowTrail.getParticleEffect());
    }

    boolean equals(ArrowTrail a, ArrowTrail b) {
        return a.getId() == b.getId() && a.getName().equals(b.getName()) && a.getPermission().equals(b.getPermission()) && a.getCost() == b.getCost() && a.getParticleEffect().equals(b.getParticleEffect());
    }
}
