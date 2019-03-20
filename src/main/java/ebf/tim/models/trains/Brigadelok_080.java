//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2018 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model:
// Model Creator:
// Created on: 13.06.2017 - 17:47:26
// Last changed on: 13.06.2017 - 17:47:26

package ebf.tim.models.trains; //Path where the model is located


import ebf.tim.models.StaticModelAnimator;
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.ModelConverter;
import fexcraft.tmt.slim.ModelRendererTurbo;

import static ebf.tim.models.StaticModelAnimator.tagLamp;

public class Brigadelok_080 extends ModelConverter //Same as Filename
{
	int textureX = 256;
	int textureY = 256;

	public Brigadelok_080() //Same as Filename
	{
		bodyModel = new ModelRendererTurbo[117];

		initbodyModel_1();
	}

	private void initbodyModel_1()
	{
		bodyModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 0
		bodyModel[1] = new ModelRendererTurbo(this, 73, 1, textureX, textureY); // Box 2
		bodyModel[2] = new ModelRendererTurbo(this, 153, 1, textureX, textureY); // Box 3
		bodyModel[3] = new ModelRendererTurbo(this, 1, 17, textureX, textureY); // Box 4
		bodyModel[4] = new ModelRendererTurbo(this, 72, 16, textureX, textureY); // Box 5
		bodyModel[5] = new ModelRendererTurbo(this, 128, 17, textureX, textureY); // Box 6
		bodyModel[6] = new ModelRendererTurbo(this, 233, 1, textureX, textureY); // Box 7
		bodyModel[7] = new ModelRendererTurbo(this, 145, 1, textureX, textureY); // Box 8
		bodyModel[8] = new ModelRendererTurbo(this, 241, 9, textureX, textureY); // Box 9
		bodyModel[9] = new ModelRendererTurbo(this, 113, 17, textureX, textureY); // Box 10
		bodyModel[10] = new ModelRendererTurbo(this, 73, 1, textureX, textureY); // Box 11
		bodyModel[11] = new ModelRendererTurbo(this, 1, 17, textureX, textureY); // Box 12
		bodyModel[12] = new ModelRendererTurbo(this, 129, 17, textureX, textureY); // Box 13
		bodyModel[13] = new ModelRendererTurbo(this, 193, 17, textureX, textureY); // Box 14
		bodyModel[14] = new ModelRendererTurbo(this, 209, 17, textureX, textureY); // Box 15
		bodyModel[15] = new ModelRendererTurbo(this, 225, 17, textureX, textureY); // Box 16
		bodyModel[16] = new ModelRendererTurbo(this, 241, 17, textureX, textureY); // Box 17
		bodyModel[17] = new ModelRendererTurbo(this, 209, 25, textureX, textureY); // Box 18
		bodyModel[18] = new ModelRendererTurbo(this, 1, 33, textureX, textureY); // Box 19
		bodyModel[19] = new ModelRendererTurbo(this, 25, 33, textureX, textureY); // Box 20
		bodyModel[20] = new ModelRendererTurbo(this, 41, 33, textureX, textureY); // Box 21
		bodyModel[21] = new ModelRendererTurbo(this, 225, 33, textureX, textureY); // Box 22
		bodyModel[22] = new ModelRendererTurbo(this, 65, 41, textureX, textureY); // Box 23
		bodyModel[23] = new ModelRendererTurbo(this, 81, 17, textureX, textureY); // Box 24
		bodyModel[24] = new ModelRendererTurbo(this, 241, 1, textureX, textureY); // Box 25
		bodyModel[25] = new ModelRendererTurbo(this, 113, 25, textureX, textureY); // Box 26
		bodyModel[26] = new ModelRendererTurbo(this, 81, 41, textureX, textureY); // Box 27
		bodyModel[27] = new ModelRendererTurbo(this, 97, 41, textureX, textureY); // Box 28
		bodyModel[28] = new ModelRendererTurbo(this, 113, 41, textureX, textureY); // Box 29
		bodyModel[29] = new ModelRendererTurbo(this, 113, 25, textureX, textureY); // Box 30
		bodyModel[30] = new ModelRendererTurbo(this, 129, 25, textureX, textureY); // Box 31
		bodyModel[31] = new ModelRendererTurbo(this, 121, 49, textureX, textureY); // Box 32
		bodyModel[32] = new ModelRendererTurbo(this, 1, 33, textureX, textureY); // Box 33
		bodyModel[33] = new ModelRendererTurbo(this, 1, 57, textureX, textureY); // Box 34
		bodyModel[34] = new ModelRendererTurbo(this, 33, 57, textureX, textureY); // Box 35
		bodyModel[35] = new ModelRendererTurbo(this, 65, 57, textureX, textureY); // Box 36
		bodyModel[36] = new ModelRendererTurbo(this, 121, 25, textureX, textureY); // Box 37
		bodyModel[37] = new ModelRendererTurbo(this, 129, 25, textureX, textureY); // Box 38
		bodyModel[38] = new ModelRendererTurbo(this, 193, 25, textureX, textureY); // Box 39
		bodyModel[39] = new ModelRendererTurbo(this, 201, 25, textureX, textureY); // Box 40
		bodyModel[40] = new ModelRendererTurbo(this, 201, 49, textureX, textureY); // Box 41
		bodyModel[41] = new ModelRendererTurbo(this, 65, 33, textureX, textureY); // Box 42
		bodyModel[42] = new ModelRendererTurbo(this, 209, 41, textureX, textureY); // Box 43
		bodyModel[43] = new ModelRendererTurbo(this, 241, 49, textureX, textureY); // Box 45
		bodyModel[44] = new ModelRendererTurbo(this, 1, 57, textureX, textureY); // Box 46
		bodyModel[45] = new ModelRendererTurbo(this, 97, 67, textureX, textureY); // Box 47
		bodyModel[46] = new ModelRendererTurbo(this, 241, 57, textureX, textureY); // Box 48
		bodyModel[47] = new ModelRendererTurbo(this, 136, 65, textureX, textureY); // Box 49
		bodyModel[48] = new ModelRendererTurbo(this, 153, 65, textureX, textureY); // Box 50
		bodyModel[49] = new ModelRendererTurbo(this, 1, 81, textureX, textureY); // Box 51
		bodyModel[50] = new ModelRendererTurbo(this, 193, 73, textureX, textureY); // Box 52
		bodyModel[51] = new ModelRendererTurbo(this, 57, 97, textureX, textureY); // Box 53
		bodyModel[52] = new ModelRendererTurbo(this, 177, 65, textureX, textureY); // Box 54
		bodyModel[53] = new ModelRendererTurbo(this, 217, 73, textureX, textureY); // Box 55
		bodyModel[54] = new ModelRendererTurbo(this, 185, 105, textureX, textureY); // Box 56
		bodyModel[55] = new ModelRendererTurbo(this, 193, 97, textureX, textureY); // Box 57
		bodyModel[56] = new ModelRendererTurbo(this, 1, 105, textureX, textureY); // Box 58
		bodyModel[57] = new ModelRendererTurbo(this, 1, 113, textureX, textureY); // Box 59
		bodyModel[58] = new ModelRendererTurbo(this, 97, 113, textureX, textureY); // Box 60
		bodyModel[59] = new ModelRendererTurbo(this, 57, 81, textureX, textureY); // Box 61
		bodyModel[60] = new ModelRendererTurbo(this, 145, 113, textureX, textureY,tagLamp("cone",-0.0001f, 0)); // Box 62
		bodyModel[61] = new ModelRendererTurbo(this, 73, 81, textureX, textureY); // Box 63
		bodyModel[62] = new ModelRendererTurbo(this, 161, 113, textureX, textureY); // Box 64
		bodyModel[63] = new ModelRendererTurbo(this, 97, 57, textureX, textureY); // Box 65
		bodyModel[64] = new ModelRendererTurbo(this, 1, 121, textureX, textureY); // Box 66
		bodyModel[65] = new ModelRendererTurbo(this, 25, 121, textureX, textureY); // Box 67
		bodyModel[66] = new ModelRendererTurbo(this, 25, 57, textureX, textureY); // Box 68
		bodyModel[67] = new ModelRendererTurbo(this, 41, 121, textureX, textureY); // Box 69
		bodyModel[68] = new ModelRendererTurbo(this, 241, 33, textureX, textureY); // Box 71
		bodyModel[69] = new ModelRendererTurbo(this, 1, 49, textureX, textureY); // Box 72
		bodyModel[70] = new ModelRendererTurbo(this, 25, 49, textureX, textureY); // Box 73
		bodyModel[71] = new ModelRendererTurbo(this, 225, 57, textureX, textureY); // Box 74
		bodyModel[72] = new ModelRendererTurbo(this, 1, 65, textureX, textureY); // Box 75
		bodyModel[73] = new ModelRendererTurbo(this, 25, 65, textureX, textureY); // Box 76
		bodyModel[74] = new ModelRendererTurbo(this, 57, 65, textureX, textureY); // Box 77
		bodyModel[75] = new ModelRendererTurbo(this, 97, 65, textureX, textureY); // Box 78
		bodyModel[76] = new ModelRendererTurbo(this, 121, 57, textureX, textureY); // Box 79
		bodyModel[77] = new ModelRendererTurbo(this, 73, 65, textureX, textureY); // Box 80
		bodyModel[78] = new ModelRendererTurbo(this, 121, 65, textureX, textureY, StaticModelAnimator.tagWheel); // wheel
		bodyModel[79] = new ModelRendererTurbo(this, 153, 65, textureX, textureY, StaticModelAnimator.tagWheel); // wheel
		bodyModel[80] = new ModelRendererTurbo(this, 33, 73, textureX, textureY, StaticModelAnimator.tagWheel); // wheel
		bodyModel[81] = new ModelRendererTurbo(this, 49, 73, textureX, textureY, StaticModelAnimator.tagWheel); // wheel
		bodyModel[82] = new ModelRendererTurbo(this, 121, 73, textureX, textureY, StaticModelAnimator.tagWheel); // wheel
		bodyModel[83] = new ModelRendererTurbo(this, 153, 73, textureX, textureY, StaticModelAnimator.tagWheel); // wheel
		bodyModel[84] = new ModelRendererTurbo(this, 1, 81, textureX, textureY, StaticModelAnimator.tagWheel); // wheel
		bodyModel[85] = new ModelRendererTurbo(this, 1, 89, textureX, textureY, StaticModelAnimator.tagWheel); // wheel
		bodyModel[86] = new ModelRendererTurbo(this, 81, 41, textureX, textureY); // Box 89
		bodyModel[87] = new ModelRendererTurbo(this, 41, 105, textureX, textureY); // Box 90
		bodyModel[88] = new ModelRendererTurbo(this, 81, 121, textureX, textureY); // Box 91
		bodyModel[89] = new ModelRendererTurbo(this, 113, 121, textureX, textureY); // Box 92
		bodyModel[90] = new ModelRendererTurbo(this, 185, 121, textureX, textureY); // Box 93
		bodyModel[91] = new ModelRendererTurbo(this, 25, 137, textureX, textureY); // Box 94
		bodyModel[92] = new ModelRendererTurbo(this, 97, 137, textureX, textureY); // Box 95
		bodyModel[93] = new ModelRendererTurbo(this, 129, 129, textureX, textureY); // Connecting Rod
		bodyModel[94] = new ModelRendererTurbo(this, 241, 89, textureX, textureY); // pistonNoName
		bodyModel[95] = new ModelRendererTurbo(this, 169, 137, textureX, textureY); // Angled Piston
		bodyModel[96] = new ModelRendererTurbo(this, 249, 41, textureX, textureY); // reverser
		bodyModel[97] = new ModelRendererTurbo(this, 209, 137, textureX, textureY, StaticModelAnimator.tagSimplePiston); // simple piston
		bodyModel[98] = new ModelRendererTurbo(this, 201, 73, textureX, textureY); // Box 99
		bodyModel[99] = new ModelRendererTurbo(this, 57, 49, textureX, textureY); // Box 100
		bodyModel[100] = new ModelRendererTurbo(this, 57, 81, textureX, textureY); // Box 101
		bodyModel[101] = new ModelRendererTurbo(this, 177, 145, textureX, textureY); // Connecting Rod
		bodyModel[102] = new ModelRendererTurbo(this, 137, 97, textureX, textureY); // piston
		bodyModel[103] = new ModelRendererTurbo(this, 1, 145, textureX, textureY, StaticModelAnimator.tagSimplePiston); // simple piston
		bodyModel[104] = new ModelRendererTurbo(this, 1, 153, textureX, textureY); // Angled Piston
		bodyModel[105] = new ModelRendererTurbo(this, 209, 49, textureX, textureY); // reverser
		bodyModel[106] = new ModelRendererTurbo(this, 201, 81, textureX, textureY); // Box 107
		bodyModel[107] = new ModelRendererTurbo(this, 57, 105, textureX, textureY); // Box 108
		bodyModel[108] = new ModelRendererTurbo(this, 65, 121, textureX, textureY); // Box 109
		bodyModel[109] = new ModelRendererTurbo(this, 209, 57, textureX, textureY); // Box 110
		bodyModel[110] = new ModelRendererTurbo(this, 59, 38, textureX, textureY); // Box 111
		bodyModel[111] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, tagLamp("cone",0.01f, 0)); // lamp
		bodyModel[112] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, "smoke 3 3"); // smoke
		bodyModel[113] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, "steam 1 4"); // steam
		bodyModel[114] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, "steam 1 4"); // steam
		bodyModel[115] = new ModelRendererTurbo(this, 169, 137, textureX, textureY); // Box 115
		bodyModel[116] = new ModelRendererTurbo(this, 169, 137, textureX, textureY); // Box 116

		bodyModel[0].addBox(0F, 0F, 0F, 32, 10, 3, 0F); // Box 0
		bodyModel[0].setRotationPoint(-25F, -8.5F, -1.5F);

		bodyModel[1].addBox(0F, 0F, 0F, 32, 8, 6, 0F); // Box 2
		bodyModel[1].setRotationPoint(-25F, -7.5F, -3F);

		bodyModel[2].addBox(0F, 0F, 0F, 32, 6, 8, 0F); // Box 3
		bodyModel[2].setRotationPoint(-25F, -6.5F, -4F);

		bodyModel[3].addBox(0F, 0F, 0F, 32, 3, 10, 0F); // Box 4
		bodyModel[3].setRotationPoint(-25F, -5F, -5F);

		bodyModel[4].addBox(0F, 0F, 0F, 8, 6, 18, 0F); // Box 5
		bodyModel[4].setRotationPoint(-23F, -5F, -9F);

		bodyModel[5].addBox(0F, 0F, 0F, 22, 7, 18, 0F); // Box 6
		bodyModel[5].setRotationPoint(-15F, -6F, -9F);

		bodyModel[6].addBox(0F, 0F, 0F, 2, 3, 3, 0F); // Box 7
		bodyModel[6].setRotationPoint(-15F, -7.5F, -7.5F);

		bodyModel[7].addBox(0F, 0F, 0F, 3, 3, 2, 0F); // Box 8
		bodyModel[7].setRotationPoint(-15.5F, -7.5F, -7F);

		bodyModel[8].addBox(0F, 0F, 0F, 2, 3, 3, 0F); // Box 9
		bodyModel[8].setRotationPoint(-6.5F, -8.5F, -7.5F);

		bodyModel[9].addBox(0F, 0F, 0F, 3, 3, 2, 0F); // Box 10
		bodyModel[9].setRotationPoint(-7F, -8.5F, -7F);

		bodyModel[10].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 11
		bodyModel[10].setRotationPoint(-6F, -9.5F, -6.5F);

		bodyModel[11].addBox(0F, 0F, 0F, 2, 1, 2, 0F); // Box 12
		bodyModel[11].setRotationPoint(-6.5F, -10.5F, -7F);

		bodyModel[12].addBox(0F, 0F, 0F, 4, 1, 3, 0F); // Box 13
		bodyModel[12].setRotationPoint(-2F, -6.5F, -7.5F);

		bodyModel[13].addBox(0F, 0F, 0F, 4, 1, 3, 0F); // Box 14
		bodyModel[13].setRotationPoint(3F, -6.5F, -7.5F);

		bodyModel[14].addBox(0F, 0F, 0F, 3, 1, 3, 0F); // Box 15
		bodyModel[14].setRotationPoint(-24F, -9F, -1.5F);

		bodyModel[15].addBox(0F, 0F, 0F, 2, 13, 2, 0F); // Box 16
		bodyModel[15].setRotationPoint(-23.5F, -21.5F, -1F);

		bodyModel[16].addBox(0F, 0F, 0F, 3, 6, 3, 0F); // Box 17
		bodyModel[16].setRotationPoint(-24F, -27F, -1.5F);

		bodyModel[17].addBox(0F, 0F, 0F, 5, 9, 2, 0F); // Box 18
		bodyModel[17].setRotationPoint(-18F, -16.5F, -1F);

		bodyModel[18].addBox(0F, 0F, 0F, 4, 9, 4, 0F); // Box 19
		bodyModel[18].setRotationPoint(-17.5F, -16.5F, -2F);

		bodyModel[19].addBox(0F, 0F, 0F, 2, 9, 5, 0F); // Box 20
		bodyModel[19].setRotationPoint(-16.5F, -16.5F, -2.5F);

		bodyModel[20].addBox(0F, 0F, 0F, 5, 13, 3, 0F); // Box 21
		bodyModel[20].setRotationPoint(-9.5F, -20.5F, -1.5F);

		bodyModel[21].addBox(0F, 0F, 0F, 4, 13, 4, 0F); // Box 22
		bodyModel[21].setRotationPoint(-9F, -20.5F, -2F);

		bodyModel[22].addBox(0F, 0F, 0F, 2, 13, 5, 0F); // Box 23
		bodyModel[22].setRotationPoint(-8F, -20.5F, -2.5F);

		bodyModel[23].addBox(0F, 0F, 0F, 2, 3, 2, 0F); // Box 24
		bodyModel[23].setRotationPoint(-8F, -23.5F, -1F);

		bodyModel[24].addBox(0F, 0F, 0F, 3, 1, 1, 0F); // Box 25
		bodyModel[24].setRotationPoint(-11F, -22F, -0.5F);

		bodyModel[25].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 26
		bodyModel[25].setRotationPoint(-10.5F, -21F, -0.5F);

		bodyModel[26].addBox(0F, 0F, 0F, 3, 7, 3, 0F); // Box 27
		bodyModel[26].setRotationPoint(-0.5F, -14.5F, -1.5F);

		bodyModel[27].addBox(0F, 0F, 0F, 4, 7, 2, 0F); // Box 28
		bodyModel[27].setRotationPoint(-1F, -14.5F, -1F);

		bodyModel[28].addBox(0F, 0F, 0F, 2, 7, 4, 0F); // Box 29
		bodyModel[28].setRotationPoint(0F, -14.5F, -2F);

		bodyModel[29].addBox(0F, 0F, 0F, 1, 2, 5, 0F); // Box 30
		bodyModel[29].setRotationPoint(0.5F, -9.5F, -2.5F);

		bodyModel[30].addBox(0F, 0F, 0F, 1, 2, 6, 0F); // Box 31
		bodyModel[30].setRotationPoint(-16F, -9.5F, -3F);

		bodyModel[31].addBox(0F, 0F, 0F, 35, 3, 12, 0F); // Box 32
		bodyModel[31].setRotationPoint(-24F, 2.5F, -6F);

		bodyModel[32].addBox(0F, 0F, 0F, 1, 2, 20, 0F); // Box 33
		bodyModel[32].setRotationPoint(-25F, 2.5F, -10F);

		bodyModel[33].addBox(0F, 0F, 0F, 1, 3, 14, 0F); // Box 34
		bodyModel[33].setRotationPoint(-25.5F, 5F, -7F);

		bodyModel[34].addBox(0F, 0F, 0F, 4, 3, 12, 0F); // Box 35
		bodyModel[34].setRotationPoint(-24.5F, 4.5F, -6F);

		bodyModel[35].addBox(0F, 0F, 0F, 3, 5, 18, 0F); // Box 36
		bodyModel[35].setRotationPoint(-21F, 2.5F, -9F);

		bodyModel[36].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 37
		bodyModel[36].setRotationPoint(-19F, 1F, -7.5F);

		bodyModel[37].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 38
		bodyModel[37].setRotationPoint(-20.5F, 1F, -7.5F);

		bodyModel[38].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 39
		bodyModel[38].setRotationPoint(-20.5F, 1F, 6.5F);

		bodyModel[39].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 40
		bodyModel[39].setRotationPoint(-19F, 1F, 6.5F);

		bodyModel[40].addBox(0F, 0F, 0F, 1, 2, 16, 0F); // Box 41
		bodyModel[40].setRotationPoint(-24.5F, 7.5F, -8F);

		bodyModel[41].addBox(0F, 0F, 0F, 1, 2, 2, 0F); // Box 42
		bodyModel[41].setRotationPoint(-27.5F, 5.5F, -1F);

		bodyModel[42].addBox(0F, 0F, 0F, 1, 3, 3, 0F); // Box 43
		bodyModel[42].setRotationPoint(-28.5F, 5F, -1.5F);

		bodyModel[43].addBox(0F, 0F, 0F, 2, 2, 3, 0F); // Box 45
		bodyModel[43].setRotationPoint(-12.5F, -7.5F, 4.5F);

		bodyModel[44].addBox(0F, 0F, 0F, 3, 2, 2, 0F); // Box 46
		bodyModel[44].setRotationPoint(-13F, -7.5F, 5F);

		bodyModel[45].addBox(0F, 0F, 0F, 1, 25, 16, 0F); // Box 47
		bodyModel[45].setRotationPoint(7F, -22.5F, -8F);

		bodyModel[46].addBox(0F, 0F, 0F, 5, 24, 1, 0F); // Box 48
		bodyModel[46].setRotationPoint(7F, -21.5F, -9F);

		bodyModel[47].addBox(0F, 0F, 0F, 5, 24, 1, 0F); // Box 49
		bodyModel[47].setRotationPoint(7F, -21.5F, 8F);

		bodyModel[48].addBox(0F, 0F, 0F, 1, 29, 18, 0F); // Box 50
		bodyModel[48].setRotationPoint(27F, -22.5F, -9F);

		bodyModel[49].addBox(0F, 0F, 0F, 15, 1, 18, 0F); // Box 51
		bodyModel[49].setRotationPoint(12F, 5.5F, -9F);

		bodyModel[50].addBox(0F, 0F, 0F, 1, 4, 18, 0F); // Box 52
		bodyModel[50].setRotationPoint(11F, 2.5F, -9F);

		bodyModel[51].addBox(0F, 0F, 0F, 4, 1, 16, 0F); // Box 53
		bodyModel[51].setRotationPoint(8F, 1.5F, -8F);

		bodyModel[52].addBox(0F, 0F, 0F, 9, 12, 1, 0F); // Box 54
		bodyModel[52].setRotationPoint(12F, -6F, 8F);

		bodyModel[53].addBox(0F, 0F, 0F, 9, 12, 1, 0F); // Box 55
		bodyModel[53].setRotationPoint(12F, -6F, -9F);

		bodyModel[54].addBox(0F, 0F, 0F, 23, 1, 10, 0F); // Box 56
		bodyModel[54].setRotationPoint(6F, -24.5F, -5F);

		bodyModel[55].addBox(0F, 0F, 0F, 23, 1, 3, 0F); // Box 57
		bodyModel[55].setRotationPoint(6F, -23.5F, -8F);

		bodyModel[56].addBox(0F, 0F, 0F, 23, 1, 3, 0F); // Box 58
		bodyModel[56].setRotationPoint(6F, -23.5F, 5F);

		bodyModel[57].addBox(0F, 0F, 0F, 23, 1, 2, 0F); // Box 59
		bodyModel[57].setRotationPoint(6F, -22.5F, 8F);

		bodyModel[58].addBox(0F, 0F, 0F, 23, 1, 2, 0F); // Box 60
		bodyModel[58].setRotationPoint(6F, -22.5F, -10F);

		bodyModel[59].addBox(0F, 0F, 0F, 1, 1, 10, 0F); // Box 61
		bodyModel[59].setRotationPoint(7F, -23.5F, -5F);

		bodyModel[60].addBox(0F, 0F, 0F, 1, 1, 10, 0F); // Box 62
		bodyModel[60].setRotationPoint(27F, -23.5F, -5F);

		bodyModel[61].addBox(0F, 0F, 0F, 5, 2, 6, 0F); // Box 63
		bodyModel[61].setRotationPoint(16F, -26.5F, -3F);

		bodyModel[62].addBox(0F, 0F, 0F, 7, 1, 7, 0F); // Box 64
		bodyModel[62].setRotationPoint(15F, -27.5F, -3.5F);

		bodyModel[63].addBox(0F, 0F, 0F, 7, 1, 4, 0F); // Box 65
		bodyModel[63].setRotationPoint(15F, -28F, -2F);

		bodyModel[64].addBox(0F, 0F, 0F, 6, 11, 10, 0F); // Box 66
		bodyModel[64].setRotationPoint(8F, -5.5F, -5F);

		bodyModel[65].addBox(0F, 0F, 0F, 6, 2, 7, 0F); // Box 67
		bodyModel[65].setRotationPoint(7.5F, -7.5F, -3.5F);

		bodyModel[66].addBox(0F, 0F, 0F, 5, 1, 4, 0F); // Box 68
		bodyModel[66].setRotationPoint(7.5F, -8.5F, -2F);

		bodyModel[67].addBox(0F, 0F, 0F, 4, 2, 12, 0F); // Box 69
		bodyModel[67].setRotationPoint(7F, 5.5F, -6F);

		bodyModel[68].addBox(0F, 0F, 0F, 3, 2, 1, 0F); // Box 71
		bodyModel[68].setRotationPoint(4F, 5.5F, -6F);

		bodyModel[69].addBox(0F, 0F, 0F, 3, 2, 1, 0F); // Box 72
		bodyModel[69].setRotationPoint(4F, 5.5F, 5F);

		bodyModel[70].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 73
		bodyModel[70].setRotationPoint(-2F, 5.5F, -6F);

		bodyModel[71].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 74
		bodyModel[71].setRotationPoint(-2F, 5.5F, 5F);

		bodyModel[72].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 75
		bodyModel[72].setRotationPoint(-14F, 5.5F, -6F);

		bodyModel[73].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 76
		bodyModel[73].setRotationPoint(-14F, 5.5F, 5F);

		bodyModel[74].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 77
		bodyModel[74].setRotationPoint(-8F, 5.5F, 5F);

		bodyModel[75].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 78
		bodyModel[75].setRotationPoint(-8F, 5.5F, -6F);

		bodyModel[76].addBox(0F, 0F, 0F, 3, 2, 1, 0F); // Box 79
		bodyModel[76].setRotationPoint(-18F, 5.5F, -6F);

		bodyModel[77].addBox(0F, 0F, 0F, 3, 2, 1, 0F); // Box 80
		bodyModel[77].setRotationPoint(-18F, 5.5F, 5F);

		bodyModel[78].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // wheel
		bodyModel[78].setRotationPoint(-14.5F, 6F, -5F);

		bodyModel[79].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // wheel
		bodyModel[79].setRotationPoint(-8.5F, 6F, -5F);

		bodyModel[80].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // wheel
		bodyModel[80].setRotationPoint(3.5F, 6F, -5F);

		bodyModel[81].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // wheel
		bodyModel[81].setRotationPoint(-2.5F, 6F, -5F);

		bodyModel[82].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // wheel
		bodyModel[82].setRotationPoint(3.5F, 6F, 5F);

		bodyModel[83].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // wheel
		bodyModel[83].setRotationPoint(-2.5F, 6F, 5F);

		bodyModel[84].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // wheel
		bodyModel[84].setRotationPoint(-8.5F, 6F, 5F);

		bodyModel[85].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // wheel
		bodyModel[85].setRotationPoint(-14.5F, 6F, 5F);

		bodyModel[86].addBox(-0.5F, -0.5F, 0F, 1, 1, 13, 0F); // Box 89
		bodyModel[86].setRotationPoint(3.5F, 6F, -6.5F);

		bodyModel[87].addBox(-0.5F, -0.5F, 0F, 1, 1, 13, 0F); // Box 90
		bodyModel[87].setRotationPoint(-2.5F, 6F, -6.5F);

		bodyModel[88].addBox(-0.5F, -0.5F, 0F, 1, 1, 13, 0F); // Box 91
		bodyModel[88].setRotationPoint(-8.5F, 6F, -6.5F);

		bodyModel[89].addBox(-0.5F, -0.5F, 0F, 1, 1, 13, 0F); // Box 92
		bodyModel[89].setRotationPoint(-14.5F, 6F, -6.5F);

		bodyModel[90].addBox(0F, 0F, 0F, 16, 1, 12, 0F); // Box 93
		bodyModel[90].setRotationPoint(11F, 6.5F, -6F);

		bodyModel[91].addBox(0F, 0F, 0F, 30, 1, 11, 0F); // Box 94
		bodyModel[91].setRotationPoint(-23F, 1F, -5.5F);

		bodyModel[92].addBox(0F, 0F, 0F, 30, 1, 9, 0F); // Box 95
		bodyModel[92].setRotationPoint(-23F, 2F, -4.5F);

		bodyModel[93].addBox(-9.5F, -0.5F, -0.5F, 19, 1, 1, 0F); // Connecting Rod
		bodyModel[93].setRotationPoint(-5.5F, 6.5F, 7F);

		bodyModel[94].addBox(-9F, -0.5F, -0.5F, 4, 1, 1, 0F); // pistonNoName
		bodyModel[94].setRotationPoint(-10.5F, 5F, 7.5F);

		bodyModel[95].addBox(-9F, -0.5F, -0.5F, 18, 1, 1, 0F); // Angled Piston
		bodyModel[95].setRotationPoint(-10.5F, 4.75F, 8F);
		bodyModel[95].rotateAngleZ = 6.14355897F;

		bodyModel[96].addBox(-1F, 0F, -0.5F, 1, 4, 1, 0F); // reverser
		bodyModel[96].setRotationPoint(-9.5F, 1.5F, 8F);

		bodyModel[97].addBox(-7F, -0.5F, -0.5F, 14, 1, 1, 0F); // simple piston
		bodyModel[97].setRotationPoint(-8.75F, 5.5F, 7.5F);
		bodyModel[97].rotateAngleZ = 6.20900881F;

		bodyModel[98].addBox(0F, 0F, 0F, 3, 1, 1, 0F); // Box 99
		bodyModel[98].setRotationPoint(4F, -19F, -0.5F);

		bodyModel[99].addBox(0F, 0F, 0F, 1, 8, 1, 0F); // Box 100
		bodyModel[99].setRotationPoint(4F, -27F, -0.5F);

		bodyModel[100].addBox(0F, 0F, 0F, 2, 2, 2, 0F); // Box 101
		bodyModel[100].setRotationPoint(3.5F, -26F, -1F);

		bodyModel[101].addBox(-9.5F, -0.5F, -0.5F, 19, 1, 1, 0F); // Connecting Rod
		bodyModel[101].setRotationPoint(-5.5F, 6.5F, -7F);

		bodyModel[102].addBox(-9F, -0.5F, -0.5F, 4, 1, 1, 0F); // piston
		bodyModel[102].setRotationPoint(-10.5F, 5F, -7.5F);

		bodyModel[103].addBox(-7F, -0.5F, -0.5F, 14, 1, 1, 0F); // simple piston
		bodyModel[103].setRotationPoint(-8.75F, 5.7F, -7.5F);
		bodyModel[103].rotateAngleZ = 6.17410223F;

		bodyModel[104].addBox(-9F, -0.5F, -0.5F, 18, 1, 1, 0F); // Angled Piston
		bodyModel[104].setRotationPoint(-10.5F, 4.75F, -8F);
		bodyModel[104].rotateAngleZ = 6.14355897F;

		bodyModel[105].addBox(-1F, 0F, -0.5F, 1, 4, 1, 0F); // reverser
		bodyModel[105].setRotationPoint(-9.5F, 1.5F, -8F);

		bodyModel[106].addBox(-1F, 0F, -0.5F, 1, 1, 3, 0F); // Box 107
		bodyModel[106].setRotationPoint(-9.5F, 1.5F, 5F);

		bodyModel[107].addBox(-1F, 0F, -0.5F, 1, 1, 3, 0F); // Box 108
		bodyModel[107].setRotationPoint(-9.5F, 1.5F, -7F);

		bodyModel[108].addBox(0F, 0F, 0F, 13, 1, 1, 0F); // Box 109
		bodyModel[108].setRotationPoint(-6F, -8.5F, 3.5F);

		bodyModel[109].addBox(0F, 0F, 0F, 2, 2, 1, 0F); // Box 110
		bodyModel[109].setRotationPoint(-8F, -8.5F, 3F);
		bodyModel[109].rotateAngleX = 0.78539816F;

		bodyModel[110].addBox(0F, 0F, 0F, 1, 3, 3, 0F); // Box 111
		bodyModel[110].setRotationPoint(-26.5F, 5F, -1.5F);

		bodyModel[111].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // lamp
		bodyModel[111].setRotationPoint(-25F, -10F, -0.5F);
		bodyModel[111].rotateAngleX=0.78539816F;

		bodyModel[112].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // smoke
		bodyModel[112].setRotationPoint(-23F, -28F, -0.5F);

		bodyModel[113].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // steam
		bodyModel[113].setRotationPoint(-18F, 4F, -6.5F);

		bodyModel[114].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // steam
		bodyModel[114].setRotationPoint(-18F, 4F, 5.5F);

		bodyModel[115].addBox(-9F, -0.5F, -0.5F, 9, 1, 1, 0F); // Box 115
		bodyModel[115].setRotationPoint(-1.5F, 6F, 8F);
		bodyModel[115].rotateAngleZ = 5.95157275F;

		bodyModel[116].addBox(-9F, -0.5F, -0.5F, 9, 1, 1, 0F); // Box 116
		bodyModel[116].setRotationPoint(-1.5F, 6F, -8F);
		bodyModel[116].rotateAngleZ = 5.95157275F;

		fixRotation(bodyModel, false, true, true);
	}
}