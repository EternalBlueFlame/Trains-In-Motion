package trains.utility;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.networking.PacketKeyPress;

public class EventHandler {

    /**
     * <h2>Keybind management</h2>
     * called when a client presses a key. this coveres pretty much everything.
     * Most cases just send a packet to manage things
     * @see PacketKeyPress
     *
     * Credit to Ferdinand for help with this function.
     *
     * @param event the event of a key being pressed on client.
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientKeyPress(InputEvent.KeyInputEvent event){
        if(Minecraft.getMinecraft().thePlayer.ridingEntity instanceof EntityTrainCore) {
            //for lamp
            if (ClientProxy.KeyLamp.getIsKeyPressed()) {
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(0));
            }
            //for inventory
            if (ClientProxy.KeyInventory.getIsKeyPressed()) {
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(1));
            }
            //for speed change
            if(ClientProxy.KeyAccelerate.getIsKeyPressed()){
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(2));
            } else if(ClientProxy.KeyReverse.getIsKeyPressed()){
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(3));
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
