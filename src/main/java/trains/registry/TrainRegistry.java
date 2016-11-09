package trains.registry;


import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.entity.Entity;
import trains.entities.trains.FirstTrain;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Train registry</h2>
 * this class lists all the trains and rollingstock provided by this mod.
 * If you need a reference to one of those, you have to call it from this class.
 */
public class TrainRegistry {
    /**
     * <h2>registry Constructor</h2>
     * because we define our own variable type for registering trains and rollingstock unlike the other registries, we have to define that and the variables here.
     */
    public Class<? extends Entity> trainClass;
    public String model;
    public String texture;
    public String bogieModel;
    public String bogieTexture;
    public String entityWorldName; //Note: Must be all lowercase
    private TrainRegistry(Class<? extends Entity> trainClass, String entityWorldName, String model, String texture, @Nullable String bogieModel, @Nullable String bogieTexture){
        this.trainClass = trainClass;
        this.entityWorldName = entityWorldName;
        this.model = model;
        this.texture = texture;
        this.bogieModel = bogieModel;
        this.bogieTexture = bogieTexture;
    }


    /**
     * <h2>Train register function</h2>
     * called by the main class to register the trains and rollingstock
     * @see trains.TrainsInMotion#init(FMLInitializationEvent)
     */
    public static List<TrainRegistry> listTrains(){
        List<TrainRegistry> output = new ArrayList<TrainRegistry>();

        output.add(new TrainRegistry(FirstTrain.class, "entityfirsttrain","060e2.obj", "null.png", null, null));

        return output;

    }
}
