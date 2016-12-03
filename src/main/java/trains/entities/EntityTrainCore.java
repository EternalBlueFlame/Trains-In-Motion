package trains.entities;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import trains.utility.FuelHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h2> Train core</h2>
 * this is the management core for all train
 */
public class EntityTrainCore extends GenericRailTransport implements IInventory {

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

    @Override
    public boolean isUseableByPlayer(EntityPlayer player){
        //if it is locked, return if player is owner, otherwise return false.
        return isLocked || owner.equals(player.getUniqueID());
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
        super.writeEntityToNBT(tag);
        tag.setBoolean("isrunning",isRunning);
        tag.setBoolean("extended.brake", brake);
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
     * <h2> process train movement</h2>
     * called by the super class to figure out the amount of movement to apply every tick
     * @see GenericRailTransport#onUpdate()
     */
    public float processMovement(){
        if (hitboxHandler.getCollision(this) & (accelerator==0 && motion[0] + motion[2] == 0) & furnaceFuel==0){
            //if we shouldn't be moving, return 0.
            return 0;
        } else {
            float accel = (getAcceleration() * (accelerator / 6)) * (((motion[0] + motion[2]) / getMaxSpeed()) * 100);
            //compensate for if the train is stopped
            if (accel == 0 && accelerator > 0) {
                accel = getAcceleration() * 0.10f;
            } else if (accel == 0 && accelerator < 0) {
                accel = -getAcceleration() * 0.10f;
            }
            //modify based on fuel
            if (getType() == 1) {
                if (furnaceFuel <= getMaxFuel() / 3) {
                    //no boiler pressure
                    accel *= 0.1f;
                } else if (furnaceFuel <= getMaxFuel() / 2) {
                    //low boiler pressure
                    accel *= 0.5f;
                }
                //we don't compensate for normal/high boiler pressure because there would be no change.
                //TODO maybe a compensation for superheated boiler pressure?
            }
            return accel;
        }
    }

    /**
     * <h2> on entity update</h2>
     * after the super method is dealt with,
     * @see GenericRailTransport#onUpdate()
     * every 5 ticks we also need to deal with the fuel.
     * @see FuelHandler#ManageFuel(EntityTrainCore)
     */
    @Override
    public void onUpdate(){
        super.onUpdate();

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
    public int getInventorySize(){return 3;}
    public int getMaxFuel(){return 100;}
    public float getAcceleration(){return 0.025f;}
    public ISound getHorn(){return null;}
    public ISound getRunning(){return null;}

}
