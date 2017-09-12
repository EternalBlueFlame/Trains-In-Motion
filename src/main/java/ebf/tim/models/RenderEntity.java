package ebf.tim.models;

import ebf.tim.entities.GenericRailTransport;
import ebf.tim.models.tmt.ModelRendererTurbo;
import ebf.tim.models.tmt.Tessellator;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.RailUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * <h2>Entity Rendering</h2>
 * used for rendering all trains and rollingstock, along with their particle effects, smoke and steam as examples.
 * all the variables have to be stored outside this class because it's assigned to the entity class, not it's instances.
 * @author Eternal Blue Flame
 */
public class RenderEntity extends Render {

    private static final float RailOffset = 0.34f;

    //public RenderEntity() {}

    /**
     * <h3>overall texture</h3>
     * returns the texture for this entity, required by the super, we use it so we have access to the texture from outside this class, for example
     * @see GroupedModelRender#doRender(RenderBlocks, ItemStack, RenderEntity, float, GenericRailTransport)
     */
    public ResourceLocation getEntityTexture(Entity entity){
        return null;
    }

    /**
     * <h3>base render extension</h3>
     * acts as a redirect for the base render function to our own function.
     * This is just to do typecasting and a few calculations beforehand so we only need to do them once per render.
     * todo: 1.9+ should support Entity<t extends GenericRailTransport> so this typecasting method should be completely useless then.
     */
    @Override
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

        if (entity.renderData.model == null) {
            entity.renderData = new TransportRenderData();
            entity.renderData.model = entity.getModel();
            entity.renderData.bogieRenders = entity.getBogieModels();
            if (entity.renderData.bogieRenders != null) {
                for (Bogie b : entity.renderData.bogieRenders) {
                    b.rotationYaw = entity.rotationYaw;
                }
            }

            //cache animating parts
            if (ClientProxy.EnableAnimations) {
                boolean isAdded;
                for (Object box : entity.renderData.model.boxList) {
                    if (box instanceof ModelRendererTurbo) {
                        ModelRendererTurbo render = ((ModelRendererTurbo) box);
                        //attempt to cache the parts for the main transport model
                        if (StaticModelAnimator.canAdd(render)) {
                            entity.renderData.animatedPart.add(new StaticModelAnimator(render));
                        } else if (GroupedModelRender.canAdd(render)) {
                            //if it's a grouped render we have to figure out if we already have a group for this or not.
                            isAdded = false;
                            for (GroupedModelRender cargo : entity.renderData.blockCargoRenders) {
                                if (cargo.getGroupName().equals(render.boxName)) {
                                    cargo.add(render, GroupedModelRender.isBlock(render), GroupedModelRender.isScaled(render));
                                    isAdded = true;
                                    break;
                                }
                            }
                            if (!isAdded) {
                                entity.renderData.blockCargoRenders.add(new GroupedModelRender().add(render, GroupedModelRender.isBlock(render), GroupedModelRender.isScaled(render)));
                            }
                        }
                    }
                }
                //cache the animating parts for bogies.
                if (entity.getBogieModels() != null) {
                    for (Bogie bogie : entity.getBogieModels()) {
                        for (Object box : bogie.bogieModel.boxList) {
                            if (box instanceof ModelRendererTurbo) {
                                entity.renderData.animatedPart.add(new StaticModelAnimator(((ModelRendererTurbo) box)));
                            }
                        }
                    }
                }
            }
        }





        GL11.glPushMatrix();
        //set the render position
        GL11.glTranslated(x, y+ RailOffset + (entity.getRenderScale()-0.0625f)*10, z);
        //rotate the model.
        GL11.glRotatef(-yaw - 180f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entity.rotationPitch - 180f, 0.0f, 0.0f, 1.0f);

        /*
         * <h3>animations</h3>
         * Be sure animations are enabled in user settings, then check of there is something to animate.
         * if there is, then calculate the vectors and apply the animations
         */
        if (!Minecraft.getMinecraft().isGamePaused() &&ClientProxy.EnableAnimations && entity.backBogie!=null &&
                (entity.backBogie.motionX > 0.1 || entity.backBogie.motionX < -0.1 || entity.backBogie.motionZ > 0.1 || entity.backBogie.motionZ < -0.1)) {
            if (entity.renderData.wheelPitch >= 6.2831855f || entity.renderData.wheelPitch <=-6.2831855f) {
                entity.renderData.wheelPitch -= Math.copySign(6.2831855f, entity.renderData.wheelPitch);
            }
            //define the rotation angle, if it's going fast enough.
            entity.renderData.wheelPitch += -((Math.sqrt(entity.backBogie.motionX * entity.backBogie.motionX) + Math.sqrt(entity.backBogie.motionZ * entity.backBogie.motionZ))*0.08f);

            if (entity.renderData.wheelPitch != entity.renderData.lastWheelPitch) {
                entity.renderData.lastWheelPitch =entity.renderData.wheelPitch;
                //if it's actually moving, then define the new position

                entity.renderData.animationCache[1][0] = entity.getPistonOffset();
                //animate the tagged parts
                for (StaticModelAnimator partToAnimate : entity.renderData.animatedPart) {
                    partToAnimate.Animate(entity.renderData.wheelPitch, entity.renderData.animationCache[1]);
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
        //System.out.println(entity.getTexture(0).getResourcePath() + entity.getDataWatcher().getWatchableObjectInt(24));
        Tessellator.bindTexture(entity.getTexture(entity.getDataWatcher().getWatchableObjectInt(24)));
        entity.renderData.model.render(null,0,0,0,0,0,0.0625f);


        //loop for the groups of cargo
        for (int i=0; i< entity.renderData.blockCargoRenders.size() && i < entity.calculatePercentageUsed(entity.renderData.blockCargoRenders.size()); i++) {
            entity.renderData.blockCargoRenders.get(i).doRender(field_147909_c, entity.getFirstBlock(i), this, entity.getRenderScale(), entity);
        }

        GL11.glPopMatrix();

        /*
         * <h4> render bogies</h4>
         * in TiM here we render the bogies. This will be removed in TC.
         * this loops for every bogie defined in the registry for the transport, that way we can have different bogies.
         */
        if (entity.renderData.bogieRenders != null && entity.renderData.bogieRenders.length >0){
            for (int i = 0; i<entity.getRenderBogieOffsets().size(); i++){
                if (entity.renderData.bogieRenders.length>i && entity.renderData.bogieRenders[i] != null) {
                    GL11.glPushMatrix();
                    //bind the texture
                    Tessellator.bindTexture(entity.renderData.bogieRenders[i].bogieTexture);
                    //set the offset
                    entity.renderData.animationCache[2][0]=entity.getRenderBogieOffsets().get(i) + Math.copySign((entity.getRenderScale()-0.0625f)*26, entity.getRenderBogieOffsets().get(i));
                    entity.renderData.animationCache[2][1] = RailOffset;
                    entity.renderData.animationCache[3] = RailUtility.rotatePoint(entity.renderData.animationCache[2], entity.rotationPitch, entity.rotationYaw,0);
                    GL11.glTranslated(entity.renderData.animationCache[3][0]+x,entity.renderData.animationCache[3][1]+y, entity.renderData.animationCache[3][2]+z);
                    entity.renderData.bogieRenders[i].setPositionAndRotation(entity, entity.getRenderBogieOffsets().get(i));
                    //set the rotation
                    GL11.glRotatef(-entity.renderData.bogieRenders[i].rotationYaw - 180f,0.0f,1.0f,0);
                    GL11.glRotatef(entity.rotationPitch - 180f, 0.0f, 0.0f, 1.0f);
                    //render the geometry
                    for (Object modelBogiePart : entity.renderData.bogieRenders[i].bogieModel.boxList) {
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
}