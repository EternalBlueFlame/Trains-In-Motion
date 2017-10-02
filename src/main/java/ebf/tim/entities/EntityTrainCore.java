package ebf.tim.entities;

import ebf.tim.TrainsInMotion;
import ebf.tim.registry.NBTKeys;
import ebf.tim.utility.FuelHandler;
import ebf.tim.utility.RailUtility;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h1>Train core</h1>
 * this is the management core for all trains.
 * @author Eternal Blue Flame
 */
public class EntityTrainCore extends GenericRailTransport {

    /**manages the items for burnHeat, and the burnHeat itself.*/
    public FuelHandler fuelHandler = new FuelHandler();
    /**defines the speed percentage the user is attempting to apply.*/
    public int accelerator =0;
    /**used to initialize all the vectors that are used to calculate everything from movement to linking, this is so we don't have to make new variable instances, saves CPU.*/
    //private double[][] vectorCache = new double[4][3];



    /*
    * <h2> Base train Constructor</h2>
    */

    /** default constructor for all trains, server only.
    * Usually this is the one you would reference unless you need to do something only on client.
    * @param owner the owner profile, used to define owner of the entity,
    * @param world the world to spawn the entity in, used in super's super.
    * @param xPos the x position to spawn entity at, used in super's super.
    * @param yPos the y position to spawn entity at, used in super's super.
    * @param zPos the z position to spawn entity at, used in super's super.
    */
    public EntityTrainCore(UUID owner, World world, double xPos, double yPos, double zPos){
        super(owner, world, xPos, yPos, zPos);
    }

    /**default constructor for all trains, client only.
     * use this if you need to do something only on client.
     * @param world the world to spawn the entity in, used in super's super.
     */
    public EntityTrainCore(World world){
        super(world);
    }

    /**
     * <h2> Data Syncing and Saving </h2>
     * SpawnData is mainly used for data that has to be created on client then sent to the server, like data processed on item use.
     * NBT is save data, which only happens on server.
     */

    /**reads the data sent from client on entity spawn*/
    @Override
    public void readSpawnData(ByteBuf additionalData) {
    super.readSpawnData(additionalData);
        accelerator = additionalData.readInt();
        fuelHandler.heatC = additionalData.readFloat();
    }
    /**sends the data to server from client*/
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        super.writeSpawnData(buffer);
        buffer.writeInt(accelerator);
        buffer.writeFloat(fuelHandler.heatC);
    }
    /**loads the entity's save file*/
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        accelerator = tag.getInteger(NBTKeys.accelerator);
        this.fuelHandler.heatC = tag.getFloat(NBTKeys.transportFuel);
        this.fuelHandler.steamTank = tag.getInteger(NBTKeys.transportSteam);
        vectorCache[7][0] = tag.getDouble(NBTKeys.trainSpeed);


    }
    /**saves the entity to server world*/
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger(NBTKeys.accelerator, accelerator);
        tag.setFloat(NBTKeys.transportFuel, fuelHandler.heatC);
        tag.setInteger(NBTKeys.transportSteam, fuelHandler.steamTank);
        tag.setDouble(NBTKeys.trainSpeed, vectorCache[7][0]);

    }

    @Override
    public void entityInit(){
        super.entityInit();
        this.dataWatcher.addObject(18, 0);//accelerator
        this.updateWatchers = true;
    }


    /**
     * <h2>Max speed</h2>
     * @return the value of the max speed in blocks per second(km/h * 0.277778) scaled to the size (1/7.68)
     * for example if the train speed is 25, the calculation would be ((25*0.277778)* 0.130208333) = 0.90422525
     * or simplified to 25 * 0.0361690103 = 0.90422525
     */
    public float getProcessedMaxSpeed(){
        return (getMaxSpeed() * 0.0361690103f);
    }

    /**
     * <h2>Calculate speed increase rate</h2>
     *
     * speed calculation provided by zodiacmal
     */
    public void calculateAcceleration(){
        boolean hasReversed = false;
        float weight = weightKg() * (getBoolean(boolValues.BRAKE)?10:1);
        float hp = getHorsePower();
        List<GenericRailTransport> checked = new ArrayList<>();
        checked.add(this);
        GenericRailTransport front = null;
        if (frontLinkedID!=null){
            front = (GenericRailTransport) worldObj.getEntityByID(frontLinkedID);
        } else if (backLinkedID != null){
            front = (GenericRailTransport) worldObj.getEntityByID(backLinkedID);
            hasReversed = true;
        }

        while(front != null){
            //calculate the speed modification
            if(!front.getType().isTrain() && front.weightKg()!=0){
                weight+= front.weightKg() * (getBoolean(boolValues.BRAKE)?2:1);
            } else if (front instanceof EntityTrainCore) {
                hp += front.getBoolean(boolValues.RUNNING)?((EntityTrainCore)front).getHorsePower()*0.75f:0;
                weight+= front.weightKg() * (getBoolean(boolValues.BRAKE)?10:1);
            }

            //add the one we just used to the checked list
            checked.add(front);
            //loop to the next rollingstock.
            Entity test = frontLinkedID!=null?worldObj.getEntityByID(front.frontLinkedID):null;
            //if it's null and we haven't reversed yet, start the loop over from the back. if we have reversed though, end the loop.
            if(test == null){
                if (!hasReversed){
                    front = backLinkedID!=null?(GenericRailTransport)worldObj.getEntityByID(backLinkedID):null;
                    hasReversed = true;
                } else {
                    front = null;
                }
                //if the list of checked transports doesn't contain the new one then change the loop to the new one,
            } else if (!checked.contains(test)) {
                front = (GenericRailTransport) test;
                //if the list does contain the checked one, and we haven't reversed yet,  start the loop over from the back. if we have reversed though, end the loop.
            } else {
                if (!hasReversed){
                    front = backLinkedID!=null?(GenericRailTransport)worldObj.getEntityByID(backLinkedID):null;
                    hasReversed = true;
                } else {
                    front = null;
                }
            }
        }

        //745.7 converts watts to horsepower, but considering the scale, it should be substantially less
        if (accelerator !=0) {
            vectorCache[7][0] += (((0.7457 * (accelerator / 6D)) * hp) / (weight * 0.7457));
        } else {
            if (vectorCache[7][0]>0) {
                vectorCache[7][0] *= (1 - (0.05* (weight * 0.0007457)));
                if (vectorCache[7][0] <0){
                    vectorCache[7][0] =0;
                }
            } else {
                vectorCache[7][0] *= (1 - (0.005* (weight * 0.0007457)));
                if (vectorCache[7][0] >0){
                    vectorCache[7][0] =0;
                }
            }
        }
        if (vectorCache[7][0] > getProcessedMaxSpeed()){
            vectorCache[7][0] = getProcessedMaxSpeed();
        } else if (vectorCache[7][0] < -getProcessedMaxSpeed()){
            vectorCache[7][0] = -getProcessedMaxSpeed();
        }


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

        if(frontBogie != null && backBogie != null && !worldObj.isRemote) {
            //twice a second, re-calculate the speed.
            if(ticksExisted %10==0){
                //stop calculation if it can't move
                if (((getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM || getType() == TrainsInMotion.transportTypes.STEAM) && fuelHandler.steamTank< getTankCapacity()*0.25)//check for steam fuel
                        || (getType() == TrainsInMotion.transportTypes.ELECTRIC && getTankAmount()<1)//check for electric fuel
                ) {
                    vectorCache[7][0] = 0;
                    setBoolean(boolValues.RUNNING, false);
                } else {
                    calculateAcceleration();
                }
            }
            vectorCache[6] = RailUtility.rotatePoint(vectorCache[7], rotationPitch, rotationYaw, 0);
            frontBogie.setVelocity(vectorCache[6][0], vectorCache[6][1], vectorCache[6][2]);
            backBogie.setVelocity(vectorCache[6][0], vectorCache[6][1], vectorCache[6][2]);

            frontVelocityX = frontBogie.motionX;
            frontVelocityZ = frontBogie.motionZ;
            backVelocityX = backBogie.motionX;
            backVelocityZ = backBogie.motionZ;
        }

        if (updateWatchers){
            this.dataWatcher.updateObject(18, accelerator);
        }
        super.onUpdate();
    }

    /**
     * <h2>linking management</h2>
     * this is an override to make sure rollingstock doesn't push trains
     * @see GenericRailTransport#manageLinks(GenericRailTransport)
     */
    @Override
    public void manageLinks(GenericRailTransport linkedTransport){}


    @Override
    public boolean ProcessPacket(int functionID){
        if (!super.ProcessPacket(functionID)){
            switch (functionID){
                case 8:{ //toggle ignition
                    setBoolean(boolValues.RUNNING, !getBoolean(boolValues.RUNNING));
                    return true;
                }case 9:{ //plays a sound on all clients within hearing distance
                    //the second to last value is volume, and idk what the last one is.
                    worldObj.playSoundEffect(posX, posY, posZ, getHorn().getResourcePath(), 1, 0.5f);
                    return true;
                }case 2:{ //decrease speed
                    if (accelerator >-6 && getBoolean(boolValues.RUNNING)) {
                        accelerator--;
                        updateWatchers = true;
                    }
                    return true;
                }case 3:{ //increase speed
                    if (accelerator <6 && getBoolean(boolValues.RUNNING)) {
                        accelerator++;
                        updateWatchers = true;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * <h2>Inherited variables</h2>
     * these functions are overridden by classes that extend this so that way the values can be changed indirectly.
     */
    /**gets the max speed of the transport in blocks per second*/
    public float getMaxSpeed(){return 0;}
    /**gets the acceleration rate of the train*/
    public float getHorsePower(){return 100f;}
    /**gets the resource location for the horn sound*/
    public ResourceLocation getHorn(){return null;}
    /**gets the resource location for the running/chugging sound*/
    public ResourceLocation getRunning(){return null;}
    /**gets the multiplication of fuel consumption, 1 is normal, 2 would be double, 1.5 would be halfway between the two, etc.*/
    public float getEfficiency(){return 1;}

}
