package trains.utility;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import trains.TrainsInMotion;
import trains.entities.EntitySeat;
import trains.entities.EntityTrainCore;
import trains.entities.GenericRailTransport;
import trains.entities.rollingstock.EntityVATLogCar;
import trains.networking.PacketKeyPress;
import trains.networking.PacketMount;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        if(player.ridingEntity instanceof GenericRailTransport || player.ridingEntity instanceof EntitySeat) {
            //for lamp
            if (ClientProxy.KeyLamp.isPressed() ) {
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(0, player.ridingEntity.getEntityId()));
                ((GenericRailTransport) player.ridingEntity).lamp.isOn = ! ((EntityTrainCore) player.ridingEntity).lamp.isOn;
            }
            //for inventory
            if (ClientProxy.KeyInventory.isPressed()) {
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(1, player.ridingEntity.getEntityId()));
            }
            if (player.ridingEntity instanceof EntityTrainCore) {
                //for speed change
                if (ClientProxy.KeyAccelerate.isPressed()) {
                    TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(2, player.ridingEntity.getEntityId()));
                    ((EntityTrainCore) player.ridingEntity).setAcceleration(true);
                } else if (ClientProxy.KeyReverse.getIsKeyPressed()) {
                    TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(3, player.ridingEntity.getEntityId()));
                    ((EntityTrainCore) player.ridingEntity).setAcceleration(false);
                }
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

            switch (((HitboxHandler.multipartHitbox) event.target).parent.getType()){
                case LOGCAR:{
                    TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(1, ((HitboxHandler.multipartHitbox) event.target).parent.getEntityId()));
                    break;
                }
                default:{
                    TrainsInMotion.keyChannel.sendToServer(new PacketMount(((HitboxHandler.multipartHitbox) event.target).parent.getEntityId()));
                    break;
                }
            }

        }
    }



    @SubscribeEvent
    public void entityJoinWorldEvent(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayer && event.entity.worldObj.isRemote) {
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("You are currently playing an alpha pre-release of Trains In Motion."));
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("For official releases, check out https://github.com/EternalBlueFlame/Trains-In-Motion/"));
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("Keep in mind that everything in this mod is subject to change, and report any bugs you find."));
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("Good luck and thanks for the assistance. - Eternal Blue Flame."));

            /*
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL("https://raw.githubusercontent.com/USER/PROJECT/BRANCH/version.txt").openConnection();
                conn.setRequestMethod("GET");
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                if (!TrainsInMotion.MOD_VERSION.equals(rd.readLine())) {
                    ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("A new version of Trains In Motion is available, check it out at:"));
                    ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText(rd.readLine()));
                }
            } catch (Exception e) {
                //couldn't check for new version, so just do nothing.
            }
             */
        }
    }
}
