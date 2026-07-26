package io.github.rcneg.legendarydelicacies.init;

import io.github.rcneg.legendarydelicacies.LegendaryDelicacies;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LegendaryDelicacies.MODID);
    private static final List<Supplier<Item>> NO_TAB_ITEMS = Arrays.asList(
            ItemRegistry.FLYING_FOOD,
            ItemRegistry.ANNIHILATION_BOX_BLOCK,
            ItemRegistry.ANNIHILATION_BOX_BLOCK_TOP
    );

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register(LegendaryDelicacies.MODID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.legendary_delicacies"))
            .icon(ItemRegistry.HAUNTED_KNIGHT_STEAK.get()::getDefaultInstance)
            .displayItems((parameters, output) -> {
                for(RegistryObject<Item> item: ItemRegistry.ITEMS.getEntries()){
                    if(!NO_TAB_ITEMS.contains(item)) output.accept(item.get());
                }
            }).build());
}
