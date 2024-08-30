package me.Paabl0.net.events.entity;

import me.Paabl0.net.TypicalPabloPlugin;
import me.Paabl0.net.components.world.physics.ExplosionBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

import java.util.Random;

public class Explosion implements Listener {

    private TypicalPabloPlugin typicalPabloPlugin;

    private Random random = new Random();
    public Explosion(TypicalPabloPlugin typicalPabloPlugin){
        this.typicalPabloPlugin = typicalPabloPlugin;
    }
    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent e){
        if(e.getEntity().hasMetadata("breakAll")){ //thanks chat modified by me

            int radius = 5; // Define the explosion radius

            // Clear the list before populating it with new blocks
            e.blockList().clear();

            // Iterate through all blocks in a cube around the explosion, but only add blocks within the spherical radius
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Block block = e.getEntity().getWorld().getBlockAt(
                                e.getLocation().getBlockX() + x, e.getLocation().getBlockY() + y, e.getLocation().getBlockZ() + z);
                        if(block.getType() != Material.AIR) {
                            if(block.getType() == Material.LAVA || block.getType() == Material.WATER){
                                block.setType(Material.AIR);
                                continue;
                            }

                            // Calculate the distance from the explosion center to the block
                            double distance = Math.sqrt(x * x + y * y + z * z);

                            // Check if the block is within the spherical explosion radius
                            if (distance <= radius) {
                                // Add the block to the list
                                e.blockList().add(block);
                            }
                        }
                    }
                }
            }
        }

        for(Block blocks : e.blockList()) {
            double x = random.nextDouble(0.5);
            double z = random.nextDouble(0.5);
            if(random.nextBoolean()){
                x = x*(-1);
            }
            if(random.nextBoolean()){
                z = z*(-1);
            }
            if(e.getEntity().hasMetadata("cannon")){
                new ExplosionBlock(typicalPabloPlugin, blocks.getLocation(), new Vector(x*2, 5, z*2));
            }
            else {
                new ExplosionBlock(typicalPabloPlugin, blocks.getLocation(), new Vector(x, 1.5 - random.nextDouble(1), z));
            }
        }

    }

}
