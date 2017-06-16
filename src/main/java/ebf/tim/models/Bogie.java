package ebf.tim.models;

import ebf.tim.entities.GenericRailTransport;
import ebf.tim.utility.RailUtility;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

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
    int updateTicks=0;


    public Bogie(ResourceLocation texture, ModelBase model){
        this.bogieTexture = texture;
        this.bogieModel = model;
    }

    /**
     * <h2>handle rotation of model</h2>
     * updates the positions of the model, and then uses that data to set the rotations.
     * @param entity the GenericRailTransport to get the pitch from.
     */
    public void setPositionAndRotation(GenericRailTransport entity, double offset){
        updateTicks ++;
        //update positions
        if(prevPos == null){
            prevPos = RailUtility.rotatePoint(new double[]{offset,0,0}, 0, entity.rotationYaw,0);
            prevPos[0] += entity.posX;
            prevPos[2] += entity.posZ;
            rotationYaw = entity.rotationYaw;
        } else if (updateTicks %4 ==0) {
            double[] position = RailUtility.rotatePoint(new double[]{offset,0,0}, 0, entity.rotationYaw,0);
            position[0] += entity.posX;
            position[2] += entity.posZ;
            rotationYaw = (float) Math.toDegrees(Math.atan2(position[2] - prevPos[2], position[0] - prevPos[0]));
            prevPos = position;
            updateTicks =0;
        }
    }
}
