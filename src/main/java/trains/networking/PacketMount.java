package trains.networking;

import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import trains.utility.EventHandler;

public class PacketMount implements IMessage {
    private int entityId;
    /**
     * <h2>Packet for keybinds beyond inventory</h2>
     * when a entityId is pressed in
     * @see EventHandler#onClientKeyPress(InputEvent.KeyInputEvent)
     * it's processed by client to sent the defined action to server.
     * The only data sent in the packet, beyond the normal overhead is a single int to define what action to do.
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
            context.getServerHandler().playerEntity.mountEntity(context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entityId));
            return null;
        }
    }
}
