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
import trains.gui.GUITest;
import trains.networking.PacketGUI;
import trains.networking.PacketKeyPress;

public class TiMEventHandler {
    //manage on key press, not for while a key is held, only for features that take a single key press
    //TODO move these to something that can be changed in a config file
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientKeyPress(InputEvent.KeyInputEvent event){
        if(Minecraft.getMinecraft().thePlayer.ridingEntity instanceof MinecartExtended) {
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
        if (event.target instanceof MinecartExtended && !event.entity.worldObj.isRemote && (event.target.riddenByEntity == null || event.target.riddenByEntity == event.entityPlayer)) {
            event.entityPlayer.mountEntity(event.target);
        }
    }



    public int parseKey(char key){
        switch (key){
            case 'a' :{return 30;}
            case 'b' :{return 48;}
            case 'c' :{return 46;}
            case 'd' :{return 32;}
            case 'e' :{return 18;}
            case 'f' :{return 33;}
            case 'g' :{return 34;}
            case 'h' :{return 35;}
            case 'i' :{return 23;}
            case 'j' :{return 36;}
            case 'k' :{return 37;}
            case 'l' :{return 38;}
            case 'm' :{return 50;}
            case 'n' :{return 49;}
            case 'o' :{return 24;}
            case 'p' :{return 25;}
            case 'q' :{return 16;}
            case 'r' :{return 19;}
            case 's' :{return 31;}
            case 't' :{return 20;}
            case 'u' :{return 22;}
            case 'v' :{return 47;}
            case 'w' :{return 17;}
            case 'x' :{return 45;}
            case 'y' :{return 21;}
            case 'z' :{return 44;}
            //finish special characters later.
            default: {return 0;}
        }
    }


}
