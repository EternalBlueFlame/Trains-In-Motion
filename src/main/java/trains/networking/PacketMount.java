package trains.networking;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import trains.entities.EntityRollingStockCore;
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
            EntityPlayer player = context.getServerHandler().playerEntity;
            GenericRailTransport entity = (GenericRailTransport) player.worldObj.getEntityByID(message.entityId);

            /*
            for (int i =0; i< entity.getRiderOffsets().length; i++) {
                if (entity.seatEntities.get(i).riddenByEntity == null) {
                    context.getServerHandler().playerEntity.mountEntity(((GenericRailTransport) context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entityId)).seatEntities.get(i));
                    break;
                }
            }
             */


            for (int i =0; i< entity.getRiderOffsets().length; i++) {
                if (i>=entity.riddenByEntities.size()){
                    mount(player, entity);
                    entity.riddenByEntities.add(player);
                    break;
                } else if (entity.riddenByEntities.get(i) == null){
                    mount(player, entity);
                    entity.riddenByEntities.set(i, player);
                    break;

                }
            }
            return null;
        }

        private void mount(EntityPlayer player, Entity p_70078_1_){
            if (p_70078_1_ == null) {
                if (player.ridingEntity != null) {
                    player.setLocationAndAngles(player.ridingEntity.posX, player.ridingEntity.boundingBox.minY + (double)player.ridingEntity.height, player.ridingEntity.posZ, player.rotationYaw, player.rotationPitch);
                    player.ridingEntity.riddenByEntity = null;
                }

                player.ridingEntity = null;
            } else {
                if (player.ridingEntity != null) {
                    player.ridingEntity.riddenByEntity = null;
                }

                for (Entity entity1 = p_70078_1_.ridingEntity; entity1 != null; entity1 = entity1.ridingEntity) {
                    if (entity1 == player) {
                        return;
                    }
                }

                player.ridingEntity = p_70078_1_;
            }
        }
    }
}
