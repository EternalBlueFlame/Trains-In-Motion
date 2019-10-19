package ebf.tim.models;

import ebf.tim.entities.GenericRailTransport;
import ebf.tim.utility.DebugUtil;
import ebf.tim.utility.RailUtility;
import fexcraft.fcl.common.lang.ArrayList;
import fexcraft.tmt.slim.ModelRendererTurbo;

import java.util.List;

import static ebf.tim.utility.RailUtility.degreesF;

/**
 * <h2>Static model animator</h2>
 * used to add animations to otherwise static .java models.
 * to add own animations override render and this, or ASM this class and add functionality before the switch.
 * @author Eternal Blue Flame
 */
public class StaticModelAnimator extends AnimationBase {

    private static List<AnimationBase> customAnimators = makelist();

    private static List< AnimationBase> makelist(){
        List<AnimationBase> list = new ArrayList<>();
        list.add(new StaticModelAnimator());
        return list;
    }

    /**tag for simple pistons, ones that move in a simple circle such as wheel connectors.*/
    public static final String tagSimplePiston = "simplepiston";
    /**tag for advanced pistons, ones that rotate and move in a simple circle.*/
    public static final String tagAdvancedPiston = "advancedpiston";
    /**tag for animatedPart, axles, and other geometry that just spins.*/
    public static final String tagSimpleRotate = "simplerotate";
    /**tag for wheels, adds support for the sparks on top of what tagSimpleRotate does.*/
    public static final String tagWheel = "wheel";

    /**
     * Note that types 2, 3, and 4 are not yet implemented.
     * @param type 0/unknown:cone. 1: sphere. 2:mars. 3:siren. 4:glare
     * @param id the ID associated with the transport method to define density, scale, and color:
     * @see GenericRailTransport#getParticleData(int)
     */
    public static String tagLamp(int type, int id){
        switch (type) {
            case 1: return "lamp sphere " + id;
            case 2: return "lamp mars " + id; //todo: does that weird Y/Z oogly spinning
            case 3: return "lamp siren " + id;//todo:spins on X at a set rate
            case 4: return "lamp glare " + id;//todo: perhaps render at point and disable 3d and depth?
            default: return "lamp cone " + id;
        }
    }

    public static String tagDoor(String type, int side, boolean mirror){
        return "";
    }
    //todo: door types - swing, swing up, slide sideways, slide vertical(stair covers), slide out (retractable stairs).

    public static String tagSmoke(int id){
        return "smoke " + id;
    }
    public static String tagSteam(int id){
        return "steam " + id;
    }

    /**
     * used to create an instance of the class, use this to store any variables from the model or transport before animation starts.
     * @param model a refrence to the model geometry to animate.
     */
    public AnimationBase init(ModelRendererTurbo model, GenericRailTransport transport){
        StaticModelAnimator s = new StaticModelAnimator();
        switch (model.boxName) {
            case tagAdvancedPiston:
            case tagSimplePiston:
            case tagSimpleRotate:
            case tagWheel: {
                //DebugUtil.println("registering animated part: ", model.boxName);
                s.originalRotationValuesXYZ = new float[]{model.rotationPointX, model.rotationPointY, model.rotationPointZ,
                        model.rotateAngleX, model.rotateAngleY, model.rotateAngleZ};
                s.modelRefrence = model;
                return s;
            }
            default:{return null;}
        }
    }

    /**
     * returns true if the part being checked should be added to the animation list for the entity.
     * when overriding, do not call the super method, it's unnecessary overhead.
     * @param part the part to check
     */
    public boolean isPart(ModelRendererTurbo part){
        if(part==null || part.boxName==null){return false;}
        return RailUtility.stringContains(part.boxName,tagAdvancedPiston) ||
                RailUtility.stringContains(part.boxName,tagSimplePiston) ||
                RailUtility.stringContains(part.boxName,tagSimpleRotate) ||
                RailUtility.stringContains(part.boxName,tagWheel) ||
                RailUtility.stringContains(part.boxName,"smoke") ||
                RailUtility.stringContains(part.boxName,"steam") ||
                RailUtility.stringContains(part.boxName,"door") ||
                RailUtility.stringContains(part.boxName,"lamp");
    }


    /**
     * Actually animate the geometry.
     * to add more animations override this and add your own checks before calling the super.
     * when overriding, do not call the super method, it's unnecessary overhead.
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


    /*
    Internal methods to iterate all animators when finding support for a specific part.
     */
    static AnimationBase initPart(ModelRendererTurbo part, GenericRailTransport entity){
        if(part==null || part.boxName==null){return null;}
        for(AnimationBase b : customAnimators){
            if (b.isPart(part)) {
                return b.init(part, entity);
            }
        }
        return null;
    }

    static boolean checkAnimators(ModelRendererTurbo part){
    for (AnimationBase animator : customAnimators){
            if (animator != null && animator.isPart(part)) {
                return true;
            }
        }
        return false;
    }

}
