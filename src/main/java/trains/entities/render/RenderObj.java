package trains.entities.render;


import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.ObjModelLoader;
import org.lwjgl.opengl.GL11;
import trains.TrainsInMotion;

import java.io.File;

public class RenderObj extends Render {

    private IModelCustom model;
    private ResourceLocation texture;

    public ResourceLocation getEntityTexture(Entity entity){
        return texture;
    }

    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_){
        //OpenGL stuff
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        //Bind the texture and render the model
        bindTexture(texture);
        model.renderAll();

        //OpenGL stuff to put everything back
        GL11.glPopMatrix();

    }


    public RenderObj(ResourceLocation modelLoad, ResourceLocation textureLoad) {
        model = new ObjModelLoader().loadInstance(modelLoad);
        if (new File(textureLoad.getResourcePath()).exists()) {
            texture = textureLoad;
        } else{
            texture = TrainsInMotion.Resources.TEXTURE.getResourceLocation("null.png");
        }
    }
}
