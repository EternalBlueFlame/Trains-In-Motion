package trains.entities.trains;

import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import trains.entities.EntityTrainCore;
import trains.gui.train.GUISteam;

import java.util.UUID;

public class FirstTrain extends EntityTrainCore {
    /**
     * there are two ways we can define the values for the train.
     * 1. dynamic, this would mean that the model and all values are processed in runtime (slow but less classes)
     * 2. static, we define all of them before hand in individual classes for each train and rollingstock. (fast but more classes, probably easier to read)
     *
     * in this case we are going to go with static, it would be more reliable and probably significantly more efficient at the cost of organization.
     *
     *
     * acceleration is applied every tick, defined as a percentage of the max speed, multiplied by the position of the accelerator, the variable MUST be 3 parts
     * the fluid tank has 2 values, one for water/RF/fuel/uranium and another for steam/.
     * speed is actually for the German 0-8-0 Brigadelok (calculation is (1/72)*70.8111, 70.8111 being the train's max speed in km/h)
     */
    private static final float initMaxSpeed = 0.9834875f;
    private static final float[] acceleration = new float[]{0.0005F,0.00075F,0.0005F};
    private static final int type = 1;
    private static FluidTank[] tank = new FluidTank[]{
            new FluidTank(new FluidStack(FluidRegistry.WATER, 0),10),
            new FluidTank(new FluidStack(FluidRegistry.WATER, 0),2)
    };
    private static final int inventoryRows = 3;
    private static final int inventoryColumns = 3;
    private static final int craftingSlots = 2;
    private static final int minecartID = 1001;
    private static final boolean canBeRidden = true;

    /**
     * @see EntityTrainCore#EntityTrainCore(UUID, World, double, double, double, float, float[], int, FluidTank[], int, int, int, int, int, boolean)
     */
    public FirstTrain(UUID owner, World world, double xPos, double yPos, double zPos) {
        super(owner, world, xPos, yPos, zPos, initMaxSpeed, acceleration, type,tank,inventoryRows, inventoryColumns,craftingSlots, GUISteam.GUI_ID,minecartID,canBeRidden);
    }

    /**
     * we have to have the constructor for the initial spawn that puts the train in the world, minecraft does this, we don't have to mess with it other than just having it.
     *
     * @param world the world to spawn it in.
     */
    public FirstTrain(World world){
        super(world);
    }
}
