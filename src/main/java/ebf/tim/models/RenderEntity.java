package ebf.tim.models;

import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.models.tmt.ModelRendererTurbo;
import ebf.tim.models.tmt.Vec2f;
import ebf.tim.models.tmt.Vec3f;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.HitboxHandler;
import ebf.tim.utility.RailUtility;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <h2>Entity Rendering</h2>
 * used for rendering all trains and rollingstock, along with their particle effects, smoke and steam as examples.
 * for the variables fed to this class:
 * @see ClientProxy#register()
 * @author Eternal Blue Flame
 */
public class RenderEntity extends Render {

    /**
     * <h2>variables</h2>
     */
    /**tag for simple pistons, ones that move in a simple circle.*/
    public static final String tagSimplePiston = "simplepiston";
    /**tag for advanced pistons, ones that rotate and move in a simple circle.*/
    public static final String tagAdvancedPiston = "advancedpiston";
    /**tag for wheels, axles, and other geometry that just spins.*/
    public static final String tagSimpleRotate = "simplerotate";
    /**tag for all the cargo to render as a block, similar to what enderman does.
     * NOTE: this supports grouping, parts with the same exact name will represent the same percentage of inventory used.
     * NOTE 2: order is defined in the model geometry order, not by name, so be sure they are organized properly in your editor.
     * EXAMPLE: (tagRenderBlockCargo + "myblock5") and (tagRenderBlockCargo + "myblock2")
     * what is shown in the example will be two separate groups, each representing half the inventory, with 5 actually being the first because it was defined first.*/
    public static final String tagRenderBlockCargo = "renderblock";
    /**tag for all the cargo to render from the model, but as representation of inventory use.
     * NOTE: this supports grouping, parts with the same exact name will represent the same percentage of inventory used.
     * NOTE 2: order is defined in the model geometry order, not by name, so be sure they are organized properly in your editor.
     * EXAMPLE: (tagRenderModelCargo + "myblock5") and (tagRenderModelCargo + "myblock2")
     * what is shown in the example will be two separate groups, each representing half the inventory, with 5 actually being the first because it was defined first.*/
    public static final String tagRenderModelCargo = "rendercrate";

    /**the base model of the entity, this holds every piece of sub-geometry and their texture mappings*/
    private ModelBase model;
    /**texture used by the base model*/
    private ResourceLocation texture;
    /**the models, textures, and other data for each bogie to render*/
    private Bogie[] bogieRenders;
    /**a cached list of all the simple pistons, ones that move in a simple circle.*/
    private List<simplePiston> simplePistons = new ArrayList<simplePiston>();
    /**a cached list of all the advanced pistons, ones that rotate and move in a simple circle.*/
    private List<advancedPiston> advancedPistons = new ArrayList<advancedPiston>();
    /**a cached list of all the wheels, and other geometry that just spins.*/
    private List<wheel> wheels = new ArrayList<wheel>();
    /**a cached list of all the cargo blocks.*/
    private List<blockCargo> blockCargoRenders = new ArrayList<blockCargo>();
    /**a cached list of all the vectors used, so we don't have to re-initialize them every frame.*/
    private double[][] animationCache = new double[6][3];
    /**a cached list of all the cubes intended to display liveries.*/
    private List<ModelRendererTurbo> liveriesSquare = new ArrayList<ModelRendererTurbo>();
    /**the value to rotate the geometry with.*/
    private float wheelPitch=0;
    /**
     * <h3>class constructor</h3>
     * instances the render, and caches the geometry tagged for runtime modification (like animation and block renders)
     * caching here means we don't even need to check it every render frame.
     * @param modelLoad the model class to render.
     * @param textureLoad the texture ResourceLocation to apply to the model.
     * @param bogieRenders the model and texture class to render for the bogies, if null then no bogies will not be rendered. null indexes are allowed as well.
     */
    public RenderEntity(ModelBase modelLoad, ResourceLocation textureLoad, @Nullable Bogie[] bogieRenders) {
        model = modelLoad;
        texture = textureLoad;
        this.bogieRenders = bogieRenders;

        if (ClientProxy.EnableAnimations){
            for (Object box : model.boxList) {
                if (box instanceof ModelRendererTurbo) {
                    ModelRendererTurbo render = ((ModelRendererTurbo) box);
                    //wheels
                    if (render.boxName.contains(tagSimpleRotate)) {
                        wheels.add(new wheel(render));
                    }
                    //advanced pistons
                    if (render.boxName.contains(tagAdvancedPiston)) {
                        advancedPistons.add(new advancedPiston(render));
                    }
                    //simple pistons and wheel connectors.
                    if (render.boxName.contains(tagSimplePiston)) {
                        simplePistons.add(new simplePiston(render));
                    }
                    //block renders
                    if (render.boxName.contains(tagRenderBlockCargo)) {
                        boolean isAdded =false;
                        for (blockCargo cargo : blockCargoRenders) {
                            if (cargo.name.equals(render.boxName)){
                                cargo.boxRefrence.add(render);
                                isAdded= true;break;
                            }
                        }
                        if (!isAdded){
                            blockCargoRenders.add(new blockCargo().add(render, true));
                        }
                    }
                    //crate renders
                    if (render.boxName.contains(tagRenderModelCargo)) {
                        boolean isAdded =false;
                        for (blockCargo cargo : blockCargoRenders) {
                            if (cargo.name.equals(render.boxName)){
                                cargo.boxRefrence.add(render);
                                isAdded= true;break;
                            }
                        }
                        if (!isAdded){
                            blockCargoRenders.add(new blockCargo().add(render, false));
                        }
                    }
                }
            }


            //bogies
            if (bogieRenders != null){
                for (Bogie bogie : bogieRenders){
                    for (Object box : bogie.bogieModel.boxList) {
                        if (ClientProxy.EnableAnimations && box instanceof ModelRendererTurbo) {
                            ModelRendererTurbo render = ((ModelRendererTurbo) box);
                            //wheel renders
                            if (render.boxName.contains(tagSimpleRotate)) {
                                wheels.add(new wheel(render));
                            }
                            //advanced piston renders
                            if (render.boxName.contains(tagAdvancedPiston)) {
                                advancedPistons.add(new advancedPiston(render));
                            }
                            //simple pistons and wheel connectors
                            if (render.boxName.contains(tagSimplePiston)) {
                                simplePistons.add(new simplePiston(render));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * <h3>overall texture</h3>
     * returns the texture for this entity, required by the super but not actually used.
     */
    public ResourceLocation getEntityTexture(Entity entity){
        return texture;
    }

    /**
     * <h3>base render extension</h3>
     * acts as a redirect for the base render function to our own class.
     * This is just to do a more efficient type cast because we have to make the first parameter an Entity.
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
        GL11.glTranslated(x, y+0.275, z);
        //rotate the model.
        GL11.glRotatef((-yaw), 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entity.rotationPitch-180f, 1.0f, 0.0f, 0.0f);

        /**
         * <h3>animations</h3>
         * Be sure animations are enabled in user settings, then check of there is something to animate.
         * if there is, then calculate the vectors and apply the animations
         */
        if (ClientProxy.EnableAnimations && entity.frontBogie!=null&&entity.backBogie!=null) {
            //define the rotation angle
            wheelPitch += (entity.backBogie.motionX + entity.backBogie.motionZ);
            if (wheelPitch > 360 || wheelPitch <-360) {
                wheelPitch = 0;
            }
            if (wheelPitch !=0) {
                //if it's not 0, aka not moving, then define the new position
                animationCache[1][0] = entity.getPistonOffset() * RailUtility.radianF;
                animationCache[0] = RailUtility.rotatePoint(animationCache[1], Math.copySign(wheelPitch, 1), Math.copySign(wheelPitch, 1), 0);
                //rotate wheels
                for (wheel tempWheel : wheels) {
                    tempWheel.rotate(wheelPitch * RailUtility.radianF);
                }
                //reposition advanced pistons
                for (advancedPiston advPiston : advancedPistons) {
                    advPiston.rotationMoveYZX((float) animationCache[0][0], (float) animationCache[0][1]);
                }
                //reposition simple pistons and wheel connectors
                for (simplePiston basicPiston : simplePistons) {
                    basicPiston.moveYZ((float) animationCache[0][0], (float) animationCache[0][1]);
                }
            }
        }

        /**
         * <h3>Render geometry</h3>
         * Bind the texture first, then render any geometry that is supposed to use the default texture.
         * after that check if there are any cargo blocks to render, then render them dependant on if there is enough stuff in the inventory to merit it.
         * In that render there is a check whether to render it as a cargo block, or use the geometry size/position/rotation to render a block similar to enderman.
         * @see net.minecraft.client.renderer.entity.RenderEnderman#renderEquippedItems(EntityEnderman, float)
         */
        bindTexture(texture);
        for(Object cube : model.boxList){
            if (cube instanceof ModelRenderer &&
                    !(((ModelRendererTurbo) cube).boxName).contains(tagRenderBlockCargo) && !(((ModelRendererTurbo) cube).boxName).contains(tagRenderModelCargo)) {
                ((ModelRenderer)cube).render(entity.getRenderScale());
            }
        }


        int itteration=0;
        if (blockCargoRenders.size()>0){
            //loop for the groups of cargo
            for (blockCargo cargo : blockCargoRenders) {
                //limit loops to how much should be rendered based on inventory content
                if (itteration<entity.calculatePercentageUsed(blockCargoRenders.size())) {
                    if (cargo.isBlock) {
                        //render a block in place of the geometry.
                        // bind the texture
                        bindTexture(TextureMap.locationBlocksTexture);
                        for (ModelRendererTurbo block : cargo.boxRefrence) {
                            GL11.glPushMatrix();
                            //define position from model
                            GL11.glTranslated(((block.offsetX + block.rotationPointX) / 16),
                                    ((block.offsetY + block.rotationPointY) / 16),
                                    ((block.offsetZ + block.rotationPointZ) / 16));
                            //define the rotation from the model
                            GL11.glRotated(block.rotateAngleX * RailUtility.degreesD, 1, 0, 0);
                            GL11.glRotated(block.rotateAngleY * RailUtility.degreesD, 0, 1, 0);
                            GL11.glRotated(block.rotateAngleZ * RailUtility.degreesD, 0, 0, 1);
                            //define scale based on the model
                            GL11.glScaled(block.xScale - 0.0175, block.yScale - 0.0175, block.zScale - 0.0175);
                            //now actually render the block.
                            ItemStack b = entity.getFirstBlock(itteration);
                            field_147909_c.renderBlockAsItem( Block.getBlockFromItem(b.getItem()), b.getItemDamage(), 1.0f);
                            GL11.glPopMatrix();
                        }
                    } else {
                        //render the geometry normally if it's not a block.
                        bindTexture(texture);
                        for (ModelRendererTurbo block : cargo.boxRefrence) {
                            block.render(entity.getRenderScale());
                        }
                    }
                }
                itteration++;
            }
        }

        GL11.glPopMatrix();

        /**
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
                    double[] rotated = RailUtility.rotatePoint(new double[]{entity.getRenderBogieOffsets().get(i),0.275,0}, entity.rotationPitch, entity.rotationYaw,0);
                    GL11.glTranslated(rotated[0]+x,rotated[1]+y, rotated[2]+z);
                    //set the rotation
                    GL11.glRotatef(bogieRenders[i].rotationYaw,0,1.0f,0);
                    GL11.glRotatef(entity.rotationPitch - 180f, 1.0f, 0.0f, 0.0f);
                    bogieRenders[i].setPositionAndRotation(animationCache[5], entity);
                    //render the geometry
                    for (Object modelBogiePart : bogieRenders[i].bogieModel.boxList) {
                        if (modelBogiePart instanceof ModelRendererTurbo) {
                            ((ModelRendererTurbo) modelBogiePart).render(entity.getRenderScale());
                        }
                    }
                    GL11.glPopMatrix();
                }
            }
        }




        /**
         * <h4>Smoke management</h4>
         * some trains, and even rollingstock will have smoke,
         * TODO: position isn't always correct.
         */
        if (ClientProxy.EnableSmokeAndSteam) {
            float randomX;
            float randomZ;
            float colorOffset;
            //define a random int to scale color
            Random rand = new Random();
            //for each smoke offset, and for each value in the smoke size, render another smoke particle.
            for (float[] smoke : entity.getSmokeOffset()) {
                for (int i = 0; i < smoke[4]; i++) {
                    //define position
                    animationCache[3][0] = smoke[0];
                    animationCache[3][1] = smoke[1];
                    animationCache[3][2] = smoke[2];
                    animationCache[2] = RailUtility.rotatePoint(animationCache[3], entity.rotationPitch, entity.rotationYaw, 0);
                    animationCache[2][0] += entity.posX;
                    animationCache[2][1] += entity.posY;
                    animationCache[2][2] += entity.posZ;
                    //make the particle
                    smokeFX renderSmoke = new smokeFX(Minecraft.getMinecraft().theWorld,
                            animationCache[2][0], animationCache[2][1], animationCache[2][2], 5);
                    //set the particle speed and direction
                    randomX = (rand.nextInt(100) - 50) * 0.0001f;
                    randomZ = (rand.nextInt(100) - 50) * 0.0001f;
                    if (smoke[1] != 0) {
                        renderSmoke.setVelocity(randomX, smoke[1] * 0.0075, randomZ);
                    } else if (entity instanceof EntityTrainCore && entity.backBogie!=null) {
                        renderSmoke.setVelocity((entity.backBogie.motionX * ((EntityTrainCore) entity).accelerator) + randomX, smoke[1], (entity.backBogie.motionZ * ((EntityTrainCore) entity).accelerator) + randomZ);
                    }
                    //set the texture
                    renderSmoke.setParticleTextureIndex(0);
                    //color the texture
                    colorOffset = (rand.nextInt(20) - 10) * 0.01f;
                    renderSmoke.setRBGColorF(smoke[3] - colorOffset, smoke[3] - colorOffset, smoke[3] - colorOffset);
                    //now actually spawn it
                    Minecraft.getMinecraft().effectRenderer.addEffect(renderSmoke);
                }
            }
        }
    }


    /**
     * <h2>variables</h2>
     * Rather than make a bunch of new class files to define variables that are only used in this class,
     * we just define them like functions and use them as variables.
     */

    /**
     * <h3>advanced piston</h3>
     * this is used for pistons that require not only movement animation but also rotation.
     */
    private class advancedPiston{
        //store a refrence to the original model, and it's original position
        private ModelRendererTurbo boxRefrence = null;
        private Vec3f position = null;

        public advancedPiston(ModelRendererTurbo boxToRender){
            if (boxRefrence == null){
                boxRefrence = boxToRender;
                position = new Vec3f(boxToRender.rotationPointZ, boxToRender.rotationPointY, boxToRender.rotateAngleX);
            }
        }
        //position and rotate the geometry based on the provided vector.
        public void rotationMoveYZX(float x, float y){
            boxRefrence.rotationPointY = position.y - (y*0.5f);
            boxRefrence.rotationPointX = position.x - x;
            boxRefrence.rotateAngleZ = position.z - (y * 0.05f);
        }
    }


    /**
     * <h3>wheel</h3>
     * used to store a reference to the wheel, and rotate it using a given radian for quick animation.
     */
    private class wheel {
        private ModelRendererTurbo boxRefrence = null;

        public wheel(ModelRendererTurbo boxToRender){
            if (boxRefrence == null){
                boxRefrence = boxToRender;
            }
        }

        public void rotate(float radian){
            boxRefrence.rotateAngleZ = radian;
        }
    }

    /**
     * <h3>BlockCargo</h3>
     * used to store references to logs and other similar carried cargo blocks.
     * each entry of this variable works as a set, so multiple parts can be rendered, or not, from the same value.
     * name is used to group them together, if the name strings are exactly the same they will be rendered together.
     * isBlock defines if it should be rendered as a normal block from the inventory or part of the model.
     */
    private class blockCargo {
        public String name;
        public boolean isBlock;
        private List<ModelRendererTurbo> boxRefrence = new ArrayList<ModelRendererTurbo>();

        public blockCargo add(ModelRendererTurbo boxToRender, boolean block){
            name = boxToRender.boxName;
            boxRefrence.add(boxToRender);
            isBlock = block;
            return this;
        }
    }

    /**
     * <h3>simple pistons</h3>
     * This is used for pistons that only need a movement animation.
     * in this case we only need to cache a reference to the geometry and the original position.
     */
    private class simplePiston {
        private ModelRendererTurbo boxRefrence = null;
        private Vec2f position = null;

        public simplePiston(ModelRendererTurbo boxToRender){
            if (boxRefrence == null){
                boxRefrence = boxToRender;
                position = new Vec2f(boxToRender.rotationPointZ, boxToRender.rotationPointY);
            }
        }

        public void moveYZ(float x, float y){
            boxRefrence.rotationPointY = position.y - y;
            boxRefrence.rotationPointX = position.x - x;
        }
    }


    /**
     * <h3>Smoke effects</h3>
     * this is used to render effect for smoke and steam, it's basically an entire entity.
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

            double oldX = x;
            double oldY = y;
            double oldZ = z;

            List list = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(x, y, z));
            for (Object box : list){
                if (!(box instanceof HitboxHandler.MultipartHitbox) && !(box instanceof GenericRailTransport)){
                    y = ((AxisAlignedBB)box).calculateYOffset(this.boundingBox, y);
                    x = ((AxisAlignedBB)box).calculateYOffset(this.boundingBox, x);
                    z = ((AxisAlignedBB)box).calculateYOffset(this.boundingBox, z);
                }
            }

            this.boundingBox.offset(x, y, z);

            if (oldY != y) {
                z = 0.0D;
                y = 0.0D;
                x = 0.0D;
            }

            if (oldX != x) {
                z = 0.0D;
                y = 0.0D;
                x = 0.0D;
            }

            if (oldZ != z) {
                z = 0.0D;
                y = 0.0D;
                x = 0.0D;
            }

            this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0D;
            this.posY = this.boundingBox.maxY - (double)this.ySize;
            this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D;

            if (oldX != x) {
                this.motionX = this.motionX*-0.75d;
            }

            if (oldY != y) {
                this.motionY = -this.motionY * 0.25d;
                this.motionZ *=-2.5d;
                this.motionX *=-2.5d;
            }

            if (oldZ != z) {
                this.motionZ = this.motionZ*-0.75d;
            }
        }


    }

}