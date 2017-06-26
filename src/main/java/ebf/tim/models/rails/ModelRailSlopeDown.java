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
import net.minecraft.entity.Entity;

public class ModelRailSlopeDown extends ModelBase
{
	int textureX = 128;
	int textureY = 32;

	public ModelRailSlopeDown()
	{
		baseModel = new ModelRendererTurbo[23];
		baseModel[0] = new ModelRendererTurbo(this, 20, 10, textureX, textureY); // Box 0
		baseModel[1] = new ModelRendererTurbo(this, 39, 6, textureX, textureY); // Box 1
		baseModel[2] = new ModelRendererTurbo(this, 58, 2, textureX, textureY); // Box 2
		baseModel[3] = new ModelRendererTurbo(this, 58, 29, textureX, textureY); // Box 4
		baseModel[4] = new ModelRendererTurbo(this, 58, 26, textureX, textureY); // Box 5
		baseModel[5] = new ModelRendererTurbo(this, 6, 7, textureX, textureY); // Box 6
		baseModel[6] = new ModelRendererTurbo(this, 6, 7, textureX, textureY); // Box 7
		baseModel[7] = new ModelRendererTurbo(this, 40, 2, textureX, textureY); // Box 8
		baseModel[8] = new ModelRendererTurbo(this, 6, 3, textureX, textureY); // Box 9
		baseModel[9] = new ModelRendererTurbo(this, 15, 7, textureX, textureY); // Box 35
		baseModel[10] = new ModelRendererTurbo(this, 1, 22, textureX, textureY); // Box 36
		baseModel[11] = new ModelRendererTurbo(this, 42, 14, textureX, textureY); // Box 37
		baseModel[12] = new ModelRendererTurbo(this, 14, 7, textureX, textureY); // Box 38
		baseModel[13] = new ModelRendererTurbo(this, 1, 18, textureX, textureY); // Box 48
		baseModel[14] = new ModelRendererTurbo(this, 22, 14, textureX, textureY); // Box 49
		baseModel[15] = new ModelRendererTurbo(this, 1, 10, textureX, textureY); // Box 50
		baseModel[16] = new ModelRendererTurbo(this, 22, 18, textureX, textureY); // Box 51
		baseModel[17] = new ModelRendererTurbo(this, 15, 7, textureX, textureY); // Box 52
		baseModel[18] = new ModelRendererTurbo(this, 14, 7, textureX, textureY); // Box 53
		baseModel[19] = new ModelRendererTurbo(this, 42, 10, textureX, textureY); // Box 54
		baseModel[20] = new ModelRendererTurbo(this, 1, 14, textureX, textureY); // Box 55
		baseModel[21] = new ModelRendererTurbo(this, 3, 14, textureX, textureY); // Box 21
		baseModel[22] = new ModelRendererTurbo(this, 1, 14, textureX, textureY); // Box 22

		baseModel[0].addBox(0F, 0F, -7F, 2, 2, 14, 0F); // Box 0
		baseModel[0].setRotationPoint(1F, 8F, 0F);

		baseModel[1].addBox(0F, 0F, -7F, 2, 2, 14, 0F); // Box 1
		baseModel[1].setRotationPoint(-3F, 8F, 0F);

		baseModel[2].addBox(0F, 0F, -7F, 2, 2, 14, 0F); // Box 2
		baseModel[2].setRotationPoint(-7F, 8F, 0F);

		baseModel[3].addBox(-8F, 0F, 0F, 11, 1, 2, 0F); // Box 4
		baseModel[3].setRotationPoint(0F, 7F, -6F);

		baseModel[4].addBox(-8F, 0F, 0F, 11, 1, 2, 0F); // Box 5
		baseModel[4].setRotationPoint(0F, 7F, 4F);

		baseModel[5].addBox(-8F, 0F, 0F, 11, 1, 1, 0F); // Box 6
		baseModel[5].setRotationPoint(0F, 6F, -5.5F);

		baseModel[6].addBox(-8F, 0F, 0F, 11, 1, 1, 0F); // Box 7
		baseModel[6].setRotationPoint(0F, 6F, 4.5F);

		baseModel[7].addBox(-8F, 0F, 0F, 11, 1, 2, 0F); // Box 8
		baseModel[7].setRotationPoint(0F, 5F, 4F);

		baseModel[8].addBox(-8F, 0F, 0F, 11, 1, 2, 0F); // Box 9
		baseModel[8].setRotationPoint(0F, 5F, -6F);

		baseModel[9].addShapeBox(-8F, 0F, 0F, 2, 1, 1, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F); // Box 35
		baseModel[9].setRotationPoint(14F, 7F, 4.5F);

		baseModel[10].addShapeBox(-8F, 0F, 0F, 2, 1, 2, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F); // Box 36
		baseModel[10].setRotationPoint(14F, 6F, 4F);

		baseModel[11].addShapeBox(-8F, 0F, 0F, 3, 1, 2, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F); // Box 37
		baseModel[11].setRotationPoint(11F, 5F, 4F);

		baseModel[12].addShapeBox(-8F, 0F, 0F, 3, 1, 1, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F); // Box 38
		baseModel[12].setRotationPoint(11F, 6F, 4.5F);

		baseModel[13].addShapeBox(-8F, 0F, 0F, 2, 1, 2, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F); // Box 48
		baseModel[13].setRotationPoint(14F, 8F, 4F);

		baseModel[14].addShapeBox(-8F, 0F, 0F, 3, 1, 2, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F); // Box 49
		baseModel[14].setRotationPoint(11F, 7F, 4F);

		baseModel[15].addShapeBox(-8F, 0F, 0F, 2, 1, 2, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F); // Box 50
		baseModel[15].setRotationPoint(14F, 8F, -6F);

		baseModel[16].addShapeBox(-8F, 0F, 0F, 3, 1, 2, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F); // Box 51
		baseModel[16].setRotationPoint(11F, 7F, -6F);

		baseModel[17].addShapeBox(-8F, 0F, 0F, 2, 1, 1, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F); // Box 52
		baseModel[17].setRotationPoint(14F, 7F, -5.5F);

		baseModel[18].addShapeBox(-8F, 0F, 0F, 3, 1, 1, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F); // Box 53
		baseModel[18].setRotationPoint(11F, 6F, -5.5F);

		baseModel[19].addShapeBox(-8F, 0F, 0F, 3, 1, 2, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F); // Box 54
		baseModel[19].setRotationPoint(11F, 5F, -6F);

		baseModel[20].addShapeBox(-8F, 0F, 0F, 2, 1, 2, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F); // Box 55
		baseModel[20].setRotationPoint(14F, 6F, -6F);

		baseModel[21].addShapeBox(0F, 0F, -7F, 1, 2, 14, 0F, 0F, -0.65F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, -0.65F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 21
		baseModel[21].setRotationPoint(5F, 8F, 0F);

		baseModel[22].addShapeBox(0F, 0F, -7F, 1, 2, 14, 0F, 0F, -1F, 0F, 0F, -1.5F, 0F, 0F, -1.5F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 22
		baseModel[22].setRotationPoint(6F, 8F, 0F);

	}
}