package aurysystem.aurumsabyss.mixin;

import aurysystem.aurumsabyss.Abyssal;
import aurysystem.aurumsabyss.Worldgen.AbyssalBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(VanillaBiomeParameters.class)
public class VanillaBiomeParametersMixin {

    @Inject(at = @At("HEAD"), method = "writeCaveBiomes")
    void writeCaveBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, CallbackInfo ci) {
        AbyssalBiomes.writeCaveBiomeParameters(parameters, MultiNoiseUtil.ParameterRange.of(-0.9F, 1.0F), AbyssalBiomes.DEFAULT_PARAMETER, MultiNoiseUtil.ParameterRange.of(-0.4F, 0.3F), AbyssalBiomes.DEFAULT_PARAMETER, AbyssalBiomes.DEFAULT_PARAMETER, 0.1F, AbyssalBiomes.WeirdCave);
        AbyssalBiomes.writeCaveBiomeParameters(parameters, MultiNoiseUtil.ParameterRange.of(-0.9F, 1.0F), AbyssalBiomes.DEFAULT_PARAMETER, MultiNoiseUtil.ParameterRange.of(-0.9F, -0.2F), AbyssalBiomes.DEFAULT_PARAMETER, AbyssalBiomes.DEFAULT_PARAMETER, 0.2F, BiomeKeys.CRIMSON_FOREST);
    }
}
