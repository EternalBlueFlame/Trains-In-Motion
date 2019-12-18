package ebf.tim.blocks.rails;

import ebf.XmlBuilder;
import ebf.tim.blocks.RailTileEntity;
import ebf.tim.utility.DebugUtil;
import ebf.tim.utility.RailUtility;
import ebf.tim.utility.Vec5f;
import ebf.tim.utility.Vec6f;
import fexcraft.tmt.slim.Vec3f;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class RailShapeCore {

    public List<Vec6f> activePath = new ArrayList<>();
    public boolean[] ends = {true, true};
    public int[] gauge;
    public float tieCount;
    /*CLIENT ONLY*/
    public List<Vec5f> activeTiePath = new ArrayList<>();
    public static Random rand = new Random();


    public static void processPoints(int x, int y, int z,
                                     RailSimpleShape coordList, int[] mmFromCenter,
                                     World dimension, @Nullable XmlBuilder data){

        if(data==null){
            if (dimension.getTileEntity(x,y,z) instanceof RailTileEntity) {
                data = ((RailTileEntity)dimension.getTileEntity(x, y, z)).data;
            }
            if(data==null) {
                data = new XmlBuilder();
            }
        }
        RailShapeCore s = multiTriGenModel(dimension,x,y,z,coordList, mmFromCenter);
        if(s!=null) {
            data.putString("route", s.toString());
        }
        if (dimension.getTileEntity(x,y,z) instanceof RailTileEntity) {
            ((RailTileEntity) dimension.getTileEntity(x, y, z)).data = data;
        }

    }


    public String toString(){
        StringBuilder sb = new StringBuilder();

        for (int g : gauge){
            sb.append(g);
            sb.append(",");
        }
        sb.append("::");
        sb.append(tieCount);
        sb.append("::");

        for(Vec6f vector : activePath){
            sb.append(vector.xCoord);
            sb.append(",");
            sb.append(vector.yCoord);
            sb.append(",");
            sb.append(vector.zCoord);
            sb.append(",");
            sb.append(vector.u);
            sb.append(",");
            sb.append(vector.v);
            sb.append(",");
            sb.append(vector.w);
            sb.append("!");
        }
        sb.append("::");
        return sb.toString();
    }

    public RailShapeCore parseString(String s){
        if(s==null){return this;}
        String[] vars = s.split("::"),currentParse, subParse;

        currentParse= vars[0].split(",");
        gauge=new int[currentParse.length];
        for(int i=0; i<currentParse.length;i++){
            gauge[i] = Integer.parseInt(currentParse[i]);
        }
        tieCount =Float.parseFloat(vars[1]);


        if(vars.length<3){return null;}
        currentParse= vars[2].split("!");
        for(String str : currentParse) {
            subParse=str.split(",");
            activePath.add(new Vec6f(Float.parseFloat(subParse[0]), Float.parseFloat(subParse[1]), Float.parseFloat(subParse[2]),
                    Float.parseFloat(subParse[3]), Float.parseFloat(subParse[4]), Float.parseFloat(subParse[5])));
        }


        return this;
    }

    public float[] getGaugePositions(){
        float[] pos = new float[gauge.length+1];
        pos[0]=-gauge[0]*0.00041666666f;//83333333f;
        if(gauge[0]!=0.0) {
            for (int i = 0; i < gauge.length; i++) {
                pos[i + 1] = gauge[i] * 0.00041666666f;
            }
        }
        return pos;
    }

    public static RailShapeCore multiTriGenModel(World world, int x, int y, int z, RailSimpleShape shape, int[] railOffsets){
        RailShapeCore sc = new RailShapeCore();
        sc.gauge=railOffsets;
        sc.activePath = new ArrayList<>();
        sc.activeTiePath= new ArrayList<>();
        List<Vec6f> points;
        float t;
        int i;

        float originalT=1f/3f;//1/6

        t = originalT*-1;
        //calculate the bezier curve, this initial janky version is used to get an accurate gauge of the distance between points.
        points = new ArrayList<>();
        Vec6f v;
        while (t <= 1+originalT) {
            //define position
            v=new Vec6f(
                    (((1f - t) * (1f - t)) * shape.getStart().xCoord) + (2f * (1f - t) * t * shape.getCenter().xCoord) + ((t * t) * shape.getEnd().xCoord),//X
                    0,//Y
                    (((1f - t) * (1f - t)) * shape.getStart().zCoord) + (2f * (1f - t) * t * shape.getCenter().zCoord) + ((t * t) * shape.getEnd().zCoord)//X
            ,0,0,
                    (((1f - t) * (1f - t)) * shape.getStart().w) + (2f * (1f - t) * t * shape.getCenter().w) + ((t * t) * shape.getEnd().w));

            if(shape.getRawStart().yCoord!=0 || shape.getRawEnd().yCoord!=0) {
                v.yCoord = (((1f - t) * (1f - t)) * shape.getStart().yCoord) + (2f * (1f - t) * t * (shape.getCenter().yCoord)) + ((t * t) * shape.getEnd().yCoord);
            }

            points.add(v);
            t += originalT;
        }
        //define rotations
        for (i=1; i < points.size() - 1; i++) {
            points.get(i).setUV(0,RailUtility.atan2degreesf(
                    points.get(i-1).zCoord - (points.get(i+1).zCoord),
                    points.get(i-1).xCoord - (points.get(i+1).xCoord)));
            sc.activePath.add(points.get(i));
        }
        //segment path
        //sc.activePath.add(points.get(1).setW(shape.getStart().w));
        //sc.activePath.add(points.get(2).setW(shape.getCenter().w));
        //sc.activePath.add(points.get(3).setW(shape.getCenter().w));
        //sc.activePath.add(points.get(4).setW(shape.getEnd().w));


        //update tracks at the ends to deform to the shape for this track


        //update this track again using the deformed versions for the other tracks.

        //add offset to counteract overlapping ties.
        for (i=1; i < points.size() - 1; i++) {
            points.get(i).yCoord+=rand.nextInt(10)*0.00001f;
        }
        //define ties todo: borked on diagonals
        t=0;
        while (!positionPastEnd(t, points)){
            sc.activeTiePath.add(getPosition(t, points));
            t+=0.25d;
        }



        return sc;
    }

    public static boolean positionPastEnd(float distance, List<Vec6f> points){
        float totalTraveled = 0;
        for(int i = 0; i < points.size() - 1; i++){
            if((totalTraveled += points.get(i).distance2d(points.get(i + 1))) > distance){
                return false;
            }
        }
        return true;
    }

    public static Vec6f getPosition(float distance, List<Vec6f> points){
        float totalTraveled = 0, traveled;
        for(int i = 0; i < points.size() - 1; i++){
            traveled = totalTraveled += points.get(i).distance2d(points.get(i + 1));
            if(traveled >= distance){
                return points.get(i);
            }
        }
        return points.get(1);//.distance(points.get(0), distance);
    }
}
