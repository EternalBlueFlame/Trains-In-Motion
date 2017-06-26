/**
 * This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
 * Copyright (C) 2017 Minecraft-SMP.de
 * This file is for Flan's Flying Mod Version 4.0.x+
 *
 * @author ApocTheWanderer
 */
package ebf.tim.models.rails;

import ebf.tim.models.tmt.ModelBase;
import ebf.tim.models.tmt.ModelRendererTurbo;
import net.minecraft.entity.Entity;

public class ModelRailStraight extends ModelBase
{
	private static final int textureX = 128;
	private static final int textureY = 32;

	public ModelRailStraight()
	{
		baseModel = new ModelRendererTurbo[10];
		baseModel[0] = new ModelRendererTurbo(this, 20, 10, textureX, textureY); // Box 0
		baseModel[1] = new ModelRendererTurbo(this, 39, 6, textureX, textureY); // Box 1
		baseModel[2] = new ModelRendererTurbo(this, 58, 2, textureX, textureY); // Box 2
		baseModel[3] = new ModelRendererTurbo(this, 1, 14, textureX, textureY); // Box 3
		baseModel[4] = new ModelRendererTurbo(this, 53, 29, textureX, textureY); // Box 4
		baseModel[5] = new ModelRendererTurbo(this, 53, 26, textureX, textureY); // Box 5
		baseModel[6] = new ModelRendererTurbo(this, 1, 7, textureX, textureY); // Box 6
		baseModel[7] = new ModelRendererTurbo(this, 1, 7, textureX, textureY); // Box 7
		baseModel[8] = new ModelRendererTurbo(this, 35, 2, textureX, textureY); // Box 8
		baseModel[9] = new ModelRendererTurbo(this, 1, 3, textureX, textureY); // Box 9

		baseModel[0].addBox(0F, 0F, -7F, 2, 2, 14, 0F); // Box 0
		baseModel[0].setRotationPoint(1F, 8F, 0F);

		baseModel[1].addBox(0F, 0F, -7F, 2, 2, 14, 0F); // Box 1
		baseModel[1].setRotationPoint(-3F, 8F, 0F);

		baseModel[2].addBox(0F, 0F, -7F, 2, 2, 14, 0F); // Box 2
		baseModel[2].setRotationPoint(-7F, 8F, 0F);

		baseModel[3].addBox(0F, 0F, -7F, 2, 2, 14, 0F); // Box 3
		baseModel[3].setRotationPoint(5F, 8F, 0F);

		baseModel[4].addBox(-8F, 0F, 0F, 16, 1, 2, 0F); // Box 4
		baseModel[4].setRotationPoint(0F, 7F, -6F);

		baseModel[5].addBox(-8F, 0F, 0F, 16, 1, 2, 0F); // Box 5
		baseModel[5].setRotationPoint(0F, 7F, 4F);

		baseModel[6].addBox(-8F, 0F, 0F, 16, 1, 1, 0F); // Box 6
		baseModel[6].setRotationPoint(0F, 6F, -5.5F);

		baseModel[7].addBox(-8F, 0F, 0F, 16, 1, 1, 0F); // Box 7
		baseModel[7].setRotationPoint(0F, 6F, 4.5F);

		baseModel[8].addBox(-8F, 0F, 0F, 16, 1, 2, 0F); // Box 8
		baseModel[8].setRotationPoint(0F, 5F, 4F);

		baseModel[9].addBox(-8F, 0F, 0F, 16, 1, 2, 0F); // Box 9
		baseModel[9].setRotationPoint(0F, 5F, -6F);
	}
}