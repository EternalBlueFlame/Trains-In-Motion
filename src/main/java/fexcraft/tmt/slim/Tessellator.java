package fexcraft.tmt.slim;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.TrainsInMotion;
import ebf.tim.utility.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

/**
* @Author EternalBlueFlame
* 
*/
@SideOnly(Side.CLIENT)
public class Tessellator{

	public static Tessellator INSTANCE = new Tessellator();

	private static int verts, dm;
	private static boolean texture4d=false;
	private static Float x, y, z;
	//supports up to 1024 vertex points for support of larger
	private static FloatBuffer bufferVertex = ByteBuffer.allocateDirect((4096*6)).order(ByteOrder.nativeOrder()).asFloatBuffer();//GLAllocation.createDirectByteBuffer(4096*6).asFloatBuffer();//one for each vertex
	private static IntBuffer bufferIndex = ByteBuffer.allocateDirect((4096*2)).order(ByteOrder.nativeOrder()).asIntBuffer();//GLAllocation.createDirectByteBuffer(4096*2).asIntBuffer();//one per set of vertex points
	private static FloatBuffer bufferTexture = ByteBuffer.allocateDirect((4096*8)).order(ByteOrder.nativeOrder()).asFloatBuffer();//GLAllocation.createDirectByteBuffer(4096*8).asFloatBuffer();//one for each texture vertex, yes there's 4 for some compatibility reason.

	//rendering quads is far more common than other shapes, so they get their own specific system that's slightly more efficient by using a preset variable for the index.
	private static final IntBuffer quadIndex = ByteBuffer.allocateDirect(16).order(ByteOrder.nativeOrder()).asIntBuffer().put(new int[]{0,1,2,3});//(IntBuffer) GLAllocation.createDirectIntBuffer(4).put(new int[]{0,1,2,3}).flip();

	public static Tessellator getInstance(){
		return INSTANCE;
	}

	//use this to reset and define the drawing mode
	public void startDrawing(int i){
		dm = i;
		bufferVertex.clear();
		bufferTexture.clear();
		if (dm != GL11.GL_QUADS){
			bufferIndex.clear();
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

		if(dm == GL11.GL_QUADS){
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

		if(dm == GL11.GL_QUADS){
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
		if(x!=null) {
			bufferVertex.put(par1 + x);
			bufferVertex.put(par3 + y);
			bufferVertex.put(par5 + z);
		} else {
			bufferVertex.put(par1);
			bufferVertex.put(par3);
			bufferVertex.put(par5);
		}
		if (dm != GL11.GL_QUADS) {
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

	public static void setTranslation(float xOffset, float yOffset, float zOffset){
		x = xOffset; y = yOffset; z = zOffset;
	}
	
	public static void addTranslation(float xOffset, float yOffset, float zOffset){
		x += xOffset; y += yOffset; z += zOffset;
	}

	public static void bindTexture(ResourceLocation uri){
		TextureManager.bindTexture(uri);
	}


}
