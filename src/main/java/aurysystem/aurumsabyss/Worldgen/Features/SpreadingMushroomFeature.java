package aurysystem.aurumsabyss.Worldgen.Features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;
import java.util.function.Predicate;

public class SpreadingMushroomFeature extends Feature<SpreadingMushroomConfig> {

    public SpreadingMushroomFeature(Codec<SpreadingMushroomConfig> codec) {
        super(codec);
    }

    private static Predicate<BlockState> getReplaceablePredicate(SpreadingMushroomConfig config) {
        Tag<Block> tag = BlockTags.getTagGroup().getTag(config.replaceable());
        return tag == null ? state -> true : state -> state.isIn(tag);
    }

    protected void generateStem(WorldAccess world, Random random, BlockPos pos, SpreadingMushroomConfig config, int height, BlockPos.Mutable mutable) {
        for (int i = 0; i < height; ++i) {
            mutable.set(pos).move(Direction.UP, i);
            if (world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) continue;
            this.setBlockState(world, mutable, config.stemProvider().getBlockState(random, pos));
        }
    }

    protected void generateRoots(WorldAccess world, Random random, BlockPos pos, Predicate<BlockState> replaceable, SpreadingMushroomConfig config, BlockPos.Mutable mutable) {
        for (int i = 0; i < 2; ++i) {
            mutable.set(pos).move(Direction.DOWN, i+1);
            if (!replaceable.test(world.getBlockState(mutable))) continue;
            this.setBlockState(world, mutable, config.rootBlock());
        }
        for (int i = 0; i < 4; ++i) {
            rootBranch(world,random,pos,Direction.byId(2+i),replaceable,config,mutable);
        }
    }

    protected void rootBranch(WorldAccess world, Random random, BlockPos pos, Direction dir, Predicate<BlockState> replaceable, SpreadingMushroomConfig config, BlockPos.Mutable mutable) {
        int rootLength = config.rootLength().get(random);
        int finLength = rootLength;
        int airBlocks = random.nextInt(rootLength-2);
        for (int i = 0; i < rootLength; ++i) {
            mutable.set(pos).move(Direction.DOWN, 2).move(dir,i);
            if (!replaceable.test(world.getBlockState(mutable)) && !world.getBlockState(mutable).isAir()) {
                finLength = i+1;
                break;
            }else if(world.getBlockState(mutable).isAir()){
                if(airBlocks == 0){
                    finLength = i+1;
                    break;
                }
                airBlocks--;
            }
            this.setBlockState(world, mutable, config.rootBlock());
        }
        for (int i = 0; i < rootLength; ++i) {
            mutable.set(pos).move(Direction.DOWN, 2).move(dir,finLength).move(Direction.UP,i);
            if (world.getBlockState(mutable).isAir() || world.getBlockState(mutable).isIn(BlockTags.REPLACEABLE_PLANTS)){
                if(i == 0) mutable.move(Direction.UP);
                if(!(world.getBlockState(mutable.down()) == config.rootBlock())) {
                    if (!replaceable.test(world.getBlockState(mutable.down())) && !world.getBlockState(mutable.down()).isAir()) break;
                    this.setBlockState(world, mutable, config.rootBlock());
                }
                world.setBlockState(mutable, config.sapling(), Block.NOTIFY_LISTENERS);
                break;
            }else {
                if (!replaceable.test(world.getBlockState(mutable)) && !world.getBlockState(mutable).isAir()) continue;
                this.setBlockState(world, mutable, config.rootBlock());
            }
        }

    }

    protected boolean canGenerate(WorldAccess world, BlockPos pos, int height, BlockPos.Mutable mutable, SpreadingMushroomConfig config) {
        int i = pos.getY();
        if (i < world.getBottomY() + 1 || i + height + 1 >= world.getTopY()) {
            return false;
        }
        BlockState blockState = world.getBlockState(pos.down());
        if (!SpreadingMushroomFeature.isSoil(blockState)){ //&& !blockState.isIn(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return false;
        }
        for (int y = 0; y <= height; ++y) {
            int size = this.getCapSize(-1, -1, config.foliageRadius(), y);
            for (int x = -size; x <= size; ++x) {
                for (int z = -size; z <= size; ++z) {
                    BlockState blockState2 = world.getBlockState(mutable.set(pos, x, y, z));
                    if (blockState2.isAir()) continue;
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean generate(FeatureContext<SpreadingMushroomConfig> context) {
        BlockPos.Mutable mutable;
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        SpreadingMushroomConfig config = context.getConfig();
        Predicate<BlockState> predicate = SpreadingMushroomFeature.getReplaceablePredicate(config);
        int i = config.height().get(random);
        if (!this.canGenerate(structureWorldAccess, blockPos, i, mutable = new BlockPos.Mutable(), config)) {
            return false;
        }
        this.generateCap(structureWorldAccess, random, blockPos, i, mutable, config);
        this.generateStem(structureWorldAccess, random, blockPos, config, i, mutable);
        this.generateRoots(structureWorldAccess,random,blockPos,predicate,config,mutable);
        return true;
    }

    protected void generateCap(WorldAccess world, Random random, BlockPos start, int y, BlockPos.Mutable mutable, SpreadingMushroomConfig config) {
        for (int i = y - 3; i <= y; ++i) {
            int size = i < y ? config.foliageRadius() : config.foliageRadius() - 1;
            int k = config.foliageRadius() - 2;
            for (int x = -size; x <= size; ++x) {
                for (int z = -size; z <= size; ++z) {
                    boolean bl = x == -size;
                    boolean bl2 = x == size;
                    boolean bl3 = z == -size;
                    boolean bl4 = z == size;
                    boolean bl5 = bl || bl2;
                    boolean bl6 = bl3 || bl4;
                    if (i < y && bl5 == bl6) continue;
                    mutable.set(start, x, i, z);
                    if (world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) continue;
                    BlockState blockState = config.capProvider().getBlockState(random, start);
                    if (blockState.contains(MushroomBlock.WEST) && blockState.contains(MushroomBlock.EAST) && blockState.contains(MushroomBlock.NORTH) && blockState.contains(MushroomBlock.SOUTH) && blockState.contains(MushroomBlock.UP)) {
                        blockState = blockState.with(MushroomBlock.UP, i >= y - 1).with(MushroomBlock.WEST, x < -k).with(MushroomBlock.EAST, x > k).with(MushroomBlock.NORTH, z < -k).with(MushroomBlock.SOUTH, z > k);
                    }
                    this.setBlockState(world, mutable, blockState);
                }
            }
        }
    }

    protected int getCapSize(int i, int j, int capSize, int y) {
        int k = 0;
        if (y < j && y >= j - 3) {
            k = capSize;
        } else if (y == j) {
            k = capSize;
        }
        return k;
    }
}
