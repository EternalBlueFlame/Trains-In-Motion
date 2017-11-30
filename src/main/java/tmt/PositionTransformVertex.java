package tmt;

import java.util.ArrayList;

import net.minecraft.client.model.PositionTextureVertex;

public class PositionTransformVertex extends PositionTextureVertex {
	
	public Vec3f neutralVector;
	public ArrayList<TransformGroup> transformGroups = new ArrayList<TransformGroup>();

	public PositionTransformVertex(float x, float y, float z, float u, float v){
		this(new Vec3f(x, y, z), u, v);
	}
	
	public PositionTransformVertex(PositionTextureVertex vertex, float u, float v){
		super(vertex, u, v);
		if(vertex instanceof PositionTransformVertex){
			neutralVector = ((PositionTransformVertex)vertex).neutralVector;
		}
		else{
			neutralVector = new Vec3f((float)vertex.vector3D.xCoord, (float)vertex.vector3D.yCoord, (float)vertex.vector3D.zCoord);
		}
	}
	
	public PositionTransformVertex(PositionTextureVertex vertex){
		this(vertex, vertex.texturePositionX, vertex.texturePositionY);
	}
	
	public PositionTransformVertex(Vec3f vector, float u, float v){
		super(new Vec3d(vector.xCoord, vector.yCoord, vector.zCoord), u, v);
		neutralVector = new Vec3f(vector.xCoord, vector.yCoord, vector.zCoord);
	}
	
	public void setTransformation(){
		if(transformGroups.size() == 0){
			vector3D = new Vec3d(neutralVector.xCoord, neutralVector.yCoord, neutralVector.zCoord);
			return;
		}
		double weight = 0D;
		for(TransformGroup group : transformGroups){
			weight += group.getWeight();
		}
		vector3D = new Vec3d(0, 0, 0);
		for(int i = 0; i < transformGroups.size(); i++){
			TransformGroup group = transformGroups.get(i);
			double cWeight = group.getWeight() / weight;
			Vec3f vector = group.doTransformation(this);
			vector3D.addVector(cWeight * vector.xCoord, cWeight * vector.yCoord, cWeight * vector.zCoord);
		}
	}
	
	public void addGroup(TransformGroup group){
		transformGroups.add(group);
	}
	
	public void removeGroup(TransformGroup group){
		transformGroups.remove(group);
	}
	
}
