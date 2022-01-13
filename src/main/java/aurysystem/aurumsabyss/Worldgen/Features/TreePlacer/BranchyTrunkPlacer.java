package aurysystem.aurumsabyss.Worldgen.Features.TreePlacer;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class BranchyTrunkPlacer extends TrunkPlacer {
    public static final Codec<BranchyTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> BranchyTrunkPlacer.fillTrunkPlacerFields(instance).apply(instance, BranchyTrunkPlacer::new));


    public BranchyTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return PlacerTypes.BRANCHY_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        ArrayList<FoliagePlacer.TreeNode> list = Lists.newArrayList();
        BlockPos blockPos = startPos.down();
        int x = startPos.getX();
        int y = startPos.getY();
        int z = startPos.getZ();
        int x1 = x;
        int z1 = z;
        int y1 = y + height;
        float rad = 0.6f;
        BranchyTrunkPlacer.setToDirt(world, replacer, random, blockPos, config);
        for(int x2 = -MathHelper.floor(rad); x2 <= MathHelper.ceil(rad); x2++){
            for(int z2 = -MathHelper.floor(rad); z2 <= MathHelper.ceil(rad); z2++){
                BranchyTrunkPlacer.setToDirt(world, replacer, random, blockPos.add(x2,0,z2), config);
            }
        }
        list.addAll(generateBranch(0,height,1,new BlockPos(y, y1+1, z),world,replacer,random,startPos,config,list,rad));
        list.addAll(generateBranch(6,height,1.2f,new BlockPos(x+6, y1+1, z+6),world,replacer,random,startPos,config,list,rad));


        return list;
    }
    private List<FoliagePlacer.TreeNode> generateBranch(int startHeight,int height, float slope, BlockPos target, TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos startPos, TreeFeatureConfig config, List<FoliagePlacer.TreeNode> list, float rad) {
        for (int heightI = startHeight; heightI < height; ++heightI) {
            int x = startPos.getX();
            int y = startPos.getY();
            int z = startPos.getZ();
            int x1 = x;
            int z1 = z;
            int y2 = target.getY()+1;
            int x2 = target.getX();
            int z2 = target.getZ();

            BlockPos blockPos2;
            x1 = MathHelper.ceil(x2+(x2-x1)/slope);
            z1 = MathHelper.ceil(z2+(z2-z1)/slope);
            if (!TreeFeature.isAirOrLeaves(world, blockPos2 = new BlockPos(x1, y + heightI, z1))) continue;
            BranchyTrunkPlacer.getAndSetState(world, replacer, random, blockPos2, config);
            for (int x3 = -MathHelper.floor(rad); x3 <= MathHelper.ceil(rad); x3++) {
                for (int z3 = -MathHelper.floor(rad); z3 <= MathHelper.ceil(rad); z3++) {
                    BranchyTrunkPlacer.getAndSetState(world, replacer, random, blockPos2.add(x3, 0, z3), config);
                }
            }
            list.add(new FoliagePlacer.TreeNode(target.up(), 0, rad >= 0.4f));
        }
        return list;
    }
}
