package pl.dream.dreamlib.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import pl.dream.dreamlib.ProtectedInventory;

public class DragItemListener implements Listener {

    @EventHandler
    public void onDragItem(InventoryDragEvent e){
        if(e.getInventory().getHolder() instanceof ProtectedInventory){
            e.setCancelled(true);
            return;
        }
    }
}
