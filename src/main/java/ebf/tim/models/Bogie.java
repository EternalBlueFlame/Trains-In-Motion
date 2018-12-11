package ebf.tim.models;

import ebf.tim.entities.GenericRailTransport;
import ebf.tim.utility.RailUtility;
import fexcraft.tmt.slim.ModelBase;

import javax.annotation.Nullable;

/**
 * <h1>New Bogie</h1>
 * This is used to keep track of where bogies are supposed to render, and is intended to be client only.
 * @author Eternal Blue Flame
 */
public class Bogie {

    /**the vector 3 of the previously known position.*/
    private float[] prevPos = null;
    /**the current yaw rotation.*/
    public float rotationYaw;
    /**the model defined in the registration of this.*/
    public final ModelBase bogieModel;
    private float[] offset = new float[]{0,0,0}, position = new float[]{0,0,0};
    public double sqrtPos = 0;
    public double oldSqrtPos = 0;


    public Bogie(ModelBase model, @Nullable float[] offset){
        this.bogieModel = model;
        if(offset!=null) {
            this.offset = offset;
        }
    }

    public static Bogie[] genBogies(ModelBase[] models, float[][] offsets, float yaw){
        if(models==null){
            return null;
        }
        int modelNumber =models.length;
        if(offsets!=null && offsets.length>modelNumber){
            modelNumber=offsets.length;
        }
        Bogie[] value = new Bogie[modelNumber];
        for (int i=0;i<modelNumber;i++){
            if(models.length>i) {
                value[i] = new Bogie(models[i], offsets[i]);
            } else {
                value[i] = new Bogie(models[0], offsets[i]);
            }
            value[i].rotationYaw=yaw;
        }
        return value;
    }

    /**
     * <h2>handle rotation of model</h2>
     * updates the positions of the model, and then uses that data to set the rotations.
     * @param entity the GenericRailTransport to get the pitch from.
     */
    public void setPositionAndRotation(GenericRailTransport entity, float distance){
        //update positions
        offset[0] = distance;
        if(prevPos == null){
            prevPos = RailUtility.rotatePointF(offset[0], offset[1], offset[2], 0, entity.rotationYaw,0);
            prevPos[0] += entity.posX;
            prevPos[2] += entity.posZ;
            rotationYaw = entity.rotationYaw;
            oldSqrtPos = Math.sqrt(entity.posX * entity.posX) + Math.sqrt(entity.posZ * entity.posZ);
        } else if (shouldUpdate()) {
            oldSqrtPos = Math.sqrt(entity.posX * entity.posX) + Math.sqrt(entity.posZ * entity.posZ);
            position = RailUtility.rotatePointF(offset[0], offset[1], offset[2], 0, entity.rotationYaw,0);
            position[0] += entity.posX;
            position[2] += entity.posZ;
            //don't update if we aren't moving fast enough.
            if ((position[0] - prevPos[0] <0.01 && position[0] - prevPos[0] >-0.01) && (position[2] - prevPos[2] <0.01 && position[2] - prevPos[2] >-0.01)){
                return;
            }
            rotationYaw = RailUtility.atan2degreesf(position[2] - prevPos[2], position[0] - prevPos[0]);
            prevPos = position;
        } else {
            sqrtPos = Math.sqrt(entity.posX * entity.posX) + Math.sqrt(entity.posZ * entity.posZ);
        }
    }

    private boolean shouldUpdate(){
        return sqrtPos -2 > oldSqrtPos || sqrtPos + 2 <oldSqrtPos;
    }
}
