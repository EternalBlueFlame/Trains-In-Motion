package trains.utility;

import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import trains.entities.GenericRailTransport;

/**
 * <h1>New Bogie</h1>
 * This is used to keep track of where bogies are supposed to render, and is intended to be client only.
 * @author Eternal Blue Flame
 */
public class Bogie {
    public double[] pos = new double[]{0,0,0};

    public double[] prevPos = new double[]{0,0,0};

    public float rotationYaw;
    public float rotationPitch;

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
        pos = position;
        if(prevPos == new double[]{0,0,0}){
            prevPos = pos;
            rotationPitch = entity.rotationPitch;
            rotationYaw = entity.rotationYaw;
        } else {
            //update rotations
            if (Math.abs(prevPos[0] - pos[0]) > 0.1) {
                rotationYaw = (float) Math.toDegrees(Math.atan2(
                        prevPos[2] - pos[2],
                        prevPos[0] - pos[0]));
            } else if (Math.abs(prevPos[1] - pos[1]) > 0.1) {
                rotationYaw = (float) Math.toDegrees(Math.atan2(
                        prevPos[2] - pos[2],
                        prevPos[0] - pos[0]));
            } else if (Math.abs(prevPos[2] - pos[2]) > 0.1) {
                rotationYaw = (float) Math.toDegrees(Math.atan2(
                        prevPos[2] - pos[2],
                        prevPos[0] - pos[0]));
            }
            rotationPitch = entity.rotationPitch;
        }
    }
}
