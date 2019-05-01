package ebf.tim.models.rails;

import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.DebugUtil;
import fexcraft.tmt.slim.Tessellator;
import fexcraft.tmt.slim.TextureManager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import javax.annotation.Nullable;
import java.util.List;

import static ebf.tim.utility.RailUtility.radianF;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;

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
        Tessellator.getInstance().addVertexWithUV(vert[0]+p[0],vert[1]+p[1], vert[2]+p[2],0,0);
    }

    public static void addVertexWithOffsetAndUV(float[] p, float width, float height, float depth, float U, float V){
        rotateVertexPoint(depth,height,width,p[3],p[4]);
        Tessellator.getInstance().addVertexWithUV(vert[0]+p[0],vert[1]+p[1], vert[2]+p[2],U,V);
    }


    //todo use the return value to manage displaylists
    public static void Model3DRail(World world, int xPos, int yPos, int zPos, List<float[]> points, float[] railOffsets, float segmentLength, @Nullable ItemStack ballast, @Nullable ItemStack ties, int[] railColor){
        if(railOffsets==null || points ==null){
            return;
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


            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LIGHTING);

            Minecraft.getMinecraft().entityRenderer.enableLightmap(1);
            TextureManager.adjustLightFixture(world,xPos,yPos,zPos);
            //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            //DebugUtil.println(ClientProxy.railLoD);
            //renders the rails, also defines min and max width
            switch (ClientProxy.railLoD){
                case 0:{ModelRail.modelPotatoRail(points, railOffsets, railColor); break;}
                case 1:{ModelRail.model3DRail(points, railOffsets, railColor); break;}
                case 2://todo normal rail
                case 3:{ModelRail.model3DRail(points, railOffsets, railColor); break;}//todo HD rail
            }
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(1.0f,1.0f,1.0f,1.0f);
            Tessellator.bindTexture(TextureMap.locationBlocksTexture);
            // clear the display buffer to the clear colour
            //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            if(ties!=null && ties.getItem()!=null) {
                if(ClientProxy.railLoD==0){
                    ModelTies.modelPotatoTies(points, maxWidth, minWidth, ties);
                } else if (ClientProxy.railLoD<3){
                    ModelTies.model3DTies(points, maxWidth, minWidth, ties);
                } else {
                    //todo: HD ties
                    ModelTies.model3DTies(points, maxWidth, minWidth, ties);
                }

            }
            if(ballast!=null && ballast.getItem()!=null) {
                if(ClientProxy.railLoD==0){
                    ModelBallast.modelPotatoBallast(points, maxWidth, minWidth, ballast, segmentLength);
                } else {
                    ModelBallast.model3DBallast(points, maxWidth, minWidth, ballast, segmentLength);
                }
            }

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);

        }
    }
}
