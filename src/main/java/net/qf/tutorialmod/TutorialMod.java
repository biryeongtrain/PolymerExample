package net.qf.tutorialmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.qf.tutorialmod.item.TestPolymerItem;

public class TutorialMod implements ModInitializer {
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM,
                new Identifier("test", "test_item"),
                new TestPolymerItem(new Item.Settings().fireproof())
        );
    }
}
