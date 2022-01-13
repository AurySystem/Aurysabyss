package aurysystem.aurumsabyss.Worldgen;

import aurysystem.aurumsabyss.Abyssal;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;

import static aurysystem.aurumsabyss.Abyssal.ID;

public class PlacedFeatureRegs {
    public static final PlacedFeature GIANT_MUSHROOMS;
    public static final PlacedFeature GRASS_DELTA;
    public static final PlacedFeature MOSS_ROCK;
    public static final PlacedFeature PATCH_BERRY_UNDER;
    public static final PlacedFeature SPREADING_FUNGUS;
    public static final PlacedFeature BRNCH_JUNGLE_TREE_CHECKED;

    static{
        BRNCH_JUNGLE_TREE_CHECKED = register("branched_jungle_tree_checked", ConfigedFeatureRegs.BRNCH_JUNGLE_TREE.withPlacement(
                CountPlacementModifier.of(21),
                SquarePlacementModifier.of(),
                PlacedFeatures.BOTTOM_TO_120_RANGE,
                EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12),
                RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                BiomePlacementModifier.of()));

        PATCH_BERRY_UNDER = register("patch_berry_underground",VegetationConfiguredFeatures.PATCH_BERRY_BUSH.withPlacement(
                new PlacementModifier[]{
                        CountPlacementModifier.of(12),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                        PlacedFeatures.BOTTOM_TO_120_RANGE,
                        SquarePlacementModifier.of(),
                        BiomePlacementModifier.of()
                }));

        MOSS_ROCK = register("underground_forest_rock", MiscConfiguredFeatures.FOREST_ROCK.withPlacement(new PlacementModifier[]{
                CountPlacementModifier.of(26),
                SquarePlacementModifier.of(),
                PlacedFeatures.BOTTOM_TO_120_RANGE,
                EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12),
                RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                BiomePlacementModifier.of()
        }));

        SPREADING_FUNGUS = register("spread_fungus", ConfigedFeatureRegs.SPREADING_FUNGUS.withPlacement(
                CountPlacementModifier.of(10),
                SquarePlacementModifier.of(),
                PlacedFeatures.BOTTOM_TO_120_RANGE,
                EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12),
                RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                BiomePlacementModifier.of()));

        GRASS_DELTA = register("grass_delta", ConfigedFeatureRegs.GRASSY_DELTA.withPlacement(
                new PlacementModifier[]{
                        CountPlacementModifier.of(12),
                        SquarePlacementModifier.of(),
                        PlacedFeatures.BOTTOM_TO_120_RANGE,
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                        BiomePlacementModifier.of()
                }));

        GIANT_MUSHROOMS = register("giant_mushroom", ConfigedFeatureRegs.UNDERGROUND_MUSHROOMS.withPlacement(
                new PlacementModifier[]{
                        CountPlacementModifier.of(37),
                        SquarePlacementModifier.of(),
                        PlacedFeatures.BOTTOM_TO_120_RANGE,
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                        BiomePlacementModifier.of()
                }));

    }


    public static PlacedFeature register(String id, PlacedFeature feature) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, ID(id), feature);
    }
}
