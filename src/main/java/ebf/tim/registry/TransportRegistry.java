package ebf.tim.registry;


import cpw.mods.fml.common.event.FMLInitializationEvent;
import ebf.tim.TrainsInMotion;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.entities.rollingstock.EntityPullmansPalace;
import ebf.tim.entities.rollingstock.EntityUP3Bay100TonHopper;
import ebf.tim.entities.rollingstock.EntityVATLogCar;
import ebf.tim.entities.rollingstock.EntityWellCar;
import ebf.tim.entities.trains.EntityBrigadelok080;
import ebf.tim.models.Bogie;
import ebf.tim.models.bogies.CMDBogie;
import ebf.tim.models.rollingstock.PullmansPalace;
import ebf.tim.models.rollingstock.UP3Bay100TonHopper;
import ebf.tim.models.rollingstock.VATLogCar;
import ebf.tim.models.rollingstock.Well_Car;
import ebf.tim.models.trains.Brigadelok_080;
import net.minecraft.client.model.ModelBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * <h1>Train registry</h1>
 * this class lists the data necessary to register trains and rollingstock.
 * this is not intended to be a way to get specific trains from the mod. (use the unlocalized entity name, or a class instanceof check)
 * @author Eternal Blue Flame
 */
public class TransportRegistry {
    /**
     * <h2>registry Constructor</h2>
     * because we define our own variable type for registering trains and rollingstock unlike the other registries, we have to define that and the variables here.
     * This all can mostly be ignored unless we're modifying how we registry trains.
     */
    /**the class for the entity*/
    public Class<? extends GenericRailTransport> trainClass;
    /**the main model for the entity*/
    public ModelBase model;
    /**the texture for the main model for the entity*/
    public ResourceLocation texture;
    /**the egg item for the entity*/
    public Item item;
    /**the crafting recipe for the egg item*/
    public Item[] recipe;
    /**the array of bogie models for the entity, consists of the model and it's texture. this can be null.*/
    public Bogie[] bogieModels;

    public TransportRegistry(Class<? extends GenericRailTransport> trainClass, Item item,
                             ModelBase model, ResourceLocation texture, Bogie[] bogieList, Item[] recipe){
        this.trainClass = trainClass;
        this.model = model;
        this.texture = texture;
        this.bogieModels = bogieList;
        this.item = item;
        this.recipe = recipe;
    }

    /**
     * <h2>Train register function</h2>
     * called by the main class to register the trains and rollingstock
     * @see TrainsInMotion#init(FMLInitializationEvent)
     * to add another train or rollingstock, just make another entry in the list, an example is already provided.
     * <h3>IMPORTANT:</h3>
     * Do not rely on the order of this, it will change very often.
     * List ends with a null, so be sure to check for that to see when the list ends.
     */
    public static TransportRegistry listTrains(int index){

        switch (index){

            /**
             * <h3>Train registry entries</h3>
             */

            //Brigadelok 0-8-0
            case 0:{return new TransportRegistry(
                    //the class for the entity
                    EntityBrigadelok080.class,
                    //the item for that entity
                    EntityBrigadelok080.thisItem,
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
            case 1:{return new TransportRegistry(EntityPullmansPalace.class, EntityPullmansPalace.thisItem,
                    new PullmansPalace(), URIRegistry.MODEL_TRAIN_TEXTURE.getResource("null.png"),
                    new Bogie[]{},
                    new Item[]{Items.sugar,null,null,null,null,null,null,null,null,null}
            );}

            /**
             * <h3>Freight Rollingstock registry entries</h3>
             */

            //V.A.T Log Car
            case 2:{return new TransportRegistry(EntityVATLogCar.class, EntityVATLogCar.thisItem,
                    new VATLogCar(), URIRegistry.TEXTURE_GENERIC.getResource("null.png"),
                    new Bogie[]{new Bogie(URIRegistry.TEXTURE_GENERIC.getResource("null.png"), new CMDBogie()), new Bogie(URIRegistry.TEXTURE_GENERIC.getResource("null.png"), new CMDBogie())},
                    new Item[]{Items.apple,null,null,null,null,null,null,null,null,null}
            );}

            //Well Car
            case 3:{return new TransportRegistry(EntityWellCar.class, EntityWellCar.thisItem,
                    new Well_Car(), URIRegistry.MODEL_ROLLINGSTOCK_TEXTURE.getResource("well_car.png"),
                    new Bogie[]{},
                    new Item[]{Items.iron_ingot,null,null,null,null,null,null,null,null,null}
            );}

            /**
             * <h3>Hopper Rollingstock registry entries</h3>
             */

            //Union Pacific 3 Bay 100 Ton Hopper Car.
            case 4:{return new TransportRegistry(EntityUP3Bay100TonHopper.class, EntityUP3Bay100TonHopper.thisItem,
                    new UP3Bay100TonHopper(), URIRegistry.MODEL_ROLLINGSTOCK_TEXTURE.getResource("null.png"),
                    new Bogie[]{new Bogie(URIRegistry.TEXTURE_GENERIC.getResource("null.png"), new CMDBogie()), new Bogie(URIRegistry.TEXTURE_GENERIC.getResource("null.png"), new CMDBogie())},
                    new Item[]{Items.bucket,null,null,null,null,null,null,null,null,null}
            );}


            //return null, end of list.
            default:{return null;}


        }

    }



}
