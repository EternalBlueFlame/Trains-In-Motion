package ebf.tim.registry;

import net.minecraft.util.ResourceLocation;

import static ebf.tim.TrainsInMotion.MODID;

/**
 * <h1> Resource Locations</h1>
 * Similar to android design, we use enums to define URI's for individual things, to keep it organized.
 * this is less of a registry and more of a list, because nothing is actually registered, this is just variables.
 * but it makes more sense to organize it with the registries then in a folder of it's own.
 * @author Eternal Blue Flame
 */
public enum URIRegistry {

    GUI_PREFIX("textures/gui/"),
    MODEL_TRAIN_TEXTURE("textures/train"),
    ITEM_TRANSPORT_ICON("item/transport/"),
    MODEL_ROLLINGSTOCK_TEXTURE("textures/rollingstock/"),
    MODEL_RAIL_TEXTURE("textures/rail/"),
    MODEL_BLOCK_TEXTURE("textures/block/"),
    TEXTURE_GENERIC("textures/generic/"),
    TEXTURE_BLOCK("textures/block/"),
    SOUND_HORN("audio/horns/"),
    SOUND_RUNNING("audio/running/");

        private String value;
        URIRegistry(String value) {this.value = value;}
        public String getValue(){return value;}

    public ResourceLocation getResource(String fileName){
            return new ResourceLocation(MODID, value + fileName);
    }
}
