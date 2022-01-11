package aurysystem.aurumsabyss.Items;

import aurysystem.aurumsabyss.Blocks.AbyssalBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static aurysystem.aurumsabyss.Abyssal.MOD_ID;

public class AbyssalItems {

    public static final Item PLOZAC_BLOCK = registerItem("plozac_block", new BlockItem(AbyssalBlocks.PLOZAC_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));

    protected static Item registerItem(String name, Item item){
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), item);
        return item;
    }

    public static void init(){

    }
}
