package fexcraft.tmt.slim;

import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
* Replaces the old `ModelBase` in this package.
* @Author Ferdinand Calo' (FEX___96)
*/

public abstract class Model<T> {
	
	public static final Model<Object> EMPTY;
	private List<ModelRendererTurbo> boxList = new ArrayList<>();
	static {
		EMPTY = new Model<Object>(){
			@Override public void render(){}
			@Override public void render(Object type, Entity element){}
			@Override public void translateAll(float x, float y, float z){}
			@Override public void rotateAll(float x, float y, float z){}
		};
	}
	
	/** render whole model */
	public abstract void render();
	
	/** render sub-model array */
	public void render(ModelRendererTurbo[] model){
		if(model==null){return;}
		for(ModelRendererTurbo sub : model){
			if(sub!=null) {
				sub.render();
			}
		}
	}
	
	public void render(ModelRendererTurbo[] model, float scale){
		if(model==null){return;}
		for(ModelRendererTurbo sub : model){
			sub.render(scale);
		}
	}
	
	/** render whole model based on data and entity */
	public abstract void render(T type, Entity entity);
	
	protected void translate(ModelRendererTurbo[] model, float x, float y, float z){
		if(model==null){return;}
		for(ModelRendererTurbo mod : model){
			mod.rotationPointX += x;
			mod.rotationPointY += y;
			mod.rotationPointZ += z;
		}
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5){}
	
	public abstract void translateAll(float x, float y, float z);
	
	protected void rotate(ModelRendererTurbo[] model, float x, float y, float z) {
		if(model==null){return;}
		for(ModelRendererTurbo mod : model){
			mod.rotateAngleX += x;
			mod.rotateAngleY += y;
			mod.rotateAngleZ += z;
		}
	}
	
	public abstract void rotateAll(float x, float y, float z);
	
	protected final void fixRotation(ModelRendererTurbo[] model, boolean... bools){
		if(bools.length >= 1 && bools[0]){
			for(ModelRendererTurbo mod : model){
				mod.rotateAngleX = -mod.rotateAngleX;
			}
		}
		if(bools.length >= 2 && bools[1]){
			for(ModelRendererTurbo mod : model){
				mod.rotateAngleY = -mod.rotateAngleY;
			}
		}
		if(bools.length >= 3 && bools[2]){
			for(ModelRendererTurbo mod : model){
				mod.rotateAngleZ = -mod.rotateAngleZ;
			}
		}
	}

	public List<ModelRendererTurbo> getParts(){
		return boxList;
	}

	public List<ModelRendererTurbo> addPart(ModelRendererTurbo part){
		boxList.add(part);
		return boxList;
	}
}