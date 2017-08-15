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

public class ModelRailSlopeUp extends ModelBase
{
	int textureX = 128;
	int textureY = 32;

	public ModelRailSlopeUp()
	{
		baseModel = new ModelRendererTurbo[29];
		baseModel[0] = new ModelRendererTurbo(this, 20, 10, textureX, textureY); // Box 0
		baseModel[1] = new ModelRendererTurbo(this, 39, 6, textureX, textureY); // Box 1
		baseModel[2] = new ModelRendererTurbo(this, 58, 2, textureX, textureY); // Box 2
		baseModel[3] = new ModelRendererTurbo(this, 60, 26, textureX, textureY); // Box 4
		baseModel[4] = new ModelRendererTurbo(this, 60, 29, textureX, textureY); // Box 5
		baseModel[5] = new ModelRendererTurbo(this, 8, 7, textureX, textureY); // Box 6
		baseModel[6] = new ModelRendererTurbo(this, 8, 7, textureX, textureY); // Box 7
		baseModel[7] = new ModelRendererTurbo(this, 42, 2, textureX, textureY); // Box 8
		baseModel[8] = new ModelRendererTurbo(this, 8, 3, textureX, textureY); // Box 9
		baseModel[9] = new ModelRendererTurbo(this, 14, 7, textureX, textureY); // Box 35
		baseModel[10] = new ModelRendererTurbo(this, 42, 14, textureX, textureY); // Box 37
		baseModel[11] = new ModelRendererTurbo(this, 13, 7, textureX, textureY); // Box 38
		baseModel[12] = new ModelRendererTurbo(this, 22, 18, textureX, textureY); // Box 40
		baseModel[13] = new ModelRendererTurbo(this, 22, 14, textureX, textureY); // Box 41
		baseModel[14] = new ModelRendererTurbo(this, 13, 7, textureX, textureY); // Box 42
		baseModel[15] = new ModelRendererTurbo(this, 42, 10, textureX, textureY); // Box 43
		baseModel[16] = new ModelRendererTurbo(this, 1, 18, textureX, textureY); // Box 44
		baseModel[17] = new ModelRendererTurbo(this, 1, 10, textureX, textureY); // Box 45
		baseModel[18] = new ModelRendererTurbo(this, 14, 7, textureX, textureY); // Box 46
		baseModel[19] = new ModelRendererTurbo(this, 1, 14, textureX, textureY); // Box 47
		baseModel[20] = new ModelRendererTurbo(this, 3, 14, textureX, textureY); // Box 21
		baseModel[21] = new ModelRendererTurbo(this, 1, 14, textureX, textureY); // Box 22
		baseModel[22] = new ModelRendererTurbo(this, 1, 22, textureX, textureY); // Box 23
		baseModel[23] = new ModelRendererTurbo(this, 1, 14, textureX, textureY); // Box 23
		baseModel[24] = new ModelRendererTurbo(this, 1, 22, textureX, textureY); // Box 24
		baseModel[25] = new ModelRendererTurbo(this, 14, 7, textureX, textureY); // Box 25
		baseModel[26] = new ModelRendererTurbo(this, 1, 10, textureX, textureY); // Box 26
		baseModel[27] = new ModelRendererTurbo(this, 1, 18, textureX, textureY); // Box 27
		baseModel[28] = new ModelRendererTurbo(this, 14, 7, textureX, textureY); // Box 28

		baseModel[0].addShapeBox(0F, 0F, -7F, 2, 2, 14, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 0
		baseModel[0].setRotationPoint(1F, 8F, 0F);

		baseModel[1].addBox(0F, 0F, -7F, 2, 2, 14, 0F); // Box 1
		baseModel[1].setRotationPoint(-3F, 8F, 0F);

		baseModel[2].addBox(0F, 0F, -7F, 2, 2, 14, 0F); // Box 2
		baseModel[2].setRotationPoint(-7F, 8F, 0F);

		baseModel[3].addBox(-8F, 0F, 0F, 9, 1, 2, 0F); // Box 4
		baseModel[3].setRotationPoint(0F, 7F, -6F);

		baseModel[4].addBox(-8F, 0F, 0F, 9, 1, 2, 0F); // Box 5
		baseModel[4].setRotationPoint(0F, 7F, 4F);

		baseModel[5].addBox(-8F, 0F, 0F, 9, 1, 1, 0F); // Box 6
		baseModel[5].setRotationPoint(0F, 6F, -5.5F);

		baseModel[6].addBox(-8F, 0F, 0F, 9, 1, 1, 0F); // Box 7
		baseModel[6].setRotationPoint(0F, 6F, 4.5F);

		baseModel[7].addBox(-8F, 0F, 0F, 9, 1, 2, 0F); // Box 8
		baseModel[7].setRotationPoint(0F, 5F, 4F);

		baseModel[8].addBox(-8F, 0F, 0F, 9, 1, 2, 0F); // Box 9
		baseModel[8].setRotationPoint(0F, 5F, -6F);

		baseModel[9].addShapeBox(-8F, 0F, 0F, 2, 1, 1, 0F, 0F, 0F, 0F, 0F, 3F, 0F, 0F, 3F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -3F, 0F, 0F, -3F, 0F, 0F, 0F, 0F); // Box 35
		baseModel[9].setRotationPoint(14F, 3F, 4.5F);

		baseModel[10].addShapeBox(-8F, 0F, 0F, 3, 1, 2, 0F, 0F, 0F, 0F, 0F, 2F, 0F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -2F, 0F, 0F, -2F, 0F, 0F, 0F, 0F); // Box 37
		baseModel[10].setRotationPoint(11F, 4F, 4F);

		baseModel[11].addShapeBox(-8F, 0F, 0F, 3, 1, 1, 0F, 0F, 0F, 0F, 0F, 2F, 0F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -2F, 0F, 0F, -2F, 0F, 0F, 0F, 0F); // Box 38
		baseModel[11].setRotationPoint(11F, 5F, 4.5F);

		baseModel[12].addShapeBox(-8F, 0F, 0F, 3, 1, 2, 0F, 0F, 0F, 0F, 0F, 2F, 0F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -2F, 0F, 0F, -2F, 0F, 0F, 0F, 0F); // Box 40
		baseModel[12].setRotationPoint(11F, 6F, 4F);

		baseModel[13].addShapeBox(-8F, 0F, 0F, 3, 1, 2, 0F, 0F, 0F, 0F, 0F, 2F, 0F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -2F, 0F, 0F, -2F, 0F, 0F, 0F, 0F); // Box 41
		baseModel[13].setRotationPoint(11F, 6F, -6F);

		baseModel[14].addShapeBox(-8F, 0F, 0F, 3, 1, 1, 0F, 0F, 0F, 0F, 0F, 2F, 0F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -2F, 0F, 0F, -2F, 0F, 0F, 0F, 0F); // Box 42
		baseModel[14].setRotationPoint(11F, 5F, -5.5F);

		baseModel[15].addShapeBox(-8F, 0F, 0F, 3, 1, 2, 0F, 0F, 0F, 0F, 0F, 2F, 0F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -2F, 0F, 0F, -2F, 0F, 0F, 0F, 0F); // Box 43
		baseModel[15].setRotationPoint(11F, 4F, -6F);

		baseModel[16].addShapeBox(-8F, 0F, 0F, 2, 1, 2, 0F, 0F, 0F, 0F, 0F, 3F, 0F, 0F, 3F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -3F, 0F, 0F, -3F, 0F, 0F, 0F, 0F); // Box 44
		baseModel[16].setRotationPoint(14F, 4F, 4F);

		baseModel[17].addShapeBox(-8F, 0F, 0F, 2, 1, 2, 0F, 0F, 0F, 0F, 0F, 3F, 0F, 0F, 3F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -3F, 0F, 0F, -3F, 0F, 0F, 0F, 0F); // Box 45
		baseModel[17].setRotationPoint(14F, 4F, -6F);

		baseModel[18].addShapeBox(-8F, 0F, 0F, 2, 1, 1, 0F, 0F, 0F, 0F, 0F, 3F, 0F, 0F, 3F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -3F, 0F, 0F, -3F, 0F, 0F, 0F, 0F); // Box 46
		baseModel[18].setRotationPoint(14F, 3F, -5.5F);

		baseModel[19].addShapeBox(-8F, 0F, 0F, 2, 1, 2, 0F, 0F, 0F, 0F, 0F, 3F, 0F, 0F, 3F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -3F, 0F, 0F, -3F, 0F, 0F, 0F, 0F); // Box 47
		baseModel[19].setRotationPoint(14F, 2F, -6F);

		baseModel[20].addShapeBox(0F, 0F, -7F, 1, 3, 14, 0F, 0F, 0.3F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0.3F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 1F, 0F); // Box 21
		baseModel[20].setRotationPoint(5F, 6F, 0F);

		baseModel[21].addShapeBox(0F, 0F, -7F, 1, 3, 14, 0F, 0F, 1F, 0F, 0F, 2.5F, 0F, 0F, 2.5F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 1F, 0F); // Box 22
		baseModel[21].setRotationPoint(6F, 6F, 0F);

		baseModel[22].addShapeBox(-8F, 0F, 0F, 2, 1, 2, 0F, 0F, 0F, 0F, 0F, 3F, 0F, 0F, 3F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -3F, 0F, 0F, -3F, 0F, 0F, 0F, 0F); // Box 23
		baseModel[22].setRotationPoint(14F, 2F, 4F);

		baseModel[23].addShapeBox(-8F, 0F, 0F, 2, 1, 2, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F); // Box 23
		baseModel[23].setRotationPoint(9F, 5F, -6F);

		baseModel[24].addShapeBox(-8F, 0F, 0F, 2, 1, 2, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F); // Box 24
		baseModel[24].setRotationPoint(9F, 5F, 4F);

		baseModel[25].addShapeBox(-8F, 0F, 0F, 2, 1, 1, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F); // Box 25
		baseModel[25].setRotationPoint(9F, 6F, -5.5F);

		baseModel[26].addShapeBox(-8F, 0F, 0F, 2, 1, 2, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F); // Box 26
		baseModel[26].setRotationPoint(9F, 7F, -6F);

		baseModel[27].addShapeBox(-8F, 0F, 0F, 2, 1, 2, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F); // Box 27
		baseModel[27].setRotationPoint(9F, 7F, 4F);

		baseModel[28].addShapeBox(-8F, 0F, 0F, 2, 1, 1, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F); // Box 28
		baseModel[28].setRotationPoint(9F, 6F, 4.5F);


	}
}