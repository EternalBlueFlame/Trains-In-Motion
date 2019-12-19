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
			Tessellator.setNormal(vertices.get(0).vector3F, vertices.get(1).vector3F,vertices.get(2).vector3F);
		} else {
			Tessellator.getInstance().startDrawing(vertices.size()==4?GL11.GL_QUADS:GL11.GL_POLYGON);

			if(vertices.get(0)==vertices.get(1) || vertices.get(1)==vertices.get(2)){
				Tessellator.setNormal(vertices.get(1).vector3F, vertices.get(2).vector3F, vertices.get(3).vector3F);
			} else {
				Tessellator.setNormal(vertices.get(0).vector3F, vertices.get(1).vector3F, vertices.get(2).vector3F);
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