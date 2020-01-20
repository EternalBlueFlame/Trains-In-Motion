package ebf.tim.blocks;

import ebf.XmlBuilder;
import ebf.tim.blocks.rails.RailShapeCore;
import ebf.tim.blocks.rails.RailSimpleShape;
import ebf.tim.models.rails.Model1x1Rail;
import ebf.tim.utility.DebugUtil;
import ebf.tim.utility.Vec5f;
import fexcraft.tmt.slim.TextureManager;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;


public class RailTileEntity extends TileEntity {

    private AxisAlignedBB boundingBox = null;
    //management variables
    //todo public int snow=0;
    //todo public int timer=0;
    //todo public int overgrowth=0;
    public Integer railGLID=null;
    public XmlBuilder data = new XmlBuilder();
    public boolean updateModel=false;
    //used for the actual path, and rendered
    //public RailShapeCore points = new RailShapeCore();
    //TODO: only rendered, to show other paths, maybe rework so they are all in the same list and just have a bool for which is active?
    //public List<RailPointData> cosmeticPoints = new ArrayList<>();


    public void func_145828_a(@Nullable CrashReportCategory report)  {
        if (report == null) {
            if (!worldObj.isRemote) {
                return;
            }
            TextureManager.adjustLightFixture(worldObj,xCoord,yCoord,zCoord);
            if(railGLID!=null){
                org.lwjgl.opengl.GL11.glCallList(railGLID);
            }

            if(railGLID==null || updateModel){
                RailShapeCore route =new RailShapeCore().parseString(data.getString("route"));
                if (route!=null && route.gauge!=null) {
                    railGLID = net.minecraft.client.renderer.GLAllocation.generateDisplayLists(1);
                    org.lwjgl.opengl.GL11.glNewList(railGLID, org.lwjgl.opengl.GL11.GL_COMPILE);

                    Model1x1Rail.Model3DRail(worldObj, xCoord, yCoord, zCoord,
                            route,
                            data.getItemStack("ballast"),
                            data.getItemStack("ties"),
                            data.getItemStack("rail"), null);

                    org.lwjgl.opengl.GL11.glEndList();
                    updateModel = false;
                }
            }
        } else {super.func_145828_a(report);}
    }

    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
        return false;
    }

    @Override
    public boolean canUpdate(){return false;}

    //int[] meta = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};//3x3x3. value -1 is for blocks that aren't a rail
    @Override
    public void updateEntity(){}

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
            updateModel=true;
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
        tag.setString("data", data.toXMLString());

    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        if(tag==null){return;}
        super.readFromNBT(tag);
        String s = tag.getString("data");
        data = new XmlBuilder(s);

    }

}
