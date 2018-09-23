//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model:
// Model Creator:
// Created on:12.01.2017 - 18:22:50
// Last changed on: 12.01.2017 - 18:22:50

package ebf.tim.models.trains;

import ebf.tim.models.StaticModelAnimator;
import net.minecraft.entity.Entity;
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.ModelRendererTurbo;

public class Brigadelok_080 extends ModelBase
{
	private static final int textureXY = 256;

	public Brigadelok_080()
	{
		brigadelok_080Model = new ModelRendererTurbo[112];
		brigadelok_080Model[0] = new ModelRendererTurbo(this, 1, 1, textureXY, textureXY); // Box 0
		brigadelok_080Model[1] = new ModelRendererTurbo(this, 73, 1, textureXY, textureXY); // Box 2
		brigadelok_080Model[2] = new ModelRendererTurbo(this, 153, 1, textureXY, textureXY); // Box 3
		brigadelok_080Model[3] = new ModelRendererTurbo(this, 1, 17, textureXY, textureXY); // Box 4
		brigadelok_080Model[4] = new ModelRendererTurbo(this, 72, 16, textureXY, textureXY); // Box 5
		brigadelok_080Model[5] = new ModelRendererTurbo(this, 128, 17, textureXY, textureXY); // Box 6
		brigadelok_080Model[6] = new ModelRendererTurbo(this, 233, 1, textureXY, textureXY); // Box 7
		brigadelok_080Model[7] = new ModelRendererTurbo(this, 145, 1, textureXY, textureXY); // Box 8
		brigadelok_080Model[8] = new ModelRendererTurbo(this, 241, 9, textureXY, textureXY); // Box 9
		brigadelok_080Model[9] = new ModelRendererTurbo(this, 113, 17, textureXY, textureXY); // Box 10
		brigadelok_080Model[10] = new ModelRendererTurbo(this, 73, 1, textureXY, textureXY); // Box 11
		brigadelok_080Model[11] = new ModelRendererTurbo(this, 1, 17, textureXY, textureXY); // Box 12
		brigadelok_080Model[12] = new ModelRendererTurbo(this, 129, 17, textureXY, textureXY); // Box 13
		brigadelok_080Model[13] = new ModelRendererTurbo(this, 193, 17, textureXY, textureXY); // Box 14
		brigadelok_080Model[14] = new ModelRendererTurbo(this, 209, 17, textureXY, textureXY); // Box 15
		brigadelok_080Model[15] = new ModelRendererTurbo(this, 225, 17, textureXY, textureXY); // Box 16
		brigadelok_080Model[16] = new ModelRendererTurbo(this, 241, 17, textureXY, textureXY); // Box 17
		brigadelok_080Model[17] = new ModelRendererTurbo(this, 209, 25, textureXY, textureXY); // Box 18
		brigadelok_080Model[18] = new ModelRendererTurbo(this, 1, 33, textureXY, textureXY); // Box 19
		brigadelok_080Model[19] = new ModelRendererTurbo(this, 25, 33, textureXY, textureXY); // Box 20
		brigadelok_080Model[20] = new ModelRendererTurbo(this, 41, 33, textureXY, textureXY); // Box 21
		brigadelok_080Model[21] = new ModelRendererTurbo(this, 225, 33, textureXY, textureXY); // Box 22
		brigadelok_080Model[22] = new ModelRendererTurbo(this, 65, 41, textureXY, textureXY); // Box 23
		brigadelok_080Model[23] = new ModelRendererTurbo(this, 81, 17, textureXY, textureXY); // Box 24
		brigadelok_080Model[24] = new ModelRendererTurbo(this, 241, 1, textureXY, textureXY); // Box 25
		brigadelok_080Model[25] = new ModelRendererTurbo(this, 113, 25, textureXY, textureXY); // Box 26
		brigadelok_080Model[26] = new ModelRendererTurbo(this, 81, 41, textureXY, textureXY); // Box 27
		brigadelok_080Model[27] = new ModelRendererTurbo(this, 97, 41, textureXY, textureXY); // Box 28
		brigadelok_080Model[28] = new ModelRendererTurbo(this, 113, 41, textureXY, textureXY); // Box 29
		brigadelok_080Model[29] = new ModelRendererTurbo(this, 113, 25, textureXY, textureXY); // Box 30
		brigadelok_080Model[30] = new ModelRendererTurbo(this, 129, 25, textureXY, textureXY); // Box 31
		brigadelok_080Model[31] = new ModelRendererTurbo(this, 121, 49, textureXY, textureXY); // Box 32
		brigadelok_080Model[32] = new ModelRendererTurbo(this, 1, 33, textureXY, textureXY); // Box 33
		brigadelok_080Model[33] = new ModelRendererTurbo(this, 1, 57, textureXY, textureXY); // Box 34
		brigadelok_080Model[34] = new ModelRendererTurbo(this, 33, 57, textureXY, textureXY); // Box 35
		brigadelok_080Model[35] = new ModelRendererTurbo(this, 65, 57, textureXY, textureXY); // Box 36
		brigadelok_080Model[36] = new ModelRendererTurbo(this, 121, 25, textureXY, textureXY); // Box 37
		brigadelok_080Model[37] = new ModelRendererTurbo(this, 129, 25, textureXY, textureXY); // Box 38
		brigadelok_080Model[38] = new ModelRendererTurbo(this, 193, 25, textureXY, textureXY); // Box 39
		brigadelok_080Model[39] = new ModelRendererTurbo(this, 201, 25, textureXY, textureXY); // Box 40
		brigadelok_080Model[40] = new ModelRendererTurbo(this, 201, 49, textureXY, textureXY); // Box 41
		brigadelok_080Model[41] = new ModelRendererTurbo(this, 65, 33, textureXY, textureXY); // Box 42
		brigadelok_080Model[42] = new ModelRendererTurbo(this, 209, 41, textureXY, textureXY); // Box 43
		brigadelok_080Model[43] = new ModelRendererTurbo(this, 241, 49, textureXY, textureXY); // Box 45
		brigadelok_080Model[44] = new ModelRendererTurbo(this, 1, 57, textureXY, textureXY); // Box 46
		brigadelok_080Model[45] = new ModelRendererTurbo(this, 97, 67, textureXY, textureXY); // Box 47
		brigadelok_080Model[46] = new ModelRendererTurbo(this, 241, 57, textureXY, textureXY); // Box 48
		brigadelok_080Model[47] = new ModelRendererTurbo(this, 136, 65, textureXY, textureXY); // Box 49
		brigadelok_080Model[48] = new ModelRendererTurbo(this, 153, 65, textureXY, textureXY); // Box 50
		brigadelok_080Model[49] = new ModelRendererTurbo(this, 1, 81, textureXY, textureXY); // Box 51
		brigadelok_080Model[50] = new ModelRendererTurbo(this, 193, 73, textureXY, textureXY); // Box 52
		brigadelok_080Model[51] = new ModelRendererTurbo(this, 57, 97, textureXY, textureXY); // Box 53
		brigadelok_080Model[52] = new ModelRendererTurbo(this, 177, 65, textureXY, textureXY); // Box 54
		brigadelok_080Model[53] = new ModelRendererTurbo(this, 217, 73, textureXY, textureXY); // Box 55
		brigadelok_080Model[54] = new ModelRendererTurbo(this, 185, 105, textureXY, textureXY); // Box 56
		brigadelok_080Model[55] = new ModelRendererTurbo(this, 193, 97, textureXY, textureXY); // Box 57
		brigadelok_080Model[56] = new ModelRendererTurbo(this, 1, 105, textureXY, textureXY); // Box 58
		brigadelok_080Model[57] = new ModelRendererTurbo(this, 1, 113, textureXY, textureXY); // Box 59
		brigadelok_080Model[58] = new ModelRendererTurbo(this, 97, 113, textureXY, textureXY); // Box 60
		brigadelok_080Model[59] = new ModelRendererTurbo(this, 57, 81, textureXY, textureXY); // Box 61
		brigadelok_080Model[60] = new ModelRendererTurbo(this, 145, 113, textureXY, textureXY); // Box 62
		brigadelok_080Model[61] = new ModelRendererTurbo(this, 73, 81, textureXY, textureXY); // Box 63
		brigadelok_080Model[62] = new ModelRendererTurbo(this, 161, 113, textureXY, textureXY); // Box 64
		brigadelok_080Model[63] = new ModelRendererTurbo(this, 97, 57, textureXY, textureXY); // Box 65
		brigadelok_080Model[64] = new ModelRendererTurbo(this, 1, 121, textureXY, textureXY); // Box 66
		brigadelok_080Model[65] = new ModelRendererTurbo(this, 25, 121, textureXY, textureXY); // Box 67
		brigadelok_080Model[66] = new ModelRendererTurbo(this, 25, 57, textureXY, textureXY); // Box 68
		brigadelok_080Model[67] = new ModelRendererTurbo(this, 41, 121, textureXY, textureXY); // Box 69
		brigadelok_080Model[68] = new ModelRendererTurbo(this, 241, 33, textureXY, textureXY); // Box 71
		brigadelok_080Model[69] = new ModelRendererTurbo(this, 1, 49, textureXY, textureXY); // Box 72
		brigadelok_080Model[70] = new ModelRendererTurbo(this, 25, 49, textureXY, textureXY); // Box 73
		brigadelok_080Model[71] = new ModelRendererTurbo(this, 225, 57, textureXY, textureXY); // Box 74
		brigadelok_080Model[72] = new ModelRendererTurbo(this, 1, 65, textureXY, textureXY); // Box 75
		brigadelok_080Model[73] = new ModelRendererTurbo(this, 25, 65, textureXY, textureXY); // Box 76
		brigadelok_080Model[74] = new ModelRendererTurbo(this, 57, 65, textureXY, textureXY); // Box 77
		brigadelok_080Model[75] = new ModelRendererTurbo(this, 97, 65, textureXY, textureXY); // Box 78
		brigadelok_080Model[76] = new ModelRendererTurbo(this, 121, 57, textureXY, textureXY); // Box 79
		brigadelok_080Model[77] = new ModelRendererTurbo(this, 73, 65, textureXY, textureXY); // Box 80
		brigadelok_080Model[78] = new ModelRendererTurbo(this, 121, 65, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // SimpleRotate
		brigadelok_080Model[79] = new ModelRendererTurbo(this, 153, 65, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // SimpleRotate
		brigadelok_080Model[80] = new ModelRendererTurbo(this, 33, 73, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // SimpleRotate
		brigadelok_080Model[81] = new ModelRendererTurbo(this, 49, 73, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // SimpleRotate
		brigadelok_080Model[82] = new ModelRendererTurbo(this, 121, 73, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // SimpleRotate
		brigadelok_080Model[83] = new ModelRendererTurbo(this, 153, 73, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // SimpleRotate
		brigadelok_080Model[84] = new ModelRendererTurbo(this, 1, 81, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // SimpleRotate
		brigadelok_080Model[85] = new ModelRendererTurbo(this, 1, 89, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // SimpleRotate
		brigadelok_080Model[86] = new ModelRendererTurbo(this, 81, 41, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // SimpleRotate
		brigadelok_080Model[87] = new ModelRendererTurbo(this, 41, 105, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // SimpleRotate
		brigadelok_080Model[88] = new ModelRendererTurbo(this, 81, 121, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // SimpleRotate
		brigadelok_080Model[89] = new ModelRendererTurbo(this, 113, 121, textureXY, textureXY, StaticModelAnimator.tagSimpleRotate); // SimpleRotate
		brigadelok_080Model[90] = new ModelRendererTurbo(this, 185, 121, textureXY, textureXY); // Box 93
		brigadelok_080Model[91] = new ModelRendererTurbo(this, 25, 137, textureXY, textureXY); // Box 94
		brigadelok_080Model[92] = new ModelRendererTurbo(this, 97, 137, textureXY, textureXY); // Box 95
		brigadelok_080Model[93] = new ModelRendererTurbo(this, 129, 129, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // simplepiston
		brigadelok_080Model[94] = new ModelRendererTurbo(this, 241, 89, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // simplepiston
		brigadelok_080Model[95] = new ModelRendererTurbo(this, 169, 137, textureXY, textureXY, StaticModelAnimator.tagAdvancedPiston); // advandedpiston
		brigadelok_080Model[96] = new ModelRendererTurbo(this, 249, 41, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // Box 99
		brigadelok_080Model[97] = new ModelRendererTurbo(this, 209, 137, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // simplepiston
		brigadelok_080Model[98] = new ModelRendererTurbo(this, 201, 73, textureXY, textureXY); // Box 99
		brigadelok_080Model[99] = new ModelRendererTurbo(this, 57, 49, textureXY, textureXY); // Box 100
		brigadelok_080Model[100] = new ModelRendererTurbo(this, 57, 81, textureXY, textureXY); // Box 101
		brigadelok_080Model[101] = new ModelRendererTurbo(this, 177, 145, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // simplepiston
		brigadelok_080Model[102] = new ModelRendererTurbo(this, 137, 97, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // simplepiston
		brigadelok_080Model[103] = new ModelRendererTurbo(this, 1, 145, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // simplepiston
		brigadelok_080Model[104] = new ModelRendererTurbo(this, 1, 153, textureXY, textureXY, StaticModelAnimator.tagAdvancedPiston); // advandedpiston
		brigadelok_080Model[105] = new ModelRendererTurbo(this, 209, 49, textureXY, textureXY, StaticModelAnimator.tagSimplePiston); // Box 106
		brigadelok_080Model[106] = new ModelRendererTurbo(this, 201, 81, textureXY, textureXY); // Box 107
		brigadelok_080Model[107] = new ModelRendererTurbo(this, 57, 105, textureXY, textureXY); // Box 108
		brigadelok_080Model[108] = new ModelRendererTurbo(this, 65, 121, textureXY, textureXY); // Box 109
		brigadelok_080Model[109] = new ModelRendererTurbo(this, 209, 57, textureXY, textureXY); // Box 110
		brigadelok_080Model[110] = new ModelRendererTurbo(this, 59, 38, textureXY, textureXY); // Box 111
		brigadelok_080Model[111] = new ModelRendererTurbo(this, 0, 0, textureXY, textureXY, "smoke 3 232323"); // Box 112

		brigadelok_080Model[0].addBox(0F, 0F, 0F, 32, 10, 3, 0F); // Box 0
		brigadelok_080Model[0].setRotationPoint(-25F, -8.5F, -1.5F);

		brigadelok_080Model[1].addBox(0F, 0F, 0F, 32, 8, 6, 0F); // Box 2
		brigadelok_080Model[1].setRotationPoint(-25F, -7.5F, -3F);

		brigadelok_080Model[2].addBox(0F, 0F, 0F, 32, 6, 8, 0F); // Box 3
		brigadelok_080Model[2].setRotationPoint(-25F, -6.5F, -4F);

		brigadelok_080Model[3].addBox(0F, 0F, 0F, 32, 3, 10, 0F); // Box 4
		brigadelok_080Model[3].setRotationPoint(-25F, -5F, -5F);

		brigadelok_080Model[4].addBox(0F, 0F, 0F, 8, 6, 18, 0F); // Box 5
		brigadelok_080Model[4].setRotationPoint(-23F, -5F, -9F);

		brigadelok_080Model[5].addBox(0F, 0F, 0F, 22, 7, 18, 0F); // Box 6
		brigadelok_080Model[5].setRotationPoint(-15F, -6F, -9F);

		brigadelok_080Model[6].addBox(0F, 0F, 0F, 2, 3, 3, 0F); // Box 7
		brigadelok_080Model[6].setRotationPoint(-15F, -7.5F, -7.5F);

		brigadelok_080Model[7].addBox(0F, 0F, 0F, 3, 3, 2, 0F); // Box 8
		brigadelok_080Model[7].setRotationPoint(-15.5F, -7.5F, -7F);

		brigadelok_080Model[8].addBox(0F, 0F, 0F, 2, 3, 3, 0F); // Box 9
		brigadelok_080Model[8].setRotationPoint(-6.5F, -8.5F, -7.5F);

		brigadelok_080Model[9].addBox(0F, 0F, 0F, 3, 3, 2, 0F); // Box 10
		brigadelok_080Model[9].setRotationPoint(-7F, -8.5F, -7F);

		brigadelok_080Model[10].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 11
		brigadelok_080Model[10].setRotationPoint(-6F, -9.5F, -6.5F);

		brigadelok_080Model[11].addBox(0F, 0F, 0F, 2, 1, 2, 0F); // Box 12
		brigadelok_080Model[11].setRotationPoint(-6.5F, -10.5F, -7F);

		brigadelok_080Model[12].addBox(0F, 0F, 0F, 4, 1, 3, 0F); // Box 13
		brigadelok_080Model[12].setRotationPoint(-2F, -6.5F, -7.5F);

		brigadelok_080Model[13].addBox(0F, 0F, 0F, 4, 1, 3, 0F); // Box 14
		brigadelok_080Model[13].setRotationPoint(3F, -6.5F, -7.5F);

		brigadelok_080Model[14].addBox(0F, 0F, 0F, 3, 1, 3, 0F); // Box 15
		brigadelok_080Model[14].setRotationPoint(-24F, -9F, -1.5F);

		brigadelok_080Model[15].addBox(0F, 0F, 0F, 2, 13, 2, 0F); // Box 16
		brigadelok_080Model[15].setRotationPoint(-23.5F, -21.5F, -1F);

		brigadelok_080Model[16].addBox(0F, 0F, 0F, 3, 6, 3, 0F); // Box 17
		brigadelok_080Model[16].setRotationPoint(-24F, -27F, -1.5F);

		brigadelok_080Model[17].addBox(0F, 0F, 0F, 5, 9, 2, 0F); // Box 18
		brigadelok_080Model[17].setRotationPoint(-18F, -16.5F, -1F);

		brigadelok_080Model[18].addBox(0F, 0F, 0F, 4, 9, 4, 0F); // Box 19
		brigadelok_080Model[18].setRotationPoint(-17.5F, -16.5F, -2F);

		brigadelok_080Model[19].addBox(0F, 0F, 0F, 2, 9, 5, 0F); // Box 20
		brigadelok_080Model[19].setRotationPoint(-16.5F, -16.5F, -2.5F);

		brigadelok_080Model[20].addBox(0F, 0F, 0F, 5, 13, 3, 0F); // Box 21
		brigadelok_080Model[20].setRotationPoint(-9.5F, -20.5F, -1.5F);

		brigadelok_080Model[21].addBox(0F, 0F, 0F, 4, 13, 4, 0F); // Box 22
		brigadelok_080Model[21].setRotationPoint(-9F, -20.5F, -2F);

		brigadelok_080Model[22].addBox(0F, 0F, 0F, 2, 13, 5, 0F); // Box 23
		brigadelok_080Model[22].setRotationPoint(-8F, -20.5F, -2.5F);

		brigadelok_080Model[23].addBox(0F, 0F, 0F, 2, 3, 2, 0F); // Box 24
		brigadelok_080Model[23].setRotationPoint(-8F, -23.5F, -1F);

		brigadelok_080Model[24].addBox(0F, 0F, 0F, 3, 1, 1, 0F); // Box 25
		brigadelok_080Model[24].setRotationPoint(-11F, -22F, -0.5F);

		brigadelok_080Model[25].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 26
		brigadelok_080Model[25].setRotationPoint(-10.5F, -21F, -0.5F);

		brigadelok_080Model[26].addBox(0F, 0F, 0F, 3, 7, 3, 0F); // Box 27
		brigadelok_080Model[26].setRotationPoint(-0.5F, -14.5F, -1.5F);

		brigadelok_080Model[27].addBox(0F, 0F, 0F, 4, 7, 2, 0F); // Box 28
		brigadelok_080Model[27].setRotationPoint(-1F, -14.5F, -1F);

		brigadelok_080Model[28].addBox(0F, 0F, 0F, 2, 7, 4, 0F); // Box 29
		brigadelok_080Model[28].setRotationPoint(0F, -14.5F, -2F);

		brigadelok_080Model[29].addBox(0F, 0F, 0F, 1, 2, 5, 0F); // Box 30
		brigadelok_080Model[29].setRotationPoint(0.5F, -9.5F, -2.5F);

		brigadelok_080Model[30].addBox(0F, 0F, 0F, 1, 2, 6, 0F); // Box 31
		brigadelok_080Model[30].setRotationPoint(-16F, -9.5F, -3F);

		brigadelok_080Model[31].addBox(0F, 0F, 0F, 35, 3, 12, 0F); // Box 32
		brigadelok_080Model[31].setRotationPoint(-24F, 2.5F, -6F);

		brigadelok_080Model[32].addBox(0F, 0F, 0F, 1, 2, 20, 0F); // Box 33
		brigadelok_080Model[32].setRotationPoint(-25F, 2.5F, -10F);

		brigadelok_080Model[33].addBox(0F, 0F, 0F, 1, 3, 14, 0F); // Box 34
		brigadelok_080Model[33].setRotationPoint(-25.5F, 5F, -7F);

		brigadelok_080Model[34].addBox(0F, 0F, 0F, 4, 3, 12, 0F); // Box 35
		brigadelok_080Model[34].setRotationPoint(-24.5F, 4.5F, -6F);

		brigadelok_080Model[35].addBox(0F, 0F, 0F, 3, 5, 18, 0F); // Box 36
		brigadelok_080Model[35].setRotationPoint(-21F, 2.5F, -9F);

		brigadelok_080Model[36].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 37
		brigadelok_080Model[36].setRotationPoint(-19F, 1F, -7.5F);

		brigadelok_080Model[37].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 38
		brigadelok_080Model[37].setRotationPoint(-20.5F, 1F, -7.5F);

		brigadelok_080Model[38].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 39
		brigadelok_080Model[38].setRotationPoint(-20.5F, 1F, 6.5F);

		brigadelok_080Model[39].addBox(0F, 0F, 0F, 1, 2, 1, 0F); // Box 40
		brigadelok_080Model[39].setRotationPoint(-19F, 1F, 6.5F);

		brigadelok_080Model[40].addBox(0F, 0F, 0F, 1, 2, 16, 0F); // Box 41
		brigadelok_080Model[40].setRotationPoint(-24.5F, 7.5F, -8F);

		brigadelok_080Model[41].addBox(0F, 0F, 0F, 1, 2, 2, 0F); // Box 42
		brigadelok_080Model[41].setRotationPoint(-27.5F, 5.5F, -1F);

		brigadelok_080Model[42].addBox(0F, 0F, 0F, 1, 3, 3, 0F); // Box 43
		brigadelok_080Model[42].setRotationPoint(-28.5F, 5F, -1.5F);

		brigadelok_080Model[43].addBox(0F, 0F, 0F, 2, 2, 3, 0F); // Box 45
		brigadelok_080Model[43].setRotationPoint(-12.5F, -7.5F, 4.5F);

		brigadelok_080Model[44].addBox(0F, 0F, 0F, 3, 2, 2, 0F); // Box 46
		brigadelok_080Model[44].setRotationPoint(-13F, -7.5F, 5F);

		brigadelok_080Model[45].addBox(0F, 0F, 0F, 1, 25, 16, 0F); // Box 47
		brigadelok_080Model[45].setRotationPoint(7F, -22.5F, -8F);

		brigadelok_080Model[46].addBox(0F, 0F, 0F, 5, 25, 1, 0F); // Box 48
		brigadelok_080Model[46].setRotationPoint(7F, -22F, -9F);

		brigadelok_080Model[47].addBox(0F, 0F, 0F, 5, 25, 1, 0F); // Box 49
		brigadelok_080Model[47].setRotationPoint(7F, -22F, 8F);

		brigadelok_080Model[48].addBox(0F, 0F, 0F, 1, 29, 18, 0F); // Box 50
		brigadelok_080Model[48].setRotationPoint(27F, -22.5F, -9F);

		brigadelok_080Model[49].addBox(0F, 0F, 0F, 15, 1, 18, 0F); // Box 51
		brigadelok_080Model[49].setRotationPoint(12F, 6F, -9F);

		brigadelok_080Model[50].addBox(0F, 0F, 0F, 1, 4, 18, 0F); // Box 52
		brigadelok_080Model[50].setRotationPoint(11F, 3F, -9F);

		brigadelok_080Model[51].addBox(0F, 0F, 0F, 4, 1, 16, 0F); // Box 53
		brigadelok_080Model[51].setRotationPoint(8F, 1.5F, -8F);

		brigadelok_080Model[52].addBox(0F, 0F, 0F, 9, 12, 1, 0F); // Box 54
		brigadelok_080Model[52].setRotationPoint(12F, -6F, 8F);

		brigadelok_080Model[53].addBox(0F, 0F, 0F, 9, 12, 1, 0F); // Box 55
		brigadelok_080Model[53].setRotationPoint(12F, -6F, -9F);

		brigadelok_080Model[54].addBox(0F, 0F, 0F, 23, 1, 10, 0F); // Box 56
		brigadelok_080Model[54].setRotationPoint(6F, -24.5F, -5F);

		brigadelok_080Model[55].addBox(0F, 0F, 0F, 23, 1, 3, 0F); // Box 57
		brigadelok_080Model[55].setRotationPoint(6F, -23.5F, -8F);

		brigadelok_080Model[56].addBox(0F, 0F, 0F, 23, 1, 3, 0F); // Box 58
		brigadelok_080Model[56].setRotationPoint(6F, -23.5F, 5F);

		brigadelok_080Model[57].addBox(0F, 0F, 0F, 23, 1, 2, 0F); // Box 59
		brigadelok_080Model[57].setRotationPoint(6F, -22.5F, 8F);

		brigadelok_080Model[58].addBox(0F, 0F, 0F, 23, 1, 2, 0F); // Box 60
		brigadelok_080Model[58].setRotationPoint(6F, -22.5F, -10F);

		brigadelok_080Model[59].addBox(0F, 0F, 0F, 1, 1, 10, 0F); // Box 61
		brigadelok_080Model[59].setRotationPoint(7F, -23.5F, -5F);

		brigadelok_080Model[60].addBox(0F, 0F, 0F, 1, 1, 10, 0F); // Box 62
		brigadelok_080Model[60].setRotationPoint(27F, -23.5F, -5F);

		brigadelok_080Model[61].addBox(0F, 0F, 0F, 5, 2, 6, 0F); // Box 63
		brigadelok_080Model[61].setRotationPoint(16F, -26.5F, -3F);

		brigadelok_080Model[62].addBox(0F, 0F, 0F, 7, 1, 7, 0F); // Box 64
		brigadelok_080Model[62].setRotationPoint(15F, -27.5F, -3.5F);

		brigadelok_080Model[63].addBox(0F, 0F, 0F, 7, 1, 4, 0F); // Box 65
		brigadelok_080Model[63].setRotationPoint(15F, -28F, -2F);

		brigadelok_080Model[64].addBox(0F, 0F, 0F, 6, 11, 10, 0F); // Box 66
		brigadelok_080Model[64].setRotationPoint(8F, -5.5F, -5F);

		brigadelok_080Model[65].addBox(0F, 0F, 0F, 6, 2, 7, 0F); // Box 67
		brigadelok_080Model[65].setRotationPoint(7.5F, -7.5F, -3.5F);

		brigadelok_080Model[66].addBox(0F, 0F, 0F, 5, 1, 4, 0F); // Box 68
		brigadelok_080Model[66].setRotationPoint(7.5F, -8.5F, -2F);

		brigadelok_080Model[67].addBox(0F, 0F, 0F, 4, 2, 12, 0F); // Box 69
		brigadelok_080Model[67].setRotationPoint(7F, 5.5F, -6F);

		brigadelok_080Model[68].addBox(0F, 0F, 0F, 3, 2, 1, 0F); // Box 71
		brigadelok_080Model[68].setRotationPoint(4F, 5.5F, -6F);

		brigadelok_080Model[69].addBox(0F, 0F, 0F, 3, 2, 1, 0F); // Box 72
		brigadelok_080Model[69].setRotationPoint(4F, 5.5F, 5F);

		brigadelok_080Model[70].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 73
		brigadelok_080Model[70].setRotationPoint(-2F, 5.5F, -6F);

		brigadelok_080Model[71].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 74
		brigadelok_080Model[71].setRotationPoint(-2F, 5.5F, 5F);

		brigadelok_080Model[72].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 75
		brigadelok_080Model[72].setRotationPoint(-14F, 5.5F, -6F);

		brigadelok_080Model[73].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 76
		brigadelok_080Model[73].setRotationPoint(-14F, 5.5F, 5F);

		brigadelok_080Model[74].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 77
		brigadelok_080Model[74].setRotationPoint(-8F, 5.5F, 5F);

		brigadelok_080Model[75].addBox(0F, 0F, 0F, 5, 2, 1, 0F); // Box 78
		brigadelok_080Model[75].setRotationPoint(-8F, 5.5F, -6F);

		brigadelok_080Model[76].addBox(0F, 0F, 0F, 3, 2, 1, 0F); // Box 79
		brigadelok_080Model[76].setRotationPoint(-18F, 5.5F, -6F);

		brigadelok_080Model[77].addBox(0F, 0F, 0F, 3, 2, 1, 0F); // Box 80
		brigadelok_080Model[77].setRotationPoint(-18F, 5.5F, 5F);

		brigadelok_080Model[78].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // SimpleRotate
		brigadelok_080Model[78].setRotationPoint(-14.5F, 6F, -5F);

		brigadelok_080Model[79].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // SimpleRotate
		brigadelok_080Model[79].setRotationPoint(-8.5F, 6F, -5F);

		brigadelok_080Model[80].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // SimpleRotate
		brigadelok_080Model[80].setRotationPoint(3.5F, 6F, -5F);

		brigadelok_080Model[81].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // SimpleRotate
		brigadelok_080Model[81].setRotationPoint(-2.5F, 6F, -5F);

		brigadelok_080Model[82].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // SimpleRotate
		brigadelok_080Model[82].setRotationPoint(3.5F, 6F, 5F);

		brigadelok_080Model[83].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // SimpleRotate
		brigadelok_080Model[83].setRotationPoint(-2.5F, 6F, 5F);

		brigadelok_080Model[84].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // SimpleRotate
		brigadelok_080Model[84].setRotationPoint(-8.5F, 6F, 5F);

		brigadelok_080Model[85].addBox(-2.5F, -2.5F, 0F, 5, 5, 0, 0F); // SimpleRotate
		brigadelok_080Model[85].setRotationPoint(-14.5F, 6F, 5F);

		brigadelok_080Model[86].addBox(-0.5F, -0.5F, 0F, 1, 1, 13, 0F); // SimpleRotate
		brigadelok_080Model[86].setRotationPoint(3.5F, 6F, -6.5F);

		brigadelok_080Model[87].addBox(-0.5F, -0.5F, 0F, 1, 1, 13, 0F); // SimpleRotate
		brigadelok_080Model[87].setRotationPoint(-2.5F, 6F, -6.5F);

		brigadelok_080Model[88].addBox(-0.5F, -0.5F, 0F, 1, 1, 13, 0F); // SimpleRotate
		brigadelok_080Model[88].setRotationPoint(-8.5F, 6F, -6.5F);

		brigadelok_080Model[89].addBox(-0.5F, -0.5F, 0F, 1, 1, 13, 0F); // SimpleRotate
		brigadelok_080Model[89].setRotationPoint(-14.5F, 6F, -6.5F);

		brigadelok_080Model[90].addBox(0F, 0F, 0F, 16, 1, 12, 0F); // Box 93
		brigadelok_080Model[90].setRotationPoint(11F, 6.5F, -6F);

		brigadelok_080Model[91].addBox(0F, 0F, 0F, 30, 1, 11, 0F); // Box 94
		brigadelok_080Model[91].setRotationPoint(-23F, 1F, -5.5F);

		brigadelok_080Model[92].addBox(0F, 0F, 0F, 30, 1, 9, 0F); // Box 95
		brigadelok_080Model[92].setRotationPoint(-23F, 2F, -4.5F);

		brigadelok_080Model[93].addBox(-9.5F, -0.5F, -0.5F, 19, 1, 1, 0F); // simplepiston
		brigadelok_080Model[93].setRotationPoint(-5.5F, 6F, 7F);

		brigadelok_080Model[94].addBox(-9F, -0.5F, -0.5F, 4, 1, 1, 0F); // simplepiston
		brigadelok_080Model[94].setRotationPoint(-10.5F, 5F, 7.5F);

		brigadelok_080Model[95].addBox(-9F, -0.5F, -0.5F, 18, 1, 1, 0F); // advandedpiston
		brigadelok_080Model[95].setRotationPoint(-10.5F, 4.75F, 8F);
		brigadelok_080Model[95].rotateAngleZ = 6.14355897F;

		brigadelok_080Model[96].addBox(-1F, 0F, -0.5F, 1, 4, 1, 0F); // Box 99
		brigadelok_080Model[96].setRotationPoint(-9.5F, 1.5F, 8F);

		brigadelok_080Model[97].addBox(-7F, -0.5F, -0.5F, 14, 1, 1, 0F); // simplepiston
		brigadelok_080Model[97].setRotationPoint(-8.75F, 5.5F, 7.5F);
		brigadelok_080Model[97].rotateAngleZ = 6.20900881F;

		brigadelok_080Model[98].addBox(0F, 0F, 0F, 3, 1, 1, 0F); // Box 99
		brigadelok_080Model[98].setRotationPoint(4F, -19F, -0.5F);

		brigadelok_080Model[99].addBox(0F, 0F, 0F, 1, 8, 1, 0F); // Box 100
		brigadelok_080Model[99].setRotationPoint(4F, -27F, -0.5F);

		brigadelok_080Model[100].addBox(0F, 0F, 0F, 2, 2, 2, 0F); // Box 101
		brigadelok_080Model[100].setRotationPoint(3.5F, -26F, -1F);

		brigadelok_080Model[101].addBox(-9.5F, -0.5F, -0.5F, 19, 1, 1, 0F); // simplepiston
		brigadelok_080Model[101].setRotationPoint(-5.5F, 6F, -7F);

		brigadelok_080Model[102].addBox(-9F, -0.5F, -0.5F, 4, 1, 1, 0F); // simplepiston
		brigadelok_080Model[102].setRotationPoint(-10.5F, 5F, -7.5F);

		brigadelok_080Model[103].addBox(-7F, -0.5F, -0.5F, 14, 1, 1, 0F); // simplepiston
		brigadelok_080Model[103].setRotationPoint(-8.75F, 5.5F, -7.5F);
		brigadelok_080Model[103].rotateAngleZ = 6.20900881F;

		brigadelok_080Model[104].addBox(-9F, -0.5F, -0.5F, 18, 1, 1, 0F); // advandedpiston
		brigadelok_080Model[104].setRotationPoint(-10.5F, 4.75F, -8F);
		brigadelok_080Model[104].rotateAngleZ = 6.14355897F;

		brigadelok_080Model[105].addBox(-1F, 0F, -0.5F, 1, 4, 1, 0F); // Box 106
		brigadelok_080Model[105].setRotationPoint(-9.5F, 1.5F, -8F);

		brigadelok_080Model[106].addBox(-1F, 0F, -0.5F, 1, 1, 3, 0F); // Box 107
		brigadelok_080Model[106].setRotationPoint(-9.5F, 1.5F, 5F);

		brigadelok_080Model[107].addBox(-1F, 0F, -0.5F, 1, 1, 3, 0F); // Box 108
		brigadelok_080Model[107].setRotationPoint(-9.5F, 1.5F, -7F);

		brigadelok_080Model[108].addBox(0F, 0F, 0F, 13, 1, 1, 0F); // Box 109
		brigadelok_080Model[108].setRotationPoint(-6F, -8.5F, 3.5F);

		brigadelok_080Model[109].addBox(0F, 0F, 0F, 2, 2, 1, 0F); // Box 110
		brigadelok_080Model[109].setRotationPoint(-8F, -8.5F, 3F);
		brigadelok_080Model[109].rotateAngleX = 0.78539816F;

		brigadelok_080Model[110].addBox(0F, 0F, 0F, 1, 3, 3, 0F); // Box 111
		brigadelok_080Model[110].setRotationPoint(-26.5F, 5F, -1.5F);

		brigadelok_080Model[111].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 17
		brigadelok_080Model[111].setRotationPoint(-24F, -34F, -1.5F);

		fixRotation(brigadelok_080Model, false, true, true);

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		for(int i = 0; i < 112; i++)
		{
			brigadelok_080Model[i].render(f5);
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
	}

	private ModelRendererTurbo brigadelok_080Model[];
}