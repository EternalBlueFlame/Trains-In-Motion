package trains.networking;

import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import trains.utility.EventManager;

public class PacketRemove implements IMessage {
    private int entityId;
    /**
     * <h2>Packet for removing the train from world</h2>
     * this is necessary because it happens from player interaction with hitboxes that are client only, so we have to send a packet to server for server to handle it.
     * when a entityId is pressed in
     * @see EventManager#onClientKeyPress(InputEvent.KeyInputEvent)
     * it's processed by client to sent the defined action to server.
     * The only data sent in the packet, beyond the normal overhead is a single int to define what action to do.
     */
    public PacketRemove() {}
    public PacketRemove(int entityId) {
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
    public static class Handler implements IMessageHandler<PacketRemove, IMessage> {
        @Override
        public IMessage onMessage(PacketRemove message, MessageContext context) {
            Entity entity = context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entityId);
            if (entity != null && message.entityId!=0) {
                context.getServerHandler().playerEntity.worldObj.removeEntity(entity);
            }
            return null;
        }
    }
}
