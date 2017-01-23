package trains.registry;


import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.model.ModelBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import trains.entities.GenericRailTransport;
import trains.entities.rollingstock.EntityPullmansPalace;
import trains.entities.trains.EntityBrigadelok080;
import trains.models.rollingstock.PullmansPalace;
import trains.models.trains.Brigadelok_080;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
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
    public ModelBase bogieModel;
    public ResourceLocation bogieTexture;
    public String entityWorldName; //Note: Must be all lowercase
    public Item item;
    public List<Item> recipe = new ArrayList<Item>();

    private TrainRegistry(Class<? extends GenericRailTransport> trainClass, Item item, String entityWorldName,
                          ModelBase model, ResourceLocation texture, @Nullable ModelBase bogieModel, @Nullable ResourceLocation bogieTexture, List<Item> recipe){
        this.trainClass = trainClass;
        this.entityWorldName = entityWorldName;
        this.model = model;
        this.texture = texture;
        this.bogieModel = bogieModel;
        this.bogieTexture = bogieTexture;
        this.item = item;
        this.recipe = recipe;
    }

    /**
     * <h2>Train register function</h2>
     * called by the main class to register the trains and rollingstock
     * to add another train or rollingstock, just make another entry in the list, an example is already provided.
     * @see trains.TrainsInMotion#init(FMLInitializationEvent)
     */
    public static List<TrainRegistry> listTrains(){
        List<TrainRegistry> output = new ArrayList<TrainRegistry>();

        output.add(new TrainRegistry(EntityBrigadelok080.class, EntityBrigadelok080.thisItem, "entityfirsttrain",
                new Brigadelok_080(), URIRegistry.MODEL_TRAIN_TEXTURE.getResource("null.png"),
                null, null,
                new ArrayList<Item>(Arrays.asList(new Item[]{Items.blaze_rod,null,null,null,null,null,null,null,null,null}))
        ));


        output.add(new TrainRegistry(EntityPullmansPalace.class, EntityPullmansPalace.thisItem, "entitypullmanspalace",
                new PullmansPalace(), URIRegistry.MODEL_TRAIN_TEXTURE.getResource("null.png"),
                null, null,
                new ArrayList<Item>(Arrays.asList(new Item[]{Items.sugar,null,null,null,null,null,null,null,null,null}))
        ));
        return output;

    }
}
