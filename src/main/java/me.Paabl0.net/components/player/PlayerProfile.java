package me.Paabl0.net.components.player;

import me.Paabl0.net.components.world.Region;
import net.minecraft.world.item.ArmorItem;
import org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PlayerProfile {

    //private boolean analyticMode = false;

    private Region playerRegion = null;
    private Skills skills = new Skills();
    private Inventory skillInv;
    private int essence;
    private long[] essenceTrain = new long[3];
    private ItemStack[] armour = new ItemStack[4];
    private double[] armourPoints = new double[4];
    private double[] spectralArmourPoints = new double[4];
    private boolean unlockedSpaceDialogue = false;

    public PlayerProfile(int damage, int health, int endurance, int exploration) {
        skills.setDamage(damage);
        skills.setHealth(health);
        skills.setEndurance(endurance);
        skills.setExploration(exploration);
        Arrays.fill(essenceTrain, 0);
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public int getEssence() {
        return essence;
    }

    public void setEssence(int essence) {
        this.essence = essence;
    }

    public Inventory getSkillInv() {
        return skillInv;
    }
    public void setSkillInv(Inventory skillInv) {
        this.skillInv = skillInv;
    }

    public ItemStack[] getArmour(){
        return armour;
    }

    public void setArmour(ItemStack[] armour) {
        this.armour = armour;
        this.translateArmour(armour);
    }

    private void translateArmour(ItemStack[] armour) {
        for (int i = 0; i < armour.length; i++) {
            if (armour[i] != null) {
                net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(armour[i]);
                if (nmsStack.getItem() instanceof ArmorItem) {
                    armourPoints[i] = ((ArmorItem) nmsStack.getItem()).getDefense();
                }
            }
            else{
                armourPoints[i] = 0;
            }
        }
    }

    public double getArmourPoints(){
        double temp = 0;
        for(double i : armourPoints){
            temp += i;
        }
        return temp;
    }

    public double[] getArmourPointChart(){
        return armourPoints;
    }

    public double getSpectralArmourPoints() {
        double temp = 0;
        for(double i : spectralArmourPoints){
            temp += i;
        }
        return temp;
    }

    public double[] getSpectralArmourChart(){
        return spectralArmourPoints;
    }

    public long[] getEssenceTrain(){
        return essenceTrain;
    }

    public boolean essenceTrainFilled(){
        int temp = 0;
        for(int i = 0; i < essenceTrain.length; i++){
            if(essenceTrain[i] > 0) {
                temp++;
            }
        }
        if(temp == essenceTrain.length){
            return true;
        }
        else{
            return false;
        }
    }

   // public boolean isAnalyticMode() {
    //    return analyticMode;
    //}

   // public void setAnalyticMode(boolean analyticMode) {
    //    this.analyticMode = analyticMode;
   // }

    public Region getPlayerRegion() {
        return playerRegion;
    }

    public void setPlayerRegion(Region playerRegion) {
        this.playerRegion = playerRegion;
    }

    public boolean isUnlockedSpaceDialogue() {
        return unlockedSpaceDialogue;
    }

    public void setUnlockedSpaceDialogue(boolean unlockedSpaceDialogue) {
        this.unlockedSpaceDialogue = unlockedSpaceDialogue;
    }
}
