package aurysystem.aurumsabyss.Items;

import aurysystem.aurumsabyss.Abyssal;
import aurysystem.aurumsabyss.Blocks.AbyssalBlocks;
import aurysystem.aurumsabyss.Blocks.SaplingExtention;
import aurysystem.aurumsabyss.Blocks.SpreadingSaplingGen;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;


public class AbyssalItems {

    public static final Item PLOZAC_BLOCK = registerItem("plozac_block", new BlockItem(AbyssalBlocks.PLOZAC_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));

    public static final Item SPREAD_SAPLING = registerItem("spreading_sapling", new BlockItem(AbyssalBlocks.SPREAD_SAPLING, new FabricItemSettings().group(ItemGroup.DECORATIONS)));

    protected static Item registerItem(String name, Item item){
        return Registry.register(Registry.ITEM, Abyssal.ID(name), item);
    }

    public static void init(){

    }
}
