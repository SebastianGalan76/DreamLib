package pl.dream.dreamlib;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class Config {

    /**
     * Retrieves a ItemStack from the specified configuration using the provided path
     *
     * @param config The config from which to retrieve the ItemStack
     * @param path The path in the configuration where the ItemStack is located
     * @return A ItemStack retrieved from the config, or null if there is an error in the config
     */
    public static @Nullable ItemStack getItemStack(@NotNull FileConfiguration config, @NotNull String path){
        Material material = getMaterial(config, path+".material");
        if(material==null){
            return null;
        }

        int amount = config.getInt(path+".amount", 1);
        if(amount<=0){
            Bukkit.getLogger().warning("Incorrect item amount: "+path);
            return null;
        }

        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();

        String name = config.getString(path+".name");
        if(name!=null){
            itemMeta.setDisplayName(Color.fixRGB(name));
        }

        if(config.getString(path+".lore")!=null){
            List<String> lore = Color.fixRGB(config.getStringList(path+".lore"));
            itemMeta.setLore(lore);
        }

        boolean glowing = config.getBoolean(path+".glowing", false);
        if(glowing){
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        HashMap<Enchantment, Integer> enchantments = getEnchantments(config, path+".enchant");
        if(enchantments!=null && !enchantments.isEmpty()){
            enchantments.forEach((enchant, level) -> {
                itemMeta.addEnchant(enchant, level, true);
            });
        }

        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Retrieves a Material from the specified configuration using the provided path
     *
     * @param config The config from which to retrieve the material
     * @param materialPath The path in the configuration where the material is located
     * @return A material retrieved from the config, or null if the material is invalid
     */
    public static @Nullable Material getMaterial(@NotNull FileConfiguration config, String materialPath){
        String materialString = config.getString(materialPath);

        Material material;
        try{
            material = Material.valueOf(materialString);
        }catch (IllegalArgumentException e){
            Bukkit.getLogger().warning("Incorrect material: "+ materialPath +"\n"+e.getMessage());
            return null;
        }

        return material;
    }

    /**
     * Retrieves a map of enchantments from the specified configuration using the provided path
     *
     * @param config The config from which to retrieve the enchantments
     * @param enchantsPath The path in the configuration where the enchantments are located
     * @return A HashMap containing Enchantment objects mapped to their respective levels, or null if no enchantments are found
     */
    public static @Nullable HashMap<Enchantment, Integer> getEnchantments(@NotNull FileConfiguration config, String enchantsPath){
        HashMap<Enchantment, Integer> enchantments = new HashMap<>();

        List<String> enchantsString = config.getStringList(enchantsPath);
        if(enchantsString.isEmpty()){
            return null;
        }

        for(String enchant:enchantsString){
            String[] parts = enchant.split(":");

            String enchantName = parts[0];
            int enchantLevel = Integer.parseInt(parts[1]);

            if(enchantLevel<=0){
                Bukkit.getLogger().warning("Incorrect enchant level: "+enchantsPath+ " X: "+enchantLevel);
                continue;
            }

            Enchantment enchantment = Enchantment.getByName(enchantName);
            if(enchantment==null){
                Bukkit.getLogger().warning("Incorrect enchant name: "+enchantsPath + " X: "+enchantName);
                continue;
            }

            enchantments.put(enchantment, enchantLevel);
        }

        return enchantments;
    }
}
