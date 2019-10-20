package ebf.tim.api;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class skin {
    public ResourceLocation texture;
    public ResourceLocation[] bogieTextures, subBogieTextures;
    public int[] colorsFrom, colorsTo;
    public String name, modid, description;
    public int id;

    public skin(String modId, String texture, String name, String descriotion, int id){
        this.texture=new ResourceLocation(modId,texture);
        this.description=descriotion;
        this.name=name;
        this.id=id;
    }

    public ResourceLocation getBogieSkin(int index){
        if(bogieTextures ==null){
            return null;
        }
        if(bogieTextures.length>index){
            return bogieTextures[index];
        }

        return null;
    }

    public ResourceLocation getSubBogieSkin(int index){
        if(subBogieTextures ==null){
            return null;
        }
        if(subBogieTextures.length>index){
            return subBogieTextures[index];
        }

        return null;
    }

    public String[] getDescription(){return description.split("\n");}


    public skin setBogieTextures(String... textures){
        bogieTextures=resourceList(modid,textures);
        return this;
    }
    public skin setSubBogieTextures(String... textures){
        subBogieTextures=resourceList(modid,textures);
        return this;
    }
    public skin setRecolorsTo(int... recolorsTo){
        colorsTo=recolorsTo;
        return this;
    }

    public skin setRecolorsFrom(int... recolorsFrom){
        colorsFrom=recolorsFrom;
        return this;
    }


    private static ResourceLocation[] resourceList(String modid, String[] URIs){
        ResourceLocation[] value = new ResourceLocation[URIs.length];
        for (int i=0; i< URIs.length; i++){
            if(URIs[i].contains(":")){
                value[i] = new ResourceLocation(URIs[i]);
            } else {
                value[i] = new ResourceLocation(modid, URIs[i]);
            }
        }
        return value;
    }
}
