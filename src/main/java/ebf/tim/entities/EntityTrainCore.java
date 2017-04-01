package ebf.tim.entities;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ebf.tim.TrainsInMotion;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.audio.ISound;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import ebf.tim.networking.PacketKeyPress;
import ebf.tim.utility.CommonProxy;
import ebf.tim.utility.FuelHandler;
import ebf.tim.utility.HitboxHandler;
import ebf.tim.utility.RailUtility;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

import static ebf.tim.utility.RailUtility.rotatePoint;

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
    private double[][] vectorCache = new double[4][3];



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
        super(owner, world, xPos, yPos, zPos);
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
        if (frontCheckID == TrainsInMotion.nullUUID && backCheckID == TrainsInMotion.nullUUID) {
            return current;
        }
        GenericRailTransport frontCheck = CommonProxy.getTransportFromUuid(frontCheckID);
        GenericRailTransport backCheck = CommonProxy.getTransportFromUuid(backCheckID);

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

        UUID nextFront = TrainsInMotion.nullUUID;
        UUID nextBack = TrainsInMotion.nullUUID;
        //detect if the next bogie to look at stats for is in the front or back of the front bogie
        if (frontCheck != null) {
            if (frontCheck.front != null & frontCheck.front != this.getPersistentID()) {
                nextFront = frontCheck.front;
            } else if (frontCheck.back != null & frontCheck.back != this.getPersistentID()) {
                nextFront = frontCheck.back;
            }
        }
        //detect if the next bogie to look at stats for is in the front or back of the back bogie
        if (backCheck != null) {
            if (backCheck.front != null & backCheck.front != this.getPersistentID()) {
                nextBack = backCheck.front;
            } else if (backCheck.back != null & backCheck.back != this.getPersistentID()) {
                nextBack = backCheck.back;
            }
        }
        //loop again to get the next carts in the list.
        return calculateDrag(current, nextFront, nextBack);

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

        if(accelerator!=0) {
            //acceleration is scaled down to fit the scale of the trains.
            vectorCache[0][0] = ((accelerator / 6f) * 0.01302083f) * getAcceleration();

            //cap speed to max.
            if (vectorCache[0][0] > getMaxSpeed()) {
                vectorCache[0][0] = getMaxSpeed();
            } else if (vectorCache[0][0] < -getMaxSpeed()) {
                vectorCache[0][0] = -getMaxSpeed();
            }


            vectorCache[1] = RailUtility.rotatePoint(vectorCache[0], rotationPitch, rotationYaw, 0);
            frontBogie.addVelocity(vectorCache[1][0], vectorCache[1][1], vectorCache[1][2]);
            backBogie.addVelocity(vectorCache[1][0], vectorCache[1][1], vectorCache[1][2]);
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
     * @see PacketKeyPress.Handler#onMessage(PacketKeyPress, MessageContext)
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
     * <h2>linking management</h2>
     * this is an override to make sure rollingstock doesn't push trains
     * @see GenericRailTransport#manageLinks()
     */
    @Override
    public void manageLinks(){
        if(!worldObj.isRemote) {

            if (front == TrainsInMotion.nullUUID && isCoupling) {
                vectorCache[2] = rotatePoint(new double[]{getHitboxPositions()[0] - 2, 0, 0}, 0, rotationYaw, 0);
                vectorCache[2][0] +=posX;
                vectorCache[2][1] +=posY;
                vectorCache[2][2] +=posZ;
                List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
                        AxisAlignedBB.getBoundingBox(vectorCache[2][0] - 0.5d, vectorCache[2][1], vectorCache[2][2] - 0.5d,
                                vectorCache[2][0] + 0.5d, vectorCache[2][1] + 2, vectorCache[2][2] +0.5d));

                if (list.size() > 0) {
                    for (Object entity : list) {
                        if (entity instanceof HitboxHandler.multipartHitbox && !hitboxList.contains(entity) && ((GenericRailTransport)worldObj.getEntityByID(((HitboxHandler.multipartHitbox) entity).parent.getEntityId())).isCoupling) {
                            front = ((HitboxHandler.multipartHitbox) entity).parent.getPersistentID();
                            System.out.println(getEntityId() + " : train front linked : ");
                        }
                    }
                }
            }


            if (back == TrainsInMotion.nullUUID && isCoupling) {
                vectorCache[3] = rotatePoint(new double[]{getHitboxPositions()[getHitboxPositions().length-1] + 2, 0, 0}, 0, rotationYaw, 0);
                vectorCache[3][0] +=posX;
                vectorCache[3][1] +=posY;
                vectorCache[3][2] +=posZ;
                List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
                        AxisAlignedBB.getBoundingBox(vectorCache[3][0] - 0.5d, vectorCache[3][1], vectorCache[3][2] - 0.5d,
                                vectorCache[3][0] + 0.5d, vectorCache[3][1] + 2, vectorCache[3][2] +0.5d));

                if (list.size() > 0) {
                    for (Object entity : list) {
                        if (entity instanceof HitboxHandler.multipartHitbox && !hitboxList.contains(entity) && ((GenericRailTransport)worldObj.getEntityByID(((HitboxHandler.multipartHitbox) entity).parent.getEntityId())).isCoupling) {
                            back = ((HitboxHandler.multipartHitbox) entity).parent.getPersistentID();
                            System.out.println(getEntityId() + " : train back linked : ");
                        }
                    }
                }
            }
        }
    }


    /**
     * <h2>menu toggles</h2>
     * called from a packet to change the settings.
     * @see PacketKeyPress.Handler#onMessage(PacketKeyPress, MessageContext)
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
