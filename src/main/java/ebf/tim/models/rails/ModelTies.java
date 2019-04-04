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

public class ModelTies {

    public static IIcon iicon;

    public static void modelPotatoTies(List<float[]> points, float maxWidth, float minWidth, Block block){
        GL11.glPushMatrix();
        GL11.glTranslated(0, 0.125, 0);
        //top side
        IIcon iicon=  RenderBlocks.getInstance().getBlockIconFromSide(block, ForgeDirection.WEST.ordinal());
        boolean first = true;
        for (float[] p :points) {
            if(first){
                first=false;
                continue;
            }

            Tessellator.getInstance().startDrawing(GL11.GL_QUADS);
            float d0 = iicon.getMinU()+ ((iicon.getMaxU()-iicon.getMinU())*0.44f);
            float d1 = d0+ ((iicon.getMaxU()-iicon.getMinU())*0.09f);

            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, 0, -0.0625f,d0, iicon.getMinV());
            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, 0, 0.0625f,d1, iicon.getMinV());

            addVertexWithOffsetAndUV(p, -0.125f + minWidth, 0, 0.0625f,d1,iicon.getMaxV());
            addVertexWithOffsetAndUV(p, -0.125f + minWidth, 0, -0.0625f,d0, iicon.getMaxV());
            Tessellator.getInstance().arrayEnabledDraw();
        }
        GL11.glPopMatrix();
    }


    public static void model3DTies(List<float[]> points, float maxWidth, float minWidth, Block block){

        modelPotatoTies(points, maxWidth, minWidth, block);

        GL11.glPushMatrix();
        GL11.glTranslated(0, 0.125, 0);
        iicon=  RenderBlocks.getInstance().getBlockIconFromSide(block, ForgeDirection.NORTH.ordinal());
        boolean first = true;
        for (float[] p :points) {
            if(first){
                first=false;
                continue;
            }


            Tessellator.getInstance().startDrawing(GL11.GL_QUADS);
            float d1 = iicon.getMinU()+ ((iicon.getMaxU()-iicon.getMinU())*0.09f);

            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, 0, 0.0625f,iicon.getMinU(),iicon.getMinV());
            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, -0.125f, 0.0625f,d1,iicon.getMinV());

            addVertexWithOffsetAndUV(p, -0.125f + minWidth, -0.125f, 0.0625f,iicon.getMinU(),iicon.getMaxV());
            addVertexWithOffsetAndUV(p, -0.125f + minWidth, 0, 0.0625f,d1,iicon.getMaxV());

            Tessellator.getInstance().arrayEnabledDraw();
        }

        iicon=  RenderBlocks.getInstance().getBlockIconFromSide(block, ForgeDirection.SOUTH.ordinal());
        first = true;
        for (float[] p :points) {
            if(first){
                first=false;
                continue;
            }

            Tessellator.getInstance().startDrawing(GL11.GL_QUADS);
            float d1 = iicon.getMinU()+ ((iicon.getMaxU()-iicon.getMinU())*0.09f);

            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, -0.125f, -0.0625f,iicon.getMinU(),iicon.getMinV());
            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, 0, -0.0625f,d1,iicon.getMinV());

            addVertexWithOffsetAndUV(p, -0.125f + minWidth, 0, -0.0625f,d1,iicon.getMaxV());
            addVertexWithOffsetAndUV(p, -0.125f + minWidth, -0.125f, -0.0625f,iicon.getMinU(),iicon.getMaxV());
            Tessellator.getInstance().arrayEnabledDraw();
        }


        iicon=  RenderBlocks.getInstance().getBlockIconFromSide(block, ForgeDirection.UP.ordinal());
        first = true;
        for (float[] p :points) {
            if(first){
                first=false;
                continue;
            }

            Tessellator.getInstance().startDrawing(GL11.GL_QUADS);

            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, -0.125f, -0.0625f,iicon.getMinU(),iicon.getMinV());
            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, -0.125f, 0.0625f,iicon.getMinU(),iicon.getMaxV());

            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, -0, 0.0625f,iicon.getMaxU(),iicon.getMaxV());
            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, -0, -0.0625f,iicon.getMaxU(),iicon.getMinV());
            Tessellator.getInstance().arrayEnabledDraw();
        }

        iicon=  RenderBlocks.getInstance().getBlockIconFromSide(block, ForgeDirection.DOWN.ordinal());
        first = true;
        for (float[] p :points) {
            if(first){
                first=false;
                continue;
            }

            Tessellator.getInstance().startDrawing(GL11.GL_QUADS);

            addVertexWithOffsetAndUV(p, -0.125f + minWidth, -0.125f, 0.0625f,iicon.getMinU(),iicon.getMinV());
            addVertexWithOffsetAndUV(p, -0.125f + minWidth, -0.125f, -0.0625f,iicon.getMinU(),iicon.getMaxV());

            addVertexWithOffsetAndUV(p, -0.125f + minWidth, -0, -0.0625f,iicon.getMaxU(),iicon.getMaxV());
            addVertexWithOffsetAndUV(p, -0.125f + minWidth, -0, 0.0625f,iicon.getMaxU(),iicon.getMinV());
            Tessellator.getInstance().arrayEnabledDraw();
        }


        GL11.glPopMatrix();


    }

    public void modelHDTies(List<float[]> points, float maxWidth, float minWidth, Block block){
        model3DTies(points, maxWidth, minWidth, block);
        //todo HD ties should all the nails
    }
}
