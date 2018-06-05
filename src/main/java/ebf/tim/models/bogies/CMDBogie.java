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
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.ModelRendererTurbo;

public class CMDBogie extends ModelBase
{
	private static final int textureX = 64;
	private static final int textureY = 32;

	public CMDBogie()
	{
		baseModel = new ModelRendererTurbo[35];
		baseModel[0] = new ModelRendererTurbo(this, 9, 9, textureX, textureY); // Axel2
		baseModel[1] = new ModelRendererTurbo(this, 29, 1, textureX, textureY); // Box10
		baseModel[2] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[3] = new ModelRendererTurbo(this, 9, 9, textureX, textureY); // Axel1
		baseModel[4] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[5] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[6] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[7] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[8] = new ModelRendererTurbo(this, 0, 10, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[9] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[10] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[11] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[12] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[13] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[14] = new ModelRendererTurbo(this, 0, 10, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[15] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 39
		baseModel[16] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 40
		baseModel[17] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 41
		baseModel[18] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 42
		baseModel[19] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 43
		baseModel[20] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 44
		baseModel[21] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 45
		baseModel[22] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 46
		baseModel[23] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[24] = new ModelRendererTurbo(this, 0, 10, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[25] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[26] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[27] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[28] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[29] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[30] = new ModelRendererTurbo(this, 0, 10, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[31] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[32] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[33] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		baseModel[34] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel

		baseModel[0].addShapeBox(-2F, -2F, 0F, 4, 4, 11, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F); // Axel2
		baseModel[0].setRotationPoint(7.5F, 6.5F, -5.5F);

		baseModel[1].addBox(0F, 0F, 0F, 6, 7, 10, 0F); // Box10
		baseModel[1].setRotationPoint(-3F, 0.5F, -5F);

		baseModel[2].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[2].setRotationPoint(7.5F, 6.5F, 5F);

		baseModel[3].addShapeBox(-2F, -2F, 0F, 4, 4, 11, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F); // Axel1
		baseModel[3].setRotationPoint(-7.5F, 6.5F, -5.5F);

		baseModel[4].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[4].setRotationPoint(7.5F, 6.5F, 4.9F);

		baseModel[5].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[5].setRotationPoint(7.5F, 6.5F, 4.8F);

		baseModel[6].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[6].setRotationPoint(7.5F, 6.5F, 4.7F);

		baseModel[7].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[7].setRotationPoint(7.5F, 6.5F, 4.5F);

		baseModel[8].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[8].setRotationPoint(7.5F, 6.5F, 4.6F);

		baseModel[9].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[9].setRotationPoint(7.5F, 6.5F, -5F);

		baseModel[10].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[10].setRotationPoint(7.5F, 6.5F, -4.9F);

		baseModel[11].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[11].setRotationPoint(7.5F, 6.5F, -4.8F);

		baseModel[12].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[12].setRotationPoint(7.5F, 6.5F, -4.7F);

		baseModel[13].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[13].setRotationPoint(7.5F, 6.5F, -4.5F);

		baseModel[14].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[14].setRotationPoint(7.5F, 6.5F, -4.6F);

		baseModel[15].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 39
		baseModel[15].setRotationPoint(-7F, 7.5F, 5.1F);

		baseModel[16].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 40
		baseModel[16].setRotationPoint(-7F, 7.5F, 5.2F);

		baseModel[17].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 41
		baseModel[17].setRotationPoint(-7F, 7.5F, 5.3F);

		baseModel[18].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 42
		baseModel[18].setRotationPoint(-7F, 7.5F, 5.4F);

		baseModel[19].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 43
		baseModel[19].setRotationPoint(-7F, 7.5F, -5.1F);

		baseModel[20].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 44
		baseModel[20].setRotationPoint(-7F, 7.5F, -5.2F);

		baseModel[21].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 45
		baseModel[21].setRotationPoint(-7F, 7.5F, -5.3F);

		baseModel[22].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 46
		baseModel[22].setRotationPoint(-7F, 7.5F, -5.4F);

		baseModel[23].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[23].setRotationPoint(-7.5F, 6.5F, 4.5F);

		baseModel[24].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[24].setRotationPoint(-7.5F, 6.5F, 4.6F);

		baseModel[25].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[25].setRotationPoint(-7.5F, 6.5F, 4.7F);

		baseModel[26].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[26].setRotationPoint(-7.5F, 6.5F, 4.8F);

		baseModel[27].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[27].setRotationPoint(-7.5F, 6.5F, 4.9F);

		baseModel[28].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[28].setRotationPoint(-7.5F, 6.5F, 5F);

		baseModel[29].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[29].setRotationPoint(-7.5F, 6.5F, -4.5F);

		baseModel[30].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[30].setRotationPoint(-7.5F, 6.5F, -4.6F);

		baseModel[31].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[31].setRotationPoint(-7.5F, 6.5F, -4.7F);

		baseModel[32].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[32].setRotationPoint(-7.5F, 6.5F, -4.8F);

		baseModel[33].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[33].setRotationPoint(-7.5F, 6.5F, -4.9F);

		baseModel[34].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		baseModel[34].setRotationPoint(-7.5F, 6.5F, -5F);

	}

	ModelRendererTurbo[] baseModel;
}