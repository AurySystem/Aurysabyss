package aurysystem.abyssal.mixin;

import aurysystem.abyssal.Abyssal;
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
        Abyssal.writeCaveBiomeParameters(parameters, MultiNoiseUtil.ParameterRange.of(-0.9F, 1.0F), Abyssal.DEFAULT_PARAMETER, MultiNoiseUtil.ParameterRange.of(-0.4F, 0.3F), Abyssal.DEFAULT_PARAMETER, Abyssal.DEFAULT_PARAMETER, 0.1F, Abyssal.WeirdCave);
        Abyssal.writeCaveBiomeParameters(parameters, MultiNoiseUtil.ParameterRange.of(-0.9F, 1.0F), Abyssal.DEFAULT_PARAMETER, MultiNoiseUtil.ParameterRange.of(-0.9F, -0.2F), Abyssal.DEFAULT_PARAMETER, Abyssal.DEFAULT_PARAMETER, 0.2F, BiomeKeys.CRIMSON_FOREST);
    }
}
