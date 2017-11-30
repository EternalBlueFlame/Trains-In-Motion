package tmt;


public abstract class TransformGroup {
	
	public abstract double getWeight();
	public abstract Vec3f doTransformation(PositionTransformVertex vertex);
	
}
