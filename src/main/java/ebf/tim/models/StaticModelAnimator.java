package ebf.tim.models;

import ebf.tim.entities.GenericRailTransport;
import ebf.tim.utility.RailUtility;
import fexcraft.tmt.slim.ModelRendererTurbo;

import static ebf.tim.utility.RailUtility.degreesF;

/**
 * <h2>Static model animator</h2>
 * used to add animations to otherwise static .java models.
 * to add own animations override render and this, or ASM this class and add functionality before the switch.
 * @author Eternal Blue Flame
 */
public class StaticModelAnimator extends AnimationBase {

    /**tag for simple pistons, ones that move in a simple circle such as wheel connectors.*/
    public static final String tagSimplePiston = "simplepiston";
    /**tag for advanced pistons, ones that rotate and move in a simple circle.*/
    public static final String tagAdvancedPiston = "advancedpiston";
    /**tag for animatedPart, axles, and other geometry that just spins.*/
    public static final String tagSimpleRotate = "simplerotate";
    /**tag for wheels, adds support for the sparks on top of what tagSimpleRotate does.*/
    public static final String tagWheel = "wheel";

    public static String tagLamp(String type, float scale, int id){
        return "lamp "+ type+" "+scale+" "+id;
    }

    public static String tagDoor(String type, int side, boolean mirror){
        return "";
    }

    public static String tagSmoke(int id){
        return "smoke " + id;
    }

    /**
     * used to create an instance of this class.
     * @param model a refrence to the model geometry to animate.
     */
    public StaticModelAnimator init(ModelRendererTurbo model){
        if(model==null || model.boxName==null){return null;}
        switch (model.boxName) {
            case tagAdvancedPiston:
            case tagSimplePiston:
            case tagSimpleRotate:
            case tagWheel: {
                this.originalRotationValuesXYZ = new float[]{model.rotationPointX, model.rotationPointY, model.rotationPointZ,
                        model.rotateAngleX, model.rotateAngleY, model.rotateAngleZ};
                modelRefrence = model;
                return this;
            }
            default:{return null;}
        }
    }

    /**
     * Actually animate the geometry.
     * to add more animations override this and add your own checks before calling the super.
     * @param rotationZ the rotation degree for the animation.
     */
    public void animate(float rotationZ, float[] pistonOffset, GenericRailTransport host){
        if(modelRefrence ==null || modelRefrence.boxName ==null){
            return;
        }
        switch (modelRefrence.boxName){
            //animate wheels
            case tagSimpleRotate: case tagWheel:{
                modelRefrence.rotateAngleZ = rotationZ*-0.5f;
                break;
            }
            //animate simple pistons, just rotates around center
            case tagSimplePiston:{
                float[] positionOffset = RailUtility.rotatePointF(pistonOffset[0],pistonOffset[1],pistonOffset[2],
                        rotationZ *degreesF,
                        rotationZ *degreesF, 0);

                modelRefrence.rotationPointY = originalRotationValuesXYZ[1] -  positionOffset[1];
                modelRefrence.rotationPointX = originalRotationValuesXYZ[0] - positionOffset[0];
                break;
            }
            //animate advanced pistons, uses position and rotation
            case tagAdvancedPiston:{
                float[] positionOffset = RailUtility.rotatePointF(pistonOffset[0],pistonOffset[1],pistonOffset[2],
                        rotationZ *degreesF,
                        rotationZ *degreesF, 0);

                modelRefrence.rotateAngleZ = originalRotationValuesXYZ[5] - (float)(positionOffset[2] * -0.1d);
            }
        }
    }


}
