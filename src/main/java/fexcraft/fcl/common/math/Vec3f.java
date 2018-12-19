package fexcraft.fcl.common.math;

import javax.annotation.Nullable;

/**
 * @author Ferdinand
 * Based off the (Minecraft) Vanilla Vec3d;
 */
public class Vec3f {
	
    public float xCoord;
    public float yCoord;
    public float zCoord;
    
    public Vec3f(){ xCoord = yCoord = zCoord = 0; }

    public Vec3f(float x, float y, float z){
        if(x == -0.0F){ x = 0.0F; }
        if(y == -0.0F){ y = 0.0F; }
        if(z == -0.0F){ z = 0.0F; }
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }

    public Vec3f(Vec3f vector){
        this(vector.xCoord, vector.yCoord, vector.zCoord);
    }

    /*public Vec3f(Vec3i vector){
        this(vector.getX(), vector.getY(), vector.getZ());
    }
    
    public Vec3f(Vec3d vector){
    	this(vector.x, vector.y, vector.z);
    }

	public Vec3f(Vec3i pos, boolean center){
		this(pos); if(center){ xCoord += 0.5f; yCoord += 0.5f; zCoord += 0.5f; }
	}*/
    
    public Vec3f(double xVec, double yVec, double zVec) {
		this((float)xVec, (float)yVec, (float)zVec);
	}

	public Vec3f subtractReverse(Vec3f vec){
        return new Vec3f(vec.xCoord - this.xCoord, vec.yCoord - this.yCoord, vec.zCoord - this.zCoord);
    }
    
    public Vec3f normalize(){
        double d0 = Math.sqrt(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
        return d0 < 1.0E-4D ? new Vec3f() : new Vec3f(this.xCoord / d0, this.yCoord / d0, this.zCoord / d0);
    }

    public float dotProduct(Vec3f vec){
        return this.xCoord * vec.xCoord + this.yCoord * vec.yCoord + this.zCoord * vec.zCoord;
    }
    
    public Vec3f crossProduct(Vec3f vec){
        return new Vec3f(this.yCoord * vec.zCoord - this.zCoord * vec.yCoord, this.zCoord * vec.xCoord - this.xCoord * vec.zCoord, this.xCoord * vec.yCoord - this.yCoord * vec.xCoord);
    }

    public Vec3f subtract(Vec3f vec){
        return this.subtract(vec.xCoord, vec.yCoord, vec.zCoord);
    }

    public Vec3f subtract(float x, float y, float z){
        return this.addVector(-x, -y, -z);
    }

    public Vec3f add(Vec3f vec){
        return this.addVector(vec.xCoord, vec.yCoord, vec.zCoord);
    }
    
    public Vec3f addVector(float x, float y, float z){
        return new Vec3f(this.xCoord + x, this.yCoord + y, this.zCoord + z);
    }
    
    public float distanceTo(Vec3f vec){
        float d0 = vec.xCoord - this.xCoord;
        float d1 = vec.yCoord - this.yCoord;
        float d2 = vec.zCoord - this.zCoord;
        return (float)Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }
    
    public float squareDistanceTo(Vec3f vec){
        float d0 = vec.xCoord - this.xCoord;
        float d1 = vec.yCoord - this.yCoord;
        float d2 = vec.zCoord - this.zCoord;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public float squareDistanceTo(float xIn, float yIn, float zIn){
        float d0 = xIn - this.xCoord;
        float d1 = yIn - this.yCoord;
        float d2 = zIn - this.zCoord;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public Vec3f scale(float scale){
        return new Vec3f(this.xCoord * scale, this.yCoord * scale, this.zCoord * scale);
    }
    
    public float lengthVector(){
        return (float)Math.sqrt(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
    }

    public float lengthSquared(){
        return this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord;
    }
    
    @Nullable
    public Vec3f getIntermediateWithXValue(Vec3f vec, float x){
        float d0 = vec.xCoord - this.xCoord;
        float d1 = vec.yCoord - this.yCoord;
        float d2 = vec.zCoord - this.zCoord;
        if(d0 * d0 < 1.0000000116860974E-7D){
            return null;
        }
        else{
            float d3 = (x - this.xCoord) / d0;
            return d3 >= 0.0F && d3 <= 1.0F ? new Vec3f(this.xCoord + d0 * d3, this.yCoord + d1 * d3, this.zCoord + d2 * d3) : null;
        }
    }
    
    @Nullable
    public Vec3f getIntermediateWithYValue(Vec3f vec, float y){
        float d0 = vec.xCoord - this.xCoord;
        float d1 = vec.yCoord - this.yCoord;
        float d2 = vec.zCoord - this.zCoord;
        if(d1 * d1 < 1.0000000116860974E-7D){
            return null;
        }
        else{
            float d3 = (y - this.yCoord) / d1;
            return d3 >= 0.0F && d3 <= 1.0F ? new Vec3f(this.xCoord + d0 * d3, this.yCoord + d1 * d3, this.zCoord + d2 * d3) : null;
        }
    }

    /**
     * Returns a new vector with z value equal to the second parameter, along the line between this vector and the
     * passed in vector, or null if not possible.
     */
    @Nullable
    public Vec3f getIntermediateWithZValue(Vec3f vec, float z){
        float d0 = vec.xCoord - this.xCoord;
        float d1 = vec.yCoord - this.yCoord;
        float d2 = vec.zCoord - this.zCoord;
        if(d2 * d2 < 1.0000000116860974E-7D){
            return null;
        }
        else{
            float d3 = (z - this.zCoord) / d2;
            return d3 >= 0.0F && d3 <= 1.0F ? new Vec3f(this.xCoord + d0 * d3, this.yCoord + d1 * d3, this.zCoord + d2 * d3) : null;
        }
    }

    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        else if(!(obj instanceof Vec3f)){
            return false;
        }
        else{
            Vec3f vec3d = (Vec3f)obj;
            return Float.compare(vec3d.xCoord, this.xCoord) != 0 ? false : (Float.compare(vec3d.yCoord, this.yCoord) != 0 ? false : Float.compare(vec3d.zCoord, this.zCoord) == 0);
        }
    }

    public int hashCode(){
        long j = Float.floatToIntBits(this.xCoord);
        int i = (int)(j ^ j >>> 32);
        j = Float.floatToIntBits(this.yCoord);
        i = 31 * i + (int)(j ^ j >>> 32);
        j = Float.floatToIntBits(this.zCoord);
        i = 31 * i + (int)(j ^ j >>> 32);
        return i;
    }

    //public String toString(){ return "[" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + "]"; }
	
	@Override
	public String toString(){
		return String.format("Vec3f[ %s, %s, %s ]", xCoord, yCoord, zCoord);
	}

	public Vec3f middle(Vec3f target){
		return new Vec3f((xCoord + target.xCoord) * 0.5, (yCoord + target.yCoord) * 0.5, (zCoord + target.zCoord) * 0.5);
	}
	
	//based on fvtm rail entity stuff

	public Vec3f distance(Vec3f dest, float am){
		Vec3f vec = new Vec3f((xCoord + dest.xCoord) * 0.5, (yCoord + dest.yCoord) * 0.5, (zCoord + dest.zCoord) * 0.5);
    	vec = direction(vec.xCoord - xCoord, vec.yCoord - yCoord, vec.zCoord - zCoord);
		return new Vec3f(xCoord + (vec.xCoord * am), yCoord + (vec.yCoord * am), zCoord + (vec.zCoord * am));
	}
	
    public double length(){
        return Math.sqrt(xCoord * xCoord + yCoord * yCoord + zCoord * zCoord);
    }
    
    public static double length(float... arr){
        return Math.sqrt(arr[0] * arr[0] + arr[1] * arr[1] + arr[2] * arr[2]);
    }
    
    public static double length(Vec3f vec){
        return Math.sqrt(vec.xCoord * vec.xCoord + vec.yCoord * vec.yCoord + vec.zCoord * vec.zCoord);
    }
    
    /*public double distanceTo(Vec3f other){
        return length(other.xCoord - xCoord, other.yCoord - yCoord, other.zCoord - zCoord);
    }*/
    
    public static Vec3f direction(float... arr){
    	double l = length(arr[0], arr[1], arr[2]); return new Vec3f(arr[0] / l, arr[1] / l, arr[2] / l);
    }
    
    public static Vec3f direction(Vec3f vec){
    	double l = length(vec.xCoord, vec.yCoord, vec.zCoord); return new Vec3f(vec.xCoord / l, vec.yCoord / l, vec.zCoord / l);
    }

	public Vec3f cross(Vec3f other){
		return new Vec3f(yCoord * other.zCoord - this.zCoord * other.yCoord,
			other.xCoord * this.zCoord - other.zCoord * this.xCoord,
			this.xCoord * other.yCoord - this.yCoord * other.xCoord);
	}

	public float dot(Vec3f other){
		return this.xCoord * other.xCoord + this.yCoord * other.yCoord + this.zCoord * other.zCoord;
	}

	public Vec3f normalize(Vec3f dest){
		float len = (float)length(); return dest == null ? new Vec3f(xCoord / len, yCoord / len, zCoord / len) : dest.set(xCoord / len, yCoord / len, zCoord / len);
	}

	private Vec3f set(float f, float g, float h){
		this.xCoord = f; this.yCoord = g; this.zCoord = h; return this;
	}
    
}