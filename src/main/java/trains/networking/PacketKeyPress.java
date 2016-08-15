package trains.networking;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.entities.MinecartExtended;

public class PacketKeyPress implements IMessage {
    int key;
    //necessary, but unused constructor
    public PacketKeyPress() {}
    //constructor
    public PacketKeyPress(int key) {
        this.key = key;
    }
    //read packet
    @Override
    public void fromBytes(ByteBuf bbuf) {
        key = bbuf.readInt();
    }
    //write packet
    @Override
    public void toBytes(ByteBuf bbuf) {
        bbuf.writeInt(key);
    }

    /**
     * handles the packet when received by client
     * First it has to check if it was actually received by the proper entity, because if not, it crashes.
     * then check if the entity is correct and the key matches a valid key for a function.
     * for more information on the key parse
     * @see TrainsInMotion#parseKey(char)
     */
    public static class Handler implements IMessageHandler<PacketKeyPress, IMessage> {
        @Override
        public IMessage onMessage(PacketKeyPress message, MessageContext context) {
            Entity ridingEntity = context.getServerHandler().playerEntity.ridingEntity;
            if (message.key == TrainsInMotion.parseKey(TrainsInMotion.KeyLamp) && ridingEntity instanceof EntityTrainCore){
                ((EntityTrainCore) ridingEntity).lamp.isOn = !((EntityTrainCore) ridingEntity).lamp.isOn;
            }
            if (message.key == TrainsInMotion.parseKey(TrainsInMotion.KeyAccelerate) && ridingEntity instanceof EntityTrainCore){
                ((EntityTrainCore) ridingEntity).setAcceleration(true);
            }
            if (message.key == TrainsInMotion.parseKey(TrainsInMotion.KeyReverse) && ridingEntity instanceof EntityTrainCore){
                ((EntityTrainCore) ridingEntity).setAcceleration(false);
            }



            return null;
        }
    }
}
