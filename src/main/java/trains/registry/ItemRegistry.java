package trains.registry;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import trains.items.ItemCore;

import static trains.TrainsInMotion.MODID;

public class ItemRegistry {


    public static Item testCart = new ItemCore().setUnlocalizedName("itemTest").setTextureName(MODID + ":itemTests");

    public ItemRegistry(){}



    public void RegisterItems(){

        GameRegistry.registerItem(testCart, testCart.getUnlocalizedName().substring(5));
    }
}
