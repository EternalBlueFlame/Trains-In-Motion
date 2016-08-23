package trains.registry;

import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.entities.trains.FirstTrain;

public class EntityRegistry {
    public EntityRegistry(){}

    public void registerEntities(){
        //register the entity, unlike items, we don't have to initialize it first.

        //this will register just the first train, we'll need another id and to do this again for every other entity
        cpw.mods.fml.common.registry.EntityRegistry.registerGlobalEntityID(FirstTrain.class, "entityfirsttrain", 3);
        cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(FirstTrain.class, "entityfirsttrain", 3, TrainsInMotion.instance, 64, 1, true);
    }
}
