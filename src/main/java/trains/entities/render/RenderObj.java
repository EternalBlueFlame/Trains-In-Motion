package trains.entities.render;


import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.ObjModelLoader;
import org.lwjgl.opengl.GL11;
import trains.TrainsInMotion;
import trains.entities.MinecartExtended;
import trains.utility.ClientProxy;

import java.io.File;

public class RenderObj extends Render {

    private IModelCustom model;
    private ResourceLocation texture;

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
            if (cart.cartTurnProgress != 0) {
                vec3 = cart.func_70489_a(
                        cart.lastTickPosX + ((cart.cartX - cart.lastTickPosX) / cart.cartTurnProgress) * (double) partialTick,
                        cart.lastTickPosY + ((cart.cartY - cart.lastTickPosY) / cart.cartTurnProgress) * (double) partialTick,
                        cart.lastTickPosZ + ((cart.cartZ - cart.lastTickPosZ) / cart.cartTurnProgress) * (double) partialTick
                );
            } else {
                vec3 = cart.func_70489_a(
                        cart.lastTickPosX + (cart.cartX - cart.lastTickPosX) * (double) partialTick,
                        cart.lastTickPosY + (cart.cartY - cart.lastTickPosY) * (double) partialTick,
                        cart.lastTickPosZ + (cart.cartZ - cart.lastTickPosZ) * (double) partialTick
                );
            }

            //now assuming the main vector isn't null, calculate the transition vector
            if (vec3 != null) {
                //first we create vectors for the transition
                Vec3 vec31 = cart.func_70495_a(vec3.xCoord, vec3.yCoord, vec3.zCoord, 0.30000001192092896D);
                Vec3 vec32 = cart.func_70495_a(vec3.xCoord, vec3.yCoord, vec3.zCoord, -0.30000001192092896D);

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


    public RenderObj(ResourceLocation modelLoad, ResourceLocation textureLoad) {
        model = new ObjModelLoader().loadInstance(modelLoad);
        if (new File(textureLoad.getResourcePath()).exists()) {
            texture = textureLoad;
        } else{
            texture = TrainsInMotion.Resources.TEXTURE.getResourceLocation("null.png");
        }
    }
}
