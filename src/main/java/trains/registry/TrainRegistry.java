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
	
	private static List<TrainRegistryEntry> entries = new ArrayList<TrainRegistryEntry>();
	
	public static void addEntry(Class<? extends Entity> clazz, String worldname, String model, String texture, @Nullable String bogie_model, @Nullable String bogie_texture){
		entries.add(new TrainRegistryEntry(clazz, worldname, model, texture, bogie_model, bogie_texture));
	}
	
    public static List<TrainRegistryEntry> getList(){
    	
        entries.add(new TrainRegistryEntry(FirstTrain.class, "entityfirsttrain","060e2.obj", "null.png", null, null));
        //TODO remove later when required file load/read method exists
        
        return entries;

    }
    
    public static class TrainRegistryEntry {
        public Class<? extends Entity> trainClass;
        public String model;
        public String texture;
        public String bogieModel;
        public String bogieTexture;
        public String entityWorldName; //Note: Must be all lowercase
        
        private TrainRegistryEntry(Class<? extends Entity> trainClass, String entityWorldName, String model, String texture, @Nullable String bogieModel, @Nullable String bogieTexture){
            this.trainClass = trainClass;
            this.entityWorldName = entityWorldName;
            this.model = model;
            this.texture = texture;
            this.bogieModel = bogieModel;
            this.bogieTexture = bogieTexture;
        }
    }
    
}
