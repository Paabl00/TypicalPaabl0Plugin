package me.Paabl0.net.events;

import me.Paabl0.net.TypicalPabloPlugin;
import me.Paabl0.net.components.player.PlayerProfile;
import me.Paabl0.net.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.UUID;

import static me.Paabl0.net.misc.Utils.*;

public class PlayerProfileController implements Listener {

    private TypicalPabloPlugin typicalPabloPlugin;
    private Random random = new Random();
    public PlayerProfileController(TypicalPabloPlugin typicalPabloPlugin){
        this.typicalPabloPlugin = typicalPabloPlugin;
        essenceTrainTimer();
        armourCheeze();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        FileConfiguration config = typicalPabloPlugin.getPlayerConfig().getConfig();
        if(!typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().containsKey(e.getPlayer().getUniqueId())) {
            if (config.contains(String.valueOf(e.getPlayer().getUniqueId()))) {
                int essence = config.getInt(e.getPlayer().getUniqueId() + ".essence");
                int damage = config.getInt(e.getPlayer().getUniqueId() + ".damage");
                int health = config.getInt(e.getPlayer().getUniqueId() + ".health");
                int endurance = config.getInt(e.getPlayer().getUniqueId() + ".endurance");
                int exploration = config.getInt(e.getPlayer().getUniqueId() + ".exploration");
                boolean unlockedSpaceDialogue = config.getBoolean(e.getPlayer().getUniqueId() + ".unlockedSpaceDialogue");

                typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().put(e.getPlayer().getUniqueId(), new PlayerProfile(damage, health, endurance, exploration));
                typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(e.getPlayer().getUniqueId()).setEssence(essence);
                typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(e.getPlayer().getUniqueId()).setUnlockedSpaceDialogue(unlockedSpaceDialogue);
            } else {
                typicalPabloPlugin.getPlayerProfileManager().addSkilledPlayer(e.getPlayer().getUniqueId());
            }
        }

        PlayerProfile p = typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(e.getPlayer().getUniqueId());

        if(p.getSkills().getHealth() != 0){
            e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(p.getSkills().scale(2));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        typicalPabloPlugin.getPlayerConfig().getConfig().set(uuid + ".essence", typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(uuid).getEssence());
        typicalPabloPlugin.getPlayerConfig().getConfig().set(uuid + ".damage", typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(uuid).getSkills().getDamage());
        typicalPabloPlugin.getPlayerConfig().getConfig().set(uuid + ".health", typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(uuid).getSkills().getHealth());
        typicalPabloPlugin.getPlayerConfig().getConfig().set(uuid + ".endurance", typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(uuid).getSkills().getEndurance());
        typicalPabloPlugin.getPlayerConfig().getConfig().set(uuid + ".exploration", typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(uuid).getSkills().getExploration());
        typicalPabloPlugin.getPlayerConfig().getConfig().set(uuid + ".unlockedSpaceDialogue", typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(uuid).isUnlockedSpaceDialogue());
    }

    @EventHandler
    public void explorationSkillRestrictions(BlockBreakEvent e){
        PlayerProfile p = typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(e.getPlayer().getUniqueId());
        if(p.getSkills().getExploration() < 4){
            if(e.getBlock().getType().getBlockTranslationKey().contains("deepslate") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("iron") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("redstone") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("gold") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("lapis") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("emerald") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("quartz") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("diamond") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("ancient")) {
                e.getPlayer().sendMessage(color("&cYou don't have the might to mine this block, level up your skills!"));
                e.setCancelled(true);
                return;//TODO: return statements, potenthial threat
            }
        }
        if(p.getSkills().getExploration() < 6){
            if(e.getBlock().getType().getBlockTranslationKey().contains("redstone") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("gold") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("lapis") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("emerald") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("quartz") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("diamond") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("ancient")){
                e.getPlayer().sendMessage(color("&cYou don't have the might to mine this block, level up your skills!"));
                e.setCancelled(true);
                return;
            }
        }
        if(p.getSkills().getExploration() < 8){
            if(e.getBlock().getType().getBlockTranslationKey().contains("diamond") ||
                    e.getBlock().getType().getBlockTranslationKey().contains("ancient") ){
                e.getPlayer().sendMessage(color("&cYou don't have the might to mine this block, level up your skills!"));
                e.setCancelled(true);
                return;
            }
        }

        if(p.getSkills().getExploration() < 12){
            if(e.getBlock().getType().getBlockTranslationKey().contains("ancient")){
                e.getPlayer().sendMessage(color("&cYou don't have the might to mine this block, level up your skills!"));
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void essenceFromBlocks(BlockBreakEvent e){
        typicalPabloPlugin.getAnalyticTool().getMessageBuffer().add("&a&l " + e.getPlayer().getName() + " --- " + e.getEventName());
        PlayerProfile p = typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(e.getPlayer().getUniqueId());

        int genNR = random.nextInt(750);
        if(p.essenceTrainFilled()) {genNR = random.nextInt(1500);}

        typicalPabloPlugin.getAnalyticTool().getMessageBuffer().add("&7Essence train filled: " + p.essenceTrainFilled() + ", gen. nr: " + genNR);

        if(genNR == 420){
            int roulette = random.nextInt(100) + 1;
            typicalPabloPlugin.getAnalyticTool().getMessageBuffer().add("&7Roulette gen. nr: " + roulette + " -- (>=80)");
            if(roulette >= 80){
                essenceRoulette(e.getPlayer());
            }
            else {
                p.setEssence(p.getEssence() + 1);
                soundPlayer(e.getPlayer(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.5f);
            }

            if(!p.essenceTrainFilled()) {
                for (int i = 0; i < p.getEssenceTrain().length; i++) {
                    if (p.getEssenceTrain()[i] == 0) {
                        p.getEssenceTrain()[i] = (System.currentTimeMillis() + 15 * 60 * 1000);
                        break;
                    }
                }
            }
            else{
                int i = 0;
                for(int ii = i+1; ii < p.getEssenceTrain().length; ii++){
                    p.getEssenceTrain()[ii-1] = p.getEssenceTrain()[ii];
                }
                p.getEssenceTrain()[p.getEssenceTrain().length-1] = (System.currentTimeMillis() + 15*60*1000);
            }
            if(roulette >= 80){
                Bukkit.broadcastMessage(color("&7" + e.getPlayer().getName() + " found an potential &d&lESSENCE &7in " + e.getBlock().getType().getBlockTranslationKey().replace("block.minecraft.", "")) + ". Time to start the gamble!");
            }
            else {
                Bukkit.broadcastMessage(color("&7" + e.getPlayer().getName() + " found an &d&lESSENCE &7in " + e.getBlock().getType().getBlockTranslationKey().replace("block.minecraft.", "")));
            }
        }

        long[] essenceT = p.getEssenceTrain().clone();
        typicalPabloPlugin.getAnalyticTool().getMessageBuffer().add("&7essence train: [0]: " + ((essenceT[0] - System.currentTimeMillis()) / 1000));
        typicalPabloPlugin.getAnalyticTool().getMessageBuffer().add("&7essence train: [1]: " + ((essenceT[1] - System.currentTimeMillis()) / 1000));
        typicalPabloPlugin.getAnalyticTool().getMessageBuffer().add("&7essence train: [2]: " + ((essenceT[2] - System.currentTimeMillis()) / 1000));
        typicalPabloPlugin.getAnalyticTool().getMessageBuffer().add("&7----------------------------------");
        typicalPabloPlugin.getAnalyticTool().serverSyncMessages();
    }

    public void essenceRoulette(Player p){
        Random random = new Random();
        final int[] multiplier = {0};
        final boolean[] s = {false};
        final boolean[] lock = {false};
        final String[] analytics = {" ", " ", " ", " ", " ", " "};

        new BukkitRunnable(){

            @Override
            public void run() {
                Utils.titlePlayer(p, "&6Essence Roulette", " ", 10, 50, 20);
                analytics[0] = "&d&l" + p.getName() + " --- Essence Roulette";
            }

        }.runTaskLater(typicalPabloPlugin, 0);

        new BukkitRunnable(){

            @Override
            public void run() {
                int genNr = random.nextInt(100);
                if(genNr >= 0 && genNr <= 69){
                    multiplier[0] = 2;
                }
                if(genNr >= 70 && genNr <= 89){
                    multiplier[0] = 3;
                }
                if(genNr >= 90 && genNr <= 98){
                    multiplier[0] = 4;
                }
                if(genNr == 99){
                    multiplier[0] = 10;
                }
                analytics[1] = "&7Essence multiplayer gen. nr: " + genNr + ", translation: " + multiplier[0];
                Utils.titlePlayer(p, "&7Multiplier: " + "&6&l&n" + multiplier[0], " ", 10, 50, 20);
            }

        }.runTaskLater(typicalPabloPlugin, 80);


        new BukkitRunnable(){

            String win = "&aWIN";
            String loss = "&cLOSS";
            String temp;

            int nr = random.nextInt(20);
            int i = 0;

            @Override
            public void run() {
                if(i == 0) {
                    if (random.nextBoolean()) {
                        s[0] = true;
                        temp = win;
                    }
                    else{
                        s[0] = false;
                        temp = loss;
                    }
                    analytics[2] = "&7Starter: " + temp;
                    analytics[3] = "&7Slots count: " + nr;
                }

                Utils.titlePlayer(p, "&6Status: " + temp, "&7Multiplier: " + multiplier[0], 0, 10, 0);
                soundPlayer(p, Sound.BLOCK_COMPARATOR_CLICK, 1f, 0.5f);
                if(s[0]){
                    s[0] = false;
                    temp = loss;
                }
                else{
                    s[0] = true;
                    temp = win;
                }
                if(i == nr) {
                    cancel();
                    lock[0] = true;
                }
                i++;
            }

        }.runTaskTimer(typicalPabloPlugin, 160, 5);


        new BukkitRunnable(){

            @Override
            public void run() {
                if (lock[0]) {
                    analytics[4] = "&7Bet status: " + s[0];
                    analytics[5] = "&7---------------------------";

                    for(int i = 0; i < analytics.length; i++){
                        typicalPabloPlugin.getAnalyticTool().getMessageBuffer().add(analytics[i]);
                        typicalPabloPlugin.getAnalyticTool().serverSyncMessages();
                    }

                    if (s[0]) {
                        Utils.titlePlayer(p, "&aWIN!!!", "&7Essences won: " + multiplier[0], 0, 50, 40);
                        soundPlayer(p, Sound.ENTITY_PLAYER_LEVELUP,1f, 0.5f);
                        PlayerProfile pp = typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(p.getUniqueId());
                        pp.setEssence(pp.getEssence() + multiplier[0]);
                        cancel();
                    } else {
                        Utils.titlePlayer(p, "&cLOSS....", "&7...and don't forget the joker!" , 0, 50, 40);
                        soundPlayer(p, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1f, 0.5f);
                        cancel();
                    }
                }
            }
        }.runTaskTimer(typicalPabloPlugin, 160, 10);

    }

    public void essenceTrainTimer(){
        new BukkitRunnable(){

            @Override
            public void run() {

                for(Player p : Bukkit.getOnlinePlayers()){
                    PlayerProfile pProfile = typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(p.getUniqueId());

                    for(int i = 0; i < pProfile.getEssenceTrain().length; i++){
                        if(pProfile.getEssenceTrain()[i] < System.currentTimeMillis()){
                            for(int ii = i+1; ii < pProfile.getEssenceTrain().length; ii++){
                                pProfile.getEssenceTrain()[ii-1] = pProfile.getEssenceTrain()[ii];
                            }
                            pProfile.getEssenceTrain()[pProfile.getEssenceTrain().length-1] = 0;
                        }
                    }

                }

            }

        }.runTaskTimer(typicalPabloPlugin, 0, 20);
    }

    @EventHandler
    public void damageSkillEvent(EntityDamageByEntityEvent e){
        int s = 0;
        if(e.getDamager() instanceof Arrow){
            if(((Arrow) e.getDamager()).getShooter() instanceof Player){
                s = 1;
            }
        }
        if(e.getDamager() instanceof Player || s == 1){
            PlayerProfile p;
            if(s==1) { p = typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(((Player) ((Arrow) e.getDamager()).getShooter()).getUniqueId()); }
            else { p = typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(e.getDamager().getUniqueId()); }
            double percent = p.getSkills().scale(1);
            double damageDealt = e.getDamage();
            e.setDamage(damageDealt*(1-Math.abs(percent)/100));
        }
    }


    @EventHandler
    public void enduranceSkillEvent(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            PlayerProfile p = typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(e.getEntity().getUniqueId());

            double percent = p.getSkills().scale(3);
            double damageTaken = e.getDamage();
            e.setDamage(damageTaken*(1+Math.abs(percent)/100));
        }
    }

    public void armourCheeze(){
        new BukkitRunnable(){

            @Override
            public void run() {

                for(Player p : Bukkit.getOnlinePlayers()){
                    PlayerProfile pProfile = typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(p.getUniqueId());

                    for (int i = 0; i < pProfile.getArmour().length; i++) {
                        int s = 0;
                        if (pProfile.getArmour()[i] != null) {
                            if(pProfile.getArmour()[i].getTranslationKey().contains("iron") && pProfile.getSkills().getExploration() < 4){
                                pProfile.getSpectralArmourChart()[i] = pProfile.getArmourPointChart()[i] * 0.8; //sus;
                                s = 1;
                            }

                            if(pProfile.getArmour()[i].getTranslationKey().contains("diamond") && pProfile.getSkills().getExploration() < 8){
                                pProfile.getSpectralArmourChart()[i] = pProfile.getArmourPointChart()[i] * 0.75; //sus;
                                s = 1;
                            }
                        }

                        if(s!=1) {
                            pProfile.getSpectralArmourChart()[i] = pProfile.getArmourPointChart()[i];
                            s=0;
                        }
                    }

                    p.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(pProfile.getSpectralArmourPoints() - pProfile.getArmourPoints()); //DMT trip sim 4k

                }

            }

        }.runTaskTimer(typicalPabloPlugin, 3, 10);
    }

    @EventHandler
    public void collectArmour(PlayerMoveEvent e){
        PlayerProfile p = typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(e.getPlayer().getUniqueId());
        ItemStack[] armour = e.getPlayer().getInventory().getArmorContents();
        for(int i = 0; i<armour.length; i++){
            if(armour[i] != null){
                if(armour.equals(p.getArmour())){
                    break;
                }
                else{
                    p.setArmour(armour);
                    break;
                }
            }
            else{
                if(i==armour.length-1){
                    p.setArmour(armour);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerSkillMenu(InventoryClickEvent e){
        PlayerProfile p = typicalPabloPlugin.getPlayerProfileManager().getPlayerProfiles().get(e.getWhoClicked().getUniqueId());
        if(e.getInventory().equals(p.getSkillInv())){
            if(!e.getClick().isLeftClick()){
                e.setCancelled(true);
            }
            else{
                if(e.getCurrentItem().isSimilar(e.getInventory().getItem(28))) { //DAMAGE
                    if(p.getSkills().canLvlUp(1)){
                        if (p.getSkills().skillCost(p.getSkills().getDamage()) <= p.getEssence()) {
                            p.setEssence(p.getEssence() - p.getSkills().skillCost(p.getSkills().getDamage()));
                            p.getSkills().setDamage(p.getSkills().getDamage()+1);
                            e.getWhoClicked().closeInventory();
                            ((Player)e.getWhoClicked()).chat("/me.Paabl0.net/events/player/skills");

                        }
                        else{
                            e.getWhoClicked().sendMessage(color("&cYou don't have the required essences to upgrade this skill!"));
                        }
                    }
                    else{
                        e.getWhoClicked().sendMessage(color("&cThis skill is already maxed!"));
                    }

                }

                if(e.getCurrentItem().isSimilar(e.getInventory().getItem(30))) { //HEALTH
                    if(p.getSkills().canLvlUp(2)){
                        if (p.getSkills().skillCost(p.getSkills().getHealth()) <= p.getEssence()) {
                            p.setEssence(p.getEssence() - p.getSkills().skillCost(p.getSkills().getHealth()));
                            p.getSkills().setHealth(p.getSkills().getHealth()+1);
                            e.getWhoClicked().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(p.getSkills().scale(2));
                            e.getWhoClicked().closeInventory();
                            ((Player)e.getWhoClicked()).chat("/me.Paabl0.net/events/player/skills");

                        }
                        else{
                            e.getWhoClicked().sendMessage(color("&cYou don't have the required essences to upgrade this skill!"));
                        }
                    }
                    else{
                        e.getWhoClicked().sendMessage(color("&cThis skill is already maxed!"));
                    }

                }

                if(e.getCurrentItem().isSimilar(e.getInventory().getItem(32))) { //ENDURANCE
                    if(p.getSkills().canLvlUp(3)){
                        if (p.getSkills().skillCost(p.getSkills().getEndurance()) <= p.getEssence()) {
                            p.setEssence(p.getEssence() - p.getSkills().skillCost(p.getSkills().getEndurance()));
                            p.getSkills().setEndurance(p.getSkills().getEndurance()+1);
                            e.getWhoClicked().closeInventory();
                            ((Player)e.getWhoClicked()).chat("/me.Paabl0.net/events/player/skills");

                        }
                        else{
                            e.getWhoClicked().sendMessage(color("&cYou don't have the required essences to upgrade this skill!"));
                        }
                    }
                    else{
                        e.getWhoClicked().sendMessage(color("&cThis skill is already maxed!"));
                    }

                }

                if(e.getCurrentItem().isSimilar(e.getInventory().getItem(34))) { //EXPLORATION
                    if(p.getSkills().canLvlUp(4)){
                        if (p.getSkills().skillCost(p.getSkills().getExploration()) <= p.getEssence()) {
                            p.setEssence(p.getEssence() - p.getSkills().skillCost(p.getSkills().getExploration()));
                            p.getSkills().setExploration(p.getSkills().getExploration()+1);
                            e.getWhoClicked().closeInventory();
                            ((Player)e.getWhoClicked()).chat("/me.Paabl0.net/events/player/skills");

                        }
                        else{
                            e.getWhoClicked().sendMessage(color("&cYou don't have the required essences to upgrade this skill!"));
                        }
                    }
                    else{
                        e.getWhoClicked().sendMessage(color("&cThis skill is already maxed!"));
                    }

                }

                e.setCancelled(true);
            }
        }
    }

}
