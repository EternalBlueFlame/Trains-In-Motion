package ebf.tim.utility;

import ebf.tim.blocks.TileEntityStorage;
import ebf.tim.items.ItemRail;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Tile Entity Container</h1>
 * creates the functionality for item storage and crafting for tile entities.
 *
 * @author Eternal Blue Flame
 */
public class TileEntitySlotManager extends Container{
    /**the tile entity*/
    public TileEntityStorage craftingTable;

    /**
     * <h2>Server-side inventory GUI for tile entities</h2>
     * works as the middleman between the client GUI and the entity on client and server.
     *
     * this mostly just runs loops to add and place all the inventory slots that will appear and can be used on client.
     * these slots draw the item, if there is one, and the tint when the cursor hovers over the slot.
     */
    public TileEntitySlotManager(InventoryPlayer iinventory, TileEntityStorage block) {

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


        if (craftingTable.storageType == 1) {

            //tile entity's crafting grid
            for (int l = 0; l < 3; ++l) {
                addSlotToContainer(new craftingSlot(craftingTable, l, 30, 2 + (l * 20)));
            }
            //tile entity's output slot
            addSlotToContainer(new Slot(craftingTable, 3, 124, 44));
            addSlotToContainer(new craftingSlot(craftingTable, 4, 124, 0));

        } else {
            //tile entity's crafting grid
            for (int l = 0; l < 3; ++l) {
                for (int i1 = 0; i1 < 3; ++i1) {
                    addSlotToContainer(new craftingSlot(craftingTable, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
                }
            }
            //tile entity's output slot
            addSlotToContainer(new Slot(craftingTable, 10, 124, 35));
        }
        onCraftMatrixChanged(craftingTable);
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
            //if this is a crafting GUI, then use the GUI matrix to handle it
            onCraftMatrixChanged(craftingTable);
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
        return craftingTable != null;
    }

    public void putStackInSlot(int slot, ItemStack stack, boolean isPlayerInventory) {
        ((Slot)this.inventorySlots.get(isPlayerInventory?slot:slot+35)).putStack(stack);
    }

    /**
     * <h2>craft matrix updater redirect</h2>
     * vanilla redirect for updating the craft matrix.
     */
    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
        if(craftingTable!=null) {
            switch (craftingTable.storageType) {
                case 0: {
                    putStackInSlot(10, transportRecipe(), false);
                    break;
                }
                case 1: {
                    putStackInSlot(4, railRecipe(), false);
                    break;
                }
            }
        }
        super.onCraftMatrixChanged(inventory);
    }

    /**
     * <h2>recipe manager</h2>
     * manages crafting recipes, and their outputs.
     * the current implementation only supports shaped recipes.
     */
    @Deprecated
    private ItemStack transportRecipe() {
        return RecipeManager.getResult(new ItemStack[]{
                craftingTable.getStackInSlot(0), craftingTable.getStackInSlot(1), craftingTable.getStackInSlot(2),
                craftingTable.getStackInSlot(3), craftingTable.getStackInSlot(4), craftingTable.getStackInSlot(5),
                craftingTable.getStackInSlot(6), craftingTable.getStackInSlot(7), craftingTable.getStackInSlot(8)
        });
    }


    public ItemStack railRecipe(){
        if(craftingTable.getStackInSlot(4)!=null && craftingTable.getStackInSlot(4).getItem() instanceof ItemRail){
            DebugUtil.println("there was a rail in the slot");
        }

        //handle adding to an existing stack
        if(craftingTable.getStackInSlot(4)!=null && craftingTable.getStackInSlot(4).getItem() instanceof ItemRail &&
                craftingTable.getStackInSlot(0)==getStackIngot() &&
                craftingTable.getStackInSlot(1)==getStackTies() &&
                craftingTable.getStackInSlot(2)==getStackBallast()){

            ItemStack rail = ItemRail.setStackData(new ItemStack(CommonProxy.railItem),
                    craftingTable.getStackInSlot(0),craftingTable.getStackInSlot(1),craftingTable.getStackInSlot(2),
                    null);

            rail.getTagCompound().setInteger("count",
                    craftingTable.getStackInSlot(4).getTagCompound().getInteger("count")+1);
            return rail;
        }
        //handle making a new stack
        if(craftingTable.getStackInSlot(0)!=null && ingotInDirectory(craftingTable.getStackInSlot(0).getItem())) {
            return ItemRail.setStackData(new ItemStack(CommonProxy.railItem),
                    craftingTable.getStackInSlot(0),craftingTable.getStackInSlot(1),craftingTable.getStackInSlot(2),
                    null);
        }
        //todo: works, but goes to wrong slot,
        //maybe issue is that the host inventory this sorts into prioritizes the player's inventory before the crafters
        //potential fix would be to compensate for player inventory space as a buffer since the value is reliably static unlike storage
        return null;

    }

    public @Nullable ItemStack getStackBallast(){
        if(craftingTable.getStackInSlot(4)==null || craftingTable.getStackInSlot(4).getTagCompound()==null){
            return null;
        } else {
            return !craftingTable.getStackInSlot(4).getTagCompound().hasKey("ballast") ? null :
                    ItemStack.loadItemStackFromNBT(craftingTable.getStackInSlot(4).getTagCompound().getCompoundTag("ballast"));
        }
    }
    public @Nullable ItemStack getStackTies(){
        if(craftingTable.getStackInSlot(4)==null || craftingTable.getStackInSlot(4).getTagCompound()==null){
            return null;
        } else {
            return !craftingTable.getStackInSlot(4).getTagCompound().hasKey("ties") ? null :
                    ItemStack.loadItemStackFromNBT(craftingTable.getStackInSlot(4).getTagCompound().getCompoundTag("ties"));
        }
    }
    public @Nullable ItemStack getStackIngot(){
        if(craftingTable.getStackInSlot(4)==null || craftingTable.getStackInSlot(4).getTagCompound()==null){
            return null;
        } else {
            return !craftingTable.getStackInSlot(4).getTagCompound().hasKey("ingot") ? null :
                    ItemStack.loadItemStackFromNBT(craftingTable.getStackInSlot(4).getTagCompound().getCompoundTag("ingot"));
        }
    }



    public static boolean stackMatches(ItemStack slot, ItemStack recipe){
        if(slot==null && recipe==null){
            return true;
        } else if(slot==null || recipe==null){
            return false;
        } else if(slot.getItem()==recipe.getItem()){
            return slot.stackSize>=recipe.stackSize;
        }
        return false;
    }

    private static List<Item> ingotDirectory = new ArrayList<>();

    public static boolean ingotInDirectory(Item i){
        if(ingotDirectory.size()==0){
            String[] ores = OreDictionary.getOreNames();
            for(String o: ores) {
                if (o.contains("ingot")) {
                    for (ItemStack s : OreDictionary.getOres(o)) {
                        ingotDirectory.add(s.getItem());
                    }
                }
            }
        }
        return ingotDirectory.contains(i);

    }


    /**
     * <h2>crafting slot</h2>
     * this is a custom crafting slot to make sure that the GUI and inventory update properly.
     * this copies vanilla behavior but forces the craft matrix change event so we can track changes.
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
            onCraftMatrixChanged(craftingTable);
        }
        @Override
        public void onSlotChanged(){
            super.onSlotChanged();
            onCraftMatrixChanged(craftingTable);
        }
        @Override
        public void putStack(ItemStack p_75215_1_) {
            this.inventory.setInventorySlotContents(this.getSlotIndex(), p_75215_1_);
        }
    }

}
