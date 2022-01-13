package aurysystem.aurumsabyss.mixin;

import aurysystem.aurumsabyss.Abyssal;
import aurysystem.aurumsabyss.Worldgen.AbyssalBiomes;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(VanillaSurfaceRules.class)
public class VanillaSurfaceRulesMixin {

    @ModifyVariable(method = "createDefaultRule", at = @At("STORE"), ordinal = 0)
    private static ImmutableList.Builder<MaterialRules.MaterialRule> injected(ImmutableList.Builder<MaterialRules.MaterialRule> builder) {
        MaterialRules.MaterialRule nether = MaterialRules.sequence(new MaterialRules.MaterialRule[]{
                MaterialRules.condition(MaterialRules.biome(BiomeKeys.CRIMSON_FOREST),
                                MaterialRules.condition(MaterialRules.aboveY(YOffset.aboveBottom(5), 0),
                                        MaterialRules.sequence(new MaterialRules.MaterialRule[]{
                                                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR,MaterialRules.block(Blocks.CRIMSON_NYLIUM.getDefaultState())),
                                                MaterialRules.block(Blocks.NETHERRACK.getDefaultState())
                                        }))

                        )
        });
        MaterialRules.MaterialRule overworld = MaterialRules.sequence(new MaterialRules.MaterialRule[]{
                MaterialRules.condition(MaterialRules.biome(AbyssalBiomes.WeirdCave),
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR,MaterialRules.block(Blocks.PODZOL.getDefaultState())))
        });
        builder.add(MaterialRules.sequence(new MaterialRules.MaterialRule[]{nether,overworld}));

        return builder;
    }
}