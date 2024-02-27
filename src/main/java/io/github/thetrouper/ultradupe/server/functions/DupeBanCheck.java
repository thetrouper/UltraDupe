package io.github.thetrouper.ultradupe.server.functions;

import io.github.thetrouper.ultradupe.UltraDupe;
import io.github.thetrouper.ultradupe.data.DupeBanStorage;
import io.github.thetrouper.ultradupe.server.util.ServerUtils;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class DupeBanCheck {
    private boolean isContainer(ItemStack itemStack) {
        return itemStack.getType() == Material.CHEST ||
                itemStack.getType() == Material.TRAPPED_CHEST ||
                itemStack.getType() == Material.FURNACE ||
                itemStack.getType() == Material.BLAST_FURNACE ||
                itemStack.getType() == Material.DROPPER ||
                itemStack.getType() == Material.DISPENSER ||
                itemStack.getType() == Material.HOPPER ||
                itemStack.getType() == Material.BARREL;
    }

    private Inventory getSubInventory(ItemStack containerItem) {
        ServerUtils.verbose("DUPEBANS: GetSubInv checking item: " + containerItem);
        if (containerItem.getItemMeta() instanceof BlockStateMeta blockStateMeta) {
            ServerUtils.verbose("DUPEBANS: subInv has (is) blockStateMeta: " + blockStateMeta);
            BlockState blockState = blockStateMeta.getBlockState();
            if (blockState instanceof Container) {
                ServerUtils.verbose("DUPEBANS: subInv has (is) container: " + (Container) blockState);
                return ((Container) blockState).getInventory();
            }
        }
        ServerUtils.verbose("DUPEBANS: Inv is null: " + containerItem);
        return null;
    }

    private boolean containerPasses(Inventory inventory) {
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack == null || itemStack.getType().isAir()) continue;
            if (!itemPasses(itemStack)) {
                ServerUtils.verbose("DUPEBANS: No pass C(I)");
                return false;
            }
            if (!isContainer(itemStack)) continue;

            Inventory subInventory = getSubInventory(itemStack);
            if (!containerPasses(subInventory)) {
                ServerUtils.verbose("DUPEBANS: No pass C(R)");
                return false;
            }
        }
        ServerUtils.verbose("DUPEBANS: Item passes recursion check.");
        return true;
    }
    
    public boolean itemPasses(ItemStack i) {
        ServerUtils.verbose("DUPEBANS: Checking if item passes: " + i.getItemMeta());
        Inventory inv = getSubInventory(i);
        if (inv != null) {
            ServerUtils.verbose("DUPEBANS: Item has a SubInv: " + inv);
            if (!containerPasses(inv)) {
                ServerUtils.verbose("DUPEBANS: No pass C");
                return false;
            }
        }
        if (UltraDupe.dupeBanStorage.dupeBans.contains(i.getType())) {
            ServerUtils.verbose("DUPEBANS: No pass T");
            return false;
        }
        if (i.hasItemMeta() && i.getItemMeta().hasCustomModelData() && i.getItemMeta().getCustomModelData() == UltraDupe.dupeBanStorage.bannedModelData) {
            ServerUtils.verbose("DUPEBANS: No pass M");
            return false;
        }

        ServerUtils.verbose("DUPEBANS: All checks passed");
        return true;
    }
}
