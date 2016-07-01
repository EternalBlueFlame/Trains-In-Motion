package trains.utility;


import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import trains.TrainsInMotion;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import trains.entities.MinecartExtended;


public class CommonProxy implements IGuiHandler {

    /**
     * defines the GUI element for the server, this is actually just the inventory manager
     * @see InventoryHandler
     *
     * we have to define the stuff for client here too, but we don't actually use them because server never rendered anything
     * @see ClientProxy
     */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(player !=null && player.ridingEntity instanceof MinecartExtended){
            return new InventoryHandler(player.inventory, (MinecartExtended) player.ridingEntity);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {return null;}
    public void registerRenderers() {}
}
