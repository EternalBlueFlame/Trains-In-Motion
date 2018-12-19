package fexcraft.fcl.common.math;

import java.util.ArrayList;
import java.util.List;

import fexcraft.fcl.tmt.Tessellator;
import org.lwjgl.opengl.GL11;

/**
 * Based on TMT, also compatible/required by the TMT branch shipped with FCL.
 */
public class TexturedPolygon {

    private boolean invert;
    private float[] normals;
    private ArrayList<Vec3f> list;
    private TexturedVertex[] vertices;
	
	public TexturedPolygon(TexturedVertex[] verts){
		this.vertices = verts; invert = false;
		normals = new float[0]; list = new ArrayList<Vec3f>();
    }

	public void setInvert(boolean bool){ invert = bool; }
	
	public void setNormals(float x, float y, float z){ normals = new float[] {x, y, z}; }
	
	public void setNormals(ArrayList<Vec3f> normallist){ list = normallist; }
	
	public void draw(Tessellator tess, float scale, RGB lincol, RGB rgb){
		if(lincol != null){ tess.startDrawing(3); tess.setColor(lincol); }
		else{
	        switch(vertices.length){
		        case 3: tess.startDrawing(GL11.GL_TRIANGLES); break;
		        case 4: tess.startDrawing(GL11.GL_QUADS); break;
		        default: tess.startDrawing(GL11.GL_POLYGON); break;
	        };
		}
        if(list.isEmpty()){
	        if(normals.length == 3){
	        	if(invert){ tess.setNormal(-normals[0], -normals[1], -normals[2]); }
	        	else{ tess.setNormal(normals[0], normals[1], normals[2]); }
	        }
	        else if(vertices.length >= 3){
		        Vec3f vec0 = new Vec3f(vertices[1].vector.subtract(vertices[0].vector));
		        Vec3f vec1 = new Vec3f(vertices[1].vector.subtract(vertices[2].vector));
		        Vec3f vec2 = vec1.crossProduct(vec0).normalize();
		        if(invert){ tess.setNormal(-vec2.xCoord, -vec2.yCoord, -vec2.zCoord); }
		        else{ tess.setNormal(vec2.xCoord, vec2.yCoord, vec2.zCoord); }
	        }
	        else{ return; }
        }
        for(int i = 0; i < vertices.length; i++){
        	TexturedVertex texvex = vertices[i];
            if(i < list.size()){
            	if(invert){ tess.setNormal(-list.get(i).xCoord, -list.get(i).yCoord, -list.get(i).zCoord); }
            	else{ tess.setNormal(list.get(i).xCoord, list.get(i).yCoord, list.get(i).zCoord); }
            }
            if(rgb == null){
            	tess.addVertexWithUV(texvex.vector.xCoord * scale, texvex.vector.yCoord * scale, texvex.vector.zCoord * scale, texvex.textureX, texvex.textureY);
            }
            else{
            	tess.addVertex(texvex.vector.xCoord * scale, texvex.vector.yCoord * scale, texvex.vector.zCoord * scale);
            	tess.setColor(rgb);
            }
        }
        tess.draw();
    }

    public void flipFace(){
        TexturedVertex[] tva = new TexturedVertex[vertices.length];
        for(int i = 0; i < vertices.length; ++i){
            tva[i] = vertices[vertices.length - i - 1];
        } vertices = tva;
    }

	public TexturedVertex[] getVertices(){
		return vertices;
	}

	public void clearNormals(){
		normals = new float[0]; list = new ArrayList<Vec3f>();
	}

	public boolean isInverted(){
		return invert;
	}

	public float[] getNormals(){
		return normals;
	}

	public List<Vec3f> getVectors(){
		return list;
	}
	
}