package trains.utility;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import trains.entities.EntityTrainCore;
import trains.gui.train.GUISteam;
import trains.gui.trainhandler.SteamInventoryHandler;



public class CommonProxy implements IGuiHandler {
    /**
     *
     *
     * <h1> Server GUI </h1>
     *
     *
     * setup the GUI element for the server, this is actually just the inventory manager.
     * @see SteamInventoryHandler
     *
     * define the stuff for client here too, however this is overridden in client proxt to define the actual use
     * @see ClientProxy#getClientGuiElement(int, EntityPlayer, World, int, int, int)
     */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        //handle GUI direction for trains
        if (player != null && player.ridingEntity instanceof EntityTrainCore) {
            switch (ID) {
                case GUISteam.GUI_ID: {
                    return new SteamInventoryHandler(player.inventory, (EntityTrainCore) player.ridingEntity);
                }

                default: {
                    return null;
                }
            }
        } else {
            //handle GUI direction for rollingstock
            return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {return null;}
    public void registerRenderers() {}

    /**
     *
     *
     * <h1> OnTick </h1>
     *
     *
     * this is used to force events from the main thread of the mod, it can create a lot of lag sometimes.
     * @see ClientProxy#onTick(TickEvent.ClientTickEvent) for actual use
     * @param tick the client tick event from the main thread
     */
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent tick) {}
}
