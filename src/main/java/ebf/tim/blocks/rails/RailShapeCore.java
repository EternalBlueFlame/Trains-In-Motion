package ebf.tim.blocks.rails;

import ebf.tim.blocks.RailTileEntity;
import ebf.tim.utility.CommonProxy;
import ebf.tim.utility.RailUtility;
import ebf.tim.utility.Vec5f;
import fexcraft.tmt.slim.Vec3f;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static ebf.tim.utility.RailUtility.radianF;

public class RailShapeCore {

    public List<Vec5f> activePath = new ArrayList<>();
    public List<List<Vec5f>> inactivePaths = new ArrayList<>();
    public int[] gauge;
    public float segmentLength;

    public static void processPoints(List<Vec3f[]> coordList, float[] gauge, RailTileEntity tile, float length){
        for(Vec3f[] coords : coordList) {
            multiTriGenModel(coords, gauge, length, tile);
        }
    }

    public static void processPoints(int x, int y, int z, List<Vec3f[]> coordList, int[] mmFromCenter, float length, World dimension, @Nullable NBTTagCompound data){
        if(data==null){
            data = CommonProxy.getRailMap(dimension).get(x,y,z);
            if(data==null) {
                data = new NBTTagCompound();
            }
        }
        RailShapeCore s = multiTriGenModel(coordList.get(0), mmFromCenter, length);
        if(s!=null) {
            data.setString("route", s.toString());
        }

        for(int i=1; i< coordList.size();i++) {
            s = multiTriGenModel(coordList.get(i), mmFromCenter, length);
            if(s!=null) {
                data.setString("subroute" + i, s.toString());
            }
        }

        CommonProxy.getRailMap(dimension).add(x,y,z, data, dimension);
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
        sb.append("::");
        return sb.toString();
    }

    public RailShapeCore parseString(String s){
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
        return this;
    }

    public static void multiTriGenModel(Vec3f[] shape, float[] railOffsets, float segmentation, RailTileEntity tile){

        RailShapeCore s = new RailShapeCore();
        for(int v=0;v<shape.length-2;v+=3) {
            segmentation = Math.max(segmentation, 1);

            float originalT = Math.abs(shape[v].xCoord) + Math.abs(shape[v].zCoord);
            originalT += Math.abs(shape[v+1].xCoord) + Math.abs(shape[v+1].zCoord);
            originalT += Math.abs(shape[v+2].xCoord) + Math.abs(shape[v+2].zCoord);
            originalT = originalT / (originalT * segmentation);

            float t = -originalT;
            int i;
            //calculate the bezier curve, this initial janky version is used to get an accurate gauge of the distance between points.
            List<float[]> points = new ArrayList<>();
            for (i = 0; i < segmentation + 3; i++) {
                //define position
                points.add(new float[]{
                        (((1 - t) * (1 - t)) * shape[v].xCoord) + (2 * (1 - t) * t * shape[v+1].xCoord) + ((t * t) * shape[v+2].xCoord),//X
                        (((1 - t) * (1 - t)) * shape[v].yCoord) + (2 * (1 - t) * t * shape[v+1].yCoord) + ((t * t) * shape[v+2].yCoord),//Y
                        (((1 - t) * (1 - t)) * shape[v].zCoord) + (2 * (1 - t) * t * shape[v+1].zCoord) + ((t * t) * shape[v+2].zCoord),//X
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
        tile.points=s;
    }

    public float[] getGaugePositions(){
        float[] pos = new float[gauge.length+1];
        pos[0]=-gauge[0]*0.000833333333f;
        for(int i=0; i<gauge.length;i++){
            pos[i+1]=gauge[i]*0.000833333333f;
        }
        return pos;
    }

    public static RailShapeCore multiTriGenModel(Vec3f[] shape, int[] railOffsets, float segmentation){
        RailShapeCore sc = new RailShapeCore();

        for(int v=0;v<shape.length-2;v+=3) {
            segmentation = Math.max(segmentation, 1);

            float originalT = Math.abs(shape[v].xCoord) + Math.abs(shape[v].zCoord);
            originalT += Math.abs(shape[v+1].xCoord) + Math.abs(shape[v+1].zCoord);
            originalT += Math.abs(shape[v+2].xCoord) + Math.abs(shape[v+2].zCoord);
            originalT = originalT / (originalT * segmentation);

            float t = -originalT;
            int i;
            //calculate the bezier curve, this initial janky version is used to get an accurate gauge of the distance between points.
            List<float[]> points = new ArrayList<>();
            for (i = 0; i < segmentation + 3; i++) {
                //define position
                points.add(new float[]{
                        (((1 - t) * (1 - t)) * shape[v].xCoord) + (2 * (1 - t) * t * shape[v+1].xCoord) + ((t * t) * shape[v+2].xCoord),//X
                        (((1 - t) * (1 - t)) * shape[v].yCoord) + (2 * (1 - t) * t * shape[v+1].yCoord) + ((t * t) * shape[v+2].yCoord),//Y
                        (((1 - t) * (1 - t)) * shape[v].zCoord) + (2 * (1 - t) * t * shape[v+1].zCoord) + ((t * t) * shape[v+2].zCoord),//X
                });
                t += originalT;
            }



            sc.activePath = new ArrayList<>();
            for (i=1; i < points.size() - 1; i++) {
                sc.activePath.add(
                        new Vec5f(points.get(i)[0],points.get(i)[1],points.get(i)[2],0, RailUtility.atan2degreesf(
                                points.get(i-1)[2] - (points.get(i+1)[2]),
                                points.get(i-1)[0] - (points.get(i+1)[0])))
                );
            }

            for(i=0; i<railOffsets.length;i++) {
                railOffsets[i] *= 0.00083333333;
            }
            sc.gauge=railOffsets;
            sc.segmentLength=segmentation;

            return sc;
        }

        return null;
    }


    /**
     * rotates an already defined path
     * @param path already defined path
     * @param yaw yaw to rotate around, if 0 returns path
     * @return
     */
    public static Vec3f[] normalizeVector(Vec3f[] path, float yaw) {
        //rotate yaw
        if (yaw != 0.0F) {
            yaw *= radianF;
            float cos = MathHelper.cos(yaw);
            float sin = MathHelper.sin(yaw);
            float xCoord;
            float zCoord;
            for(Vec3f xyz: path) {
                xCoord =xyz.xCoord-0.5f;
                zCoord =xyz.zCoord-0.5f;
                xyz.xCoord = (xCoord * cos) - (zCoord * sin);
                xyz.zCoord = (xCoord * sin) + (zCoord * cos);
                xyz.xCoord+=0.5f;
                xyz.zCoord+=0.5f;
            }
        }
        return path;
    }

    /**
     * defines a path based on the provided x/y/z vectors.
     * @param yaw rotates the path if it's not 0
     * @return
     */
    public static Vec3f[] normalizeVector(float yaw, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4) {
        //rotate yaw
        if (yaw != 0.0F) {
            yaw *= radianF;
            float cos = MathHelper.cos(yaw);
            float sin = MathHelper.sin(yaw);

            float xCoord =x1;
            float zCoord =z1;
            x1 = (xCoord * cos) - (zCoord * sin);
            z1 = (xCoord * sin) + (zCoord * cos);

            xCoord =x2;
            zCoord =z2;
            x2 = (xCoord * cos) - (zCoord * sin);
            z2 = (xCoord * sin) + (zCoord * cos);

            xCoord =x3;
            zCoord =z3;
            x3 = (xCoord * cos) - (zCoord * sin);
            z3 = (xCoord * sin) + (zCoord * cos);

            xCoord =x4;
            zCoord =z4;
            x4 = (xCoord * cos) - (zCoord * sin);
            z4 = (xCoord * sin) + (zCoord * cos);
        }
        return new Vec3f[]{new Vec3f(x1+0.5f, y1, z1+0.5f), new Vec3f(x2+0.5f, y2, z2+0.5f), new Vec3f(x3+0.5f, y3, z3+0.5f), new Vec3f(x4+0.5f, y4, z4+0.5f)};
    }

    /**
     * centers the path based on the provided x/y/z vectors.
     * this is intended for use with
     * @see RailShapeCore#multiTriGenModel(Vec3f[], float[], float, RailTileEntity)
     * @return
     */
    public static Vec3f[] normalizeVectors(float[][] vectors) {
        Vec3f[] values = new Vec3f[vectors.length];
        for (int i=0; i<vectors.length;i++){
            values[i]= new Vec3f(vectors[i][0]+0.5f,vectors[i][1],vectors[i][2]+0.5f);
        }
        return values;
    }

    /**
     * centers the path based on the provided x/y/z vectors. this one is aimed more-so at defining individual points to replace points of a path.
     * good for deforms or other weird edits.
     * @return
     */
    public static Vec3f normalizeVector(float x1, float y1, float z1) {
        return new Vec3f(x1+0.5f, y1, z1+0.5f);
    }
}
