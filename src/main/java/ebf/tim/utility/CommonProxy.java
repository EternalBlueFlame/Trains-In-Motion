package ebf.tim.utility;


import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import ebf.tim.TrainsInMotion;
import ebf.tim.blocks.BlockDynamic;
import ebf.tim.blocks.RailTileEntity;
import ebf.tim.blocks.rails.BlockRailCore;
import ebf.tim.blocks.BlockTrainFluid;
import ebf.tim.blocks.TileEntityStorage;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.gui.GUIAdminBook;
import ebf.tim.items.ItemAdminBook;
import ebf.tim.items.ItemRail;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

import javax.annotation.Nullable;
import java.util.*;

import static cpw.mods.fml.common.registry.GameRegistry.addRecipe;


/**
 * <h1>Common and Server proxy</h1>
 * defines some of the more important server only, and dual sided functionality that runs on the main thread of the mod.
 * @author Eternal Blue Flame
 */
public class CommonProxy implements IGuiHandler {

    /**
     * <h2> Server GUI Redirect </h2>
     * Mostly a redirect between the event handler and the actual Container Handler
     *
     * the inventory manager that server uses for this menu is defined in
     * @see ClientProxy#getClientGuiElement(int, EntityPlayer, World, int, int, int)
     */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        //Trains
        if (player != null) {
            if (player.worldObj.getEntityByID(ID) instanceof GenericRailTransport && !((GenericRailTransport) player.worldObj.getEntityByID(ID)).hasCustomGUI()) {
                return new TransportSlotManager(player.inventory, (GenericRailTransport) player.worldObj.getEntityByID(ID));
                //tile entities
            } else if (world.getTileEntity(x,y,z) instanceof TileEntityStorage){
                return new TileEntitySlotManager(player.inventory, (TileEntityStorage) world.getTileEntity(x,y,z));
            }
        }
        return null;
    }

    public void adminGui(String datacsv){};

    public boolean isClient(){return false;}

    /**
     * <h2>Load config</h2>
     * this loads the config values that will only effect server.
     */
    public void loadConfig(Configuration config){}

    /**
     * <h2>load entity from UUID</h2>
     * This checks every entity in every dimension for one with the proper UUID,
     * this is very similar to the system used in 1.8+.
     * NOTE: this is SERVER ONLY.
     *
     * We can't use a foreach loop, if we do it will very often throw a java.util.ConcurrentModificationException
     */
    @Nullable
    public static Entity getEntityFromUuid(UUID uuid) {
        //loop for dimensions, even ones from mods.
        for (int w=0; w < MinecraftServer.getServer().worldServers.length; w++) {
            if (MinecraftServer.getServer().worldServers[w] != null) {
                //if the server isn't null, loop for the entities loaded in that server
                for (int i=0; i< MinecraftServer.getServer().worldServers[w].getLoadedEntityList().size();i++) {
                    //if it's an entity, not null, and has a matching UUID, then return it.
                    if (MinecraftServer.getServer().worldServers[w].getLoadedEntityList().get(i) instanceof Entity &&
                            ((Entity) MinecraftServer.getServer().worldServers[w].getLoadedEntityList().get(i)).getUniqueID().equals(uuid)) {
                        return (Entity) MinecraftServer.getServer().worldServers[w].getLoadedEntityList().get(i);
                    }
                }
            }
        }
        return null;
    }


    /**
     * <h2>registry</h2>
     * placeholder code for the client registration.
     * @see ClientProxy#register) for actual use:
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {return null;}


    /*
     * <h1> registration </h1>
     */

    //initialize the oil
    /**the oil fluid block*/
    public static BlockTrainFluid blockFluidOil;
    /**the diesel fluid block*/
    public static BlockTrainFluid blockFluidDiesel;
    /**the oil fluid*/
    public static Fluid fluidOil = new Fluid("Oil");
    /**the diesel fluid*/
    public static Fluid fluidDiesel = new Fluid("Diesel");
    /**the oil bucket*/
    public static ItemBucket bucketOil;
    /**the diesel bucket*/
    public static ItemBucket bucketDiesel;

    /**the crafting table for trains*/
    public static BlockDynamic trainTable = new BlockDynamic("blocktraintable", Material.wood, TrainsInMotion.blockTypes.CRAFTING);

    public static BlockRailCore railBlock = new BlockRailCore();


    public static Item railItem = new ItemRail();
    /**
     * <h2>Server Register</h2>
     * Used for registering server only functions.
     * Also serves as a placeholder for the client function, which is actually used, so we don't get a missing function error.
     */
    public void register() {

        fluidOil = RegisterFluid(fluidOil, "oil", false, 700);
        blockFluidOil = RegisterFluidBlock(fluidOil, "oil", MapColor.blackColor);
        bucketOil = RegisterFluidBucket(fluidOil, blockFluidOil, "oil", TrainsInMotion.creativeTab);
        fluidDiesel = RegisterFluid(fluidDiesel, "diesel", false, 500);
        blockFluidDiesel = RegisterFluidBlock(fluidDiesel, "diesel", MapColor.sandColor);
        bucketDiesel = RegisterFluidBucket(fluidDiesel, blockFluidDiesel, "diesel", TrainsInMotion.creativeTab);

        RegisterItem(new ItemAdminBook(), "adminbook", "adminbook", TrainsInMotion.creativeTab, null);

        RegisterBlock(railBlock, "block.timrail", "block.timrail", null);

        GameRegistry.registerTileEntity(TileEntityStorage.class, "StorageEntity");
        GameRegistry.registerTileEntity(RailTileEntity.class, "TiMRailEntity");

        //register the train crafting table
        addRecipe(new ItemStack(RegisterBlock(trainTable, "block.traintable", "block.traintable", TrainsInMotion.creativeTab),1),
                "WWW", "WIW", "WWW", 'W', Blocks.planks, 'I', Items.iron_ingot);

        addRecipe(new ItemStack(RegisterItem(new ItemRail(), "item.timrail","item.timrail",TrainsInMotion.creativeTab, null), 1),
                "I I", "IWI", "IWI", 'W', Blocks.planks, 'I', Items.iron_ingot);
    }

    public static Fluid RegisterFluid(Fluid fluid, String unlocalizedName, boolean isGaseous, int density){
        fluid.setUnlocalizedName(unlocalizedName).setGaseous(isGaseous).setDensity(density);
        FluidRegistry.registerFluid(fluid);
        if (TrainsInMotion.proxy.isClient() && fluid.getUnlocalizedName().equals(StatCollector.translateToLocal(fluid.getUnlocalizedName()))){
            DebugUtil.println("Fluid missing lang entry: " + fluid.getUnlocalizedName());
        }

        return fluid;
    }

    public static BlockTrainFluid RegisterFluidBlock(Fluid fluid, String unlocalizedName, MapColor color){
        BlockTrainFluid block = new BlockTrainFluid(fluid, new MaterialLiquid(color));
        block.setBlockName("block."+unlocalizedName);
        GameRegistry.registerBlock(block, "block."+unlocalizedName);
        if (TrainsInMotion.proxy.isClient() && block.getUnlocalizedName().equals(StatCollector.translateToLocal(block.getUnlocalizedName()))){
            DebugUtil.println("Block missing lang entry: " + block.getUnlocalizedName());
        }

        fluid.setBlock(block);
        return block;
    }

    public static ItemBucket RegisterFluidBucket(Fluid fluid, Block block, String unlocalizedName, CreativeTabs tab){
        ItemBucket bucket = new ItemBucket(block);
        bucket.setCreativeTab(tab).setUnlocalizedName("item." + unlocalizedName + ".bucket").setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucket, "fluid." + unlocalizedName + ".bucket");
        if (TrainsInMotion.proxy.isClient() && bucket.getUnlocalizedName().equals(StatCollector.translateToLocal(block.getUnlocalizedName()))){
            DebugUtil.println("Item missing lang entry: " + bucket.getUnlocalizedName());
        }

        FluidContainerRegistry.registerFluidContainer(fluid, new ItemStack(bucket), new ItemStack(Items.bucket));
        return bucket;
    }



    public static Item RegisterItem(Item itm, String name, String unlocalizedName, @Nullable CreativeTabs tab, @Nullable Item container){
        if (tab!=null) {
            itm.setCreativeTab(tab);
        }
        if (container!=null){
            itm.setContainerItem(container);
        }
        if (!unlocalizedName.equals("")){
            itm.setUnlocalizedName(unlocalizedName);
        }
        GameRegistry.registerItem(itm, name);
        if (TrainsInMotion.proxy.isClient() && itm.getUnlocalizedName().equals(StatCollector.translateToLocal(itm.getUnlocalizedName()))){
            DebugUtil.println("Item missing lang entry: " + itm.getUnlocalizedName());
        }
        return itm;
    }


    public static Block RegisterBlock(Block block, String name, String unlocalizedName, @Nullable CreativeTabs tab){
        if (tab!=null){
            block.setCreativeTab(tab);
        }
        if (!unlocalizedName.equals("")){
            block.setBlockName(unlocalizedName);
        }
        GameRegistry.registerBlock(block, name);
        if (TrainsInMotion.proxy.isClient() && block.getUnlocalizedName().equals(StatCollector.translateToLocal(block.getUnlocalizedName()))){
            DebugUtil.println("Block missing lang entry: " + block.getUnlocalizedName());
        }
        return block;
    }
}
