package trains.registry;

import trains.TrainsInMotion;
import trains.entities.trains.FirstTrain;

public class EntityRegistry {
    public EntityRegistry(){}

    public void registerEntities(){
        //register the entity, unlike items, we don't have to initialize it first.
        //However we will have to create a new ID for every entity, we'll store it in an array to save space and simplify it a bit
        int[] id = new int[]{
                cpw.mods.fml.common.registry.EntityRegistry.findGlobalUniqueEntityId()

        };

        //this will register just the first train, we'll need another id and to do this again for every other entity
        cpw.mods.fml.common.registry.EntityRegistry.registerGlobalEntityID(FirstTrain.class, "entityfirsttrain", id[0]);
        cpw.mods.fml.common.registry.EntityRegistry.registerModEntity(FirstTrain.class, "entityfirsttrain", id[0], TrainsInMotion.instance, 64, 1, true);
    }
}
