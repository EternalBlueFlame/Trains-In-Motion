package ebf.tim.api;

import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class skin {
    public ResourceLocation texture;
    public ResourceLocation[] bogieTextures;
    public ResourceLocation[] subBogieTextures;
    public List<int[]> partialRecolors = new ArrayList<>();
    public String name, modid;
    private String description;
    public int id;

    public skin(ResourceLocation texture, @Nullable ResourceLocation[] bogieTextures, @Nullable ResourceLocation[] subBogieTextures, @Nullable int[][] recolor, String skinName, String mod, String skinDescription, int id){
        this.texture=texture;
        name=skinName;
        this.modid=mod;
        description=skinDescription;
        partialRecolors = recolor==null?null: Arrays.asList(recolor);
        this.bogieTextures= bogieTextures;
        this.subBogieTextures= subBogieTextures;
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
}
