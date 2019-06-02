package ebf.tim.utility;

import fexcraft.tmt.slim.Vec3f;

public class Vec5f extends Vec3f {
    public float u,v;

    public Vec5f(float x, float y, float z, float u, float v){
        super(x,y,z);
        this.u=u;
        this.v=v;
    }
}
