package ebf.tim.models;

import ebf.tim.models.tmt.ModelRendererTurbo;
import ebf.tim.utility.RailUtility;

import static ebf.tim.models.tmt.ModelRendererTurbo.degreesF;

/**
 * <h2>Static model animator</h2>
 * used to add animations to otherwise static .java models.
 * to add own animations override render and this, or ASM this class and add functionality before the switch.
 * @author Eternal Blue Flame
 */
public class StaticModelAnimator {

    /**tag for simple pistons, ones that move in a simple circle.*/
    public static final String tagSimplePiston = "simplepiston";
    /**tag for advanced pistons, ones that rotate and move in a simple circle.*/
    public static final String tagAdvancedPiston = "advancedpiston";
    /**tag for animatedPart, axles, and other geometry that just spins.*/
    public static final String tagSimpleRotate = "simplerotate";
    /**A copy of the original Vec6F for the model part*/
    private final float[] originalRotationValuesXYZ;
    /**a reference to the current model geometry, the one with the modifications that's actually being rendered.*/
    private ModelRendererTurbo modelRefrence;

    /**
     * used to create an instance of this class.
     * @param model a refrence to the model geometry to animate.
     */
    public StaticModelAnimator(ModelRendererTurbo model){
        this.originalRotationValuesXYZ = new float[]{model.rotationPointX, model.rotationPointY, model.rotationPointZ,
                model.rotateAngleX, model.rotateAngleY, model.rotateAngleZ};
        modelRefrence=model;
    }

    /**
     * checks if the model is something this can animate.
     * to add more tags override this and add your own checks before calling the super.
     * @param modelRefrence the geometry to check
     * @return whether or not it can be added.
     */
    public static boolean canAdd(ModelRendererTurbo modelRefrence){
        switch (modelRefrence.boxName){
            case tagAdvancedPiston:case tagSimplePiston:case tagSimpleRotate:{return true;}
            default:{return false;}
        }
    }

    /**
     * Actually animate the geometry.
     * to add more animations override this and add your own checks before calling the super.
     * @param rotationZ the rotation degree for the animation.
     */
    public void Animate(float rotationZ, double[] pistonOffset){
        switch (modelRefrence.boxName){
            //animate wheels
            case tagSimpleRotate:{
                modelRefrence.rotateAngleZ = rotationZ*0.3f;
                break;
            }
            //animate simple pistons, just rotates around center
            case tagSimplePiston:{
                double[] positionOffset = RailUtility.rotatePoint(pistonOffset,
                        rotationZ *degreesF,
                        rotationZ *degreesF, 0);

                modelRefrence.rotationPointY = originalRotationValuesXYZ[1] - (float) positionOffset[1];
                modelRefrence.rotationPointX = originalRotationValuesXYZ[0] - (float) positionOffset[0];
                break;
            }
            //animate advanced pistons, uses position and rotation
            case tagAdvancedPiston:{
                double[] positionOffset = RailUtility.rotatePoint(pistonOffset,
                        rotationZ *degreesF,
                        rotationZ *degreesF, 0);

                modelRefrence.rotationPointY = originalRotationValuesXYZ[1] - (float)(positionOffset[2]*0.5d);
                modelRefrence.rotationPointX = originalRotationValuesXYZ[0] - (float)positionOffset[0];
                modelRefrence.rotateAngleZ = originalRotationValuesXYZ[5] - (float)(positionOffset[2] * 0.05d);
            }
        }
    }


}
