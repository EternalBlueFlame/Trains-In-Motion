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

import ebf.tim.models.tmt.ModelRendererTurbo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;

public class CMDBogie extends ModelBase
{
	private static final int textureXY = 32;

	public CMDBogie()
	{
		cmdbogieModel = new ModelRendererTurbo[13];
		cmdbogieModel[0] = new ModelRendererTurbo(this, 1, 1, textureXY, textureXY, "wheel"); // Wheel 5
		cmdbogieModel[1] = new ModelRendererTurbo(this, 1, 1, textureXY, textureXY, "wheel"); // Wheel 8
		cmdbogieModel[2] = new ModelRendererTurbo(this, 10, 1, textureXY, textureXY, "wheel"); // Wheel 7
		cmdbogieModel[3] = new ModelRendererTurbo(this, 10, 1, textureXY, textureXY, "wheel"); // Wheel 6
		cmdbogieModel[4] = new ModelRendererTurbo(this, 1, 1, textureXY, textureXY, "axel"); // Axel 1
		cmdbogieModel[5] = new ModelRendererTurbo(this, 1, 1, textureXY, textureXY, "wheel"); // Wheel 4
		cmdbogieModel[6] = new ModelRendererTurbo(this, 10, 1, textureXY, textureXY, "wheel"); // Wheel 3
		cmdbogieModel[7] = new ModelRendererTurbo(this, 1, 1, textureXY, textureXY, "axel"); // Axel 2
		cmdbogieModel[8] = new ModelRendererTurbo(this, 10, 1, textureXY, textureXY, "wheel"); // Wheel 2
		cmdbogieModel[9] = new ModelRendererTurbo(this, 1, 1, textureXY, textureXY, "wheel"); // Wheel 1
		cmdbogieModel[10] = new ModelRendererTurbo(this, 2, 12, textureXY, textureXY); // Box 10
		cmdbogieModel[11] = new ModelRendererTurbo(this, 6, 14, textureXY, textureXY); // Box 11
		cmdbogieModel[12] = new ModelRendererTurbo(this, 6, 11, textureXY, textureXY); // Box 12

		cmdbogieModel[0].addBox(-2F, -2F, 0F, 4, 4, 0, 0F); // Wheel 5
		cmdbogieModel[0].setRotationPoint(-4F, 8F, -5F);

		cmdbogieModel[1].addBox(-2F, -2F, 0F, 4, 4, 0, 0F); // Wheel 8
		cmdbogieModel[1].setRotationPoint(-4F, 8F, 5F);

		cmdbogieModel[2].addBox(-2F, -2F, 0F, 4, 4, 0, 0F); // Wheel 7
		cmdbogieModel[2].setRotationPoint(-4F, 8F, 4.5F);

		cmdbogieModel[3].addBox(-2F, -2F, 0F, 4, 4, 0, 0F); // Wheel 6
		cmdbogieModel[3].setRotationPoint(-4F, 8F, -4.5F);

		cmdbogieModel[4].addBox(-0.5F, -0.5F, 0F, 1, 1, 12, 0F); // Axel 1
		cmdbogieModel[4].setRotationPoint(-4F, 8F, -6F);

		cmdbogieModel[5].addBox(-2F, -2F, 0F, 4, 4, 0, 0F); // Wheel 4
		cmdbogieModel[5].setRotationPoint(4F, 8F, -5F);

		cmdbogieModel[6].addBox(-2F, -2F, 0F, 4, 4, 0, 0F); // Wheel 3
		cmdbogieModel[6].setRotationPoint(4F, 8F, -4.5F);

		cmdbogieModel[7].addBox(-0.5F, -0.5F, 0F, 1, 1, 12, 0F); // Axel 2
		cmdbogieModel[7].setRotationPoint(4F, 8F, -6F);

		cmdbogieModel[8].addBox(-2F, -2F, 0F, 4, 4, 0, 0F); // Wheel 2
		cmdbogieModel[8].setRotationPoint(4F, 8F, 4.5F);

		cmdbogieModel[9].addBox(-2F, -2F, 0F, 4, 4, 0, 0F); // Wheel 1
		cmdbogieModel[9].setRotationPoint(4F, 8F, 5F);

		cmdbogieModel[10].addBox(0F, 0F, 0F, 3, 4, 11, 0F); // Box 10
		cmdbogieModel[10].setRotationPoint(-1.5F, 5.5F, -5.5F);

		cmdbogieModel[11].addBox(0F, 0F, 0F, 10, 2, 0, 0F); // Box 11
		cmdbogieModel[11].setRotationPoint(-5F, 7F, 5.5F);

		cmdbogieModel[12].addBox(0F, 0F, 0F, 10, 2, 0, 0F); // Box 12
		cmdbogieModel[12].setRotationPoint(-5F, 7F, -5.5F);


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