package me.Paabl0.net;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import static me.Paabl0.net.misc.Utils.color;

public class CustomMob {
    private EntityType entityType;
    private double maxHealth;
    private double health;
    private PotionEffect potionEffect;
    private ItemStack[] armor;
    private ItemStack mainHand;

    public CustomMob(EntityType entityType) {
        this.entityType = entityType;
        this.maxHealth = 20.0; // Default max health
        this.health = 20.0;    // Default health
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return this.health;
    }

    public void setPotionEffect(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles) {
        this.potionEffect = new PotionEffect(type, duration, amplifier, ambient, particles);
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }


    public boolean canSpawn(Location location) {
        // Add custom spawn conditions here
        // Example: Check if location meets certain criteria
        // For instance, check if it's nighttime, if the location is within a certain biome, etc.

        return true; // Return true if spawn conditions are met; false otherwise
    }

    protected void onSpawn(LivingEntity entity) {
        // Default behavior (can be overridden by subclasses)
        // For example, you can add additional behavior here.
    }

    protected void onHit(LivingEntity entity, EntityDamageEvent event) {
        // Default behavior when the mob gets hit
        // You can override this method in subclasses to add custom behavior
       // entity.setCustomName(color(entityName + " " + " &c" + (int) entity.getHealth() + "/" + (int) entity.getMaxHealth() + "❤️"));
       // entity.setCustomNameVisible(true);
    }

    public LivingEntity spawn(Location location) {
        if (!canSpawn(location)) {
            return null; // Don't spawn if conditions are not met
        }

        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);
        entity.setMaxHealth(maxHealth);
        entity.setHealth(health);

        if (potionEffect != null) {
            entity.addPotionEffect(potionEffect);
        }

        EntityEquipment equipment = entity.getEquipment();
        if (armor != null) equipment.setArmorContents(armor);
        equipment.setHelmetDropChance(0f);
        equipment.setChestplateDropChance(0f);
        equipment.setLeggingsDropChance(0f);
        equipment.setBootsDropChance(0f);

        if (mainHand != null) {
            equipment.setItemInMainHand(mainHand);
            equipment.setItemInMainHandDropChance(0f);
        }

        // Call the overridden onSpawn method
        onSpawn(entity);

        return entity;
    }
}