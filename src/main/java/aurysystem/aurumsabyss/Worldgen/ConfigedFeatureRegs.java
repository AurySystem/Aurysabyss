package aurysystem.aurumsabyss.Worldgen;

import aurysystem.aurumsabyss.Blocks.AbyssalBlocks;
import aurysystem.aurumsabyss.Worldgen.Features.SpreadingMushroomConfig;
import aurysystem.aurumsabyss.Worldgen.Features.TreePlacer.BranchyTrunkPlacer;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.PlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.JungleFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.MegaJungleTrunkPlacer;

import java.util.List;

import static aurysystem.aurumsabyss.Abyssal.ID;

public class ConfigedFeatureRegs {
    public static final ConfiguredFeature<RandomFeatureConfig, ?> UNDERGROUND_MUSHROOMS;
    public static final ConfiguredFeature<VegetationPatchFeatureConfig, ?> GRASSY_DELTA;
    public static final ConfiguredFeature<HugeFungusFeatureConfig, ?> RED_FUNGUS;
    public static final ConfiguredFeature<SpreadingMushroomConfig,?> SPREADING_FUNGUS;
    public static final ConfiguredFeature<TreeFeatureConfig, ?> BRNCH_JUNGLE_TREE;
    static{
        BRNCH_JUNGLE_TREE = register("branched_jungle_tree", Feature.TREE.configure(
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(Blocks.JUNGLE_LOG),
                        new BranchyTrunkPlacer(13, 3, 24),
                        BlockStateProvider.of(Blocks.JUNGLE_LEAVES),
                        new JungleFoliagePlacer(
                                ConstantIntProvider.create(2),
                                ConstantIntProvider.create(0), 2),
                        new TwoLayersFeatureSize(1, 1, 2)
                ).decorators(ImmutableList.of(
                        TrunkVineTreeDecorator.INSTANCE,
                        LeavesVineTreeDecorator.INSTANCE)
                ).build()));

        RED_FUNGUS = register("red_fungus", Feature.HUGE_FUNGUS.configure(
                new HugeFungusFeatureConfig(
                        Blocks.PODZOL.getDefaultState(),
                        Blocks.MUSHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false).with(MushroomBlock.DOWN, false),
                        Blocks.RED_MUSHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false),
                        Blocks.SHROOMLIGHT.getDefaultState(),
                        false))
        );

        SPREADING_FUNGUS = register("spread_fungus", AbyssalFeatures.SPREADABLE_MUSHROOM.configure(
                new SpreadingMushroomConfig(
                        BlockStateProvider.of(Blocks.SHROOMLIGHT.getDefaultState()),
                        BlockStateProvider.of(Blocks.MUSHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false).with(MushroomBlock.DOWN, false)),
                        Blocks.MYCELIUM.getDefaultState(),
                        AbyssalBlocks.SPREAD_SAPLING.getDefaultState(),
                        2,
                        2,
                        UniformIntProvider.create(6, 12),
                        UniformIntProvider.create(6, 11),
                        BlockTags.MOSS_REPLACEABLE.getId()
                )
        ));

        UNDERGROUND_MUSHROOMS = register("underground_mushroom", Feature.RANDOM_SELECTOR.configure(
                new RandomFeatureConfig(List.of(new RandomFeatureEntry(
                        TreeConfiguredFeatures.HUGE_BROWN_MUSHROOM.withPlacement(), 0.25F),
                        new RandomFeatureEntry(RED_FUNGUS.withPlacement(), 0.25F)),
                        VegetationConfiguredFeatures.PATCH_RED_MUSHROOM.withPlacement())
        ));

        GRASSY_DELTA = register("grass_delta", Feature.WATERLOGGED_VEGETATION_PATCH.configure(
                new VegetationPatchFeatureConfig(BlockTags.LUSH_GROUND_REPLACEABLE.getId(),
                        BlockStateProvider.of(Blocks.GRASS_BLOCK), () -> {
                            return VegetationConfiguredFeatures.PATCH_WATERLILY.withPlacement();
                        },
                        VerticalSurfaceType.FLOOR,
                        ConstantIntProvider.create(3),0.8F, 5, 0.1F,
                        UniformIntProvider.create(4, 7), 0.7F)));

    }

    public static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID(id), configuredFeature);
    }
}
