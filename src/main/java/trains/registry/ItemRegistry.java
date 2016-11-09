package trains.registry;


import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import trains.items.Bucket;
import trains.items.trains.ItemFirstTrain;

import static trains.TrainsInMotion.MODID;

/**
 * <h2>Item registry</h2>
 * this class lists all the items provided by this mod.
 * If you need a reference to one of those items you have to call it from this class.
 */
public class ItemRegistry {
    //initialize the item
    public static Item testCart = new ItemFirstTrain().setUnlocalizedName("itemTest").setTextureName(MODID + ":itemTests");
    public static final Item bucketOil = new Bucket(BlockRegistry.blockFluidOil).setUnlocalizedName("OilBucket").setContainerItem(Items.bucket);

    /**
     * <h2>Item register function</h2>
     * called by the main class to register the items
     * @see trains.TrainsInMotion#init(FMLInitializationEvent)
     */
    public static void RegisterItems(){
        //register the item we initialized
        GameRegistry.registerItem(testCart, testCart.getUnlocalizedName().substring(5));

        GameRegistry.registerItem(bucketOil, "OilBucket");
        FluidContainerRegistry.registerFluidContainer(BlockRegistry.fluidOil, new ItemStack(bucketOil), new ItemStack(Items.bucket));
    }
}
