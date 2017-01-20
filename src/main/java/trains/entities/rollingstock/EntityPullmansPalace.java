package trains.entities.rollingstock;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import trains.TrainsInMotion;
import trains.entities.EntityRollingStockCore;
import trains.items.rollingstock.ItemPullmansPalace;
import trains.models.tmt.Vec3d;
import trains.registry.TrainRegistry;
import trains.utility.LiquidManager;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static trains.TrainsInMotion.MODID;

public class EntityPullmansPalace extends EntityRollingStockCore{
    /**
     * <h2>Basic Train Constructor</h2>
     * To make your own custom train, create a new class that is a copy of this class, in that copy, you only need to change the values of private static final variables defined here.
     * You also have to make your own item class, for that
     * @see ItemPullmansPalace
     * lastly you have to register them in
     * @see TrainRegistry#listTrains()
     *
     * The fluid tank has 2 values, one for water/RF/fuel/uranium and another for steam.
     * SOUND_HORN and SOUND_RUNNING are refrences to the sound files for the train horn and running sounds.
     * thisItem is the item for this train that will get registered.
     */
    public static final Item thisItem = new ItemPullmansPalace().setUnlocalizedName("itempullmanspalace").setTextureName(MODID + ":itemTests");

    /**
     * these basic constructors only need to have their names changed to that of this class, that is assuming your editor doesn't automatically do that.
     * @see EntityRollingStockCore
     */
    public EntityPullmansPalace(UUID owner, World world, double xPos, double yPos, double zPos) {
        super(owner, world, xPos, yPos, zPos);
    }
    public EntityPullmansPalace(World world){
        super(world);
    }

    /**
     * <h1>Train Variable Overrides</h1>
     * We override the functions defined in the super here, to give them different values.
     * This is more efficient than having to store them in the super because we won't have to store them in the NBT.
     */

    /**
     * <h2>name</h2>
     * @return the name of the train, this is used for the inventory name.
     */
    @Override
    public String getName() {return "Pullman's Palace Car";}
    /**
     * <h2>Bogie Offset</h2>
     * @return the list of offsets for the bogies, 0 being the center of the train. negative values are towards the front of the train.
     */
    @Override
    public List<Float> getBogieOffsets(){return  Arrays.asList(-1.75f, 1.75f);}
    /**
     * <h2>Inventory Size</h2>
     * @return the size of the inventory not counting any crafting/fuel slots.
     */
    @Override
    public TrainsInMotion.inventorySizes getInventorySize(){return TrainsInMotion.inventorySizes.THREExTHREE;}
    /**
     * <h2>Train Type</h2>
     * @return the type of train, defines a number of things like fuel management, GUI style, and animations.
     */
    @Override
    public TrainsInMotion.transportTypes getType(){return TrainsInMotion.transportTypes.PASSENGER;}
    /**
     * <h2>Rider offset</h2>
     * @return defines the offset of the rider in blocks, the first value is how far back, and the second is how high.
     */
    @Override
    public float[] getRiderOffset(){return new float[]{1.3f,1.5f};}
    /**
     * <h2>Hitbox offsets</h2>
     * @return defines the positions for the hitboxes in blocks. 0 being the center of the train, negative values being towards the front..
     */
    @Override
    public float[] getHitboxPositions(){return new float[]{-2.75f,-1.25f,0f,1.35f,2.75f};}
    /**
     * <h2>Lamp offset</h2>
     * @return defines the offset for the lamp in blocks.
     * negative X values are towards the front of the train.
     * a Y value of less than 2 will register the lamp as null (this is to allow for null lamps, and to prevent from breaking the rails the train is on).
     */
    @Override
    public Vec3d getLampOffset(){return new Vec3d(-0,2,0);}
    /**
     * <h2>Lamp offset</h2>
     * @return defines the radius in microblocks for the piston rotation. Usually only used for steam
     */
    @Override
    public float getPistonOffset(){return 0.5f;}
    /**
     * <h2>Smoke offset</h2>
     * @return defines the array of positions in blocks for smoke.
     * the first number in the position defines the X/Z axis, negative values are towards the front of the train.
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
    public LiquidManager getTank(){return null;}
    @Override
    public Item getItem(){
        return thisItem;
    }
}
