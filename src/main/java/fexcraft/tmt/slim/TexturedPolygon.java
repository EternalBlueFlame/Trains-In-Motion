package fexcraft.tmt.slim;

import ebf.tim.utility.DebugUtil;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glGetError;


public class TexturedPolygon {


	public List<PositionTransformVertex> vertices;

	public TexturedPolygon(List<PositionTransformVertex> apositionTexturevertex){
		vertices = apositionTexturevertex;
	}

	public void draw(float f){
		switch (vertices.size()){
			case 3:{
				Tessellator.getInstance().startDrawing(GL11.GL_TRIANGLES);
				break;
			}
			case 4:{
				Tessellator.getInstance().startDrawing(GL11.GL_QUADS);
				break;
			}
			default:{
				Tessellator.getInstance().startDrawing(GL11.GL_POLYGON);
			}
		}

		for (PositionTransformVertex positionTexturevertex : vertices){
			Tessellator.getInstance().addVertexWithUV(positionTexturevertex.vector3F.xCoord * f, positionTexturevertex.vector3F.yCoord * f, positionTexturevertex.vector3F.zCoord * f, positionTexturevertex.textureX, positionTexturevertex.textureY);
		}
		Tessellator.getInstance().draw();
	}


	public TexturedPolygon flipFace() {
		List<PositionTransformVertex> apositiontexturevertex = new ArrayList<>();

		for (int i = 0; i < this.vertices.size(); ++i) {
			apositiontexturevertex.add(this.vertices.get(this.vertices.size() - i - 1));
		}

		this.vertices = apositiontexturevertex;
		return this;
	}

}