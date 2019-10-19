package fexcraft.tmt.slim;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

/**
* @Author EternalBlueFlame
* 
*/
@SideOnly(Side.CLIENT)
public class Tessellator{

	public static Tessellator INSTANCE = new Tessellator();

	private static Float x, y, z;
	private static List<float[]> verticies = new ArrayList<>(); //0,1,2 are the position, 3,4,5,6 are the texture vectors.

	public static Tessellator getInstance(){
		return INSTANCE;
	}

	//use this to reset and define the drawing mode
	public void startDrawing(int mode){
		verticies=new ArrayList<>();
		GL11.glBegin(mode);
	}

	public void draw(){
		for(float[] f : verticies){
			if(f.length>3) {
				GL11.glTexCoord2f(f[3], f[4]);
			}
			GL11.glVertex3f(f[0],f[1],f[2]);
		}
		GL11.glEnd();
	}
	
	public void addVertex(float i, float j, float k){
		if(x!=null){
			verticies.add(new float[]{i + x, j + y, k + z});

		} else {
			verticies.add(new float[]{i, j, k});
		}
	}
	
	public void addVertexWithUV(float i, float j, float k, float u, float v){
		if(x!=null){
			verticies.add(new float[]{i + x, j + y, k + z, u, v});
		} else {
			verticies.add(new float[]{i, j, k, u, v});
		}
	}
	
	public void addVertexWithUVW(float i, float j, float k, float l, float m, float n){
		this.setTextureUVW(l, m, n);
		this.addVertex(i, j, k);
	}

	/**
	 * this is only used when lighting is defined on a per pixel scale, not when it's on a per face or per vertex scale
	 */
	@Deprecated
	public void setNormal(float x, float y, float z){}


	public void setTextureUV(float u, float v){
		float[] vert = verticies.get(verticies.size()-1);
		verticies.set(verticies.size()-1, new float[]{vert[0],vert[1],vert[2],u,v});
	}
	
	public void setTextureUVW(float u, float v, float w){
		float[] vert = verticies.get(verticies.size()-1);
		verticies.set(verticies.size()-1, new float[]{vert[0],vert[1],vert[2],u,v,0.0f,w});
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
