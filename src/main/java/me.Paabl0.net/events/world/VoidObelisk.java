package me.Paabl0.net.events.world;

import me.Paabl0.net.TypicalPabloPlugin;
import me.Paabl0.net.components.world.Border;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

import static me.Paabl0.net.misc.Utils.soundPlayer;

public class VoidObelisk implements Listener {

    private TypicalPabloPlugin typicalPabloPlugin;

    private boolean ongoigEvent = false;
    private ArmorStand armorStand;

    private Location eventLocation = null;

    private Random random = new Random();
    public VoidObelisk(TypicalPabloPlugin typicalPabloPlugin){this.typicalPabloPlugin = typicalPabloPlugin;}

     Runnable Animation = () -> {

        //eventLocation = typicalPabloPlugin.getItemHandler().getCW().getEventLocation();
        armorStand = (ArmorStand) eventLocation.getWorld().spawnEntity(eventLocation.add(0, -1, 0), EntityType.ARMOR_STAND);
        armorStand.setInvisible(true);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setMarker(true);
        armorStand.getEquipment().setHelmet(new ItemStack(Material.DRAGON_EGG));

         ongoigEvent = true;
         eventLocation.getWorld().setTime(18000);

        new BukkitRunnable(){
            double yOffset = 0;
            int duration = 1000;

            int mine = 0;
            boolean quarterTimerStart = false;
            int quarterTimer = 1;
            int reps = 0;

            @Override
            public void run() {

                yOffset += 0.01;
                    if(yOffset <= 3){
                        armorStand.teleport(eventLocation.clone().add(0, yOffset, 0));
                    }
                    else {
                        if(!quarterTimerStart) {
                            quarterTimerStart = true;
                        }
                        armorStand.setRotation((armorStand.getLocation().getYaw() + 36), 0);
                        for(float i = 3+2; i<100; i+=0.5){
                            eventLocation.getWorld().spawnParticle(Particle.DRAGON_BREATH,eventLocation.clone().add(0,i,0), 1);
                        }
                        if(reps == 6){
                            armorStand.getLocation().getWorld().strikeLightning(armorStand.getLocation());
                        }
                        if(reps > 6 && quarterTimer == 10) {
                            if (eventLocation.getY() - 5 * mine > -70) {
                                eventLocation.getWorld().spawn(eventLocation.clone().add(0, -5 * mine, 0), TNTPrimed.class, bomb -> {
                                    bomb.setInvulnerable(true);
                                    bomb.setYield(3F);
                                    bomb.setFuseTicks(0);
                                    bomb.setMetadata("breakAll", new FixedMetadataValue(typicalPabloPlugin, "whyDoWeNeedValueHere"));
                                    bomb.setMetadata("cannon", new FixedMetadataValue(typicalPabloPlugin, "whyDoWeNeedValueHere"));
                                });
                                mine++;
                            }
                        }

                    }

                    if(duration <= 0){
                        armorStand.remove();
                        ongoigEvent = false;
                        for (int x = -5; x <= 5; x++) {
                            for (int z = -5; z <= 5; z++) {
                                Block block = eventLocation.getWorld().getBlockAt(
                                        eventLocation.getBlockX() + x, -64, eventLocation.getBlockZ() + z);

                                // Calculate the distance from the explosion center to the block
                                double distance = Math.sqrt(x * x + z * z);

                                // Check if the block is within the spherical explosion radius
                                if (distance <= 5) {
                                    // Add the block to the list
                                    block.setType(Material.END_PORTAL);
                                }
                            }
                        }
                        Border border = new Border(eventLocation.clone().add(-10,10,10), eventLocation.clone().add(10, -255, -10));
                        border.assignCorrectBorders();
                        typicalPabloPlugin.getRegionManager().createNewRegion("&5&kczcfg", "&7The dimensional interstice..",
                                true, border);
                        cancel();
                    }

                    duration--;

                    if(quarterTimerStart) {
                        if (quarterTimer == 10) {
                            quarterTimer = 0;
                            reps++;
                        }
                        quarterTimer++;
                    }
            }

        }.runTaskTimer(typicalPabloPlugin, 0, 1);

    };

    public Location getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(Location eventLocation) {
        this.eventLocation = eventLocation;
    }

    @EventHandler
    public void throwPlayer(PlayerMoveEvent e) {
        if (ongoigEvent) {
            if (!armorStand.getNearbyEntities(5, 5, 5).isEmpty()){
                List<Entity> entityList = armorStand.getNearbyEntities(5, 5, 5);
                for(Entity entity : entityList){
                    if(entity instanceof Player){
                        entity.setVelocity(new Vector(armorStand.getLocation().getX() - entity.getLocation().getX(),
                                0,
                                armorStand.getLocation().getZ() - entity.getLocation().getZ()).multiply(-0.13));
                        soundPlayer((Player) entity, Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 0.05f);
                    }
                }
            }
        }
    }

}
