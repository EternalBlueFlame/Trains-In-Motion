package ebf.tim.blocks.rails;

import ebf.tim.utility.DebugUtil;
import fexcraft.tmt.slim.Vec3f;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ebf.tim.blocks.rails.BlockRailCore.checkMultiblock;

/**
 * @author Eternal Blue Flame
 */
public class RailVanillaShapes extends RailShapeCore{


    public static List<RailSimpleShape> vanillaZStraight(World worldObj, int xCoord, int yCoord, int zCoord){
        //new code
        RailSimpleShape shape = new RailSimpleShape();
        shape.setSegments(4);
        shape.addPoints(normalizeVectors(new float[][]{
                {0,0,0.5f},
                {0,0, 0},
                {0,0, -0.5f}
        }));


        //intersections
        if(checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,1},{xCoord, yCoord,zCoord+2,0}})){
            shape.setPoint(0,0,0,1);
        }
        if(checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,1},{xCoord, yCoord,zCoord-2,0}})){
            shape.setPoint(0,0,0,-1);
        }
        //cover curve blending
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,7},{xCoord-1, yCoord,zCoord-1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,6},{xCoord+1, yCoord,zCoord-1,8}})){
            shape.setPoint(2,0,0,0f)
                    .setPoint(1,0,0,0.25f);

            shape.setSegments(2);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,9}, {xCoord+1, yCoord,zCoord+1,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,8},{xCoord-1, yCoord,zCoord+1,6}})){
            shape.setPoint(0,0,0,0f)
                    .setPoint(1,0,0,-0.25f)
                    .setSegments(2);
        }else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,7 }}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1,6}})){
            shape.setPoint(0,0,0,1.5f)
                    .setSegments(6);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,8}})) {
            shape.setPoint(2,0,0,-1.5f)
                    .setSegments(6);
        }


        //slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,4}})){
            shape.setPointY(2,0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1, 4}})){
            shape.setPointY(0,-0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1, 5}})){
            shape.setPointY(0,0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1, 5}})){
            shape.setPointY(2,-0.15f);
        }

        return Collections.singletonList(shape);
    }

    public static List<RailSimpleShape> vanillaXStraight(World worldObj, int xCoord, int yCoord, int zCoord){
        RailSimpleShape shape = new RailSimpleShape();
        shape.setSegments(4);
        shape.addPoints(normalizeVectors(new float[][]{
                {0.5f, 0, 0},
                {0f, 0, 0},
                {-0.5f, 0, 0}
        }));

        //intersections
        if(checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,0},{xCoord-2, yCoord,zCoord,1}})){
            shape.setPoint(0,-1,0,0);
        }
        if(checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,0},{xCoord+2, yCoord,zCoord,1}})){
            shape.setPoint(2,1,0,0);
        }


        //cover curve blending
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,9},{xCoord-1, yCoord,zCoord-1,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,6},{xCoord-1, yCoord,zCoord+1,8}})){

            shape.setPoint(0,0,0,0)
                    .setPoint(1,0.25f,0,0)
                    .setSegments(2);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,7}, {xCoord+1, yCoord,zCoord+1,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,8},{xCoord+1, yCoord,zCoord-1,6}})){

            shape.setPoint(2,0,0,0)
                    .setPoint(1,-0.25f,0,0)
                    .setSegments(2);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,9}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,6}})){
            shape.setPoint(2,1.5f,0,0);
        } else if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,7}}) ||
                checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,8}})){
            shape.setPoint(0,1.5f,0,0);
        }

        //slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,3}})){
            shape.setPointY(0,0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            shape.setPointY(2,-0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,2}})){
            shape.setPointY(2,0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            shape.setPointY(0,-0.15f);
        }


        return Collections.singletonList(shape);
    }


    public static List<RailSimpleShape> vanillaCurve6(World worldObj, int xCoord, int yCoord, int zCoord){
        RailSimpleShape shape = new RailSimpleShape();
        shape.setSegments(4);
        shape.addPoints(normalizeVectors(new float[][]{
                {0f, 0, 0.5f},
                {0f, 0, 0f},
                {0.5f, 0, 0f}
        }));

        if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,8}}) || checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,8}})) {
            shape.setPoint(1,normalizeVector( 0.25f, 0, 0.25f))
                    .setSegments(3);
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,0}})){
                shape.setPoint(0,0,0,1)
                        .setPoint(1,0f,0,0.5f)
                        .setSegments(5);
            }
            if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,1}})){
                shape.setPoint(2,1,0,0)
                        .setPoint(1,0.5f,0,0)
                        .setSegments(5);
            }
        }

        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            shape.setPointY(2,-0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,2}})){
            shape.setPointY(2,0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1, 4}})){
            shape.setPointY(0,-0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1, 5}})){
            shape.setPointY(0,0.15f);
        }

        return Collections.singletonList(shape);
    }

    public static List<RailSimpleShape> vanillaCurve8(World worldObj, int xCoord, int yCoord, int zCoord){
        //the base shape
        RailSimpleShape shape = new RailSimpleShape();
        shape.setSegments(4);
        shape.addPoints(normalizeVectors(new float[][]{
                {-0.5f, 0, 0},
                {0, 0, 0},
                {0, 0, -0.5f}
        }));

        if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,6}}) || checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,6}})) {
            //first half of the diagonal
            shape.setPoint(1,normalizeVector( -0.25f, 0, -0.25f))
                    .setSegments(3);
            if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord, zCoord - 1, 0}})) {
                //extension of one side to match up with a straight block
                shape.setPoint(2,normalizeVector( 0, 0, -1f))
                        .setPoint(1,normalizeVector( 0, 0, -0.5f))
                        .setSegments(5);
            }
            if (checkMultiblock(worldObj, new int[][]{{xCoord - 1, yCoord, zCoord, 1}})) {
                //extension of one side to match up with a straight block
                shape.setPoint(0,normalizeVector( -1, 0, 0))
                        .setPoint(1,normalizeVector( -0.5f, 0, 0))
                        .setSegments(5);
            }
        }

        //slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,3}})){
            shape.setPointY(0,0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            shape.setPointY(0,-0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,4}})){
            shape.setPointY(2,0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1, 5}})){
            shape.setPointY(2,-0.15f);
        }

        return Collections.singletonList(shape);
    }


    public static List<RailSimpleShape> vanillaCurve7(World worldObj, int xCoord, int yCoord, int zCoord){
        //the base shape
        RailSimpleShape shape = new RailSimpleShape();
        shape.setSegments(4);
        shape.addPoints(normalizeVectors(new float[][]{
                {-0.5f, 0, 0},
                {0f, 0, 0},
                {0, 0, 0.5f}
        }));

        if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,9}}) || checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,9}})){
            shape.setPoint(1,normalizeVector( -0.25f,0, 0.25f))
                    .setSegments(3);
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord+1,0}})){
                //extension of one side to match up with a straight block
                shape.setPoint(2,normalizeVector(0, 0,1))
                        .setPoint(1,normalizeVector( 0,0, 0.5f))
                        .setSegments(5);
            }
            if (checkMultiblock(worldObj, new int[][]{{xCoord-1,yCoord,zCoord,1}})){
                //extension of one side to match up with a straight block
                shape.setPoint(0,normalizeVector( -1, 0,0))
                        .setPoint(1,normalizeVector( -0.5f , 0,0))
                        .setSegments(5);
            }
        }

        //slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord,zCoord,3}})){
            shape.setPointY(0,0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            shape.setPointY(0,-0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1, 4}})){
            shape.setPointY(2,-0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord+1, 5}})){
            shape.setPointY(2,0.15f);
        }

        return Collections.singletonList(shape);
    }

    public static List<RailSimpleShape> vanillaCurve9(World worldObj, int xCoord, int yCoord, int zCoord){
        //the base shape
        RailSimpleShape shape = new RailSimpleShape();
        shape.setSegments(4);
        shape.addPoints(normalizeVectors(new float[][]{
                {0.5f, 0, 0},
                {0, 0, 0},
                {0, 0, -0.5f}
        }));

        if (checkMultiblock(worldObj, new int[][]{{xCoord+1,yCoord,zCoord,7}}) || checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,7}})){
            //first half of the diagonal
            shape.setPoint(1,normalizeVector( 0.25f, 0,-0.25f))
                    .setSegments(3);
            if (checkMultiblock(worldObj, new int[][]{{xCoord,yCoord,zCoord-1,0}})){
                //extension of one side to match up with a straight block
                shape.setPoint(2,normalizeVector( 0,0,-1))
                        .setPoint(1,normalizeVector( 0, 0,-0.5f))
                        .setSegments(5);
            }
            if (checkMultiblock(worldObj, new int[][]{{xCoord + 1, yCoord, zCoord, 1}})) {
                //extension of one side to match up with a straight block
                shape.setPoint(0,normalizeVector( 1, 0,0))
                        .setPoint(1,normalizeVector( 0.5f,0, 0))
                        .setSegments(5);
            }
        }
//slopes
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            shape.setPointY(0,-0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord,zCoord,2}})){
            shape.setPointY(0,0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord,zCoord-1,4}})){
            shape.setPointY(2,0.15f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1, 5}})){
            shape.setPointY(2,-0.15f);
        }

        return Collections.singletonList(shape);
    }





    public static List<RailSimpleShape> vanillaSlopeZ5(World worldObj, int xCoord, int yCoord, int zCoord){
        RailSimpleShape shape = new RailSimpleShape();
        shape.setSegments(4);
        shape.addPoints(normalizeVectors(new float[][]{
                {0, 0.15f, -0.5f},
                {0, 0.2f, -0.25f},

                {0, 0.5f, 0},
                {0, 0.5f, 0},

                {0, 0.8f, 0.25f},
                {0, 0.85f, 0.5f}
        }));
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord-1,5}})){
            shape.setPointY(0,0f)
                    .setPointY(1,0.25f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord+1,zCoord+1,5}})){
            shape.setPointY(5,1)
                    .setPointY(4,0.75f);
        }

        return Collections.singletonList(shape);
    }

    public static List<RailSimpleShape> vanillaSlopeZ4(World worldObj, int xCoord, int yCoord, int zCoord){
        RailSimpleShape shape = new RailSimpleShape();
        shape.setSegments(4);
        shape.addPoints(normalizeVectors(new float[][]{
                {0, 0.15f, 0.5f},
                {0, 0.2f, 0.25f},

                {0, 0.5f, 0},
                {0, 0.5f, 0},

                {0, 0.8f, -0.25f},
                {0, 0.85f, -0.5f}
        }));
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord-1,zCoord+1,4}})){
            shape.setPointY(0,0)
                    .setPointY(1,0.25f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord, yCoord+1,zCoord-1,4}})){
            shape.setPointY(5,1f)
                    .setPointY(4,0.75f);
        }

        return Collections.singletonList(shape);
    }

    public static List<RailSimpleShape> vanillaSlopeX2(World worldObj, int xCoord, int yCoord, int zCoord){
        RailSimpleShape shape = new RailSimpleShape();
        shape.setSegments(4);
        shape.addPoints(normalizeVectors(new float[][]{
                {-0.5f, 0.15f, 0},
                {-0.25f, 0.2f, 0},

                {0, 0.5f, 0},
                {0, 0.5f, 0},

                {0.25f, 0.8f, 0},
                {0.5f, 0.85f, 0}
        }));

        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord-1,zCoord,2}})){
            shape.setPointY(0,0)
                    .setPointY(1,0.25f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord+1,zCoord,2}})){
            shape.setPointY(5,1f)
                    .setPointY(4,0.75f);
        }

        return Collections.singletonList(shape);
    }

    public static List<RailSimpleShape> vanillaSlopeX3(World worldObj, int xCoord, int yCoord, int zCoord){
        RailSimpleShape shape = new RailSimpleShape();
        shape.setSegments(4);
        shape.addPoints(normalizeVectors(new float[][]{
                {0.5f, 0.15f, 0},
                {0.25f, 0.2f, 0},

                {0, 0.5f, 0},
                {0, 0.5f, 0},

                {-0.25f, 0.8f, 0},
                {-0.5f, 0.85f, 0}
        }));
        if (checkMultiblock(worldObj, new int[][]{{xCoord+1, yCoord-1,zCoord,3}})){
            shape.setPointY(0,0)
                    .setPointY(1,0.25f);
        }
        if (checkMultiblock(worldObj, new int[][]{{xCoord-1, yCoord+1,zCoord,3}})){
            shape.setPointY(5,1f)
                    .setPointY(4,0.75f);
        }

        return Collections.singletonList(shape);
    }

}
