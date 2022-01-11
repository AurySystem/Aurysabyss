package aurysystem.aurumsabyss.Blocks;

import aurysystem.aurumsabyss.Abyssal;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AbyssalBlocks {

    public static final Block PLOZAC_BLOCK = AbyssalBlocks.register("plozac_block", new Block(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.LICHEN_GREEN).strength(1.0f).sounds(BlockSoundGroup.FUNGUS).luminance(state -> 8)));

    public static void init(){

    }

    private static Block register(String id, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(Abyssal.MOD_ID,id), block);
    }
}
