package ebf.tim.models.rails;

import ebf.tim.blocks.rails.RailShapeCore;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.DebugUtil;
import ebf.tim.utility.Vec5f;
import fexcraft.tmt.slim.Tessellator;
import fexcraft.tmt.slim.TextureManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.Map;

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

    public static void addVertexWithOffset(Vec5f p, float width, float height, float depth){
        rotateVertexPoint(depth,height,width,p.u,p.v);
        Tessellator.getInstance().addVertexWithUV(vert[0]+p.xCoord,vert[1]+p.yCoord, vert[2]+p.zCoord,0,0);
    }

    public static void addVertexWithOffsetAndUV(Vec5f p, float width, float height, float depth, float U, float V){
        rotateVertexPoint(depth,height,width,p.u,p.v);
        Tessellator.getInstance().addVertexWithUV(vert[0]+p.xCoord,vert[1]+p.yCoord, vert[2]+p.zCoord,U,V);
    }


    //todo use the return value to manage displaylists
    public static void Model3DRail(World world, int xPos, int yPos, int zPos, RailShapeCore shape, @Nullable ItemStack ballast, @Nullable ItemStack ties, @Nullable ItemStack rail){
        if(shape.gauge==null || shape.activePath ==null || rail==null){
            return;
        }


        float minWidth=0, maxWidth=0;
        if(shape.gauge.length>1) {
            for (float offset : shape.getGaugePositions()) {
                DebugUtil.println(offset);
                //might as well do this here since we gotta loop it anyway, and only need to do it for the first, not like it changes later.
                if (offset < minWidth) {
                    minWidth = offset;
                }
                if (offset > maxWidth) {
                    maxWidth = offset;
                }
            }
        } else{
            minWidth=shape.getGaugePositions()[0];maxWidth=-shape.getGaugePositions()[0];
        }
        //DebugUtil.println(minWidth,maxWidth);


        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);

        Minecraft.getMinecraft().entityRenderer.enableLightmap(1);
        TextureManager.adjustLightFixture(world,xPos,yPos,zPos);
        //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        int[] railColor = new int[]{0,0,0};
        for (Map.Entry<ItemStack, int[]> e : TextureManager.ingotColors.entrySet()) {
            if (e.getKey().getItem() == rail.getItem() &&
                    e.getKey().getTagCompound() == rail.getTagCompound() &&
                    e.getKey().getItemDamage() == rail.getItemDamage()) {
                railColor = TextureManager.ingotColors.get(e.getKey());
            }
        }
        //DebugUtil.println(ClientProxy.railLoD);
        //renders the rails, also defines min and max width
        switch (ClientProxy.railLoD){
            case 0:{ModelRail.modelPotatoRail(shape, railColor); break;}
            case 1:{ModelRail.model3DRail(shape, railColor); break;}
            case 2://todo normal rail
            case 3:{ModelRail.model3DRail(shape, railColor); break;}//todo HD rail
        }
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(1.0f,1.0f,1.0f,1.0f);
        Tessellator.bindTexture(TextureMap.locationBlocksTexture);
        // clear the display buffer to the clear colour
        //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        if(ties!=null && ties.getItem()!=null) {
            if(ClientProxy.railLoD==0){
                ModelTies.modelPotatoTies(shape, maxWidth, minWidth, ties);
            } else if (ClientProxy.railLoD<3){
                ModelTies.model3DTies(shape, maxWidth, minWidth, ties);
            } else {
                //todo: HD ties
                ModelTies.model3DTies(shape, maxWidth, minWidth, ties);
            }

        }
        if(ballast!=null && ballast.getItem()!=null) {
            if(ClientProxy.railLoD==0){
                ModelBallast.modelPotatoBallast(shape, maxWidth, minWidth, ballast);
            } else {
                ModelBallast.model3DBallast(shape, maxWidth, minWidth, ballast);
            }
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);

    }
}
