package trains.utility;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import trains.entities.MinecartExtended;



public class InventoryHandler extends Container{
    private MinecartExtended trainEntity;
    private InventoryPlayer playerInv;

    public InventoryHandler(InventoryPlayer iinventory, MinecartExtended entityminecart) {
        trainEntity = entityminecart;
        playerInv = iinventory;
        //add player inventory slots
        for (int ic = 0; ic < 9; ic++) {//create a loop for columns
            for (int ir = 0; ir < 3; ir++) {//now do a loop for rows
                addSlotToContainer(new Slot(playerInv, (((ir * 9) + ic) + 9), 8 + (ic * 18), 84 + (ir * 18)));
            }
        }


        //add player toolbar
        for (int iT = 0; iT < 9; iT++) {
            addSlotToContainer(new Slot(playerInv, iT, 8 + iT * 18, 142));
        }

        //add the train's inventory
        for (int ia = 0; ia < entityminecart.columns; ia++) {
            for (int ib = 0; ib < entityminecart.rows; ib++) {
                addSlotToContainer(new Slot(entityminecart, ((ib * entityminecart.columns) + ia), (8 + (ib * 18)), (8 + (ia * 18))));
            }
        }



        //switch train type add crafter slots
    }

    //for sorting items from inventory to the train inventory, or the reverse way.
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        return super.transferStackInSlot(player, slot);
    }

    //update other viewers
    @Override
    public void addCraftingToCrafters(ICrafting crafter){
        super.addCraftingToCrafters(crafter);

    }
    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int slotIndex, int updatedValue) {}

    @Override
    @SideOnly(Side.CLIENT)
    public void putStacksInSlots(ItemStack[] stack) {
        for (int i = 0; i < stack.length; ++i) {
            if (i < inventorySlots.size())
            getSlot(i).putStack(stack[i]);
        }
    }

    //detects changes to slots
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        //used to keep track of if the crafter slot needs updating. also works for fluid tanks.
        /*/Iterator<?> itera;
        for (int i = 0; i < lastData.length; ++i) {
            if (lastData[i] != trainEntity.inventory[i]) {
                lastData[i] = trainEntity.inventory[i];
                itera = crafters.iterator();
                while (itera.hasNext()) {
                    ((ICrafting) itera.next()).sendProgressBarUpdate(this, i, trainEntity.inventory[i]);
                }
            }
        }/*/
    }

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
