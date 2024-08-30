package me.Paabl0.net;
import me.Paabl0.net.CustomMob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CustomMobSpawner<T extends CustomMob> {
    private final JavaPlugin plugin;
    private final Class<T> mobClass;
    private final Random random;

    private Map<LivingEntity, CustomMob> bank = new HashMap<>();

    public CustomMobSpawner(JavaPlugin plugin, Class<T> mobClass) {
        this.plugin = plugin;
        this.mobClass = mobClass;
        this.random = new Random();
    }

    public void startSpawningTask() {
        // Run the spawning task every 20 ticks (1 second)
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            // Get a random player to spawn near

            Player player = getRandomPlayer(); //did need to move it here from the methods to later on make the conditionsAreMet translucent in all of the three words. (or meaning the canSpawn())
            if(player == null){
                return;
            }
            Location spawnLocation = getRandomLocationNearPlayer(player);

            // Check conditions for spawning here
            if (conditionsAreMet(spawnLocation)) {
                spawnCustomMob(player, spawnLocation);
            }
        }, 0L, 300L);
    }

    private boolean conditionsAreMet(Location location) {
        // Use the mobClass's canSpawn method to check conditions
        try {
            T customMob = mobClass.getDeclaredConstructor().newInstance();
            return customMob.canSpawn(location);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void spawnCustomMob(Player player, Location spawnLocation) {

        if (player != null) {
            try {
                T customMob = mobClass.getDeclaredConstructor().newInstance();

                bank.put(customMob.spawn(spawnLocation), customMob);
                if(Singularities.spawnerBanks.containsKey(mobClass)){
                    Singularities.spawnerBanks.replace(mobClass, getBank());
                }
                else{
                    Singularities.spawnerBanks.put(mobClass, getBank());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Player getRandomPlayer() {
        // Get a list of all online players
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();

        // Return a random player from the list (if there are players online)
        if (!players.isEmpty()) {
            int randomIndex = random.nextInt(players.size());
            return players.get(randomIndex);
        }

        return null; // No players online
    }

    private Location getRandomLocationNearPlayer(Player player) {
        // Get the player's current location
        Location playerLocation = player.getLocation();

        // Generate a random location within a certain radius from the player
        double radius = 10.0; // Adjust this radius as needed
        double randomX = playerLocation.getX() + (random.nextDouble() * 2 * radius) - radius;
        double randomZ = playerLocation.getZ() + (random.nextDouble() * 2 * radius) - radius;

        // Ensure the location is within the world bounds
        double maxY = Bukkit.getWorld("me.Paabl0.net/world").getMaxHeight();
        double minY = Bukkit.getWorld("me.Paabl0.net/world").getMinHeight();
        double randomY = playerLocation.getY() + random.nextInt((int) (maxY - minY + 1)) + minY;

        return new Location(playerLocation.getWorld(), randomX, randomY, randomZ);
    }

    public Map<LivingEntity, CustomMob> getBank() {
        return bank;
    }
}