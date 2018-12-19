package fexcraft.fcl.tmt;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
* Replaces the old `ModelBase` in this package.
* @Author Ferdinand Calo' (FEX___96)
*/

public abstract class ModelBase {
	
	public static final ModelBase EMPTY = new EmptyModelBase();
	
	/** render whole model */
	public abstract void render();
	
	/** render sub-model array */
	public void render(ModelRendererTurbo[] model){
		for(ModelRendererTurbo sub : model){
			sub.render();
		}
	}
	
	public void render(ModelRendererTurbo[] model, float scale, boolean rotorder){
		for(ModelRendererTurbo sub : model){
			sub.render(scale);
		}
	}
	
	protected void translate(ModelRendererTurbo[] model, float x, float y, float z){
		for(ModelRendererTurbo mod : model){
			mod.rotationPointX += x;
			mod.rotationPointY += y;
			mod.rotationPointZ += z;
		}
	}
	
	public abstract void translate(float x, float y, float z);
	
	protected void rotate(ModelRendererTurbo[] model, float x, float y, float z){
		for(ModelRendererTurbo mod : model){
			mod.rotationAngleX += x;
			mod.rotationAngleY += y;
			mod.rotationAngleZ += z;
		}
	}
	
	public abstract void rotate(float x, float y, float z);
	
	/** Legacy Method */
	protected void flip(ModelRendererTurbo[] model){
		fixRotations(model);
	}
	
	/** Legacy Method */
	public void flipAll(){
		//To be overriden by extending classes.
	}
	
	/**
	 * Based on @EternalBlueFlame's fix.
	 * @param array ModelRendererTurbo Array
	 */
	public static void fixRotations(ModelRendererTurbo[] array){
        for(ModelRendererTurbo model : array){
            if(model.isShape3D){
                model.rotationAngleY = -model.rotationAngleY;
                model.rotationAngleX = -model.rotationAngleX;
                model.rotationAngleZ = -model.rotationAngleZ + 180f;//Static.rad180;
            }
            else{
                model.rotationAngleZ = -model.rotationAngleZ;
            }
        }
    }
	
	public static final void bindTexture(ResourceLocation rs){
		Minecraft.getMinecraft().renderEngine.bindTexture(rs);
		//TextureManager.bindTexture(rs);
	}
	
}