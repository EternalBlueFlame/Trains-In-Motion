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
import ebf.tim.models.tmt.ModelRendererTurbo;
import ebf.tim.models.tmt.ModelBase;
import net.minecraft.entity.Entity;

public class VATLogCar extends ModelBase
{
	private static final int textureX = 256;
	private static final int textureY = 64;

	private static final String cargo1 = GroupedModelRender.tagRenderBlockCargo + "1";
	private static final String cargo2 = GroupedModelRender.tagRenderBlockCargo + "2";
	private static final String cargo3 = GroupedModelRender.tagRenderBlockCargo + "3";
	private static final String cargo4 = GroupedModelRender.tagRenderBlockCargo + "4";
	private static final String cargo5 = GroupedModelRender.tagRenderBlockCargo + "5";

	public VATLogCar()
	{
		vatlogcarModel = new ModelRendererTurbo[46];
		vatlogcarModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 0
		vatlogcarModel[1] = new ModelRendererTurbo(this, 129, 2, textureX, textureY); // Box 1
		vatlogcarModel[2] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 5
		vatlogcarModel[3] = new ModelRendererTurbo(this, 248, 1, textureX, textureY); // Box 6
		vatlogcarModel[4] = new ModelRendererTurbo(this, 113, 1, textureX, textureY); // Box 7
		vatlogcarModel[5] = new ModelRendererTurbo(this, 121, 1, textureX, textureY); // Box 8
		vatlogcarModel[6] = new ModelRendererTurbo(this, 129, 1, textureX, textureY); // Box 9
		vatlogcarModel[7] = new ModelRendererTurbo(this, 137, 1, textureX, textureY); // Box 10
		vatlogcarModel[8] = new ModelRendererTurbo(this, 239, 1, textureX, textureY); // Box 11
		vatlogcarModel[9] = new ModelRendererTurbo(this, 9, 1, textureX, textureY); // Box 12
		vatlogcarModel[10] = new ModelRendererTurbo(this, 1, 25, textureX, textureY); // Box 13
		vatlogcarModel[11] = new ModelRendererTurbo(this, 49, 25, textureX, textureY); // Box 14
		vatlogcarModel[12] = new ModelRendererTurbo(this, 53, 25, textureX, textureY); // Box 15
		vatlogcarModel[13] = new ModelRendererTurbo(this, 25, 25, textureX, textureY); // Box 16
		vatlogcarModel[14] = new ModelRendererTurbo(this, 33, 25, textureX, textureY); // Box 17
		vatlogcarModel[15] = new ModelRendererTurbo(this, 41, 25, textureX, textureY); // Box 18
		vatlogcarModel[16] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo1); // logblock 1
		vatlogcarModel[17] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo2); // logblock 2
		vatlogcarModel[18] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo2); // logblock 2
		vatlogcarModel[19] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo1); // logblock 1
		vatlogcarModel[20] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo2); // logblock 2
		vatlogcarModel[21] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo1); // logblock 1
		vatlogcarModel[22] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo1); // logblock 1
		vatlogcarModel[23] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo2); // logblock 2
		vatlogcarModel[24] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo2); // logblock 2
		vatlogcarModel[25] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo1); // logblock 1
		vatlogcarModel[26] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo1); // logblock 1
		vatlogcarModel[27] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo2); // logblock 2
		vatlogcarModel[28] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo3); // logblock 3
		vatlogcarModel[29] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo4); // logblock 4
		vatlogcarModel[30] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo3); // logblock 3
		vatlogcarModel[31] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo4); // logblock 4
		vatlogcarModel[32] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo3); // logblock 3
		vatlogcarModel[33] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo4); // logblock 4
		vatlogcarModel[34] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo4); // logblock 4
		vatlogcarModel[35] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo3); // logblock 3
		vatlogcarModel[36] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo3); // logblock 3
		vatlogcarModel[37] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo4); // logblock 4
		vatlogcarModel[38] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo4); // logblock 4
		vatlogcarModel[39] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo3); // logblock 3
		vatlogcarModel[40] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo5); // logblock 5
		vatlogcarModel[41] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo5); // logblock 5
		vatlogcarModel[42] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo5); // logblock 5
		vatlogcarModel[43] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo5); // logblock 5
		vatlogcarModel[44] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo5); // logblock 5
		vatlogcarModel[45] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, cargo5); // logblock 5

		vatlogcarModel[0].addBox(0F, 0F, 0F, 44, 2, 18, 0F); // Box 0
		vatlogcarModel[0].setRotationPoint(-22F, -2F, -9F);

		vatlogcarModel[1].addBox(0F, 0F, 0F, 44, 5, 16, 0F); // Box 1
		vatlogcarModel[1].setRotationPoint(-22F, 0F, -8F);

		vatlogcarModel[2].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 5
		vatlogcarModel[2].setRotationPoint(-16F, -15F, 9F);

		vatlogcarModel[3].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 6
		vatlogcarModel[3].setRotationPoint(16F, -15F, 9F);

		vatlogcarModel[4].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 7
		vatlogcarModel[4].setRotationPoint(6F, -15F, 9F);

		vatlogcarModel[5].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 8
		vatlogcarModel[5].setRotationPoint(-6F, -15F, 9F);

		vatlogcarModel[6].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 9
		vatlogcarModel[6].setRotationPoint(16F, -15F, -10F);

		vatlogcarModel[7].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 10
		vatlogcarModel[7].setRotationPoint(6F, -15F, -10F);

		vatlogcarModel[8].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 11
		vatlogcarModel[8].setRotationPoint(-6F, -15F, -10F);

		vatlogcarModel[9].addBox(0F, 0F, 0F, 1, 15, 1, 0F); // Box 12
		vatlogcarModel[9].setRotationPoint(-16F, -15F, -10F);

		vatlogcarModel[10].addBox(0F, 0F, 0F, 1, 4, 20, 0F); // Box 13
		vatlogcarModel[10].setRotationPoint(22F, 0F, -10F);

		vatlogcarModel[11].addBox(0F, 0F, 0F, 1, 4, 20, 0F); // Box 14
		vatlogcarModel[11].setRotationPoint(-23F, 0F, -10F);

		vatlogcarModel[12].addBox(0F, 0F, 0F, 4, 1, 1, 0F); // Box 15
		vatlogcarModel[12].setRotationPoint(22F, 4F, -1F);

		vatlogcarModel[13].addBox(0F, 0F, 0F, 1, 2, 2, 0F); // Box 16
		vatlogcarModel[13].setRotationPoint(26F, 3.5F, -1.5F);

		vatlogcarModel[14].addBox(0F, 0F, 0F, 1, 2, 2, 0F); // Box 17
		vatlogcarModel[14].setRotationPoint(-27F, 3.5F, -1.5F);

		vatlogcarModel[15].addBox(0F, 0F, 0F, 4, 1, 1, 0F); // Box 18
		vatlogcarModel[15].setRotationPoint(-26F, 4F, -1F);

		vatlogcarModel[16].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 1
		vatlogcarModel[16].setRotationPoint(-20F, -6F, -4F);
		vatlogcarModel[16].rotateAngleX = 1.57079633F;

		vatlogcarModel[17].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 2
		vatlogcarModel[17].setRotationPoint(-20F, -6F, 4F);
		vatlogcarModel[17].rotateAngleX = 1.57079633F;

		vatlogcarModel[18].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 2
		vatlogcarModel[18].setRotationPoint(-12F, -6F, 4F);
		vatlogcarModel[18].rotateAngleX = 1.57079633F;

		vatlogcarModel[19].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 1
		vatlogcarModel[19].setRotationPoint(-12F, -6F, -4F);
		vatlogcarModel[19].rotateAngleX = 1.57079633F;

		vatlogcarModel[20].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 2
		vatlogcarModel[20].setRotationPoint(4F, -6F, 4F);
		vatlogcarModel[20].rotateAngleX = 1.57079633F;

		vatlogcarModel[21].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 1
		vatlogcarModel[21].setRotationPoint(4F, -6F, -4F);
		vatlogcarModel[21].rotateAngleX = 1.57079633F;

		vatlogcarModel[22].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 1
		vatlogcarModel[22].setRotationPoint(-4F, -6F, -4F);
		vatlogcarModel[22].rotateAngleX = 1.57079633F;

		vatlogcarModel[23].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 2
		vatlogcarModel[23].setRotationPoint(-4F, -6F, 4F);
		vatlogcarModel[23].rotateAngleX = 1.57079633F;

		vatlogcarModel[24].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 2
		vatlogcarModel[24].setRotationPoint(20F, -6F, 4F);
		vatlogcarModel[24].rotateAngleX = 1.57079633F;

		vatlogcarModel[25].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 1
		vatlogcarModel[25].setRotationPoint(20F, -6F, -4F);
		vatlogcarModel[25].rotateAngleX = 1.57079633F;

		vatlogcarModel[26].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 1
		vatlogcarModel[26].setRotationPoint(12F, -6F, -4F);
		vatlogcarModel[26].rotateAngleX = 1.57079633F;

		vatlogcarModel[27].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 2
		vatlogcarModel[27].setRotationPoint(12F, -6F, 4F);
		vatlogcarModel[27].rotateAngleX = 1.57079633F;

		vatlogcarModel[28].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 3
		vatlogcarModel[28].setRotationPoint(20F, -14F, -4F);
		vatlogcarModel[28].rotateAngleX = 1.57079633F;

		vatlogcarModel[29].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 4
		vatlogcarModel[29].setRotationPoint(20F, -14F, 4F);
		vatlogcarModel[29].rotateAngleX = 1.57079633F;

		vatlogcarModel[30].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 3
		vatlogcarModel[30].setRotationPoint(12F, -14F, -4F);
		vatlogcarModel[30].rotateAngleX = 1.57079633F;

		vatlogcarModel[31].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 4
		vatlogcarModel[31].setRotationPoint(12F, -14F, 4F);
		vatlogcarModel[31].rotateAngleX = 1.57079633F;

		vatlogcarModel[32].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 3
		vatlogcarModel[32].setRotationPoint(4F, -14F, -4F);
		vatlogcarModel[32].rotateAngleX = 1.57079633F;

		vatlogcarModel[33].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 4
		vatlogcarModel[33].setRotationPoint(4F, -14F, 4F);
		vatlogcarModel[33].rotateAngleX = 1.57079633F;

		vatlogcarModel[34].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 4
		vatlogcarModel[34].setRotationPoint(-4F, -14F, 4F);
		vatlogcarModel[34].rotateAngleX = 1.57079633F;

		vatlogcarModel[35].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 3
		vatlogcarModel[35].setRotationPoint(-4F, -14F, -4F);
		vatlogcarModel[35].rotateAngleX = 1.57079633F;

		vatlogcarModel[36].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 3
		vatlogcarModel[36].setRotationPoint(-12F, -14F, -4F);
		vatlogcarModel[36].rotateAngleX = 1.57079633F;

		vatlogcarModel[37].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 4
		vatlogcarModel[37].setRotationPoint(-12F, -14F, 4F);
		vatlogcarModel[37].rotateAngleX = 1.57079633F;

		vatlogcarModel[38].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 4
		vatlogcarModel[38].setRotationPoint(-20F, -14F, 4F);
		vatlogcarModel[38].rotateAngleX = 1.57079633F;

		vatlogcarModel[39].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 3
		vatlogcarModel[39].setRotationPoint(-20F, -14F, -4F);
		vatlogcarModel[39].rotateAngleX = 1.57079633F;

		vatlogcarModel[40].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 5
		vatlogcarModel[40].setRotationPoint(20F, -22F, 0F);
		vatlogcarModel[40].rotateAngleX = 1.57079633F;

		vatlogcarModel[41].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 5
		vatlogcarModel[41].setRotationPoint(12F, -22F, 0F);
		vatlogcarModel[41].rotateAngleX = 1.57079633F;

		vatlogcarModel[42].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 5
		vatlogcarModel[42].setRotationPoint(4F, -22F, 0F);
		vatlogcarModel[42].rotateAngleX = 1.57079633F;

		vatlogcarModel[43].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 5
		vatlogcarModel[43].setRotationPoint(-4F, -22F, 0F);
		vatlogcarModel[43].rotateAngleX = 1.57079633F;

		vatlogcarModel[44].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 5
		vatlogcarModel[44].setRotationPoint(-12F, -22F, 0F);
		vatlogcarModel[44].rotateAngleX = 1.57079633F;

		vatlogcarModel[45].addBox(-4F, -4F, -4F, 8, 8, 8, 0F); // logblock 5
		vatlogcarModel[45].setRotationPoint(-20F, -22F, 0F);
		vatlogcarModel[45].rotateAngleX = 1.57079633F;


	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		for(int i = 0; i < 46; i++)
		{
			vatlogcarModel[i].render(f5);
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
	}

	private ModelRendererTurbo vatlogcarModel[];
}