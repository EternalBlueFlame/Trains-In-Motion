package trains.utility;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.oredict.OreDictionary;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.entities.GenericRailTransport;
import trains.items.ItemTicket;
import trains.tileentities.TileEntityStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Inventory management</h1>
 * sores the inventory variables, handles a good degree of the inventory processing, and NBT management.
 * @see ContainerHandler for the related code.
 *
 * if the inventory needs to be filtered, on entity creation call
 * @see InventoryHandler#setFilter(boolean, TrainsInMotion.itemTypes, ItemStack[])
 *
 * @author Eternal Blue Flame
 */
public class InventoryHandler implements IInventory{
    /**
     * <h2>variables</h2>
     * host defines the entity host, if this is null then it's expected to be a tile entity.
     * blockHost defines the tile entity host, if this is null, everything is expected to be null.
     * filter defines the array of items to check with the blacklist/whitelist, assuming isType is false.
     * isWhitelist defines if it searches via blacklist or whitelist.
     * isType defines if it should search via item or block type defined in
     *     @see TrainsInMotion.itemTypes
     */
    private GenericRailTransport host;
    private TileEntityStorage blockHost;
    private List<ItemStack> items = new ArrayList<ItemStack>();
    public List<ItemStack> filter = new ArrayList<ItemStack>();
    public boolean isWhitelist = false;
    public TrainsInMotion.itemTypes isType = TrainsInMotion.itemTypes.ALL;

    /**
     * <h2>entity constructor</h2>
     * sets the host variable, and inventory size then creates an instance of the class, this can be re-used for any train or rollingstock.
     * also creates ticket slots for every additional rider.
     */
    public InventoryHandler(GenericRailTransport host){
        if (host != null) {
            this.host = host;
            while (items.size() < getSizeInventory() + (host.getRiderOffsets().length-1)) {
                items.add(null);
            }
        } else {
            items.add(null);
        }
    }
    /**
     * <h2>tile entity constructor</h2>
     * sets the host variable, and inventory size then creates an instance of the class, this can be re-used for any tile entity.
     */
    public InventoryHandler(TileEntity craftingTable){
        if (craftingTable instanceof TileEntityStorage){
            while (items.size() < 9) {
                items.add(null);
            }
            blockHost = (TileEntityStorage) craftingTable;
        }
    }

    /**
     * <h2>define filters</h2>
     * this is called on the creation of an entity that need it's inventory filtered, or on the event that the entity's filter is set.
     * whitelist as true will allow only the defined types or items. while as false will allow anything except the defined types or items.
     * types in most cases will override items because items are always checked last, if at all.
     * itemTypes.ALL is basically just ignored, this is only called when you are not going to filter by type.
     */
    public void setFilter(boolean isWhitelist, TrainsInMotion.itemTypes type, ItemStack[] items){
        this.isWhitelist = isWhitelist;
        if (items != null) {
            filter.clear();
            filter.addAll(Arrays.asList(items));
        }
        isType = type;
    }

    /**
     * <h2>inventory size</h2>
     * @return the number of slots the inventory should have.
     * if it's a train we have to calculate the size based on the type and the size of inventory its supposed to have.
     */
    @Override
    public int getSizeInventory() {
        if (host != null) {
            int size =0;
            if (host instanceof EntityTrainCore){
                size++;
            }
            if (host.getType()== TrainsInMotion.transportTypes.STEAM || host.getType()== TrainsInMotion.transportTypes.NUCLEAR_STEAM){
                size++;
            }
            if (host.getRiderOffsets().length >1){
                size++;
            }
            return size+ (host.getInventorySize().getCollumn() * host.getInventorySize().getRow());

        } else if (blockHost != null){
            return 9;
        }
        return 0;
    }

    /**
     * <h2>get item</h2>
     * @return the item in the requested slot
     */
    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot <0 || slot >= getSizeInventory()){
            return null;
        } else {
            return items.get(slot);
        }
    }

    /**
     * <h2>decrease stack size</h2>
     * @return the itemstack with the decreased size. If the decreased size is equal to or less than the current stack size it returns null.
     */
    @Override
    public ItemStack decrStackSize(int slot, int stackSize) {
        if (items.size()>=slot && items.get(slot) != null) {
            ItemStack itemstack;

            if (items.get(slot).stackSize <= stackSize) {
                itemstack = items.get(slot).copy();
                items.set(slot, null);

                return itemstack;
            } else {
                itemstack = items.get(slot).splitStack(stackSize);
                if (items.get(slot).stackSize == 0) {
                    items.set(slot, null);
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    /**
     * <h2>Set slot</h2>
     * sets the slot contents, this is a direct override so we don't have to compensate for anything.
     */
    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
        if (p_70299_1_>=0 && p_70299_1_ < items.size()) {
            items.set(p_70299_1_, p_70299_2_);
        }
    }

    /**
     * <h2>name and stack limit</h2>
     * These are grouped together because they are pretty self-explanatory.
     */
    @Override
    public String getInventoryName() {
        if (host != null) {
            return host.getItem().getUnlocalizedName();
        } else {
            return TrainsInMotion.MODID + ":storage";
        }
    }
    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }
    @Override
    public int getInventoryStackLimit() {
        if (host != null) {
            return 64;
        } else if (blockHost!= null) {
            return 64;
        } else {
            return 0;
        }
    }

    /**
     * <h2>is Locked</h2>
     * returns if the entity is locked, and if it is, if the player is the owner.
     * This makes sure the inventory can be accessed by anyone if its unlocked and only by the owner when it is locked.
     * if it's a tile entity, it's just another null check to be sure no one crashes.
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        if (host != null){
            return host.getPermissions(p_70300_1_, false, false);
        } else {
            return blockHost != null;
        }
    }

    /**
     * <h2>slot limiter</h2>
     * this limits certain items to certain slots, this way ticket slots can only take tickets, and fuel slots can only take fuels.
     * TODO: this needs to be expanded to support blacklists and whitelists.
     * TODO: this may not even be needed with proper slot use.
     * @see FuelHandler
     */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {

        if (slot ==0) {
            if (host instanceof EntityTrainCore) {
                //if its a train, slot 0 is always fuel
                return FuelHandler.isFuel(itemStack, host);
            } else if (host != null && host.getRiderOffsets().length > 1) {
                //if it's not a train, the first slot is a ticket, assuming there are passengers.
                return itemStack.getItem() instanceof ItemTicket && ((ItemTicket) itemStack.getItem()).getTransport() == host.getPersistentID();
            } else {
                //if it's not a train, or rollingstock, it's either null or its a crafter.
                return blockHost != null;
            }
        }

        if (slot == 1) {
            if (host instanceof EntityTrainCore) {
                //if it's a train the second slot is either for the water for the boiler, or the ticket depending on the type of train
                if (host.getType() == TrainsInMotion.transportTypes.STEAM || host.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM) {
                    return FuelHandler.isWater(itemStack, host);
                } else if (host.getRiderOffsets().length > 1) {
                    //if it's not a train with a boiler, the second slot is a ticket,
                    return itemStack.getItem() instanceof ItemTicket && ((ItemTicket) itemStack.getItem()).getTransport() == host.getPersistentID();
                } else {
                    //if there are no passenger slots and it doesn't have a boiler then it's a generic inventory slot.
                    return true;
                }
            } else {
                //if it's not a train, it's a generic inventory slot for either a rollingstock or a crafter, so we only need to null check.
                return (host != null || blockHost != null);
            }


        } else if (slot == 2) {
            //if it's a train with passenger slots and a boiler then the third slot is for a ticket.
            if ((host.getType() == TrainsInMotion.transportTypes.STEAM || host.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM) &&
                    host.getRiderOffsets().length > 1) {
                return itemStack.getItem() instanceof ItemTicket && ((ItemTicket) itemStack.getItem()).getTransport() == host.getPersistentID();
            } else if (host instanceof EntityTrainCore) {
                //otherwise its a normal inventory slot
                return true;
            }
        } else {
            //otherwise it's just a null check
            return (host != null || blockHost != null);
        }

        //if it's not one of the 3 main slots, then it's just a normal slot and we only need to null check.
        return (host != null || blockHost != null);
    }


    /**
     * <h2>Get Ticket Slot</h2>
     * this just simply returns the itemstack in the ticket slot, assuming there is one.
     */
    public ItemStack getTicketSlot(){
        //if it's a train with a boiler, send back slot 2.
        if ((host.getType() == TrainsInMotion.transportTypes.STEAM || host.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM) &&
                host.getRiderOffsets().length > 1) {
            return items.get(2);
        } else if (host instanceof EntityTrainCore && host.getRiderOffsets().length > 1){
            //if it's a train without a boiler, send back slot 1
            return items.get(1);
        } else if (host.getRiderOffsets().length > 1){
            //if it's not a train, send back slot 0
            return items.get(0);
        }
        //if it's not meant to have multiple passengers, send back null because there is no ticket slot.
        return null;
    }

    /**
     * <h2>NBT functionality</h2>
     * we manage the functionality for reading and writing NBT tags of the inventory here, to simplify it in other classes.
     */
    public NBTTagCompound writeNBT(){
        NBTTagCompound nbtitems = new NBTTagCompound();
        for (int i = 0; i < items.size(); ++i) {
            if (items.get(i) != null) {
                items.get(i).writeToNBT(nbtitems);
            } else {
                new ItemStack(Items.potato, 0).writeToNBT(nbtitems);
            }
        }
        /*
        nbtitems.setInteger("filter.length", filter.size());
        for (ItemStack item : filter){
            if (item != null) {
                item.writeToNBT(nbtitems);
            } else {
                new ItemStack(Items.potato, 0).writeToNBT(nbtitems);
            }
        }
        */

        return nbtitems;
    }
    public void readNBT(NBTTagCompound tag, String tagName){
        NBTTagCompound nbtitems = tag.getCompoundTag(tagName);
        for (int i = 0; i < items.size(); i++) {
            ItemStack item = ItemStack.loadItemStackFromNBT(nbtitems);
            if (item.stackSize != 0) {
                setInventorySlotContents(i, item);
            }
        }
    }

    /**
     * <h2>Add item to train inventory</h2>
     * custom function for adding items to the train's inventory.
     * similar to a container's TransferStackInSlot function, this will automatically sort an item into the inventory.
     * if there is no room in the inventory for the item, it will drop on the ground.
     */
    public void addItem(ItemStack item){
        for (int i=1; i<getSizeInventory();i++){
            if (i==1){
                if (host.getType() == TrainsInMotion.transportTypes.STEAM || host.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM){
                    i++;
                }
                if (host.getRiderOffsets().length>1){
                    i++;
                }
            }

            if (getStackInSlot(i) ==null){
                setInventorySlotContents(i, item);
                return;
            } else if (getStackInSlot(i).getItem() == item.getItem() &&
                    item.stackSize + items.get(i).stackSize <= item.getMaxStackSize()){
                setInventorySlotContents(i, new ItemStack(item.getItem(), item.stackSize + items.get(i).stackSize));
                return;
            }
        }
        if (host != null) {
            host.dropItem(item.getItem(), item.stackSize);
        }
    }

    /**
     * <h2>unused</h2>
     * we have to initialize these values, but due to the design of the entity we don't actually use them.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {return null;}
    @Override
    public void markDirty() {}
    @Override
    public void openInventory() {}
    @Override
    public void closeInventory() {}
}
