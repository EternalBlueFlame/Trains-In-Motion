package fexcraft.fcl.common.math;

/**
 * @author Ferdinand Calo' (FEX___96)
 */
public class TexturedVertex {
	
	public Vec3f vector;
	public float textureX, textureY;

	public TexturedVertex(Vec3f vec, float x, float y){
		vector = vec; textureX = x; textureY = y;
	}

	public TexturedVertex(float x, float y, float z, float u, float v){
		this(new Vec3f(x, y, z), u, v);
	}

	public TexturedVertex(TexturedVertex texver, float x, float y){
		vector = new Vec3f(texver.vector); textureX = x; textureY = y;
	}

	public TexturedVertex(TexturedVertex other){
		vector = new Vec3f(other.vector);
		textureX = other.textureX; textureY = other.textureY;
	}

	public TexturedVertex setTexturePosition(float x, float y){
		return new TexturedVertex(this, x, y);
	}
	
}