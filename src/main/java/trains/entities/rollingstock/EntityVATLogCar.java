package trains.entities.rollingstock;

import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import trains.TrainsInMotion;
import trains.entities.EntityRollingStockCore;
import trains.items.ItemTransport;
import trains.models.tmt.Vec3d;
import trains.registry.TrainRegistry;
import trains.utility.LiquidManager;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * <h1>Pullman's Palace entity</h1>
 * @author Eternal Blue Flame
 */
public class EntityVATLogCar extends EntityRollingStockCore{
    /**
     * <h2>Basic Rollingstock Constructor</h2>
     * To make your own custom rollingstock, create a new class that is a copy of this class, in that copy, you only need to change the values of private static final variables defined here.
     * You also have to make your own item class, for that
     * @see ItemTransport
     * lastly you have to register them in
     * @see TrainRegistry#listTrains()
     *
     * tank only has one value for the tank if there is one.
     * thisItem is the item for this train that will get registered,
     *     the unlocalized name will define the name used for the inventory name (if there is one)
     *     and it will define the name shown for all instances like items, gui, the entity name, and the texture name.
     */
    private static final LiquidManager tank = null;
    public static final String[] itemDescription = new String[]{
            "\u00A77" + StatCollector.translateToLocal("menu.item.weight") +": 2" + StatCollector.translateToLocal("menu.item.tons"),
            "\u00A77" + StatCollector.translateToLocal("menu.item.sizeof") +": 27" + StatCollector.translateToLocal("menu.item.slots")};
    public static final Item thisItem = new ItemTransport(itemDescription, EntityVATLogCar.class.getConstructors()[0]).setUnlocalizedName("vatlogcar");

    /**
     * these basic constructors only need to have their names changed to that of this class, that is assuming your editor doesn't automatically do that.
     * @see EntityRollingStockCore
     */
    public EntityVATLogCar(UUID owner, World world, double xPos, double yPos, double zPos) {
        super(owner, world, xPos, yPos, zPos);
    }
    public EntityVATLogCar(World world){
        super(world);
    }

    /**
     * <h1>Variable Overrides</h1>
     * We override the functions defined in the super here, to give them different values.
     * This is more efficient than having to store them in the super because we won't have to store them in the NBT.
     */

    /**
     * <h2>Bogie Offset</h2>
     * @return the list of offsets for the bogies, 0 being the center. negative values are towards the front.
     */
    @Override
    public List<Double> getBogieOffsets(){return  Arrays.asList(-0.9, 0.9);}
    /**
     * <h2>Inventory Size</h2>
     * @return the size of the inventory not counting any fuel or crafting slots.
     */
    @Override
    public TrainsInMotion.inventorySizes getInventorySize(){return TrainsInMotion.inventorySizes.NINExTHREE;}
    /**
     * <h2>Type</h2>
     * @return the type which will define it's features, GUI, and a number of other things.
     */
    @Override
    public TrainsInMotion.transportTypes getType(){return TrainsInMotion.transportTypes.LOGCAR;}
    /**
     * <h2>Rider offsets</h2>
     * @return defines the offsets of the riders in blocks, the first value is how far back, and the second is how high. Negative values are towards the front, ground, or right.
     *     Each set of floats represents a different rider.
     */
    @Override
    public double[][] getRiderOffsets(){return new double[][]{{}};}
    /**
     * <h2>Hitbox offsets</h2>
     * @return defines the positions for the hitboxes in blocks. 0 being the center of the train, negative values being towards the front..
     */
    @Override
    public float[] getHitboxPositions(){return new float[]{-1.25f,0f,1.25f};}
    /**
     * <h2>Lamp offset</h2>
     * @return defines the offset for the lamp in blocks.
     * negative X values are towards the front.
     * a Y value of less than 2 will register the lamp as null (this is to allow for null lamps, and to prevent from breaking the rails).
     */
    @Override
    public Vec3d getLampOffset(){return new Vec3d(0,0,0);}
    /**
     * <h2>Smoke offset</h2>
     * @return defines the array of positions in blocks for smoke.
     * the first number in the position defines the X/Z axis, negative values are towards the front.
     * the second number defined the Y position, 0 is the rails.
     * the third number defines left to right.
     * the forth number defines the color from 255 (white) to 0 (black)
     * the 5th number is for density, there's no min/max but larger numbers will create more lag.
     */
    @Override
    public float[][] getSmokeOffset(){return new float[][]{};}



    /**
     * <h2>pre-asigned values</h2>
     * These return values defined from the top of the class.
     * These should only need modification for advanced users.
     */    @Override
    public LiquidManager getTank(){return tank;}
    @Override
    public Item getItem(){
        return thisItem;
    }
}
