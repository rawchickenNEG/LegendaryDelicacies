package io.github.rcneg.legendarydelicacies.tags;

import io.github.rcneg.legendarydelicacies.LegendaryDelicacies;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class LMDTags {
    public static final TagKey<Item> BOSS_FOODS = modItemTag("boss_foods");
    public static final TagKey<Item> CAN_BE_POSSESSED = modItemTag("can_be_possessed");
    public static final TagKey<Block> PRIMITIVE_HEAT_SOURCES = modBlockTag("primitive_heat_sources");

    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(new ResourceLocation(LegendaryDelicacies.MODID, path));
    }
    private static TagKey<Block> modBlockTag(String path) {
        return BlockTags.create(new ResourceLocation(LegendaryDelicacies.MODID, path));
    }
}
