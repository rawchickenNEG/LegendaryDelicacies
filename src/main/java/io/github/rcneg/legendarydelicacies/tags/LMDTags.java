package io.github.rcneg.legendarydelicacies.tags;

import io.github.rcneg.legendarydelicacies.LegendaryDelicacies;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class LMDTags {
    public static final TagKey<Item> BOSS_FOODS = modItemTag("boss_foods");
    public static final TagKey<Item> CAN_BE_POSSESSED = modItemTag("can_be_possessed");

    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(new ResourceLocation(LegendaryDelicacies.MODID, path));
    }
}
