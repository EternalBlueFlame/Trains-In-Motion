//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator:
// Created on:-
// Last changed on: -

package ebf.tim.models.rails;

import ebf.tim.blocks.RailTileEntity;
import ebf.tim.utility.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import tmt.Tessellator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModelRailSegment
{

	private static float heightOffset=0.05f;
	public float[] position = new float[]{0,0,0};
	public float[] zOffset = null;
	public List<subModel> models = new ArrayList<>();
	RailTileEntity host;


	public subModel genNewSubModel(float[] inner, float[] outer, RailTileEntity hostObj, byte partID){
		return new subModel(inner, outer, hostObj, partID);
	}

	public class subModel{

		public subModel(float[] inner, float[]outer, RailTileEntity hostObj, byte partID){
			positionInner = inner;
			positionOuter = outer;
			offset = new float[]{new Random().nextInt(4),0,0};
			host = hostObj;
			type = partID;

		}
		public float[] positionInner = null;
		public float[] positionOuter = null;
		public float[] lastPositionInner = null;
		public float[] lastPositionOuter = null;
		public float offset[];
		public byte type =0;

		public void render(Tessellator tessellator, boolean isFront, boolean isEnd, Block ballast, Block ties) {
			if (positionOuter != null && positionInner != null && lastPositionOuter!=null && lastPositionInner != null) {

				GL11.glPushMatrix();

				//set the color with the tint.   * 0.00392156863 is the same as /255, but multiplication is more efficient than division and doesn't fry on 0.
				//set the position

				GL11.glEnable(GL11.GL_VERTEX_ARRAY);
				GL11.glEnable(GL11.GL_TEXTURE_COORD_ARRAY);
				switch (ClientProxy.railSkin) {
					case 1: {
						switch (type) {
							case 1: {
								model3DBallast(tessellator, ballast);
								break;
							}
							default: {
								GL11.glDisable(GL11.GL_TEXTURE_2D);
								model3DMC(tessellator, isFront, isEnd);
								GL11.glEnable(GL11.GL_TEXTURE_2D);
							}
						}
						break;
					}
					case 2: {
						switch (type) {
							case 1: {
								model3DBallast(tessellator, Blocks.gravel);
								break;
							}
							case 2:{
								model3DTies(tessellator, Blocks.log);
								break;
							}
							default: {
								GL11.glDisable(GL11.GL_TEXTURE_2D);
								modelTiM(tessellator, isFront, isEnd);
								GL11.glEnable(GL11.GL_TEXTURE_2D);
							}
						}
						break;
					}
					case 3: {
						switch (type) {
							case 1: {
								model3DBallast(tessellator, ballast);
								break;
							}
							default: {
								GL11.glDisable(GL11.GL_TEXTURE_2D);
								modelTC(tessellator, isFront, isEnd);
								GL11.glEnable(GL11.GL_TEXTURE_2D);
							}
						}
						break;
					}

					default: {
						switch (type){
							case 1:{
								modelPotatoBallast(tessellator, ballast);
								break;
							}
							default:{
								GL11.glDisable(GL11.GL_TEXTURE_2D);
								modelPotato(tessellator);
								GL11.glEnable(GL11.GL_TEXTURE_2D);
							}
						}
						break;
					}
				}

				GL11.glDisable(GL11.GL_TEXTURE_COORD_ARRAY);
				GL11.glDisable(GL11.GL_VERTEX_ARRAY);
				GL11.glPopMatrix();
			}
		}


		/*
		*-------------------------------------------------------------
		*Ties
		*-------------------------------------------------------------
		 */

		public void model3DTies(Tessellator tessellator, Block tie){

			if (tie!=null) {
				Tessellator.bindTexture(TextureMap.locationBlocksTexture);

				IIcon iicon;

				if (RenderBlocks.getInstance().hasOverrideBlockTexture()) {
					iicon = RenderBlocks.getInstance().overrideBlockTexture;
				} else {
					iicon = RenderBlocks.getInstance().getBlockIconFromSide(tie, ForgeDirection.WEST.ordinal());
				}

				float d0 = iicon.getMinU() + offset[0];
				float d1 = iicon.getMinV();
				float d2 = iicon.getMinU() + offset[1];
				float d3 = iicon.getMaxV();

				tessellator.startDrawing(GL11.GL_QUADS);
				//top inner
				tessellator.addVertexWithUV(positionInner[0],positionInner[1]+0.15f, positionInner[2],d0,d1);
				tessellator.addVertexWithUV(lastPositionInner[0],lastPositionInner[1]+0.15f,lastPositionInner[2],d2,d1);
				//top outer

				tessellator.addVertexWithUV(lastPositionOuter[0],lastPositionOuter[1]+0.15f,lastPositionOuter[2],d2,d3);
				tessellator.addVertexWithUV(positionOuter[0],positionOuter[1]+0.15f, positionOuter[2],d0,d3);
				tessellator.arrayEnabledDraw();

				if (iicon != RenderBlocks.getInstance().overrideBlockTexture) {
					iicon = RenderBlocks.getInstance().getBlockIconFromSide(tie, ForgeDirection.NORTH.ordinal());
					d0 = iicon.getMinU() + offset[0];
					d1 = iicon.getMinV();
					d2 = iicon.getMinU() + offset[1];
					d3 = iicon.getMaxV();
				}

				tessellator.startDrawing(GL11.GL_QUADS);
				//top inner
				tessellator.addVertexWithUV(positionInner[0],0,positionInner[2],d2,d1);
				tessellator.addVertexWithUV(positionInner[0],positionInner[1]+0.15f, positionInner[2],d0,d1);
				//top outer

				tessellator.addVertexWithUV(positionOuter[0],positionOuter[1]+0.15f, positionOuter[2],d0,d3);
				tessellator.addVertexWithUV(positionOuter[0],0,positionOuter[2],d2,d3);
				tessellator.arrayEnabledDraw();

				if (iicon != RenderBlocks.getInstance().overrideBlockTexture) {
					iicon = RenderBlocks.getInstance().getBlockIconFromSide(tie, ForgeDirection.SOUTH.ordinal());
					d0 = iicon.getMinU() + offset[0];
					d1 = iicon.getMinV();
					d2 = iicon.getMinU() + offset[1];
					d3 = iicon.getMaxV();
				}

				tessellator.startDrawing(GL11.GL_QUADS);
				//top inner
				tessellator.addVertexWithUV(lastPositionInner[0],lastPositionInner[1]+0.15f, lastPositionInner[2],d0,d1);
				tessellator.addVertexWithUV(lastPositionInner[0],0,lastPositionInner[2],d2,d1);
				//top outer

				tessellator.addVertexWithUV(lastPositionOuter[0],0,lastPositionOuter[2],d2,d3);
				tessellator.addVertexWithUV(lastPositionOuter[0],lastPositionOuter[1]+0.15f, lastPositionOuter[2],d0,d3);
				tessellator.arrayEnabledDraw();

				if(offset[2]==1 || offset[2]==3) {
					if (iicon != RenderBlocks.getInstance().overrideBlockTexture) {
						iicon = RenderBlocks.getInstance().getBlockIconFromSide(tie, ForgeDirection.UP.ordinal());
						d0 = iicon.getMinU();
						d1 = iicon.getMaxV();
						d2 = iicon.getMaxU();
						d3 = iicon.getMaxV();
					}

					tessellator.startDrawing(GL11.GL_QUADS);
					//bottom outer
					tessellator.addVertexWithUV(positionOuter[0], positionOuter[1] + 0.15f, positionOuter[2], d0, d1);
					tessellator.addVertexWithUV(lastPositionOuter[0], lastPositionOuter[1] + 0.15f, lastPositionOuter[2], d2, d1);

					tessellator.addVertexWithUV(lastPositionOuter[0], 0, lastPositionOuter[2], d2, d3 - ((1 - lastPositionOuter[1]) * 0.03f));
					tessellator.addVertexWithUV(positionOuter[0], 0, positionOuter[2], d0, d3 - ((1 - positionOuter[1]) * 0.03f));
					tessellator.arrayEnabledDraw();
				}

				if(offset[2]==2 || offset[2]==3) {
					if (iicon != RenderBlocks.getInstance().overrideBlockTexture) {
						iicon = RenderBlocks.getInstance().getBlockIconFromSide(tie, ForgeDirection.DOWN.ordinal());
						d0 = iicon.getMinU();
						d1 = iicon.getMaxV();
						d2 = iicon.getMaxU();
						d3 = iicon.getMaxV();
					}

					tessellator.startDrawing(GL11.GL_QUADS);
					//bottom outer
					tessellator.addVertexWithUV(lastPositionInner[0], lastPositionInner[1] + 0.15f, lastPositionInner[2], d0, d1);
					tessellator.addVertexWithUV(positionInner[0], positionInner[1] + 0.15f, positionInner[2], d2, d1);

					tessellator.addVertexWithUV(positionInner[0], 0, positionInner[2], d2, d3 - ((1 - positionInner[1]) * 0.03f));
					tessellator.addVertexWithUV(lastPositionInner[0], 0, lastPositionInner[2], d0, d3 - ((1 - lastPositionInner[1]) * 0.03f));
					tessellator.arrayEnabledDraw();
				}
			}
		}

		/*
		*-------------------------------------------------------------
		*Ballast
		*-------------------------------------------------------------
		 */

		public void modelPotatoBallast(Tessellator tessellator, Block ballast){
			if (ballast!=null) {
				Tessellator.bindTexture(TextureMap.locationBlocksTexture);

				IIcon iicon;

				if (RenderBlocks.getInstance().hasOverrideBlockTexture()) {
					iicon = RenderBlocks.getInstance().overrideBlockTexture;
				} else {
					iicon = RenderBlocks.getInstance().getBlockIconFromSide(ballast, 0);
				}

				float d0 = iicon.getMinU() + offset[0];
				float d1 = iicon.getMinV();
				float d2 = iicon.getMinU() + offset[1];
				float d3 = iicon.getMaxV();

				//top inner
				tessellator.startDrawing(GL11.GL_QUAD_STRIP);
				tessellator.addVertexWithUV(positionInner[0], positionInner[1] + 0.05f, positionInner[2],
						d0, d1);
				tessellator.addVertexWithUV(lastPositionInner[0], lastPositionInner[1] + 0.05f, lastPositionInner[2],
						d2, d1);
				//top outer
				tessellator.addVertexWithUV(positionOuter[0], positionOuter[1] + 0.05f, positionOuter[2],
						d0, d3);
				tessellator.addVertexWithUV(lastPositionOuter[0], lastPositionOuter[1] + 0.05f, lastPositionOuter[2],
						d2, d3);
				tessellator.arrayEnabledDraw();
			}
		}

		public void model3DBallast(Tessellator tessellator, Block ballast){

			if (ballast!=null) {
				Tessellator.bindTexture(TextureMap.locationBlocksTexture);

				IIcon iicon;

				if (RenderBlocks.getInstance().hasOverrideBlockTexture()) {
					iicon = RenderBlocks.getInstance().overrideBlockTexture;
				} else {
					iicon = RenderBlocks.getInstance().getBlockIconFromSide(ballast, ForgeDirection.UP.ordinal());
				}

				float d0 = iicon.getMinU() + offset[0];
				float d1 = iicon.getMinV();
				float d2 = iicon.getMinU() + offset[1];
				float d3 = iicon.getMaxV();


				tessellator.startDrawing(GL11.GL_QUADS);
				//top inner
				tessellator.addVertexWithUV(positionInner[0],positionOuter[1]+0.1f, positionInner[2],d0,d1);
				tessellator.addVertexWithUV(lastPositionInner[0],lastPositionOuter[1]+0.1f,lastPositionInner[2],d2,d1);
				//top outer

				tessellator.addVertexWithUV(lastPositionOuter[0],lastPositionOuter[1]+0.1f,lastPositionOuter[2],d2,d3);
				tessellator.addVertexWithUV(positionOuter[0],positionOuter[1]+0.1f, positionOuter[2],d0,d3);
				tessellator.arrayEnabledDraw();


				if(iicon!=RenderBlocks.getInstance().overrideBlockTexture){
					iicon = RenderBlocks.getInstance().getBlockIconFromSide(ballast, ForgeDirection.WEST.ordinal());
					d0 = iicon.getMinU() + offset[0];
					d1 = iicon.getMinV();
					d2 = iicon.getMinU() + offset[1];
					d3 = iicon.getMaxV();
				}

				tessellator.startDrawing(GL11.GL_QUADS);
				//bottom outer
				tessellator.addVertexWithUV(positionOuter[0],positionOuter[1]+0.1f, positionOuter[2],d2,d1);
				tessellator.addVertexWithUV(lastPositionOuter[0],lastPositionOuter[1]+0.1f,lastPositionOuter[2],d0,d1);

				tessellator.addVertexWithUV(lastPositionOuter[0],0,lastPositionOuter[2],d0,d3-((1-lastPositionOuter[1])*0.03f));
				tessellator.addVertexWithUV(positionOuter[0],0, positionOuter[2],d2,d3-((1-positionOuter[1])*0.03f));
				tessellator.arrayEnabledDraw();

				if(iicon!=RenderBlocks.getInstance().overrideBlockTexture){
					iicon = RenderBlocks.getInstance().getBlockIconFromSide(ballast, ForgeDirection.EAST.ordinal());
					d0 = iicon.getMinU() + offset[0];
					d1 = iicon.getMinV();
					d2 = iicon.getMinU() + offset[1];
					d3 = iicon.getMaxV();
				}

				tessellator.startDrawing(GL11.GL_QUADS);
				//bottom outer
				tessellator.addVertexWithUV(lastPositionInner[0],lastPositionInner[1]+0.1f,lastPositionInner[2],d0,d1);
				tessellator.addVertexWithUV(positionInner[0],positionInner[1]+0.1f, positionInner[2],d2,d1);

				tessellator.addVertexWithUV(positionInner[0],0, positionInner[2],d2,d3-((1-positionInner[1])*0.03f));
				tessellator.addVertexWithUV(lastPositionInner[0],0,lastPositionInner[2],d0,d3-((1-lastPositionInner[1])*0.03f));
				tessellator.arrayEnabledDraw();
			}
		}

		/*
		*-------------------------------------------------------------
		*Rails
		*-------------------------------------------------------------
		 */

		public void modelPotato(Tessellator tessellator){

			GL11.glTranslated(0,0.175,0);
			tessellator.startDrawing(GL11.GL_QUAD_STRIP);

			//top inner
			tessellator.addVertex(positionInner[0],positionInner[1], positionInner[2]);
			tessellator.addVertex(lastPositionInner[0],lastPositionInner[1],lastPositionInner[2]);
			//top outer
			tessellator.addVertex(positionOuter[0],positionOuter[1], positionOuter[2]);
			tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1],lastPositionOuter[2]);

			tessellator.arrayEnabledDraw();
		}


		public void model3DMC(Tessellator tessellator, boolean isFront, boolean isEnd){

			tessellator.startDrawing(GL11.GL_QUAD_STRIP);
			GL11.glTranslated(0,0.225,0);

			//top inner
			tessellator.addVertex(positionInner[0],positionInner[1], positionInner[2]);
			tessellator.addVertex(lastPositionInner[0],lastPositionInner[1],lastPositionInner[2]);
			//top outer
			tessellator.addVertex(positionOuter[0],positionOuter[1], positionOuter[2]);
			tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1],lastPositionOuter[2]);
			//bottom outer
			tessellator.addVertex(positionOuter[0],positionOuter[1]-heightOffset, positionOuter[2]);
			tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1]-heightOffset,lastPositionOuter[2]);
			//bottom inner
			tessellator.addVertex(positionInner[0],positionInner[1]-heightOffset, positionInner[2]);
			tessellator.addVertex(lastPositionInner[0],lastPositionInner[1]-heightOffset,lastPositionInner[2]);
			//top inner
			tessellator.addVertex(positionInner[0],positionInner[1], positionInner[2]);
			tessellator.addVertex(lastPositionInner[0],lastPositionInner[1],lastPositionInner[2]);

			tessellator.arrayEnabledDraw();


			//front and back only if its at the end of the list.
			if (isFront){
				tessellator.startDrawing(GL11.GL_QUADS);
				tessellator.addVertex(positionInner[0],positionInner[1], positionInner[2]);
				tessellator.addVertex(positionOuter[0],positionOuter[1], positionOuter[2]);
				tessellator.addVertex(positionOuter[0],positionOuter[1]-heightOffset, positionOuter[2]);
				tessellator.addVertex(positionInner[0],positionInner[1]-heightOffset, positionInner[2]);
				tessellator.arrayEnabledDraw();
			} else if(isEnd){
				tessellator.startDrawing(GL11.GL_QUADS);
				tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1], lastPositionOuter[2]);
				tessellator.addVertex(lastPositionInner[0],lastPositionInner[1], lastPositionInner[2]);
				tessellator.addVertex(lastPositionInner[0],lastPositionInner[1]-heightOffset, lastPositionInner[2]);
				tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1]-heightOffset, lastPositionOuter[2]);
				tessellator.arrayEnabledDraw();
			}
		}



		public void modelTiM(Tessellator tessellator, boolean isFront, boolean isEnd){

			GL11.glColor3f(0.65f,0.65f,0.65f);

			GL11.glTranslated(0,0.225,0);
			tessellator.startDrawing(GL11.GL_QUAD_STRIP);

			//top inner
			tessellator.addVertex(positionInner[0],positionInner[1], positionInner[2]);
			tessellator.addVertex(lastPositionInner[0],lastPositionInner[1],lastPositionInner[2]);
			//top outer
			tessellator.addVertex(positionOuter[0],positionOuter[1], positionOuter[2]);
			tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1],lastPositionOuter[2]);
			//bottom outer
			tessellator.addVertex(positionOuter[0],positionOuter[1]-heightOffset, positionOuter[2]);
			tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1]-heightOffset,lastPositionOuter[2]);
			//bottom inner
			tessellator.addVertex(positionInner[0],positionInner[1]-heightOffset, positionInner[2]);
			tessellator.addVertex(lastPositionInner[0],lastPositionInner[1]-heightOffset,lastPositionInner[2]);
			//top inner
			tessellator.addVertex(positionInner[0],positionInner[1], positionInner[2]);
			tessellator.addVertex(lastPositionInner[0],lastPositionInner[1],lastPositionInner[2]);

			tessellator.arrayEnabledDraw();
			GL11.glTranslated(0,0.1,0);
			GL11.glColor3f(0.7f,0.7f,0.7f);
			tessellator.startDrawing(GL11.GL_QUAD_STRIP);

			//top inner
			tessellator.addVertex(positionInner[0],positionInner[1], positionInner[2]);
			tessellator.addVertex(lastPositionInner[0],lastPositionInner[1],lastPositionInner[2]);
			//top outer
			tessellator.addVertex(positionOuter[0],positionOuter[1], positionOuter[2]);
			tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1],lastPositionOuter[2]);
			//bottom outer
			tessellator.addVertex(positionOuter[0],positionOuter[1]-heightOffset, positionOuter[2]);
			tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1]-heightOffset,lastPositionOuter[2]);
			//bottom inner
			tessellator.addVertex(positionInner[0],positionInner[1]-heightOffset, positionInner[2]);
			tessellator.addVertex(lastPositionInner[0],lastPositionInner[1]-heightOffset,lastPositionInner[2]);
			//top inner
			tessellator.addVertex(positionInner[0],positionInner[1], positionInner[2]);
			tessellator.addVertex(lastPositionInner[0],lastPositionInner[1],lastPositionInner[2]);

			tessellator.arrayEnabledDraw();


			GL11.glTranslated(0,-0.05,0);
			GL11.glColor3f(0.6f,0.6f,0.6f);
			GL11.glPushMatrix();
			//GL11.glScaled(0.75,1,1);
			tessellator.startDrawing(GL11.GL_QUAD_STRIP);
			//GL11.

			//top inner
			tessellator.addVertex(positionInner[0],positionInner[1], positionInner[2]);
			tessellator.addVertex(lastPositionInner[0],lastPositionInner[1],lastPositionInner[2]);
			//top outer
			tessellator.addVertex(positionOuter[0]-(zOffset[0]*0.5f),positionOuter[1], positionOuter[2]-(zOffset[2]*0.5f));
			tessellator.addVertex(lastPositionOuter[0]-(zOffset[0]*0.5f),lastPositionOuter[1],lastPositionOuter[2]-(zOffset[2]*0.5f));
			//bottom outer
			tessellator.addVertex(positionOuter[0]-(zOffset[0]*0.5f),positionOuter[1]-heightOffset, positionOuter[2]-(zOffset[2]*0.5f));
			tessellator.addVertex(lastPositionOuter[0]-(zOffset[0]*0.5f),lastPositionOuter[1]-heightOffset,lastPositionOuter[2]-(zOffset[2]*0.5f));
			//bottom inner
			tessellator.addVertex(positionInner[0]+(zOffset[0]*0.5f),positionInner[1]-heightOffset, positionInner[2]+(zOffset[2]*0.5f));
			tessellator.addVertex(lastPositionInner[0]+(zOffset[0]*0.5f),lastPositionInner[1]-heightOffset,lastPositionInner[2]+(zOffset[2]*0.5f));
			//top inner
			tessellator.addVertex(positionInner[0]+(zOffset[0]*0.5f),positionInner[1], positionInner[2]+(zOffset[2]*0.5f));
			tessellator.addVertex(lastPositionInner[0]+(zOffset[0]*0.5f),lastPositionInner[1],lastPositionInner[2]+(zOffset[2]*0.5f));
			tessellator.arrayEnabledDraw();
			GL11.glPopMatrix();
			//GL11.glScaled(1.325,1,1);

			GL11.glColor3f(0.7f,0.7f,0.7f);
			GL11.glTranslated(0,0.05,0);
			//front and back only if its at the end of the list.
			if (isFront){
				//top
				tessellator.startDrawing(GL11.GL_QUADS);
				tessellator.addVertex(positionInner[0],positionInner[1], positionInner[2]);
				tessellator.addVertex(positionOuter[0],positionOuter[1], positionOuter[2]);
				tessellator.addVertex(positionOuter[0],positionOuter[1]-heightOffset, positionOuter[2]);
				tessellator.addVertex(positionInner[0],positionInner[1]-heightOffset, positionInner[2]);
				tessellator.arrayEnabledDraw();

				//bottom
				tessellator.startDrawing(GL11.GL_QUADS);
				GL11.glTranslated(0,-0.1,0);
				GL11.glColor3f(0.65f,0.65f,0.65f);
				tessellator.addVertex(positionInner[0],positionInner[1], positionInner[2]);
				tessellator.addVertex(positionOuter[0],positionOuter[1], positionOuter[2]);
				tessellator.addVertex(positionOuter[0],positionOuter[1]-heightOffset, positionOuter[2]);
				tessellator.addVertex(positionInner[0],positionInner[1]-heightOffset, positionInner[2]);
				tessellator.arrayEnabledDraw();

				//middle
				tessellator.startDrawing(GL11.GL_QUADS);
				GL11.glTranslated(0,0.05,0);
				GL11.glColor3f(0.6f,0.6f,0.6f);
				tessellator.addVertex(positionInner[0]+(zOffset[0]*0.5f),positionInner[1], positionInner[2]+(zOffset[2]*0.5f));
				tessellator.addVertex(positionOuter[0]-(zOffset[0]*0.5f),positionOuter[1], positionOuter[2]-(zOffset[2]*0.5f));
				tessellator.addVertex(positionOuter[0]-(zOffset[0]*0.5f),positionOuter[1]-heightOffset, positionOuter[2]-(zOffset[2]*0.5f));
				tessellator.addVertex(positionInner[0]+(zOffset[0]*0.5f),positionInner[1]-heightOffset, positionInner[2]+(zOffset[2]*0.5f));
				tessellator.arrayEnabledDraw();
			} else if(isEnd){
				//top
				tessellator.startDrawing(GL11.GL_QUADS);
				tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1], lastPositionOuter[2]);
				tessellator.addVertex(lastPositionInner[0],lastPositionInner[1], lastPositionInner[2]);
				tessellator.addVertex(lastPositionInner[0],lastPositionInner[1]-heightOffset, lastPositionInner[2]);
				tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1]-heightOffset, lastPositionOuter[2]);
				tessellator.arrayEnabledDraw();

				//bottom
				tessellator.startDrawing(GL11.GL_QUADS);
				GL11.glTranslated(0,-0.1,0);
				GL11.glColor3f(0.65f,0.65f,0.65f);
				tessellator.addVertex(lastPositionInner[0],lastPositionInner[1], lastPositionInner[2]);
				tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1], lastPositionOuter[2]);
				tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1]-heightOffset, lastPositionOuter[2]);
				tessellator.addVertex(lastPositionInner[0],lastPositionInner[1]-heightOffset, lastPositionInner[2]);
				tessellator.arrayEnabledDraw();

				//middle
				tessellator.startDrawing(GL11.GL_QUADS);
				GL11.glTranslated(0,0.05,0);
				GL11.glColor3f(0.6f,0.6f,0.6f);
				tessellator.addVertex(lastPositionInner[0]+(zOffset[0]*0.5f),lastPositionInner[1], lastPositionInner[2]+(zOffset[2]*0.5f));
				tessellator.addVertex(lastPositionOuter[0]-(zOffset[0]*0.5f),lastPositionOuter[1], lastPositionOuter[2]-(zOffset[2]*0.5f));
				tessellator.addVertex(lastPositionOuter[0]-(zOffset[0]*0.5f),lastPositionOuter[1]-heightOffset, lastPositionOuter[2]-(zOffset[2]*0.5f));
				tessellator.addVertex(lastPositionInner[0]+(zOffset[0]*0.5f),lastPositionInner[1]-heightOffset, lastPositionInner[2]+(zOffset[2]*0.5f));
				tessellator.arrayEnabledDraw();
			}
		}



		public void modelTC(Tessellator tessellator, boolean isFront, boolean isEnd){

		}

	}
}