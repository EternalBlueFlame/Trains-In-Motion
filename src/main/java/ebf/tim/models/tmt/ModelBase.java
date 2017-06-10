package ebf.tim.models.tmt;

/**
 * Converts ModelBase use to ModelRendererTurbo.
 */
public class ModelBase extends net.minecraft.client.model.ModelBase {

	public ModelRendererTurbo base[] = new ModelRendererTurbo[0];

	public void render(){
		render(base);
	}
	
	public void render(ModelRendererTurbo[] part){
		for(ModelRendererTurbo mrt : part){
			mrt.render();
		}
	}
	
	protected void translate(ModelRendererTurbo[] model, float x, float y, float z){
		for(ModelRendererTurbo mod : model){
			mod.rotationPointX += x;
			mod.rotationPointY += y;
			mod.rotationPointZ += z;
		}
	}
	
	public void translateAll(float x, float y, float z){
		translate(base, x, y, z);
	}
}
