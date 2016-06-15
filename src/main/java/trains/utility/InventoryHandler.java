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

import java.util.Iterator;


public class InventoryHandler extends Container{
    private MinecartExtended trainEntity;
    private InventoryPlayer playerInv;

    public InventoryHandler(InventoryPlayer iinventory, MinecartExtended entityminecart) {
        trainEntity = entityminecart;
        playerInv = iinventory;

        //switch train type add crafter slots
        //for train slots this.addSlotToContainer


        //add the train inventory
        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(entityminecart, i, 8 + i * 18, 18));
        }
        //add player inventory slots
        for (int i = 0; i < 3; ++i) {//create a loop for columns
            for (int j = 0; j < 9; ++j) {//now do a loop for rows
                addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        //add player toolbar
        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));//142
        }

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

    //detects changes to slots
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        //used to keep track of if the crafter slot needs updating. also works for fluid tanks.
       /*/ Iterator<?> itera;
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
