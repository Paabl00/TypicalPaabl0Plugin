package me.Paabl0.net.components.world;

import org.bukkit.Location;

public class Border {

    private Location min, max;

    public Border() {
    }

    public Border(Location min, Location max) {
        this.min = min;
        this.max = max;
    }

    public boolean isComplete(){
        return min != null && max != null;
    }

    public boolean isWithinBounds(Location loc){
        int minX = min.getBlockX(), minY = min.getBlockY(), minZ = min.getBlockZ();
        int maxX = max.getBlockX() + 1, maxY = max.getBlockY() + 1, maxZ = max.getBlockZ() + 1;
        double x = loc.getX(), y = loc.getY(), z = loc.getZ();
        return (x >= minX && x <= maxX &&
                y >= minY && y <= maxY &&
                z >= minZ && z <= maxZ);
    }

    public void assignCorrectBorders(){
        int minX = min.getBlockX(), minY = min.getBlockY(), minZ = min.getBlockZ();
        int maxX = max.getBlockX(), maxY = max.getBlockY(), maxZ = max.getBlockZ();
        if(minX > maxX){
            int tempX = maxX;
            maxX = minX;
            minX = tempX;
        }
        if(minY > maxY){
            int tempY = maxY;
            maxY = minY;
            minY = tempY;
        }
        if(minZ > maxZ){
            int tempZ = maxZ;
            maxZ = minZ;
            minZ = tempZ;
        }

        min = new Location(min.getWorld(), minX, minY, minZ);
        max = new Location(max.getWorld(), maxX, maxY, maxZ);
    }

    public Location getMin() {
        return min;
    }

    public void setMin(Location min) {
        this.min = min;
    }

    public Location getMax() {
        return max;
    }

    public void setMax(Location max) {
        this.max = max;
    }
}
