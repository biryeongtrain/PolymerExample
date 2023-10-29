package net.qf.tutorialmod;

import eu.pb4.polymer.core.api.entity.PolymerEntityUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.qf.tutorialmod.item.TestPolymerItem;
import net.qf.tutorialmod.registry.EntityRegistry;

public class TutorialMod implements ModInitializer {
    /**
     * Runs the mod initializer.
     */

    public static String MOD_ID = "test";
    @Override
    public void onInitialize() {
        PolymerResourcePackUtils.addModAssets(MOD_ID);
        Registry.register(Registries.ITEM,
                new Identifier("test", "test_item"),
                new TestPolymerItem(new Item.Settings().fireproof())
        );
        EntityRegistry.init();
    }

}
