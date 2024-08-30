package me.Paabl0.net.commands;

import me.Paabl0.net.TypicalPabloPlugin;
import me.Paabl0.net.components.player.PlayerProfile;
import me.Paabl0.net.managers.PlayerProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static me.Paabl0.net.misc.Utils.color;

public class EssenceCommand extends Command{

    private PlayerProfileManager playerProfileManager;

    public EssenceCommand(TypicalPabloPlugin typicalPabloPlugin) {
        super(typicalPabloPlugin, "transfuse");
        playerProfileManager = typicalPabloPlugin.getPlayerProfileManager();
    }

    @Override
    protected void execute(Player player, String[] args) {
        if(args.length != 2){
            player.sendMessage(color("&cInvalid command arguments!"));
            return;
        }
        else{
            if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))){

                if(Bukkit.getPlayer(args[0]).equals(player)){
                    player.sendMessage(color("&cYou can't transfuse essences to yourself!"));
                    return;
                }

                int count = 0;
                try {
                    count = Integer.parseInt(args[1]);
                } catch (NumberFormatException e){
                    player.sendMessage(color("&cSecond argument must be a number!"));
                    return;
                }
                if(count > 0) {
                    PlayerProfile playerProfile = playerProfileManager.getPlayerProfiles().get(player.getUniqueId());
                    PlayerProfile pReciever = playerProfileManager.getPlayerProfiles().get(Bukkit.getPlayer(args[0]).getUniqueId());

                    if (playerProfile.getEssence() >= count) {
                        playerProfile.setEssence(playerProfile.getEssence() - count);
                        pReciever.setEssence(pReciever.getEssence() + count);
                        player.sendMessage(color("&aSuccessfully sent " + count + " essences to player " + Bukkit.getPlayer(args[0]).getName() + "!"));
                        Bukkit.getPlayer(args[0]).sendMessage(color("&dRecieved " + count + " essences from player " + player.getName()));
                    } else {
                        player.sendMessage(color("&cYou don't have the amount of essences you want to transfuse!"));
                        return;
                    }
                }
                else{
                    player.sendMessage(color("&cImproper amount of essences given!"));
                    return;
                }

            }
            else{
                player.sendMessage(color("&cCheck your spelling or make sure recieving player is online!"));
                return;
            }
        }
    }

}
