package ebf.tim.models;

import ebf.tim.models.tmt.ModelRendererTurbo;

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
    /**A copy of the original geometry before modifications*/
    private final ModelRendererTurbo originalGeometryCopy;
    /**a reference to the current model geometry, the one with the modifications that's actually being rendered.*/
    private ModelRendererTurbo modelRefrence;

    /**
     * used to create an instance of this class.
     * @param model a refrence to the model geometry to animate.
     */
    public StaticModelAnimator(ModelRendererTurbo model){
        this.originalGeometryCopy = model;
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
     * @param positionOffset the x/y/z of the position to move the geometry to in the animation.
     */
    public void Animate(float rotationZ, double[] positionOffset){
        switch (originalGeometryCopy.boxName){
            //animate wheels
            case tagSimpleRotate:{
                modelRefrence.rotateAngleZ = rotationZ;
                break;
            }
            //animate simple pistons, just rotates around center
            case tagSimplePiston:{
                modelRefrence.rotationPointY = originalGeometryCopy.rotationPointY - (float) positionOffset[1];
                modelRefrence.rotationPointX = originalGeometryCopy.rotationPointY - (float) positionOffset[0];
                break;
            }
            //animate advanced pistons, uses position and rotation
            case tagAdvancedPiston:{
                modelRefrence.rotationPointY = originalGeometryCopy.rotationPointY - (float)(positionOffset[2]*0.5d);
                modelRefrence.rotationPointX = originalGeometryCopy.rotationPointX - (float)positionOffset[0];
                modelRefrence.rotateAngleZ = originalGeometryCopy.rotateAngleZ - (float)(positionOffset[2] * 0.05d);
            }
        }
    }


}
