package trains.networking;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import org.lwjgl.input.Keyboard;
import trains.entities.MinecartExtended;

/**
 * Sent to the server when a key is pressed on the client.
 *
 * 		7 = R
 * 		4 = W
 * 		5 = S
 * 		8 = H
 * 		9 = F
 * 		0 = Y
 * 		1 = A
 * 		2 = X
 * 		3 = D
 * 		6 = C
 */
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
                switch (message.key){
                    case Keyboard.KEY_L:{
                        ((MinecartExtended) ridingEntity).lamp = !((MinecartExtended) ridingEntity).lamp;
                    }
                }
            }



            return null;
        }
    }
}
