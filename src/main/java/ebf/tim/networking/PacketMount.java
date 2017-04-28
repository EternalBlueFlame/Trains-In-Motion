package ebf.tim.networking;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ebf.tim.entities.EntitySeat;
import ebf.tim.entities.GenericRailTransport;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.ChatComponentText;

/**
 * <h1>Mount packet</h1>
 * This is necessary because when interacting with the hitboxes because the event is expected to be client only,
 * @author Eternal Blue Flame
 */
public class PacketMount implements IMessage {
    /**the ID of the entity to dismount from*/
    private int entityId;

    public PacketMount() {}
    public PacketMount(int entityId) {
        this.entityId = entityId;
    }
    /**reads the packet on server to get the variables from the Byte Buffer*/
    @Override
    public void fromBytes(ByteBuf bbuf) {
        entityId = bbuf.readInt();
    }
    /**puts the variables into a Byte Buffer so they can be sent to server*/
    @Override
    public void toBytes(ByteBuf bbuf) {
        bbuf.writeInt(entityId);
    }

    /**
     * <h2>packet handler</h2>
     * handles the packet when received by server
     */
    public static class Handler implements IMessageHandler<PacketMount, IMessage> {
        @Override
        public IMessage onMessage(PacketMount message, MessageContext context) {
            //First it has to check if it was actually received by the proper entity, because if not, it crashes.
            GenericRailTransport transport = (GenericRailTransport) context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entityId);
            //be sure the player has permission to enter the transport, and that the transport has the main seat open.
            if (transport.riddenByEntity == null && transport.getPermissions(context.getServerHandler().playerEntity, false)) {
                context.getServerHandler().playerEntity.mountEntity(transport);
                return null;
                //if the player had permission but the main seat isnt open, check for seat entities to mount.
            } else {
                for (EntitySeat seat : transport.seats){
                    if (seat.riddenByEntity == null && transport.getPermissions(context.getServerHandler().playerEntity, false)){
                        context.getServerHandler().playerEntity.mountEntity(seat);
                        return null;
                    }
                }
            }
            //if both the above options failed, figure out why, and then tell the player.
            if (transport.getPermissions(context.getServerHandler().playerEntity, false)) {
                context.getServerHandler().playerEntity.addChatMessage(new ChatComponentText("There are no available seats"));
            } else {
                context.getServerHandler().playerEntity.addChatMessage(new ChatComponentText("You don't have permission to do that."));
            }
            return null;
        }
    }
}
