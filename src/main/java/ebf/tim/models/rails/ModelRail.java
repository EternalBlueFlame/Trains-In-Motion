package ebf.tim.models.rails;

import ebf.tim.blocks.rails.RailShapeCore;
import ebf.tim.utility.Vec5f;
import fexcraft.tmt.slim.Tessellator;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static ebf.tim.models.rails.Model1x1Rail.addVertexWithOffset;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class ModelRail {

    public static void drawFace(List<Vec5f> path, float gaugeOffset, float x1, float x2, float y1, float y2){
        Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);
        for (Vec5f p : path) {
            addVertexWithOffset(p, x1 + gaugeOffset, y1, 0);
            addVertexWithOffset(p, x2 + gaugeOffset, y2, 0);
        }
        Tessellator.getInstance().arrayEnabledDraw();
    }

    public static void centerShading(float offset, int[] color, int dark, boolean pass0){
        if(offset>0 ^ !pass0) {
            GL11.glColor4f(
                    (color[0]) * 0.00392156863f,
                    (color[1]) * 0.00392156863f,
                    (color[2]) * 0.00392156863f, 1);
        } else {
            GL11.glColor4f(
                    (color[0]-dark)* 0.00392156863f,
                    (color[1]-dark)* 0.00392156863f,
                    (color[2]-dark)* 0.00392156863f, 1);
        }
    }

    public static void modelPotatoRail(RailShapeCore shape, int[] color){
        GL11.glPushMatrix();
        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glTranslated(0, 0.15, 0);
        for(float rail : shape.getGaugePositions()) {

            centerShading(rail,color,30,true);
            drawFace(shape.activePath, rail, 0.0625f, 0,0,0);

            centerShading(rail,color,30,false);
            drawFace(shape.activePath, rail, 0, -0.0625f,0,0);

        }
        GL11.glPopMatrix();
        GL11.glEnable(GL_TEXTURE_2D);
    }

    public static void modelExtrudedRail(RailShapeCore shape, int[] color) {
        GL11.glPushMatrix();
        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glTranslated(0, 0.225, 0);

        for (float rail : shape.getGaugePositions()) {

            centerShading(rail,color,30,true);
            drawFace(shape.activePath, rail, 0.0625f, 0,0,0);

            centerShading(rail,color,30,false);
            drawFace(shape.activePath, rail, 0, -0.0625f,0,0);

            centerShading(rail,color,20,true);
            drawFace(shape.activePath, rail, 0.0625f, 0.0625f,-0.085f,0);

            centerShading(rail,color,20,false);
            drawFace(shape.activePath, rail, -0.0625f, -0.0625f,0, -0.085f);


            if(shape.ends[0]) {
                Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);

                addVertexWithOffset(shape.activePath.get(0), -0.0625f + rail, -0.085f, 0);
                addVertexWithOffset(shape.activePath.get(0), -0.0625f + rail, 0, 0);
                addVertexWithOffset(shape.activePath.get(0), 0.0625f + rail, -0.085f, 0);
                addVertexWithOffset(shape.activePath.get(0), 0.0625f + rail, 0, 0);

                Tessellator.getInstance().arrayEnabledDraw();
            }
            if(shape.ends[1]) {
                Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);

                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), 0.0625f + rail, -0.0625f, 0);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), 0.0625f + rail, 0, 0);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), -0.0625f + rail, -0.0625f, 0);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), -0.0625f + rail, 0, 0);

                Tessellator.getInstance().arrayEnabledDraw();
            }
        }
        GL11.glPopMatrix();
    }




    public static void model3DRail(RailShapeCore shape, int[] color){

        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glTranslated(0, 0.225, 0);

        for(float rail : shape.getGaugePositions()) {
            GL11.glPushMatrix();
            centerShading(rail,color,20,true);
            drawFace(shape.activePath, rail, 0.0625f, 0,0,0);
            drawFace(shape.activePath, rail, 0.0625f, 0.0625f,-0.025f,0);
            drawFace(shape.activePath, rail, 0.0625f, 0.0625f,-0.085f,-0.06f);

            centerShading(rail,color,0,true);
            drawFace(shape.activePath, rail, 0.0625f, -0.0625f,-0.06f,-0.06f);

            centerShading(rail,color,20,false);
            drawFace(shape.activePath, rail, 0, -0.0625f,0,0);
            drawFace(shape.activePath, rail, -0.0625f, -0.0625f,0, -0.025f);
            drawFace(shape.activePath, rail, -0.0625f, -0.0625f,-0.06f, -0.085f);


            GL11.glColor4f(
                    (color[0]-30)* 0.00392156863f,
                    (color[1]-30)* 0.00392156863f,
                    (color[2]-30)* 0.00392156863f, 1);
            drawFace(shape.activePath, rail, 0.03125f, 0.03125f,-0.06f,-0.025f);
            drawFace(shape.activePath, rail, -0.03125f, -0.03125f,-0.025f,-0.06f);




            GL11.glColor4f(
                    (color[0]-20)* 0.00392156863f,
                    (color[1]-20)* 0.00392156863f,
                    (color[2]-20)* 0.00392156863f, 1);
            if(shape.ends[0]) {
                Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);

                addVertexWithOffset(shape.activePath.get(0), -0.0625f + rail, -0.025f, 0);
                addVertexWithOffset(shape.activePath.get(0), -0.0625f + rail, 0, 0);
                addVertexWithOffset(shape.activePath.get(0), 0.0625f + rail, -0.025f, 0);
                addVertexWithOffset(shape.activePath.get(0), 0.0625f + rail, 0, 0);

                Tessellator.getInstance().arrayEnabledDraw();

                GL11.glTranslatef(0,-0.025f,0);

                Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);
                addVertexWithOffset(shape.activePath.get(0), -0.03125f + rail, -0.035f, 0);
                addVertexWithOffset(shape.activePath.get(0), -0.03125f + rail, 0, 0);
                addVertexWithOffset(shape.activePath.get(0), 0.03125f + rail, -0.035f, 0);
                addVertexWithOffset(shape.activePath.get(0), 0.03125f + rail, 0, 0);
                Tessellator.getInstance().arrayEnabledDraw();

                GL11.glTranslatef(0,-0.035f,0);
                Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);
                addVertexWithOffset(shape.activePath.get(0), -0.0625f + rail, -0.025f, 0);
                addVertexWithOffset(shape.activePath.get(0), -0.0625f + rail, 0, 0);
                addVertexWithOffset(shape.activePath.get(0), 0.0625f + rail, -0.025f, 0);
                addVertexWithOffset(shape.activePath.get(0), 0.0625f + rail, 0, 0);
                Tessellator.getInstance().arrayEnabledDraw();

            }
            if(shape.ends[1]) {
                Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), 0.0625f + rail, -0.025f, 0);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), 0.0625f + rail, 0, 0);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), -0.0625f + rail, -0.025f, 0);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), -0.0625f + rail, 0, 0);
                Tessellator.getInstance().arrayEnabledDraw();

                GL11.glTranslatef(0,0.035f,0);
                Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), 0.03125f + rail, -0.035f, 0);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), 0.03125f + rail, 0, 0);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), -0.03125f + rail, -0.035f, 0);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), -0.03125f + rail, 0, 0);
                Tessellator.getInstance().arrayEnabledDraw();

                GL11.glTranslatef(0,0.025f,0);
                Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), 0.0625f + rail, -0.025f, 0);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), 0.0625f + rail, 0, 0);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), -0.0625f + rail, -0.025f, 0);
                addVertexWithOffset(shape.activePath.get(shape.activePath.size() - 1), -0.0625f + rail, 0, 0);
                Tessellator.getInstance().arrayEnabledDraw();

            }
            GL11.glPopMatrix();
        }
        GL11.glEnable(GL_TEXTURE_2D);
    }
}
