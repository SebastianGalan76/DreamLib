package pl.dream.dreamlib;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config {

    /**
     * Retrieves a ItemStack from the specified configuration using the provided path
     *
     * @param config The config from which to retrieve the ItemStack
     * @param path   The path in the configuration where the ItemStack is located
     * @return ItemStack retrieved from the config, or null if there is an error in the config
     */
    public static @Nullable ItemStack getItemStack(@NotNull FileConfiguration config, @NotNull String path) {
        //Loading material
        Material material = getMaterial(config, path + ".material");
        if (material == null) {
            return null;
        }

        //Loading amount of item
        int amount = config.getInt(path + ".amount", 1);
        if (amount <= 0) {
            return null;
        }

        //Create ItemStack and ItemMeta
        ItemStack item = new ItemStack(material, amount);

        String rgbColor = config.getString(path + ".rgbColor");
        if (rgbColor != null) {
            String[] colors = rgbColor.split(";");
            if (colors.length == 3) {
                try {
                    int r = Integer.parseInt(colors[0]);
                    int g = Integer.parseInt(colors[1]);
                    int b = Integer.parseInt(colors[2]);

                    colorItem(item, r, g, b);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        ItemMeta itemMeta = item.getItemMeta();

        //Loading item name
        String name = config.getString(path + ".name");
        if (name != null) {
            itemMeta.setDisplayName(Color.fixAll(name));
        }

        //Loading lore
        if (config.getString(path + ".lore") != null) {
            List<String> lore = Color.fixAll(config.getStringList(path + ".lore"));
            itemMeta.setLore(lore);
        }

        //Loading glowing
        boolean glowing = config.getBoolean(path + ".glowing", false);
        if (glowing) {
            itemMeta.addEnchant(Enchantment.UNBREAKING, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        //Loading enchantments
        HashMap<Enchantment, Integer> enchantments = getEnchantments(config, path + ".enchant");
        if (enchantments != null && !enchantments.isEmpty()) {
            enchantments.forEach((enchant, level) -> {
                itemMeta.addEnchant(enchant, level, true);
            });
        }

        //Loading item flags
        List<ItemFlag> itemFlagList = getItemFlags(config, path + ".itemFlag");
        if (itemFlagList != null && !itemFlagList.isEmpty()) {
            itemFlagList.forEach((itemFlag) -> {
                itemMeta.addItemFlags(itemFlag);
            });
        }

        //Loading potion effect
        PotionType potionType = getPotionType(config, path + ".potionType");
        if (potionType != null) {
            if (itemMeta instanceof PotionMeta potionMeta) {
                potionMeta.setBasePotionType(potionType);
            }
        }

        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Retrieves a Material from the specified configuration using the provided path
     *
     * @param config       The config from which to retrieve the material
     * @param materialPath The path in the configuration where the material is located
     * @return Material retrieved from the config, or null if the material is invalid
     */
    public static @Nullable Material getMaterial(@NotNull FileConfiguration config, String materialPath) {
        String materialString = config.getString(materialPath);
        if (materialString == null) {
            return null;
        }

        Material material;
        try {
            material = Material.valueOf(materialString);
        } catch (IllegalArgumentException e) {
            Bukkit.getLogger().warning("Incorrect material: " + materialPath + "\n" + e.getMessage());
            return null;
        }

        return material;
    }

    /**
     * Retrieves a map of enchantments from the specified configuration using the provided path
     *
     * @param config       The config from which to retrieve the enchantments
     * @param enchantsPath The path in the configuration where the enchantments are located
     * @return HashMap containing Enchantment objects mapped to their respective levels, or null if no enchantments are found
     */
    public static @Nullable HashMap<Enchantment, Integer> getEnchantments(@NotNull FileConfiguration config, String enchantsPath) {
        HashMap<Enchantment, Integer> enchantments = new HashMap<>();

        List<String> enchantsStringList = config.getStringList(enchantsPath);
        if (enchantsStringList.isEmpty()) {
            return null;
        }

        for (String enchant : enchantsStringList) {
            String[] parts = enchant.split(":");

            String enchantName = parts[0];
            int enchantLevel = Integer.parseInt(parts[1]);

            if (enchantLevel <= 0) {
                Bukkit.getLogger().warning("Incorrect enchant level: " + enchantsPath + " X: " + enchantLevel);
                continue;
            }

            Enchantment enchantment = Enchantment.getByName(enchantName);
            if (enchantment == null) {
                Bukkit.getLogger().warning("Incorrect enchant name: " + enchantsPath + " X: " + enchantName);
                continue;
            }

            enchantments.put(enchantment, enchantLevel);
        }

        return enchantments;
    }

    /**
     * Retrieves a list of ItemFlags from the specified configuration using the provided path
     *
     * @param config       The config from which to retrieve the enchantments
     * @param itemFlagPath The path in the configuration where the ItemFlags are located
     * @return List of materials retrieved from the config, or null if no item flags are found
     */
    public static @Nullable List<ItemFlag> getItemFlags(@NotNull FileConfiguration config, String itemFlagPath) {
        List<ItemFlag> itemFlagList = new ArrayList<>();

        List<String> itemFlagStringList = config.getStringList(itemFlagPath);
        if (itemFlagStringList.isEmpty()) {
            return null;
        }

        for (String itemFlagString : itemFlagStringList) {
            ItemFlag itemFlag;

            try {
                itemFlag = ItemFlag.valueOf(itemFlagString);
            } catch (IllegalArgumentException e) {
                Bukkit.getLogger().warning("Incorrect item flag: " + itemFlagPath + "\n" + e.getMessage());
                continue;
            }

            itemFlagList.add(itemFlag);
        }

        return itemFlagList;
    }

    /**
     * Retrieves Location from the specified configuration using the provided path
     *
     * @param config       The config from which to retrieve the location
     * @param locationPath The path in the configuration where the Location is located
     * @return Location retrieved from the config, or null if the location is not saved
     */
    public static @Nullable Location getLocation(@NotNull FileConfiguration config, String locationPath) {
        if (config.get(locationPath) == null) {
            return null;
        }

        String worldName = config.getString(locationPath + ".world");
        if (worldName == null) {
            return null;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return null;
        }

        double x = config.getDouble(locationPath + ".x");
        double y = config.getDouble(locationPath + ".y");
        double z = config.getDouble(locationPath + ".z");
        float yaw = (float) config.getDouble(locationPath + ".yaw");
        float pitch = (float) config.getDouble(locationPath + ".pitch");

        return new Location(world, x, y, z, yaw, pitch);
    }

    /**
     * Save Location to the specified configuration using the provided path
     *
     * @param javaPlugin   Plugin
     * @param locationPath The path in the configuration where the Location should be saved
     * @param loc          Location to save in the config
     * @return Returns true if we override existing data.
     */
    public static boolean setLocation(JavaPlugin javaPlugin, String locationPath, Location loc) {
        FileConfiguration config = javaPlugin.getConfig();
        boolean override = config.get(locationPath) != null;

        config.set(locationPath + ".world", loc.getWorld().getName());
        config.set(locationPath + ".x", loc.getX());
        config.set(locationPath + ".y", loc.getY());
        config.set(locationPath + ".z", loc.getZ());
        config.set(locationPath + ".yaw", loc.getYaw());
        config.set(locationPath + ".pitch", loc.getPitch());

        javaPlugin.saveConfig();
        return override;
    }

    public static @Nullable PotionType getPotionType(@NotNull FileConfiguration config, String potionTypePath) {
        String name = config.getString(potionTypePath);

        if (name == null) {
            return null;
        }

        try {
            return PotionType.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static void colorItem(ItemStack itemStack, int r, int g, int b) {
        org.bukkit.Color color = org.bukkit.Color.fromRGB(r, g, b);

        if (itemStack.getItemMeta() instanceof FireworkEffectMeta fireworkMeta) {
            FireworkEffect effect = FireworkEffect.builder()
                    .withColor(color)
                    .build();

            fireworkMeta.setEffect(effect);

            itemStack.setItemMeta(fireworkMeta);
            return;
        }

        if (itemStack.getItemMeta() instanceof LeatherArmorMeta leatherMeta) {
            leatherMeta.setColor(color);

            itemStack.setItemMeta(leatherMeta);
            return;
        }
    }
}
