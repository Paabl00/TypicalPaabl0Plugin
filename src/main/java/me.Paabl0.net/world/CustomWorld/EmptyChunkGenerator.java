package me.Paabl0.net.world.CustomWorld;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class EmptyChunkGenerator extends ChunkGenerator {

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData data = createChunkData(world);
        /*
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunkX << 4) + x;
                int worldZ = (chunkZ << 4) + z;
                //DONT DO THAT not completed
                data.setBlock(x, 110, z, Material.BLACK_TERRACOTTA);
                data.setBlock(x, 90, z, Material.BLACK_TERRACOTTA);
            }
        }
         */
        return data;
    }

}
