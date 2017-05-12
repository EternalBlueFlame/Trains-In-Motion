package ebf.tim.networking;

import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntitySeat;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.gui.GUITransport;
import ebf.tim.utility.EventManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

/**
 * <h1>Key press packet</h1>
 *
 * when a key is pressed in
 * @see EventManager#onClientKeyPress(InputEvent.KeyInputEvent)
 * it's processed by client and then a packet is sent to server so the action can take place.
 * The only data sent in the packet, beyond the normal overhead, is two ints to define what action to do, and the ID of the entity to make do it.
 * @author Eternal Blue Flame
 */
public class PacketKeyPress implements IMessage {

    /**the key to define what function to use*/
    private int key;
    /**the entity ID to define what entity to use the function on*/
    private int entity;

    public PacketKeyPress() {}
    public PacketKeyPress(int key, int entity) {
        this.key = key;
        this.entity = entity;
    }
    /**reads the packet on server to get the variables from the Byte Buffer*/
    @Override
    public void fromBytes(ByteBuf bbuf) {
        key = bbuf.readInt();
        entity = bbuf.readInt();
    }
    /**puts the variables into a Byte Buffer so they can be sent to server*/
    @Override
    public void toBytes(ByteBuf bbuf) {
        bbuf.writeInt(key);
        bbuf.writeInt(entity);
    }

    /**
     * <h2>packet handler</h2>
     * handles the packet when received by server
     * First it has to check if it was actually received by the proper entity, because if not, it crashes.
     * then check if the entity is correct it's handed off to the entity to manage, however in the case of inventory, we still open it from here.
     * @see GenericRailTransport#ProcessPacket(int)
     * @see EntityTrainCore#ProcessPacket(int)
     */
    public static class Handler implements IMessageHandler<PacketKeyPress, IMessage> {
        @Override
        public IMessage onMessage(PacketKeyPress message, MessageContext context) {
            GenericRailTransport transportEntity;
            if (context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entity) instanceof GenericRailTransport){
                transportEntity = (GenericRailTransport) context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entity);
            } else if (context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entity) instanceof EntitySeat){
                transportEntity = (GenericRailTransport) context.getServerHandler().playerEntity.worldObj.getEntityByID(
                        ((EntitySeat)context.getServerHandler().playerEntity.worldObj.getEntityByID(message.entity)).parentId);
            } else {
                return null;
            }
            //process the packet on the entity, and if we still gotta do something here, then manage GUI
            if(!transportEntity.ProcessPacket(message.key) && message.key ==1) {
                EntityPlayer entityPlayer = context.getServerHandler().playerEntity;
                if (entityPlayer != null) {
                    int transport;
                    //if the entity is a seat we have to get the parent, otherwise we just get the parent.
                    if (entityPlayer.worldObj.getEntityByID(message.entity) instanceof EntitySeat){
                        transport = ((EntitySeat)entityPlayer.worldObj.getEntityByID(message.entity)).parentId;
                    } else {
                        transport = message.entity;
                    }
                    /** now open the GUI, we handle the GUI though
                     * @see GUITransport
                     */
                    entityPlayer.openGui(TrainsInMotion.instance, transport, entityPlayer.worldObj,
                            MathHelper.floor_double(transportEntity.posX), MathHelper.floor_double(transportEntity.posY),
                            MathHelper.floor_double(transportEntity.posZ));
                }
            }
            return null;
        }
    }
}
