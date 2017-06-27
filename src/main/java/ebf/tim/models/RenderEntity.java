package ebf.tim.models;

import ebf.tim.entities.GenericRailTransport;
import ebf.tim.models.tmt.ModelRendererTurbo;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.RailUtility;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Entity Rendering</h2>
 * used for rendering all trains and rollingstock, along with their particle effects, smoke and steam as examples.
 * for the variables fed to this class:
 * @see ClientProxy#register()
 * @author Eternal Blue Flame
 */
public class RenderEntity extends Render {

    /*
     * <h2>variables</h2>
     */

    /**the base model of the entity, this holds every piece of sub-geometry and their texture mappings*/
    private final ModelBase model;
    /**texture used by the base model*/
    private final ResourceLocation texture;
    /**the models, textures, and other data for each bogie to render*/
    private Bogie[] bogieRenders;
    /**a cached list of all the animatedPart, and other geometry that just spins.
     * all the actual work related to this variable is handled in
     * @see StaticModelAnimator*/
    private List<StaticModelAnimator> animatedPart = new ArrayList<StaticModelAnimator>();
    /**a cached list of all the cargo blocks for both blocks and parts of the main model.
     * most of the actual work related to this variable is handled in
     * @see GroupedModelRender*/
    private List<GroupedModelRender> blockCargoRenders = new ArrayList<GroupedModelRender>();
    /**a cached list of all the vectors used, so we don't have to re-initialize them every frame.*/
    private double[][] animationCache = new double[4][3];
    /**a cached list of all the cubes intended to display liveries.*/
    private List<ModelRendererTurbo> liveriesSquare = new ArrayList<ModelRendererTurbo>();
    /**the value to rotate the geometry with.*/
    private float wheelPitch=0;

    private ResourceLocation boundTexture;

    private static final float RailOffset = 0.34f;

    /**
     * <h3>class constructor</h3>
     * instances the render, and caches the geometry tagged for runtime modification (like animation and block renders)
     * caching here means we don't even need to check it every render frame.
     * @param modelLoad the model class to render.
     * @param textureLoad the texture ResourceLocation to apply to the model.
     * @param bogieRenders the model and texture class to render for the bogies, if null then no bogies will not be rendered. null indexes are allowed as well.
     */
    public RenderEntity(ModelBase modelLoad, ResourceLocation textureLoad, @Nullable Bogie[] bogieRenders) {
        //define the models and textures.
        model = modelLoad;
        texture = textureLoad;
        this.bogieRenders = bogieRenders;

        if (ClientProxy.EnableAnimations){
            boolean isAdded;
            for (Object box : model.boxList) {
                if (box instanceof ModelRendererTurbo) {
                    ModelRendererTurbo render = ((ModelRendererTurbo) box);
                    //attempt to cache the parts for the main transport model
                    if (StaticModelAnimator.canAdd(render)) {
                        animatedPart.add(new StaticModelAnimator(render));
                    } else if (GroupedModelRender.canAdd(render)) {
                        //if it's a grouped render we have to figure out if we already have a group for this or not.
                        isAdded =false;
                        for (GroupedModelRender cargo : blockCargoRenders) {
                            if (cargo.getGroupName().equals(render.boxName)){
                                cargo.add(render, GroupedModelRender.isBlock(render), GroupedModelRender.isScaled(render));
                                isAdded= true;break;
                            }
                        }
                        if (!isAdded){
                            blockCargoRenders.add(new GroupedModelRender().add(render, GroupedModelRender.isBlock(render), GroupedModelRender.isScaled(render)));
                        }
                    }
                }
            }


            //cache the animating parts for bogies.
            if (bogieRenders != null){
                for (Bogie bogie : bogieRenders){
                    for (Object box : bogie.bogieModel.boxList) {
                        if (box instanceof ModelRendererTurbo) {
                            animatedPart.add(new StaticModelAnimator(((ModelRendererTurbo) box)));
                        }
                    }
                }
            }
        }
    }

    /**
     * <h3>overall texture</h3>
     * returns the texture for this entity, required by the super, we use it so we have access to the texture from outside this class, for example
     * @see GroupedModelRender#doRender(RenderBlocks, ItemStack, RenderEntity, float, GenericRailTransport)
     */
    public ResourceLocation getEntityTexture(Entity entity){
        return texture;
    }

    /**
     * <h3>base render extension</h3>
     * acts as a redirect for the base render function to our own function.
     * This is just to do typecasting and a few calculations beforehand so we only need to do them once per render.
     */
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick){
        if (entity instanceof GenericRailTransport){

            doRender((GenericRailTransport) entity,x,y,z, entity.prevRotationYaw + MathHelper.wrapAngleTo180_float(entity.rotationYaw - entity.prevRotationYaw)*partialTick);
        }
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
     * @param yaw is used to rotate the train's yaw, its exactly the same as entity.rotationYaw.
     *
     * TODO: texture recolor similar to game maker's colorize partial so we can change the color without effecting grayscale, or lighting like on rivets.
     *                    May be able to use multiple instances of this to have multiple recolorable things on the train.
     *
     */
    public void doRender(GenericRailTransport entity, double x, double y, double z, float yaw){
        GL11.glPushMatrix();
        //set the render position
        GL11.glTranslated(x, y+ RailOffset + (entity.getRenderScale()-0.0625f)*10, z);
        //rotate the model.
        GL11.glRotatef(-yaw, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entity.rotationPitch-180f, 1.0f, 0.0f, 0.0f);

        /*
         * <h3>animations</h3>
         * Be sure animations are enabled in user settings, then check of there is something to animate.
         * if there is, then calculate the vectors and apply the animations
         */
        if (ClientProxy.EnableAnimations && entity.frontBogie!=null&&entity.backBogie!=null) {
            //define the rotation angle
            wheelPitch += (float)(entity.backBogie.motionX * entity.backBogie.motionX + entity.backBogie.motionZ * entity.backBogie.motionZ)*20;
            if (wheelPitch > 360 || wheelPitch <-360) {
                wheelPitch = 0;
            }
            if (wheelPitch !=0) {
                //if it's actually moving, then define the new position
                animationCache[1][0] = entity.getPistonOffset() * RailUtility.radianF;
                animationCache[0] = RailUtility.rotatePoint(animationCache[1], Math.copySign(wheelPitch, 1), Math.copySign(wheelPitch, 1), 0);
                //animate the tagged parts
                for (StaticModelAnimator partToAnimate : animatedPart) {
                    partToAnimate.Animate(wheelPitch, animationCache[0]);
                }
            }
        }

        /*
         * <h3>Render geometry</h3>
         * Be sure the bound texture reference is null then Bind the texture. After that render any geometry that is supposed to use the default texture.
         * if there are any cargo blocks to render, then render them dependant on if there is enough stuff in the inventory to merit it.
         * In that render there is a check whether to render it as a cargo block, or use the geometry size/position/rotation to render a block similar to enderman.
         * @see net.minecraft.client.renderer.entity.RenderEnderman#renderEquippedItems(EntityEnderman, float)
         */
        boundTexture = null;
        bindTexture(texture);
        for(Object cube : model.boxList){
            if (cube instanceof ModelRendererTurbo && !(GroupedModelRender.canAdd((ModelRendererTurbo) cube))) {
                ((ModelRendererTurbo)cube).render();
            }
        }


        int itteration=0;
        //loop for the groups of cargo
        for (GroupedModelRender cargo : blockCargoRenders) {

            if (itteration<entity.calculatePercentageUsed(blockCargoRenders.size())) {
                cargo.doRender(field_147909_c, entity.getFirstBlock(itteration), this, entity.getRenderScale(), entity);
                itteration++;
            } else {
                break;
            }
        }

        GL11.glPopMatrix();

        /*
         * <h4> render bogies</h4>
         * in TiM here we render the bogies. This will be removed in TC.
         * this loops for every bogie defined in the registry for the transport, that way we can have different bogies.
         */
        if (bogieRenders != null && bogieRenders.length >0){
            for (int i = 0; i<entity.getRenderBogieOffsets().size(); i++){
                if (bogieRenders.length>i && bogieRenders[i] != null) {
                    //bind the texture
                    bindTexture(bogieRenders[i].bogieTexture);
                    GL11.glPushMatrix();
                    //set the offset
                    animationCache[2][0]=entity.getRenderBogieOffsets().get(i) + Math.copySign((entity.getRenderScale()-0.0625f)*26, entity.getRenderBogieOffsets().get(i));
                    animationCache[2][1] = RailOffset;
                    animationCache[3] = RailUtility.rotatePoint(animationCache[2], entity.rotationPitch, entity.rotationYaw,0);
                    GL11.glTranslated(animationCache[3][0]+x,animationCache[3][1]+y, animationCache[3][2]+z);
                    bogieRenders[i].setPositionAndRotation(entity, entity.getRenderBogieOffsets().get(i));
                    //set the rotation
                    GL11.glRotatef(-bogieRenders[i].rotationYaw,0,1.0f,0);
                    GL11.glRotatef(entity.rotationPitch - 180f, 1.0f, 0.0f, 0.0f);
                    //render the geometry
                    for (Object modelBogiePart : bogieRenders[i].bogieModel.boxList) {
                        if (modelBogiePart instanceof ModelRendererTurbo) {
                            ((ModelRendererTurbo) modelBogiePart).render();
                        }
                    }
                    GL11.glPopMatrix();
                }
            }
        }


        //render the particles, if there are any.
        for(ParticleFX particle : entity.particles){
            ParticleFX.doRender(particle, x,y,z);
        }
    }

    /**
     * <h2>Bind texture override</h2>
     * binds the texture to the render, assuming it's not already bound.
     * NOTE: be sure to reset the boundTexture value on the start of doRender, otherwise it will carry over from the last loop when no texture is actually bound.
     */
    @Override
    public void bindTexture(ResourceLocation resourceLocation){
        if(boundTexture == null || resourceLocation != boundTexture){
            this.renderManager.renderEngine.bindTexture(resourceLocation);
            boundTexture = resourceLocation;
        }
    }
}