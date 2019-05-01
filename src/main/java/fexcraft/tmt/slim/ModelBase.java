package fexcraft.tmt.slim;

import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* Similar to 'FlansMod'-type Models, for a fast convert.
* @Author Ferdinand Calo' (FEX___96)
*/
public class ModelBase extends Model<Object> {

	public List<String> creators = new ArrayList<>();
	public ModelRendererTurbo base[],bodyModel[],open[],closed[],r1[],r2[],r3[],r4[],r5[],r6[],r7[],r8[],r9[],r0[];
	   
	public void render(){
		render(base);
		render(open);
		render(closed);
		render(r0);
		render(r1);
		render(r2);
		render(r3);
		render(r4);
		render(r5);
		render(r6);
		render(r7);
		render(r8);
		render(r9);
		render(bodyModel);
	}

	@Override
	public void render(Object type, Entity ent){
		render();
	}
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {render();}
	
	@Override
	public void translateAll(float x, float y, float z){
		translate(base, x, y, z);
		translate(open, x, y, z);
		translate(closed, x, y, z);
		translate(r0, x, y, z);
		translate(r1, x, y, z);
		translate(r2, x, y, z);
		translate(r3, x, y, z);
		translate(r4, x, y, z);
		translate(r5, x, y, z);
		translate(r6, x, y, z);
		translate(r7, x, y, z);
		translate(r8, x, y, z);
		translate(r9, x, y, z);
	}

	@Override
	public void rotateAll(float x, float y, float z){
		rotate(base, x, y, z);
		rotate(open, x, y, z);
		rotate(closed, x, y, z);
		rotate(r0, x, y, z);
		rotate(r1, x, y, z);
		rotate(r2, x, y, z);
		rotate(r3, x, y, z);
		rotate(r4, x, y, z);
		rotate(r5, x, y, z);
		rotate(r6, x, y, z);
		rotate(r7, x, y, z);
		rotate(r8, x, y, z);
		rotate(r9, x, y, z);
	}


	public void fixRotations(ModelRendererTurbo[] geometry){
		for(ModelRendererTurbo turbo : geometry){
			if(turbo.isShape) {
				turbo.rotateAngleY = -turbo.rotateAngleY;
				turbo.rotateAngleX = -turbo.rotateAngleX;
				turbo.rotateAngleZ = -turbo.rotateAngleZ + 3.14159f;
			} else {
				turbo.rotateAngleZ = -turbo.rotateAngleZ;
			}
		}
	}
	
}
