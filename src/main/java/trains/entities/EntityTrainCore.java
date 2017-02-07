package trains.entities;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.audio.ISound;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import trains.networking.PacketKeyPress;
import trains.utility.FuelHandler;

import javax.annotation.Nullable;
import java.util.UUID;

import static trains.TrainsInMotion.nullUUID;
import static trains.TrainsInMotion.proxy;
import static trains.utility.RailUtility.rotatePoint;

/**
 * <h1>Train core</h1>
 * this is the management core for all trains.
 * @author Eternal Blue Flame
 */
public class EntityTrainCore extends GenericRailTransport {

    /**
     * <h3>variables</h3>
     * isRunning is used for non-steam based trains to define if it's actually on.
     * fuelHandler manages the items for fuel, and the fuel itself.
     * accelerator defines the speed percentage the user is attempting to apply.
     */
    public boolean isRunning = false;
    public FuelHandler fuelHandler = new FuelHandler();
    public int accelerator =0;



    /**
    * <h2> Base train Constructor</h2>
    *
    * default constructor for all trains, the first one is server only, the second is client only.
    *
    * @param owner the owner profile, used to define owner of the entity,
    * @param world the world to spawn the entity in, used in super's super.
    * @param xPos the x position to spawn entity at, used in super's super.
    * @param yPos the y position to spawn entity at, used in super's super.
    * @param zPos the z position to spawn entity at, used in super's super.
    */
    public EntityTrainCore(UUID owner, World world, double xPos, double yPos, double zPos){
        super(world, owner, xPos, yPos, zPos);
    }
    //this constructor is for client side spawning
    public EntityTrainCore(World world){
        super(world);
    }

    /**
     * <h2> Data Syncing and Saving </h2>
     * this is explained in
     * @see GenericRailTransport#readSpawnData(ByteBuf)
     */
    @Override
    public void readSpawnData(ByteBuf additionalData) {
    super.readSpawnData(additionalData);
        isRunning = additionalData.readBoolean();
        accelerator = additionalData.readInt();
        fuelHandler.fuel = additionalData.readInt();
    }
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        super.writeSpawnData(buffer);
        buffer.writeBoolean(isRunning);
        buffer.writeInt(accelerator);
        buffer.writeInt(fuelHandler.fuel);
    }
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        isRunning = tag.getBoolean("isrunning");
        accelerator = tag.getInteger("train.accel");
        fuelHandler.readEntityFromNBT(tag);

    }
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setBoolean("isrunning",isRunning);
        tag.setInteger("train.accel", accelerator);
        fuelHandler.writeEntityToNBT(tag);

    }


    /**
     * <h2>Calculate drag</h2>
     * Add more drag if there are rollingstock.
     * if you have more than one train pulling or pushing a load, the drag should be reduced accordingly.
     */
    public float calculateDrag(float current, @Nullable UUID frontCheckID, @Nullable UUID backCheckID){

        //if front and back are null then return null
        if (frontCheckID == nullUUID && backCheckID == nullUUID) {
            return current;
        }
        GenericRailTransport frontCheck = proxy.getTransportFromUuid(frontCheckID);
        GenericRailTransport backCheck = proxy.getTransportFromUuid(backCheckID);

        //if front is a train then reduce drag, otherwise increase it. If it's null then nothing happens.
        if (frontCheck instanceof EntityTrainCore){
            current *= 1.25f;
        } else if (frontCheck instanceof EntityRollingStockCore){
            current *=0.9f;
        }
        //if back is a train then reduce drag, otherwise increase it. If it's null then nothing happens.
        if (backCheck instanceof EntityTrainCore){
            current *= 1.25f;
        } else if (backCheck instanceof EntityRollingStockCore){
            current *=0.9f;
        }

        UUID nextFront = nullUUID;
        UUID nextBack = nullUUID;
        //detect if the next bogie to look at stats for is in the front or back of the front bogie
        if (frontCheck != null)
        if (frontCheck.front != null || frontCheck.front != this.getPersistentID()){
            nextFront = frontCheck.front;
        } else if (frontCheck.back != null || frontCheck.back != this.getPersistentID()){
            nextFront = frontCheck.back;
        }
        //detect if the next bogie to look at stats for is in the front or back of the back bogie
        if (backCheck != null) {
            if (backCheck.front != null || backCheck.front != this.getPersistentID()) {
                nextBack = backCheck.front;
            } else if (backCheck.back != null || backCheck.back != this.getPersistentID()) {
                nextBack = backCheck.back;
            }
        }
        //loop again to get the next carts in the list.
        return calculateDrag(current, nextFront, nextBack);

    }

    /**
     * <h2>process train movement</h2>
     * called by onUpdate to figure out the amount of movement to apply every tick and in what direction
     * @see #onUpdate()
     */
    @Override
    public double processMovement(EntityBogie entityBogie){

        //first define direction
        double X =0;
        if (MathHelper.floor_double(entityBogie.motionX) !=0){
            X = entityBogie.motionX;
        } else {
            X = entityBogie.motionZ;
        }
        //add drag.
        double speed =  X * calculateDrag(0.9f, front, back);

        //compensate for if the train is still.
        if (speed ==0){
            speed = ((accelerator / 6f)*0.1f) * getAcceleration();
        }

        //now apply the accelerator if it's not 0 and there is enough fuel.
        if (accelerator!=0) {
            //if ((getType() == TrainsInMotion.transportTypes.STEAM || getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM)) {
            //    if (tanks.getTank(false).getFluidAmount() > tanks.getTank(false).getCapacity()*0.5f) {
            speed *=  (Math.copySign(1f, accelerator) + ((accelerator / 6f) * getAcceleration()));
            //System.out.println(speed + " : " + getMaxSpeed());
            //    }
            //} else if (fuelHandler.fuel>0){
            //    speed *= 1 + ((accelerator / 6f) * getAcceleration());
            //}
        }

        //cap speed to max.
        if (speed>getMaxSpeed()){
           speed=getMaxSpeed();
        } else if (speed<-getMaxSpeed()) {
            speed=-getMaxSpeed();
        }
        return speed;
    }

    /**
     * <h2> on entity update</h2>
     * first we have to manage our speed by updating our motion vector and applying it to the bogies.
     * after that we let the super handle things.
     * @see GenericRailTransport#onUpdate()
     * lastly we use a tick check to define if we should manage fuel, since we shouldn't be doing that every tick.
     */
    @Override
    public void onUpdate() {
        if (bogie.size()>0){
            boolean collision = !hitboxHandler.getCollision(this);
            //if theres no collision then process movement for the bogies.
            if (collision) {
                for (EntityBogie currentBogie : bogie) {
                    if (currentBogie != null) {
                        motion = rotatePoint(new double[]{processMovement(currentBogie), (float) motionY, 0}, 0.0f, currentBogie.rotationYaw, 0.0f);

                        currentBogie.setVelocity(motion[0], motion[1], motion[2]);
                        currentBogie.minecartMove();
                    }
                }
            } else {
                motion = new double[]{0d, 0d, 0d};
            }
        }
        super.onUpdate();

        //simple tick management so some code does not need to be run every tick.
        if (!worldObj.isRemote) {
            if (transportTicks%5==1){
                fuelHandler.ManageFuel(this);
            }
        }
    }



    /**
     * <h2>acceleration</h2>
     * function called from a packet for setting the train's speed and whether or not it is reverse.
     * @see trains.networking.PacketKeyPress.Handler#onMessage(PacketKeyPress, MessageContext)
     * TODO: for traincraft we need to limit the accelerator to 1.
     */
    public void setAcceleration(boolean increase){
        if (increase && accelerator <6){
            accelerator++;
        } else if (!increase && accelerator >-6){
            accelerator--;
        }
    }


    /**
     * <h2>menu toggles</h2>
     * called from a packet to change the settings.
     * @see trains.networking.PacketKeyPress.Handler#onMessage(PacketKeyPress, MessageContext)
     *
     * @see GenericRailTransport#toggleBool(int) for more info.
     */
    @Override
    public boolean toggleBool(int index){
        if (!super.toggleBool(index)){
            switch (index){
                case 8:{
                    isRunning = !isRunning;
                    return true;
                }case 9:{
                    //Play horn
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <h2>Inherited variables</h2>
     * these functions are overridden by classes that extend this so that way the values can be changed indirectly.
     */
    public float getMaxSpeed(){return 0;}
    public int getMaxFuel(){return 100;}
    public float getAcceleration(){return 0.025f;}
    public ISound getHorn(){return null;}
    public ISound getRunning(){return null;}

}
