package trains.networking;

import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import trains.TrainsInMotion;
import trains.entities.EntitySeat;
import trains.entities.EntityTrainCore;
import trains.entities.GenericRailTransport;
import trains.utility.CommonProxy;
import trains.utility.EventManager;

import static trains.TrainsInMotion.nullUUID;

/**
 * <h1>Key press packet</h1>
 *
 * when a key is pressed in
 * @see EventManager#onClientKeyPress(InputEvent.KeyInputEvent)
 * it's processed by client to sent the defined action to server.
 * The only data sent in the packet, beyond the normal overhead is a single int to define what action to do.
 * @author Eternal Blue Flame
 */
public class PacketKeyPress implements IMessage {
    /**
     * <h2>data transfer variables</h2>
     * stores and transfers the variable through the byte buffer.
     */
    private int key;
    private int entity;
    public PacketKeyPress() {}
    public PacketKeyPress(int key, int entity) {
        this.key = key;
        this.entity = entity;
    }
    @Override
    public void fromBytes(ByteBuf bbuf) {
        key = bbuf.readInt();
        entity = bbuf.readInt();
    }
    @Override
    public void toBytes(ByteBuf bbuf) {
        bbuf.writeInt(key);
        bbuf.writeInt(entity);
    }

    /**
     * <h2>packet handler</h2>
     * handles the packet when received by server
     * First it has to check if it was actually received by the proper entity, because if not, it crashes.
     * then check if the entity is correct and the key matches a valid key for a function.
     */
    public static class Handler implements IMessageHandler<PacketKeyPress, IMessage> {
        @Override
        public IMessage onMessage(PacketKeyPress message, MessageContext context) {
            GenericRailTransport ridingEntity = (GenericRailTransport) context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entity);
            /**
             * this if check actually will stop the rest of the function and apply the correct boolean value if it's true
             * @see GenericRailTransport#toggleBool(int)
             * @see EntityTrainCore#toggleBool(int)
             *
             * if it's not one of the booleans then we do a switch on which function it is here.
             */
            if(!ridingEntity.toggleBool(message.key)) {
                switch (message.key){
                    //speed
                    case 2:{((EntityTrainCore) ridingEntity).setAcceleration(false); break;}
                    case 3:{ ((EntityTrainCore) ridingEntity).setAcceleration(true); break;}
                    //drop key item
                    case 12:{ridingEntity.entityDropItem(ridingEntity.key, 0); break;}
                    //uncouple cars
                    case 13:{
                        //front
                        if (ridingEntity.front != nullUUID){
                            if(ridingEntity.getPersistentID() == CommonProxy.getTransportFromUuid(ridingEntity.front).front){
                                CommonProxy.getTransportFromUuid(ridingEntity.front).front =nullUUID;
                            } else if (ridingEntity.getPersistentID() == CommonProxy.getTransportFromUuid(ridingEntity.front).back){
                                CommonProxy.getTransportFromUuid(ridingEntity.front).back =nullUUID;
                            }
                        }
                        //back
                        if (ridingEntity.back != nullUUID){
                            if(ridingEntity.getPersistentID() == CommonProxy.getTransportFromUuid(ridingEntity.back).front){
                                CommonProxy.getTransportFromUuid(ridingEntity.back).front =nullUUID;
                            } else if (ridingEntity.getPersistentID() == CommonProxy.getTransportFromUuid(ridingEntity.back).back){
                                CommonProxy.getTransportFromUuid(ridingEntity.back).back =nullUUID;
                            }
                        }
                        //double check and be sure both are null
                        ridingEntity.front = nullUUID; ridingEntity.back = nullUUID; break;}

                    /**
                     * <h3>Manage the inventory key press</h3>
                     * get a reference to the player and the transport then open the GUI with those refrences.
                     */
                    case 1:{
                        EntityPlayer entityPlayer = context.getServerHandler().playerEntity;
                        if (entityPlayer != null) {
                            int transport;
                            if (entityPlayer.worldObj.getEntityByID(message.entity) instanceof EntitySeat){
                                transport = ((EntitySeat)entityPlayer.worldObj.getEntityByID(message.entity)).parentId;
                            } else {
                                transport = message.entity;
                            }
                            entityPlayer.openGui(TrainsInMotion.instance, transport, entityPlayer.worldObj,
                                    MathHelper.floor_double(ridingEntity.posX), MathHelper.floor_double(ridingEntity.posY),
                                    MathHelper.floor_double(ridingEntity.posZ));
                        }
                        break;
                    }
                }
            }
            return null;
        }
    }
}
