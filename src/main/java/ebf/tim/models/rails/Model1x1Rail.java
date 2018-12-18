package ebf.tim.models.rails;

import fexcraft.tmt.slim.Tessellator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.List;

import static ebf.tim.utility.RailUtility.radianF;

public class Model1x1Rail {

    private static float cos, sin;
    private static float[] vert = new float[]{0,0,0};
    //NOTE: this method is not thread safe, in the case of multi-threaded rendering this may cause problems, in that case revert to the variant in RailUtility.
    private static void rotateVertexPoint(float x, float y, float z, float pitch, float yaw) {
        vert[0]=x;vert[1]=y;vert[2]=z;
        //rotate pitch
        if (pitch != 0.0F) {
            pitch *= radianF;
            cos = MathHelper.cos(pitch);
            sin = MathHelper.sin(pitch);

            vert[0] = (y * sin) + (x * cos);
            vert[1] = (y * cos) - (x * sin);
        }
        //rotate yaw
        if (yaw != 0.0F) {
            yaw *= radianF;
            cos = MathHelper.cos(yaw);
            sin = MathHelper.sin(yaw);

            vert[0] = (x * cos) - (z * sin);
            vert[2] = (x * sin) + (z * cos);
        }
        //rotate roll
        /*if (roll != 0.0F) {
            roll *=  radianF;
            cos = MathHelper.cos(roll);
            sin = MathHelper.sin(roll);

            xyz[1] = (z * cos) - (y * sin);
            xyz[2] = (z * sin) + (y * cos);
        }*/
    }

    public static void addVertexWithOffset(float[] p, float width, float height, float depth){
        rotateVertexPoint(depth,height,width,p[3],p[4]);
        Tessellator.getInstance().addVertex(vert[0]+p[0],vert[1]+p[1], vert[2]+p[2]);
    }

    public static void addVertexWithOffsetAndUV(float[] p, float width, float height, float depth, float U, float V){
        rotateVertexPoint(depth,height,width,p[3],p[4]);
        Tessellator.getInstance().addVertexWithUV(vert[0]+p[0],vert[1]+p[1], vert[2]+p[2],U,V);
    }


    //todo use the return value to manage displaylists
    public static boolean Model3DRail(List<float[]> points, float[] railOffsets, float segmentLength, @Nullable Block ballast, @Nullable Block ties, ItemStack railBlock){
        if(railOffsets==null || points ==null){
            return false;
        }

        if(points.size()>0) {
            float minWidth=0, maxWidth=0;
            for(float rail : railOffsets) {
                //might as well do this here since we gotta loop it anyway, and only need to do it for the first, not like it changes later.
                if (rail < minWidth) {
                    minWidth = rail;
                }
                if (rail > maxWidth) {
                    maxWidth = rail;
                }
            }


            GL11.glEnable(GL11.GL_VERTEX_ARRAY);
            GL11.glEnable(GL11.GL_TEXTURE_COORD_ARRAY);
            GL11.glDisable(GL11.GL_LIGHTING);

            //renders the rails, also defines min and max width
            ModelRail.model3DRail(points,railOffsets, railBlock);
            //shouldnt even need to define models like the rails, only need 2, flat and 3d, get a boolean operator config.ispotato?.

            if(ties!=null) {
                ModelTies.model3DTies(points, maxWidth, minWidth, ties);
            }
            if(ballast!=null) {
                ModelBallast.model3DBallast(points, maxWidth, minWidth, ballast, segmentLength);
            }


            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_COORD_ARRAY);
            GL11.glDisable(GL11.GL_VERTEX_ARRAY);

        }


        return true;
    }
}
