//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator: 
// Created on: 14.05.2017 - 15:36:58
// Last changed on: 14.05.2017 - 15:36:58

package ebf.tim.models.rollingstock;

import ebf.tim.models.tmt.ModelBase;
import ebf.tim.models.tmt.ModelRendererTurbo;
import net.minecraft.entity.Entity;

public class ModelGATX1300GallonTanker extends ModelBase
{
	private static final int textureX = 256;
	private static final int textureY = 256;

	public ModelGATX1300GallonTanker()
	{
		gatx1300gallontankerModel = new ModelRendererTurbo[54];
		gatx1300gallontankerModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 0
		gatx1300gallontankerModel[1] = new ModelRendererTurbo(this, 49, 1, textureX, textureY); // Box 1
		gatx1300gallontankerModel[2] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 2
		gatx1300gallontankerModel[3] = new ModelRendererTurbo(this, 33, 1, textureX, textureY); // Box 3
		gatx1300gallontankerModel[4] = new ModelRendererTurbo(this, 97, 1, textureX, textureY); // Box 4
		gatx1300gallontankerModel[5] = new ModelRendererTurbo(this, 1, 25, textureX, textureY); // Box 5
		gatx1300gallontankerModel[6] = new ModelRendererTurbo(this, 1, 57, textureX, textureY); // Box 6
		gatx1300gallontankerModel[7] = new ModelRendererTurbo(this, 1, 89, textureX, textureY); // Box 7
		gatx1300gallontankerModel[8] = new ModelRendererTurbo(this, 1, 129, textureX, textureY); // Box 8
		gatx1300gallontankerModel[9] = new ModelRendererTurbo(this, 1, 169, textureX, textureY); // Box 9
		gatx1300gallontankerModel[10] = new ModelRendererTurbo(this, 145, 1, textureX, textureY); // Box 10
		gatx1300gallontankerModel[11] = new ModelRendererTurbo(this, 49, 1, textureX, textureY); // Box 11
		gatx1300gallontankerModel[12] = new ModelRendererTurbo(this, 57, 1, textureX, textureY); // Box 12
		gatx1300gallontankerModel[13] = new ModelRendererTurbo(this, 169, 1, textureX, textureY); // Box 13
		gatx1300gallontankerModel[14] = new ModelRendererTurbo(this, 201, 9, textureX, textureY); // Box 14
		gatx1300gallontankerModel[15] = new ModelRendererTurbo(this, 81, 1, textureX, textureY); // Box 15
		gatx1300gallontankerModel[16] = new ModelRendererTurbo(this, 185, 33, textureX, textureY); // Box 16
		gatx1300gallontankerModel[17] = new ModelRendererTurbo(this, 97, 1, textureX, textureY); // Box 17
		gatx1300gallontankerModel[18] = new ModelRendererTurbo(this, 105, 1, textureX, textureY); // Box 18
		gatx1300gallontankerModel[19] = new ModelRendererTurbo(this, 217, 33, textureX, textureY); // Box 19
		gatx1300gallontankerModel[20] = new ModelRendererTurbo(this, 121, 1, textureX, textureY); // Box 20
		gatx1300gallontankerModel[21] = new ModelRendererTurbo(this, 1, 201, textureX, textureY); // Box 21
		gatx1300gallontankerModel[22] = new ModelRendererTurbo(this, 129, 201, textureX, textureY); // Box 22
		gatx1300gallontankerModel[23] = new ModelRendererTurbo(this, 201, 1, textureX, textureY); // Box 23
		gatx1300gallontankerModel[24] = new ModelRendererTurbo(this, 225, 1, textureX, textureY); // Box 25
		gatx1300gallontankerModel[25] = new ModelRendererTurbo(this, 161, 57, textureX, textureY); // Box 27
		gatx1300gallontankerModel[26] = new ModelRendererTurbo(this, 137, 1, textureX, textureY); // Box 28
		gatx1300gallontankerModel[27] = new ModelRendererTurbo(this, 169, 1, textureX, textureY); // Box 31
		gatx1300gallontankerModel[28] = new ModelRendererTurbo(this, 153, 1, textureX, textureY); // Box 32
		gatx1300gallontankerModel[29] = new ModelRendererTurbo(this, 241, 1, textureX, textureY); // Box 33
		gatx1300gallontankerModel[30] = new ModelRendererTurbo(this, 249, 1, textureX, textureY); // Box 34
		gatx1300gallontankerModel[31] = new ModelRendererTurbo(this, 1, 9, textureX, textureY); // Box 35
		gatx1300gallontankerModel[32] = new ModelRendererTurbo(this, 217, 1, textureX, textureY); // Box 36
		gatx1300gallontankerModel[33] = new ModelRendererTurbo(this, 9, 9, textureX, textureY); // Box 37
		gatx1300gallontankerModel[34] = new ModelRendererTurbo(this, 33, 9, textureX, textureY); // Box 38
		gatx1300gallontankerModel[35] = new ModelRendererTurbo(this, 41, 9, textureX, textureY); // Box 39
		gatx1300gallontankerModel[36] = new ModelRendererTurbo(this, 49, 9, textureX, textureY); // Box 40
		gatx1300gallontankerModel[37] = new ModelRendererTurbo(this, 57, 9, textureX, textureY); // Box 41
		gatx1300gallontankerModel[38] = new ModelRendererTurbo(this, 81, 9, textureX, textureY); // Box 42
		gatx1300gallontankerModel[39] = new ModelRendererTurbo(this, 97, 9, textureX, textureY); // Box 43
		gatx1300gallontankerModel[40] = new ModelRendererTurbo(this, 225, 9, textureX, textureY); // Box 44
		gatx1300gallontankerModel[41] = new ModelRendererTurbo(this, 169, 25, textureX, textureY); // Box 45
		gatx1300gallontankerModel[42] = new ModelRendererTurbo(this, 217, 57, textureX, textureY); // Box 61
		gatx1300gallontankerModel[43] = new ModelRendererTurbo(this, 177, 81, textureX, textureY); // Box 62
		gatx1300gallontankerModel[44] = new ModelRendererTurbo(this, 209, 89, textureX, textureY); // Box 63
		gatx1300gallontankerModel[45] = new ModelRendererTurbo(this, 193, 105, textureX, textureY); // Box 64
		gatx1300gallontankerModel[46] = new ModelRendererTurbo(this, 169, 113, textureX, textureY); // Box 65
		gatx1300gallontankerModel[47] = new ModelRendererTurbo(this, 217, 121, textureX, textureY); // Box 66
		gatx1300gallontankerModel[48] = new ModelRendererTurbo(this, 185, 129, textureX, textureY); // Box 67
		gatx1300gallontankerModel[49] = new ModelRendererTurbo(this, 233, 137, textureX, textureY); // Box 68
		gatx1300gallontankerModel[50] = new ModelRendererTurbo(this, 209, 145, textureX, textureY); // Box 69
		gatx1300gallontankerModel[51] = new ModelRendererTurbo(this, 169, 153, textureX, textureY); // Box 70
		gatx1300gallontankerModel[52] = new ModelRendererTurbo(this, 217, 161, textureX, textureY); // Box 71
		gatx1300gallontankerModel[53] = new ModelRendererTurbo(this, 1, 209, textureX, textureY); // Box 72

		gatx1300gallontankerModel[0].addBox(0F, 0F, 0F, 2, 2, 20, 0F); // Box 0
		gatx1300gallontankerModel[0].setRotationPoint(-30F, 0F, -10F);

		gatx1300gallontankerModel[1].addBox(0F, 0F, 0F, 2, 2, 20, 0F); // Box 1
		gatx1300gallontankerModel[1].setRotationPoint(-37F, 0F, -10F);

		gatx1300gallontankerModel[2].addBox(0F, 0F, 0F, 5, 2, 2, 0F); // Box 2
		gatx1300gallontankerModel[2].setRotationPoint(-35F, 0F, -10F);

		gatx1300gallontankerModel[3].addBox(0F, 0F, 0F, 5, 2, 2, 0F); // Box 3
		gatx1300gallontankerModel[3].setRotationPoint(-35F, 0F, 8F);

		gatx1300gallontankerModel[4].addBox(0F, 0F, 0F, 2, 3, 18, 0F); // Box 4
		gatx1300gallontankerModel[4].setRotationPoint(-30F, -3F, -9F);

		gatx1300gallontankerModel[5].addBox(0F, 0F, 0F, 70, 8, 20, 0F); // Box 5
		gatx1300gallontankerModel[5].setRotationPoint(-32F, -13F, -10F);

		gatx1300gallontankerModel[6].addBox(0F, 0F, 0F, 70, 12, 18, 0F); // Box 6
		gatx1300gallontankerModel[6].setRotationPoint(-32F, -15F, -9F);

		gatx1300gallontankerModel[7].addBox(0F, 0F, 0F, 70, 16, 16, 0F); // Box 7
		gatx1300gallontankerModel[7].setRotationPoint(-32F, -17F, -8F);

		gatx1300gallontankerModel[8].addBox(0F, 0F, 0F, 70, 18, 14, 0F); // Box 8
		gatx1300gallontankerModel[8].setRotationPoint(-32F, -18F, -7F);

		gatx1300gallontankerModel[9].addBox(0F, 0F, 0F, 70, 20, 10, 0F); // Box 9
		gatx1300gallontankerModel[9].setRotationPoint(-32F, -19F, -5F);

		gatx1300gallontankerModel[10].addBox(0F, 0F, 0F, 1, 1, 17, 0F); // Box 10
		gatx1300gallontankerModel[10].setRotationPoint(-37F, -6F, -8.5F);

		gatx1300gallontankerModel[11].addBox(0F, 0F, 0F, 1, 5, 1, 0F); // Box 11
		gatx1300gallontankerModel[11].setRotationPoint(-37F, -5F, -9F);

		gatx1300gallontankerModel[12].addBox(0F, 0F, 0F, 1, 5, 1, 0F); // Box 12
		gatx1300gallontankerModel[12].setRotationPoint(-37F, -5F, 8F);

		gatx1300gallontankerModel[13].addBox(0F, 0F, 0F, 2, 2, 20, 0F); // Box 13
		gatx1300gallontankerModel[13].setRotationPoint(34F, 0F, -10F);

		gatx1300gallontankerModel[14].addBox(0F, 0F, 0F, 2, 3, 18, 0F); // Box 14
		gatx1300gallontankerModel[14].setRotationPoint(34F, -3F, -9F);

		gatx1300gallontankerModel[15].addBox(0F, 0F, 0F, 5, 2, 2, 0F); // Box 15
		gatx1300gallontankerModel[15].setRotationPoint(36F, 0F, 8F);

		gatx1300gallontankerModel[16].addBox(0F, 0F, 0F, 2, 2, 20, 0F); // Box 16
		gatx1300gallontankerModel[16].setRotationPoint(41F, 0F, -10F);

		gatx1300gallontankerModel[17].addBox(0F, 0F, 0F, 1, 5, 1, 0F); // Box 17
		gatx1300gallontankerModel[17].setRotationPoint(42F, -5F, 8F);

		gatx1300gallontankerModel[18].addBox(0F, 0F, 0F, 1, 5, 1, 0F); // Box 18
		gatx1300gallontankerModel[18].setRotationPoint(42F, -5F, -9F);

		gatx1300gallontankerModel[19].addBox(0F, 0F, 0F, 1, 1, 17, 0F); // Box 19
		gatx1300gallontankerModel[19].setRotationPoint(42F, -6F, -8.5F);

		gatx1300gallontankerModel[20].addBox(0F, 0F, 0F, 5, 2, 2, 0F); // Box 20
		gatx1300gallontankerModel[20].setRotationPoint(36F, 0F, -10F);

		gatx1300gallontankerModel[21].addBox(0F, 0F, 0F, 62, 1, 1, 0F); // Box 21
		gatx1300gallontankerModel[21].setRotationPoint(-28F, 0F, 9F);

		gatx1300gallontankerModel[22].addBox(0F, 0F, 0F, 62, 1, 1, 0F); // Box 22
		gatx1300gallontankerModel[22].setRotationPoint(-28F, 0F, -10F);

		gatx1300gallontankerModel[23].addBox(0F, 0F, 0F, 5, 17, 0, 0F); // Box 23
		gatx1300gallontankerModel[23].setRotationPoint(0F, -16F, 10.5F);

		gatx1300gallontankerModel[24].addBox(0F, 0F, 0F, 5, 17, 0, 0F); // Box 25
		gatx1300gallontankerModel[24].setRotationPoint(0F, -16F, -10.5F);

		gatx1300gallontankerModel[25].addBox(0F, 0F, 0F, 12, 1, 15, 0F); // Box 27
		gatx1300gallontankerModel[25].setRotationPoint(-3F, -21F, -7.5F);

		gatx1300gallontankerModel[26].addBox(0F, 0F, 0F, 5, 6, 0, 0F); // Box 28
		gatx1300gallontankerModel[26].setRotationPoint(0F, -21F, 7.4F);
		gatx1300gallontankerModel[26].rotateAngleX = 0.54105207F;

		gatx1300gallontankerModel[27].addBox(0F, 0F, 0F, 5, 6, 0, 0F); // Box 31
		gatx1300gallontankerModel[27].setRotationPoint(0F, -21F, -7.4F);
		gatx1300gallontankerModel[27].rotateAngleX = 5.74213324F;

		gatx1300gallontankerModel[28].addBox(0F, 0F, 0F, 1, 8, 1, 0F); // Box 32
		gatx1300gallontankerModel[28].setRotationPoint(-3F, -25F, -7.5F);

		gatx1300gallontankerModel[29].addBox(0F, 0F, 0F, 1, 8, 1, 0F); // Box 33
		gatx1300gallontankerModel[29].setRotationPoint(8F, -25F, -7.5F);

		gatx1300gallontankerModel[30].addBox(0F, 0F, 0F, 1, 8, 1, 0F); // Box 34
		gatx1300gallontankerModel[30].setRotationPoint(8F, -25F, 6.5F);

		gatx1300gallontankerModel[31].addBox(0F, 0F, 0F, 1, 8, 1, 0F); // Box 35
		gatx1300gallontankerModel[31].setRotationPoint(-3F, -25F, 6.5F);

		gatx1300gallontankerModel[32].addBox(0F, 0F, 0F, 2, 1, 1, 0F); // Box 36
		gatx1300gallontankerModel[32].setRotationPoint(-2F, -25F, 6.5F);

		gatx1300gallontankerModel[33].addBox(0F, 0F, 0F, 2, 1, 1, 0F); // Box 37
		gatx1300gallontankerModel[33].setRotationPoint(-2F, -25F, -7.5F);

		gatx1300gallontankerModel[34].addBox(0F, 0F, 0F, 1, 4, 1, 0F); // Box 38
		gatx1300gallontankerModel[34].setRotationPoint(0F, -25F, -7.5F);

		gatx1300gallontankerModel[35].addBox(0F, 0F, 0F, 1, 4, 1, 0F); // Box 39
		gatx1300gallontankerModel[35].setRotationPoint(0F, -25F, 6.5F);

		gatx1300gallontankerModel[36].addBox(0F, 0F, 0F, 1, 4, 1, 0F); // Box 40
		gatx1300gallontankerModel[36].setRotationPoint(4F, -25F, 6.5F);

		gatx1300gallontankerModel[37].addBox(0F, 0F, 0F, 1, 4, 1, 0F); // Box 41
		gatx1300gallontankerModel[37].setRotationPoint(4F, -25F, -7.5F);

		gatx1300gallontankerModel[38].addBox(0F, 0F, 0F, 3, 1, 1, 0F); // Box 42
		gatx1300gallontankerModel[38].setRotationPoint(5F, -25F, -7.5F);

		gatx1300gallontankerModel[39].addBox(0F, 0F, 0F, 3, 1, 1, 0F); // Box 43
		gatx1300gallontankerModel[39].setRotationPoint(5F, -25F, 6.5F);

		gatx1300gallontankerModel[40].addBox(0F, 0F, 0F, 1, 1, 13, 0F); // Box 44
		gatx1300gallontankerModel[40].setRotationPoint(8F, -25F, -6.5F);

		gatx1300gallontankerModel[41].addBox(0F, 0F, 0F, 1, 1, 13, 0F); // Box 45
		gatx1300gallontankerModel[41].setRotationPoint(-3F, -25F, -6.5F);

		gatx1300gallontankerModel[42].addBox(0F, 0F, 0F, 1, 13, 14, 0F); // Box 61
		gatx1300gallontankerModel[42].setRotationPoint(38F, -15.5F, -7F);

		gatx1300gallontankerModel[43].addBox(0F, 0F, 0F, 1, 16, 12, 0F); // Box 62
		gatx1300gallontankerModel[43].setRotationPoint(38F, -17F, -6F);

		gatx1300gallontankerModel[44].addBox(0F, 0F, 0F, 1, 10, 16, 0F); // Box 63
		gatx1300gallontankerModel[44].setRotationPoint(38F, -14F, -8F);

		gatx1300gallontankerModel[45].addBox(0F, 0F, 0F, 1, 6, 12, 0F); // Box 64
		gatx1300gallontankerModel[45].setRotationPoint(39F, -12F, -6F);

		gatx1300gallontankerModel[46].addBox(0F, 0F, 0F, 1, 9, 10, 0F); // Box 65
		gatx1300gallontankerModel[46].setRotationPoint(39F, -13.5F, -5F);

		gatx1300gallontankerModel[47].addBox(0F, 0F, 0F, 1, 12, 8, 0F); // Box 66
		gatx1300gallontankerModel[47].setRotationPoint(39F, -15F, -4F);

		gatx1300gallontankerModel[48].addBox(0F, 0F, 0F, 1, 6, 12, 0F); // Box 67
		gatx1300gallontankerModel[48].setRotationPoint(-34F, -12F, -6F);

		gatx1300gallontankerModel[49].addBox(0F, 0F, 0F, 1, 9, 10, 0F); // Box 68
		gatx1300gallontankerModel[49].setRotationPoint(-34F, -13.5F, -5F);

		gatx1300gallontankerModel[50].addBox(0F, 0F, 0F, 1, 12, 8, 0F); // Box 69
		gatx1300gallontankerModel[50].setRotationPoint(-34F, -15F, -4F);

		gatx1300gallontankerModel[51].addBox(0F, 0F, 0F, 1, 10, 16, 0F); // Box 70
		gatx1300gallontankerModel[51].setRotationPoint(-33F, -14F, -8F);

		gatx1300gallontankerModel[52].addBox(0F, 0F, 0F, 1, 13, 14, 0F); // Box 71
		gatx1300gallontankerModel[52].setRotationPoint(-33F, -15.5F, -7F);

		gatx1300gallontankerModel[53].addBox(0F, 0F, 0F, 1, 16, 12, 0F); // Box 72
		gatx1300gallontankerModel[53].setRotationPoint(-33F, -17F, -6F);

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		for(int i = 0; i < 54; i++)
		{
			gatx1300gallontankerModel[i].render(f5);
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {}

	private ModelRendererTurbo gatx1300gallontankerModel[];
}