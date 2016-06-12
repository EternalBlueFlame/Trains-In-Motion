package trains.utility;


import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.gui.GUITest;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;


public class CommonProxy implements IGuiHandler {

    public static KeyBinding[] keyBindings;

    public void setKeyBinding(String name, int value) {
        ClientRegistry.registerKeyBinding(new KeyBinding(name, value, TrainsInMotion.MODID));
    }
    @SideOnly(Side.SERVER)
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(player !=null && player.ridingEntity instanceof EntityTrainCore){
            return new InventoryHandler(player.inventory, (EntityTrainCore) player.ridingEntity);
        }
        return null;
    }
    @SideOnly(Side.CLIENT)
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUITest.GUI_ID: {
                if (player.ridingEntity instanceof EntityTrainCore) {
                    return new GUITest(player.inventory, (EntityTrainCore) player.ridingEntity);
                } else {
                    return null;
                }
            }

            default:{
                return null;
            }
        }
    }
}
