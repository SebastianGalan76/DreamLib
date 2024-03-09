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
import java.util.ArrayList;
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
        //Loading material
        Material material = getMaterial(config, path+".material");
        if(material==null){
            return null;
        }

        //Loading amount of item
        int amount = config.getInt(path+".amount", 1);
        if(amount<=0){
            Bukkit.getLogger().warning("Incorrect item amount: "+path);
            return null;
        }

        //Create ItemStack and ItemMeta
        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();

        //Loading item name
        String name = config.getString(path+".name");
        if(name!=null){
            itemMeta.setDisplayName(Color.fixRGB(name));
        }

        //Loading lore
        if(config.getString(path+".lore")!=null){
            List<String> lore = Color.fixRGB(config.getStringList(path+".lore"));
            itemMeta.setLore(lore);
        }

        //Loading glowing
        boolean glowing = config.getBoolean(path+".glowing", false);
        if(glowing){
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        //Loading enchantments
        HashMap<Enchantment, Integer> enchantments = getEnchantments(config, path+".enchant");
        if(enchantments!=null && !enchantments.isEmpty()){
            enchantments.forEach((enchant, level) -> {
                itemMeta.addEnchant(enchant, level, true);
            });
        }

        //Loading item flags
        List<ItemFlag> itemFlagList = getItemFlags(config, path+".itemFlag");
        if(itemFlagList!=null && !itemFlagList.isEmpty()){
            itemFlagList.forEach((itemFlag) -> {
                itemMeta.addItemFlags(itemFlag);
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

        List<String> enchantsStringList = config.getStringList(enchantsPath);
        if(enchantsStringList.isEmpty()){
            return null;
        }

        for(String enchant:enchantsStringList){
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

    /**
     * Retrieves a list of ItemFlags from the specified configuration using the provided path
     *
     * @param config The config from which to retrieve the enchantments
     * @param itemFlagPath The path in the configuration where the ItemFlags are located
     * @return A list of materials retrieved from the config, or null if no item flags are found
     */
    public static @Nullable List<ItemFlag> getItemFlags(@NotNull FileConfiguration config, String itemFlagPath){
        List<ItemFlag> itemFlagList = new ArrayList<>();

        List<String> itemFlagStringList = config.getStringList(itemFlagPath);
        if(itemFlagStringList.isEmpty()){
            return null;
        }

        for(String itemFlagString:itemFlagStringList){
            ItemFlag itemFlag;

            try{
                itemFlag = ItemFlag.valueOf(itemFlagString);
            }catch (IllegalArgumentException e){
                Bukkit.getLogger().warning("Incorrect item flag: "+ itemFlagPath +"\n"+e.getMessage());
                continue;
            }

            itemFlagList.add(itemFlag);
        }

        return itemFlagList;
    }
}
