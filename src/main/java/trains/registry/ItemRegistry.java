package trains.registry;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import trains.items.ItemFirstTrain;
import trains.utility.Fluid.Oil;

import static trains.TrainsInMotion.MODID;

public class ItemRegistry {

    //initialize the item
    public static Item testCart = new ItemFirstTrain().setUnlocalizedName("itemTest").setTextureName(MODID + ":itemTests");
    public static Fluid oil = new Oil("Oil"); //Just "Oil" for now TODO make variable for Oil
    public ItemRegistry(){}


    public void RegisterItems(){
        //register the item we initialized
        GameRegistry.registerItem(testCart, testCart.getUnlocalizedName().substring(5));
        FluidRegistry.registerFluid(oil);
    }
}
