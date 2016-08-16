package trains.gui.trainhandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import trains.entities.EntityTrainCore;



public class SteamInventoryHandler extends Container{
    private EntityTrainCore trainEntity;
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
    public SteamInventoryHandler(InventoryPlayer iinventory, EntityTrainCore entityTrain) {
        trainEntity = entityTrain;
        playerInv = iinventory;

        //player inventory
        for (int ic = 0; ic < 9; ic++) {
            for (int ir = 0; ir < 3; ir++) {
                addSlotToContainer(new Slot(playerInv, (((ir * 9) + ic) + 9), 8 + (ic * 18), 84 + (ir * 18)));
            }
        }
        //player toolbar
        for (int iT = 0; iT < 9; iT++) {
            addSlotToContainer(new Slot(playerInv, iT, 8 + iT * 18, 142));
        }

        //define the train's inventory size
        int columns=0;
        int rows=0;
        switch (entityTrain.inventory.size()){
            case 2:case 3:case 4:{columns=2;rows=2;break;}
            case 6:case 7:case 8:{columns=2;rows=3;break;}
            case 9:case 10:case 11:{columns=3;rows=3;break;}
            case 12:case 13:case 14:{columns=3;rows=4;break;}
            case 16:case 17:case 18:{columns=4;rows=4;break;}
        }


        //train inventory
        for (int ia = 0; ia < rows; ia++) {
            for (int ib = 0; ib < columns; ib++) {
                addSlotToContainer(new Slot(entityTrain, ((ib * columns) + ia) +2, 98 + (ib * 18), 8 + (ia * 18)));
            }
        }

        //trainc rafting slots
        //fuel slot
        addSlotToContainer(new Slot(entityTrain, 0, 8, 53));

        //water slot
        addSlotToContainer(new Slot(entityTrain, 1, 35, 53));

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
