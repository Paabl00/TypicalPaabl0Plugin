package me.Paabl0.net.world.CustomWorld;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

import java.util.*;

public class COMBlockPopulator extends BlockPopulator {

    public double getPackedIceChance(int y) {
        y = y - 30;
        if (y < 15) {
            return 0.5;
        }
        if (y >= 15 && y < 40) {
            return 1;
        }
        if (y >= 40) {
            return 2;
        }
        return 0;
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int maxGlowstonePerChunk = 5; // Lower the number of glowstone per chunk to reduce impact
        int glowstonePlaced = 0;

        int minY = 30;
        int maxY = 110;

        int interspace = 45;

        // Simplified Glowstone Placement
        while (glowstonePlaced < maxGlowstonePerChunk) {
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            int y = random.nextInt(maxY - minY) + minY;

            Block block = chunk.getBlock(x, y, z);
            Block aboveBlock = chunk.getBlock(x, y + 1, z);

            if (block.getType().getBlockTranslationKey().contains("sandstone") && aboveBlock.getType() == Material.AIR) {
                block.setType(Material.GLOWSTONE);
                glowstonePlaced++;
            }
        }

        // Place packed ice on cliffs
        for (int x = 1; x < 15; x++) {
            for (int z = 1; z < 15; z++) {
                for (int y = minY; y <= maxY; y++) {
                    Block block = chunk.getBlock(x, y, z);

                    // Check if the block is RED_SANDSTONE and part of a cliff
                    if (block.getType().getBlockTranslationKey().contains("sandstone") && isCliffFace(chunk, x, y, z)) {
                        // Place packed ice with a specified chance
                        if (random.nextDouble() * 100 < getPackedIceChance(y)) {
                            block.setType(Material.BLUE_ICE);
                            //if(y-30 > 40){
                            //    block.getRelative(BlockFace.EAST).setType(Material.BLUE_ICE);
                           // }
                        }
                    }
                }
            }
        }
    }

    // Helper method to determine if a block is on the surface of a cliff
    private boolean isCliffFace(Chunk chunk, int x, int y, int z) {
        return (chunk.getBlock(x + 1, y, z).getType() == Material.AIR ||
                chunk.getBlock(x - 1, y, z).getType() == Material.AIR ||
                chunk.getBlock(x, y, z + 1).getType() == Material.AIR ||
                chunk.getBlock(x, y, z - 1).getType() == Material.AIR);
    }

}