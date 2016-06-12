package trains.utility;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import trains.entities.EntityTrainCore;
import trains.gui.GUITest;

public class ClientProxy extends CommonProxy {

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
