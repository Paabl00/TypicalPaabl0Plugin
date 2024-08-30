package me.Paabl0.net.events;

import me.Paabl0.net.TypicalPabloPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class EntitySpawnController implements Listener {
    private Random random = new Random();

    private TypicalPabloPlugin typicalPabloPlugin;
    private
    ItemStack[] heavy_duty_zombie_armor = {new ItemStack(Material.IRON_BOOTS),new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_HELMET)};

    public EntitySpawnController(TypicalPabloPlugin plugin){
        this.typicalPabloPlugin = plugin;
    }

    @EventHandler
    public void onMonsterSpawn(EntitySpawnEvent e){
        if(e.getEntity() instanceof Monster){
            if(random.nextInt(100) + 1 <= 10){
                if(e.getEntity().getType() == EntityType.ZOMBIE){
                    e.setCancelled(true);
                    e.getLocation().getWorld().spawn(e.getLocation(), Zombie.class, zombie -> {
                       //zombie.setCustomName("Heavy Duty Zombie");
                       zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
                       zombie.setHealth(40);
                       zombie.getEquipment().setArmorContents(heavy_duty_zombie_armor);
                       zombie.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
                       zombie.getEquipment().setHelmetDropChance(0f);
                       zombie.getEquipment().setChestplateDropChance(0f);
                       zombie.getEquipment().setLeggingsDropChance(0f);
                       zombie.getEquipment().setBootsDropChance(0f);
                       zombie.getEquipment().setItemInMainHandDropChance(0f);
                    });
                   e.getEntity().getPersistentDataContainer().set(typicalPabloPlugin.getCustName(), PersistentDataType.STRING, "Heavy Duty Zombie");
                }

                if(e.getEntity().getType() == EntityType.SKELETON){
                    e.setCancelled(true);
                    e.getLocation().getWorld().spawn(e.getLocation(), Skeleton.class, skeleton -> {
                        //skeleton.setCustomName("Reckless Archer");
                        skeleton.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.5);
                        skeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(25);
                        ItemStack specialBow = new ItemStack(Material.BOW);
                        specialBow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
                        skeleton.getEquipment().setItemInMainHand(specialBow);
                        skeleton.getEquipment().setItemInMainHandDropChance(0f);
                    });
                   e.getEntity().getPersistentDataContainer().set(typicalPabloPlugin.getCustName(), PersistentDataType.STRING, "Reckless Archer");
                }

                if(e.getEntity().getType() == EntityType.CREEPER){
                    e.setCancelled(true);
                    e.getLocation().getWorld().spawn(e.getLocation(), Creeper.class, creeper -> {
                        creeper.setCustomName("Silent Bomber");
                        creeper.setCustomNameVisible(false);
                        creeper.setPowered(true);
                        creeper.setInvisible(true);
                        creeper.setSilent(true);
                        creeper.setExplosionRadius(2);
                    });
                }

            }
        }
    }
}
