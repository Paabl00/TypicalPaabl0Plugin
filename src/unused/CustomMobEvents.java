package me.Paabl0.net;

import me.Paabl0.net.classes.FireEssence;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static me.Paabl0.net.misc.Utils.color;

public class CustomMobEvents implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
            //LivingEntity damager = (LivingEntity) event.getDamager();
            LivingEntity damaged = (LivingEntity) event.getEntity();

            if (damaged.hasMetadata("fireessence")) {
                //for (LivingEntity entity : Singularities.spawnerBanks.get(FireEssence.class).keySet()) {
                  //  if (damaged == entity) {
                  //      FireEssence fireEssence = (FireEssence) entity;
                  //      fireEssence.onHit(damaged, event);
                  //  };
                //}
                damaged.setCustomName(color("&l&4Fire Essence" + " " + " &c" + (int) damaged.getHealth() + "/" + (int) damaged.getMaxHealth() + "❤"));
                damaged.setCustomNameVisible(true);
            }
        }
    }
}