package ebf.tim.utility;

import com.google.common.collect.Sets;
import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.Set;

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
        //transport reference
        if (railTransport == null) {
            railTransport = entityTrain;
        }


        int slot=0;
        //cover trains
        if (entityTrain instanceof EntityTrainCore) {
            slot++;
            //burnHeat slot
            addSlotToContainer(new fuelSlot(railTransport, 0, 114, 32));
        }

        switch (entityTrain.getType()){
            case TANKER:{
                addSlotToContainer(new waterSlot(railTransport, 0, 72, -28));
                addSlotToContainer(new filteredSlot(railTransport, 1, 152, 46).setSlotMax(1));
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
    }

    /**
     * <h2>Inventory sorting and shift-clicking</h2>
     * sorts items from the players inventory to the entity's inventory, and the reverse.
     * This happens with shift click and during some other circumstances.
     * We manage player inventory first because we bound it first, plus it's more reliable to be the size we expected.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < railTransport.getSizeInventory()-1) {
                if (!this.mergeItemStack(itemstack1, railTransport.getSizeInventory(), this.inventorySlots.size(), true)) {
                    System.out.println(player.worldObj.isRemote + " moved the item to slot " + index);
                    slot.putStack(null);
                   return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, railTransport.getSizeInventory(), false)) {
                slot.putStack(null);
                return null;
            }
            slot.putStack(null);
        }
        return itemstack;
    }

    /**modified from 1.7.10 version to check if the item is valid for the slot*/
    @Override
    protected boolean mergeItemStack(ItemStack itemStack, int startIndex, int endIndex, boolean reverseDirection) {
        int k = startIndex;

        if (reverseDirection) {
            k = endIndex - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (itemStack.isStackable()) {
            while (itemStack.stackSize > 0 && (!reverseDirection && k < endIndex || reverseDirection && k >= startIndex)) {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (slot.isItemValid(itemStack) && itemstack1 != null && itemstack1.getItem() == itemStack.getItem() && (!itemStack.getHasSubtypes() ||
                        itemStack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(itemStack, itemstack1)) {
                    int l = itemstack1.stackSize + itemStack.stackSize;

                    if (l <= itemStack.getMaxStackSize() && l <= slot.getSlotStackLimit()) {
                        itemStack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        return true;
                    }
                    else if (itemstack1.stackSize < itemStack.getMaxStackSize() && l <= slot.getSlotStackLimit()) {
                        itemStack.stackSize -= (slot.getSlotStackLimit()>= itemStack.getMaxStackSize()?(itemStack.getMaxStackSize() - itemstack1.stackSize): (slot.getSlotStackLimit() - itemstack1.stackSize));
                        itemstack1.stackSize = (slot.getSlotStackLimit()>= itemStack.getMaxStackSize()?itemStack.getMaxStackSize():slot.getSlotStackLimit());
                        slot.onSlotChanged();
                        return true;
                    }
                }

                if (reverseDirection) {
                    --k;
                } else {
                    ++k;
                }
            }
        }

        if (itemStack.stackSize > 0) {
            if (reverseDirection) {
                k = endIndex - 1;
            } else {
                k = startIndex;
            }

            while (!reverseDirection && k < endIndex || reverseDirection && k >= startIndex) {
                slot = (Slot)this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 == null && slot.isItemValid(itemStack)) {
                    if (itemStack.stackSize < slot.getSlotStackLimit()) {
                        slot.putStack(itemStack.copy());
                        slot.onSlotChanged();
                        itemStack.stackSize = 0;
                        return true;
                    } else {
                        slot.putStack(new ItemStack(itemStack.getItem(), slot.getSlotStackLimit()));
                        slot.onSlotChanged();
                        itemStack.stackSize -=slot.getSlotStackLimit();
                        if (itemStack.stackSize <1){
                            return true;
                        }
                    }
                }

                if (reverseDirection) {
                    --k;
                }
                else {
                    ++k;
                }
            }
        }

        return false;
    }





    private int dragEvent;
    private final Set<Slot> dragSlots = Sets.<Slot>newHashSet();
    private int dragMode =-1;



    /*a heavily modified replica of the 1.12 version*/
    @Override
    public ItemStack slotClick(int slotId, int dragType, int clickTypeIn, EntityPlayer player) {
        //return super.slotClick(fromSlot,0,clickTypeIn!=4?0:4,player);
        player.addChatComponentMessage(new ChatComponentText("Slot Debug Info" + slotId + ":" + dragType + ":" + clickTypeIn));
        if (clickTypeIn == 4){
                clickTypeIn = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) ? 1 ://cover shift click
                        player.inventory.getItemStack() != null ? 4 : //cover if the cursor is carrying an item
                                (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))?3://cover CTRL clicking
                                        0;//cover everything else
        }
        ItemStack itemstack = null;
        InventoryPlayer inventoryplayer = player.inventory;

        if (clickTypeIn == 5/*ClickType.QUICK_CRAFT*/) {
            int j1 = this.dragEvent;
            this.dragEvent = dragType & 3;

            if (((j1 != 1 || this.dragEvent != 2) && j1 != this.dragEvent) || inventoryplayer.getItemStack() == null) {
                this.dragEvent = 0;
                this.dragSlots.clear();
            } else if (this.dragEvent == 0) {
                this.dragMode = dragType >> 2 & 3;

                if ((this.dragMode == 0 || this.dragMode ==1 || (this.dragMode == 2 && player.capabilities.isCreativeMode))) {
                    this.dragEvent = 1;
                    this.dragSlots.clear();
                } else {
                    this.dragEvent = 0;
                    this.dragSlots.clear();
                }
            } else if (this.dragEvent == 1) {
                Slot slot7 = (Slot) this.inventorySlots.get(slotId);

                if (slot7 != null && canAddItemToSlot(slot7, inventoryplayer.getItemStack()) && slot7.isItemValid(inventoryplayer.getItemStack()) && (this.dragMode == 2 || inventoryplayer.getItemStack().stackSize > this.dragSlots.size()) && this.canDragIntoSlot(slot7)) {
                    this.dragSlots.add(slot7);
                }
            } else if (this.dragEvent == 2) {
                if (!this.dragSlots.isEmpty()) {
                    int k1 = inventoryplayer.getItemStack().stackSize;

                    for (Slot slot8 : this.dragSlots) {
                        ItemStack itemstack13 = inventoryplayer.getItemStack();

                        if (slot8 != null && canAddItemToSlot(slot8, itemstack13) && slot8.isItemValid(itemstack13) && (this.dragMode == 2 || itemstack13.stackSize >= this.dragSlots.size()) && this.canDragIntoSlot(slot8)) {
                            ItemStack itemstack14 = inventoryplayer.getItemStack().copy();
                            int j3 = slot8.getHasStack() ? slot8.getStack().stackSize : 0;
                            computeStackSize(this.dragSlots, this.dragMode, itemstack14, j3);
                            int k3 = Math.min(itemstack14.getMaxStackSize(), slot8.getSlotStackLimit());

                            if (itemstack14.stackSize > k3){
                                itemstack14.stackSize =k3;
                            }

                            k1 -= itemstack14.stackSize - j3;
                            slot8.putStack(itemstack14);
                        }
                    }
                    inventoryplayer.getItemStack().stackSize =k1;
                }

                this.dragEvent = 0;
                this.dragSlots.clear();
            } else {
                this.dragEvent = 0;
                this.dragSlots.clear();
            }
        } else if (this.dragEvent != 0) {
            this.dragEvent = 0;

            this.dragSlots.clear();
        } else if ((clickTypeIn == 0 /*ClickType.PICKUP*/ || clickTypeIn == 1 /*ClickType.QUICK_MOVE*/) && (dragType == 0 || dragType == 1)) {
            if (slotId == -999) { //if the slot was the cursor
                if (inventoryplayer.getItemStack() != null) {
                    if (dragType == 0) {
                        if (!player.worldObj.isRemote){
                            player.entityDropItem(inventoryplayer.getItemStack(),inventoryplayer.getItemStack().stackSize);
                        }
                        inventoryplayer.setItemStack(null);
                    }

                    if (dragType == 1 && !player.worldObj.isRemote) {
                        player.entityDropItem(inventoryplayer.getItemStack(), 1);
                    }
                }
            } else if (clickTypeIn == 1 /*ClickType.QUICK_MOVE*/) {//used for shift click
                if (slotId < 0) {
                    return null;
                }

                Slot slot5 = (Slot) this.inventorySlots.get(slotId);

                if (slot5 == null || !slot5.canTakeStack(player)) {
                    return null;
                }

                for (ItemStack itemstack7 = this.transferStackInSlot(player, slotId);
                     itemstack7 != null && areItemStackTagsEqual(slot5.getStack(), itemstack7);
                     itemstack7 = this.transferStackInSlot(player, slotId)) {
                    itemstack = itemstack7.copy();
                }
            } else { //used for normal click
                if (slotId < 0) {
                    return null;
                }

                Slot slot6 = (Slot) this.inventorySlots.get(slotId);

                if (slot6 != null) {
                    ItemStack itemstack8 = slot6.getStack();
                    ItemStack itemstack11 = inventoryplayer.getItemStack();

                    if (itemstack8 != null) {
                        itemstack = itemstack8.copy();
                    }

                    if (itemstack8 == null) {
                        if (itemstack11 != null && slot6.isItemValid(itemstack11)) {
                            int i3 = dragType == 0 ? itemstack11.stackSize : 1;

                            if (i3 > slot6.getSlotStackLimit()) {
                                i3 = slot6.getSlotStackLimit();
                            }

                            slot6.putStack(itemstack11.splitStack(i3));
                        }
                    } else if (slot6.canTakeStack(player)) {
                        if (itemstack11 == null) {
                            inventoryplayer.setItemStack(slot6.decrStackSize(dragType == 0 ? itemstack8.stackSize : (itemstack8.stackSize + 1) / 2));

                            slot6.onSlotChanged();
                        } else if (slot6.isItemValid(itemstack11)) {
                            if (itemstack8.getItem() == itemstack11.getItem() && itemstack8.getItemDamage() == itemstack11.getItemDamage() && ItemStack.areItemStackTagsEqual(itemstack8, itemstack11)) {
                                int k2 = dragType == 0 ? itemstack11.stackSize : 1;

                                if (k2 > slot6.getSlotStackLimit()) {
                                    k2 = slot6.getSlotStackLimit();
                                }

                                if (k2 > itemstack11.getMaxStackSize() - itemstack8.stackSize) {
                                    k2 = itemstack11.getMaxStackSize() - itemstack8.stackSize;
                                }

                                itemstack11.stackSize -= k2;
                                itemstack8.stackSize +=k2;
                            } else if (itemstack11.stackSize <= slot6.getSlotStackLimit()) {
                                slot6.putStack(itemstack11);
                                inventoryplayer.setItemStack(itemstack8);
                            }
                        } else if (itemstack8.getItem() == itemstack11.getItem() && itemstack11.getMaxStackSize() > 1 && (!itemstack8.getHasSubtypes() || itemstack8.getItemDamage() == itemstack11.getItemDamage()) && ItemStack.areItemStackTagsEqual(itemstack8, itemstack11)) {
                            int j2 = itemstack8.stackSize;

                            if (j2 + itemstack11.stackSize <= itemstack11.getMaxStackSize()) {
                                itemstack11.stackSize += j2;
                                itemstack8 = slot6.decrStackSize(j2);

                                if (itemstack8 == null) {
                                    slot6.putStack(null);
                                }

                                slot6.onSlotChanged();
                            }
                        }
                    }
                    slot6.onSlotChanged();
                }
            }
        } else if (clickTypeIn == 2 /*ClickType.SWAP*/ && dragType >= 0 && dragType < 9) {
            Slot slot4 = (Slot) this.inventorySlots.get(slotId);
            ItemStack itemstack6 = inventoryplayer.getStackInSlot(dragType);
            ItemStack itemstack10 = slot4.getStack();

            if (itemstack6 != null || itemstack10 != null) {
                if (itemstack6 == null) {
                    if (slot4.canTakeStack(player)) {
                        inventoryplayer.setInventorySlotContents(dragType, itemstack10);
                        //slot4.onSwapCraft(itemstack10.stackSize); //Even useless in 1.12?
                        slot4.putStack(null);
                        slot4.onSlotChanged();
                    }
                } else if (itemstack10 == null) {
                    if (slot4.isItemValid(itemstack6)) {
                        if (itemstack6.stackSize > slot4.getSlotStackLimit()) {
                            slot4.putStack(itemstack6.splitStack(slot4.getSlotStackLimit()));
                        } else {
                            slot4.putStack(itemstack6);
                            inventoryplayer.setInventorySlotContents(dragType, null);
                        }
                    }
                } else if (slot4.canTakeStack(player) && slot4.isItemValid(itemstack6)) {

                    if (itemstack6.stackSize > slot4.getSlotStackLimit()) {
                        slot4.putStack(itemstack6.splitStack(slot4.getSlotStackLimit()));
                        slot4.onSlotChanged();

                        if (!inventoryplayer.addItemStackToInventory(itemstack10) && !player.worldObj.isRemote) {
                            player.entityDropItem(itemstack10, itemstack10.stackSize);
                        }
                    } else {
                        slot4.putStack(itemstack6);
                        inventoryplayer.setInventorySlotContents(dragType, itemstack10);
                        slot4.onSlotChanged();
                    }
                }
            }
        } else if (clickTypeIn == 3 /*ClickType.CLONE*/ && player.capabilities.isCreativeMode && inventoryplayer.getItemStack() == null && slotId >= 0) {
            Slot slot3 = (Slot) this.inventorySlots.get(slotId);

            if (slot3 != null && slot3.getHasStack()) {
                ItemStack itemstack5 = slot3.getStack().copy();
                itemstack5.stackSize =itemstack5.getMaxStackSize();
                inventoryplayer.setItemStack(itemstack5);
            }
        }//theoretically case 4 will never happen, and probably wouldn't even happen in vanilla.
        else if (clickTypeIn == 4 /*ClickType.THROW*/ && inventoryplayer.getItemStack() == null && slotId >= 0) {
            Slot slot2 = (Slot) this.inventorySlots.get(slotId);

            if (slot2 != null && slot2.getHasStack() && slot2.canTakeStack(player)) {
                ItemStack itemstack4 = slot2.decrStackSize(dragType == 0 ? 1 : slot2.getStack().stackSize);
                slot2.onSlotChanged();
                if (!player.worldObj.isRemote) {
                    player.entityDropItem(itemstack4, itemstack4.stackSize);
                }
            }
        } else if (clickTypeIn == 6/*ClickType.PICKUP_ALL*/ && slotId >= 0) {
            Slot slot = (Slot) this.inventorySlots.get(slotId);
            ItemStack itemstack1 = inventoryplayer.getItemStack();

            if (itemstack1 != null && (slot == null || !slot.getHasStack() || !slot.canTakeStack(player))) {
                int i = dragType == 0 ? 0 : this.inventorySlots.size() - 1;
                int j = dragType == 0 ? 1 : -1;

                for (int k = 0; k < 2; ++k) {
                    for (int l = i; l >= 0 && l < this.inventorySlots.size() && itemstack1.stackSize < itemstack1.getMaxStackSize(); l += j) {
                        Slot slot1 = (Slot) this.inventorySlots.get(l);

                        if (slot1.getHasStack() && canAddItemToSlot(slot1, itemstack1) && slot1.canTakeStack(player)) {
                            ItemStack itemstack2 = slot1.getStack();

                            if (k != 0 || itemstack2.stackSize != itemstack2.getMaxStackSize()) {
                                int i1 = Math.min(itemstack1.getMaxStackSize() - itemstack1.stackSize, itemstack2.stackSize);
                                ItemStack itemstack3 = slot1.decrStackSize(i1);
                                itemstack1.stackSize +=i1;

                                if (itemstack3 == null) {
                                    slot1.putStack(null);
                                }

                                slot1.onSlotChanged();
                            }
                        }
                    }
                }
            }

            this.detectAndSendChanges();
        }

        if (inventoryplayer.getItemStack() != null && inventoryplayer.getItemStack().stackSize ==0){
            inventoryplayer.setItemStack(null);
        }
        return itemstack;

    }



    /*a modified replica of the 1.12 version*/
    public static boolean canAddItemToSlot(@Nullable Slot slotIn, ItemStack stack) {
        boolean flag = slotIn == null || !slotIn.getHasStack();

        if (!flag && stack.isItemEqual(slotIn.getStack()) && ItemStack.areItemStackTagsEqual(slotIn.getStack(), stack)) {
            return slotIn.getStack().stackSize <= stack.getMaxStackSize();
        } else {
            return flag;
        }
    }

    /*a modified replica of the 1.12 version*/
    public static void computeStackSize(Set<Slot> dragSlotsIn, int dragModeIn, ItemStack stack, int slotStackSize) {
        switch (dragModeIn) {
            case 0: {
                stack.stackSize = (MathHelper.floor_float(stack.stackSize / dragSlotsIn.size()));
                break;
            }
            case 1: {
                stack.stackSize = 1;
                break;
            }
            case 2: {
                stack.stackSize = (stack.getMaxStackSize());
            }
        }
        stack.stackSize +=(slotStackSize);
    }

    /*a modified replica of the 1.12 version*/
    public static boolean areItemStackTagsEqual(ItemStack stackA, ItemStack stackB) {
        if (stackA == null && stackB == null) {
            return true;
        } else if (stackA != null && stackB != null) {
            return !(stackA.stackTagCompound == null && stackB.stackTagCompound != null) && (stackA.stackTagCompound == null || stackA.stackTagCompound.equals(stackB.stackTagCompound));
        } else {
            return false;
        }
    }



    @Override
    public void onContainerClosed(EntityPlayer p_75134_1_)
    {
        super.onContainerClosed(p_75134_1_);
        this.railTransport.closeInventory();
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
        private int slotmax =64;

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

        public filteredSlot setSlotMax(int max){
            slotmax = max;
            return this;
        }
        @Override
        public int getSlotStackLimit(){
            System.out.println(slotmax + ":");
            return slotmax;}
    }


    /**
     * <h2>Water Filtered Slot</h2>
     * basically exactly the same as a filtered slot but used to check for water via the burnHeat handler
     * @see FuelHandler#isUseableFluid(ItemStack, GenericRailTransport)
     */
    private class waterSlot extends Slot{
        public waterSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(p_i1824_1_,p_i1824_2_,p_i1824_3_,p_i1824_4_);
        }
        @Override
        public boolean isItemValid(ItemStack item) {
            return FuelHandler.isUseableFluid(item, railTransport) != null;
        }

        @Override
        public int getSlotStackLimit(){return 1;}
    }

    /**
     * <h2>Fuel Filtered Slot</h2>
     * basically exactly the same as a filtered slot but used to check for burnHeat via the burnHeat handler
     */
    private class fuelSlot extends Slot{
        public fuelSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(p_i1824_1_,p_i1824_2_,p_i1824_3_,p_i1824_4_);
        }
        @Override
        public boolean isItemValid(ItemStack item) {
            switch (railTransport.getType()) {
                case STEAM:{return TileEntityFurnace.getItemBurnTime(item) >0;}
                case ELECTRIC:case MAGLEV:{FuelHandler.isUseableFluid(item,railTransport);}
            }
            return false;
        }
    }
}
