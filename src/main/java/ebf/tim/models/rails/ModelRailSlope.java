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

public class ModelRailSlope extends ModelBase
{
	int textureX = 128;
	int textureY = 32;

	public ModelRailSlope()
	{
		baseModel = new ModelRendererTurbo[36];
		baseModel[0] = new ModelRendererTurbo(this, 1, 3, textureX, textureY); // Box 56
		baseModel[1] = new ModelRendererTurbo(this, 1, 7, textureX, textureY); // Box 61
		baseModel[2] = new ModelRendererTurbo(this, 58, 2, textureX, textureY); // Box 91
		baseModel[3] = new ModelRendererTurbo(this, 91, 20, textureX, textureY); // Box 44
		baseModel[4] = new ModelRendererTurbo(this, 53, 29, textureX, textureY); // Box 38
		baseModel[5] = new ModelRendererTurbo(this, 53, 26, textureX, textureY); // Box 42
		baseModel[6] = new ModelRendererTurbo(this, 35, 2, textureX, textureY); // Box 43
		baseModel[7] = new ModelRendererTurbo(this, 1, 7, textureX, textureY); // Box 44
		baseModel[8] = new ModelRendererTurbo(this, 39, 6, textureX, textureY); // Box 39
		baseModel[9] = new ModelRendererTurbo(this, 20, 10, textureX, textureY); // Box 40
		baseModel[10] = new ModelRendererTurbo(this, 1, 14, textureX, textureY); // Box 41
		baseModel[11] = new ModelRendererTurbo(this, 91, 20, textureX, textureY); // Box 42
		baseModel[12] = new ModelRendererTurbo(this, 100, 14, textureX, textureY); // Box 43
		baseModel[13] = new ModelRendererTurbo(this, 100, 14, textureX, textureY); // Box 44
		baseModel[14] = new ModelRendererTurbo(this, 109, 8, textureX, textureY); // Box 45
		baseModel[15] = new ModelRendererTurbo(this, 109, 8, textureX, textureY); // Box 46
		baseModel[16] = new ModelRendererTurbo(this, 118, 2, textureX, textureY); // Box 47
		baseModel[17] = new ModelRendererTurbo(this, 118, 2, textureX, textureY); // Box 48
		baseModel[18] = new ModelRendererTurbo(this, 1, 14, textureX, textureY); // Box 49
		baseModel[19] = new ModelRendererTurbo(this, 20, 10, textureX, textureY); // Box 50
		baseModel[20] = new ModelRendererTurbo(this, 39, 6, textureX, textureY); // Box 51
		baseModel[21] = new ModelRendererTurbo(this, 58, 2, textureX, textureY); // Box 52
		baseModel[22] = new ModelRendererTurbo(this, 20, 10, textureX, textureY); // Box 53
		baseModel[23] = new ModelRendererTurbo(this, 39, 6, textureX, textureY); // Box 54
		baseModel[24] = new ModelRendererTurbo(this, 39, 6, textureX, textureY); // Box 55
		baseModel[25] = new ModelRendererTurbo(this, 58, 2, textureX, textureY); // Box 56
		baseModel[26] = new ModelRendererTurbo(this, 58, 2, textureX, textureY); // Box 57
		baseModel[27] = new ModelRendererTurbo(this, 58, 2, textureX, textureY); // Box 58
		baseModel[28] = new ModelRendererTurbo(this, 77, 10, textureX, textureY); // Box 59
		baseModel[29] = new ModelRendererTurbo(this, 77, 10, textureX, textureY); // Box 60
		baseModel[30] = new ModelRendererTurbo(this, 77, 4, textureX, textureY); // Box 61
		baseModel[31] = new ModelRendererTurbo(this, 77, 4, textureX, textureY); // Box 62
		baseModel[32] = new ModelRendererTurbo(this, 73, 20, textureX, textureY); // Box 63
		baseModel[33] = new ModelRendererTurbo(this, 73, 20, textureX, textureY); // Box 64
		baseModel[34] = new ModelRendererTurbo(this, 109, 2, textureX, textureY); // Box 65
		baseModel[35] = new ModelRendererTurbo(this, 109, 2, textureX, textureY); // Box 66

		baseModel[0].addShapeBox(-8F, 0F, 0F, 16, 1, 2, 0F, 0F, 20F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, 20F, 0F, 0F, -20F, 0F, 0F, 4F, 0F, 0F, 4F, 0F, 0F, -20F, 0F); // Box 56
		baseModel[0].setRotationPoint(0F, -5F, -6F);

		baseModel[1].addShapeBox(-8F, 0F, 0F, 16, 1, 1, 0F, 0F, 12F, 0F, 0F, -12F, 0F, 0F, -12F, 0F, 0F, 12F, 0F, 0F, -12F, 0F, 0F, 12F, 0F, 0F, 12F, 0F, 0F, -12F, 0F); // Box 61
		baseModel[1].setRotationPoint(0F, -12F, -5.5F);

		baseModel[2].addShapeBox(0F, 0F, -7F, 2, 3, 14, 0F, 0F, 0.25F, 0F, 0F, -2.75F, 0F, 0F, -2.75F, 0F, 0F, 0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 91
		baseModel[2].setRotationPoint(-7F, -20.2F, 0F);

		baseModel[3].addBox(0F, 0F, -7F, 2, 10, 2, 0F); // Box 44
		baseModel[3].setRotationPoint(5F, 0.75F, 11F);

		baseModel[4].addShapeBox(-8F, 0F, 0F, 16, 1, 2, 0F, 0F, 20F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, 20F, 0F, 0F, -20F, 0F, 0F, 4F, 0F, 0F, 4F, 0F, 0F, -20F, 0F); // Box 38
		baseModel[4].setRotationPoint(0F, -3F, -6F);

		baseModel[5].addShapeBox(-8F, 0F, 0F, 16, 1, 2, 0F, 0F, 20F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, 20F, 0F, 0F, -20F, 0F, 0F, 4F, 0F, 0F, 4F, 0F, 0F, -20F, 0F); // Box 42
		baseModel[5].setRotationPoint(0F, -3F, 4F);

		baseModel[6].addShapeBox(-8F, 0F, 0F, 16, 1, 2, 0F, 0F, 20F, 0F, 0F, -4F, 0F, 0F, -4F, 0F, 0F, 20F, 0F, 0F, -20F, 0F, 0F, 4F, 0F, 0F, 4F, 0F, 0F, -20F, 0F); // Box 43
		baseModel[6].setRotationPoint(0F, -5F, 4F);

		baseModel[7].addShapeBox(-8F, 0F, 0F, 16, 1, 1, 0F, 0F, 12F, 0F, 0F, -12F, 0F, 0F, -12F, 0F, 0F, 12F, 0F, 0F, -12F, 0F, 0F, 12F, 0F, 0F, 12F, 0F, 0F, -12F, 0F); // Box 44
		baseModel[7].setRotationPoint(0F, -12F, 4.5F);

		baseModel[8].addShapeBox(0F, 0F, -7F, 2, 3, 14, 0F, 0F, 0.25F, 0F, 0F, -2.75F, 0F, 0F, -2.75F, 0F, 0F, 0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 39
		baseModel[8].setRotationPoint(-3F, -14.2F, 0F);

		baseModel[9].addShapeBox(0F, 0F, -7F, 2, 3, 14, 0F, 0F, 0.25F, 0F, 0F, -2.75F, 0F, 0F, -2.75F, 0F, 0F, 0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 40
		baseModel[9].setRotationPoint(1F, -8.2F, 0F);

		baseModel[10].addShapeBox(0F, 0F, -7F, 2, 3, 14, 0F, 0F, 0.25F, 0F, 0F, -2.75F, 0F, 0F, -2.75F, 0F, 0F, 0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 41
		baseModel[10].setRotationPoint(5F, -2.2F, 0F);

		baseModel[11].addBox(0F, 0F, -7F, 2, 10, 2, 0F); // Box 42
		baseModel[11].setRotationPoint(5F, 0.75F, 1F);

		baseModel[12].addBox(0F, 0F, -7F, 2, 16, 2, 0F); // Box 43
		baseModel[12].setRotationPoint(1F, -5.25F, 1F);

		baseModel[13].addBox(0F, 0F, -7F, 2, 16, 2, 0F); // Box 44
		baseModel[13].setRotationPoint(1F, -5.25F, 11F);

		baseModel[14].addBox(0F, 0F, -7F, 2, 22, 2, 0F); // Box 45
		baseModel[14].setRotationPoint(-3F, -11.25F, 1F);

		baseModel[15].addBox(0F, 0F, -7F, 2, 22, 2, 0F); // Box 46
		baseModel[15].setRotationPoint(-3F, -11.25F, 11F);

		baseModel[16].addBox(0F, 0F, -7F, 2, 28, 2, 0F); // Box 47
		baseModel[16].setRotationPoint(-7F, -17.25F, 1F);

		baseModel[17].addBox(0F, 0F, -7F, 2, 28, 2, 0F); // Box 48
		baseModel[17].setRotationPoint(-7F, -17.25F, 11F);

		baseModel[18].addBox(0F, 0F, -7F, 2, 3, 14, 0F); // Box 49
		baseModel[18].setRotationPoint(5F, 3.8F, 0F);

		baseModel[19].addBox(0F, 0F, -7F, 2, 3, 14, 0F); // Box 50
		baseModel[19].setRotationPoint(1F, 3.8F, 0F);

		baseModel[20].addBox(0F, 0F, -7F, 2, 3, 14, 0F); // Box 51
		baseModel[20].setRotationPoint(-3F, 3.8F, 0F);

		baseModel[21].addBox(0F, 0F, -7F, 2, 3, 14, 0F); // Box 52
		baseModel[21].setRotationPoint(-7F, 3.8F, 0F);

		baseModel[22].addBox(0F, 0F, -7F, 2, 3, 14, 0F); // Box 53
		baseModel[22].setRotationPoint(1F, -2.2F, 0F);

		baseModel[23].addBox(0F, 0F, -7F, 2, 3, 14, 0F); // Box 54
		baseModel[23].setRotationPoint(-3F, -2.2F, 0F);

		baseModel[24].addBox(0F, 0F, -7F, 2, 3, 14, 0F); // Box 55
		baseModel[24].setRotationPoint(-3F, -8.2F, 0F);

		baseModel[25].addBox(0F, 0F, -7F, 2, 3, 14, 0F); // Box 56
		baseModel[25].setRotationPoint(-7F, -2.2F, 0F);

		baseModel[26].addBox(0F, 0F, -7F, 2, 3, 14, 0F); // Box 57
		baseModel[26].setRotationPoint(-7F, -8.2F, 0F);

		baseModel[27].addBox(0F, 0F, -7F, 2, 3, 14, 0F); // Box 58
		baseModel[27].setRotationPoint(-7F, -14.2F, 0F);

		baseModel[28].addBox(0F, 0F, -7F, 10, 3, 2, 0F); // Box 59
		baseModel[28].setRotationPoint(-5F, 3.8F, 1F);

		baseModel[29].addBox(0F, 0F, -7F, 10, 3, 2, 0F); // Box 60
		baseModel[29].setRotationPoint(-5F, 3.8F, 11F);

		baseModel[30].addBox(0F, 0F, -7F, 10, 3, 2, 0F); // Box 61
		baseModel[30].setRotationPoint(-5F, -2.2F, 11F);

		baseModel[31].addBox(0F, 0F, -7F, 10, 3, 2, 0F); // Box 62
		baseModel[31].setRotationPoint(-5F, -2.2F, 1F);

		baseModel[32].addBox(0F, 0F, -7F, 6, 3, 2, 0F); // Box 63
		baseModel[32].setRotationPoint(-5F, -8.2F, 11F);

		baseModel[33].addBox(0F, 0F, -7F, 6, 3, 2, 0F); // Box 64
		baseModel[33].setRotationPoint(-5F, -8.2F, 1F);

		baseModel[34].addBox(0F, 0F, -7F, 2, 3, 2, 0F); // Box 65
		baseModel[34].setRotationPoint(-5F, -14.2F, 11F);

		baseModel[35].addBox(0F, 0F, -7F, 2, 3, 2, 0F); // Box 66
		baseModel[35].setRotationPoint(-5F, -14.2F, 1F);


	}
}