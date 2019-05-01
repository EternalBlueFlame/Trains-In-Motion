package ebf.tim.blocks.rails;

import ebf.tim.blocks.RailTileEntity;
import fexcraft.tmt.slim.Vec3f;
import net.minecraft.util.MathHelper;

import java.util.List;

import static ebf.tim.utility.RailUtility.radianF;

public class RailShapeCore {


    public static void processPoints(List<Vec3f[]> coordList, float[] gauge, RailTileEntity tile, float length){
        for(Vec3f[] coords : coordList) {
            BlockRailCore.multiTriGenModel(coords, gauge, length, tile);
        }
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
     * this is intended for use with
     * @see BlockRailCore#quadGenModel(Vec3f, Vec3f, Vec3f, Vec3, float[], float, RailTileEntity)
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
     * @see BlockRailCore#multiTriGenModel(Vec3f[], float[], float, RailTileEntity)
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
