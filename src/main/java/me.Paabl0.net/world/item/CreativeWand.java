package me.Paabl0.net.world.item;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.Callable;

import static me.Paabl0.net.misc.Utils.color;
import static me.Paabl0.net.misc.Utils.createItem;

public class CreativeWand implements Listener {
    private final ItemStack creativeWand = createItem(Material.STICK, 1, true, true, false, color("&4Creative Wand"), "" );
    private Runnable function;
    private Location eventLocation;

    boolean eventBlocker = true;
     @EventHandler
    public void useCreativeWand(PlayerInteractEvent e){
         if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
             if(e.getPlayer().getInventory().getItemInOffHand() != null && e.getPlayer().getInventory().getItemInOffHand().equals(creativeWand)) {
                 if (function != null) {
                     if (eventBlocker) {
                         try {
                             eventLocation = e.getPlayer().getEyeLocation();
                             function.run();
                         } catch (Exception ex) {
                             e.getPlayer().sendMessage(color("&cFunction call failed!"));
                         }
                         eventBlocker = false;
                     }
                     else{
                         eventBlocker = true;
                     }
                 }
             }
         }
     }

    public void setFunction(Runnable function) {
        this.function = function;
    }

    public ItemStack getCreativeWand() {
        return creativeWand;
    }

    public Location getEventLocation(){
         return eventLocation;
    }

}
