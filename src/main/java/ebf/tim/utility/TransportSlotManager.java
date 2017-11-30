package ebf.tim.utility;

import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        if (railTransport.inventory == null) {
            railTransport.inventory = new ArrayList<ItemStackSlot>();
            while (railTransport.inventory.size()<railTransport.getSizeInventory()){
                railTransport.inventory.add(new ItemStackSlot(railTransport,slot));
                slot++;
            }
        }
        slot=0;
        if (railTransport.getType() != TrainsInMotion.transportTypes.PASSENGER) {
            //player toolbar
            for (int iT = 0; iT < 9; iT++) {
                if (railTransport instanceof EntityTrainCore || railTransport.getInventorySize().getRow() > 5) {
                    addSlotToContainer(new ItemStackSlot(iinventory, iT).setCoords( 113 + (iT * 18), 142).setSlotID(slot));
                } else {
                    addSlotToContainer(new ItemStackSlot(iinventory, iT).setCoords( 8 + (iT * 18), 149).setSlotID(slot));
                }
                slot++;
            }
            //player inventory
            for (int ic = 0; ic < 9; ic++) {
                for (int ir = 0; ir < 3; ir++) {
                    if (railTransport instanceof EntityTrainCore || railTransport.getInventorySize().getRow() > 5) {
                        addSlotToContainer(new ItemStackSlot(iinventory, (((ir * 9) + ic) + 9)).setCoords(113 + (ic * 18), 84 + (ir * 18)).setSlotID(slot));
                    } else {
                        addSlotToContainer(new ItemStackSlot(iinventory, (((ir * 9) + ic) + 9)).setCoords( 8 + (ic * 18), 91 + (ir * 18)).setSlotID(slot));
                    }
                    slot++;
                }
            }
        }
        int transportSlot=0;
        //cover trains
        if (entityTrain instanceof EntityTrainCore) {
            //burnHeat slot
            addSlotToContainer(railTransport.inventory.get(0).setCoords(114,32).setSlotID(slot).setSlot(transportSlot));
            slot++;transportSlot++;
        }

        switch (entityTrain.getType()){
            case TANKER:{
                addSlotToContainer(railTransport.inventory.get(0).setCoords(72, -28).setSlotID(slot).setSlot(transportSlot));
                slot++;transportSlot++;
                addSlotToContainer(railTransport.inventory.get(1).setCoords(152, 46).setSlotID(slot).setSlot(transportSlot));
                slot++;transportSlot++;
                break;
            }
            case STEAM:case NUCLEAR_STEAM:{
                addSlotToContainer(railTransport.inventory.get(1).setCoords(150, 32).setSlotID(slot).setSlot(transportSlot));
                slot++;
                break;
            }
        }
        if (railTransport.getInventorySize().getRow()>0) {
            int yCenter = (int) ((11 - entityTrain.getInventorySize().getRow()) * 0.5f) * 18;
            //transport inventory
            for (int ia = 0; ia > -entityTrain.getInventorySize().getRow(); ia--) {
                for (int ib = 0; ib < 9; ib++) {
                    if (entityTrain instanceof EntityTrainCore || entityTrain.getInventorySize().getRow() > 5) {
                        addSlotToContainer(railTransport.inventory.get(slot-35).setCoords(-97 + (ib * 18), (ia * 18) + (yCenter) - 19).setSlotID(slot).setSlot(transportSlot));
                    } else {
                        addSlotToContainer(railTransport.inventory.get(slot-35).setCoords(8 + (ib * 18), 60 + (ia * 18)).setSlotID(slot).setSlot(transportSlot));
                    }

                    slot++;transportSlot++;
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
    @Deprecated
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            itemstack = slot.getStack().copy();
            if (index < railTransport.getSizeInventory()-1) {
                if (!this.mergeItemStack(itemstack, 0, this.inventorySlots.size(), true)) {
                    slot.putStack(null);
                   return null;
                }
            } else if (!this.mergeItemStack(itemstack, railTransport.getSizeInventory(),this.inventorySlots.size(), false)) {
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
        System.out.println("something is using merge stack, this is bad");
        int k = reverseDirection? endIndex-1:startIndex;

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
    private final List<ItemStackSlot> dragSlots = new ArrayList<ItemStackSlot>();
    private int dragMode =-1;


    private ItemStackSlot getSlotByID(int id){
        for(ItemStackSlot slot: (List<ItemStackSlot>)inventorySlots){
            if (slot.getSlotID() ==id){
                return slot;
            }
        }
        return null;
    }

    /*a heavily modified replica of the 1.12 version*/
    @Override
    public ItemStack slotClick(int slotId, int dragType, int clickTypeIn, EntityPlayer player) {

        if (clickTypeIn == 4){
            clickTypeIn = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) ? 1 ://cover shift click
                    player.inventory.getItemStack() != null ? 4 : //cover if the cursor is carrying an item
                            (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))?3://cover CTRL clicking
                                    0;//cover everything else
        }
        ItemStack itemstack = null;
        InventoryPlayer inventoryplayer = player.inventory;
        ItemStackSlot slot = getSlotByID(slotId);

        switch (clickTypeIn) {
            case 0: {/*ClickType.PICKUP    aka normal pickup and put down*/
                if (slotId == -999) { //if the slot was the cursor
                    if (inventoryplayer.getItemStack() != null) {
                        if (dragType == 0) {
                            if (!player.worldObj.isRemote) {
                                player.entityDropItem(inventoryplayer.getItemStack(), inventoryplayer.getItemStack().stackSize);
                            }
                            inventoryplayer.setItemStack(null);
                        } else if (dragType == 1 && !player.worldObj.isRemote) {
                            player.entityDropItem(inventoryplayer.getItemStack(), 1);
                        }
                    }
                } else {
                    if (slot == null) {
                        return null;
                    } else if (slot.getHasStack() && inventoryplayer.getItemStack() == null) {
                        inventoryplayer.setItemStack(slot.getStack());
                        if (inventoryplayer.getItemStack() != null) {
                            slot.setSlotContents(null);
                        }
                    } else {
                        inventoryplayer.setItemStack(slot.mergeStack(inventoryplayer.getItemStack()));
                    }
                }
                break;
            }
            case 1:{/*ClickType.QUICK_MOVE    aka shift click*/
                if (slot == null) {
                    return null;
                }

                if(railTransport.getType().isTrain() || railTransport.getType().isTanker()){
                    if(slotId != railTransport.inventory.get(0).getSlotIndex()){
                        slot.setSlotContents(railTransport.inventory.get(0).mergeStack(slot.getStack()));
                        if (slot.getStack() == null){return null;}
                    }
                    if (slot.getStack()!=null && (railTransport.getType() == TrainsInMotion.transportTypes.STEAM || railTransport.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM)
                            && slotId != railTransport.inventory.get(1).getSlotIndex()){
                        slot.setSlotContents(railTransport.inventory.get(1).mergeStack(slot.getStack()));
                        if (slot.getStack() == null){return null;}
                    }
                }

                //its in player inventory
                if(slotId>35){
                    for(ItemStackSlot s : (List<ItemStackSlot>)inventorySlots){
                        if(s.getSlotID() == slotId){continue;}
                        slot.setSlotContents(s.mergeStack(slot.getStack()));
                        if (slot.getStack() == null){return null;}
                    }
                } else {//its in train inventory
                    for(ItemStackSlot s : railTransport.inventory){
                        if(s.getSlotID() == slotId){continue;}
                        slot.setSlotContents(s.mergeStack(slot.getStack()));
                        if (slot.getStack() == null){return null;}
                    }
                    for(ItemStackSlot s : (List<ItemStackSlot>)inventorySlots){
                        if(s.getSlotID() == slotId){continue;}
                        slot.setSlotContents(s.mergeStack(slot.getStack()));
                        if (slot.getStack() == null){return null;}
                    }
                }
                break;
            }
            case 2: {/*ClickType.SWAP*/
                if (dragType >= 0 && dragType < 9){
                    itemstack = inventoryplayer.getStackInSlot(dragType);
                    if (itemstack != null || (slot!= null && slot.getStack() != null)) {
                        if (itemstack == null) {
                            if (slot.canTakeStack(player)) {
                                inventoryplayer.setInventorySlotContents(dragType, slot.getStack());
                                slot.setSlotContents(null);
                            }
                        } else if (slot != null) {
                            inventoryplayer.setInventorySlotContents(dragType, slot.mergeStack(itemstack.splitStack(slot.getSlotStackLimit())));
                        }
                    }
                }
                break;
            }
            case 3: { /*ClickType.CLONE*/
                if (player.capabilities.isCreativeMode && inventoryplayer.getItemStack() == null && slotId >= 0 && slot != null && slot.getHasStack()) {
                    itemstack = slot.getStack().copy();
                    itemstack.stackSize =itemstack.getMaxStackSize();
                    inventoryplayer.setItemStack(itemstack);
                    return itemstack;
                }
                break;
            }
            case 4: { /*ClickType.THROW*/
                if (inventoryplayer.getItemStack() == null && slotId >= 0 && slot != null && slot.getHasStack() && slot.canTakeStack(player)) {
                    itemstack = slot.decrStackSize(dragType == 0 ? 1 : slot.getStack().stackSize);
                    if (!player.worldObj.isRemote) {
                        player.entityDropItem(itemstack, itemstack.stackSize);
                    }
                }
                break;
            }
            case 5 : {/*ClickType.QUICK_CRAFT*/
                    int j1 = this.dragEvent;
                    this.dragEvent = dragType & 3;

                    if (((j1 != 1 || this.dragEvent != 2) && j1 != this.dragEvent) || inventoryplayer.getItemStack() == null) {
                        this.dragEvent = 0;
                        this.dragSlots.clear();
                    } else if (this.dragEvent == 0) {
                        this.dragMode = dragType >> 2 & 3;

                        if ((this.dragMode <= 1 || (this.dragMode == 2 && player.capabilities.isCreativeMode))) {
                            this.dragEvent = 1;
                            this.dragSlots.clear();
                        } else {
                            this.dragEvent = 0;
                            this.dragSlots.clear();
                        }
                    } else if (this.dragEvent == 1) {
                        if (slot != null &&
                                slot.isItemValid(inventoryplayer.getItemStack()) && (this.dragMode == 2 || inventoryplayer.getItemStack().stackSize > this.dragSlots.size())) {
                            this.dragSlots.add(slot);
                        }
                    } else if (this.dragEvent == 2) {
                        if (!this.dragSlots.isEmpty()) {
                            int k1 = inventoryplayer.getItemStack().stackSize;

                            for (ItemStackSlot slot2 : this.dragSlots) {
                                if (slot2 != null && (this.dragMode == 2 || inventoryplayer.getItemStack().stackSize >= this.dragSlots.size())) {
                                    ItemStack itemstack14 = inventoryplayer.getItemStack().copy();
                                    int j3 = slot2.getHasStack() ? slot2.getStack().stackSize : 0;
                                    computeStackSize(this.dragSlots, this.dragMode, itemstack14, j3);
                                    int k3 = Math.min(itemstack14.getMaxStackSize(), slot2.getSlotStackLimit());

                                    if (itemstack14.stackSize > k3) {
                                        itemstack14.stackSize = k3;
                                    }

                                    k1 -= itemstack14.stackSize - j3;
                                    slot2.setSlotContents(itemstack14);
                                }
                            }
                            if (k1 !=0) {
                                inventoryplayer.getItemStack().stackSize = k1;
                            } else {
                                inventoryplayer.setItemStack(null);
                            }
                        }
                        this.dragEvent = 0;
                        this.dragSlots.clear();
                    } else {
                        this.dragEvent = 0;
                        this.dragSlots.clear();
                    }
                    break;
                }
                default:{
                    if (this.dragEvent != 0) {
                        this.dragEvent = 0;

                        this.dragSlots.clear();
                    }
                }
            case 6: {
                if (slotId >= 0) {
                    ItemStack itemstack1 = inventoryplayer.getItemStack();

                    if (itemstack1 != null && (slot == null || !slot.getHasStack() || !slot.canTakeStack(player))) {
                        int i = dragType == 0 ? 0 : this.inventorySlots.size() - 1;
                        int j = dragType == 0 ? 1 : -1;

                        for (int k = 0; k < 2; ++k) {
                            for (int l = i; l >= 0 && l < this.inventorySlots.size() && itemstack1.stackSize < itemstack1.getMaxStackSize(); l += j) {
                                Slot slot1 = getSlotByID(l);

                                if (slot1 != null && slot1.getHasStack() && canAddItemToSlot(slot1, itemstack1) && slot1.canTakeStack(player)) {
                                    ItemStack itemstack2 = slot1.getStack();

                                    if (k != 0 || itemstack2.stackSize != itemstack2.getMaxStackSize()) {
                                        int i1 = Math.min(itemstack1.getMaxStackSize() - itemstack1.stackSize, itemstack2.stackSize);
                                        ItemStack itemstack3 = slot1.decrStackSize(i1);
                                        itemstack1.stackSize += i1;

                                        if (itemstack3 == null) {
                                            slot1.putStack(null);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.detectAndSendChanges();
                }
            }


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

    public static void computeStackSize(List<ItemStackSlot> dragSlotsIn, int dragModeIn, ItemStack stack, int slotStackSize) {
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
}
