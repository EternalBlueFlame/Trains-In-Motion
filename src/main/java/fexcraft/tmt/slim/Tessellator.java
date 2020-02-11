package fexcraft.tmt.slim;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
* @Author EternalBlueFlame
* 
*/
@SideOnly(Side.CLIENT)
public class Tessellator{

	public static Tessellator INSTANCE = new Tessellator();

	private static int i,j;//only used for calculations
	private static Float x, y, z;
	private static Vec3f normal=null, p1;//p1 only used for calculations
	private static List<float[]> verticies = new ArrayList<>(); //0,1,2 are the position, 3,4,5,6 are the texture vectors.

	public static Tessellator getInstance(){
		return INSTANCE;
	}

	//use this to reset and define the drawing mode
	public void startDrawing(int mode){
		verticies=new ArrayList<>();
		normal=null;
		GL11.glBegin(mode);
	}

	public void draw(){
		if(normal!=null) {
			GL11.glNormal3f(normal.xCoord, normal.yCoord, normal.zCoord);
		}
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

	public static void setNormal(List<TexturedVertex> vertex){
		normal=new Vec3f(0,0,0);
		Vec3f p0,p1;
		for(int i=0, j=1; i<vertex.size(); i++,j++) {

			if (j == vertex.size()){j=1;}
			p0 = vertex.get(i).vector3F;// current vertex
			p1 = vertex.get(j).vector3F;// next vertex

			normal.xCoord += (p0.yCoord - p1.yCoord) * (p0.zCoord + p1.zCoord);
			normal.yCoord += (p0.zCoord - p1.zCoord) * (p0.xCoord + p1.xCoord);
			normal.zCoord += (p0.xCoord - p1.xCoord) * (p0.yCoord + p1.yCoord);
		}
	}


	public void addTexturedVertsWithNormal(List<TexturedVertex> vertexList){
		i=0;j=1;
		normal=new Vec3f(0,0,0);
		for(TexturedVertex vert : vertexList) {
			if (x != null) {
				verticies.add(new float[]{
						vert.vector3F.xCoord + x, vert.vector3F.yCoord + y, vert.vector3F.zCoord + z,
						vert.textureX, vert.textureY});
			} else {
				verticies.add(new float[]{
						vert.vector3F.xCoord, vert.vector3F.yCoord, vert.vector3F.zCoord,
						vert.textureX, vert.textureY});
			}

			if (j == vertexList.size()){j=1;}
			p1 = vertexList.get(j).vector3F;// next vertex

			normal.xCoord += (vert.vector3F.yCoord - p1.yCoord) * (vert.vector3F.zCoord + p1.zCoord);
			normal.yCoord += (vert.vector3F.zCoord - p1.zCoord) * (vert.vector3F.xCoord + p1.xCoord);
			normal.zCoord += (vert.vector3F.xCoord - p1.xCoord) * (vert.vector3F.yCoord + p1.yCoord);
		}
	}


	public void drawTexturedVertsWithNormal(List<TexturedVertex> vertexList, float scale){
		verticies=new ArrayList<>();
		normal=null;
		i=0;j=1;
		normal=new Vec3f(0,0,0);
		GL11.glBegin(vertexList.size()==4?GL11.GL_QUADS:vertexList.size()==3?GL11.GL_TRIANGLES:GL11.GL_POLYGON);
		for(TexturedVertex vert : vertexList) {
			GL11.glTexCoord2f(vert.textureX, vert.textureY);
			if (x != null) {
				GL11.glVertex3f((vert.vector3F.xCoord + x)*scale, (vert.vector3F.yCoord + y)*scale, (vert.vector3F.zCoord + z)*scale);
			} else {
				GL11.glVertex3f(vert.vector3F.xCoord*scale, vert.vector3F.yCoord*scale, vert.vector3F.zCoord*scale);
			}

			if (j == vertexList.size()){j=1;}
			p1 = vertexList.get(j).vector3F;// next vertex

			normal.xCoord += (vert.vector3F.yCoord - p1.yCoord) * (vert.vector3F.zCoord + p1.zCoord);
			normal.yCoord += (vert.vector3F.zCoord - p1.zCoord) * (vert.vector3F.xCoord + p1.xCoord);
			normal.zCoord += (vert.vector3F.xCoord - p1.xCoord) * (vert.vector3F.yCoord + p1.yCoord);
		}
		GL11.glNormal3f(normal.xCoord, normal.yCoord, normal.zCoord);
		GL11.glEnd();
	}



}
