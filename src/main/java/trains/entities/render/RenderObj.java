package trains.entities.render;


import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.ObjModelLoader;
import org.lwjgl.opengl.GL11;
import trains.entities.EntityTrainCore;

public class RenderObj extends Render {

    private IModelCustom model;
    private ResourceLocation texture;
    public ResourceLocation getEntityTexture(Entity entity){
        return texture;
    }

    /**
     * this class is to be removed once we get the .java models in for the trains and rollingstock.
     */
    public static void DrawCube(Entity entity, double x, double y, double z){
    	Tessellator t = Tessellator.instance;
    	        
    	//draw lines between the vertices
        t.startDrawing(GL11.GL_LINE);
        
        GL11.glVertex3d(x, y, z);
        GL11.glLineWidth(20F);
        GL11.glVertex3d(x, y, z + 1);
        GL11.glVertex3d(x + 2, y, z + 1);
        GL11.glVertex3d(x + 2, y, z);
        GL11.glVertex3d(x + 2, y + 1.5, z);
        GL11.glVertex3d(x + 2, y + 1.5, z + 1);
        GL11.glVertex3d(x, y + 1.5, z + 1);
        GL11.glVertex3d(x, y + 1.5, z);

        GL11.glColor3d(1.0, 0, 0);
        t.draw();
        
    	        
    	
    	    }
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
        
        DrawCube(entity, x, y, z); 
        //clear the cache, one pop for every push.
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        
        if (entity != null) {
            GL11.glPopMatrix();
            
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
