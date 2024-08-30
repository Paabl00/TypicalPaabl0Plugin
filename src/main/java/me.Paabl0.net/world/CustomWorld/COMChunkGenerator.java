package me.Paabl0.net.world.CavesOfMars;

import me.Paabl0.net.world.CustomWorld.COMBlockPopulator;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.PerlinNoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.Bukkit.getServer;

public class COMChunkGenerator extends ChunkGenerator {

    private PerlinNoiseGenerator groundNoiseGenerator;
    private PerlinNoiseGenerator cliffNoiseGenerator;

    private Random random = new Random();

    private int worldMinY = 20;
    private int worldMaxY = 110;
    public COMChunkGenerator() {
        this.groundNoiseGenerator = new PerlinNoiseGenerator(random);
        this.cliffNoiseGenerator = new PerlinNoiseGenerator(random); // Separate noise for cliffs
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunkData = createChunkData(world);

        int baseHeight = 40;  // General height level where terrain starts
        int caveHeightRange = 30; // Range for the height of the cave
        int cliffHeightRange = 50; // Height range for the cliffs

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = (chunkX << 4) + x;
                int worldZ = (chunkZ << 4) + z;

                // Ground noise for smooth hills
                double groundNoise = groundNoiseGenerator.noise(worldX * 0.05, worldZ * 0.05) * 5;

                // Cliff noise for steep, climbable cliffs
                double cliffNoise = cliffNoiseGenerator.noise(worldX * 0.08, worldZ * 0.08) * cliffHeightRange;

                // Calculate the ground and ceiling heights
                int groundHeight = (int) (baseHeight + groundNoise);
                int ceilingHeight = (int) (baseHeight + caveHeightRange + cliffNoise);

                for (int y = worldMinY; y < worldMaxY; y++) {
                    if (y < groundHeight || y > ceilingHeight) {
                        if(random.nextInt(100) < 20){
                            if(random.nextInt(20) < 10) {
                                chunkData.setBlock(x, y, z, Material.RED_SANDSTONE_STAIRS);
                            }
                            else{
                                chunkData.setBlock(x, y, z, Material.RED_SANDSTONE_SLAB);
                            }
                        }
                        else{
                            if(random.nextInt(100) < 6){
                                if(random.nextInt(6) < 4) {
                                    chunkData.setBlock(x, y, z, Material.POLISHED_GRANITE);
                                }
                                else{
                                    chunkData.setBlock(x, y, z, Material.YELLOW_TERRACOTTA);
                                }
                            }
                            else {
                                chunkData.setBlock(x, y, z, Material.RED_SANDSTONE);
                            }
                        }
                    } else {
                        chunkData.setBlock(x, y, z, Material.AIR);
                    }
                }

                // Ensure cliffs are climbable, connecting ground to ceiling
                if (ceilingHeight - groundHeight < 6) {
                    for (int y = groundHeight; y <= ceilingHeight; y++) {
                        chunkData.setBlock(x, y, z, Material.RED_SANDSTONE);
                    }
                }
            }
        }
        return chunkData;
    }


    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        List<BlockPopulator> populators = new ArrayList<>();
        populators.add(new COMBlockPopulator());
        return populators;
    }

}
