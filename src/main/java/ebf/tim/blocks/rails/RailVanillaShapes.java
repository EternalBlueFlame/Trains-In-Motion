package ebf.tim.blocks.rails;

import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.RailUtility;
import net.minecraft.util.MathHelper;
import tmt.Vec3d;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

import static ebf.tim.blocks.rails.BlockRailCore.checkMultiblock;
import static ebf.tim.utility.RailUtility.radianF;

/**
 * @author Eternal Blue Flame
 */
public class RailVanillaShapes {


    public static int processRailMeta(int meta, EntityMinecart cart, int x, int y, int z){
        if (cart == null || cart.getEntityData() == null){
            return meta;
        }
        //first be sure the key exists, and create it if it doesn't, that way we be sure we don't crash. Also if it doesn't exist we can just return the base meta unchanged.
        if (!cart.getEntityData().hasKey("tim.lastusedrail.meta")){
            cart.getEntityData().setInteger("tim.lastusedrail.meta",meta);
            return meta;
        }
        boolean changed = false;
        switch (meta){
            //add support for intersections
            case 0:{
                //only part that theoretically works.
                if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 1){
                    meta=1;
                } else {
                    changed=true;
                }
                break;
            }
            case 1:{
                if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 0){
                    meta=0;
                } else {
                    changed=true;
                }
                break;
            }
            default:{
                changed=true;
            }
            /*
            //in some specific circumstances we have to cover how rails are approached to smooth movement So if you enter a turn from the wrong side, it treats it as a straight rather than a turn.
            case 6:{
                if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 1){
                    meta=1;changed=true;
                } else if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 0){
                    meta=0;changed=true;
                }
                break;
            }
            case 7:{
                if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 1){
                    meta=1;changed=true;
                } else if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 0){
                    meta=0;changed=true;
                }
                break;
            }
            case 8:{
                if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 1){
                    meta=1;changed=true;
                } else if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 0 && cart.getEntityData().getInteger("tim.lastusedrail.z") > z){
                    meta=0;changed=true;
                }
                break;
            }
            case 9:{
                if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 1 && cart.getEntityData().getInteger("tim.lastusedrail.x") < x){
                    meta=1;changed=true;
                } else if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 0 && cart.getEntityData().getInteger("tim.lastusedrail.z") > z){
                    meta=0;changed=true;
                }
                break;
            }*/
        }
        if (changed){
            cart.getEntityData().setInteger("tim.lastusedrail.meta",meta);
            cart.getEntityData().setInteger("tim.lastusedrail.x",x);
            cart.getEntityData().setInteger("tim.lastusedrail.z",z);
        }
        return meta;
    }

    public static Vec3d[] vanillaZStraight(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords =
                groupBlockCoords(gauge,0.5,
                        gauge, 0.25,
                        gauge, -0.25,
                        gauge, -0.565);

        //X axis slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,4}})){
            coords[3].yCoord = 0.15;
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1, 4}})){
            coords[0].yCoord = -0.15;
        }
        //Z axis slope
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1, 5}})){
            coords[0].yCoord = 0.15;
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1, 5}})){
            coords[3].yCoord = -0.15;
        }

        //intersections
        if(checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,1},{xCoord, yCoord,zCoord+2,0}})){
            coords[0] = coordsFromBlock(gauge, 1.06);
        }
        if(checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,1},{xCoord, yCoord,zCoord-2,0}})){
            coords[3] = coordsFromBlock(gauge, -1.06);
        }

        //cover curve blending
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,7},{xCoord-1, yCoord,zCoord-1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,6},{xCoord+1, yCoord,zCoord-1,8}})){
            coords[3] = coordsFromBlock(gauge, 0);
            coords[2] = coordsFromBlock(gauge, 0.25);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,9}, {xCoord+1, yCoord,zCoord+1,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,8},{xCoord-1, yCoord,zCoord+1,6}})){
            coords[0] = coordsFromBlock(gauge, 0);
            coords[1] = coordsFromBlock(gauge, -0.25);
        }else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,7 }}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,6}})){
            coords[0] = coordsFromBlock(gauge, 1.5);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,8}})) {
            coords[3] = coordsFromBlock(gauge, -1.5);
        }

        return coords;
    }

    public static Vec3d[] vanillaXStraight(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords =
                groupBlockCoords(
                  -0.565,gauge,
                  -0.25,gauge,
                  0.25,gauge,
                  0.5,gauge
                );

        //X axis slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,3}})){
            coords[0].yCoord = 0.15;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            coords[3].yCoord = -0.15;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3},{xCoord-1, yCoord,zCoord,3}})){
            coords[1].yCoord = 0.05;
            coords[2].yCoord = -0.05;
        }
        //Z axis slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,2}})){
            coords[3].yCoord = 0.15;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            coords[0].yCoord = -0.15;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}, {xCoord+1, yCoord,zCoord,2}})){
            coords[1].yCoord = -0.05;
            coords[2].yCoord = 0.05;
        }
        //intersections
        if(checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,0},{xCoord-2, yCoord,zCoord,1}})){
            coords[0] = coordsFromBlock(-1.06, gauge);
        }
        if(checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,0},{xCoord+2, yCoord,zCoord,1}})){
            coords[3] = coordsFromBlock(1.06, gauge);
        }


        //cover curve blending
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,9},{xCoord-1, yCoord,zCoord-1,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,6},{xCoord-1, yCoord,zCoord+1,8}})){
            coords[0] = coordsFromBlock(-0.1,gauge);
            coords[1] = coordsFromBlock(0.25,gauge);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,7}, {xCoord+1, yCoord,zCoord+1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,8},{xCoord+1, yCoord,zCoord-1,6}})){
            coords[3] = coordsFromBlock(0.05,gauge);
            coords[2] = coordsFromBlock(-0.25,gauge);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,6}})){
            coords[3] = coordsFromBlock(1.455, gauge);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,8}})){
            coords[0] = coordsFromBlock(-1.565, gauge);
        }

        return coords;
    }


    public static Vec3d[] vanillaCurve6(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords = groupBlockCoords(
                gauge,0.5,
                gauge, 0.25+(gauge*0.5),
                0.25+(gauge*0.5),gauge,
                0.5,gauge
        );

        if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,8}})){

            coords[3]=coordsFromBlock(0.5+(gauge*0.75),(gauge*0.75));
            coords[2]=coordsFromBlock(0.375+(gauge*0.75),0.125+(gauge*0.75));
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,0}})){
                coords[0] = coordsFromBlock(gauge,1);
                coords[1] = coordsFromBlock(gauge, 0.5 + (gauge*0.75));
            }
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,8}})){

            coords[0] = coordsFromBlock((gauge*0.75)-0.025, 0.5+(gauge*0.75));
            coords[1] = coordsFromBlock( 0.125+(gauge*0.75), 0.375+(gauge*0.75));
            if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,1}})){
                coords[3] = coordsFromBlock(1, gauge);
                coords[2] = coordsFromBlock(0.5+(gauge*0.5),gauge);
            }
        }

        return coords;
    }

    public static Vec3d[] vanillaCurve8(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        //the base shape
        Vec3d[] coords = groupBlockCoords(
                -0.55, gauge,
                -0.25+(gauge*0.5), gauge,
                gauge, -0.25+(gauge*0.5),
                gauge, -0.55);

        if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,6}})){
            //first half of the diagonal
            coords[0] = coordsFromBlock(-0.5+(gauge*0.75), (gauge*0.75));
            coords[1] = coordsFromBlock(-0.375+(gauge*0.75), -0.125+(gauge*0.75));
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,0}})){
                //extension of one side to match up with a straight block
                coords[3] = coordsFromBlock(gauge,-1.0625);
                coords[2] = coordsFromBlock(gauge, -0.5 + (gauge*0.75));
            }
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,6}})) {
            //second half of the diagonal
            coords[3] = coordsFromBlock((gauge*0.75)+0.025, -0.55+ (gauge*0.75));
            coords[2] = coordsFromBlock(-0.375+(gauge*0.75), -0.125+(gauge*0.75));
            if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord, zCoord, 1}})) {
                //extension of one side to match up with a straight block
                coords[0] = coordsFromBlock(-1, gauge);
                coords[1] = coordsFromBlock(-0.6875 + (gauge*0.75), gauge );
            }
        }

        return coords;
    }


    public static Vec3d[] vanillaCurve7(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        //the base shape
        Vec3d[] coords = groupBlockCoords(
                -0.565, gauge,
                -0.25-(gauge*0.5), gauge,
                -gauge, 0.25+(gauge*0.5),
                -gauge, 0.5);


        if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,9}})){
            //first half of the diagonal
            coords[0] = coordsFromBlock(-0.5625-(gauge*0.75), -0.0625+(gauge*0.75));
            coords[1] = coordsFromBlock(-0.375-(gauge*0.75), 0.125+(gauge*0.75));
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,0}})){
                //extension of one side to match up with a straight block
                coords[3] = coordsFromBlock(-gauge,1);
                coords[2] = coordsFromBlock(-gauge, 0.5 + (gauge*0.75));
            }
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,9}})){
            //second half of the diagonal
            coords[3] = coordsFromBlock(0.2625-(gauge*0.75), 0.7375+ (gauge*0.75));
            coords[2] = coordsFromBlock(-0.375-(gauge*0.75), 0.125+(gauge*0.75));
            if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,1}})){
                //extension of one side to match up with a straight block
                coords[0] = coordsFromBlock(-1.05, gauge);
                coords[1] = coordsFromBlock(-0.75 - (gauge*0.75), gauge);
            }
        }

        return coords;
    }

    public static Vec3d[] vanillaCurve9(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        //the base shape
        Vec3d[] coords = groupBlockCoords(
                0.5, gauge,
                0.25-(gauge*0.5), gauge,
                -gauge, -0.25+(gauge*0.5),
                -gauge, -0.565);

        coords[0] = coordsFromBlock(0.5, gauge);
        coords[1] = coordsFromBlock(0-gauge, -0+gauge);
        coords[2] = coordsFromBlock(-gauge, -0.565);

        if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,7}})){
            //first half of the diagonal
            coords[0] = coordsFromBlock(0.5+(gauge*0.75), -(gauge*0.75));
            coords[1] = coordsFromBlock(0.375+(gauge*0.75), -0.125-(gauge*0.75));
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,0}})){
                //extension of one side to match up with a straight block
                coords[3] = coordsFromBlock(gauge,-1.0625);
                coords[2] = coordsFromBlock(gauge, -0.5 - (gauge*0.75));
            }
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,7}})) {
            //second half of the diagonal
            coords[3] = coordsFromBlock((gauge*0.75), -0.5- (gauge*0.75));
            coords[2] = coordsFromBlock(0.375+(gauge*0.75), -0.125-(gauge*0.75));
            if (checkMultiblock(worldObj, new int[][]{{xCoord + 1, yCoord, zCoord, 1}})) {
                //extension of one side to match up with a straight block
                coords[0] = coordsFromBlock(1.1, -gauge);
                coords[1] = coordsFromBlock(0.6875 + (gauge*0.75), -gauge );
            }
        }


        return coords;
    }





    public static Vec3d[] vanillaSlopeZ5(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords = groupBlockCoords(
                gauge,0.1,-0.6,
                gauge,0.25,-0.25,
                gauge,0.775,0.25,
                gauge,0.88, 0.5);
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1,5}})){
            coords[0].yCoord= -0.05;
            coords[0].zCoord= -0.05;
            coords[1].yCoord=0.25;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord+1,zCoord+1,5}})){
            coords[3].yCoord=1;
            coords[2].yCoord=0.75;
        }

        return coords;
    }

    public static Vec3d[] vanillaSlopeZ4(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords = groupBlockCoords(
                gauge,0.13,0.5,
                gauge,0.2,0.25,
                gauge,0.75,-0.25,
                gauge,0.875, -0.55);
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1,4}})){
            coords[0].yCoord=0;
            coords[1].yCoord=0.25;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord+1,zCoord-1,4}})){
            coords[3].yCoord=1.05;
            coords[2].yCoord=0.75;
        }

        return coords;
    }

    public static Vec3d[] vanillaSlopeX2(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords = groupBlockCoords(
                -0.525,0.13,gauge,
                -0.25, 0.25, gauge,
                0.25, 0.8, gauge,
                0.525,0.88,gauge);

        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            coords[0].yCoord=0;
            coords[1].yCoord=0.25;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord+1,zCoord,2}})){
            coords[3].yCoord = 1.05;
            coords[2].yCoord=0.75;
        }

        return coords;
    }

    public static Vec3d[] vanillaSlopeX3(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords = groupBlockCoords(
                0.525,0.11,gauge,
                0.25, 0.225, gauge,
                -0.25, 0.75, gauge,
                -0.525,0.865,gauge);
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            coords[0].yCoord=0;
            coords[1].yCoord=0.25;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord+1,zCoord,3}})){
            coords[3].yCoord=1.05;
            coords[2].yCoord=0.75;
        }

        return coords;
    }


    public static Vec3d[] groupBlockCoords(double x1, double z1, double x2, double z2, double x3, double z3, double x4, double z4){
        return new Vec3d[]{
                coordsFromBlock(x1,z1),
                coordsFromBlock(x2,z2),
                coordsFromBlock(x3,z3),
                coordsFromBlock(x4,z4)};
    }

    public static Vec3d coordsFromBlock(double x,  double z){
        return new Vec3d(x+0.5, 0, z+0.5);
    }

    public static Vec3d[] groupBlockCoords(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4){
        return new Vec3d[]{
                coordsFromBlock(x1,y1,z1),
                coordsFromBlock(x2,y2,z2),
                coordsFromBlock(x3,y3,z3),
                coordsFromBlock(x4,y4,z4)};
    }

    public static Vec3d coordsFromBlock(double x, double y,  double z){
        return new Vec3d(x+0.5, y, z+0.5);
    }



    public static Vec3d[] makePath(double gauge, double length1, float angle1, double length2, float angle2, double length3, float angle3, double length4, float angle4){
        return new Vec3d[]{
                rotateYaw(length1, gauge, angle1, true),
                rotateYaw(length2, gauge, angle2, false),
                rotateYaw(length3, gauge, angle3, false),
                rotateYaw(length4, gauge, angle4, true)
        };
    }
    public static Vec3d rotateYaw(double xCoord, double zCoord, float yaw, boolean changeGauge) {
        Vec3d xyz = new Vec3d(xCoord, 0, zCoord);
        //rotate yaw
        if (yaw != 0.0F) {
            yaw *= radianF;
            double cos = MathHelper.cos(yaw);
            double sin = MathHelper.sin(yaw);

            xyz.xCoord = (xCoord * cos) - (zCoord * sin);
            xyz.zCoord = (xCoord * sin) + (zCoord * cos);
            if(!changeGauge) {
                //xyz.add(rotateYaw(zCoord*-0.5, 0, yaw, true));
            }
        }
        return xyz;
    }


    //great in theory, doesn't work for shit in practice.
    public static Vec3d[] setGauge(Vec3d[] path, double gauge){
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
