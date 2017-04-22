package ebf.tim.utility;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntitySeat;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.networking.PacketKeyPress;
import ebf.tim.networking.PacketMount;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

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
                } else if (ClientProxy.KeyHorn.isPressed()){
                    TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(4, player.ridingEntity.getEntityId()));
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
        if (event.target instanceof HitboxHandler.MultipartHitbox
                && event.entity.worldObj.isRemote) {

            if (((HitboxHandler.MultipartHitbox) event.target).parent.getRiderOffsets() != null) {
                TrainsInMotion.keyChannel.sendToServer(new PacketMount(((HitboxHandler.MultipartHitbox) event.target).parent.getEntityId()));
            } else {
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(1, ((HitboxHandler.MultipartHitbox) event.target).parent.getEntityId()));
            }

        }
    }


    /**
     * <h2>join world</h2>
     * This event is called when a player joins the world, we use this to display the alpha notice, and check for new mod versions, this is only displayed on the client side, but can be used for server..
     */
    @SubscribeEvent
    public void entityJoinWorldEvent(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayer && event.entity.worldObj.isRemote) {
            //add alpha notice
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("You are currently playing an alpha pre-release of Trains In Motion."));
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("For official releases, check out https://github.com/EternalBlueFlame/Trains-In-Motion/"));
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("Keep in mind that everything in this mod is subject to change, and report any bugs you find."));
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("Good luck and thanks for the assistance. - Eternal Blue Flame."));

            //use an HTTP request and parse to check for new versions of the mod from github.
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL("https://raw.githubusercontent.com/USER/PROJECT/BRANCH/version.txt").openConnection();
                conn.setRequestMethod("GET");
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //read the first line of the text document, if it's not the same as the current running version, notify there is an update, then display the second line, which is intended for a download URL.
                if (!TrainsInMotion.MOD_VERSION.equals(rd.readLine())) {
                    ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("A new version of Trains In Motion is available, check it out at:"));
                    ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText(rd.readLine()));
                }
            } catch (Exception e) {
                //couldn't check for new version, so just do nothing.
            }

        }
    }
}
