package trains.entities;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import trains.TrainsInMotion;
import trains.utility.FuelHandler;
import trains.utility.LampHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.Math.sqrt;

public class EntityTrainCore extends Entity implements IInventory, IEntityAdditionalSpawnData {

    public float[] acceleration = new float[]{0.0005F,0.0006F,0.0005F}; //the first 3 values are a point curve, representing <35%, 35-70% and >70% to modify how acceleration is handled at each point. //the 4th value defines how much the weight hauled effects acceleration.
    public int trainType=0;//list of train types 0 is null, 1 is steam, 2 is diesel, 3 is electric, 4 is hydrogen, 5 is nuclear steam, 6 is nuclear electric
    public boolean isRunning = false;// if the train is running/using fuel
    public int furnaceFuel = 0; //the amount of fuel in the furnace, only used for steam and nuclear trains
    public int maxFuel =0; //the max fuel in the train's furnace.
    public float maxSpeed = 0; // the max speed
    private int trainTicks =0; //defines the train's tick count.
    public int accelerator =0; //defines the value of how much to speed up, or brake the train.


    //Main Values
    public int[] colors; //allows certain parts of certain trains to be recolored
    public String name;
    public boolean isLocked = false; //mostly used to lock other players from using/accessing parts of the cart/train
    public boolean brake = false; //bool for the train/rollingstock's break.
    public LampHandler lamp = new LampHandler(); //manages the lamp, or lack there of.
    public int GUIID = 0; //id for the GUI
    public UUID owner = null;  //universal, get train owner

    //list of boogies
    public List<MinecartExtended> bogie = new ArrayList<MinecartExtended>();


    public boolean isReverse =false;
    public float spawnDirection =0;
    public int cartYaw =0;
    public float cartPitch=0F;


    //inventory
    public List<ItemStack> inventory = new ArrayList<ItemStack>(){};//Inventory, every train will have this to some extent or another, //the first two slots are for crafting
    public FluidTank[] tank = new FluidTank[]{};//depending on the train this is either used for diesel, steam, or redstone flux


    /**
     * this class defines the core of the Rolling Stock, in most cases the functionality will be altered by the class that extends this, however whatever is generic to rolling stock specifically
     * for things generic to Rolling Stock:
     *
     *
     * for things generic to both these classes:
     * @see MinecartExtended
     *
     * default constructor for setting up variables after this is created
     * @param owner the owner profile, used to define owner of the entity,
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
     * @param GUIid the ID used to define what GUI the entity uses (0 for no GUI).
     */
    public EntityTrainCore(UUID owner, World world, double xPos, double yPos, double zPos, float maxSpeed, float[] acceleration,
                           int type,FluidTank[] tank,int inventorySize, int GUIid){
        super(world);
        this.posY = yPos;
        this.posX = xPos;
        this.posZ = zPos;

        this.acceleration = acceleration;
        trainType = type;
        this.maxSpeed = maxSpeed;
        this.owner = owner;
        this.tank = tank;

        //define the number of inventory slots to add to the train based on type for crafting slots and inventory scale
        int craftingSlots =0;
        switch (type){
            case 1:case 5: {craftingSlots =2;break;}
            case 2:case 3: case 4:case 6:{craftingSlots =1;break;}
        }
        switch (inventorySize){
            case 1:{craftingSlots+=4;break;}//2x2
            case 2:{craftingSlots+=6;break;}//2x3
            case 3:{craftingSlots+=9;break;}//3x3
            case 4:{craftingSlots+=12;break;}//4x3
            case 5:{craftingSlots+=16;break;}//4x4
        }
        for (int i=0; i<=craftingSlots;i++){
            inventory.add(null);
        }

        GUIID = GUIid;

    }

    /**
     * we have to have the constructor for the initial spawn that puts the train in the world, minecraft does this, we don't have to mess with it other than just having it.
     *
     * @param world the world to spawn it in.
     */
    public EntityTrainCore(World world){
        super(world);
        //set the size of the collision box
        this.setSize(2.5F,2.5F);
    }

    /**
     *
     */
    @Override
    public void entityInit(){}

    /**
     * this is basically NBT for entity spawn, to keep data between client and server in sync because some data is not automatically shared.
     */
    @Override
    public void readSpawnData(ByteBuf additionalData) {
        maxSpeed = additionalData.readFloat();
        spawnDirection = additionalData.readFloat();
        isReverse = additionalData.readBoolean();
        owner = new UUID(additionalData.readLong(), additionalData.readLong());
    }
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeFloat(maxSpeed);
        buffer.writeFloat(spawnDirection);
        buffer.writeBoolean(isReverse);
        buffer.writeLong(owner.getMostSignificantBits());
        buffer.writeLong(owner.getLeastSignificantBits());
    }



    @Override
    public boolean interactFirst(EntityPlayer player){
        System.out.println("player tried to use");
        if (this.riddenByEntity == null){
            player.mountEntity(this);
        }


        return true;
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

        if(worldObj.isRemote && trainTicks>0){
            if (!TrainsInMotion.carts.contains(this)) {
                /**
                 * add lamp to main class handlertrain when created, so the main thread can deal with updating the lighting, or not.
                 * @see TrainsInMotion#onTick(TickEvent.ClientTickEvent)
                 */
                TrainsInMotion.carts.add(this);
            }

            cartPitch = MathHelper.floor_double(Math.acos(bogie.get(0).posY / bogie.get(bogie.size()).posY));
            cartYaw = MathHelper.floor_double(sqrt(MathHelper.floor_double(bogie.get(0).cartX*bogie.get(0).cartX) + MathHelper.floor_double(bogie.get(0).cartZ*bogie.get(0).cartZ))/MathHelper.floor_double(bogie.get(0).cartY));

        }

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
                double motion = sqrt(motionX * motionX + motionZ * motionZ);
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
                this.bogie.get(0).addVelocity(x, motionY, z);
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

    /**
     * @see MinecartExtended#readFromNBT(NBTTagCompound)
     */
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        isRunning = tag.getBoolean("train.isRunning");
        trainTicks = tag.getInteger("train.trainTicks");
        trainType = tag.getInteger("train.type");
        isLocked = tag.getBoolean("extended.isLocked");
        brake = tag.getBoolean("extended.brake");
        lamp.isOn = tag.getBoolean("extended.lamp");
        isReverse = tag.getBoolean("extended.isreverse");
        spawnDirection = tag.getFloat("extended.directon");
        owner = new UUID(tag.getLong("extended.ownerM"),tag.getLong("extended.ownerL"));
        //read through the itemstacks
        NBTTagList taglist = tag.getTagList("Items", 10);
        for (int i = 0; i < taglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = taglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < inventory.size()) {
                inventory.add(ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }

        for (int t=0; t<tank.length; t++){
            tank[t].readFromNBT(tag);
        }
        //items with static-esk values that shouldn't need NBT,
        //name, maxSpeed, GUIID, trainType, acceleration, filters, isLoco.
    }
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        tag.setBoolean("train.isRunning",isRunning);
        tag.setInteger("train.ticks", trainTicks);
        tag.setInteger("train.typr", trainType);
        tag.setBoolean("extended.isLocked", isLocked);
        tag.setBoolean("extended.brake", brake);
        tag.setBoolean("extended.lamp", lamp.isOn);
        tag.setBoolean("extended.isreverse", isReverse);
        tag.setFloat("extended.direction", spawnDirection);
        tag.setLong("extended.ownerM", owner.getMostSignificantBits());
        tag.setLong("extended.ownerL", owner.getLeastSignificantBits());
        //write the itemset to a tag list before adding it
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < inventory.size(); ++i) {
            if (inventory.get(i) != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                inventory.get(i).writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        tag.setTag("Items", nbttaglist);

        for (int t=0; t<tank.length; t++){
            tank[t].writeToNBT(tag);
        }
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

    /**
     * Inventory stuff, most of this is self-explanatory.
     * Occasionally some parts are re-defined so that filters may be applied
     */
    @Override
    public String getInventoryName() {
        return name;
    }
    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }
    @Override
    public void openInventory() {}
    @Override
    public void closeInventory() {}
    @Override
    public void markDirty() {}

    @Override
    public ItemStack getStackInSlot(int slot) {
        //be sure the slot exists before trying to return anything,
        if (slot>=0 && slot< inventory.size()) {
            return inventory.get(slot);
        } else {
            return null;
        }
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
        //be sure the slot exists before trying to return anything,
        if (slot>=0 && slot< inventory.size()) {
            //if subtraction makes slot empty/null then set it to null and return null, otherwise return the stack.
            if (inventory.get(slot).stackSize <= amount ^ inventory.get(slot).stackSize <= 0) {
                inventory.set(slot, null);
                return null;
            } else {
                return inventory.get(slot).splitStack(amount);
            }
        } else {
            return null;
        }
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

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    //return if the item can be placed in the slot, for this slot it's just a check if the slot exists, but other things may have slots for specific items, this filters that.
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item){
        return (slot>=0 && slot< inventory.size());
    }

    //return the number of inventory slots
    @Override
    public int getSizeInventory(){
        if(inventory != null){
            return inventory.size();
        } else{
            return 0;
        }
    }

    //modify the player sitting position
    @Override
    public void updateRiderPosition() {
        if (riddenByEntity != null) {
            riddenByEntity.setPosition(posX, posY+getMountedYOffset() + riddenByEntity.getYOffset(), posZ);
        }
    }




    //return if the train can be used by the player, if it's locked, only the owner can use it.
    @Override
    public boolean isUseableByPlayer(EntityPlayer player){
        if (isLocked){
            if(owner.equals(player.getUniqueID())){
                return true;
            } else {
                return false;
            }
        } else{
            return true;
        }
    }


    public void setOwner(UUID player){owner = player;}
    public UUID getOwnerUUID(){return owner;}
    public void setDirection(float direction){spawnDirection = direction;}



}
