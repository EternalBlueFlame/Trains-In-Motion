package ebf.tim.api;

import ebf.tim.utility.RailUtility;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkinRegistry {

    public static Map<String, Map<String, skin>> transports = new HashMap<String, Map<String, skin>>();

    public static void addSkin(Class c,String modid, String textureURI, @Nullable List<int[]> recolor, String skinName, String skinDescription){
        if (!transports.containsKey(c.getName())){
            transports.put(c.getName(), new HashMap<String, skin>());
        }
        transports.get(c.getName()).put(modid + ":" + textureURI, new skin(new ResourceLocation(modid, textureURI), recolor, skinName, skinDescription));
    }

    public ResourceLocation getTexture(Class c,String modid, String textureURI){
        return transports.get(c.getName()).get(modid + ":" + textureURI).texture;
    }

    public String getSkinName(Class c,String modid, String textureURI){
        return RailUtility.translate(transports.get(c.getName()).get(modid + ":" + textureURI).name);
    }

    public String getSkinDescription(Class c,String modid, String textureURI){
        return RailUtility.translate(transports.get(c.getName()).get(modid + ":" + textureURI).description);
    }

}
