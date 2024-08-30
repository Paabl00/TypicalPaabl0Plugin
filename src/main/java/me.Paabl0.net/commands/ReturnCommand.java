package me.Paabl0.net.commands;

import me.Paabl0.net.TypicalPabloPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReturnCommand extends Command{

    public ReturnCommand(TypicalPabloPlugin typicalPabloPlugin) {
        super(typicalPabloPlugin, "return");
    }

    @Override
    protected void execute(Player player, String[] args) {
        if(player.getWorld() != Bukkit.getWorld("world") &&
                player.getWorld() != Bukkit.getWorld("world_nether") &&
                player.getWorld() != Bukkit.getWorld("world_the_end")){
            if(player.getBedSpawnLocation() != null){
                player.teleport(player.getBedSpawnLocation());
            }
            else{
                player.teleport(Bukkit.getWorld("world").getSpawnLocation());
            }
        }
    }

}
