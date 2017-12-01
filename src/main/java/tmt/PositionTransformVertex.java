package tmt;

import java.util.ArrayList;

import net.minecraft.client.model.PositionTextureVertex;

public class PositionTransformVertex extends PositionTextureVertex {


	public PositionTransformVertex(float x, float y, float z, float u, float v){
		this(new Vec3f(x, y, z), u, v);
	}
	
	public PositionTransformVertex(PositionTextureVertex vertex, float u, float v){
		super(vertex, u, v);
	}
	
	public PositionTransformVertex(PositionTextureVertex vertex){
		this(vertex, vertex.texturePositionX, vertex.texturePositionY);
	}
	
	public PositionTransformVertex(Vec3f vector, float u, float v){
		super(new Vec3d(vector.xCoord, vector.yCoord, vector.zCoord), u, v);
	}
	
}
