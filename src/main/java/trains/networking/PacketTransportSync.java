package trains.networking;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import trains.TrainsInMotion;
import trains.entities.GenericRailTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h1>Mount packet</h1>
 * This is necessary because when interacting with the hitboxes because they are client side only,
 * @author Eternal Blue Flame
 */
public class PacketTransportSync implements IMessage {
    private List<UUID> entityIds = new ArrayList<>();
    private UUID hostID;
    /**
     * <h2>data transfer variables</h2>
     * stores and transfers the variable through the byte buffer.
     */
    public PacketTransportSync() {}
    public PacketTransportSync(List<UUID> entityIds, UUID hostID) {this.entityIds = entityIds; this.hostID = hostID;}
    @Override
    public void fromBytes(ByteBuf bbuf) {
        hostID =new UUID(bbuf.readLong(), bbuf.readLong());
        int ids = bbuf.readInt();
        for (int i=0;i<ids;i++){
            UUID test = new UUID(bbuf.readLong(), bbuf.readLong());
            System.out.println(test.getMostSignificantBits() + ":" + test.getLeastSignificantBits());
            entityIds.add(test);
        }
    }
    @Override
    public void toBytes(ByteBuf bbuf) {
        bbuf.writeLong(hostID.getMostSignificantBits());
        bbuf.writeLong(hostID.getLeastSignificantBits());
        bbuf.writeInt(entityIds.size()-1);
        for (UUID id : entityIds){
            bbuf.writeLong(id.getMostSignificantBits());
            bbuf.writeLong(id.getLeastSignificantBits());
        }
    }

    /**
     * <h2>packet handler</h2>
     * handles the packet when received by server
     * First it has to check if it was actually received by the proper entity, because if not, it crashes.
     * then check if the entity is correct and the entityId matches a valid entityId for a function.
     */
    public static class Handler implements IMessageHandler<PacketTransportSync, IMessage> {
        @Override
        public IMessage onMessage(PacketTransportSync message, MessageContext context) {
            GenericRailTransport entity = (GenericRailTransport) TrainsInMotion.proxy.getEntityFromUuid(message.hostID);
            if (entity != null) {
                entity.riddenByEntities = message.entityIds;
            }
            return null;
        }
    }
}
