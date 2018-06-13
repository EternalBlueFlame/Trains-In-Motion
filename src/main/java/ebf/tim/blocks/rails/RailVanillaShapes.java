package ebf.tim.blocks.rails;

import net.minecraft.world.World;
import fexcraft.tmt.slim.Vec3f;

import java.util.ArrayList;
import java.util.List;

import static ebf.tim.blocks.rails.BlockRailCore.checkMultiblock;

/**
 * @author Eternal Blue Flame
 */
public class RailVanillaShapes extends RailShapeCore{


    public static List<Vec3f[]> vanillaZStraight(World worldObj, int xCoord, int yCoord, int zCoord){
        Vec3f[] coords = rotateYaw(0,
                0,0,0.5f,
                0,0, 0,
                0,0, -0.5f
        );


        //intersections
        if(checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,1},{xCoord, yCoord,zCoord+2,0}})){
            coords[0]=rotateYaw(0,0,0,1);
        }
        if(checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,1},{xCoord, yCoord,zCoord-2,0}})){
            coords[2]=rotateYaw(0,0,0,-1);
        }
        //cover curve blending
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,7},{xCoord-1, yCoord,zCoord-1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,6},{xCoord+1, yCoord,zCoord-1,8}})){
            coords[2]=rotateYaw(0,0,0,0f);
            coords[1]=rotateYaw(0,0,0,0.25f);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,9}, {xCoord+1, yCoord,zCoord+1,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,8},{xCoord-1, yCoord,zCoord+1,6}})){
            coords[0]=rotateYaw(0,0,0,0f);
            coords[1]=rotateYaw(0,0,0,-0.25f);
        }else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,7 }}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,6}})){
            coords[0]=rotateYaw(0,0,0,1.5f);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,8}})) {
            coords[2]=rotateYaw(0,0,0,-1.5f);
        }


        //slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,4}})){
            coords[2].yCoord = 0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1, 4}})){
            coords[0].yCoord = -0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1, 5}})){
            coords[0].yCoord = 0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1, 5}})){
            coords[2].yCoord = -0.15f;
        }

        List<Vec3f[]> coordSets = new ArrayList<Vec3f[]>();
        coordSets.add(coords);
        return coordSets;
    }

    public static List<Vec3f[]> vanillaXStraight(World worldObj, int xCoord, int yCoord, int zCoord){
        Vec3f[] coords = rotateYaw(0,
                -0.5f,0,0,
                0f,0,0,
                0.5f,0,0
        );

        //intersections
        if(checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,0},{xCoord-2, yCoord,zCoord,1}})){
            coords[0]=rotateYaw(0,-1,0,0);
        }
        if(checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,0},{xCoord+2, yCoord,zCoord,1}})){
            coords[2]=rotateYaw(0,1,0,0);
        }


        //cover curve blending
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,9},{xCoord-1, yCoord,zCoord-1,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,6},{xCoord-1, yCoord,zCoord+1,8}})){

            coords[0]=rotateYaw(0,0,0,0);
            coords[1]=rotateYaw(0,0.25f,0,0);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,7}, {xCoord+1, yCoord,zCoord+1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,8},{xCoord+1, yCoord,zCoord-1,6}})){

            coords[2]=rotateYaw(0,0,0,0);
            coords[1]=rotateYaw(0,-0.25f,0,0);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,6}})){
            coords[2]=rotateYaw(0,1.5f,0,0);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,8}})){
            coords[0]=rotateYaw(0,1.5f,0,0);
        }

        //slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,3}})){
            coords[0].yCoord = 0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            coords[2].yCoord = -0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,2}})){
            coords[2].yCoord = 0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            coords[0].yCoord = -0.15f;
        }


        List<Vec3f[]> coordSets = new ArrayList<Vec3f[]>();
        coordSets.add(coords);
        return coordSets;
    }


    public static List<Vec3f[]> vanillaCurve6(World worldObj, int xCoord, int yCoord, int zCoord){
        Vec3f[] coords = rotateYaw(0,
                0f,0,0.5f,
                0f,0, 0f,
                0.5f,0,0f);

        if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,8}}) || checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,8}})) {
            coords[1] = rotateYaw(0, 0.125f, 0, 0.375f);
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,0}})){
                coords[0]=rotateYaw(0,0,0,1);
                coords[1]=rotateYaw(0,0f,0,0.5f);
            }
            if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,1}})){
                coords[2]=rotateYaw(0,1,0,0);
                coords[1]=rotateYaw(0,0.5f,0,0);
            }
        }

        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            coords[2].yCoord = -0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,2}})){
            coords[2].yCoord = 0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1, 4}})){
            coords[0].yCoord = -0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1, 5}})){
            coords[0].yCoord = 0.15f;
        }

        List<Vec3f[]> coordSets = new ArrayList<Vec3f[]>();
        coordSets.add(coords);
        return coordSets;
    }

    public static List<Vec3f[]> vanillaCurve8(World worldObj, int xCoord, int yCoord, int zCoord){
        //the base shape
        Vec3f[] coords = rotateYaw(0,
                -0.5f,0, 0,
                0, 0,0,
                0,0, -0.5f);

        if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,6}}) || checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,6}})) {
            //first half of the diagonal
            coords[1] = rotateYaw(0, -0.375f, 0, -0.125f);
            if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord, zCoord - 1, 0}})) {
                //extension of one side to match up with a straight block
                coords[2] = rotateYaw(0, 0, 0, -1f);
                coords[1] = rotateYaw(0, 0, 0, -0.5f);
            }
            if (checkMultiblock(worldObj, new int[][]{{xCoord - 1, yCoord, zCoord, 1}})) {
                //extension of one side to match up with a straight block
                coords[0] = rotateYaw(0, -1, 0, 0);
                coords[1] = rotateYaw(0, -0.5f, 0, 0);
            }
        }

        //slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,3}})){
            coords[0].yCoord = 0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            coords[0].yCoord = -0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,4}})){
            coords[2].yCoord = 0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1, 5}})){
            coords[2].yCoord = -0.15f;
        }

        List<Vec3f[]> coordSets = new ArrayList<Vec3f[]>();
        coordSets.add(coords);
        return coordSets;
    }


    public static List<Vec3f[]> vanillaCurve7(World worldObj, int xCoord, int yCoord, int zCoord){
        //the base shape
        Vec3f[] coords = rotateYaw(0,
                -0.5f, 0,0,
                0f, 0,0,
                0,0, 0.5f);

        if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,9}}) || checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,9}})){
            coords[1] = rotateYaw(0, -0.375f,0, 0.125f);
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,0}})){
                //extension of one side to match up with a straight block
                coords[2] = rotateYaw(0,0, 0,1);
                coords[1] = rotateYaw(0, 0,0, 0.5f);
            }
            if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,1}})){
                //extension of one side to match up with a straight block
                coords[0] = rotateYaw(0, -1f, 0,0);
                coords[1] = rotateYaw(0, -0.5f , 0,0);
            }
        }

        //slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,3}})){
            coords[0].yCoord = 0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            coords[0].yCoord = -0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1, 4}})){
            coords[2].yCoord = -0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1, 5}})){
            coords[2].yCoord = 0.15f;
        }

        List<Vec3f[]> coordSets = new ArrayList<Vec3f[]>();
        coordSets.add(coords);
        return coordSets;
    }

    public static List<Vec3f[]> vanillaCurve9(World worldObj, int xCoord, int yCoord, int zCoord){
        //the base shape
        Vec3f[] coords = rotateYaw(0,
                0.5f, 0,0,
                0, 0,0,
                0,0, -0.5f);

        if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,7}}) || checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,7}})){
            //first half of the diagonal
            coords[1] = rotateYaw(0, 0.375f, 0,-0.125f);
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,0}})){
                //extension of one side to match up with a straight block
                coords[2] = rotateYaw(0, 0,0,-1f);
                coords[1] = rotateYaw(0, 0, 0,-0.5f);
            }
            if (checkMultiblock(worldObj, new int[][]{{xCoord + 1, yCoord, zCoord, 1}})) {
                //extension of one side to match up with a straight block
                coords[0] = rotateYaw(0, 1f, 0,0);
                coords[1] = rotateYaw(0, 0.5f,0, 0 );
            }
        }
//slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            coords[0].yCoord = -0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,2}})){
            coords[0].yCoord = 0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,4}})){
            coords[2].yCoord = 0.15f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1, 5}})){
            coords[2].yCoord = -0.15f;
        }

        List<Vec3f[]> coordSets = new ArrayList<Vec3f[]>();
        coordSets.add(coords);
        return coordSets;
    }





    public static List<Vec3f[]> vanillaSlopeZ5(World worldObj, int xCoord, int yCoord, int zCoord){
        Vec3f[] coords = rotateYaw(0,
                0,0.15f,-0.5f,
                0,0.2f,-0.25f,
                0,0.8f,0.25f,
                0,0.85f, 0.5f);
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1,5}})){
            coords[0].yCoord=0f;
            coords[1].yCoord=0.25f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord+1,zCoord+1,5}})){
            coords[3].yCoord=1;
            coords[2].yCoord=0.75f;
        }

        List<Vec3f[]> coordSets = new ArrayList<Vec3f[]>();
        coordSets.add(coords);
        return coordSets;
    }

    public static List<Vec3f[]> vanillaSlopeZ4(World worldObj, int xCoord, int yCoord, int zCoord){
        Vec3f[] coords = rotateYaw(0,
                0,0.15f,0.5f,
                0,0.2f,0.25f,
                0,0.8f,-0.25f,
                0,0.85f, -0.5f);
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1,4}})){
            coords[0].yCoord=0;
            coords[1].yCoord=0.25f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord+1,zCoord-1,4}})){
            coords[3].yCoord=1f;
            coords[2].yCoord=0.75f;
        }

        List<Vec3f[]> coordSets = new ArrayList<Vec3f[]>();
        coordSets.add(coords);
        return coordSets;
    }

    public static List<Vec3f[]> vanillaSlopeX2(World worldObj, int xCoord, int yCoord, int zCoord){
        Vec3f[] coords = rotateYaw(0,
                -0.5f,0.15f,0,
                -0.25f, 0.2f, 0,
                0.25f, 0.8f, 0,
                0.5f,0.85f,0);

        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            coords[0].yCoord=0;
            coords[1].yCoord=0.25f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord+1,zCoord,2}})){
            coords[3].yCoord = 1f;
            coords[2].yCoord=0.75f;
        }

        List<Vec3f[]> coordSets = new ArrayList<Vec3f[]>();
        coordSets.add(coords);
        return coordSets;
    }

    public static List<Vec3f[]> vanillaSlopeX3(World worldObj, int xCoord, int yCoord, int zCoord){
        Vec3f[] coords = rotateYaw(0,
                0.5f,0.15f,0,
                0.25f, 0.2f, 0,
                -0.25f, 0.8f, 0,
                -0.5f,0.85f,0);
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            coords[0].yCoord=0;
            coords[1].yCoord=0.25f;
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord+1,zCoord,3}})){
            coords[3].yCoord=1f;
            coords[2].yCoord=0.75f;
        }

        List<Vec3f[]> coordSets = new ArrayList<Vec3f[]>();
        coordSets.add(coords);
        return coordSets;
    }

}
