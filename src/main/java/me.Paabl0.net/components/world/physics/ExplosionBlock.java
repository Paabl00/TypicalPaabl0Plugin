package me.Paabl0.net.components.world.physics;

import me.Paabl0.net.TypicalPabloPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ExplosionBlock {

    private TypicalPabloPlugin typicalPabloPlugin;
    private Location block;
    private Vector velocity;

    public ExplosionBlock(TypicalPabloPlugin typicalPabloPlugin, Location block, Vector velocity){
        this.typicalPabloPlugin = typicalPabloPlugin;
        this.block = block;
        this.velocity = velocity;
        Animation.run();
    }

    Runnable Animation = () -> { //change to normal class probably easier on the cpu

        Material blockType =block.getBlock().getType();
        block.getBlock().setType(Material.AIR);

        ArmorStand armorStand = (ArmorStand) block.getWorld().spawnEntity(block, EntityType.ARMOR_STAND);
        armorStand.setInvisible(true);
        armorStand.setInvulnerable(true);
        armorStand.setCollidable(false);
        armorStand.setGravity(true);
        armorStand.setMarker(false);
        armorStand.setRemoveWhenFarAway(true);
        armorStand.getEquipment().setHelmet(new ItemStack(blockType));

        armorStand.setVelocity(velocity);

        final int[] secondClean = {0};

        new BukkitRunnable(){

            @Override
            public void run() {

                if (secondClean[0] > 25) {
                    for (int x = -1; x <= 1; x++) {
                        for (int z = -1; z <= 1; z++) {
                            if (armorStand.getLocation().clone().add(x, -1, z).getBlock().getType() != Material.AIR) {
                                armorStand.getLocation().getBlock().setType(blockType);
                                armorStand.remove();
                                cancel();
                            }
                        }
                    }
                }
                else{
                    if(armorStand.getLocation().clone().add(0, -1, 0).getBlock().getType() != Material.AIR){
                        armorStand.getLocation().getBlock().setType(blockType);
                        armorStand.remove();
                        cancel();
                    }
                }
                secondClean[0] += 1;
            }


        }.runTaskTimer(typicalPabloPlugin, 10, 1);

    };

}
