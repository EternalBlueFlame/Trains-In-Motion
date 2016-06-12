package trains.utility;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.gui.GUITest;
import trains.networking.PacketGUI;
import trains.networking.PacketKeyPress;

public class TiMEventHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientKeyPress(InputEvent.KeyInputEvent event){
        //client doesn't know what its riding?
        //if(Minecraft.getMinecraft().thePlayer.ridingEntity instanceof EntityTrainCore) {
            if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(Keyboard.KEY_L));
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
                TrainsInMotion.keyChannel.sendToServer(new PacketGUI(GUITest.GUI_ID));
            }
        //}
    }


}
