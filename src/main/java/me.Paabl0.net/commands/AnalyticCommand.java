package me.Paabl0.net.commands;

import me.Paabl0.net.TypicalPabloPlugin;
import me.Paabl0.net.components.player.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import static me.Paabl0.net.misc.Utils.color;
import static me.Paabl0.net.misc.Utils.msgPlayer;

public class AnalyticCommand extends Command{
    public AnalyticCommand(TypicalPabloPlugin typicalPabloPlugin) {
        super(typicalPabloPlugin, "analysis");
    }
    private List<String> playerIPAdressList = new ArrayList<>();
    @Override
    protected void execute(Player player, String[] args) {
        if(!player.getName().equals("Paabl0")){
            msgPlayer(player, "&cYou don't have permission to execute this command!");
            return;
        }
        if (args.length != 1) {
            msgPlayer(player, "&cInvalid command arguments!");
            return;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if(p != null) {
            if (!playerIPAdressList.contains(p.getAddress().getHostName())) {
                playerIPAdressList.add(p.getAddress().getHostName());
                p.sendMessage(color("&aAnalytic mode on!"));
            } else {
                playerIPAdressList.remove(p.getAddress().getHostName());
                p.sendMessage(color("&cAnalytic mode off!"));
            }
        }
        else{
            msgPlayer(player, "&cThis player doesn't exist!");
            return;
        }

    }

    public List<String> getPlayerIPAdressList() {
        return playerIPAdressList;
    }
}
