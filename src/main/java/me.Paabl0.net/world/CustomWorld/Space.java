package me.Paabl0.net.world.CustomWorld;

import me.Paabl0.net.TypicalPabloPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import static me.Paabl0.net.misc.Utils.*;

public class Space implements Listener {

    private TypicalPabloPlugin typicalPabloPlugin;
    private final String worldName = "space";
    private String wormholeDestination;
    private World customWorldHandle;
    public Space(TypicalPabloPlugin typicalPabloPlugin) {
        this.typicalPabloPlugin = typicalPabloPlugin;
        WorldCreator space = new WorldCreator(worldName);
        space.environment(World.Environment.NORMAL);
        space.generator(new EmptyChunkGenerator());
        customWorldHandle = Bukkit.createWorld(space);
        customWorldHandle.setTime(18000);
        customWorldHandle.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        customWorldHandle.setSpawnLocation(0, 40, 0);

        int minY = 30, maxY=50;
        int minX = -10, maxX=10;
        int minZ = -10, maxZ=10;

        for(int y = minY; y <= maxY; y++){
            for(int x = minX; x <= maxX; x++){
                for(int z = minZ; z <= maxZ; z++){
                    if(y == minY || y == maxY ||
                    x == minX || x == maxX ||
                    z == minZ || z == maxZ){
                        customWorldHandle.getBlockAt(x,y,z).setType(Material.BLACK_TERRACOTTA);
                    }
                }
            }
        }

        if (customWorldHandle != null) {
            Bukkit.getLogger().info("World '" + worldName + "' has been generated and loaded.");
        } else {
            Bukkit.getLogger().severe("Failed to generate the COM world.");
        }
    }

    @EventHandler
    public void dimensionMenuTeleporter(PlayerPortalEvent e){
        if(typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(e.getPlayer().getUniqueId()).getPlayerRegion() != null) {
            if (typicalPabloPlugin.getRegionManager().getRegions().get("&5&kczcfg").equals(typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(e.getPlayer().getUniqueId()).getPlayerRegion())) {
                e.getPlayer().teleport(Bukkit.getWorld(worldName).getSpawnLocation());
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void removeEnvironmentEntities(EntitySpawnEvent e){
        if(e.getEntity().getWorld() == Bukkit.getWorld(worldName)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void unallowPlacing(BlockPlaceEvent e){
        if(e.getBlock().getWorld() == Bukkit.getWorld(worldName)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void unallowDestroying(BlockBreakEvent e){
        if(e.getBlock().getWorld() == Bukkit.getWorld(worldName)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void unallowMoving(PlayerMoveEvent e){
        if(e.getPlayer().getWorld() == Bukkit.getWorld(worldName)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void unallowInteractions(PlayerInteractEvent e){
        if(e.getPlayer().getWorld() == Bukkit.getWorld(worldName)) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onPlayerSwitchWorlds(PlayerTeleportEvent e){
        if(e.getFrom().getWorld() != e.getTo().getWorld()){
            if(e.getTo().getWorld() == Bukkit.getWorld(worldName)){
                titlePlayer(e.getPlayer(), "&dSpace", "&7Unknown horizons...", 10, 70, 20);
                soundPlayer(e.getPlayer(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.5f);
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, PotionEffect.INFINITE_DURATION, 10));
                e.getPlayer().setGravity(false);
                e.getPlayer().setInvisible(true);
                if(!typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(e.getPlayer().getUniqueId()).isUnlockedSpaceDialogue()) {
                    typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(e.getPlayer().getUniqueId()).setUnlockedSpaceDialogue(true);
                    runDialogue(e.getPlayer());
                }
                else{
                    wormholeDialogueDone(e.getPlayer());
                }
            }
        }
        if(e.getFrom().getWorld() == Bukkit.getWorld(worldName)){
            e.getPlayer().setGravity(true);
            e.getPlayer().setInvisible(false);
            e.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }

    final String[] lore = {"&5Essence: &7Destiny keep bringing us together, each time in worse scenario.",
            "&5Essence: &7Where is thy will? Why do destiny have you at its center?",
            "&5Essence: &7Something picks my intuition.....",
            "&5Essence: &7You're currently falling through a wormhole, which your actions have completed.",
            "&5Essence: &7Im beginning to believe, * .... *, here take this.",
            "&5Essence: &7This metaphysical item will guide you through space and matter.",
            "&aTip:&7*Use /return to travel back from other worlds*",
            "&5Essence: &7Judgement day is coming closer, haste up mortal..."};

    public void runDialogue(Player p){
        new BukkitRunnable(){

            int i = 1;

            @Override
            public void run() {
                if(i > lore.length){
                    gateway(p);
                    cancel();
                }
                else{
                    p.sendMessage(color(lore[i-1]));
                    soundPlayer(p.getPlayer(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.5f);
                    i++;
                }
            }

        }.runTaskTimer(typicalPabloPlugin, 150, 100);
    }

    public void wormholeDialogueDone(Player p){
        p.sendMessage(color("&dYou're going through a wormhole! &7You can always return by using the command &a/return"));
        gateway(p);
    }

    public void gateway(Player p){
        TextComponent textComponent = new TextComponent("[TRAVERSE THE WORMHOLE]");
        textComponent.setColor(ChatColor.GREEN);
        textComponent.setBold(true);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpme"));
        p.spigot().sendMessage(textComponent);
    }

    @EventHandler
    public void wormholeCommand(PlayerCommandPreprocessEvent e){
        if(e.getMessage().equalsIgnoreCase("/tpme")){
            if(e.getPlayer().getWorld() == Bukkit.getWorld(worldName)){
                e.getPlayer().teleport(Bukkit.getWorld(wormholeDestination).getSpawnLocation());
                e.setCancelled(true);
            }
        }
    }

    public String getWorldName() {
        return worldName;
    }

    public World getCustomWorldHandle() {
        return customWorldHandle;
    }

    public String getWormholeDestination() {
        return wormholeDestination;
    }

    public void setWormholeDestination(String s){
        this.wormholeDestination = s;
    }

}
