package ebf.tim.utility;


import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import ebf.tim.TrainsInMotion;
import ebf.tim.api.SkinRegistry;
import ebf.tim.blocks.BlockDynamic;
import ebf.tim.blocks.TileEntityStorage;
import ebf.tim.blocks.rails.BlockRailCore;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.items.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static cpw.mods.fml.common.registry.GameRegistry.addRecipe;
import static ebf.tim.registry.TiMGenericRegistry.*;


/**
 * <h1>Common and Server proxy</h1>
 * defines some of the more important server only, and dual sided functionality that runs on the main thread of the mod.
 * @author Eternal Blue Flame
 */
public class CommonProxy implements IGuiHandler {


    public static EventManagerServer eventManagerServer = new EventManagerServer();
    private static HashMap<Integer,BlockNBTMap> railMap = new HashMap<Integer, BlockNBTMap>();
    public static Map<String, List<Recipe>> recipesInMods = new HashMap<>();

    public static BlockNBTMap clientList = new BlockNBTMap();


    public static BlockNBTMap getRailMap(World worldObj){
        if(!railMap.containsKey(worldObj.provider.dimensionId)){

            MapStorage storage = worldObj.perWorldStorage;
            BlockNBTMap m = (BlockNBTMap) storage.loadData(BlockNBTMap.class, "railMap");

            if (m == null) {
                m=new BlockNBTMap();
                storage.setData("railMap", m);
            }
            railMap.put(worldObj.provider.dimensionId, m);
        }
        return railMap.get(worldObj.provider.dimensionId);
    }

    public static void setRailMap(World worldObj, BlockNBTMap map){
        railMap.put(worldObj.provider.dimensionId, map).setDirty(true);
    }



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
    public void loadConfig(FMLPreInitializationEvent event){

        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        config.addCustomCategoryComment("Debug (Common)", "Used on server and client.");
        SkinRegistry.forceSkinRegister = config.getBoolean("ForceSkinRegister", "Debug (Common)", false,
                "Forces skins to register even if the add-on for said skin is not available, doesn't cause instability just uses unnecessary ram.");

        config.addCustomCategoryComment("Debug (Common, IDE Only)", "Only runs from IDE instances.");
        SkinRegistry.debugSkinRegistration = config.getBoolean("DebugSkinRegister", "Debug (Common, IDE Only)",false,
                "Logs all skin registration events to debug console.");

        config.save();



    }

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
                for (int i=0; i< MinecraftServer.getServer().worldServers[w].loadedEntityList.size();i++) {
                    //if it's an entity, not null, and has a matching UUID, then return it.
                    if (MinecraftServer.getServer().worldServers[w].loadedEntityList.get(i) instanceof Entity &&
                            ((Entity) MinecraftServer.getServer().worldServers[w].loadedEntityList.get(i)).getUniqueID().equals(uuid)) {
                        return (Entity) MinecraftServer.getServer().worldServers[w].loadedEntityList.get(i);
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


    public Object getTESR(){return null;}
    public Object getEntityRender(){return null;}
    public Object getNullRender(){return null;}

    /*
     * <h1> registration </h1>
     */

    /**the oil fluid*/
    public static Fluid fluidOil = new Fluid("Oil");
    /**the diesel fluid*/
    public static Fluid fluidDiesel = new Fluid("Diesel");

    /**the crafting table for trains*/
    public static BlockDynamic trainTable = new BlockDynamic("blocktraintable", new Material(MapColor.mapColorArray[13]), 0);

    public static BlockDynamic railTable = new BlockDynamic("blockrailtable", new Material(MapColor.mapColorArray[6]), 1);

    public static BlockRailCore railBlock = new BlockRailCore();

    public static Item railItem;

    /**
     * <h2>Server Register</h2>
     * Used for registering server only functions.
     * Also serves as a placeholder for the client function, which is actually used, so we don't get a missing function error.
     */
    public void register() {

        RegisterFluid(TrainsInMotion.proxy.isClient(),fluidOil, TrainsInMotion.MODID, "oil", false, 700,MapColor.blackColor, TrainsInMotion.creativeTab);
        RegisterFluid(TrainsInMotion.proxy.isClient(),fluidDiesel, TrainsInMotion.MODID, "diesel", false, 500, MapColor.sandColor, TrainsInMotion.creativeTab);


        RegisterItem(TrainsInMotion.proxy.isClient(),new ItemAdminBook(),TrainsInMotion.MODID, "adminbook", TrainsInMotion.creativeTab);
        RegisterItem(TrainsInMotion.proxy.isClient(),new ItemCraftGuide(),TrainsInMotion.MODID, "craftbook", TrainsInMotion.creativeTab);

        RegisterItem(TrainsInMotion.proxy.isClient(),new ItemKey(),TrainsInMotion.MODID,  "transportkey", TrainsInMotion.creativeTab);
        RegisterItem(TrainsInMotion.proxy.isClient(),new ItemTicket(),TrainsInMotion.MODID,  "transportticket", TrainsInMotion.creativeTab);
        railItem = RegisterItem(TrainsInMotion.proxy.isClient(),new ItemRail(),TrainsInMotion.MODID,  "timrail", TrainsInMotion.creativeTab);

        registerBlock(isClient(), railBlock, null, "block.timrail", null, getTESR());

        //register the train crafting table
        addRecipe(new ItemStack(registerBlock(isClient(), trainTable, TrainsInMotion.creativeTab,"block.traintable", null, null),1),
                "WWW", "WIW", "WWW", 'W', Blocks.planks, 'I', Items.iron_ingot);

        addRecipe(new ItemStack(registerBlock(isClient(), railTable, TrainsInMotion.creativeTab,"block.railtable", null, null),1),
                "III", "I I", "I I", 'I', Items.iron_ingot);
    }

}
