package trains.entities;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.input.Keyboard;
import trains.utility.BlockLight;

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

    @Override
    public int getMinecartType() {
        return 1001;
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
        if (i>=0 && i< inventory.length) {
            return inventory[i];
        } else {
            System.out.println("inventory slot " + i + " is not a thing");
            return null;
        }
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
