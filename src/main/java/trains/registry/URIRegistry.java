package trains.registry;

import net.minecraft.util.ResourceLocation;

import static trains.TrainsInMotion.MODID;

/**
 * <h2> Resource Locations</h2>
 * Similar to android design, we use enums to define URI's for individual things, to keep it organized.
 * this is less of a registry and more of a list, because nothing is actually registered, this is just variables.
 * but it makes more sense to organize it with the registries then in a folder of it's own.
 */
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
