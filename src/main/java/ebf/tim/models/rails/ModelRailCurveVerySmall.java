//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator:
// Created on:17.04.2017 - 16:25:28
// Last changed on: 17.04.2017 - 16:25:28

package ebf.tim.models.rails;

import ebf.tim.models.tmt.Coord2D;
import ebf.tim.models.tmt.ModelRendererTurbo;
import ebf.tim.models.tmt.Shape2D;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class ModelRailCurveVerySmall extends ModelBase
{
	int textureX = 512;
	int textureY = 32;

	public ModelRailCurveVerySmall()
	{
		railcurveverysmallModel = new ModelRendererTurbo[63];
		railcurveverysmallModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Shape 11
		railcurveverysmallModel[1] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 15
		railcurveverysmallModel[2] = new ModelRendererTurbo(this, 25, 1, textureX, textureY); // Box 16
		railcurveverysmallModel[3] = new ModelRendererTurbo(this, 41, 1, textureX, textureY); // Box 17
		railcurveverysmallModel[4] = new ModelRendererTurbo(this, 57, 1, textureX, textureY); // Box 18
		railcurveverysmallModel[5] = new ModelRendererTurbo(this, 73, 1, textureX, textureY); // Box 19
		railcurveverysmallModel[6] = new ModelRendererTurbo(this, 89, 1, textureX, textureY); // Box 20
		railcurveverysmallModel[7] = new ModelRendererTurbo(this, 97, 1, textureX, textureY); // Box 21
		railcurveverysmallModel[8] = new ModelRendererTurbo(this, 113, 1, textureX, textureY); // Shape 23
		railcurveverysmallModel[9] = new ModelRendererTurbo(this, 121, 1, textureX, textureY); // Shape 24
		railcurveverysmallModel[10] = new ModelRendererTurbo(this, 129, 1, textureX, textureY); // Shape 25
		railcurveverysmallModel[11] = new ModelRendererTurbo(this, 137, 1, textureX, textureY); // Box 26
		railcurveverysmallModel[12] = new ModelRendererTurbo(this, 153, 1, textureX, textureY); // Box 27
		railcurveverysmallModel[13] = new ModelRendererTurbo(this, 161, 1, textureX, textureY); // Box 28
		railcurveverysmallModel[14] = new ModelRendererTurbo(this, 177, 1, textureX, textureY); // Shape 29
		railcurveverysmallModel[15] = new ModelRendererTurbo(this, 9, 1, textureX, textureY); // Shape 31
		railcurveverysmallModel[16] = new ModelRendererTurbo(this, 185, 1, textureX, textureY); // Shape 32
		railcurveverysmallModel[17] = new ModelRendererTurbo(this, 193, 1, textureX, textureY); // Box 35
		railcurveverysmallModel[18] = new ModelRendererTurbo(this, 201, 1, textureX, textureY); // Box 36
		railcurveverysmallModel[19] = new ModelRendererTurbo(this, 209, 1, textureX, textureY); // Box 37
		railcurveverysmallModel[20] = new ModelRendererTurbo(this, 217, 1, textureX, textureY); // Shape 59
		railcurveverysmallModel[21] = new ModelRendererTurbo(this, 225, 1, textureX, textureY); // Box 60
		railcurveverysmallModel[22] = new ModelRendererTurbo(this, 233, 1, textureX, textureY); // Box 61
		railcurveverysmallModel[23] = new ModelRendererTurbo(this, 241, 1, textureX, textureY); // Shape 62
		railcurveverysmallModel[24] = new ModelRendererTurbo(this, 249, 1, textureX, textureY); // Shape 63
		railcurveverysmallModel[25] = new ModelRendererTurbo(this, 257, 1, textureX, textureY); // Shape 64
		railcurveverysmallModel[26] = new ModelRendererTurbo(this, 265, 1, textureX, textureY); // Shape 65
		railcurveverysmallModel[27] = new ModelRendererTurbo(this, 273, 1, textureX, textureY); // Shape 66
		railcurveverysmallModel[28] = new ModelRendererTurbo(this, 281, 1, textureX, textureY); // Shape 67
		railcurveverysmallModel[29] = new ModelRendererTurbo(this, 289, 1, textureX, textureY); // Box 68
		railcurveverysmallModel[30] = new ModelRendererTurbo(this, 305, 1, textureX, textureY); // Box 69
		railcurveverysmallModel[31] = new ModelRendererTurbo(this, 321, 1, textureX, textureY); // Box 70
		railcurveverysmallModel[32] = new ModelRendererTurbo(this, 337, 1, textureX, textureY); // Shape 71
		railcurveverysmallModel[33] = new ModelRendererTurbo(this, 345, 1, textureX, textureY); // Box 72
		railcurveverysmallModel[34] = new ModelRendererTurbo(this, 353, 1, textureX, textureY); // Box 73
		railcurveverysmallModel[35] = new ModelRendererTurbo(this, 361, 1, textureX, textureY); // Shape 74
		railcurveverysmallModel[36] = new ModelRendererTurbo(this, 369, 1, textureX, textureY); // Shape 75
		railcurveverysmallModel[37] = new ModelRendererTurbo(this, 377, 1, textureX, textureY); // Box 76
		railcurveverysmallModel[38] = new ModelRendererTurbo(this, 385, 1, textureX, textureY); // Shape 77
		railcurveverysmallModel[39] = new ModelRendererTurbo(this, 393, 1, textureX, textureY); // Shape 78
		railcurveverysmallModel[40] = new ModelRendererTurbo(this, 401, 1, textureX, textureY); // Box 79
		railcurveverysmallModel[41] = new ModelRendererTurbo(this, 409, 1, textureX, textureY); // Shape 80
		railcurveverysmallModel[42] = new ModelRendererTurbo(this, 417, 1, textureX, textureY); // Shape 81
		railcurveverysmallModel[43] = new ModelRendererTurbo(this, 425, 1, textureX, textureY); // Box 82
		railcurveverysmallModel[44] = new ModelRendererTurbo(this, 433, 1, textureX, textureY); // Shape 83
		railcurveverysmallModel[45] = new ModelRendererTurbo(this, 441, 1, textureX, textureY); // Shape 84
		railcurveverysmallModel[46] = new ModelRendererTurbo(this, 449, 1, textureX, textureY); // Box 85
		railcurveverysmallModel[47] = new ModelRendererTurbo(this, 457, 1, textureX, textureY); // Shape 86
		railcurveverysmallModel[48] = new ModelRendererTurbo(this, 465, 1, textureX, textureY); // Shape 87
		railcurveverysmallModel[49] = new ModelRendererTurbo(this, 473, 1, textureX, textureY); // Box 88
		railcurveverysmallModel[50] = new ModelRendererTurbo(this, 481, 1, textureX, textureY); // Shape 89
		railcurveverysmallModel[51] = new ModelRendererTurbo(this, 489, 1, textureX, textureY); // Shape 90
		railcurveverysmallModel[52] = new ModelRendererTurbo(this, 497, 1, textureX, textureY); // Box 91
		railcurveverysmallModel[53] = new ModelRendererTurbo(this, 505, 1, textureX, textureY); // Shape 92
		railcurveverysmallModel[54] = new ModelRendererTurbo(this, 1, 9, textureX, textureY); // Box 93
		railcurveverysmallModel[55] = new ModelRendererTurbo(this, 25, 9, textureX, textureY); // Shape 94
		railcurveverysmallModel[56] = new ModelRendererTurbo(this, 33, 9, textureX, textureY); // Shape 95
		railcurveverysmallModel[57] = new ModelRendererTurbo(this, 33, 9, textureX, textureY); // Box 96
		railcurveverysmallModel[58] = new ModelRendererTurbo(this, 73, 9, textureX, textureY); // Box 97
		railcurveverysmallModel[59] = new ModelRendererTurbo(this, 57, 9, textureX, textureY); // Box 98
		railcurveverysmallModel[60] = new ModelRendererTurbo(this, 97, 9, textureX, textureY); // Box 100
		railcurveverysmallModel[61] = new ModelRendererTurbo(this, 73, 9, textureX, textureY); // Box 101
		railcurveverysmallModel[62] = new ModelRendererTurbo(this, 113, 9, textureX, textureY); // Box 102

		railcurveverysmallModel[0].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(0, 0, 0, 0), new Coord2D(1, 0, 1, 0), new Coord2D(1, 2, 1, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {3 ,2 ,1}); // Shape 11
		railcurveverysmallModel[0].setRotationPoint(-5F, 5F, 6F);
		railcurveverysmallModel[0].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[0].rotateAngleY = 1.57079633F;

		railcurveverysmallModel[1].addBox(-1F, 0F, 0F, 2, 2, 14, 0F); // Box 15
		railcurveverysmallModel[1].setRotationPoint(-9F, 8F, -6F);
		railcurveverysmallModel[1].rotateAngleY = -0.34906585F;

		railcurveverysmallModel[2].addBox(-2F, 0F, 0F, 4, 1, 2, 0F); // Box 16
		railcurveverysmallModel[2].setRotationPoint(-7F, 7F, 4F);

		railcurveverysmallModel[3].addBox(-2F, 0F, 0F, 4, 1, 2, 0F); // Box 17
		railcurveverysmallModel[3].setRotationPoint(-7F, 5F, 4F);

		railcurveverysmallModel[4].addBox(-2F, 0F, 0F, 4, 1, 1, 0F); // Box 18
		railcurveverysmallModel[4].setRotationPoint(-7F, 6F, 4.5F);

		railcurveverysmallModel[5].addBox(-1F, 0F, 0F, 2, 1, 2, 0F); // Box 19
		railcurveverysmallModel[5].setRotationPoint(-8F, 5F, -6F);

		railcurveverysmallModel[6].addBox(-1F, 0F, 0F, 2, 1, 1, 0F); // Box 20
		railcurveverysmallModel[6].setRotationPoint(-8F, 6F, -5.5F);

		railcurveverysmallModel[7].addBox(-1F, 0F, 0F, 2, 1, 2, 0F); // Box 21
		railcurveverysmallModel[7].setRotationPoint(-8F, 7F, -6F);

		railcurveverysmallModel[8].addShape3D(1F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 2, 1, 2), new Coord2D(1, 0, 1, 0), new Coord2D(0, 2, 0, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,3 ,2}); // Shape 23
		railcurveverysmallModel[8].setRotationPoint(-5F, 6F, 3F);
		railcurveverysmallModel[8].rotateAngleX = -1.57079633F;
		railcurveverysmallModel[8].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[9].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 2, 1, 2), new Coord2D(1, 0, 1, 0), new Coord2D(0, 2, 0, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,3 ,2}); // Shape 24
		railcurveverysmallModel[9].setRotationPoint(-3F, 6F, 3F);
		railcurveverysmallModel[9].rotateAngleX = -1.57079633F;
		railcurveverysmallModel[9].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[10].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(0, 0, 0, 0), new Coord2D(1, 0, 1, 0), new Coord2D(1, 2, 1, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {3 ,2 ,1}); // Shape 25
		railcurveverysmallModel[10].setRotationPoint(-3F, 5F, 5F);
		railcurveverysmallModel[10].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[10].rotateAngleY = 1.57079633F;

		railcurveverysmallModel[11].addBox(-1F, 0F, 0F, 2, 1, 2, 0F); // Box 26
		railcurveverysmallModel[11].setRotationPoint(-7F, 5F, -7F);
		railcurveverysmallModel[11].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[12].addBox(-1F, 0F, 0F, 2, 1, 1, 0F); // Box 27
		railcurveverysmallModel[12].setRotationPoint(-6.5F, 6F, -7F);
		railcurveverysmallModel[12].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[13].addBox(-1F, 0F, 0F, 2, 1, 2, 0F); // Box 28
		railcurveverysmallModel[13].setRotationPoint(-7F, 7F, -7F);
		railcurveverysmallModel[13].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[14].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(0, 0, 0, 0), new Coord2D(2, 0, 2, 0), new Coord2D(2, 2, 2, 2) }), 1, 2, 2, 7, 1, ModelRendererTurbo.MR_FRONT, new float[] {3 ,2 ,2}); // Shape 29
		railcurveverysmallModel[14].setRotationPoint(-7F, 5F, -4F);
		railcurveverysmallModel[14].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[14].rotateAngleY = 1.57079633F;

		railcurveverysmallModel[15].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(0, 0, 0, 0), new Coord2D(1, 0, 1, 0), new Coord2D(1, 1, 1, 1) }), 1, 1, 1, 4, 1, ModelRendererTurbo.MR_FRONT, new float[] {2 ,1 ,1}); // Shape 31
		railcurveverysmallModel[15].setRotationPoint(-1F, 5F, 4F);
		railcurveverysmallModel[15].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[15].rotateAngleY = 1.57079633F;

		railcurveverysmallModel[16].addShape3D(1F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 1, 1, 1), new Coord2D(1, 0, 1, 0), new Coord2D(0, 1, 0, 1) }), 1, 1, 1, 4, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,2 ,1}); // Shape 32
		railcurveverysmallModel[16].setRotationPoint(-1F, 6F, 2F);
		railcurveverysmallModel[16].rotateAngleX = -1.57079633F;
		railcurveverysmallModel[16].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[17].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 35
		railcurveverysmallModel[17].setRotationPoint(-1F, 5F, 2F);

		railcurveverysmallModel[18].addBox(0F, 0F, 0F, 2, 1, 1, 0F); // Box 36
		railcurveverysmallModel[18].setRotationPoint(-3F, 5F, 3F);

		railcurveverysmallModel[19].addBox(0F, 0F, 0F, 2, 1, 1, 0F); // Box 37
		railcurveverysmallModel[19].setRotationPoint(-5F, 5F, 4F);

		railcurveverysmallModel[20].addShape3D(1F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 1, 1, 1), new Coord2D(1, 0, 1, 0), new Coord2D(0, 1, 0, 1) }), 1, 1, 1, 4, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,2 ,1}); // Shape 59
		railcurveverysmallModel[20].setRotationPoint(0F, 6F, 1F);
		railcurveverysmallModel[20].rotateAngleX = -1.57079633F;
		railcurveverysmallModel[20].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[21].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 60
		railcurveverysmallModel[21].setRotationPoint(0F, 5F, 1F);

		railcurveverysmallModel[22].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 61
		railcurveverysmallModel[22].setRotationPoint(1F, 5F, 0F);

		railcurveverysmallModel[23].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(0, 0, 0, 0), new Coord2D(1, 0, 1, 0), new Coord2D(1, 1, 1, 1) }), 1, 1, 1, 4, 1, ModelRendererTurbo.MR_FRONT, new float[] {2 ,1 ,1}); // Shape 62
		railcurveverysmallModel[23].setRotationPoint(0F, 5F, 3F);
		railcurveverysmallModel[23].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[23].rotateAngleY = 1.57079633F;

		railcurveverysmallModel[24].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(0, 0, 0, 0), new Coord2D(1, 0, 1, 0), new Coord2D(1, 1, 1, 1) }), 1, 1, 1, 4, 1, ModelRendererTurbo.MR_FRONT, new float[] {2 ,1 ,1}); // Shape 63
		railcurveverysmallModel[24].setRotationPoint(1F, 5F, 2F);
		railcurveverysmallModel[24].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[24].rotateAngleY = 1.57079633F;

		railcurveverysmallModel[25].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 2, 1, 2), new Coord2D(1, 0, 1, 0), new Coord2D(0, 2, 0, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,3 ,2}); // Shape 64
		railcurveverysmallModel[25].setRotationPoint(1F, 5F, -2F);
		railcurveverysmallModel[25].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[25].rotateAngleY = -3.14159265F;

		railcurveverysmallModel[26].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 2, 1, 2), new Coord2D(1, 0, 1, 0), new Coord2D(0, 2, 0, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,3 ,2}); // Shape 65
		railcurveverysmallModel[26].setRotationPoint(2F, 5F, -4F);
		railcurveverysmallModel[26].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[26].rotateAngleY = -3.14159265F;

		railcurveverysmallModel[27].addShape3D(1F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 2, 1, 2), new Coord2D(1, 0, 1, 0), new Coord2D(0, 2, 0, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,3 ,2}); // Shape 66
		railcurveverysmallModel[27].setRotationPoint(3F, 5F, 0F);
		railcurveverysmallModel[27].rotateAngleX = 1.57079633F;

		railcurveverysmallModel[28].addShape3D(1F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 2, 1, 2), new Coord2D(1, 0, 1, 0), new Coord2D(0, 2, 0, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,3 ,2}); // Shape 67
		railcurveverysmallModel[28].setRotationPoint(4F, 5F, -2F);
		railcurveverysmallModel[28].rotateAngleX = 1.57079633F;

		railcurveverysmallModel[29].addBox(-2F, 0F, 0F, 4, 1, 2, 0F); // Box 68
		railcurveverysmallModel[29].setRotationPoint(3F, 5F, -6F);
		railcurveverysmallModel[29].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[30].addBox(-2F, 0F, 0F, 4, 1, 1, 0F); // Box 69
		railcurveverysmallModel[30].setRotationPoint(3.5F, 6F, -6F);
		railcurveverysmallModel[30].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[31].addBox(-2F, 0F, 0F, 4, 1, 2, 0F); // Box 70
		railcurveverysmallModel[31].setRotationPoint(3F, 7F, -6F);
		railcurveverysmallModel[31].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[32].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(0, 0, 0, 0), new Coord2D(1, 0, 1, 0), new Coord2D(1, 1, 1, 1) }), 1, 1, 1, 4, 1, ModelRendererTurbo.MR_FRONT, new float[] {2 ,1 ,1}); // Shape 71
		railcurveverysmallModel[32].setRotationPoint(2F, 5F, 1F);
		railcurveverysmallModel[32].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[32].rotateAngleY = 1.57079633F;

		railcurveverysmallModel[33].addBox(0F, 0F, 0F, 1, 1, 2, 0F); // Box 72
		railcurveverysmallModel[33].setRotationPoint(2F, 5F, -2F);

		railcurveverysmallModel[34].addBox(0F, 0F, 0F, 1, 1, 2, 0F); // Box 73
		railcurveverysmallModel[34].setRotationPoint(3F, 5F, -4F);

		railcurveverysmallModel[35].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(0, 0, 0, 0), new Coord2D(2, 0, 2, 0), new Coord2D(2, 2, 2, 2) }), 1, 2, 2, 7, 1, ModelRendererTurbo.MR_FRONT, new float[] {3 ,2 ,2}); // Shape 74
		railcurveverysmallModel[35].setRotationPoint(-7F, 7F, -4F);
		railcurveverysmallModel[35].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[35].rotateAngleY = 1.57079633F;

		railcurveverysmallModel[36].addShape3D(1F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 2, 1, 2), new Coord2D(1, 0, 1, 0), new Coord2D(0, 2, 0, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,3 ,2}); // Shape 75
		railcurveverysmallModel[36].setRotationPoint(4F, 7F, -2F);
		railcurveverysmallModel[36].rotateAngleX = 1.57079633F;

		railcurveverysmallModel[37].addBox(0F, 0F, 0F, 1, 1, 2, 0F); // Box 76
		railcurveverysmallModel[37].setRotationPoint(3F, 7F, -4F);

		railcurveverysmallModel[38].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 2, 1, 2), new Coord2D(1, 0, 1, 0), new Coord2D(0, 2, 0, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,3 ,2}); // Shape 77
		railcurveverysmallModel[38].setRotationPoint(2F, 7F, -4F);
		railcurveverysmallModel[38].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[38].rotateAngleY = -3.14159265F;

		railcurveverysmallModel[39].addShape3D(1F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 2, 1, 2), new Coord2D(1, 0, 1, 0), new Coord2D(0, 2, 0, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,3 ,2}); // Shape 78
		railcurveverysmallModel[39].setRotationPoint(3F, 7F, 0F);
		railcurveverysmallModel[39].rotateAngleX = 1.57079633F;

		railcurveverysmallModel[40].addBox(0F, 0F, 0F, 1, 1, 2, 0F); // Box 79
		railcurveverysmallModel[40].setRotationPoint(2F, 7F, -2F);

		railcurveverysmallModel[41].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 2, 1, 2), new Coord2D(1, 0, 1, 0), new Coord2D(0, 2, 0, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,3 ,2}); // Shape 80
		railcurveverysmallModel[41].setRotationPoint(1F, 7F, -2F);
		railcurveverysmallModel[41].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[41].rotateAngleY = -3.14159265F;

		railcurveverysmallModel[42].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(0, 0, 0, 0), new Coord2D(1, 0, 1, 0), new Coord2D(1, 1, 1, 1) }), 1, 1, 1, 4, 1, ModelRendererTurbo.MR_FRONT, new float[] {2 ,1 ,1}); // Shape 81
		railcurveverysmallModel[42].setRotationPoint(2F, 7F, 1F);
		railcurveverysmallModel[42].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[42].rotateAngleY = 1.57079633F;

		railcurveverysmallModel[43].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 82
		railcurveverysmallModel[43].setRotationPoint(1F, 7F, 0F);

		railcurveverysmallModel[44].addShape3D(1F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 1, 1, 1), new Coord2D(1, 0, 1, 0), new Coord2D(0, 1, 0, 1) }), 1, 1, 1, 4, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,2 ,1}); // Shape 83
		railcurveverysmallModel[44].setRotationPoint(0F, 8F, 1F);
		railcurveverysmallModel[44].rotateAngleX = -1.57079633F;
		railcurveverysmallModel[44].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[45].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(0, 0, 0, 0), new Coord2D(1, 0, 1, 0), new Coord2D(1, 1, 1, 1) }), 1, 1, 1, 4, 1, ModelRendererTurbo.MR_FRONT, new float[] {2 ,1 ,1}); // Shape 84
		railcurveverysmallModel[45].setRotationPoint(1F, 7F, 2F);
		railcurveverysmallModel[45].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[45].rotateAngleY = 1.57079633F;

		railcurveverysmallModel[46].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 85
		railcurveverysmallModel[46].setRotationPoint(0F, 7F, 1F);

		railcurveverysmallModel[47].addShape3D(1F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 1, 1, 1), new Coord2D(1, 0, 1, 0), new Coord2D(0, 1, 0, 1) }), 1, 1, 1, 4, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,2 ,1}); // Shape 86
		railcurveverysmallModel[47].setRotationPoint(-1F, 8F, 2F);
		railcurveverysmallModel[47].rotateAngleX = -1.57079633F;
		railcurveverysmallModel[47].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[48].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(0, 0, 0, 0), new Coord2D(1, 0, 1, 0), new Coord2D(1, 1, 1, 1) }), 1, 1, 1, 4, 1, ModelRendererTurbo.MR_FRONT, new float[] {2 ,1 ,1}); // Shape 87
		railcurveverysmallModel[48].setRotationPoint(0F, 7F, 3F);
		railcurveverysmallModel[48].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[48].rotateAngleY = 1.57079633F;

		railcurveverysmallModel[49].addBox(0F, 0F, 0F, 1, 1, 1, 0F); // Box 88
		railcurveverysmallModel[49].setRotationPoint(-1F, 7F, 2F);

		railcurveverysmallModel[50].addShape3D(1F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 2, 1, 2), new Coord2D(1, 0, 1, 0), new Coord2D(0, 2, 0, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,3 ,2}); // Shape 89
		railcurveverysmallModel[50].setRotationPoint(-3F, 8F, 3F);
		railcurveverysmallModel[50].rotateAngleX = -1.57079633F;
		railcurveverysmallModel[50].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[51].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(0, 0, 0, 0), new Coord2D(1, 0, 1, 0), new Coord2D(1, 1, 1, 1) }), 1, 1, 1, 4, 1, ModelRendererTurbo.MR_FRONT, new float[] {2 ,1 ,1}); // Shape 90
		railcurveverysmallModel[51].setRotationPoint(-1F, 7F, 4F);
		railcurveverysmallModel[51].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[51].rotateAngleY = 1.57079633F;

		railcurveverysmallModel[52].addBox(0F, 0F, 0F, 2, 1, 1, 0F); // Box 91
		railcurveverysmallModel[52].setRotationPoint(-3F, 7F, 3F);

		railcurveverysmallModel[53].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(0, 0, 0, 0), new Coord2D(1, 0, 1, 0), new Coord2D(1, 2, 1, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {3 ,2 ,1}); // Shape 92
		railcurveverysmallModel[53].setRotationPoint(-3F, 7F, 5F);
		railcurveverysmallModel[53].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[53].rotateAngleY = 1.57079633F;

		railcurveverysmallModel[54].addBox(0F, 0F, 0F, 2, 1, 1, 0F); // Box 93
		railcurveverysmallModel[54].setRotationPoint(-5F, 7F, 4F);

		railcurveverysmallModel[55].addShape3D(1F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(1, 2, 1, 2), new Coord2D(1, 0, 1, 0), new Coord2D(0, 2, 0, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {1 ,3 ,2}); // Shape 94
		railcurveverysmallModel[55].setRotationPoint(-5F, 8F, 4F);
		railcurveverysmallModel[55].rotateAngleX = -1.57079633F;
		railcurveverysmallModel[55].rotateAngleY = -1.57079633F;

		railcurveverysmallModel[56].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[] { new Coord2D(0, 0, 0, 0), new Coord2D(1, 0, 1, 0), new Coord2D(1, 2, 1, 2) }), 1, 1, 2, 6, 1, ModelRendererTurbo.MR_FRONT, new float[] {3 ,2 ,1}); // Shape 95
		railcurveverysmallModel[56].setRotationPoint(-5F, 7F, 6F);
		railcurveverysmallModel[56].rotateAngleX = 1.57079633F;
		railcurveverysmallModel[56].rotateAngleY = 1.57079633F;

		railcurveverysmallModel[57].addBox(-1F, 0F, 0F, 2, 2, 14, 0F); // Box 96
		railcurveverysmallModel[57].setRotationPoint(-8F, 8F, -7F);
		railcurveverysmallModel[57].rotateAngleY = -0.78539816F;

		railcurveverysmallModel[58].addBox(-1F, 0F, 0F, 2, 2, 14, 0F); // Box 97
		railcurveverysmallModel[58].setRotationPoint(-7F, 8F, -8F);
		railcurveverysmallModel[58].rotateAngleY = -1.22173048F;

		railcurveverysmallModel[59].addBox(-2F, -0.5F, -0.5F, 4, 1, 1, 0F); // Box 98
		railcurveverysmallModel[59].setRotationPoint(3.25F, 6.5F, -2.3F);
		railcurveverysmallModel[59].rotateAngleY = -1.13446401F;

		railcurveverysmallModel[60].addBox(-3F, -0.5F, -0.5F, 6, 1, 1, 0F); // Box 100
		railcurveverysmallModel[60].setRotationPoint(0.35F, 6.5F, 1.5F);
		railcurveverysmallModel[60].rotateAngleY = -0.78539816F;

		railcurveverysmallModel[61].addBox(-2F, -0.5F, -0.5F, 4, 1, 1, 0F); // Box 101
		railcurveverysmallModel[61].setRotationPoint(-3.4F, 6.5F, 4.3F);
		railcurveverysmallModel[61].rotateAngleY = -0.43633231F;

		railcurveverysmallModel[62].addBox(-0.5F, -0.5F, -0.5F, 2, 1, 1, 0F); // Box 102
		railcurveverysmallModel[62].setRotationPoint(-6.9F, 6.5F, -5.2F);
		railcurveverysmallModel[62].rotateAngleY = -0.78539816F;

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		for(int i = 0; i < 63; i++)
		{
			railcurveverysmallModel[i].render();
		}
	}

	protected void flip(ModelRendererTurbo[] model)
	{
		for(ModelRendererTurbo part : model)
		{
			part.doMirror(false, true, true);
			part.setRotationPoint(part.rotationPointX, - part.rotationPointY, -part.rotationPointZ);
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
	}

	public static ModelRendererTurbo railcurveverysmallModel[];
}