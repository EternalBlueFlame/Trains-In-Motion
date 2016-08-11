package trains.entities;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import trains.utility.FuelHandler;

import java.util.UUID;

public class EntityTrainCore extends MinecartExtended {

    public float[] acceleration = new float[]{0.0005F,0.0006F,0.0005F}; //the first 3 values are a point curve, representing <35%, 35-70% and >70% to modify how acceleration is handled at each point. //the 4th value defines how much the weight hauled effects acceleration.
    public int trainType=0;//list of train types 0 is null, 1 is steam, 2 is diesel, 3 is electric, 4 is hydrogen, 5 is nuclear steam, 6 is nuclear electric
    public boolean isRunning = false;// if the train is running/using fuel
    public int furnaceFuel = 0; //the amount of fuel in the furnace, only used for steam and nuclear trains
    public int maxFuel =0; //the max fuel in the train's furnace.
    public float maxSpeed = 0; // the max speed
    private int trainTicks =0; //defines the train's tick count.
    public int accelerator =0; //defines the value of how much to speed up, or brake the train.
    public String destination = "";  //railcraft destination


    /**
     * this class defines the core of the Rolling Stock, in most cases the functionality will be altered by the class that extends this, however whatever is generic to rolling stock specifically
     * for things generic to Rolling Stock:
     * @see EntityRollingStockCore
     *
     * for things generic to both these classes:
     * @see MinecartExtended
     *
     * default constructor for setting up variables after this is created
     * @param owner the owner profile, used to define owner of the entity, used in super.
     * @param world the world to spawn the entity in, used in super's super.
     * @param xPos the x position to spawn entity at, used in super's super.
     * @param yPos the y position to spawn entity at, used in super's super.
     * @param zPos the z position to spawn entity at, used in super's super.
     * @param maxSpeed the max speed a train can go, 1.0 is equal to 72kmph or 72000 blocks/h.
     * @param type what kind of rolling stock it is.
     *             typelist:
     *             1: steam
     *             2: diesel
     *             3: hydrogen diesel
     *             4: electric
     *             5: nuclear steam
     *             6: nuclear electric
     *             7: maglev
     * @param tank used to define the fluid tank(s) if there are any
     *             empty array for no tanks, - steam and nuclear take two tanks. - all other trains take one tank
     *             all tanks besides diesel should use FluidRegistry.WATER
     * @param inventoryrows defines the rows of inventory, inventory size is defined by rows * columns.
     * @param inventoryColumns defines the columns of the inventory.
     * @param craftingSlots defines the number of crafting slots, 1 is for fuel, 2 is for boiler.
     * @param GUIid the ID used to define what GUI the entity uses (0 for no GUI).
     * @param minecartNumber used to define the unique ID of the minecart, this prevents issues with base game and modded minecarts.
     * @param canBeRidden used to toggle if the player can ride the entity.
     */
    public EntityTrainCore(UUID owner, World world, double xPos, double yPos, double zPos, float maxSpeed, float[] acceleration,
                           int type,FluidTank[] tank,int inventoryrows, int inventoryColumns, int craftingSlots, int GUIid, int minecartNumber, boolean canBeRidden){
        super(owner, world, xPos, yPos, zPos, type, tank, inventoryrows, inventoryColumns, craftingSlots, GUIid, minecartNumber,  canBeRidden);

        this.acceleration = acceleration;
        trainType = type;
        this.maxSpeed = maxSpeed;
    }

    /**
     * we have to have the constructor for the initial spawn that puts the train in the world, minecraft does this, we don't have to mess with it other than just having it.
     *
     * @param world the world to spawn it in.
     */
    public EntityTrainCore(World world){
        super(world);
    }

    /**
     * this is basically NBT for entity spawn, to keep data between client and server in sync because some data is not automatically shared.
     */
    @Override
    public void readSpawnData(ByteBuf additionalData) {
        super.readSpawnData(additionalData);
        maxSpeed = additionalData.readFloat();
    }
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        super.writeSpawnData(buffer);
        buffer.writeFloat(maxSpeed);
    }

    /**
     * defines what should be done this tick to separate processing and improve overall performance.
     * begins with call to super class to handle the core movement processing and some other small things.
     * @see MinecartExtended#onUpdate()
     *
     * before the actual tick count, we deal with the acceleration and drag.
     *
     * ticks are defined by the variable: trainTicks
     *
     * tick 5 defines the fuel handling which is managed by
     * @see FuelHandler
     *
     * tick 6 defines the acceleration (probably change later)
     *
     * the default tick checks if the tick count is over 10, and sets it back to 0, meaning this loop is run twice a second.
     * the reset also prevents potential memory leaks.
     */
    @Override
    public void onUpdate() {

        if (!worldObj.isRemote /*&& furnaceFuel*/) {

            /**
             * the 0.96 is the drag, this subtracts 0.04 from the speed to calculate for the drag.
             * TODO after we can link carts, this needs to be changed to 1-(0.04*ConnectedCars) && braking should be added to the drag
             *
             * based on the motion (x speed + z speed) compared to max speed, figure out which stage of acceleration the train is in.
             * from there, if the x and or z motions are not 0, we create a new x and or z value, then apply the drag (0.96 currently)
             * after we handle the acceleration in
             * @see EntityTrainCore#accelerate(int)
             */
            if (accelerator != 0) {
                double x = 0;
                double z = 0;
                double motion = Math.sqrt(motionX * motionX + motionZ * motionZ);
                int accelIndex = 0;
                if (motion > maxSpeed || motion < -maxSpeed) {accelIndex = -1;}
                else if (motion > maxSpeed * 0.6d || motion < -(maxSpeed * 0.6d)) {accelIndex = 2;}
                else if (motion > maxSpeed * 0.3d || motion < -(maxSpeed * 0.3d)) {accelIndex = 1;}

                if (accelerator !=0 && accelIndex !=-1) {
                    if (motionX > 0) {
                        x = (acceleration[accelIndex] * accelerator);
                    } else {
                        x = -(acceleration[accelIndex] * accelerator);
                    }
                    if (motionZ > 0) {
                        z = (acceleration[accelIndex] * accelerator);
                    } else {
                        z = -(acceleration[accelIndex] * accelerator);
                    }

                    //compensate for stopping the train to 0, this is needed for brake and switching from forward to reverse.
                    /*if (motionX != 0.0D && ((x < 0.0D && motionX > 0.0D) || (x > 0.0D && motionX < 0.0D))){
                        x=0.0D;
                    }
                    if ( motionZ != 0.0D && ((z < 0.0D && motionZ > 0.0D) || (z > 0.0D && motionZ < 0.0D))){
                        z=0.0D;
                    }*/

                    System.out.println("motion: "+ motion + " : accelIndex : " + accelIndex + " : accelerator : " + accelerator + ": XMotion :" + x + " : ZMotion :" + z);
                }
                this.addVelocity(x, motionY, z);
            }

            trainTicks++;

            switch (trainTicks) {
                case 5: {
                    if (isRunning) {
                        FuelHandler.FuelHandler(this);
                    }
                    break;
                }
                default: {
                    //if the tick count is higher than the values used, reset it so it can count up again.
                    if (trainTicks > 10) {
                        trainTicks = 1;
                    }
                }
            }
        }
        super.onUpdate();
    }


    @Override
    public float getMaxCartSpeedOnRail() {
        return 5F;
    }


    /**
     * @see MinecartExtended#readFromNBT(NBTTagCompound)
     */
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        isRunning = tag.getBoolean("train.isRunning");
        trainTicks = tag.getInteger("train.trainTicks");
        destination = tag.getString("train.destination");
        trainType = tag.getInteger("train.type");
    }
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setBoolean("train.isRunning",isRunning);
        tag.setInteger("train.ticks", trainTicks);
        tag.setString("train.destination", destination);
        tag.setInteger("train.typr", trainType);
    }


    /**
     * this function, and the following one define railcraft support for trains by adding in destination and if you can set one.
     * @see mods.railcraft.api.carts.IRoutableCart
     */
    @Override
    public String getDestination() {
        return destination;
    }
    //Only locomotives can receive a destination from a track.
    @Override
    public boolean setDestination(ItemStack ticket) {
        return true;
    }


    /**
     * simple function for setting the train's speed and whether or not it is reverse.
     */
    public void setAcceleration(boolean increase){
        if (increase && accelerator <6){
            accelerator++;
        } else if (!increase && accelerator >-6){
            accelerator--;
        }
        if (accelerator>0 && !isReverse){
            isReverse = false;
        } else if (accelerator <0 && isReverse){
            isReverse = true;
        }
    }

}
