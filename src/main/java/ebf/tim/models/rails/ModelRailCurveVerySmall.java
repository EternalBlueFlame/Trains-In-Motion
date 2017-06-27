/**
 * This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
 * Copyright (C) 2017 Minecraft-SMP.de
 * This file is for Flan's Flying Mod Version 4.0.x+
 *
 * @author ApocTheWanderer
 * @editor Eternal Blue Flame
*/
package ebf.tim.models.rails;

import ebf.tim.models.tmt.ModelBase;
import ebf.tim.models.tmt.ModelRendererTurbo;

public class ModelRailCurveVerySmall extends ModelBase
{
	int textureX = 64;
	int textureY = 32;

	public ModelRailCurveVerySmall()
	{
		baseModel = new ModelRendererTurbo[30];
		baseModel[0] = new ModelRendererTurbo(this, 49, 5, textureX, textureY); // Box 17
		baseModel[1] = new ModelRendererTurbo(this, 46, 28, textureX, textureY); // Box 19
		baseModel[2] = new ModelRendererTurbo(this, 28, 28, textureX, textureY); // Box 26
		baseModel[3] = new ModelRendererTurbo(this, 36, 11, textureX, textureY); // Box 68
		baseModel[4] = new ModelRendererTurbo(this, 2, 2, textureX, textureY); // Box 96
		baseModel[5] = new ModelRendererTurbo(this, 19, 28, textureX, textureY); // Box 29
		baseModel[6] = new ModelRendererTurbo(this, 40, 22, textureX, textureY); // Box 33
		baseModel[7] = new ModelRendererTurbo(this, 21, 3, textureX, textureY); // Box 34
		baseModel[8] = new ModelRendererTurbo(this, 49, 13, textureX, textureY); // Box 35
		baseModel[9] = new ModelRendererTurbo(this, 21, 11, textureX, textureY); // Box 36
		baseModel[10] = new ModelRendererTurbo(this, 36, 3, textureX, textureY); // Box 40
		baseModel[11] = new ModelRendererTurbo(this, 37, 28, textureX, textureY); // Box 41
		baseModel[12] = new ModelRendererTurbo(this, 1, 28, textureX, textureY); // Box 43
		baseModel[13] = new ModelRendererTurbo(this, 1, 22, textureX, textureY); // Box 44
		baseModel[14] = new ModelRendererTurbo(this, 44, 19, textureX, textureY); // Box 45
		baseModel[15] = new ModelRendererTurbo(this, 31, 19, textureX, textureY); // Box 46
		baseModel[16] = new ModelRendererTurbo(this, 27, 22, textureX, textureY); // Box 47
		baseModel[17] = new ModelRendererTurbo(this, 14, 22, textureX, textureY); // Box 48
		baseModel[18] = new ModelRendererTurbo(this, 36, 7, textureX, textureY); // Box 49
		baseModel[19] = new ModelRendererTurbo(this, 49, 9, textureX, textureY); // Box 51
		baseModel[20] = new ModelRendererTurbo(this, 15, 25, textureX, textureY); // Box 52
		baseModel[21] = new ModelRendererTurbo(this, 1, 25, textureX, textureY); // Box 54
		baseModel[22] = new ModelRendererTurbo(this, 2, 2, textureX, textureY); // Box 55
		baseModel[23] = new ModelRendererTurbo(this, 2, 2, textureX, textureY); // Box 56
		baseModel[24] = new ModelRendererTurbo(this, 21, 7, textureX, textureY); // Box 57
		baseModel[25] = new ModelRendererTurbo(this, 55, 24, textureX, textureY); // Box 58
		baseModel[26] = new ModelRendererTurbo(this, 55, 28, textureX, textureY); // Box 59
		baseModel[27] = new ModelRendererTurbo(this, 10, 28, textureX, textureY); // Box 60
		baseModel[28] = new ModelRendererTurbo(this, 22, 25, textureX, textureY); // Box 61
		baseModel[29] = new ModelRendererTurbo(this, 8, 25, textureX, textureY); // Box 63

		baseModel[0].addBox(-2F, 0F, 0F, 4, 1, 2, 0F); // Box 17
		baseModel[0].setRotationPoint(-6F, 5F, 4F);

		baseModel[1].addBox(-1F, 0F, 0F, 1, 1, 2, 0F); // Box 19
		baseModel[1].setRotationPoint(-7F, 5F, -6F);

		baseModel[2].addBox(-1F, 0F, 0F, 1, 1, 2, 0F); // Box 26
		baseModel[2].setRotationPoint(-6F, 5F, -8F);
		baseModel[2].rotateAngleY = -1.57079633F;

		baseModel[3].addBox(-2F, 0F, 0F, 4, 1, 2, 0F); // Box 68
		baseModel[3].setRotationPoint(4F, 5F, -6F);
		baseModel[3].rotateAngleY = -1.57079633F;

		baseModel[4].addBox(-1F, 0F, 0F, 2, 2, 14, 0F); // Box 96
		baseModel[4].setRotationPoint(-7F, 8F, -7F);
		baseModel[4].rotateAngleY = -0.78539816F;

		baseModel[5].addShapeBox(-1F, 0F, 0F, 2, 1, 2, 0F, -1.25F, 0F, 0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, -1.25F, 0F, 0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 29
		baseModel[5].setRotationPoint(-6F, 5F, -6F);
		baseModel[5].rotateAngleY = -1.57079633F;

		baseModel[6].addShapeBox(-2F, 0F, 0F, 5, 1, 2, 0F, 0F, 0F, 0F, -1F, 0F, 2F, -0.5F, 0F, -2.5F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 2F, -0.5F, 0F, -2.5F, 0F, 0F, 0F); // Box 33
		baseModel[6].setRotationPoint(-2F, 5F, 4F);

		baseModel[7].addShapeBox(-2F, 0F, 0F, 5, 1, 2, 0F, -1F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, -2.5F, -1F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, -2.5F); // Box 34
		baseModel[7].setRotationPoint(4F, 5F, -1F);
		baseModel[7].rotateAngleY = -1.57079633F;

		baseModel[8].addShapeBox(-2F, 0F, 0F, 5, 1, 2, 0F, -3F, 0F, 2F, 0F, 0F, 0F, -0.5F, 0F, -0.5F, -1.5F, 0F, -3.5F, -3F, 0F, 2F, 0F, 0F, 0F, -0.5F, 0F, -0.5F, -1.5F, 0F, -3.5F); // Box 35
		baseModel[8].setRotationPoint(2F, 5F, 3F);
		baseModel[8].rotateAngleY = -1.57079633F;

		baseModel[9].addBox(-2F, 0F, 0F, 4, 1, 2, 0F); // Box 36
		baseModel[9].setRotationPoint(4F, 7F, -6F);
		baseModel[9].rotateAngleY = -1.57079633F;

		baseModel[10].addBox(-2F, 0F, 0F, 4, 1, 2, 0F); // Box 40
		baseModel[10].setRotationPoint(-6F, 7F, 4F);

		baseModel[11].addBox(-1F, 0F, 0F, 1, 1, 2, 0F); // Box 41
		baseModel[11].setRotationPoint(-7F, 7F, -6F);

		baseModel[12].addBox(-1F, 0F, 0F, 1, 1, 2, 0F); // Box 43
		baseModel[12].setRotationPoint(-6F, 7F, -8F);
		baseModel[12].rotateAngleY = -1.57079633F;

		baseModel[13].addBox(-2F, 0F, 0F, 4, 1, 1, 0F); // Box 44
		baseModel[13].setRotationPoint(4.5F, 6F, -6F);
		baseModel[13].rotateAngleY = -1.57079633F;

		baseModel[14].addShapeBox(-2F, 0F, 0F, 5, 1, 1, 0F, -1F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, -2.5F, -1F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, -2.5F); // Box 45
		baseModel[14].setRotationPoint(4.5F, 6F, -1F);
		baseModel[14].rotateAngleY = -1.57079633F;

		baseModel[15].addShapeBox(-2F, 0F, 0F, 5, 1, 1, 0F, -2.5F, 0F, 2.5F, 0F, 0F, 0F, -0.5F, 0F, -0.5F, -2F, 0F, -3F, -2.5F, 0F, 2.5F, 0F, 0F, 0F, -0.5F, 0F, -0.5F, -2F, 0F, -3F); // Box 46
		baseModel[15].setRotationPoint(2.5F, 6F, 3F);
		baseModel[15].rotateAngleY = -1.57079633F;

		baseModel[16].addShapeBox(-2F, 0F, 0F, 5, 1, 1, 0F, 0F, 0F, 0F, -1F, 0F, 2F, -0.5F, 0F, -2.5F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 2F, -0.5F, 0F, -2.5F, 0F, 0F, 0F); // Box 47
		baseModel[16].setRotationPoint(-2F, 6F, 4.5F);

		baseModel[17].addBox(-2F, 0F, 0F, 4, 1, 1, 0F); // Box 48
		baseModel[17].setRotationPoint(-6F, 6F, 4.5F);

		baseModel[18].addShapeBox(-2F, 0F, 0F, 5, 1, 2, 0F, 0F, 0F, 0F, -1F, 0F, 2F, -0.5F, 0F, -2.5F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 2F, -0.5F, 0F, -2.5F, 0F, 0F, 0F); // Box 49
		baseModel[18].setRotationPoint(-2F, 7F, 4F);

		baseModel[19].addShapeBox(-2F, 0F, 0F, 5, 1, 2, 0F, -1F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, -2.5F, -1F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, -2.5F); // Box 51
		baseModel[19].setRotationPoint(4F, 7F, -1F);
		baseModel[19].rotateAngleY = -1.57079633F;

		baseModel[20].addBox(-1F, 0F, 0F, 1, 1, 1, 0F); // Box 52
		baseModel[20].setRotationPoint(-7F, 6F, -5.5F);

		baseModel[21].addBox(-1F, 0F, 0F, 1, 1, 1, 0F); // Box 54
		baseModel[21].setRotationPoint(-5.5F, 6F, -8F);
		baseModel[21].rotateAngleY = -1.57079633F;

		baseModel[22].addBox(-1F, 0F, 0F, 2, 2, 14, 0F); // Box 55
		baseModel[22].setRotationPoint(-7.5F, 8F, -6.75F);
		baseModel[22].rotateAngleY = -0.26179939F;

		baseModel[23].addBox(-1F, 0F, 0F, 2, 2, 14, 0F); // Box 56
		baseModel[23].setRotationPoint(-7F, 8F, -7.5F);
		baseModel[23].rotateAngleY = -1.30899694F;

		baseModel[24].addShapeBox(-2F, 0F, 0F, 5, 1, 2, 0F, -3F, 0F, 2F, 0F, 0F, 0F, -0.5F, 0F, -0.5F, -1.5F, 0F, -3.5F, -3F, 0F, 2F, 0F, 0F, 0F, -0.5F, 0F, -0.5F, -1.5F, 0F, -3.5F); // Box 57
		baseModel[24].setRotationPoint(2F, 7F, 3F);
		baseModel[24].rotateAngleY = -1.57079633F;

		baseModel[25].addShapeBox(-1F, 0F, 0F, 2, 1, 2, 0F, -1.5F, 0F, 0.5F, -0.25F, 0F, -0.25F, -1.5F, 0F, -0.5F, 0.5F, 0F, -2.5F, -1.5F, 0F, 0.5F, -0.25F, 0F, -0.25F, -1.5F, 0F, -0.5F, 0.5F, 0F, -2.5F); // Box 58
		baseModel[25].setRotationPoint(-6.5F, 5F, -5.5F);
		baseModel[25].rotateAngleY = -1.57079633F;

		baseModel[26].addShapeBox(-1F, 0F, 0F, 2, 1, 2, 0F, -1.25F, 0F, 0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, -1.25F, 0F, 0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 59
		baseModel[26].setRotationPoint(-6F, 7F, -6F);
		baseModel[26].rotateAngleY = -1.57079633F;

		baseModel[27].addShapeBox(-1F, 0F, 0F, 2, 1, 2, 0F, -1.5F, 0F, 0.5F, -0.25F, 0F, -0.25F, -1.5F, 0F, -0.5F, 0.5F, 0F, -2.5F, -1.5F, 0F, 0.5F, -0.25F, 0F, -0.25F, -1.5F, 0F, -0.5F, 0.5F, 0F, -2.5F); // Box 60
		baseModel[27].setRotationPoint(-6.5F, 7F, -5.5F);
		baseModel[27].rotateAngleY = -1.57079633F;

		baseModel[28].addShapeBox(-1F, 0F, 0F, 2, 1, 1, 0F, -1F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, -0.375F, 0F, -0.75F, -1F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, -0.375F, 0F, -0.75F); // Box 61
		baseModel[28].setRotationPoint(-5.5F, 6F, -6F);
		baseModel[28].rotateAngleY = -1.57079633F;

		baseModel[29].addShapeBox(-1F, 0F, 0F, 2, 1, 1, 0F, -1.5F, 0F, 0.5F, 0F, 0F, -0.5F, -0.625F, 0F, 0.25F, -0.5F, 0F, -1.5F, -1.5F, 0F, 0.5F, 0F, 0F, -0.5F, -0.625F, 0F, 0.25F, -0.5F, 0F, -1.5F); // Box 63
		baseModel[29].setRotationPoint(-6.5F, 6F, -5F);
		baseModel[29].rotateAngleY = -1.57079633F;


	}
}