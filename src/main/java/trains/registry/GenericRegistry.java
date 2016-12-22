package trains.registry;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import trains.blocks.LampBlock;
import trains.blocks.Oil;
import trains.items.Bucket;

/**
 * <h2>Block registry</h2>
 * this class lists all the blocks provided by this mod.
 * If you need a reference to one of those blocks you have to call it from this class.
 */

public class GenericRegistry {
    //initialize the oil
    public static final Material Mat_Oil = new MaterialLiquid(MapColor.blackColor);
    public static final Fluid fluidOil = new Oil("Oil").setUnlocalizedName("Oil");
    public static BlockFluidClassic blockFluidOil;
    public static final Item bucketOil = new Bucket(GenericRegistry.blockFluidOil).setUnlocalizedName("OilBucket").setContainerItem(Items.bucket);

    //initialize the lamp block
    @SideOnly(Side.CLIENT)
    public static Block lampBlock = new LampBlock();


    /**
     * <h2>Block and Item register function</h2>
     * called by the main class to register the blocks and items
     * @see trains.TrainsInMotion#init(FMLInitializationEvent) 
     */
    public static void RegisterStuff(){
        /**
         * <h3>register Blocks</h3>
         */
        //register oil
        FluidRegistry.registerFluid(fluidOil);
        blockFluidOil = new BlockFluidClassic(fluidOil, Mat_Oil);
        GameRegistry.registerBlock(blockFluidOil, "OilBlock");

        /**
         * <h3>register Items</h3>
         */
        GameRegistry.registerItem(bucketOil, "OilBucket");
        FluidContainerRegistry.registerFluidContainer(GenericRegistry.fluidOil, new ItemStack(bucketOil), new ItemStack(Items.bucket));
    }

    /**
     * <h2>Client onlyBlock and Item register function</h2>
     * called by the main class to register the blocks and items, these are only on the client side.
     * @see trains.TrainsInMotion#init(FMLInitializationEvent)
     */
    @SideOnly(Side.CLIENT)
    public static void RegisterClientStuff(){
        GameRegistry.registerBlock(lampBlock, "lampblock");
        lampBlock.setLightLevel(1f);
    }

}
