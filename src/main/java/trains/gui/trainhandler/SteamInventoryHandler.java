package trains.gui.trainhandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;


public class SteamInventoryHandler extends Container{
    private EntityTrainCore trainEntity;

    /**
     * <h2>Server-side inventory GUI</h2>
     * works as the middleman between the GUI and the entity.
     *
     * runs a series of loops for managing the inventory slots.
     */
    public SteamInventoryHandler(InventoryPlayer iinventory, EntityTrainCore entityTrain) {
        trainEntity = entityTrain;

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

        //define the train's inventory size
        int columns=0;
        int rows=0;
        int slot=1;
        if (entityTrain.getType() == TrainsInMotion.transportTypes.STEAM || entityTrain.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM){
            slot=2;
        }
        switch (entityTrain.getInventorySize()){
            case TWOxTWO:{columns=2;rows=2;break;}
            case THREExTWO:{columns=2;rows=3;break;}
            case THREExTHREE:{columns=3;rows=3;break;}
            case FOURxTHREE:{columns=3;rows=4;break;}
            case FOURxFOUR:{columns=4;rows=4;break;}
        }
        //train crafting slots

        //fuel slot
        addSlotToContainer(new Slot(trainEntity.inventory, 0, 8, 53));
        if (slot==2) {
            //water slot
            addSlotToContainer(new Slot(trainEntity.inventory, 1, 35, 53));
        }

        //train inventory
        for (int ia = 0; ia < rows; ia++) {
            for (int ib = 0; ib < columns; ib++) {
                addSlotToContainer(new Slot(trainEntity.inventory, slot, 98 + (ib * 18), 8 + (ia * 18)));
                slot++;
            }
        }



    }

    //for sorting items from inventory to the train inventory, or the reverse way.
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

        return returnStack;
    }

    //if inventory can be accessed/modified.
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if (trainEntity.isDead) {
            return false;
        } else if (trainEntity.isLocked && trainEntity.owner != player.getUniqueID()){
            return false;
        }
        return true;
    }


}
