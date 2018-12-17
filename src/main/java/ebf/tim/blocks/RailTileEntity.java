package ebf.tim.blocks;

import cpw.mods.fml.common.registry.GameData;
import ebf.tim.blocks.rails.RailShapeCore;
import ebf.tim.blocks.rails.RailVanillaShapes;
import ebf.tim.items.ItemRail;
import ebf.tim.models.rails.Model1x1Rail;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
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


public class RailTileEntity extends TileEntity {

    private AxisAlignedBB boundingBox = null;
    //management variables
    public ItemStack rail;
    public Block ballast;
    public Block ties;
    public Block wires;
    public int snow=0;
    public int timer=0;
    public int overgrowth=0;
    //render data
    public int metal = 0xcccccc;

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
            Model1x1Rail.Model3DRail(points, gauge750mm, segmentLength, ties, ballast, rail);
        } else {super.func_145828_a(report);}
    }

    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
        return oldMeta != newMeta;
    }

    @Override
    public boolean canUpdate(){return worldObj!=null && !worldObj.isRemote;}

    public static final float[] gauge750mm={0.3125f, -0.3125f};

    int[] meta = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};//3x3x3. value -1 is for blocks that aren't a rail
    int tickOffset=0;
    @Override
    public void updateEntity(){
        if (worldObj.isRemote) return;
        tickOffset++;
        if(tickOffset>20){
            tickOffset=1;
        }
        if(tickOffset!=1){
            return;
        }
        int itteration = 0;
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                for (int z = -1; z < 2; z++) {
                    if (meta[itteration] != ((worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z) instanceof BlockRailBase) ?
                            worldObj.getBlockMetadata(xCoord + x, yCoord + y, zCoord + z) :
                            -1)) {
                        meta[itteration] = ((worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z) instanceof BlockRailBase) ?
                                worldObj.getBlockMetadata(xCoord + x, yCoord + y, zCoord + z) :
                                -1);
                        updateShape();
                    }
                    itteration++;
                }
            }
        }
    }

    public void updateShape() {

        List<float[]> oldPoints = points;//hold on to check if we need update.


        points= new ArrayList<>();
        //todo: process these directly into quad/triProcessPoints on server, then sync the offsets over the NBT network packet.
            switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord)){
                //Z straight
                case 0: {
                    RailShapeCore.triProcessPoints(RailVanillaShapes.vanillaZStraight(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                //X straight
                case 1: {
                    RailShapeCore.triProcessPoints(RailVanillaShapes.vanillaXStraight(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }

                //curves
                case 9: {
                    RailShapeCore.triProcessPoints(RailVanillaShapes.vanillaCurve9(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                case 8: {
                    RailShapeCore.triProcessPoints(RailVanillaShapes.vanillaCurve8(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                case 7: {
                    RailShapeCore.triProcessPoints(RailVanillaShapes.vanillaCurve7(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                case 6: {
                    RailShapeCore.triProcessPoints(RailVanillaShapes.vanillaCurve6(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                //Z slopes
                case 5 :{
                    RailShapeCore.quadProcessPoints(RailVanillaShapes.vanillaSlopeZ5(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                case 4 :{
                    RailShapeCore.quadProcessPoints(RailVanillaShapes.vanillaSlopeZ4(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                //X slopes
                case 2 :{
                    RailShapeCore.quadProcessPoints(RailVanillaShapes.vanillaSlopeX2(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
                case 3 :{
                    RailShapeCore.quadProcessPoints(RailVanillaShapes.vanillaSlopeX3(worldObj, xCoord, yCoord, zCoord), gauge750mm, this, 4);
                    break;
                }
            }

            if(oldPoints != points) {
                this.markDirty();
            }
            oldPoints=null;//clean it out of ram, we dont need it now, save GC the effort.

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
        }

    }

    @Override
    public Packet getDescriptionPacket() {
            updateShape();
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        if(pkt ==null){return;}
        readFromNBT(pkt.func_148857_g());
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
            new ItemStack(Item.getItemFromBlock(ballast)).writeToNBT(compound);
            tag.setTag("ballast", compound);
        }
        if(ties!=null) {
            compound = new NBTTagCompound();
            new ItemStack(Item.getItemFromBlock(ties)).writeToNBT(compound);
            tag.setTag("ties", compound);
        }
        if(wires!=null) {
            compound = new NBTTagCompound();
            new ItemStack(Item.getItemFromBlock(wires)).writeToNBT(compound);
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
            ballast = Block.getBlockFromItem(ItemStack.loadItemStackFromNBT(tag.getCompoundTag("ballast")).getItem());
        }
        if(tag.hasKey("ties")) {
            ties = Block.getBlockFromItem(ItemStack.loadItemStackFromNBT(tag.getCompoundTag("ties")).getItem());
        }
        if(tag.hasKey("wires")) {
            wires = Block.getBlockFromItem(ItemStack.loadItemStackFromNBT(tag.getCompoundTag("wires")).getItem());
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
