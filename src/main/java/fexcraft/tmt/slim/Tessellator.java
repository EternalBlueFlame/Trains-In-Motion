package fexcraft.tmt.slim;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.TrainsInMotion;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.DebugUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import sun.applet.Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT24;

/**
* @Author EternalBlueFlame
* 
*/
@SideOnly(Side.CLIENT)
public class Tessellator{

	public static Tessellator INSTANCE = new Tessellator();

	private int verts, dm;
	private boolean translated=false, texture4d=false, isQuad =true;
	private float x, y, z;
	//supports up to 64 vertex points for support of larger
	private static FloatBuffer bufferVertex = GLAllocation.createDirectByteBuffer(128*3).asFloatBuffer();//one for each vertex
	private static IntBuffer bufferIndex = GLAllocation.createDirectByteBuffer(128).asIntBuffer();//one per set of vertex points
	private static FloatBuffer bufferTexture = GLAllocation.createDirectByteBuffer(128*4).asFloatBuffer();//one for each texture vertex, yes there's 4 for some compatibility reason.

	//rendering quads is far more common than other shapes, so they get their own specific system that's slightly more efficient by using a preset variable for the index.
	private static final IntBuffer quadIndex = (IntBuffer) GLAllocation.createDirectIntBuffer(4).put(new int[]{0,1,2,3}).flip();


	public static Tessellator getInstance(){
		return INSTANCE;
	}

	//use this to reset and define the drawing mode
	public void startDrawing(int i){
		dm = i;
		bufferVertex.clear();
		bufferTexture.clear();
		isQuad = dm == GL11.GL_QUADS;
		if (!isQuad){
			bufferIndex.clear();
			isQuad = false;
			verts =0;
		}
		//GL11.glColor4f(255, 255, 255, 255);//fixes alpha layering bugs with other mods that don't clear their GL cache
		//todo call this from the actual render rather than tessellation, it breaks the recolor system here.
	}

	/**
	 * old draw function for compatibility.
	 * use the enable and disable calls around the render of the model as a whole and call
	 * @see #arrayEnabledDraw() instead so they don't have to be enabled and disabled during the render of every face.
	 */
	public void draw(){
		GL11.glEnable(GL11.GL_VERTEX_ARRAY);
		GL11.glEnable(GL11.GL_TEXTURE_COORD_ARRAY);
		bufferVertex.flip();
		bufferTexture.flip();

		if(isQuad){
			GL11.glVertexPointer(3, 0, bufferVertex);
			GL11.glTexCoordPointer(texture4d?4:2, 0, bufferTexture);
			GL11.glDrawElements(dm, quadIndex);
		} else {
			bufferIndex.flip();

			GL11.glVertexPointer(3, 0, bufferVertex);
			GL11.glTexCoordPointer(texture4d?4:2, 0, bufferTexture);
			GL11.glDrawElements(dm, bufferIndex);
		}
		GL11.glDisable(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glDisable(GL11.GL_VERTEX_ARRAY);
	}

	public void arrayEnabledDraw(){
		bufferVertex.flip();
		bufferTexture.flip();

		if(isQuad){
			GL11.glVertexPointer(3, 0, bufferVertex);
			GL11.glTexCoordPointer(texture4d?4:2, 0, bufferTexture);
			GL11.glDrawElements(dm, quadIndex);
		} else {
			bufferIndex.flip();

			GL11.glVertexPointer(3, 0, bufferVertex);
			GL11.glTexCoordPointer(texture4d?4:2, 0, bufferTexture);
			GL11.glDrawElements(dm, bufferIndex);
		}
	}
	
	public void addVertex(float par1, float par3, float par5){
		if(translated) {
			bufferVertex.put(par1 + x);
			bufferVertex.put(par3 + y);
			bufferVertex.put(par5 + z);
		} else {
			bufferVertex.put(par1);
			bufferVertex.put(par3);
			bufferVertex.put(par5);
		}
		if (!isQuad) {
			bufferIndex.put(verts);
			verts++;
		}
	}
	
	public void addVertexWithUV(float i, float j, float k, float u, float v){
		this.addVertex(i, j, k);
		this.setTextureUV(u, v);
	}
	
	public void addVertexWithUVW(float i, float j, float k, float l, float m, float n){
		this.setTextureUVW(l, m, n);
		this.addVertex(i, j, k);
	}

	/**
	 * this does absolutely nothing, never really did..
	 */
	@Deprecated
	public void setNormal(float x, float y, float z){}


	public void setTextureUV(float u, float v){
		bufferTexture.put(u);
		bufferTexture.put(v);
	}
	
	public void setTextureUVW(float u, float v, float w){
		bufferTexture.put(u);
		bufferTexture.put(v);
		bufferTexture.put(0.0f);
		bufferTexture.put(w);
		texture4d=true;
	}
	
	public void setTranslation(float x, float y, float z){
		this.x = x; this.y = y; this.z = z;
		translated=true;
	}
	
	public void addTranslation(float x, float y, float z){
		this.x += x; this.y += y; this.z += z;
		translated=true;
	}

	private static Map<ResourceLocation, Integer> tmtMap = new HashMap<>();

	/**
	 * custom texture binding method, generally same as vanilla, but possible to improve performance later.
	 * @param textureURI
	 */
	public static void bindTexture(ResourceLocation textureURI) {
		if (textureURI == null){
			return;
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


	private static Map<ResourceLocation, int[]> tmtTextureMap = new HashMap<>();

	public static int[] loadTexture(ResourceLocation resource){
		int[] texture = tmtTextureMap.get(resource);

		bindTexture(resource);
		if(texture==null){
			int width =glGetTexLevelParameteri(GL_TEXTURE_2D, 0, GL_TEXTURE_WIDTH);
			int height =glGetTexLevelParameteri(GL_TEXTURE_2D, 0, GL_TEXTURE_HEIGHT);

			int format = glGetTexLevelParameteri(GL_TEXTURE_2D, 0, GL_TEXTURE_INTERNAL_FORMAT);
			ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * (format == GL_RGB?3:4));

			GL11.glGetTexImage(GL_TEXTURE_2D, 0, format, GL_UNSIGNED_BYTE, buffer);

			texture = new int[((width*height)*4)+2];
			texture[0]=width;
			texture[1]=height;
			for (int i=2; i<((width*height)*(format == GL_RGB?3:4))-2; i+=(format == GL_RGB?3:4)){
				texture[i+3]=format==GL_RGB?0xFF:buffer.get(i+3) & 0xFF;//alpha
				texture[i+2]=buffer.get(i+2);//Red
				texture[i+1]=buffer.get(i+1);//Green
				texture[i]=buffer.get(i);//Blue
			}
			tmtTextureMap.put(resource, texture);
		}

		return texture;
	}

	private static ByteBuffer renderPixels = ByteBuffer.allocateDirect((4096*4096)*4);
	private static int i,ii, length;
	private static int[] RGBint, pixels;
	private static final byte fullAlpha=(byte)0;
	public static void maskColors(ResourceLocation textureURI, List<int[]> colors){
		pixels = loadTexture(textureURI);
		length = ((pixels[0]*pixels[1])*4)-4;

		for(i=0; i<length; i+=4) {
			if (pixels[i+3] == fullAlpha){
				continue;//skip pixels with no color
			}
			renderPixels.put(i+3, b(pixels[i+3]));//alpha is always from host texture.
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

		//glTexSubImage2D (GL_TEXTURE_2D, 0, 0, 0, pixels[0], pixels[1], GL_RGBA, GL_UNSIGNED_BYTE, renderPixels);
		renderPixels.clear();//reset the buffer to all 0's.
	}

	//most compilers should process this type of function faster than a normal typecast.
	public static byte b(int i){return (byte) i;}

	public static boolean colorInRange(int r, int g, int b, int oldR, int oldG, int oldB){
		return oldR-r>-15 && oldR-r <15 &&
				oldG-g>-15 && oldG-g <15 &&
				oldB-b>-15 && oldB-b <15;
	}
}
