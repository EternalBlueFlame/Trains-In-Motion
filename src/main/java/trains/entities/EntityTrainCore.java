package trains.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.input.Keyboard;
import trains.TrainsInMotion;

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
    public TileEntity lampChild = null;




    //railcraft variables
    public String destination = "";  //railcraft destination
    public boolean isLoco = false;  //if this can accept destination tickets, aka is a locomotive
    public UUID owner = null;  //universal, get train owner

    public EntityTrainCore(World world, double xPos, double yPos, double zPos, float maxSpeed, float[] acceleration, int inventorySlots, int type /*1-steam, 2-diesel, 3-electric*/) {
        super(world, xPos, yPos, zPos);
        super.isLoco = true;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.trainType = type;
        this.inventory = new ItemStack[inventorySlots];
    }
    //function to run every tick
    @Override
    public void onUpdate(){
        super.onUpdate();
        if (lamp && lampChild ==null) {
            lampChild = new EntityMovingLight();
            worldObj.addTileEntity(lampChild);
        } else if (!lamp && lampChild != null){
            lampChild=null;
        }

        if(worldObj.isRemote && riddenByEntity != null && riddenByEntity.ridingEntity != null && riddenByEntity.ridingEntity == this) {
            if (Keyboard.isKeyDown(Keyboard.KEY_L)) {
                System.out.println("lamp");
                lamp = !lamp;
            }
        }
    }
    /*/
    Inventory stuff
    /*/
    @Override
    public String getInventoryName() {
        return name;
    }
    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public void markDirty() {
        if (!worldObj.isRemote) {
            this.slotsFilled = 0;
            for (int i = 0; i < getSizeInventory(); i++) {
                if (getStackInSlot(i) != null) {
                    slotsFilled++;
                }
            }
            //send the update packet
        }
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory[i];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.inventory[slot] != null) {
            this.inventory[slot] = null;
            return this.inventory[slot];
        } else {
            return null;
        }
    }
    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (inventory[slot] != null) {
            if (inventory[slot].stackSize <= amount ^ inventory[slot].stackSize <= 0) {
                inventory[slot] = null;
                return inventory[slot];
            } else {
                return inventory[slot].splitStack(amount);
            }
        }
        else {
            return null;
        }
    }
    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack) {
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
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item){return true;}
    //return the number of inventory slots
    @Override
    public int getSizeInventory(){
        if(inventory != null){
            return inventory.length;
        } else{
            return 0;
        }
    }
    @Override
    public boolean isUseableByPlayer(EntityPlayer player){
        if (this.isLocked){
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
