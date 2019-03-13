package ebf.tim.models.rails;

import fexcraft.tmt.slim.Tessellator;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static ebf.tim.models.rails.Model1x1Rail.addVertexWithOffsetAndUV;

public class ModelBallast {
    public static IIcon iicon;

    public static void modelPotatoBallast(List<float[]> points, float maxWidth, float minWidth, Block b, float segmentLength){
        GL11.glPushMatrix();
        GL11.glTranslated(0, 0.1, 0);

        iicon=  RenderBlocks.getInstance().getBlockIconFromSide(b, ForgeDirection.UP.ordinal());
        Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);
        float ballastloop=0;
        float d0;
        //todo loop this dependant on @dist, if it's greater than 1.75
        for (float[] p : points) {
            d0 = iicon.getMinU();
            d0+= (iicon.getMaxU()-iicon.getMinU())*(ballastloop*(1f/segmentLength));

            addVertexWithOffsetAndUV(p, 0.0625f + maxWidth, 0, 0,d0,iicon.getMinV());
            addVertexWithOffsetAndUV(p, -0.0625f + minWidth, 0, 0,d0,iicon.getMaxV());
            ballastloop++;
        }
        Tessellator.getInstance().arrayEnabledDraw();
        GL11.glPopMatrix();
    }

    public static void model3DBallast(List<float[]> points, float maxWidth, float minWidth, Block b, float segmentLength){
        modelPotatoBallast(points, maxWidth, minWidth, b, segmentLength);

        GL11.glPushMatrix();
        GL11.glTranslated(0, 0.1, 0);

        float d0;
        //west side
        iicon=  RenderBlocks.getInstance().getBlockIconFromSide(b, ForgeDirection.WEST.ordinal());
        Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);
        float ballastloop=0;
        for (float[] p : points) {
            d0 = iicon.getMinU();
            d0+= (iicon.getMaxU()-iicon.getMinU())*(ballastloop*(1f/segmentLength));

            addVertexWithOffsetAndUV(p, 0.1825f + maxWidth, -0.0625f, 0,d0,iicon.getMinV());
            addVertexWithOffsetAndUV(p, 0.0625f + maxWidth, 0, 0,d0,iicon.getMinV()+((iicon.getMaxV()-iicon.getMinV())*0.15f));
            ballastloop++;
        }
        Tessellator.getInstance().arrayEnabledDraw();

        //east side
        iicon=  RenderBlocks.getInstance().getBlockIconFromSide(b, ForgeDirection.EAST.ordinal());
        Tessellator.getInstance().startDrawing(GL11.GL_QUAD_STRIP);
        ballastloop=0;
        for (float[] p : points) {
            d0 = iicon.getMinU();
            d0+= (iicon.getMaxU()-iicon.getMinU())*(ballastloop*(1f/segmentLength));

            addVertexWithOffsetAndUV(p, -0.0625f + minWidth, 0, 0,d0,iicon.getMinV());
            addVertexWithOffsetAndUV(p, -0.1825f + minWidth, -0.0625f, 0,d0,iicon.getMinV()+((iicon.getMaxV()-iicon.getMinV())*0.15f));
            ballastloop++;
        }
        Tessellator.getInstance().arrayEnabledDraw();
        GL11.glPopMatrix();
    }
}
