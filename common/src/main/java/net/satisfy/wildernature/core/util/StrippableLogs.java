package net.satisfy.wildernature.core.util;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

/** Log pairs used by beavers, including wood types added after the original mod. */
public final class StrippableLogs {
    private static Map<Block, Block> blocks;

    private StrippableLogs() {}

    public static Map<Block, Block> blocks() {
        if (blocks == null) {
            Map<Block, Block> pairs = new LinkedHashMap<>();
            for (Block block : BuiltInRegistries.BLOCK) {
                Identifier id = BuiltInRegistries.BLOCK.getKey(block);
                if (id.getPath().startsWith("stripped_")) continue;
                Identifier stripped = Identifier.fromNamespaceAndPath(id.getNamespace(), "stripped_" + id.getPath());
                BuiltInRegistries.BLOCK.getOptional(stripped).ifPresent(target -> pairs.put(block, target));
            }
            blocks = Map.copyOf(pairs);
        }
        return blocks;
    }
}
