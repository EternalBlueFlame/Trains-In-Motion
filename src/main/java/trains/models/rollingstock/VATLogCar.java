//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator:
// Created on:12.02.2017 - 22:37:23
// Last changed on: 12.02.2017 - 22:37:23

package trains.models.rollingstock;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import trains.models.tmt.ModelRendererTurbo;

public class VATLogCar extends ModelBase {
	int textureX = 256;
	int textureY = 64;

	public VATLogCar() {
		modelvatlogcarModel = new ModelRendererTurbo[27];
		modelvatlogcarModel[0] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 0
		modelvatlogcarModel[1] = new ModelRendererTurbo(this, 129, 1, textureX, textureY); // Box 1
		modelvatlogcarModel[2] = new ModelRendererTurbo(this, 1, 1, textureX, textureY); // Box 5
		modelvatlogcarModel[3] = new ModelRendererTurbo(this, 248, 2, textureX, textureY); // Box 6
		modelvatlogcarModel[4] = new ModelRendererTurbo(this, 113, 1, textureX, textureY); // Box 7
		modelvatlogcarModel[5] = new ModelRendererTurbo(this, 121, 1, textureX, textureY); // Box 8
		modelvatlogcarModel[6] = new ModelRendererTurbo(this, 129, 1, textureX, textureY); // Box 9
		modelvatlogcarModel[7] = new ModelRendererTurbo(this, 137, 1, textureX, textureY); // Box 10
		modelvatlogcarModel[8] = new ModelRendererTurbo(this, 239, 2, textureX, textureY); // Box 11
		modelvatlogcarModel[9] = new ModelRendererTurbo(this, 9, 1, textureX, textureY); // Box 12
		modelvatlogcarModel[10] = new ModelRendererTurbo(this, 1, 25, textureX, textureY); // Box 13
		modelvatlogcarModel[11] = new ModelRendererTurbo(this, 49, 25, textureX, textureY); // Box 14
		modelvatlogcarModel[12] = new ModelRendererTurbo(this, 53, 25, textureX, textureY); // Box 15
		modelvatlogcarModel[13] = new ModelRendererTurbo(this, 25, 25, textureX, textureY); // Box 16
		modelvatlogcarModel[14] = new ModelRendererTurbo(this, 33, 25, textureX, textureY); // Box 17
		modelvatlogcarModel[15] = new ModelRendererTurbo(this, 41, 25, textureX, textureY); // Box 18
		modelvatlogcarModel[16] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, "BlockLog"); // BlockLog
		modelvatlogcarModel[17] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, "BlockLog"); // BlockLog 2
		modelvatlogcarModel[18] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, "BlockLog"); // BlockLog 3
		modelvatlogcarModel[19] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, "BlockLog"); // BlockLog 4
		modelvatlogcarModel[20] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, "BlockLog"); // BlockLog 5
		modelvatlogcarModel[21] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, "BlockLog"); // BlockLog 6
		modelvatlogcarModel[22] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, "BlockLog"); // BlockLog 7
		modelvatlogcarModel[23] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, "BlockLog"); // BlockLog 8
		modelvatlogcarModel[24] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, "BlockLog"); // BlockLog 9
		modelvatlogcarModel[25] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, "BlockLog"); // BlockLog 10
		modelvatlogcarModel[26] = new ModelRendererTurbo(this, 73, 25, textureX, textureY, "BlockLog"); // BlockLog 11

		modelvatlogcarModel[0].addBox(0F, 0F, 0F, 18, 2, 44, 0F); // Box 0
		modelvatlogcarModel[0].setRotationPoint(-9F, -2F, -22F);

		modelvatlogcarModel[1].addBox(0F, 0F, 0F, 16, 5, 44, 0F); // Box 1
		modelvatlogcarModel[1].setRotationPoint(-8F, 0F, -22F);

		modelvatlogcarModel[2].addBox(0F, 0F, 0F, 1, 11, 1, 0F); // Box 5
		modelvatlogcarModel[2].setRotationPoint(-10F, -11F, -16F);

		modelvatlogcarModel[3].addBox(0F, 0F, 0F, 1, 11, 1, 0F); // Box 6
		modelvatlogcarModel[3].setRotationPoint(-10F, -11F, 16F);

		modelvatlogcarModel[4].addBox(0F, 0F, 0F, 1, 11, 1, 0F); // Box 7
		modelvatlogcarModel[4].setRotationPoint(-10F, -11F, 6F);

		modelvatlogcarModel[5].addBox(0F, 0F, 0F, 1, 11, 1, 0F); // Box 8
		modelvatlogcarModel[5].setRotationPoint(-10F, -11F, -6F);

		modelvatlogcarModel[6].addBox(0F, 0F, 0F, 1, 11, 1, 0F); // Box 9
		modelvatlogcarModel[6].setRotationPoint(9F, -11F, 16F);

		modelvatlogcarModel[7].addBox(0F, 0F, 0F, 1, 11, 1, 0F); // Box 10
		modelvatlogcarModel[7].setRotationPoint(9F, -11F, 6F);

		modelvatlogcarModel[8].addBox(0F, 0F, 0F, 1, 11, 1, 0F); // Box 11
		modelvatlogcarModel[8].setRotationPoint(9F, -11F, -6F);

		modelvatlogcarModel[9].addBox(0F, 0F, 0F, 1, 11, 1, 0F); // Box 12
		modelvatlogcarModel[9].setRotationPoint(9F, -11F, -16F);

		modelvatlogcarModel[10].addBox(0F, 0F, 0F, 20, 4, 1, 0F); // Box 13
		modelvatlogcarModel[10].setRotationPoint(-10F, 0F, 22F);

		modelvatlogcarModel[11].addBox(0F, 0F, 0F, 20, 4, 1, 0F); // Box 14
		modelvatlogcarModel[11].setRotationPoint(-10F, 0F, -23F);

		modelvatlogcarModel[12].addBox(0F, 0F, 0F, 1, 1, 4, 0F); // Box 15
		modelvatlogcarModel[12].setRotationPoint(0F, 4F, 22F);

		modelvatlogcarModel[13].addBox(0F, 0F, 0F, 2, 2, 1, 0F); // Box 16
		modelvatlogcarModel[13].setRotationPoint(-0.5F, 3.5F, 26F);

		modelvatlogcarModel[14].addBox(0F, 0F, 0F, 2, 2, 1, 0F); // Box 17
		modelvatlogcarModel[14].setRotationPoint(-0.5F, 3.5F, -27F);

		modelvatlogcarModel[15].addBox(0F, 0F, 0F, 1, 1, 4, 0F); // Box 18
		modelvatlogcarModel[15].setRotationPoint(0F, 4F, -26F);

		modelvatlogcarModel[16].addBox(0F, 0F, 0F, 4, 4, 48, 0F); // BlockLog
		modelvatlogcarModel[16].setRotationPoint(4F, -6F, -24F);

		modelvatlogcarModel[17].addBox(0F, 0F, 0F, 4, 4, 48, 0F); // BlockLog 2
		modelvatlogcarModel[17].setRotationPoint(0F, -6F, -24F);

		modelvatlogcarModel[18].addBox(0F, 0F, 0F, 4, 4, 48, 0F); // BlockLog 3
		modelvatlogcarModel[18].setRotationPoint(-4F, -6F, -24F);

		modelvatlogcarModel[19].addBox(0F, 0F, 0F, 4, 4, 48, 0F); // BlockLog 4
		modelvatlogcarModel[19].setRotationPoint(-8F, -6F, -24F);

		modelvatlogcarModel[20].addBox(0F, 0F, 0F, 4, 4, 48, 0F); // BlockLog 5
		modelvatlogcarModel[20].setRotationPoint(-4F, -10F, -24F);

		modelvatlogcarModel[21].addBox(0F, 0F, 0F, 4, 4, 48, 0F); // BlockLog 6
		modelvatlogcarModel[21].setRotationPoint(0F, -10F, -24F);

		modelvatlogcarModel[22].addBox(0F, 0F, 0F, 4, 4, 48, 0F); // BlockLog 7
		modelvatlogcarModel[22].setRotationPoint(4F, -10F, -24F);

		modelvatlogcarModel[23].addBox(0F, 0F, 0F, 4, 4, 48, 0F); // BlockLog 8
		modelvatlogcarModel[23].setRotationPoint(-8F, -10F, -24F);

		modelvatlogcarModel[24].addBox(0F, 0F, 0F, 4, 4, 48, 0F); // BlockLog 9
		modelvatlogcarModel[24].setRotationPoint(2F, -14F, -24F);

		modelvatlogcarModel[25].addBox(0F, 0F, 0F, 4, 4, 48, 0F); // BlockLog 10
		modelvatlogcarModel[25].setRotationPoint(-2F, -14F, -24F);

		modelvatlogcarModel[26].addBox(0F, 0F, 0F, 4, 4, 48, 0F); // BlockLog 11
		modelvatlogcarModel[26].setRotationPoint(-6F, -14F, -24F);


	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		for(int i = 0; i < 27; i++) {
			modelvatlogcarModel[i].render(f5);
		}
	}

	public ModelRendererTurbo modelvatlogcarModel[];
}