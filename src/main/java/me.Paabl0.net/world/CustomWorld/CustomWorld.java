package me.Paabl0.net.world.CustomWorld;

import me.Paabl0.net.TypicalPabloPlugin;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getServer;

public class CustomWorld {
    private TypicalPabloPlugin typicalPabloPlugin;
    private CavesOfMars COM;

    private Space space;

    public CustomWorld(TypicalPabloPlugin typicalPabloPlugin){
        this.typicalPabloPlugin = typicalPabloPlugin;
        getServer().getPluginManager().registerEvents(COM = new CavesOfMars(typicalPabloPlugin), typicalPabloPlugin);
        getServer().getPluginManager().registerEvents(space = new Space(typicalPabloPlugin), typicalPabloPlugin);
        space.setWormholeDestination(COM.getWorldName());
    }

    public CavesOfMars getCOM() {
        return COM;
    }
}
