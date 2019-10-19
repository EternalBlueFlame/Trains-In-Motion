package ebf.tim.api;

import cpw.mods.fml.common.Loader;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.utility.DebugUtil;
import ebf.tim.utility.RailUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkinRegistry {

    public static boolean forceSkinRegister=false,debugSkinRegistration=true;
    private static Map<String, Map<String, skin>> transports = new HashMap<String, Map<String, skin>>();
    public static Map<String, skin> getTransportSkins(Class c){
        return transports.containsKey(c.getName())?transports.get(c.getName()):null;
    }

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

    public static void addSkinRecolor(Class c,String modid, String textureURI, @Nullable int[][] recolor, String skinName, String skinDescription){
        addSkinRecolor(c.getName(),modid,textureURI,null,null,recolor,skinName,skinDescription);
    }

    public static void addSkinRecolor(Class c,String modid, String textureURI, String[] bogieTextureURI, @Nullable int[][] recolor, String skinName, String skinDescription){
        addSkinRecolor(c.getName(),modid,textureURI,bogieTextureURI,null,recolor,skinName,skinDescription);
    }

    public static void addSkinRecolor(Class c,String modid, String textureURI, String[] bogieTextureURI,String[] subBogieTextureURI, @Nullable int[][] recolor, String skinName, String skinDescription){
        addSkinRecolor(c.getName(),modid,textureURI,bogieTextureURI,subBogieTextureURI,recolor,skinName,skinDescription);
    }

    public static void addSkinRecolor(String c,String modid, String textureURI, String[] bogieTextureURI, String[] subBogieTextureURI, @Nullable int[][] recolor, String skinName, String skinDescription){

        if (debugSkinRegistration) {
            DebugUtil.println("REGISTERING SKIN", c, "MODID: " + modid, textureURI, skinName, Loader.isModLoaded(modid));
        }

        if(Loader.isModLoaded(modid) || forceSkinRegister) {
            if (!transports.containsKey(c)) {
                transports.put(c, new HashMap<String, skin>());
            }
            if(transports.get(c).containsKey(modid + ":" + skinName)){
                DebugUtil.println("ERROR", "Duplicate skin entry: " + skinName, "In entity: " + c, "Overriding original entry");
            }
            transports.get(c).put(modid + ":" + skinName,
                    new skin(new ResourceLocation(modid, textureURI), resourceList(modid, bogieTextureURI), resourceList(modid, subBogieTextureURI), recolor, skinName, modid, skinDescription, transports.get(c).size()));
        }
    }

    private static ResourceLocation[] resourceList(String modid, String[] URIs){
        if(URIs == null || URIs.length==0){
            return null;
        }
        ResourceLocation[] value = new ResourceLocation[URIs.length];
        for (int i=0; i< URIs.length; i++){
            value[i]= new ResourceLocation(modid, URIs[i]);
        }
        return value;
    }

    public static ResourceLocation getTexture(GenericRailTransport entity, EntityPlayer player, boolean isPaintBucket, String modid, String textureURI){
        if (entity.getSkinList(player, isPaintBucket)==null || !entity.getSkinList(player, isPaintBucket).containsKey(modid + ":" + textureURI)){
            return null;
        }
        return entity.getSkinList(player, isPaintBucket).get(modid + ":" + textureURI).texture;
    }

    public static skin getSkin(GenericRailTransport entity, EntityPlayer player, boolean isPaintBucket, String internalResourceURI){
        if (entity.getSkinList(player, isPaintBucket)==null || !entity.getSkinList(player, isPaintBucket).containsKey(internalResourceURI)){
            return null;
        }
        return entity.getSkinList(player, isPaintBucket).get(internalResourceURI);
    }

    public static ResourceLocation getTexture(GenericRailTransport entity, EntityPlayer player, boolean isPaintBucket, String internalResourceURI){
        if (entity.getSkinList(player, isPaintBucket)==null || !entity.getSkinList(player, isPaintBucket).containsKey(internalResourceURI)){
            return null;
        }
        return entity.getSkinList(player, isPaintBucket).get(internalResourceURI).texture;
    }

    public static String getSkinName(GenericRailTransport entity, EntityPlayer player, boolean isPaintBucket, String modid, String textureURI){
        return RailUtility.translate(entity.getSkinList(player,isPaintBucket).get(modid + ":" + textureURI).name);
    }

    public static String[] getSkinDescription(GenericRailTransport entity, EntityPlayer player, boolean isPaintBucket,String modid, String textureURI){
        return RailUtility.multiTranslate(entity.getSkinList(player,isPaintBucket).get(modid + ":" + textureURI).getDescription());
    }

}
