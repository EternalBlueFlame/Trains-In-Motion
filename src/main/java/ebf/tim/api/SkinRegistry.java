package ebf.tim.api;

import ebf.tim.utility.RailUtility;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkinRegistry {

    public static Map<String, Map<String, skin>> transports = new HashMap<String, Map<String, skin>>();

    public static void addSkin(Class c,String modid, String textureURI, @Nullable int[][] recolor, String skinName, String skinDescription){
        if (!transports.containsKey(c.getName())){
            transports.put(c.getName(), new HashMap<String, skin>());
            //add the default/null skin
            transports.get(c.getName()).put(modid + ":" + "-1", new skin(new ResourceLocation(modid, textureURI), recolor, skinName, skinDescription));
        }
        transports.get(c.getName()).put(modid + ":" + textureURI, new skin(new ResourceLocation(modid, textureURI), recolor, skinName, skinDescription));
    }

    public static ResourceLocation getTexture(Class c,String modid, String textureURI){
        if (!transports.containsKey(c.getName()) || !transports.get(c.getName()).containsKey(modid + ":" + textureURI)){
            return null;
        }
        return transports.get(c.getName()).get(modid + ":" + textureURI).texture;
    }

    public static ResourceLocation getTexture(Class c, String internalResourceURI){
        if (!transports.containsKey(c.getName()) || !transports.get(c.getName()).containsKey(internalResourceURI)){
            return null;
        }
        return transports.get(c.getName()).get(internalResourceURI).texture;
    }

    public static String getSkinName(Class c,String modid, String textureURI){
        return RailUtility.translate(transports.get(c.getName()).get(modid + ":" + textureURI).name);
    }

    public static String getSkinDescription(Class c,String modid, String textureURI){
        return RailUtility.translate(transports.get(c.getName()).get(modid + ":" + textureURI).description);
    }

}
