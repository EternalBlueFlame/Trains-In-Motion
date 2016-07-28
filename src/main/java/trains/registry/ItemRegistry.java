package trains.registry;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import trains.items.Bucket;
import trains.items.ItemFirstTrain;

import static trains.TrainsInMotion.MODID;

public class ItemRegistry {

    //initialize the item
    public static Item testCart = new ItemFirstTrain().setUnlocalizedName("itemTest").setTextureName(MODID + ":itemTests");

    public static final Item bucketOil = new Bucket(BlockRegistry.blockFluidOil).setUnlocalizedName("OilBucket").setContainerItem(Items.bucket);

    public ItemRegistry(){}

    public void RegisterItems(){
        //register the item we initialized
        GameRegistry.registerItem(testCart, testCart.getUnlocalizedName().substring(5));


        GameRegistry.registerItem(bucketOil, "OilBucket");
        FluidContainerRegistry.registerFluidContainer(BlockRegistry.fluidOil, new ItemStack(bucketOil), new ItemStack(Items.bucket));
    }
}
