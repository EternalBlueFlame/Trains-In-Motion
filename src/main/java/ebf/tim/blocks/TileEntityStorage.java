package ebf.tim.blocks;


import ebf.tim.TrainsInMotion;
import ebf.tim.utility.TileEntitySlotManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Tile Entity storage container</h1>
 * this is a tile entity for storage, similar to the game's chest, but due to the advanced container class of this mod it can be used for anything from chests to crafting tables.
 * @see TileEntitySlotManager
 * @author Eternal Blue Flame
 */
public class TileEntityStorage extends TileEntity implements IInventory {


    public TileEntityStorage(int type){
        for (int i=0; i< getSizeInventory(); i++){
            items.add(null);
        }
        storageType=type;
    }
    /**the list of item stacks in the inventory*/
    private List<ItemStack> items = new ArrayList<ItemStack>();
    public int storageType=0;

    /**
     * <h2>Syncing</h2>
     */
    /**loads the tile entity's save file*/
    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        storageType=p_145839_1_.getInteger("storageType");
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack item = ItemStack.loadItemStackFromNBT(p_145839_1_);
            if (item.stackSize != 0) {
                setInventorySlotContents(i, item);
            }
        }
    }
    /**saves the tile entity to server world*/
    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setInteger("stroageType", storageType);
        for (ItemStack stack : items) {
            if (stack != null) {
                stack.writeToNBT(p_145841_1_);
            } else {
                new ItemStack(Items.potato, 0).writeToNBT(p_145841_1_);
            }
        }
    }
    /**
     * <h2>inventory management</h2>
     */
    /**gets the number of slots the inventory.*/
    @Override
    public int getSizeInventory() {
        switch (storageType){
            case 1:{return 3;}
            default:{return 9;}
        }
    }


    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot <0 || slot >= getSizeInventory()){
            return null;
        } else {
            return items.get(slot);
        }
    }


    @Override
    public ItemStack decrStackSize(int slot, int stackSize) {
        if (getSizeInventory()>=slot && items.get(slot) != null) {
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


    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
        if (p_70299_1_>=0 && p_70299_1_ < items.size()) {
            items.set(p_70299_1_, p_70299_2_);
        }
    }


    @Override
    public String getInventoryName() {return TrainsInMotion.MODID + ":storage";}

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }
    @Override
    public int getInventoryStackLimit() {return 64;}
    /**checks if the player can interact with this container, usually used for a check if it's already in use or not*/
    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {return true;}

    /**
     * <h2>slot limiter</h2>
     * This is supposed to see if a specific slot will take a specific item. However it's only called from slots we know are actual inventory slots,
     * which we put filters on there.
     * Because of this we don't even need to check the slot, just the item.
     */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {return true;}

    /**
     * <h2>unused</h2>
     * we have to initialize these values, but due to the design of the entity we don't actually use them.
     */
    /**for running functionality when opening the inventory, such as setting it as in use.*/
    @Override
    public void openInventory() {}
    /**for running functionality when closing the inventory, such as setting it as not in use.*/
    @Override
    public void closeInventory() {}
    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {return null;}
    @Override
    public void markDirty() {}
}
