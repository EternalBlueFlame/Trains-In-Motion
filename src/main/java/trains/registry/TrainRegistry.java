package trains.registry;


import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.model.ModelBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import trains.entities.GenericRailTransport;
import trains.entities.rollingstock.EntityPullmansPalace;
import trains.entities.rollingstock.EntityVATLogCar;
import trains.entities.rollingstock.EntityWellCar;
import trains.entities.trains.EntityBrigadelok080;
import trains.models.bogies.CMDBogie;
import trains.models.rollingstock.PullmansPalace;
import trains.models.rollingstock.VATLogCar;
import trains.models.rollingstock.Well_Car;
import trains.models.trains.Brigadelok_080;
import trains.utility.Bogie;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Train registry</h1>
 * this class lists all the trains and rollingstock provided by this mod.
 * If you need a reference to one of those, you have to call it from this class.
 */
public class TrainRegistry {
    /**
     * <h2>registry Constructor</h2>
     * because we define our own variable type for registering trains and rollingstock unlike the other registries, we have to define that and the variables here.
     * This all can mostly be ignored unless we're modifying how we registry trains.
     */
    public Class<? extends GenericRailTransport> trainClass;
    public ModelBase model;
    public ResourceLocation texture;
    public String entityWorldName; //Note: Must be all lowercase
    public Item item;
    public Item[] recipe;
    public Bogie[] bogieModels;

    public TrainRegistry(Class<? extends GenericRailTransport> trainClass, Item item, String entityWorldName,
                          ModelBase model, ResourceLocation texture, Bogie[] bogieList, Item[] recipe){
        this.trainClass = trainClass;
        this.entityWorldName = entityWorldName;
        this.model = model;
        this.texture = texture;
        this.bogieModels = bogieList;
        this.item = item;
        this.recipe = recipe;
    }

    /**
     * <h2>Train register function</h2>
     * called by the main class to register the trains and rollingstock
     * to add another train or rollingstock, just make another entry in the list, an example is already provided.
     * @see trains.TrainsInMotion#init(FMLInitializationEvent)
     * <h3>IMPORTANT:</h3>
     * Do not rely on the order of this, it will change very often.
     * List ends with a null, so be sure to check for that to see when the list ends.
     */
    public static TrainRegistry listTrains(int index){

        switch (index){

            /**
             * <h3>Train registry entries</h3>
             */

            //Brigadelok 0-8-0
            case 0:{return new TrainRegistry(
                    //the class for the entity
                    EntityBrigadelok080.class,
                    //the item for that entity
                    EntityBrigadelok080.thisItem,
                    //the entity world name. Best to be lowercase.
                    "entitybrigadelok",
                    //the model and texture for the entity
                    new Brigadelok_080(), URIRegistry.MODEL_TRAIN_TEXTURE.getResource("null.png"),
                    //the bogies for the entity (The V.A.T Log Car is a better example of this)
                    new Bogie[]{},
                    //the recipe to craft the item
                    new Item[]{Items.coal,null,null,null,null,null,null,null,null,null}
            );}

            /**
             * <h3>Passenger Rollingstock registry entries</h3>
             */

            //pullman's palace
            case 1:{return new TrainRegistry(EntityPullmansPalace.class, EntityPullmansPalace.thisItem, "entitypullmanspalace",
                    new PullmansPalace(), URIRegistry.MODEL_TRAIN_TEXTURE.getResource("null.png"),
                    new Bogie[]{},
                    new Item[]{Items.sugar,null,null,null,null,null,null,null,null,null}
            );}

            /**
             * <h3>Freight Rollingstock registry entries</h3>
             */

            //V.A.T Log Car
            case 2:{return new TrainRegistry(EntityVATLogCar.class, EntityVATLogCar.thisItem, "entityvatlogcar",
                    new VATLogCar(), URIRegistry.TEXTURE_GENERIC.getResource("null.png"),
                    new Bogie[]{new Bogie(URIRegistry.TEXTURE_GENERIC.getResource("null.png"), new CMDBogie()), new Bogie(URIRegistry.TEXTURE_GENERIC.getResource("null.png"), new CMDBogie())},
                    new Item[]{Items.apple,null,null,null,null,null,null,null,null,null}
            );}

            //Well Car
            case 3:{return new TrainRegistry(EntityWellCar.class, EntityWellCar.thisItem, "entityvatwellcar",
                    new Well_Car(), URIRegistry.MODEL_ROLLINGSTOCK_TEXTURE.getResource("well_car.png"),
                    new Bogie[]{},
                    new Item[]{Items.apple,null,null,null,null,null,null,null,null,null}
            );}


            //return null, end of list.
            default:{return null;}


        }

    }



}
