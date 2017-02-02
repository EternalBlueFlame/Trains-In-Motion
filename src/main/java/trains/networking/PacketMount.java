package trains.networking;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ChatComponentText;
import trains.entities.EntitySeat;
import trains.entities.GenericRailTransport;

/**
 * <h1>Mount packet</h1>
 * This is necessary because when interacting with the hitboxes because they are client side only,
 * @author Eternal Blue Flame
 */
public class PacketMount implements IMessage {
    private int entityId;
    /**
     * <h2>data transfer variables</h2>
     * stores and transfers the variable through the byte buffer.
     */
    public PacketMount() {}
    public PacketMount(int entityId) {
        this.entityId = entityId;
    }
    @Override
    public void fromBytes(ByteBuf bbuf) {
        entityId = bbuf.readInt();
    }
    @Override
    public void toBytes(ByteBuf bbuf) {
        bbuf.writeInt(entityId);
    }

    /**
     * <h2>packet handler</h2>
     * handles the packet when received by server
     * First it has to check if it was actually received by the proper entity, because if not, it crashes.
     * then check if the entity is correct and the entityId matches a valid entityId for a function.
     */
    public static class Handler implements IMessageHandler<PacketMount, IMessage> {
        @Override
        public IMessage onMessage(PacketMount message, MessageContext context) {
            GenericRailTransport transport = (GenericRailTransport) context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entityId);
            if (transport.riddenByEntity == null) {
                context.getServerHandler().playerEntity.mountEntity(transport);
                return null;
            } else {
                for (EntitySeat seat : transport.seats){
                    if (seat.riddenByEntity == null){
                        context.getServerHandler().playerEntity.mountEntity(seat);
                        return null;
                    }
                }
            }
            context.getServerHandler().playerEntity.addChatMessage(new ChatComponentText("There are no available seats"));
            return null;
        }
    }
}
