package net.qf.tutorialmod.mixin;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.qf.tutorialmod.duck.ServerPlayerEntityDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements ServerPlayerEntityDuck {
    @Unique
    private int testMod$testVar = 0;
    @Unique
    private final String TEST_ID = "test_date_id";

    @Override
    @Unique
    public int testMod$getTest() {
        return this.testMod$testVar;
    }

    @Override
    @Unique
    public void testMod$setTest(int val) {
        this.testMod$testVar = val;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void testMod$writeCustomData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt(TEST_ID, this.testMod$testVar);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void testMod$readCustomData(NbtCompound nbt, CallbackInfo ci) {
        this.testMod$testVar = nbt.getInt(TEST_ID);
    }
}
