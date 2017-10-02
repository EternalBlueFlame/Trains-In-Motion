package ebf.tim.models.rails;

import ebf.tim.models.tmt.Vec3d;

public class RailPointRenderData {
    public double[] position;//a vec 6 of the x/y/z/pitch/yaw/roll
    public RailPointRenderData NextPosition;//set by this when placed, defined by the first nearby block.
    public RailPointRenderData LastPosition;//defined as/by the other when defining the nextPosition
    public char railType;//the rail identifier

    private static final int textureX = 64;
    private static final int textureY = 32;

}
