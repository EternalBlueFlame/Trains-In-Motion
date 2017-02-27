package trains.networking;

import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import trains.TrainsInMotion;
import trains.entities.EntitySeat;
import trains.entities.EntityTrainCore;
import trains.entities.GenericRailTransport;
import trains.entities.rollingstock.EntityVATLogCar;
import trains.utility.EventManager;
import trains.utility.HitboxHandler;

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
            Entity ridingEntity = context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entity);
            //Toggles,
            if(!((GenericRailTransport) ridingEntity).toggleBool(message.key)) {
                //speed
                if (message.key == 2) {
                    ((EntityTrainCore) ridingEntity).setAcceleration(true);
                } else if (message.key == 3) {
                    ((EntityTrainCore) ridingEntity).setAcceleration(false);
                }
                /**
                 * <h3>Manage the inventory key press</h3>
                 * here we have to figure out what kind of train or rollingstock the player is riding, and activate the related GUI.
                 */
                else if (message.key == 1) {
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
                }
            }



            return null;
        }
    }
}
