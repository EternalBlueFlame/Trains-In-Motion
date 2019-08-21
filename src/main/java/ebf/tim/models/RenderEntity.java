package ebf.tim.models;

import ebf.tim.entities.GenericRailTransport;
import ebf.tim.utility.ClientProxy;
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.ModelRendererTurbo;
import fexcraft.tmt.slim.Tessellator;
import fexcraft.tmt.slim.TextureManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
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
    private static int i=0, ii=0, iii=0;

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
     * todo: 1.9+ should support Entity<t zextends GenericRailTransport> so this typecasting method should be completely useless then.
     */
    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick){
        if (entity instanceof GenericRailTransport && ((GenericRailTransport) entity).frontBogie!=null){
            render((GenericRailTransport) entity,x,y,z, entity.prevRotationYaw + MathHelper.wrapAngleTo180_float(entity.rotationYaw - entity.prevRotationYaw)*partialTick, false);
        }
    }

    public void render(GenericRailTransport entity, double x, double y, double z, float yaw, boolean isPaintBucket) {
        doRender(entity,x,y,z,yaw,entity.frontBogie!=null?entity.frontBogie.yOffset:0, isPaintBucket);
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
    public void doRender(GenericRailTransport entity, double x, double y, double z, float yaw, float bogieOffset, boolean isPaintBucket){

        if (entity.renderData.modelList == null || entity.renderData.needsModelUpdate) {
            entity.renderData = new TransportRenderData();
            entity.renderData.modelList = entity.getModel();
            entity.renderData.bogies = entity.bogies();


            //cache animating parts
            if (ClientProxy.EnableAnimations && entity.renderData.needsModelUpdate) {
                boolean isAdded;
                for (ModelBase part : entity.renderData.modelList) {
                    for (ModelRendererTurbo render : part.getParts()) {
                        if (render.boxName ==null){continue;}
                        //attempt to cache the parts for the main transport model
                        if(render.boxName.toLowerCase().contains("hide") || render.boxName.toLowerCase().contains("cull")){
                            render.showModel = false;
                        }
                        if (StaticModelAnimator.checkAnimators(render)) {
                            entity.renderData.animatedPart.add(StaticModelAnimator.initPart(render, entity));
                        } else if (GroupedModelRender.canAdd(render)) {
                            //if it's a grouped render we have to figure out if we already have a group for this or not.
                            isAdded = false;
                            for (GroupedModelRender cargo : entity.renderData.blockCargoRenders) {
                                if (cargo.getGroupName().equals(render.boxName)) {
                                    cargo.add(render);
                                    isAdded = true;
                                    break;
                                }
                            }
                            if (!isAdded) {
                                entity.renderData.blockCargoRenders.add(new GroupedModelRender().add(render));
                            }
                            render.showModel = false;
                        }
                        if(ParticleFX.parseData(render.boxName)!=null){
                            entity.renderData.particles.addAll(ParticleFX.newParticleItterator(render.boxName,
                                    render.rotationPointX, render.rotationPointY, render.rotationPointZ,
                                    render.rotateAngleX,render.rotateAngleY,render.rotateAngleZ, entity));
                        }
                    }
                }
                //cache the animating parts on the bogies.
                if (entity.renderData.bogies != null) {
                    for (Bogie bogie : entity.renderData.bogies) { {
                            for (ModelRendererTurbo box : bogie.bogieModel.getParts()) {
                                if (StaticModelAnimator.checkAnimators(box)) {
                                    entity.renderData.animatedPart.add(StaticModelAnimator.initPart(box, entity));
                                }
                            }
                            if(bogie.subBogies==null){continue;}
                            //cache the animating parts on sub-bogies
                            for(Bogie subBogie : bogie.subBogies){
                                for(ModelRendererTurbo box : subBogie.bogieModel.getParts()){
                                    if (StaticModelAnimator.checkAnimators(box)) {
                                        entity.renderData.animatedPart.add(StaticModelAnimator.initPart(box, entity));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            entity.renderData.needsModelUpdate=false;
        }




        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        //set the render position
        GL11.glTranslated(x, y+ RailOffset + ((entity.getRenderScale()-0.0625f)*10)+bogieOffset, z);
        //rotate the model.
        GL11.glRotatef(-yaw - 180f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entity.rotationPitch - 180f, 0.0f, 0.0f, 1.0f);

        /*
         * <h3>animations</h3>
         * Be sure animations are enabled in user settings, then check of there is something to animate.
         * if there is, then calculate the vectors and apply the animations
         */
        if (!Minecraft.getMinecraft().isGamePaused() &&ClientProxy.EnableAnimations) {
            if (entity.renderData.wheelPitch >= 6.2831855f || entity.renderData.wheelPitch <=-6.2831855f) {
                entity.renderData.wheelPitch -= Math.copySign(6.2831855f, entity.renderData.wheelPitch);
            }
            //define the rotation angle, if it's going fast enough.
            entity.renderData.wheelPitch += (((entity.frontVelocityX * entity.frontVelocityX) + (entity.frontVelocityZ * entity.frontVelocityZ))*0.3f);

            if (entity.renderData.wheelPitch != entity.renderData.lastWheelPitch) {
                entity.renderData.lastWheelPitch =entity.renderData.wheelPitch;
                //if it's actually moving, then define the new position

                entity.renderData.animationCache[0][0] = entity.getPistonOffset();
                //animate the tagged parts
                for (AnimationBase partToAnimate : entity.renderData.animatedPart) {
                    if(partToAnimate==null){continue;}
                    partToAnimate.animate(entity.renderData.wheelPitch, entity.renderData.animationCache[0], entity);
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
        if(entity.worldObj!=null) {
            TextureManager.adjustLightFixture(entity.worldObj, (int) entity.posX, (int) entity.posY + 1, (int) entity.posZ);
            //todo: TextureManager.maskColors(entity.getTexture().texture, entity.colors);
            TextureManager.bindTexture(entity.getTexture(Minecraft.getMinecraft().thePlayer).texture);
        } else {
            TextureManager.bindTexture(entity.getTextureByIndex(Minecraft.getMinecraft().thePlayer, isPaintBucket,0/*todo use X as skin index?*/).texture);
        }
        for(i=0; i< entity.renderData.modelList.length;i++) {
            GL11.glPushMatrix();
            if(entity.modelOffsets()!=null && entity.modelOffsets().length>i) {
                GL11.glTranslated(entity.modelOffsets()[i][0],entity.modelOffsets()[i][1],entity.modelOffsets()[i][2]);
            }
            entity.renderData.modelList[i].render(null, 0, 0, 0, 0, 0, entity.getRenderScale());
            GL11.glPopMatrix();
        }


        //loop for the groups of cargo
        for (i = 0; i< entity.renderData.blockCargoRenders.size() && i < entity.calculatePercentageOfSlotsUsed(entity.renderData.blockCargoRenders.size()); i++) {
            entity.renderData.blockCargoRenders.get(i).doRender(field_147909_c, entity.getFirstBlock(i), this, entity.getRenderScale(), entity);
        }

        /*
         * <h4> render bogies</h4>
         * in TiM here we render the bogies. This will be removed in TC.
         * this loops for every bogie defined in the registry for the transport, that way we can have different bogies.
         */

        if (entity.renderData.bogies != null) {
            for(Bogie b : entity.renderData.bogies) {
                ii=0;
                GL11.glPushMatrix();
                //bind the texture
                if(entity.worldObj!=null){
                    if (entity.getTexture(Minecraft.getMinecraft().thePlayer).getBogieSkin(ii) != null) {
                        Tessellator.bindTexture(entity.getTexture(Minecraft.getMinecraft().thePlayer).getBogieSkin(i));
                    }
                } else {
                    if(entity.getTextureByIndex(Minecraft.getMinecraft().thePlayer, true, 0).getBogieSkin(ii) != null) {
                        Tessellator.bindTexture(entity.getTextureByIndex(Minecraft.getMinecraft().thePlayer, isPaintBucket, 0/*todo use x for skin index?*/).getBogieSkin(i));
                    }
                }
                GL11.glTranslated(-b.offset[0], -b.offset[1], -b.offset[2]);
                b.setRotation(entity);
                GL11.glPushMatrix();
                GL11.glRotatef(b.rotationYaw-yaw, 0.0f, 1.0f, 0);
                //GL11.glRotatef(entity.rotationPitch - 180f, 0.0f, 0.0f, 1.0f);
                b.bogieModel.render(null, 0, 0, 0, 0, 0, entity.getRenderScale());
                GL11.glPopMatrix();
                if(b.subBogies!=null) {
                    iii=0;
                    for (Bogie sub : b.subBogies) {
                        if(entity.worldObj!=null) {
                            TextureManager.bindTexture(entity.getTexture(Minecraft.getMinecraft().thePlayer).getSubBogieSkin(iii));
                        } else {
                            TextureManager.bindTexture(entity.getTextureByIndex(Minecraft.getMinecraft().thePlayer, isPaintBucket, 0/*todo use x for skin index?*/).getSubBogieSkin(iii));
                        }
                        GL11.glPushMatrix();
                        GL11.glTranslated(sub.offset[0], sub.offset[1], sub.offset[2]);
                        sub.setRotation(entity);
                        GL11.glRotatef(sub.rotationYaw, 0.0f, 1.0f, 0);
                        GL11.glRotatef(entity.rotationPitch - 180f, 0.0f, 0.0f, 1.0f);
                        sub.bogieModel.render(null, 0, 0, 0, 0, 0, entity.getRenderScale());
                        GL11.glPopMatrix();
                        iii++;
                    }
                }

                GL11.glPopMatrix();
                ii++;
            }
        }



        GL11.glPopMatrix();
        //render the particles, if there are any.
        for(ParticleFX particle : entity.renderData.particles){
            ParticleFX.doRender(particle, x,y,z);
        }

        GL11.glDisable(GL11.GL_BLEND);



        //render hitboxes
        if(RenderManager.debugBoundingBox && entity.collisionHandler!=null && entity.collisionHandler.renderShape !=null) {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            //GL11.glDepthMask(false);

            //todo: likely the issue is that x/y/z already deal with the entity position, but the pos _also_ have the position
            //todo: so we need to seperate the position from the hitbox, likely by processing it seperatley here, or an additional gl translate if possible...
            GL11.glTranslated(x,y,z);

            GL11.glColor3f(1,1,1);
            //GL11.glEnable(GL11.GL_LINE);
            //DebugUtil.println(entity.collisionHandler.renderShape[0]);

            Tessellator.getInstance().startDrawing(GL11.GL_LINE_STRIP);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[0].xCoord, entity.collisionHandler.renderShape[0].yCoord, entity.collisionHandler.renderShape[0].zCoord);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[1].xCoord, entity.collisionHandler.renderShape[1].yCoord, entity.collisionHandler.renderShape[1].zCoord);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[2].xCoord, entity.collisionHandler.renderShape[2].yCoord, entity.collisionHandler.renderShape[2].zCoord);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[3].xCoord, entity.collisionHandler.renderShape[3].yCoord, entity.collisionHandler.renderShape[3].zCoord);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[0].xCoord, entity.collisionHandler.renderShape[0].yCoord, entity.collisionHandler.renderShape[0].zCoord);
            Tessellator.getInstance().draw();


            Tessellator.getInstance().startDrawing(GL11.GL_LINE_STRIP);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[4].xCoord, entity.collisionHandler.renderShape[4].yCoord, entity.collisionHandler.renderShape[4].zCoord);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[5].xCoord, entity.collisionHandler.renderShape[5].yCoord, entity.collisionHandler.renderShape[5].zCoord);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[6].xCoord, entity.collisionHandler.renderShape[6].yCoord, entity.collisionHandler.renderShape[6].zCoord);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[7].xCoord, entity.collisionHandler.renderShape[7].yCoord, entity.collisionHandler.renderShape[7].zCoord);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[4].xCoord, entity.collisionHandler.renderShape[4].yCoord, entity.collisionHandler.renderShape[4].zCoord);
            Tessellator.getInstance().draw();



            Tessellator.getInstance().startDrawing(GL11.GL_LINES);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[0].xCoord, entity.collisionHandler.renderShape[0].yCoord, entity.collisionHandler.renderShape[0].zCoord);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[4].xCoord, entity.collisionHandler.renderShape[4].yCoord, entity.collisionHandler.renderShape[4].zCoord);
            Tessellator.getInstance().draw();
            Tessellator.getInstance().startDrawing(GL11.GL_LINES);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[1].xCoord, entity.collisionHandler.renderShape[1].yCoord, entity.collisionHandler.renderShape[1].zCoord);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[5].xCoord, entity.collisionHandler.renderShape[5].yCoord, entity.collisionHandler.renderShape[5].zCoord);
            Tessellator.getInstance().draw();
            Tessellator.getInstance().startDrawing(GL11.GL_LINES);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[2].xCoord, entity.collisionHandler.renderShape[2].yCoord, entity.collisionHandler.renderShape[2].zCoord);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[6].xCoord, entity.collisionHandler.renderShape[6].yCoord, entity.collisionHandler.renderShape[6].zCoord);
            Tessellator.getInstance().draw();
            Tessellator.getInstance().startDrawing(GL11.GL_LINE_STRIP);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[3].xCoord, entity.collisionHandler.renderShape[3].yCoord, entity.collisionHandler.renderShape[3].zCoord);
            Tessellator.getInstance().addVertex(entity.collisionHandler.renderShape[7].xCoord, entity.collisionHandler.renderShape[7].yCoord, entity.collisionHandler.renderShape[7].zCoord);
            Tessellator.getInstance().draw();

            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            //GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }
    }
}