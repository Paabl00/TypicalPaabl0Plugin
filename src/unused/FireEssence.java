package me.Paabl0.net.classes;

import me.Paabl0.net.CustomMob;
import me.Paabl0.net.Singularities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;

import static me.Paabl0.net.misc.Utils.color;

public class FireEssence extends CustomMob {

    public FireEssence() {
        super(EntityType.BLAZE);
        setMaxHealth(200);
        setHealth(200);
    }


    @Override
    public boolean canSpawn(Location location){
        // Check if it's nighttime
        long time = location.getWorld().getTime();
        if (time >= 12541 && time <= 23458) { // Nighttime check (adjust the time range as needed)
            return true; // Mob can spawn at night
        }

        return false; // Mob cannot spawn during the day
    }

    @Override
    protected void onSpawn(LivingEntity entity) {
        // Custom behavior for the spawned custom zombie
        entity.setCustomName(color("&l&4Fire Essence" + " " + " &c" + (int) entity.getHealth() + "/" + (int) entity.getMaxHealth() + "❤"));
        entity.setCustomNameVisible(true);
        entity.setMetadata("fireessence", new FixedMetadataValue(Singularities.getPlugin(Singularities.class), true));
        // Add any additional custom behavior here
    }

    @Override
    public LivingEntity spawn(Location location){
        return super.spawn(location);
    }

    @Override
    public void onHit(LivingEntity entity, EntityDamageEvent event){
        super.onHit(entity, event);
    }

}
