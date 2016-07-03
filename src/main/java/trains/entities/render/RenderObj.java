package trains.entities.render;


import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.ObjModelLoader;
import org.lwjgl.opengl.GL11;

public class RenderObj extends TileEntitySpecialRenderer{

    private IModelCustom model;
    private ResourceLocation texture;

    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
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
        texture = textureLoad;
    }
}
