package me.Paabl0.net.commands;

import me.Paabl0.net.TypicalPabloPlugin;
import me.Paabl0.net.components.world.Border;
import me.Paabl0.net.managers.RegionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.Paabl0.net.misc.Utils.msgPlayer;

public class RegionCommand extends Command{

    private RegionManager regionManager;
    private Map<UUID, Border> regionSetup = new HashMap<>();

    public RegionCommand(TypicalPabloPlugin typicalPabloPlugin) {
        super(typicalPabloPlugin, "region");
        regionManager = typicalPabloPlugin.getRegionManager();
    }

    @Override
    protected void execute(Player player, String[] args) {
        if(args.length == 0){
            msgPlayer(player, "&cInvalid command arguments!");
            return;
        }

        switch (args[0].toLowerCase()){
            case "create":
                if(!regionSetup.containsKey(player.getUniqueId())){
                    //Wants to go into setup mode
                    regionSetup.put(player.getUniqueId(), new Border());
                    msgPlayer(player, "&eLeft-Click &fon a corner block to set &aPosition #1",
                            "Right-Click &fon the opposite corner block to set &bPosition #2",
                            "&fWhen done, type &d/region create <Region Name> <Is Safezone (true/false)> [Description (optional)]");
                }
                else if (args.length == 1){
                    regionSetup.remove(player.getUniqueId());
                    msgPlayer(player, "&cCancelled region creation!");
                } else if (args.length < 3) {
                    msgPlayer(player,"&cInvalid Usage, Correct Usage: &f/region create <Region Name> <Is Safezone (true/false)> [Description (optional)]");
                }
                else{
                    Border border = regionSetup.get(player.getUniqueId());
                    if(!border.isComplete()){
                        msgPlayer(player, "&fPlease select the two opposite corners of the boundary!");
                        return;
                    }
                    String name = args[1], safeInput = args[2].toLowerCase();
                    boolean isSafeZone;
                    if(safeInput.equalsIgnoreCase("true")){
                        isSafeZone = true;
                    } else if (safeInput.equalsIgnoreCase("false")) {
                        isSafeZone = false;
                    } else{
                        msgPlayer(player, "&fPlease specify &ctrue/false &ffor the region to be a safe zone!");

                        return;
                    }
                    StringBuilder builder = new StringBuilder();
                    for(int i = 4; i <= args.length; i++){
                        builder.append(args[i-1]).append(" ");
                    }
                    String description = builder.toString().trim();
                    border.assignCorrectBorders();

                    if(regionManager.getRegions().containsKey(ChatColor.stripColor(name).toLowerCase())){
                        msgPlayer(player, "&fThis name is already being used in a region!");
                        return;
                    }

                    regionManager.createNewRegion(name, description, isSafeZone, border);
                    regionSetup.remove(player.getUniqueId());
                    msgPlayer(player, "&aSuccessfully created region" + "&f" + name,
                            ChatColor.stripColor(" <safezone: " + isSafeZone + ">"),
                            ChatColor.stripColor(" Desc: [&7" + description + "&f]"));
                }
                break;
            default:
                msgPlayer(player, "&cUnknown command argument " + args[0] + "!");
                break;
        }
    }

    public Map<UUID, Border> getRegionSetup() {
        return regionSetup;
    }

}
