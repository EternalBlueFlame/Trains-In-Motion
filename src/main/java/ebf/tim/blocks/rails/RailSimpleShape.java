package ebf.tim.blocks.rails;

import ebf.tim.utility.DebugUtil;
import fexcraft.tmt.slim.Vec3f;

public class RailSimpleShape {

    private Vec3f[] path = new Vec3f[0];
    int tieCount=0;

    public RailSimpleShape setSegments(int s){
        tieCount=s;
        return this;
    }
    public float getPathLength(){
        if(path.length<1){return 0;}
        float length =0;

        for (int i=0; i<path.length-1;i++){
            length+=new Vec3f(path[i]).distance(path[i+1],1).length();
        }
        return length;

    }

    public Vec3f[] getPath(){return path;}

    public RailSimpleShape setPoint(int point, float x, float y, float z){
        path[point].xCoord=x+0.5f;
        path[point].yCoord=y;
        path[point].zCoord=z+0.5f;
        return this;
    }

    public RailSimpleShape setPoint(int point, Vec3f value){
        path[point] = value.add(normalize());
        return this;
    }

    RailSimpleShape setPointX(int point, float x){
        path[point].xCoord=x+0.5f;
        return this;
    }
    RailSimpleShape setPointY(int point, float y){
        path[point].yCoord=y;
        return this;
    }
    RailSimpleShape setPointZ(int point, float z){
        path[point].zCoord=z+0.5f;
        return this;
    }

    public RailSimpleShape addPoints(Vec3f[] points){
        Vec3f[] length = new Vec3f[path.length+points.length];
        int i=0;
        for(;i<path.length;i++){
            length[i]=path[i].add(normalize());
        }
        for(int ii=0; ii<points.length;ii++){
            length[i+ii]=points[ii].add(normalize());
        }
        path=length;
        return this;
    }

    private static Vec3f normalize(){return new Vec3f(0.5f,0,0.5f);}

}
