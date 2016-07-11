package trains.entities.trains;

import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import trains.entities.EntityTrainCore;
import trains.gui.GUITrain;

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
     * speed is actually for the German 0-8-0 Brigadelok (calculation is (1/72)*70.8111, 70.8111 being the train's max speed in km/h)
     */
    private static float initMaxSpeed = 0.9834875f;
    private static float[] acceleration = new float[]{1,3,1};
    private static int type = 1;
    private static FluidTank[] tank = new FluidTank[]{
            new FluidTank(new FluidStack(FluidRegistry.WATER, 0),10),
            new FluidTank(new FluidStack(FluidRegistry.WATER, 0),2)
    };
    private static int inventoryRows = 3;
    private static int inventoryColumns = 3;
    private static int craftingSlots = 2;
    private static int minecartID = 1001;
    private static boolean canBeRidden = true;

    /**
     * @see EntityTrainCore#EntityTrainCore(UUID, World, double, double, double, float, float[], int, FluidTank[], int, int, int, int, int, boolean)
     */
    public FirstTrain(UUID owner, World world, double xPos, double yPos, double zPos) {
        super(owner, world, xPos, yPos, zPos, initMaxSpeed, acceleration, type,tank,inventoryRows, inventoryColumns,craftingSlots,GUITrain.GUI_ID,minecartID,canBeRidden);

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
