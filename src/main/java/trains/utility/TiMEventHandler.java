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

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientKeyPress(InputEvent.KeyInputEvent event){
        //on key pressed manager
        //TODO client thinks the train is an instance of net.minecraft.entity.item.EntityMinecartEmpty, not EntityTrainCore
        if(Minecraft.getMinecraft().thePlayer.ridingEntity instanceof EntityTrainCore) {
            if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(Keyboard.KEY_L));
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
                TrainsInMotion.keyChannel.sendToServer(new PacketGUI(GUITest.GUI_ID));
            }
        }
    }

    //mount rideable cart
    @SubscribeEvent
    public void entityInteractEvent(EntityInteractEvent event) {
        if (event.target instanceof EntityTrainCore && !event.entity.worldObj.isRemote && (event.target.riddenByEntity == null || event.target.riddenByEntity == event.entityPlayer)) {
            event.entityPlayer.mountEntity(event.target);
        }
    }


}
