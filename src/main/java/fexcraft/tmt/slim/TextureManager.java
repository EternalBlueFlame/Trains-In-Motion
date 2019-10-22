package fexcraft.tmt.slim;

import ebf.tim.TrainsInMotion;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.DebugUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import sun.awt.image.InputStreamImageSource;
import sun.awt.image.PNGImageDecoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.lwjgl.opengl.GL11.*;

public class TextureManager {


    public static ByteBuffer renderPixels = ByteBuffer.allocateDirect((4096*4096)*4);
    private static int  skyLight;
    private static int[] RGBint, pixels;
    private static final byte fullAlpha=(byte)0;
    private static Set<?> MCResourcePacks;

    public static Map<ItemStack,int[]> ingotColors = new HashMap<>();


    private static Map<ResourceLocation, Integer> tmtMap = new HashMap<>();

    private static Map<String, int[]> tmtTextureMap = new HashMap<>();

    private static ITextureObject object;

    /**
     * custom texture binding method, generally same as vanilla, but possible to improve performance later.
     * @param textureURI
     */
    public static void bindTexture(ResourceLocation textureURI) {
        if (textureURI == null){
            textureURI= new ResourceLocation(TrainsInMotion.MODID,"nullTrain");
        }
        if(ClientProxy.ForceTextureBinding) {
            ITextureObject object = Minecraft.getMinecraft().getTextureManager().getTexture(textureURI);
            if (object == null) {
                object = new SimpleTexture(textureURI);
                Minecraft.getMinecraft().getTextureManager().loadTexture(textureURI, object);
            }
            GL11.glBindTexture(GL_TEXTURE_2D, object.getGlTextureId());
        } else {
            Integer id = tmtMap.get(textureURI);
            if (id ==null){
                ITextureObject object = Minecraft.getMinecraft().getTextureManager().getTexture(textureURI);
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
    }

/*

    public static int[] loadTexture(ResourceLocation resource, List<Integer> mask){
        int[] texture = tmtTextureMap.get(resource);

        bindTexture(resource);
        if(texture==null){
            int width =glGetTexLevelParameteri(GL_TEXTURE_2D, 0, GL_TEXTURE_WIDTH);
            int height =glGetTexLevelParameteri(GL_TEXTURE_2D, 0, GL_TEXTURE_HEIGHT);

            ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

            GL11.glGetTexImage(GL_TEXTURE_2D, 0, GL11.GL_RGBA, GL_UNSIGNED_BYTE, buffer);

            texture = new int[((width*height)*4)+2];
            texture[0]=width;
            texture[1]=height;
            for (int i=2; i<((width*height)*(4))-2; i+=(4)){
                texture[i+3]=buffer.get(i+3);//alpha
                texture[i+2]=buffer.get(i+2);//Red
                texture[i+1]=buffer.get(i+1);//Green
                texture[i]=buffer.get(i);//Blue
            }
            tmtTextureMap.put(getID(resource, mask), texture);
        }

        return texture;
    }

    private static int i,ii, length;
    public static void maskColors(ResourceLocation textureURI, List<Integer> colors){
        //bindAWT(textureURI, null, null);


        pixels = loadTexture(textureURI, colors);
        length = ((pixels[0]*pixels[1])*4)-4;

        for(i=0; i<length; i+=4) {
            if (pixels[i+3] == fullAlpha){
                continue;//skip pixels with no color
            }
            renderPixels.put(i+3, b(pixels[i+3]));//alpha is always from host texture.
            //for each set of recoloring
            if (colors!=null || true) {
                for (ii = 0; ii < colors.size(); ii++) {
                    RGBint = hexTorgba(0xff0000);
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

        glTexSubImage2D (GL_TEXTURE_2D, 0, 0, 0, pixels[0], pixels[1], GL_RGBA, GL_UNSIGNED_INT, renderPixels);
        renderPixels.clear();//reset the buffer to all 0's.
    }
*/
    //--------------------------------------------------------------


    //most compilers should process this type of function faster than a normal typecast.
    public static byte b(int i){return (byte) i;}

    public static boolean colorInRange(int r, int g, int b, int oldR, int oldG, int oldB){
        return oldR-r>-15 && oldR-r <15 &&
                oldG-g>-15 && oldG-g <15 &&
                oldB-b>-15 && oldB-b <15;
    }


    /**Lighting fix*/
    public static void adjustLightFixture(World world, int i, int j, int k) {
        skyLight = world.getSkyBlockTypeBrightness(EnumSkyBlock.Block, i, j, k);
        skyLight=world.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, i, j, k) << 20 | (skyLight<0?0:skyLight) << 4;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,  skyLight % 65536,  skyLight * 0.00001525878f);
        GL11.glColor4f(255, 255, 255, 255);//fixes alpha layering bugs with other mods that don't clear their GL cache
    }


    /**
     * Ingot color textures
     */
    public static void collectIngotColors(){
        String[] ores = OreDictionary.getOreNames();

        int red,green,blue,divisor;
        int[]rgb, colorBuff;
        ResourceLocation texture;
        for(String o: ores){
            if (o.contains("ingot")){
                for (ItemStack s : OreDictionary.getOres(o)){

                    texture=null;
                    red =0;green=0;blue=0;divisor=0;
                    Item item = s.getItem();
                    String textureName = item.getIcon(s,0).getIconName();
                    if(textureName != null){
                        if(textureName.split(":").length == 1){
                            textureName = "minecraft:" + textureName;
                        }
                        texture = new ResourceLocation(textureName.split(":")[0], "textures/items/" + textureName.split(":")[1] + ".png");
                    }

                    if(texture != null){
                        try {
                            colorBuff = TextureUtil.readImageData(Minecraft.getMinecraft().getResourceManager(), texture);
                            for(int c : colorBuff){
                                rgb=hexTorgba(c);
                                if(rgb[3]>128) {
                                    if(rgb[0]+rgb[1]+rgb[2]>20) {
                                        red+=rgb[2];
                                        blue+=rgb[1];
                                        green+=rgb[0];
                                        divisor++;
                                    }
                                }
                            }
                            ingotColors.put(s, new int[]{red/divisor,blue/divisor,green/divisor});
                        } catch (IOException e) {
                            DebugUtil.println("Caught exception while parsing texture to get color: ");
                            e.printStackTrace();
                        }

                    }


                }
            }
        }
    }

    public static IIcon bindBlockTextureFromSide(int side, ItemStack b){
        IIcon texture = RenderBlocks.getInstance().getBlockIconFromSideAndMetadata(Block.getBlockFromItem(b.getItem()), side,b.getItemDamage());
        if (RenderBlocks.getInstance().hasOverrideBlockTexture()) {
            texture = RenderBlocks.getInstance().overrideBlockTexture;
        }
        return texture;
    }

    public static int[] hexTorgba(int hex){
        return new int[]{hex&0xFF, (hex>>8)&0xFF, (hex>>16)&0xFF, (hex>>24)&0xFF};
    }














    public static Map<String, Integer> tmtBoundTextures = new HashMap<>();
    private static Integer currentKey;

    public static void bindTexture(ResourceLocation textureURI, List<Integer> colorsFrom, List<Integer> colorsTo){
        GL11.glEnable(GL_TEXTURE_2D);
        if(!tmtBoundTextures.containsKey(getID(textureURI,colorsFrom,colorsTo,false))){
            if(createAWT(textureURI, colorsFrom, colorsTo)){
                try {
                    BufferedImage image = ImageIO.read(new File(getID(textureURI, colorsFrom, colorsTo, true)));

                    currentKey =tmtBoundTextures.put(getID(textureURI, colorsFrom, colorsTo, false),
                            Minecraft.getMinecraft().getTextureManager().getTexture(
                                    Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(
                                            getID(textureURI, colorsFrom, colorsTo, true),
                                    new DynamicTexture(image))).getGlTextureId());
                } catch (IOException ignored){}
            }
        } else {
            currentKey = tmtBoundTextures.get(getID(textureURI, colorsFrom, colorsTo, false));
        }

        //if for some reason the texture couldn't be written to I/O, which should never be an issue.
        if(currentKey==null){
            ITextureObject object = Minecraft.getMinecraft().getTextureManager().getTexture(textureURI);
            if (object == null) {
                object = new SimpleTexture(textureURI);
                Minecraft.getMinecraft().getTextureManager().loadTexture(textureURI, object);
            }

            if(GL11.glGetInteger(GL_TEXTURE_2D) !=object.getGlTextureId()) {
                GL11.glBindTexture(GL_TEXTURE_2D, object.getGlTextureId());
            }
        } else {
            if(GL11.glGetInteger(GL_TEXTURE_2D) !=currentKey) {
                GL11.glBindTexture(GL_TEXTURE_2D, currentKey);
            }
        }

    }



    public static boolean createAWT(ResourceLocation textureURI, List<Integer> colorsFrom, List<Integer> colorsTo){
        //do vanilla MC texture bind.
        ITextureObject object = Minecraft.getMinecraft().getTextureManager().getTexture(textureURI);
        if (object == null) {
            object = new SimpleTexture(textureURI);
            Minecraft.getMinecraft().getTextureManager().loadTexture(textureURI, object);
        }
        //dont bother checking if its already bound, there's no way that could happen
        GL11.glBindTexture(GL_TEXTURE_2D, object.getGlTextureId());


        //get image data from the currently bound image
        int width =glGetTexLevelParameteri(GL_TEXTURE_2D, 0, GL_TEXTURE_WIDTH);
        int height =glGetTexLevelParameteri(GL_TEXTURE_2D, 0, GL_TEXTURE_HEIGHT);

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

        GL11.glGetTexImage(GL_TEXTURE_2D, 0, GL11.GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        //create a buffered image and push the data to it
        BufferedImage skin = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //int[] px = ((DataBufferInt)skin.getRaster().getDataBuffer()).getData();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int i = (x + (width * y)) * 4;
                int r = buffer.get(i) & 0xff;
                int g = buffer.get(i + 1) & 0xff;
                int b = buffer.get(i + 2) & 0xff;
                int a = buffer.get(i + 3) & 0xff;
                skin.setRGB(x,y, (a<<24) | (r<<16) | (g<<8) | b);
            }
        }

        try {
            if(!new File(ClientProxy.configDirectory+"/TrainsInMotionCache/"+
                    resourceLocation(textureURI)).exists()){
                new File(ClientProxy.configDirectory+"/TrainsInMotionCache/"+
                        resourceLocation(textureURI)).mkdirs();
            }
            ImageIO.write(skin, "PNG", new File(getID(textureURI,colorsFrom,colorsTo,true)));
            buffer.clear();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            buffer.clear();
            return false;
        }
    }

    private static String getID(ResourceLocation textureURI, List<Integer> colorsFrom, List<Integer> colorsTo, boolean isFile){
        StringBuilder filePath = new StringBuilder();
        if(isFile) {
            filePath.append(ClientProxy.configDirectory);
            filePath.append("/TrainsInMotionCache/");
            filePath.append(resourceLocation(textureURI));
            filePath.append("/");
            if(colorsFrom==null || colorsTo==null || colorsFrom.size()+colorsTo.size()==0){
                filePath.append("000_000");
            } else {
                for (Integer i : colorsFrom) {
                    filePath.append(Integer.toHexString(i));
                }
                filePath.append("_");
                for (Integer i : colorsTo) {
                    filePath.append(Integer.toHexString(i));
                }
            }
            filePath.append(".png");
        } else {
            filePath.append(textureURI.getResourceDomain());
            filePath.append("_");
            filePath.append(textureURI.getResourcePath().replace("/",""));
            filePath.append(".");
            if(colorsFrom==null || colorsTo==null || colorsFrom.size()+colorsTo.size()==0){
                filePath.append("000_000");
            } else {
                for (Integer i : colorsFrom) {
                    filePath.append(Integer.toHexString(i));
                }
                for (Integer i : colorsTo) {
                    filePath.append(Integer.toHexString(i));
                }
            }
        }
        return filePath.toString();
    }

    private static String resourceLocation(ResourceLocation res){
        return (res.getResourceDomain() + "/"
                +res.getResourcePath().
                substring(res.getResourcePath().lastIndexOf("/"), res.getResourcePath().lastIndexOf(".")));
    }

}
