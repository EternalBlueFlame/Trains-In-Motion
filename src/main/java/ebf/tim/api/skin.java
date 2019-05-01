package ebf.tim.api;

import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class skin {
    public ResourceLocation texture;
    public ResourceLocation[] bogieTextures;
    public ResourceLocation[] subBogieTextures;
    public List<int[]> partialRecolors = new ArrayList<>();
    public String name;
    private String description;

    public skin(ResourceLocation texture, @Nullable ResourceLocation[] bogieTextures, @Nullable ResourceLocation[] subBogieTextures, @Nullable int[][] recolor, String skinName, String skinDescription){
        this.texture=texture;
        name=skinName;
        description=skinDescription;
        partialRecolors = recolor==null?null:Arrays.asList(recolor);
        this.bogieTextures= bogieTextures;
        this.subBogieTextures= subBogieTextures;
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
