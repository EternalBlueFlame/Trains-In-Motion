package tmt;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;

public class TexturedPolygon extends TexturedQuad {

    private boolean invertNormal;
    private float[] normals;
    private ArrayList<Vec3f> iNormals;
	
	public TexturedPolygon(PositionTextureVertex apositionTexturevertex[]){
		super(apositionTexturevertex);
		invertNormal = false;
		normals = new float[0];
		iNormals = new ArrayList<Vec3f>();
    }
	
	public void setInvertNormal(boolean isSet){
		invertNormal = isSet;
	}
	
	public void setNormals(float x, float y, float z){
		normals = new float[] {x, y, z};
	}
	
	public void setNormals(ArrayList<Vec3f> iNormal){
		iNormals = iNormal;
	}
	
	public void draw(Tessellator tessellator, float f){
        if(nVertices == 3){
        	tessellator.startDrawing(GL11.GL_TRIANGLES);
        }
        else if (nVertices == 4){
        	tessellator.startDrawing(GL11.GL_QUADS);
        }
        else{
        	tessellator.startDrawing(GL11.GL_POLYGON);
        }
        
        if(iNormals.size() == 0){
	        if(normals.length == 3){
	        	if(invertNormal){
	        		tessellator.setNormal(-normals[0], -normals[1], -normals[2]);
	        	}
	        	else{
	        		tessellator.setNormal(normals[0], normals[1], normals[2]);
	        	}
	        }
	        else if(vertexPositions.length >= 3){
		        Vec3f Vec3d = new Vec3f(vertexPositions[1].vector3D.subtract(vertexPositions[0].vector3D));
		        Vec3f Vec3d1 = new Vec3f(vertexPositions[1].vector3D.subtract(vertexPositions[2].vector3D));
		        Vec3f Vec3d2 = Vec3d1.crossProduct(Vec3d).normalize();
		        if(invertNormal){
		            tessellator.setNormal(-Vec3d2.xCoord, -Vec3d2.yCoord, -Vec3d2.zCoord);
		        }
		        else{
		            tessellator.setNormal(Vec3d2.xCoord, Vec3d2.yCoord, Vec3d2.zCoord);
		        }
	        }
	        else{
	        	return;
	        }
        }
        for(int i = 0; i < nVertices; i++){
            PositionTextureVertex positionTexturevertex = vertexPositions[i];
            if(positionTexturevertex instanceof PositionTransformVertex){
            	((PositionTransformVertex)positionTexturevertex).setTransformation();
            }
            if(i < iNormals.size()){
            	if(invertNormal){
            		tessellator.setNormal(-iNormals.get(i).xCoord, -iNormals.get(i).yCoord, -iNormals.get(i).zCoord);
            	}
            	else{
            		tessellator.setNormal(iNormals.get(i).xCoord, iNormals.get(i).yCoord, iNormals.get(i).zCoord);
            	}
            }
            tessellator.addVertexWithUV(positionTexturevertex.vector3D.xCoord * f, positionTexturevertex.vector3D.yCoord * f, positionTexturevertex.vector3D.zCoord * f, positionTexturevertex.texturePositionX, positionTexturevertex.texturePositionY);
        }
        tessellator.draw();
    }
	
}
