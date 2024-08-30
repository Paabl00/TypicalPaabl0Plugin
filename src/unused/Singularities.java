package me.Paabl0.net;

import me.Paabl0.net.classes.FireEssence;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Singularities extends JavaPlugin {

	//Previous one for this plugin, with my own help and chatGPT but it didnt work since i cant cast the entity that spawns as my CustomMob class instance. For example in the CustomMobEvents class for now closed.
	//New base is by ThatOneRR (Youtube) RPGMobs video. Very good code so i use it as a learning base.

    public static Map< Class<?>,Map<LivingEntity, CustomMob>> spawnerBanks = new HashMap<>();

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Enabled!");
        getServer().getPluginManager().registerEvents(new CustomMobEvents(), this);

        // Create a CustomMobSpawner for FireEssence
        CustomMobSpawner<FireEssence> fireEssenceSpawner = new CustomMobSpawner<>(this, FireEssence.class);
        fireEssenceSpawner.startSpawningTask();
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Disabled!");
        // Plugin shutdown logic
    }
}
