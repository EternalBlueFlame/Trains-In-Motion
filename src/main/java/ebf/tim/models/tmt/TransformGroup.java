package ebf.tim.models.tmt;


public abstract class TransformGroup
{
	public abstract double getWeight();
	public abstract Vec3d doTransformation(PositionTransformVertex vertex);
}
