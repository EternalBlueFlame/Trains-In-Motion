package trains.utility;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.gui.train.GUISteam;
import trains.gui.trainhandler.SteamInventoryHandler;



public class CommonProxy implements IGuiHandler {
    /**
     * <h2> Server GUI Redirect </h2>
     * Mostly a redirect between the event handler and the actual Inventory Handler
     *
     *
     * define the stuff for client here too, however this is overridden in client proxy so that way it doesn't get run on server.
     * @see ClientProxy#getClientGuiElement(int, EntityPlayer, World, int, int, int)
     */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        //Trains
        if (player != null && player.ridingEntity instanceof EntityTrainCore) {
            switch (ID) {
                case TrainsInMotion.STEAM_GUI_ID: {
                    return new SteamInventoryHandler(player.inventory, (EntityTrainCore) player.ridingEntity);
                }

                default: {return null;}
            }
        } else {
            //Rollingstock
            return null;
        }
    }

    /**
     * <h2>render registry</h2>
     * placeholder code for the render registration.
     * @see ClientProxy#registerRenderers()
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {return null;}
    public void registerRenderers() {}

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
