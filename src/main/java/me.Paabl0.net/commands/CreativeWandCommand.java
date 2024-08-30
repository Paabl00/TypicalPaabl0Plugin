package me.Paabl0.net.commands;

import me.Paabl0.net.TypicalPabloPlugin;
import org.bukkit.entity.Player;

public class CreativeWandCommand extends Command{
    public CreativeWandCommand(TypicalPabloPlugin typicalPabloPlugin) {
        super(typicalPabloPlugin, "cw");
    }

    @Override
    protected void execute(Player player, String[] args) {
        if(player.getName().equals("Paabl0")){
            player.getInventory().addItem(typicalPabloPlugin.getItemHandler().getCW().getCreativeWand());
        }
    }

}
