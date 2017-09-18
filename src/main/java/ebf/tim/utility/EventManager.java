package ebf.tim.utility;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
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
import org.lwjgl.input.Keyboard;

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
     * manages key pressed or released, since 1.7.10 has no direct support for key released we have to do it directly through LWJGL.
     * Most cases just send a packet to manage things
     * @see PacketKeyPress
     *
     * Credit to Ferdinand for help with this function.
     *
     * @param event the event of a key being pressed on client.
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientKeyPress(InputEvent.KeyInputEvent event) {
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        if (player.ridingEntity instanceof GenericRailTransport || player.ridingEntity instanceof EntitySeat) {
            //for lamp
            if (ClientProxy.KeyLamp.isPressed()) {
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(0, player.ridingEntity.getEntityId()));
                ((GenericRailTransport) player.ridingEntity).setBoolean(GenericRailTransport.boolValues.LAMP, !((GenericRailTransport) player.ridingEntity).getBoolean(GenericRailTransport.boolValues.LAMP));
            }
            //for inventory
            if (ClientProxy.KeyInventory.isPressed()) {
                TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(1, player.ridingEntity.getEntityId()));
            }
            if (player.ridingEntity instanceof EntityTrainCore) {
                //for speed change
                if (FMLClientHandler.instance().getClient().gameSettings.keyBindForward.isPressed()) {
                    TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(2, player.ridingEntity.getEntityId()));
                } else if (FMLClientHandler.instance().getClient().gameSettings.keyBindBack.getIsKeyPressed()) {
                    TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(3, player.ridingEntity.getEntityId()));
                } else if (ClientProxy.KeyHorn.isPressed()){
                    TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(9, player.ridingEntity.getEntityId()));
                } else if (FMLClientHandler.instance().getClient().gameSettings.keyBindJump.isPressed()){
                    TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(4, player.ridingEntity.getEntityId()));
                }

                //manage key release events
                if (Keyboard.getEventKey() == FMLClientHandler.instance().getClient().gameSettings.keyBindJump.getKeyCode() && !Keyboard.getEventKeyState()){
                    TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(15, player.ridingEntity.getEntityId()));
                }
            }
        }
    }

    /**
     * <h2>Entity Interaction</h2>
     * this client event manages when the player tries to interact with the transport to ride it, or use an item on it.
     */
    @SubscribeEvent
    @SuppressWarnings("unused")
    public void entityInteractEvent(EntityInteractEvent event) {
        //be sure the target is a transport hitbox
        if (event.target instanceof HitboxHandler.MultipartHitbox && event.entity.worldObj.isRemote) {

            //TODO: if(event.entityPlayer.getHeldItem() instanceof stakeItem) {do linking/unlinking stuff;}
            //if the rider offsets weren't null, try and mount
            if (((HitboxHandler.MultipartHitbox) event.target).parent.getRiderOffsets() != null) {
                TrainsInMotion.keyChannel.sendToServer(new PacketMount(((HitboxHandler.MultipartHitbox) event.target).parent.getEntityId()));
                //if they were null, try and open the inventory
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
    @SuppressWarnings("unused")
    public void entityJoinWorldEvent(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayer && event.entity.worldObj.isRemote) {
            //add alpha notice
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("You are currently playing an alpha release of Trains In Motion."));
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("For official releases, check out https://github.com/EternalBlueFlame/Trains-In-Motion/"));
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("Keep in mind that everything in this mod is subject to change, especially the rails, and report any bugs you find."));
            ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("Good luck and thanks for the assistance. - Eternal Blue Flame."));

            //use an HTTP request and parse to check for new versions of the mod from github.
            System.out.println();
            try {
                //make an HTTP connection to the version text file, and set the type as get.
                HttpURLConnection conn = (HttpURLConnection) new URL("https://raw.githubusercontent.com/USER/PROJECT/BRANCH/version.txt").openConnection();
                conn.setRequestMethod("GET");
                //use the HTTP connection as an input stream to actually get the file, then put it into a buffered reader.
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //read the first line of the text document, if it's not the same as the current running version, notify there is an update, then display the second line, which is intended for a download URL.
                if (!TrainsInMotion.MOD_VERSION.equals(rd.readLine())) {
                    ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText("A new version of Trains In Motion is available, check it out at:"));
                    ((EntityPlayer) event.entity).addChatMessage(new ChatComponentText(rd.readLine()));
                }
            } catch (Exception e) {
                //couldn't check for new version, most likely because there's no internet, so just do nothing.
            }

        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void playerQuitEvent(PlayerEvent.PlayerLoggedOutEvent event){
        if (event.player.ridingEntity instanceof GenericRailTransport || event.player.ridingEntity instanceof EntitySeat){
            event.player.dismountEntity(event.player.ridingEntity);
        }
    }


}
