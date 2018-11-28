package ebf.tim.models.rails;

import ebf.tim.utility.RailUtility;
import fexcraft.tmt.slim.Tessellator;
import net.minecraft.init.Blocks;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class Model1x1Rail {

    private static float[] vert;

    public static void addVertexWithOffset(float[] p, float width, float height, float depth){
        vert= RailUtility.rotatePointF(depth,height,width,0,p[4],0);
        Tessellator.getInstance().addVertex(vert[0]+p[0],vert[1]+p[1], vert[2]+p[2]);
    }

    public static void addVertexWithOffsetAndUV(float[] p, float width, float height, float depth, float U, float V){
        vert= RailUtility.rotatePointF(depth,height,width,p[3],p[4],0);
        Tessellator.getInstance().addVertexWithUV(vert[0]+p[0],vert[1]+p[1], vert[2]+p[2],U,V);
    }


    //todo use the return value to manage displaylists
    public static boolean Model3DRail(List<float[]> points, float[] railOffsets, float segmentLength){
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
            ModelRail.model3DRail(points,railOffsets);
            //shouldnt even need to define models like the rails, only need 2, flat and 3d, get a boolean operator config.ispotato?.

            ModelTies.model3DTies(points,maxWidth,minWidth, Blocks.log);
            ModelBallast.model3DBallast(points,maxWidth,minWidth, Blocks.gravel, segmentLength);


            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_COORD_ARRAY);
            GL11.glDisable(GL11.GL_VERTEX_ARRAY);

        }


        return true;
    }
}
