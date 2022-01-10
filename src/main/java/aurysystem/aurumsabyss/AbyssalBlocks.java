package aurysystem.aurumsabyss;

import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public class AbyssalBlocks {

    public static Block PLOZAC;

    public static void init(){
        PLOZAC = AbyssalBlocks.register("plozac", new Block(AbstractBlock.Settings.of(Material.SOLID_ORGANIC, MapColor.LICHEN_GREEN).strength(1.0f).sounds(BlockSoundGroup.FUNGUS).luminance(state -> 8)));

    }

    private static Block register(String id, Block block) {
        return Registry.register(Registry.BLOCK, Abyssal.MOD_ID+":"+id, block);
    }
}
