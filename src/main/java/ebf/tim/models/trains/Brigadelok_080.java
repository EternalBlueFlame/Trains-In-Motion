//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model:
// Model Creator:
// Created on:12.01.2017 - 18:22:50
// Last changed on: 12.01.2017 - 18:22:50

package ebf.tim.models.trains;

import ebf.tim.models.StaticModelAnimator;
import ebf.tim.models.tmt.ModelRendererTurbo;
import ebf.tim.models.tmt.ModelBase;
import net.minecraft.entity.Entity;

public class Brigadelok_080 extends ModelBase
{
	private static final int textureXY = 256;

	public Brigadelok_080()
	{
		brigadelok_080Model = new ModelRendererTurbo[106];
		brigadelok_080Model[0] = new ModelRendererTurbo(this, 1, 1, textureXY, textureXY); // Box 0
		brigadelok_080Model[1] = new ModelRendererTurbo(this, 73, 1, textureXY, textureXY); // Box 1
		brigadelok_080Model[2] = new ModelRendererTurbo(this, 153, 1, textureXY, textureXY); // Box 2
		brigadelok_080Model[3] = new ModelRendererTurbo(this, 1, 17, textureXY, textureXY); // Box 3
		brigadelok_080Model[4] = new ModelRendererTurbo(this, 73, 17, textureXY, textureXY); // Box 4
		brigadelok_080Model[5] = new ModelRendererTurbo(this, 113, 17, textureXY, textureXY); // Box 5
		brigadelok_080Model[6] = new ModelRendererTurbo(this, 1, 33, textureXY, textureXY); // Box 6
		brigadelok_080Model[7] = new ModelRendererTurbo(this, 177, 17, textureXY, textureXY); // Box 7
		brigadelok_080Model[8] = new ModelRendererTurbo(this, 113, 33, textureXY, textureXY); // Box 8
		brigadelok_080Model[9] = new ModelRendererTurbo(this, 209, 25, textureXY, textureXY); // Box 9
		brigadelok_080Model[10] = new ModelRendererTurbo(this, 201, 17, textureXY, textureXY); // Box 10
		brigadelok_080Model[11] = new ModelRendererTurbo(this, 1, 49, textureXY, textureXY); // Box 11
		brigadelok_080Model[12] = new ModelRendererTurbo(this, 225, 9, textureXY, textureXY); // Box 12
		brigadelok_080Model[13] = new ModelRendererTurbo(this, 57, 49, textureXY, textureXY); // Box 13
		brigadelok_080Model[14] = new ModelRendererTurbo(this, 89, 49, textureXY, textureXY); // Box 14
		brigadelok_080Model[15] = new ModelRendererTurbo(this, 129, 65, textureXY, textureXY); // Box 15
		brigadelok_080Model[16] = new ModelRendererTurbo(this, 145, 65, textureXY, textureXY); // Box 16
		brigadelok_080Model[17] = new ModelRendererTurbo(this, 177, 57, textureXY, textureXY); // Box 17
		brigadelok_080Model[18] = new ModelRendererTurbo(this, 233, 25, textureXY, textureXY); // Box 18
		brigadelok_080Model[19] = new ModelRendererTurbo(this, 1, 73, textureXY, textureXY); // Box 19
		brigadelok_080Model[20] = new ModelRendererTurbo(this, 81, 73, textureXY, textureXY); // Box 20
		brigadelok_080Model[21] = new ModelRendererTurbo(this, 1, 89, textureXY, textureXY); // Box 21
		brigadelok_080Model[22] = new ModelRendererTurbo(this, 33, 89, textureXY, textureXY); // Box 22
		brigadelok_080Model[23] = new ModelRendererTurbo(this, 145, 89, textureXY, textureXY); // Box 23
		brigadelok_080Model[24] = new ModelRendererTurbo(this, 145, 1, textureXY, textureXY); // Box 24
		brigadelok_080Model[25] = new ModelRendererTurbo(this, 233, 1, textureXY, textureXY); // Box 25
		brigadelok_080Model[26] = new ModelRendererTurbo(this, 73, 1, textureXY, textureXY); // Box 26
		brigadelok_080Model[27] = new ModelRendererTurbo(this, 1, 17, textureXY, textureXY); // Box 27
		brigadelok_080Model[28] = new ModelRendererTurbo(this, 177, 33, textureXY, textureXY); // Box 28
		brigadelok_080Model[29] = new ModelRendererTurbo(this, 177, 41, textureXY, textureXY); // Box 29
		brigadelok_080Model[30] = new ModelRendererTurbo(this, 193, 41, textureXY, textureXY); // Box 31
		brigadelok_080Model[31] = new ModelRendererTurbo(this, 81, 17, textureXY, textureXY); // Box 32
		brigadelok_080Model[32] = new ModelRendererTurbo(this, 217, 73, textureXY, textureXY); // Box 33
		brigadelok_080Model[33] = new ModelRendererTurbo(this, 113, 17, textureXY, textureXY); // Box 34
		brigadelok_080Model[34] = new ModelRendererTurbo(this, 1, 49, textureXY, textureXY); // Box 35
		brigadelok_080Model[35] = new ModelRendererTurbo(this, 57, 49, textureXY, textureXY); // Box 36
		brigadelok_080Model[36] = new ModelRendererTurbo(this, 81, 49, textureXY, textureXY); // Box 37
		brigadelok_080Model[37] = new ModelRendererTurbo(this, 185, 17, textureXY, textureXY); // Box 38
		brigadelok_080Model[38] = new ModelRendererTurbo(this, 129, 81, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Axel
		brigadelok_080Model[39] = new ModelRendererTurbo(this, 113, 89, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Axel 2
		brigadelok_080Model[40] = new ModelRendererTurbo(this, 217, 89, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Axel 3
		brigadelok_080Model[41] = new ModelRendererTurbo(this, 81, 97, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Axel 4
		brigadelok_080Model[42] = new ModelRendererTurbo(this, 41, 105, textureXY, textureXY); // Box 43
		brigadelok_080Model[43] = new ModelRendererTurbo(this, 193, 105, textureXY, textureXY); // Box 44
		brigadelok_080Model[44] = new ModelRendererTurbo(this, 1, 113, textureXY, textureXY); // Box 45
		brigadelok_080Model[45] = new ModelRendererTurbo(this, 57, 113, textureXY, textureXY); // Box 46
		brigadelok_080Model[46] = new ModelRendererTurbo(this, 105, 113, textureXY, textureXY); // Box 47
		brigadelok_080Model[47] = new ModelRendererTurbo(this, 161, 65, textureXY, textureXY); // Box 48
		brigadelok_080Model[48] = new ModelRendererTurbo(this, 105, 73, textureXY, textureXY); // Box 49
		brigadelok_080Model[49] = new ModelRendererTurbo(this, 169, 113, textureXY, textureXY); // Box 50
		brigadelok_080Model[50] = new ModelRendererTurbo(this, 81, 57, textureXY, textureXY); // Box 51
		brigadelok_080Model[51] = new ModelRendererTurbo(this, 233, 89, textureXY, textureXY); // Box 53
		brigadelok_080Model[52] = new ModelRendererTurbo(this, 241, 73, textureXY, textureXY); // Box 55
		brigadelok_080Model[53] = new ModelRendererTurbo(this, 1, 89, textureXY, textureXY); // Box 56
		brigadelok_080Model[54] = new ModelRendererTurbo(this, 1, 57, textureXY, textureXY); // Box 57
		brigadelok_080Model[55] = new ModelRendererTurbo(this, 201, 113, textureXY, textureXY); // Box 58
		brigadelok_080Model[56] = new ModelRendererTurbo(this, 241, 105, textureXY, textureXY); // Box 59
		brigadelok_080Model[57] = new ModelRendererTurbo(this, 1, 121, textureXY, textureXY); // Box 60
		brigadelok_080Model[58] = new ModelRendererTurbo(this, 1, 33, textureXY, textureXY); // Box 61
		brigadelok_080Model[59] = new ModelRendererTurbo(this, 225, 113, textureXY, textureXY); // Box 62
		brigadelok_080Model[60] = new ModelRendererTurbo(this, 217, 89, textureXY, textureXY); // Box 63
		brigadelok_080Model[61] = new ModelRendererTurbo(this, 25, 121, textureXY, textureXY); // Box 64
		brigadelok_080Model[62] = new ModelRendererTurbo(this, 49, 121, textureXY, textureXY); // Box 65
		brigadelok_080Model[63] = new ModelRendererTurbo(this, 97, 97, textureXY, textureXY); // Box 66
		brigadelok_080Model[64] = new ModelRendererTurbo(this, 241, 1, textureXY, textureXY); // Box 67
		brigadelok_080Model[65] = new ModelRendererTurbo(this, 153, 1, textureXY, textureXY); // Box 68
		brigadelok_080Model[66] = new ModelRendererTurbo(this, 145, 97, textureXY, textureXY); // Box 69
		brigadelok_080Model[67] = new ModelRendererTurbo(this, 249, 9, textureXY, textureXY); // Box 71
		brigadelok_080Model[68] = new ModelRendererTurbo(this, 57, 57, textureXY, textureXY); // Box 73
		brigadelok_080Model[69] = new ModelRendererTurbo(this, 225, 17, textureXY, textureXY); // Box 74
		brigadelok_080Model[70] = new ModelRendererTurbo(this, 161, 81, textureXY, textureXY); // Box 75
		brigadelok_080Model[71] = new ModelRendererTurbo(this, 1, 73, textureXY, textureXY); // Box 76
		brigadelok_080Model[72] = new ModelRendererTurbo(this, 217, 73, textureXY, textureXY); // Box 77
		brigadelok_080Model[73] = new ModelRendererTurbo(this, 113, 25, textureXY, textureXY); // Box 78
		brigadelok_080Model[74] = new ModelRendererTurbo(this, 185, 65, textureXY, textureXY); // Box 79
		brigadelok_080Model[75] = new ModelRendererTurbo(this, 185, 41, textureXY, textureXY); // Box 81
		brigadelok_080Model[76] = new ModelRendererTurbo(this, 33, 89, textureXY, textureXY); // Box 82
		brigadelok_080Model[77] = new ModelRendererTurbo(this, 113, 105, textureXY, textureXY); // Box 83
		brigadelok_080Model[78] = new ModelRendererTurbo(this, 129, 97, textureXY, textureXY); // Box 84
		brigadelok_080Model[79] = new ModelRendererTurbo(this, 129, 105, textureXY, textureXY); // Box 85
		brigadelok_080Model[80] = new ModelRendererTurbo(this, 97, 49, textureXY, textureXY); // Box 86
		brigadelok_080Model[81] = new ModelRendererTurbo(this, 65, 121, textureXY, textureXY); // Box 87
		brigadelok_080Model[82] = new ModelRendererTurbo(this, 113, 97, textureXY, textureXY); // Box 88
		brigadelok_080Model[83] = new ModelRendererTurbo(this, 65, 129, textureXY, textureXY); // Box 89
		brigadelok_080Model[84] = new ModelRendererTurbo(this, 201, 49, textureXY, textureXY); // Box 90
		brigadelok_080Model[85] = new ModelRendererTurbo(this, 249, 49, textureXY, textureXY); // Box 91
		brigadelok_080Model[86] = new ModelRendererTurbo(this, 73, 129, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // WheelConnectorPiston
		brigadelok_080Model[87] = new ModelRendererTurbo(this, 121, 129, textureXY, textureXY, StaticModelAnimator.tagAdvancedPiston); // PistonValveConnector
		brigadelok_080Model[88] = new ModelRendererTurbo(this, 161, 129, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // UpperPistonValveConnector
		brigadelok_080Model[89] = new ModelRendererTurbo(this, 217, 129, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // UpperPiston
		brigadelok_080Model[90] = new ModelRendererTurbo(this, 201, 57, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // UpperPistonArm
		brigadelok_080Model[91] = new ModelRendererTurbo(this, 25, 137, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // WheelConnectorPiston
		brigadelok_080Model[92] = new ModelRendererTurbo(this, 73, 137, textureXY, textureXY, StaticModelAnimator.tagAdvancedPiston); // PistonValveConnector
		brigadelok_080Model[93] = new ModelRendererTurbo(this, 113, 137, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // UpperPistonValveConnector
		brigadelok_080Model[94] = new ModelRendererTurbo(this, 249, 57, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // UpperPistonArm
		brigadelok_080Model[95] = new ModelRendererTurbo(this, 137, 137, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // UpperPiston
		brigadelok_080Model[96] = new ModelRendererTurbo(this, 161, 65, textureXY, textureXY); // Box 102
		brigadelok_080Model[97] = new ModelRendererTurbo(this, 201, 65, textureXY, textureXY); // Box 103
		brigadelok_080Model[98] = new ModelRendererTurbo(this, 185, 129, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel
		brigadelok_080Model[99] = new ModelRendererTurbo(this, 241, 129, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel
		brigadelok_080Model[100] = new ModelRendererTurbo(this, 161, 137, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel
		brigadelok_080Model[101] = new ModelRendererTurbo(this, 177, 137, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel
		brigadelok_080Model[102] = new ModelRendererTurbo(this, 193, 137, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel
		brigadelok_080Model[103] = new ModelRendererTurbo(this, 209, 137, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel
		brigadelok_080Model[104] = new ModelRendererTurbo(this, 225, 137, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel
		brigadelok_080Model[105] = new ModelRendererTurbo(this, 241, 137, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // Wheel

		brigadelok_080Model[0].addBox(0F, 0F, 0F, 32, 10, 3, 0F); // Box 0
		brigadelok_080Model[0].setRotationPoint(-24F, -7F, -1.5F);

		brigadelok_080Model[1].addBox(0F, 0F, 0F, 32, 8, 6, 0F); // Box 1
		brigadelok_080Model[1].setRotationPoint(-24F, -6F, -3F);

		brigadelok_080Model[2].addBox(0F, 0F, 0F, 32, 6, 8, 0F); // Box 2
		brigadelok_080Model[2].setRotationPoint(-24F, -5F, -4F);

		brigadelok_080Model[3].addBox(0F, 0F, 0F, 32, 3, 10, 0F); // Box 3
		brigadelok_080Model[3].setRotationPoint(-24F, -3.5F, -5F);

		brigadelok_080Model[4].addBox(0F, 0F, 0F, 8, 6, 18, 0F); // Box 4
		brigadelok_080Model[4].setRotationPoint(-22F, -3.5F, -9F);

		brigadelok_080Model[5].addBox(0F, 0F, 0F, 30, 1, 11, 0F); // Box 5
		brigadelok_080Model[5].setRotationPoint(-22F, 2.5F, -5.5F);

		brigadelok_080Model[6].addBox(0F, 0F, 0F, 30, 1, 9, 0F); // Box 6
		brigadelok_080Model[6].setRotationPoint(-22F, 3.5F, -4.5F);

		brigadelok_080Model[7].addBox(0F, 0F, 0F, 1, 2, 20, 0F); // Box 7
		brigadelok_080Model[7].setRotationPoint(-24F, 4F, -10F);

		brigadelok_080Model[8].addBox(0F, 0F, 0F, 22, 7, 18, 0F); // Box 8
		brigadelok_080Model[8].setRotationPoint(-14F, -4.5F, -9F);

		brigadelok_080Model[9].addBox(0F, 0F, 0F, 1, 25, 18, 0F); // Box 9
		brigadelok_080Model[9].setRotationPoint(8F, -21F, -9F);

		brigadelok_080Model[10].addBox(0F, 0F, 0F, 9, 15, 1, 0F); // Box 10
		brigadelok_080Model[10].setRotationPoint(13F, -8F, -9F);

		brigadelok_080Model[11].addBox(0F, 0F, 0F, 15, 1, 18, 0F); // Box 11
		brigadelok_080Model[11].setRotationPoint(13F, 7F, -9F);

		brigadelok_080Model[12].addBox(0F, 0F, 0F, 2, 3, 12, 0F); // Box 12
		brigadelok_080Model[12].setRotationPoint(-23.5F, 5F, -6F);

		brigadelok_080Model[13].addBox(0F, 0F, 0F, 1, 3, 14, 0F); // Box 13
		brigadelok_080Model[13].setRotationPoint(-24.5F, 6.5F, -7F);

		brigadelok_080Model[14].addBox(0F, 0F, 0F, 1, 2, 16, 0F); // Box 14
		brigadelok_080Model[14].setRotationPoint(-23.5F, 8F, -8F);

		brigadelok_080Model[15].addBox(0F, 0F, 0F, 4, 24, 1, 0F); // Box 15
		brigadelok_080Model[15].setRotationPoint(9F, -20F, -9F);

		brigadelok_080Model[16].addBox(0F, 0F, 0F, 4, 24, 1, 0F); // Box 16
		brigadelok_080Model[16].setRotationPoint(9F, -20F, 8F);

		brigadelok_080Model[17].addBox(0F, 0F, 0F, 1, 29, 18, 0F); // Box 17
		brigadelok_080Model[17].setRotationPoint(28F, -21F, -9F);

		brigadelok_080Model[18].addBox(0F, 0F, 0F, 9, 15, 1, 0F); // Box 18
		brigadelok_080Model[18].setRotationPoint(13F, -8F, 8F);

		brigadelok_080Model[19].addBox(0F, 0F, 0F, 34, 3, 12, 0F); // Box 19
		brigadelok_080Model[19].setRotationPoint(-22F, 4F, -6F);

		brigadelok_080Model[20].addBox(0F, 0F, 0F, 1, 4, 18, 0F); // Box 20
		brigadelok_080Model[20].setRotationPoint(12F, 4F, -9F);

		brigadelok_080Model[21].addBox(0F, 0F, 0F, 4, 1, 16, 0F); // Box 21
		brigadelok_080Model[21].setRotationPoint(9F, 3F, -8F);

		brigadelok_080Model[22].addBox(0F, 0F, 0F, 16, 1, 12, 0F); // Box 22
		brigadelok_080Model[22].setRotationPoint(12F, 8F, -6F);

		brigadelok_080Model[23].addBox(0F, 0F, 0F, 3, 5, 18, 0F); // Box 23
		brigadelok_080Model[23].setRotationPoint(-20F, 4.5F, -9F);

		brigadelok_080Model[24].addBox(0F, 0F, 0F, 1, 2, 2, 0F); // Box 24
		brigadelok_080Model[24].setRotationPoint(-25.5F, 7F, -1F);

		brigadelok_080Model[25].addBox(0F, 0F, 0F, 1, 3, 3, 0F); // Box 25
		brigadelok_080Model[25].setRotationPoint(-26.5F, 6.5F, -1.5F);

		brigadelok_080Model[26].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 26
		brigadelok_080Model[26].setRotationPoint(-27F, 5.5F, -0.5F);

		brigadelok_080Model[27].addBox(0F, 0F, 0F, 3, 2, 1, 0F); // Box 27
		brigadelok_080Model[27].setRotationPoint(-17F, 7F, -6F);

		brigadelok_080Model[28].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 28
		brigadelok_080Model[28].setRotationPoint(-13F, 7F, -6F);

		brigadelok_080Model[29].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 29
		brigadelok_080Model[29].setRotationPoint(-7F, 7F, -6F);

		brigadelok_080Model[30].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 31
		brigadelok_080Model[30].setRotationPoint(-1F, 7F, -6F);

		brigadelok_080Model[31].addBox(0F, 0F, 0F, 3, 2, 1, 0F); // Box 32
		brigadelok_080Model[31].setRotationPoint(5F, 7F, -6F);

		brigadelok_080Model[32].addBox(0F, 0F, 0F, 4, 2, 12, 0F); // Box 33
		brigadelok_080Model[32].setRotationPoint(8F, 7F, -6F);

		brigadelok_080Model[33].addBox(0F, 0F, 0F, 3, 2, 1, 0F); // Box 34
		brigadelok_080Model[33].setRotationPoint(5F, 7F, 5F);

		brigadelok_080Model[34].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 35
		brigadelok_080Model[34].setRotationPoint(-1F, 7F, 5F);

		brigadelok_080Model[35].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 36
		brigadelok_080Model[35].setRotationPoint(-7F, 7F, 5F);

		brigadelok_080Model[36].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 37
		brigadelok_080Model[36].setRotationPoint(-13F, 7F, 5F);

		brigadelok_080Model[37].addBox(0F, 0F, 0F, 3, 2, 1, 0F); // Box 38
		brigadelok_080Model[37].setRotationPoint(-17F, 7F, 5F);

		brigadelok_080Model[38].addBox(-0.5F, -0.5F, 0F, 1, 1, 13, 0F); // Axel
		brigadelok_080Model[38].setRotationPoint(4.5F, 7.5F, -6.5F);

		brigadelok_080Model[39].addBox(-0.5F, -0.5F, 0F, 1, 1, 13, 0F); // Axel 2
		brigadelok_080Model[39].setRotationPoint(-1.5F, 7.5F, -6.5F);

		brigadelok_080Model[40].addBox(-0.5F, -0.5F, 0F, 1, 1, 13, 0F); // Axel 3
		brigadelok_080Model[40].setRotationPoint(-7.5F, 7.5F, -6.5F);

		brigadelok_080Model[41].addBox(-0.5F, -0.5F, 0F, 1, 1, 13, 0F); // Axel 4
		brigadelok_080Model[41].setRotationPoint(-13.5F, 7.5F, -6.5F);

		brigadelok_080Model[42].addBox(0F, 0F, 0F, 23, 1, 2, 0F); // Box 43
		brigadelok_080Model[42].setRotationPoint(7F, -21F, -10F);

		brigadelok_080Model[43].addBox(0F, 0F, 0F, 23, 1, 2, 0F); // Box 44
		brigadelok_080Model[43].setRotationPoint(7F, -21F, 8F);

		brigadelok_080Model[44].addBox(0F, 0F, 0F, 23, 1, 3, 0F); // Box 45
		brigadelok_080Model[44].setRotationPoint(7F, -22F, -8F);

		brigadelok_080Model[45].addBox(0F, 0F, 0F, 23, 1, 3, 0F); // Box 46
		brigadelok_080Model[45].setRotationPoint(7F, -22F, 5F);

		brigadelok_080Model[46].addBox(0F, 0F, 0F, 23, 1, 10, 0F); // Box 47
		brigadelok_080Model[46].setRotationPoint(7F, -23F, -5F);

		brigadelok_080Model[47].addBox(0F, 0F, 0F, 5, 2, 6, 0F); // Box 48
		brigadelok_080Model[47].setRotationPoint(18F, -25F, -3F);

		brigadelok_080Model[48].addBox(0F, 0F, 0F, 1, 1, 10, 0F); // Box 49
		brigadelok_080Model[48].setRotationPoint(8F, -22F, -5F);

		brigadelok_080Model[49].addBox(0F, 0F, 0F, 7, 1, 7, 0F); // Box 50
		brigadelok_080Model[49].setRotationPoint(17F, -26F, -3.5F);

		brigadelok_080Model[50].addBox(0F, 0F, 0F, 7, 1, 4, 0F); // Box 51
		brigadelok_080Model[50].setRotationPoint(17F, -26.5F, -2F);

		brigadelok_080Model[51].addBox(0F, 0F, 0F, 1, 1, 10, 0F); // Box 53
		brigadelok_080Model[51].setRotationPoint(28F, -22F, -5F);

		brigadelok_080Model[52].addBox(0F, 0F, 0F, 3, 7, 3, 0F); // Box 55
		brigadelok_080Model[52].setRotationPoint(1F, -13F, -1.5F);

		brigadelok_080Model[53].addBox(0F, 0F, 0F, 2, 7, 4, 0F); // Box 56
		brigadelok_080Model[53].setRotationPoint(1.5F, -13F, -2F);

		brigadelok_080Model[54].addBox(0F, 0F, 0F, 4, 7, 2, 0F); // Box 57
		brigadelok_080Model[54].setRotationPoint(0.5F, -13F, -1F);

		brigadelok_080Model[55].addBox(0F, 0F, 0F, 5, 13, 3, 0F); // Box 58
		brigadelok_080Model[55].setRotationPoint(-8F, -19F, -1.5F);

		brigadelok_080Model[56].addBox(0F, 0F, 0F, 2, 13, 5, 0F); // Box 59
		brigadelok_080Model[56].setRotationPoint(-6.5F, -19F, -2.5F);

		brigadelok_080Model[57].addBox(0F, 0F, 0F, 4, 13, 4, 0F); // Box 60
		brigadelok_080Model[57].setRotationPoint(-7.5F, -19F, -2F);

		brigadelok_080Model[58].addBox(0F, 0F, 0F, 2, 3, 2, 0F); // Box 61
		brigadelok_080Model[58].setRotationPoint(-6.5F, -22F, -1F);

		brigadelok_080Model[59].addBox(0F, 0F, 0F, 2, 13, 2, 0F); // Box 62
		brigadelok_080Model[59].setRotationPoint(-22F, -20F, -1F);

		brigadelok_080Model[60].addBox(0F, 0F, 0F, 3, 6, 3, 0F); // Box 63
		brigadelok_080Model[60].setRotationPoint(-22.5F, -24.5F, -1.5F);

		brigadelok_080Model[61].addBox(0F, 0F, 0F, 4, 9, 4, 0F); // Box 64
		brigadelok_080Model[61].setRotationPoint(-16F, -15F, -2F);

		brigadelok_080Model[62].addBox(0F, 0F, 0F, 2, 9, 5, 0F); // Box 65
		brigadelok_080Model[62].setRotationPoint(-15F, -15F, -2.5F);

		brigadelok_080Model[63].addBox(0F, 0F, 0F, 5, 9, 2, 0F); // Box 66
		brigadelok_080Model[63].setRotationPoint(-16.5F, -15F, -1F);

		brigadelok_080Model[64].addBox(0F, 0F, 0F, 3, 1, 1, 0F); // Box 67
		brigadelok_080Model[64].setRotationPoint(-9.5F, -20.5F, -0.5F);

		brigadelok_080Model[65].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 68
		brigadelok_080Model[65].setRotationPoint(-9F, -19.5F, -0.5F);

		brigadelok_080Model[66].addBox(0F, 0F, 0F, 1, 2, 6, 0F); // Box 69
		brigadelok_080Model[66].setRotationPoint(-14.5F, -8F, -3F);

		brigadelok_080Model[67].addBox(0F, 0F, 0F, 1, 8, 1, 0F); // Box 71
		brigadelok_080Model[67].setRotationPoint(5.5F, -24.5F, -0.5F);

		brigadelok_080Model[68].addBox(0F, 0F, 0F, 2, 2, 2, 0F); // Box 73
		brigadelok_080Model[68].setRotationPoint(5F, -24F, -1F);

		brigadelok_080Model[69].addBox(0F, 0F, 0F, 3, 1, 1, 0F); // Box 74
		brigadelok_080Model[69].setRotationPoint(5.5F, -16.5F, -0.5F);

		brigadelok_080Model[70].addBox(0F, 0F, 0F, 3, 1, 3, 0F); // Box 75
		brigadelok_080Model[70].setRotationPoint(-22.5F, -7.5F, -1.5F);

		brigadelok_080Model[71].addBox(0F, 0F, 0F, 2, 3, 3, 0F); // Box 76
		brigadelok_080Model[71].setRotationPoint(-6.5F, -7.5F, 5F);

		brigadelok_080Model[72].addBox(0F, 0F, 0F, 3, 3, 2, 0F); // Box 77
		brigadelok_080Model[72].setRotationPoint(-7F, -7.5F, 5.5F);

		brigadelok_080Model[73].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 78
		brigadelok_080Model[73].setRotationPoint(-6F, -8.5F, 6F);

		brigadelok_080Model[74].addBox(0F, 0F, 0F, 2, 1, 2, 0F); // Box 79
		brigadelok_080Model[74].setRotationPoint(-6.5F, -9.5F, 5.5F);

		brigadelok_080Model[75].addBox(0F, 0F, 0F, 1, 2, 5, 0F); // Box 81
		brigadelok_080Model[75].setRotationPoint(2F, -8F, -2.5F);

		brigadelok_080Model[76].addBox(0F, 0F, 0F, 3, 3, 2, 0F); // Box 82
		brigadelok_080Model[76].setRotationPoint(-15.5F, -6F, 5.5F);

		brigadelok_080Model[77].addBox(0F, 0F, 0F, 2, 3, 3, 0F); // Box 83
		brigadelok_080Model[77].setRotationPoint(-15F, -6F, 5F);

		brigadelok_080Model[78].addBox(0F, 0F, 0F, 4, 1, 3, 0F); // Box 84
		brigadelok_080Model[78].setRotationPoint(-2F, -5F, 5F);

		brigadelok_080Model[79].addBox(0F, 0F, 0F, 4, 1, 3, 0F); // Box 85
		brigadelok_080Model[79].setRotationPoint(3F, -5F, 5F);

		brigadelok_080Model[80].addBox(0F, 0F, 0F, 2, 2, 1, 0F); // Box 86
		brigadelok_080Model[80].setRotationPoint(-6.5F, -8F, -4.5F);

		brigadelok_080Model[81].addBox(0F, 0F, 0F, 13, 1, 1, 0F); // Box 87
		brigadelok_080Model[81].setRotationPoint(-4.5F, -7.5F, -4.5F);

		brigadelok_080Model[82].addBox(0F, 0F, 0F, 3, 2, 2, 0F); // Box 88
		brigadelok_080Model[82].setRotationPoint(-12F, -6.5F, -7.5F);

		brigadelok_080Model[83].addBox(0F, 0F, 0F, 2, 2, 3, 0F); // Box 89
		brigadelok_080Model[83].setRotationPoint(-11.5F, -6.5F, -8F);

		brigadelok_080Model[84].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 90
		brigadelok_080Model[84].setRotationPoint(-18F, 2.5F, -7.5F);

		brigadelok_080Model[85].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 91
		brigadelok_080Model[85].setRotationPoint(-19.5F, 2.5F, -7.5F);

		brigadelok_080Model[86].addBox(-9.5F, -0.5F, -0.5F, 19, 1, 1, 0F); // WheelConnectorPiston
		brigadelok_080Model[86].setRotationPoint(-4.5F, 7.5F, -7F);

		brigadelok_080Model[87].addBox(-9F, -0.5F, -0.5F, 18, 1, 1, 0F); // PistonValveConnector
		brigadelok_080Model[87].setRotationPoint(-10F, 7.5F, -8F);

		brigadelok_080Model[88].addBox(-4.5F, -0.5F, -0.5F, 9, 1, 1, 0F); // UpperPistonValveConnector
		brigadelok_080Model[88].setRotationPoint(-13.5F, 6F, -8F);

		brigadelok_080Model[89].addBox(-4F, -0.5F, -0.5F, 8, 1, 1, 0F); // UpperPiston
		brigadelok_080Model[89].setRotationPoint(-5F, 6.75F, -8F);
		brigadelok_080Model[89].rotateAngleZ = -0.19198622F;

		brigadelok_080Model[90].addBox(-0.5F, -1F, -0.5F, 1, 2, 1, 0F); // UpperPistonArm
		brigadelok_080Model[90].setRotationPoint(-9F, 5.75F, -8F);

		brigadelok_080Model[91].addBox(-9.5F, -0.5F, -0.5F, 19, 1, 1, 0F); // WheelConnectorPiston
		brigadelok_080Model[91].setRotationPoint(-4.5F, 7.5F, 7F);

		brigadelok_080Model[92].addBox(-9F, -0.5F, -0.5F, 18, 1, 1, 0F); // PistonValveConnector
		brigadelok_080Model[92].setRotationPoint(-10F, 7.5F, 8F);

		brigadelok_080Model[93].addBox(-4.5F, -0.5F, -0.5F, 9, 1, 1, 0F); // UpperPistonValveConnector
		brigadelok_080Model[93].setRotationPoint(-13.5F, 6F, 8F);

		brigadelok_080Model[94].addBox(-0.5F, -1F, -0.5F, 1, 2, 1, 0F); // UpperPistonArm
		brigadelok_080Model[94].setRotationPoint(-9F, 5.75F, 8F);

		brigadelok_080Model[95].addBox(-4F, -0.5F, -0.5F, 8, 1, 1, 0F); // UpperPiston
		brigadelok_080Model[95].setRotationPoint(-5F, 6.75F, 8F);
		brigadelok_080Model[95].rotateAngleZ = -0.19198622F;

		brigadelok_080Model[96].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 102
		brigadelok_080Model[96].setRotationPoint(-18F, 2.5F, 6.5F);

		brigadelok_080Model[97].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 103
		brigadelok_080Model[97].setRotationPoint(-19.5F, 2.5F, 6.5F);

		brigadelok_080Model[98].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // Wheel
		brigadelok_080Model[98].setRotationPoint(4.5F, 7.5F, -5F);

		brigadelok_080Model[99].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // Wheel
		brigadelok_080Model[99].setRotationPoint(-1.5F, 7.5F, -5F);

		brigadelok_080Model[100].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // Wheel
		brigadelok_080Model[100].setRotationPoint(-7.5F, 7.5F, -5F);

		brigadelok_080Model[101].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // Wheel
		brigadelok_080Model[101].setRotationPoint(-13.5F, 7.5F, -5F);

		brigadelok_080Model[102].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // Wheel
		brigadelok_080Model[102].setRotationPoint(-13.5F, 7.5F, 5F);

		brigadelok_080Model[103].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // Wheel
		brigadelok_080Model[103].setRotationPoint(-7.5F, 7.5F, 5F);

		brigadelok_080Model[104].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // Wheel
		brigadelok_080Model[104].setRotationPoint(-1.5F, 7.5F, 5F);

		brigadelok_080Model[105].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // Wheel
		brigadelok_080Model[105].setRotationPoint(4.5F, 7.5F, 5F);


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

	private ModelRendererTurbo brigadelok_080Model[];
}