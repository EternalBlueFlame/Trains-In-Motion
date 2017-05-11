package ebf.tim.models.tmt;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.GLAllocation;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class Tessellator extends net.minecraft.client.renderer.Tessellator{
	
	private static ByteBuffer bbuf = GLAllocation.createDirectByteBuffer(0x200000 * 4);
	private int rbs = 0, verts = 0, rbi = 0, dm, n;
	private boolean ht = false, in = false, drawing = false, hc = false;
	private static Tessellator INSTANCE = new Tessellator();
	private static FloatBuffer fbuf = bbuf.asFloatBuffer();
	private static IntBuffer ibuf = bbuf.asIntBuffer();
	private double u, v, w, x_o, y_o, z_o;
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
	
	@Override
	public int draw(){
		if(drawing){
			drawing = false; int o = 0;
			while (o < verts){
				int vtc = Math.min(verts - o, 0x200000 >> 5);
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
			if(rbs > 0x20000 && rbi < (rbs << 3)){
				rbs = 0; rb = null;
			}
			reset();
		}
		return 0;
	}
	
	private void reset(){
		verts = rbi = 0;
		
		bbuf.clear();
	}
	
	public void addVertex(double par1, double par3, double par5){
		if(rbi >= rbs - 40) {
			if(rbs == 0){rbs = 0x10000; rb = new int[rbs];}
			else{rbs *= 2; rb = Arrays.copyOf(rb, rbs);}
		}
		if(ht){
			rb[rbi + 3] = Float.floatToRawIntBits((float)u); rb[rbi + 4] = Float.floatToRawIntBits((float)v);
			rb[rbi + 5] = 0; rb[rbi + 6] = Float.floatToRawIntBits((float)w);
		}
		if(in){rb[rbi + 8] = n;}
		rb[rbi] = Float.floatToRawIntBits((float)(par1 + x_o));
		rb[rbi + 1] = Float.floatToRawIntBits((float)(par3 + y_o));
		rb[rbi + 2] = Float.floatToRawIntBits((float)(par5 + z_o));
		rbi += 10; verts++;
	}
	
	public void addVertexWithUV(double i, double j, double k, double l, double m){
		this.setTextureUV(l, m); this.addVertex(i, j, k);
	}
	
	public void addVertexWithUVW(double i, double j, double k, double l, double m, double n){
		this.setTextureUVW(l, m, n); this.addVertex(i, j, k);
	}

	public void setNormal(float x, float y, float z){
		setNormal((int)x, (int)y, (int)z);
	}
	
	public void setNormal(int x, int y, int z){
		in = true;
		n = ((x * 127)) & 255 | (((y * 127)) & 255) << 8 | (((z * 127)) & 255) << 16;
	}
	
	public void setTextureUV(double i, double j){
		this.ht = true; this.u = i; this.v = j; this.w = 1.0D;
	}
	
	public void setTextureUVW(double i, double j, double k){
		this.ht = true; this.u = i; this.v = j; this.w = k;
	}
	
	public void setTranslation(double x, double y, double z){
		x_o = x; y_o = y; z_o = z;
	}
	
	public void addTranslation(float x, float y, float z){
		x_o += x; y_o += y; z_o += z;
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
	
}