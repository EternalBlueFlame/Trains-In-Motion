package ebf.tim.models.tmt;

import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * An extension of TexturedQuad that adds support for more shapes beyond simple quads.
 */
public class TexturedCube {
	public TexturedCube(PositionTransformVertex avert[]) {
		this.vertexPositions = avert;
    }


	public static void drawQuads(Tessellator tessellator, float f, List<TexturedCube> faces) {

		tessellator.startDrawing();
		for(TexturedCube quad: faces) {
			for(PositionTransformVertex vert : quad.vertexPositions){
				vert.setTransformation();
				tessellator.addVertexWithUV(
						vert.vector3D.xCoord * f,
						vert.vector3D.yCoord * f,
						vert.vector3D.zCoord * f,
						vert.texturePositionX, vert.texturePositionY);
			}
		}

		tessellator.draw();
	}

	public void flipFace() {
		PositionTransformVertex[] apositiontexturevertex = new PositionTransformVertex[this.vertexPositions.length];
		for (int i = 0; i < this.vertexPositions.length; ++i) {
			apositiontexturevertex[i] = this.vertexPositions[this.vertexPositions.length - i - 1];
		}

		this.vertexPositions = apositiontexturevertex;
	}

	public PositionTransformVertex[] vertexPositions;
}
