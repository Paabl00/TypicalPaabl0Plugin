package me.Paabl0.net.world.CustomWorld;

import me.Paabl0.net.TypicalPabloPlugin;
import me.Paabl0.net.misc.DualObject;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static me.Paabl0.net.misc.Utils.*;

public class CavesOfMars implements Listener {

    private TypicalPabloPlugin typicalPabloPlugin;
    private HashMap<DualObject, Integer> blockTimer = new HashMap<>();
    private Random random = new Random();
    private final String worldName = "caves_of_mars";
    private World customWorldHandle;
    public CavesOfMars(TypicalPabloPlugin typicalPabloPlugin) {
        this.typicalPabloPlugin = typicalPabloPlugin;
        blockReset();
        WorldCreator COM = new WorldCreator(worldName);
        COM.environment(World.Environment.NORMAL);
        COM.generator(new me.Paabl0.net.world.CavesOfMars.COMChunkGenerator());
        customWorldHandle = Bukkit.createWorld(COM);
        //customWorldHandle.setSpawnLocation(0, 40, 0);
        int randX = 0, randZ = 0;
        for(int i = 20; i <= 60; i++){
            if(customWorldHandle.getBlockAt(randX, i, randZ).getType() != Material.AIR){
                if(i+1 == 60){
                    i = 20;
                    randX = getRandomWithNeg(100); randZ = getRandomWithNeg(100);
                }
                if(customWorldHandle.getBlockAt(randX, i+1, randZ).getType() == Material.AIR){
                    customWorldHandle.setSpawnLocation(randX, i + 1, randZ);
                }
            }
        }
        customWorldHandle.setTime(18000);
        customWorldHandle.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);

        if (customWorldHandle != null) {
            Bukkit.getLogger().info("COM World '" + worldName + "' has been generated and loaded.");
        } else {
            Bukkit.getLogger().severe("Failed to generate the COM world.");
        }
    }

    public String getWorldName() {
        return worldName;
    }

    public World getCustomWorldHandle() {
        return customWorldHandle;
    }

    @EventHandler
    public void onPlayerSwitchWorlds(PlayerTeleportEvent e){
        if(e.getFrom().getWorld() != e.getTo().getWorld()){
            if(e.getTo().getWorld() == Bukkit.getWorld(worldName)){
                titlePlayer(e.getPlayer(), "&6Caves of Mars", "&cFrom mars....", 10, 70, 20);
                soundPlayer(e.getPlayer(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.5f);
            }
        }
    }
    @EventHandler
    public void onMiningSandstone(BlockBreakEvent e){
        if(e.getBlock().getLocation().getWorld().equals(Bukkit.getWorld(worldName))){
            if(e.getBlock().getType().getBlockTranslationKey().contains("sandstone") ||
            e.getBlock().getType().getBlockTranslationKey().contains("granite")){
                if(random.nextInt(10000) == 420){
                    e.getPlayer().sendMessage("Insert new ore");
                }
                blockTimer.put(new DualObject(e.getBlock().getType(), e.getBlock().getLocation()), 10*20);
                e.setCancelled(true);
                e.getBlock().setType(Material.BEDROCK);
            }
        }
    }

    public void blockReset(){
        new BukkitRunnable(){

            ArrayList<DualObject> removables = new ArrayList<>();

            @Override
            public void run() {

                if(!blockTimer.isEmpty()){
                    for(DualObject obj : blockTimer.keySet()){
                        if(blockTimer.get(obj) - 10 <= 0){
                            ((Location)obj.getValue()).getBlock().setType((Material) obj.getKey());
                            removables.add(obj);
                        }
                        else{
                            blockTimer.replace(obj, blockTimer.get(obj) - 10);
                        }
                    }
                    if(!removables.isEmpty()){
                        for(DualObject obj : removables){
                            blockTimer.remove(obj);
                        }
                    }
                }

            }

        }.runTaskTimer(typicalPabloPlugin, 10, 10);
    }

    @EventHandler
    public void stopSpawningMobs(EntitySpawnEvent e){
        if(e.getEntity().getWorld().equals(Bukkit.getWorld(worldName))){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMiningPreciousOres(BlockBreakEvent e){
        if(e.getBlock().getWorld().equals(Bukkit.getWorld(worldName))){
            if(e.getBlock().getType() == Material.GLOWSTONE || e.getBlock().getType() == Material.YELLOW_TERRACOTTA){
                if(random.nextInt(1000) == 420){
                    e.getPlayer().sendMessage("Insert infernal rock");
                }
            }
            if(e.getBlock().getType() == Material.BLUE_ICE){
                if(random.nextInt(750) == 420){
                    e.getPlayer().sendMessage("Insert Traces of Microorganism");
                }
            }
        }
    }

    @EventHandler
    public void unAllowPuttingBlocks(BlockPlaceEvent e){
        if(e.getBlock().getWorld().equals(Bukkit.getWorld(worldName))){
            if(e.getBlock().getTranslationKey().contains("sandstone") ||
                    e.getBlock().getTranslationKey().contains("ice") ||
                    e.getBlock().getTranslationKey().contains("terracotta") ||
                    e.getBlock().getTranslationKey().contains("glowstone")){
                e.setCancelled(true);
            }
        }
    }

}
