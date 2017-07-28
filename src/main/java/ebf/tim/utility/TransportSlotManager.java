package ebf.tim.utility;

import com.google.common.collect.Sets;
import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.*;

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

            if (index < railTransport.getSizeInventory()) {
                if (!this.mergeItemStack(itemstack1, railTransport.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, railTransport.getSizeInventory(), false)) {
                return null;
            }
            slot.putStack(null);
        }
        return itemstack;
    }

    /**modified from 1.7.10 version to check if the item is valid for the slot*/
    @Override
    protected boolean mergeItemStack(ItemStack itemStack, int startIndex, int endIndex, boolean reverseDirection) {
        boolean flag1 = false;
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

                if (slot.isItemValid(itemStack) && itemstack1 != null && itemstack1.getItem() == itemStack.getItem() && (!itemStack.getHasSubtypes() || itemStack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(itemStack, itemstack1)) {
                    int l = itemstack1.stackSize + itemStack.stackSize;

                    if (l <= itemStack.getMaxStackSize()) {
                        itemStack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                    else if (itemstack1.stackSize < itemStack.getMaxStackSize()) {
                        itemStack.stackSize -= itemStack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = itemStack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
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
                    slot.putStack(itemStack.copy());
                    slot.onSlotChanged();
                    itemStack.stackSize = 0;
                    flag1 = true;
                    break;
                }

                if (reverseDirection) {
                    --k;
                }
                else {
                    ++k;
                }
            }
        }

        return flag1;
    }





    private int dragEvent;
    private final Set<Slot> dragSlots = Sets.<Slot>newHashSet();
    private int dragMode =-1;



    /*a heavily modified replica of the 1.12 version*/
    @Override
    public ItemStack slotClick(int slotId, int dragType, int clickTypeIn, EntityPlayer player) {
        //return super.slotClick(fromSlot,0,clickTypeIn!=4?0:4,player);
        //player.addChatComponentMessage(new ChatComponentText("Slot Debug Info" + slotId + ":" + dragType + ":" + clickTypeIn));
        if (clickTypeIn == 4){
            clickTypeIn = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)?1:
                    getSlot(-999) != null?4:0;
        }
        ItemStack itemstack = null;
        InventoryPlayer inventoryplayer = player.inventory;
        int i1;
        ItemStack itemstack3;

        if (clickTypeIn == 5)
        {
            int l = dragEvent;
            dragEvent = func_94532_c(dragType);

            if ((l != 1 || dragEvent != 2) && l != dragEvent)
            {
                this.func_94533_d();
            }
            else if (inventoryplayer.getItemStack() == null)
            {
                this.func_94533_d();
            }
            else if (dragEvent == 0)
            {
                dragMode = func_94529_b(dragType);

                if (func_94528_d(dragMode))
                {
                    dragEvent = 1;
                    dragSlots.clear();
                }
                else
                {
                    this.func_94533_d();
                }
            }
            else if (dragEvent == 1)
            {
                Slot slot = (Slot)this.inventorySlots.get(slotId);

                if (slot != null && func_94527_a(slot, inventoryplayer.getItemStack(), true) && slot.isItemValid(inventoryplayer.getItemStack()) && inventoryplayer.getItemStack().stackSize > dragSlots.size() && this.canDragIntoSlot(slot))
                {
                    dragSlots.add(slot);
                }
            }
            else if (dragEvent == 2)
            {
                if (!dragSlots.isEmpty())
                {
                    itemstack3 = inventoryplayer.getItemStack().copy();
                    i1 = inventoryplayer.getItemStack().stackSize;
                    Iterator iterator = dragSlots.iterator();

                    while (iterator.hasNext())
                    {
                        Slot slot1 = (Slot)iterator.next();

                        if (slot1 != null && func_94527_a(slot1, inventoryplayer.getItemStack(), true) && slot1.isItemValid(inventoryplayer.getItemStack()) && inventoryplayer.getItemStack().stackSize >= dragSlots.size() && this.canDragIntoSlot(slot1))
                        {
                            ItemStack itemstack1 = itemstack3.copy();
                            int j1 = slot1.getHasStack() ? slot1.getStack().stackSize : 0;
                            func_94525_a(dragSlots, dragMode, itemstack1, j1);

                            if (itemstack1.stackSize > itemstack1.getMaxStackSize())
                            {
                                itemstack1.stackSize = itemstack1.getMaxStackSize();
                            }

                            if (itemstack1.stackSize > slot1.getSlotStackLimit())
                            {
                                itemstack1.stackSize = slot1.getSlotStackLimit();
                            }

                            i1 -= itemstack1.stackSize - j1;
                            slot1.putStack(itemstack1);
                        }
                    }

                    itemstack3.stackSize = i1;

                    if (itemstack3.stackSize <= 0)
                    {
                        itemstack3 = null;
                    }

                    inventoryplayer.setItemStack(itemstack3);
                }

                this.func_94533_d();
            }
            else
            {
                this.func_94533_d();
            }
        }
        else if (dragEvent != 0)
        {
            this.func_94533_d();
        }
        else
        {
            Slot slot2;
            int l1;
            ItemStack itemstack5;

            if ((clickTypeIn == 0 || clickTypeIn == 1) && (dragType == 0 || dragType == 1))
            {
                if (slotId == -999)
                {
                    if (inventoryplayer.getItemStack() != null && slotId == -999)
                    {
                        if (dragType == 0)
                        {
                            player.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), true);
                            inventoryplayer.setItemStack((ItemStack)null);
                        }

                        if (dragType == 1)
                        {
                            player.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack().splitStack(1), true);

                            if (inventoryplayer.getItemStack().stackSize == 0)
                            {
                                inventoryplayer.setItemStack((ItemStack)null);
                            }
                        }
                    }
                }
                else if (clickTypeIn == 1)
                {
                    if (slotId < 0)
                    {
                        return null;
                    }

                    slot2 = (Slot)this.inventorySlots.get(slotId);

                    if (slot2 != null && slot2.canTakeStack(player))
                    {
                        itemstack3 = this.transferStackInSlot(player, slotId);

                        if (itemstack3 != null)
                        {
                            Item item = itemstack3.getItem();
                            itemstack = itemstack3.copy();

                            if (slot2.getStack() != null && slot2.getStack().getItem() == item)
                            {
                                this.retrySlotClick(slotId, dragType, true, player);
                            }
                        }
                    }
                }
                else
                {
                    if (slotId < 0)
                    {
                        return null;
                    }

                    slot2 = (Slot)this.inventorySlots.get(slotId);

                    if (slot2 != null)
                    {
                        itemstack3 = slot2.getStack();
                        ItemStack itemstack4 = inventoryplayer.getItemStack();

                        if (itemstack3 != null)
                        {
                            itemstack = itemstack3.copy();
                        }

                        if (itemstack3 == null)
                        {
                            if (itemstack4 != null && slot2.isItemValid(itemstack4))
                            {
                                l1 = dragType == 0 ? itemstack4.stackSize : 1;

                                if (l1 > slot2.getSlotStackLimit())
                                {
                                    l1 = slot2.getSlotStackLimit();
                                }

                                if (itemstack4.stackSize >= l1)
                                {
                                    slot2.putStack(itemstack4.splitStack(l1));
                                }

                                if (itemstack4.stackSize == 0)
                                {
                                    inventoryplayer.setItemStack((ItemStack)null);
                                }
                            }
                        }
                        else if (slot2.canTakeStack(player))
                        {
                            if (itemstack4 == null)
                            {
                                l1 = dragType == 0 ? itemstack3.stackSize : (itemstack3.stackSize + 1) / 2;
                                itemstack5 = slot2.decrStackSize(l1);
                                inventoryplayer.setItemStack(itemstack5);

                                if (itemstack3.stackSize == 0)
                                {
                                    slot2.putStack((ItemStack)null);
                                }

                                slot2.onPickupFromSlot(player, inventoryplayer.getItemStack());
                            }
                            else if (slot2.isItemValid(itemstack4))
                            {
                                if (itemstack3.getItem() == itemstack4.getItem() && itemstack3.getItemDamage() == itemstack4.getItemDamage() && ItemStack.areItemStackTagsEqual(itemstack3, itemstack4))
                                {
                                    l1 = dragType == 0 ? itemstack4.stackSize : 1;

                                    if (l1 > slot2.getSlotStackLimit() - itemstack3.stackSize)
                                    {
                                        l1 = slot2.getSlotStackLimit() - itemstack3.stackSize;
                                    }

                                    if (l1 > itemstack4.getMaxStackSize() - itemstack3.stackSize)
                                    {
                                        l1 = itemstack4.getMaxStackSize() - itemstack3.stackSize;
                                    }

                                    itemstack4.splitStack(l1);

                                    if (itemstack4.stackSize == 0)
                                    {
                                        inventoryplayer.setItemStack((ItemStack)null);
                                    }

                                    itemstack3.stackSize += l1;
                                }
                                else if (itemstack4.stackSize <= slot2.getSlotStackLimit())
                                {
                                    slot2.putStack(itemstack4);
                                    inventoryplayer.setItemStack(itemstack3);
                                }
                            }
                            else if (itemstack3.getItem() == itemstack4.getItem() && itemstack4.getMaxStackSize() > 1 && (!itemstack3.getHasSubtypes() || itemstack3.getItemDamage() == itemstack4.getItemDamage()) && ItemStack.areItemStackTagsEqual(itemstack3, itemstack4))
                            {
                                l1 = itemstack3.stackSize;

                                if (l1 > 0 && l1 + itemstack4.stackSize <= itemstack4.getMaxStackSize())
                                {
                                    itemstack4.stackSize += l1;
                                    itemstack3 = slot2.decrStackSize(l1);

                                    if (itemstack3.stackSize == 0)
                                    {
                                        slot2.putStack((ItemStack)null);
                                    }

                                    slot2.onPickupFromSlot(player, inventoryplayer.getItemStack());
                                }
                            }
                        }

                        slot2.onSlotChanged();
                    }
                }
            }
            else if (clickTypeIn == 2 && dragType >= 0 && dragType < 9)
            {
                slot2 = (Slot)this.inventorySlots.get(slotId);

                if (slot2.canTakeStack(player))
                {
                    itemstack3 = inventoryplayer.getStackInSlot(dragType);
                    boolean flag = itemstack3 == null || slot2.inventory == inventoryplayer && slot2.isItemValid(itemstack3);
                    l1 = -1;

                    if (!flag)
                    {
                        l1 = inventoryplayer.getFirstEmptyStack();
                        flag |= l1 > -1;
                    }

                    if (slot2.getHasStack() && flag)
                    {
                        itemstack5 = slot2.getStack();
                        inventoryplayer.setInventorySlotContents(dragType, itemstack5.copy());

                        if ((slot2.inventory != inventoryplayer || !slot2.isItemValid(itemstack3)) && itemstack3 != null)
                        {
                            if (l1 > -1)
                            {
                                inventoryplayer.addItemStackToInventory(itemstack3);
                                slot2.decrStackSize(itemstack5.stackSize);
                                slot2.putStack((ItemStack)null);
                                slot2.onPickupFromSlot(player, itemstack5);
                            }
                        }
                        else
                        {
                            slot2.decrStackSize(itemstack5.stackSize);
                            slot2.putStack(itemstack3);
                            slot2.onPickupFromSlot(player, itemstack5);
                        }
                    }
                    else if (!slot2.getHasStack() && itemstack3 != null && slot2.isItemValid(itemstack3))
                    {
                        inventoryplayer.setInventorySlotContents(dragType, (ItemStack)null);
                        slot2.putStack(itemstack3);
                    }
                }
            }
            else if (clickTypeIn == 3 && player.capabilities.isCreativeMode && inventoryplayer.getItemStack() == null && slotId >= 0)
            {
                slot2 = (Slot)this.inventorySlots.get(slotId);

                if (slot2 != null && slot2.getHasStack())
                {
                    itemstack3 = slot2.getStack().copy();
                    itemstack3.stackSize = itemstack3.getMaxStackSize();
                    inventoryplayer.setItemStack(itemstack3);
                }
            }
            else if (clickTypeIn == 4 && inventoryplayer.getItemStack() == null && slotId >= 0)
            {
                slot2 = (Slot)this.inventorySlots.get(slotId);

                if (slot2 != null && slot2.getHasStack() && slot2.canTakeStack(player))
                {
                    itemstack3 = slot2.decrStackSize(dragType == 0 ? 1 : slot2.getStack().stackSize);
                    slot2.onPickupFromSlot(player, itemstack3);
                    player.dropPlayerItemWithRandomChoice(itemstack3, true);
                }
            }
            else if (clickTypeIn == 6 && slotId >= 0)
            {
                slot2 = (Slot)this.inventorySlots.get(slotId);
                itemstack3 = inventoryplayer.getItemStack();

                if (itemstack3 != null && (slot2 == null || !slot2.getHasStack() || !slot2.canTakeStack(player)))
                {
                    i1 = dragType == 0 ? 0 : this.inventorySlots.size() - 1;
                    l1 = dragType == 0 ? 1 : -1;

                    for (int i2 = 0; i2 < 2; ++i2)
                    {
                        for (int j2 = i1; j2 >= 0 && j2 < this.inventorySlots.size() && itemstack3.stackSize < itemstack3.getMaxStackSize(); j2 += l1)
                        {
                            Slot slot3 = (Slot)this.inventorySlots.get(j2);

                            if (slot3.getHasStack() && func_94527_a(slot3, itemstack3, true) && slot3.canTakeStack(player) && this.func_94530_a(itemstack3, slot3) && (i2 != 0 || slot3.getStack().stackSize != slot3.getStack().getMaxStackSize()))
                            {
                                int k1 = Math.min(itemstack3.getMaxStackSize() - itemstack3.stackSize, slot3.getStack().stackSize);
                                ItemStack itemstack2 = slot3.decrStackSize(k1);
                                itemstack3.stackSize += k1;

                                if (itemstack2.stackSize <= 0)
                                {
                                    slot3.putStack((ItemStack)null);
                                }

                                slot3.onPickupFromSlot(player, itemstack2);
                            }
                        }
                    }
                }

                this.detectAndSendChanges();
            }
        }

        return itemstack;

    }



    public ItemStack processOldItemstack(int slotId, int dragType, int clickTypeIn, EntityPlayer player){


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
