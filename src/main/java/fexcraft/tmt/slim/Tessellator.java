package fexcraft.tmt.slim;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.TrainsInMotion;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.DebugUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

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
	private static Float x, y, z;
	//supports up to 1024 vertex points for support of larger
	private static FloatBuffer bufferVertex = ByteBuffer.allocateDirect((4096*6)).order(ByteOrder.nativeOrder()).asFloatBuffer();//GLAllocation.createDirectByteBuffer(4096*6).asFloatBuffer();//one for each vertex
	private static IntBuffer bufferIndex = ByteBuffer.allocateDirect((4096*2)).order(ByteOrder.nativeOrder()).asIntBuffer();//GLAllocation.createDirectByteBuffer(4096*2).asIntBuffer();//one per set of vertex points
	private static FloatBuffer bufferTexture = ByteBuffer.allocateDirect((4096*4)).order(ByteOrder.nativeOrder()).asFloatBuffer();//GLAllocation.createDirectByteBuffer(4096*8).asFloatBuffer();//one for each texture vertex, yes there's 4 for some compatibility reason.

	public static Tessellator getInstance(){
		return INSTANCE;
	}

	//use this to reset and define the drawing mode
	public void startDrawing(int i){
		dm = i;
		bufferVertex.clear();
		bufferTexture.clear();
		bufferIndex.clear();
		verts =0;
	}

	/**
	 * old draw function for compatibility.
	 * use the enable and disable calls around the render of the model as a whole and call
	 * @see #arrayEnabledDraw() instead so they don't have to be enabled and disabled during the render of every face.
	 */
	@Deprecated
	public void draw(){
		GL11.glEnable(GL11.GL_VERTEX_ARRAY);
		GL11.glEnable(GL11.GL_TEXTURE_COORD_ARRAY);
		bufferVertex.flip();
		bufferTexture.flip();
		bufferIndex.flip();

		GL11.glVertexPointer(3, 0, bufferVertex);
		GL11.glTexCoordPointer(4, 0, bufferTexture);
		GL11.glDrawElements(dm, bufferIndex);
		GL11.glDisable(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glDisable(GL11.GL_VERTEX_ARRAY);
	}

	public void arrayEnabledDraw(){
		bufferVertex.flip();
		bufferTexture.flip();
		bufferIndex.flip();

		GL11.glVertexPointer(3, 0, bufferVertex);
		GL11.glTexCoordPointer(4, 0, bufferTexture);
		GL11.glDrawElements(dm, bufferIndex);
		int err;
		for(;;) {
			err = glGetError();
			if (err == GL_NO_ERROR){ break;}
			else {System.out.println("Error: %s  : " + GLU.gluErrorString(err));}
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
		bufferIndex.put(verts);
		verts++;
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
		bufferTexture.put(0.0f);
		bufferTexture.put(1f);
	}
	
	public void setTextureUVW(float u, float v, float w){
		bufferTexture.put(u);
		bufferTexture.put(v);
		bufferTexture.put(0.0f);
		bufferTexture.put(w);
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
