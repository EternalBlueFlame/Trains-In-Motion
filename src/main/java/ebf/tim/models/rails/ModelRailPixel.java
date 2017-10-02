//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator:
// Created on:17.04.2017 - 16:25:28
// Last changed on: 17.04.2017 - 16:25:28

package ebf.tim.models.rails;

import ebf.tim.models.tmt.ModelBase;
import ebf.tim.models.tmt.ModelRendererTurbo;

public class ModelRailPixel extends ModelBase
{
	int textureX = 128;
	int textureY = 32;

	public ModelRailPixel()
	{
		baseModel = new ModelRendererTurbo[6];
		baseModel[0] = new ModelRendererTurbo(this, 1, 7, textureX, textureY); // Box 6
		baseModel[1] = new ModelRendererTurbo(this, 1, 3, textureX, textureY); // Box 9
		baseModel[2] = new ModelRendererTurbo(this, 1, 3, textureX, textureY); // Box 6
		baseModel[3] = new ModelRendererTurbo(this, 1, 3, textureX, textureY); // Box 7
		baseModel[4] = new ModelRendererTurbo(this, 1, 7, textureX, textureY); // Box 8
		baseModel[5] = new ModelRendererTurbo(this, 1, 3, textureX, textureY); // Box 9

		baseModel[0].addBox(-0.5F, 6F, -6.5F, 1, 1, 1, 0F); // Box 6
		baseModel[0].setRotationPoint(0F, 0F, 0F);

		baseModel[1].addBox(-0.5F, 5F, -7F, 1, 1, 2, 0F); // Box 9
		baseModel[1].setRotationPoint(0F, 0F, 0F);

		baseModel[2].addBox(-0.5F, 7F, -7F, 1, 1, 2, 0F); // Box 6
		baseModel[2].setRotationPoint(0F, 0F, 0F);

		baseModel[3].addBox(-0.5F, 5F, 5F, 1, 1, 2, 0F); // Box 7
		baseModel[3].setRotationPoint(0F, 0F, 0F);

		baseModel[4].addBox(-0.5F, 6F, 5.5F, 1, 1, 1, 0F); // Box 8
		baseModel[4].setRotationPoint(0F, 0F, 0F);

		baseModel[5].addBox(-0.5F, 7F, 5F, 1, 1, 2, 0F); // Box 9
		baseModel[5].setRotationPoint(0F, 0F, 0F);


	}
}