package me.Paabl0.net;


import me.Paabl0.net.commands.*;
import me.Paabl0.net.events.CustomMobController;
import me.Paabl0.net.events.PlayerProfileController;
import me.Paabl0.net.events.RegionListener;
import me.Paabl0.net.events.entity.Explosion;
import me.Paabl0.net.events.world.WorldEventManager;
import me.Paabl0.net.managers.RegionManager;
import me.Paabl0.net.managers.PlayerProfileManager;
import me.Paabl0.net.misc.AnalyticTool;
import me.Paabl0.net.misc.ConfigUtil;
import me.Paabl0.net.world.CustomWorld.CustomWorld;
import me.Paabl0.net.world.item.ItemHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TypicalPabloPlugin extends JavaPlugin implements Listener {

    private AnalyticTool analyticTool;
    private CustomWorld customWorld;
    private RegionManager regionManager = new RegionManager();
    private PlayerProfileManager playerProfileManager = new PlayerProfileManager();
    private RegionCommand regionCommand;
    private SkillsCommand skillsCommand;
    private AnalyticCommand analyticCommand;

    private EssenceCommand essenceCommand;

    private CreativeWandCommand creativeWandCommand;

    private ReturnCommand returnCommand;
    private ConfigUtil playerConfig;
    private ConfigUtil worldEventConfig;
    private WorldEventManager worldEventManager;
    private ItemHandler itemHandler;
    @Override
    public void onEnable() {
        analyticTool = new AnalyticTool(this);
        customWorld = new CustomWorld(this);
        playerConfig = new ConfigUtil(this, "playerprofile.yml");
        worldEventConfig = new ConfigUtil(this, "worldEvents.yml");

        itemHandler = new ItemHandler(this);
        worldEventManager = new WorldEventManager(this);

        regionCommand = new RegionCommand(this);
        skillsCommand = new SkillsCommand(this);
        analyticCommand = new AnalyticCommand(this);
        essenceCommand = new EssenceCommand(this);
        creativeWandCommand = new CreativeWandCommand(this);
        returnCommand = new ReturnCommand(this);
        getServer().getPluginManager().registerEvents(new Explosion(this), this);
        getServer().getPluginManager().registerEvents(new RegionListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerProfileController(this), this);
        getServer().getPluginManager().registerEvents(new CustomMobController(this), this);

    }

    public AnalyticTool getAnalyticTool() {
        return analyticTool;
    }

    public RegionManager getRegionManager() {
        return regionManager;
    }

    public RegionCommand getRegionCommand() {
        return regionCommand;
    }

    public AnalyticCommand getAnalyticCommand() {
        return analyticCommand;
    }

    public PlayerProfileManager getPlayerProfileManager() {
        return playerProfileManager;
    }

    public WorldEventManager getWorldEventManager() {
        return worldEventManager;
    }

    public ItemHandler getItemHandler(){
        return itemHandler;
    }

    public ConfigUtil getPlayerConfig(){
        return playerConfig;
    }

    public ConfigUtil getWorldEventConfig() {
        return worldEventConfig;
    }

    @Override
    public void onDisable(){
        playerConfig.save();
        worldEventConfig.save();
    }

}
