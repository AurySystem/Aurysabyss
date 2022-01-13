package aurysystem.aurumsabyss.Blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class SaplingExtention extends SaplingBlock {
    public SaplingExtention(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
    }


    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(pos.up()) >= 1 && random.nextInt(2) == 0) {
            this.generate(world, pos, state, random);
        }
    }
}

