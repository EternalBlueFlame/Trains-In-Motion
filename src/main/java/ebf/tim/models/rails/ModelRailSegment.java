//This File was created with the Minecraft-SMP Modelling Toolbox 2.3.0.0
// Copyright (C) 2017 Minecraft-SMP.de
// This file is for Flan's Flying Mod Version 4.0.x+

// Model: 
// Model Creator:
// Created on:-
// Last changed on: -

package ebf.tim.models.rails;

import ebf.tim.utility.ClientProxy;
import org.lwjgl.opengl.GL11;
import tmt.Tessellator;

import java.util.ArrayList;
import java.util.List;

public class ModelRailSegment
{

	private static float heightOffset=0.05f;
	public float[] position = new float[]{0,0,0};
	public float[] zOffset = null;
	public List<subModel> models = new ArrayList<>();


	public subModel genNewSubModel(float[] inner, float[] outer){
		return new subModel(inner, outer);
	}

	public class subModel{

		public subModel(float[] inner, float[]outer){
			positionInner = inner;
			positionOuter = outer;

		}
		public float[] positionInner = null;
		public float[] positionOuter = null;
		public float[] lastPositionInner = null;
		public float[] lastPositionOuter = null;

		public void render(Tessellator tessellator, int materialColor, boolean isFront, Boolean isEnd) {
			if (positionOuter != null && positionInner != null && lastPositionOuter!=null && lastPositionInner != null) {

				GL11.glPushMatrix();

				//set the color with the tint.   * 0.00392156863 is the same as /255, but multiplication is more efficient than division and doesn't fry on 0.
				//set the position
				GL11.glDisable(GL11.GL_LIGHTING);
				switch (ClientProxy.railSkin){
					case 0:{
						modelPotato(tessellator, materialColor);
						break;
					}
					case 1:{
						model3DMC(tessellator, materialColor, isFront, isEnd);
						break;
					}
					case 2:{
						modelTiM(tessellator, materialColor, isFront, isEnd);
						break;
					}
					case 3:{
						modelTC(tessellator, materialColor, isFront, isEnd);
						break;
					}
				}
				GL11.glDisable(GL11.GL_LIGHTING);

				GL11.glPopMatrix();
			}
		}



		public void modelPotato(Tessellator tessellator, int materialColor){

			tessellator.startDrawing(GL11.GL_QUAD_STRIP);
			GL11.glTranslated(0,0.1,0);

			//top inner
			tessellator.addVertex(positionInner[0],positionInner[1], positionInner[2]);
			tessellator.addVertex(lastPositionInner[0],lastPositionInner[1],lastPositionInner[2]);
			//top outer
			tessellator.addVertex(positionOuter[0],positionOuter[1], positionOuter[2]);
			tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1],lastPositionOuter[2]);

			tessellator.draw();
		}


		public void model3DMC(Tessellator tessellator, int materialColor, boolean isFront, boolean isEnd){

			tessellator.startDrawing(GL11.GL_QUAD_STRIP);
			GL11.glTranslated(0,0.1,0);

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

			tessellator.draw();


			//front and back only if its at the end of the list.
			if (isFront){
				tessellator.startDrawing(GL11.GL_QUADS);
				tessellator.addVertex(positionInner[0],positionInner[1], positionInner[2]);
				tessellator.addVertex(positionOuter[0],positionOuter[1], positionOuter[2]);
				tessellator.addVertex(positionOuter[0],positionOuter[1]-heightOffset, positionOuter[2]);
				tessellator.addVertex(positionInner[0],positionInner[1]-heightOffset, positionInner[2]);
				tessellator.draw();
			} else if(isEnd){
				tessellator.startDrawing(GL11.GL_QUADS);
				tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1], lastPositionOuter[2]);
				tessellator.addVertex(lastPositionInner[0],lastPositionInner[1], lastPositionInner[2]);
				tessellator.addVertex(lastPositionInner[0],lastPositionInner[1]-heightOffset, lastPositionInner[2]);
				tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1]-heightOffset, lastPositionOuter[2]);
				tessellator.draw();
			}
		}



		public void modelTiM(Tessellator tessellator, int materialColor, boolean isFront, boolean isEnd){

			GL11.glColor3f(0.65f,0.65f,0.65f);

			GL11.glTranslated(0,0.1,0);
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

			tessellator.draw();
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

			tessellator.draw();


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
			//todo this works but the z offset variable should probably be scaled to match the model so we dont have to re-calculate it every time. either that or cache the post-calculation vertex points.
			tessellator.addVertex(positionOuter[0]-(zOffset[0]*0.5f),positionOuter[1], positionOuter[2]-(zOffset[2]*0.5f));//todo i dont think this side is needed for center...?
			tessellator.addVertex(lastPositionOuter[0]-(zOffset[0]*0.5f),lastPositionOuter[1],lastPositionOuter[2]-(zOffset[2]*0.5f));
			//bottom outer
			tessellator.addVertex(positionOuter[0]-(zOffset[0]*0.5f),positionOuter[1]-heightOffset, positionOuter[2]-(zOffset[2]*0.5f));
			tessellator.addVertex(lastPositionOuter[0]-(zOffset[0]*0.5f),lastPositionOuter[1]-heightOffset,lastPositionOuter[2]-(zOffset[2]*0.5f));
			//bottom inner
			tessellator.addVertex(positionInner[0]-(zOffset[0]*0.5f),positionInner[1]-heightOffset, positionInner[2]-(zOffset[2]*0.5f));
			tessellator.addVertex(lastPositionInner[0]-(zOffset[0]*0.5f),lastPositionInner[1]-heightOffset,lastPositionInner[2]-(zOffset[2]*0.5f));
			//top inner
			tessellator.addVertex(positionInner[0]-(zOffset[0]*0.5f),positionInner[1], positionInner[2]-(zOffset[2]*0.5f));
			tessellator.addVertex(lastPositionInner[0]-(zOffset[0]*0.5f),lastPositionInner[1],lastPositionInner[2]-(zOffset[2]*0.5f));
			tessellator.draw();
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
				tessellator.draw();

				//bottom
				tessellator.startDrawing(GL11.GL_QUADS);
				GL11.glTranslated(0,-0.1,0);
				GL11.glColor3f(0.65f,0.65f,0.65f);
				tessellator.addVertex(positionInner[0],positionInner[1], positionInner[2]);
				tessellator.addVertex(positionOuter[0],positionOuter[1], positionOuter[2]);
				tessellator.addVertex(positionOuter[0],positionOuter[1]-heightOffset, positionOuter[2]);
				tessellator.addVertex(positionInner[0],positionInner[1]-heightOffset, positionInner[2]);
				tessellator.draw();

				//middle
				tessellator.startDrawing(GL11.GL_QUADS);
				GL11.glTranslated(0,0.05,0);
				GL11.glColor3f(0.6f,0.6f,0.6f);
				tessellator.addVertex(positionInner[0]-(zOffset[0]*0.5f),positionInner[1], positionInner[2]-(zOffset[2]*0.5f));
				tessellator.addVertex(positionOuter[0]-(zOffset[0]*0.5f),positionOuter[1], positionOuter[2]-(zOffset[2]*0.5f));
				tessellator.addVertex(positionOuter[0]-(zOffset[0]*0.5f),positionOuter[1]-heightOffset, positionOuter[2]-(zOffset[2]*0.5f));
				tessellator.addVertex(positionInner[0]-(zOffset[0]*0.5f),positionInner[1]-heightOffset, positionInner[2]-(zOffset[2]*0.5f));
				tessellator.draw();
			} else if(isEnd){
				//top
				tessellator.startDrawing(GL11.GL_QUADS);
				tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1], lastPositionOuter[2]);
				tessellator.addVertex(lastPositionInner[0],lastPositionInner[1], lastPositionInner[2]);
				tessellator.addVertex(lastPositionInner[0],lastPositionInner[1]-heightOffset, lastPositionInner[2]);
				tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1]-heightOffset, lastPositionOuter[2]);
				tessellator.draw();

				//bottom
				tessellator.startDrawing(GL11.GL_QUADS);
				GL11.glTranslated(0,-0.1,0);
				GL11.glColor3f(0.65f,0.65f,0.65f);
				tessellator.addVertex(lastPositionInner[0],lastPositionInner[1], lastPositionInner[2]);
				tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1], lastPositionOuter[2]);
				tessellator.addVertex(lastPositionOuter[0],lastPositionOuter[1]-heightOffset, lastPositionOuter[2]);
				tessellator.addVertex(lastPositionInner[0],lastPositionInner[1]-heightOffset, lastPositionInner[2]);
				tessellator.draw();

				//middle
				tessellator.startDrawing(GL11.GL_QUADS);
				GL11.glTranslated(0,0.05,0);
				GL11.glColor3f(0.6f,0.6f,0.6f);
				tessellator.addVertex(lastPositionInner[0]-(zOffset[0]*0.5f),lastPositionInner[1], lastPositionInner[2]-(zOffset[2]*0.5f));
				tessellator.addVertex(lastPositionOuter[0]-(zOffset[0]*0.5f),lastPositionOuter[1], lastPositionOuter[2]-(zOffset[2]*0.5f));
				tessellator.addVertex(lastPositionOuter[0]-(zOffset[0]*0.5f),lastPositionOuter[1]-heightOffset, lastPositionOuter[2]-(zOffset[2]*0.5f));
				tessellator.addVertex(lastPositionInner[0]-(zOffset[0]*0.5f),lastPositionInner[1]-heightOffset, lastPositionInner[2]-(zOffset[2]*0.5f));
				tessellator.draw();
			}
		}



		public void modelTC(Tessellator tessellator, int materialColor, boolean isFront, boolean isEnd){

		}

	}
}