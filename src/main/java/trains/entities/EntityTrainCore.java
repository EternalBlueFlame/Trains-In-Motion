package trains.entities;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.audio.ISound;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import trains.utility.FuelHandler;
import trains.utility.InventoryHandler;

import java.util.UUID;

/**
 * <h2> Train core</h2>
 * this is the management core for all train
 */
public class EntityTrainCore extends GenericRailTransport {

    /**
     * <h3>use variables</h3>
     * switch variables, mostly used to maintain things.
     */
    public boolean brake = false; //bool for the train/rollingstock's break.
    public boolean isRunning = false;// if the train is running/using fuel
    public int furnaceFuel = 0; //the amount of fuel in the furnace, only used for steam and nuclear trains
    public int trainTicks =0; //defines the train's tick count.


    /**
     * <h3>movement and bogies</h3>
     * variables to store bogies, motion, collision, and other movement related things.
     */
    public int accelerator =0; //defines the value of how much to speed up, or brake the


    /**
     * <h3>long-term stored variables</h3>
     * these variables usually don't change often, or maybe even ever.
     */
    public String destination ="";

    /**
     * <h3>inventory</h3>
     * variables for the inventory.
     */
    public InventoryHandler inventory = new InventoryHandler(this); //Inventory, every train will have this to some extent or another, //the first two slots are for crafting



    /**
    *
    *
    * <h2> Base train Constructor</h2>
    *
    * default constructor for all trains, server side only.
    *
    * @param owner the owner profile, used to define owner of the entity,
    * @param world the world to spawn the entity in, used in super's super.
    * @param xPos the x position to spawn entity at, used in super's super.
    * @param yPos the y position to spawn entity at, used in super's super.
    * @param zPos the z position to spawn entity at, used in super's super.
    */

    public EntityTrainCore(UUID owner, World world, double xPos, double yPos, double zPos){
        super(world);
        posY = yPos;
        posX = xPos;
        posZ = zPos;

        this.owner = owner;

        setSize(1f,2);
        this.boundingBox.minX = 0;
        this.boundingBox.minY = 0;
        this.boundingBox.minZ = 0;
        this.boundingBox.maxX = 0;
        this.boundingBox.maxY = 0;
        this.boundingBox.maxZ = 0;
    }
    //this constructor is lower level spawning
    public EntityTrainCore(World world){
        super(world);
    }

    /**
     * <h2> Data Syncing and Saving </h2>
     * this is explained in
     * @see GenericRailTransport#readSpawnData(ByteBuf)
     */
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        isRunning = tag.getBoolean("isrunning");
        brake = tag.getBoolean("extended.brake");

        inventory.readNBT(tag, "items");
        getTank().readFromNBT(tag);
    }
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setBoolean("isrunning",isRunning);
        tag.setBoolean("extended.brake", brake);

        tag.setTag("items", inventory.writeNBT());
        getTank().writeToNBT(tag);
    }


    /**
     * <h2> process train movement</h2>
     * called by the super class to figure out the amount of movement to apply every tick
     * @see GenericRailTransport#onUpdate()
     */
    public float processMovement(){


        float speed = 0;
        if (accelerator!=0) {
            //calculate acceleration based on train's throttle.
           speed = getAcceleration() * (accelerator / 6);
        }
        speed *= (float) Math.exp(
                -Math.pow(motionX-(getMaxSpeed() *0.5),2)
                        /Math.pow(2*getMaxSpeed(),2)
                        /Math.sqrt(Math.pow(2*Math.PI*getMaxSpeed(),2d)));
        //System.out.println("speed : " + speed);
        return speed;

        //if motion > maxspeed/2 calcualte based on the negative result, else calculatue based on posative result


        /**

        float accel = 0;
        if (bogie.get(0).motionX + bogie.get(0).motionZ + bogie.get(bogie.size()-1).motionX + bogie.get(bogie.size()-1).motionZ !=0){
            accel = (( (float) ((bogie.get(0).motionX + bogie.get(0).motionZ + bogie.get(bogie.size()-1).motionX + bogie.get(bogie.size()-1).motionZ)*0.5f)/ getMaxSpeed()) * 100);
        }
            accel *= (getAcceleration() * (16.66666666e7 * accelerator));
        if (hitboxHandler.getCollision(this) & accel==0 & !tank.canDrain((int) accel*20, 1)){
            //if we shouldn't be moving, return 0.
            return 0;
        } else {
            tank.drainFluid((int) accel*20, 1);
            //compensate for if the train is stopped
            if (accel == 0 && accelerator > 0) {
                accel = getAcceleration() * 0.10f;
            } else if (accel == 0 && accelerator < 0) {
                accel = -getAcceleration() * 0.10f;
            }
            //modify based on fuel
            if (getType() == 1) {
                if (tank.tankFluidAmount(1) <= tank.tankCapacity(1) / 3) {
                    //no boiler pressure
                    accel *= 0.1f;
                } else if (tank.tankFluidAmount(1) <= tank.tankCapacity(1)  / 2) {
                    //low boiler pressure
                    accel *= 0.5f;
                }
                //we don't compensate for normal/high boiler pressure because there would be no change.
                //TODO maybe a compensation for superheated boiler pressure?
            }
            return accel;

        }*/
    }

    /**
     * <h2> on entity update</h2>
     * after the super method is dealt with,
     * @see GenericRailTransport#onUpdate()
     * every 5 ticks we also need to deal with the fuel.
     * @see FuelHandler#ManageFuel(EntityTrainCore)
     */
    @Override
    public void onUpdate() {
        super.onUpdate();
        //simple tick management so some code does not need to be run every tick.
        if (!worldObj.isRemote) {
            switch (trainTicks) {
                case 5: {
                    //deal with the fuel
                    FuelHandler.ManageFuel(this);
                break;
            }
            default: {
                //if the tick count is higher than the values used, reset it so it can count up again.
                if (trainTicks > 10) {
                    trainTicks = 1;
                }
            }

        }
        trainTicks++;
    }
    }



    /**
     * <h2> acceleration</h2>
     * function called from a packet for setting the train's speed and whether or not it is reverse.
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
