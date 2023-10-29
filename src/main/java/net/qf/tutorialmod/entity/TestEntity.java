package net.qf.tutorialmod.entity;

import eu.pb4.polymer.core.api.entity.PolymerEntity;
import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.VirtualEntityUtils;
import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment;
import eu.pb4.polymer.virtualentity.api.elements.InteractionElement;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import eu.pb4.polymer.virtualentity.api.elements.MobAnchorElement;
import eu.pb4.polymer.virtualentity.api.tracker.EntityTrackedData;
import eu.pb4.polymer.virtualentity.mixin.accessors.EntityAccessor;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.qf.tutorialmod.TutorialMod;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.item.Items.WHITE_DYE;

public class TestEntity extends IronGolemEntity implements PolymerEntity {
    private final InteractionElement hitbox = InteractionElement.redirect(this);
    @NotNull
    private final TestHolder holder;
    private final MobAnchorElement rideAnchor;
    private final EntityAttachment attachment;
    public TestEntity(EntityType<? extends TestEntity> type, World world) {
        super(type, world);
        this.holder = new TestHolder();
        this.rideAnchor = new MobAnchorElement();
        this.attachment = new EntityAttachment(this.holder, this, true);
        rideAnchor.setHolder(this.holder);

        VirtualEntityUtils.addVirtualPassenger(this, rideAnchor.getEntityId(), holder.BODY.getEntityId());
        this.holder.addElement(hitbox);
    }

    @Override
    public EntityType<?> getPolymerEntityType(ServerPlayerEntity player) {
        return EntityType.ARMOR_STAND;
    }

    @Override
    public void modifyRawTrackedData(List<DataTracker.SerializedEntry<?>> data, ServerPlayerEntity player, boolean initial) {
        data.add(DataTracker.SerializedEntry.of(
                EntityTrackedData.FLAGS, (byte) (1 << EntityTrackedData.INVISIBLE_FLAG_INDEX))
        );
        data.add(new DataTracker.SerializedEntry(
                EntityAccessor.getNO_GRAVITY().getId(), EntityAccessor.getNO_GRAVITY().getType(), true)
        );
        data.add(DataTracker.SerializedEntry.of(
                ArmorStandEntity.ARMOR_STAND_FLAGS, (byte) (ArmorStandEntity.SMALL_FLAG | ArmorStandEntity.MARKER_FLAG))
        );

    }

    @Override
    public void onEntityPacketSent(Consumer<Packet<?>> consumer, Packet<?> packet) {
        if (packet instanceof EntityPassengersSetS2CPacket passengersSetS2CPacket) {
            TestHolder model = this.holder;
            IntList list = new IntArrayList(passengersSetS2CPacket.getPassengerIds());
            list.addAll(IntList.of(this.hitbox.getEntityId(), holder.BODY.getEntityId(), rideAnchor.getEntityId()
            ));
            consumer.accept(VirtualEntityUtils.createRidePacket(this.rideAnchor.getEntityId(), list));
            return;
        }

        consumer.accept(packet);
    }

    public class TestHolder extends ElementHolder {
        public static ItemStack BODY_ITEM = new ItemStack(WHITE_DYE);

        static {
            BODY_ITEM.getOrCreateNbt().putInt(
                "CustomModelData", PolymerResourcePackUtils.requestModel(WHITE_DYE, new Identifier(TutorialMod.MOD_ID, "item/test_body")).value()
            );
        }

        public final ItemDisplayElement BODY;
        public TestHolder() {
            this.BODY = this.addElement(new ItemDisplayElement(BODY_ITEM));
        }
    }
}
