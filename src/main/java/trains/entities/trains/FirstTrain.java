package trains.entities.trains;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.ISound;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import trains.entities.EntityTrainCore;
import trains.items.trains.ItemFirstTrain;
import trains.registry.TrainRegistry;
import trains.registry.URIRegistry;
import trains.utility.LiquidManager;
import trains.utility.RailUtility;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static trains.TrainsInMotion.MODID;

public class FirstTrain extends EntityTrainCore {
    /**
     * <h2>Basic Train Constructor</h2>
     * To make your own custom train, create a new class that is a copy of this class, in that copy, you only need to change the values of private static final variables defined here.
     * You also have to make your own item class, for that
     * @see ItemFirstTrain
     * lastly you have to register them in
     * @see TrainRegistry#listTrains()
     *
     * initMaxSpeed is actually for the German 0-8-0 Brigadelok (calculation is (1/72)*70.8111, 70.8111 being the train's max speed in km/h)
     * Acceleration is applied every tick, with similar math to max speed.
     * The fluid tank has 2 values, one for water/RF/fuel/uranium and another for steam.
     * Type defines the kind of train, this will effect fuel type, and the GUI used. the types are:
     *             1: steam
     *             2: diesel
     *             3: hydrogen diesel
     *             4: electric
     *             5: nuclear steam
     *             6: nuclear electric
     *             7: maglev
     * inventorySize is pre-defined number of spaces in the train's inventory. Not counting crafting slots (bucket/fuel slots)
     *              1: 2x2
     *              2: 2x2
     *              3: 3x3
     *              4: 4x3
     *              5: 4x4
     * offsetXZ is the size in blocks to offset the player sitting position on X and/or Z dependant on rotation
     * bogieOffset is the list of bogie positions in blocks with 0 being the center of the train.
     * hirboxPositions is the list of positions in blocks for hitboxes of the train, they are automatically rotated with the train and are always 2 blocks tall.
     * horn and running are refrences to the sound files for the train horn and running sounds.
     * trainName is the name of the train that displays in the GUI and on mods like WAILA.
     * thisItem is the item for this train that will get registered.
     * TODO: we will need a list of items for the crafting recipe. we also need items to fill that list.....
     */
    private static final float initMaxSpeed = 0.9834875f;
    private static final float acceleration = 0.0001F;
    private static final int type = 1;
    private static final int maxFuel = 12800;
    private LiquidManager tank = new LiquidManager(new int[]{10000, 10000}, new Fluid[]{FluidRegistry.WATER},true);
    private static final int inventorySize= 3;
    private static final float offsetXZ =1.3f;
    private static final List<Double> bogieOffset = Arrays.asList(-1.0D, 1.0D);
    private static final int[] hitboxPositions = new int[]{-1,0,1};
    private static final ResourceLocation horn = URIRegistry.SOUND_HORN.getResource("h080brigadelok.ogg");
    private static final ResourceLocation running = URIRegistry.SOUND_RUNNING.getResource("r080brigadelok.ogg");
    private static final String trainName = "0-8-0 Brigadelok";
    public static final Item thisItem = new ItemFirstTrain().setUnlocalizedName("itemFirstTrain").setTextureName(MODID + ":itemTests");

    /**
     * these basic constructors only need to have their names changed to that of this class, that is assuming your editor doesn't automatically do that.
     * @see EntityTrainCore
     */
    public FirstTrain(UUID owner, World world, double xPos, double yPos, double zPos) {
        super(owner, world, xPos, yPos, zPos);
    }
    public FirstTrain(World world){
        super(world);
    }

    /**
     * <h2>lamp management</h2>
     * This defines the position the train is supposed to place the light, relative to its own, in this case 2 blocks above the rail block and 3 blocks in front of the train.
     * Remove this function if the train has no lamp.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void onUpdate(){
        super.onUpdate();
        lamp.ShouldUpdate(worldObj, RailUtility.rotatePoint(new double[]{posX+3,posY+2, posZ}, rotationPitch, rotationYaw, 0));
    }


    /**
     * <h2>Variable Overrides</h2>
     * We override the functions defined in the super here, to give them different values.
     * This is more efficient than having to store them in the super because we won't have to store them in the NBT.
     * You shouldn't need to modify these unless your train's features are a bit more 'special' than normal.
     */
    @Override
    public float getMaxSpeed(){return initMaxSpeed;}
    @Override
    public String getName() {return trainName;}
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
    public Item getItem(){
        return thisItem;
    }
    @Override
    public int[] getHitboxPositions(){return hitboxPositions;}
    @Override
    public LiquidManager getTank(){return tank;}
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
