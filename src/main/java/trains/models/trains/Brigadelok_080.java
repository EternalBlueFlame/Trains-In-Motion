//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 0-8-0 Brigadelok
// Model Creator: Lunar Tales
// Created on:12.01.2017 - 18:22:50
// Last changed on: 12.01.2017 - 18:22:50

package trains.models.trains;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import trains.models.tmt.ModelRendererTurbo;

public class Brigadelok_080 extends ModelBase
{
	int textureX = 256;
	int textureY = 256;

	public Brigadelok_080()
	{
		brigadelok_080Model = new ModelRendererTurbo[106];
		brigadelok_080Model[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 0
		brigadelok_080Model[1] = new ModelRendererTurbo(this, 73, 1, textureX, textureY); // Box 1
		brigadelok_080Model[2] = new ModelRendererTurbo(this, 153, 1, textureX, textureY); // Box 2
		brigadelok_080Model[3] = new ModelRendererTurbo(this, 121, 41, textureX, textureY); // Box 3
		brigadelok_080Model[4] = new ModelRendererTurbo(this, 41, 1, textureX, textureY); // Box 4
		brigadelok_080Model[5] = new ModelRendererTurbo(this, 1, 49, textureX, textureY); // Box 5
		brigadelok_080Model[6] = new ModelRendererTurbo(this, 177, 41, textureX, textureY); // Box 6
		brigadelok_080Model[7] = new ModelRendererTurbo(this, 121, 1, textureX, textureY); // Box 7
		brigadelok_080Model[8] = new ModelRendererTurbo(this, 1, 81, textureX, textureY); // Box 8
		brigadelok_080Model[9] = new ModelRendererTurbo(this, 209, 1, textureX, textureY); // Box 9
		brigadelok_080Model[10] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 10
		brigadelok_080Model[11] = new ModelRendererTurbo(this, 57, 49, textureX, textureY); // Box 11
		brigadelok_080Model[12] = new ModelRendererTurbo(this, 121, 9, textureX, textureY); // Box 12
		brigadelok_080Model[13] = new ModelRendererTurbo(this, 153, 9, textureX, textureY); // Box 13
		brigadelok_080Model[14] = new ModelRendererTurbo(this, 41, 17, textureX, textureY); // Box 14
		brigadelok_080Model[15] = new ModelRendererTurbo(this, 241, 33, textureX, textureY); // Box 15
		brigadelok_080Model[16] = new ModelRendererTurbo(this, 177, 41, textureX, textureY); // Box 16
		brigadelok_080Model[17] = new ModelRendererTurbo(this, 209, 73, textureX, textureY); // Box 17
		brigadelok_080Model[18] = new ModelRendererTurbo(this, 1, 49, textureX, textureY); // Box 18
		brigadelok_080Model[19] = new ModelRendererTurbo(this, 49, 81, textureX, textureY); // Box 19
		brigadelok_080Model[20] = new ModelRendererTurbo(this, 121, 17, textureX, textureY); // Box 20
		brigadelok_080Model[21] = new ModelRendererTurbo(this, 41, 25, textureX, textureY); // Box 21
		brigadelok_080Model[22] = new ModelRendererTurbo(this, 113, 81, textureX, textureY); // Box 22
		brigadelok_080Model[23] = new ModelRendererTurbo(this, 161, 81, textureX, textureY); // Box 23
		brigadelok_080Model[24] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 24
		brigadelok_080Model[25] = new ModelRendererTurbo(this, 17, 1, textureX, textureY); // Box 25
		brigadelok_080Model[26] = new ModelRendererTurbo(this, 41, 1, textureX, textureY); // Box 26
		brigadelok_080Model[27] = new ModelRendererTurbo(this, 89, 1, textureX, textureY); // Box 27
		brigadelok_080Model[28] = new ModelRendererTurbo(this, 161, 1, textureX, textureY); // Box 28
		brigadelok_080Model[29] = new ModelRendererTurbo(this, 73, 17, textureX, textureY); // Box 29
		brigadelok_080Model[30] = new ModelRendererTurbo(this, 89, 17, textureX, textureY); // Box 31
		brigadelok_080Model[31] = new ModelRendererTurbo(this, 161, 17, textureX, textureY); // Box 32
		brigadelok_080Model[32] = new ModelRendererTurbo(this, 121, 25, textureX, textureY); // Box 33
		brigadelok_080Model[33] = new ModelRendererTurbo(this, 89, 25, textureX, textureY); // Box 34
		brigadelok_080Model[34] = new ModelRendererTurbo(this, 17, 25, textureX, textureY); // Box 35
		brigadelok_080Model[35] = new ModelRendererTurbo(this, 161, 25, textureX, textureY); // Box 36
		brigadelok_080Model[36] = new ModelRendererTurbo(this, 193, 41, textureX, textureY); // Box 37
		brigadelok_080Model[37] = new ModelRendererTurbo(this, 1, 49, textureX, textureY); // Box 38
		brigadelok_080Model[38] = new ModelRendererTurbo(this, 113, 49, textureX, textureY, "axel"); // Axel
		brigadelok_080Model[39] = new ModelRendererTurbo(this, 113, 57, textureX, textureY, "axel"); // Axel 2
		brigadelok_080Model[40] = new ModelRendererTurbo(this, 57, 73, textureX, textureY, "axel"); // Axel 3
		brigadelok_080Model[41] = new ModelRendererTurbo(this, 89, 73, textureX, textureY, "axel"); // Axel 4
		brigadelok_080Model[42] = new ModelRendererTurbo(this, 153, 97, textureX, textureY); // Box 43
		brigadelok_080Model[43] = new ModelRendererTurbo(this, 121, 105, textureX, textureY); // Box 44
		brigadelok_080Model[44] = new ModelRendererTurbo(this, 185, 105, textureX, textureY); // Box 45
		brigadelok_080Model[45] = new ModelRendererTurbo(this, 1, 113, textureX, textureY); // Box 46
		brigadelok_080Model[46] = new ModelRendererTurbo(this, 33, 121, textureX, textureY); // Box 47
		brigadelok_080Model[47] = new ModelRendererTurbo(this, 121, 65, textureX, textureY); // Box 48
		brigadelok_080Model[48] = new ModelRendererTurbo(this, 233, 65, textureX, textureY); // Box 49
		brigadelok_080Model[49] = new ModelRendererTurbo(this, 113, 105, textureX, textureY); // Box 50
		brigadelok_080Model[50] = new ModelRendererTurbo(this, 185, 97, textureX, textureY); // Box 51
		brigadelok_080Model[51] = new ModelRendererTurbo(this, 153, 105, textureX, textureY); // Box 53
		brigadelok_080Model[52] = new ModelRendererTurbo(this, 57, 49, textureX, textureY); // Box 55
		brigadelok_080Model[53] = new ModelRendererTurbo(this, 193, 49, textureX, textureY); // Box 56
		brigadelok_080Model[54] = new ModelRendererTurbo(this, 1, 81, textureX, textureY); // Box 57
		brigadelok_080Model[55] = new ModelRendererTurbo(this, 65, 81, textureX, textureY); // Box 58
		brigadelok_080Model[56] = new ModelRendererTurbo(this, 113, 81, textureX, textureY); // Box 59
		brigadelok_080Model[57] = new ModelRendererTurbo(this, 217, 105, textureX, textureY); // Box 60
		brigadelok_080Model[58] = new ModelRendererTurbo(this, 17, 49, textureX, textureY); // Box 61
		brigadelok_080Model[59] = new ModelRendererTurbo(this, 241, 105, textureX, textureY); // Box 62
		brigadelok_080Model[60] = new ModelRendererTurbo(this, 1, 113, textureX, textureY); // Box 63
		brigadelok_080Model[61] = new ModelRendererTurbo(this, 33, 121, textureX, textureY); // Box 64
		brigadelok_080Model[62] = new ModelRendererTurbo(this, 81, 121, textureX, textureY); // Box 65
		brigadelok_080Model[63] = new ModelRendererTurbo(this, 9, 121, textureX, textureY); // Box 66
		brigadelok_080Model[64] = new ModelRendererTurbo(this, 169, 1, textureX, textureY); // Box 67
		brigadelok_080Model[65] = new ModelRendererTurbo(this, 249, 1, textureX, textureY); // Box 68
		brigadelok_080Model[66] = new ModelRendererTurbo(this, 169, 25, textureX, textureY); // Box 69
		brigadelok_080Model[67] = new ModelRendererTurbo(this, 25, 9, textureX, textureY); // Box 71
		brigadelok_080Model[68] = new ModelRendererTurbo(this, 81, 17, textureX, textureY); // Box 73
		brigadelok_080Model[69] = new ModelRendererTurbo(this, 193, 65, textureX, textureY); // Box 74
		brigadelok_080Model[70] = new ModelRendererTurbo(this, 1, 97, textureX, textureY); // Box 75
		brigadelok_080Model[71] = new ModelRendererTurbo(this, 33, 113, textureX, textureY); // Box 76
		brigadelok_080Model[72] = new ModelRendererTurbo(this, 153, 113, textureX, textureY); // Box 77
		brigadelok_080Model[73] = new ModelRendererTurbo(this, 97, 1, textureX, textureY); // Box 78
		brigadelok_080Model[74] = new ModelRendererTurbo(this, 153, 25, textureX, textureY); // Box 79
		brigadelok_080Model[75] = new ModelRendererTurbo(this, 185, 41, textureX, textureY); // Box 81
		brigadelok_080Model[76] = new ModelRendererTurbo(this, 185, 113, textureX, textureY); // Box 82
		brigadelok_080Model[77] = new ModelRendererTurbo(this, 97, 121, textureX, textureY); // Box 83
		brigadelok_080Model[78] = new ModelRendererTurbo(this, 113, 121, textureX, textureY); // Box 84
		brigadelok_080Model[79] = new ModelRendererTurbo(this, 129, 121, textureX, textureY); // Box 85
		brigadelok_080Model[80] = new ModelRendererTurbo(this, 97, 9, textureX, textureY); // Box 86
		brigadelok_080Model[81] = new ModelRendererTurbo(this, 225, 113, textureX, textureY); // Box 87
		brigadelok_080Model[82] = new ModelRendererTurbo(this, 97, 129, textureX, textureY); // Box 88
		brigadelok_080Model[83] = new ModelRendererTurbo(this, 241, 121, textureX, textureY); // Box 89
		brigadelok_080Model[84] = new ModelRendererTurbo(this, 249, 9, textureX, textureY); // Box 90
		brigadelok_080Model[85] = new ModelRendererTurbo(this, 97, 17, textureX, textureY); // Box 91
		brigadelok_080Model[86] = new ModelRendererTurbo(this, 89, 121, textureX, textureY, "simple"); // WheelConnectorPiston
		brigadelok_080Model[87] = new ModelRendererTurbo(this, 161, 129, textureX, textureY, "advanced"); // PistonValveConnector
		brigadelok_080Model[88] = new ModelRendererTurbo(this, 233, 129, textureX, textureY); // UpperPistonValveConnector
		brigadelok_080Model[89] = new ModelRendererTurbo(this, 129, 137, textureX, textureY, "simple"); // UpperPiston
		brigadelok_080Model[90] = new ModelRendererTurbo(this, 177, 17, textureX, textureY, "simple"); // UpperPistonArm
		brigadelok_080Model[91] = new ModelRendererTurbo(this, 129, 137, textureX, textureY, "simple"); // WheelConnectorPiston
		brigadelok_080Model[92] = new ModelRendererTurbo(this, 185, 137, textureX, textureY, "advanced"); // PistonValveConnector
		brigadelok_080Model[93] = new ModelRendererTurbo(this, 209, 137, textureX, textureY); // UpperPistonValveConnector
		brigadelok_080Model[94] = new ModelRendererTurbo(this, 249, 17, textureX, textureY, "simple"); // UpperPistonArm
		brigadelok_080Model[95] = new ModelRendererTurbo(this, 153, 137, textureX, textureY, "simple"); // UpperPiston
		brigadelok_080Model[96] = new ModelRendererTurbo(this, 25, 25, textureX, textureY); // Box 102
		brigadelok_080Model[97] = new ModelRendererTurbo(this, 81, 25, textureX, textureY); // Box 103
		brigadelok_080Model[98] = new ModelRendererTurbo(this, 113, 129, textureX, textureY, "wheel"); // Wheel
		brigadelok_080Model[99] = new ModelRendererTurbo(this, 1, 145, textureX, textureY, "wheel"); // Wheel
		brigadelok_080Model[100] = new ModelRendererTurbo(this, 17, 145, textureX, textureY, "wheel"); // Wheel
		brigadelok_080Model[101] = new ModelRendererTurbo(this, 97, 145, textureX, textureY, "wheel"); // Wheel
		brigadelok_080Model[102] = new ModelRendererTurbo(this, 113, 145, textureX, textureY, "wheel"); // Wheel
		brigadelok_080Model[103] = new ModelRendererTurbo(this, 225, 145, textureX, textureY, "wheel"); // Wheel
		brigadelok_080Model[104] = new ModelRendererTurbo(this, 241, 145, textureX, textureY, "wheel"); // Wheel
		brigadelok_080Model[105] = new ModelRendererTurbo(this, 9, 153, textureX, textureY, "wheel"); // Wheel

		brigadelok_080Model[0].addBox(0F, 0F, 0F, 3, 10, 32, 0F); // Box 0
		brigadelok_080Model[0].setRotationPoint(-1.5F, -8F, -8F);

		brigadelok_080Model[1].addBox(0F, 0F, 0F, 6, 8, 32, 0F); // Box 1
		brigadelok_080Model[1].setRotationPoint(-3F, -7F, -8F);

		brigadelok_080Model[2].addBox(0F, 0F, 0F, 8, 6, 32, 0F); // Box 2
		brigadelok_080Model[2].setRotationPoint(-4F, -6F, -8F);

		brigadelok_080Model[3].addBox(0F, 0F, 0F, 10, 3, 32, 0F); // Box 3
		brigadelok_080Model[3].setRotationPoint(-5F, -4.5F, -8F);

		brigadelok_080Model[4].addBox(0F, 0F, 0F, 18, 6, 8, 0F); // Box 4
		brigadelok_080Model[4].setRotationPoint(-9F, -4.5F, 14F);

		brigadelok_080Model[5].addBox(0F, 0F, 0F, 11, 1, 30, 0F); // Box 5
		brigadelok_080Model[5].setRotationPoint(-5.5F, 1.5F, -8F);

		brigadelok_080Model[6].addBox(0F, 0F, 0F, 9, 1, 30, 0F); // Box 6
		brigadelok_080Model[6].setRotationPoint(-4.5F, 2.5F, -8F);

		brigadelok_080Model[7].addBox(0F, 0F, 0F, 20, 2, 1, 0F); // Box 7
		brigadelok_080Model[7].setRotationPoint(-10F, 3F, 23F);

		brigadelok_080Model[8].addBox(0F, 0F, 0F, 18, 7, 22, 0F); // Box 8
		brigadelok_080Model[8].setRotationPoint(-9F, -5.5F, -8F);

		brigadelok_080Model[9].addBox(0F, 0F, 0F, 18, 25, 1, 0F); // Box 9
		brigadelok_080Model[9].setRotationPoint(-9F, -22F, -9F);

		brigadelok_080Model[10].addBox(0F, 0F, 0F, 1, 15, 9, 0F); // Box 10
		brigadelok_080Model[10].setRotationPoint(-9F, -9F, -22F);

		brigadelok_080Model[11].addBox(0F, 0F, 0F, 18, 1, 15, 0F); // Box 11
		brigadelok_080Model[11].setRotationPoint(-9F, 6F, -28F);

		brigadelok_080Model[12].addBox(0F, 0F, 0F, 12, 3, 2, 0F); // Box 12
		brigadelok_080Model[12].setRotationPoint(-6F, 4F, 21.5F);

		brigadelok_080Model[13].addBox(0F, 0F, 0F, 14, 3, 1, 0F); // Box 13
		brigadelok_080Model[13].setRotationPoint(-7F, 5.5F, 23.5F);

		brigadelok_080Model[14].addBox(0F, 0F, 0F, 16, 2, 1, 0F); // Box 14
		brigadelok_080Model[14].setRotationPoint(-8F, 7F, 22.5F);

		brigadelok_080Model[15].addBox(0F, 0F, 0F, 1, 24, 4, 0F); // Box 15
		brigadelok_080Model[15].setRotationPoint(-9F, -21F, -13F);

		brigadelok_080Model[16].addBox(0F, 0F, 0F, 1, 24, 4, 0F); // Box 16
		brigadelok_080Model[16].setRotationPoint(8F, -21F, -13F);

		brigadelok_080Model[17].addBox(0F, 0F, 0F, 18, 29, 1, 0F); // Box 17
		brigadelok_080Model[17].setRotationPoint(-9F, -22F, -29F);

		brigadelok_080Model[18].addBox(0F, 0F, 0F, 1, 15, 9, 0F); // Box 18
		brigadelok_080Model[18].setRotationPoint(8F, -9F, -22F);

		brigadelok_080Model[19].addBox(0F, 0F, 0F, 12, 3, 34, 0F); // Box 19
		brigadelok_080Model[19].setRotationPoint(-6F, 3F, -12F);

		brigadelok_080Model[20].addBox(0F, 0F, 0F, 18, 4, 1, 0F); // Box 20
		brigadelok_080Model[20].setRotationPoint(-9F, 3F, -13F);

		brigadelok_080Model[21].addBox(0F, 0F, 0F, 16, 1, 4, 0F); // Box 21
		brigadelok_080Model[21].setRotationPoint(-8F, 2F, -13F);

		brigadelok_080Model[22].addBox(0F, 0F, 0F, 12, 1, 16, 0F); // Box 22
		brigadelok_080Model[22].setRotationPoint(-6F, 7F, -28F);

		brigadelok_080Model[23].addBox(0F, 0F, 0F, 18, 5, 3, 0F); // Box 23
		brigadelok_080Model[23].setRotationPoint(-9F, 3.5F, 17F);

		brigadelok_080Model[24].addBox(0F, 0F, 0F, 2, 2, 1, 0F); // Box 24
		brigadelok_080Model[24].setRotationPoint(-1F, 6F, 24.5F);

		brigadelok_080Model[25].addBox(0F, 0F, 0F, 3, 3, 1, 0F); // Box 25
		brigadelok_080Model[25].setRotationPoint(-1.5F, 5.5F, 25.5F);

		brigadelok_080Model[26].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 26
		brigadelok_080Model[26].setRotationPoint(-0.5F, 4.5F, 26F);

		brigadelok_080Model[27].addBox(0F, 0F, 0F, 1, 2, 3, 0F); // Box 27
		brigadelok_080Model[27].setRotationPoint(-6F, 6F, 14F);

		brigadelok_080Model[28].addBox(0F, 0F, 0F, 1, 2, 5, 0F); // Box 28
		brigadelok_080Model[28].setRotationPoint(-6F, 6F, 8F);

		brigadelok_080Model[29].addBox(0F, 0F, 0F, 1, 2, 5, 0F); // Box 29
		brigadelok_080Model[29].setRotationPoint(-6F, 6F, 2F);

		brigadelok_080Model[30].addBox(0F, 0F, 0F, 1, 2, 5, 0F); // Box 31
		brigadelok_080Model[30].setRotationPoint(-6F, 6F, -4F);

		brigadelok_080Model[31].addBox(0F, 0F, 0F, 1, 2, 3, 0F); // Box 32
		brigadelok_080Model[31].setRotationPoint(-6F, 6F, -8F);

		brigadelok_080Model[32].addBox(0F, 0F, 0F, 12, 2, 4, 0F); // Box 33
		brigadelok_080Model[32].setRotationPoint(-6F, 6F, -12F);

		brigadelok_080Model[33].addBox(0F, 0F, 0F, 1, 2, 3, 0F); // Box 34
		brigadelok_080Model[33].setRotationPoint(5F, 6F, -8F);

		brigadelok_080Model[34].addBox(0F, 0F, 0F, 1, 2, 5, 0F); // Box 35
		brigadelok_080Model[34].setRotationPoint(5F, 6F, -4F);

		brigadelok_080Model[35].addBox(0F, 0F, 0F, 1, 2, 5, 0F); // Box 36
		brigadelok_080Model[35].setRotationPoint(5F, 6F, 2F);

		brigadelok_080Model[36].addBox(0F, 0F, 0F, 1, 2, 5, 0F); // Box 37
		brigadelok_080Model[36].setRotationPoint(5F, 6F, 8F);

		brigadelok_080Model[37].addBox(0F, 0F, 0F, 1, 2, 3, 0F); // Box 38
		brigadelok_080Model[37].setRotationPoint(5F, 6F, 14F);

		brigadelok_080Model[38].addBox(0F, -0.5F, -0.5F, 13, 1, 1, 0F); // Axel
		brigadelok_080Model[38].setRotationPoint(-6.5F, 6.5F, -4.5F);

		brigadelok_080Model[39].addBox(0F, -0.5F, -0.5F, 13, 1, 1, 0F); // Axel 2
		brigadelok_080Model[39].setRotationPoint(-6.5F, 6.5F, 1.5F);

		brigadelok_080Model[40].addBox(0F, -0.5F, -0.5F, 13, 1, 1, 0F); // Axel 3
		brigadelok_080Model[40].setRotationPoint(-6.5F, 6.5F, 7.5F);

		brigadelok_080Model[41].addBox(0F, -0.5F, -0.5F, 13, 1, 1, 0F); // Axel 4
		brigadelok_080Model[41].setRotationPoint(-6.5F, 6.5F, 13.5F);

		brigadelok_080Model[42].addBox(0F, 0F, 0F, 2, 1, 23, 0F); // Box 43
		brigadelok_080Model[42].setRotationPoint(-10F, -22F, -30F);

		brigadelok_080Model[43].addBox(0F, 0F, 0F, 2, 1, 23, 0F); // Box 44
		brigadelok_080Model[43].setRotationPoint(8F, -22F, -30F);

		brigadelok_080Model[44].addBox(0F, 0F, 0F, 3, 1, 23, 0F); // Box 45
		brigadelok_080Model[44].setRotationPoint(-8F, -23F, -30F);

		brigadelok_080Model[45].addBox(0F, 0F, 0F, 3, 1, 23, 0F); // Box 46
		brigadelok_080Model[45].setRotationPoint(5F, -23F, -30F);

		brigadelok_080Model[46].addBox(0F, 0F, 0F, 10, 1, 23, 0F); // Box 47
		brigadelok_080Model[46].setRotationPoint(-5F, -24F, -30F);

		brigadelok_080Model[47].addBox(0F, 0F, 0F, 6, 2, 5, 0F); // Box 48
		brigadelok_080Model[47].setRotationPoint(-3F, -26F, -23F);

		brigadelok_080Model[48].addBox(0F, 0F, 0F, 10, 1, 1, 0F); // Box 49
		brigadelok_080Model[48].setRotationPoint(-5F, -23F, -9F);

		brigadelok_080Model[49].addBox(0F, 0F, 0F, 7, 1, 7, 0F); // Box 50
		brigadelok_080Model[49].setRotationPoint(-3.5F, -27F, -24F);

		brigadelok_080Model[50].addBox(0F, 0F, 0F, 4, 1, 7, 0F); // Box 51
		brigadelok_080Model[50].setRotationPoint(-2F, -27.5F, -24F);

		brigadelok_080Model[51].addBox(0F, 0F, 0F, 10, 1, 1, 0F); // Box 53
		brigadelok_080Model[51].setRotationPoint(-5F, -23F, -29F);

		brigadelok_080Model[52].addBox(0F, 0F, 0F, 3, 7, 3, 0F); // Box 55
		brigadelok_080Model[52].setRotationPoint(-1.5F, -14F, -4F);

		brigadelok_080Model[53].addBox(0F, 0F, 0F, 4, 7, 2, 0F); // Box 56
		brigadelok_080Model[53].setRotationPoint(-2F, -14F, -3.5F);

		brigadelok_080Model[54].addBox(0F, 0F, 0F, 2, 7, 4, 0F); // Box 57
		brigadelok_080Model[54].setRotationPoint(-1F, -14F, -4.5F);

		brigadelok_080Model[55].addBox(0F, 0F, 0F, 3, 13, 5, 0F); // Box 58
		brigadelok_080Model[55].setRotationPoint(-1.5F, -20F, 3F);

		brigadelok_080Model[56].addBox(0F, 0F, 0F, 5, 13, 2, 0F); // Box 59
		brigadelok_080Model[56].setRotationPoint(-2.5F, -20F, 4.5F);

		brigadelok_080Model[57].addBox(0F, 0F, 0F, 4, 13, 4, 0F); // Box 60
		brigadelok_080Model[57].setRotationPoint(-2F, -20F, 3.5F);

		brigadelok_080Model[58].addBox(0F, 0F, 0F, 2, 3, 2, 0F); // Box 61
		brigadelok_080Model[58].setRotationPoint(-1F, -23F, 4.5F);

		brigadelok_080Model[59].addBox(0F, 0F, 0F, 2, 13, 2, 0F); // Box 62
		brigadelok_080Model[59].setRotationPoint(-1F, -21F, 20F);

		brigadelok_080Model[60].addBox(0F, 0F, 0F, 3, 6, 3, 0F); // Box 63
		brigadelok_080Model[60].setRotationPoint(-1.5F, -25.5F, 19.5F);

		brigadelok_080Model[61].addBox(0F, 0F, 0F, 4, 9, 4, 0F); // Box 64
		brigadelok_080Model[61].setRotationPoint(-2F, -16F, 12F);

		brigadelok_080Model[62].addBox(0F, 0F, 0F, 5, 9, 2, 0F); // Box 65
		brigadelok_080Model[62].setRotationPoint(-2.5F, -16F, 13F);

		brigadelok_080Model[63].addBox(0F, 0F, 0F, 2, 9, 5, 0F); // Box 66
		brigadelok_080Model[63].setRotationPoint(-1F, -16F, 11.5F);

		brigadelok_080Model[64].addBox(0F, 0F, 0F, 1, 1, 3, 0F); // Box 67
		brigadelok_080Model[64].setRotationPoint(-0.5F, -21.5F, 6.5F);

		brigadelok_080Model[65].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 68
		brigadelok_080Model[65].setRotationPoint(-0.5F, -20.5F, 8F);

		brigadelok_080Model[66].addBox(0F, 0F, 0F, 6, 2, 1, 0F); // Box 69
		brigadelok_080Model[66].setRotationPoint(-3F, -9F, 13.5F);

		brigadelok_080Model[67].addBox(0F, 0F, 0F, 1, 8, 1, 0F); // Box 71
		brigadelok_080Model[67].setRotationPoint(-0.5F, -25.5F, -6.5F);

		brigadelok_080Model[68].addBox(0F, 0F, 0F, 2, 2, 2, 0F); // Box 73
		brigadelok_080Model[68].setRotationPoint(-1F, -25F, -7F);

		brigadelok_080Model[69].addBox(0F, 0F, 0F, 1, 1, 3, 0F); // Box 74
		brigadelok_080Model[69].setRotationPoint(-0.5F, -17.5F, -8.5F);

		brigadelok_080Model[70].addBox(0F, 0F, 0F, 3, 1, 3, 0F); // Box 75
		brigadelok_080Model[70].setRotationPoint(-1.5F, -8.5F, 19.5F);

		brigadelok_080Model[71].addBox(0F, 0F, 0F, 3, 3, 2, 0F); // Box 76
		brigadelok_080Model[71].setRotationPoint(5F, -8.5F, 4.5F);

		brigadelok_080Model[72].addBox(0F, 0F, 0F, 2, 3, 3, 0F); // Box 77
		brigadelok_080Model[72].setRotationPoint(5.5F, -8.5F, 4F);

		brigadelok_080Model[73].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 78
		brigadelok_080Model[73].setRotationPoint(6F, -9.5F, 5F);

		brigadelok_080Model[74].addBox(0F, 0F, 0F, 2, 1, 2, 0F); // Box 79
		brigadelok_080Model[74].setRotationPoint(5.5F, -10.5F, 4.5F);

		brigadelok_080Model[75].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 81
		brigadelok_080Model[75].setRotationPoint(-2.5F, -9F, -3F);

		brigadelok_080Model[76].addBox(0F, 0F, 0F, 2, 3, 3, 0F); // Box 82
		brigadelok_080Model[76].setRotationPoint(5.5F, -7F, 12.5F);

		brigadelok_080Model[77].addBox(0F, 0F, 0F, 3, 3, 2, 0F); // Box 83
		brigadelok_080Model[77].setRotationPoint(5F, -7F, 13F);

		brigadelok_080Model[78].addBox(0F, 0F, 0F, 3, 1, 4, 0F); // Box 84
		brigadelok_080Model[78].setRotationPoint(5F, -6F, -2F);

		brigadelok_080Model[79].addBox(0F, 0F, 0F, 3, 1, 4, 0F); // Box 85
		brigadelok_080Model[79].setRotationPoint(5F, -6F, -7F);

		brigadelok_080Model[80].addBox(0F, 0F, 0F, 1, 2, 2, 0F); // Box 86
		brigadelok_080Model[80].setRotationPoint(-4.5F, -9F, 4.5F);

		brigadelok_080Model[81].addBox(0F, 0F, 0F, 1, 1, 13, 0F); // Box 87
		brigadelok_080Model[81].setRotationPoint(-4.5F, -8.5F, -8.5F);

		brigadelok_080Model[82].addBox(0F, 0F, 0F, 2, 2, 3, 0F); // Box 88
		brigadelok_080Model[82].setRotationPoint(-7.5F, -7.5F, 9F);

		brigadelok_080Model[83].addBox(0F, 0F, 0F, 3, 2, 2, 0F); // Box 89
		brigadelok_080Model[83].setRotationPoint(-8F, -7.5F, 9.5F);

		brigadelok_080Model[84].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 90
		brigadelok_080Model[84].setRotationPoint(-7.5F, 1.5F, 17F);

		brigadelok_080Model[85].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 91
		brigadelok_080Model[85].setRotationPoint(-7.5F, 1.5F, 18.5F);

		brigadelok_080Model[86].addBox(-0.5F, -0.5F, -9.5F, 1, 1, 19, 0F); // WheelConnectorPiston
		brigadelok_080Model[86].setRotationPoint(-7F, 6.5F, 4.5F);

		brigadelok_080Model[87].addBox(-0.5F, -0.5F, -9F, 1, 1, 18, 0F); // PistonValveConnector
		brigadelok_080Model[87].setRotationPoint(-8F, 6.5F, 10F);

		brigadelok_080Model[88].addBox(-0.5F, -0.5F, -4.5F, 1, 1, 9, 0F); // UpperPistonValveConnector
		brigadelok_080Model[88].setRotationPoint(-8F, 5F, 13.5F);

		brigadelok_080Model[89].addBox(-0.5F, -0.5F, -4F, 1, 1, 8, 0F); // UpperPiston
		brigadelok_080Model[89].setRotationPoint(-8F, 5.75F, 5F);
		brigadelok_080Model[89].rotateAngleX = 0.19198622F;

		brigadelok_080Model[90].addBox(-0.5F, -1F, -0.5F, 1, 2, 1, 0F); // UpperPistonArm
		brigadelok_080Model[90].setRotationPoint(-8F, 4.75F, 9F);

		brigadelok_080Model[91].addBox(-0.5F, -0.5F, -9.5F, 1, 1, 19, 0F); // WheelConnectorPiston
		brigadelok_080Model[91].setRotationPoint(7F, 6.5F, 4.5F);

		brigadelok_080Model[92].addBox(-0.5F, -0.5F, -9F, 1, 1, 18, 0F); // PistonValveConnector
		brigadelok_080Model[92].setRotationPoint(8F, 6.5F, 10F);

		brigadelok_080Model[93].addBox(-0.5F, -0.5F, -4.5F, 1, 1, 9, 0F); // UpperPistonValveConnector
		brigadelok_080Model[93].setRotationPoint(8F, 5F, 13.5F);

		brigadelok_080Model[94].addBox(-0.5F, -1F, -0.5F, 1, 2, 1, 0F); // UpperPistonArm
		brigadelok_080Model[94].setRotationPoint(8F, 4.75F, 9F);

		brigadelok_080Model[95].addBox(-0.5F, -0.5F, -4F, 1, 1, 8, 0F); // UpperPiston
		brigadelok_080Model[95].setRotationPoint(8F, 5.75F, 5F);
		brigadelok_080Model[95].rotateAngleX = 0.19198622F;

		brigadelok_080Model[96].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 102
		brigadelok_080Model[96].setRotationPoint(6.5F, 1.5F, 17F);

		brigadelok_080Model[97].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 103
		brigadelok_080Model[97].setRotationPoint(6.5F, 1.5F, 18.5F);

		brigadelok_080Model[98].addBox(0F, -2.5F, -2.5F, 0, 5, 5, 0F); // Wheel
		brigadelok_080Model[98].setRotationPoint(-5F, 6.5F, -4.5F);

		brigadelok_080Model[99].addBox(0F, -2.5F, -2.5F, 0, 5, 5, 0F); // Wheel
		brigadelok_080Model[99].setRotationPoint(-5F, 6.5F, 1.5F);

		brigadelok_080Model[100].addBox(0F, -2.5F, -2.5F, 0, 5, 5, 0F); // Wheel
		brigadelok_080Model[100].setRotationPoint(-5F, 6.5F, 7.5F);

		brigadelok_080Model[101].addBox(0F, -2.5F, -2.5F, 0, 5, 5, 0F); // Wheel
		brigadelok_080Model[101].setRotationPoint(-5F, 6.5F, 13.5F);

		brigadelok_080Model[102].addBox(0F, -2.5F, -2.5F, 0, 5, 5, 0F); // Wheel
		brigadelok_080Model[102].setRotationPoint(5F, 6.5F, 13.5F);

		brigadelok_080Model[103].addBox(0F, -2.5F, -2.5F, 0, 5, 5, 0F); // Wheel
		brigadelok_080Model[103].setRotationPoint(5F, 6.5F, 7.5F);

		brigadelok_080Model[104].addBox(0F, -2.5F, -2.5F, 0, 5, 5, 0F); // Wheel
		brigadelok_080Model[104].setRotationPoint(5F, 6.5F, 1.5F);

		brigadelok_080Model[105].addBox(0F, -2.5F, -2.5F, 0, 5, 5, 0F); // Wheel
		brigadelok_080Model[105].setRotationPoint(5F, 6.5F, -4.5F);


	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		for(int i = 0; i < 106; i++)
		{
			brigadelok_080Model[i].render(f5);
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
	}

	public ModelRendererTurbo brigadelok_080Model[];
}