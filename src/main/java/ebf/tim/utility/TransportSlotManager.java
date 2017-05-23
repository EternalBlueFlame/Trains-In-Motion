package ebf.tim.utility;

import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author EternalBlueFlame
 */
public class TransportSlotManager extends net.minecraft.inventory.Container {

    private GenericRailTransport railTransport;

    /**
     * <h2>Server-side inventory GUI for trains and rollingstock</h2>
     * works as the middleman between the client GUI and the entity on client and server.
     *
     * this mostly just runs loops to add and place all the inventory slots that will appear and can be used on client.
     */
    public TransportSlotManager(InventoryPlayer iinventory, GenericRailTransport entityTrain) {
        if (entityTrain.getType() != TrainsInMotion.transportTypes.PASSENGER) {
            //player inventory
            for (int ic = 0; ic < 9; ic++) {
                for (int ir = 0; ir < 3; ir++) {
                    if(entityTrain instanceof EntityTrainCore || entityTrain.getInventorySize().getRow()>5) {
                        addSlotToContainer(new Slot(iinventory, (((ir * 9) + ic) + 9), 113 + (ic * 18), 84 + (ir * 18)));
                    } else {
                        addSlotToContainer(new Slot(iinventory, (((ir * 9) + ic) + 9), 8 + (ic * 18), 91 + (ir * 18)));
                    }
                }
            }
            //player toolbar
            for (int iT = 0; iT < 9; iT++) {
                if(entityTrain instanceof EntityTrainCore || entityTrain.getInventorySize().getRow()>5) {
                    addSlotToContainer(new Slot(iinventory, iT, 113 + (iT * 18), 142));
                } else {
                    addSlotToContainer(new Slot(iinventory, iT, 8 + (iT * 18), 149));
                }
            }
        }

        //transport reference
        if (railTransport == null) {
            railTransport = entityTrain;
        }


        int slot=0;
        //cover trains
        if (entityTrain instanceof EntityTrainCore) {
            slot++;
            //fuel slot
            addSlotToContainer(new fuelSlot(railTransport, 0, 114, 32));
        }

        switch (entityTrain.getType()){
            case TANKER:{
                addSlotToContainer(new waterSlot(railTransport, 0, 72, -28));
                addSlotToContainer(new filteredSlot(railTransport, 1, 152, 46));
                break;
            }
            case STEAM:case NUCLEAR_STEAM:{
                addSlotToContainer(new waterSlot( railTransport, 1, 150, 32));
                slot=2;
                break;
            }
        }
        if (railTransport.getInventorySize().getRow()>0) {
            int yCenter = (int) ((11 - entityTrain.getInventorySize().getRow()) * 0.5f) * 18;
            //transport inventory
            for (int ia = 0; ia > -entityTrain.getInventorySize().getRow(); ia--) {
                for (int ib = 0; ib < 9; ib++) {
                    if (entityTrain instanceof EntityTrainCore || entityTrain.getInventorySize().getRow() > 5) {
                        addSlotToContainer(new filteredSlot(railTransport, slot, -97 + (ib * 18), (ia * 18) + (yCenter) - 19));
                    } else {
                        addSlotToContainer(new filteredSlot(railTransport, slot, 8 + (ib * 18), 60 + (ia * 18)));
                    }

                    slot++;
                }
            }
        }

    }

    /**
     * <h2>Inventory sorting and shift-clicking</h2>
     * sorts items from the players inventory to the entity's inventory, and the reverse.
     * This happens with shift click and during some other circumstances.
     * We manage player inventory first because we bound it first, plus it's more reliable to be the size we expected.
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
        return !railTransport.isDead && railTransport.getPermissions(player, railTransport instanceof EntityTrainCore);
    }


    /**
     * <h2>filtered slot</h2>
     * A simple slot meant for the actual inventory to allow filtering.
     */
    private class filteredSlot extends Slot{

        public filteredSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(p_i1824_1_,p_i1824_2_,p_i1824_3_,p_i1824_4_);
        }

        /**
         * <h2>filter items</h2>
         * makes sure the slot will only accept what the inventory will allow.
         * This is actually handled through
         * @see GenericRailTransport#isItemValidForSlot(int, ItemStack) so that way loaders will see it properly.
         * @return if the item should be allowed or blocked.
         */
        @Override
        public boolean isItemValid(ItemStack item) {
            return railTransport.isItemValidForSlot(0, item);
        }
    }


    /**
     * <h2>Water Filtered Slot</h2>
     * basically exactly the same as a filtered slot but used to check for water via the fuel handler
     * @see FuelHandler#isWater(ItemStack, GenericRailTransport)
     */
    private class waterSlot extends Slot{
        public waterSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(p_i1824_1_,p_i1824_2_,p_i1824_3_,p_i1824_4_);
        }
        @Override
        public boolean isItemValid(ItemStack item) {
            return FuelHandler.isWater(item, railTransport);
        }
    }

    /**
     * <h2>Fuel Filtered Slot</h2>
     * basically exactly the same as a filtered slot but used to check for fuel via the fuel handler
     * @see FuelHandler#isFuel(ItemStack, GenericRailTransport)
     */
    private class fuelSlot extends Slot{
        public fuelSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(p_i1824_1_,p_i1824_2_,p_i1824_3_,p_i1824_4_);
        }
        @Override
        public boolean isItemValid(ItemStack item) {
            return FuelHandler.isFuel(item, railTransport);
        }
    }
}
