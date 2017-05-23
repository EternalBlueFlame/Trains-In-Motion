package ebf.tim.utility;

import ebf.tim.blocks.TileEntityStorage;
import ebf.tim.registry.TransportRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

/**
 * <h1>Tile Entity Container</h1>
 * creates the functionality for item storage and crafting for tile entities.
 *
 * @author Eternal Blue Flame
 */
public class TileEntitySlotManager extends Container{
    /**the tile entity*/
    private TileEntityStorage craftingTable;

    /**
     * <h2>Server-side inventory GUI for tile entities</h2>
     * works as the middleman between the client GUI and the entity on client and server.
     *
     * this mostly just runs loops to add and place all the inventory slots that will appear and can be used on client.
     * these slots draw the item, if there is one, and the tint when the cursor hovers over the slot.
     */
    public TileEntitySlotManager(InventoryPlayer iinventory, TileEntityStorage block) {

        //player inventory
        for (int ic = 0; ic < 9; ic++) {
            for (int ir = 0; ir < 3; ir++) {
                addSlotToContainer(new Slot(iinventory, (((ir * 9) + ic) + 9), 8 + (ic * 18), 84 + (ir * 18)));
            }
        }
        //player toolbar
        for (int iT = 0; iT < 9; iT++) {
            addSlotToContainer(new Slot(iinventory, iT, 8 + iT * 18, 142));
        }

        //tile entity reference
        if (craftingTable == null) {
            craftingTable = block;
        }



        //tile entity's output slot
        this.addSlotToContainer(new SlotCrafting(iinventory.player, craftingTable, craftingTable, 10, 124, 35));
        //tile entity's crafting grid
        for (int l = 0; l < 3; ++l) {
            for (int i1 = 0; i1 < 3; ++i1) {
                this.addSlotToContainer(new craftingSlot(craftingTable, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
            }
        }
        onCraftMatrixChanged(craftingTable);
    }



    /**
     * <h2>Inventory sorting and shift-clicking</h2>
     * sorts items from the players inventory to the entity's inventory, and the reverse.
     * This happens with shift click and during some other circumstances.
     * We manage player inventory first because we bound it first, plus it's more reliable to be the size we expected.
     * TODO: doesn't seem to work for the crafting slot
     * TODO: should be able to use this to define auto sorting for crafting.
     * TODO: should be able to use this to define pipe input, either that or it has to be through ISidedInventory
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        //be sure the slot isn't null
        if (this.inventorySlots.size()>=slot) {
            Slot stack = (Slot) this.inventorySlots.get(slot);
            Slot tempSlot;
            if (stack.getStack() == null) {
                return null;
            }
            //make a copy of the stack so we can modify the original and still return it as if nothing happened.
            ItemStack returnStack = stack.getStack().copy();
            //now modify the original itemstack
            for (int currentSlot = 0; currentSlot < this.inventorySlots.size(); currentSlot++) {
                tempSlot = (Slot) this.inventorySlots.get(currentSlot);
                //if the item is in the train inventory
                if (slot > 35 || currentSlot > 35) {
                    //if the slot is empty, just move it and return null
                    if (!tempSlot.getHasStack()) {
                        tempSlot.putStack(stack.getStack());
                        ((Slot) this.inventorySlots.get(slot)).decrStackSize(stack.getStack().stackSize);
                        return null;
                        //if the slot contains the same item, and has room to add this stack to it, then add it and return null
                    } else if (tempSlot.getStack().getItem().equals(stack.getStack().getItem()) &&
                            tempSlot.getStack().getMaxStackSize() > stack.getStack().stackSize + tempSlot.getStack().stackSize) {
                        tempSlot.getStack().stackSize += stack.getStack().stackSize;
                        ((Slot) this.inventorySlots.get(slot)).decrStackSize(stack.getStack().stackSize);
                        return null;
                    }
                }
            }
            //if this is a crafting GUI, then use the GUI matrix to handle it
            onCraftMatrixChanged(craftingTable);
            //if the stack did not transfer, then return the original stack
            return returnStack;
        }
        //if the slot was null, return null
        return null;
    }

    /**
     * <h2>can interact with inventory</h2>
     * just a simple return true/false if the train is dead, or if the owner locked the transport and the player trying to access isn't the owner.
     * it's also a null check to be sure that no one tries to interact with an errored entity.
     */
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return craftingTable != null;
    }


    /**
     * <h2>craft matrix updater redirect</h2>
     * vanilla redirect for updating the craft matrix.
     * @see TileEntitySlotManager#findMatchingRecipe() for the actual use.
     */
    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
        this.craftingTable.setInventorySlotContents(10, findMatchingRecipe());
    }

    /**
     * <h2>recipe manager</h2>
     * manages crafting recipes, and their outputs.
     * the current implementation only supports shaped recipes.
     */
    private ItemStack findMatchingRecipe() {
        //if this is for a crafting table
        if (craftingTable != null) {
            int index=0;
            //for every train and rollingstock in the registry, check the recipe
            while (TransportRegistry.listTrains(index)!=null) {
                TransportRegistry registry = TransportRegistry.listTrains(index);
                int i = 0;
                //check each item value, if one is false, set i to 20, which makes it return null
                for (; i < 8; i++) {
                    if (craftingTable.getStackInSlot(i) == null) {
                        if (registry.recipe[i] != null) {
                            i = 20;
                        }
                    } else {
                        if (registry.recipe[i] != craftingTable.getStackInSlot(i).getItem()) {
                            i = 20;
                        }
                    }
                }
                //if i is 8 (remember this is a 0 system so 8 is actually 9 slots) that means the recipe was correct, so return the proper itemstack.
                if (i == 8) {
                    return new ItemStack(registry.item, 1);
                }
                index++;
            }
        }
        return null;
    }


    /**
     * <h2>crafting slot</h2>
     * this is a custom crafting slot to make sure that the GUI and inventory update properly.
     * this copies vanilla behavior but forces the craft matrix change event so we can track changes.
     *
     * @author Eternal Blue Flame
     */
    private class craftingSlot extends Slot{
        public craftingSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(p_i1824_1_,p_i1824_2_,p_i1824_3_,p_i1824_4_);
        }
        @Override
        public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_) {
            super.onSlotChange(p_75220_1_,p_75220_2_);
            onCraftMatrixChanged(craftingTable);
        }
        @Override
        public void onSlotChanged(){
            super.onSlotChanged();
            onCraftMatrixChanged(craftingTable);
        }
    }

}
