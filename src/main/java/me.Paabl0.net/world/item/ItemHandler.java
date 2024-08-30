package me.Paabl0.net.world.item;

import me.Paabl0.net.TypicalPabloPlugin;
import me.Paabl0.net.events.RegionListener;

import static org.bukkit.Bukkit.getServer;

public class ItemHandler {

    private CreativeWand cw;

    public ItemHandler(TypicalPabloPlugin typicalPabloPlugin){
        getServer().getPluginManager().registerEvents( new PortableEnderchest(typicalPabloPlugin), typicalPabloPlugin);
        getServer().getPluginManager().registerEvents(cw = new CreativeWand(), typicalPabloPlugin);
    }

    public CreativeWand getCW() {
        return cw;
    }
}
