package ebf.tim.networking;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ebf.tim.TrainsInMotion;
import ebf.tim.utility.*;
import fexcraft.tmt.slim.TextureManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        StringBuilder str = new StringBuilder();
        str.append("\n number of chunks:");
        str.append(map.data.size());
        for (List<Integer> vec : map.data.keySet()) {
            str.append("\n the chunk was:");
            str.append(vec.get(0));
            str.append(",");
            str.append(vec.get(1));
            str.append("\n");
            for (Map.Entry<List<Integer>, NBTTagCompound> v : map.data.get(vec).entrySet()) {
                str.append("\n");
                str.append(v.getKey().get(0));
                str.append(",");
                str.append(v.getKey().get(1));
                str.append(",");
                str.append(v.getKey().get(2));
                str.append("\n");
                str.append(v.getValue().hasKey("route"));
                str.append(":");
                str.append(v.getValue().hasKey("rail"));
                str.append(":");
                if(v.getValue().hasKey("route")){
                    str.append("\n");
                    str.append(new String(Base64.decodeBase64(v.getValue().getString("route"))));
                }
            }
        }
        DebugUtil.println(str.toString());
        DebugUtil.println("Total packet size " + bbuf.array().length +" Bytes.");
    }

    /**
     * <h2>packet handler</h2>
     * handles the packet when received by Client
     */
    public static class Handler implements IMessageHandler<PacketBlockNBTMap, IMessage> {
        @Override
        public IMessage onMessage(PacketBlockNBTMap message, MessageContext context) {
            DebugUtil.println(context.side, message.map.data.size());
            CommonProxy.clientList =message.map;
        return null;
        }
    }
}
