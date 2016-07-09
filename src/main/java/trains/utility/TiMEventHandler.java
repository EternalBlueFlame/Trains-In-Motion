package trains.utility;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import org.lwjgl.input.Keyboard;
import trains.TrainsInMotion;
import trains.entities.MinecartExtended;
import trains.gui.GUITrain;
import trains.networking.PacketGUI;
import trains.networking.PacketKeyPress;

public class TiMEventHandler {
    /**
     * Manages the key press event, doesn't work for when a key is held, that would need to be in the onUpdate event.
     *
     * Most cases just send a packet to manage things
     * @see PacketKeyPress
     * @see PacketGUI
     *
     *
     * @param event the event of a key being pressed on client.
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientKeyPress(InputEvent.KeyInputEvent event){
        if(Minecraft.getMinecraft().thePlayer.ridingEntity instanceof MinecartExtended) {
            //for lamp
            if (Keyboard.isKeyDown(TrainsInMotion.parseKey(TrainsInMotion.instance.KeyLamp))) {
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(TrainsInMotion.parseKey(TrainsInMotion.instance.KeyLamp)));
            }
            //for inventory
            if (Keyboard.isKeyDown(TrainsInMotion.parseKey(TrainsInMotion.instance.KeyInventory))) {
                TrainsInMotion.keyChannel.sendToServer(new PacketGUI(GUITrain.GUI_ID));
            }
            //for speed change
            if(Keyboard.isKeyDown(TrainsInMotion.parseKey(TrainsInMotion.instance.KeyAccelerate))){
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(TrainsInMotion.parseKey(TrainsInMotion.instance.KeyAccelerate)));
            }
            if(Keyboard.isKeyDown(TrainsInMotion.parseKey(TrainsInMotion.instance.KeyReverse))){
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(TrainsInMotion.parseKey(TrainsInMotion.instance.KeyReverse)));
            }
        }
    }

    /**
     * this event manages when the player tries to interact with the train/rollingstock to ride it.
     * IDE reports that the function is never used, which is lies.
     */
    @SubscribeEvent
    public void entityInteractEvent(EntityInteractEvent event) {
        if (event.target instanceof MinecartExtended && !event.entity.worldObj.isRemote && (event.target.riddenByEntity == null || event.target.riddenByEntity == event.entityPlayer)) {
            event.entityPlayer.mountEntity(event.target);
        }
    }

}
