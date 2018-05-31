package tmt;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

/**
* @Author EternalBlueFlame
* 
*/
@SideOnly(Side.CLIENT)
public class Tessellator{

	public static Tessellator INSTANCE = new Tessellator();

	private int verts, dm;
	private boolean drawing = false;
	private float x, y, z;
	private static FloatBuffer bufferVertex = GLAllocation.createDirectFloatBuffer(384);//one for each vertex
	private static IntBuffer bufferIndex = GLAllocation.createDirectIntBuffer(128);//one per vertex
	private static FloatBuffer buff_texs = GLAllocation.createDirectFloatBuffer(512);//one for each texture vertex, yes there's 4 for some compatibility reason.


	public static Tessellator getInstance(){
		return INSTANCE;
	}
	
	public void startDrawing(int i){
		//use this to reset and define the drawing mode
		if(!drawing){
			drawing = true;
			dm = i;
			verts =0;
			bufferIndex.clear();
			bufferVertex.clear();
			buff_texs.clear();
		}
	}
	public void draw(){
		if(drawing){
			drawing = false;
			bufferVertex.flip();
			bufferIndex.flip();
			buff_texs.flip();

			GL11.glEnable(GL11.GL_VERTEX_ARRAY);
			GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			GL11.glVertexPointer(3, 0, bufferVertex);
			GL11.glTexCoordPointer(4, 0, buff_texs);
			GL11.glDrawElements(dm, bufferIndex);
			GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			GL11.glDisable(GL11.GL_VERTEX_ARRAY);
		}
	}
	
	public void addVertex(float par1, float par3, float par5){
		bufferVertex.put(par1+x);
		bufferVertex.put(par3+y);
		bufferVertex.put(par5+z);

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
		buff_texs.put(u);
		buff_texs.put(v);
		buff_texs.put(0.0f);
		buff_texs.put(1.0f);
	}
	
	public void setTextureUVW(float u, float v, float w){
		buff_texs.put(u);
		buff_texs.put(v);
		buff_texs.put(0.0f);
		buff_texs.put(w);
	}
	
	public void setTranslation(float x, float y, float z){
		this.x = x; this.y = y; this.z = z;
	}
	
	public void addTranslation(float x, float y, float z){
		this.x += x; this.y += y; this.z += z;
	}


	/**
	 * custom texture binding method, generally same as vanilla, but possible to improve performance later.
	 * @param textureURI
	 */
	public static void bindTexture(ResourceLocation textureURI) {
		ITextureObject object = Minecraft.getMinecraft().getTextureManager().getTexture(textureURI);
		if (object == null) {
			object = new SimpleTexture(textureURI);
			Minecraft.getMinecraft().getTextureManager().loadTexture(textureURI, object);
		}
		GL11.glBindTexture(GL_TEXTURE_2D, object.getGlTextureId());
	}
	
}
