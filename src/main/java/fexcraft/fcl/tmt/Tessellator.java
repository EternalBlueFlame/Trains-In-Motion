package fexcraft.fcl.tmt;

import java.util.Arrays;

import fexcraft.fcl.common.math.RGB;
import org.lwjgl.opengl.GL11;


/**
* @Author Ferdinand (FEX___96)
* 
*/
public class Tessellator {
	
	private static java.nio.ByteBuffer bbuf = java.nio.ByteBuffer.allocateDirect(0x200000 * 4).order(java.nio.ByteOrder.nativeOrder());
	private int rbs = 0, verts = 0, /*br = 0, c,*/ rbi = 0, /*vertices = 0,*/ dm, n;
	private boolean ht = false, in = false, drawing = false;
	public static Tessellator INSTANCE = new Tessellator();
	private static java.nio.FloatBuffer fbuf = bbuf.asFloatBuffer();
	private static java.nio.IntBuffer ibuf = bbuf.asIntBuffer();
	private double u, v, w, x, y, z;
	private int[] rb; private RGB color;
	
	public static Tessellator getInstance(){
		return INSTANCE;
	}
	
	public void startDrawing(int i){
		if(!drawing){
			drawing = true; dm = i;
			in = ht = false;
			reset();
		}
	}
	
	public void draw(){
		if(!drawing){ return; }
		drawing = false; int o = 0;
		while(o < verts){
			int vtc = Math.min(verts - o, 0x200000 >> 5);
			ibuf.clear(); ibuf.put(rb, o * 10, vtc * 10); bbuf.position(0); bbuf.limit(vtc * 40); o += vtc;
			if(ht){
				fbuf.position(3);
				GL11.glTexCoordPointer(4, 40, fbuf);
				GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			}
			if(in){
				bbuf.position(32);
				GL11.glNormalPointer(40, bbuf);
				GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
			}
			fbuf.position(0);
			GL11.glVertexPointer(3, 40, fbuf);
			GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
			if(dm == 3 || color != null) color.glColorApply();
			GL11.glDrawArrays(dm, 0, vtc);
			GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
			if(ht){ GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY); }
			if(in){ GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY); }
			if(dm == 3 || color != null) RGB.glColorReset();
		}
		if(rbs > 0x20000 && rbi < (rbs << 3)){ rbs = 0; rb = null; }
		reset();
	}
	
	private void reset(){
		verts = rbi = 0;//vertices = 0;
		color = null;
		bbuf.clear();
	}
	
	public void addVertex(double i, double j, double k){
		if(rbi >= rbs - 40) {
			if(rbs == 0){rbs = 0x10000; rb = new int[rbs];}
			else{rbs *= 2; rb = Arrays.copyOf(rb, rbs);}
		}
		if(ht){
			rb[rbi + 3] = Float.floatToRawIntBits((float)u); rb[rbi + 4] = Float.floatToRawIntBits((float)v);
			rb[rbi + 5] = Float.floatToRawIntBits(0.0F); rb[rbi + 6] = Float.floatToRawIntBits((float)w);
		}
		if(in){rb[rbi + 8] = n;}
		rb[rbi] = Float.floatToRawIntBits((float)(i + x));
		rb[rbi + 1] = Float.floatToRawIntBits((float)(j + y));
		rb[rbi + 2] = Float.floatToRawIntBits((float)(k + z));
		rbi += 10; verts++; //vertices++;
	}
	
	public void addVertexWithUV(double i, double j, double k, double l, double m){
		this.setTextureUV(l, m); this.addVertex(i, j, k);
	}
	
	public void addVertexWithUVW(double i, double j, double k, double l, double m, double n){
		this.setTextureUVW(l, m, n); this.addVertex(i, j, k);
	}
	
	public void setNormal(float x, float y, float z){
		in = true;
		byte b0 = (byte)((int)(x * 127.0F)); byte b1 = (byte)((int)(y * 127.0F)); byte b2 = (byte)((int)(z * 127.0F));
		n = b0 & 255 | (b1 & 255) << 8 | (b2 & 255) << 16;
	}
	
	public void setTextureUV(double i, double j){
		this.ht = true; this.u = i; this.v = j; this.w = 1.0D;
	}
	
	public void setTextureUVW(double i, double j, double k){
		this.ht = true; this.u = i; this.v = j; this.w = k;
	}
	
	public void setTranslation(double x, double y, double z){
		this.x = x; this.y = y; this.z = z;
	}
	
	public void addTranslation(float x, float y, float z){
		this.x += x; this.y += y; this.z += z;
	}

	public void setColor(RGB rgb){
		this.color = rgb;
	}
	
}