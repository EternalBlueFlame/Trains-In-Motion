package fexcraft.tmt.slim;

import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;


public class TexturedPolygon {


	public List<PositionTransformVertex> vertices;

	public TexturedPolygon(List<PositionTransformVertex> apositionTexturevertex){
		vertices = apositionTexturevertex;
	}

	public void draw(float f){
		if(vertices.size()==3){
			Tessellator.getInstance().startDrawing(GL11.GL_TRIANGLES);
		} else {
			Tessellator.getInstance().startDrawing(vertices.size()==4?GL11.GL_QUADS:GL11.GL_POLYGON);
		}

		for (PositionTransformVertex positionTexturevertex : vertices){
			Tessellator.getInstance().addVertexWithUV(positionTexturevertex.vector3F.xCoord * f, positionTexturevertex.vector3F.yCoord * f, positionTexturevertex.vector3F.zCoord * f, positionTexturevertex.textureX, positionTexturevertex.textureY);
		}
		Tessellator.setNormal(vertices);
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