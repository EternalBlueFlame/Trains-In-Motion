package trains.utility;


import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import trains.TrainsInMotion;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import trains.entities.MinecartExtended;


public class CommonProxy implements IGuiHandler {
    //setup the key bindings
    public void setKeyBinding(String name, int value) {
        ClientRegistry.registerKeyBinding(new KeyBinding(name, value, TrainsInMotion.MODID));
    }
    //manage the server-side GUI stuff
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(player !=null && player.ridingEntity instanceof MinecartExtended){
            return new InventoryHandler(player.inventory, (MinecartExtended) player.ridingEntity);
        }
        return null;
    }
    //do this in ClientProxy
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {return null;}
    //do this in client proxy, this needs to be here anyway for stability reasons.
    public void registerRenderers() {}
}
