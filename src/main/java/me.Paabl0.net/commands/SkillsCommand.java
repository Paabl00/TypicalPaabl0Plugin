package me.Paabl0.net.commands;

import me.Paabl0.net.TypicalPabloPlugin;
import me.Paabl0.net.components.player.PlayerProfile;
import me.Paabl0.net.managers.PlayerProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static me.Paabl0.net.misc.Utils.color;
import static me.Paabl0.net.misc.Utils.createItem;

public class SkillsCommand extends Command{

    private PlayerProfileManager playerProfileManager;

    public SkillsCommand(TypicalPabloPlugin typicalPabloPlugin) {
        super(typicalPabloPlugin, "skills");
        playerProfileManager = typicalPabloPlugin.getPlayerProfileManager();
    }

    @Override
    protected void execute(Player player, String[] args) {

        PlayerProfile playerProfile = playerProfileManager.getPlayerProfiles().get(player.getUniqueId());
        Inventory skillsGUI;

        if(playerProfile.getSkillInv() == null) {
            skillsGUI = Bukkit.createInventory(null, 54, color("&l&4Skills"));
        }
        else{
            skillsGUI = playerProfile.getSkillInv();
        }

        ItemStack overlay = createItem(Material.GRAY_STAINED_GLASS_PANE, 1, false, false, true, ChatColor.RESET + "", null);

        for(int i = 0; i<skillsGUI.getSize(); i++){
            skillsGUI.setItem(i, overlay);
        }

        skillsGUI.setItem(13, createItem(Material.EXPERIENCE_BOTTLE, 1, false, false, true, color("&d&lYou have &5" + String.valueOf(playerProfile.getEssence()) + " &d&lessences left!"), null));

        skillsGUI.setItem(28, createItem(Material.IRON_SWORD, 1, false, false, true, color("&4&lDamage"),
                color("&7Skill lvl: " + playerProfile.getSkills().getDamage()),
                color( "&7Effect: " + playerProfile.getSkills().scale(1) + "% damage given"),
                color("&7Essences for next lvl: " + playerProfile.getSkills().skillCost(playerProfile.getSkills().getDamage()))));

        skillsGUI.setItem(30, createItem(Material.RED_DYE, 1, false, false, true, color("&c&lHealth"),
                color("&7Skill lvl: " + playerProfile.getSkills().getHealth()),
                color("&7Effect: " + playerProfile.getSkills().scale(2) + " hearts"),
                color("&7Essences for next lvl: " + playerProfile.getSkills().skillCost(playerProfile.getSkills().getHealth()))));

        skillsGUI.setItem(32, createItem(Material.IRON_CHESTPLATE, 1, false, false, true, color("&a&lEndurance"),
                color("&7Skill lvl: " + playerProfile.getSkills().getEndurance()),
                        color("&7Effect: " + playerProfile.getSkills().scale(3) + "% endurance"),
                color("&7Essences for next lvl: " + playerProfile.getSkills().skillCost(playerProfile.getSkills().getEndurance()))));


        skillsGUI.setItem(34, createItem(Material.WRITABLE_BOOK, 1, true, false, true, color("&f&lExploration"),
                color("&7Skill lvl: " + playerProfile.getSkills().getExploration()),
                color("&7Effect: Can mine: "),
                color("&7Essences for next lvl: " + playerProfile.getSkills().skillCost(playerProfile.getSkills().getExploration()))));

        ItemMeta appendExplorationMilestones = skillsGUI.getItem(34).getItemMeta();
        List<String> prevLore = appendExplorationMilestones.getLore();
        prevLore.addAll(2, playerProfile.getSkills().explorationMilestones());
        appendExplorationMilestones.setLore(prevLore);
        skillsGUI.getItem(34).setItemMeta(appendExplorationMilestones);
        /*for(int i = 2; i <= playerProfile.getSkills().explorationMilestones().size() + 2; i++){
            for(String iterator : playerProfile.getSkills().explorationMilestones()){

            }
        }
        */

        playerProfile.setSkillInv(skillsGUI);
        player.openInventory(skillsGUI);
    }

}
