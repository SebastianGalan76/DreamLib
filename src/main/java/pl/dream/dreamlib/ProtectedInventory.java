package pl.dream.dreamlib;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class ProtectedInventory implements InventoryHolder {
    protected Inventory inv;

    public void setInventory(Inventory inv){
        this.inv = inv;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
