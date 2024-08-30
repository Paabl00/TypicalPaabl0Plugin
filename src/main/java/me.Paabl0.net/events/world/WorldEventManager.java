package me.Paabl0.net.events.world;

import me.Paabl0.net.TypicalPabloPlugin;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.getServer;

public class WorldEventManager {

    private TypicalPabloPlugin typicalPabloPlugin;
    public WorldEventManager(TypicalPabloPlugin typicalPabloPlugin){
        this.typicalPabloPlugin = typicalPabloPlugin;
        VoidObelisk voidObelisk = new VoidObelisk(typicalPabloPlugin);
        typicalPabloPlugin.getItemHandler().getCW().setFunction(voidObelisk.Animation);

        if(!typicalPabloPlugin.getWorldEventConfig().getConfig().contains("EnderDragonSlayerEvent")){
            typicalPabloPlugin.getWorldEventConfig().getConfig().set("EnderDragonSlayerEvent", true);
        }

        if(typicalPabloPlugin.getWorldEventConfig().getConfig().getBoolean("EnderDragonSlayerEvent", false)) {
            getServer().getPluginManager().registerEvents(new EnderDragonSlayer(typicalPabloPlugin), typicalPabloPlugin);
        }
        if(typicalPabloPlugin.getWorldEventConfig().getConfig().getBoolean("TremorsEvent", false)) {
            getServer().getPluginManager().registerEvents(new TremorsPhase(typicalPabloPlugin), typicalPabloPlugin);
        }
        //getServer().getPluginManager().registerEvents(voidObelisk, typicalPabloPlugin);
    }

    public Object initEventChangeFaze(Class<?> clazz) { //yooo thanks chat
        try {
            // Assuming the class has a constructor that takes a single parameter of type TypicalPabloPlugin
            Object eventInstance = clazz.getConstructor(TypicalPabloPlugin.class).newInstance(typicalPabloPlugin);

            // Now register the event
            getServer().getPluginManager().registerEvents((Listener) eventInstance, typicalPabloPlugin);
            return eventInstance;
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions (InstantiationException, IllegalAccessException, etc.)
            return null;
        }
    }

}
