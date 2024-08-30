package me.Paabl0.net.managers;

import me.Paabl0.net.components.world.Border;
import me.Paabl0.net.components.world.Region;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class RegionManager {

    private Map<String, Region> regions = new HashMap<>();

    public void createNewRegion(String name, String description, boolean isSafeZone, Border border) {
        Region region = new Region(name, description, isSafeZone, border);
        regions.put(ChatColor.stripColor(name.toLowerCase()), region);
    }

    public Map<String, Region> getRegions() {
        return regions;
    }
}
