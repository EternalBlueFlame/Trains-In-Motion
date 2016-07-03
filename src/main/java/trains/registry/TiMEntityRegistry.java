package trains.registry;

import cpw.mods.fml.common.registry.EntityRegistry;
import trains.TrainsInMotion;
import trains.entities.trains.FirstTrain;

public class TiMEntityRegistry {
    public TiMEntityRegistry(){}

    public void registerEntities(){
        //register the entity, unlike items, we don't have to initialize it first.
        //However we will have to create a new ID for every entity, we'll store it in an array to save space and simplify it a bit
        int[] id = new int[]{
                EntityRegistry.findGlobalUniqueEntityId()

        };

        //this will register just the first train, we'll need another id and to do this again for every other entity
        EntityRegistry.registerGlobalEntityID(FirstTrain.class, "entityfirsttrain", id[0]);
        EntityRegistry.registerModEntity(FirstTrain.class, "entityfirsttrain", id[0], TrainsInMotion.instance, 64, 1, true);
    }
}
