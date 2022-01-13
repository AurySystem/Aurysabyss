package aurysystem.aurumsabyss;

import aurysystem.aurumsabyss.Blocks.AbyssalBlocks;
import aurysystem.aurumsabyss.Items.AbyssalItems;
import aurysystem.aurumsabyss.Worldgen.AbyssalBiomes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;



public class Abyssal implements ModInitializer {
    public static final String MOD_ID = "aurumsabyss";


    @Override
    public void onInitialize() {
        LOG("Generating gay dragons");
        AbyssalBlocks.init();
        AbyssalItems.init();
        AbyssalBiomes.init();
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),GenerationStep.Feature.VEGETAL_DECORATION,RegistryKey.of(Registry.PLACED_FEATURE_KEY,ID("spread_fungus")));
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),GenerationStep.Feature.VEGETAL_DECORATION,RegistryKey.of(Registry.PLACED_FEATURE_KEY,ID("branched_jungle_tree_checked")));
    }


    public static void LOG(String s){
        System.out.println(MOD_ID+": "+s);
    }

    public static Identifier ID(String s){
        return new Identifier(MOD_ID,s);
    }
}
