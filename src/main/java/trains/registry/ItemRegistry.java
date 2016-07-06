package trains.registry;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import trains.blocks.Oil;
import trains.items.ItemFirstTrain;
import trains.items.OilBucket;

import static trains.TrainsInMotion.MODID;

public class ItemRegistry {

    //initialize the item
    public static Item testCart = new ItemFirstTrain().setUnlocalizedName("itemTest").setTextureName(MODID + ":itemTests");
    public static Item testOil = new OilBucket().setUnlocalizedName("OilTest");
    public ItemRegistry(){}


    public void RegisterItems(){
        //register the item we initialized
        GameRegistry.registerItem(testCart, testCart.getUnlocalizedName().substring(5));
        GameRegistry.registerItem(testOil, testOil.getUnlocalizedName().substring(5));
    }
}
