package me.Paabl0.net.events;

import me.Paabl0.net.TypicalPabloPlugin;
import me.Paabl0.net.components.CustomMob;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.*;

import static me.Paabl0.net.misc.Utils.color;
import static me.Paabl0.net.misc.Utils.getRandomOffset;

public class DamageIndicators implements Listener {

    private HashMap<Entity, Integer> indicators = new HashMap<>();
    private HashMap<Entity, Entity> traceNameIndicator = new HashMap<>();
    private TypicalPabloPlugin typicalPabloPlugin;

    private DecimalFormat formatter = new DecimalFormat("#.##");

    public DamageIndicators(TypicalPabloPlugin plugin){
        this.typicalPabloPlugin = plugin;
        damageIndicators();
    }

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
                        if(traceNameIndicator.containsKey(stand)){
                            traceNameIndicator.remove(stand);
                        }
                        continue;
                    }
                    ticksLeft--;
                    indicators.put(stand, ticksLeft);
                    if(traceNameIndicator.containsKey(stand)){
                        Location follow = traceNameIndicator.get(stand).getLocation(); follow.add(0,2,0);
                        stand.teleport(follow);
                    }
                }
                stands.removeAll(removal);
            }
        }.runTaskTimer(typicalPabloPlugin, 0L, 1L); //change if lagging //TODO: potenthial threat
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Mob) {
            LivingEntity entity = (LivingEntity) e.getEntity();
            double damage = e.getFinalDamage();
            if (entity.getHealth() > damage) {
                String name = entity.getName();


                if(e.getEntity().getPersistentDataContainer().has(typicalPabloPlugin.getCustName(), PersistentDataType.STRING)){
                    name = e.getEntity().getPersistentDataContainer().get(typicalPabloPlugin.getCustName(), PersistentDataType.STRING);
                }

                final String name2 = name;

                // If the entity survived the hit
                if(traceNameIndicator.containsValue(e.getEntity())){
                    for(Entity ent : traceNameIndicator.keySet()){
                        if(traceNameIndicator.get(ent).equals(e.getEntity())){
                            ent.setCustomName(color(name2 + " &r&c" + (int) (entity.getHealth()-damage) + "/" + (int) entity.getMaxHealth() + "❤"));
                            ent.teleport(e.getEntity().getLocation().add(0,2,0));
                            indicators.replace(ent, 30);
                        }
                    }
                }
                else {
                    Location loc = entity.getLocation().clone().add(0, 2.2, 0);
                    entity.getWorld().spawn(loc, ArmorStand.class, armorStand -> {
                        armorStand.setMarker(true);
                        armorStand.setVisible(false);
                        armorStand.setGravity(false);
                        armorStand.setSmall(true);
                        armorStand.setCustomNameVisible(true);
                        armorStand.setCustomName(color(name2 + " &r&c" + (int) (entity.getHealth()-damage) + "/" + (int) entity.getMaxHealth() + "❤"));
                        traceNameIndicator.put(armorStand, e.getEntity());
                        indicators.put(armorStand, 30);
                    });
                }
            }
            Location loc = entity.getLocation().clone().add(getRandomOffset(), 1, getRandomOffset());
            entity.getWorld().spawn(loc, ArmorStand.class, armorStand -> {
                armorStand.setMarker(true);
                armorStand.setVisible(false);
                armorStand.setGravity(false);
                armorStand.setSmall(true);
                armorStand.setCustomNameVisible(true);
                armorStand.setCustomName(color("&c" + formatter.format(damage)));
                indicators.put(armorStand, 30);
            });
        }
    }

}
