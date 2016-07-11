package trains.entities.render;


import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.ObjModelLoader;
import org.lwjgl.opengl.GL11;
import trains.TrainsInMotion;
import trains.entities.MinecartExtended;

import java.io.File;

public class RenderObj extends Render {

    private IModelCustom model;
    private ResourceLocation texture;
    private static final int[][][] matrix = new int[][][] {{{0, 0, -1}, {0, 0, 1}}, {{ -1, 0, 0}, {1, 0, 0}}, {{ -1, -1, 0}, {1, 0, 0}}, {{ -1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, { -1, 0, 0}}, {{0, 0, -1}, { -1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};

    public ResourceLocation getEntityTexture(Entity entity){
        return texture;
    }

    public void doRender(Entity entity, double x, double y, double z, float rotation, float partialTick){
        //begin rendering
        GL11.glPushMatrix();
        //set the position of the graphic and save changes
        GL11.glTranslated(x, y, z);
        GL11.glPushMatrix();

        /**
         * now we have to handle rotation, assuming the entity is an instance of MinecartExtended this is a lot more complicated because we have to define rotation based on it's current and previous positions.
         */

        //first initialize the variables
        if (entity instanceof MinecartExtended) {
            MinecartExtended cart = (MinecartExtended) entity;
            Vec3 vec3;
            float f5 = 0;

            //now calculate the main vector based on position, using cartTurnProgress to smooth.
            //TODO func_70489_a is kind of retarded and needs to be remade.
            if (cart.cartTurnProgress != 0) {
                vec3 = func_70489_a(cart,
                        cart.lastTickPosX + ((cart.cartX - cart.lastTickPosX) / cart.cartTurnProgress) * (double) partialTick,
                        cart.lastTickPosY + ((cart.cartY - cart.lastTickPosY) / cart.cartTurnProgress) * (double) partialTick,
                        cart.lastTickPosZ + ((cart.cartZ - cart.lastTickPosZ) / cart.cartTurnProgress) * (double) partialTick
                );
            } else {
                vec3 = func_70489_a(cart,
                        cart.lastTickPosX + (cart.cartX - cart.lastTickPosX) * (double) partialTick,
                        cart.lastTickPosY + (cart.cartY - cart.lastTickPosY) * (double) partialTick,
                        cart.lastTickPosZ + (cart.cartZ - cart.lastTickPosZ) * (double) partialTick
                );
            }

            //now assuming the main vector isn't null, calculate the transition vector
            if (vec3 != null) {
                //first we create vectors for the transition
                //TODO func_70495_a is kind of retarded and needs to be remade.
                Vec3 vec31 = func_70495_a(cart, vec3.xCoord, vec3.yCoord, vec3.zCoord, 0.30000001192092896D);
                Vec3 vec32 = func_70495_a(cart, vec3.xCoord, vec3.yCoord, vec3.zCoord, -0.30000001192092896D);

                //if one of the two ends up being null, have it match the main vector.
                if (vec31 == null) {
                    vec31 = vec3;
                }
                if (vec32 == null) {
                    vec32 = vec3;
                }

                //now add the vectors together
                Vec3 vec33 = vec32.addVector(-vec31.xCoord, -vec31.yCoord, -vec31.zCoord);

                // finally, if the result isn't 0, round the result and set the proper values. However if it was 0 then we only change the yaw.
                //NOTE 57.2957795131D is the result of 180/Pi
                if (vec33.lengthVector() != 0.0D) {
                    vec33 = vec33.normalize();
                    rotation = (float) (Math.atan2(vec33.zCoord, vec33.xCoord) * 57.2957795131D);
                    f5 = (float) (Math.atan(vec33.yCoord) * 73.0D);
                } else {
                    f5 = cart.cartYaw + (cart.cartPitch - cart.prevRotationPitch) * partialTick;
                }
            }

            /**
             * now we actually set the values we just finished defining.
             * because rotation is set on (amount, xaxis, yaxis, xaxis) we have to rotate each axis individually, then push
             */
            GL11.glRotatef(180F - rotation, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-f5, 0.0F, 0.0F, 1.0F);
            GL11.glPushMatrix();
        }

        //Bind the texture and render the model
        bindTexture(texture);
        model.renderAll();

        //clear the cache, one pop for every push.
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        if (entity instanceof MinecartExtended) {
            GL11.glPopMatrix();
        }

    }

    /**
     * initializer for the render function.
     * @param modelLoad resource location for model
     * @param textureLoad resource location for texture, if invalid defaults to the null texture.
     */
    public RenderObj(ResourceLocation modelLoad, ResourceLocation textureLoad) {
        model = new ObjModelLoader().loadInstance(modelLoad);
        if (new File(textureLoad.getResourcePath()).exists()) {
            texture = textureLoad;
        } else{
            texture = TrainsInMotion.Resources.TEXTURE.getResourceLocation("null.png");
        }
    }


    /**
     * these functions are for calculating the vectors from the metadata in rails, this will long run ell the ernder which direction to rotate the model.
     */
    private Vec3 func_70495_a(MinecartExtended cart, double p_70495_1_, double p_70495_3_, double p_70495_5_, double p_70495_7_) {
        int i = MathHelper.floor_double(p_70495_1_);
        int j = MathHelper.floor_double(p_70495_3_);
        int k = MathHelper.floor_double(p_70495_5_);

        if (BlockRailBase.func_150049_b_(cart.worldObj, i, j - 1, k)) {
            --j;
        }

        Block block = cart.worldObj.getBlock(i, j, k);

        if (BlockRailBase.func_150051_a(block)) {
            int l = ((BlockRailBase)block).getBasicRailMetadata(cart.worldObj, cart, i, j, k);

            p_70495_3_ = (double)j;

            if (l >= 2 && l <= 5) {
                p_70495_3_ = (double)(j + 1);
            }

            int[][] aint = matrix[l];
            double d4 = (aint[1][0] - aint[0][0]);
            double d5 = (aint[1][2] - aint[0][2]);
            double d6 = Math.sqrt(d4 * d4 + d5 * d5);
            d4 /= d6;
            d5 /= d6;
            p_70495_1_ += d4 * p_70495_7_;
            p_70495_5_ += d5 * p_70495_7_;

            if (aint[0][1] != 0 && MathHelper.floor_double(p_70495_1_) - i == aint[0][0] && MathHelper.floor_double(p_70495_5_) - k == aint[0][2]) {
                p_70495_3_ += aint[0][1];
            } else if (aint[1][1] != 0 && MathHelper.floor_double(p_70495_1_) - i == aint[1][0] && MathHelper.floor_double(p_70495_5_) - k == aint[1][2]) {
                p_70495_3_ += aint[1][1];
            }

            return func_70489_a(cart, p_70495_1_, p_70495_3_, p_70495_5_);
        } else {
            return null;
        }
    }


    private Vec3 func_70489_a(MinecartExtended cart, double p_70489_1_, double p_70489_3_, double p_70489_5_) {
        int i = MathHelper.floor_double(p_70489_1_);
        int j = MathHelper.floor_double(p_70489_3_);
        int k = MathHelper.floor_double(p_70489_5_);

        if (BlockRailBase.func_150049_b_(cart.worldObj, i, j - 1, k)) {
            --j;
        }

        Block block = cart.worldObj.getBlock(i, j, k);

        if (BlockRailBase.func_150051_a(block)) {
            int l = ((BlockRailBase)block).getBasicRailMetadata(cart.worldObj, cart, i, j, k);
            p_70489_3_ = j;

            if (l >= 2 && l <= 5) {
                p_70489_3_+=1;
            }

            int[][] aint = matrix[l];
            double d3 = 0.0D;
            double d4 = 0.5D + i + aint[0][0] * 0.5D;
            double d5 = 0.5D + j + aint[0][1] * 0.5D;
            double d6 = 0.5D + k + aint[0][2] * 0.5D;
            double d7 = 0.5D + i + aint[1][0] * 0.5D;
            double d8 = 0.5D + j + aint[1][1] * 0.5D;
            double d9 = 0.5D + k + aint[1][2] * 0.5D;
            double d10 = d7 - d4;
            double d11 = (d8 - d5) * 2.0D;
            double d12 = d9 - d6;

            if (d10 == 0.0D) {
                p_70489_1_ = i + 0.5D;
                d3 = p_70489_5_ - k;
            } else if (d12 == 0.0D) {
                p_70489_5_ = k + 0.5D;
                d3 = p_70489_1_ - i;
            } else {
                d3 = ((p_70489_1_ - d4) * d10 + (p_70489_5_ - d6) * d12) * 2.0D;
            }

            p_70489_1_ = d4 + d10 * d3;
            p_70489_3_ = d5 + d11 * d3;
            p_70489_5_ = d6 + d12 * d3;

            if (d11 < 0.0D) {
                p_70489_3_+=1;
            } else if (d11 > 0.0D) {
                p_70489_3_ += 0.5D;
            }

            return Vec3.createVectorHelper(p_70489_1_, p_70489_3_, p_70489_5_);
        } else {
            return null;
        }
    }

}
