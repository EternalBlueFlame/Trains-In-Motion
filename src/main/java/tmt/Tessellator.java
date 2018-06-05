package tmt;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.utility.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

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
			GL11.glBindTexture(GL_TEXTURE_2D, id);
		}
	}
	
}
