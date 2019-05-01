package ebf.tim.api;

import cpw.mods.fml.common.Loader;
import ebf.tim.utility.DebugUtil;
import ebf.tim.utility.RailUtility;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class SkinRegistry {

    public static boolean forceSkinRegister=false,debugSkinRegistration=true;
    public static Map<String, Map<String, skin>> transports = new HashMap<String, Map<String, skin>>();

    public static void addSkin(Class c, String modid, String textureURI, String name, String description){
        addSkinRecolor(c.getName(),modid,textureURI,null, null, null,name,description);
    }

    public static void addSkin(Class c, String modid, String textureURI, String bogieTextureURI, String name, String description){
        addSkinRecolor(c.getName(),modid,textureURI,new String[]{bogieTextureURI}, null,null,name,description);
    }
    public static void addSkin(Class c, String modid, String textureURI, String bogieTextureURI, String subBogieTextureURI, String name, String description){
        addSkinRecolor(c.getName(),modid,textureURI,new String[]{bogieTextureURI}, new String[]{subBogieTextureURI},null,name,description);
    }

    public static void addSkin(Class c, String modid, String textureURI, @Nullable String[] bogieTextureURIs, String name, String description){
        addSkinRecolor(c.getName(),modid,textureURI,bogieTextureURIs,null,null,name,description);
    }

    public static void addSkin(Class c, String modid, String textureURI, @Nullable String[] bogieTextureURIs,@Nullable String[] subBogieTextureURIs, String name, String description){
        addSkinRecolor(c.getName(),modid,textureURI,bogieTextureURIs,subBogieTextureURIs,null,name,description);
    }

    public static void addSkin(String c, String modid, String textureURI, String name, String description){
        addSkinRecolor(c,modid,textureURI,null,null, null,name,description);
    }

    public static void addSkin(String c, String modid, String textureURI, String bogieTextureURI, String name, String description){
        addSkinRecolor(c,modid,textureURI,new String[]{bogieTextureURI},null,null,name,description);
    }

    public static void addSkin(String c, String modid, String textureURI, String bogieTextureURI, String subBogieTextureURI, String name, String description){
        addSkinRecolor(c,modid,textureURI,new String[]{bogieTextureURI},new String[]{subBogieTextureURI},null,name,description);
    }

    public static void addSkin(String c, String modid, String textureURI, @Nullable String[] bogieTextureURIs, String name, String description){
        addSkinRecolor(c,modid,textureURI,bogieTextureURIs,null,null,name,description);
    }

    public static void addSkin(String c, String modid, String textureURI, @Nullable String[] bogieTextureURIs,  @Nullable String[] subBogieTextureURIs, String name, String description){
        addSkinRecolor(c,modid,textureURI,bogieTextureURIs,subBogieTextureURIs,null,name,description);
    }

    public static void addSkinRecolor(Class c,String modid, String textureURI, String[] bogieTextureURI, @Nullable int[][] recolor, String skinName, String skinDescription){
        addSkinRecolor(c.getName(),modid,textureURI,bogieTextureURI,null,recolor,skinName,skinDescription);
    }

    public static void addSkinRecolor(Class c,String modid, String textureURI, String[] bogieTextureURI,String[] subBogieTextureURI, @Nullable int[][] recolor, String skinName, String skinDescription){
        addSkinRecolor(c.getName(),modid,textureURI,bogieTextureURI,subBogieTextureURI,recolor,skinName,skinDescription);
    }

    public static void addSkinRecolor(String c,String modid, String textureURI, String[] bogieTextureURI, String[] subBogieTextureURI, @Nullable int[][] recolor, String skinName, String skinDescription){
        if(Loader.isModLoaded(modid) || forceSkinRegister) {
            if (debugSkinRegistration) {
                DebugUtil.println("REGISTERING SKIN", c, "MODID: " + modid, textureURI, skinName);
            }
            if (!transports.containsKey(c)) {
                transports.put(c, new HashMap<String, skin>());
                //add the default/null skin
                transports.get(c).put(modid + ":" + "-1", new skin(new ResourceLocation(modid, textureURI), resourceList(modid, bogieTextureURI), resourceList(modid, subBogieTextureURI), recolor, skinName, skinDescription));
            }
            transports.get(c).put(modid + ":" + textureURI, new skin(new ResourceLocation(modid, textureURI), resourceList(modid, bogieTextureURI), resourceList(modid, subBogieTextureURI), recolor, skinName, skinDescription));
        }
    }

    private static ResourceLocation[] resourceList(String modid, String[] URIs){
        if(URIs == null){
            return null;
        }
        ResourceLocation[] value = new ResourceLocation[URIs.length];
        for (int i=0; i< URIs.length; i++){
            value[i]= new ResourceLocation(modid, URIs[i]);
        }
        return value;
    }

    public static ResourceLocation getTexture(Class c,String modid, String textureURI){
        if (!transports.containsKey(c.getName()) || !transports.get(c.getName()).containsKey(modid + ":" + textureURI)){
            return null;
        }
        return transports.get(c.getName()).get(modid + ":" + textureURI).texture;
    }

    public static skin getSkin(Class c, String internalResourceURI){
        if (!transports.containsKey(c.getName()) || !transports.get(c.getName()).containsKey(internalResourceURI)){
            return null;
        }
        return transports.get(c.getName()).get(internalResourceURI);
    }

    public static ResourceLocation getTexture(Class c, String internalResourceURI){
        if (!transports.containsKey(c.getName()) || !transports.get(c.getName()).containsKey(internalResourceURI)){
            return null;
        }
        return transports.get(c.getName()).get(internalResourceURI).texture;
    }

    public static ResourceLocation getDefaultTexture(Class c){
        if (!transports.containsKey(c.getName()) || transports.get(c.getName()).size()<1){
            return null;
        }
        return transports.get(c.getName()).values().iterator().next().texture;
    }

    public static String getSkinName(Class c,String modid, String textureURI){
        return RailUtility.translate(transports.get(c.getName()).get(modid + ":" + textureURI).name);
    }

    public static String[] getSkinDescription(Class c,String modid, String textureURI){
        return RailUtility.multiTranslate(transports.get(c.getName()).get(modid + ":" + textureURI).getDescription());
    }

}
