package trains.utility;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import org.lwjgl.input.Keyboard;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.gui.train.GUISteam;
import trains.networking.PacketGUI;
import trains.networking.PacketKeyPress;

public class EventHandler {

    /**
     * <h2>Keybind management</h2>
     * called when a client presses a key. this coveres pretty much everything.
     * Most cases just send a packet to manage things
     * @see PacketKeyPress
     * @see PacketGUI
     *
     * @param event the event of a key being pressed on client.
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientKeyPress(InputEvent.KeyInputEvent event){
        if(Minecraft.getMinecraft().thePlayer.ridingEntity instanceof EntityTrainCore) {
            //for lamp
            if (Keyboard.isKeyDown(TrainsInMotion.parseKey(TrainsInMotion.KeyLamp))) {
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(TrainsInMotion.parseKey(TrainsInMotion.KeyLamp)));
            }
            //for inventory
            if (Keyboard.isKeyDown(TrainsInMotion.parseKey(TrainsInMotion.KeyInventory))) {
                TrainsInMotion.keyChannel.sendToServer(new PacketGUI(TrainsInMotion.STEAM_GUI_ID));
            }
            //for speed change
            if(Keyboard.isKeyDown(TrainsInMotion.parseKey(TrainsInMotion.KeyAccelerate))){
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(TrainsInMotion.parseKey(TrainsInMotion.KeyAccelerate)));
            }
            if(Keyboard.isKeyDown(TrainsInMotion.parseKey(TrainsInMotion.KeyReverse))){
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(TrainsInMotion.parseKey(TrainsInMotion.KeyReverse)));
            }
        }
    }

    /**
     * <h2>Entity Interaction</h2>
     * this event manages when the player tries to interact with the train/rollingstock to ride it or use an item on it.
     */
    @SubscribeEvent
    public void entityInteractEvent(EntityInteractEvent event) {
        if (event.target instanceof EntityTrainCore
                && !event.entity.worldObj.isRemote
                && event.target.riddenByEntity == null) {
            event.entityPlayer.mountEntity(event.target);
        }
    }

    /**
     * <h2>Entity destruction</h2>
     * this event manages when the player tries to break a train or rollingstock
     * TODO: manage breaking for non-creative mode
     * TODO: be sure damagesource is not a projectile.
     */
    @SubscribeEvent
    public void attackEntityEvent(AttackEntityEvent event){
        if (event.target instanceof EntityTrainCore && event.entityPlayer.capabilities.isCreativeMode){
            for (EntityMinecart cart : ((EntityTrainCore)event.target).bogie){
                cart.worldObj.removeEntity(cart);
            }

            event.target.worldObj.removeEntity(event.target);
        }
    }

}
