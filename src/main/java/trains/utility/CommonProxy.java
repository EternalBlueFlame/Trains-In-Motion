package trains.utility;


import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import trains.entities.EntityTrainCore;
import trains.gui.trainhandler.SteamInventoryHandler;


public class CommonProxy implements IGuiHandler {

    /**
     * defines the GUI element for the server, this is actually just the inventory manager
     * @see SteamInventoryHandler
     *
     * we have to define the stuff for client here too, but we don't actually use them because server never rendered anything
     * @see ClientProxy
     */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(player !=null && player.ridingEntity instanceof EntityTrainCore){
            return new SteamInventoryHandler(player.inventory, (EntityTrainCore) player.ridingEntity);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {return null;}
    public void registerRenderers() {}
}
