package ebf.tim.models.tmt;

import com.sun.prism.shader.Mask_TextureSuper_Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.jinput.LWJGLEnvironmentPlugin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

@SideOnly(Side.CLIENT)
public class Tessellator {

	private static ByteBuffer bbuf = GLAllocation.createDirectByteBuffer(0x200000 * 4);
	private int rbs = 0, verts = 0, rbi = 0, o = 0, vtc = 0, dm, n;
	private boolean ht = false, in = false, drawing = false, hc = false;
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

	public void startDrawing(int i){
		if(!drawing){
			drawing = true; dm = i;
			in = ht = hc = false;
			reset();
		}
	}

	public int draw(){
		if(drawing){
			drawing = false; o = 0;
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			while (o < verts){
				vtc = Math.min(verts - o, 0x200000 >> 5);
				ibuf.clear(); ibuf.put(rb, o * 10, vtc * 10); bbuf.position(0); bbuf.limit(vtc * 40); o += vtc;
				if(ht){
					fbuf.position(3);
					GL11.glTexCoordPointer(4, 40, fbuf);
					GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				}
				if(hc){
					bbuf.position(28);
					GL11.glColorPointer(4, true, 40, bbuf);
					GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
				}
				if(in){
					bbuf.position(32);
					GL11.glNormalPointer(40, bbuf);
					GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
				}
				fbuf.position(0);
				GL11.glVertexPointer(3, 40, fbuf);
				GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
				GL11.glDrawArrays(dm, 0, vtc);
				GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
				if(ht){
					GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				}
				if(hc){
					GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
				}
				if(in){
					GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
				}
			}
			if(rbs > 0x20000/*131072*/ && rbi < (rbs << 3)){
				rbs = 0; rb = null;
			}
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);

			GL11.glPopMatrix();
		}
		return 0;
	}

	private void reset(){
		verts = rbi = 0;

		bbuf.clear();
	}

	public void addVertex(float par1, float par3, float par5){
		if(rbi >= rbs - 40) {
			if(rbs == 0){rbs = 0x10000/*65536*/; rb = new int[rbs];}//theoretically the value of rbs could never exceed 9? (8 if one value was set to 7)
			else{rbs *= 2; rb = Arrays.copyOf(rb, rbs);}
		}
		if(ht){
			rb[rbi + 3] = Float.floatToRawIntBits(u); rb[rbi + 4] = Float.floatToRawIntBits(v);
			rb[rbi + 5] = 0; rb[rbi + 6] = Float.floatToRawIntBits(w);
		}
		if(in){rb[rbi + 8] = n;}
		rb[rbi] = Float.floatToRawIntBits(par1);
		rb[rbi + 1] = Float.floatToRawIntBits(par3);
		rb[rbi + 2] = Float.floatToRawIntBits(par5);
		rbi += 10; verts++;
	}

	public void addVertex(double par1, double par3, double par5){
		/*
		if(rbi >= rbs - 40) {
			if(rbs == 0){rbs = 0x10000; rb = new int[rbs];}//theoretically the value of rbs could never exceed 9? (8 if one value was set to 7)
			else{rbs *= 2; rb = Arrays.copyOf(rb, rbs);}
		}*/
		if(rbs == 0){rbs = 40; rb = new int[rbs];}
		if(ht){
			rb[rbi + 3] = Float.floatToRawIntBits(u); rb[rbi + 4] = Float.floatToRawIntBits(v);
			rb[rbi + 5] = 0; rb[rbi + 6] = Float.floatToRawIntBits(w);
		}
		if(in){rb[rbi + 8] = n;}
		rb[rbi] = Float.floatToRawIntBits((float) par1);
		rb[rbi + 1] = Float.floatToRawIntBits((float) par3);
		rb[rbi + 2] = Float.floatToRawIntBits((float) par5);
		rbi += 10; verts++;
	}

	public void addVertexWithUV(float i, float j, float k, float l, float m){
		this.setTextureUV(l, m); this.addVertex(i, j, k);
	}

	public void addVertexWithUV(double i, double j, double k, float l, float m){
		this.setTextureUV(l, m); this.addVertex(i, j, k);
	}

	public void setNormal(float x, float y, float z){
		setNormal((int)x, (int)y, (int)z);
	}

	public void setNormal(int x, int y, int z){
		in = true;
		n = ((x * 127)) & 255 | (((y * 127)) & 255) << 8 | (((z * 127)) & 255) << 16;
	}

	public void setTextureUV(float i, float j){
		this.ht = true; this.u = i; this.v = j; this.w = 1.0F;
	}

	public void setTextureUVW(float i, float j, float k){
		this.ht = true; this.u = i; this.v = j; this.w = k;
	}

	public void setColorRGBAf(float f, float g, float h, float i) {
		this.setColorRGBA((int)(f * 255.0F), (int)(g * 255.0F), (int)(h * 255.0F), (int)(i));
	}

	public void setColorRGBA(int i, int j, int k, int l){
		if(i > 255){i = 255;} if(j > 255){j = 255;} if(k > 255){k = 255;} if(l > 255){l = 255;}
		if(i < 0){i = 0;} if(j < 0){j = 0;} if(k < 0){k = 0;} if (l < 0){l = 0;}
		hc = true;
    }

	public void disableColor() {
		hc = false;
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

		if (GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D) != object.getGlTextureId()) {
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