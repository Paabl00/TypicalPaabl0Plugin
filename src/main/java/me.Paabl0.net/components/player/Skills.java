package me.Paabl0.net.components.player;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

import static me.Paabl0.net.misc.Utils.color;

public class Skills {

    static int FLAG_DAMAGE = 1;
    static int FLAG_HEALTH = 2;
    static int FLAG_ENDURANCE = 3;
    static int FLAG_EXPLORATION = 4;


    private int damage;
    private int damageStart = -80; //Scaling in percentage
    private int damageLvlChange = 4; //Scaling in levels rising %
    private int damageLvlCap = 20; //how many lvls can you buff the stat


    private int health;
    private int healthStart = 20; //Numbers of hearts
    private int healthLvlChange = 1; //Scaling in nr. hearts rising pr. lvl
    private int healthLvlCap = 20;


    private int endurance;
    private int enduranceStart = -50; //Scaling in percentage
    private int enduranceLvlChange = 5; //Scaling in levels rising %
    private int enduranceLvlCap = 10;

    private int exploration;
    private int explorationStart = 0; //Different system
    private int explorationLvlCap = 12;

    public double scale(int flag){
        if(flag == FLAG_DAMAGE){return damageStart + (damage*damageLvlChange);}
        if(flag == FLAG_HEALTH){return healthStart + (health*healthLvlChange);}
        if(flag == FLAG_ENDURANCE){return enduranceStart + (endurance*enduranceLvlChange);}
        return 0;
    }

    public List<String> explorationMilestones(){
        List<String> lore = new ArrayList<>();
        lore.add(color("&7 - STONE, COAL, COPPER"));
        if(exploration >= 4){
            lore.add(color("&7 - DEEPSLATE (same ores), IRON"));
        }
        if(exploration >= 6){
            lore.add(color("&7 - REDSTONE, GOLD (+nether), LAPIS, EMERALDS, QUARTZ"));
        }
        if(exploration >= 8){
            lore.add(color("&7 - DIAMONDS"));
        }
        if(exploration == 12){
            lore.add(color("&7 - ANCIENT DEBRIS"));
        }
        return lore;
    }

    public boolean canLvlUp(int flag){
        if(flag == FLAG_DAMAGE && damage < damageLvlCap){return true;}
        if(flag == FLAG_HEALTH && health < healthLvlCap){return true;}
        if(flag == FLAG_ENDURANCE && endurance < enduranceLvlCap){return true;}
        if(flag == FLAG_EXPLORATION && exploration < explorationLvlCap){return true;}
        else return false;
    }

    public int skillCost(int x){
        return (int) (1.11*Math.pow(x+1, 1.03));
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getExploration() {
        return exploration;
    }

    public void setExploration(int exploration) {
        this.exploration = exploration;
    }
}
