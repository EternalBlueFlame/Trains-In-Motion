package trains.utility;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.entities.GenericRailTransport;
import trains.networking.PacketKeyPress;
import trains.networking.PacketMount;

import java.util.UUID;

/**
 * <h1>event management</h1>
 * used to manage specific events that can't be predicted, like player key presses.
 * @author Eternal Blue Flame
 */
public class EventManager {

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
            if (ClientProxy.KeyLamp.isPressed() ) {
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(0, Minecraft.getMinecraft().thePlayer.ridingEntity.getPersistentID()));
                ((EntityTrainCore) Minecraft.getMinecraft().thePlayer.ridingEntity).lamp.isOn = ! ((EntityTrainCore) Minecraft.getMinecraft().thePlayer.ridingEntity).lamp.isOn;
            }
            //for inventory
            if (ClientProxy.KeyInventory.isPressed()) {
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(1, Minecraft.getMinecraft().thePlayer.ridingEntity.getPersistentID()));
                System.out.println("pressed inventory key on" + TrainsInMotion.proxy.getEntityFromUuid(Minecraft.getMinecraft().thePlayer.ridingEntity.getPersistentID()));
            }
            //for speed change
            if(ClientProxy.KeyAccelerate.isPressed()){
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(2, Minecraft.getMinecraft().thePlayer.ridingEntity.getPersistentID()));
                ((EntityTrainCore) Minecraft.getMinecraft().thePlayer.ridingEntity).setAcceleration(true);
            } else if(ClientProxy.KeyReverse.getIsKeyPressed()){
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(3, Minecraft.getMinecraft().thePlayer.ridingEntity.getPersistentID()));
                ((EntityTrainCore) Minecraft.getMinecraft().thePlayer.ridingEntity).setAcceleration(false);
            }
            //player dismount
            if (Minecraft.getMinecraft().gameSettings.keyBindSneak.isPressed()){
                dismountEntity(Minecraft.getMinecraft().thePlayer.getPersistentID());
            }
        }
    }

    /**
     * <h2>Entity Interaction</h2>
     * this event manages when the player tries to interact with the train/rollingstock to ride it or use an item on it.
     */
    @SubscribeEvent
    public void entityInteractEvent(EntityInteractEvent event) {
        if (event.target instanceof HitboxHandler.multipartHitbox
                && event.entity.worldObj.isRemote) {
            mountEntity(((HitboxHandler.multipartHitbox) event.target).parent, event.entityPlayer);
        }
    }


    public static void mountEntity(GenericRailTransport transport, EntityPlayer player){

        for (int i = 0; i < transport.getRiderOffsets().length; i++) {
            System.out.println("tried to interact" + transport.riddenByEntities.get(i).getMostSignificantBits() + " : " + transport.riddenByEntities.get(i).getLeastSignificantBits());
            if (transport.riddenByEntities.get(i).getLeastSignificantBits() == 0 && transport.riddenByEntities.get(i).getMostSignificantBits() == 0) {
                System.out.println("id was valid");
                player.mountEntity(transport);
                transport.riddenByEntities.set(i, player.getUniqueID());
                System.out.println("tried to interact" + transport.riddenByEntities.get(i).getMostSignificantBits() + " : " + transport.riddenByEntities.get(i).getLeastSignificantBits());
                break;
            }
        }

        TrainsInMotion.keyChannel.sendToServer(new PacketMount(transport.getPersistentID()));
    }

    public static void dismountEntity(UUID player){
        Entity entityPlayer = TrainsInMotion.proxy.getEntityFromUuid(player);
        if (entityPlayer instanceof EntityPlayer) {
            if (entityPlayer.ridingEntity instanceof GenericRailTransport) {
                ((GenericRailTransport) entityPlayer.ridingEntity).riddenByEntities.remove(player);
                ((EntityPlayer) entityPlayer).dismountEntity(entityPlayer.ridingEntity);
            }
        }
    }
}
