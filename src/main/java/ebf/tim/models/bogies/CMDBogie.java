//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model:
// Created on:12.02.2017 - 23:47:19
// Last changed on: 12.02.2017 - 23:47:19
/**
 * @author Eternal Blue Flame
 */
package ebf.tim.models.bogies;

import ebf.tim.models.StaticModelAnimator;
import ebf.tim.models.tmt.ModelBase;
import ebf.tim.models.tmt.ModelRendererTurbo;
import net.minecraft.entity.Entity;

public class CMDBogie extends ModelBase
{
	private static final int textureX = 64;
	private static final int textureY = 32;

	public CMDBogie()
	{
		cmdbogieModel = new ModelRendererTurbo[35];
		cmdbogieModel[0] = new ModelRendererTurbo(this, 9, 9, textureX, textureY); // Axel2
		cmdbogieModel[1] = new ModelRendererTurbo(this, 29, 1, textureX, textureY); // Box10
		cmdbogieModel[2] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[3] = new ModelRendererTurbo(this, 9, 9, textureX, textureY); // Axel1
		cmdbogieModel[4] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[5] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[6] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[7] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[8] = new ModelRendererTurbo(this, 0, 10, textureX, textureY); // Wheel
		cmdbogieModel[9] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[10] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[11] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[12] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[13] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[14] = new ModelRendererTurbo(this, 0, 10, textureX, textureY); // Wheel
		cmdbogieModel[15] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 39
		cmdbogieModel[16] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 40
		cmdbogieModel[17] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 41
		cmdbogieModel[18] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 42
		cmdbogieModel[19] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 43
		cmdbogieModel[20] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 44
		cmdbogieModel[21] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 45
		cmdbogieModel[22] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 46
		cmdbogieModel[23] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[24] = new ModelRendererTurbo(this, 0, 10, textureX, textureY); // Wheel
		cmdbogieModel[25] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[26] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[27] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[28] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[29] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[30] = new ModelRendererTurbo(this, 0, 10, textureX, textureY); // Wheel
		cmdbogieModel[31] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[32] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[33] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel
		cmdbogieModel[34] = new ModelRendererTurbo(this, 0, 0, textureX, textureY); // Wheel

		cmdbogieModel[0].addShapeBox(-2F, -2F, 0F, 4, 4, 11, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F); // Axel2
		cmdbogieModel[0].setRotationPoint(8.5F, 6.5F, -5.5F);

		cmdbogieModel[1].addBox(0F, 0F, 0F, 7, 7, 10, 0F); // Box10
		cmdbogieModel[1].setRotationPoint(-3.5F, 0.5F, -5F);

		cmdbogieModel[2].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[2].setRotationPoint(8.5F, 6.5F, 5F);

		cmdbogieModel[3].addShapeBox(-2F, -2F, 0F, 4, 4, 11, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F); // Axel1
		cmdbogieModel[3].setRotationPoint(-8.5F, 6.5F, -5.5F);

		cmdbogieModel[4].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[4].setRotationPoint(8.5F, 6.5F, 4.9F);

		cmdbogieModel[5].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[5].setRotationPoint(8.5F, 6.5F, 4.8F);

		cmdbogieModel[6].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[6].setRotationPoint(8.5F, 6.5F, 4.7F);

		cmdbogieModel[7].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[7].setRotationPoint(8.5F, 6.5F, 4.5F);

		cmdbogieModel[8].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[8].setRotationPoint(8.5F, 6.5F, 4.6F);

		cmdbogieModel[9].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[9].setRotationPoint(8.5F, 6.5F, -5F);

		cmdbogieModel[10].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[10].setRotationPoint(8.5F, 6.5F, -4.9F);

		cmdbogieModel[11].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[11].setRotationPoint(8.5F, 6.5F, -4.8F);

		cmdbogieModel[12].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[12].setRotationPoint(8.5F, 6.5F, -4.7F);

		cmdbogieModel[13].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[13].setRotationPoint(8.5F, 6.5F, -4.5F);

		cmdbogieModel[14].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[14].setRotationPoint(8.5F, 6.5F, -4.6F);

		cmdbogieModel[15].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F); // Box 39
		cmdbogieModel[15].setRotationPoint(-7F, 7.5F, 5.1F);

		cmdbogieModel[16].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F); // Box 40
		cmdbogieModel[16].setRotationPoint(-7F, 7.5F, 5.2F);

		cmdbogieModel[17].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F); // Box 41
		cmdbogieModel[17].setRotationPoint(-7F, 7.5F, 5.3F);

		cmdbogieModel[18].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F); // Box 42
		cmdbogieModel[18].setRotationPoint(-7F, 7.5F, 5.4F);

		cmdbogieModel[19].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F); // Box 43
		cmdbogieModel[19].setRotationPoint(-7F, 7.5F, -5.1F);

		cmdbogieModel[20].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F); // Box 44
		cmdbogieModel[20].setRotationPoint(-7F, 7.5F, -5.2F);

		cmdbogieModel[21].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F); // Box 45
		cmdbogieModel[21].setRotationPoint(-7F, 7.5F, -5.3F);

		cmdbogieModel[22].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F, -2F, -2F, 0F); // Box 46
		cmdbogieModel[22].setRotationPoint(-7F, 7.5F, -5.4F);

		cmdbogieModel[23].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[23].setRotationPoint(-8.5F, 6.5F, 4.5F);

		cmdbogieModel[24].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[24].setRotationPoint(-8.5F, 6.5F, 4.6F);

		cmdbogieModel[25].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[25].setRotationPoint(-8.5F, 6.5F, 4.7F);

		cmdbogieModel[26].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[26].setRotationPoint(-8.5F, 6.5F, 4.8F);

		cmdbogieModel[27].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[27].setRotationPoint(-8.5F, 6.5F, 4.9F);

		cmdbogieModel[28].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[28].setRotationPoint(-8.5F, 6.5F, 5F);

		cmdbogieModel[29].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[29].setRotationPoint(-8.5F, 6.5F, -4.5F);

		cmdbogieModel[30].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[30].setRotationPoint(-8.5F, 6.5F, -4.6F);

		cmdbogieModel[31].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[31].setRotationPoint(-8.5F, 6.5F, -4.7F);

		cmdbogieModel[32].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[32].setRotationPoint(-8.5F, 6.5F, -4.8F);

		cmdbogieModel[33].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[33].setRotationPoint(-8.5F, 6.5F, -4.9F);

		cmdbogieModel[34].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		cmdbogieModel[34].setRotationPoint(-8.5F, 6.5F, -5F);

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		for(int i = 0; i < 35; i++)
		{
			cmdbogieModel[i].render(f5);
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
	}

	private ModelRendererTurbo cmdbogieModel[];
}