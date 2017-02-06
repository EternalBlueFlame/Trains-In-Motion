package trains.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.entities.GenericRailTransport;
import trains.registry.TrainRegistry;
import trains.tileentities.TileEntityStorage;

/**
 * <h1>Container manager</h1>
 * creates the functionality for item storage and crafting.
 * @see InventoryHandler for the inventory variables.
 *
 * @author Eternal Blue Flame
 */
public class ContainerHandler extends Container{
    private GenericRailTransport railTransport;
    private TileEntityStorage craftingTable;
    public IInventory craftResult = new InventoryCraftResult();
    private boolean isCrafting;



    /**
     * <h2>Server-side inventory GUI for trains and rollingstock</h2>
     * works as the middleman between the client GUI and the entity on client and server.
     *
     * this mostly just runs loops to add and place all the inventory slots that will appear and can be used on client.
     */
    public ContainerHandler(InventoryPlayer iinventory, GenericRailTransport entityTrain,boolean isCrafting) {
        this.isCrafting = isCrafting;

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

        //transport reference
        if (railTransport == null) {
            railTransport = entityTrain;
        }


        if (entityTrain instanceof EntityTrainCore) {
            //define the transport's inventory size
            int slot=1;
            if (entityTrain.getType() == TrainsInMotion.transportTypes.STEAM || entityTrain.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM){
                slot=2;
            }
            //fuel slot
            addSlotToContainer(new Slot(((EntityTrainCore) railTransport).inventory, 0, 8, 53));
            if (slot == 2) {
                //water slot
                addSlotToContainer(new Slot(((EntityTrainCore) railTransport).inventory, 1, 35, 53));
            }
            if (railTransport.getRiderOffset().length >1){
                addSlotToContainer(new Slot(((EntityTrainCore) railTransport).inventory,2,0,0));
                slot++;
            }


            //transport inventory
            for (int ia = 0; ia > -entityTrain.getInventorySize().getRow(); ia--) {
                for (int ib = 0; ib < entityTrain.getInventorySize().getCollumn(); ib++) {
                    addSlotToContainer(new Slot(((EntityTrainCore) railTransport).inventory, slot, 98 + (ib * 18), (ia * 18) + 44));
                    slot++;
                }
            }
        } else if (isCrafting){
            //crafting item output slot
            this.addSlotToContainer(new SlotCrafting(iinventory.player, ((EntityTrainCore) railTransport).inventory, this.craftResult, 10, 124, 35));
            //crafting grid.
            for (int l = 0; l < 3; ++l) {
                for (int i1 = 0; i1 < 3; ++i1) {
                    this.addSlotToContainer(new craftingSlot(((EntityTrainCore) railTransport).inventory, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
                }
            }

            onCraftMatrixChanged(craftResult);
        }
    }

    /**
     * <h2>Server-side inventory GUI for tile entities</h2>
     * works as the middleman between the client GUI and the entity on client and server.
     *
     * this mostly just runs loops to add and place all the inventory slots that will appear and can be used on client.
     */
    public ContainerHandler(InventoryPlayer iinventory, TileEntityStorage block, boolean isCrafting) {
        this.isCrafting = isCrafting;

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


        if (!isCrafting) {
            //tile entity inventory
            int slot=0;
            for (int ia = 0; ia > -craftingTable.getInventorySize().getRow(); ia--) {
                for (int ib = 0; ib < craftingTable.getInventorySize().getCollumn(); ib++) {
                    addSlotToContainer(new Slot(craftingTable.inventory, slot, 98 + (ib * 18), (ia * 18) + 44));
                    slot++;
                }
            }
        } else {
            //tile entity's output slot
            this.addSlotToContainer(new SlotCrafting(iinventory.player, craftingTable.inventory, this.craftResult, 10, 124, 35));
            //tile entity's crafting grid
            for (int l = 0; l < 3; ++l) {
                for (int i1 = 0; i1 < 3; ++i1) {
                    this.addSlotToContainer(new craftingSlot(craftingTable.inventory, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
                }
            }

            onCraftMatrixChanged(craftResult);
        }
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
        Slot stack = (Slot)this.inventorySlots.get(slot);
        Slot tempSlot;
        if (stack.getStack() == null){
            return null;
        }
        ItemStack returnStack = stack.getStack().copy();
        for (int currentSlot =0; currentSlot< this.inventorySlots.size(); currentSlot++){
            tempSlot = (Slot)this.inventorySlots.get(currentSlot);
            //if the item is in the train inventory
            if (slot>35 || currentSlot>35) {
                //if the slot is empty, just move it
                if (!tempSlot.getHasStack()) {
                    tempSlot.putStack(stack.getStack());
                    ((Slot) this.inventorySlots.get(slot)).decrStackSize(stack.getStack().stackSize);
                    return null;
                    //if the slot contains the same item, and has room to add this stack to it, then add it
                } else if (tempSlot.getStack().getItem().equals(stack.getStack().getItem()) &&
                        tempSlot.getStack().getMaxStackSize() > stack.getStack().stackSize + tempSlot.getStack().stackSize) {
                    tempSlot.getStack().stackSize += stack.getStack().stackSize;
                    ((Slot) this.inventorySlots.get(slot)).decrStackSize(stack.getStack().stackSize);
                    return null;
                }
            }
        }
        if (isCrafting) {
            if (craftingTable != null) {
                onCraftMatrixChanged(craftingTable.inventory);
            } else {
                onCraftMatrixChanged(((EntityTrainCore) railTransport).inventory);
            }
        }
        return returnStack;
    }

    /**
     * <h2>can interact with inventory</h2>
     * just a simple return true/false if the train is dead, or if the owner locked the transport and the player trying to access isn't the owner.
     * it's also a null check to be sure that no one tries to interact with an errored entity.
     */
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if (railTransport != null) {
            if (railTransport.isDead) {
                return false;
            } else {
                return railTransport.getPermissions(player, true);
            }
        } else {
            return craftingTable != null;
        }
    }


    /**
     * <h2>craft matrix updater redirect</h2>
     * vanilla redirect for updating the craft matrix.
     * @see ContainerHandler#findMatchingRecipe() for the actual use.
     */
    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
        this.craftResult.setInventorySlotContents(9, findMatchingRecipe());
    }

    /**
     * <h2>recipe manager</h2>
     * manages crafting recipes, and their outputs.
     * the current implementation only supports shaped recipes.
     */
    private ItemStack findMatchingRecipe() {
        if (craftingTable != null) {
            for (TrainRegistry registry : TrainRegistry.listTrains()) {
                int i = 0;
                for (; i < 8; i++) {
                    if (craftingTable.inventory.getStackInSlot(i) == null) {
                        if (registry.recipe.get(i) != null) {
                            i = 20;
                        }
                    } else {
                        if (registry.recipe.get(i) != craftingTable.inventory.getStackInSlot(i).getItem()) {
                            i = 20;
                        }
                    }
                }
                if (i == 8) {
                    return new ItemStack(registry.item, 1);
                }
            }
        } else {
            //TODO: normal crafting for rollingstock here
        }
        return null;
    }


    /**
     * <h2>crafting slot</h2>
     * this is a custom crafting slot to make sure that the GUI and inventory update properly.
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
            onCraftMatrixChanged(craftResult);
        }
        @Override
        public void onSlotChanged(){
            super.onSlotChanged();
            onCraftMatrixChanged(craftResult);
        }

    }
}
