package me.Paabl0.net.world.item;

import me.Paabl0.net.TypicalPabloPlugin;
import me.Paabl0.net.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import static me.Paabl0.net.misc.Utils.createItem;


public class PortableEnderchestRecipe {

    private final ItemStack item = createItem(Material.ENDER_CHEST,1,false,false,false, Utils.color("&dPortable Enderchest"),
            "&7Usual enderchest... with bluetooth 3.0");
    public PortableEnderchestRecipe(TypicalPabloPlugin typicalPabloPlugin){
        ShapedRecipe pec = new ShapedRecipe(new NamespacedKey(typicalPabloPlugin, "portableEnderchestRecipe"), item);

        pec.shape(" S ", "PEP", " Y ");
        pec.setIngredient('S', Material.NETHER_STAR);
        pec.setIngredient('P', Material.ENDER_PEARL);
        pec.setIngredient('E', Material.ENDER_CHEST);
        pec.setIngredient('Y', Material.ENDER_EYE);

        Bukkit.addRecipe(pec);
    }

    public ItemStack getItem() {
        return item;
    }

}
