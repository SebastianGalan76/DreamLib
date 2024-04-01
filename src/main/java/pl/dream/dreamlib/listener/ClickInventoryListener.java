package pl.dream.dreamlib.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import pl.dream.dreamlib.ProtectedInventory;

public class ClickInventoryListener implements Listener {
    @EventHandler
    public void onPlayerClick(InventoryClickEvent e){
        if(e.getClickedInventory()==null){return; }
        InventoryHolder invHolder = e.getInventory().getHolder();
        if(invHolder instanceof ProtectedInventory){
            if(e.getClick().isShiftClick() || e.getClick() == ClickType.DOUBLE_CLICK){
                e.setCancelled(true);
            }

            if(e.getAction() == InventoryAction.COLLECT_TO_CURSOR){
                e.setCancelled(true);
            }
        }

        if(e.getClickedInventory().getHolder() instanceof ProtectedInventory){
            e.setCancelled(true);
        }
    }
}
