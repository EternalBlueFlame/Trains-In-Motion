package ebf.tim.models.tmt;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.utility.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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


	public static void bindTexture(ResourceLocation textureURI) {
		ITextureObject object;
		if (textureURI != null) {
			object = Minecraft.getMinecraft().getTextureManager().getTexture(textureURI);
			if (object == null) {
				object = new SimpleTexture(textureURI);
				Minecraft.getMinecraft().getTextureManager().loadTexture(textureURI, object);
			}
		} else {
			object = TextureUtil.missingTexture;
		}

		if (ClientProxy.ForceTextureBinding || GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D) != object.getGlTextureId()) {
			GL11.glBindTexture(GL_TEXTURE_2D, object.getGlTextureId());
		}
	}

	/**EXPERIMENTAL CODE
	 * this is an experimental texture loader for external files, use should be
	 * @param file "images/image.png"
	 */
	private void loadExternalTexture(String file) {
		IntBuffer i = BufferUtils.createIntBuffer(1);
		GL11.glGenTextures(i);
		int id =  i.get(0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		try {
			//get the image file
			URL url = this.getClass().getResource( file);
			BufferedImage image = ImageIO.read(url);
			ByteBuffer imageData = loadTexture(image);
			//set the currently bound texture to the image we loaded
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D,0,GL11.GL_RGBA, image.getWidth(),image.getHeight(),0,GL11.GL_RGBA,GL11.GL_UNSIGNED_BYTE,imageData);
			//add extra effects, and define filtering
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MAG_FILTER,GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MIN_FILTER,GL11.GL_NEAREST);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		} catch (IOException e){
			System.out.println("Failed to load image " + file);
		}
	}
	/**EXPERIMENTAL CODE
	 * this is an experimental texture loader for external files, do not call this directly, instead use
	 * @see #loadExternalTexture(String)
	 */
	protected ByteBuffer loadTexture(BufferedImage image) {
		BufferedImage img = new BufferedImage(image.getWidth(),image.getHeight(),image.getColorModel().getNumComponents() == 3? BufferedImage.TYPE_3BYTE_BGR: BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = img.createGraphics();
		g.scale(1,-1);
		g.drawImage(image, 0, -image.getHeight(), null);
		byte[] data = new byte[image.getColorModel().getNumComponents() * image.getWidth() * image.getHeight()];
		img.getRaster().getDataElements(0, 0, image.getWidth(), image.getHeight(), data);
		ByteBuffer pixels = BufferUtils.createByteBuffer(data.length);
		pixels.put(data).rewind();
		return pixels;
	}
	
}