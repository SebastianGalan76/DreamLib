package pl.dream.dreamlib;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Equipment {
    /**
     * Adds items to the player's inventory or drops items if the
     * player does not have enough space.
     *
     * @param player The player to whom we give items
     * @param item   An item to be added
     * @return True if some items dropped on the ground
     */
    public static boolean addItems(Player player, ItemStack item) {
        return addItems(player, Collections.singletonList(item));
    }

    /**
     * Adds items to the player's inventory or drops items if the
     * player does not have enough space.
     *
     * @param player The player to whom we give items
     * @param items  A List of items to be added
     * @return True if some items dropped on the ground
     */
    public static boolean addItems(Player player, List<ItemStack> items) {
        World world = player.getWorld();
        Location loc = player.getLocation();
        PlayerInventory inventory = player.getInventory();

        boolean droppedItems = false;
        for (ItemStack item : items) {
            int maxStackSize = item.getType().getMaxStackSize();
            int itemCount = item.getAmount();

            ItemStack itemCloned = item.clone();

            while (itemCount > 0) {
                int toAdd = Math.min(itemCount, maxStackSize);
                itemCloned.setAmount(toAdd);

                if (!equipArmor(player, item)) {
                    HashMap<Integer, ItemStack> itemsToDrop = inventory.addItem(itemCloned);
                    if (!itemsToDrop.isEmpty()) {
                        droppedItems = true;
                        for (ItemStack itemToDrop : itemsToDrop.values()) {
                            world.dropItem(loc, itemToDrop);
                        }
                    }
                }

                itemCount -= toAdd;
            }
        }

        return droppedItems;
    }

    public static void takeItems(Player player, ItemStack item) {
        takeItems(player, Collections.singletonList(item));
    }

    /**
     * Takes items from the player's inventory.
     *
     * @param player The player whose items we will take
     * @param items  A List of items to be taken
     */
    public static void takeItems(Player player, List<ItemStack> items) {
        PlayerInventory inventory = player.getInventory();

        for (ItemStack item : items) {
            int remainingAmount = item.getAmount();

            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack itemStack = inventory.getItem(i);

                if (itemStack != null && itemStack.isSimilar(item)) {
                    int stackAmount = itemStack.getAmount();

                    if (stackAmount <= remainingAmount) {
                        remainingAmount -= stackAmount;
                        inventory.setItem(i, new ItemStack(Material.AIR));

                        if (remainingAmount == 0) {
                            break;
                        }
                    } else {
                        itemStack.setAmount(stackAmount - remainingAmount);
                        break;
                    }
                }
            }
        }

        player.updateInventory();
    }

    public static boolean hasEnoughItems(Player player, ItemStack item) {
        return getItemAmount(player, item) >= item.getAmount();
    }

    public static boolean hasEnoughItems(Player player, List<ItemStack> items) {
        for (ItemStack item : items) {
            if (getItemAmount(player, item) < item.getAmount()) {
                return false;
            }
        }

        return true;
    }

    public static boolean hasEnoughSpace(Player player, int space) {
        int amount = 0;
        for (ItemStack itemStack : player.getInventory().getStorageContents()) {
            if (itemStack == null) {
                amount++;

                if(amount >= space){
                    return true;
                }
            }
        }

        return amount >= space;
    }

    public static int getItemAmount(Player player, @Nullable ItemStack item) {
        int amount = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (item == null && itemStack == null) {
                amount++;
            } else {
                if (itemStack != null && itemStack.isSimilar(item)) {
                    amount += itemStack.getAmount();
                }
            }
        }

        return amount;
    }

    public static List<ItemStack> convertToMaxItemStack(List<ItemStack> items) {
        List<ItemStack> convertedItems = new ArrayList<>();

        for (ItemStack item : items) {
            int maxStack = item.getType().getMaxStackSize();
            int itemAmount = item.getAmount();

            if (itemAmount <= maxStack) {
                convertedItems.add(item);
            } else {
                while (itemAmount != 0) {
                    int amount = Math.min(itemAmount, maxStack);

                    ItemStack convertedItem = item.clone();
                    convertedItem.setAmount(amount);
                    convertedItems.add(convertedItem);
                    itemAmount -= amount;
                }
            }
        }

        return convertedItems;
    }

    public static boolean equipArmor(Player p, ItemStack item) {
        PlayerInventory inv = p.getInventory();
        if (item.getType().name().contains("HELMET")) {
            if (inv.getHelmet() == null) {
                inv.setHelmet(item);
                return true;
            }
        } else if (item.getType().name().contains("CHESTPLATE")) {
            if (inv.getChestplate() == null) {
                inv.setChestplate(item);
                return true;
            }
        } else if (item.getType().name().contains("LEGGINGS")) {
            if (inv.getLeggings() == null) {
                inv.setLeggings(item);
                return true;
            }
        } else if (item.getType().name().contains("BOOTS")) {
            if (inv.getBoots() == null) {
                inv.setBoots(item);
                return true;
            }
        }

        return false;
    }
}
