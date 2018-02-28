package ebf.tim.models.rails;

import tmt.Vec3f;

import java.util.List;

public class RailPointRenderData {
    public double[] position;//a vec 6 of the x/y/z/pitch/yaw/roll
    public int[] railGauge;//the rail identifier
    public ModelRailSegment segment = new ModelRailSegment(0);

    private static final int textureX = 64;
    private static final int textureY = 32;


    public RailPointRenderData(){}


    public static void setRotation(List<RailPointRenderData> points){
        for (int s =0; s< points.size()-1; s++) {

            double x = Math.abs(points.get(s).position[0] - points.get(s+1).position[0]);
            double y = Math.abs(points.get(s).position[1] - points.get(s+1).position[1]);
            double z = Math.abs(points.get(s).position[2] - points.get(s+1).position[2]);

                float distance = (16f)* (float)((Math.abs(x)+Math.abs(z) + Math.abs(y)));
                if (distance == 0f){
                    continue;
                }

                points.get(s).segment.railTop[1] = new Vec3f(distance, 0, 0);
                points.get(s).segment.railTop[5] = new Vec3f(distance, 0, 0);


                //vec2 = RailUtility.rotatePoint(new double[]{0,0,1}, pitch, yaw,0);
                points.get(s).segment.railTop[6] = new Vec3f(distance, 0, 0);
                points.get(s).segment.railTop[2] = new Vec3f(distance, 0, 0);

                points.get(s).segment.regenModel();
        }
    }


}
