package ebf.tim.utility;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntityTrainCore;
import fexcraft.tmt.slim.ModelBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface GenericTransportData {
    /*
     * <h2>Inherited variables</h2>
     * these functions are overridden by classes that extend GenericRailTransport, or EntityTrainCore so that way the values can be changed indirectly.
     */

    /*
    <h1>Bogies and models</h1>
    */

    /**returns the x/y/z offset each bogie should render at, with 0 being the entity center, in order with getBogieModels
     * example:
     * return new float[][]{{x1,y1,z1},{x2,y2,z2}, etc...};
     * may return null.*/
    @SideOnly(Side.CLIENT)
    float[][] bogieModelOffsets();

    /**returns a list of models to be used for the bogies
     * example:
     * return new ModelBase[]{new MyModel1(), new myModel2(), etc...};
     * may return null. */
    @SideOnly(Side.CLIENT)
    ModelBase[] bogieModels();

    /**defines the points that the entity uses for path-finding and rotation, with 0 being the entity center.
     * Usually the point where the front and back bogies would connect to the transport.
     * Or the center of the frontmost and backmost wheel if there are no bogies.
     * example:
     * return new float{2f, -1f};
     * may not return null*/
    float[] bogieLengthFromCenter();

    /**defines the radius from center in microblocks that the pistons animate, if there are any.*/
    float getPistonOffset();

    /**defines the scale to render the model at. Default is 0.0625*/
    float getRenderScale();

    /**returns the x/y/z offset each model should render at, with 0 being the entity center, in order with getModels
     * example:
     * return new float[][]{{x1,y1,z1},{x2,y2,z2}, etc...};
     * may return null.*/
    @SideOnly(Side.CLIENT)
    float[][] modelOffsets();

    /**event is to add skins for the model to the skins registry on mod initialization.
     * this function can be used to register multiple skins, one after another.
     * example:
     * SkinRegistry.addSkin(this.class, MODID, "folder/mySkin.png", new int[][]{{oldHex, newHex},{oldHex, newHex}, etc... }, displayName, displayDescription);
     * the int[][] for hex recolors may be null.
     * hex values use "0x" in place of "#"
     * "0xff00aa" as an example.
     * the first skin added to the registry for a transport class will be the default
     * additionally the addSkin function may be called from any other class at any time.
     * the registerSkins method is only for organization and convenience.*/
    void registerSkins();

    /**returns a list of models to be used for the transport
     * example:
     * return new MyModel();
     * may return null. */
    @SideOnly(Side.CLIENT)
    ModelBase[] getModel();


    /*
    <h1>riders and interaction</h1>
    */

    /**defines the rider position offsets, with 0 being the center of the entity.
     * Each set of coords represents a new rider seat, with the first one being the "driver"
     * example:
     * return new float[][]{{x1,y1,z1},{x2,y2,z2}, etc...};
     * may return null*/
    float[][] getRiderOffsets();

    /**returns the size of the hitbox in blocks.
     * example:
     * return new float[]{x,y,z};
     * may not return null*/
    float[] getHitboxSize();

    /**defines if the transport is immune to explosions*/
    boolean isReinforced();


    /*
    <h1> inventory and fluid tanks </h1>
    */

    /**defines the size of the inventory row by row, not counting any special slots like for fuel.
     * end result number of slots is this times 9. plus any crafting/fuel slots
     * may not return null*/
    int getInventoryRows();

    /**defines the capacity of the fluidTank tank.
     * each value defibes another tank.
     * Usually value is 1,000 *the cubic meter capacity, so 242 gallons, is 0.9161 cubic meters, which is 916.1 tank capacity
     * mind you one water bucket is values at 1000, a full cubic meter of water.
     *example:
     * return new int[]{11000, 1000};
     * may return null*/
    int[] getTankCapacity();

    /** defines the whitelist of fluid names for the tank defined by tankID
     * example:
     * if(tankID==0){return new String[]{"water", "diesel"}} else { return null}*/
    @Deprecated //replace with an array of string arrays, more similar to other methods
    String[] getTankFilters(int tankID);

    /**defines the capacity of the RF storage, intended for electric rollingstock that store power for the train.*/
    int getRFCapacity();

    /**this function allows individual trains and rollingstock to implement custom fuel consumption and management
     * you can call one of the existing methods in the FuelHandler class:
     * manageSteam, manageElectric, manageDiesel
     * you may also leave it empty if you don't plan to use it.
     * for more detail on implementing custom versions, take a look at the existing ones, for example:
     * @see FuelHandler#manageSteam(EntityTrainCore) for an example*/
    void manageFuel();

    /** returns the max fuel.
     * for steam trains this is cubic meters of the firebox size. (1.5 on average)
     * for diesel this is cubic meters of the fuel tank. (11.3 on average)
     * for electric this is Kw. (400 on average)*/
    float getMaxFuel();






    /**defines the weight of the transport.*/
     float weightKg();

    /**defines the recipe in order from topleft to bottom right.
     * example:
     * return new ItemStack[]{new ItemStack(Blocks.dirt, 2), new ItemStack(Blocks.glass,1), etc};
     * array must contain 9 values. may not return null.*/
    ItemStack[] getRecipie();


    /**defines the name used for registration and the default name used in the gui.*/
     String transportName();
     /**defines the country of origin for the transport*/
     String transportcountry();
     /**the year or year range to display for the transport.*/
     String transportYear();

    /**the fuel type to display for the transport.*/
     String transportEra();

    /**the top speed in km/h for the transport.
     * not used tor rollingstock.*/
     float transportTopSpeed();
     /**displays in item lore if the transport is fictional or not*/
     boolean isFictional();
     /**the tractive effort for the transport, this is a fallback if metric horsepower (mhp) is not available*/
     float transportTractiveEffort();
     /**this is the default value to define the acceleration speed and pulling power of a transport.*/
     float transportMetricHorsePower();

    /**additional lore for the item, each entry in the array is a new line.
     * may return null.*/
     String[] additionalItemText();



    /*
    <h1>stuff i need to replace soon</h1>
     */


    /**returns the type of transport, this is planned to be removed in favor of a few more methods.
     * for a list of options:
     * @see TrainsInMotion.transportTypes
     * may not return null.*/
    @Deprecated
    TrainsInMotion.transportTypes getType();

    /**returns the item of this transport, this should be a static value in the transport's class.
     * example:
     * public static final Item thisItem = new ItemTransport(new EntityThis(null));
     * Item getItem(){return thisItem;}
     * may not return null*/
    @Deprecated
    Item getItem();

}
