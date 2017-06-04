//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator:
// Created on:17.04.2017 - 16:25:28
// Last changed on: 17.04.2017 - 16:25:28

package ebf.tim.models.rails;

import ebf.tim.models.tmt.ModelRendererTurbo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelRailStraight extends ModelBase
{
	private static final int textureX = 256;
	private static final int textureY = 32;

	public ModelRailStraight()
	{
		railstraightModel = new ModelRendererTurbo[10];
		railstraightModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 0
		railstraightModel[1] = new ModelRendererTurbo(this, 41, 1, textureX, textureY); // Box 1
		railstraightModel[2] = new ModelRendererTurbo(this, 81, 1, textureX, textureY); // Box 2
		railstraightModel[3] = new ModelRendererTurbo(this, 121, 1, textureX, textureY); // Box 3
		railstraightModel[4] = new ModelRendererTurbo(this, 145, 1, textureX, textureY); // Box 4
		railstraightModel[5] = new ModelRendererTurbo(this, 185, 1, textureX, textureY); // Box 5
		railstraightModel[6] = new ModelRendererTurbo(this, 145, 9, textureX, textureY); // Box 6
		railstraightModel[7] = new ModelRendererTurbo(this, 185, 9, textureX, textureY); // Box 7
		railstraightModel[8] = new ModelRendererTurbo(this, 153, 17, textureX, textureY); // Box 8
		railstraightModel[9] = new ModelRendererTurbo(this, 193, 17, textureX, textureY); // Box 9

		railstraightModel[0].addBox(0F, 0F, -7F, 2, 2, 14, 0F); // Box 0
		railstraightModel[0].setRotationPoint(0F, 8F, 0F);

		railstraightModel[1].addBox(0F, 0F, -7F, 2, 2, 14, 0F); // Box 1
		railstraightModel[1].setRotationPoint(-4F, 8F, 0F);

		railstraightModel[2].addBox(0F, 0F, -7F, 2, 2, 14, 0F); // Box 2
		railstraightModel[2].setRotationPoint(-8F, 8F, 0F);

		railstraightModel[3].addBox(0F, 0F, -7F, 2, 2, 14, 0F); // Box 3
		railstraightModel[3].setRotationPoint(4F, 8F, 0F);

		railstraightModel[4].addBox(-8F, 0F, 0F, 16, 1, 2, 0F); // Box 4
		railstraightModel[4].setRotationPoint(-1F, 7F, -6F);

		railstraightModel[5].addBox(-8F, 0F, 0F, 16, 1, 2, 0F); // Box 5
		railstraightModel[5].setRotationPoint(-1F, 7F, 4F);

		railstraightModel[6].addBox(-8F, 0F, 0F, 16, 1, 1, 0F); // Box 6
		railstraightModel[6].setRotationPoint(-1F, 6F, -5.5F);

		railstraightModel[7].addBox(-8F, 0F, 0F, 16, 1, 1, 0F); // Box 7
		railstraightModel[7].setRotationPoint(-1F, 6F, 4.5F);

		railstraightModel[8].addBox(-8F, 0F, 0F, 16, 1, 2, 0F); // Box 8
		railstraightModel[8].setRotationPoint(-1F, 5F, 4F);

		railstraightModel[9].addBox(-8F, 0F, 0F, 16, 1, 2, 0F); // Box 9
		railstraightModel[9].setRotationPoint(-1F, 5F, -6F);

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		for(int i = 0; i < 10; i++)
		{
			railstraightModel[i].render();
		}
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
	}

	public static ModelRendererTurbo railstraightModel[];
}