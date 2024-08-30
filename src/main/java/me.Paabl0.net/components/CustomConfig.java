package me.Paabl0.net.components;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class CustomConfig {

    private Plugin plugin;
    private File file;
    private String name;
    private FileConfiguration config;

    public CustomConfig(String name, Plugin plugin){
        this.plugin = plugin;
        this.name = name;
        Setup();
    }

    public File getFile(){
        return this.file;
    }

    public FileConfiguration getConfig(){
        return this.config;
    }

    public void Setup(){
        this.file = new File(Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()).getDataFolder(), name + ".yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {}
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }
    public void Save(String entryKey){
        try {
            config.save(file);
        } catch (IOException e) { Bukkit.getLogger().info(ChatColor.DARK_RED + "Failed saving " + name + ".yml"); }
    }


}
