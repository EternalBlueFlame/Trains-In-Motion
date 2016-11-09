package trains.entities.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import trains.utility.ClientProxy;

import javax.annotation.Nullable;

/**
 * <h2> .Java Entity Rendering</h2>
 * this is planned to replace:
 * @see RenderObj
 * to be used for rendering all trains and rollingstock, along with their particle effects.
 * for the variables fed to this class:
 * @see ClientProxy#registerRenderers()
 */
public class RenderEntity extends Render {

    private ModelBase model;
    private ModelBase bogieModel;
    private ResourceLocation texture;
    private ResourceLocation bogieTexture;
    private char smokeType = 'n';

    /**
     * <h3>class constructor</h3>
     * @param modelLoad the model class to render.
     * @param textureLoad the texture ResourceLocation to apply to the model.
     * @param modelBogie the model to render for the bogies, if null then no bogies will not be rendered.
     * @param bogieTexture the texture to apply to the bogie, can be null if the bogie model is null.
     * @param particleEffect the type of particle effect to render.
     *                       n - null
     *                       s - Smoke (steam)
     *                       d - Smoke (diesel)
     *                       r - Steam (nuclear steam)
     */
    public RenderEntity(ModelBase modelLoad, ResourceLocation textureLoad, @Nullable ModelBase modelBogie, @Nullable ResourceLocation bogieTexture, char particleEffect) {
        model = modelLoad;
        texture = textureLoad;
        bogieModel = modelBogie;
        this.bogieTexture = bogieTexture;
        smokeType = particleEffect;
    }

    public ResourceLocation getEntityTexture(Entity entity){
        return texture;
    }


    /**
     * <h3>Actually render</h3>
     *
     * here we define position, rotation, pitch, bind the texture, render the model, and then manage smoke.
     *
     * Most all of this is pretty self-explanatory by function name, except for:
     *      glPushMatrix - this basically allocates room that we want to do something, like the GL equivalent of a starting bracket.
     *      glPopMatrix - this basically tells GL to do what we just allocated, like the GL equivalent of an ending bracket.
     *
     * @param entity the entity to render.
     * @param x the x position of the entity with offset for the camera position.
     * @param y the y position of the entity with offset for the camera position.
     * @param z the z position of the entity with offset for the camera position.
     * @param rotation not used.
     * @param partialTick not used.
     */
    public void doRender(Entity entity, double x, double y, double z, float rotation, float partialTick){
        GL11.glPushMatrix();
        //set the render position
        GL11.glTranslated(x, y, z);
        GL11.glPushMatrix();
        //rotate the model.
        GL11.glRotatef(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.rotationPitch, 0.0F, 0.0F, 1.0F);
        GL11.glPushMatrix();
        //Bind the texture and render the model
        bindTexture(texture);
        model.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.06f);
        //clear the cache, one pop for every push.
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        GL11.glPopMatrix();

        /**
         * <h4> render bogies</h4>
         * in TiM here we render the bogies.
         * we get the entity and do an instanceof check, see if it's a train or a rollingstock, then do a for loop on the bogies, and render them the same way we do trains and rollingstock.
         */


        /**
         * <h4>Smoke management</h4>
         * some trains, and even rollingstock will have smoke,
         */
        switch (smokeType){
            case 'n':{break;}
            //TODO
        }

    }

}