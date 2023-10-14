package net.qf.tutorialmod.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.utils.PolymerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.qf.tutorialmod.duck.ServerPlayerEntityDuck;
import org.jetbrains.annotations.Nullable;

public class TestPolymerItem extends Item implements PolymerItem {
    public TestPolymerItem(Settings settings) {
        super(settings);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        if (player == null) {
            return Items.DIAMOND;
        }

        ServerPlayerEntityDuck duck = (ServerPlayerEntityDuck) player;
        int value = duck.testMod$getTest();
        return value == 10 ? Items.COAL : Items.DIAMOND;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var result = super.use(world, user, hand);

        if (world.isClient) {
            return result;
        }

        user.sendMessage(Text.literal("use event!"), false);
        if (user instanceof ServerPlayerEntity player) {
            ServerPlayerEntityDuck duck = (ServerPlayerEntityDuck) player;

            if (duck.testMod$getTest() == 0) {
                duck.testMod$setTest(10);
                player.sendMessage(Text.literal("var changed : now var is " + duck.testMod$getTest()));
            } else {
                duck.testMod$setTest(0);
                player.sendMessage(Text.literal("var changed : now var is " + duck.testMod$getTest()));
            }
            PolymerUtils.reloadInventory(player);
        }

        return TypedActionResult.success(result.getValue());
    }


}
