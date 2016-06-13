package trains.utility;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import org.lwjgl.input.Keyboard;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.gui.GUITest;
import trains.networking.PacketGUI;
import trains.networking.PacketKeyPress;

public class TiMEventHandler {
    //manage on key press, not for while a key is held, only for features that take a single key press
    //TODO move these to something that can be changed in a config file
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientKeyPress(InputEvent.KeyInputEvent event){
        if(Minecraft.getMinecraft().thePlayer.ridingEntity instanceof EntityTrainCore) {
            //key for lamp
            if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(Keyboard.KEY_L));
            }
            //key for inventory
            if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
                TrainsInMotion.keyChannel.sendToServer(new PacketGUI(GUITest.GUI_ID));
            }
        }
    }

    //make trains rideable
    @SubscribeEvent
    public void entityInteractEvent(EntityInteractEvent event) {
        if (event.target instanceof EntityTrainCore && !event.entity.worldObj.isRemote && (event.target.riddenByEntity == null || event.target.riddenByEntity == event.entityPlayer)) {
            event.entityPlayer.mountEntity(event.target);
        }
    }


}
