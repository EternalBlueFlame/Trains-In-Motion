package ebf.tim.api;

import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class skin {
    public ResourceLocation texture;
    public ResourceLocation[] bogieTextures;
    public List<int[]> partialRecolors = new ArrayList<>();
    public String name;
    public String description;

    public skin(ResourceLocation texture, @Nullable ResourceLocation[] bogieTextures, @Nullable int[][] recolor, String skinName, String skinDescription){
        this.texture=texture;
        name=skinName;
        description=skinDescription;
        partialRecolors = recolor==null?null:Arrays.asList(recolor);
        this.bogieTextures= bogieTextures;
    }

    public ResourceLocation getBogieSkin(int index){
        if(bogieTextures ==null){
            return null;
        }
        if(bogieTextures.length>index){
            return bogieTextures[index];
        }

        return bogieTextures[0];
    }
}
