package trains.entities.render;


import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.ObjModelLoader;
import org.lwjgl.opengl.GL11;
import trains.entities.EntityTrainCore;
import trains.entities.MinecartExtended;

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
        //TODO set the GL Translation to a vector between the front and back bogies of the entity.
        GL11.glTranslated(x, y, z);
        GL11.glPushMatrix();

        /**
         * now we have to handle rotation, assuming the entity is an instance of MinecartExtended this is a lot more complicated because we have to define rotation based on it's current and previous positions.
         */

        //first initialize the variables
        if (entity instanceof EntityTrainCore) {
            EntityTrainCore cart = (EntityTrainCore) entity;

            /**
             * now we actually set the values we just finished defining.
             * because rotation is set on (amount, xaxis, yaxis, xaxis) we have to rotate each axis individually, then push
             */
            GL11.glRotatef(cart.rotationYaw, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(cart.rotationPitch, 0.0F, 0.0F, 1.0F);
            GL11.glPushMatrix();
        }

        //Bind the texture and render the model
        bindTexture(texture);
        model.renderAll();

        //clear the cache, one pop for every push.
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        if (entity instanceof EntityTrainCore) {
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
        texture = textureLoad;
    }


    /**
     * these functions are for calculating the vectors from the metadata in rails, this will long run ell the ernder which direction to rotate the model.
     */
    private Vec3 func_70495_a(MinecartExtended cart, double X, double Y, double Z, double p_70495_7_) {
        int i = MathHelper.floor_double(X);
        int j = MathHelper.floor_double(Y);
        int k = MathHelper.floor_double(Z);

        if (BlockRailBase.func_150049_b_(cart.worldObj, i, j - 1, k)) {
            --j;
        }

        Block block = cart.worldObj.getBlock(i, j, k);

        if (BlockRailBase.func_150051_a(block)) {
            int l = ((BlockRailBase)block).getBasicRailMetadata(cart.worldObj, cart, i, j, k);

            Y = (double)j;

            if (l >= 2 && l <= 5) {
                Y = (double)(j + 1);
            }

            int[][] aint = matrix[l];
            double d4 = (aint[1][0] - aint[0][0]);
            double d5 = (aint[1][2] - aint[0][2]);
            double d6 = Math.sqrt(d4 * d4 + d5 * d5);
            d4 /= d6;
            d5 /= d6;
            X += d4 * p_70495_7_;
            Z += d5 * p_70495_7_;

            if (aint[0][1] != 0 && MathHelper.floor_double(X) - i == aint[0][0] && MathHelper.floor_double(Z) - k == aint[0][2]) {
                Y += aint[0][1];
            } else if (aint[1][1] != 0 && MathHelper.floor_double(X) - i == aint[1][0] && MathHelper.floor_double(Z) - k == aint[1][2]) {
                Y += aint[1][1];
            }

            return Vec3.createVectorHelper(X, Y, Z);
        } else {
            return null;
        }
    }

}
