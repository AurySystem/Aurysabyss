package aurysystem.abyssal;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;


public class Abyssal implements ModInitializer {
    public static final String MOD_ID = "aurumsabyss";

    public static final ConfiguredFeature<RandomFeatureConfig, ?> UNDERGROUND_MUSHROOMS;
    public static final ConfiguredFeature<VegetationPatchFeatureConfig, ?> GRASSY_DELTA;
    public static final PlacedFeature GIANT_MUSHROOMS;
    public static final PlacedFeature GRASS_DELTA;
    public static final PlacedFeature MOSS_ROCK;
    public static final PlacedFeature PATCH_BERRY_UNDER;
    static{

        UNDERGROUND_MUSHROOMS = ConfiguredFeatures.register(MOD_ID+":underground_mushroom", Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(List.of(new RandomFeatureEntry(TreeConfiguredFeatures.HUGE_BROWN_MUSHROOM.withPlacement(new PlacementModifier[0]), 0.25F), new RandomFeatureEntry(TreeConfiguredFeatures.HUGE_RED_MUSHROOM.withPlacement(new PlacementModifier[0]), 0.25F)), VegetationConfiguredFeatures.PATCH_RED_MUSHROOM.withPlacement(new PlacementModifier[0]))));

        GRASSY_DELTA = ConfiguredFeatures.register(MOD_ID+":grass_delta", Feature.WATERLOGGED_VEGETATION_PATCH.configure(new VegetationPatchFeatureConfig(BlockTags.LUSH_GROUND_REPLACEABLE.getId(), BlockStateProvider.of(Blocks.GRASS_BLOCK), () -> {
            return VegetationConfiguredFeatures.PATCH_WATERLILY.withPlacement(new PlacementModifier[0]);
        }, VerticalSurfaceType.FLOOR, ConstantIntProvider.create(3), 0.8F, 5, 0.1F, UniformIntProvider.create(4, 7), 0.7F)));

        //

        PATCH_BERRY_UNDER = PlacedFeatures.register(MOD_ID+":patch_berry_underground", VegetationConfiguredFeatures.PATCH_BERRY_BUSH.withPlacement(new PlacementModifier[]{CountPlacementModifier.of(12), RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)), PlacedFeatures.BOTTOM_TO_120_RANGE, SquarePlacementModifier.of(), BiomePlacementModifier.of()}));

        MOSS_ROCK = PlacedFeatures.register(MOD_ID+":underground_forest_rock", MiscConfiguredFeatures.FOREST_ROCK.withPlacement(new PlacementModifier[]{CountPlacementModifier.of(26), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12), RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)), BiomePlacementModifier.of()}));

        GRASS_DELTA = PlacedFeatures.register(MOD_ID+":grass_delta", Abyssal.GRASSY_DELTA.withPlacement(new PlacementModifier[]{CountPlacementModifier.of(12), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12), RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)), BiomePlacementModifier.of()}));

        GIANT_MUSHROOMS = PlacedFeatures.register(MOD_ID+":giant_mushroom", Abyssal.UNDERGROUND_MUSHROOMS.withPlacement(new PlacementModifier[]{CountPlacementModifier.of(37), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12), RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)), BiomePlacementModifier.of()}));

    }

    public static final RegistryKey<Biome> WeirdCave = registerBiomeKey("strange_depths");
    @Override
    public void onInitialize() {
        registerBiome(Abyssal.WeirdCave, Abyssal.createWeirdCaves());
        System.out.println("aaaaa");
    }

    public static final MultiNoiseUtil.ParameterRange DEFAULT_PARAMETER = MultiNoiseUtil.ParameterRange.of(-1.0F, 1.0F);
    public static void writeCaveBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, float offset, RegistryKey<Biome> biome) {
        parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(0.2F, 0.9F), weirdness, offset), biome));
    }

    private static Biome registerBiome(RegistryKey<Biome> key, Biome biome) {
        return (Biome) BuiltinRegistries.set(BuiltinRegistries.BIOME, key, biome);
    }
    private static RegistryKey<Biome> registerBiomeKey(String name) {
        return RegistryKey.of(Registry.BIOME_KEY, new Identifier(MOD_ID,name));
    }

    public static Biome createWeirdCaves() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.ZOMBIFIED_PIGLIN, 25, 5, 16))
                .spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.BLAZE, 25, 5, 16))
                .spawn(SpawnGroup.WATER_AMBIENT, new SpawnSettings.SpawnEntry(EntityType.TROPICAL_FISH, 50, 8, 8))
                .spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 150, 8, 8));
        DefaultBiomeFeatures.addBatsAndMonsters(builder);
        net.minecraft.world.biome.GenerationSettings.Builder builder2 = new net.minecraft.world.biome.GenerationSettings.Builder();
        addBasicFeatures(builder2);
        builder2.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.NETHER_CAVE);
        builder2.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, NetherPlacedFeatures.BASALT_PILLAR);
        builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, Abyssal.GIANT_MUSHROOMS);
        builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, Abyssal.PATCH_BERRY_UNDER);
        builder2.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, Abyssal.MOSS_ROCK);
        DefaultBiomeFeatures.addPlainsTallGrass(builder2);
        DefaultBiomeFeatures.addDefaultOres(builder2);
        DefaultBiomeFeatures.addDefaultMushrooms(builder2);
        DefaultBiomeFeatures.addDefaultDisks(builder2);
        builder2.feature(GenerationStep.Feature.SURFACE_STRUCTURES, Abyssal.GRASS_DELTA);
        MusicSound musicSound = MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_LUSH_CAVES);
        return createBiome(Biome.Precipitation.NONE, Biome.Category.UNDERGROUND, 0.8F, 0.5F, builder, builder2, musicSound);
    }


    //copied from overworld biome creator decompilation for now
    private static Biome createBiome(Biome.Precipitation precipitation, Biome.Category category, float temperature, float downfall, SpawnSettings.Builder spawnSettings, net.minecraft.world.biome.GenerationSettings.Builder generationSettings, @Nullable MusicSound music) {
        return createBiome(precipitation, category, temperature, downfall, 4159204, 329011, spawnSettings, generationSettings, music);
    }

    private static Biome createBiome(Biome.Precipitation precipitation, Biome.Category category, float temperature, float downfall, int waterColor, int waterFogColor, SpawnSettings.Builder spawnSettings, net.minecraft.world.biome.GenerationSettings.Builder generationSettings, @Nullable MusicSound music) {
        return (new net.minecraft.world.biome.Biome.Builder()).precipitation(precipitation).category(category).temperature(temperature).downfall(downfall).effects((new net.minecraft.world.biome.BiomeEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(getSkyColor(temperature)).moodSound(BiomeMoodSound.CAVE).music(music).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    protected static int getSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = MathHelper.clamp(f, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }

    private static void addBasicFeatures(net.minecraft.world.biome.GenerationSettings.Builder generationSettings) {
        DefaultBiomeFeatures.addLandCarvers(generationSettings);
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        DefaultBiomeFeatures.addDungeons(generationSettings);
        DefaultBiomeFeatures.addMineables(generationSettings);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
    }

}
