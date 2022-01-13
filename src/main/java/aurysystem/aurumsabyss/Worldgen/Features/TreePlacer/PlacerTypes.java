package aurysystem.aurumsabyss.Worldgen.Features.TreePlacer;

import aurysystem.aurumsabyss.mixin.FoliagePlacerInvoker;
import aurysystem.aurumsabyss.mixin.TrunkPlacerInvoker;
import com.mojang.serialization.Codec;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import static aurysystem.aurumsabyss.Abyssal.ID;

public class PlacerTypes {
    public static final TrunkPlacerType<BranchyTrunkPlacer> BRANCHY_TRUNK_PLACER = registerTrunk("branchy_trunk_placer", BranchyTrunkPlacer.CODEC);

    public static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunk(String id, Codec<P> codec){
        return TrunkPlacerInvoker.callRegister(ID(id).toString(),codec);
    }
    public static <P extends FoliagePlacer> FoliagePlacerType<P> registerFoliage(String id, Codec<P> codec){
        return FoliagePlacerInvoker.callRegister(ID(id).toString(),codec);
    }
}
