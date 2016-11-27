package trains.entities.trains;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import trains.entities.EntityTrainCore;
import trains.registry.ItemRegistry;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FirstTrain extends EntityTrainCore {
    /**
     * <h2>Basic Train Constructor</h2>
     * This class defines a basic train class, all other trains basically just copy this class.
     *
     * Acceleration is applied every tick, defined as a percentage of the max speed, multiplied by the position of the accelerator, the variable MUST be 3 parts
     * The fluid tank has 2 values, one for water/RF/fuel/uranium and another for steam.
     * Speed is actually for the German 0-8-0 Brigadelok (calculation is (1/72)*70.8111, 70.8111 being the train's max speed in km/h)
     * Type defines the kind of train, this will effect fuel type, and the GUI used. the types are:
     *             1: steam
     *             2: diesel
     *             3: hydrogen diesel
     *             4: electric
     *             5: nuclear steam
     *             6: nuclear electric
     *             7: maglev
     */
    private static final float initMaxSpeed = 0.9834875f;
    private static final float acceleration = 0.001F;
    private static final int type = 1;
    private static final int maxFuel = 100;
    private static FluidTank[] tank = new FluidTank[]{
            new FluidTank(new FluidStack(FluidRegistry.WATER, 0),10),
            new FluidTank(new FluidStack(FluidRegistry.WATER, 0),2)
    };
    private static final int inventorySize= 3;
    private static final float offsetXZ =0f;
    private static final List<Double> bogieOffset = Arrays.asList(-1.0D, 1.0D);
    private static final int[] hitboxPositions = new int[]{-2,-1,0,1};

    /**
     * @see EntityTrainCore
     */
    public FirstTrain(UUID owner, World world, double xPos, double yPos, double zPos) {
        super(owner, world, xPos, yPos, zPos, tank);
    }

    /**
     * we have to have the constructor for the initial spawn that puts the train in the world, minecraft does this, we don't have to mess with it other than just having it.
     *
     * @param world the world to spawn it in.
     */
    public FirstTrain(World world){
        super(world);
    }


    /**
     * <h2>Variable Overrides</h2>
     * We override the functions defined in the super here, to give them different values.
     * This is more efficient than having to store them in the super because we won't have to store them in the NBT.
     */
    @Override
    public float getMaxSpeed(){return initMaxSpeed;}
    @Override
    public String getInventoryName() {return "Brigadelok";}
    @Override
    public List<Double> getBogieOffsets(){return bogieOffset;}
    @Override
    public int getInventorySize(){return inventorySize;}
    @Override
    public int getType(){return type;}
    @Override
    public int getMaxFuel(){return maxFuel;}
    @Override
    public float getRiderOffset(){return offsetXZ;}
    @Override
    public float getAcceleration(){return acceleration;}
    @Override
    public ItemStack getItem(){
        return new ItemStack(ItemRegistry.testCart, 1);
    }
    @Override
    public int[] getHitboxPositions(){return hitboxPositions;}
}
