package trains.networking;


import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.gui.GUITest;

public class PacketGUI  implements IMessage {
    // The gui that was pressed.
    int gui;

    public PacketGUI() {}

    public PacketGUI(int gui) {
        this.gui = gui;
    }

    @Override
    public void fromBytes(ByteBuf bbuf) {
        gui = bbuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf bbuf) {
        bbuf.writeInt(gui);
    }

    public static class Handler implements IMessageHandler<PacketGUI, IMessage> {
        @Override
        public IMessage onMessage(PacketGUI message, MessageContext context) {
            System.out.println("key recieved");
            EntityPlayer ridingEntity = context.getServerHandler().playerEntity;
            if (ridingEntity != null && ridingEntity.ridingEntity instanceof EntityTrainCore) {
                System.out.println("key processed");
                ridingEntity.openGui(TrainsInMotion.instance, GUITest.GUI_ID, ridingEntity.ridingEntity.worldObj,
                        MathHelper.floor_double(ridingEntity.ridingEntity.posX), MathHelper.floor_double(ridingEntity.ridingEntity.posY), MathHelper.floor_double(ridingEntity.ridingEntity.posZ));
            }



            return null;
        }
    }
}
