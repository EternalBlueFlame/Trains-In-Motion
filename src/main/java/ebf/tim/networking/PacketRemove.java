package ebf.tim.networking;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ebf.tim.entities.GenericRailTransport;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

/**
 * <h1>Remove entity packet</h1>
 * used to remove an entity from the world. this is necessary because the entity interaction is through client only hitboxes.
 * @author Eternal Blue Flame
 */
public class PacketRemove implements IMessage {
    /**the entity ID to define what entity to use the function on*/
    private int entityId;

    public PacketRemove() {}
    public PacketRemove(int entityId) {
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
    public static class Handler implements IMessageHandler<PacketRemove, IMessage> {
        @Override
        public IMessage onMessage(PacketRemove message, MessageContext context) {
            //First it has to check if it was actually received by the proper entity, because if not, it crashes.
            Entity entity = context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entityId);
            //if the entity was an instance of Generic Rail Transport, then spawn it's item and remove it from world.
            if (entity instanceof GenericRailTransport) {
                entity.worldObj.spawnEntityInWorld(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(((GenericRailTransport) entity).getItem(),1)));
                context.getServerHandler().playerEntity.worldObj.removeEntity(entity);
            }
            return null;
        }
    }
}
