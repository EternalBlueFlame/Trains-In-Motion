package ebf.tim.entities;

import ebf.tim.TrainsInMotion;
import ebf.tim.registry.NBTKeys;
import ebf.tim.utility.CommonProxy;
import ebf.tim.utility.FuelHandler;
import ebf.tim.utility.HitboxHandler;
import ebf.tim.utility.RailUtility;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

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

    /**manages the items for fuel, and the fuel itself.*/
    public FuelHandler fuelHandler = new FuelHandler();
    /**defines the speed percentage the user is attempting to apply.*/
    public int accelerator =0;
    /**used to initialize all the vectors that are used to calculate everything from movement to linking, this is so we don't have to make new variable instances, saves CPU.*/
    private double[][] vectorCache = new double[4][3];



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
        fuelHandler.fuel = additionalData.readInt();
    }
    /**sends the data to server from client*/
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        super.writeSpawnData(buffer);
        buffer.writeInt(accelerator);
        buffer.writeInt(fuelHandler.fuel);
    }
    /**loads the entity's save file*/
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        accelerator = tag.getInteger(NBTKeys.accelerator);
        this.fuelHandler.fuel = tag.getInteger(NBTKeys.transportFuel);
        this.fuelHandler.steamTank = tag.getInteger(NBTKeys.transportSteam);

    }
    /**saves the entity to server world*/
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger(NBTKeys.accelerator, accelerator);
        tag.setInteger(NBTKeys.transportFuel, fuelHandler.fuel);
        tag.setInteger(NBTKeys.transportSteam, fuelHandler.steamTank);

    }

    @Override
    public void entityInit(){
        super.entityInit();
        this.dataWatcher.addObject(18, 0);//accelerator
        this.updateWatchers = true;
    }

    /**
     * <h2>Calculate drag</h2>
     * Add more drag if there are rollingstock.
     * if you have more than one train pulling or pushing a load, the drag should be reduced accordingly.
     */
    public float calculateDrag(float current, @Nullable UUID frontCheckID, @Nullable UUID backCheckID){

        //if frontLinkedTransport and backLinkedTransport are null then return null
        if (frontCheckID == null && backCheckID == null) {
            return current;
        }
        GenericRailTransport frontCheck = (GenericRailTransport) CommonProxy.getEntityFromUuid(frontCheckID);
        GenericRailTransport backCheck = (GenericRailTransport) CommonProxy.getEntityFromUuid(backCheckID);

        //if frontLinkedTransport is a train then reduce drag, otherwise increase it. If it's null then nothing happens.
        if (frontCheck instanceof EntityTrainCore && frontCheck.getBoolean(boolValues.RUNNING)){
            current += ((accelerator / 6f) * 0.01302083f) *(((EntityTrainCore) frontCheck).getAcceleration()*0.5f);
        } else if (frontCheck != null && frontCheck.weightTons() !=0){
            current *=(0.01302083f * frontCheck.weightTons());
        }
        //if backLinkedTransport is a train then reduce drag, otherwise increase it. If it's null then nothing happens.
        if (backCheck instanceof EntityTrainCore && backCheck.getBoolean(boolValues.RUNNING)){
            current += ((accelerator / 6f) * 0.01302083f) *(((EntityTrainCore) backCheck).getAcceleration()*0.5f);
        } else if (backCheck != null && backCheck.weightTons() !=0){
            current *=(0.01302083f * backCheck.weightTons());
        }

        UUID nextFront = null;
        UUID nextBack = null;
        //detect if the next bogie to look at stats for is in the frontLinkedTransport or backLinkedTransport of the frontLinkedTransport bogie
        if (frontCheck != null) {
            if (frontCheck.frontLinkedTransport != null & frontCheck.frontLinkedTransport != this.getPersistentID()) {
                nextFront = frontCheck.frontLinkedTransport;
            } else if (frontCheck.backLinkedTransport != null & frontCheck.backLinkedTransport != this.getPersistentID()) {
                nextFront = frontCheck.backLinkedTransport;
            }
        }
        //detect if the next bogie to look at stats for is in the frontLinkedTransport or backLinkedTransport of the backLinkedTransport bogie
        if (backCheck != null) {
            if (backCheck.frontLinkedTransport != null & backCheck.frontLinkedTransport != this.getPersistentID()) {
                nextBack = backCheck.frontLinkedTransport;
            } else if (backCheck.backLinkedTransport != null & backCheck.backLinkedTransport != this.getPersistentID()) {
                nextBack = backCheck.backLinkedTransport;
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

        if(accelerator!=0 && frontBogie != null && backBogie != null) {
            //acceleration is scaled down to fit the scale of the trains.
            vectorCache[0][0] = calculateDrag(((accelerator / 6f) * 0.01302083f) * getAcceleration(), frontLinkedTransport, backLinkedTransport);

            //cap speed to max.
            if (frontBogie.motionX+ frontBogie.motionZ > getMaxSpeed() || frontBogie.motionX+ frontBogie.motionZ <-getMaxSpeed() ||
                    !((getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM || getType() == TrainsInMotion.transportTypes.STEAM) && fuelHandler.steamTank> getTankCapacity()*0.25)//check for steam fuel
                    || !(getType() == TrainsInMotion.transportTypes.ELECTRIC && getTankAmount()<1)//check for electric fuel
                    ) {
                vectorCache[0][0] = 0;
            }


            vectorCache[1] = RailUtility.rotatePoint(vectorCache[0], rotationPitch, rotationYaw, 0);
            frontBogie.addVelocity(vectorCache[1][0], vectorCache[1][1], vectorCache[1][2]);
            backBogie.addVelocity(vectorCache[1][0], vectorCache[1][1], vectorCache[1][2]);
        }

        if (updateWatchers){
            this.dataWatcher.updateObject(18, accelerator);
        }
        super.onUpdate();
    }

    /**
     * <h2>linking management</h2>
     * this is an override to make sure rollingstock doesn't push trains
     * @see GenericRailTransport#manageLinks()
     */
    @Override
    public void manageLinks(){}


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
                    if (accelerator >-6) {
                        accelerator--;
                        updateWatchers = true;
                        return true;
                    }
                }case 3:{ //increase speed
                    if (accelerator <6) {
                        accelerator++;
                        updateWatchers = true;
                        return true;
                    }
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
    /**gets the max fuel for the train, burnables in the case of steam, RF in the case of electric, etc.*/
    public int getMaxFuel(){return 100;}
    /**gets the acceleration rate of the train*/
    public float getAcceleration(){return 0.025f;}
    /**gets the resource location for the horn sound*/
    public ResourceLocation getHorn(){return null;}
    /**gets the resource location for the running/chugging sound*/
    public ResourceLocation getRunning(){return null;}
    /**gets the multiplication of fuel consumption, 1 is normal, 2 would be double, 1.5 would be halfway between the two, etc.*/
    public float getEfficiency(){return 1;}

}
