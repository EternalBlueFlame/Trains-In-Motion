package ebf.tim.utility;

import fexcraft.tmt.slim.Vec3f;

public class Vec5f extends Vec3f {
    public float u,v;


    public Vec5f(double x, double y, double z, float u, float v){
        super(x,y,z);
        this.u=u;
        this.v=v;
    }

    public Vec5f(float x, float y, float z, float u, float v){
        super(x,y,z);
        this.u=u;
        this.v=v;
    }

    public void setUV(float u, float v){
        this.u=u;
        this.v=v;
    }

    public Vec5f crossProduct(Vec5f o){
        return new Vec5f(xCoord*o.xCoord,yCoord*o.yCoord,zCoord*o.zCoord,u*o.u,v*o.v);
    }
}
