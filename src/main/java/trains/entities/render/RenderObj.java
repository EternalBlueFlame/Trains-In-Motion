package trains.entities.render;


import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.ObjModelLoader;
import org.lwjgl.opengl.GL11;
import trains.entities.EntityTrainCore;
import trains.utility.RailUtility;

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
            GL11.glBegin(GL11.GL_QUADS);
            x-=0.5d;
            z-=0.5d;
            //bottom
            GL11.glVertex3d(x +1, y, z);
            GL11.glVertex3d(x +1, y, z + 1);
            GL11.glVertex3d(x, y, z + 1);
            GL11.glVertex3d(x, y, z);
            //top
            y+=2;
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(x, y, z + 1);
            GL11.glVertex3d(x +1, y, z + 1);
            GL11.glVertex3d(x +1, y, z);
            //front
            y-=2;
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(x, y+2, z);
            GL11.glVertex3d(x +1, y+2, z);
            GL11.glVertex3d(x +1, y, z);
            //back
            z+=1;
            GL11.glVertex3d(x +1, y, z);
            GL11.glVertex3d(x +1, y+2, z);
            GL11.glVertex3d(x, y+2, z);
            GL11.glVertex3d(x, y, z);
            //left
            z-=1;
            GL11.glVertex3d(x, y, z+1);
            GL11.glVertex3d(x, y+2, z+1);
            GL11.glVertex3d(x, y+2, z);
            GL11.glVertex3d(x, y, z);
            //right
            x+=1;
            GL11.glVertex3d(x, y, z);
            GL11.glVertex3d(x, y+2, z);
            GL11.glVertex3d(x, y+2, z+1);
            GL11.glVertex3d(x, y, z+1);

            GL11.glColor3d(1.0, 0, 0);
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
            for (int i : ((EntityTrainCore) entity).getHitboxPositions()) {
                double[] position = rotatePoint(new double[]{i,0,0}, entity.rotationPitch, entity.rotationYaw, 0);
                DrawCube(position[0]+x, position[1]+y, position[2]+z);
            }


        }
        
        

    }
    public static double[] rotatePoint(double[] f, float pitch, float yaw, float roll) {

        float cos;
        float sin;
        double x = f[0];
        double y = f[1];
        double z = f[2];

        if (pitch != 0.0F) {
            pitch *=  RailUtility.radian;
            //fit sin and cos to their respective natural curve
            if(pitch % (Math.PI * 2) == 0){
            	cos = MathHelper.cos(pitch);
            	sin = MathHelper.sin((pitch) * -1);
            	x = (f[0] * cos) + (f[2] * sin);
                y = (f[0] * sin) - (f[2] * cos);
            } else {
            	cos = (MathHelper.cos(pitch) * -1);
            	sin = (MathHelper.sin(pitch));
            	x = (f[0] * cos) + (f[2] * sin);
                y = (f[0] * sin) - (f[2] * cos);
            }
        }

        if (yaw != 0.0F) {
            yaw *=  RailUtility.radian;
            if(yaw % (Math.PI * 2) == 0){
            	cos = MathHelper.cos(yaw);
            	sin = MathHelper.sin((yaw) * -1);
            	y = (f[0] * cos) + (f[2] * sin);
                z = (f[0] * sin) - (f[2] * cos);
            } else {
            	cos = (MathHelper.cos(yaw) * -1);
            	sin = (MathHelper.sin(yaw));
            	y = (f[0] * cos) + (f[2] * sin);
                z = (f[0] * sin) - (f[2] * cos);
            }
            
        }

        if (roll != 0.0F) {
            roll *=  RailUtility.radian;
            if(roll % (90) == 0){
            	cos = 0;
            	sin = 1;
            	if(roll % 180 == 0){
            		sin = 0;
            		cos = 1;
            		if(roll % 360 == 0){
            			sin = 0;
            			cos = 1;
            		}
            		
            	}
            	else if (roll % 270 == 0){
            		sin = -1;
            		cos = 0;
            	}
            	x = (f[0] * cos) + (f[2] * sin);
                z = (f[0] * sin) - (f[2] * cos);
            } else {
            	cos = (MathHelper.cos(roll));
            	sin = (MathHelper.sin(roll));
            	x = (f[0] * cos) - (f[2] * sin);
                z = (f[0] * sin) + (f[2] * cos);
            }
        }

        return new double[] {x, y, z};
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
