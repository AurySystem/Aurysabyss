package aurysystem.aurumsabyss.mixin;

import aurysystem.aurumsabyss.Abyssal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Mixin(TropicalFishEntity.class)
public class TropicalFishEntityMixin {

    @Inject(at = @At("RETURN"), method = "canTropicalFishSpawn", cancellable = true)
    private static void inject(EntityType<TropicalFishEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() || (world.getFluidState(pos.down()).isIn(FluidTags.WATER) && (Objects.equals(world.getBiomeKey(pos), Optional.of(Abyssal.WeirdCave)))));
    }
}
