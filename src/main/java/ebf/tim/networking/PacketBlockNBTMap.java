package ebf.tim.networking;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ebf.tim.TrainsInMotion;
import ebf.tim.utility.BlockNBTMap;
import ebf.tim.utility.CommonProxy;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.SerializationUtils;


/**
 * <h1>Remove entity packet</h1>
 * used to remove an entity from the world. this is necessary because the entity interaction is through client only hitboxes.
 * @author Eternal Blue Flame
 */
public class PacketBlockNBTMap implements IMessage {
    /**the entity ID to define what entity to use the function on*/
    private BlockNBTMap map;

    public PacketBlockNBTMap() {}
    public PacketBlockNBTMap(BlockNBTMap m) {
        this.map=m;
    }
    /**reads the packet on server to get the variables from the Byte Buffer*/
    @Override
    public void fromBytes(ByteBuf bbuf) {
        map = new BlockNBTMap("client");
        map.readFromNBT(ByteBufUtils.readTag(bbuf));
    }
    /**puts the variables into a Byte Buffer so they can be sent to server*/
    @Override
    public void toBytes(ByteBuf bbuf) {
        NBTTagCompound tag = new NBTTagCompound();
        map.writeToNBT(tag);
        ByteBufUtils.writeTag(bbuf, tag);
    }

    /**
     * <h2>packet handler</h2>
     * handles the packet when received by Client
     */
    public static class Handler implements IMessageHandler<PacketBlockNBTMap, IMessage> {
        @Override
        public IMessage onMessage(PacketBlockNBTMap message, MessageContext context) {
        CommonProxy.setRailMap(Minecraft.getMinecraft().theWorld,message.map);
        return null;
        }
    }
}
