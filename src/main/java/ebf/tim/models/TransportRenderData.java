package ebf.tim.models;

import ebf.tim.models.tmt.ModelBase;
import ebf.tim.models.tmt.ModelRendererTurbo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eternal Blue Flame
 */
public class TransportRenderData {


    /**the base model of the entity, this holds every piece of sub-geometry and their texture mappings*/
    public List<? extends ModelBase> modelList = null;
    /**the models, textures, and other data for each bogie to render*/
    public Bogie[] bogieRenders = null;
    /**a cached list of all the animatedPart, and other geometry that just spins.
     * all the actual work related to this variable is handled in
     * @see StaticModelAnimator*/
    public List<StaticModelAnimator> animatedPart = new ArrayList<StaticModelAnimator>();
    /**a cached list of all the cargo blocks for both blocks and parts of the main model.
     * most of the actual work related to this variable is handled in
     * @see GroupedModelRender*/
    public List<GroupedModelRender> blockCargoRenders = new ArrayList<GroupedModelRender>();
    /**a cached list of all the vectors used, so we don't have to re-initialize them every frame.*/
    public double[][] animationCache = new double[4][3];
    /**a cached list of all the cubes intended to display liveries.*/
    public List<ModelRendererTurbo> liveriesSquare = new ArrayList<ModelRendererTurbo>();
    /**the value to rotate the geometry with.*/
    public float wheelPitch=0;
    public float lastWheelPitch =0;

}
