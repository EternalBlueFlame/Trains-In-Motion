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
import ebf.tim.models.tmt.ModelRendererTurbo;
import ebf.tim.models.tmt.ModelBase;
import net.minecraft.entity.Entity;

public class CMDBogie extends ModelBase
{
	private static final int textureX = 64;
	private static final int textureY = 32;

	public CMDBogie()
	{
		cmdbogieModel = new ModelRendererTurbo[29];
		cmdbogieModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 13
		cmdbogieModel[1] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 22
		cmdbogieModel[2] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 17
		cmdbogieModel[3] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 16
		cmdbogieModel[4] = new ModelRendererTurbo(this, 5, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Axel 1
		cmdbogieModel[5] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 12
		cmdbogieModel[6] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 7
		cmdbogieModel[7] = new ModelRendererTurbo(this, 3, 17, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Axel 2
		cmdbogieModel[8] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 6
		cmdbogieModel[9] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 1
		cmdbogieModel[10] = new ModelRendererTurbo(this, 27, 9, textureX, textureY); // Box 10
		cmdbogieModel[11] = new ModelRendererTurbo(this, 23, 1, textureX, textureY); // Box 11
		cmdbogieModel[12] = new ModelRendererTurbo(this, 23, 5, textureX, textureY); // Box 12
		cmdbogieModel[13] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 5
		cmdbogieModel[14] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 4
		cmdbogieModel[15] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 3
		cmdbogieModel[16] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 2
		cmdbogieModel[17] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 21
		cmdbogieModel[18] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 20
		cmdbogieModel[19] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 19
		cmdbogieModel[20] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 18
		cmdbogieModel[21] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 16
		cmdbogieModel[22] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 16
		cmdbogieModel[23] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 15
		cmdbogieModel[24] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 14
		cmdbogieModel[25] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 8
		cmdbogieModel[26] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 9
		cmdbogieModel[27] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 10
		cmdbogieModel[28] = new ModelRendererTurbo(this, 1, 1, textureX, textureY, StaticModelAnimator.tagSimpleRotate); // Wheel 11

		cmdbogieModel[0].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 13
		cmdbogieModel[0].setRotationPoint(-10F, 5F, -5F);

		cmdbogieModel[1].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 22
		cmdbogieModel[1].setRotationPoint(-10F, 5F, 5F);

		cmdbogieModel[2].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 17
		cmdbogieModel[2].setRotationPoint(-10F, 5F, 4.5F);

		cmdbogieModel[3].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 16
		cmdbogieModel[3].setRotationPoint(-10F, 5F, -4.5F);

		cmdbogieModel[4].addBox(-1F, -1F, 0F, 2, 2, 12, 0F); // Axel 1
		cmdbogieModel[4].setRotationPoint(-8.5F, 6.5F, -6F);

		cmdbogieModel[5].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 12
		cmdbogieModel[5].setRotationPoint(7F, 5F, -5F);

		cmdbogieModel[6].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 7
		cmdbogieModel[6].setRotationPoint(7F, 5F, -4.5F);

		cmdbogieModel[7].addBox(-1F, -1F, 0F, 2, 2, 12, 0F); // Axel 2
		cmdbogieModel[7].setRotationPoint(8.5F, 6.5F, -6F);

		cmdbogieModel[8].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 6
		cmdbogieModel[8].setRotationPoint(7F, 5F, 4.5F);

		cmdbogieModel[9].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 1
		cmdbogieModel[9].setRotationPoint(7F, 5F, 5F);

		cmdbogieModel[10].addBox(0F, 0F, 0F, 7, 8, 11, 0F); // Box 10
		cmdbogieModel[10].setRotationPoint(-3.5F, 0.5F, -5.5F);

		cmdbogieModel[11].addBox(0F, 0F, 0F, 20, 3, 0, 0F); // Box 11
		cmdbogieModel[11].setRotationPoint(-10F, 5F, 5.55F);

		cmdbogieModel[12].addBox(0F, 0F, 0F, 20, 3, 0, 0F); // Box 12
		cmdbogieModel[12].setRotationPoint(-10F, 5F, -5.55F);

		cmdbogieModel[13].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 5
		cmdbogieModel[13].setRotationPoint(7F, 5F, 4.6F);

		cmdbogieModel[14].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 4
		cmdbogieModel[14].setRotationPoint(7F, 5F, 4.7F);

		cmdbogieModel[15].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 3
		cmdbogieModel[15].setRotationPoint(7F, 5F, 4.8F);

		cmdbogieModel[16].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 2
		cmdbogieModel[16].setRotationPoint(7F, 5F, 4.9F);

		cmdbogieModel[17].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 21
		cmdbogieModel[17].setRotationPoint(-10F, 5F, 4.9F);

		cmdbogieModel[18].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 20
		cmdbogieModel[18].setRotationPoint(-10F, 5F, 4.8F);

		cmdbogieModel[19].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 19
		cmdbogieModel[19].setRotationPoint(-10F, 5F, 4.7F);

		cmdbogieModel[20].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 18
		cmdbogieModel[20].setRotationPoint(-10F, 5F, 4.6F);

		cmdbogieModel[21].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 16
		cmdbogieModel[21].setRotationPoint(-10F, 5F, -4.6F);

		cmdbogieModel[22].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 16
		cmdbogieModel[22].setRotationPoint(-10F, 5F, -4.7F);

		cmdbogieModel[23].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 15
		cmdbogieModel[23].setRotationPoint(-10F, 5F, -4.8F);

		cmdbogieModel[24].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 14
		cmdbogieModel[24].setRotationPoint(-10F, 5F, -4.9F);

		cmdbogieModel[25].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 8
		cmdbogieModel[25].setRotationPoint(7F, 5F, -4.6F);

		cmdbogieModel[26].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 9
		cmdbogieModel[26].setRotationPoint(7F, 5F, -4.7F);

		cmdbogieModel[27].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 10
		cmdbogieModel[27].setRotationPoint(7F, 5F, -4.8F);

		cmdbogieModel[28].addBox(-2F, -2F, 0F, 7, 7, 0, 0F); // Wheel 11
		cmdbogieModel[28].setRotationPoint(7F, 5F, -4.9F);

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		for(int i = 0; i < 13; i++)
		{
			cmdbogieModel[i].render(f5);
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
	}

	private ModelRendererTurbo cmdbogieModel[];
}