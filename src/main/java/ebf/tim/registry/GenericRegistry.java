package ebf.tim.registry;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import ebf.tim.TrainsInMotion;
import ebf.tim.blocks.LampBlock;
import ebf.tim.blocks.Oil;
import ebf.tim.items.Bucket;
import ebf.tim.tileentities.TileEntityStorage;
import ebf.tim.utility.BlockDynamic;

import static cpw.mods.fml.common.registry.GameRegistry.addRecipe;

/**
 * <h1>Generic registry</h1>
 * this class lists all the blocks, fluids, and non-train/rollingstock items provided by this mod.
 * If you need a reference to one of those you have to call it from this class.
 * @author Eternal Blue Flame
 * @author Justice
 */

public class GenericRegistry {
    //initialize the oil
    public static final Fluid fluidOil = new Oil("Oil").setUnlocalizedName("fluid.oil");
    public static BlockFluidClassic blockFluidOil;
    public static final Item bucketOil = new Bucket(GenericRegistry.blockFluidOil).setUnlocalizedName("item.oilbucket").setContainerItem(Items.bucket);

    //define the train crafting table.
    public static BlockDynamic trainTable = new BlockDynamic("blocktraintable",Material.wood, TrainsInMotion.blockTypes.CRAFTING);

    //initialize the lamp block
    public static Block lampBlock;


    /**
     * <h2>Block and Item register function</h2>
     * called by the main class to register the blocks and items
     * @see TrainsInMotion#init(FMLInitializationEvent)
     */
    public static void RegisterStuff(){
        /**
         * <h3>register Blocks</h3>
         */
        //register oil
        FluidRegistry.registerFluid(fluidOil);
        blockFluidOil = new BlockFluidClassic(fluidOil, new MaterialLiquid(MapColor.blackColor));
        GameRegistry.registerBlock(blockFluidOil, "OilBlock");

        GameRegistry.registerBlock(trainTable, "TrainTable");
        GameRegistry.registerTileEntity(TileEntityStorage.class, "StorageEntity");
        addRecipe(new ItemStack(trainTable, 1),  "WWW", "WIW", "WWW", 'W', Blocks.planks, 'I', Items.iron_ingot);

        /**
         * <h3>register Items</h3>
         */
        GameRegistry.registerItem(bucketOil, "OilBucket");
        FluidContainerRegistry.registerFluidContainer(GenericRegistry.fluidOil, new ItemStack(bucketOil), new ItemStack(Items.bucket));
    }

    /**
     * <h2>Client onlyBlock and Item register function</h2>
     * called by the main class to register the blocks and items, these are only on the client side.
     * @see TrainsInMotion#init(FMLInitializationEvent)
     */
    @SideOnly(Side.CLIENT)
    public static void RegisterClientStuff(){
        lampBlock = new LampBlock();
        GameRegistry.registerBlock(lampBlock, "lampblock");
        lampBlock.setLightLevel(1f);
    }

}
