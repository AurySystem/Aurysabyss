package aurysystem.aurumsabyss.Worldgen.Features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record SpreadingMushroomConfig(BlockStateProvider capProvider,
                                      BlockStateProvider stemProvider,
                                      BlockState rootBlock,
                                      BlockState sapling,
                                      int foliageRadius,
                                      int size,
                                      IntProvider rootLength,
                                      IntProvider height,
                                      Identifier replaceable) implements FeatureConfig {
    public static final Codec<SpreadingMushroomConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    BlockStateProvider.TYPE_CODEC.fieldOf("cap_provider").forGetter(SpreadingMushroomConfig::capProvider),
                    BlockStateProvider.TYPE_CODEC.fieldOf("stem_provider").forGetter(SpreadingMushroomConfig::stemProvider),
                    BlockState.CODEC.fieldOf("root_block").forGetter(SpreadingMushroomConfig::rootBlock),
                    BlockState.CODEC.fieldOf("sapling").forGetter(SpreadingMushroomConfig::sapling),
                    Codec.INT.fieldOf("foliage_radius").orElse(2).forGetter(SpreadingMushroomConfig::foliageRadius),
                    Codec.INT.fieldOf("size").orElse(1).forGetter(SpreadingMushroomConfig::size),
                    IntProvider.VALUE_CODEC.fieldOf("root_length").forGetter(SpreadingMushroomConfig::rootLength),
                    IntProvider.VALUE_CODEC.fieldOf("height").forGetter(SpreadingMushroomConfig::height),
                    Identifier.CODEC.fieldOf("replaceable").forGetter(SpreadingMushroomConfig::replaceable)
            ).apply(instance, SpreadingMushroomConfig::new));
}
