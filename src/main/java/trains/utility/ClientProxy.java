package trains.utility;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import trains.entities.MinecartExtended;
import trains.gui.GUITest;

public class ClientProxy extends CommonProxy {
    //Register the client side of the gui
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUITest.GUI_ID: {
                if (player != null && player.ridingEntity instanceof MinecartExtended) {
                    return new GUITest(player.inventory, (MinecartExtended) player.ridingEntity);
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
