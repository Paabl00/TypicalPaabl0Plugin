package me.Paabl0.net.components.world;

import me.Paabl0.net.components.world.Border;

public class Region {

    private String name, description;
    private Border border;
    private boolean isSafeZone;

    public Region(String name, String description,boolean isSafeZone, Border border) {
        this.name = name;
        this.description = description;
        this.isSafeZone = isSafeZone;
        this.border = border;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Border getBorder() {
        return border;
    }

    public boolean isSafeZone() {
        return isSafeZone;
    }
}
