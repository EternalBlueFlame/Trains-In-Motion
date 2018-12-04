package ebf.tim.api;

import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.models.Bogie;
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.Vec3d;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

/**
 * Created by Eternal Blue Flame
 *
 * This is mainly for the purpose of documentation and ease of use.
 * you don't have to extend this class, you can just extend EntityTrainCore e if you want.
 */
public abstract class TrainBase extends EntityTrainCore{

    //Constructors, no need to override these unless you need to.
    public TrainBase(UUID owner, World world, double xPos, double yPos, double zPos){
        super(owner, world, xPos, yPos, zPos);
    }
    public TrainBase(World worldObj){
        super(worldObj);
    }

    public abstract Bogie[] getBogieModels();


    /**
     * returns the lengths from center that represent the offset each bogie should render at
     */
    public abstract List<Double> getRenderBogieOffsets();

    /**
     * returns the type of transport, for a list of options:
     *
     * @see TrainsInMotion.transportTypes
     */
    public abstract TrainsInMotion.transportTypes getType();

    /**
     * returns the positions for the hitbox, they are defined by length from center.
     * must have at least 4 hitboxes, the first and last values are used for coupling positions
     */
    public abstract double[][] getHitboxPositions();

    /**
     * returns the item of the transport, this should be a static value in the transport's class.
     */
    public abstract Item getItem();

    /**
     * defines the size of the inventory, not counting any special slots like for fuel.
     *
     */
    public abstract int getInventoryRows();

    /**
     * defines the radius in microblocks that the pistons animate
     */
    public abstract float getPistonOffset();

    /**
     * defines smoke positions, the outer array defines each new smoke point, the inner arrays define the X/Y/Z
     */
    public abstract float[][] getSmokeOffset();

    /**
     * defines the length from center of the transport, thus is used for the motion calculation
     */
    public abstract float[] bogieLengthFromCenter();

    /**
     * defines the render scale, minecraft's default is 0.0625
     */
    public abstract float getRenderScale();

    /**
     * defines if the transport is explosion resistant
     */
    public abstract boolean isReinforced();

    /**
     * defines the capacity of the fluidTank tank.
     * Usually value is 10,000 *the cubic meter capacity, so 242 gallons, is 0.9161 cubic meters, which is 9161 tank capacity
     * NOTE if this is used for a train, minimum value should be 1100, which is just a little over a single bucket to allow prevention of overheating.
     */
    public abstract int[] getTankCapacity();

    /**
     * defines the capacity of the RF storage, intended for electric rollingstock that store power for the train.
     */
    public abstract int getRFCapacity();

    /**
     * this function allows individual trains and rollingstock to implement custom fuel consumption and management
     * @see ebf.tim.utility.FuelHandler#manageElectric(EntityTrainCore)
     *
     * this is also used by rollingstock for consumeables, like filling a water tank
     * @see ebf.tim.utility.FuelHandler#manageTanker(GenericRailTransport)
     */
    public abstract void manageFuel();

    /**
     * defines the weight of the transport.
     */
    public abstract float weightKg();

    /**
     * returns the max fuel.
     * for steam trains this is cubic meters of the firebox size. (1.5 on average)
     * for diesel this is cubic meters. (11.3 on average)
     * for electric this is Kw. (400 on average)
     * for nuclear this is the number of fusion cores, rounded down. (usually 1)
     */
    public abstract float getMaxFuel();

}
