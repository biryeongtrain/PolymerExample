package net.qf.tutorialmod.registry;

import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.qf.tutorialmod.TutorialMod;
import net.qf.tutorialmod.entity.TestEntity;

public class EntityRegistry {
    public static final EntityType<TestEntity> TEST_ENTITY_TYPE = registerEntity(
            "test_entity", EntityType.Builder.<TestEntity>create(TestEntity::new, SpawnGroup.MISC));


    private static <T extends Entity> EntityType<T> registerEntity(String id, EntityType.Builder<T> type) {
        var built = type.build(id);
        Registry.register(Registries.ENTITY_TYPE, new Identifier(TutorialMod.MOD_ID, id), built);
        PolymerEntityUtils.registerType(built);

        return built;
    }
    public static void init() {
        FabricDefaultAttributeRegistry.register(TEST_ENTITY_TYPE, IronGolemEntity.createIronGolemAttributes());
    }
}
