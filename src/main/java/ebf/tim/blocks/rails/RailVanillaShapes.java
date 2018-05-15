package ebf.tim.blocks.rails;

import ebf.tim.models.rails.ModelRailSegment;
import ebf.tim.utility.RailUtility;
import net.minecraft.util.MathHelper;
import tmt.Vec3d;
import net.minecraft.world.World;
import tmt.Vec3f;

import java.util.List;

import static ebf.tim.blocks.rails.BlockRailCore.checkMultiblock;
import static ebf.tim.utility.RailUtility.radianF;

/**
 * @author Eternal Blue Flame
 */
public class RailVanillaShapes {


    public static List<?extends ModelRailSegment> vanillaZStraight(World worldObj, int xCoord, int yCoord, int zCoord, float gauge){
        Vec3f[] coords =
                groupBlockCoords(gauge,0.5f,
                        gauge, 0.25f,
                        gauge, -0.25f,
                        gauge, -0.5f);

        //X axis slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,4}})){
            coords[3].yCoord = 0.15f;
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1, 4}})){
            coords[0].yCoord = -0.15f;
        }
        //Z axis slope
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1, 5}})){
            coords[0].yCoord = 0.15f;
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1, 5}})){
            coords[3].yCoord = -0.15f;
        }

        //intersections
        if(checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,1},{xCoord, yCoord,zCoord+2,0}})){
            coords[0] = coordsFromBlock(gauge, 1.06f);
        }
        if(checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,1},{xCoord, yCoord,zCoord-2,0}})){
            coords[3] = coordsFromBlock(gauge, -1.06f);
        }

        //cover curve blending
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,7},{xCoord-1, yCoord,zCoord-1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,6},{xCoord+1, yCoord,zCoord-1,8}})){
            coords[3] = coordsFromBlock(gauge, 0);
            coords[2] = coordsFromBlock(gauge, 0.25f);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,9}, {xCoord+1, yCoord,zCoord+1,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,8},{xCoord-1, yCoord,zCoord+1,6}})){
            coords[0] = coordsFromBlock(gauge, 0);
            coords[1] = coordsFromBlock(gauge, -0.25f);
        }else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,7 }}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,6}})){
            coords[0] = coordsFromBlock(gauge, 1.5f);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,8}})) {
            coords[3] = coordsFromBlock(gauge, -1.5f);
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3]);
    }

    public static List<?extends ModelRailSegment> vanillaXStraight(World worldObj, int xCoord, int yCoord, int zCoord, float gauge){
        Vec3f[] coords =
                groupBlockCoords(
                  -0.5f,gauge,
                  -0.25f,gauge,
                  0.25f,gauge,
                  0.5f,gauge
                );

        //X axis slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,3}})){
            coords[0].yCoord = 0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            coords[3].yCoord = -0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3},{xCoord-1, yCoord,zCoord,3}})){
            coords[1].yCoord = 0.05f;
            coords[2].yCoord = -0.05f;
        }
        //Z axis slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,2}})){
            coords[3].yCoord = 0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            coords[0].yCoord = -0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}, {xCoord+1, yCoord,zCoord,2}})){
            coords[1].yCoord = -0.05f;
            coords[2].yCoord = 0.05f;
        }
        //intersections
        if(checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,0},{xCoord-2, yCoord,zCoord,1}})){
            coords[0] = coordsFromBlock(-1.06f, gauge);
        }
        if(checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,0},{xCoord+2, yCoord,zCoord,1}})){
            coords[3] = coordsFromBlock(1.06f, gauge);
        }


        //cover curve blending
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,9},{xCoord-1, yCoord,zCoord-1,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,6},{xCoord-1, yCoord,zCoord+1,8}})){
            coords[0] = coordsFromBlock(0,gauge);
            coords[1] = coordsFromBlock(0.25f,gauge);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,7}, {xCoord+1, yCoord,zCoord+1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,8},{xCoord+1, yCoord,zCoord-1,6}})){
            coords[3] = coordsFromBlock(0,gauge);
            coords[2] = coordsFromBlock(-0.25f,gauge);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,6}})){
            coords[3] = coordsFromBlock(1.455f, gauge);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,8}})){
            coords[0] = coordsFromBlock(-1.565f, gauge);
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3]);
    }


    public static List<?extends ModelRailSegment> vanillaCurve6(World worldObj, int xCoord, int yCoord, int zCoord, float[] gauge){
        Vec3f[] coords = groupBlockCoords(
                0f,0.5f,
                0f, 0.25f,
                0.25f,0f,
                0.5f,0f
        );

        if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,8}})){

            coords[3]=coordsFromBlock(0.5f,0);
            coords[2]=coordsFromBlock(0.375f,0.125f);
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,0}})){
                coords[0] = coordsFromBlock(0,1);
                coords[1] = coordsFromBlock(0, 0.5f + 0);
            }
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,8}})){

            coords[0] = coordsFromBlock(-0.025f, 0.5f);
            coords[1] = coordsFromBlock( 0.125f, 0.375f);
            if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,1}})){
                coords[3] = coordsFromBlock(1, 0);
                coords[2] = coordsFromBlock(0.5f,0);
            }
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3], gauge);
    }

    public static List<?extends ModelRailSegment> vanillaCurve8(World worldObj, int xCoord, int yCoord, int zCoord, float gauge){
        //the base shape
        Vec3f[] coords = groupBlockCoords(
                -0.5f, gauge,
                -0.25f+(gauge*0.5f), gauge,
                gauge, -0.25f+(gauge*0.5f),
                gauge, -0.5f);

        if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,6}})){
            //first half of the diagonal
            coords[0] = coordsFromBlock(-0.5f+(gauge*0.75f), (gauge*0.75f));
            coords[1] = coordsFromBlock(-0.375f+(gauge*0.75f), -0.125f+(gauge*0.75f));
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,0}})){
                //extension of one side to match up with a straight block
                coords[3] = coordsFromBlock(gauge,-1.0625f);
                coords[2] = coordsFromBlock(gauge, -0.5f + (gauge*0.75f));
            }
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,6}})) {
            //second half of the diagonal
            coords[3] = coordsFromBlock((gauge*0.75f)+0.025f, -0.5f+ (gauge*0.75f));
            coords[2] = coordsFromBlock(-0.375f+(gauge*0.75f), -0.125f+(gauge*0.75f));
            if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord, zCoord, 1}})) {
                //extension of one side to match up with a straight block
                coords[0] = coordsFromBlock(-1, gauge);
                coords[1] = coordsFromBlock(-0.6875f + (gauge*0.75f), gauge );
            }
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3]);
    }


    public static List<?extends ModelRailSegment> vanillaCurve7(World worldObj, int xCoord, int yCoord, int zCoord, float gauge){
        //the base shape
        Vec3f[] coords = groupBlockCoords(
                -0.5f, gauge,
                -0.25f-(gauge*0.5f), gauge,
                -gauge, 0.25f+(gauge*0.5f),
                -gauge, 0.5f);


        if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,9}})){
            //first half of the diagonal
            coords[0] = coordsFromBlock(-0.5625f-(gauge*0.75f), -0.0625f+(gauge*0.75f));
            coords[1] = coordsFromBlock(-0.375f-(gauge*0.75f), 0.125f+(gauge*0.75f));
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,0}})){
                //extension of one side to match up with a straight block
                coords[3] = coordsFromBlock(-gauge,1);
                coords[2] = coordsFromBlock(-gauge, 0.5f + (gauge*0.75f));
            }
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,9}})){
            //second half of the diagonal
            coords[3] = coordsFromBlock(0.2625f-(gauge*0.75f), 0.7375f+ (gauge*0.75f));
            coords[2] = coordsFromBlock(-0.375f-(gauge*0.75f), 0.125f+(gauge*0.75f));
            if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,1}})){
                //extension of one side to match up with a straight block
                coords[0] = coordsFromBlock(-1.05f, gauge);
                coords[1] = coordsFromBlock(-0.75f - (gauge*0.75f), gauge);
            }
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3]);
    }

    public static List<?extends ModelRailSegment> vanillaCurve9(World worldObj, int xCoord, int yCoord, int zCoord, float gauge){
        //the base shape
        Vec3f[] coords = groupBlockCoords(
                0.5f, gauge,
                0.25f-(gauge*0.5f), gauge,
                -gauge, -0.25f+(gauge*0.5f),
                -gauge, -0.5f);

        coords[0] = coordsFromBlock(0.5f, gauge);
        coords[1] = coordsFromBlock(0-gauge, -0+gauge);
        coords[2] = coordsFromBlock(-gauge, -0.5f);

        if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,7}})){
            //first half of the diagonal
            coords[0] = coordsFromBlock(0.5f+(gauge*0.75f), -(gauge*0.75f));
            coords[1] = coordsFromBlock(0.375f+(gauge*0.75f), -0.125f-(gauge*0.75f));
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,0}})){
                //extension of one side to match up with a straight block
                coords[3] = coordsFromBlock(gauge,-1.0625f);
                coords[2] = coordsFromBlock(gauge, -0.5f - (gauge*0.75f));
            }
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,7}})) {
            //second half of the diagonal
            coords[3] = coordsFromBlock((gauge*0.75f), -0.5f- (gauge*0.75f));
            coords[2] = coordsFromBlock(0.375f+(gauge*0.75f), -0.125f-(gauge*0.75f));
            if (checkMultiblock(worldObj, new int[][]{{xCoord + 1, yCoord, zCoord, 1}})) {
                //extension of one side to match up with a straight block
                coords[0] = coordsFromBlock(1.1f, -gauge);
                coords[1] = coordsFromBlock(0.6875f + (gauge*0.75f), -gauge );
            }
        }


        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3]);
    }





    public static List<?extends ModelRailSegment> vanillaSlopeZ5(World worldObj, int xCoord, int yCoord, int zCoord, float gauge){
        Vec3f[] coords = groupBlockCoords(
                gauge,0.1f,-0.6f,
                gauge,0.25f,-0.25f,
                gauge,0.775f,0.25f,
                gauge,0.88f, 0.5f);
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1,5}})){
            coords[0].yCoord= -0.05f;
            coords[0].zCoord= -0.05f;
            coords[1].yCoord=0.25f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord+1,zCoord+1,5}})){
            coords[3].yCoord=1;
            coords[2].yCoord=0.75f;
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3]);
    }

    public static List<?extends ModelRailSegment> vanillaSlopeZ4(World worldObj, int xCoord, int yCoord, int zCoord, float gauge){
        Vec3f[] coords = groupBlockCoords(
                gauge,0.13f,0.5f,
                gauge,0.2f,0.25f,
                gauge,0.75f,-0.25f,
                gauge,0.875f, -0.5f);
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1,4}})){
            coords[0].yCoord=0;
            coords[1].yCoord=0.25f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord+1,zCoord-1,4}})){
            coords[3].yCoord=1.05f;
            coords[2].yCoord=0.75f;
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3]);
    }

    public static List<?extends ModelRailSegment> vanillaSlopeX2(World worldObj, int xCoord, int yCoord, int zCoord, float gauge){
        Vec3f[] coords = groupBlockCoords(
                -0.525f,0.13f,gauge,
                -0.25f, 0.25f, gauge,
                0.25f, 0.8f, gauge,
                0.525f,0.88f,gauge);

        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            coords[0].yCoord=0;
            coords[1].yCoord=0.25f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord+1,zCoord,2}})){
            coords[3].yCoord = 1.05f;
            coords[2].yCoord=0.75f;
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3]);
    }

    public static List<?extends ModelRailSegment> vanillaSlopeX3(World worldObj, int xCoord, int yCoord, int zCoord, float gauge){
        Vec3f[] coords = groupBlockCoords(
                0.525f,0.11f,gauge,
                0.25f, 0.225f, gauge,
                -0.25f, 0.75f, gauge,
                -0.525f,0.865f,gauge);
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            coords[0].yCoord=0;
            coords[1].yCoord=0.25f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord+1,zCoord,3}})){
            coords[3].yCoord=1.05f;
            coords[2].yCoord=0.75f;
        }

        return BlockRailCore.quadGenModel(coords[0],coords[1], coords[2], coords[3]);
    }


    public static Vec3f[] groupBlockCoords(float x1, float z1, float x2, float z2, float x3, float z3, float x4, float z4){
        return new Vec3f[]{
                coordsFromBlock(x1,z1),
                coordsFromBlock(x2,z2),
                coordsFromBlock(x3,z3),
                coordsFromBlock(x4,z4)};
    }

    public static Vec3f coordsFromBlock(float x,  float z){
        return new Vec3f(x+0.5f, 0, z+0.5f);
    }

    public static Vec3f[] groupBlockCoords(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4){
        return new Vec3f[]{
                coordsFromBlock(x1,y1,z1),
                coordsFromBlock(x2,y2,z2),
                coordsFromBlock(x3,y3,z3),
                coordsFromBlock(x4,y4,z4)};
    }

    public static Vec3f coordsFromBlock(float x, float y,  float z){
        return new Vec3f(x+0.5f, y, z+0.5f);
    }



    public static Vec3f[] makePath(float gauge, float length1, float angle1, float length2, float angle2, float length3, float angle3, float length4, float angle4){
        return new Vec3f[]{
                rotateYaw(length1, gauge, angle1, true),
                rotateYaw(length2, gauge, angle2, false),
                rotateYaw(length3, gauge, angle3, false),
                rotateYaw(length4, gauge, angle4, true)
        };
    }
    public static Vec3f rotateYaw(float xCoord, float zCoord, float yaw, boolean changeGauge) {
        Vec3f xyz = new Vec3f(xCoord, 0, zCoord);
        //rotate yaw
        if (yaw != 0.0F) {
            yaw *= radianF;
            float cos = MathHelper.cos(yaw);
            float sin = MathHelper.sin(yaw);

            xyz.xCoord = (xCoord * cos) - (zCoord * sin);
            xyz.zCoord = (xCoord * sin) + (zCoord * cos);
            if(!changeGauge) {
                //xyz.add(rotateYaw(zCoord*-0.5, 0, yaw, true));
            }
        }
        return xyz;
    }


    //great in theory, doesn't work for shit in practice.
    public static Vec3d[] setGauge(Vec3d[] path, float gauge){
        float direction = (float)Math.toDegrees(Math.atan2(
                path[1].xCoord - path[0].xCoord,
                path[1].zCoord - path[0].zCoord));

        if(direction>180){
            direction-=180;
        } else if (direction <0) {
            direction +=180;
        }

        float direction2 = (float)Math.toDegrees(Math.atan2(
                path[3].xCoord - path[2].xCoord,
                path[3].zCoord - path[2].zCoord));

        if(direction2>180){
            direction2-=180;
        } else if (direction2<0) {
            direction2 +=180;
        }

        path[0].add(RailUtility.rotateDistance(gauge, 0, direction));

        path[1].add(RailUtility.rotateDistance(gauge, 0, (direction+direction2) * 0.5f));

        path[2].add(RailUtility.rotateDistance(gauge, 0, (direction+direction2) * 0.5f));

        path[3].add(RailUtility.rotateDistance(gauge, 0, direction2));



        return path;

    }

}
