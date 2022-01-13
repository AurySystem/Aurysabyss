package aurysystem.aurumsabyss.Blocks;

import aurysystem.aurumsabyss.Abyssal;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public class AbyssalBlocks {

    public static final Block PLOZAC_BLOCK = registerBlock("plozac_block", new Block(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.LICHEN_GREEN).strength(1.0f).sounds(BlockSoundGroup.FUNGUS).luminance(state -> 8)));

    public static final Block SPREAD_SAPLING = registerBlock("spreading_sapling", new SaplingExtention(new SpreadingSaplingGen(),FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.LICHEN_GREEN).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)));

    public static void init(){

    }

    private static Block registerBlock(String id, Block block) {
        return Registry.register(Registry.BLOCK, Abyssal.ID(id), block);
    }
}
