package ebf.tim.models.tmt;

import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.util.Vec3;

import java.util.ArrayList;

/**
 * an extension to PositionTextureVertex that adds support for the neutral vector.
 */
public class PositionTransformVertex {
	public Vec3d vector3D;
	public float texturePositionX;
	public float texturePositionY;

	public PositionTransformVertex(double x, double y, double z, float u, float v) {
		this(new Vec3d(x, y, z), u, v);
	}

	public PositionTransformVertex(PositionTransformVertex vertex, float u, float v) {
		vector3D = vertex.vector3D;
		texturePositionX = u;
		texturePositionY = v;
		neutralVector = vertex.neutralVector;
	}

	public PositionTransformVertex setTexturePosition(float p_78240_1_, float p_78240_2_)
	{
		return new PositionTransformVertex(this, p_78240_1_, p_78240_2_);
	}
	
	public PositionTransformVertex(Vec3d vector, float u, float v) {
		texturePositionX = u;
		texturePositionY = v;
		vector3D = neutralVector = vector;
	}
	
	public void setTransformation() {
		if(transformGroups.size() == 0) {
			vector3D = neutralVector;
			return;
		}
		double weight = 0D;
		for(TransformGroupBone transform : transformGroups) {
			weight += transform.getWeight();
		}
		vector3D.xCoord = 0;
		vector3D.yCoord = 0;
		vector3D.zCoord = 0;

		for(TransformGroupBone transform : transformGroups) {
			double cWeight = transform.getWeight() / weight;
			Vec3d vector = transform.doTransformation(this);
			
			vector3D.xCoord += cWeight * vector.xCoord;
			vector3D.yCoord += cWeight * vector.yCoord;
			vector3D.zCoord += cWeight * vector.zCoord;
		}
	}
	
	public void addGroup(TransformGroupBone group)
	{
		transformGroups.add(group);
	}
	
	public void removeGroup(TransformGroupBone group)
	{
		transformGroups.remove(group);
	}

	public Vec3d neutralVector;
	public ArrayList<TransformGroupBone> transformGroups = new ArrayList<TransformGroupBone>();
	
}
