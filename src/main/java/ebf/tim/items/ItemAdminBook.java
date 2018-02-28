package ebf.tim.items;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.TrainsInMotion;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.gui.GUIAdminBook;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.DimensionManager;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * <h1>Key Item</h1>
 * the key used to allow people other than the owner to interact with a locked train or rollingstock.
 * @author Eternal Blue Flame
 */
public class ItemAdminBook extends Item{

    public ItemAdminBook(){
        setCreativeTab(TrainsInMotion.creativeTab);
    }

    /**
     * <h2>Description text</h2>
     * Allows items to add custom lines of information to the mouseover description, by adding new lines to stringList.
     * Each string added defines a new line.
     * We can cover the key and ticket description here, to simplify other classes.
     */
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack thisStack, EntityPlayer player, List stringList, boolean p_77624_4_) {
        stringList.add("This book is for Operators ONLY, and allows the following:");
        stringList.add("- drop trains/rollingstock and their inventory lost during a crash");
        stringList.add("- Lock or unlock trains/rollingstock");
    }
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer playerEntity, World worldObj, int posX, int posY, int posZ, int blockSide, float pointToRayX, float pointToRayY, float pointToRayZ) {

        if(worldObj.isRemote){
            return true;//checks if player is OP.
        } else if (!playerEntity.canCommandSenderUseCommand(2, "")){
            return false;
        }

        Vec3 v = playerEntity.getLookVec().normalize();
        for(float i = 0.5f;i<4;i+=0.5f){
            AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(playerEntity.posX + (v.xCoord * i),
                    playerEntity.posY + (v.yCoord * i), playerEntity.posZ + (v.zCoord * i),
                    playerEntity.posX + (v.xCoord * i), playerEntity.posY + (v.yCoord * i),
                    playerEntity.posZ + (v.zCoord * i));
            List list = worldObj.getEntitiesWithinAABB(GenericRailTransport.class, aabb);
            if(list.iterator().hasNext()){
                GenericRailTransport transport = (GenericRailTransport)list.get(0);
                TrainsInMotion.keyChannel.sendTo(new PacketAdminBook(0, transport.getEntityId(), ""), (EntityPlayerMP) playerEntity);
                return true;
            }
        }
        System.out.println("running");
        if(new File(DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath() + "/traincraft/").exists()) {
            //if player wasin't looking at a train
            StringBuilder sb = new StringBuilder();
            for (String f : new File(DimensionManager.getCurrentSaveRootDirectory().getAbsolutePath() + "/traincraft/").list()) {
                sb.append(f);
                sb.append(",");
            }
            //wrong player or something....?
            TrainsInMotion.keyChannel.sendTo(new PacketAdminBook(1, -1, sb.toString()), (EntityPlayerMP) playerEntity);
            return true;
        } else {
            return false;
        }

    }


    public static class PacketAdminBook implements IMessage{


        /**the key to define what function to use*/
        private int key;
        private int ID;
        private String datacsv;

        public PacketAdminBook(int key, int ID, String data) {
            this.key = key;
            this.ID = ID;
            this.datacsv = data;
        }
        /**reads the packet on server to get the variables from the Byte Buffer*/
        @Override
        public void fromBytes(ByteBuf bbuf) {
            String data = bbuf.toString();
            key=Integer.parseInt(data.substring(0,data.indexOf(",")));
            data= data.substring(data.indexOf(",")+1, data.length());
            ID=Integer.parseInt(data.substring(0,data.indexOf(",")));
            datacsv= data.substring(data.indexOf(",")+1, data.length()-1);
        }
        /**puts the variables into a Byte Buffer so they can be sent to server*/
        @Override
        public void toBytes(ByteBuf bbuf) {
            StringBuilder sb=new StringBuilder();
            sb.append(key);
            sb.append(",");
            sb.append(ID);
            sb.append(",");
            sb.append(datacsv);
            bbuf.writeBytes(sb.toString().getBytes());
        }


        public static class Handler implements IMessageHandler<PacketAdminBook, IMessage> {
            @Override
            public IMessage onMessage(PacketAdminBook message, MessageContext context) {

                Minecraft.getMinecraft().displayGuiScreen(new GUIAdminBook(message.datacsv));


                return null;
            }

        }




    }

}
