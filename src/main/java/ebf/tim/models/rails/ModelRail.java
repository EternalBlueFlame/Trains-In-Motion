package ebf.tim.models.rails;

import fexcraft.tmt.slim.Tessellator;
import fexcraft.tmt.slim.TextureManager;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import javax.annotation.Nullable;
import java.util.List;

import static ebf.tim.models.rails.Model1x1Rail.addVertexWithOffset;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class ModelRail {

    public static void modelPotatoRail(List<float[]> points, float[] railOffsets,int[] color){

        GL11.glPushMatrix();
        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glTranslated(0, 0.15, 0);
        GL11.glColor4f(
                (color[0])* 0.00392156863f,
                (color[1])* 0.00392156863f,
                (color[2])* 0.00392156863f, 1);

        for(float rail : railOffsets) {
            Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);
            for (float[] p : points) {
                addVertexWithOffset(p, 0.0625f + rail, 0, 0);
                addVertexWithOffset(p, -0.0625f + rail, 0, 0);
            }
            Tessellator.getInstance().arrayEnabledDraw();
        }
        GL11.glPopMatrix();
        GL11.glEnable(GL_TEXTURE_2D);
    }

    public static void modelExtrudedRail(List<float[]> points, float[] railOffsets, int[] color) {

    }

    //todo: rework the method to pre-cache the vectors in an array based on the shape OR, if possible cache the end result render in a displaylist.
    public static void model3DRail(List<float[]> points, float[] railOffsets, int[] color){

        GL11.glPushMatrix();
        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glTranslated(0, 0.225, 0);

        for(float rail : railOffsets) {

            GL11.glColor4f(
                    (color[0])* 0.00392156863f,
                    (color[1])* 0.00392156863f,
                    (color[2])* 0.00392156863f, 1);
            Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);
            for (float[] p : points) {
                addVertexWithOffset(p, 0.0625f+rail, 0, 0);
                addVertexWithOffset(p, -0.0625f+rail, 0, 0);
            }
            Tessellator.getInstance().arrayEnabledDraw();


            GL11.glColor4f(
                    (color[0]-20)* 0.00392156863f,
                    (color[1]-20)* 0.00392156863f,
                    (color[2]-20)* 0.00392156863f, 1);
            Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);
            for (float[] p : points) {
                addVertexWithOffset(p, 0.0625f+rail, -0.085f, 0);
                addVertexWithOffset(p, 0.0625f+rail, 0, 0);
            }
            Tessellator.getInstance().arrayEnabledDraw();

            Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);
            for (float[] p : points) {
                addVertexWithOffset(p, -0.0625f+rail, 0, 0);
                addVertexWithOffset(p, -0.0625f+rail, -0.085f, 0);
            }
            Tessellator.getInstance().arrayEnabledDraw();


            Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);

            addVertexWithOffset(points.get(0), -0.0625f+rail, -0.085f, 0);
            addVertexWithOffset(points.get(0), -0.0625f+rail, 0, 0);
            addVertexWithOffset(points.get(0), 0.0625f+rail, -0.085f, 0);
            addVertexWithOffset(points.get(0), 0.0625f+rail, 0, 0);

            Tessellator.getInstance().arrayEnabledDraw();

            Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);

            addVertexWithOffset(points.get(points.size()-1), 0.0625f+rail, -0.0625f, 0);
            addVertexWithOffset(points.get(points.size()-1), 0.0625f+rail, 0, 0);
            addVertexWithOffset(points.get(points.size()-1), -0.0625f+rail, -0.0625f, 0);
            addVertexWithOffset(points.get(points.size()-1), -0.0625f+rail, 0, 0);

            Tessellator.getInstance().arrayEnabledDraw();
        }
        GL11.glEnable(GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }
}
