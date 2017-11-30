package ebf.tim.blocks.rails;

import tmt.Vec3d;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

import static ebf.tim.blocks.rails.BlockRailCore.checkMultiblock;

/**
 * @author Eternal Blue Flame
 */
public class RailVanillaShapes {


    public static int processRailMeta(int meta, EntityMinecart cart, int x, int y, int z){
        if (cart == null || cart.getEntityData() == null){
            return meta;
        }
        //first be sure the key exists, and create it if it doesn't, that way we be sure we don't crash. Also if it doesn't exist we can just return the base meta unchanged.
        if (!cart.getEntityData().hasKey("tim.lastusedrail.meta") || !cart.getEntityData().hasKey("tim.lastusedrail.x") || !cart.getEntityData().hasKey("tim.lastusedrail.z")){
            cart.getEntityData().setInteger("tim.lastusedrail.meta",meta);
            cart.getEntityData().setInteger("tim.lastusedrail.x",x);
            cart.getEntityData().setInteger("tim.lastusedrail.z",z);
            return meta;
        }
        boolean changed = false;
        switch (meta){
            //add support for intersections
            case 0:{
                //only part that theoretically works.
                if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 1 && cart.getEntityData().getInteger("tim.lastusedrail.x") != x &&
                        cart.getEntityData().getInteger("tim.lastusedrail.z") == z){
                    meta=1;changed=true;
                }
                break;
            }
            case 1:{
                if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 0 && cart.getEntityData().getInteger("tim.lastusedrail.x") != x &&
                        cart.getEntityData().getInteger("tim.lastusedrail.z") == z){
                    meta=0;changed=true;
                    cart.getEntityData().setInteger("tim.lastusedrail.meta",meta);
                    cart.getEntityData().setInteger("tim.lastusedrail.x",x);
                    cart.getEntityData().setInteger("tim.lastusedrail.z",z);
                }
                break;
            }
            //in some specific circumstances we have to cover how rails are approached to smooth movement So if you enter a turn from the wrong side, it treats it as a straight rather than a turn.
            case 6:{
                if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 1 && cart.getEntityData().getInteger("tim.lastusedrail.x") < x){
                    meta=1;changed=true;
                } else if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 0 && cart.getEntityData().getInteger("tim.lastusedrail.z") < z){
                    meta=0;changed=true;
                }
                break;
            }
            case 7:{
                if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 1 && cart.getEntityData().getInteger("tim.lastusedrail.x") > x){
                    meta=1;changed=true;
                } else if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 0 && cart.getEntityData().getInteger("tim.lastusedrail.z") < z){
                    meta=0;changed=true;
                }
                break;
            }
            case 8:{
                if (cart.getEntityData().getInteger("tim.lastusedrail.meta") == 1 && cart.getEntityData().getInteger("tim.lastusedrail.x") > x){
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
            }
        }
        if (changed){
            cart.getEntityData().setInteger("tim.lastusedrail.meta",meta);
            cart.getEntityData().setInteger("tim.lastusedrail.x",x);
            cart.getEntityData().setInteger("tim.lastusedrail.z",z);
        }
        return meta;
    }

    public static Vec3d[] vanillaZStraight(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords = new Vec3d[]{new Vec3d(0.5+gauge,0,1.1),new Vec3d(0.5+gauge,0,0.75),new Vec3d(0.5+gauge,0,0.25), new Vec3d(0.5+gauge,0,0)};
        //X axis slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,4}})){
            coords[3].yCoord = 0.1;
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1, 4}})){
            coords[0].yCoord = -0.1;
        }
        //Z axis slope
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1, 5}})){
            coords[0].yCoord = 0.1;
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1, 5}})){
            coords[3].yCoord = -0.1;
        }
        //cover curve blending
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,7},{xCoord-1, yCoord,zCoord-1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,6},{xCoord+1, yCoord,zCoord-1,8}})){
            coords[3].zCoord =coords[2].zCoord =0.5;
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,9}, {xCoord+1, yCoord,zCoord+1,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,8},{xCoord-1, yCoord,zCoord+1,6}})){
            coords[0].zCoord =coords[1].zCoord =0.5;
        }

        return coords;
    }

    public static Vec3d[] vanillaXStraight(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords = new Vec3d[]{new Vec3d(1.1,0,0.5+gauge),new Vec3d(0.75,0,0.5+gauge),new Vec3d(0.25,0,0.5+gauge), new Vec3d(0,0,0.5+gauge)};
        //X axis slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,3}})){
            coords[3].yCoord = 0.1;
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            coords[0].yCoord = -0.1;
        }
        //Z axis slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,2}})){
            coords[0].yCoord = 0.1;
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            coords[3].yCoord = -0.1;
        }


        //cover curve blending
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,9},{xCoord-1, yCoord,zCoord-1,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,6},{xCoord-1, yCoord,zCoord+1,8}})){
            coords[3].xCoord =coords[2].xCoord =0.5;
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,7}, {xCoord+1, yCoord,zCoord+1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,8},{xCoord+1, yCoord,zCoord-1,6}})){
            coords[0].xCoord =coords[1].xCoord =0.5;
        }

        return coords;
    }

    //FINALLY FIXED
    public static Vec3d[] vanillaCurve6(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords =new Vec3d[]{new Vec3d(0.5+gauge,0,1),new Vec3d(0.5+gauge,0,0.7+(gauge*0.5)),new Vec3d(0.7+(gauge*0.5),0,0.5+gauge), new Vec3d(1,0,0.5+gauge)};

        if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,8}})){
            coords[0].zCoord+=gauge*0.5;
            coords[3].xCoord+=gauge*0.5;
            coords[2].xCoord+=0.3;
            coords[1].zCoord+=0.3;
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,0}})){
                coords[0].zCoord+=0.5;
            }
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,8}})){
            coords[0].zCoord+=gauge*0.5;
            coords[3].xCoord+=gauge*0.5;
            coords[2].xCoord+=0.3;
            coords[1].zCoord+=0.3;
            if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,1}})){
                coords[3].xCoord+=0.5-(gauge*0.5);
            }
        }

        return coords;
    }

    public static Vec3d[] vanillaCurve8(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords = new Vec3d[]{new Vec3d(0.5+gauge,0,0),new Vec3d(0.5+gauge,0,0.3+(gauge*0.5)),new Vec3d(0.3+(gauge*0.5),0,0.5+gauge), new Vec3d(0.5,0,1+gauge)};

        if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,6}})){
            //coords[2].xCoord-=0.3;
            //coords[1].zCoord-=0.5;
            //coords[0].xCoord+=0.1;
            //coords[0].zCoord-=0.1;
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,0}})){
                coords[0].zCoord-=0.575;
                coords[0].xCoord-=0.095;
            }
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,6}})) {
            //coords[2].xCoord-=0.3;
            //coords[1].zCoord-=0.5;

//            coords = new Vec3d[]{new Vec3d(1.1,0,0.5+gauge),new Vec3d(0.75,0,0.5+gauge),new Vec3d(0.25,0,0.5+gauge), new Vec3d(0,0,0.5+gauge)};
            if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord, zCoord, 1}})) {

                //coords[3].xCoord -=0.5;
                //coords[0].xCoord+=0.05;
                //coords[0].zCoord-=7.05;
            }
        }

        return coords;
    }



    public static Vec3d[] vanillaCurve7(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords = new Vec3d[]{new Vec3d(0,0,0.5),new Vec3d(0.3,0,0.5),new Vec3d(0.5,0,0.7),new Vec3d(0.5,0,1)};

        if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,9}})){
            coords[1].xCoord=0;coords[2].zCoord =1;
            coords[0].xCoord-=0.1;
            coords[0].zCoord-=0.1;
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,0}})){
                coords[3].zCoord+=0.5;
                coords[3].xCoord-=0.005;
            }
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,9}})){
            coords[1].xCoord=0;coords[2].zCoord =1;
            if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,1}})){
                coords[0].xCoord+=0.575;
                coords[0].zCoord-=0.005;
            }
        }

        return coords;
    }

    public static Vec3d[] vanillaCurve9(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords = new Vec3d[]{new Vec3d(1,0,0.5),new Vec3d(0.7,0,0.5),new Vec3d(0.5,0,0.3), new Vec3d(0.5,0,0)};

        if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,7}})){
            coords[1].xCoord=1;
            coords[2].zCoord =0;
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,0}})){
                coords[3].zCoord=-0.505;
            }
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,7}})) {
            coords[1].xCoord=1;
            coords[2].zCoord =0;
            if (checkMultiblock(worldObj, new int[][]{{xCoord + 1, yCoord, zCoord, 1}})) {
                coords[0].xCoord = 1.51;
                coords[0].zCoord += 0.005;
            }
        }

        return coords;
    }






    public static Vec3d[] vanillaSlopeZ5(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords = new Vec3d[]{new Vec3d(0.5,0.9,1),new Vec3d(0.5,0.9,0.75),new Vec3d(0.5,0.1,0.25), new Vec3d(0.5,0.1,0)};
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1,5}})){
            coords[3].yCoord=0;
            coords[2].yCoord=0.25;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord+1,zCoord+1,5}})){
            coords[0].yCoord=1;
            coords[1].yCoord=0.75;
        }

        return coords;
    }

    public static Vec3d[] vanillaSlopeZ4(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords = new Vec3d[]{new Vec3d(0.5,0.1,1),new Vec3d(0.5,0.19,0.75),new Vec3d(0.5,0.9,0.25), new Vec3d(0.5,0.9,0)};
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1,4}})){
            coords[0].yCoord=0;
            coords[1].yCoord=0.25;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord+1,zCoord-1,4}})){
            coords[3].yCoord=1;
            coords[2].yCoord=0.75;
        }

        return coords;
    }


    public static Vec3d[] vanillaSlopeX2(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords = new Vec3d[]{new Vec3d(1,0.9,0.5),new Vec3d(0.75,0.9,0.5),new Vec3d(0.25,0.1,0.5), new Vec3d(0,0.1,0.5)};
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            coords[3].yCoord=0;
            coords[2].yCoord=0.25;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord+1,zCoord,2}})){
            coords[0].yCoord=1;
            coords[1].yCoord=0.75;
        }

        return coords;
    }

    public static Vec3d[] vanillaSlopeX3(World worldObj, int xCoord, int yCoord, int zCoord, double gauge){
        Vec3d[] coords = new Vec3d[]{new Vec3d(1,0.1,0.5),new Vec3d(0.75,0.1,0.5),new Vec3d(0.25,0.9,0.5), new Vec3d(0,0.9,0.5)};
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            coords[0].yCoord=0;
            coords[1].yCoord=0.25;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord+1,zCoord,3}})){
            coords[3].yCoord=1;
            coords[2].yCoord=0.75;
        }

        return coords;
    }


}
