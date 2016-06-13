package trains.networking;


import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.gui.GUITest;

public class PacketGUI  implements IMessage {
    // The gui that was pressed.
    int gui;
    //constructor
    public PacketGUI(int gui) {
        this.gui = gui;
    }
    //read packet
    @Override
    public void fromBytes(ByteBuf bbuf) {
        gui = bbuf.readInt();
    }
    //write packet
    @Override
    public void toBytes(ByteBuf bbuf) {
        bbuf.writeInt(gui);
    }
    //handle packet
    public static class Handler implements IMessageHandler<PacketGUI, IMessage> {
        @Override
        public IMessage onMessage(PacketGUI message, MessageContext context) {
            //be sure the entities are correct
            EntityPlayer entityPlayer = context.getServerHandler().playerEntity;
            if (entityPlayer  != null && entityPlayer .ridingEntity instanceof EntityTrainCore) {
                //open the gui for the player
                entityPlayer .openGui(TrainsInMotion.instance, GUITest.GUI_ID, entityPlayer .ridingEntity.worldObj,
                        MathHelper.floor_double(entityPlayer.ridingEntity.posX), MathHelper.floor_double(entityPlayer.ridingEntity.posY), MathHelper.floor_double(entityPlayer.ridingEntity.posZ));
            }
            return null;
        }
    }
}
