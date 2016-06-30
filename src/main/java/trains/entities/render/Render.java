package trains.entities.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import trains.TrainsInMotion;

public class Render extends RenderBiped {

    public Render(ModelBiped model, float shadowSize) {
        super(model, shadowSize);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        //switch on entity to get necessary texture
        return new ResourceLocation(TrainsInMotion.enumResources.MODEL_TRAIN + "entity.png");
    }
}