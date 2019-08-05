package ebf.tim.networking;

import cpw.mods.fml.common.network.ByteBufUtils;
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
public class PacketPaint implements IMessage {
    /**the ID of the entity to dismount from*/
    private int entityId;
    private String key;

    public PacketPaint() {}
    public PacketPaint(String skin, int entityId) {
        this.key=skin;
        this.entityId = entityId;
    }
    /**reads the packet on server to get the variables from the Byte Buffer*/
    @Override
    public void fromBytes(ByteBuf bbuf) {
        entityId = bbuf.readInt();
        key = ByteBufUtils.readUTF8String(bbuf);
    }
    /**puts the variables into a Byte Buffer so they can be sent to server*/
    @Override
    public void toBytes(ByteBuf bbuf) {
        bbuf.writeInt(entityId);
        ByteBufUtils.writeUTF8String(bbuf, key);
    }

    /**
     * <h2>packet handler</h2>
     * handles the packet when received by server
     */
    public static class Handler implements IMessageHandler<PacketPaint, IMessage> {
        @Override
        public IMessage onMessage(PacketPaint message, MessageContext context) {
            //First it has to check if it was actually received by the proper entity, because if not, it crashes.
            try {
                context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entityId).
                    getDataWatcher().updateObject(24, message.key);
            } catch (Exception e){
                System.out.println("Forge must have confused trains with chickens... You should tell Eternal, and send him this entire stacktrace, just to be sure.");
                e.printStackTrace();
            }

            return null;
        }
    }
}
