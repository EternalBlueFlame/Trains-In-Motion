package ebf.tim.blocks.rails;

import ebf.XmlBuilder;
import ebf.tim.blocks.RailTileEntity;
import ebf.tim.utility.*;
import fexcraft.tmt.slim.Vec3f;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static ebf.tim.utility.RailUtility.radianF;

public class RailShapeCore {

    public List<Vec5f> activePath = new ArrayList<>();
    public boolean[] ends = {true, true};
    public List<List<Vec5f>> inactivePaths = new ArrayList<>();
    public int[] gauge;
    public float segmentLength;

    public static void processPoints(List<RailSimpleShape> coordList, float[] gauge, RailTileEntity tile){
        for(RailSimpleShape coords : coordList) {
            multiTriGenModel(coords, gauge, tile);
        }
    }

    public static void processPoints(int x, int y, int z,
                                     List<RailSimpleShape> coordList, int[] mmFromCenter,
                                     World dimension, @Nullable XmlBuilder data){

        if(data==null){
            if (dimension.getTileEntity(x,y,z) instanceof RailTileEntity) {
                data = ((RailTileEntity)dimension.getTileEntity(x, y, z)).data;
                //data = CommonProxy.getRailMap(dimension).data.getXml(BlockNBTMap.vector(x, y, z));
            }
            if(data==null) {
                data = new XmlBuilder();
            }
        }
        RailShapeCore s = multiTriGenModel(coordList.get(0), mmFromCenter);
        if(s!=null) {
            data.putString("route", s.toString());
        }

        for(int i=1; i< coordList.size();i++) {
            s = multiTriGenModel(coordList.get(i), mmFromCenter);
            if(s!=null) {
                data.putString("subroute" + i, s.toString());
            }
        }
        if (dimension.getTileEntity(x,y,z) instanceof RailTileEntity) {
            ((RailTileEntity) dimension.getTileEntity(x, y, z)).data = data;
        }

        //CommonProxy.getRailMap(dimension).add(x,y,z, data, dimension);
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();

        for (int g : gauge){
            sb.append(g);
            sb.append(",");
        }
        sb.append("::");
        sb.append(segmentLength);
        sb.append("::");

        for(Vec5f vector : activePath){
            sb.append(vector.xCoord);
            sb.append(",");
            sb.append(vector.yCoord);
            sb.append(",");
            sb.append(vector.zCoord);
            sb.append(",");
            sb.append(vector.u);
            sb.append(",");
            sb.append(vector.v);
            sb.append("!");
        }
        //sb.append("::");
        //todo: loop above for tie paths
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
        segmentLength=Float.parseFloat(vars[1]);

        currentParse= vars[2].split("!");
        for(String str : currentParse) {
            subParse=str.split(",");
            activePath.add(new Vec5f(Float.parseFloat(subParse[0]),Float.parseFloat(subParse[1]),Float.parseFloat(subParse[2]),
                    Float.parseFloat(subParse[3]),Float.parseFloat(subParse[4])));
        }
        //todo: loop above for tie paths
        /*
        currentParse= vars[3].split("!");
        for(String str : currentParse) {
         */


        return this;
    }

    public static void multiTriGenModel(RailSimpleShape shape, float[] railOffsets, RailTileEntity tile){
        RailShapeCore s = new RailShapeCore();
        for(int v=0;v<shape.getPath().length-2;v+=3) {
            float originalT = Math.abs(shape.getPath()[v].xCoord) + Math.abs(shape.getPath()[v].zCoord);
            originalT += Math.abs(shape.getPath()[v+1].xCoord) + Math.abs(shape.getPath()[v+1].zCoord);
            originalT += Math.abs(shape.getPath()[v+2].xCoord) + Math.abs(shape.getPath()[v+2].zCoord);
            originalT = originalT / (originalT * shape.getPathLength());

            float t = -originalT;
            int i;
            //calculate the bezier curve, this initial janky version is used to get an accurate gauge of the distance between points.
            List<float[]> points = new ArrayList<>();
            for (i = 0; i < shape.getPathLength() + 3; i++) {
                //define position
                points.add(new float[]{
                        (((1 - t) * (1 - t)) * shape.getPath()[v].xCoord) + (2 * (1 - t) * t * shape.getPath()[v+1].xCoord) + ((t * t) * shape.getPath()[v+2].xCoord),//X
                        (((1 - t) * (1 - t)) * shape.getPath()[v].yCoord) + (2 * (1 - t) * t * shape.getPath()[v+1].yCoord) + ((t * t) * shape.getPath()[v+2].yCoord),//Y
                        (((1 - t) * (1 - t)) * shape.getPath()[v].zCoord) + (2 * (1 - t) * t * shape.getPath()[v+1].zCoord) + ((t * t) * shape.getPath()[v+2].zCoord),//X
                });
                t += originalT;
            }

            for (i=1; i < points.size() - 1; i++) {
                s.activePath.add(
                        new Vec5f(points.get(i)[0],points.get(i)[1],points.get(i)[2],0, RailUtility.atan2degreesf(
                                points.get(i-1)[2] - (points.get(i+1)[2]),
                                points.get(i-1)[0] - (points.get(i+1)[0])))
                );
            }
        }

        int[] intOffsets= new int[railOffsets.length];
        for(int i=0; i<railOffsets.length;i++) {
            railOffsets[i] *= 0.00083333333;
            intOffsets[i]= (int)railOffsets[i];
        }
        s.gauge=intOffsets;
        //tile.points=s;
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

    //TODO: path for ties needs to be it's own thing
    public static RailShapeCore multiTriGenModel(RailSimpleShape shape, int[] railOffsets){
        RailShapeCore sc = new RailShapeCore();
        sc.gauge=railOffsets;
        sc.segmentLength=shape.getPathLength();
        sc.activePath = new ArrayList<>();
        List<float[]> points;
        float originalT, t;
        int i;

        for(int v=0;v<shape.getPath().length-2;v+=3) {

            originalT = Math.abs(shape.getPath()[v].xCoord) + Math.abs(shape.getPath()[v].zCoord);
            originalT += Math.abs(shape.getPath()[v+1].xCoord) + Math.abs(shape.getPath()[v+1].zCoord);
            originalT += Math.abs(shape.getPath()[v+2].xCoord) + Math.abs(shape.getPath()[v+2].zCoord);
            originalT = originalT / (originalT * shape.getPathLength());

            t = -originalT;
            //calculate the bezier curve, this initial janky version is used to get an accurate gauge of the distance between points.
            points = new ArrayList<>();
            for (i = 0; i < shape.getPathLength() + 3; i++) {
                //define position
                points.add(new float[]{
                        (((1 - t) * (1 - t)) * shape.getPath()[v].xCoord) + (2 * (1 - t) * t * shape.getPath()[v+1].xCoord) + ((t * t) * shape.getPath()[v+2].xCoord),//X
                        (((1 - t) * (1 - t)) * shape.getPath()[v].yCoord) + (2 * (1 - t) * t * shape.getPath()[v+1].yCoord) + ((t * t) * shape.getPath()[v+2].yCoord),//Y
                        (((1 - t) * (1 - t)) * shape.getPath()[v].zCoord) + (2 * (1 - t) * t * shape.getPath()[v+1].zCoord) + ((t * t) * shape.getPath()[v+2].zCoord),//X
                });
                t += originalT;
            }

            for (i=1; i < points.size() - 1; i++) {
                sc.activePath.add(
                        new Vec5f(points.get(i)[0],points.get(i)[1],points.get(i)[2],0, RailUtility.atan2degreesf(
                                points.get(i-1)[2] - (points.get(i+1)[2]),
                                points.get(i-1)[0] - (points.get(i+1)[0])))
                );
            }

            //TODO: tie position math here, should be more or less the same as above.
            //will need new variable in this class for the ties.

        }
        return sc;
    }


    /**
     * centers the path based on the provided x/y/z vectors.
     * this is intended for use with
     * @see RailShapeCore#multiTriGenModel(RailSimpleShape, float[], RailTileEntity)
     * @return
     */
    @Deprecated //todo: this is unnecessary overhead converting arrays of floats to vec3's.
    public static Vec3f[] normalizeVectors(float[][] vectors) {
        Vec3f[] values = new Vec3f[vectors.length];
        for (int i=0; i<vectors.length;i++){
            values[i]= new Vec3f(vectors[i][0],vectors[i][1],vectors[i][2]);
        }
        return values;
    }

    /**
     * centers the path based on the provided x/y/z vectors. this one is aimed more-so at defining individual points to replace points of a path.
     * good for deforms or other weird edits.
     * @return
     */
    @Deprecated
    public static Vec3f normalizeVector(float x1, float y1, float z1) {
        return new Vec3f(x1+0f, y1, z1+0f);
    }
}
