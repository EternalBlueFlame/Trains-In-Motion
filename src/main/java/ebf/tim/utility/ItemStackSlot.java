package ebf.tim.utility;

import ebf.tim.entities.GenericRailTransport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eternal Blue Flame
 * TODO: add support for tile entities post 0.2.7.
 */
public class ItemStackSlot extends Slot {

    private ItemStack stack = null;
    private int slotID;

    public ItemStackSlot(IInventory host, int slot){
        super(host, slot, 0,0);
    }

    public ItemStackSlot(IInventory host, int slot, int x, int y){
        super(host, slot, 0,0);
        setSlot(slot);
        slotID=slot;
        xDisplayPosition=x;
        yDisplayPosition=y;
    }

    public ItemStackSlot setCoords(int x, int y){
        xDisplayPosition=x;
        yDisplayPosition=y;
        return this;
    }
    public ItemStackSlot setSlotID(int slot){
        slotID=slot;
        return this;
    }
    public ItemStackSlot setSlot(int slot){
        this.slotNumber=slot;
        return this;
    }

    public Item getItem(){
        return getStack()!=null?getStack().getItem():null;
    }

    public boolean equals(ItemStack other){
        if (getStack()==null || other ==null){
            return getStack() == null && other == null;
        }
        return getStack().getItem() == other.getItem() && (getStack().getTagCompound()) == other.getTagCompound();
    }

    public NBTTagCompound writeToNBT(){
        return getStack()!=null?getStack().writeToNBT(new NBTTagCompound()):null;
    }

    public int getStackSize(){
        return getStack()!=null?getStack().stackSize:0;
    }


    public ItemStack mergeStack(ItemStack itemStack){
        if (isItemValid(itemStack)) {
            if (!getHasStack()) {
                return setSlotContents(itemStack) ? null : itemStack;
            } else {
                if (equals(itemStack) && getStack().stackSize < getStack().getMaxStackSize()) {
                    if (itemStack.stackSize <= getStack().getMaxStackSize() - getStack().stackSize) {
                        getStack().stackSize += itemStack.stackSize;
                        return null;
                    } else {
                        itemStack.stackSize -= getStack().getMaxStackSize() - getStack().stackSize;
                        getStack().stackSize = getStack().getMaxStackSize();
                        return itemStack;
                    }
                }
            }
        }
        return itemStack;
    }

    public boolean setSlotContents(@Nullable ItemStack stack){
        if (isItemValid(stack) || stack == null) {
            if (!(inventory instanceof GenericRailTransport)) {
                inventory.setInventorySlotContents(slotNumber, stack);
            } else {
                this.stack = stack;
            }
            this.onSlotChanged();
            return true;
        }
        return false;
    }

    /**
     * if par2 has more items than par1, onCrafting(item,countIncrease) is called
     */
    public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_) {
        if (p_75220_1_ != null && p_75220_2_ != null && p_75220_1_.getItem() == p_75220_2_.getItem()) {
            int i = p_75220_2_.stackSize - p_75220_1_.stackSize;

            if (i > 0) {
                this.onCrafting(p_75220_1_, i);
            }
        }
    }

    /*
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     protected void onCrafting(ItemStack p_75210_1_, int p_75210_2_) {}
     */

    /*
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     protected void onCrafting(ItemStack p_75208_1_) {}
     */

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    @Override
    public boolean isItemValid(ItemStack p_75214_1_) {
        return !(inventory instanceof GenericRailTransport) || inventory.isItemValidForSlot(getSlotID(), p_75214_1_);
    }

    /**
     * Helper fnct to get the stack in the slot.
     */
    @Override
    public ItemStack getStack() {
        return inventory instanceof GenericRailTransport?stack:
                inventory!=null && inventory.getSizeInventory()>slotNumber?
                        inventory.getStackInSlot(slotNumber):null;
    }

    /**
     * Helper method to put a stack in the slot.
     * @deprecated use {@link #setSlotContents(ItemStack)} instead because it can return whether ot not it actually did it.
     */
    @Override
    @Deprecated
    public void putStack(ItemStack p_75215_1_) {
        setSlotContents(p_75215_1_);
    }

    /**
     * Called when the stack in a Slot changes, only for tile entities.
     */
    @Override
    public void onSlotChanged() {
        inventory.markDirty();
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    @Override
    public int getSlotStackLimit() {
        return getHasStack()?getStack().getMaxStackSize():64;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    @Override
    public ItemStack decrStackSize(int p_75209_1_) {
        if(!getHasStack() ||p_75209_1_ >=getStack().stackSize){
            setSlotContents(null);
        } else {
            getStack().stackSize-=p_75209_1_;
        }
        return getStack();
    }

    /**
     * returns if the target Iinventory is from the same class as this
     */
    @Override
    @Deprecated
    public boolean isSlotInInventory(IInventory p_75217_1_, int p_75217_2_) {
        return inventory.getClass() == p_75217_1_.getClass();
    }

    /**
     * Return whether this slot's stack can be taken from this slot.
     */
    @Override
    public boolean canTakeStack(EntityPlayer p_82869_1_) {
        return !(inventory instanceof GenericRailTransport) || ((GenericRailTransport) inventory).getPermissions(p_82869_1_, false, false);
    }

    /**
     * Retrieves the index in the inventory for this slot, this value should typically not
     * be used, but can be useful for some occasions.
     *
     * @return Index in associated inventory for this slot.
     */
    @Override
    public int getSlotIndex() { return slotNumber; }


    public int getSlotID(){return slotID;}
}
