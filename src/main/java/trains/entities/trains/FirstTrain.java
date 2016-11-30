package trains.entities.trains;

import net.minecraft.client.audio.ISound;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import trains.entities.EntityTrainCore;
import trains.registry.ItemRegistry;
import trains.registry.TrainRegistry;
import trains.registry.URIRegistry;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FirstTrain extends EntityTrainCore {
    /**
     * <h2>Basic Train Constructor</h2>
     * This class defines a basic train class, all other trains basically just copy this class and change the values of the variables.
     * Be sure other trains are also defined in the train registry
     * @see TrainRegistry#listTrains()
     *
     * Acceleration is applied every tick, with similar math to max speed.
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
    private static final int[] hitboxPositions = new int[]{-1,0,1};
    private static final ResourceLocation horn = URIRegistry.SOUND_HORN.getResource("h080brigadelok.ogg");
    private static final ResourceLocation running = URIRegistry.SOUND_RUNNING.getResource("r080brigadelok.ogg");
    private static final String trainName = "0-8-0 Brigadelok";

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
    public String getInventoryName() {return trainName;}
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
    @Override
    public ISound getHorn(){
        return new ISound() {
            @Override
            public ResourceLocation getPositionedSoundLocation() {
                return horn;
            }

            @Override
            public boolean canRepeat() {
                return false;
            }

            @Override
            public int getRepeatDelay() {
                return 0;
            }

            @Override
            public float getVolume() {
                return 100;
            }

            @Override
            public float getPitch() {
                return 0;
            }

            @Override
            public float getXPosF() {
                return (float) posX;
            }

            @Override
            public float getYPosF() {
                return (float) posY;
            }

            @Override
            public float getZPosF() {
                return (float) posZ;
            }

            @Override
            public AttenuationType getAttenuationType() {
                return null;
            }
        };
    }
    @Override
    public ISound getRunning(){
        return new ISound() {
            @Override
            public ResourceLocation getPositionedSoundLocation() {
                return running;
            }

            @Override
            public boolean canRepeat() {
                return true;
            }

            @Override
            public int getRepeatDelay() {
                return 0;
            }

            @Override
            public float getVolume() {
                return 100;
            }

            @Override
            public float getPitch() {
                return 0;
            }

            @Override
            public float getXPosF() {
                return (float) posX;
            }

            @Override
            public float getYPosF() {
                return (float) posY;
            }

            @Override
            public float getZPosF() {
                return (float) posZ;
            }

            @Override
            public AttenuationType getAttenuationType() {
                return null;
            }
        };
    }
}
