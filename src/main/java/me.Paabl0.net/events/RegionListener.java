package me.Paabl0.net.events;

import me.Paabl0.net.TypicalPabloPlugin;
import me.Paabl0.net.components.world.Border;
import me.Paabl0.net.components.world.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.Paabl0.net.misc.Utils.*;

public class RegionListener implements Listener {

    private TypicalPabloPlugin typicalPabloPlugin;
    private Map<String, Region> regions;
    private Map<UUID, Border> regionSetup;

    //private Map<UUID, Region> playerRegion = new HashMap<>();

    public RegionListener(TypicalPabloPlugin typicalPabloPlugin) {
        this.typicalPabloPlugin = typicalPabloPlugin;
        this.regions = typicalPabloPlugin.getRegionManager().getRegions();
        this.regionSetup = typicalPabloPlugin.getRegionCommand().getRegionSetup();
        new BukkitRunnable(){
            @Override
            public void run(){
                for(UUID uuid : regionSetup.keySet()){
                    Player player = Bukkit.getPlayer(uuid);
                    if(player == null) continue;
                    Border border = regionSetup.get(uuid);
                    if(!border.isComplete()) continue;
                    Border newBorder = new Border(border.getMin(), border.getMax());
                    newBorder.assignCorrectBorders();
                    Location min = newBorder.getMin(), max = newBorder.getMax();
                    for(int x = min.getBlockX(); x <= max.getBlockX() + 1; x++){ //loops through all blocks
                        for(int y = min.getBlockY(); y <= max.getBlockY() + 1; y++){
                            for(int z = min.getBlockZ(); z <= max.getBlockZ() + 1; z++){
                                if(x == min.getBlockX() || x == max.getBlockX() + 1 || //checks if any value is on its max, makes the "outline" work instead of filling with particles.
                                        y == min.getBlockY() || y == max.getBlockY() + 1 ||
                                        z == min.getBlockZ() || z == max.getBlockZ() + 1)
                                    player.spawnParticle(Particle.VILLAGER_HAPPY, x, y, z, 1);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(typicalPabloPlugin, 0L, 5L);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(!regionSetup.containsKey(player.getUniqueId())) return;
        Border border = regionSetup.get(player.getUniqueId());
        Block block = event.getClickedBlock(); //is nullable, hover over event.getClickedBlock();
        if(block == null) return;
        switch (event.getAction()){
            case LEFT_CLICK_BLOCK:
                event.setCancelled(true);
                border.setMin(block.getLocation());
                msgPlayer(player, "&aLocation #1 set!");
                break;
            case RIGHT_CLICK_BLOCK:
                event.setCancelled(true);
                border.setMax(block.getLocation());
                msgPlayer(player, "&bLocation #2 set!");
                break;
        }

    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Region current = typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(event.getPlayer().getUniqueId()).getPlayerRegion(), active = null;
        for(Region region : regions.values()){
            if(!region.getBorder().isWithinBounds(player.getLocation())) continue;
            active = region;
            if(current == region) continue;
            titlePlayer(player, region.getName(), region.getDescription(), 10, 70, 20);
            soundPlayer(player, Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.5f);
        }
        typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(event.getPlayer().getUniqueId()).setPlayerRegion(active);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        Entity entity = event.getEntity();
        if(!(entity instanceof Player)) return;
        Player player = (Player) entity;
        Region region = typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(player.getUniqueId()).getPlayerRegion();
        if(region != null && region.isSafeZone()) event.setCancelled(true);
    }

    @EventHandler
    public void onInteractSafeMode(PlayerInteractEvent e){
        Region region = typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(e.getPlayer().getUniqueId()).getPlayerRegion();
        if(region != null && region.isSafeZone()) e.setCancelled(true);
    }

}
