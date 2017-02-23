package trains.utility;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.entities.GenericRailTransport;
import trains.tileentities.TileEntityStorage;

import javax.annotation.Nullable;
import java.util.UUID;


/**
 * <h1>Common and Server proxy</h1>
 * defines some of the more important server only, and dual sided functionality that runs on the main thread of the mod.
 * @author Eternal Blue Flame
 */
public class CommonProxy implements IGuiHandler {

    public static int UpdateFrequency =10;

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
                return new ContainerHandler(player.inventory, (GenericRailTransport) player.worldObj.getEntityByID(ID), false);
                //tile entities
            } else if (world.getTileEntity(x,y,z) instanceof TileEntityStorage){
                return new ContainerHandler(player.inventory, (TileEntityStorage) world.getTileEntity(x,y,z), true);
            }
        }
        return null;
    }

    /**
     * <h2>Load config</h2>
     * this loads the config values that will only effect server.
     */
    public void loadConfig(Configuration config){
        config.addCustomCategoryComment("Update Packet frequency (Server only)", "The frequency (in Ticks) the server sends the packet of train and rollingstock data to client. Must be between 1 and 20.");
        UpdateFrequency = config.get(Configuration.CATEGORY_GENERAL, "UpdateFrequency", 10).getInt();
        //Just in case someone decides to put in a wrong value, clamp it.
        if (UpdateFrequency>20){
            UpdateFrequency=20;
        } else if (UpdateFrequency<1){
            UpdateFrequency=1;
        }
    }

    /**
     * <h2>load entity from UUID</h2>
     * this is very similar to the system used in 1.8+ the difference is that we override this in client proxy so we can only check the loaded world.
     * There are also a second version specifically for Generic Rail Transport.
     *
     * We can't use a foreach loop, if we do it will very often throw a java.util.ConcurrentModificationException
     */
    @Nullable
    public static GenericRailTransport getTransportFromUuid(UUID uuid) {
        for (int w=0; w < MinecraftServer.getServer().worldServers.length; w++) {
            if (MinecraftServer.getServer().worldServers[w] != null) {
                for (int i=0; i< MinecraftServer.getServer().worldServers[w].getLoadedEntityList().size();i++) {
                    if (MinecraftServer.getServer().worldServers[w].getLoadedEntityList().get(i) instanceof GenericRailTransport &&
                            ((GenericRailTransport) MinecraftServer.getServer().worldServers[w].getLoadedEntityList().get(i)).getPersistentID().equals(uuid)) {
                        return (GenericRailTransport) MinecraftServer.getServer().worldServers[w].getLoadedEntityList().get(i);
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    public Entity getEntityFromUuid(UUID uuid) {
        for (int w=0; w < MinecraftServer.getServer().worldServers.length; w++) {
            if (MinecraftServer.getServer().worldServers[w] != null) {
                for (int i=0; i< MinecraftServer.getServer().worldServers[w].getLoadedEntityList().size();i++) {
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

    public void register() {}

    /**
     * <h2> OnTick </h2>
     *
     * this is used to force events from the main thread of the mod, it can create a lot of lag sometimes.
     * @see ClientProxy#onTick(TickEvent.ClientTickEvent) for actual use
     * @param tick the client tick event from the main thread
     */
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent tick) {}
}
