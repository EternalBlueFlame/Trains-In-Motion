//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model:
// Created on:12.02.2017 - 23:47:19
// Last changed on: 12.02.2017 - 23:47:19
/**
 * @author Eternal Blue Flame
 */
package ebf.timsquared.models.bogies;

import ebf.tim.models.StaticModelAnimator;
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.ModelRendererTurbo;

public class CMDBogie extends ModelBase
{
	private static final int textureX = 64;
	private static final int textureY = 32;

	public CMDBogie()
	{
		bodyModel = new ModelRendererTurbo[35];
		bodyModel[0] = new ModelRendererTurbo(this, 9, 9, textureX, textureY); // Axel2
		bodyModel[1] = new ModelRendererTurbo(this, 29, 1, textureX, textureY); // Box10
		bodyModel[2] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[3] = new ModelRendererTurbo(this, 9, 9, textureX, textureY); // Axel1
		bodyModel[4] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[5] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[6] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[7] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[8] = new ModelRendererTurbo(this, 0, 10, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[9] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[10] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[11] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[12] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[13] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[14] = new ModelRendererTurbo(this, 0, 10, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[15] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 39
		bodyModel[16] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 40
		bodyModel[17] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 41
		bodyModel[18] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 42
		bodyModel[19] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 43
		bodyModel[20] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 44
		bodyModel[21] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 45
		bodyModel[22] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 46
		bodyModel[23] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[24] = new ModelRendererTurbo(this, 0, 10, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[25] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[26] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[27] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[28] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[29] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[30] = new ModelRendererTurbo(this, 0, 10, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[31] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[32] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[33] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel
		bodyModel[34] = new ModelRendererTurbo(this, 0, 0, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel

		bodyModel[0].addShapeBox(-2F, -2F, 0F, 4, 4, 11, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F); // Axel2
		bodyModel[0].setRotationPoint(7.5F, 6.5F, -5.5F);

		bodyModel[1].addBox(0F, 0F, 0F, 6, 7, 10, 0F); // Box10
		bodyModel[1].setRotationPoint(-3F, 0.5F, -5F);

		bodyModel[2].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[2].setRotationPoint(7.5F, 6.5F, 5F);

		bodyModel[3].addShapeBox(-2F, -2F, 0F, 4, 4, 11, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F, -1.25F, -1.25F, 0F); // Axel1
		bodyModel[3].setRotationPoint(-7.5F, 6.5F, -5.5F);

		bodyModel[4].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[4].setRotationPoint(7.5F, 6.5F, 4.9F);

		bodyModel[5].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[5].setRotationPoint(7.5F, 6.5F, 4.8F);

		bodyModel[6].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[6].setRotationPoint(7.5F, 6.5F, 4.7F);

		bodyModel[7].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[7].setRotationPoint(7.5F, 6.5F, 4.5F);

		bodyModel[8].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[8].setRotationPoint(7.5F, 6.5F, 4.6F);

		bodyModel[9].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[9].setRotationPoint(7.5F, 6.5F, -5F);

		bodyModel[10].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[10].setRotationPoint(7.5F, 6.5F, -4.9F);

		bodyModel[11].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[11].setRotationPoint(7.5F, 6.5F, -4.8F);

		bodyModel[12].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[12].setRotationPoint(7.5F, 6.5F, -4.7F);

		bodyModel[13].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[13].setRotationPoint(7.5F, 6.5F, -4.5F);

		bodyModel[14].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[14].setRotationPoint(7.5F, 6.5F, -4.6F);

		bodyModel[15].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 39
		bodyModel[15].setRotationPoint(-7F, 7.5F, 5.1F);

		bodyModel[16].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 40
		bodyModel[16].setRotationPoint(-7F, 7.5F, 5.2F);

		bodyModel[17].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 41
		bodyModel[17].setRotationPoint(-7F, 7.5F, 5.3F);

		bodyModel[18].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 42
		bodyModel[18].setRotationPoint(-7F, 7.5F, 5.4F);

		bodyModel[19].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 43
		bodyModel[19].setRotationPoint(-7F, 7.5F, -5.1F);

		bodyModel[20].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 44
		bodyModel[20].setRotationPoint(-7F, 7.5F, -5.2F);

		bodyModel[21].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 45
		bodyModel[21].setRotationPoint(-7F, 7.5F, -5.3F);

		bodyModel[22].addShapeBox(-5F, -5F, 0F, 24, 8, 0, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F, -3.25F, -2F, 0F); // Box 46
		bodyModel[22].setRotationPoint(-7F, 7.5F, -5.4F);

		bodyModel[23].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[23].setRotationPoint(-7.5F, 6.5F, 4.5F);

		bodyModel[24].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[24].setRotationPoint(-7.5F, 6.5F, 4.6F);

		bodyModel[25].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[25].setRotationPoint(-7.5F, 6.5F, 4.7F);

		bodyModel[26].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[26].setRotationPoint(-7.5F, 6.5F, 4.8F);

		bodyModel[27].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[27].setRotationPoint(-7.5F, 6.5F, 4.9F);

		bodyModel[28].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[28].setRotationPoint(-7.5F, 6.5F, 5F);

		bodyModel[29].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[29].setRotationPoint(-7.5F, 6.5F, -4.5F);

		bodyModel[30].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[30].setRotationPoint(-7.5F, 6.5F, -4.6F);

		bodyModel[31].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[31].setRotationPoint(-7.5F, 6.5F, -4.7F);

		bodyModel[32].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[32].setRotationPoint(-7.5F, 6.5F, -4.8F);

		bodyModel[33].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[33].setRotationPoint(-7.5F, 6.5F, -4.9F);

		bodyModel[34].addShapeBox(-5F, -5F, 0F, 10, 10, 0, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F, -1.5F, -1.5F, 0F); // Wheel
		bodyModel[34].setRotationPoint(-7.5F, 6.5F, -5F);

	}
}