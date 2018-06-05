package ebf.tim.models;

import ebf.tim.entities.GenericRailTransport;
import ebf.tim.utility.RailUtility;
import net.minecraft.util.ResourceLocation;
import fexcraft.tmt.slim.ModelBase;

/**
 * <h1>New Bogie</h1>
 * This is used to keep track of where bogies are supposed to render, and is intended to be client only.
 * @author Eternal Blue Flame
 */
public class Bogie {

    /**the vector 3 of the previously known position.*/
    private double[] prevPos = null;
    /**the current yaw rotation.*/
    public float rotationYaw;
    /**the texture defined in the registration of this.*/
    public final ResourceLocation bogieTexture;
    /**the model defined in the registration of this.*/
    public final ModelBase bogieModel;
    private double[] offset = new double[]{0,0,0};
    public double sqrtPos = 0;
    public double oldSqrtPos = 0;


    public Bogie(ResourceLocation texture, ModelBase model){
        this.bogieTexture = texture;
        this.bogieModel = model;
    }

    /**
     * <h2>handle rotation of model</h2>
     * updates the positions of the model, and then uses that data to set the rotations.
     * @param entity the GenericRailTransport to get the pitch from.
     */
    public void setPositionAndRotation(GenericRailTransport entity, double distance){
        //update positions
        offset[0] = distance;
        if(prevPos == null){
            prevPos = RailUtility.rotatePoint(offset, 0, entity.rotationYaw,0);
            prevPos[0] += entity.posX;
            prevPos[2] += entity.posZ;
            rotationYaw = entity.rotationYaw;
            oldSqrtPos = Math.sqrt(entity.posX * entity.posX) + Math.sqrt(entity.posZ * entity.posZ);
        } else if (shouldUpdate()) {
            oldSqrtPos = Math.sqrt(entity.posX * entity.posX) + Math.sqrt(entity.posZ * entity.posZ);
            double[] position = RailUtility.rotatePoint(offset, 0, entity.rotationYaw,0);
            position[0] += entity.posX;
            position[2] += entity.posZ;
            //don't update if we aren't moving fast enough.
            if ((position[0] - prevPos[0] <0.01 && position[0] - prevPos[0] >-0.01) && (position[2] - prevPos[2] <0.01 && position[2] - prevPos[2] >-0.01)){
                return;
            }
            rotationYaw = (float) Math.toDegrees(Math.atan2(position[2] - prevPos[2], position[0] - prevPos[0]));
            prevPos = position;
        } else {
            sqrtPos = Math.sqrt(entity.posX * entity.posX) + Math.sqrt(entity.posZ * entity.posZ);
        }
    }

    private boolean shouldUpdate(){
        return sqrtPos -2 > oldSqrtPos || sqrtPos + 2 <oldSqrtPos;
    }
}
