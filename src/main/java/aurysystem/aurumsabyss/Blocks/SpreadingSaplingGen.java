package aurysystem.aurumsabyss.Blocks;

import aurysystem.aurumsabyss.Worldgen.ConfigedFeatureRegs;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;


public class SpreadingSaplingGen extends SaplingGenerator {
    @Override
    protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bees) {
        return ConfigedFeatureRegs.SPREADING_FUNGUS;
    }
}
