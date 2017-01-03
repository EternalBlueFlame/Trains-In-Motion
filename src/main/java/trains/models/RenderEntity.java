package trains.models;

import com.sun.javafx.geom.Vec2f;
import com.sun.javafx.geom.Vec3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.entities.GenericRailTransport;
import trains.utility.ClientProxy;
import trains.utility.HitboxHandler;
import trains.utility.RailUtility;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <h2> .Java Entity Rendering</h2>
 * to be used for rendering all trains and rollingstock, along with their particle effects.
 * for the variables fed to this class:
 * @see ClientProxy#register()
 */
public class RenderEntity extends Render {

    /**
     * <h2>variables</h2>
     * model defines the model of the train.
     * texture defines the texture used for the train
     * bogieModel defines the model that will get re-used for bogies, this can be null.
     * bogie texture defines the texture to use on the bogies.
     * simple and advanced pistons are used to cache the piston boxes of the train so we can animate them properly without needing to re-parse the base models.
     * wheels is similar to the pistons but much simpler.
     * we define rotationvec and wheel pitch here so we don't have to re-initialize it every frame.
     * smoke type is defined in the train registry this will decide the thickness and color of smoke that comes from the train.
     */
    private ModelBase model;
    private ResourceLocation texture;
    private ModelBase bogieModel;
    private ResourceLocation bogieTexture;
    private List<simplePiston> simplePistons = new ArrayList<simplePiston>();
    private List<advancedPiston> advancedPistons = new ArrayList<advancedPiston>();
    private List<wheel> wheels = new ArrayList<wheel>();
    private List<ModelBase> bogieRenders = new ArrayList<ModelBase>();
    private Vec2f rotationvec;

    private float wheelPitch=0;
    /**
     * <h3>class constructor</h3>
     * @param modelLoad the model class to render.
     * @param textureLoad the texture ResourceLocation to apply to the model.
     * @param modelBogie the model to render for the bogies, if null then no bogies will not be rendered.
     * @param bogieTexture the texture to apply to the bogie, can be null if the bogie model is null.
     */
    public RenderEntity(ModelBase modelLoad, ResourceLocation textureLoad, @Nullable ModelBase modelBogie, @Nullable ResourceLocation bogieTexture) {
        model = modelLoad;
        texture = textureLoad;
        bogieModel = modelBogie;
        this.bogieTexture = bogieTexture;
    }

    public ResourceLocation getEntityTexture(Entity entity){
        return texture;
    }

    /**
     * <h3>base render extension</h3>
     * acts as a redirect for the base render function to our own class.
     * This is just to do a more efficient type cast.
     */
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick){
        if (entity instanceof GenericRailTransport){
            doRender((GenericRailTransport) entity,x,y,z,yaw,partialTick);
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
     * @param partialTick not used.
     *
     * TODO: texture recolor similar to game maker's colorize partial so we can change the color without effecting grayscale, or lighting like on rivets.
     *                    May be able to use multiple instances of this to have multiple recolorable things on the train.
     *
     */
    public void doRender(GenericRailTransport entity, double x, double y, double z, float yaw, float partialTick){

    	GL11.glPushMatrix();
        //set the render position
        GL11.glTranslated(x, y + 1.3d, z);
        //rotate the model.
        GL11.glRotatef((-yaw) - 90, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entity.rotationPitch - 180f, 0.0f, 0.0f, 1.0f);

        if (entity.bogie.size() > 0) {
            wheelPitch += 1f;//(float) (((GenericRailTransport) entity).bogie.get(0).motionX + ((GenericRailTransport) entity).bogie.get(0).motionZ) * 0.05f;
            if (wheelPitch > 360) {
                wheelPitch = 0;
            }
        }

        //Bind the texture
        bindTexture(texture);

        /**
         * If the wheels aren't defined, and the train isn't maglev, then we need to parse all the ModelRenderers for the train and its bogies
         * and cache references, along with original positions of all the pistons.
         * In some cases we just need the reference, like for wheels, and more advanced pistons we also need the rotation.
         *
         * Assuming there are cached values to animate, then we'll animate them using the functionality from the variable classes.
         */
        if (ClientProxy.EnableAnimations) {
            if (wheels != null && wheels.size() < 1 && entity.getType() != TrainsInMotion.transportTypes.MAGLEV) {
                List boxes = model.boxList;
                if (bogieModel != null) {
                    boxes.add(bogieModel.boxList);
                }
                for (Object box : boxes) {
                    if (box instanceof ModelRenderer) {
                        ModelRenderer render = ((ModelRenderer) box);
                        if (render.boxName.equals("wheel")) {
                            wheels.add(new wheel(render));
                        } else if (render.boxName.equals("pistonvalveconnector")) {
                            advancedPistons.add(new advancedPiston(render));
                        } else if (
                                render.boxName.equals("wheelconnector") || render.boxName.equals("upperpiston") || render.boxName.equals("upperpistonarm")
                                ) {
                            simplePistons.add(new simplePiston(render));
                        }
                    }
                }
                //if there are no boxes to animate set wheels to null
                if (wheels.size() < 1) {
                    wheels = null;
                }
            }

            //if wheels is not null, then animate the model.
            if (wheels != null) {
                rotationvec = new Vec2f((entity.getPistonOffset() * MathHelper.sin(-wheelPitch * RailUtility.radianF)), (entity.getPistonOffset() * MathHelper.cos(-wheelPitch * RailUtility.radianF)));
                for (wheel tempWheel : wheels) {
                    tempWheel.rotate(wheelPitch);
                }

                for (advancedPiston advPiston : advancedPistons) {
                    advPiston.rotationMoveYZX(rotationvec);
                }

                for (simplePiston basicPiston : simplePistons) {
                    basicPiston.moveYZ(rotationvec);
                }
            }
        }

        //finally render the model and push the changes to GL.
        model.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.065f);
        GL11.glPushMatrix();



        //clear the cache, one pop for every push.
        GL11.glPopMatrix();
        GL11.glPopMatrix();

        /**
         * <h4> render bogies</h4>
         * in TiM here we render the bogies.
         * we get the entity and do an instanceof check, see if it's a train or a rollingstock, then do a for loop on the bogies, and render them the same way we do trains and rollingstock.
         */
        if (bogieModel != null){
            for (int i=0; i<entity.getBogieOffsets().size();i++){
                if (bogieRenders.size() <= i){
                    //we move it to another variable before putting it in the array as an attempt to clone it. This is probably wrong but it will have to be sorted out later.
                    ModelBase tempModel = bogieModel;
                    bogieRenders.add(tempModel);
                }
            }
        }

        /**
         * <h4>Smoke management</h4>
         * some trains, and even rollingstock will have smoke,
         */
        if (ClientProxy.EnableSmokeAndSteam) {
            float randomX;
            float randomZ;
            float colorOffset;
            Random rand = new Random();
            float[] direction;
            for (float[] smoke : entity.getSmokeOffset()) {
                for (int i = 0; i < smoke[4]; i++) {
                    direction = RailUtility.rotatePoint(new float[]{smoke[0], smoke[1], smoke[2]}, entity.rotationPitch, entity.rotationYaw, 0);
                    direction[0] += entity.posX;
                    direction[1] += entity.posY;
                    direction[2] += entity.posZ;
                    smokeFX renderSmoke = new smokeFX(entity.worldObj,
                            direction[0], direction[1], direction[2], 10);
                    randomX = (rand.nextInt(100) - 50) * 0.0001f;
                    randomZ = (rand.nextInt(100) - 50) * 0.0001f;
                    colorOffset = (rand.nextInt(20) - 10) * 0.01f;
                    if (smoke[1] != 0) {
                        renderSmoke.setVelocity(randomX, smoke[1] * 0.05, randomZ);
                    } else if (entity instanceof EntityTrainCore) {

                        renderSmoke.setVelocity((entity.motion[0] * ((EntityTrainCore) entity).accelerator) + randomX, randomZ, (entity.motion[2] * ((EntityTrainCore) entity).accelerator) + randomZ);
                    }
                    renderSmoke.setParticleTextureIndex(0);
                    if (smoke[3] > 0.55f) {
                        renderSmoke.setRBGColorF(smoke[3] - colorOffset, smoke[3] - colorOffset, smoke[3] - colorOffset);
                    } else {
                        renderSmoke.setRBGColorF(smoke[3] + colorOffset, smoke[3] + colorOffset, smoke[3] + colorOffset);
                    }
                    Minecraft.getMinecraft().effectRenderer.addEffect(renderSmoke);
                }
            }
        }


    }


    /**
     * <h3>advanced piston</h3>
     * this is used for pistons that require not only movement animation but also rotation.
     * this caches a reference to the ModelRenderer as well.
     */
    private class advancedPiston{
        private ModelRenderer boxRefrence = null;
        private Vec3f position = null;

        public advancedPiston(ModelRenderer boxToRender){
            if (boxRefrence == null){
                boxRefrence = boxToRender;
                position = new Vec3f(boxToRender.rotationPointZ, boxToRender.rotationPointY, boxToRender.rotateAngleX);
            }
        }


        public void rotationMoveYZX(Vec2f radian){
            boxRefrence.rotationPointY = position.y - (radian.y*0.5f);
            boxRefrence.rotationPointZ = position.x - radian.x;
            boxRefrence.rotateAngleX = position.z - (radian.y * 0.05f);
        }
    }


    /**
     * <h3>wheel</h3>
     * used to store a reference to the wheel for quick animation.
     */
    private class wheel {
        private ModelRenderer boxRefrence = null;

        public wheel(ModelRenderer boxToRender){
            if (boxRefrence == null){
                boxRefrence = boxToRender;
            }
        }

        public void rotate(float radian){
            boxRefrence.rotateAngleX = radian;
        }
    }

    /**
     * <h3>simple pistons</h3>
     * This is used for pistons that only need a movement animation.
     * in this case we only need to cache a reference and the original position.
     */
    private class simplePiston {
        private ModelRenderer boxRefrence = null;
        private Vec2f position = null;

        public simplePiston(ModelRenderer boxToRender){
            if (boxRefrence == null){
                boxRefrence = boxToRender;
                position = new Vec2f(boxToRender.rotationPointZ, boxToRender.rotationPointY);
            }
        }

        public void moveYZ(Vec2f radian){
            boxRefrence.rotationPointY = position.y - radian.y;
            boxRefrence.rotationPointZ = position.x - radian.x;
        }
    }


    /**
     * <h3>Smoke effects</h3>
     * this is used to render effect for smoke and steam
     */
    private class smokeFX extends EntityFX {
        private int lifespan =0;

        public smokeFX(World worldObj, double x, double y, double z, int life) {
            super(worldObj, x,y,z,0,0,0);
            this.particleScale *= 2.5f;
            lifespan = (int)(life / this.rand.nextFloat());
        }

        @Override
        public void onUpdate(){
            if (this.particleAge++ >= this.lifespan) {
                this.setDead();
            }

            if (motionX>0.02d){
                motionX=0.02d;
            } else if (motionX<-0.02d){
                motionX=-0.02d;
            }

            if (motionZ>0.02d){
                motionZ=0.02d;
            } else if (motionZ<-0.02d){
                motionZ=-0.02d;
            }

            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;

            this.moveEntity(this.motionX, this.motionY, this.motionZ);

            this.motionX *= 0.99d;
            this.motionZ *= 0.99d;
        }

        @Override
        public void moveEntity(double x, double y, double z) {
            this.ySize *= 0.4F;

            double d6 = x;
            double d7 = y;
            double d8 = z;

            List list = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(x, y, z));
            for (Object box : list){
                if (box instanceof HitboxHandler.multipartHitbox){
                    list.remove(box);
                } else if (box instanceof GenericRailTransport){
                    list.remove(box);
                }
            }

            for (Object obj: list) {
                y = ((AxisAlignedBB)obj).calculateYOffset(this.boundingBox, y);
            }

            this.boundingBox.offset(0.0D, y, 0.0D);

            if (d7 != y) {
                z = 0.0D;
                y = 0.0D;
                x = 0.0D;
            }

            int j;

            for (j = 0; j < list.size(); ++j) {
                x = ((AxisAlignedBB)list.get(j)).calculateXOffset(this.boundingBox, x);
            }

            this.boundingBox.offset(x, 0.0D, 0.0D);

            if (d6 != x) {
                z = 0.0D;
                y = 0.0D;
                x = 0.0D;
            }

            for (j = 0; j < list.size(); ++j) {
                z = ((AxisAlignedBB)list.get(j)).calculateZOffset(this.boundingBox, z);
            }

            this.boundingBox.offset(0.0D, 0.0D, z);

            if (d8 != z) {
                z = 0.0D;
                y = 0.0D;
                x = 0.0D;
            }

            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0D;
            this.posY = this.boundingBox.minY + (double)this.yOffset - (double)this.ySize;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D;

            if (d6 != x) {
                this.motionX = this.motionX*-0.75d;
            }

            if (d7 != y) {
                this.motionY = -this.motionY * 0.25d;
                this.motionZ *=-2.5d;
                this.motionX *=-2.5d;
            }

            if (d8 != z) {
                this.motionZ = this.motionZ*-0.75d;
            }
        }


    }

}