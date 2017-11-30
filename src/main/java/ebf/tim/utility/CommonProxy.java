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
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

import javax.annotation.Nullable;
import java.util.UUID;

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
            if (player.worldObj.getEntityByID(ID) instanceof GenericRailTransport) {
                return new TransportSlotManager(player.inventory, (GenericRailTransport) player.worldObj.getEntityByID(ID));
                //tile entities
            } else if (world.getTileEntity(x,y,z) instanceof TileEntityStorage){
                return new TileEntitySlotManager(player.inventory, (TileEntityStorage) world.getTileEntity(x,y,z));
            }
        }
        return null;
    }

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
    public static final Fluid fluidOil = new Fluid("Oil").setUnlocalizedName("fluid.oil").setBlock(blockFluidOil).setGaseous(false).setDensity(700);
    /**the diesel fluid*/
    public static final Fluid fluidDiesel = new Fluid("Diesel").setUnlocalizedName("fluid.diesel").setBlock(blockFluidDiesel).setGaseous(false).setDensity(500);
    /**the oil bucket*/
    public static ItemBucket bucketOil;
    /**the diesel bucket*/
    public static ItemBucket bucketDiesel;

    /**the crafting table for trains*/
    public static BlockDynamic trainTable = new BlockDynamic("blocktraintable", Material.wood, TrainsInMotion.blockTypes.CRAFTING);

    public static BlockRailCore railBlock = new BlockRailCore();
    /**
     * <h2>Server Register</h2>
     * Used for registering server only functions.
     * Also serves as a placeholder for the client function, which is actually used, so we don't get a missing function error.
     */
    public void register() {

        //register fluids
        FluidRegistry.registerFluid(fluidOil);
        FluidRegistry.registerFluid(fluidDiesel);
        //register fluid blocks
        blockFluidOil = new BlockTrainFluid(fluidOil, new MaterialLiquid(MapColor.blackColor));
        blockFluidOil.setBlockName("block.oil");
        GameRegistry.registerBlock(blockFluidOil, "block.oil");
        blockFluidDiesel = new BlockTrainFluid(fluidDiesel, new MaterialLiquid(MapColor.dirtColor)).setFlammable(true, 1);
        blockFluidDiesel.setBlockName("block.diesel");
        GameRegistry.registerBlock(blockFluidDiesel, "block.diesel");
        //register the buckets
        bucketOil = new ItemBucket(blockFluidOil);
        bucketOil.setCreativeTab(TrainsInMotion.creativeTab).setUnlocalizedName("item.oilbucket").setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketOil, "fluid.oil.bucket");
        FluidContainerRegistry.registerFluidContainer(fluidOil, new ItemStack(bucketOil), new ItemStack(Items.bucket));
        bucketDiesel = new ItemBucket(blockFluidDiesel);
        bucketDiesel.setCreativeTab(TrainsInMotion.creativeTab).setUnlocalizedName("item.dieselbucket").setContainerItem(Items.bucket);
        GameRegistry.registerItem(bucketDiesel, "fluid.diesel.bucket");
        FluidContainerRegistry.registerFluidContainer(fluidDiesel, new ItemStack(bucketDiesel), new ItemStack(Items.bucket));

        //register the train crafting table
        trainTable.setBlockName("block.traintable");
        GameRegistry.registerBlock(trainTable, "block.traintable");
        GameRegistry.registerTileEntity(TileEntityStorage.class, "StorageEntity");
        addRecipe(new ItemStack(trainTable, 1),  "WWW", "WIW", "WWW", 'W', Blocks.planks, 'I', Items.iron_ingot);

        railBlock.setCreativeTab(TrainsInMotion.creativeTab).setBlockName("block.timrail");
        GameRegistry.registerBlock(railBlock, "block.timrail");
        GameRegistry.registerTileEntity(RailTileEntity.class, "TiMRailEntity");
        addRecipe(new ItemStack(railBlock, 1),  "I I", "IWI", "IWI", 'W', Blocks.planks, 'I', Items.iron_ingot);
    }
}
