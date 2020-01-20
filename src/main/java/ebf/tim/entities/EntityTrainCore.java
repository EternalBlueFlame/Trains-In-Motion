package ebf.tim.entities;

import ebf.tim.registry.NBTKeys;
import ebf.tim.utility.DebugUtil;
import ebf.tim.utility.FuelHandler;
import ebf.tim.utility.RailUtility;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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

    /**loads the entity's save file*/
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        accelerator = tag.getInteger(NBTKeys.accelerator);
        dataWatcher.updateObject(16, tag.getFloat(NBTKeys.transportFuel));
        vectorCache[1][0] = tag.getFloat(NBTKeys.trainSpeed);


    }
    /**saves the entity to server world*/
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger(NBTKeys.accelerator, accelerator);
        tag.setFloat(NBTKeys.transportFuel, dataWatcher.getWatchableObjectFloat(16));
        tag.setFloat(NBTKeys.trainSpeed, vectorCache[1][0]);

    }

    @Override
    public void entityInit(){
        super.entityInit();
        this.dataWatcher.addObject(18, accelerator);//accelerator
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


    @Override
    public boolean hasDrag(){return accelerator==0;}
    //in newtons
    @Override
    public float getPower(){
        //create a parabolic curve between reverse max speed and forward max speed, then get the position from current speed.
        float t = getVelocity();
        t=(((1f - t) * (1f - t)) * transportTopSpeed()*-0.5f) + (2f * (1f - t) * t * 0) + ((t * t) * transportTopSpeed());
        //the average difference between metric horsepower and MHP is about 3.75% or tractiveEffort*26.3=MHP
            return ((transportTractiveEffort()<1f?transportMetricHorsePower()*26f
                    :transportTractiveEffort())

                    //90mhp translating to 1 mircoblock a second sounds about right
                    //(*0.009/16 = 0.0005625), then divide by ticks to turn to seconds (0.0005625/20=0.000028125).
                    // lastly compensate for acceleration curve by multiplying by t.
                    *getAcceleratiorPercentage())

                    *(0.000028125f*t);
    }

    //returns the current speed
    public float getVelocity(){
        return (Math.abs(motionX)+Math.abs(motionZ))==0?0.01f:(float)(Math.abs(motionX)+Math.abs(motionZ));
    }
    //gets the throttle position as a percentage with 1 as max and -1 as max reverse
    public float getAcceleratiorPercentage(){
        return (accelerator*0.16666666666f)*0.05f;
    }

    private float maxPowerMicroblocks =0;

    @Override
    public void setValuesOnLinkUpdate(List<GenericRailTransport> consist){
        maxPowerMicroblocks =0;
        pullingWeight=0;
        for(GenericRailTransport t : consist) {
            maxPowerMicroblocks +=t.getPower();
            pullingWeight +=t.weightKg();
        }
    }

    /**
     * <h2>Calculate speed increase rate</h2>
     */
    public void calculateAcceleration(){
        float weight=pullingWeight * (getBoolean(boolValues.BRAKE)?2:1);
        if (accelerator !=0 && ticksExisted%20!=0) {
            //speed is defined by the power in newtons divided by the weight, divided by the number of ticks in a second.
            if(maxPowerMicroblocks !=0) {
                // weight's effect on HP is generally inverse of HP itself, it can be described as
                // 30 lbs of coal about 100 feet in one minute = 33,000 lbf for 1.01387 MHP
                // however this is for vertical, converting to horizontal means multiplying by around 85% of gravity
                // so say you have a train with 75mhp, that means your carrying capacity sits around
                // 75*1.11039648 tons. (83.279)
                //clamp to a max of the pulling power as to not generate negative pulling power
                vectorCache[1][0] += ((maxPowerMicroblocks- Math.min(weight*1.11039648,maxPowerMicroblocks)));//applied power

                //debuff for rain
                vectorCache[1][1]=( (1.75f * (worldObj.isRaining()?0.5f:1)));

                //todo rework this, the math isnt based on newtons anymore.
                if(Math.abs(vectorCache[1][0])*-745.7>vectorCache[1][1]/7457){
                    //todo: add sparks to animator.
                    //DebugUtil.println("SCREECH","wheelspin: " + (vectorCache[1][0]*-745.7),
                    //        "Grip: " + (vectorCache[1][1]/7457), "i really need to get those spark particles in..");
                    //vectorCache[1][0] *=0.33;
                }

            } else {
                updateConsist();
            }

        }
        //apply drag, always.
        vectorCache[1][1]=0;
        if (vectorCache[1][0]>0) {
            vectorCache[1][0] *= (1 - (0.005* (weight * 0.0007457)));
            if (vectorCache[1][0] <0){
                vectorCache[1][0] =0;
            }
        } else {
            vectorCache[1][0] *= (1 - (0.005* (weight * 0.0007457)));
            if (vectorCache[1][0] >0){
                vectorCache[1][0] =0;
            }
        }

        //cap movement to the max speed
        if (vectorCache[1][0] > (transportTopSpeed()*0.00694445f)){
            vectorCache[1][0] = (transportTopSpeed()*0.00694445f);
        } else if (vectorCache[1][0] < (-transportTopSpeed()*0.00694445f)){
            vectorCache[1][0] = (-transportTopSpeed()*0.00694445f);
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

        if(accelerator!=0 && frontBogie != null && backBogie != null && !worldObj.isRemote) {
            //twice a second, re-calculate the speed.
            if(ticksExisted %10==0){
                //stop calculation if it can't move, running should be managed from the fuel handler, to be more dynamic
                if (getBoolean(boolValues.RUNNING)) {
                    calculateAcceleration();
                } else {
                    vectorCache[1][0] = 0;
                    accelerator=0;
                    this.dataWatcher.updateObject(18, accelerator);
                }
            }
            vectorCache[3] = RailUtility.rotatePointF(vectorCache[1][0],0,0, rotationPitch, rotationYaw, 0);
            frontBogie.addVelocity(vectorCache[3][0], vectorCache[3][1], vectorCache[3][2]);
            backBogie.addVelocity(vectorCache[3][0], vectorCache[3][1], vectorCache[3][2]);

            frontVelocityX = frontBogie.motionX;
            backVelocityX = backBogie.motionX;
            backVelocityZ = backBogie.motionZ;
            frontVelocityZ = frontBogie.motionZ;
        }


        super.onUpdate();
    }

    /**
     * <h2>linking management</h2>
     * this is an override to make sure rollingstock doesn't push trains
     * @see GenericRailTransport#manageLinks(GenericRailTransport)
     */
    @Override
    public void manageLinks(GenericRailTransport linkedTransport){
        if(accelerator==0){
            super.manageLinks(linkedTransport);
        }
    }


    @Override
    public boolean interact(int player, boolean isFront, boolean isBack, int key) {
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

    @Override
    public int getMinecartType(){return 10004;}

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
