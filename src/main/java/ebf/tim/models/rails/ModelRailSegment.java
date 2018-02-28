//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator:
// Created on:-
// Last changed on: -

package ebf.tim.models.rails;

import net.minecraft.entity.Entity;
import tmt.ModelBase;
import tmt.ModelRendererTurbo;
import tmt.Vec3f;

public class ModelRailSegment extends ModelBase
{
	int textureX = 32;
	int textureY = 32;

	float segmentLength=1;
	public float distance=0;
	public final double railWidth=2;
	//modify these then regen the model. each vector 3 represents one of the points.
	public Vec3f[] railTop = new Vec3f[]{new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0)};
	public Vec3f[] railMiddle = new Vec3f[]{new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0)};
	public Vec3f[] railBottom = new Vec3f[]{new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0),new Vec3f(0,0,0)};

	public ModelRailSegment(float offset) {
		distance = offset;
	}

	public float[] position = new float[]{0,0,0};

	public void regenModel(){

		baseModel = new ModelRendererTurbo[3];
		baseModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 0
		baseModel[1] = new ModelRendererTurbo(this, 9, 1, textureX, textureY); // Box 3
		baseModel[2] = new ModelRendererTurbo(this, 17, 1, textureX, textureY); // Box 4

		baseModel[0].addShapeBox(position[0],position[1],position[2], segmentLength, 1, 4, 0F,
				railTop[0].xCoord-1, railTop[0].yCoord, railTop[0].zCoord,
				railTop[1].xCoord, railTop[1].yCoord, railTop[1].zCoord,
				railTop[2].xCoord, railTop[2].yCoord, railTop[2].zCoord,
				railTop[3].xCoord-1, railTop[3].yCoord, railTop[3].zCoord,
				railTop[4].xCoord-1, railTop[4].yCoord, railTop[4].zCoord,
				railTop[5].xCoord, railTop[5].yCoord, railTop[5].zCoord,
				railTop[6].xCoord, railTop[6].yCoord, railTop[6].zCoord,
				railTop[7].xCoord-1, railTop[7].yCoord, railTop[7].zCoord); // Box 0
		baseModel[0].setRotationPoint(-0.5F, -5F, -2F + distance);

		baseModel[1].addShapeBox(position[0],position[1],position[2], segmentLength, 1, 2, 0F,
				railMiddle[0].xCoord-1, railMiddle[0].yCoord, railMiddle[0].zCoord,
				railMiddle[1].xCoord, railMiddle[1].yCoord, railMiddle[1].zCoord,
				railMiddle[2].xCoord, railMiddle[2].yCoord, railMiddle[2].zCoord,
				railMiddle[3].xCoord-1, railMiddle[3].yCoord, railMiddle[3].zCoord,
				railMiddle[4].xCoord-1, railMiddle[4].yCoord, railMiddle[4].zCoord,
				railMiddle[5].xCoord, railMiddle[5].yCoord, railMiddle[5].zCoord,
				railMiddle[6].xCoord, railMiddle[6].yCoord, railMiddle[6].zCoord,
				railMiddle[7].xCoord-1, railMiddle[7].yCoord, railMiddle[7].zCoord); // Box 3
		baseModel[1].setRotationPoint(-0.5F, -4F, -1F +distance);

		baseModel[2].addShapeBox(position[0],position[1],position[2], segmentLength, 1, 4, 0F,
				railBottom[0].xCoord-1, railBottom[0].yCoord, railBottom[0].zCoord,
				railBottom[1].xCoord, railBottom[1].yCoord, railBottom[1].zCoord,
				railBottom[2].xCoord, railBottom[2].yCoord, railBottom[2].zCoord,
				railBottom[3].xCoord-1, railBottom[3].yCoord, railBottom[3].zCoord,
				railBottom[4].xCoord-1, railBottom[4].yCoord, railBottom[4].zCoord,
				railBottom[5].xCoord, railBottom[5].yCoord, railBottom[5].zCoord,
				railBottom[6].xCoord, railBottom[6].yCoord, railBottom[6].zCoord,
				railBottom[7].xCoord-1, railBottom[7].yCoord, railBottom[7].zCoord); // Box 4
		baseModel[2].setRotationPoint(-0.5F, -3F, -2F+distance);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		if (baseModel != null) {
			for (ModelRendererTurbo model : baseModel) {
				model.renderNoTexture(0.0625f);
			}
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
	}

	public ModelRendererTurbo[] baseModel = null;
}