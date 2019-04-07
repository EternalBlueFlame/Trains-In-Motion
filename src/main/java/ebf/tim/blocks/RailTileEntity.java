package ebf.tim.blocks;

import ebf.tim.blocks.rails.RailShapeCore;
import ebf.tim.blocks.rails.RailVanillaShapes;
import ebf.tim.models.rails.Model1x1Rail;
import ebf.tim.utility.DebugUtil;
import fexcraft.tmt.slim.TextureManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RailTileEntity extends TileEntity {

    private AxisAlignedBB boundingBox = null;
    //management variables
    public ItemStack rail;
    private int[] railColor=null;
    public ItemStack ballast;
    public ItemStack ties;
    public ItemStack wires;
    public int snow=0;
    public int timer=0;
    public int overgrowth=0;
    Integer railGLID=null;

    public float getRailSpeed(){
        return 0.4f;
    }

    //used for the actual path, and rendered
    public List<float[]> points = new ArrayList<>();
    public float segmentLength=0;
    //used to define the number of rails and their offset from the center.
    public float[] railGauges;
    //TODO: only rendered, to show other paths, maybe rework so they are all in the same list and just have a bool for which is active?
    //public List<RailPointData> cosmeticPoints = new ArrayList<>();


    public void func_145828_a(@Nullable CrashReportCategory report)  {
        if (report == null) {
            if (!worldObj.isRemote) {
                return;
            }
            //DebugUtil.println(segmentLength);
            TextureManager.adjustLightFixture(worldObj,xCoord,yCoord,zCoord);
            if(railGLID!=null){
                org.lwjgl.opengl.GL11.glCallList(railGLID);
            } else {

                if(railColor==null){
                    if(rail !=null && TextureManager.ingotColors!=null) {
                        for(Map.Entry<ItemStack, int[]> e : TextureManager.ingotColors.entrySet()){
                            if(e.getKey().getItem()==rail.getItem()&&
                                    e.getKey().getTagCompound()==rail.getTagCompound() &&
                                        e.getKey().getItemDamage()==rail.getItemDamage()) {
                                railColor = TextureManager.ingotColors.get(e.getKey());
                            }
                        }
                    }
                    if(railColor==null){
                        railColor=new int[]{0,0,0};
                    }
                }

                railGLID = net.minecraft.client.renderer.GLAllocation.generateDisplayLists(1);
                org.lwjgl.opengl.GL11.glNewList(railGLID, org.lwjgl.opengl.GL11.GL_COMPILE);
                Model1x1Rail.Model3DRail(worldObj,xCoord,yCoord,zCoord,points, gauge750mm, segmentLength, ties, ballast, railColor);
                org.lwjgl.opengl.GL11.glEndList();
            }
        } else {super.func_145828_a(report);}
    }

    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
        return oldMeta != newMeta || points.size()==0;
    }

    @Override
    public boolean canUpdate(){return worldObj!=null && !worldObj.isRemote;}

    public static final float[] gauge750mm={0.3125f, -0.3125f};

    //int[] meta = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};//3x3x3. value -1 is for blocks that aren't a rail
    int tickOffset=0;
    @Override
    public void updateEntity(){
        if (worldObj.isRemote) return;
        tickOffset++;
        if(tickOffset>20){
            tickOffset=1;
        }
    }

    public void updateShape() {
        points= new ArrayList<>();
        //todo: process these directly into quad/processPoints(); on server, then sync the offsets over the NBT network packet.
            switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord)){
                //Z straight
                case 0: {
                    RailShapeCore.processPoints(RailVanillaShapes.vanillaZStraight(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                //X straight
                case 1: {
                    RailShapeCore.processPoints(RailVanillaShapes.vanillaXStraight(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }

                //curves
                case 9: {
                    RailShapeCore.processPoints(RailVanillaShapes.vanillaCurve9(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                case 8: {
                    RailShapeCore.processPoints(RailVanillaShapes.vanillaCurve8(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                case 7: {
                    RailShapeCore.processPoints(RailVanillaShapes.vanillaCurve7(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                case 6: {
                    RailShapeCore.processPoints(RailVanillaShapes.vanillaCurve6(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                //Z slopes
                case 5 :{
                    RailShapeCore.processPoints(RailVanillaShapes.vanillaSlopeZ5(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                case 4 :{
                    RailShapeCore.processPoints(RailVanillaShapes.vanillaSlopeZ4(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                //X slopes
                case 2 :{
                    RailShapeCore.processPoints(RailVanillaShapes.vanillaSlopeX2(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                case 3 :{
                    RailShapeCore.processPoints(RailVanillaShapes.vanillaSlopeX3(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
            }

    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if (boundingBox == null) {
            boundingBox = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
        }
        return boundingBox;
    }


    public void markDirty() {
        if (this.worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            //this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
            this.worldObj.getChunkFromBlockCoords(xCoord,yCoord).setChunkModified();
            for (int x=-1;x<2;x++){
                for (int y=-1;y<2;y++){
                    for (int z=-1;z<2;z++){
                        worldObj.getBlock(xCoord+x,yCoord+y,zCoord+z).onNeighborChange(
                                worldObj, xCoord+x,yCoord+y,zCoord+z, xCoord, yCoord, zCoord);
                    }
                }
            }
            this.worldObj.func_147453_f(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
            updateShape();
        }

    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        if(pkt ==null){return;}
        readFromNBT(pkt.func_148857_g());
        markDirty();
    }


    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        NBTTagCompound compound = new NBTTagCompound();
        if(rail!=null) {
            rail.writeToNBT(compound);
            tag.setTag("rail", compound);
        }
        if(ballast!=null) {
            compound = new NBTTagCompound();
            ballast.writeToNBT(compound);
            tag.setTag("ballast", compound);
        }
        if(ties!=null) {
            compound = new NBTTagCompound();
            ties.writeToNBT(compound);
            tag.setTag("ties", compound);
        }
        if(wires!=null) {
            compound = new NBTTagCompound();
            wires.writeToNBT(compound);
            tag.setTag("wires", compound);
        }

        tag.setFloat("segmentLength", segmentLength);
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(points);
            tag.setByteArray("path", bo.toByteArray());
            so.flush();
            bo.close();

        } catch (Exception e){
            System.out.println("HOW DID YOU GET AN IO EXCEPTION WITHOUT WRITING TO FILE?");
            e.printStackTrace();
        }

    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        if(tag.hasKey("rail")) {
            rail = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("rail"));
        }
        if(tag.hasKey("ballast")) {
            ballast = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("ballast"));
        }
        if(tag.hasKey("ties")) {
            ties = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("ties"));
        }
        if(tag.hasKey("wires")) {
            wires = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("wires"));
        }

        if (tag.hasKey("segmentLength")) {
            segmentLength = tag.getFloat("segmentLength");
        }

        byte[] b = tag.getByteArray("path");
        try {
          points = (List<float[]>) new ObjectInputStream(new ByteArrayInputStream(b)).readObject();
        } catch (Exception e){
          e.printStackTrace();
        }
    }

}
