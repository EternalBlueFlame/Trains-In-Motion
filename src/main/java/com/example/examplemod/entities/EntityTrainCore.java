package com.example.examplemod.entities;


import com.mojang.authlib.GameProfile;
import mods.railcraft.api.carts.IMinecart;
import mods.railcraft.api.carts.IRoutableCart;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

public class EntityTrainCore extends MinecartExtended implements IInventory {

    //Main Values
    public FluidTank tank; //depending on the train this is either used for diesel, steam, or redstone flux
    public ItemStack[] inventory;//Inventory, every train will have this to some extent or another,
    private int slotsFilled = 0; //used to manage
    public float maxSpeed; // the train's max speed
    public float[] acceleration; //the first 3 values are a point curve, representing 0-35%, 35-70% and >70% to modify how acceleration is handled at each point. //the 4th value defines how much the weight hauled effects acceleration.
    public int trainType=0;//list of train types 0 is null, 1 is steam, 2 is diesel, 3 is electric
    public boolean isRunning = false;// if the train is running/using fuel
    public boolean lamp = false; //controls the headlight/lamp




    //railcraft variables
    public String destination = "";  //railcraft destination
    public boolean isLoco = false;  //if this can accept destination tickets, aka is a locomotive
    public GameProfile owner = null;  //universal, get train owner

    public EntityTrainCore(World world, double xPos, double yPos, double zPos, float maxSpeed, float[] acceleration, int inventorySlots, int type /*1-steam, 2-diesel, 3-electric*/) {
        super(world, xPos, yPos, zPos);
        super.isLoco = true;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.trainType = type;
        this.inventory = new ItemStack[inventorySlots];
    }






    @Override
    public void onUpdate(){
        System.out.println("i exist");
        this.worldObj.setLightValue(EnumSkyBlock.Block, (int)this.posX, (int)this.posY, (int)this.posZ, 15);
        this.worldObj.scheduleBlockUpdateWithPriority((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ), 10, 5);
    }






    /*/
    Inventory stuff
    /*/

    @Override
    public String getInventoryName() {
        return name;
    }
    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public void markDirty() {
        if (!worldObj.isRemote) {
            this.slotsFilled = 0;
            for (int i = 0; i < getSizeInventory(); i++) {
                ItemStack itemstack = getStackInSlot(i);
                if (itemstack != null) {
                    slotsFilled++;
                }
            }
            //send the update packet
        }
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory[i];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.inventory[par1] != null) {
            ItemStack var2 = this.inventory[par1];
            this.inventory[par1] = null;
            return var2;
        }
        else {
            return null;
        }
    }
    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (inventory[i] != null) {
            if (inventory[i].stackSize <= j) {
                ItemStack itemstack = inventory[i];
                inventory[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = inventory[i].splitStack(j);
            if (inventory[i].stackSize == 0) {
                inventory[i] = null;
            }
            return itemstack1;

        }
        else {
            return null;
        }
    }
    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item){return true;}
    //return the number of inventory slots
    @Override
    public int getSizeInventory(){
        if(inventory != null){
            return inventory.length;
        } else{
            return 0;
        }
    }
    @Override
    public boolean isUseableByPlayer(EntityPlayer player){
        if (this.isLocked){
            if(owner.getId().equals(player.getUniqueID())){
                return true;
            } else {
                return false;
            }
        } else{
            return true;
        }
    }

}
