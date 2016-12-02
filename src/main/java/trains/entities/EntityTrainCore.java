package trains.entities;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import trains.entities.trains.FirstTrain;
import trains.utility.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static trains.utility.RailUtility.rotatePoint;

/**
 * <h2> Train core</h2>
 * this is the management core for all train
 */
public class EntityTrainCore extends Entity implements IInventory, IEntityAdditionalSpawnData, IEntityMultiPart {

    /**
     * <h3>use variables</h3>
     * switch variables, mostly used to maintain things.
     */
    public boolean isLocked = false; //mostly used to lock other players from using/accessing parts of the cart/train
    public boolean brake = false; //bool for the train/rollingstock's break.
    public boolean isRunning = false;// if the train is running/using fuel
    public int furnaceFuel = 0; //the amount of fuel in the furnace, only used for steam and nuclear trains
    public int trainTicks =0; //defines the train's tick count.
    public LampHandler lamp = new LampHandler(); //manages the lamp, or lack there of.


    /**
     * <h3>movement and bogies</h3>
     * variables to store bogies, motion, collision, and other movement related things.
     */
    public List<EntityBogie> bogie = new ArrayList<EntityBogie>();
    public List<double[]> bogieXYZ = new ArrayList<double[]>();
    public int accelerator =0; //defines the value of how much to speed up, or brake the
    protected float[] motion = new float[]{0,0,0};
    private boolean isReverse =false;
    public List<HitboxHandler.multipartHitbox> hitboxList = new ArrayList<HitboxHandler.multipartHitbox>();
    private HitboxHandler hitboxHandler = new HitboxHandler();


    /**
     * <h3>long-term stored variables</h3>
     * these variables usually don't change often, or maybe even ever.
     */
    public int[] colors = new int[]{0,0,0}; //allows certain parts of certain trains to be recolored
    public UUID owner = null;  //universal, get train owner
    public String destination ="";

    /**
     * <h3>inventory</h3>
     * variables for the inventory.
     */
    public List<ItemStack> inventory = new ArrayList<ItemStack>(){};//Inventory, every train will have this to some extent or another, //the first two slots are for crafting
    public FluidTank[] tank = new FluidTank[]{};//depending on the train this is either used for diesel, steam, or redstone flux



    /**
    *
    *
    * <h2> Base train Constructor</h2>
    *
    * default constructor for all trains
    *
    * @param owner the owner profile, used to define owner of the entity,
    * @param world the world to spawn the entity in, used in super's super.
    * @param xPos the x position to spawn entity at, used in super's super.
    * @param yPos the y position to spawn entity at, used in super's super.
    * @param zPos the z position to spawn entity at, used in super's super.
    * @param tank used to define the fluid tank(s) if there are any
    *             empty array for no tanks, - steam and nuclear take two tanks. - all other trains take one tank
    *             all tanks besides diesel should use FluidRegistry.WATER
    */

    public EntityTrainCore(UUID owner, World world, double xPos, double yPos, double zPos, FluidTank[] tank){
        super(world);
        posY = yPos;
        posX = xPos;
        posZ = zPos;

        this.owner = owner;
        this.tank = tank;

        //define the number of inventory slots to add to the train based on type for crafting slots and inventory scale
        int craftingSlots =0;
        switch (getType()){
            case 1:case 5: {craftingSlots =2;break;}
            case 2:case 3: case 4:case 6:{craftingSlots =1;break;}
        }
        switch (getSizeInventory()){
            case 1:{craftingSlots+=4;break;}//2x2
            case 2:{craftingSlots+=6;break;}//2x3
            case 3:{craftingSlots+=9;break;}//3x3
            case 4:{craftingSlots+=12;break;}//4x3
            case 5:{craftingSlots+=16;break;}//4x4
        }
        for (int i=0; i<=craftingSlots;i++){
            inventory.add(null);
        }
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
     * <h2>base entity overrides</h2>
     * modify basic entity variables to give different uses/values.
     * entity init runs right before the first tick, but we don't use this.
     * collision and bounding box stuff just return the in-built stuff.
     * getParts returns the list of hitboxes so they can be treated as if they are part of this entity.
     * The positionAndRotation2 override is intended to do the same as the super, except for giving a Y offset on collision, we skip that similar to EntityMinecart.
     */
    public void setOwner(UUID player){owner = player;}
    public UUID getOwnerUUID(){return owner;}
    @Override
    public boolean canBePushed()
    {
        return false;
    }
    @Override
    public boolean isUseableByPlayer(EntityPlayer player){
        //if it is locked, return if player is owner, otherwise return false.
        return isLocked || owner.equals(player.getUniqueID());
    }
    @Override
    public void entityInit(){}
    @Override
    public World func_82194_d(){return worldObj;}
    @Override
    public boolean attackEntityFromPart(EntityDragonPart part, DamageSource damageSource, float damage){
     return HitboxHandler.AttackEvent(this,damageSource,damage);
    }
    @Override
    public Entity[] getParts(){
        return hitboxList.toArray(new HitboxHandler.multipartHitbox[hitboxList.size()]);
    }
    @Override
    public AxisAlignedBB getBoundingBox(){return boundingBox;}
    @Override
    public AxisAlignedBB getCollisionBox(Entity collidedWith){return boundingBox;}
    @Override
    public boolean canBeCollidedWith() {return true;}
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_) {
        this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
        this.setRotation(p_70056_7_, p_70056_8_);
    }


    /**
     * <h2> Data Syncing and Saving </h2>
     *
     * used for syncing the bogies between client and server, syncing the spawn data with client and server(SpawnData), and saving/loading information from world (NBT)
     * @see EntityBogie#readSpawnData(ByteBuf)
     * @see IEntityAdditionalSpawnData
     * @see NBTTagCompound
     * the spawn data will make sure that variables that don't uually sync on spawn, like from the item, get synced.
     * the NBT will make sure that variables save to the world so it will be there next time you load the world up.
     */
    @SideOnly(Side.CLIENT)
    public void addbogies(EntityBogie cart){
        bogie.add(cart);
    }
    @Override
    public void readSpawnData(ByteBuf additionalData) {
        isReverse = additionalData.readBoolean();
        owner = new UUID(additionalData.readLong(), additionalData.readLong());
        //we loop using the offset double length because we expect bogieXYZ to be null.
        for (int i=0; i<getBogieOffsets().size(); i++){
            bogieXYZ.add(new double[]{additionalData.readDouble(), additionalData.readDouble(), additionalData.readDouble()});
        }

    }
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeBoolean(isReverse);
        buffer.writeLong(owner.getMostSignificantBits());
        buffer.writeLong(owner.getLeastSignificantBits());
        for (double[] xyz : bogieXYZ) {
            buffer.writeDouble(xyz[0]);
            buffer.writeDouble(xyz[1]);
            buffer.writeDouble(xyz[2]);
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        isRunning = tag.getBoolean("isrunning");
        isLocked = tag.getBoolean("extended.islocked");
        brake = tag.getBoolean("extended.brake");
        lamp.isOn = tag.getBoolean("extended.lamp");
        lamp.X = tag.getInteger("extended.lamp.x");
        lamp.Y = tag.getInteger("extended.lamp.y");
        lamp.Z = tag.getInteger("extended.lamp.z");
        isReverse = tag.getBoolean("extended.isreverse");
        owner = new UUID(tag.getLong("extended.ownerm"),tag.getLong("extended.ownerl"));
        //read through the bogie positions
        NBTTagList bogieTaglList = tag.getTagList("extended.bogies", 10);
        for (int i = 0; i < bogieTaglList.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = bogieTaglList.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("bogie");

            if (b0 >= 0) {
                bogieXYZ.add(new double[]{nbttagcompound1.getDouble("bogieindex.a." + i),nbttagcompound1.getDouble("bogieindex.b." + i),nbttagcompound1.getDouble("bogieindex.c." + i)});
            }
        }
        //read through the itemstacks
        NBTTagList taglist = tag.getTagList("items", 10);
        for (int i = 0; i < taglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = taglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("slot");

            if (b0 >= 0 && b0 < inventory.size()) {
                inventory.set(b0, ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }

        for (FluidTank tempTank : tank){
            tempTank.readFromNBT(tag);
        }
    }
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        tag.setBoolean("isrunning",isRunning);
        tag.setBoolean("extended.islocked", isLocked);
        tag.setBoolean("extended.brake", brake);
        tag.setBoolean("extended.lamp", lamp.isOn);
        tag.setInteger("extended.lamp.x", lamp.X);
        tag.setInteger("extended.lamp.y", lamp.Y);
        tag.setInteger("extended.lamp.z", lamp.Z);
        tag.setBoolean("extended.isreverse", isReverse);
        tag.setLong("extended.ownerm", owner.getMostSignificantBits());
        tag.setLong("extended.ownerl", owner.getLeastSignificantBits());
        //write the list of bogies
        NBTTagList nbtBogieTaglist = new NBTTagList();
        for (int i = 0; i < bogieXYZ.size(); ++i) {
            if (bogieXYZ.get(i) != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("bogie", (byte)i);
                nbttagcompound1.setDouble("bogieindex.a." + i,bogieXYZ.get(i)[0]);
                nbttagcompound1.setDouble("bogieindex.b." + i,bogieXYZ.get(i)[1]);
                nbttagcompound1.setDouble("bogieindex.c." + i,bogieXYZ.get(i)[2]);
                nbtBogieTaglist.appendTag(nbttagcompound1);
            }
        }
        tag.setTag("extended.bogies", nbtBogieTaglist);
        //write the itemset to a tag list before adding it
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < inventory.size(); ++i) {
            if (inventory.get(i) != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("slot", (byte)i);
                inventory.get(i).writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        tag.setTag("items", nbttaglist);

        for (FluidTank tempTank : tank){
            tempTank.writeToNBT(tag);
        }
    }

    /**
     * <h2> Inventory management </h2>
     * functions for managing the Inventory as defined in:
     * @see IInventory
     * getInventoryName is overidden in the chass that extends this to give it a proper name.
     */
    @Override
    public String getInventoryName() {
        return "";
    }
    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }
    @Override
    public void openInventory() {}
    @Override
    public void closeInventory() {}
    @Override
    public void markDirty() {}
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot>=0 && slot< inventory.size()) {
            return inventory.get(slot);
        }
        return null;
    }
    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        //we return null no matter what, but we want to make sure the slot is properly set as well.
        if (slot>=0 && slot< inventory.size()) {
            inventory.set(slot, null);
        }
        return null;
    }
    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (slot>=0 && slot< inventory.size()) {
            //if subtraction makes slot empty/null then set it to null and return null, otherwise return the stack.
            if (inventory.get(slot).stackSize <= amount ^ inventory.get(slot).stackSize <= 0) {
                inventory.set(slot, null);
            } else {
                return inventory.get(slot).splitStack(amount);
            }
        }
        return null;
    }
    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack) {
        //be sure item stack isn't null, then add the itemstack, and be sure the slot doesn't go over the limit.
        if (itemstack != null && slot >=0 && slot<inventory.size()) {
            if (itemstack.stackSize >= getInventoryStackLimit()) {
                itemstack.stackSize = getInventoryStackLimit();
            }
            inventory.set(slot, itemstack);
        }
    }
    //return if the item can be placed in the slot, for this slot it's just a check if the slot exists, but other things may have slots for specific items Override this to filter that.
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item){
        return (slot>=0 && slot< inventory.size());
    }
    @Override
    public int getSizeInventory(){
        if(inventory != null){
            return inventory.size();
        } else{
            return 0;
        }
    }
    //function for adding an itemstack to the inventory with an offset
    public boolean addItems(int offset, ItemStack itemStack){
        for (int i=offset; i<inventory.size();) {
            if (inventory.get(i).getItem() == null){
                inventory.set(i, itemStack);
                return true;
            } else if (inventory.get(i).getMaxStackSize() <= inventory.get(i).stackSize){
                inventory.get(i).stackSize+=1;
                return true;
            } else {
                i++;
            }
        }
        return false;
    }



    /**
    * <h2> onUpdate </h2>
    *
    * defines what should be done every tick
    * used for:
    * managing the list of bogies which are used for defining position and rotation, respawning them if they disappear.* managing speed, acceleration. and direction.
    * managing rotationYaw and rotationPitch.
    * and calling the fuel handler for fuel consumption.
    * @see FuelHandler#ManageFuel(EntityTrainCore)
    * being sure the train is listed in the main class (for lighting management).
    * @see ClientProxy#onTick(TickEvent.ClientTickEvent)
     *
     * TODO: we need to put back lamp management
     * TODO: yaw rotation isn't right.
    */

    @Override
    public void onUpdate() {
            //be sure bogies exist
            int xyzSize = bogieXYZ.size()-1;
            if (xyzSize > 0) {
                int bogieSize = bogie.size() - 1;
                //always be sure the bogies exist on client and server.
                if (!worldObj.isRemote && bogieSize < 1) {
                    for (double[] pos : bogieXYZ) {
                        //it should never be possible for bogieXYZ to be null unless there is severe server data corruption.
                            EntityBogie spawnBogie = new EntityBogie(worldObj, pos[0], pos[1], pos[2], getEntityId());
                            worldObj.spawnEntityInWorld(spawnBogie);
                            bogie.add(spawnBogie);
                        }
                    bogieSize = xyzSize;
                }

                /**
                 *check if the bogies exist, because they may not yet, and if they do, check if they are actually moving or colliding.
                 * no point in processing movement if they aren't moving or if the train hit something.
                 * if it is clear however, then we need to add velocity to the bogies based on the current state of the train's speed and fuel, and reposition the train.
                 * but either way we have to position the bogies around the train, just to be sure they dont accidentally fly off at some point.
                 */
                if (bogieSize>0){

                    if (hitboxHandler.getCollision(this) & (accelerator==0 && motion[0] + motion[2] == 0)){
                        for (EntityBogie currentBogie : bogie) {
                            currentBogie.setVelocity(0, 0, 0);
                        }
                    } else {
                        float accel = (getAcceleration() * (accelerator /6)) * (((motion[0] + motion[2]) / getMaxSpeed()) * 100);
                        //compensate for if the train is stopped
                        if (accel == 0 && accelerator >0){
                            accel = getAcceleration() *0.10f;
                        } else if (accel ==0 && accelerator <0){
                            accel = -getAcceleration() * 0.10f;
                        }
                        //modify based on fuel
                        if (furnaceFuel ==0){
                                accel =0;
                        }else if (getType() ==1){
                            if (furnaceFuel <= getMaxFuel()/3){
                                //no boiler pressure
                                accel *= 0.1f;
                            }else if (furnaceFuel <= getMaxFuel()/2){
                                //low boiler pressure
                                accel *= 0.5f;
                            }
                            //we don't compensate for normal boiler pressure because there would be no change.
                        }

                        motion = rotatePoint(new float[]{accel, 0.0f, 0.0f}, 0.0f, rotationYaw, 0.0f);
                        //move the bogies, unit of motion, blocks per second 1/20
                        for (EntityBogie currentBogie : bogie) {
                            currentBogie.addVelocity(motion[0], currentBogie.motionY, motion[2]);
                            currentBogie.minecartMove();
                        }
                        //position train
                        setPosition(
                                (bogie.get(bogieSize).posX + bogie.get(0).posX) * 0.5D,
                                (bogie.get(bogieSize).boundingBox.minY + bogie.get(0).boundingBox.minY) * 0.5D,
                                (bogie.get(bogieSize).posZ + bogie.get(0).posZ) * 0.5D);

                        setRotation(
                                (float) (Math.atan2(
                                        bogie.get(bogieSize).posZ - bogie.get(0).posZ,
                                        bogie.get(bogieSize).posX - bogie.get(0).posX)) * RailUtility.degreesF,
                                MathHelper.floor_double(Math.acos(bogie.get(0).posY / bogie.get(bogieSize).posY))
                        );
                    }

                    //align bogies
                    for (int i = 0; i < bogie.size(); ) {
                        float[] var = rotatePoint(new float[]{(float) getBogieOffsets().get(i).doubleValue(), 0.0f, 0.0f}, 0.0f, rotationYaw, 0.0f);
                        bogie.get(i).setPosition(var[0] + posX, bogie.get(i).posY, var[2] + posZ);
                        bogieXYZ.set(i, new double[]{bogie.get(i).posX, bogie.get(i).posY, bogie.get(i).posZ});
                        i++;
                    }
                }

            }

            //this is just to be sure the client proxy knows this train exists.
            if (worldObj.isRemote && xyzSize > 0 && bogieXYZ.get(0)[0] + bogieXYZ.get(0)[1] + bogieXYZ.get(0)[2] != 0.0D) {
                if (!ClientProxy.carts.contains(this)) {
                    ClientProxy.carts.add(this);
                }
            }

        //simple tick management so some code does not need to be run every tick.
        switch (trainTicks) {
            case 5: {
                //deal with the fuel
                if (!worldObj.isRemote && isRunning) {
                    FuelHandler.ManageFuel(this);
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
        trainTicks++;
    }

    /**
     * <h2>Rider offset</h2>
     * this runs every tick to be sure the rider is in the correct position for the
     */
    @Override
    public void updateRiderPosition() {
        if (riddenByEntity != null) {
            if (bogie.size()>1) {

                float[] riderOffset = rotatePoint(new float[]{getRiderOffset(),2f,0}, rotationPitch, rotationYaw, 0);
                riddenByEntity.setPosition(posX + riderOffset[0], posY + riderOffset[1], posZ + riderOffset[2]);
            } else {
                riddenByEntity.setPosition(posX, posY + 2D, posZ);
            }
        }
    }

    /**
     * <h2> acceleration</h2>
     *
     * function for setting the train's speed and whether or not it is reverse.
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
     * <h2>reverse inheritance functions</h2>
     * these functions work as the middleman between normal train functionality and the classes that extend this. for an example:
     * @see FirstTrain#getMaxSpeed()
     */
    public float getMaxSpeed(){return 0;}
    public List<Double> getBogieOffsets(){return new ArrayList<Double>();}
    public int getInventorySize(){return 3;}
    public int getType(){return 0;}
    public int getMaxFuel(){return 100;}
    public float getRiderOffset(){return 0;}
    public float getAcceleration(){return 0.025f;}
    public ItemStack getItem(){return null;}
    public int[] getHitboxPositions(){return new int[]{-1,0,1};}
    public ISound getHorn(){return null;}
    public ISound getRunning(){return null;}

}
