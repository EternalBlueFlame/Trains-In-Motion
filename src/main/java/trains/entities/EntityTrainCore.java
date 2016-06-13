package trains.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

import java.util.UUID;

public class EntityTrainCore extends MinecartExtended implements IInventory {

    //Main Values
    public FluidTank tank; //depending on the train this is either used for diesel, steam, or redstone flux
    public ItemStack[] inventory;//Inventory, every train will have this to some extent or another,
    private int slotsFilled = 0; //used to manage
    public float maxSpeed; // the train's max speed
    public float[] acceleration; //the first 3 values are a point curve, representing 0-35%, 35-70% and >70% to modify how acceleration is handled at each point. //the 4th value defines how much the weight hauled effects acceleration.
    public int trainType=0;//list of train types 0 is null, 1 is steam, 2 is diesel, 3 is electric
    public boolean isRunning = false;// if the train is running/using fuel
    public int[] previousLampPosition = new int[]{0,0,0}; //this is the position of the light previously, only two lights per train wille ver exist at one time.
    private int ticks = 0; //tick count.
    public int GUIID = 0; // id for the GUI




    //railcraft variables
    public String destination = "";  //railcraft destination
    public boolean isLoco = false;  //if this can accept destination tickets, aka is a locomotive
    public UUID owner = null;  //universal, get train owner

    //default constructor for registering entity
    public EntityTrainCore(World world){
        super(world);
    }
    //default constructor that we actually use.
    public EntityTrainCore(UUID owner, World world, double xPos, double yPos, double zPos, float maxSpeed, float[] acceleration, int inventorySlots, int type /*1-steam, 2-diesel, 3-electric*/, int GUIid) {
        super(world,xPos, yPos, zPos);
        super.isLoco = true;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.trainType = type;
        this.inventory = new ItemStack[inventorySlots];
        this.GUIID = GUIid;
        this.owner = owner;
    }
    //function to run every tick
    @Override
    public void onUpdate(){
        super.onUpdate();
        ticks++;
        /*/ for managing the lamp, will need to implement it better later. Maybe do a client side only to change lighting of individual blocks?
        if(ticks %5 ==0 && previousLampPosition != new int[]{MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ+2)}){
            if(previousLampPosition != new int[]{0,0,0}) {
                worldObj.setBlockToAir(previousLampPosition[0], previousLampPosition[1], previousLampPosition[2]);
            }
            previousLampPosition=new int[]{MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ+2)};

            if (lamp && worldObj.isAirBlock(previousLampPosition[0], previousLampPosition[1], previousLampPosition[2])) {
                worldObj.setBlock(previousLampPosition[0], previousLampPosition[1], previousLampPosition[2], new BlockLight());
                System.out.println("created lamp child");
            }
        }
        /*/
    }
    //technically this is a normal minecart, so return the value for that, which isn't in the base game or another mod.
    @Override
    public int getMinecartType() {
        return 1001;
    }
    /*/
    Inventory stuff, most of this is self-explanatory.
    /*/
    @Override
    public String getInventoryName() {
        return name;
    }
    @Override
    public void openInventory() {}
    @Override
    public void closeInventory() {}

    //re calculate stored variables when the entity needs refreshing
    @Override
    public void markDirty() {
        if (!worldObj.isRemote) {
            slotsFilled = 0;
            for (int i = 0; i < getSizeInventory(); i++) {
                if (getStackInSlot(i) != null) {
                    slotsFilled++;
                }
            }
            //send the update packet
        }
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        //be sure the slot exists before trying to return anything,
        if (slot>=0 && slot< inventory.length) {
            return inventory[slot];
        } else {
            return null;
        }
    }
    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        //we return null no matter what, but we want to make sure the slot is properly set as well.
        if (slot>=0 && slot< inventory.length) {
            inventory[slot] = null;
        }
        return null;
    }
    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        //be sure the slot exists before trying to return anything,
        if (slot>=0 && slot< inventory.length) {
            //if subtraction makes slot empty/null then set it to null and return null, otherwise return the stack.
            if (inventory[slot].stackSize <= amount ^ inventory[slot].stackSize <= 0) {
                inventory[slot] = null;
                return null;
            } else {
                return inventory[slot].splitStack(amount);
            }
        } else {
            return null;
        }
    }
    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack) {
        //be sure item stack isn't null, then add the itemstack, and be sure the slot doesn't go over the limit.
        if (itemstack != null) {
            inventory[slot] = itemstack;
            if (itemstack.stackSize >= getInventoryStackLimit()) {
                itemstack.stackSize = getInventoryStackLimit();
            }
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    //return if the item can be placed in the slot, for this slot it's just a check if the slot exists, but other things may have slots for specific items, this filters that.
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item){return (slot>=0 && slot< inventory.length);}

    //return the number of inventory slots
    @Override
    public int getSizeInventory(){
        if(inventory != null){
            return inventory.length;
        } else{
            return 0;
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

}
