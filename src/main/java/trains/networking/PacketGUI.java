package trains.networking;


import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import trains.TrainsInMotion;
import trains.entities.MinecartExtended;
import trains.gui.train.GUISteam;

public class PacketGUI  implements IMessage {
    int gui;
    //necessary, but unused constructor
    public PacketGUI() {}
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

    /**
     * handles the packet when received by client.
     * First it has to check if it was actually received by the proper entity, because if not, it crashes.
     * then it opens the GUI based on the ID number that was sent in the packet
     * @see GUISteam
     */
    public static class Handler implements IMessageHandler<PacketGUI, IMessage> {
        @Override
        public IMessage onMessage(PacketGUI message, MessageContext context) {
            EntityPlayer entityPlayer = context.getServerHandler().playerEntity;
            if (entityPlayer != null && entityPlayer.ridingEntity instanceof MinecartExtended) {
                switch (message.gui) {
                    case GUISteam.GUI_ID: {
                        entityPlayer.openGui(TrainsInMotion.instance, GUISteam.GUI_ID, entityPlayer.ridingEntity.worldObj,
                                MathHelper.floor_double(entityPlayer.ridingEntity.posX), MathHelper.floor_double(entityPlayer.ridingEntity.posY), MathHelper.floor_double(entityPlayer.ridingEntity.posZ));
                    }
                    default: {
                        return null;
                    }
                }
            }
            return null;
        }
    }
}
