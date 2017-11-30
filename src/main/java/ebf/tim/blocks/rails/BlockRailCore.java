package ebf.tim.blocks.rails;

import ebf.tim.blocks.RailTileEntity;
import ebf.tim.models.rails.*;
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
        if (world.getTileEntity(x,y,z) instanceof RailTileEntity){
            return ((RailTileEntity) world.getTileEntity(x,y,z)).getRailSpeed();
        }
        return 0.4f;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        setTickRandomly(true);
        return new RailTileEntity();
    }

    @Override
    public int getBasicRailMetadata(IBlockAccess world, EntityMinecart cart, int x, int y, int z) {
        return RailVanillaShapes.processRailMeta(super.getBasicRailMetadata(world, cart, x, y, z), cart,x,y,z);
    }


    @Override
    public void randomDisplayTick(World worldObj, int xCoord, int yCoord, int zCoord, Random p_149674_5_) {
        super.randomDisplayTick(worldObj, xCoord, yCoord, zCoord, p_149674_5_);
        updateTick(worldObj, xCoord, yCoord, zCoord, p_149674_5_);
    }

    //TODO: nee a new method(s) to run this from that's less common, because this seems to be rather demanding
    @Override
    public void updateTick(World worldObj, int xCoord, int yCoord, int zCoord, Random p_149674_5_) {
        super.updateTick(worldObj, xCoord,yCoord,zCoord,p_149674_5_);
        hasTicked = !hasTicked;
        if (hasTicked || !(worldObj.getTileEntity(xCoord,yCoord,zCoord) instanceof RailTileEntity)){
            return;
        }
        Vec3d[][] baseShape= new Vec3d[3][];
        switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord)){
            //Z straight
            case 0: {
                baseShape[0] = RailVanillaShapes.vanillaZStraight(worldObj, xCoord,yCoord,zCoord,0);
                baseShape[1] = RailVanillaShapes.vanillaZStraight(worldObj, xCoord,yCoord,zCoord,0.3125);
                baseShape[2] = RailVanillaShapes.vanillaZStraight(worldObj, xCoord,yCoord,zCoord,-0.3125);
                break;
            }
            //X straight
            case 1: {
                baseShape[0] = RailVanillaShapes.vanillaXStraight(worldObj, xCoord, yCoord, zCoord, 0);
                baseShape[1] = RailVanillaShapes.vanillaXStraight(worldObj, xCoord, yCoord, zCoord, 0.3125);
                baseShape[2] = RailVanillaShapes.vanillaXStraight(worldObj, xCoord, yCoord, zCoord, -0.3125);
                break;
            }

            //curves
            case 9: {
                baseShape[0] = RailVanillaShapes.vanillaCurve9(worldObj, xCoord, yCoord, zCoord, 0);
                baseShape[1] = RailVanillaShapes.vanillaCurve9(worldObj, xCoord, yCoord, zCoord, 0.3125);
                baseShape[2] = RailVanillaShapes.vanillaCurve9(worldObj, xCoord, yCoord, zCoord, -0.3125);
                break;
            }
            case 8: {
                baseShape[0] = RailVanillaShapes.vanillaCurve8(worldObj, xCoord, yCoord, zCoord, 0);
                baseShape[1] = RailVanillaShapes.vanillaCurve8(worldObj, xCoord, yCoord, zCoord, 0.3125);
                baseShape[2] = RailVanillaShapes.vanillaCurve8(worldObj, xCoord, yCoord, zCoord, -0.3125);
                break;
            }
            case 7: {
                baseShape[0] = RailVanillaShapes.vanillaCurve7(worldObj, xCoord, yCoord, zCoord, 0);
                baseShape[1] = RailVanillaShapes.vanillaCurve7(worldObj, xCoord, yCoord, zCoord, 0.3125);
                baseShape[2] = RailVanillaShapes.vanillaCurve7(worldObj, xCoord, yCoord, zCoord, -0.3125);
                break;
            }
            case 6: {
                baseShape[0] = RailVanillaShapes.vanillaCurve6(worldObj, xCoord, yCoord, zCoord, 0);
                baseShape[1] = RailVanillaShapes.vanillaCurve6(worldObj, xCoord, yCoord, zCoord, 0.3125);
                baseShape[2] = RailVanillaShapes.vanillaCurve6(worldObj, xCoord, yCoord, zCoord, -0.3125);
                break;
            }
            //Z slopes
            case 5 :{
                baseShape[0] = RailVanillaShapes.vanillaSlopeZ5(worldObj, xCoord, yCoord, zCoord, 0);
                baseShape[1] = RailVanillaShapes.vanillaSlopeZ5(worldObj, xCoord, yCoord, zCoord, 0.3125);
                baseShape[2] = RailVanillaShapes.vanillaSlopeZ5(worldObj, xCoord, yCoord, zCoord, -0.3125);
                break;
            }
            case 4 :{
                baseShape[0] = RailVanillaShapes.vanillaSlopeZ4(worldObj, xCoord, yCoord, zCoord, 0);
                baseShape[1] = RailVanillaShapes.vanillaSlopeZ4(worldObj, xCoord, yCoord, zCoord, 0.3125);
                baseShape[2] = RailVanillaShapes.vanillaSlopeZ4(worldObj, xCoord, yCoord, zCoord, -0.3125);
                break;
            }
            //X slopes
            case 2 :{
                baseShape[0] = RailVanillaShapes.vanillaSlopeX2(worldObj, xCoord, yCoord, zCoord, 0);
                baseShape[1] = RailVanillaShapes.vanillaSlopeX2(worldObj, xCoord, yCoord, zCoord, 0.3125);
                baseShape[2] = RailVanillaShapes.vanillaSlopeX2(worldObj, xCoord, yCoord, zCoord, -0.3125);
                break;
            }
            case 3 :{
                baseShape[0] = RailVanillaShapes.vanillaSlopeX3(worldObj, xCoord, yCoord, zCoord, 0);
                baseShape[1] = RailVanillaShapes.vanillaSlopeX3(worldObj, xCoord, yCoord, zCoord, 0.3125);
                baseShape[2] = RailVanillaShapes.vanillaSlopeX3(worldObj, xCoord, yCoord, zCoord, -0.3125);
                break;
            }
        }

        //set the path
        ((RailTileEntity) worldObj.getTileEntity(xCoord,yCoord,zCoord)).
                setPath(quadCurveAtPoint(1, baseShape[0][0], baseShape[0][1], baseShape[0][2], baseShape[0][3]));


        ((RailTileEntity) worldObj.getTileEntity(xCoord,yCoord,zCoord)).
                setRenderShape(quadCurveAtPoint(1, baseShape[1][0], baseShape[1][1], baseShape[1][2], baseShape[1][3]), quadCurveAtPoint(1, baseShape[2][0], baseShape[2][1], baseShape[2][2], baseShape[2][3]));


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

    /**
     * generated a 3 point bezier curve using De Casteljau's algorithm on each axis.
     * @param t number of blocks in length
     * @param P1 first point
     * @param P2 second point (mostly modifies first)
     * @param P3 third point (mostly modifies last)
     * @param P4 end point
     * @return the list of rail points to define positional data.
     */
    protected static List<RailPointRenderData> quadCurveAtPoint(double t, Vec3d P1, Vec3d P2, Vec3d P3, Vec3d P4){
        if (t ==0){
            throw new ReportedException(CrashReport.makeCrashReport(new Throwable(), "Why did you make a rail with a length of 0 blocks???"));
        }
        final double originalT =(1F/16F)/t;
        t=0;
        List<RailPointRenderData> points = new ArrayList<RailPointRenderData>();
        while(t<=1.0d) {
            RailPointRenderData tempPoint = new RailPointRenderData();
            //define position
            tempPoint.position = new double[]{
                    (Math.pow(1 - t, 3) * P1.xCoord) + (3*Math.pow(1-t,2)*t*P2.xCoord) + (3*(1-t)*Math.pow(t,2)*P3.xCoord) + (Math.pow(t,3)*P4.xCoord),//X
                    (Math.pow(1 - t, 3) * P1.yCoord) + (3*Math.pow(1-t,2)*t*P2.yCoord) + (3*(1-t)*Math.pow(t,2)*P3.yCoord) + (Math.pow(t,3)*P4.yCoord),//Y
                    (Math.pow(1 - t, 3) * P1.zCoord) + (3*Math.pow(1-t,2)*t*P2.zCoord) + (3*(1-t)*Math.pow(t,2)*P3.zCoord) + (Math.pow(t,3)*P4.zCoord),//X
                    0,0,0
            };
            //define yaw rotation
            if(t==originalT){
                points.get(0).position[4] = 180-Math.toDegrees(Math.atan2(tempPoint.position[2] - points.get(0).position[2], tempPoint.position[0] - points.get(0).position[0]));
            }
            if (t !=0){
                double dX = tempPoint.position[0] - points.get(points.size()-1).position[0];
                double dY = tempPoint.position[1] - points.get(points.size()-1).position[1];
                double dZ = tempPoint.position[2] - points.get(points.size()-1).position[2];
                tempPoint.position[4] = 180-Math.toDegrees(Math.atan2(tempPoint.position[2] - points.get(points.size()-1).position[2], tempPoint.position[0] - points.get(points.size()-1).position[0]));
                tempPoint.position[3] = Math.toDegrees(Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI);
            }
            //TODO: define pitch and roll
            points.add(tempPoint);
            t += originalT;
        }
        RailPointRenderData.setRotation(points);
        return points;
    }



    protected static List<RailPointRenderData> quadCurveAtPointR(double t, Vec3d P1, Vec3d P2, Vec3d P3, Vec3d P4, double gague){
        if (t ==0){
            throw new ReportedException(CrashReport.makeCrashReport(new Throwable(), "Why did you make a rail with a length of 0 blocks???"));
        }
        final double originalT =(1F/16F)/t;
        t=0;
        List<RailPointRenderData> points = new ArrayList<RailPointRenderData>();
        while(t<=1.0d) {
            RailPointRenderData tempPoint = new RailPointRenderData();
            //define position
            tempPoint.position = new double[]{
                    (Math.pow(1 - t, 3) * P1.xCoord) + (3*Math.pow(1-t,2)*t*P2.xCoord) + (3*(1-t)*Math.pow(t,2)*P3.xCoord) + (Math.pow(t,3)*P4.xCoord),//X
                    (Math.pow(1 - t, 3) * P1.yCoord) + (3*Math.pow(1-t,2)*t*P2.yCoord) + (3*(1-t)*Math.pow(t,2)*P3.yCoord) + (Math.pow(t,3)*P4.yCoord),//Y
                    (Math.pow(1 - t, 3) * P1.zCoord) + (3*Math.pow(1-t,2)*t*P2.zCoord) + (3*(1-t)*Math.pow(t,2)*P3.zCoord) + (Math.pow(t,3)*P4.zCoord),//X
                    0,0,0
            };
            //define yaw rotation
            if(t==originalT){
                points.get(0).position[4] = 180-Math.toDegrees(Math.atan2(tempPoint.position[2] - points.get(0).position[2], tempPoint.position[0] - points.get(0).position[0]));
                double[] vec = RailUtility.rotatePoint(new double[]{0,0,gague}, (float)points.get(0).position[3], (float)points.get(0).position[4],0);
                points.get(0).position[0] += vec[0];
                points.get(0).position[1] += vec[1];
                points.get(0).position[2] += vec[2];

            }
            if (t !=0){
                double dX = tempPoint.position[0] - points.get(points.size()-1).position[0];
                double dY = tempPoint.position[1] - points.get(points.size()-1).position[1];
                double dZ = tempPoint.position[2] - points.get(points.size()-1).position[2];
                tempPoint.position[4] = 180-Math.toDegrees(Math.atan2(tempPoint.position[2] - points.get(points.size()-1).position[2], tempPoint.position[0] - points.get(points.size()-1).position[0]));
                tempPoint.position[3] = Math.toDegrees(Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI);
                double[] vec = RailUtility.rotatePoint(new double[]{0,0,gague}, (float)tempPoint.position[3], (float)tempPoint.position[4],0);
                tempPoint.position[0] += vec[0];
                tempPoint.position[1] += vec[1];
                tempPoint.position[2] += vec[2];
            }
            //TODO: define pitch and roll
            points.add(tempPoint);
            t += originalT;
        }
        RailPointRenderData.setRotation(points);
        return points;
    }
}
