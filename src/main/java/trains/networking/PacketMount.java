package trains.networking;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import trains.TrainsInMotion;
import trains.entities.GenericRailTransport;

import java.util.UUID;

/**
 * <h1>Mount packet</h1>
 * This is necessary because when interacting with the hitboxes because they are client side only,
 * @author Eternal Blue Flame
 */
public class PacketMount implements IMessage {
    private UUID entityId;
    /**
     * <h2>data transfer variables</h2>
     * stores and transfers the variable through the byte buffer.
     */
    public PacketMount() {}
    public PacketMount(UUID entityId) {
        this.entityId = entityId;
    }
    @Override
    public void fromBytes(ByteBuf bbuf) {
        entityId = new UUID(bbuf.readLong(), bbuf.readLong());
    }
    @Override
    public void toBytes(ByteBuf bbuf) {
        bbuf.writeLong(entityId.getMostSignificantBits()); bbuf.writeLong(entityId.getLeastSignificantBits());
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
            EntityPlayer player = context.getServerHandler().playerEntity;
            GenericRailTransport entity = (GenericRailTransport) TrainsInMotion.proxy.getEntityFromUuid(message.entityId);

            for (int i = 0; i < entity.getRiderOffsets().length; i++) {
                if (entity.riddenByEntities.get(i) == null) {
                    player.mountEntity(entity);
                    entity.riddenByEntities.set(i, player.getUniqueID());
                    break;
                }
            }
            return null;
        }
    }
}
