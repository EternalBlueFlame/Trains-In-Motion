package ebf.tim.blocks.rails;

import ebf.tim.TrainsInMotion;
import ebf.tim.blocks.RailTileEntity;
import ebf.tim.models.rails.*;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.CommonProxy;
import net.minecraft.block.Block;
import tmt.Vec3d;
import ebf.tim.utility.RailUtility;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tmt.Vec3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Eternal Blue Flame
 */
public class BlockRailCore extends BlockRail implements ITileEntityProvider {

    private boolean hasTicked = true;

    @Override
    public int tickRate(World world){return 10;}


    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isFlexibleRail(IBlockAccess world, int y, int x, int z){return true;}

    @Override
    public float getRailMaxSpeed(World world, EntityMinecart cart, int y, int x, int z){
        //if (world.getTileEntity(x,y,z) instanceof RailTileEntity){
        //    return ((RailTileEntity) world.getTileEntity(x,y,z)).getRailSpeed();
        //}todo: do this in the rail, the tile entity should only be an intermediate for the block to save
        return 0.4f;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        setTickRandomly(true);
        return new RailTileEntity();
    }

    @Override
    public int getBasicRailMetadata(IBlockAccess world, EntityMinecart cart, int x, int y, int z) {
        return world.getBlockMetadata(x,y,z);//RailVanillaShapes.processRailMeta(super.getBasicRailMetadata(world, cart, x, y, z), cart,x,y,z);
    }


    @Override
    public void randomDisplayTick(World worldObj, int xCoord, int yCoord, int zCoord, Random p_149674_5_) {
        super.randomDisplayTick(worldObj, xCoord, yCoord, zCoord, p_149674_5_);
        updateTick(worldObj, xCoord, yCoord, zCoord, p_149674_5_);
    }


    @Override
    public void updateTick(World worldObj, int xCoord, int yCoord, int zCoord, Random p_149674_5_) {
        super.updateTick(worldObj, xCoord,yCoord,zCoord,p_149674_5_);
    }




    @Override
    public void onNeighborBlockChange(World worldObj, int x, int y, int z, Block b) {
        super.onNeighborBlockChange(worldObj, x, y, z, b);
    }

    //stuff from block container to make tile entity more reliable.
    @Override
    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
    }
    @Override
    public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_)
    {
        super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
        TileEntity tileentity = p_149696_1_.getTileEntity(p_149696_2_, p_149696_3_, p_149696_4_);
        return tileentity != null && tileentity.receiveClientEvent(p_149696_5_, p_149696_6_);
    }



    /**
     * checks an array of blocks for a specific class and meta to check if the shape should be made.
     * @param worldobj
     * @param positionsWithMeta array of vector 4's with the 4th value being the desired meta
     * @return boolean for if the shape can be made
     */
    public static boolean checkMultiblock(World worldobj, int[][] positionsWithMeta){
        for(int[] point : positionsWithMeta){
            if (!worldobj.getChunkProvider().chunkExists(point[0]/16, point[2]/16)){
                return false;
            } else if (!(worldobj.getBlock(point[0],point[1],point[2]) instanceof BlockRailBase)){
                return false;
            } else if (worldobj.getBlockMetadata(point[0],point[1],point[2]) != point[3]){
                return false;
            }
        }
        return true;
    }


    protected static List<ModelRailSegment> quadGenModel(Vec3f P1, Vec3f P2, Vec3f P3, Vec3f P4, float[] railOffsets, float blockLength){
        double dX=P4.xCoord - P1.xCoord;
        double dZ=P4.zCoord - P1.zCoord;
        float segment = TrainsInMotion.proxy.isClient()?ClientProxy.railLoD:8;
        segment*=blockLength;
        if (segment<3){
            segment=3;
        }
        double originalT =(Math.abs(P4.xCoord) + Math.abs(P4.zCoord) + Math.abs(P1.xCoord) + Math.abs(P1.zCoord))/ (segment*(Math.abs(P4.xCoord) + Math.abs(P4.zCoord) + Math.abs(P1.xCoord) + Math.abs(P1.zCoord)));
        double t=-originalT;
        int i;
        //calculate the bezier curve
        List<float[]> points = new ArrayList<>();
        for (i=0; i<segment+3;i++){
            //define position
            points.add(new float[]{
                    (float) ((Math.pow(1 - t, 3) * P1.xCoord) + (3*Math.pow(1-t,2)*t*P2.xCoord) + (3*(1-t)*Math.pow(t,2)*P3.xCoord) + (Math.pow(t,3)*P4.xCoord)),//X
                    (float) ((Math.pow(1 - t, 3) * P1.yCoord) + (3*Math.pow(1-t,2)*t*P2.yCoord) + (3*(1-t)*Math.pow(t,2)*P3.yCoord) + (Math.pow(t,3)*P4.yCoord)),//Y
                    (float) ((Math.pow(1 - t, 3) * P1.zCoord) + (3*Math.pow(1-t,2)*t*P2.zCoord) + (3*(1-t)*Math.pow(t,2)*P3.zCoord) + (Math.pow(t,3)*P4.zCoord))//Z
            });
            t += originalT;
        }
        points.set(points.size()-2, new float[]{P4.xCoord,P4.yCoord,P4.zCoord});
        points.set(1, new float[]{P1.xCoord,P1.yCoord,P1.zCoord});

        double[] offsetInner;
        double[] offsetOuter;

        //now make the points
        List<ModelRailSegment> segments = new ArrayList<>();
        for (i = 1; i < points.size() - 1; i++) {

            //define the actual path
            ModelRailSegment seg = new ModelRailSegment();
            seg.position = new float[]{
                    points.get(i)[0] * 16, points.get(i)[1] * 16, points.get(i)[2] * 16
            };

            //define the offset for model modifications.
            dX = points.get(i-1)[0] - (points.get(i + 1)[0]);
            dZ = points.get(i-1)[2] - (points.get(i + 1)[2]);
            offsetOuter = RailUtility.rotatePoint(new double[]{0,0,0.0625}, 0,(float)Math.toDegrees(Math.atan2(dZ,dX)),0);
            seg.zOffset = new float[]{(float)offsetOuter[0],(float)offsetOuter[1],(float)offsetOuter[2]};

            //define the front positions for each model
            for (float f : railOffsets){
                if (f==0f || !TrainsInMotion.proxy.isClient()){
                    continue;
                }
                offsetInner = RailUtility.rotatePoint(new double[]{0,0,-0.0625+f}, 0,(float)Math.toDegrees(Math.atan2(dZ,dX)),0);
                offsetOuter = RailUtility.rotatePoint(new double[]{0,0,0.0625+f}, 0,(float)Math.toDegrees(Math.atan2(dZ,dX)),0);

                seg.models.add(seg.genNewSubModel(
                        new float[]{
                                (float)(offsetInner[0]+points.get(i)[0]), (float)(offsetInner[1]+points.get(i)[1]), (float)(offsetInner[2]+points.get(i)[2])
                        },
                        new float[]{
                                (float)(offsetOuter[0]+points.get(i)[0]), (float)(offsetOuter[1]+points.get(i)[1]), (float)(offsetOuter[2]+points.get(i)[2])
                        }
                ));
            }

            segments.add(seg);
        }

        //connect the back positions of each model to the previous model's front positions
        if (segments.size()>0 && TrainsInMotion.proxy.isClient()) {
            int ii=0;
            for (i = 0; i < segments.size() - 1; i++) {
                for (ii=0; ii<segments.get(i).models.size(); ii++) {

                    segments.get(i).models.get(ii).lastPositionInner = segments.get(i + 1).models.get(ii).positionInner;
                    segments.get(i).models.get(ii).lastPositionOuter = segments.get(i + 1).models.get(ii).positionOuter;
                }
            }

            for (ii=0; ii<segments.get(i).models.size(); ii++) {
                segments.get(segments.size() - 1).models.get(ii).lastPositionInner = segments.get(segments.size() - 1).models.get(ii).positionInner = null;
                segments.get(segments.size() - 1).models.get(ii).lastPositionOuter = segments.get(segments.size() - 1).models.get(ii).lastPositionOuter = null;
            }
        }
        return segments;
    }


/* three point bezier
                        (Math.pow(1 - t, 2) * P1.xCoord) + (2 * (1 - t) * t * P2.xCoord) + ((Math.pow(t, 2) * P3.xCoord)),//X
                        (Math.pow(1 - t, 2) * P1.yCoord) + (2 * (1 - t) * t * P2.yCoord) + ((Math.pow(t, 2) * P3.yCoord)),//Y
                        (Math.pow(1 - t, 2) * P1.zCoord) + (2 * (1 - t) * t * P2.zCoord) + ((Math.pow(t, 2) * P3.zCoord)),//X
 */
}
