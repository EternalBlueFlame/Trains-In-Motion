package trains.networking;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import trains.TrainsInMotion;
import trains.entities.MinecartExtended;

public class PacketKeyPress implements IMessage {

    //The key that was pressed.
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
    //handle packet
    public static class Handler implements IMessageHandler<PacketKeyPress, IMessage> {
        @Override
        public IMessage onMessage(PacketKeyPress message, MessageContext context) {
            //be sure the entities are correct
            Entity ridingEntity = context.getServerHandler().playerEntity.ridingEntity;
            if (ridingEntity instanceof MinecartExtended) {
                //depending on the key, do a different function on the train
                if (message.key == TrainsInMotion.parseKey(TrainsInMotion.instance.KeyLamp)){
                    ((MinecartExtended) ridingEntity).lamp.isOn = !((MinecartExtended) ridingEntity).lamp.isOn;
                }
            }



            return null;
        }
    }
}
