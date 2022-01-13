package aurysystem.aurumsabyss.Worldgen;

import aurysystem.aurumsabyss.Worldgen.Features.SpreadingMushroomConfig;
import aurysystem.aurumsabyss.Worldgen.Features.SpreadingMushroomFeature;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

import static aurysystem.aurumsabyss.Abyssal.ID;

public class AbyssalFeatures {
    public static final Feature<SpreadingMushroomConfig> SPREADABLE_MUSHROOM = register("spreadable_mushroom", new SpreadingMushroomFeature(SpreadingMushroomConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registry.FEATURE, ID(name), feature);
    }
}
