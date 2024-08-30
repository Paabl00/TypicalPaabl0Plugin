package me.Paabl0.net.commands;

import me.Paabl0.net.TypicalPabloPlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Command implements CommandExecutor {

    protected TypicalPabloPlugin typicalPabloPlugin;

    public Command(TypicalPabloPlugin typicalPabloPlugin, String name) {
        this.typicalPabloPlugin = typicalPabloPlugin;
        typicalPabloPlugin.getCommand(name).setExecutor(this);
    }

    protected abstract void execute(Player player, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String alias, String[] args) {

        if (sender instanceof Player) execute((Player) sender, args);

        return true;
    }
}
