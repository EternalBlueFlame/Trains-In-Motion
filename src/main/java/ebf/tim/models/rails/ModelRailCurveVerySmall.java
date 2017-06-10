/**
 * This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
 * Copyright (C) 2017 Minecraft-SMP.de
 * This file is for Flan's Flying Mod Version 4.0.x+
 *
 * @author ApocTheWanderer
 * @editor Eternal Blue Flame
*/
package ebf.tim.models.rails;

import ebf.tim.models.tmt.ModelRendererTurbo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class ModelRailCurveVerySmall extends ModelBase
{
	int textureX = 64;
	int textureY = 32;

	public ModelRailCurveVerySmall()
	{
		railcurveverysmallModel = new ModelRendererTurbo[30];
		railcurveverysmallModel[0] = new ModelRendererTurbo(this, 49, 5, textureX, textureY); // Box 17
		railcurveverysmallModel[1] = new ModelRendererTurbo(this, 46, 28, textureX, textureY); // Box 19
		railcurveverysmallModel[2] = new ModelRendererTurbo(this, 28, 28, textureX, textureY); // Box 26
		railcurveverysmallModel[3] = new ModelRendererTurbo(this, 36, 11, textureX, textureY); // Box 68
		railcurveverysmallModel[4] = new ModelRendererTurbo(this, 2, 2, textureX, textureY); // Box 96
		railcurveverysmallModel[5] = new ModelRendererTurbo(this, 19, 28, textureX, textureY); // Box 29
		railcurveverysmallModel[6] = new ModelRendererTurbo(this, 40, 22, textureX, textureY); // Box 33
		railcurveverysmallModel[7] = new ModelRendererTurbo(this, 21, 3, textureX, textureY); // Box 34
		railcurveverysmallModel[8] = new ModelRendererTurbo(this, 49, 13, textureX, textureY); // Box 35
		railcurveverysmallModel[9] = new ModelRendererTurbo(this, 21, 11, textureX, textureY); // Box 36
		railcurveverysmallModel[10] = new ModelRendererTurbo(this, 36, 3, textureX, textureY); // Box 40
		railcurveverysmallModel[11] = new ModelRendererTurbo(this, 37, 28, textureX, textureY); // Box 41
		railcurveverysmallModel[12] = new ModelRendererTurbo(this, 1, 28, textureX, textureY); // Box 43
		railcurveverysmallModel[13] = new ModelRendererTurbo(this, 1, 22, textureX, textureY); // Box 44
		railcurveverysmallModel[14] = new ModelRendererTurbo(this, 44, 19, textureX, textureY); // Box 45
		railcurveverysmallModel[15] = new ModelRendererTurbo(this, 31, 19, textureX, textureY); // Box 46
		railcurveverysmallModel[16] = new ModelRendererTurbo(this, 27, 22, textureX, textureY); // Box 47
		railcurveverysmallModel[17] = new ModelRendererTurbo(this, 14, 22, textureX, textureY); // Box 48
		railcurveverysmallModel[18] = new ModelRendererTurbo(this, 36, 7, textureX, textureY); // Box 49
		railcurveverysmallModel[19] = new ModelRendererTurbo(this, 49, 9, textureX, textureY); // Box 51
		railcurveverysmallModel[20] = new ModelRendererTurbo(this, 15, 25, textureX, textureY); // Box 52
		railcurveverysmallModel[21] = new ModelRendererTurbo(this, 1, 25, textureX, textureY); // Box 54
		railcurveverysmallModel[22] = new ModelRendererTurbo(this, 2, 2, textureX, textureY); // Box 55
		railcurveverysmallModel[23] = new ModelRendererTurbo(this, 2, 2, textureX, textureY); // Box 56
		railcurveverysmallModel[24] = new ModelRendererTurbo(this, 21, 7, textureX, textureY); // Box 57
		railcurveverysmallModel[25] = new ModelRendererTurbo(this, 55, 24, textureX, textureY); // Box 58
		railcurveverysmallModel[26] = new ModelRendererTurbo(this, 55, 28, textureX, textureY); // Box 59
		railcurveverysmallModel[27] = new ModelRendererTurbo(this, 10, 28, textureX, textureY); // Box 60
		railcurveverysmallModel[28] = new ModelRendererTurbo(this, 22, 25, textureX, textureY); // Box 61
		railcurveverysmallModel[29] = new ModelRendererTurbo(this, 8, 25, textureX, textureY); // Box 63

		railcurveverysmallModel[0].addBox(-2F, 0F, 0F, 5, 1, 2, 0F); // Box 17
		railcurveverysmallModel[0].setRotationPoint(-7F, 5F, 4F);

		railcurveverysmallModel[1].addBox(-1F, 0F, 0F, 2, 1, 2, 0F); // Box 19
		railcurveverysmallModel[1].setRotationPoint(-8F, 5F, -6F);

		railcurveverysmallModel[2].addBox(-1F, 0F, 0F, 2, 1, 2, 0F); // Box 26
		railcurveverysmallModel[2].setRotationPoint(-6F, 5F, -8F);
		railcurveverysmallModel[2].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[3].addBox(-2F, 0F, 0F, 5, 1, 2, 0F); // Box 68
		railcurveverysmallModel[3].setRotationPoint(4F, 5F, -6F);
		railcurveverysmallModel[3].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[4].addBox(-1F, 0F, 0F, 2, 2, 14, 0F); // Box 96
		railcurveverysmallModel[4].setRotationPoint(-7F, 8F, -7F);
		railcurveverysmallModel[4].rotateAngleY = -0.78539816F;

		railcurveverysmallModel[5].addShapeBox(-1F, 0F, 0F, 2, 1, 2, 0F, -1.25F, 0F, 0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, -1.25F, 0F, 0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 29
		railcurveverysmallModel[5].setRotationPoint(-6F, 5F, -6F);
		railcurveverysmallModel[5].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[6].addShapeBox(-2F, 0F, 0F, 5, 1, 2, 0F, 0F, 0F, 0F, -1F, 0F, 2F, -0.5F, 0F, -2.5F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 2F, -0.5F, 0F, -2.5F, 0F, 0F, 0F); // Box 33
		railcurveverysmallModel[6].setRotationPoint(-2F, 5F, 4F);

		railcurveverysmallModel[7].addShapeBox(-2F, 0F, 0F, 5, 1, 2, 0F, -1F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, -2.5F, -1F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, -2.5F); // Box 34
		railcurveverysmallModel[7].setRotationPoint(4F, 5F, -1F);
		railcurveverysmallModel[7].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[8].addShapeBox(-2F, 0F, 0F, 5, 1, 2, 0F, -3F, 0F, 2F, 0F, 0F, 0F, -0.5F, 0F, -0.5F, -1.5F, 0F, -3.5F, -3F, 0F, 2F, 0F, 0F, 0F, -0.5F, 0F, -0.5F, -1.5F, 0F, -3.5F); // Box 35
		railcurveverysmallModel[8].setRotationPoint(2F, 5F, 3F);
		railcurveverysmallModel[8].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[9].addBox(-2F, 0F, 0F, 5, 1, 2, 0F); // Box 36
		railcurveverysmallModel[9].setRotationPoint(4F, 7F, -6F);
		railcurveverysmallModel[9].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[10].addBox(-2F, 0F, 0F, 5, 1, 2, 0F); // Box 40
		railcurveverysmallModel[10].setRotationPoint(-7F, 7F, 4F);

		railcurveverysmallModel[11].addBox(-1F, 0F, 0F, 2, 1, 2, 0F); // Box 41
		railcurveverysmallModel[11].setRotationPoint(-8F, 7F, -6F);

		railcurveverysmallModel[12].addBox(-1F, 0F, 0F, 2, 1, 2, 0F); // Box 43
		railcurveverysmallModel[12].setRotationPoint(-6F, 7F, -8F);
		railcurveverysmallModel[12].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[13].addBox(-2F, 0F, 0F, 5, 1, 1, 0F); // Box 44
		railcurveverysmallModel[13].setRotationPoint(4.5F, 6F, -6F);
		railcurveverysmallModel[13].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[14].addShapeBox(-2F, 0F, 0F, 5, 1, 1, 0F, -1F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, -2.5F, -1F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, -2.5F); // Box 45
		railcurveverysmallModel[14].setRotationPoint(4.5F, 6F, -1F);
		railcurveverysmallModel[14].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[15].addShapeBox(-2F, 0F, 0F, 5, 1, 1, 0F, -2.5F, 0F, 2.5F, 0F, 0F, 0F, -0.5F, 0F, -0.5F, -2F, 0F, -3F, -2.5F, 0F, 2.5F, 0F, 0F, 0F, -0.5F, 0F, -0.5F, -2F, 0F, -3F); // Box 46
		railcurveverysmallModel[15].setRotationPoint(2.5F, 6F, 3F);
		railcurveverysmallModel[15].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[16].addShapeBox(-2F, 0F, 0F, 5, 1, 1, 0F, 0F, 0F, 0F, -1F, 0F, 2F, -0.5F, 0F, -2.5F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 2F, -0.5F, 0F, -2.5F, 0F, 0F, 0F); // Box 47
		railcurveverysmallModel[16].setRotationPoint(-2F, 6F, 4.5F);

		railcurveverysmallModel[17].addBox(-2F, 0F, 0F, 5, 1, 1, 0F); // Box 48
		railcurveverysmallModel[17].setRotationPoint(-7F, 6F, 4.5F);

		railcurveverysmallModel[18].addShapeBox(-2F, 0F, 0F, 5, 1, 2, 0F, 0F, 0F, 0F, -1F, 0F, 2F, -0.5F, 0F, -2.5F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 2F, -0.5F, 0F, -2.5F, 0F, 0F, 0F); // Box 49
		railcurveverysmallModel[18].setRotationPoint(-2F, 7F, 4F);

		railcurveverysmallModel[19].addShapeBox(-2F, 0F, 0F, 5, 1, 2, 0F, -1F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, -2.5F, -1F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, -2.5F); // Box 51
		railcurveverysmallModel[19].setRotationPoint(4F, 7F, -1F);
		railcurveverysmallModel[19].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[20].addBox(-1F, 0F, 0F, 2, 1, 1, 0F); // Box 52
		railcurveverysmallModel[20].setRotationPoint(-8F, 6F, -5.5F);

		railcurveverysmallModel[21].addBox(-1F, 0F, 0F, 2, 1, 1, 0F); // Box 54
		railcurveverysmallModel[21].setRotationPoint(-5.5F, 6F, -8F);
		railcurveverysmallModel[21].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[22].addBox(-1F, 0F, 0F, 2, 2, 14, 0F); // Box 55
		railcurveverysmallModel[22].setRotationPoint(-7.5F, 8F, -6.75F);
		railcurveverysmallModel[22].rotateAngleY = -0.26179939F;

		railcurveverysmallModel[23].addBox(-1F, 0F, 0F, 2, 2, 14, 0F); // Box 56
		railcurveverysmallModel[23].setRotationPoint(-7F, 8F, -7.5F);
		railcurveverysmallModel[23].rotateAngleY = -1.30899694F;

		railcurveverysmallModel[24].addShapeBox(-2F, 0F, 0F, 5, 1, 2, 0F, -3F, 0F, 2F, 0F, 0F, 0F, -0.5F, 0F, -0.5F, -1.5F, 0F, -3.5F, -3F, 0F, 2F, 0F, 0F, 0F, -0.5F, 0F, -0.5F, -1.5F, 0F, -3.5F); // Box 57
		railcurveverysmallModel[24].setRotationPoint(2F, 7F, 3F);
		railcurveverysmallModel[24].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[25].addShapeBox(-1F, 0F, 0F, 2, 1, 2, 0F, -1.5F, 0F, 0.5F, -0.25F, 0F, -0.25F, -1.5F, 0F, -0.5F, 0.5F, 0F, -2.5F, -1.5F, 0F, 0.5F, -0.25F, 0F, -0.25F, -1.5F, 0F, -0.5F, 0.5F, 0F, -2.5F); // Box 58
		railcurveverysmallModel[25].setRotationPoint(-6.5F, 5F, -5.5F);
		railcurveverysmallModel[25].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[26].addShapeBox(-1F, 0F, 0F, 2, 1, 2, 0F, -1.25F, 0F, 0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, -1.25F, 0F, 0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F); // Box 59
		railcurveverysmallModel[26].setRotationPoint(-6F, 7F, -6F);
		railcurveverysmallModel[26].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[27].addShapeBox(-1F, 0F, 0F, 2, 1, 2, 0F, -1.5F, 0F, 0.5F, -0.25F, 0F, -0.25F, -1.5F, 0F, -0.5F, 0.5F, 0F, -2.5F, -1.5F, 0F, 0.5F, -0.25F, 0F, -0.25F, -1.5F, 0F, -0.5F, 0.5F, 0F, -2.5F); // Box 60
		railcurveverysmallModel[27].setRotationPoint(-6.5F, 7F, -5.5F);
		railcurveverysmallModel[27].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[28].addShapeBox(-1F, 0F, 0F, 2, 1, 1, 0F, -1F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, -0.375F, 0F, -0.75F, -1F, 0F, 0.5F, 0F, 0F, 0F, 0F, 0F, 0F, -0.375F, 0F, -0.75F); // Box 61
		railcurveverysmallModel[28].setRotationPoint(-5.5F, 6F, -6F);
		railcurveverysmallModel[28].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[29].addShapeBox(-1F, 0F, 0F, 2, 1, 1, 0F, -1.5F, 0F, 0.5F, 0F, 0F, -0.5F, -0.625F, 0F, 0.25F, -0.5F, 0F, -1.5F, -1.5F, 0F, 0.5F, 0F, 0F, -0.5F, -0.625F, 0F, 0.25F, -0.5F, 0F, -1.5F); // Box 63
		railcurveverysmallModel[29].setRotationPoint(-6.5F, 6F, -5F);
		railcurveverysmallModel[29].rotateAngleY = -1.57079633F;


	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		for(int i = 0; i < 30; i++)
		{
			railcurveverysmallModel[i].render();
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
	}

	public ModelRendererTurbo railcurveverysmallModel[];
}