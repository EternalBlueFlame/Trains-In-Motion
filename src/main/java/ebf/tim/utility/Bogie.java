package ebf.tim.utility;

import ebf.tim.entities.GenericRailTransport;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

/**
 * <h1>New Bogie</h1>
 * This is used to keep track of where bogies are supposed to render, and is intended to be client only.
 * @author Eternal Blue Flame
 */
public class Bogie {

    public double[] prevPos = null;

    public float rotationYaw;

    public ResourceLocation bogieTexture;
    public ModelBase bogieModel;


    public Bogie(ResourceLocation texture, ModelBase model){
        this.bogieTexture = texture;
        this.bogieModel = model;
    }

    /**
     * <h2>handle rotation of model</h2>
     * updates the positions of the model, and then uses that data to set the rotations.
     * @param entity the GenericRailTransport to get the pitch from.
     */
    public void setPositionAndRotation(double[] position, GenericRailTransport entity){
        //update positions
        if(prevPos == null){
            prevPos = position;
            rotationYaw = entity.rotationYaw;
        } else {
            //update rotations
            if (Math.abs(prevPos[0] - position[0]) > 0.01 || Math.abs(prevPos[1] - position[1]) > 0.01 || Math.abs(prevPos[2] - position[2]) > 0.01) {
                rotationYaw = (float) Math.toDegrees(Math.atan2(
                        prevPos[2] - position[2],
                        prevPos[0] - position[0]));
            }
        }
    }
}
