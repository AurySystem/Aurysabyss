package aurysystem.aurumsabyss.Worldgen;

import aurysystem.aurumsabyss.Abyssal;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.NetherPlacedFeatures;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

import static aurysystem.aurumsabyss.Abyssal.ID;

public class AbyssalBiomes {

    public static final RegistryKey<Biome> WeirdCave = registerBiomeKey("strange_depths");

    public static void init(){

        registerBiome(WeirdCave, createWeirdCaves());
    }


    public static final MultiNoiseUtil.ParameterRange DEFAULT_PARAMETER = MultiNoiseUtil.ParameterRange.of(-1.0F, 1.0F);
    public static void writeCaveBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, float offset, RegistryKey<Biome> biome) {
        parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(0.2F, 0.9F), weirdness, offset), biome));
    }

    private static Biome registerBiome(RegistryKey<Biome> key, Biome biome) {
        return (Biome) BuiltinRegistries.set(BuiltinRegistries.BIOME, key, biome);
    }
    private static RegistryKey<Biome> registerBiomeKey(String name) {
        return RegistryKey.of(Registry.BIOME_KEY, ID(name));
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
        builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedFeatureRegs.GIANT_MUSHROOMS);
        builder2.feature(GenerationStep.Feature.VEGETAL_DECORATION, PlacedFeatureRegs.PATCH_BERRY_UNDER);
        builder2.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, PlacedFeatureRegs.MOSS_ROCK);
        DefaultBiomeFeatures.addPlainsTallGrass(builder2);
        DefaultBiomeFeatures.addDefaultOres(builder2);
        DefaultBiomeFeatures.addDefaultMushrooms(builder2);
        DefaultBiomeFeatures.addDefaultDisks(builder2);
        builder2.feature(GenerationStep.Feature.SURFACE_STRUCTURES, PlacedFeatureRegs.GRASS_DELTA);
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
