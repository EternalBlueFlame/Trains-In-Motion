package trains.networking;


import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import trains.TrainsInMotion;
import trains.entities.EntityBogie;
import trains.gui.train.GUISteam;
import trains.utility.EventHandler;

public class PacketGUI  implements IMessage {
    private int gui;

    /**
     * <h2>Packet for GUI</h2>
     * when the key is pressed in
     * @see EventHandler#onClientKeyPress(InputEvent.KeyInputEvent)
     * it's processed by client and sent to server via packet to open the GUI from a minecraft packet thats sent back to client.
     * The only data sent in the packet, beyond the normal overhead is a single int to define what GUI to use.
     */
    public PacketGUI() {}
    public PacketGUI(int gui) {
        this.gui = gui;
    }
    @Override
    public void fromBytes(ByteBuf bbuf) {
        gui = bbuf.readInt();
    }
    @Override
    public void toBytes(ByteBuf bbuf) {
        bbuf.writeInt(gui);
    }

    /**
     * <h2>packet handler</h2>
     * handles the packet when received by client.
     * First it has to check if it was actually received by the proper entity, because if not, it crashes.
     * then it opens the GUI based on the ID number that was sent in the packet
     * @see GUISteam
     */
    public static class Handler implements IMessageHandler<PacketGUI, IMessage> {
        @Override
        public IMessage onMessage(PacketGUI message, MessageContext context) {
            EntityPlayer entityPlayer = context.getServerHandler().playerEntity;
            if (entityPlayer != null && entityPlayer.ridingEntity instanceof EntityBogie) {
                switch (message.gui) {
                    case TrainsInMotion.STEAM_GUI_ID: {
                        entityPlayer.openGui(TrainsInMotion.instance, TrainsInMotion.STEAM_GUI_ID, entityPlayer.ridingEntity.worldObj,
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
