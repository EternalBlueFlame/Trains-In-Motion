package ebf.tim.networking;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ebf.tim.entities.GenericRailTransport;
import io.netty.buffer.ByteBuf;

/**
 * <h1>Mount packet</h1>
 * This is intended to be a replacement for
 * @see net.minecraft.network.play.client.C02PacketUseEntity
 * because for whatever reason, the stupid thing refuses to send for our entities.
 * @author Eternal Blue Flame
 */
public class PacketInteract implements IMessage {
    /**the ID of the entity to dismount from*/
    private int entityId;
    private int key;

    public PacketInteract() {}
    public PacketInteract(int key, int entityId) {
        this.key=key;
        this.entityId = entityId;
    }
    /**reads the packet on server to get the variables from the Byte Buffer*/
    @Override
    public void fromBytes(ByteBuf bbuf) {
        key = bbuf.readInt();
        entityId = bbuf.readInt();
    }
    /**puts the variables into a Byte Buffer so they can be sent to server*/
    @Override
    public void toBytes(ByteBuf bbuf) {
        bbuf.writeInt(key);
        bbuf.writeInt(entityId);
    }

    /**
     * <h2>packet handler</h2>
     * handles the packet when received by server
     */
    public static class Handler implements IMessageHandler<PacketInteract, IMessage> {
        @Override
        public IMessage onMessage(PacketInteract message, MessageContext context) {
            //First it has to check if it was actually received by the proper entity, because if not, it crashes.
            try {
                //if (message.key == -1) {
                    ((GenericRailTransport) context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entityId)).
                            interact(context.getServerHandler().playerEntity, false, false, message.key);
                //} else {
                    //((GenericRailTransport) context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entityId)).ProcessPacket(message.key, context.getServerHandler().playerEntity.getEntityId());
                //}

            } catch (Exception e){
                System.out.println("Forge must have confused trains with chickens... You should tell Eternal, and send him this entire stacktrace, just to be sure.");
                e.printStackTrace();
            }

            return null;
        }
    }
}
