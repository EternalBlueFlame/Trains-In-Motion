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
            double z = Math.abs(points.get(s).position[2] - points.get(s+1).position[2]);

            float yaw = (float)(points.get(s).position[4] - points.get(s+1).position[4]);

                float distance = (16f)* (float)(x+z);
                if (distance == 0f){
                    continue;
                }
                float zOffset=yaw!=0?(yaw*0.0465f)*distance:0;

                points.get(s).segment.railTop[1] = new Vec3f(distance, 0, (zOffset));
                points.get(s).segment.railTop[5] = new Vec3f(distance, 0, (zOffset));


                //vec2 = RailUtility.rotatePoint(new double[]{0,0,1}, pitch, yaw,0);
                points.get(s).segment.railTop[6] = new Vec3f(distance, 0, (-zOffset));
                points.get(s).segment.railTop[2] = new Vec3f(distance, 0, (-zOffset));

                points.get(s).segment.regenModel();
        }
    }


}
