package ebf.tim.models.rails;

import ebf.tim.blocks.rails.RailShapeCore;
import ebf.tim.utility.Vec5f;
import fexcraft.tmt.slim.Tessellator;
import fexcraft.tmt.slim.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import static ebf.tim.models.rails.Model1x1Rail.addVertexWithOffsetAndUV;

public class ModelTies {


    public static IIcon iicon;
    public static float d0, d1;

    public static void modelPotatoTies(RailShapeCore shape, float maxWidth, float minWidth, ItemStack block){
        GL11.glPushMatrix();
        GL11.glTranslated(0, 0.125, 0);
        //top side
        iicon=  TextureManager.bindBlockTextureFromSide(ForgeDirection.WEST.ordinal(), block);
        boolean first = true;
        for (Vec5f p :shape.activeTiePath) {
            if(first){
                first=false;
                continue;
            }

            d0 = iicon.getMinU()+ ((iicon.getMaxU()-iicon.getMinU())*0.44f);
            d1 = d0+ ((iicon.getMaxU()-iicon.getMinU())*0.09f);
            GL11.glPushMatrix();
            GL11.glRotatef(p.u,1,0,0);
            Tessellator.getInstance().startDrawing(GL11.GL_QUADS);

            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, 0, 0.0625f,d0, iicon.getMinV());
            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, 0, 0.1875f,d1, iicon.getMinV());

            addVertexWithOffsetAndUV(p, -0.125f + minWidth, 0, 0.1875f,d1,iicon.getMaxV());
            addVertexWithOffsetAndUV(p, -0.125f + minWidth, 0, 0.0625f,d0, iicon.getMaxV());
            Tessellator.getInstance().draw();
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }


    public static void model3DTies(RailShapeCore shape, float maxWidth, float minWidth, ItemStack block){

        modelPotatoTies(shape, maxWidth, minWidth, block);

        GL11.glPushMatrix();
        GL11.glTranslated(0, 0.125, 0);
        iicon=  TextureManager.bindBlockTextureFromSide(ForgeDirection.NORTH.ordinal(),block);
        boolean first = true;
        for (Vec5f p :shape.activeTiePath) {
            if(first){
                first=false;
                continue;
            }


            d1 = iicon.getMinU()+ ((iicon.getMaxU()-iicon.getMinU())*0.09f);
            GL11.glPushMatrix();
            GL11.glRotatef(p.u,1,0,0);
            Tessellator.getInstance().startDrawing(GL11.GL_QUADS);

            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, 0, 0.1875f,iicon.getMinU(),iicon.getMinV());
            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, -0.125f, 0.1875f,d1,iicon.getMinV());

            addVertexWithOffsetAndUV(p, -0.125f + minWidth, -0.125f, 0.1875f,iicon.getMinU(),iicon.getMaxV());
            addVertexWithOffsetAndUV(p, -0.125f + minWidth, 0, 0.1875f,d1,iicon.getMaxV());

            Tessellator.getInstance().draw();
            GL11.glPopMatrix();
        }

        iicon=  TextureManager.bindBlockTextureFromSide(ForgeDirection.SOUTH.ordinal(), block);
        first = true;
        for (Vec5f p :shape.activeTiePath) {
            if(first){
                first=false;
                continue;
            }

            d1 = iicon.getMinU()+ ((iicon.getMaxU()-iicon.getMinU())*0.09f);
            GL11.glPushMatrix();
            GL11.glRotatef(p.u,1,0,0);
            Tessellator.getInstance().startDrawing(GL11.GL_QUADS);


            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, -0.125f, 0.0625f,iicon.getMinU(),iicon.getMinV());
            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, 0, 0.0625f,d1,iicon.getMinV());

            addVertexWithOffsetAndUV(p, -0.125f + minWidth, 0, 0.0625f,d1,iicon.getMaxV());
            addVertexWithOffsetAndUV(p, -0.125f + minWidth, -0.125f, 0.0625f,iicon.getMinU(),iicon.getMaxV());
            Tessellator.getInstance().draw();
            GL11.glPopMatrix();
        }


        iicon=  TextureManager.bindBlockTextureFromSide(ForgeDirection.UP.ordinal(), block);
        first = true;
        for (Vec5f p :shape.activeTiePath) {
            if(first){
                first=false;
                continue;
            }

            GL11.glPushMatrix();
            GL11.glRotatef(p.u,1,0,0);
            Tessellator.getInstance().startDrawing(GL11.GL_QUADS);

            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, -0.125f, 0.0625f,iicon.getMinU(),iicon.getMinV());
            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, -0.125f, 0.1875f,iicon.getMinU(),iicon.getMaxV());

            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, -0, 0.1875f,iicon.getMaxU(),iicon.getMaxV());
            addVertexWithOffsetAndUV(p, 0.125f + maxWidth, -0, 0.0625f,iicon.getMaxU(),iicon.getMinV());
            Tessellator.getInstance().draw();
            GL11.glPopMatrix();
        }

        iicon=  TextureManager.bindBlockTextureFromSide(ForgeDirection.DOWN.ordinal(), block);
        first = true;
        for (Vec5f p :shape.activeTiePath) {
            if(first){
                first=false;
                continue;
            }


            GL11.glPushMatrix();
            GL11.glRotatef(p.u,1,0,0);
            Tessellator.getInstance().startDrawing(GL11.GL_QUADS);

            addVertexWithOffsetAndUV(p, -0.125f + minWidth, -0.125f, 0.1875f,iicon.getMinU(),iicon.getMinV());
            addVertexWithOffsetAndUV(p, -0.125f + minWidth, -0.125f, 0.0625f,iicon.getMinU(),iicon.getMaxV());

            addVertexWithOffsetAndUV(p, -0.125f + minWidth, -0, 0.0625f,iicon.getMaxU(),iicon.getMaxV());
            addVertexWithOffsetAndUV(p, -0.125f + minWidth, -0, 0.1875f,iicon.getMaxU(),iicon.getMinV());
            Tessellator.getInstance().draw();
            GL11.glPopMatrix();
        }


        GL11.glPopMatrix();


    }

    public void modelHDTies(RailShapeCore shape, float maxWidth, float minWidth, ItemStack block){
        model3DTies(shape, maxWidth, minWidth, block);
        //todo HD ties should all the nails
    }
}
