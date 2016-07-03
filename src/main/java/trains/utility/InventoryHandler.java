package trains.utility;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import trains.entities.EntityTrainCore;
import trains.entities.MinecartExtended;



public class InventoryHandler extends Container{
    private MinecartExtended trainEntity;
    private InventoryPlayer playerInv;

    /**
     * creates the inventories based on the values in entityMinecart
     *
     * the first set of loops creates the player inventory, using a loop for columns and then rows.
     *
     * the second loop creates the player toolbar slots.
     *
     * the third creates slots based on the columns and rows
     *
     * and the final part creates the crafting slots for trains.
     *
     */
    public InventoryHandler(InventoryPlayer iinventory, MinecartExtended entityminecart) {
        trainEntity = entityminecart;
        playerInv = iinventory;

        for (int ic = 0; ic < 9; ic++) {
            for (int ir = 0; ir < 3; ir++) {
                addSlotToContainer(new Slot(playerInv, (((ir * 9) + ic) + 9), 8 + (ic * 18), 84 + (ir * 18)));
            }
        }

        for (int iT = 0; iT < 9; iT++) {
            addSlotToContainer(new Slot(playerInv, iT, 8 + iT * 18, 142));
        }

        for (int ia = 0; ia < entityminecart.columns; ia++) {
            for (int ib = 0; ib < entityminecart.rows; ib++) {
                addSlotToContainer(new Slot(entityminecart, ((ib * entityminecart.columns) + ia) +2, (8 + (ib * 18)), (8 + (ia * 18))));
            }
        }

        if (entityminecart instanceof EntityTrainCore){
            EntityTrainCore cart = (EntityTrainCore) entityminecart;
            addSlotToContainer(new Slot(cart, 0, 26, 80));
            if (cart.trainType == 1 || cart.trainType == 5){
                addSlotToContainer(new Slot(cart, 1, 42, 80));
            }
        }
        //rollingstock switch for crafters here

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
