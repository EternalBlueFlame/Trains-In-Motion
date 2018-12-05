//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model:
// Created on:12.02.2017 - 22:37:23
// Last changed on: 12.02.2017 - 22:37:23
/**
 * @author Eternal Blue Flame
 */

package ebf.tim.models.rollingstock;

import ebf.tim.models.GroupedModelRender;
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.ModelRendererTurbo;
import net.minecraft.entity.Entity;

public class VATLogCar extends ModelBase
{
	private static final int textureX = 256;
	private static final int textureY = 64;

	private static final String cargo1 = GroupedModelRender.tagRenderBlockCargo + "1";
	private static final String cargo2 = GroupedModelRender.tagRenderBlockCargo + "2";
	private static final String cargo3 = GroupedModelRender.tagRenderBlockCargo + "3";

	public VATLogCar()
	{
		vatlogcarModel = new ModelRendererTurbo[34];
		vatlogcarModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 0
		vatlogcarModel[1] = new ModelRendererTurbo(this, 1, 25, textureX, textureY); // Box 1
		vatlogcarModel[2] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 5
		vatlogcarModel[3] = new ModelRendererTurbo(this, 9, 1, textureX, textureY); // Box 6
		vatlogcarModel[4] = new ModelRendererTurbo(this, 145, 1, textureX, textureY); // Box 7
		vatlogcarModel[5] = new ModelRendererTurbo(this, 153, 1, textureX, textureY); // Box 8
		vatlogcarModel[6] = new ModelRendererTurbo(this, 161, 1, textureX, textureY); // Box 9
		vatlogcarModel[7] = new ModelRendererTurbo(this, 169, 1, textureX, textureY); // Box 10
		vatlogcarModel[8] = new ModelRendererTurbo(this, 177, 1, textureX, textureY); // Box 11
		vatlogcarModel[9] = new ModelRendererTurbo(this, 185, 1, textureX, textureY); // Box 12
		vatlogcarModel[10] = new ModelRendererTurbo(this, 169, 1, textureX, textureY); // Box 13
		vatlogcarModel[11] = new ModelRendererTurbo(this, 201, 9, textureX, textureY); // Box 14
		vatlogcarModel[12] = new ModelRendererTurbo(this, 201, 1, textureX, textureY); // Box 15
		vatlogcarModel[13] = new ModelRendererTurbo(this, 217, 1, textureX, textureY); // Box 16
		vatlogcarModel[14] = new ModelRendererTurbo(this, 225, 1, textureX, textureY); // Box 17
		vatlogcarModel[15] = new ModelRendererTurbo(this, 233, 1, textureX, textureY); // Box 18
		vatlogcarModel[16] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo1); // logblock 1
		vatlogcarModel[17] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo2); // logblock 2
		vatlogcarModel[18] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo2); // logblock 2
		vatlogcarModel[19] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo1); // logblock 1
		vatlogcarModel[20] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo2); // logblock 2
		vatlogcarModel[21] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo1); // logblock 1
		vatlogcarModel[22] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo1); // logblock 1
		vatlogcarModel[23] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo2); // logblock 2
		vatlogcarModel[24] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo2); // logblock 2
		vatlogcarModel[25] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo1); // logblock 1
		vatlogcarModel[26] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo1); // logblock 1
		vatlogcarModel[27] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo2); // logblock 2
		vatlogcarModel[28] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo3); // logblock 3
		vatlogcarModel[29] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo3); // logblock 3
		vatlogcarModel[30] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo3); // logblock 3
		vatlogcarModel[31] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo3); // logblock 3
		vatlogcarModel[32] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo3); // logblock 3
		vatlogcarModel[33] = new ModelRendererTurbo(this, 161, 33, textureX, textureY, cargo3); // logblock 3

		vatlogcarModel[0].addBox(0F, 0F, 0F, 60, 2, 20, 0F); // Box 0
		vatlogcarModel[0].setRotationPoint(-30F, -6F, -10F);

		vatlogcarModel[1].addBox(0F, 0F, 0F, 60, 5, 16, 0F); // Box 1
		vatlogcarModel[1].setRotationPoint(-30F, -4F, -8F);

		vatlogcarModel[2].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 5
		vatlogcarModel[2].setRotationPoint(-24F, -19F, 10F);

		vatlogcarModel[3].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 6
		vatlogcarModel[3].setRotationPoint(24F, -19F, 10F);

		vatlogcarModel[4].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 7
		vatlogcarModel[4].setRotationPoint(10F, -19F, 10F);

		vatlogcarModel[5].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 8
		vatlogcarModel[5].setRotationPoint(-10F, -19F, 10F);

		vatlogcarModel[6].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 9
		vatlogcarModel[6].setRotationPoint(24F, -19F, -11F);

		vatlogcarModel[7].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 10
		vatlogcarModel[7].setRotationPoint(10F, -19F, -11F);

		vatlogcarModel[8].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 11
		vatlogcarModel[8].setRotationPoint(-10F, -19F, -11F);

		vatlogcarModel[9].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 12
		vatlogcarModel[9].setRotationPoint(-24F, -19F, -11F);

		vatlogcarModel[10].addBox(0F, 0F, 0F, 1, 4, 22, 0F); // Box 13
		vatlogcarModel[10].setRotationPoint(30F, -4F, -11F);

		vatlogcarModel[11].addBox(0F, 0F, 0F, 1, 4, 22, 0F); // Box 14
		vatlogcarModel[11].setRotationPoint(-31F, -4F, -11F);

		vatlogcarModel[12].addBox(0F, 0F, 0F, 4, 1, 1, 0F); // Box 15
		vatlogcarModel[12].setRotationPoint(30F, 0F, -1F);

		vatlogcarModel[13].addBox(0F, 0F, 0F, 1, 2, 2, 0F); // Box 16
		vatlogcarModel[13].setRotationPoint(34F, -0.5F, -1.5F);

		vatlogcarModel[14].addBox(0F, 0F, 0F, 1, 2, 2, 0F); // Box 17
		vatlogcarModel[14].setRotationPoint(-35F, -0.5F, -1.5F);

		vatlogcarModel[15].addBox(0F, 0F, 0F, 4, 1, 1, 0F); // Box 18
		vatlogcarModel[15].setRotationPoint(-34F, 0F, -1F);

		vatlogcarModel[16].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 1
		vatlogcarModel[16].setRotationPoint(-26F, -10F, -6F);
		vatlogcarModel[16].rotateAngleX = 1.57079633F;

		vatlogcarModel[17].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 2
		vatlogcarModel[17].setRotationPoint(-26F, -10F, 4F);
		vatlogcarModel[17].rotateAngleX = 1.57079633F;

		vatlogcarModel[18].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 2
		vatlogcarModel[18].setRotationPoint(-16F, -10F, 4F);
		vatlogcarModel[18].rotateAngleX = 1.57079633F;

		vatlogcarModel[19].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 1
		vatlogcarModel[19].setRotationPoint(-16F, -10F, -6F);
		vatlogcarModel[19].rotateAngleX = 1.57079633F;

		vatlogcarModel[20].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 2
		vatlogcarModel[20].setRotationPoint(4F, -10F, 4F);
		vatlogcarModel[20].rotateAngleX = 1.57079633F;

		vatlogcarModel[21].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 1
		vatlogcarModel[21].setRotationPoint(4F, -10F, -6F);
		vatlogcarModel[21].rotateAngleX = 1.57079633F;

		vatlogcarModel[22].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 1
		vatlogcarModel[22].setRotationPoint(-6F, -10F, -6F);
		vatlogcarModel[22].rotateAngleX = 1.57079633F;

		vatlogcarModel[23].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 2
		vatlogcarModel[23].setRotationPoint(-6F, -10F, 4F);
		vatlogcarModel[23].rotateAngleX = 1.57079633F;

		vatlogcarModel[24].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 2
		vatlogcarModel[24].setRotationPoint(24F, -10F, 4F);
		vatlogcarModel[24].rotateAngleX = 1.57079633F;

		vatlogcarModel[25].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 1
		vatlogcarModel[25].setRotationPoint(24F, -10F, -6F);
		vatlogcarModel[25].rotateAngleX = 1.57079633F;

		vatlogcarModel[26].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 1
		vatlogcarModel[26].setRotationPoint(14F, -10F, -6F);
		vatlogcarModel[26].rotateAngleX = 1.57079633F;

		vatlogcarModel[27].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 2
		vatlogcarModel[27].setRotationPoint(14F, -10F, 4F);
		vatlogcarModel[27].rotateAngleX = 1.57079633F;

		vatlogcarModel[28].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 3
		vatlogcarModel[28].setRotationPoint(-26F, -20F, -1F);
		vatlogcarModel[28].rotateAngleX = 1.57079633F;

		vatlogcarModel[29].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 3
		vatlogcarModel[29].setRotationPoint(-16F, -20F, -1F);
		vatlogcarModel[29].rotateAngleX = 1.57079633F;

		vatlogcarModel[30].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 3
		vatlogcarModel[30].setRotationPoint(-6F, -20F, -1F);
		vatlogcarModel[30].rotateAngleX = 1.57079633F;

		vatlogcarModel[31].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 3
		vatlogcarModel[31].setRotationPoint(4F, -20F, -1F);
		vatlogcarModel[31].rotateAngleX = 1.57079633F;

		vatlogcarModel[32].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 3
		vatlogcarModel[32].setRotationPoint(14F, -20F, -1F);
		vatlogcarModel[32].rotateAngleX = 1.57079633F;

		vatlogcarModel[33].addBox(-4F, -4F, -4F, 10, 10, 10, 0F); // logblock 3
		vatlogcarModel[33].setRotationPoint(24F, -20F, -1F);
		vatlogcarModel[33].rotateAngleX = 1.57079633F;

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		for(int i = 0; i < 34; i++)
		{
			vatlogcarModel[i].render(f5);
		}
	}


	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
	}

	private ModelRendererTurbo vatlogcarModel[];
}