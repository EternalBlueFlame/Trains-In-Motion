//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator:
// Created on:10.01.2017 - 11:25:17
// Last changed on: 10.01.2017 - 11:25:17

package trains.models.rollingstock;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import trains.models.tmt.ModelRendererTurbo;

public class Pullmans_Palace extends ModelBase
{
	int textureX = 512;
	int textureY = 512;

	public Pullmans_Palace()
	{
		pullmans_palaceModel = new ModelRendererTurbo[79];
		pullmans_palaceModel[0] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 0
		pullmans_palaceModel[1] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 1
		pullmans_palaceModel[2] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 2
		pullmans_palaceModel[3] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 3
		pullmans_palaceModel[4] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 4
		pullmans_palaceModel[5] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 5
		pullmans_palaceModel[6] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 6
		pullmans_palaceModel[7] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 8
		pullmans_palaceModel[8] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 9
		pullmans_palaceModel[9] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 10
		pullmans_palaceModel[10] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 11
		pullmans_palaceModel[11] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 12
		pullmans_palaceModel[12] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 13
		pullmans_palaceModel[13] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 14
		pullmans_palaceModel[14] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 15
		pullmans_palaceModel[15] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 16
		pullmans_palaceModel[16] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 17
		pullmans_palaceModel[17] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 18
		pullmans_palaceModel[18] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 19
		pullmans_palaceModel[19] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 38
		pullmans_palaceModel[20] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel 1
		pullmans_palaceModel[21] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 39
		pullmans_palaceModel[22] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 40
		pullmans_palaceModel[23] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 41
		pullmans_palaceModel[24] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 42
		pullmans_palaceModel[25] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 43
		pullmans_palaceModel[26] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 44
		pullmans_palaceModel[27] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 45
		pullmans_palaceModel[28] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 46
		pullmans_palaceModel[29] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 47
		pullmans_palaceModel[30] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 48
		pullmans_palaceModel[31] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 49
		pullmans_palaceModel[32] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 50
		pullmans_palaceModel[33] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 51
		pullmans_palaceModel[34] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 52
		pullmans_palaceModel[35] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 53
		pullmans_palaceModel[36] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 54
		pullmans_palaceModel[37] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 55
		pullmans_palaceModel[38] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 56
		pullmans_palaceModel[39] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 57
		pullmans_palaceModel[40] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 58
		pullmans_palaceModel[41] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 59
		pullmans_palaceModel[42] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 60
		pullmans_palaceModel[43] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 61
		pullmans_palaceModel[44] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 62
		pullmans_palaceModel[45] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 63
		pullmans_palaceModel[46] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 64
		pullmans_palaceModel[47] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 65
		pullmans_palaceModel[48] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 66
		pullmans_palaceModel[49] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 67
		pullmans_palaceModel[50] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 68
		pullmans_palaceModel[51] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 69
		pullmans_palaceModel[52] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 70
		pullmans_palaceModel[53] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 71
		pullmans_palaceModel[54] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 72
		pullmans_palaceModel[55] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 73
		pullmans_palaceModel[56] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 74
		pullmans_palaceModel[57] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 75
		pullmans_palaceModel[58] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 76
		pullmans_palaceModel[59] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 77
		pullmans_palaceModel[60] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 78
		pullmans_palaceModel[61] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 79
		pullmans_palaceModel[62] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 80
		pullmans_palaceModel[63] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 81
		pullmans_palaceModel[64] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 82
		pullmans_palaceModel[65] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 83
		pullmans_palaceModel[66] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 84
		pullmans_palaceModel[67] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 85
		pullmans_palaceModel[68] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 87
		pullmans_palaceModel[69] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 88
		pullmans_palaceModel[70] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 89
		pullmans_palaceModel[71] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 90
		pullmans_palaceModel[72] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 91
		pullmans_palaceModel[73] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 92
		pullmans_palaceModel[74] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 93
		pullmans_palaceModel[75] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 94
		pullmans_palaceModel[76] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 95
		pullmans_palaceModel[77] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 96
		pullmans_palaceModel[78] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Box 97

		pullmans_palaceModel[0].addBox(-50F, -0.5F, -10F, 100, 1, 20, 0F); // Box 0
		pullmans_palaceModel[0].setRotationPoint(0F, 16F, 0F);

		pullmans_palaceModel[1].addBox(-40F, -15F, 0F, 80, 30, 1, 0F); // Box 1
		pullmans_palaceModel[1].setRotationPoint(0F, 0.5F, -10F);

		pullmans_palaceModel[2].addBox(-40F, -15F, 0F, 80, 30, 1, 0F); // Box 2
		pullmans_palaceModel[2].setRotationPoint(0F, 0.5F, 9F);

		pullmans_palaceModel[3].addBox(0F, -8F, -3.5F, 1, 12, 7, 0F); // Box 3
		pullmans_palaceModel[3].setRotationPoint(49F, 11.5F, 6.5F);

		pullmans_palaceModel[4].addBox(0F, -8F, -3.5F, 1, 12, 7, 0F); // Box 4
		pullmans_palaceModel[4].setRotationPoint(49F, 11.5F, -6.5F);

		pullmans_palaceModel[5].addBox(0F, -8F, -3.5F, 1, 12, 7, 0F); // Box 5
		pullmans_palaceModel[5].setRotationPoint(-50F, 11.5F, -6.5F);

		pullmans_palaceModel[6].addBox(0F, -8F, -3.5F, 1, 12, 7, 0F); // Box 6
		pullmans_palaceModel[6].setRotationPoint(-50F, 11.5F, 6.5F);

		pullmans_palaceModel[7].addBox(-0.5F, -15F, -9F, 1, 30, 18, 0F); // Box 8
		pullmans_palaceModel[7].setRotationPoint(-39.5F, 0.5F, 0F);

		pullmans_palaceModel[8].addBox(-0.5F, -15F, -9F, 1, 30, 18, 0F); // Box 9
		pullmans_palaceModel[8].setRotationPoint(39.5F, 0.5F, 0F);

		pullmans_palaceModel[9].addBox(-50F, -0.5F, -10F, 100, 1, 20, 0F); // Box 10
		pullmans_palaceModel[9].setRotationPoint(0F, -15F, 0F);

		pullmans_palaceModel[10].addBox(-50F, -0.5F, -9F, 100, 2, 18, 0F); // Box 11
		pullmans_palaceModel[10].setRotationPoint(0F, -17F, 0F);

		pullmans_palaceModel[11].addBox(-50F, -0.5F, -7F, 100, 2, 14, 0F); // Box 12
		pullmans_palaceModel[11].setRotationPoint(0F, -19F, 0F);

		pullmans_palaceModel[12].addBox(-50F, -0.5F, -6F, 100, 1, 12, 0F); // Box 13
		pullmans_palaceModel[12].setRotationPoint(0F, -20F, 0F);

		pullmans_palaceModel[13].addBox(-50F, -0.5F, -4F, 100, 1, 8, 0F); // Box 14
		pullmans_palaceModel[13].setRotationPoint(0F, -21F, 0F);

		pullmans_palaceModel[14].addBox(-50F, -0.5F, -2F, 100, 1, 4, 0F); // Box 15
		pullmans_palaceModel[14].setRotationPoint(0F, -22F, 0F);

		pullmans_palaceModel[15].addBox(-0.5F, -0.5F, -3F, 1, 1, 6, 0F); // Box 16
		pullmans_palaceModel[15].setRotationPoint(49.5F, 6.5F, 0F);

		pullmans_palaceModel[16].addBox(-0.5F, -0.5F, -3F, 1, 1, 6, 0F); // Box 17
		pullmans_palaceModel[16].setRotationPoint(49.5F, 8.5F, 0F);

		pullmans_palaceModel[17].addBox(-0.5F, -0.5F, -3F, 1, 1, 6, 0F); // Box 18
		pullmans_palaceModel[17].setRotationPoint(-49.5F, 8.5F, 0F);

		pullmans_palaceModel[18].addBox(-0.5F, -0.5F, -3F, 1, 1, 6, 0F); // Box 19
		pullmans_palaceModel[18].setRotationPoint(-49.5F, 6.5F, 0F);

		pullmans_palaceModel[19].addBox(-0.5F, -0.5F, -0.5F, 3, 7, 1, 0F); // Box 38
		pullmans_palaceModel[19].setRotationPoint(-31F, 17F, -5.5F);

		pullmans_palaceModel[20].addBox(-5F, -5F, -0.5F, 10, 10, 0, 0F); // Wheel 1
		pullmans_palaceModel[20].setRotationPoint(-30F, 22F, -4.5F);

		pullmans_palaceModel[21].addBox(-0.5F, -0.5F, -0.5F, 3, 3, 1, 0F); // Box 39
		pullmans_palaceModel[21].setRotationPoint(-34F, 17F, -5.5F);

		pullmans_palaceModel[22].addBox(-0.5F, -0.5F, -0.5F, 3, 3, 1, 0F); // Box 40
		pullmans_palaceModel[22].setRotationPoint(-28F, 17F, -5.5F);

		pullmans_palaceModel[23].addBox(-0.5F, -0.5F, -0.5F, 3, 2, 1, 0F); // Box 41
		pullmans_palaceModel[23].setRotationPoint(-25F, 17F, -5.5F);

		pullmans_palaceModel[24].addBox(-0.5F, -0.5F, -0.5F, 3, 2, 1, 0F); // Box 42
		pullmans_palaceModel[24].setRotationPoint(-37F, 17F, -5.5F);

		pullmans_palaceModel[25].addBox(-0.5F, -0.5F, 0F, 3, 2, 1, 0F); // Box 43
		pullmans_palaceModel[25].setRotationPoint(-25F, 17F, -4.5F);

		pullmans_palaceModel[26].addBox(-0.5F, -0.5F, 0F, 3, 3, 1, 0F); // Box 44
		pullmans_palaceModel[26].setRotationPoint(-28F, 17F, -4.5F);

		pullmans_palaceModel[27].addBox(-0.5F, -0.5F, 0F, 3, 7, 1, 0F); // Box 45
		pullmans_palaceModel[27].setRotationPoint(-31F, 17F, -4.5F);

		pullmans_palaceModel[28].addBox(-0.5F, -0.5F, 0F, 3, 3, 1, 0F); // Box 46
		pullmans_palaceModel[28].setRotationPoint(-34F, 17F, -4.5F);

		pullmans_palaceModel[29].addBox(-0.5F, -0.5F, 0F, 3, 2, 1, 0F); // Box 47
		pullmans_palaceModel[29].setRotationPoint(-37F, 17F, -4.5F);

		pullmans_palaceModel[30].addBox(-5F, -5F, 0F, 10, 10, 0, 0F); // Box 48
		pullmans_palaceModel[30].setRotationPoint(-30F, 22F, -4.5F);

		pullmans_palaceModel[31].addBox(-0.5F, -0.5F, -0.5F, 3, 3, 1, 0F); // Box 49
		pullmans_palaceModel[31].setRotationPoint(-34F, 17F, 4F);

		pullmans_palaceModel[32].addBox(-0.5F, -0.5F, -0.5F, 3, 7, 1, 0F); // Box 50
		pullmans_palaceModel[32].setRotationPoint(-31F, 17F, 4F);

		pullmans_palaceModel[33].addBox(-0.5F, -0.5F, -0.5F, 3, 3, 1, 0F); // Box 51
		pullmans_palaceModel[33].setRotationPoint(-28F, 17F, 4F);

		pullmans_palaceModel[34].addBox(-0.5F, -0.5F, -0.5F, 3, 2, 1, 0F); // Box 52
		pullmans_palaceModel[34].setRotationPoint(-25F, 17F, 4F);

		pullmans_palaceModel[35].addBox(-0.5F, -0.5F, -0.5F, 3, 2, 1, 0F); // Box 53
		pullmans_palaceModel[35].setRotationPoint(-37F, 17F, 4F);

		pullmans_palaceModel[36].addBox(-5F, -5F, -0.5F, 10, 10, 0, 0F); // Box 54
		pullmans_palaceModel[36].setRotationPoint(-30F, 22F, 5F);

		pullmans_palaceModel[37].addBox(-0.5F, -0.5F, 0F, 3, 2, 1, 0F); // Box 55
		pullmans_palaceModel[37].setRotationPoint(-37F, 17F, 5F);

		pullmans_palaceModel[38].addBox(-0.5F, -0.5F, 0F, 3, 3, 1, 0F); // Box 56
		pullmans_palaceModel[38].setRotationPoint(-34F, 17F, 5F);

		pullmans_palaceModel[39].addBox(-5F, -5F, 0F, 10, 10, 0, 0F); // Box 57
		pullmans_palaceModel[39].setRotationPoint(-30F, 22F, 5F);

		pullmans_palaceModel[40].addBox(-0.5F, -0.5F, 0F, 3, 7, 1, 0F); // Box 58
		pullmans_palaceModel[40].setRotationPoint(-31F, 17F, 5F);

		pullmans_palaceModel[41].addBox(-0.5F, -0.5F, 0F, 3, 3, 1, 0F); // Box 59
		pullmans_palaceModel[41].setRotationPoint(-28F, 17F, 5F);

		pullmans_palaceModel[42].addBox(-0.5F, -0.5F, 0F, 3, 2, 1, 0F); // Box 60
		pullmans_palaceModel[42].setRotationPoint(-25F, 17F, 5F);

		pullmans_palaceModel[43].addBox(-5F, -5F, -0.5F, 10, 10, 0, 0F); // Box 61
		pullmans_palaceModel[43].setRotationPoint(30F, 22F, -4.5F);

		pullmans_palaceModel[44].addBox(-0.5F, -0.5F, -0.5F, 3, 2, 1, 0F); // Box 62
		pullmans_palaceModel[44].setRotationPoint(23F, 17F, -5.5F);

		pullmans_palaceModel[45].addBox(-0.5F, -0.5F, -0.5F, 3, 3, 1, 0F); // Box 63
		pullmans_palaceModel[45].setRotationPoint(26F, 17F, -5.5F);

		pullmans_palaceModel[46].addBox(-0.5F, -0.5F, -0.5F, 3, 7, 1, 0F); // Box 64
		pullmans_palaceModel[46].setRotationPoint(29F, 17F, -5.5F);

		pullmans_palaceModel[47].addBox(-0.5F, -0.5F, -0.5F, 3, 2, 1, 0F); // Box 65
		pullmans_palaceModel[47].setRotationPoint(35F, 17F, -5.5F);

		pullmans_palaceModel[48].addBox(-0.5F, -0.5F, -0.5F, 3, 3, 1, 0F); // Box 66
		pullmans_palaceModel[48].setRotationPoint(32F, 17F, -5.5F);

		pullmans_palaceModel[49].addBox(-0.5F, -0.5F, 0F, 3, 2, 1, 0F); // Box 67
		pullmans_palaceModel[49].setRotationPoint(35F, 17F, -4.5F);

		pullmans_palaceModel[50].addBox(-0.5F, -0.5F, 0F, 3, 3, 1, 0F); // Box 68
		pullmans_palaceModel[50].setRotationPoint(32F, 17F, -4.5F);

		pullmans_palaceModel[51].addBox(-0.5F, -0.5F, 0F, 3, 3, 1, 0F); // Box 69
		pullmans_palaceModel[51].setRotationPoint(26F, 17F, -4.5F);

		pullmans_palaceModel[52].addBox(-0.5F, -0.5F, 0F, 3, 7, 1, 0F); // Box 70
		pullmans_palaceModel[52].setRotationPoint(29F, 17F, -4.5F);

		pullmans_palaceModel[53].addBox(-0.5F, -0.5F, 0F, 3, 2, 1, 0F); // Box 71
		pullmans_palaceModel[53].setRotationPoint(23F, 17F, -4.5F);

		pullmans_palaceModel[54].addBox(-5F, -5F, 0F, 10, 10, 0, 0F); // Box 72
		pullmans_palaceModel[54].setRotationPoint(30F, 22F, -4.5F);

		pullmans_palaceModel[55].addBox(-5F, -5F, 0F, 10, 10, 0, 0F); // Box 73
		pullmans_palaceModel[55].setRotationPoint(30F, 22F, 5F);

		pullmans_palaceModel[56].addBox(-5F, -5F, -0.5F, 10, 10, 0, 0F); // Box 74
		pullmans_palaceModel[56].setRotationPoint(30F, 22F, 5F);

		pullmans_palaceModel[57].addBox(-0.5F, -0.5F, -0.5F, 3, 7, 1, 0F); // Box 75
		pullmans_palaceModel[57].setRotationPoint(29F, 17F, 4F);

		pullmans_palaceModel[58].addBox(-0.5F, -0.5F, -0.5F, 3, 3, 1, 0F); // Box 76
		pullmans_palaceModel[58].setRotationPoint(26F, 17F, 4F);

		pullmans_palaceModel[59].addBox(-0.5F, -0.5F, -0.5F, 3, 2, 1, 0F); // Box 77
		pullmans_palaceModel[59].setRotationPoint(23F, 17F, 4F);

		pullmans_palaceModel[60].addBox(-0.5F, -0.5F, 0F, 3, 2, 1, 0F); // Box 78
		pullmans_palaceModel[60].setRotationPoint(23F, 17F, 5F);

		pullmans_palaceModel[61].addBox(-0.5F, -0.5F, 0F, 3, 3, 1, 0F); // Box 79
		pullmans_palaceModel[61].setRotationPoint(26F, 17F, 5F);

		pullmans_palaceModel[62].addBox(-0.5F, -0.5F, 0F, 3, 7, 1, 0F); // Box 80
		pullmans_palaceModel[62].setRotationPoint(29F, 17F, 5F);

		pullmans_palaceModel[63].addBox(-0.5F, -0.5F, 0F, 3, 3, 1, 0F); // Box 81
		pullmans_palaceModel[63].setRotationPoint(32F, 17F, 5F);

		pullmans_palaceModel[64].addBox(-0.5F, -0.5F, 0F, 3, 2, 1, 0F); // Box 82
		pullmans_palaceModel[64].setRotationPoint(35F, 17F, 5F);

		pullmans_palaceModel[65].addBox(-0.5F, -0.5F, -0.5F, 3, 3, 1, 0F); // Box 83
		pullmans_palaceModel[65].setRotationPoint(32F, 17F, 4F);

		pullmans_palaceModel[66].addBox(-0.5F, -0.5F, -0.5F, 3, 2, 1, 0F); // Box 84
		pullmans_palaceModel[66].setRotationPoint(35F, 17F, 4F);

		pullmans_palaceModel[67].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 85
		pullmans_palaceModel[67].setRotationPoint(-49F, 16F, -11F);

		pullmans_palaceModel[68].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 87
		pullmans_palaceModel[68].setRotationPoint(-49F, 18F, -12F);

		pullmans_palaceModel[69].addBox(0F, 0F, 0F, 9, 2, 1, 0F); // Box 88
		pullmans_palaceModel[69].setRotationPoint(-49F, 20F, -13F);

		pullmans_palaceModel[70].addBox(0F, 0F, 0F, 9, 2, 1, 0F); // Box 89
		pullmans_palaceModel[70].setRotationPoint(40F, 20F, -13F);

		pullmans_palaceModel[71].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 90
		pullmans_palaceModel[71].setRotationPoint(40F, 18F, -12F);

		pullmans_palaceModel[72].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 91
		pullmans_palaceModel[72].setRotationPoint(40F, 16F, -11F);

		pullmans_palaceModel[73].addBox(0F, 0F, 0F, 9, 2, 1, 0F); // Box 92
		pullmans_palaceModel[73].setRotationPoint(40F, 20F, 12F);

		pullmans_palaceModel[74].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 93
		pullmans_palaceModel[74].setRotationPoint(40F, 18F, 11F);

		pullmans_palaceModel[75].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 94
		pullmans_palaceModel[75].setRotationPoint(40F, 16F, 10F);

		pullmans_palaceModel[76].addBox(0F, 0F, 0F, 9, 2, 1, 0F); // Box 95
		pullmans_palaceModel[76].setRotationPoint(-49F, 20F, 12F);

		pullmans_palaceModel[77].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 96
		pullmans_palaceModel[77].setRotationPoint(-49F, 18F, 11F);

		pullmans_palaceModel[78].addBox(0F, 0F, 0F, 9, 3, 1, 0F); // Box 97
		pullmans_palaceModel[78].setRotationPoint(-49F, 16F, 10F);


	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		GL11.glRotatef(90,0,1,0);
		for(int i = 0; i < 79; i++)
		{
			pullmans_palaceModel[i].render(f5);
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
	}

	public ModelRendererTurbo pullmans_palaceModel[];
}