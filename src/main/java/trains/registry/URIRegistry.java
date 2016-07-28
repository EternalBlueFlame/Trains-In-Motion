package trains.registry;

import net.minecraft.util.ResourceLocation;

import static trains.TrainsInMotion.MODID;

public enum URIRegistry {

    GUI_PREFIX("textures/gui/"),
    MODEL_TRAIN("models/train/"),
    MODEL_TRAIN_TEXTURE("models/train/texture/"),
    MODEL_ROLLINGSTOCK("models/rollingstock/"),
    MODEL_ROLLINGSTOCK_TEXTURE("models/rollingstock/texture/"),
    MODEL_RAIL("models/rail/"),
    MODEL_RAIL_TEXTURE("models/rail/textures/"),
    MODEL_BLOCK("models/block/"),
    MODEL_BLOCK_TEXTURE("models/block/texture"),
    TEXTURE_GENERIC("textures/generic/"),
    TEXTURE_BLOCK("textures/block/");

        private String value;
        URIRegistry(String value) {this.value = value;}
        public String getValue(){return value;}

    public ResourceLocation getResource(String fileName){
            return new ResourceLocation(MODID, value + fileName);
    }
}
