package trains.entities.render;


import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.ObjModelLoader;
import org.lwjgl.opengl.GL11;
import trains.entities.EntityTrainCore;

import static trains.utility.RailUtility.rotatePoint;

public class RenderObj extends Render {

    private IModelCustom model;
    private ResourceLocation texture;
    public ResourceLocation getEntityTexture(Entity entity){
        return texture;
    }

    /**
     * <h2>Custom hitbox render</h2>
     * because we use custom bounding boxes, we have to custom render them.
     * Credit to Justice/MisterJW for getting most of this function working
     */
    private void DrawCube(double x, double y, double z){
            //draw lines between the vertices
            GL11.glPushMatrix();
            x-=0.5d;
            z-=0.5d;
            //front
        GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(x, y+2, z);
            GL11.glVertex3d(x +1, y+2, z);
            GL11.glVertex3d(x +1, y, z);
        GL11.glColor3d(0, 0, 0);
        GL11.glEnd();
            //back
            z+=1;
        GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x +1, y, z);
            GL11.glVertex3d(x +1, y+2, z);
            GL11.glVertex3d(x, y+2, z);
            GL11.glVertex3d(x, y, z);
        GL11.glColor3d(0, 0, 0);
        GL11.glEnd();
            //left
            z-=1;
        GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x, y, z+1);
            GL11.glVertex3d(x, y+2, z+1);
            GL11.glVertex3d(x, y+2, z);
            GL11.glVertex3d(x, y, z);
        GL11.glColor3d(0, 0, 0);
        GL11.glEnd();
            //right
            x+=1;
        GL11.glBegin(GL11.GL_LINE_STRIP);
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(x, y+2, z);
            GL11.glVertex3d(x, y+2, z+1);
            GL11.glVertex3d(x, y, z+1);
        GL11.glColor3d(0, 0, 0);
        GL11.glEnd();
            GL11.glPopMatrix();
    }

    /**
     * this class is to be removed once we get the .java models in for the trains and rollingstock.
     */
    @Deprecated
    public void doRender(Entity entity, double x, double y, double z, float rotation, float partialTick){
        //begin rendering
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glPushMatrix();
        //set the position of the graphic and save changes
        //first initialize the variables
        if (entity != null) {
            GL11.glRotatef(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
            GL11.glPushMatrix();
            GL11.glLineWidth(5F);
        }
        //Bind the texture and render the model
        bindTexture(texture);
        model.renderAll();

        GL11.glPushMatrix();

        //clear the cache, one pop for every push.
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        
        if (entity != null) {
            GL11.glPopMatrix();
            
        }

        //because we use custom bounding boxes, we have to custom render them.
        if (RenderManager.debugBoundingBox && entity instanceof EntityTrainCore) {
            double[] position;
            for (int i : ((EntityTrainCore) entity).getHitboxPositions()) {
                position = rotatePoint(new double[]{i,0,0}, entity.rotationPitch, entity.rotationYaw, 0);
               //DrawCube(position[0]+x, position[1]+y, position[2]+z);
            }


        }
        
        

    }


    /**
     * initializer for the render function.
     * This class is to be removed once we get the .java models in for the trains and rollingstock.
     * @param modelLoad resource location for model
     * @param textureLoad resource location for texture, if invalid defaults to the null texture.
     */
    @Deprecated
    public RenderObj(ResourceLocation modelLoad, ResourceLocation textureLoad) {
        model = new ObjModelLoader().loadInstance(modelLoad);
        texture = textureLoad;
    }



}
