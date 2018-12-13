package ebf.tim.entities;

import ebf.tim.registry.NBTKeys;
import ebf.tim.utility.FuelHandler;
import ebf.tim.utility.RailUtility;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import javax.annotation.Nullable;
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
    }
    /**sends the data to server from client*/
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        super.writeSpawnData(buffer);
        buffer.writeInt(accelerator);
    }
    /**loads the entity's save file*/
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        accelerator = tag.getInteger(NBTKeys.accelerator);
        dataWatcher.updateObject(16, tag.getFloat(NBTKeys.transportFuel));
        vectorCache[7][0] = tag.getFloat(NBTKeys.trainSpeed);


    }
    /**saves the entity to server world*/
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger(NBTKeys.accelerator, accelerator);
        tag.setFloat(NBTKeys.transportFuel, dataWatcher.getWatchableObjectFloat(16));
        tag.setFloat(NBTKeys.trainSpeed, vectorCache[7][0]);

    }

    @Override
    public void entityInit(){
        super.entityInit();
        this.dataWatcher.addObject(18, accelerator);//accelerator
        this.dataWatcher.addObject(16, 0f);//accelerator
        this.updateWatchers = true;
    }

    @Override
    public void initInventorySlots(){
        super.initInventorySlots();
        inventory.add(fuelSlot());
        if(getTankInfo(ForgeDirection.UNKNOWN).length>1){
            inventory.add(waterSlot());
        }
    }


    //in newtons
    public float getPower(){
        if (transportTractiveEffort()<1f){
            //2650 is a conversion factor for newtons, and 0.72 is a rough estimate for efficiency
            return 2650f*((0.72f*
                    (transportMetricHorsePower()*getAcceleratiorPercentage()))/
                    getVelocity());
        } else {
            //standard tractive effort to newtons
            return 4.4482216f*(transportTractiveEffort()*getAcceleratiorPercentage());
        }
    }
    //gets the max power output to estimate how much the train can support of it's own weight
    public float getMaxPower(){
        if (transportTractiveEffort()<1f){
            //2650 is a conversion factor for newtons, and 0.72 is a rough estimate for efficiency
            return 2650f*((0.72f*
                    (transportMetricHorsePower()*0.05f))/
                    getVelocity());
        } else {
            //standard tractive effort to newtons
            return 4.4482216f*(transportTractiveEffort()*0.05f);
        }
    }

    //returns the current speed
    public float getVelocity(){
        return (Math.abs(motionX)+Math.abs(motionZ))==0?0.01f:(float)(Math.abs(motionX)+Math.abs(motionZ));
    }
    //gets the throttle position as a percentage with 1 as max and -1 as max reverse
    public float getAcceleratiorPercentage(){
        return (accelerator*0.16666666666f)*0.05f;
    }

    /**
     * <h2>Calculate speed increase rate</h2>
     *
     * speed calculation provided by zodiacmal
     */
    public void calculateAcceleration(){
        boolean hasReversed = false;
        float weight=weightKg() * (getBoolean(boolValues.BRAKE)?2:1);
        float pullingWeight=weight;
        float powerNewtons = getPower();
        float maxPowerNewtons = getMaxPower();
        List<GenericRailTransport> checked = new ArrayList<>();
        checked.add(this);
        GenericRailTransport front = null;
        if (frontLinkedID!=null){
            front = (GenericRailTransport) worldObj.getEntityByID(frontLinkedID);
        } else if (backLinkedID != null){
            front = (GenericRailTransport) worldObj.getEntityByID(backLinkedID);
            hasReversed = true;
        }

        //todo: this should probably be cached so it only has to check every few ticks, like the fuel update for example.
        while(front != null){
            //calculate the speed modification
            if(!(front instanceof EntityTrainCore) && front.weightKg()!=0){
                weight+= front.weightKg() * (getBoolean(boolValues.BRAKE)?2:1) * (front.frontBogie.isOnSlope?2:1) * (front.backBogie.isOnSlope?2:1);
            } else if (front instanceof EntityTrainCore) {
                powerNewtons += front.getBoolean(boolValues.RUNNING)?((EntityTrainCore)front).getPower()*0.75f:0;
                maxPowerNewtons += front.getBoolean(boolValues.RUNNING)?((EntityTrainCore)front).getMaxPower()*0.75f:0;
                weight+= front.weightKg() * (getBoolean(boolValues.BRAKE)?4:1) * (front.frontBogie.isOnSlope?2:1) * (front.backBogie.isOnSlope?2:1);
                pullingWeight +=front.weightKg() * (getBoolean(boolValues.BRAKE)?4:1) * (front.frontBogie.isOnSlope?2:1) * (front.backBogie.isOnSlope?2:1);
            }

            //add the one we just used to the checked list
            checked.add(front);
            //loop to the next rollingstock.
            @Nullable
            Entity test = front.frontLinkedID!=null?worldObj.getEntityByID(front.frontLinkedID):null;
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




        if (accelerator !=0) {
            //speed is defined by the power in newtons divided by the weight, divided by the number of ticks in a second.
            if(powerNewtons!=0) {
                //745.7 converts to watts, which seems more accurate.
                vectorCache[7][0] += ((powerNewtons/weight)/745.7);//applied power


                vectorCache[7][1]=Math.abs((maxPowerNewtons/pullingWeight)/745.7f)-Math.abs(vectorCache[7][0]);//max power produced without drag, minus the current power
                if(vectorCache[7][1]>0.005){
                    //todo: add sparks to animator.
                    vectorCache[7][0] -= ((powerNewtons/weight)/745.7)*0.5f;
                }


            }

        } else {
            vectorCache[7][1]=0;
            if (vectorCache[7][0]>0) {
                vectorCache[7][0] *= (1 - (0.005* (weight * 0.0007457)));
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

        //cap movement to the max speed
        if (vectorCache[7][0] > (transportTopSpeed()*0.0138889f)){
            vectorCache[7][0] = (transportTopSpeed()*0.0138889f);
        } else if (vectorCache[7][0] < (-transportTopSpeed()*0.0138889f)){
            vectorCache[7][0] = (-transportTopSpeed()*0.0138889f);
        }
        //todo: make the max reduced when going reverse


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
                //stop calculation if it can't move, running should be managed from the fuel handler, to be more dynamic
                if (getBoolean(boolValues.RUNNING)) {
                    calculateAcceleration();
                } else {
                    vectorCache[7][0] = 0;
                    accelerator=0;
                    this.dataWatcher.updateObject(18, accelerator);
                }
            }
            vectorCache[6] = RailUtility.rotatePointF(vectorCache[7][0],vectorCache[7][1],vectorCache[7][2], rotationPitch, rotationYaw, 0);
            frontBogie.setVelocity(vectorCache[6][0], vectorCache[6][1], vectorCache[6][2]);
            backBogie.setVelocity(vectorCache[6][0], vectorCache[6][1], vectorCache[6][2]);

            frontVelocityX = frontBogie.motionX;
            frontVelocityZ = frontBogie.motionZ;
            backVelocityX = backBogie.motionX;
            backVelocityZ = backBogie.motionZ;
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
    public boolean interact(EntityPlayer player, boolean isFront, boolean isBack, int key) {
        if (!super.interact(player, isFront, isBack, key)){
            switch (key){
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
                        this.dataWatcher.updateObject(18, accelerator);
                    }
                    return true;
                }case 3:{ //increase speed
                    if (accelerator <6 && getBoolean(boolValues.RUNNING)) {
                        accelerator++;
                        this.dataWatcher.updateObject(18, accelerator);
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
    /**gets the resource location for the horn sound*/
    public ResourceLocation getHorn(){return null;}
    /**gets the resource location for the running/chugging sound*/
    public ResourceLocation getRunningSound(){return null;}
    /**gets the multiplication of fuel consumption, 1 is normal, 2 would be double, 1.5 would be halfway between the two, etc.*/
    public float getEfficiency(){return 1;}

}
