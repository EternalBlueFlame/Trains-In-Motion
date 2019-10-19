//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model:
// Created on:10.01.2017 - 11:25:17
// Last changed on: 10.01.2017 - 11:25:17
/**
 * @author Eternal Blue Flame
 */

package ebf.timsquared.models.rollingstock;

import ebf.tim.models.StaticModelAnimator;
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.ModelRendererTurbo;
import net.minecraft.entity.Entity;

public class PullmansPalace extends ModelBase
{
	private static final int textureXY = 512;

	public PullmansPalace()
	{
		pullmanspalaceModel = new ModelRendererTurbo[67];
		pullmanspalaceModel[0] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 0
		pullmanspalaceModel[1] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 1
		pullmanspalaceModel[2] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 2
		pullmanspalaceModel[3] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 3
		pullmanspalaceModel[4] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 4
		pullmanspalaceModel[5] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 5
		pullmanspalaceModel[6] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 6
		pullmanspalaceModel[7] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 8
		pullmanspalaceModel[8] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 9
		pullmanspalaceModel[9] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 10
		pullmanspalaceModel[10] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 11
		pullmanspalaceModel[11] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 12
		pullmanspalaceModel[12] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 13
		pullmanspalaceModel[13] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 14
		pullmanspalaceModel[14] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 15
		pullmanspalaceModel[15] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 19
		pullmanspalaceModel[16] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel 8
		pullmanspalaceModel[17] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 78
		pullmanspalaceModel[18] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 79
		pullmanspalaceModel[19] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 80
		pullmanspalaceModel[20] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 85
		pullmanspalaceModel[21] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 87
		pullmanspalaceModel[22] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 88
		pullmanspalaceModel[23] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 89
		pullmanspalaceModel[24] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 90
		pullmanspalaceModel[25] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 91
		pullmanspalaceModel[26] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 92
		pullmanspalaceModel[27] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 93
		pullmanspalaceModel[28] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 94
		pullmanspalaceModel[29] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 95
		pullmanspalaceModel[30] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 96
		pullmanspalaceModel[31] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 97
		pullmanspalaceModel[32] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 79
		pullmanspalaceModel[33] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 80
		pullmanspalaceModel[34] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 82
		pullmanspalaceModel[35] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 83
		pullmanspalaceModel[36] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel 7
		pullmanspalaceModel[37] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 69
		pullmanspalaceModel[38] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 70
		pullmanspalaceModel[39] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 71
		pullmanspalaceModel[40] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 72
		pullmanspalaceModel[41] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 73
		pullmanspalaceModel[42] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 74
		pullmanspalaceModel[43] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 75
		pullmanspalaceModel[44] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 76
		pullmanspalaceModel[45] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 77
		pullmanspalaceModel[46] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel 6
		pullmanspalaceModel[47] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel 5
		pullmanspalaceModel[48] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 80
		pullmanspalaceModel[49] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 81
		pullmanspalaceModel[50] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 82
		pullmanspalaceModel[51] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 83
		pullmanspalaceModel[52] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 84
		pullmanspalaceModel[53] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 85
		pullmanspalaceModel[54] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel 3
		pullmanspalaceModel[55] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel 4
		pullmanspalaceModel[56] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 88
		pullmanspalaceModel[57] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 89
		pullmanspalaceModel[58] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 90
		pullmanspalaceModel[59] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 91
		pullmanspalaceModel[60] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 92
		pullmanspalaceModel[61] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 93
		pullmanspalaceModel[62] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel 1
		pullmanspalaceModel[63] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel 2
		pullmanspalaceModel[64] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 96
		pullmanspalaceModel[65] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 97
		pullmanspalaceModel[66] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY); // Box 98

		pullmanspalaceModel[0].addBox(0F, -0.5F, 0F, 100, 1, 20, 0F); // Box 0
		pullmanspalaceModel[0].setRotationPoint(-50F, -1.5F, -10F);

		pullmanspalaceModel[1].addBox(0F, 0F, 0F, 80, 23, 1, 0F); // Box 1
		pullmanspalaceModel[1].setRotationPoint(-40F, -25F, 9F);

		pullmanspalaceModel[2].addBox(0F, 0F, 0F, 80, 23, 1, 0F); // Box 2
		pullmanspalaceModel[2].setRotationPoint(-40F, -25F, -10F);

		pullmanspalaceModel[3].addBox(0F, 0F, 0F, 1, 12, 7, 0F); // Box 3
		pullmanspalaceModel[3].setRotationPoint(49F, -14F, -10F);

		pullmanspalaceModel[4].addBox(0F, 0F, 0F, 1, 12, 7, 0F); // Box 4
		pullmanspalaceModel[4].setRotationPoint(49F, -14F, 3F);

		pullmanspalaceModel[5].addBox(0F, 0F, 0F, 1, 12, 7, 0F); // Box 5
		pullmanspalaceModel[5].setRotationPoint(-50F, -14F, 3F);

		pullmanspalaceModel[6].addBox(0F, 0F, 0F, 1, 12, 7, 0F); // Box 6
		pullmanspalaceModel[6].setRotationPoint(-50F, -14F, -10F);

		pullmanspalaceModel[7].addBox(0F, 0F, 0F, 1, 23, 18, 0F); // Box 8
		pullmanspalaceModel[7].setRotationPoint(-40F, -25F, -9F);

		pullmanspalaceModel[8].addBox(0F, 0F, 0F, 1, 23, 18, 0F); // Box 9
		pullmanspalaceModel[8].setRotationPoint(39F, -25F, -9F);

		pullmanspalaceModel[9].addBox(0F, 0F, 0F, 100, 1, 20, 0F); // Box 10
		pullmanspalaceModel[9].setRotationPoint(-50F, -26F, -10F);

		pullmanspalaceModel[10].addBox(0F, 0F, 0F, 100, 2, 18, 0F); // Box 11
		pullmanspalaceModel[10].setRotationPoint(-50F, -28F, -9F);

		pullmanspalaceModel[11].addBox(0F, 0F, 0F, 100, 2, 14, 0F); // Box 12
		pullmanspalaceModel[11].setRotationPoint(-50F, -30F, -7F);

		pullmanspalaceModel[12].addBox(0F, 0F, 0F, 100, 1, 12, 0F); // Box 13
		pullmanspalaceModel[12].setRotationPoint(-50F, -31F, -6F);

		pullmanspalaceModel[13].addBox(0F, 0F, 0F, 100, 1, 8, 0F); // Box 14
		pullmanspalaceModel[13].setRotationPoint(-50F, -32F, -4F);

		pullmanspalaceModel[14].addBox(0F, 0F, 0F, 100, 1, 4, 0F); // Box 15
		pullmanspalaceModel[14].setRotationPoint(-50F, -33F, -2F);

		pullmanspalaceModel[15].addBox(0F, 0F, 0F, 1, 1, 6, 0F); // Box 19
		pullmanspalaceModel[15].setRotationPoint(-50F, -10F, -3F);

		pullmanspalaceModel[16].addBox(-5F, -5F, 0F, 10, 10, 0, 0F); // Wheel 8
		pullmanspalaceModel[16].setRotationPoint(30F, 4F, -6F);

		pullmanspalaceModel[17].addBox(0F, 0F, 0F, 15, 2, 1, 0F); // Box 78
		pullmanspalaceModel[17].setRotationPoint(22.5F, -1.5F, -7F);

		pullmanspalaceModel[18].addBox(0F, 0F, 0F, 9, 4, 1, 0F); // Box 79
		pullmanspalaceModel[18].setRotationPoint(25.5F, -1.5F, -7F);

		pullmanspalaceModel[19].addBox(0F, 0F, 0F, 3, 7, 1, 0F); // Box 80
		pullmanspalaceModel[19].setRotationPoint(28.5F, -1.5F, -7F);

		pullmanspalaceModel[20].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 85
		pullmanspalaceModel[20].setRotationPoint(-49F, -1F, 10F);

		pullmanspalaceModel[21].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 87
		pullmanspalaceModel[21].setRotationPoint(-49F, 1F, 11F);

		pullmanspalaceModel[22].addBox(0F, 0F, 0F, 9, 2, 1, 0F); // Box 88
		pullmanspalaceModel[22].setRotationPoint(-49F, 3F, 12F);

		pullmanspalaceModel[23].addBox(0F, 0F, 0F, 9, 2, 1, 0F); // Box 89
		pullmanspalaceModel[23].setRotationPoint(40F, 3F, 12F);

		pullmanspalaceModel[24].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 90
		pullmanspalaceModel[24].setRotationPoint(40F, 1F, 11F);

		pullmanspalaceModel[25].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 91
		pullmanspalaceModel[25].setRotationPoint(40F, -1F, 10F);

		pullmanspalaceModel[26].addBox(0F, 0F, 0F, 9, 2, 1, 0F); // Box 92
		pullmanspalaceModel[26].setRotationPoint(40F, 3F, -13F);

		pullmanspalaceModel[27].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 93
		pullmanspalaceModel[27].setRotationPoint(40F, 1F, -12F);

		pullmanspalaceModel[28].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 94
		pullmanspalaceModel[28].setRotationPoint(40F, -1F, -11F);

		pullmanspalaceModel[29].addBox(0F, 0F, 0F, 9, 2, 1, 0F); // Box 95
		pullmanspalaceModel[29].setRotationPoint(-49F, 3F, -13F);

		pullmanspalaceModel[30].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 96
		pullmanspalaceModel[30].setRotationPoint(-49F, 1F, -12F);

		pullmanspalaceModel[31].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 97
		pullmanspalaceModel[31].setRotationPoint(-49F, -1F, -11F);

		pullmanspalaceModel[32].addBox(0F, 0F, 0F, 78, 24, 1, 0F); // Box 79
		pullmanspalaceModel[32].setRotationPoint(-39F, -25.5F, 4F);

		pullmanspalaceModel[33].addBox(0F, 0F, 0F, 1, 24, 13, 0F); // Box 80
		pullmanspalaceModel[33].setRotationPoint(19F, -25.5F, -9F);

		pullmanspalaceModel[34].addBox(0F, 0F, 0F, 1, 24, 13, 0F); // Box 82
		pullmanspalaceModel[34].setRotationPoint(-1F, -25.5F, -9F);

		pullmanspalaceModel[35].addBox(0F, 0F, 0F, 1, 24, 13, 0F); // Box 83
		pullmanspalaceModel[35].setRotationPoint(-19F, -25.5F, -9F);

		pullmanspalaceModel[36].addBox(-5F, -5F, 0F, 10, 10, 0, 0F); // Wheel 7
		pullmanspalaceModel[36].setRotationPoint(30F, 4F, -5.5F);

		pullmanspalaceModel[37].addBox(0F, 0F, 0F, 15, 2, 1, 0F); // Box 69
		pullmanspalaceModel[37].setRotationPoint(22.5F, -1.5F, -5.5F);

		pullmanspalaceModel[38].addBox(0F, 0F, 0F, 9, 4, 1, 0F); // Box 70
		pullmanspalaceModel[38].setRotationPoint(25.5F, -1.5F, -5.5F);

		pullmanspalaceModel[39].addBox(0F, 0F, 0F, 3, 7, 1, 0F); // Box 71
		pullmanspalaceModel[39].setRotationPoint(28.5F, -1.5F, -5.5F);

		pullmanspalaceModel[40].addBox(0F, 0F, 0F, 15, 2, 1, 0F); // Box 72
		pullmanspalaceModel[40].setRotationPoint(22.5F, -1.5F, 3.5F);

		pullmanspalaceModel[41].addBox(0F, 0F, 0F, 9, 4, 1, 0F); // Box 73
		pullmanspalaceModel[41].setRotationPoint(25.5F, -1.5F, 3.5F);

		pullmanspalaceModel[42].addBox(0F, 0F, 0F, 3, 7, 1, 0F); // Box 74
		pullmanspalaceModel[42].setRotationPoint(28.5F, -1.5F, 3.5F);

		pullmanspalaceModel[43].addBox(0F, 0F, 0F, 15, 2, 1, 0F); // Box 75
		pullmanspalaceModel[43].setRotationPoint(22.5F, -1.5F, 2F);

		pullmanspalaceModel[44].addBox(0F, 0F, 0F, 9, 4, 1, 0F); // Box 76
		pullmanspalaceModel[44].setRotationPoint(25.5F, -1.5F, 2F);

		pullmanspalaceModel[45].addBox(0F, 0F, 0F, 3, 7, 1, 0F); // Box 77
		pullmanspalaceModel[45].setRotationPoint(28.5F, -1.5F, 2F);

		pullmanspalaceModel[46].addBox(-5F, -5F, 0F, 10, 10, 0, 0F); // Wheel 6
		pullmanspalaceModel[46].setRotationPoint(30F, 4F, 3F);

		pullmanspalaceModel[47].addBox(-5F, -5F, 0F, 10, 10, 0, 0F); // Wheel 5
		pullmanspalaceModel[47].setRotationPoint(30F, 4F, 3.5F);

		pullmanspalaceModel[48].addBox(0F, 0F, 0F, 15, 2, 1, 0F); // Box 80
		pullmanspalaceModel[48].setRotationPoint(-37.5F, -1.5F, 3.5F);

		pullmanspalaceModel[49].addBox(0F, 0F, 0F, 9, 4, 1, 0F); // Box 81
		pullmanspalaceModel[49].setRotationPoint(-34.5F, -1.5F, 3.5F);

		pullmanspalaceModel[50].addBox(0F, 0F, 0F, 3, 7, 1, 0F); // Box 82
		pullmanspalaceModel[50].setRotationPoint(-31.5F, -1.5F, 3.5F);

		pullmanspalaceModel[51].addBox(0F, 0F, 0F, 15, 2, 1, 0F); // Box 83
		pullmanspalaceModel[51].setRotationPoint(-37.5F, -1.5F, 2F);

		pullmanspalaceModel[52].addBox(0F, 0F, 0F, 9, 4, 1, 0F); // Box 84
		pullmanspalaceModel[52].setRotationPoint(-34.5F, -1.5F, 2F);

		pullmanspalaceModel[53].addBox(0F, 0F, 0F, 3, 7, 1, 0F); // Box 85
		pullmanspalaceModel[53].setRotationPoint(-31.5F, -1.5F, 2F);

		pullmanspalaceModel[54].addBox(-5F, -5F, 0F, 10, 10, 0, 0F); // Wheel 3
		pullmanspalaceModel[54].setRotationPoint(-30F, 4F, 3F);

		pullmanspalaceModel[55].addBox(-5F, -5F, 0F, 10, 10, 0, 0F); // Wheel 4
		pullmanspalaceModel[55].setRotationPoint(-30F, 4F, 3.5F);

		pullmanspalaceModel[56].addBox(0F, 0F, 0F, 15, 2, 1, 0F); // Box 88
		pullmanspalaceModel[56].setRotationPoint(-37.5F, -1.5F, -5.5F);

		pullmanspalaceModel[57].addBox(0F, 0F, 0F, 9, 4, 1, 0F); // Box 89
		pullmanspalaceModel[57].setRotationPoint(-34.5F, -1.5F, -5.5F);

		pullmanspalaceModel[58].addBox(0F, 0F, 0F, 3, 7, 1, 0F); // Box 90
		pullmanspalaceModel[58].setRotationPoint(-31.5F, -1.5F, -5.5F);

		pullmanspalaceModel[59].addBox(0F, 0F, 0F, 15, 2, 1, 0F); // Box 91
		pullmanspalaceModel[59].setRotationPoint(-37.5F, -1.5F, -7F);

		pullmanspalaceModel[60].addBox(0F, 0F, 0F, 9, 4, 1, 0F); // Box 92
		pullmanspalaceModel[60].setRotationPoint(-34.5F, -1.5F, -7F);

		pullmanspalaceModel[61].addBox(0F, 0F, 0F, 3, 7, 1, 0F); // Box 93
		pullmanspalaceModel[61].setRotationPoint(-31.5F, -1.5F, -7F);

		pullmanspalaceModel[62].addBox(-5F, -5F, 0F, 10, 10, 0, 0F); // Wheel 1
		pullmanspalaceModel[62].setRotationPoint(-30F, 4F, -6F);

		pullmanspalaceModel[63].addBox(-5F, -5F, 0F, 10, 10, 0, 0F); // Wheel 2
		pullmanspalaceModel[63].setRotationPoint(-30F, 4F, -5.5F);

		pullmanspalaceModel[64].addBox(0F, 0F, 0F, 1, 1, 6, 0F); // Box 96
		pullmanspalaceModel[64].setRotationPoint(-50F, -12F, -3F);

		pullmanspalaceModel[65].addBox(0F, 0F, 0F, 1, 1, 6, 0F); // Box 97
		pullmanspalaceModel[65].setRotationPoint(49F, -12F, -3F);

		pullmanspalaceModel[66].addBox(0F, 0F, 0F, 1, 1, 6, 0F); // Box 98
		pullmanspalaceModel[66].setRotationPoint(49F, -10F, -3F);


	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		for(int i = 0; i < 67; i++)
		{
			pullmanspalaceModel[i].render(f5);
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
	}

	private ModelRendererTurbo pullmanspalaceModel[];
}