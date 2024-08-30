package me.Paabl0.net.managers;

import me.Paabl0.net.components.player.PlayerProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfileManager {

    private Map<UUID, PlayerProfile> playerProfiles = new HashMap<>();

    public void addSkilledPlayer(UUID uuid){
        PlayerProfile player = new PlayerProfile(0,0,0,0);
        player.setEssence(0);
        playerProfiles.put(uuid, player);
    }

    public Map<UUID, PlayerProfile> getPlayerProfiles() {
        return playerProfiles;
    }

}
