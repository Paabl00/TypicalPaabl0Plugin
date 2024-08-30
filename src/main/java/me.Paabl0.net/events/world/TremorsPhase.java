package me.Paabl0.net.events.world;

import me.Paabl0.net.TypicalPabloPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

import static me.Paabl0.net.misc.Utils.color;

public class TremorsPhase implements Listener {
    private TypicalPabloPlugin typicalPabloPlugin;
    private Random random = new Random();

    private int feedAmount = 0;
    public TremorsPhase(TypicalPabloPlugin typicalPabloPlugin){
        this.typicalPabloPlugin = typicalPabloPlugin;

        final long[] nextMessage = {0};

        new BukkitRunnable(){

            @Override
            public void run() {
                if(System.currentTimeMillis() > nextMessage[0]) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.setVelocity(new Vector(random.nextDouble(1),random.nextDouble(1),random.nextDouble(1)));
                        p.sendMessage(color("&7Strange tremors are noticed around the whole globe..."));
                    }
                    nextMessage[0] = (40 - (random.nextInt(30) + 1)) * 60 * 1000 + System.currentTimeMillis();
                }

            }

        }.runTaskTimer(typicalPabloPlugin, 0, 60*20);
    }

    @EventHandler
    public void onFeedDragonEgg(PlayerInteractEvent e){
        if(e.getPlayer().getWorld() == Bukkit.getWorld("world")){
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(e.getClickedBlock().getType() == Material.DRAGON_EGG){
                    feedAmount ++;
                    if(feedAmount >= 100){
                        Bukkit.broadcastMessage(color("&5The VOID EGG awakens.!"));
                        Bukkit.broadcastMessage(color("&7The ground starts to crack..."));
                        e.getPlayer().setVelocity(new Vector(random.nextDouble(2),random.nextDouble(2),random.nextDouble(2)));
                        e.getClickedBlock().setType(Material.AIR);
                        VoidObelisk c = (VoidObelisk) typicalPabloPlugin.getWorldEventManager().initEventChangeFaze(VoidObelisk.class);
                        c.setEventLocation(e.getClickedBlock().getLocation());
                        c.Animation.run();
                    }
                    else {
                        e.getPlayer().sendMessage(color("&7You feel a persence coming from the egg. Something subconscious demands you to give yourself to it."));
                        e.getPlayer().sendMessage(color("&7Egg status: &d" + feedAmount + "/100 essences"));
                    }
                    e.setCancelled(true);
                }
            }
        }
    }

}
