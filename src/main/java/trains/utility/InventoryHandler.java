package trains.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.entities.GenericRailTransport;
import trains.tileentities.TileEntityStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Inventory management</h1>
 * sores the inventory variables, handles a good degree of the inventory processing, and NBT management.
 * @see ContainerHandler for the related code.
 *
 * @author Eternal Blue Flame
 */
public class InventoryHandler implements IInventory{
    private GenericRailTransport host;
    private TileEntityStorage blockHost;
    private List<ItemStack> items = new ArrayList<ItemStack>();

    /**
     * <h2>entity constructor</h2>
     * sets the host variable, and inventory size then creates an instance of the class, this can be re-used for any train or rollingstock.
     */
    public InventoryHandler(GenericRailTransport host){
        if (host != null) {
            this.host = host;
            while (items.size() < getSizeInventory()) {
                items.add(null);
            }
        } else {
            items.add(null);
        }
    }
    /**
     * <h2>tile entity constructor</h2>
     * sets the host variable, and inventory size then creates an instance of the class, this can be re-used for any tile entity.
     */
    public InventoryHandler(TileEntity craftingTable){
        if (craftingTable instanceof TileEntityStorage){
            while (items.size() < 9) {
                items.add(null);
            }
            blockHost = (TileEntityStorage) craftingTable;
        }
    }

    /**
     * <h2>inventory size</h2>
     * @return the number of slots the inventory should have.
     * if it's a train we have to calculate the size based on the type and the size of inventory its supposed to have.
     */
    @Override
    public int getSizeInventory() {
        if (host instanceof EntityTrainCore) {
            int size =1;
            if (host.getType()== TrainsInMotion.transportTypes.STEAM || host.getType()== TrainsInMotion.transportTypes.NUCLEAR_STEAM){
                size=2;
            }
            return size+ (host.getInventorySize().getCollumn() * host.getInventorySize().getRow());

        } else if (blockHost != null){
            return 9;
        }
        return 0;
    }

    /**
     * <h2>get item</h2>
     * @return the item in the requested slot
     */
    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot <0 || slot >= getSizeInventory()){
            return null;
        } else {
            return items.get(slot);
        }
    }

    /**
     * <h2>decrease stack size</h2>
     * @return the itemstack with the decreased size. If the decreased size is equal to or less than the current stack size it returns null.
     */
    @Override
    public ItemStack decrStackSize(int slot, int stackSize) {
        if (items.size()>=slot && items.get(slot) != null) {
            ItemStack itemstack;

            if (items.get(slot).stackSize <= stackSize) {
                itemstack = items.get(slot).copy();
                items.set(slot, null);

                return itemstack;
            } else {
                itemstack = items.get(slot).splitStack(stackSize);
                if (items.get(slot).stackSize == 0) {
                    items.set(slot, null);
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    /**
     * <h2>Set slot</h2>
     * sets the slot contents, this is a direct override so we don't have to compensate for anything.
     */
    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
        if (p_70299_1_>=0 && p_70299_1_ < items.size()) {
            items.set(p_70299_1_, p_70299_2_);
        }
    }

    /**
     * <h2>name and stack limit</h2>
     * These are grouped together because they are pretty self-explanatory.
     */
    @Override
    public String getInventoryName() {
        if (host != null) {
            return host.getItem().getUnlocalizedName();
        } else {
            return TrainsInMotion.MODID + ":storage";
        }
    }
    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }
    @Override
    public int getInventoryStackLimit() {
        if (host != null) {
            return 64;
        } else if (blockHost!= null) {
            return 64;
        } else {
            return 0;
        }
    }

    /**
     * <h2>is Locked</h2>
     * returns if the entity is locked, and if it is, if the player is the owner.
     * This makes sure the inventory can be accessed by anyone if its unlocked and only by the owner when it is locked.
     * if it's a tile entity, it's just another null check to be sure no one crashes.
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        if (host != null){
            return host.getPermissions(p_70300_1_, false);
        } else {
            return blockHost != null;
        }
    }

    /**
     * <h2>slot limiter</h2>
     * this limits certain items to certain slots, most of the functionality is dealt with from the fuel handler.
     * @see FuelHandler
     */
    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        if (host instanceof EntityTrainCore) {
            if (p_94041_1_ == 0) {
                return FuelHandler.isFuel(p_94041_2_, host);
            } else if (p_94041_1_ ==1) {
                return FuelHandler.isWater(p_94041_2_, host);
            } else {
                return true;
            }
        } else{
            return blockHost != null;
        }
    }

    /**
     * <h2>NBT functionality</h2>
     * we manage the functionality for reading and writing NBT tags of the inventory here, to simplify it in other classes.
     */
    public NBTTagCompound writeNBT(){
        NBTTagCompound nbtitems = new NBTTagCompound();
        for (int i = 0; i < items.size(); ++i) {
            if (items.get(i) != null) {
                items.get(i).writeToNBT(nbtitems);
            } else {
                new ItemStack(Items.potato, 0).writeToNBT(nbtitems);
            }
        }
        return nbtitems;
    }
    public void readNBT(NBTTagCompound tag, String tagName){
        NBTTagCompound nbtitems = tag.getCompoundTag(tagName);
        for (int i = 0; i < items.size(); i++) {
            ItemStack item = ItemStack.loadItemStackFromNBT(nbtitems);
            if (item.stackSize != 0) {
                setInventorySlotContents(i, item);
            }
        }
    }

    /**
     * <h2>Add item to train inventory</h2>
     * custom function for adding items to the train's inventory.
     * similar to a container's TransferStackInSlot function, this will automatically sort an item into the inventory.
     * if there is no room in the inventory for the item, it will drop on the ground.
     */
    public void addItem(ItemStack item){
        for (int i=1; i<getSizeInventory();i++){
            if (i==1){
                if (host.getType() == TrainsInMotion.transportTypes.STEAM || host.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM){
                    i++;
                }
            }

            if (getStackInSlot(i) ==null){
                setInventorySlotContents(i, item);
                return;
            } else if (getStackInSlot(i).getItem() == item.getItem() &&
                    item.stackSize + items.get(i).stackSize <= item.getMaxStackSize()){
                setInventorySlotContents(i, new ItemStack(item.getItem(), item.stackSize + items.get(i).stackSize));
                return;
            }
        }
        if (host != null) {
            host.dropItem(item.getItem(), item.stackSize);
        }
    }

    /**
     * <h2>unused</h2>
     * we have to initialize these values, but due to the design of the entity we don't actually use them.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {return null;}
    @Override
    public void markDirty() {}
    @Override
    public void openInventory() {}
    @Override
    public void closeInventory() {}
}
