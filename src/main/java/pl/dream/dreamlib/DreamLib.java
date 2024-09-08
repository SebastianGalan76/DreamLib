package pl.dream.dreamlib;

import org.bukkit.plugin.java.JavaPlugin;
import pl.dream.dreamlib.listener.ClickInventoryListener;
import pl.dream.dreamlib.listener.DragItemListener;

public final class DreamLib extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ClickInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new DragItemListener(), this);
    }
}
