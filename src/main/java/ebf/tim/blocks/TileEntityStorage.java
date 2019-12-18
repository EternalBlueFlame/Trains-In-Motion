package ebf.tim.blocks;


import ebf.tim.TrainsInMotion;
import ebf.tim.utility.ItemStackSlot;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
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
        int s=400;
        if(type==0){
            //inventory grid
            for (int l = 0; l < 3; ++l) {
                for (int i1 = 0; i1 < 3; ++i1) {
                    inventory.add(new ItemStackSlot(this,s).setCoords( 30 + i1 * 18, 17 + l * 18).setCrafting(true));
                    s++;
                }
            }
            //tile entity's crafting grid
            for (int l = 0; l < 3; ++l) {
                for (int i1 = 0; i1 < 3; ++i1) {
                    inventory.add(new ItemStackSlot(this,s).setCoords( 106 + i1 * 18, 17 + l * 18).setCrafting(false));
                    s++;
                }
            }
        } else {
            inventory.add(new ItemStackSlot(this,400).setCoords( 30 , -2).setCrafting(true)); //ingot
            inventory.add(new ItemStackSlot(this,401).setCoords( 30 , 15).setCrafting(true)); //ties
            inventory.add(new ItemStackSlot(this,402).setCoords( 30 , 33).setCrafting(true)); //ballast

            inventory.add(new ItemStackSlot(this,403).setCoords( 48 , 7).setCrafting(true)); //wires
            inventory.add(new ItemStackSlot(this,404).setCoords( 48 , 25).setCrafting(true));//augument slot

            inventory.add(new ItemStackSlot(this,405).setCoords( 124 , -2).setCrafting(true));//old shape input

            inventory.add(new ItemStackSlot(this,406).setCoords( 124 , 33).setCrafting(false)); //output

        }
        storageType=type;
    }
    /**the list of item stacks in the inventory*/
    public List<ItemStackSlot> inventory = new ArrayList<ItemStackSlot>();
    public int storageType=0;
    public int[] extraData = null;
    public int outputPage=0;
    public boolean multiPage=false;

    /**
     * <h2>Syncing</h2>
     */
    /**loads the tile entity's save file*/
    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        storageType=p_145839_1_.getInteger("storageType");
        int extraDataLength=p_145839_1_.getInteger("extraDataLength");
        if(extraDataLength>0){
            extraData = new int[extraDataLength];
            for(int i=0; i<extraDataLength;i++){
                extraData[i]=p_145839_1_.getInteger("extraData_"+i);
            }
        }
        if (getSizeInventory()>0) {
            for (int i=0;i<getSizeInventory();i++) {
                NBTTagCompound invTag = p_145839_1_.getCompoundTag("transportinv."+i);
                if (invTag!=null) {
                    inventory.get(i).setSlotContents(ItemStack.loadItemStackFromNBT(invTag));
                }
            }
        }
    }
    /**saves the tile entity to server world*/
    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setInteger("stroageType", storageType);
        p_145841_1_.setInteger("extraDataLength", extraData==null?0:extraData.length);
        if(extraData!=null){
            for(int i=0; i<extraData.length;i++){
                p_145841_1_.setInteger("extraData_"+i,extraData[i]);
            }
        }
        if (inventory!=null) {
            for (int i=0;i<getSizeInventory();i++) {
                NBTTagCompound invTag = new NBTTagCompound();
                if(inventory.get(i)!=null && inventory.get(i).getStack()!=null) {
                    inventory.get(i).getStack().writeToNBT(invTag);
                }
                p_145841_1_.setTag("transportinv."+i, invTag);
            }
        }
    }
    /**
     * <h2>inventory management</h2>
     */
    /**gets the number of slots the inventory.*/
    @Override
    public int getSizeInventory() {
        return inventory.size();
    }


    @Override
    public @Nullable ItemStack getStackInSlot(int slot) {
        if (inventory == null || slot <0 || slot >= inventory.size()){
            return null;
        } else {
            return inventory.get(slot).getStack();
        }
    }



    @Override
    public ItemStack decrStackSize(int slot, int stackSize) {
        if (inventory!= null && getSizeInventory()>=slot) {
            return inventory.get(slot).decrStackSize(stackSize);
        } else {
            return null;
        }
    }


    public ItemStackSlot getSlotIndexByID(int id){
        for(ItemStackSlot s : inventory){
            if (s.getSlotID() == id){
                return s;
            }
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        if (inventory != null && slot >=0 && slot <= getSizeInventory()) {
            inventory.get(slot).setSlotContents(itemStack);
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
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        switch (storageType){
            case 1:{
                if(slot==0){return OreDictionary.getOres("ingot").contains(itemStack);}
                if(slot==1||slot==2){
                    //todo: if block.modid==chisel return false;
                    return Block.getBlockFromItem(itemStack.getItem())!=null && Block.getBlockFromItem(itemStack.getItem()).isOpaqueCube();
                }
            }
        }
        return true;
    }


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
