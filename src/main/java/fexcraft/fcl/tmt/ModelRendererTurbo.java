package fexcraft.fcl.tmt;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fexcraft.fcl.common.Static;
import fexcraft.fcl.common.math.RGB;
import fexcraft.fcl.common.math.TexturedPolygon;
import fexcraft.fcl.common.math.TexturedVertex;
import fexcraft.fcl.common.math.Vec3f;
import org.lwjgl.opengl.GL11;

/**
 * <span style='text-decoration:line-through;'>An extension to the ModelRenderer class.</span> It basically is a copy to ModelRenderer,
 * however, it contains various new methods to make your models.
 * <hr>
 * Since the ModelRendererTurbo class gets loaded during startup, the models made
 * can be very complex. This is why I can afford to add, for example, Wavefont OBJ
 * support or have the addSprite method, methods that add a lot of vertices and
 * polygons.
 * <hr>
 * This is a (by now) heavily modified version of TMT, it was and is being updated/maintained/improved by Ferdinand (FEX___96).
 * Due to various circumstances, starting from version "XII.44" of FCL (October 2018) the license changes to the Fexcraft "Common" License for "Modifications".
 * <hr>
 * If you find any code here that shouldn't be, inform me and it will be removed <b>asap</b>.
 *
 * @originalAuthor GaryCXJk
 * @author Ferdinand (FEX___96)
 * @license http://fexcraft.net/license?id=mods
 *
 */
public class ModelRendererTurbo {
	
    public float textureWidth = 0, textureHeight = 0;
    /** Changed from radians to degrees, please re-check your model rotations!**/
    public float rotationAngleX = 0, rotationAngleY = 0, rotationAngleZ = 0;
    public float rotationPointX = 0, rotationPointY = 0, rotationPointZ = 0;
	//
    public static final int MR_FRONT = 0, MR_BACK = 1, MR_LEFT = 2, MR_RIGHT = 3, MR_TOP = 4, MR_BOTTOM = 5;
    public boolean showModel, forcedRecompile, mirror, flip;
    public boolean isShape3D, textured = true;
    public RGB linesColor, polygonColor;
    private TexturedVertex vertices[];
    private TexturedPolygon faces[];
    private Integer displayList;
    public int texoffx, texoffy;
    public List<?> childModels;
    public String boxName;
	
	public ModelRendererTurbo(Object modelbase, String s){
		//super(modelbase, s);
    	flip = false; mirror = false; showModel = true;
        vertices = new TexturedVertex[0];
        faces = new TexturedPolygon[0];
        forcedRecompile = false; boxName = s;
	}
	
	public ModelRendererTurbo(Object modelbase){
		this(modelbase, null);
	}
	
	/**
	 * Creates a new ModelRenderTurbo object. It requires the coordinates of the
	 * position of the texture.
	 * @param modelbase
	 * @param textureX the x-coordinate on the texture
	 * @param textureY the y-coordinate on the texture
	 */
    public ModelRendererTurbo(Object modelbase, int textureX, int textureY){
    	this(modelbase, textureX, textureY, 64, 32);
    }

    /**
     * Creates a new ModelRenderTurbo object. It requires the coordinates of the
     * position of the texture, but also allows you to specify the width and height
     * of the texture, allowing you to use bigger textures instead.
     * @param modelbase
     * @param textureX
     * @param textureY
     * @param textureU
     * @param textureV
     */
    public ModelRendererTurbo(Object modelbase, int textureX, int textureY, int textureU, int textureV){
    	this(modelbase); texoffx = textureX; texoffy = textureY; textureWidth = textureU; textureHeight = textureV;
    }
    
    /**
     * Sets the rotation point of the shape, relative to the model's origins. Note that changing
     * the offsets will not change the pivot of the model.
	 * @param x the x-rotation point of the shape
	 * @param y the y-rotation point of the shape
	 * @param z the z-rotation point of the shape
	 */
	public ModelRendererTurbo setRotationPoint(float x, float y, float z){
		rotationPointX = x; rotationPointY = y; rotationPointZ = z; return this;
	}
    
    /**
     * Sets the rotation angles of the shape.
	 * @param x the x-angle point of the shape
	 * @param y the y-angle point of the shape
	 * @param z the z-angle point of the shape
	 */
	public ModelRendererTurbo setRotationAngle(float x, float y, float z){
		this.rotationAngleX = x; this.rotationAngleY = y; this.rotationAngleZ = z; return this;
	}
	
	public ModelRendererTurbo setTextured(boolean bool){
		this.textured = bool; return this;
	}
	
	public ModelRendererTurbo setLines(boolean bool){
		this.linesColor = bool ? RGB.BLACK : null; return this;
	}
	
	public ModelRendererTurbo setLines(RGB color){
		this.linesColor = color; return this;
	}
	
	public ModelRendererTurbo setColor(RGB color){
		this.polygonColor = color; return this;
	}
	
	public ModelRendererTurbo setVisible(boolean bool){
		this.showModel = bool; return this;
	}
	
	public ModelRendererTurbo setInvisible(boolean bool){
		this.showModel = !bool; return this;
	}
	
	public ModelRendererTurbo setName(String name){
		this.boxName = name; return this;
	}
    
    /**
     * Creates a new polygon.
     * @param verts an array of vertices
     */
    public void addPolygon(TexturedVertex[] verts){
    	copyTo(verts, new TexturedPolygon[] {new TexturedPolygon(verts)});
    }
    
    /**
     * Creates a new polygon, and adds UV mapping to it.
     * @param verts an array of vertices
     * @param uv an array of UV coordinates
     */
    public void addPolygon(TexturedVertex[] verts, int[][] uv){
    	try{
    		for(int i = 0; i < verts.length; i++){
    			verts[i] = verts[i].setTexturePosition(uv[i][0] / textureWidth, uv[i][1] / textureHeight);
    		}
    	}
    	finally{
    		addPolygon(verts);
    	}
    }
    
    /**
     * Creates a new polygon with a given UV.
     * @param verts an array of vertices
     * @param u1
     * @param v1
     * @param u2
     * @param v2
     */
    public void addPolygon(TexturedVertex[] verts, int u1, int v1, int u2, int v2){
    	copyTo(verts, new TexturedPolygon[] {addPolygonReturn(verts, u1, v1, u2, v2)});
    }
    
    private TexturedPolygon addPolygonReturn(TexturedVertex[] verts, float f, float g, float h, float j){
    	if(verts.length < 3){ return null; }
    	float uOffs = 1.0F / (textureWidth * 10.0F);
    	float vOffs = 1.0F / (textureHeight * 10.0F);
    	if(verts.length < 4){
    		float xMin = -1;
    		float yMin = -1;
    		float xMax = 0;
    		float yMax = 0;
    		for(int i = 0; i < verts.length; i++){
    			float xPos = verts[i].textureX;
    			float yPos = verts[i].textureY;
    			xMax = Math.max(xMax, xPos);
    			xMin = (xMin < -1 ? xPos : Math.min(xMin, xPos));
    			yMax = Math.max(yMax, yPos);
    			yMin = (yMin < -1 ? yPos : Math.min(yMin, yPos));
    		}
    		float uMin = f / textureWidth + uOffs;
    		float vMin = g / textureHeight + vOffs;
    		float uSize = (h - f) / textureWidth - uOffs * 2;
    		float vSize = (j - g) / textureHeight - vOffs * 2;
    		float xSize = xMax - xMin;
    		float ySize = yMax - yMin;
    		for(int i = 0; i < verts.length; i++){
    			float xPos = verts[i].textureX;
    			float yPos = verts[i].textureY;
    			xPos = (xPos - xMin) / xSize;
    			yPos = (yPos - yMin) / ySize;
    			verts[i] = verts[i].setTexturePosition(uMin + (xPos * uSize), vMin + (yPos * vSize));
    		}
    	}
    	else{
	    	verts[0] = verts[0].setTexturePosition(h / textureWidth - uOffs, g / textureHeight + vOffs);
	    	verts[1] = verts[1].setTexturePosition(f / textureWidth + uOffs, g / textureHeight + vOffs);
	    	verts[2] = verts[2].setTexturePosition(f / textureWidth + uOffs, j / textureHeight - vOffs);
	    	verts[3] = verts[3].setTexturePosition(h / textureWidth - uOffs, j / textureHeight - vOffs);
    	}
    	return new TexturedPolygon(verts);
    }
    
    /**
     * Adds a rectangular shape. Basically, you can make any eight-pointed shape you want,
     * as the method requires eight vector coordinates.
     * @param v0 a float array with three values, the x, y and z coordinates of the vertex
     * @param v1 a float array with three values, the x, y and z coordinates of the vertex
     * @param v2 a float array with three values, the x, y and z coordinates of the vertex
     * @param v3 a float array with three values, the x, y and z coordinates of the vertex
     * @param v4 a float array with three values, the x, y and z coordinates of the vertex
     * @param v5 a float array with three values, the x, y and z coordinates of the vertex
     * @param v6 a float array with three values, the x, y and z coordinates of the vertex
     * @param v7 a float array with three values, the x, y and z coordinates of the vertex
     * @param w the width of the shape, used in determining the texture
     * @param h the height of the shape, used in determining the texture
     * @param d the depth of the shape, used in determining the texture
     */
    public ModelRendererTurbo addRectShape(float[] v0, float[] v1, float[] v2, float[] v3, float[] v4, float[] v5, float[] v6, float[] v7, float w, float h, float d){
    	TexturedVertex[] verts = new TexturedVertex[8];
        TexturedPolygon[] poly = new TexturedPolygon[6];
        TexturedVertex tv0 = new TexturedVertex(v0[0], v0[1], v0[2], 0.0F, 0.0F);
        TexturedVertex tv1 = new TexturedVertex(v1[0], v1[1], v1[2], 0.0F, 8.0F);
        TexturedVertex tv2 = new TexturedVertex(v2[0], v2[1], v2[2], 8.0F, 8.0F);
        TexturedVertex tv3 = new TexturedVertex(v3[0], v3[1], v3[2], 8.0F, 0.0F);
        TexturedVertex tv4 = new TexturedVertex(v4[0], v4[1], v4[2], 0.0F, 0.0F);
        TexturedVertex tv5 = new TexturedVertex(v5[0], v5[1], v5[2], 0.0F, 8.0F);
        TexturedVertex tv6 = new TexturedVertex(v6[0], v6[1], v6[2], 8.0F, 8.0F);
        TexturedVertex tv7 = new TexturedVertex(v7[0], v7[1], v7[2], 8.0F, 0.0F);
        verts[0] = tv0; verts[1] = tv1; verts[2] = tv2; verts[3] = tv3;
        verts[4] = tv4; verts[5] = tv5; verts[6] = tv6; verts[7] = tv7;
        poly[0] = addPolygonReturn(new TexturedVertex[] { tv5, tv1, tv2, tv6 }, texoffx + d + w, texoffy + d, texoffx + d + w + d, texoffy + d + h);
        poly[1] = addPolygonReturn(new TexturedVertex[] { tv0, tv4, tv7, tv3 }, texoffx + 0, texoffy + d, texoffx + d, texoffy + d + h);
        poly[2] = addPolygonReturn(new TexturedVertex[] { tv5, tv4, tv0, tv1 }, texoffx + d, texoffy + 0, texoffx + d + w, texoffy + d);
        poly[3] = addPolygonReturn(new TexturedVertex[] { tv2, tv3, tv7, tv6 }, texoffx + d + w, texoffy + 0, texoffx + d + w + w, texoffy + d);
        poly[4] = addPolygonReturn(new TexturedVertex[] { tv1, tv0, tv3, tv2 }, texoffx + d, texoffy + d, texoffx + d + w, texoffy + d + h);
        poly[5] = addPolygonReturn(new TexturedVertex[] { tv4, tv5, tv6, tv7 }, texoffx + d + w + d, texoffy + d, texoffx + d + w + d + w, texoffy + d + h);
        if(mirror ^ flip){
            for(int l = 0; l < poly.length; l++){
            	poly[l].flipFace();
            }
        }
        return copyTo(verts, poly);
    }

    /**
     * Adds a new box to the model.
     * @param x the starting x-position
     * @param y the starting y-position
     * @param z the starting z-position
     * @param w the width (over the x-direction)
     * @param h the height (over the y-direction)
     * @param d the depth (over the z-direction)
     */
    public ModelRendererTurbo addBox(float x, float y, float z, float w, float h, float d){
        return addBox(x, y, z, w, h, d, 0.0F);
    }
    
    /**
     * Adds a new box to the model.
     * @param x the starting x-position
     * @param y the starting y-position
     * @param z the starting z-position
     * @param w the width (over the x-direction)
     * @param h the height (over the y-direction)
     * @param d the depth (over the z-direction)
     * @param expansion the expansion of the box. It increases the size in each direction by that many.
     */
    public ModelRendererTurbo addBox(float x, float y, float z, float w, float h, float d, float expansion){
    	return addBox(x, y, z, w, h, d, expansion, 1F);
    }
    
    /**
     * Adds a new box to the model.
     * @param x the starting x-position
     * @param y the starting y-position
     * @param z the starting z-position
     * @param w the width (over the x-direction)
     * @param h the height (over the y-direction)
     * @param d the depth (over the z-direction)
     * @param expansion the expansion of the box. It increases the size in each direction by that many. It's independent from the scale.
     * @param scale
     */
    public ModelRendererTurbo addBox(float x, float y, float z, float w, float h, float d, float expansion, float scale){
    	if(w == 0){ w = 0.01F; } if(h == 0){ h = 0.01F; } if(d == 0){ d = 0.01F; }
        float scaleX = w * scale, scaleY = h * scale, scaleZ = d * scale;
        float x1 = x + scaleX, y1 = y + scaleY, z1 = z + scaleZ;
        float expX = expansion + scaleX - w;
        float expY = expansion + scaleY - h;
        float expZ = expansion + scaleZ - d;
        //
        x -= expX; y -= expY; z -= expZ;
        x1 += expansion;  y1 += expansion; z1 += expansion;
        if(mirror){ float xTemp = x1; x1 = x; x = xTemp; }
        //
        float[] v0 = {x, y, z}, v1 = {x1, y, z}, v2 = {x1, y1, z}, v3 = {x, y1, z};
        float[] v4 = {x, y, z1}, v5 = {x1, y, z1}, v6 = {x1, y1, z1}, v7 = {x, y1, z1};
        return addRectShape(v0, v1, v2, v3, v4, v5, v6, v7, w, h, d);
    }
    
    /**
     * Adds a trapezoid-like shape. It's achieved by expanding the shape on one side.
     * You can use the static variables <code>MR_RIGHT</code>, <code>MR_LEFT</code>,
     * <code>MR_FRONT</code>, <code>MR_BACK</code>, <code>MR_TOP</code> and
     * <code>MR_BOTTOM</code>.
     * @param x the starting x-position
     * @param y the starting y-position
     * @param z the starting z-position
     * @param w the width (over the x-direction)
     * @param h the height (over the y-direction)
     * @param d the depth (over the z-direction)
     * @param scale the "scale" of the box. It only increases the size in each direction by that many.
     * @param bottomScale the "scale" of the bottom
     * @param dir the side the scaling is applied to
     */
    public ModelRendererTurbo addTrapezoid(float x, float y, float z, int w, int h, int d, float scale, float bottomScale, int dir){
        float f4 = x + w, f5 = y + h, f6 = z + d; x -= scale; y -= scale; z -= scale; f4 += scale; f5 += scale; f6 += scale;
        int m = (mirror ? -1 : 1); if(mirror){ float f7 = f4; f4 = x; x = f7; }
        float[] v0 = {x, y, z}, v1 = {f4, y, z}, v2 = {f4, f5, z}, v3 = {x, f5, z};
        float[] v4 = {x, y, f6}, v5 = {f4, y, f6}, v6 = {f4, f5, f6}, v7 = {x, f5, f6};
        //
        switch(dir){
	        case MR_RIGHT:
	        	v0[1] -= bottomScale; v0[2] -= bottomScale;
	        	v3[1] += bottomScale; v3[2] -= bottomScale;
	        	v4[1] -= bottomScale; v4[2] += bottomScale;
	        	v7[1] += bottomScale; v7[2] += bottomScale;
	        	break;
	        case MR_LEFT:
	        	v1[1] -= bottomScale; v1[2] -= bottomScale;
	        	v2[1] += bottomScale; v2[2] -= bottomScale;
	        	v5[1] -= bottomScale; v5[2] += bottomScale;
	        	v6[1] += bottomScale; v6[2] += bottomScale;
	        	break;
	        case MR_FRONT:
	        	v0[0] -= m * bottomScale; v0[1] -= bottomScale;
	        	v1[0] += m * bottomScale; v1[1] -= bottomScale;
	        	v2[0] += m * bottomScale; v2[1] += bottomScale;
	        	v3[0] -= m * bottomScale; v3[1] += bottomScale;
	        	break;
	        case MR_BACK:
	        	v4[0] -= m * bottomScale; v4[1] -= bottomScale;
	        	v5[0] += m * bottomScale; v5[1] -= bottomScale;
	        	v6[0] += m * bottomScale; v6[1] += bottomScale;
	        	v7[0] -= m * bottomScale; v7[1] += bottomScale;
	        	break;
	        case MR_TOP:
	        	v0[0] -= m * bottomScale; v0[2] -= bottomScale;
	        	v1[0] += m * bottomScale; v1[2] -= bottomScale;
	        	v4[0] -= m * bottomScale; v4[2] += bottomScale;
	        	v5[0] += m * bottomScale; v5[2] += bottomScale;
	        	break;
	        case MR_BOTTOM:
	        	v2[0] += m * bottomScale; v2[2] -= bottomScale;
	        	v3[0] -= m * bottomScale; v3[2] -= bottomScale;
	        	v6[0] += m * bottomScale; v6[2] += bottomScale;
	        	v7[0] -= m * bottomScale; v7[2] += bottomScale;
	        	break;
        }
        return addRectShape(v0, v1, v2, v3, v4, v5, v6, v7, w, h, d);
    }
    
    /**
     * Creates a shape from a 2D vector shape.
     * @param x the starting x position
     * @param y the starting y position
     * @param z the starting z position
     * @param coordinates an array of coordinates that form the shape
     * @param depth the depth of the shape
     * @param shapeTextureWidth the width of the texture of one side of the shape
     * @param shapeTextureHeight the height of the texture the shape
     * @param sideTextureWidth the width of the texture of the side of the shape
     * @param sideTextureHeight the height of the texture of the side of the shape
     * @param direction the direction the starting point of the shape is facing
     */
    public ModelRendererTurbo addShape3D(float x, float y, float z, Coord2D[] coordinates, float depth, int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, int direction){
    	return addShape3D(x, y, z, coordinates, depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth, sideTextureHeight, direction, null);
    }
   
    /**
     * Creates a shape from a 2D vector shape.
     * @param x the starting x position
     * @param y the starting y position
     * @param z the starting z position
     * @param coordinates an array of coordinates that form the shape
     * @param depth the depth of the shape
     * @param shapeTextureWidth the width of the texture of one side of the shape
     * @param shapeTextureHeight the height of the texture the shape
     * @param sideTextureWidth the width of the texture of the side of the shape
     * @param sideTextureHeight the height of the texture of the side of the shape
     * @param direction the direction the starting point of the shape is facing
     * @param faceLengths An array with the length of each face. Used to set
     * the texture width of each face on the side manually.
     */
    public ModelRendererTurbo addShape3D(float x, float y, float z, Coord2D[] coordinates, float depth, int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, int direction, float[] faceLengths){
    	return addShape3D(x, y, z, new Shape2D(coordinates), depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth, sideTextureHeight, direction, faceLengths);
    }
    
    /**
     * Creates a shape from a 2D vector shape.
     * @param x the starting x position
     * @param y the starting y position
     * @param z the starting z position
     * @param coordinates an ArrayList of coordinates that form the shape
     * @param depth the depth of the shape
     * @param shapeTextureWidth the width of the texture of one side of the shape
     * @param shapeTextureHeight the height of the texture the shape
     * @param sideTextureWidth the width of the texture of the side of the shape
     * @param sideTextureHeight the height of the texture of the side of the shape
     * @param direction the direction the starting point of the shape is facing
     */
    public ModelRendererTurbo addShape3D(float x, float y, float z, ArrayList<Coord2D> coordinates, float depth, int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, int direction){
    	return addShape3D(x, y, z, coordinates, depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth, sideTextureHeight, direction, null);
    }
    
    /**
     * Creates a shape from a 2D vector shape.
     * @param x the starting x position
     * @param y the starting y position
     * @param z the starting z position
     * @param coordinates an ArrayList of coordinates that form the shape
     * @param depth the depth of the shape
     * @param shapeTextureWidth the width of the texture of one side of the shape
     * @param shapeTextureHeight the height of the texture the shape
     * @param sideTextureWidth the width of the texture of the side of the shape
     * @param sideTextureHeight the height of the texture of the side of the shape
     * @param direction the direction the starting point of the shape is facing
     * @param faceLengths An array with the length of each face. Used to set
     * the texture width of each face on the side manually.
     */
    public ModelRendererTurbo addShape3D(float x, float y, float z, ArrayList<Coord2D> coordinates, float depth, int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, int direction, float[] faceLengths){
    	return addShape3D(x, y, z, new Shape2D(coordinates), depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth, sideTextureHeight, direction, faceLengths);
    }

    /**
     * Creates a shape from a 2D vector shape.
     * @param x the starting x position
     * @param y the starting y position
     * @param z the starting z position
     * @param shape a Shape2D which contains the coordinates of the shape points
     * @param depth the depth of the shape
     * @param shapeTextureWidth the width of the texture of one side of the shape
     * @param shapeTextureHeight the height of the texture the shape
     * @param sideTextureWidth the width of the texture of the side of the shape
     * @param sideTextureHeight the height of the texture of the side of the shape
     * @param direction the direction the starting point of the shape is facing
     */
    public ModelRendererTurbo addShape3D(float x, float y, float z, Shape2D shape, float depth, int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, int direction){
    	return addShape3D(x, y, z, shape, depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth, sideTextureHeight, direction, null);
    }
    
    /**
     * Creates a shape from a 2D vector shape.
     * @param x the starting x position
     * @param y the starting y position
     * @param z the starting z position
     * @param shape a Shape2D which contains the coordinates of the shape points
     * @param depth the depth of the shape
     * @param shapeTextureWidth the width of the texture of one side of the shape
     * @param shapeTextureHeight the height of the texture the shape
     * @param sideTextureWidth the width of the texture of the side of the shape
     * @param sideTextureHeight the height of the texture of the side of the shape
     * @param direction the direction the starting point of the shape is facing
     * @param faceLengths An array with the length of each face. Used to set
     * the texture width of each face on the side manually.
     */
    public ModelRendererTurbo addShape3D(float x, float y, float z, Shape2D shape, float depth, int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, int direction, float[] faceLengths){
    	float rotX = 0, rotY = 0, rotZ = 0;
    	switch(direction){
	    	case MR_LEFT: rotY = Static.PI / 2; break;
	    	case MR_RIGHT: rotY = -Static.PI / 2; break;
	    	case MR_TOP: rotX = Static.PI / 2; break;
	    	case MR_BOTTOM: rotX = -Static.PI / 2; break;
	    	case MR_FRONT: rotY = Static.PI; break;
	    	case MR_BACK: break;
    	}
    	return addShape3D(x, y, z, shape, depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth, sideTextureHeight, rotX, rotY, rotZ, faceLengths);
    }
    
    /**
     * Creates a shape from a 2D vector shape.
     * @param x the starting x position
     * @param y the starting y position
     * @param z the starting z position
     * @param shape a Shape2D which contains the coordinates of the shape points
     * @param depth the depth of the shape
     * @param shapeTextureWidth the width of the texture of one side of the shape
     * @param shapeTextureHeight the height of the texture the shape
     * @param sideTextureWidth the width of the texture of the side of the shape
     * @param sideTextureHeight the height of the texture of the side of the shape
     * @param rotX the rotation around the x-axis
     * @param rotY the rotation around the y-axis
     * @param rotZ the rotation around the z-axis
     */
    public ModelRendererTurbo addShape3D(float x, float y, float z, Shape2D shape, float depth, int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, float rotX, float rotY, float rotZ){
    	return addShape3D(x, y, z, shape, depth, shapeTextureWidth, shapeTextureHeight, sideTextureWidth, sideTextureHeight, rotX, rotY, rotZ, null);
    }
    
    /**
     * NOTE: `x` and `z` are inverted to prevent "Toolbox" or "Flansmod"-type models from being rendered wrong.
     * There is currently no other commonly used editor for such models, so let's leave it that way for now.<br>
     * <s>NOTE2: Also let's rotate by 180 degrees for whatever reason.</s><br>
     * NOTE2: Let's mark as Shape3D for further processing.
     * @Ferdinand
     */
    public ModelRendererTurbo addShape3D(float x, float y, float z, Shape2D shape, float depth, int shapeTextureWidth, int shapeTextureHeight, int sideTextureWidth, int sideTextureHeight, float rotX, float rotY, float rotZ, float[] faceLengths){
    	Shape3D shape3D = shape.extrude(-x, y, -z, rotX, rotY, rotZ, depth, texoffx, texoffy, textureWidth, textureHeight, shapeTextureWidth, shapeTextureHeight, sideTextureWidth, sideTextureHeight, faceLengths);
    	if(flip){ for(int idx = 0; idx < shape3D.faces.length; idx++){ shape3D.faces[idx].flipFace(); } }
    	isShape3D = true; return copyTo(shape3D.vertices, shape3D.faces);
    }
    
    /**
     * Adds a cube the size of one pixel. It will take a pixel from the texture and
     * uses that as the texture of said cube. The accurate name would actually be
     * "addVoxel". This method has been added to make it more compatible with Techne,
     * and allows for easy single-colored boxes.
     * @param x the starting x-position
     * @param y the starting y-position
     * @param z the starting z-position
     * @param width the width of the box
     * @param height the height of the box
     * @param length the length of the box
     */
    public ModelRendererTurbo addPixel(float x, float y, float z, float width, float height, float length){
    	return addPixel(x, y, z, new float[] {width, height, length}, texoffx, texoffy);
    }
    
    /**
     * Adds a cube the size of one pixel. It will take a pixel from the texture and
     * uses that as the texture of said cube. The accurate name would actually be
     * "addVoxel". It will not overwrite the model data, but rather, it will add to
     * the model.
     * @param x the starting x-position
     * @param y the starting y-position
     * @param z the starting z-position
     * @param scale the "scale" of the cube, where scale is a float integer consisting of three values
     * @param w the x-coordinate on the texture
     * @param h the y-coordinate on the texture
     */
    public ModelRendererTurbo addPixel(float x, float y, float z, float[] scale, int w, int h){
    	TexturedVertex[] verts = new TexturedVertex[8];
	    TexturedPolygon[] poly = new TexturedPolygon[6];
    	float x1 = x + scale[0];
    	float y1 = y + scale[1];
    	float z1 = z + scale[2];
        float[] f = {x, y, z};
        float[] f1 = {x1, y, z};
        float[] f2 = {x1, y1, z};
        float[] f3 = {x, y1, z};
        float[] f4 = {x, y, z1};
        float[] f5 = {x1, y, z1};
        float[] f6 = {x1, y1, z1};
        float[] f7 = {x, y1, z1};
        TexturedVertex TexturedVertex = new TexturedVertex(f[0], f[1], f[2], 0.0F, 0.0F);
        TexturedVertex TexturedVertex1 = new TexturedVertex(f1[0], f1[1], f1[2], 0.0F, 8F);
        TexturedVertex TexturedVertex2 = new TexturedVertex(f2[0], f2[1], f2[2], 8F, 8F);
        TexturedVertex TexturedVertex3 = new TexturedVertex(f3[0], f3[1], f3[2], 8F, 0.0F);
        TexturedVertex TexturedVertex4 = new TexturedVertex(f4[0], f4[1], f4[2], 0.0F, 0.0F);
        TexturedVertex TexturedVertex5 = new TexturedVertex(f5[0], f5[1], f5[2], 0.0F, 8F);
        TexturedVertex TexturedVertex6 = new TexturedVertex(f6[0], f6[1], f6[2], 8F, 8F);
        TexturedVertex TexturedVertex7 = new TexturedVertex(f7[0], f7[1], f7[2], 8F, 0.0F);
        verts[0] = TexturedVertex;
        verts[1] = TexturedVertex1;
        verts[2] = TexturedVertex2;
        verts[3] = TexturedVertex3;
        verts[4] = TexturedVertex4;
        verts[5] = TexturedVertex5;
        verts[6] = TexturedVertex6;
        verts[7] = TexturedVertex7;
        poly[0] = addPolygonReturn(new TexturedVertex[] {
            TexturedVertex5, TexturedVertex1, TexturedVertex2, TexturedVertex6
        }, w, h, w + 1, h + 1);
        poly[1] = addPolygonReturn(new TexturedVertex[] {
            TexturedVertex, TexturedVertex4, TexturedVertex7, TexturedVertex3
        }, w, h, w + 1, h + 1);
        poly[2] = addPolygonReturn(new TexturedVertex[] {
            TexturedVertex5, TexturedVertex4, TexturedVertex, TexturedVertex1
        }, w, h, w + 1, h + 1);
        poly[3] = addPolygonReturn(new TexturedVertex[] {
            TexturedVertex2, TexturedVertex3, TexturedVertex7, TexturedVertex6
        }, w, h, w + 1, h + 1);
        poly[4] = addPolygonReturn(new TexturedVertex[] {
            TexturedVertex1, TexturedVertex, TexturedVertex3, TexturedVertex2
        }, w, h, w + 1, h + 1);
        poly[5] = addPolygonReturn(new TexturedVertex[] {
            TexturedVertex4, TexturedVertex5, TexturedVertex6, TexturedVertex7
        }, w, h, w + 1, h + 1);
        return copyTo(verts, poly);
    }
    
    /**
     * Creates a model shaped like the exact image on the texture. Note that this method will
     * increase the amount of quads on your model, which could effectively slow down your
     * PC, so unless it is really a necessity to use it, I'd suggest you avoid using this
     * method to create your model.
     * @param x the starting x-position
     * @param y the starting y-position
     * @param z the starting z-position
     * @param w the width of the sprite
     * @param h the height of the sprite
     * @param expansion the expansion of the sprite. It only increases the size in each direction by that many.
     */
    public ModelRendererTurbo addSprite(float x, float y, float z, int w, int h, float expansion){
    	return addSprite(x, y, z, w, h, 1, false, false, false, false, false, expansion);
    }
    
    /**
     * Creates a model shaped like the exact image on the texture. Note that this method will
     * increase the amount of quads on your model, which could effectively slow down your
     * PC, so unless it is really a necessity to use it, I'd suggest you avoid using this
     * method to create your model.
     * @param x the starting x-position
     * @param y the starting y-position
     * @param z the starting z-position
     * @param w the width of the sprite
     * @param h the height of the sprite
     * @param rotX a boolean to define if it rotates 90 degrees around its yaw-axis
     * @param rotY a boolean to define if it rotates 90 degrees around its pitch-axis
     * @param rotZ a boolean to define if it rotates 90 degrees around its roll-axis
     * @param mirrorX a boolean to define if the sprite should be mirrored
     * @param mirrorY a boolean to define if the sprite should be flipped
     * @param expansion the expansion of the sprite. It only increases the size in each direction by that many.
     */
    public ModelRendererTurbo addSprite(float x, float y, float z, int w, int h, boolean rotX, boolean rotY, boolean rotZ, boolean mirrorX, boolean mirrorY, float expansion){
    	return addSprite(x, y, z, w, h, 1, rotX, rotY, rotZ, mirrorX, mirrorY, expansion);
    }
    
    /**
     * Creates a model shaped like the exact image on the texture. Note that this method will
     * increase the amount of quads on your model, which could effectively slow down your
     * PC, so unless it is really a necessity to use it, I'd suggest you avoid using this
     * method to create your model.
     * @param x the starting x-position
     * @param y the starting y-position
     * @param z the starting z-position
     * @param w the width of the sprite
     * @param h the height of the sprite
     * @param d the depth of the shape itself
     * @param rotX a boolean to define if it rotates 90 degrees around its yaw-axis
     * @param rotY a boolean to define if it rotates 90 degrees around its pitch-axis
     * @param rotZ a boolean to define if it rotates 90 degrees around its roll-axis
     * @param mirrorX a boolean to define if the sprite should be mirrored
     * @param mirrorY a boolean to define if the sprite should be flipped
     * @param expansion the expansion of the sprite. It only increases the size in each direction by that many.
     */
    public ModelRendererTurbo addSprite(float x, float y, float z, int w, int h, int d, boolean rotX, boolean rotY, boolean rotZ, boolean mirrorX, boolean mirrorY, float expansion){
    	return addSprite(x, y, z, w, h, d, 1.0F, rotX, rotY, rotZ, mirrorX, mirrorY, expansion);
    }
    
    /**
     * Creates a model shaped like the exact image on the texture. Note that this method will
     * increase the amount of quads on your model, which could effectively slow down your
     * PC, so unless it is really a necessity to use it, I'd suggest you avoid using this
     * method to create your model.
     * @param x the starting x-position
     * @param y the starting y-position
     * @param z the starting z-position
     * @param w the width of the sprite
     * @param h the height of the sprite
     * @param d the depth of the shape itself
     * @param pixelScale the scale of each individual pixel
     * @param rotX a boolean to define if it rotates 90 degrees around its yaw-axis
     * @param rotY a boolean to define if it rotates 90 degrees around its pitch-axis
     * @param rotZ a boolean to define if it rotates 90 degrees around its roll-axis
     * @param mirrorX a boolean to define if the sprite should be mirrored
     * @param mirrorY a boolean to define if the sprite should be flipped
     * @param expansion the expansion of the sprite. It only increases the size in each direction by that many.
     */
    public ModelRendererTurbo addSprite(float x, float y, float z, int w, int h, int d, float pixelScale, boolean rotX, boolean rotY, boolean rotZ, boolean mirrorX, boolean mirrorY, float expansion){
    	String[] mask = new String[h]; char[] str = new char[w]; Arrays.fill(str, '1'); Arrays.fill(mask, new String(str));
    	return addSprite(x, y, z, mask, d, pixelScale, rotX, rotY, rotZ, mirrorX, mirrorY, expansion);
    }

    /**
     * Creates a model shaped like the exact image on the texture. Note that this method will
     * increase the amount of quads on your model, which could effectively slow down your
     * PC, so unless it is really a necessity to use it, I'd suggest you avoid using this
     * method to create your model.
     * <br /><br />
     * This method uses a mask string. This way you can reduce the amount of quads used. To
     * use this, create a String array, where you use a 1 to signify that the pixel will be
     * drawn. Any other character will cause that pixel to not be drawn.
     * @param x the starting x-position
     * @param y the starting y-position
     * @param z the starting z-position
     * @param mask an array with the mask string
     * @param d the depth of the shape itself
     * @param pixelScale the scale of each individual pixel
     * @param rotX a boolean to define if it rotates 90 degrees around its yaw-axis
     * @param rotY a boolean to define if it rotates 90 degrees around its pitch-axis
     * @param rotZ a boolean to define if it rotates 90 degrees around its roll-axis
     * @param mirrorX a boolean to define if the sprite should be mirrored
     * @param mirrorY a boolean to define if the sprite should be flipped
     * @param expansion the expansion of the sprite. It only increases the size in each direction by that many.
     */
    public ModelRendererTurbo addSprite(float x, float y, float z, String[] mask, int d, float pixelScale, boolean rotX, boolean rotY, boolean rotZ, boolean mirrorX, boolean mirrorY, float expansion){
    	int w = mask[0].length();
    	int h = mask.length;
    	float x1 = x - expansion;
    	float y1 = y - expansion;
    	float z1 = z - expansion;
    	int wDir = 0;
    	int hDir = 0;
    	int dDir = 0;
    	float wScale = 1F + (expansion / ( w * pixelScale));
    	float hScale = 1F + (expansion / ( h * pixelScale));	    	
    	if(!rotX){
    		if(!rotY){
    			if(!rotZ){
    				wDir = 0;
    				hDir = 1;
    				dDir = 2;
    			}
    			else{
    				wDir = 1;
    				hDir = 0;
    				dDir = 2;
    			}
    		}
    		else{
    			if(!rotZ){
    				wDir = 2;
    				hDir = 1;
    				dDir = 0;
       			}
    			else{
    				wDir = 2;
    				hDir = 0;
    				dDir = 1;
    			}
    		}
    	}
    	else{
    		if(!rotY){
    			if(!rotZ){
    				wDir = 0;
    				hDir = 2;
    				dDir = 1;
    			}
    			else{
    				wDir = 1;
    				hDir = 2;
    				dDir = 0;
    			}
    		}
    		else{
    			if(!rotZ){
    				wDir = 2;
    				hDir = 0;
    				dDir = 1;
       			}
    			else{
    				wDir = 2;
    				hDir = 1;
    				dDir = 0;
    			}
    		}
    	}
    	int texStartX = texoffx + (mirrorX ? w * 1 - 1 : 0);
    	int texStartY = texoffy + (mirrorY ? h * 1 - 1 : 0);
    	int texDirX = (mirrorX ? -1 : 1);
    	int texDirY = (mirrorY ? -1 : 1);
    	float wVoxSize = getPixelSize(wScale, hScale, d * pixelScale + expansion * 2, 0, 1, wDir, 1, 1);
    	float hVoxSize = getPixelSize(wScale, hScale, d * pixelScale + expansion * 2, 0, 1, hDir, 1, 1);
    	float dVoxSize = getPixelSize(wScale, hScale, d * pixelScale + expansion * 2, 0, 1, dDir, 1, 1);	
    	for(int i = 0; i < w; i++){
    		for(int j = 0; j < h; j++){
    			if(mask[j].charAt(i) == '1'){
	    			addPixel(x1 + getPixelSize(wScale, hScale, 0, wDir, hDir, 0, i, j),
	    					 y1 + getPixelSize(wScale, hScale, 0, wDir, hDir, 1, i, j),
	    					 z1 + getPixelSize(wScale, hScale, 0, wDir, hDir, 2, i, j),
	    					 new float[] {wVoxSize, hVoxSize, dVoxSize}, texStartX + texDirX * i, texStartY + texDirY * j);
    			}
    		}
    	}
    	return this;
    }
    
    private float getPixelSize(float wScale, float hScale, float dScale, int wDir, int hDir, int checkDir, int texPosX, int texPosY){
    	return (wDir == checkDir ? wScale * texPosX : (hDir == checkDir ? hScale * texPosY : dScale));
    }
    
    /**
     * Adds a spherical shape.
     * @param x
     * @param y
     * @param z
     * @param r
     * @param segs
     * @param rings
     * @param textureW
     * @param textureH
     */
    public ModelRendererTurbo addSphere(float x, float y, float z, float r, int segs, int rings, int textureW, int textureH){
    	if(segs < 3){ segs = 3; } rings++;
    	TexturedVertex[] tempVerts = new TexturedVertex[segs * (rings - 1) + 2];
    	TexturedPolygon[] poly = new TexturedPolygon[segs * rings];
    	tempVerts[0] = new TexturedVertex(x, y - r, z, 0, 0);
    	tempVerts[tempVerts.length - 1] = new TexturedVertex(x, y + r, z, 0, 0);
    	float uOffs = 1.0F / ( textureWidth * 10.0F);
    	float vOffs = 1.0F / ( textureHeight * 10.0F);
    	float texW =  textureW / textureWidth - 2F * uOffs;
    	float texH =  textureH / textureHeight - 2F * vOffs;
    	float segW = texW /  segs;
    	float segH = texH /  rings;
    	float startU =  texoffx /  textureWidth;
    	float startV =  texoffy /  textureHeight;	
    	int currentFace = 0;
    	for(int j = 1; j < rings; j++){
    		for(int i = 0; i < segs; i++){
    			float yWidth = (float)Math.cos(-Static.PI / 2 + (Static.PI / rings) *  j);
    			float yHeight = (float)Math.sin(-Static.PI / 2 + (Static.PI / rings) *  j);
    			float xSize = (float)(Math.sin((Static.PI / segs) * i * 2F + Static.PI) * yWidth);
    			float zSize = (float)(-Math.cos((Static.PI / segs) * i * 2F + Static.PI) * yWidth);
    			int curVert = 1 + i + segs * (j - 1);
    			tempVerts[curVert] = new TexturedVertex(x + xSize * r, y + yHeight * r, z + zSize * r, 0, 0);
    			if(i > 0){
    				TexturedVertex[] verts;
	    			if(j == 1){
	    				verts = new TexturedVertex[4];
	    				verts[0] = tempVerts[curVert].setTexturePosition(startU + segW * i, startV + segH * j);
	    				verts[1] = tempVerts[curVert - 1].setTexturePosition(startU + segW * (i - 1), startV + segH * j);
	    				verts[2] = tempVerts[0].setTexturePosition(startU + segW * (i - 1), startV);
	    				verts[3] = tempVerts[0].setTexturePosition(startU + segW + segW * i, startV);
	    			}
	    			else{
	    				verts = new TexturedVertex[4];
	    				verts[0] = tempVerts[curVert].setTexturePosition(startU + segW * i, startV + segH * j);
	    				verts[1] = tempVerts[curVert - 1].setTexturePosition(startU + segW * (i - 1), startV + segH * j);
	    				verts[2] = tempVerts[curVert - 1 - segs].setTexturePosition(startU + segW * (i - 1), startV + segH * (j - 1));	    				
	    				verts[3] = tempVerts[curVert - segs].setTexturePosition(startU + segW * i, startV + segH * (j - 1));
	    			}
	    			poly[currentFace] = new TexturedPolygon(verts);
	    			currentFace++;
    			}
    		}
			TexturedVertex[] verts;
   			if(j == 1){
    			verts = new TexturedVertex[4];
    			verts[0] = tempVerts[1].setTexturePosition(startU + segW * segs, startV + segH * j);
    			verts[1] = tempVerts[segs].setTexturePosition(startU + segW * (segs - 1), startV + segH * j);
    			verts[2] = tempVerts[0].setTexturePosition(startU + segW * (segs - 1), startV);
    			verts[3] = tempVerts[0].setTexturePosition(startU + segW * segs, startV);
    		}
    		else{
    			verts = new TexturedVertex[4];
    			verts[0] = tempVerts[1 + segs * (j - 1)].setTexturePosition(startU + texW, startV + segH * j);
    			verts[1] = tempVerts[segs * (j - 1) + segs].setTexturePosition(startU + texW - segW, startV + segH * j);
    			verts[2] = tempVerts[segs * (j - 1)].setTexturePosition(startU + texW - segW, startV + segH * (j - 1));	    				
    			verts[3] = tempVerts[1 + segs * (j - 1) - segs].setTexturePosition(startU + texW, startV + segH * (j - 1));
			}
   			poly[currentFace] = new TexturedPolygon(verts);
   			currentFace++;
    	}
		for(int i = 0; i < segs; i++){
			TexturedVertex[] verts = new TexturedVertex[3];
			int curVert = tempVerts.length - (segs + 1);
			verts[0] = tempVerts[tempVerts.length - 1].setTexturePosition(startU + segW * (i + 0.5F), startV + texH);
			verts[1] = tempVerts[curVert + i].setTexturePosition(startU + segW * i, startV + texH - segH);
			verts[2] = tempVerts[curVert + ((i + 1) % segs)].setTexturePosition(startU + segW * (i + 1), startV + texH - segH);
			poly[currentFace] = new TexturedPolygon(verts);
			currentFace++;
		}
		return copyTo(tempVerts, poly);
    }
    
    /**
     * Adds a cone.
     * @param x the x-position of the base
     * @param y the y-position of the base
     * @param z the z-position of the base
     * @param radius the radius of the cylinder
     * @param length the length of the cylinder
     * @param segments the amount of segments the cylinder is made of
     */
    public ModelRendererTurbo addCone(float x, float y, float z, float radius, float length, int segments){
    	return addCone(x, y, z, radius, length, segments, 1F);
    }
    
    /**
     * Adds a cone.
     * 
     * baseScale cannot be zero. If it is, it will automatically be set to 1F.
     * 
     * @param x the x-position of the base
     * @param y the y-position of the base
     * @param z the z-position of the base
     * @param radius the radius of the cylinder
     * @param length the length of the cylinder
     * @param segments the amount of segments the cylinder is made of
     * @param baseScale the scaling of the base. Can be negative.
     */
    public ModelRendererTurbo addCone(float x, float y, float z, float radius, float length, int segments, float baseScale){
    	return addCone(x, y, z, radius, length, segments, baseScale, MR_TOP);
    }
    
    /**
     * Adds a cone.
     * 
     * baseScale cannot be zero. If it is, it will automatically be set to 1F.
     * 
     * Setting the baseDirection to either MR_LEFT, MR_BOTTOM or MR_BACK will result in
     * the top being placed at the (x,y,z).
     * 
     * @param x the x-position of the base
     * @param y the y-position of the base
     * @param z the z-position of the base
     * @param radius the radius of the cylinder
     * @param length the length of the cylinder
     * @param segments the amount of segments the cylinder is made of
     * @param baseScale the scaling of the base. Can be negative.
     * @param baseDirection the direction it faces
     */    
    public ModelRendererTurbo addCone(float x, float y, float z, float radius, float length, int segments, float baseScale, int baseDirection){
    	return addCone(x, y, z, radius, length, segments, baseScale, baseDirection, (int)Math.floor(radius * 2F), (int)Math.floor(radius * 2F));
    }
    
    /**
     * Adds a cone.
     * 
     * baseScale cannot be zero. If it is, it will automatically be set to 1F.
     * 
     * Setting the baseDirection to either MR_LEFT, MR_BOTTOM or MR_BACK will result in
     * the top being placed at the (x,y,z).
     * 
     * The textures for the sides are placed next to each other.
     * 
     * @param x the x-position of the base
     * @param y the y-position of the base
     * @param z the z-position of the base
     * @param radius the radius of the cylinder
     * @param length the length of the cylinder
     * @param segments the amount of segments the cylinder is made of
     * @param baseScale the scaling of the base. Can be negative.
     * @param baseDirection the direction it faces
     * @param textureCircleDiameterW the diameter width of the circle on the texture
     * @param textureCircleDiameterH the diameter height of the circle on the texture
     */
    public ModelRendererTurbo addCone(float x, float y, float z, float radius, float length, int segments, float baseScale, int baseDirection, int textureCircleDiameterW, int textureCircleDiameterH){
    	return addCylinder(x, y, z, radius, length, segments, baseScale, 0.0F, baseDirection, textureCircleDiameterW, textureCircleDiameterH, 1, null);
    }
    
    /**
     * Adds a cylinder.
     * @param x the x-position of the base
     * @param y the y-position of the base
     * @param z the z-position of the base
     * @param radius the radius of the cylinder
     * @param length the length of the cylinder
     * @param segments the amount of segments the cylinder is made of
     */
    public ModelRendererTurbo addCylinder(float x, float y, float z, float radius, float length, int segments){
    	return addCylinder(x, y, z, radius, length, segments, 1F, 1F);
    }
    
    /**
     * Adds a cylinder.
     * 
     * You can make cones by either setting baseScale or topScale to zero. Setting both
     * to zero will set the baseScale to 1F.
     * 
     * @param x the x-position of the base
     * @param y the y-position of the base
     * @param z the z-position of the base
     * @param radius the radius of the cylinder
     * @param length the length of the cylinder
     * @param segments the amount of segments the cylinder is made of
     * @param baseScale the scaling of the base. Can be negative.
     * @param topScale the scaling of the top. Can be negative.
     */
    public ModelRendererTurbo addCylinder(float x, float y, float z, float radius, float length, int segments, float baseScale, float topScale){
    	return addCylinder(x, y, z, radius, length, segments, baseScale, topScale, MR_TOP);
    }
    
    /**
     * Adds a cylinder.
     * 
     * You can make cones by either setting baseScale or topScale to zero. Setting both
     * to zero will set the baseScale to 1F.
     * 
     * Setting the baseDirection to either MR_LEFT, MR_BOTTOM or MR_BACK will result in
     * the top being placed at the (x,y,z).
     * 
     * @param x the x-position of the base
     * @param y the y-position of the base
     * @param z the z-position of the base
     * @param radius the radius of the cylinder
     * @param length the length of the cylinder
     * @param segments the amount of segments the cylinder is made of
     * @param baseScale the scaling of the base. Can be negative.
     * @param topScale the scaling of the top. Can be negative.
     * @param baseDirection the direction it faces
     */
    public ModelRendererTurbo addCylinder(float x, float y, float z, float radius, float length, int segments, float baseScale, float topScale, int baseDirection){
    	return addCylinder(x, y, z, radius, length, segments, baseScale, topScale, baseDirection, (int)Math.floor(radius * 2F), (int)Math.floor(radius * 2F), (int)Math.floor(length), null);
    }
    
    public ModelRendererTurbo addCylinder(float x, float y, float z, float radius, float length, int segments, float baseScale, float topScale, int baseDirection, Vec3f topoff){
    	return addCylinder(x, y, z, radius, length, segments, baseScale, topScale, baseDirection, (int)Math.floor(radius * 2F), (int)Math.floor(radius * 2F), (int)Math.floor(length), topoff);
    }
    
    /**
     * Adds a cylinder.
     * 
     * You can make cones by either setting baseScale or topScale to zero. Setting both
     * to zero will set the baseScale to 1F.
     * 
     * Setting the baseDirection to either MR_LEFT, MR_BOTTOM or MR_BACK will result in
     * the top being placed at the (x,y,z).
     * 
     * The textures for the base and top are placed next to each other, while the body
     * will be placed below the circles.
     * 
     * @param x the x-position of the base
     * @param y the y-position of the base
     * @param z the z-position of the base
     * @param radius the radius of the cylinder
     * @param length the length of the cylinder
     * @param segments the amount of segments the cylinder is made of
     * @param baseScale the scaling of the base. Can be negative.
     * @param topScale the scaling of the top. Can be negative.
     * @param baseDirection the direction it faces
     * @param textureCircleDiameterW the diameter width of the circle on the texture
     * @param textureCircleDiameterH the diameter height of the circle on the texture
     * @param textureH the height of the texture of the body
     */
	public ModelRendererTurbo addCylinder(float x, float y, float z, float radius, float length, int segments, float baseScale, float topScale, int baseDirection, int textureCircleDiameterW, int textureCircleDiameterH, int textureH, Vec3f topoff){
		boolean dirTop = (baseDirection == MR_TOP || baseDirection == MR_BOTTOM);
		boolean dirSide = (baseDirection == MR_RIGHT || baseDirection == MR_LEFT);
		boolean dirFront = (baseDirection == MR_FRONT || baseDirection == MR_BACK);
		boolean dirMirror = (baseDirection == MR_LEFT || baseDirection == MR_BOTTOM || baseDirection == MR_BACK);
		boolean coneBase = (baseScale == 0);
		boolean coneTop = (topScale == 0);
		if(coneBase && coneTop){
			baseScale = 1F;
			coneBase = false;
		}
		TexturedVertex[] tempVerts = new TexturedVertex[segments * (coneBase || coneTop ? 1 : 2) + 2];
		TexturedPolygon[] poly = new TexturedPolygon[segments * (coneBase || coneTop ? 2 : 3)];
		float xLength = (dirSide ? length : 0);
		float yLength = (dirTop ? length : 0);
		float zLength = (dirFront ? length : 0);
		float xStart = (dirMirror ? x + xLength : x);
		float yStart = (dirMirror ? y + yLength : y);
		float zStart = (dirMirror ? z + zLength : z);
		float xEnd = (!dirMirror ? x + xLength : x) + (topoff == null ? 0 : topoff.xCoord);
		float yEnd = (!dirMirror ? y + yLength : y) + (topoff == null ? 0 : topoff.yCoord);
		float zEnd = (!dirMirror ? z + zLength : z) + (topoff == null ? 0 : topoff.zCoord);
		tempVerts[0] = new TexturedVertex(xStart, yStart, zStart, 0, 0);
		tempVerts[tempVerts.length - 1] = new TexturedVertex(xEnd, yEnd, zEnd, 0, 0);
		float xCur = xStart;
		float yCur = yStart;
		float zCur = zStart;
		float sCur = (coneBase ? topScale : baseScale);
		for(int repeat = 0; repeat < (coneBase || coneTop ? 1 : 2); repeat++){
			for(int index = 0; index < segments; index++){
				float xSize = (float)((mirror ^ dirMirror ? -1 : 1) * Math.sin((Static.PI / segments) * index * 2F + Static.PI) * radius * sCur);
				float zSize = (float)(-Math.cos((Static.PI / segments) * index * 2F + Static.PI) * radius * sCur);
				float xPlace = xCur + (!dirSide ? xSize : 0);
				float yPlace = yCur + (!dirTop ? zSize : 0);
				float zPlace = zCur + (dirSide ? xSize : (dirTop ? zSize : 0));
				tempVerts[1 + index + repeat * segments] = new TexturedVertex(xPlace, yPlace, zPlace, 0, 0 );
			}
			xCur = xEnd; yCur = yEnd; zCur = zEnd; sCur = topScale;
		}
		float uScale = 1.0F / textureWidth;
		float vScale = 1.0F / textureHeight;
		float uOffset = uScale / 20.0F;
		float vOffset = vScale / 20.0F;
		float uCircle = textureCircleDiameterW * uScale;
		float vCircle = textureCircleDiameterH * vScale;
		float uWidth = (uCircle * 2F - uOffset * 2F) / segments;
		float vHeight = textureH * vScale - uOffset * 2f;
		float uStart = texoffx * uScale;
		float vStart = texoffy * vScale;	
		TexturedVertex[] vert;
		for(int index = 0; index < segments; index++){
			int index2 = (index + 1) % segments;
			float uSize = (float)(Math.sin((Static.PI / segments) * index * 2F + (!dirTop ? 0 : Static.PI)) * (0.5F * uCircle - 2F * uOffset));
			float vSize = (float)(Math.cos((Static.PI / segments) * index * 2F + (!dirTop ? 0 : Static.PI)) * (0.5F * vCircle - 2F * vOffset));
			float uSize1 = (float)(Math.sin((Static.PI / segments) * index2 * 2F + (!dirTop ? 0 : Static.PI)) * (0.5F * uCircle - 2F * uOffset));
			float vSize1 = (float)(Math.cos((Static.PI / segments) * index2 * 2F + (!dirTop ? 0 : Static.PI)) * (0.5F * vCircle - 2F * vOffset));
			vert = new TexturedVertex[3];	
			vert[0] = tempVerts[0].setTexturePosition(uStart + 0.5F * uCircle, vStart + 0.5F * vCircle);
			vert[1] = tempVerts[1 + index2].setTexturePosition(uStart + 0.5F * uCircle + uSize1, vStart + 0.5F * vCircle + vSize1);
			vert[2] = tempVerts[1 + index].setTexturePosition(uStart + 0.5F * uCircle + uSize, vStart + 0.5F * vCircle + vSize);
			poly[index] = new TexturedPolygon(vert);
			if(!(mirror ^ flip)){
				poly[index].flipFace();
			}
			if(!coneBase && !coneTop){
				vert = new TexturedVertex[4];
				vert[0] = tempVerts[1 + index].setTexturePosition(uStart + uOffset + uWidth * index, vStart + vOffset + vCircle);
				vert[1] = tempVerts[1 + index2].setTexturePosition(uStart + uOffset + uWidth * (index + 1), vStart + vOffset + vCircle);
				vert[2] = tempVerts[1 + segments + index2].setTexturePosition(uStart + uOffset + uWidth * (index + 1), vStart + vOffset + vCircle + vHeight);
				vert[3] = tempVerts[1 + segments + index].setTexturePosition(uStart + uOffset + uWidth * index, vStart + vOffset + vCircle + vHeight);
				poly[index + segments] = new TexturedPolygon(vert);
				if(!(mirror ^ flip)){
					poly[index + segments].flipFace();
				}
			}
			vert = new TexturedVertex[3];
			vert[0] = tempVerts[tempVerts.length - 1].setTexturePosition(uStart + 1.5F * uCircle, vStart + 0.5F * vCircle);
			vert[1] = tempVerts[tempVerts.length - 2 - index].setTexturePosition(uStart + 1.5F * uCircle + uSize1, vStart + 0.5F * vCircle + vSize1);
			vert[2] = tempVerts[tempVerts.length - (1 + segments) + ((segments - index) % segments)].setTexturePosition(uStart + 1.5F * uCircle + uSize, vStart + 0.5F * vCircle + vSize);
			poly[poly.length - segments + index]  = new TexturedPolygon(vert);
			if(!(mirror ^ flip)){
				poly[poly.length - segments + index].flipFace();
			}
		}
		return copyTo(tempVerts, poly);
	}
    
    /**
     * Adds a Waveform .obj file as a model. Model files use the entire texture file.
     * @param location the ResourceLocation of the .obj file.
     */
    public ModelRendererTurbo addObj(String id, InputStream steam){
    	return addModel(id, steam, ModelPool.OBJ);
    }
    
    /**
     * Adds model format support. Model files use the entire texture file.
     * @param file the location of the model file.
     * @param modelFormat the class of the model format interpreter
     */
    public ModelRendererTurbo addModel(String id, InputStream stream, Class<?> modelFormat){
    	ModelPoolEntry entry = ModelPool.addLocation(id, stream, modelFormat);
    	if(entry == null){ return null; }
    	TexturedVertex[] verts = Arrays.copyOf(entry.vertices, entry.vertices.length);
    	TexturedPolygon[] poly = Arrays.copyOf(entry.faces, entry.faces.length);
    	if(flip){ for(int l = 0; l < faces.length; l++){ faces[l].flipFace(); } }
    	return copyTo(verts, poly);
    }
    
    /**
     * Sets a new position for the texture offset.
     * @param x the x-coordinate of the texture start
     * @param y the y-coordinate of the texture start
     */
    public ModelRendererTurbo setTextureOffset(int x, int y){
    	texoffx = x; texoffy = y; return this;
    }

    /**
     * Sets the position of the shape, relative to the model's origins. Note that changing
     * the offsets will not change the pivot of the model.
     * @param x the x-position of the shape
     * @param y the y-position of the shape
     * @param z the z-position of the shape
     */
    public ModelRendererTurbo setPosition(float x, float y, float z){
        rotationPointX = x; rotationPointY = y; rotationPointZ = z; return this;
    }
    
    /**
     * Mirrors the model in any direction.
     * @param x whether the model should be mirrored in the x-direction
     * @param y whether the model should be mirrored in the y-direction
     * @param z whether the model should be mirrored in the z-direction
     */
    public ModelRendererTurbo doMirror(boolean x, boolean y, boolean z){
    	for(TexturedPolygon face : faces){
    		TexturedVertex[] verts = face.getVertices();
    		for(TexturedVertex vert : verts){
    			vert.vector.addVector(vert.vector.xCoord * (x ? -1 : 1), vert.vector.yCoord * (y ? -1 : 1), vert.vector.zCoord * (z ? -1 : 1));
    		} if(x ^ y ^ z){ face.flipFace(); }
    	}
    	return this;
    }
    
    /**
     * Sets whether the shape is mirrored or not. This has effect on the way the textures
     * get displayed. When working with addSprite, addPixel and addObj, it will be ignored.
     * @param isMirrored a boolean to define whether the shape is mirrored
     */
    public ModelRendererTurbo setMirrored(boolean isMirrored){
    	mirror = isMirrored; return this;
    }
    
    /**
     * Sets whether the shape's faces are flipped or not. When GL_CULL_FACE is enabled,
     * it won't render the back faces, effectively giving you the possibility to make
     * "hollow" shapes. When working with addSprite and addPixel, it will be ignored.
     * @param isFlipped a boolean to define whether the shape is flipped
     */
    public ModelRendererTurbo setFlipped(boolean isFlipped){
    	flip = isFlipped; return this;
    }
    
    /**
     * Clears the current shape. Since all shapes are stacked into one shape, you can't
     * just replace a shape by overwriting the shape with another one. In this case you
     * would need to clear the shape first.
     */
    public ModelRendererTurbo clear(){
    	vertices = new TexturedVertex[0];
    	faces = new TexturedPolygon[0];
    	return this;
    }
    
    /**
     * Copies an array of vertices and polygons to the current shape. This mainly is
     * used to copy each shape to the main class, but you can just use it to copy
     * your own shapes, for example from other classes, into the current class.
     * @param verts the array of vertices you want to copy
     * @param poly the array of polygons you want to copy
     * @return 
     */
    public ModelRendererTurbo copyTo(TexturedVertex[] verts, TexturedPolygon[] poly){
        vertices = Arrays.copyOf(vertices, vertices.length + verts.length);
        faces = Arrays.copyOf(faces, faces.length + poly.length);
        for(int idx = 0; idx < verts.length; idx++){
        	vertices[vertices.length - verts.length + idx] = verts[idx];
        }
        for(int idx = 0; idx < poly.length; idx++){
        	faces[faces.length - poly.length + idx] = poly[idx];
        } return this;
    }
    
    /**
     * Copies an array of vertices and quads to the current shape. This method
     * converts quads to polygons and then calls the main copyTo method.
     * @param verts the array of vertices you want to copy
     * @param quad the array of quads you want to copy
     */
    /*public void copyTo(TexturedVertex[] verts, TexturedQuad[] quad){
    	TexturedPolygon[] poly = new TexturedPolygon[quad.length];
    	for(int idx = 0; idx < quad.length; idx++){
    		poly[idx] = new TexturedPolygon(quad[idx].vertexPositions);
    	}
    	copyTo(verts, poly);
    }*/
    
    public void render(){ render(0.0625F); }
    
    /**
     * Renders the shape.
     * @param scale the scale of the shape. Usually is 0.0625.
     */
    public void render(float scale){
        if(!showModel){ return; }
        if(displayList == null || forcedRecompile){
            compileDisplayList(scale);
        }
        if(rotationAngleX != 0.0F || rotationAngleY != 0.0F || rotationAngleZ != 0.0F){
            GL11.glPushMatrix();
            GL11.glTranslatef(rotationPointX * scale, rotationPointY * scale, rotationPointZ * scale);
            if(rotationAngleY != 0.0F){
                GL11.glRotatef(rotationAngleY /** 57.29578F*/, 0.0F, 1.0F, 0.0F);
            }
            if(rotationAngleZ != 0.0F){
                GL11.glRotatef(rotationAngleZ /** 57.29578F*/, 0.0F, 0.0F, 1.0F);
            }
            if(rotationAngleX != 0.0F){
                GL11.glRotatef(rotationAngleX /** 57.29578F*/, 1.0F, 0.0F, 0.0F);
            }
    		GL11.glCallList(displayList);
            if(childModels != null){
                for(Object child : childModels){
                    ((ModelRendererTurbo)child).render(scale);
                }
            }
            GL11.glPopMatrix();
        }
        else if(rotationPointX != 0.0F || rotationPointY != 0.0F || rotationPointZ != 0.0F){
            GL11.glTranslatef(rotationPointX * scale, rotationPointY * scale, rotationPointZ * scale);
    		GL11.glCallList(displayList);
            if(childModels != null){
                for(Object child : childModels){
                    ((ModelRendererTurbo)child).render(scale);
                }
            }
            GL11.glTranslatef(-rotationPointX * scale, -rotationPointY * scale, -rotationPointZ * scale);
        }
        else{
    		GL11.glCallList(displayList);
        	if(childModels != null){
                for(Object child : childModels){
                    ((ModelRendererTurbo)child).render(scale);
                }
            }
        }
    }
    
    public void renderWithRotation(float scale){
        if(!showModel){ return; }
        if(displayList == null){ compileDisplayList(scale); }
        GL11.glPushMatrix();
        GL11.glTranslatef(rotationPointX * scale, rotationPointY * scale, rotationPointZ * scale);
        if(rotationAngleY != 0.0F){
            GL11.glRotatef(rotationAngleY /** 57.29578F*/, 0.0F, 1.0F, 0.0F);
        }
        if(rotationAngleX != 0.0F){
            GL11.glRotatef(rotationAngleX /** 57.29578F*/, 1.0F, 0.0F, 0.0F);
        }
        if(rotationAngleZ != 0.0F){
            GL11.glRotatef(rotationAngleZ /** 57.29578F*/, 0.0F, 0.0F, 1.0F);
        }
		GL11.glCallList(displayList);
        GL11.glPopMatrix();
    }

    public void postRender(float scale){
        if(!showModel){ return; }
        if(displayList == null || forcedRecompile){ compileDisplayList(scale); }
        if(rotationAngleX != 0.0F || rotationAngleY != 0.0F || rotationAngleZ != 0.0F){
            GL11.glTranslatef(rotationPointX * scale, rotationPointY * scale, rotationPointZ * scale);
            if(rotationAngleZ != 0.0F){
                GL11.glRotatef(rotationAngleZ /** 57.29578F*/, 0.0F, 0.0F, 1.0F);
            }
            if(rotationAngleY != 0.0F){
                GL11.glRotatef(rotationAngleY /** 57.29578F*/, 0.0F, 1.0F, 0.0F);
            }
            if(rotationAngleX != 0.0F){
                GL11.glRotatef(rotationAngleX /** 57.29578F*/, 1.0F, 0.0F, 0.0F);
            }
        }
        else if(rotationPointX != 0.0F || rotationPointY != 0.0F || rotationPointZ != 0.0F){
            GL11.glTranslatef(rotationPointX * scale, rotationPointY * scale, rotationPointZ * scale);
        }
    }
    
    private void compileDisplayList(float scale){
        displayList = GL11.glGenLists(1);
        GL11.glNewList(displayList, 4864 /*GL_COMPILE*/);
        for(int i = 0; i < faces.length; i++){
            faces[i].draw(Tessellator.INSTANCE, scale, linesColor, getColor(i));
        }
        GL11.glEndList();
    }

	private static RGB lightred   = new RGB(255, 127, 175);
    private static RGB lightgreen = new RGB(175, 255, 127);
    private static RGB lightblue  = new RGB(127, 175, 255);

    public RGB getColor(int face){
		return getColor(this, face);
	}
    
    public static RGB getColor(ModelRendererTurbo turbo, int face){
    	if(!turbo.textured){
    		if(turbo.polygonColor != null) return turbo.polygonColor;
    		switch(face){
    			case 0: return RGB.BLUE;
    			case 1: return lightblue;
    			case 2: return lightred;
    			case 3: return RGB.RED;
    			case 4: return lightgreen;
    			case 5: return RGB.GREEN;
    		}
    		if(face < 192){
    			if(face % 3 == 0) return new RGB(255, 127, 256 - (face * 4));
    			if(face % 3 == 1) return new RGB(256 - (face * 4), 255, 127);
    			if(face % 3 == 2) return new RGB(127, 256 - (face * 4), 255);
    		}
    		return RGB.WHITE;
    	}
    	return null;
    }
    
	public ModelRendererTurbo addShapeBox(float x, float y, float z, int w, int h, int d, float scale, float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, float x5, float y5, float z5, float x6, float y6, float z6, float x7, float y7, float z7){
    	return addShapeBox(x, y, z, (float)w, (float)h, (float)d, scale, x0, y0, z0, x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, x5, y5, z5, x6, y6, z6, x7, y7, z7);
	}

	//TODO /* Texture issue fixed by Ferdinand/FEX at 09-Nov-2018 */
	public ModelRendererTurbo addShapeBox(float x, float y, float z, float w, float h, float d, float scale, float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, float x5, float y5, float z5, float x6, float y6, float z6, float x7, float y7, float z7){
    	float xw = x + w, yh = y + h, zd = z + d;
		x -= scale; y -= scale; z -= scale;
		xw += scale; yh += scale; zd += scale;
		if(mirror){ float fl = xw; xw = x; x = fl; }
		//
		float[] v0 = {x  - x0, y  - y0, z  - z0}, v1 = {xw + x1, y  - y1, z  - z1}, v2 = {xw + x5, yh + y5, z  - z5};
		float[] v3 = {x  - x4, yh + y4, z  - z4}, v4 = {x  - x3, y  - y3, zd + z3}, v5 = {xw + x2, y  - y2, zd + z2};
		float[] v6 = {xw + x6, yh + y6, zd + z6}, v7 = {x  - x7, yh + y7, zd + z7};
		//
		/*float tex0x = d + Math.max(Math.max(z2, z1), Math.max(z5, z6));//leftmax + rightmax, check if not < 0;
		float tex0y = y + Math.max(Math.max(y2, y1), Math.max(y5, y6));
		//
		float tex1x = d + Math.max(Math.max(z0, z3), Math.max(z7, z4));
		float tex1y = y + Math.max(Math.max(y0, y3), Math.max(y7, y4));
		//
		float tex2x = w + Math.max(Math.max(x2, x3), Math.max(x0, x1));
		float tex2y = y + Math.max(Math.max(y2, y3), Math.max(y0, y1));
		//
		float tex3x = w + Math.max(Math.max(x5, x4), Math.max(x7, x6));
		float tex3y = y + Math.max(Math.max(y5, y4), Math.max(y7, y6));
		//
		float tex4x = w + Math.max(Math.max(x1, x0), Math.max(x4, x5));
		float tex4y = y + Math.max(Math.max(y1, y0), Math.max(y4, y5));
		//
		float tex5x = w + Math.max(Math.max(x3, x2), Math.max(x6, x7));
		float tex5y = y + Math.max(Math.max(y3, y2), Math.max(y6, y7));
		//
		//bellow code is based on "addRectShape/addPolygonReturn", but adjusted
    	TexturedVertex[] verts = new TexturedVertex[8];
        TexturedPolygon[] poly = new TexturedPolygon[6];
        TexturedVertex tv0 = new TexturedVertex(v0[0], v0[1], v0[2], 0.0F, 0.0F), tv1 = new TexturedVertex(v1[0], v1[1], v1[2], 0.0F, 8.0F);
        TexturedVertex tv2 = new TexturedVertex(v2[0], v2[1], v2[2], 8.0F, 8.0F), tv3 = new TexturedVertex(v3[0], v3[1], v3[2], 8.0F, 0.0F);
        TexturedVertex tv4 = new TexturedVertex(v4[0], v4[1], v4[2], 0.0F, 0.0F), tv5 = new TexturedVertex(v5[0], v5[1], v5[2], 0.0F, 8.0F);
        TexturedVertex tv6 = new TexturedVertex(v6[0], v6[1], v6[2], 8.0F, 8.0F), tv7 = new TexturedVertex(v7[0], v7[1], v7[2], 8.0F, 0.0F);
        verts[0] = tv0; verts[1] = tv1; verts[2] = tv2; verts[3] = tv3; verts[4] = tv4; verts[5] = tv5; verts[6] = tv6; verts[7] = tv7;
        float uOffs = 1.0F / (textureWidth * 10.0F);
    	float vOffs = 1.0F / (textureHeight * 10.0F); float[] tex;
    	tex = new float[]{ texoffx + d + w, texoffy + d, texoffx + d + w + d, texoffy + d + h };
        poly[0] = new TexturedPolygon(new TexturedVertex[]{
	    	tv5.setTexturePosition((tex[2] + z2) / textureWidth - uOffs, (tex[1] - y2) / textureHeight + vOffs),
	    	tv1.setTexturePosition((tex[0] - z1) / textureWidth + uOffs, (tex[1] - y1) / textureHeight + vOffs),
	    	tv2.setTexturePosition((tex[0] - z5) / textureWidth + uOffs, (tex[3] + y5) / textureHeight - vOffs),
	    	tv6.setTexturePosition((tex[2] + z6) / textureWidth - uOffs, (tex[3] + y6) / textureHeight - vOffs)});
        //poly[1] = addPolygonReturn(new TexturedVertex[] { tv0, tv4, tv7, tv3 }, texoffx + 0, texoffy + d, texoffx + d, texoffy + d + h);
    	tex = new float[]{ texoffx + 0, texoffy + d, texoffx + d, texoffy + d + h };
        poly[1] = new TexturedPolygon(new TexturedVertex[]{
	    	tv0.setTexturePosition((tex[2] - z0) / textureWidth - uOffs, (tex[1] - y0) / textureHeight + vOffs),
	    	tv4.setTexturePosition((tex[0] + z3) / textureWidth + uOffs, (tex[1] - y3) / textureHeight + vOffs),
	    	tv7.setTexturePosition((tex[0] + z7) / textureWidth + uOffs, (tex[3] + y7) / textureHeight - vOffs),
	    	tv3.setTexturePosition((tex[2] - z4) / textureWidth - uOffs, (tex[3] + y4) / textureHeight - vOffs)});
        //poly[2] = addPolygonReturn(new TexturedVertex[] { tv5, tv4, tv0, tv1 }, texoffx + d, texoffy + 0, texoffx + d + w, texoffy + d);
        tex = new float[]{ texoffx + d, texoffy + 0, texoffx + d + w, texoffy + d };
        poly[2] = new TexturedPolygon(new TexturedVertex[]{
	    	tv5.setTexturePosition((tex[2] + x2) / textureWidth - uOffs, (tex[1] - y2) / textureHeight + vOffs),
	    	tv4.setTexturePosition((tex[0] - x3) / textureWidth + uOffs, (tex[1] - y3) / textureHeight + vOffs),
	    	tv0.setTexturePosition((tex[0] - x0) / textureWidth + uOffs, (tex[3] - y0) / textureHeight - vOffs),
	    	tv1.setTexturePosition((tex[2] + x1) / textureWidth - uOffs, (tex[3] - y1) / textureHeight - vOffs)});
        //poly[3] = addPolygonReturn(new TexturedVertex[] { tv2, tv3, tv7, tv6 }, texoffx + d + w, texoffy + 0, texoffx + d + w + w, texoffy + d);
        tex = new float[]{ texoffx + d + w, texoffy + 0, texoffx + d + w + w, texoffy + d };
        poly[3] = new TexturedPolygon(new TexturedVertex[]{
	    	tv2.setTexturePosition((tex[2] + x5) / textureWidth - uOffs, (tex[1] + y5) / textureHeight + vOffs),
	    	tv3.setTexturePosition((tex[0] - x4) / textureWidth + uOffs, (tex[1] + y4) / textureHeight + vOffs),
	    	tv7.setTexturePosition((tex[0] - x7) / textureWidth + uOffs, (tex[3] + y7) / textureHeight - vOffs),
	    	tv6.setTexturePosition((tex[2] + x6) / textureWidth - uOffs, (tex[3] + y6) / textureHeight - vOffs)});
        //poly[4] = addPolygonReturn(new TexturedVertex[] { tv1, tv0, tv3, tv2 }, texoffx + d, texoffy + d, texoffx + d + w, texoffy + d + h);
        tex = new float[]{ texoffx + d, texoffy + d, texoffx + d + w, texoffy + d + h };
        poly[4] = new TexturedPolygon(new TexturedVertex[]{
	    	tv1.setTexturePosition((tex[2] + x1) / textureWidth - uOffs, (tex[1] - z1) / textureHeight + vOffs),
	    	tv0.setTexturePosition((tex[0] - x0) / textureWidth + uOffs, (tex[1] - z0) / textureHeight + vOffs),
	    	tv3.setTexturePosition((tex[0] - x4) / textureWidth + uOffs, (tex[3] - z4) / textureHeight - vOffs),
	    	tv2.setTexturePosition((tex[2] + x5) / textureWidth - uOffs, (tex[3] - z5) / textureHeight - vOffs)});
        //poly[5] = addPolygonReturn(new TexturedVertex[] { tv4, tv5, tv6, tv7 }, texoffx + d + w + d, texoffy + d, texoffx + d + w + d + w, texoffy + d + h);
        tex = new float[]{ texoffx + d + w + d, texoffy + d, texoffx + d + w + d + w, texoffy + d + h };
        poly[5] = new TexturedPolygon(new TexturedVertex[]{
	    	tv4.setTexturePosition((tex[2] - x3) / textureWidth - uOffs, (tex[1] + z3) / textureHeight + vOffs),
	    	tv5.setTexturePosition((tex[0] + x2) / textureWidth + uOffs, (tex[1] + z2) / textureHeight + vOffs),
	    	tv6.setTexturePosition((tex[0] + x6) / textureWidth + uOffs, (tex[3] + z6) / textureHeight - vOffs),
	    	tv7.setTexturePosition((tex[2] - x7) / textureWidth - uOffs, (tex[3] + z7) / textureHeight - vOffs)});
        if(mirror ^ flip){
            for(int l = 0; l < poly.length; l++){
            	poly[l].flipFace();
            }
        }
        return copyTo(verts, poly);*/
		return addRectShape(v0, v1, v2, v3, v4, v5, v6, v7, w, h, d);
	}
	
	public String toString(String alt){
		String str = this.toString();
		return str == null || str.equals("") ? alt : str;
	}
	
	@Override
	public String toString(){
		return boxName;
	}
	
	public TexturedPolygon[] getFaces(){
		return faces;
	}
	
	public TexturedVertex[] getVertices(){
		return vertices;
	}
	
	public Integer displaylist(){
		return displayList;
	}
	
	//---///----///---//
	/** Code bellow added in means of compatibility, mainly used by the FMT Editor's MTB importer, from where it is, ask the one who made the importer. **/
	
    public ModelRendererTurbo addTrapezoid(float x, float y, float z, float w, float h, float d, float scale, float bottomScale, int dir){
        float f4 = x + w, f5 = y + h, f6 = z + d; x -= scale; y -= scale; z -= scale; f4 += scale; f5 += scale; f6 += scale;
        int m = (mirror ? -1 : 1); if(mirror){ float f7 = f4;  f4 = x; x = f7; }
        float[] v0 = {x, y, z}, v1 = {f4, y, z}, v2 = {f4, f5, z}, v3 = {x, f5, z};
        float[] v4 = {x, y, f6}, v5 = {f4, y, f6}, v6 = {f4, f5, f6}, v7 = {x, f5, f6};
        //
        switch(dir){
	        case MR_RIGHT:{
	        	v0[1] -= bottomScale; v0[2] -= bottomScale; v3[1] += bottomScale; v3[2] -= bottomScale;
	        	v4[1] -= bottomScale; v4[2] += bottomScale; v7[1] += bottomScale; v7[2] += bottomScale; break;
	        }
	        case MR_LEFT:{
	        	v1[1] -= bottomScale; v1[2] -= bottomScale; v2[1] += bottomScale; v2[2] -= bottomScale;
	        	v5[1] -= bottomScale; v5[2] += bottomScale; v6[1] += bottomScale; v6[2] += bottomScale; break;
	        }
	        case MR_FRONT:{
	        	v0[0] -= m * bottomScale; v0[1] -= bottomScale; v1[0] += m * bottomScale; v1[1] -= bottomScale;
	        	v2[0] += m * bottomScale; v2[1] += bottomScale; v3[0] -= m * bottomScale; v3[1] += bottomScale; break;
	        }
	        case MR_BACK:{
	        	v4[0] -= m * bottomScale; v4[1] -= bottomScale; v5[0] += m * bottomScale; v5[1] -= bottomScale;
	        	v6[0] += m * bottomScale; v6[1] += bottomScale; v7[0] -= m * bottomScale; v7[1] += bottomScale; break;
	        }
	        case MR_TOP:{
	        	v0[0] -= m * bottomScale; v0[2] -= bottomScale; v1[0] += m * bottomScale; v1[2] -= bottomScale;
	        	v4[0] -= m * bottomScale; v4[2] += bottomScale; v5[0] += m * bottomScale; v5[2] += bottomScale; break;
	        }
	        case MR_BOTTOM:{
	        	v2[0] += m * bottomScale; v2[2] -= bottomScale; v3[0] -= m * bottomScale; v3[2] -= bottomScale;
	        	v6[0] += m * bottomScale; v6[2] += bottomScale; v7[0] -= m * bottomScale; v7[2] += bottomScale; break;
	        }
        }
        //
        return addRectShape(v0, v1, v2, v3, v4, v5, v6, v7, w, h, d);
    }
    
	public ModelRendererTurbo addFlexBox(float x, float y, float z, float w, float h, float d, float scale, float bScale1, float bScale2, float bScale3, float bScale4, int dir){
		float f4 = x + w, f5 = y + h, f6 = z + d;
		x -= scale; y -= scale; z -= scale; f4 += scale; f5 += scale; f6 += scale;
		int m = (mirror ? -1 : 1); if(mirror){ float f7 = f4; f4 = x; x = f7; }
		float[] v0 = {x, y, z}, v1 = {f4, y, z}, v2 = {f4, f5, z}, v3 = {x, f5, z};
		float[] v4 = {x, y, f6}, v5 = {f4, y, f6}, v6 = {f4, f5, f6}, v7 = {x, f5, f6};
		//
		switch(dir){
			case MR_RIGHT:{
				v0[1] -= bScale1; v0[2] -= bScale3; v3[1] += bScale2; v3[2] -= bScale3;
				v4[1] -= bScale1; v4[2] += bScale4; v7[1] += bScale2; v7[2] += bScale4; break;
			}
			case MR_LEFT:{
				v1[1] -= bScale1; v1[2] -= bScale3; v2[1] += bScale2; v2[2] -= bScale3;
				v5[1] -= bScale1; v5[2] += bScale4; v6[1] += bScale2; v6[2] += bScale4; break;
			}
			case MR_FRONT:{
				v0[0] -= m * bScale4; v0[1] -= bScale1; v1[0] += m * bScale3; v1[1] -= bScale1;
				v2[0] += m * bScale3; v2[1] += bScale2; v3[0] -= m * bScale4; v3[1] += bScale2; break;
			}
			case MR_BACK:{
				v4[0] -= m * bScale4; v4[1] -= bScale1; v5[0] += m * bScale3; v5[1] -= bScale1;
				v6[0] += m * bScale3; v6[1] += bScale2; v7[0] -= m * bScale4; v7[1] += bScale2; break;
			}
			case MR_TOP:{
				v0[0] -= m * bScale1; v0[2] -= bScale3; v1[0] += m * bScale2; v1[2] -= bScale3;
				v4[0] -= m * bScale1; v4[2] += bScale4; v5[0] += m * bScale2; v5[2] += bScale4; break;
			}
			case MR_BOTTOM:{
				v2[0] += m * bScale2; v2[2] -= bScale3; v3[0] -= m * bScale1; v3[2] -= bScale3;
				v6[0] += m * bScale2; v6[2] += bScale4; v7[0] -= m * bScale1; v7[2] += bScale4; break;
			}
		}
		//
		return addRectShape(v0, v1, v2, v3, v4, v5, v6, v7, w, h, d);
	}
	
	public ModelRendererTurbo addFlexTrapezoid(float x, float y, float z, float w, float h, float d, float scale, float bScale1, float bScale2, float bScale3, float bScale4, float fScale1, float fScale2, int dir){
		float f4 = x + w, f5 = y + h, f6 = z + d; x -= scale; y -= scale; z -= scale; f4 += scale; f5 += scale; f6 += scale;
		int m = (mirror ? -1 : 1); if(mirror){ float f7 = f4; f4 = x; x = f7; }
		float[] v0 = {x, y, z}, v1 = {f4, y, z}, v2 = {f4, f5, z}, v3 = {x, f5, z};
		float[] v4 = {x, y, f6}, v5 = {f4, y, f6}, v6 = {f4, f5, f6}, v7 = {x, f5, f6};
		//
		switch(dir){
			case MR_RIGHT:{
				v0[2] -= fScale1; v1[2] -= fScale1; v4[2] += fScale2; v5[2] += fScale2;
				//
				v0[1] -= bScale1; v0[2] -= bScale3; v3[1] += bScale2; v3[2] -= bScale3;
				v4[1] -= bScale1; v4[2] += bScale4; v7[1] += bScale2; v7[2] += bScale4;
				break;
			}
			case MR_LEFT:{
				v0[2] -= fScale1; v1[2] -= fScale1; v4[2] += fScale2; v5[2] += fScale2;
				//
				v1[1] -= bScale1; v1[2] -= bScale3; v2[1] += bScale2; v2[2] -= bScale3;
				v5[1] -= bScale1; v5[2] += bScale4; v6[1] += bScale2; v6[2] += bScale4;
				break;
			}
			case MR_FRONT:{
				v1[1] -= fScale1; v5[1] -= fScale1; v2[1] += fScale2; v6[1] += fScale2;
				//
				v0[0] -= m * bScale4; v0[1] -= bScale1; v1[0] += m * bScale3; v1[1] -= bScale1;
				v2[0] += m * bScale3; v2[1] += bScale2; v3[0] -= m * bScale4; v3[1] += bScale2;
				break;
			}
			case MR_BACK:{
				v1[1] -= fScale1; v5[1] -= fScale1; v2[1] += fScale2; v6[1] += fScale2;
				//
				v4[0] -= m * bScale4; v4[1] -= bScale1; v5[0] += m * bScale3; v5[1] -= bScale1;
				v6[0] += m * bScale3; v6[1] += bScale2; v7[0] -= m * bScale4; v7[1] += bScale2;
				break;
			}
			case MR_TOP:{
				v1[2] -= fScale1; v2[2] -= fScale1; v5[2] += fScale2; v6[2] += fScale2;
				//
				v0[0] -= m * bScale1; v0[2] -= bScale3; v1[0] += m * bScale2; v1[2] -= bScale3;
				v4[0] -= m * bScale1; v4[2] += bScale4; v5[0] += m * bScale2; v5[2] += bScale4;
				break;
			}
			case MR_BOTTOM:{
				v1[2] -= fScale1; v2[2] -= fScale1; v5[2] += fScale2; v6[2] += fScale2;
				//
				v2[0] += m * bScale2; v2[2] -= bScale3; v3[0] -= m * bScale1; v3[2] -= bScale3;
				v6[0] += m * bScale2; v6[2] += bScale4; v7[0] -= m * bScale1; v7[2] += bScale4;
				break;
			}
		}
		//
		return addRectShape(v0, v1, v2, v3, v4, v5, v6, v7, w, h, d);
	}
	
}