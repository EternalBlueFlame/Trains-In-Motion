package trains.entities.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import trains.TrainsInMotion;
import trains.entities.MinecartExtended;
import trains.utility.ClientProxy;

public class RenderCore extends RenderBiped {

    /**
     * this class defines basic .java rendering,
     * the model is defined in
     * @see ClientProxy#registerRenderers()
     *
     * @param model defines the model to be used
     * @param shadowSize defines the size of the shadow that the model should have
     */
    public RenderCore(ModelBiped model, float shadowSize) {
        super(model, shadowSize);
    }

    /**
     * this defines the texture of the model, based on the value of the MinecartType
     * @see MinecartExtended#getMinecartType()
     *
     *
     * @param entity
     * @return
     */
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        if (entity instanceof MinecartExtended) {
            switch (((MinecartExtended) entity).getMinecartType()) {
                //this is for FirstTrain
                case 1001: {
                    return new ResourceLocation(TrainsInMotion.Resources.MODEL_TRAIN + "entity.png");
                }
                //all else fails, return a null texture, because pink and black is always in style.
                default: {
                    return new ResourceLocation(TrainsInMotion.Resources.MODEL_TRAIN + "null.png");
                }
            }
        } else {
            //all else fails, return a null texture, because pink and black is always in style.
            return new ResourceLocation(TrainsInMotion.Resources.MODEL_TRAIN + "null.png");
        }
    }
}