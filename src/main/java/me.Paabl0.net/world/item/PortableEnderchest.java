package me.Paabl0.net.world.item;

import me.Paabl0.net.TypicalPabloPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PortableEnderchest implements Listener { //TODO: FIX ENDERCHEST GLITCH ITEM WHEN CLOSING INVENTORY

    private PortableEnderchestRecipe portableEnderchestRecipe;
    public PortableEnderchest(TypicalPabloPlugin typicalPabloPlugin){
        portableEnderchestRecipe = new PortableEnderchestRecipe(typicalPabloPlugin);

    }

    @EventHandler
    public void onPlacingEnderchest(BlockPlaceEvent e){
        if(e.getItemInHand() != null && e.getItemInHand().equals(portableEnderchestRecipe.getItem())){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onEnderchestIteraction(InventoryClickEvent e){
        if (e.getCurrentItem() != null) {
            if (e.getCurrentItem().equals(portableEnderchestRecipe.getItem())) {
                if (e.getWhoClicked() instanceof Player) {
                    if (e.getClick().isRightClick()) {
                        e.getWhoClicked().openInventory(e.getWhoClicked().getEnderChest());
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

}
