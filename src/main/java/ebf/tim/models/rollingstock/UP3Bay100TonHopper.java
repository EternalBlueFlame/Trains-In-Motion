//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model:
// Created on:10.03.2017 - 19:24:13
// Last changed on: 10.03.2017 - 19:24:13
/**
 * @author Eternal Blue Flame
 */

package ebf.tim.models.rollingstock;

import ebf.tim.models.GroupedModelRender;
import ebf.tim.models.tmt.ModelRendererTurbo;
import ebf.tim.models.tmt.ModelBase;
import net.minecraft.entity.Entity;

public class UP3Bay100TonHopper extends ModelBase {
	private static final int textureX = 256;
	private static final int textureY = 128;

	private static final String cargo1 = GroupedModelRender.tagRenderModelCargo + "1";
	private static final String cargo2 = GroupedModelRender.tagRenderModelCargo + "2";
	private static final String cargo3 = GroupedModelRender.tagRenderModelCargo + "3";

	public UP3Bay100TonHopper() {
		up3bay100tonhopperModel = new ModelRendererTurbo[166];
		up3bay100tonhopperModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 0
		up3bay100tonhopperModel[1] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 1
		up3bay100tonhopperModel[2] = new ModelRendererTurbo(this, 9, 1, textureX, textureY); // Box 2
		up3bay100tonhopperModel[3] = new ModelRendererTurbo(this, 113, 1, textureX, textureY); // Box 3
		up3bay100tonhopperModel[4] = new ModelRendererTurbo(this, 121, 1, textureX, textureY); // Box 4
		up3bay100tonhopperModel[5] = new ModelRendererTurbo(this, 137, 1, textureX, textureY); // Box 17
		up3bay100tonhopperModel[6] = new ModelRendererTurbo(this, 177, 1, textureX, textureY); // Box 18
		up3bay100tonhopperModel[7] = new ModelRendererTurbo(this, 217, 1, textureX, textureY); // Box 19
		up3bay100tonhopperModel[8] = new ModelRendererTurbo(this, 113, 9, textureX, textureY); // Box 20
		up3bay100tonhopperModel[9] = new ModelRendererTurbo(this, 1, 25, textureX, textureY); // Box 21
		up3bay100tonhopperModel[10] = new ModelRendererTurbo(this, 41, 25, textureX, textureY); // Box 22
		up3bay100tonhopperModel[11] = new ModelRendererTurbo(this, 65, 25, textureX, textureY); // Box 23
		up3bay100tonhopperModel[12] = new ModelRendererTurbo(this, 137, 25, textureX, textureY); // Box 25
		up3bay100tonhopperModel[13] = new ModelRendererTurbo(this, 177, 25, textureX, textureY); // Box 26
		up3bay100tonhopperModel[14] = new ModelRendererTurbo(this, 217, 25, textureX, textureY); // Box 27
		up3bay100tonhopperModel[15] = new ModelRendererTurbo(this, 89, 25, textureX, textureY); // Box 28
		up3bay100tonhopperModel[16] = new ModelRendererTurbo(this, 113, 33, textureX, textureY); // Box 29
		up3bay100tonhopperModel[17] = new ModelRendererTurbo(this, 1, 49, textureX, textureY); // Box 30
		up3bay100tonhopperModel[18] = new ModelRendererTurbo(this, 41, 49, textureX, textureY); // Box 31
		up3bay100tonhopperModel[19] = new ModelRendererTurbo(this, 65, 49, textureX, textureY); // Box 32
		up3bay100tonhopperModel[20] = new ModelRendererTurbo(this, 137, 49, textureX, textureY); // Box 33
		up3bay100tonhopperModel[21] = new ModelRendererTurbo(this, 185, 49, textureX, textureY); // Box 34
		up3bay100tonhopperModel[22] = new ModelRendererTurbo(this, 97, 57, textureX, textureY); // Box 35
		up3bay100tonhopperModel[23] = new ModelRendererTurbo(this, 209, 57, textureX, textureY); // Box 36
		up3bay100tonhopperModel[24] = new ModelRendererTurbo(this, 1, 73, textureX, textureY); // Box 37
		up3bay100tonhopperModel[25] = new ModelRendererTurbo(this, 49, 73, textureX, textureY); // Box 38
		up3bay100tonhopperModel[26] = new ModelRendererTurbo(this, 121, 73, textureX, textureY); // Box 39
		up3bay100tonhopperModel[27] = new ModelRendererTurbo(this, 169, 73, textureX, textureY); // Box 40
		up3bay100tonhopperModel[28] = new ModelRendererTurbo(this, 1, 97, textureX, textureY); // Box 41
		up3bay100tonhopperModel[29] = new ModelRendererTurbo(this, 89, 97, textureX, textureY); // Box 42
		up3bay100tonhopperModel[30] = new ModelRendererTurbo(this, 1, 105, textureX, textureY); // Box 44
		up3bay100tonhopperModel[31] = new ModelRendererTurbo(this, 177, 97, textureX, textureY); // Box 45
		up3bay100tonhopperModel[32] = new ModelRendererTurbo(this, 89, 105, textureX, textureY); // Box 46
		up3bay100tonhopperModel[33] = new ModelRendererTurbo(this, 169, 105, textureX, textureY); // Box 47
		up3bay100tonhopperModel[34] = new ModelRendererTurbo(this, 1, 112, textureX, textureY); // Box 49
		up3bay100tonhopperModel[35] = new ModelRendererTurbo(this, 73, 112, textureX, textureY); // Box 50
		up3bay100tonhopperModel[36] = new ModelRendererTurbo(this, 153, 113, textureX, textureY); // Box 51
		up3bay100tonhopperModel[37] = new ModelRendererTurbo(this, 1, 120, textureX, textureY); // Box 52
		up3bay100tonhopperModel[38] = new ModelRendererTurbo(this, 137, 1, textureX, textureY); // Box 53
		up3bay100tonhopperModel[39] = new ModelRendererTurbo(this, 145, 1, textureX, textureY); // Box 54
		up3bay100tonhopperModel[40] = new ModelRendererTurbo(this, 161, 1, textureX, textureY); // Box 56
		up3bay100tonhopperModel[41] = new ModelRendererTurbo(this, 169, 1, textureX, textureY); // Box 57
		up3bay100tonhopperModel[42] = new ModelRendererTurbo(this, 177, 1, textureX, textureY); // Box 58
		up3bay100tonhopperModel[43] = new ModelRendererTurbo(this, 185, 1, textureX, textureY); // Box 59
		up3bay100tonhopperModel[44] = new ModelRendererTurbo(this, 201, 1, textureX, textureY); // Box 60
		up3bay100tonhopperModel[45] = new ModelRendererTurbo(this, 209, 1, textureX, textureY); // Box 61
		up3bay100tonhopperModel[46] = new ModelRendererTurbo(this, 217, 1, textureX, textureY); // Box 62
		up3bay100tonhopperModel[47] = new ModelRendererTurbo(this, 225, 1, textureX, textureY); // Box 63
		up3bay100tonhopperModel[48] = new ModelRendererTurbo(this, 241, 1, textureX, textureY); // Box 64
		up3bay100tonhopperModel[49] = new ModelRendererTurbo(this, 249, 1, textureX, textureY); // Box 65
		up3bay100tonhopperModel[50] = new ModelRendererTurbo(this, 1, 25, textureX, textureY); // Box 66
		up3bay100tonhopperModel[51] = new ModelRendererTurbo(this, 9, 25, textureX, textureY); // Box 67
		up3bay100tonhopperModel[52] = new ModelRendererTurbo(this, 25, 25, textureX, textureY); // Box 68
		up3bay100tonhopperModel[53] = new ModelRendererTurbo(this, 33, 25, textureX, textureY); // Box 69
		up3bay100tonhopperModel[54] = new ModelRendererTurbo(this, 41, 25, textureX, textureY); // Box 70
		up3bay100tonhopperModel[55] = new ModelRendererTurbo(this, 49, 25, textureX, textureY); // Box 71
		up3bay100tonhopperModel[56] = new ModelRendererTurbo(this, 65, 25, textureX, textureY); // Box 72
		up3bay100tonhopperModel[57] = new ModelRendererTurbo(this, 73, 25, textureX, textureY); // Box 73
		up3bay100tonhopperModel[58] = new ModelRendererTurbo(this, 161, 49, textureX, textureY); // Box 75
		up3bay100tonhopperModel[59] = new ModelRendererTurbo(this, 73, 74, textureX, textureY); // Box 76
		up3bay100tonhopperModel[60] = new ModelRendererTurbo(this, 89, 120, textureX, textureY); // Box 77
		up3bay100tonhopperModel[61] = new ModelRendererTurbo(this, 88, 124, textureX, textureY); // Box 78
		up3bay100tonhopperModel[62] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[63] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[64] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[65] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[66] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[67] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[68] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[69] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[70] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[71] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[72] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[73] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[74] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[75] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[76] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[77] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[78] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[79] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[80] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[81] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[82] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[83] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[84] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[85] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[86] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[87] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[88] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[89] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo1); // Cargo1
		up3bay100tonhopperModel[90] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[91] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[92] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[93] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[94] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[95] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[96] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[97] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[98] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[99] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[100] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[101] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[102] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[103] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[104] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[105] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[106] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[107] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[108] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[109] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[110] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[111] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[112] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[113] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[114] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[115] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[116] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[117] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[118] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[119] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[120] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[121] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[122] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[123] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[124] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[125] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo2); // Cargo2
		up3bay100tonhopperModel[126] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[127] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[128] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[129] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[130] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[131] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[132] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[133] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[134] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[135] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[136] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[137] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[138] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[139] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[140] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[141] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[142] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[143] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[144] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[145] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[146] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[147] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[148] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[149] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[150] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[151] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[152] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[153] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[154] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[155] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[156] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[157] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[158] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[159] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[160] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[161] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[162] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[163] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[164] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3
		up3bay100tonhopperModel[165] = new ModelRendererTurbo(this, 234, 116, textureX, textureY, cargo3); // Cargo3

		up3bay100tonhopperModel[0].addBox(0F, 0F, 0F, 44, 2, 20, 0F); // Box 0
		up3bay100tonhopperModel[0].setRotationPoint(-22F, 3F, -11F);

		up3bay100tonhopperModel[1].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 1
		up3bay100tonhopperModel[1].setRotationPoint(-22F, -7F, 8F);

		up3bay100tonhopperModel[2].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 2
		up3bay100tonhopperModel[2].setRotationPoint(21F, -7F, 8F);

		up3bay100tonhopperModel[3].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 3
		up3bay100tonhopperModel[3].setRotationPoint(21F, -7F, -11F);

		up3bay100tonhopperModel[4].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 4
		up3bay100tonhopperModel[4].setRotationPoint(-22F, -7F, -11F);

		up3bay100tonhopperModel[5].addBox(0F, 0F, 0F, 1, 3, 18, 0F); // Box 17
		up3bay100tonhopperModel[5].setRotationPoint(-22F, -7F, -10F);

		up3bay100tonhopperModel[6].addBox(0F, 0F, 0F, 1, 3, 18, 0F); // Box 18
		up3bay100tonhopperModel[6].setRotationPoint(21F, -7F, -10F);

		up3bay100tonhopperModel[7].addBox(0F, 0F, 0F, 1, 1, 18, 0F); // Box 19
		up3bay100tonhopperModel[7].setRotationPoint(20F, -4F, -10F);

		up3bay100tonhopperModel[8].addBox(0F, 0F, 0F, 1, 1, 18, 0F); // Box 20
		up3bay100tonhopperModel[8].setRotationPoint(19F, -3F, -10F);

		up3bay100tonhopperModel[9].addBox(0F, 0F, 0F, 1, 1, 18, 0F); // Box 21
		up3bay100tonhopperModel[9].setRotationPoint(18F, -2F, -10F);

		up3bay100tonhopperModel[10].addBox(0F, 0F, 0F, 1, 1, 18, 0F); // Box 22
		up3bay100tonhopperModel[10].setRotationPoint(17F, -1F, -10F);

		up3bay100tonhopperModel[11].addBox(0F, 0F, 0F, 1, 1, 16, 0F); // Box 23
		up3bay100tonhopperModel[11].setRotationPoint(16F, 0F, -9F);

		up3bay100tonhopperModel[12].addBox(0F, 0F, 0F, 1, 2, 16, 0F); // Box 25
		up3bay100tonhopperModel[12].setRotationPoint(15F, 1F, -9F);

		up3bay100tonhopperModel[13].addBox(0F, 0F, 0F, 1, 2, 16, 0F); // Box 26
		up3bay100tonhopperModel[13].setRotationPoint(-16F, 1F, -9F);

		up3bay100tonhopperModel[14].addBox(0F, 0F, 0F, 1, 1, 16, 0F); // Box 27
		up3bay100tonhopperModel[14].setRotationPoint(-17F, 0F, -9F);

		up3bay100tonhopperModel[15].addBox(0F, 0F, 0F, 1, 1, 18, 0F); // Box 28
		up3bay100tonhopperModel[15].setRotationPoint(-18F, -1F, -10F);

		up3bay100tonhopperModel[16].addBox(0F, 0F, 0F, 1, 1, 18, 0F); // Box 29
		up3bay100tonhopperModel[16].setRotationPoint(-19F, -2F, -10F);

		up3bay100tonhopperModel[17].addBox(0F, 0F, 0F, 1, 1, 18, 0F); // Box 30
		up3bay100tonhopperModel[17].setRotationPoint(-20F, -3F, -10F);

		up3bay100tonhopperModel[18].addBox(0F, 0F, 0F, 1, 1, 18, 0F); // Box 31
		up3bay100tonhopperModel[18].setRotationPoint(-21F, -4F, -10F);

		up3bay100tonhopperModel[19].addBox(0F, 0F, 0F, 6, 1, 16, 0F); // Box 32
		up3bay100tonhopperModel[19].setRotationPoint(-12F, 5F, -8F);

		up3bay100tonhopperModel[20].addBox(0F, 0F, 0F, 4, 1, 16, 0F); // Box 33
		up3bay100tonhopperModel[20].setRotationPoint(-11F, 6F, -8F);

		up3bay100tonhopperModel[21].addBox(0F, 0F, 0F, 2, 1, 16, 0F); // Box 34
		up3bay100tonhopperModel[21].setRotationPoint(-10F, 7F, -8F);

		up3bay100tonhopperModel[22].addBox(0F, 0F, 0F, 2, 1, 16, 0F); // Box 35
		up3bay100tonhopperModel[22].setRotationPoint(8F, 7F, -8F);

		up3bay100tonhopperModel[23].addBox(0F, 0F, 0F, 4, 1, 16, 0F); // Box 36
		up3bay100tonhopperModel[23].setRotationPoint(7F, 6F, -8F);

		up3bay100tonhopperModel[24].addBox(0F, 0F, 0F, 6, 1, 16, 0F); // Box 37
		up3bay100tonhopperModel[24].setRotationPoint(6F, 5F, -8F);

		up3bay100tonhopperModel[25].addBox(0F, 0F, 0F, 2, 1, 16, 0F); // Box 38
		up3bay100tonhopperModel[25].setRotationPoint(-1F, 7F, -8F);

		up3bay100tonhopperModel[26].addBox(0F, 0F, 0F, 4, 1, 16, 0F); // Box 39
		up3bay100tonhopperModel[26].setRotationPoint(-2F, 6F, -8F);

		up3bay100tonhopperModel[27].addBox(0F, 0F, 0F, 6, 1, 16, 0F); // Box 40
		up3bay100tonhopperModel[27].setRotationPoint(-3F, 5F, -8F);

		up3bay100tonhopperModel[28].addBox(0F, 0F, 0F, 42, 3, 1, 0F); // Box 41
		up3bay100tonhopperModel[28].setRotationPoint(-21F, -7F, 7F);

		up3bay100tonhopperModel[29].addBox(0F, 0F, 0F, 42, 3, 1, 0F); // Box 42
		up3bay100tonhopperModel[29].setRotationPoint(-21F, -7F, -10F);

		up3bay100tonhopperModel[30].addBox(0F, 0F, 0F, 40, 1, 1, 0F); // Box 44
		up3bay100tonhopperModel[30].setRotationPoint(-20F, -4F, -10F);

		up3bay100tonhopperModel[31].addBox(0F, 0F, 0F, 38, 1, 1, 0F); // Box 45
		up3bay100tonhopperModel[31].setRotationPoint(-19F, -3F, -10F);

		up3bay100tonhopperModel[32].addBox(0F, 0F, 0F, 36, 1, 1, 0F); // Box 46
		up3bay100tonhopperModel[32].setRotationPoint(-18F, -2F, -10F);

		up3bay100tonhopperModel[33].addBox(0F, 0F, 0F, 34, 4, 1, 0F); // Box 47
		up3bay100tonhopperModel[33].setRotationPoint(-17F, -1F, -10F);

		up3bay100tonhopperModel[34].addBox(0F, 0F, 0F, 34, 4, 1, 0F); // Box 49
		up3bay100tonhopperModel[34].setRotationPoint(-17F, -1F, 7F);

		up3bay100tonhopperModel[35].addBox(0F, 0F, 0F, 38, 1, 1, 0F); // Box 50
		up3bay100tonhopperModel[35].setRotationPoint(-19F, -3F, 7F);

		up3bay100tonhopperModel[36].addBox(0F, 0F, 0F, 36, 1, 1, 0F); // Box 51
		up3bay100tonhopperModel[36].setRotationPoint(-18F, -2F, 7F);

		up3bay100tonhopperModel[37].addBox(0F, 0F, 0F, 40, 1, 1, 0F); // Box 52
		up3bay100tonhopperModel[37].setRotationPoint(-20F, -4F, 7F);

		up3bay100tonhopperModel[38].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 53
		up3bay100tonhopperModel[38].setRotationPoint(-18F, -7F, 8F);

		up3bay100tonhopperModel[39].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 54
		up3bay100tonhopperModel[39].setRotationPoint(-14F, -7F, 8F);

		up3bay100tonhopperModel[40].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 56
		up3bay100tonhopperModel[40].setRotationPoint(-6F, -7F, 8F);

		up3bay100tonhopperModel[41].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 57
		up3bay100tonhopperModel[41].setRotationPoint(-10F, -7F, 8F);

		up3bay100tonhopperModel[42].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 58
		up3bay100tonhopperModel[42].setRotationPoint(18F, -7F, 8F);

		up3bay100tonhopperModel[43].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 59
		up3bay100tonhopperModel[43].setRotationPoint(14F, -7F, 8F);

		up3bay100tonhopperModel[44].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 60
		up3bay100tonhopperModel[44].setRotationPoint(10F, -7F, 8F);

		up3bay100tonhopperModel[45].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 61
		up3bay100tonhopperModel[45].setRotationPoint(6F, -7F, 8F);

		up3bay100tonhopperModel[46].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 62
		up3bay100tonhopperModel[46].setRotationPoint(2F, -7F, 8F);

		up3bay100tonhopperModel[47].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 63
		up3bay100tonhopperModel[47].setRotationPoint(-2F, -7F, 8F);

		up3bay100tonhopperModel[48].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 64
		up3bay100tonhopperModel[48].setRotationPoint(-18F, -7F, -11F);

		up3bay100tonhopperModel[49].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 65
		up3bay100tonhopperModel[49].setRotationPoint(-14F, -7F, -11F);

		up3bay100tonhopperModel[50].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 66
		up3bay100tonhopperModel[50].setRotationPoint(-10F, -7F, -11F);

		up3bay100tonhopperModel[51].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 67
		up3bay100tonhopperModel[51].setRotationPoint(-6F, -7F, -11F);

		up3bay100tonhopperModel[52].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 68
		up3bay100tonhopperModel[52].setRotationPoint(-2F, -7F, -11F);

		up3bay100tonhopperModel[53].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 69
		up3bay100tonhopperModel[53].setRotationPoint(2F, -7F, -11F);

		up3bay100tonhopperModel[54].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 70
		up3bay100tonhopperModel[54].setRotationPoint(6F, -7F, -11F);

		up3bay100tonhopperModel[55].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 71
		up3bay100tonhopperModel[55].setRotationPoint(10F, -7F, -11F);

		up3bay100tonhopperModel[56].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 72
		up3bay100tonhopperModel[56].setRotationPoint(14F, -7F, -11F);

		up3bay100tonhopperModel[57].addBox(0F, 0F, 0F, 1, 10, 1, 0F); // Box 73
		up3bay100tonhopperModel[57].setRotationPoint(18F, -7F, -11F);

		up3bay100tonhopperModel[58].addBox(0F, 0F, 0F, 1, 1, 18, 0F); // Box 75
		up3bay100tonhopperModel[58].setRotationPoint(22F, -8F, -10F);

		up3bay100tonhopperModel[59].addBox(0F, 0F, 0F, 1, 1, 18, 0F); // Box 76
		up3bay100tonhopperModel[59].setRotationPoint(-23F, -8F, -10F);

		up3bay100tonhopperModel[60].addBox(0F, 0F, 0F, 46, 1, 1, 0F); // Box 77
		up3bay100tonhopperModel[60].setRotationPoint(-23F, -8F, 8F);

		up3bay100tonhopperModel[61].addBox(0F, 0F, 0F, 46, 1, 1, 0F); // Box 78
		up3bay100tonhopperModel[61].setRotationPoint(-23F, -8F, -11F);

		up3bay100tonhopperModel[62].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[62].setRotationPoint(-14F, -1F, 3F);

		up3bay100tonhopperModel[63].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[63].setRotationPoint(-14F, -1F, -1F);

		up3bay100tonhopperModel[64].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[64].setRotationPoint(-14F, -1F, -5F);

		up3bay100tonhopperModel[65].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[65].setRotationPoint(-14F, -1F, -9F);

		up3bay100tonhopperModel[66].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[66].setRotationPoint(-10F, -1F, -9F);

		up3bay100tonhopperModel[67].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[67].setRotationPoint(-10F, -1F, 3F);

		up3bay100tonhopperModel[68].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[68].setRotationPoint(-10F, -1F, -1F);

		up3bay100tonhopperModel[69].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[69].setRotationPoint(-10F, -1F, -5F);

		up3bay100tonhopperModel[70].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[70].setRotationPoint(-6F, -1F, -9F);

		up3bay100tonhopperModel[71].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[71].setRotationPoint(-6F, -1F, 3F);

		up3bay100tonhopperModel[72].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[72].setRotationPoint(-6F, -1F, -1F);

		up3bay100tonhopperModel[73].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[73].setRotationPoint(-6F, -1F, -5F);

		up3bay100tonhopperModel[74].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[74].setRotationPoint(-2F, -1F, -9F);

		up3bay100tonhopperModel[75].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[75].setRotationPoint(-2F, -1F, 3F);

		up3bay100tonhopperModel[76].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[76].setRotationPoint(-2F, -1F, -1F);

		up3bay100tonhopperModel[77].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[77].setRotationPoint(-2F, -1F, -5F);

		up3bay100tonhopperModel[78].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[78].setRotationPoint(2F, -1F, -9F);

		up3bay100tonhopperModel[79].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[79].setRotationPoint(2F, -1F, 3F);

		up3bay100tonhopperModel[80].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[80].setRotationPoint(2F, -1F, -1F);

		up3bay100tonhopperModel[81].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[81].setRotationPoint(2F, -1F, -5F);

		up3bay100tonhopperModel[82].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[82].setRotationPoint(6F, -1F, -9F);

		up3bay100tonhopperModel[83].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[83].setRotationPoint(6F, -1F, 3F);

		up3bay100tonhopperModel[84].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[84].setRotationPoint(6F, -1F, -1F);

		up3bay100tonhopperModel[85].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[85].setRotationPoint(6F, -1F, -5F);

		up3bay100tonhopperModel[86].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[86].setRotationPoint(10F, -1F, -9F);

		up3bay100tonhopperModel[87].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[87].setRotationPoint(10F, -1F, 3F);

		up3bay100tonhopperModel[88].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[88].setRotationPoint(10F, -1F, -1F);

		up3bay100tonhopperModel[89].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo1
		up3bay100tonhopperModel[89].setRotationPoint(10F, -1F, -5F);

		up3bay100tonhopperModel[90].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[90].setRotationPoint(6F, -5F, 3F);

		up3bay100tonhopperModel[91].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[91].setRotationPoint(6F, -5F, -1F);

		up3bay100tonhopperModel[92].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[92].setRotationPoint(6F, -5F, -5F);

		up3bay100tonhopperModel[93].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[93].setRotationPoint(6F, -5F, -9F);

		up3bay100tonhopperModel[94].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[94].setRotationPoint(2F, -5F, -9F);

		up3bay100tonhopperModel[95].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[95].setRotationPoint(-2F, -5F, -9F);

		up3bay100tonhopperModel[96].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[96].setRotationPoint(-6F, -5F, -9F);

		up3bay100tonhopperModel[97].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[97].setRotationPoint(-10F, -5F, -9F);

		up3bay100tonhopperModel[98].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[98].setRotationPoint(-14F, -5F, -9F);

		up3bay100tonhopperModel[99].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[99].setRotationPoint(-18F, -5F, -9F);

		up3bay100tonhopperModel[100].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[100].setRotationPoint(-18F, -5F, -5F);

		up3bay100tonhopperModel[101].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[101].setRotationPoint(-14F, -5F, -5F);

		up3bay100tonhopperModel[102].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[102].setRotationPoint(-10F, -5F, -5F);

		up3bay100tonhopperModel[103].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[103].setRotationPoint(-6F, -5F, -5F);

		up3bay100tonhopperModel[104].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[104].setRotationPoint(-2F, -5F, -5F);

		up3bay100tonhopperModel[105].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[105].setRotationPoint(2F, -5F, -5F);

		up3bay100tonhopperModel[106].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[106].setRotationPoint(2F, -5F, -1F);

		up3bay100tonhopperModel[107].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[107].setRotationPoint(-2F, -5F, -1F);

		up3bay100tonhopperModel[108].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[108].setRotationPoint(-6F, -5F, -1F);

		up3bay100tonhopperModel[109].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[109].setRotationPoint(-10F, -5F, -1F);

		up3bay100tonhopperModel[110].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[110].setRotationPoint(-14F, -5F, -1F);

		up3bay100tonhopperModel[111].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[111].setRotationPoint(-18F, -5F, -1F);

		up3bay100tonhopperModel[112].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[112].setRotationPoint(-18F, -5F, 3F);

		up3bay100tonhopperModel[113].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[113].setRotationPoint(-10F, -5F, 3F);

		up3bay100tonhopperModel[114].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[114].setRotationPoint(-14F, -5F, 3F);

		up3bay100tonhopperModel[115].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[115].setRotationPoint(-6F, -5F, 3F);

		up3bay100tonhopperModel[116].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[116].setRotationPoint(2F, -5F, 3F);

		up3bay100tonhopperModel[117].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[117].setRotationPoint(-2F, -5F, 3F);

		up3bay100tonhopperModel[118].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[118].setRotationPoint(14F, -5F, -9F);

		up3bay100tonhopperModel[119].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[119].setRotationPoint(14F, -5F, -5F);

		up3bay100tonhopperModel[120].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[120].setRotationPoint(14F, -5F, -1F);

		up3bay100tonhopperModel[121].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[121].setRotationPoint(14F, -5F, 3F);

		up3bay100tonhopperModel[122].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[122].setRotationPoint(10F, -5F, 3F);

		up3bay100tonhopperModel[123].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[123].setRotationPoint(10F, -5F, -1F);

		up3bay100tonhopperModel[124].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[124].setRotationPoint(10F, -5F, -5F);

		up3bay100tonhopperModel[125].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo2
		up3bay100tonhopperModel[125].setRotationPoint(10F, -5F, -9F);

		up3bay100tonhopperModel[126].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[126].setRotationPoint(8F, -9F, -9F);

		up3bay100tonhopperModel[127].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[127].setRotationPoint(12F, -9F, -9F);

		up3bay100tonhopperModel[128].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[128].setRotationPoint(12F, -9F, -5F);

		up3bay100tonhopperModel[129].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[129].setRotationPoint(12F, -9F, -1F);

		up3bay100tonhopperModel[130].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[130].setRotationPoint(8F, -9F, 3F);

		up3bay100tonhopperModel[131].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[131].setRotationPoint(4F, -9F, 3F);

		up3bay100tonhopperModel[132].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[132].setRotationPoint(-4F, -9F, 3F);

		up3bay100tonhopperModel[133].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[133].setRotationPoint(-16F, -9F, 3F);

		up3bay100tonhopperModel[134].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[134].setRotationPoint(-4F, -9F, -1F);

		up3bay100tonhopperModel[135].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[135].setRotationPoint(4F, -9F, -1F);

		up3bay100tonhopperModel[136].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[136].setRotationPoint(8F, -9F, -1F);

		up3bay100tonhopperModel[137].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[137].setRotationPoint(8F, -9F, -5F);

		up3bay100tonhopperModel[138].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[138].setRotationPoint(4F, -9F, -5F);

		up3bay100tonhopperModel[139].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[139].setRotationPoint(0F, -9F, -5F);

		up3bay100tonhopperModel[140].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[140].setRotationPoint(-12F, -9F, -5F);

		up3bay100tonhopperModel[141].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[141].setRotationPoint(-16F, -9F, -5F);

		up3bay100tonhopperModel[142].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[142].setRotationPoint(-20F, -9F, -9F);

		up3bay100tonhopperModel[143].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[143].setRotationPoint(-16F, -9F, -9F);

		up3bay100tonhopperModel[144].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[144].setRotationPoint(-12F, -9F, -9F);

		up3bay100tonhopperModel[145].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[145].setRotationPoint(-4F, -9F, -9F);

		up3bay100tonhopperModel[146].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[146].setRotationPoint(0F, -9F, -9F);

		up3bay100tonhopperModel[147].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[147].setRotationPoint(4F, -9F, -9F);

		up3bay100tonhopperModel[148].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[148].setRotationPoint(-8F, -9F, -9F);

		up3bay100tonhopperModel[149].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[149].setRotationPoint(-4F, -9F, -5F);

		up3bay100tonhopperModel[150].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[150].setRotationPoint(-8F, -9F, -5F);

		up3bay100tonhopperModel[151].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[151].setRotationPoint(0F, -9F, -1F);

		up3bay100tonhopperModel[152].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[152].setRotationPoint(0F, -9F, 3F);

		up3bay100tonhopperModel[153].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[153].setRotationPoint(12F, -9F, 3F);

		up3bay100tonhopperModel[154].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[154].setRotationPoint(-8F, -9F, 3F);

		up3bay100tonhopperModel[155].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[155].setRotationPoint(-8F, -9F, -1F);

		up3bay100tonhopperModel[156].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[156].setRotationPoint(-12F, -9F, -1F);

		up3bay100tonhopperModel[157].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[157].setRotationPoint(-12F, -9F, 3F);

		up3bay100tonhopperModel[158].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[158].setRotationPoint(-20F, -9F, 3F);

		up3bay100tonhopperModel[159].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[159].setRotationPoint(-20F, -9F, -1F);

		up3bay100tonhopperModel[160].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[160].setRotationPoint(-20F, -9F, -5F);

		up3bay100tonhopperModel[161].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[161].setRotationPoint(-16F, -9F, -1F);

		up3bay100tonhopperModel[162].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[162].setRotationPoint(16F, -9F, -9F);

		up3bay100tonhopperModel[163].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[163].setRotationPoint(16F, -9F, -5F);

		up3bay100tonhopperModel[164].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[164].setRotationPoint(16F, -9F, -1F);

		up3bay100tonhopperModel[165].addBox(0F, 0F, 0F, 4, 4, 4, 0F); // Cargo3
		up3bay100tonhopperModel[165].setRotationPoint(16F, -9F, 3F);

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		for (int i = 0; i < 166; i++) {
			up3bay100tonhopperModel[i].render(f5);
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
	}

	private ModelRendererTurbo up3bay100tonhopperModel[];
}