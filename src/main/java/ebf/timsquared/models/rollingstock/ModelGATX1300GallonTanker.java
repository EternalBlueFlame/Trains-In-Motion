//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator: 
// Created on: 14.05.2017 - 15:36:58
// Last changed on: 14.05.2017 - 15:36:58

package ebf.timsquared.models.rollingstock;

import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.ModelRendererTurbo;
import net.minecraft.entity.Entity;

public class ModelGATX1300GallonTanker extends ModelBase
{
	private static final int textureX = 512;
	private static final int textureY = 256;

	public ModelGATX1300GallonTanker()
	{
		gatx1300gallontankerModel = new ModelRendererTurbo[88];
		gatx1300gallontankerModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 1
		gatx1300gallontankerModel[1] = new ModelRendererTurbo(this, 25, 1, textureX, textureY); // Box 2
		gatx1300gallontankerModel[2] = new ModelRendererTurbo(this, 65, 1, textureX, textureY); // Box 3
		gatx1300gallontankerModel[3] = new ModelRendererTurbo(this, 89, 1, textureX, textureY); // Box 4
		gatx1300gallontankerModel[4] = new ModelRendererTurbo(this, 137, 1, textureX, textureY); // Box 6
		gatx1300gallontankerModel[5] = new ModelRendererTurbo(this, 113, 1, textureX, textureY); // Box 7
		gatx1300gallontankerModel[6] = new ModelRendererTurbo(this, 177, 1, textureX, textureY); // Box 8
		gatx1300gallontankerModel[7] = new ModelRendererTurbo(this, 225, 1, textureX, textureY); // Box 9
		gatx1300gallontankerModel[8] = new ModelRendererTurbo(this, 265, 1, textureX, textureY); // Box 10
		gatx1300gallontankerModel[9] = new ModelRendererTurbo(this, 201, 1, textureX, textureY); // Box 11
		gatx1300gallontankerModel[10] = new ModelRendererTurbo(this, 289, 1, textureX, textureY); // Box 12
		gatx1300gallontankerModel[11] = new ModelRendererTurbo(this, 1, 25, textureX, textureY); // Box 13
		gatx1300gallontankerModel[12] = new ModelRendererTurbo(this, 177, 25, textureX, textureY); // Box 14
		gatx1300gallontankerModel[13] = new ModelRendererTurbo(this, 1, 57, textureX, textureY); // Box 15
		gatx1300gallontankerModel[14] = new ModelRendererTurbo(this, 193, 65, textureX, textureY); // Box 16
		gatx1300gallontankerModel[15] = new ModelRendererTurbo(this, 1, 97, textureX, textureY); // Box 17
		gatx1300gallontankerModel[16] = new ModelRendererTurbo(this, 201, 105, textureX, textureY); // Box 18
		gatx1300gallontankerModel[17] = new ModelRendererTurbo(this, 337, 1, textureX, textureY); // Box 19
		gatx1300gallontankerModel[18] = new ModelRendererTurbo(this, 377, 1, textureX, textureY); // Box 20
		gatx1300gallontankerModel[19] = new ModelRendererTurbo(this, 417, 1, textureX, textureY); // Box 21
		gatx1300gallontankerModel[20] = new ModelRendererTurbo(this, 449, 1, textureX, textureY); // Box 22
		gatx1300gallontankerModel[21] = new ModelRendererTurbo(this, 481, 1, textureX, textureY); // Box 23
		gatx1300gallontankerModel[22] = new ModelRendererTurbo(this, 361, 25, textureX, textureY); // Box 24
		gatx1300gallontankerModel[23] = new ModelRendererTurbo(this, 505, 1, textureX, textureY); // Box 25
		gatx1300gallontankerModel[24] = new ModelRendererTurbo(this, 161, 1, textureX, textureY); // Box 48
		gatx1300gallontankerModel[25] = new ModelRendererTurbo(this, 377, 25, textureX, textureY); // Box 49
		gatx1300gallontankerModel[26] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 50
		gatx1300gallontankerModel[27] = new ModelRendererTurbo(this, 265, 1, textureX, textureY); // Box 51
		gatx1300gallontankerModel[28] = new ModelRendererTurbo(this, 249, 1, textureX, textureY); // Box 52
		gatx1300gallontankerModel[29] = new ModelRendererTurbo(this, 177, 1, textureX, textureY); // Box 55
		gatx1300gallontankerModel[30] = new ModelRendererTurbo(this, 161, 1, textureX, textureY); // Box 56
		gatx1300gallontankerModel[31] = new ModelRendererTurbo(this, 257, 1, textureX, textureY); // Box 57
		gatx1300gallontankerModel[32] = new ModelRendererTurbo(this, 33, 9, textureX, textureY); // Box 59
		gatx1300gallontankerModel[33] = new ModelRendererTurbo(this, 289, 1, textureX, textureY); // Box 60
		gatx1300gallontankerModel[34] = new ModelRendererTurbo(this, 297, 1, textureX, textureY); // Box 61
		gatx1300gallontankerModel[35] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 62
		gatx1300gallontankerModel[36] = new ModelRendererTurbo(this, 177, 1, textureX, textureY); // Box 63
		gatx1300gallontankerModel[37] = new ModelRendererTurbo(this, 361, 49, textureX, textureY); // Box 64
		gatx1300gallontankerModel[38] = new ModelRendererTurbo(this, 361, 57, textureX, textureY); // Box 65
		gatx1300gallontankerModel[39] = new ModelRendererTurbo(this, 337, 1, textureX, textureY); // Box 66
		gatx1300gallontankerModel[40] = new ModelRendererTurbo(this, 185, 1, textureX, textureY); // Box 67
		gatx1300gallontankerModel[41] = new ModelRendererTurbo(this, 65, 9, textureX, textureY); // Box 68
		gatx1300gallontankerModel[42] = new ModelRendererTurbo(this, 265, 1, textureX, textureY); // Box 69
		gatx1300gallontankerModel[43] = new ModelRendererTurbo(this, 345, 1, textureX, textureY); // Box 70
		gatx1300gallontankerModel[44] = new ModelRendererTurbo(this, 361, 1, textureX, textureY); // Box 71
		gatx1300gallontankerModel[45] = new ModelRendererTurbo(this, 369, 1, textureX, textureY); // Box 72
		gatx1300gallontankerModel[46] = new ModelRendererTurbo(this, 377, 1, textureX, textureY); // Box 73
		gatx1300gallontankerModel[47] = new ModelRendererTurbo(this, 385, 1, textureX, textureY); // Box 74
		gatx1300gallontankerModel[48] = new ModelRendererTurbo(this, 273, 1, textureX, textureY); // Box 75
		gatx1300gallontankerModel[49] = new ModelRendererTurbo(this, 401, 1, textureX, textureY); // Box 76
		gatx1300gallontankerModel[50] = new ModelRendererTurbo(this, 409, 1, textureX, textureY); // Box 77
		gatx1300gallontankerModel[51] = new ModelRendererTurbo(this, 441, 1, textureX, textureY); // Box 78
		gatx1300gallontankerModel[52] = new ModelRendererTurbo(this, 425, 1, textureX, textureY); // Box 80
		gatx1300gallontankerModel[53] = new ModelRendererTurbo(this, 465, 1, textureX, textureY); // Box 81
		gatx1300gallontankerModel[54] = new ModelRendererTurbo(this, 473, 1, textureX, textureY); // Box 82
		gatx1300gallontankerModel[55] = new ModelRendererTurbo(this, 481, 1, textureX, textureY); // Box 83
		gatx1300gallontankerModel[56] = new ModelRendererTurbo(this, 361, 1, textureX, textureY); // Box 84
		gatx1300gallontankerModel[57] = new ModelRendererTurbo(this, 121, 9, textureX, textureY); // Box 85
		gatx1300gallontankerModel[58] = new ModelRendererTurbo(this, 401, 25, textureX, textureY); // Box 91
		gatx1300gallontankerModel[59] = new ModelRendererTurbo(this, 25, 9, textureX, textureY); // Box 95
		gatx1300gallontankerModel[60] = new ModelRendererTurbo(this, 497, 33, textureX, textureY); // Box 105
		gatx1300gallontankerModel[61] = new ModelRendererTurbo(this, 57, 9, textureX, textureY); // Box 106
		gatx1300gallontankerModel[62] = new ModelRendererTurbo(this, 89, 9, textureX, textureY); // Box 118
		gatx1300gallontankerModel[63] = new ModelRendererTurbo(this, 97, 9, textureX, textureY); // Box 119
		gatx1300gallontankerModel[64] = new ModelRendererTurbo(this, 201, 9, textureX, textureY); // Box 120
		gatx1300gallontankerModel[65] = new ModelRendererTurbo(this, 417, 33, textureX, textureY); // Box 121
		gatx1300gallontankerModel[66] = new ModelRendererTurbo(this, 113, 9, textureX, textureY); // Box 122
		gatx1300gallontankerModel[67] = new ModelRendererTurbo(this, 137, 9, textureX, textureY); // Box 123
		gatx1300gallontankerModel[68] = new ModelRendererTurbo(this, 401, 9, textureX, textureY); // Box 124
		gatx1300gallontankerModel[69] = new ModelRendererTurbo(this, 417, 9, textureX, textureY); // Box 125
		gatx1300gallontankerModel[70] = new ModelRendererTurbo(this, 57, 17, textureX, textureY); // Box 126
		gatx1300gallontankerModel[71] = new ModelRendererTurbo(this, 1, 25, textureX, textureY); // Box 127
		gatx1300gallontankerModel[72] = new ModelRendererTurbo(this, 169, 25, textureX, textureY); // Box 128
		gatx1300gallontankerModel[73] = new ModelRendererTurbo(this, 385, 65, textureX, textureY); // Box 130
		gatx1300gallontankerModel[74] = new ModelRendererTurbo(this, 425, 65, textureX, textureY); // Box 131
		gatx1300gallontankerModel[75] = new ModelRendererTurbo(this, 465, 65, textureX, textureY); // Box 132
		gatx1300gallontankerModel[76] = new ModelRendererTurbo(this, 465, 17, textureX, textureY); // Box 133
		gatx1300gallontankerModel[77] = new ModelRendererTurbo(this, 489, 81, textureX, textureY); // Box 134
		gatx1300gallontankerModel[78] = new ModelRendererTurbo(this, 497, 57, textureX, textureY); // Box 135
		gatx1300gallontankerModel[79] = new ModelRendererTurbo(this, 385, 89, textureX, textureY); // Box 136
		gatx1300gallontankerModel[80] = new ModelRendererTurbo(this, 441, 33, textureX, textureY); // Box 137
		gatx1300gallontankerModel[81] = new ModelRendererTurbo(this, 369, 65, textureX, textureY); // Box 138
		gatx1300gallontankerModel[82] = new ModelRendererTurbo(this, 193, 65, textureX, textureY); // Box 139
		gatx1300gallontankerModel[83] = new ModelRendererTurbo(this, 409, 65, textureX, textureY); // Box 140
		gatx1300gallontankerModel[84] = new ModelRendererTurbo(this, 1, 57, textureX, textureY); // Box 141
		gatx1300gallontankerModel[85] = new ModelRendererTurbo(this, 121, 9, textureX, textureY); // Box 142
		gatx1300gallontankerModel[86] = new ModelRendererTurbo(this, 377, 25, textureX, textureY); // Box 143
		gatx1300gallontankerModel[87] = new ModelRendererTurbo(this, 497, 1, textureX, textureY); // Box 144

		gatx1300gallontankerModel[0].addBox(0F, 0F, 0F, 2, 1, 18, 0F); // Box 1
		gatx1300gallontankerModel[0].setRotationPoint(-50F, -1F, -9F);

		gatx1300gallontankerModel[1].addBox(0F, 0F, 0F, 17, 2, 2, 0F); // Box 2
		gatx1300gallontankerModel[1].setRotationPoint(-50F, -1F, -11F);

		gatx1300gallontankerModel[2].addBox(0F, 0F, 0F, 17, 2, 2, 0F); // Box 3
		gatx1300gallontankerModel[2].setRotationPoint(-50F, -1F, 9F);

		gatx1300gallontankerModel[3].addBox(0F, 0F, 0F, 2, 2, 18, 0F); // Box 4
		gatx1300gallontankerModel[3].setRotationPoint(-35F, -2F, -9F);

		gatx1300gallontankerModel[4].addBox(0F, 0F, 0F, 2, 2, 16, 0F); // Box 6
		gatx1300gallontankerModel[4].setRotationPoint(-35F, -4F, -8F);

		gatx1300gallontankerModel[5].addBox(0F, 0F, 0F, 17, 2, 2, 0F); // Box 7
		gatx1300gallontankerModel[5].setRotationPoint(33F, -1F, 9F);

		gatx1300gallontankerModel[6].addBox(0F, 0F, 0F, 2, 1, 18, 0F); // Box 8
		gatx1300gallontankerModel[6].setRotationPoint(48F, -1F, -9F);

		gatx1300gallontankerModel[7].addBox(0F, 0F, 0F, 2, 2, 16, 0F); // Box 9
		gatx1300gallontankerModel[7].setRotationPoint(33F, -4F, -8F);

		gatx1300gallontankerModel[8].addBox(0F, 0F, 0F, 2, 2, 18, 0F); // Box 10
		gatx1300gallontankerModel[8].setRotationPoint(33F, -2F, -9F);

		gatx1300gallontankerModel[9].addBox(0F, 0F, 0F, 17, 2, 2, 0F); // Box 11
		gatx1300gallontankerModel[9].setRotationPoint(33F, -1F, -11F);

		gatx1300gallontankerModel[10].addBox(0F, 0F, 0F, 15, 1, 15, 0F); // Box 12
		gatx1300gallontankerModel[10].setRotationPoint(-7.5F, -25F, -7.5F);

		gatx1300gallontankerModel[11].addBox(0F, 0F, 0F, 78, 21, 9, 0F); // Box 13
		gatx1300gallontankerModel[11].setRotationPoint(-39F, -23F, -4.5F);

		gatx1300gallontankerModel[12].addBox(0F, 0F, 0F, 78, 19, 13, 0F); // Box 14
		gatx1300gallontankerModel[12].setRotationPoint(-39F, -22F, -6.5F);

		gatx1300gallontankerModel[13].addBox(0F, 0F, 0F, 78, 17, 15, 0F); // Box 15
		gatx1300gallontankerModel[13].setRotationPoint(-39F, -21F, -7.5F);

		gatx1300gallontankerModel[14].addBox(0F, 0F, 0F, 78, 15, 17, 0F); // Box 16
		gatx1300gallontankerModel[14].setRotationPoint(-39F, -20F, -8.5F);

		gatx1300gallontankerModel[15].addBox(0F, 0F, 0F, 78, 11, 19, 0F); // Box 17
		gatx1300gallontankerModel[15].setRotationPoint(-39F, -18F, -9.5F);

		gatx1300gallontankerModel[16].addBox(0F, 0F, 0F, 78, 7, 21, 0F); // Box 18
		gatx1300gallontankerModel[16].setRotationPoint(-39F, -16F, -10.5F);

		gatx1300gallontankerModel[17].addBox(0F, 0F, 0F, 2, 3, 17, 0F); // Box 19
		gatx1300gallontankerModel[17].setRotationPoint(-41F, -14F, -8.5F);

		gatx1300gallontankerModel[18].addBox(0F, 0F, 0F, 2, 7, 15, 0F); // Box 20
		gatx1300gallontankerModel[18].setRotationPoint(-41F, -16F, -7.5F);

		gatx1300gallontankerModel[19].addBox(0F, 0F, 0F, 2, 11, 13, 0F); // Box 21
		gatx1300gallontankerModel[19].setRotationPoint(-41F, -18F, -6.5F);

		gatx1300gallontankerModel[20].addBox(0F, 0F, 0F, 2, 13, 11, 0F); // Box 22
		gatx1300gallontankerModel[20].setRotationPoint(-41F, -19F, -5.5F);

		gatx1300gallontankerModel[21].addBox(0F, 0F, 0F, 2, 15, 9, 0F); // Box 23
		gatx1300gallontankerModel[21].setRotationPoint(-41F, -20F, -4.5F);

		gatx1300gallontankerModel[22].addBox(0F, 0F, 0F, 2, 17, 5, 0F); // Box 24
		gatx1300gallontankerModel[22].setRotationPoint(-41F, -21F, -2.5F);

		gatx1300gallontankerModel[23].addBox(0F, 0F, 0F, 2, 19, 1, 0F); // Box 25
		gatx1300gallontankerModel[23].setRotationPoint(-41F, -22F, -0.5F);

		gatx1300gallontankerModel[24].addBox(0F, 0F, 0F, 1, 3, 11, 0F); // Box 48
		gatx1300gallontankerModel[24].setRotationPoint(-42F, -14F, -5.5F);

		gatx1300gallontankerModel[25].addBox(0F, 0F, 0F, 1, 7, 9, 0F); // Box 49
		gatx1300gallontankerModel[25].setRotationPoint(-42F, -16F, -4.5F);

		gatx1300gallontankerModel[26].addBox(0F, 0F, 0F, 1, 9, 7, 0F); // Box 50
		gatx1300gallontankerModel[26].setRotationPoint(-42F, -17F, -3.5F);

		gatx1300gallontankerModel[27].addBox(0F, 0F, 0F, 1, 11, 5, 0F); // Box 51
		gatx1300gallontankerModel[27].setRotationPoint(-42F, -18F, -2.5F);

		gatx1300gallontankerModel[28].addBox(0F, 0F, 0F, 1, 13, 1, 0F); // Box 52
		gatx1300gallontankerModel[28].setRotationPoint(-42F, -19F, -0.5F);

		gatx1300gallontankerModel[29].addBox(0F, 0F, 0F, 1, 3, 5, 0F); // Box 55
		gatx1300gallontankerModel[29].setRotationPoint(-43F, -14F, -2.5F);

		gatx1300gallontankerModel[30].addBox(0F, 0F, 0F, 1, 5, 3, 0F); // Box 56
		gatx1300gallontankerModel[30].setRotationPoint(-43F, -15F, -1.5F);

		gatx1300gallontankerModel[31].addBox(0F, 0F, 0F, 1, 7, 1, 0F); // Box 57
		gatx1300gallontankerModel[31].setRotationPoint(-43F, -16F, -0.5F);

		gatx1300gallontankerModel[32].addBox(0F, 0F, 0F, 1, 1, 14, 0F); // Box 59
		gatx1300gallontankerModel[32].setRotationPoint(48F, -13F, -7F);

		gatx1300gallontankerModel[33].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 60
		gatx1300gallontankerModel[33].setRotationPoint(48F, -11F, -8F);

		gatx1300gallontankerModel[34].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 61
		gatx1300gallontankerModel[34].setRotationPoint(48F, -11F, 7F);

		gatx1300gallontankerModel[35].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 62
		gatx1300gallontankerModel[35].setRotationPoint(48F, -12F, -7.5F);

		gatx1300gallontankerModel[36].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 63
		gatx1300gallontankerModel[36].setRotationPoint(48F, -12F, 6.5F);

		gatx1300gallontankerModel[37].addBox(0F, 0F, 0F, 66, 1, 1, 0F); // Box 64
		gatx1300gallontankerModel[37].setRotationPoint(-33F, -0.5F, 9.5F);

		gatx1300gallontankerModel[38].addBox(0F, 0F, 0F, 66, 1, 1, 0F); // Box 65
		gatx1300gallontankerModel[38].setRotationPoint(-33F, -0.5F, -10.5F);

		gatx1300gallontankerModel[39].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 66
		gatx1300gallontankerModel[39].setRotationPoint(-49F, -11F, -8F);

		gatx1300gallontankerModel[40].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 67
		gatx1300gallontankerModel[40].setRotationPoint(-49F, -12F, -7.5F);

		gatx1300gallontankerModel[41].addBox(0F, 0F, 0F, 1, 1, 14, 0F); // Box 68
		gatx1300gallontankerModel[41].setRotationPoint(-49F, -13F, -7F);

		gatx1300gallontankerModel[42].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 69
		gatx1300gallontankerModel[42].setRotationPoint(-49F, -12F, 6.5F);

		gatx1300gallontankerModel[43].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 70
		gatx1300gallontankerModel[43].setRotationPoint(-49F, -11F, 7F);

		gatx1300gallontankerModel[44].addBox(0F, 0F, 0F, 1, 9, 1, 0F); // Box 71
		gatx1300gallontankerModel[44].setRotationPoint(-7.5F, -30F, -7.5F);

		gatx1300gallontankerModel[45].addBox(0F, 0F, 0F, 1, 9, 1, 0F); // Box 72
		gatx1300gallontankerModel[45].setRotationPoint(-7.5F, -30F, 6.5F);

		gatx1300gallontankerModel[46].addBox(0F, 0F, 0F, 1, 9, 1, 0F); // Box 73
		gatx1300gallontankerModel[46].setRotationPoint(6.5F, -30F, 6.5F);

		gatx1300gallontankerModel[47].addBox(0F, 0F, 0F, 1, 9, 1, 0F); // Box 74
		gatx1300gallontankerModel[47].setRotationPoint(6.5F, -30F, -7.5F);

		gatx1300gallontankerModel[48].addBox(0F, 0F, 0F, 2, 1, 1, 0F); // Box 75
		gatx1300gallontankerModel[48].setRotationPoint(4.5F, -30F, 6.5F);

		gatx1300gallontankerModel[49].addBox(0F, 0F, 0F, 2, 1, 1, 0F); // Box 76
		gatx1300gallontankerModel[49].setRotationPoint(4.5F, -30F, -7.5F);

		gatx1300gallontankerModel[50].addBox(0F, 0F, 0F, 5, 1, 1, 0F); // Box 77
		gatx1300gallontankerModel[50].setRotationPoint(-6.5F, -30F, 6.5F);

		gatx1300gallontankerModel[51].addBox(0F, 0F, 0F, 5, 1, 1, 0F); // Box 78
		gatx1300gallontankerModel[51].setRotationPoint(-6.5F, -30F, -7.5F);

		gatx1300gallontankerModel[52].addBox(0F, 0F, 0F, 1, 5, 1, 0F); // Box 80
		gatx1300gallontankerModel[52].setRotationPoint(3.5F, -30F, -7.5F);

		gatx1300gallontankerModel[53].addBox(0F, 0F, 0F, 1, 5, 1, 0F); // Box 81
		gatx1300gallontankerModel[53].setRotationPoint(3.5F, -30F, 6.5F);

		gatx1300gallontankerModel[54].addBox(0F, 0F, 0F, 1, 5, 1, 0F); // Box 82
		gatx1300gallontankerModel[54].setRotationPoint(-1.5F, -30F, -7.5F);

		gatx1300gallontankerModel[55].addBox(0F, 0F, 0F, 1, 5, 1, 0F); // Box 83
		gatx1300gallontankerModel[55].setRotationPoint(-1.5F, -30F, 6.5F);

		gatx1300gallontankerModel[56].addBox(0F, 0F, 0F, 1, 1, 13, 0F); // Box 84
		gatx1300gallontankerModel[56].setRotationPoint(-7.5F, -30F, -6.5F);

		gatx1300gallontankerModel[57].addBox(0F, 0F, 0F, 1, 1, 13, 0F); // Box 85
		gatx1300gallontankerModel[57].setRotationPoint(6.5F, -30F, -6.5F);

		gatx1300gallontankerModel[58].addBox(0F, 0F, 0F, 6, 21, 0, 0F); // Box 91
		gatx1300gallontankerModel[58].setRotationPoint(-1.5F, -21F, -11F);

		gatx1300gallontankerModel[59].addBox(0F, 0F, 0F, 6, 5, 0, 0F); // Box 95
		gatx1300gallontankerModel[59].setRotationPoint(-1.5F, -21F, -11F);
		gatx1300gallontankerModel[59].rotateAngleX = 2.35619449F;

		gatx1300gallontankerModel[60].addBox(0F, 0F, 0F, 6, 21, 0, 0F); // Box 105
		gatx1300gallontankerModel[60].setRotationPoint(-1.5F, -21F, 11F);

		gatx1300gallontankerModel[61].addBox(0F, 0F, 0F, 6, 5, 0, 0F); // Box 106
		gatx1300gallontankerModel[61].setRotationPoint(-1.5F, -21F, 11F);
		gatx1300gallontankerModel[61].rotateAngleX = 3.92699082F;

		gatx1300gallontankerModel[62].addBox(0F, 0F, 0F, 3, 1, 1, 0F); // Box 118
		gatx1300gallontankerModel[62].setRotationPoint(-44F, -10F, 5.5F);

		gatx1300gallontankerModel[63].addBox(0F, 0F, 0F, 0, 4, 4, 0F); // Box 119
		gatx1300gallontankerModel[63].setRotationPoint(-44.05F, -11.5F, 4F);

		gatx1300gallontankerModel[64].addBox(0F, 0F, 0F, 13, 2, 2, 0F); // Box 120
		gatx1300gallontankerModel[64].setRotationPoint(35F, -1F, -1F);

		gatx1300gallontankerModel[65].addBox(0F, 0F, 0F, 13, 2, 2, 0F); // Box 121
		gatx1300gallontankerModel[65].setRotationPoint(-48F, -1F, -1F);

		gatx1300gallontankerModel[66].addBox(0F, 0F, 0F, 4, 2, 2, 0F); // Box 122
		gatx1300gallontankerModel[66].setRotationPoint(-52F, 0F, -1F);

		gatx1300gallontankerModel[67].addBox(0F, 0F, 0F, 3, 2, 4, 0F); // Box 123
		gatx1300gallontankerModel[67].setRotationPoint(-55F, 0F, -2F);

		gatx1300gallontankerModel[68].addBox(0F, 0F, 0F, 3, 2, 4, 0F); // Box 124
		gatx1300gallontankerModel[68].setRotationPoint(52F, 0F, -2F);

		gatx1300gallontankerModel[69].addBox(0F, 0F, 0F, 4, 2, 2, 0F); // Box 125
		gatx1300gallontankerModel[69].setRotationPoint(48F, 0F, -1F);

		gatx1300gallontankerModel[70].addBox(0F, 0F, 0F, 4, 1, 4, 0F); // Box 126
		gatx1300gallontankerModel[70].setRotationPoint(-1.5F, -28F, -2.5F);

		gatx1300gallontankerModel[71].addBox(0F, 0F, 0F, 2, 2, 2, 0F); // Box 127
		gatx1300gallontankerModel[71].setRotationPoint(-0.5F, -27F, -1.5F);

		gatx1300gallontankerModel[72].addBox(0F, 0F, 0F, 2, 3, 2, 0F); // Box 128
		gatx1300gallontankerModel[72].setRotationPoint(-0.5F, -2F, -1.5F);

		gatx1300gallontankerModel[73].addBox(0F, 0F, 0F, 2, 3, 17, 0F); // Box 130
		gatx1300gallontankerModel[73].setRotationPoint(39F, -14F, -8.5F);

		gatx1300gallontankerModel[74].addBox(0F, 0F, 0F, 2, 7, 15, 0F); // Box 131
		gatx1300gallontankerModel[74].setRotationPoint(39F, -16F, -7.5F);

		gatx1300gallontankerModel[75].addBox(0F, 0F, 0F, 2, 11, 13, 0F); // Box 132
		gatx1300gallontankerModel[75].setRotationPoint(39F, -18F, -6.5F);

		gatx1300gallontankerModel[76].addBox(0F, 0F, 0F, 2, 13, 11, 0F); // Box 133
		gatx1300gallontankerModel[76].setRotationPoint(39F, -19F, -5.5F);

		gatx1300gallontankerModel[77].addBox(0F, 0F, 0F, 2, 15, 9, 0F); // Box 134
		gatx1300gallontankerModel[77].setRotationPoint(39F, -20F, -4.5F);

		gatx1300gallontankerModel[78].addBox(0F, 0F, 0F, 2, 19, 1, 0F); // Box 135
		gatx1300gallontankerModel[78].setRotationPoint(39F, -22F, -0.5F);

		gatx1300gallontankerModel[79].addBox(0F, 0F, 0F, 2, 17, 5, 0F); // Box 136
		gatx1300gallontankerModel[79].setRotationPoint(39F, -21F, -2.5F);

		gatx1300gallontankerModel[80].addBox(0F, 0F, 0F, 1, 3, 11, 0F); // Box 137
		gatx1300gallontankerModel[80].setRotationPoint(41F, -14F, -5.5F);

		gatx1300gallontankerModel[81].addBox(0F, 0F, 0F, 1, 7, 9, 0F); // Box 138
		gatx1300gallontankerModel[81].setRotationPoint(41F, -16F, -4.5F);

		gatx1300gallontankerModel[82].addBox(0F, 0F, 0F, 1, 9, 7, 0F); // Box 139
		gatx1300gallontankerModel[82].setRotationPoint(41F, -17F, -3.5F);

		gatx1300gallontankerModel[83].addBox(0F, 0F, 0F, 1, 11, 5, 0F); // Box 140
		gatx1300gallontankerModel[83].setRotationPoint(41F, -18F, -2.5F);

		gatx1300gallontankerModel[84].addBox(0F, 0F, 0F, 1, 13, 1, 0F); // Box 141
		gatx1300gallontankerModel[84].setRotationPoint(41F, -19F, -0.5F);

		gatx1300gallontankerModel[85].addBox(0F, 0F, 0F, 1, 3, 5, 0F); // Box 142
		gatx1300gallontankerModel[85].setRotationPoint(42F, -14F, -2.5F);

		gatx1300gallontankerModel[86].addBox(0F, 0F, 0F, 1, 5, 3, 0F); // Box 143
		gatx1300gallontankerModel[86].setRotationPoint(42F, -15F, -1.5F);

		gatx1300gallontankerModel[87].addBox(0F, 0F, 0F, 1, 7, 1, 0F); // Box 144
		gatx1300gallontankerModel[87].setRotationPoint(42F, -16F, -0.5F);

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