package me.Paabl0.net.events.world;

import me.Paabl0.net.TypicalPabloPlugin;
import me.Paabl0.net.misc.Utils;
import org.bukkit.*;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

import static me.Paabl0.net.misc.Utils.color;
import static me.Paabl0.net.misc.Utils.soundPlayer;
import static org.bukkit.Bukkit.getServer;
import static org.bukkit.Bukkit.getWorld;

public class EnderDragonSlayer implements Listener {

    private TypicalPabloPlugin typicalPabloPlugin;
    private Random random = new Random();
    public EnderDragonSlayer(TypicalPabloPlugin typicalPabloPlugin){
        this.typicalPabloPlugin = typicalPabloPlugin;
    }

    @EventHandler
    public void onDragonDeath(EntityDeathEvent e){
        for(Player p : Bukkit.getWorld("world_the_end").getPlayers()){
            if(e.getEntity() instanceof EnderDragon) {
                if(typicalPabloPlugin.getWorldEventConfig().getConfig().getBoolean("EnderDragonSlayerEvent")) {
                    enderDragonEvent(p);
                    typicalPabloPlugin.getWorldEventConfig().getConfig().set("EnderDragonSlayerEvent", false);
                }
            }
        }
    }

    @EventHandler
    public void blockBedInEnd(BlockPlaceEvent e){
        if(e.getPlayer().getWorld().getName().equals("world_the_end")){
            if(e.getItemInHand().getTranslationKey().contains("bed")){
                e.setCancelled(true);
            }
        }
    }

    public void enderDragonEvent(Player p){
        new BukkitRunnable(){

            int i=0;

            @Override
            public void run() {
                if(i<60){
                    int x = 0, z = 0;
                    x = random.nextInt(30) + 1;
                    z = random.nextInt(30) + 1;

                    if(random.nextBoolean()){
                        x = x*(-1);
                    }
                    if(random.nextBoolean()){
                        z = z*(-1);
                    }
                    int y = getWorld(p.getWorld().getName()).getHighestBlockYAt((int) (p.getLocation().getX() + x), (int) (p.getLocation().getZ()+z));

                    p.getWorld().strikeLightning(new Location(p.getWorld(), p.getLocation().getX() + x, y, p.getLocation().getZ() + z));
                    i++;
                }
                else {
                    cancel();
                }
            }

        }.runTaskTimer(typicalPabloPlugin, 0, 5);

        new BukkitRunnable(){

            @Override
            public void run() {
                Utils.titlePlayer(p,  ("&d&k.dx.zd.c.f"), color("&7You feel that something has changed..."), 20, 100, 20);
            }

        }.runTaskLater(typicalPabloPlugin, 340);

        new BukkitRunnable(){

            int i = 1;
            final String[] lore = {"&5Essence: &7Hmmm... your ignorant actions have fractured the multiverse.",
                    "&5Essence: &7Even the most distant corners of everything could feel the immense tremor.",
                    "&5Essence: &7Ah, maybe let me introduce myself, im both your soul and the physical matter in this world.",
                    "&7You wouldn't normally have the sense to interact with me, but it seems like the splitting merged the many layers of existence.",
                    "&5Essence: &7There are many secrets to this universe, even the ones who shouldn't be touched on, like this one....",
                    "&5Essence: &7Your actions will have consequences and the judgement of the greater powers will come.",
                    "&5Essence: &7Or who am I to say what destiny will become, if we already ended up here, shattered soul..",
                    "&5Essence: &7Farewell. oh, praise The Greater Powers..."};


            @Override
            public void run() {
                if(i>=lore.length){
                    soundPlayer(p.getPlayer(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 2f, 0.1f);
                    soundPlayer(p.getPlayer(), Sound.ENTITY_ENDERMAN_AMBIENT, 2f, 0.1f);
                    if(!typicalPabloPlugin.getWorldEventConfig().getConfig().getBoolean("TremorsEvent")) {
                        typicalPabloPlugin.getWorldEventManager().initEventChangeFaze(TremorsPhase.class);
                        typicalPabloPlugin.getWorldEventConfig().getConfig().set("TremorsEvent", true);
                    }
                    cancel();
                }
                p.sendMessage(color(lore[i-1]));
                soundPlayer(p.getPlayer(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.5f);
                i++;
            }

        }.runTaskTimer(typicalPabloPlugin, 480, 100);

    }

}
