package fexcraft.tmt.slim;

import ebf.tim.TrainsInMotion;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.DebugUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.util.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;

public class TextureManager {


    public static ByteBuffer renderPixels = ByteBuffer.allocateDirect((4096*4096)*4);
    private static int i,ii, length, skyLight;
    private static int[] RGBint, pixels;
    private static final byte fullAlpha=(byte)0;
    private static Set MCResourcePacks;

    //2 bytes for idk GL does something with them, then 4 bytes per pixel at 4kx4k resolution. any bigger breaks intel GPU's anyway.
    private static ByteBuffer bufferTexturePixels = GLAllocation.createDirectByteBuffer(67108866);

    public static int[] ingotColors = new int[]{};


    private static Map<ResourceLocation, Integer> tmtMap = new HashMap<>();

    private static Map<ResourceLocation, int[]> tmtTextureMap = new HashMap<>();

    private static ITextureObject object;
    /**
     * custom texture binding method, generally same as vanilla, but possible to improve performance later.
     * @param textureURI
     */
    public static boolean bindTexture(ResourceLocation textureURI) {
        if (textureURI == null){
            textureURI= new ResourceLocation(TrainsInMotion.MODID,"nullTrain");
        }
        if(ClientProxy.ForceTextureBinding) {
            object = Minecraft.getMinecraft().getTextureManager().getTexture(textureURI);
            if (object == null) {
                object = new SimpleTexture(textureURI);
                Minecraft.getMinecraft().getTextureManager().loadTexture(textureURI, object);
            }
            GL11.glBindTexture(GL_TEXTURE_2D, object.getGlTextureId());
        } else {
            Integer id = tmtMap.get(textureURI);
            if (id ==null){
                object = Minecraft.getMinecraft().getTextureManager().getTexture(textureURI);
                if (object == null) {
                    object = new SimpleTexture(textureURI);
                    Minecraft.getMinecraft().getTextureManager().loadTexture(textureURI, object);
                }
                id=object.getGlTextureId();
                tmtMap.put(textureURI, id);
            }
            if(GL11.glGetInteger(GL_TEXTURE_2D) !=id) {
                GL11.glBindTexture(GL_TEXTURE_2D, id);
            }
        }
        return true;
    }

    public static int[] loadTexture(ResourceLocation resource){
        //if the list of loaded resource packs has changed, invalidate our texture cache as well.
        if(Minecraft.getMinecraft().getResourceManager().getResourceDomains() != MCResourcePacks){
            MCResourcePacks = Minecraft.getMinecraft().getResourceManager().getResourceDomains();
            tmtTextureMap =new HashMap<>();
        }

        int[] texture = tmtTextureMap.get(resource);

        bindTexture(resource);
        if(texture==null){
            int width =glGetTexLevelParameteri(GL_TEXTURE_2D, 0, GL_TEXTURE_WIDTH);
            int height =glGetTexLevelParameteri(GL_TEXTURE_2D, 0, GL_TEXTURE_HEIGHT);

            GL11.glGetTexImage(GL_TEXTURE_2D, 0, GL11.GL_RGBA, GL_UNSIGNED_BYTE, bufferTexturePixels);

            texture = new int[((width*height)*4)+2];
            texture[0]=width;
            texture[1]=height;
            for (int i=2; i<((width*height)*(4))-2; i+=(4)){
                texture[i+3]=bufferTexturePixels.get(i+3);//alpha
                texture[i+2]=bufferTexturePixels.get(i+2);//Red
                texture[i+1]=bufferTexturePixels.get(i+1);//Green
                texture[i]=bufferTexturePixels.get(i);//Blue
            }
            tmtTextureMap.put(resource, texture);
        }

        return texture;
    }




    public static void maskColors(ResourceLocation textureURI, List<int[]> colors){
        pixels = loadTexture(textureURI);
        if(pixels.length==2){
            return;
        }
        length = ((pixels[0]*pixels[1])*4)-4;

        for(i=0; i<length; i+=4) {
            renderPixels.put(i+3, b(pixels[i+3]));//alpha is always from host texture.
            if (pixels[i+3] == fullAlpha){
                continue;//skip pixels with no color
            }
            //for each set of recoloring
            if (colors!=null) {
                for (ii = 0; ii < colors.size(); ii++) {
                    RGBint = colors.get(ii);
                    //if it's within 10 RGB, add the actual color we want to the differences
                    if (colorInRange(pixels[i] & 0xFF, pixels[i + 1] & 0xFF, pixels[i + 2] & 0xFF,
                            RGBint[0] & 0xFF, (RGBint[0] >> 8) & 0xFF, (RGBint[0] >> 16) & 0xFF)) {
                        renderPixels.put(i, b(RGBint[1]));
                        renderPixels.put(i + 1, b(RGBint[1] >> 8));
                        renderPixels.put(i + 2, b(RGBint[1] >> 16));
                    } else {
                        renderPixels.put(i, b(pixels[i]));
                        renderPixels.put(i + 1, b(pixels[i + 1]));
                        renderPixels.put(i + 2, b(pixels[i + 2]));
                    }
                }
            } else {
                renderPixels.put(i, b(pixels[i]));
                renderPixels.put(i + 1, b(pixels[i + 1]));
                renderPixels.put(i + 2, b(pixels[i + 2]));
            }
        }

        glTexSubImage2D (GL_TEXTURE_2D, 0, 0, 0, pixels[0], pixels[1], GL_RGBA, GL_UNSIGNED_BYTE, renderPixels);
        renderPixels.clear();//reset the buffer to all 0's.
    }

    //most compilers should process this type of function faster than a normal typecast.
    public static byte b(int i){return (byte) i;}

    public static boolean colorInRange(int r, int g, int b, int oldR, int oldG, int oldB){
        return oldR-r>-15 && oldR-r <15 &&
                oldG-g>-15 && oldG-g <15 &&
                oldB-b>-15 && oldB-b <15;
    }


    /**Lighting fix*/
    public static void adjustLightFixture(World world, int i, int j, int k) {
        skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  skyLight % 65536,  skyLight / 65536f);
    }

    /**
     * Ingot color textures
     */


    public static void collectIngotColors(){
        String[] ores = OreDictionary.getOreNames();

        Map<String, int[]> colors = new TreeMap<>();//map of delegate, color
        for(String o: ores){
            if (o.contains("ingot")){
                for (ItemStack s : OreDictionary.getOres(o)){
                    IIcon itm = s.getItem().getIcon(s,0);

                    bindTexture(TextureMap.locationItemsTexture);

                    int texturewidth=GL11.glGetTexLevelParameteri(GL_TEXTURE_2D, 0, GL_TEXTURE_WIDTH);

                    /*GL11.glGetTexImage(GL_TEXTURE_2D, 0, GL11.GL_RGBA, GL_UNSIGNED_BYTE, bufferTexturePixels);
                    int width = (int) ((itm.getMaxU()-itm.getMinU())*16f);
                    int height = (int) ((itm.getMaxV()-itm.getMinV())*16f);
                    int[] texture = new int[((width*height)*4)+2];
                    i=0;

                    //loop for the pixels fo the texture and collect the entire texture
                    for (int x=(int)(itm.getMinU()*16f); x<itm.getMaxU()*16f; x++){
                        for (int y=(int)(itm.getMinV()*16f); y<itm.getMaxV()*16f; y++){
                            int pos = (texturewidth*y)+x;
                            texture[i+3]=bufferTexturePixels.get(pos+3);//alpha
                            texture[i+2]=bufferTexturePixels.get(pos+2);//Red
                            texture[i+1]=bufferTexturePixels.get(pos+1);//Green
                            texture[i]=bufferTexturePixels.get(pos);//Blue
                            i++;
                        }
                    }*/



                    pixels = loadTexture(TextureMap.locationItemsTexture);
                    if(pixels.length==2){
                        return;
                    }
                    length = ((pixels[0]*pixels[1])*4)-4;


                    //todo: we got the sprite in pixels now, so we need to average out the color
                    // add all the colors, then divide by how many were actually added
                    //this could probably be compressed into the above method.
                    int[] color={0,0,0};
                    int divisor=0;
                    int x=-1,y=0;
                    int[] pixel = new int[]{0,0,0,0};
                    for(i=0; i<length; i+=4) {
                        x++;
                        if(x>texturewidth){
                            x=0;
                            y++;
                        }
                        ////if(x<minx||x>maxX||y<miny||y>maxy){
                          //  continue;
                       // }

                        pixel[3]=pixels[i+3];//alpha is always from host texture.
                        if (pixels[i+3] == fullAlpha){
                            continue;//skip pixels with no color
                        }



                        divisor++;
                        color[0]+=pixels[i];
                        color[1]+=pixels[i+1];
                        color[2]+=pixels[i+2];
                    }


                    DebugUtil.println(" colors for ingot " + s.getDisplayName(),color[0]+":"+color[1]+":"+color[2]);

                    if(divisor!=0) {
                        color[0] = color[0] / divisor;
                        color[1] = color[1] / divisor;
                        color[2] = color[2] / divisor;
                    }

                    //now add to list
                    colors.put(s.getItem().delegate.name(), color);
                }
            }
        }




    }
}
