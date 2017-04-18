package ebf.tim.tileentities;


import ebf.tim.TrainsInMotion;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.utility.ContainerHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

/**
 * <h1>Tile Entity storage container</h1>
 * this is a tile entity for storage, similar to the game's chest, but due to the advanced container class of this mod it can be used for anything from chests to crafting tables.
 * @see ContainerHandler
 * @author Eternal Blue Flame
 */
public class TileEntityStorage extends TileEntity implements IInventory {

    private List<ItemStack> items = null;

    /**
     * <h2>syncing</h2>
     * syncs data between client and server, also saves the data, for more info:
     * @see GenericRailTransport#readSpawnData(ByteBuf)
     */
    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        for (int i = 0; i < items.size(); i++) {
            ItemStack item = ItemStack.loadItemStackFromNBT(p_145839_1_);
            if (item.stackSize != 0) {
                setInventorySlotContents(i, item);
            }
        }
    }

    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        for (ItemStack stack : items) {
            if (stack != null) {
                stack.writeToNBT(p_145841_1_);
            } else {
                new ItemStack(Items.potato, 0).writeToNBT(p_145841_1_);
            }
        }
    }
    /**
     * <h2>inventory size</h2>
     * @return the number of slots the inventory should have.
     * We do this once for normal and again for this mod's specific size type.
     */
    @Override
    public int getSizeInventory() {return 9;}
    public TrainsInMotion.inventorySizes getInventorySize(){return TrainsInMotion.inventorySizes.THREExTHREE;}

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
    public String getInventoryName() {return TrainsInMotion.MODID + ":storage";}

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }
    @Override
    public int getInventoryStackLimit() {return 64;}
    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {return true;}

    /**
     * <h2>slot limiter</h2>
     * This is supposed to see if a specific slot will take a specific item. However it's only called from slots we know are actual inventory slots, which we put filters on there.
     * Because of this we don't even need to check the slot, just the item.
     */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {return true;}

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
