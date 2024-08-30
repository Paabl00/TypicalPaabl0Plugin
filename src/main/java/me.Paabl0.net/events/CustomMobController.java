package me.Paabl0.net.events;

import me.Paabl0.net.components.entity.CustomMob;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.*;

import static me.Paabl0.net.misc.Utils.*;
import static me.Paabl0.net.misc.Utils.color;

public class CustomMobController implements Listener {

    private Map<Entity, CustomMob> entities = new HashMap<>();

    private Map<Entity, Integer> indicators = new HashMap<>();
    private DecimalFormat formatter = new DecimalFormat("#.##");
    private Plugin plugin;

    private Random random = new Random();

    public CustomMobController(Plugin plugin){
        this.plugin = plugin;
        //spawnMobs(size, mobCap, spawnTime);
        damageIndicators();
    }

    @EventHandler
    public void onNaturalSpawn(EntitySpawnEvent e){
        if(e.getEntity() instanceof Mob){
            LivingEntity entity = (LivingEntity) e.getEntity();
            CustomMob[] customMobs = CustomMob.values();

            ArrayList<CustomMob> potentialSpawns = new ArrayList<>();

            for(CustomMob mob : customMobs){
                if(entity.getType() == mob.getType()){
                    potentialSpawns.add(mob);
                }
            }

            if(potentialSpawns != null && !potentialSpawns.isEmpty()) {
                int chosen = random.nextInt(100) + 1;
                int collection = 0;

                for (CustomMob mob : potentialSpawns) {
                    if (chosen >= collection && chosen <= mob.getSpawnChance() + collection) {
                        entities.put(mob.spawn(entity.getLocation()), mob);
                        e.setCancelled(true);
                    } else {
                        collection += mob.getSpawnChance();
                    }
                }
            }

        }
    }

/*
    public void spawnMobs(int size, int mobCap, int spawnTime) {
        CustomMob[] mobTypes = CustomMob.values();
         new BukkitRunnable() {
            Set<Entity> spawned = entities.keySet();
            List<Entity> removal = new ArrayList<>();
            @Override
            public void run() {
                for (Entity entity : spawned) {
                    if (!entity.isValid() || entity.isDead()) removal.add(entity);
                }
                spawned.removeAll(removal);

                // Spawning Algorithm
                int diff = mobCap - entities.size();
                if (diff <= 0) return;
                int spawnAmount = (int) (Math.random() * (diff + 1)), count = 0;
                while (count <= spawnAmount) {
                    count++;
                    int ranX = getRandomWithNeg(size), ranZ = getRandomWithNeg(size);
                    double xOffset = getRandomOffset(), zOffset = getRandomOffset();
                    if(Bukkit.getOnlinePlayers() != null) {
                        Location loc = new Location(Bukkit.getWorld("world"),0,0,0);
                        for(Player p : Bukkit.getOnlinePlayers()) {
                            Block block = p.getWorld().getHighestBlockAt((int) (ranX + p.getLocation().getX()), (int) (ranZ + p.getLocation().getZ()));
                            loc = block.getLocation().clone().add(xOffset, 1, zOffset);
                        }
                        if (!isSpawnable(loc)) continue;
                        double random = Math.random() * 101, previous = 0;
                        CustomMob typeToSpawn = mobTypes[0];
                        for (CustomMob type : mobTypes) {
                            previous += type.getSpawnChance();
                            if (random <= previous) {
                                typeToSpawn = type;
                                break;
                            }
                        }
                        entities.put(typeToSpawn.spawn(loc), typeToSpawn);
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, spawnTime);
    }

    private boolean isSpawnable(Location loc) {
        Block feetBlock = loc.getBlock(), headBlock = loc.clone().add(0, 1, 0).getBlock(), upperBlock = loc.clone().add(0, 2, 0).getBlock();
        return feetBlock.isPassable() && !feetBlock.isLiquid() && headBlock.isPassable() && !headBlock.isLiquid() && upperBlock.isPassable() && !upperBlock.isLiquid();
    }
*/
    public void damageIndicators(){
        new BukkitRunnable() {
            Set<Entity> stands = indicators.keySet();
            List<Entity> removal = new ArrayList<>();
            @Override
            public void run() {
                for (Entity stand : stands) {
                    int ticksLeft = indicators.get(stand);
                    if (ticksLeft == 0) {
                        stand.remove();
                        removal.add(stand);
                        continue;
                    }
                    ticksLeft--;
                    indicators.put(stand, ticksLeft);
                }
                stands.removeAll(removal);
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity rawEntity = event.getEntity();
        if(entities.containsKey(rawEntity)) {
            LivingEntity entity = (LivingEntity) rawEntity;
            String name = entity.getName();
            double maxHealth = entity.getMaxHealth();
            CustomMob mob = null;
            if (entities.containsKey(rawEntity)) {
                mob = entities.get(rawEntity);
                name = mob.getName();
                maxHealth = mob.getMaxHealth();
            }
            double damage = event.getFinalDamage(), health = entity.getHealth();
            if (health > damage) {
                // If the entity survived the hit
                health -= damage;
                entity.setCustomName(color(name + " &r&c" + (int) health + "/" + (int) maxHealth + "❤"));
            }

            Location loc = rawEntity.getLocation().clone().add(getRandomOffset(), 1, getRandomOffset());
            rawEntity.getWorld().spawn(loc, ArmorStand.class, armorStand -> {
                armorStand.setMarker(true);
                armorStand.setVisible(false);
                armorStand.setGravity(false);
                armorStand.setSmall(true);
                armorStand.setCustomNameVisible(true);
                armorStand.setCustomName(color("&c" + formatter.format(event.getFinalDamage())));
                indicators.put(armorStand, 30);
            });
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        //clear CustomMobs natural item drops
        if (!entities.containsKey(event.getEntity())) return;
        //event.setDroppedExp(0);
        event.getDrops().clear();
        //remove entity from the list on death, and drop the custom lootTable.
        entities.remove(event.getEntity()).tryDropLoot(event.getEntity().getLocation());
    }

}
