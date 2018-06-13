package ebf.tim.blocks;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameData;
import ebf.tim.TrainsInMotion;
import ebf.tim.blocks.rails.RailShapeCore;
import ebf.tim.blocks.rails.RailVanillaShapes;
import ebf.tim.models.rails.ModelRailSegment;
import fexcraft.tmt.slim.Vec3f;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import fexcraft.tmt.slim.Tessellator;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import scala.reflect.internal.Trees;
import sun.misc.BASE64Encoder;

import javax.annotation.Nullable;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RailTileEntity extends TileEntity {

    private AxisAlignedBB boundingBox = null;
    public List<List<?extends ModelRailSegment>> renderPath = new ArrayList<>();
    public List<Vec3f[]> pathCore = null;
    //management variables
    public Block ballast = null;
    public Block ties = null;
    public Block wires = null;
    public Item ingot;
    public int snow=0;
    public int timer=0;
    public int overgrowth=0;
    //render data
    public int metal = 0xcccccc;

    public float getRailSpeed(){
        return 0.4f;
    }

    private static final double[] color = {
            (Blocks.iron_block.getMapColor(0).colorValue >> 16 & 0xFF)* 0.00392156863,
            (Blocks.iron_block.getMapColor(0).colorValue >> 8 & 0xFF)* 0.00392156863,
            (Blocks.iron_block.getMapColor(0).colorValue & 0xFF)* 0.00392156863
    };

    public void func_145828_a(@Nullable CrashReportCategory report)  {
        if (report == null) {
            if (!worldObj.isRemote || renderPath == null) {
                return;
            }
            int i=0;
            int last=0;
            for (List<? extends ModelRailSegment> path : renderPath) {
                last = renderPath.size() - 1;
                for (ModelRailSegment point : path) {

                    for (ModelRailSegment.subModel model : point.models) {
                        model.render(Tessellator.getInstance(), i == 0, i == last, ballast, ties);
                    }
                    i++;
                }
            }
        } else {super.func_145828_a(report);}
    }

    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
        return oldMeta != newMeta;
    }

    @Override
    public boolean canUpdate(){return worldObj!=null && !worldObj.isRemote;}

    public static final float[] gauge750mm={0f, 0.3125f, -0.3125f};

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

        List<Vec3f[]> oldPath = pathCore;

            switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord)){
                //Z straight
                case 0: {
                    pathCore = RailVanillaShapes.vanillaZStraight(worldObj, xCoord, yCoord, zCoord);
                    break;
                }
                //X straight
                case 1: {
                    pathCore = RailVanillaShapes.vanillaXStraight(worldObj, xCoord, yCoord, zCoord);
                    break;
                }

                //curves
                case 9: {
                    pathCore = RailVanillaShapes.vanillaCurve9(worldObj, xCoord, yCoord, zCoord);
                    break;
                }
                case 8: {
                    pathCore = RailVanillaShapes.vanillaCurve8(worldObj, xCoord, yCoord, zCoord);
                    break;
                }
                case 7: {
                    pathCore = RailVanillaShapes.vanillaCurve7(worldObj, xCoord, yCoord, zCoord);
                    break;
                }
                case 6: {
                    pathCore = RailVanillaShapes.vanillaCurve6(worldObj, xCoord, yCoord, zCoord);
                    break;
                }
                //Z slopes
                case 5 :{
                    pathCore = RailVanillaShapes.vanillaSlopeZ5(worldObj, xCoord, yCoord, zCoord);
                    break;
                }
                case 4 :{
                    pathCore = RailVanillaShapes.vanillaSlopeZ4(worldObj, xCoord, yCoord, zCoord);
                    break;
                }
                //X slopes
                case 2 :{
                    pathCore = RailVanillaShapes.vanillaSlopeX2(worldObj, xCoord, yCoord, zCoord);
                    break;
                }
                case 3 :{
                    pathCore = RailVanillaShapes.vanillaSlopeX3(worldObj, xCoord, yCoord, zCoord);
                    break;
                }
            }

            if(oldPath != pathCore) {
                this.markDirty();
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
        if (pathCore == null){
            return;
        }
        renderPath = new ArrayList<>();
        for (Vec3f[] p : pathCore) {
            if (p.length == 3) {
                renderPath.add(RailShapeCore.triProcessPoints(p, gauge750mm, this, 1));
            } else if (p.length == 4) {
                renderPath.add(RailShapeCore.quadProcessPoints(p, gauge750mm, this, 1));
            }
        }
    }


    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        tag.setString("ballast", ballast!=null?ballast.delegate.name():"null");
        tag.setString("ties", ties!=null?ties.delegate.name():"null");
        tag.setString("rail", ingot!=null?ingot.delegate.name():"null");
        tag.setString("wires", wires!=null?wires.delegate.name():"null");
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(pathCore);
            tag.setByteArray("path", bo.toByteArray());
            so.flush();
        } catch (Exception e){
            System.out.println("HOW DID YOU GET AN IO EXCEPTION WITHOUT WRITING TO FILE?");
            e.printStackTrace();
        }

    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        String s = "null";
        if (tag.hasKey("ballast")) {
            s = tag.getString("ballast");
            if (!s.equals("null")) {
                ballast = GameData.getBlockRegistry().getObject(s);
            }
        }

        if (tag.hasKey("ties")) {
            s = tag.getString("ties");
            if (!s.equals("null")) {
                ties = GameData.getBlockRegistry().getObject(s);
            }
        }

        if (tag.hasKey("wires")) {
            s = tag.getString("wires");
            if (!s.equals("null")) {
                wires = GameData.getBlockRegistry().getObject(s);
            }
        }

        if (tag.hasKey("rail")) {
            s = tag.getString("rail");
            if (!s.equals("null")) {
                ingot = GameData.getItemRegistry().getObject(s);
            }
        }

        byte[] b = tag.getByteArray("path");
        pathCore = new ArrayList<>();
        try {
          pathCore = (List<Vec3f[]>) new ObjectInputStream(new ByteArrayInputStream(b)).readObject();
        } catch (Exception e){
          e.printStackTrace();
        }
    }

}
