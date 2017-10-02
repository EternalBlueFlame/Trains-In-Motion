package ebf.tim.models.tmt;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

@SideOnly(Side.CLIENT)
public class Tessellator {

	private static ByteBuffer bbuf = GLAllocation.createDirectByteBuffer(0x200000 * 4);
	private int rbi = 0, n;
	private boolean ht = false;
	private static Tessellator INSTANCE = new Tessellator();
	private static FloatBuffer fbuf = bbuf.asFloatBuffer();
	private static IntBuffer ibuf = bbuf.asIntBuffer();
	private float u, v, w;
	private int[] rb;
	private static Map<ResourceLocation, Integer> cachedTextures = new HashMap<ResourceLocation, Integer>();

	public static Tessellator getInstance(){
		return INSTANCE;
	}

	public Tessellator(){

	}

	public void startDrawing(){
		ht = false;
		rbi = 0;
		rb = new int[240];
		ibuf.clear();
		bbuf.clear();
	}

	public void draw(){
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			ibuf.put(rb, 0, 240);
			bbuf.position(0);
					//for texture
					fbuf.position(3);
					GL11.glTexCoordPointer(4, 40, fbuf);
					GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
					//for normals
					bbuf.position(32);
					GL11.glNormalPointer(40, bbuf);
					GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
				fbuf.position(0);
				GL11.glVertexPointer(3, 40, fbuf);
				GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
				GL11.glDrawArrays(GL11.GL_QUADS, 0, 24);
				GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
					GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
					GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);

			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);

			GL11.glPopMatrix();
	}


	public void addVertex(float par1, float par3, float par5){
		//vertexes
		rb[rbi] = Float.floatToRawIntBits(par1);
		rb[rbi+1] = Float.floatToRawIntBits(par3);
		rb[rbi+2] = Float.floatToRawIntBits(par5);
		//texture
		rb[rbi+3] = Float.floatToRawIntBits(u);
		rb[rbi+4] = Float.floatToRawIntBits(v);
		rb[rbi +6] = Float.floatToRawIntBits(w);
		//normal
		rb[rbi + 8] = n;
		rbi +=10;
	}

	public void addVertexWithUV(double i, double j, double k, float l, float m){
		this.setTextureUV(l, m); this.addVertex((float) i,(float) j,(float) k);
	}

	public void setNormal(int x, int y, int z){
		n = ((x * 127)) & 255 | (((y * 127)) & 255) << 8 | (((z * 127)) & 255) << 16;
	}

	public void setTextureUV(float i, float j){
		this.ht = true; this.u = i; this.v = j; this.w = 1.0F;
	}

	public static void bindTexture(ResourceLocation textureURI, ModelBase model) {
		if (cachedTextures.containsKey(textureURI) && textureURI != null){
			GL11.glBindTexture(GL_TEXTURE_2D, cachedTextures.get(textureURI));
		} else {
			ITextureObject object = Minecraft.getMinecraft().getTextureManager().getTexture(textureURI);
			if (object == null) {
				object = new SimpleTexture(textureURI);
				Minecraft.getMinecraft().getTextureManager().loadTexture(textureURI, object);
			}
			cachedTextures.put(textureURI, object.getGlTextureId());
			GL11.glBindTexture(GL_TEXTURE_2D, object.getGlTextureId());
		}




		if (false || textureURI != null && !textureURI.getResourcePath().contains("null")) {
			if (cachedTextures.containsKey(textureURI)){
				GL11.glBindTexture(GL_TEXTURE_2D, cachedTextures.get(textureURI));
			} else {
				//replace the assets string with ./ to go to the mods folder, theoretically... the entire address could also be replaced with a URI or drive directory.
				BufferedImage image = TextureLoader.loadImage(textureURI.getResourcePath());//The path is inside the jar file
				if (image != null) {
					int texID = TextureLoader.loadTexture(image, model==null?0:model.getTextureNoiseScale());
					GL11.glBindTexture(GL_TEXTURE_2D, texID);
					cachedTextures.put(textureURI, texID);
				} else {
					GL11.glBindTexture(GL_TEXTURE_2D, TextureUtil.missingTexture.getGlTextureId());
				}
			}
		}
	}

	public static void bindTexture(ResourceLocation textureURI) {
		if (cachedTextures.containsKey(textureURI)){
			GL11.glBindTexture(GL_TEXTURE_2D, cachedTextures.get(textureURI));
		} else {
			ITextureObject object = Minecraft.getMinecraft().getTextureManager().getTexture(textureURI);
			if (object == null) {
				object = new SimpleTexture(textureURI);
				Minecraft.getMinecraft().getTextureManager().loadTexture(textureURI, object);
			}
			cachedTextures.put(textureURI, object.getGlTextureId());
			GL11.glBindTexture(GL_TEXTURE_2D, object.getGlTextureId());
		}

	}
}