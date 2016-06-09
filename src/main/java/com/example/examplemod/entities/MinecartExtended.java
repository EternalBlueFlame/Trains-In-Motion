package com.example.examplemod.entities;


import com.mojang.authlib.GameProfile;
import mods.railcraft.api.carts.IMinecart;
import mods.railcraft.api.carts.IRoutableCart;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

public class MinecartExtended extends EntityMinecart implements IMinecart, IRoutableCart {

    //Main Values
    public int[] colors; //allows certain parts of certain trains to be recolored
    public String name;
    public boolean isLocked = false; //mostly used to lock other players from using/accessing parts of the cart/train
    public boolean brake = false; //bool for the train/rollingstock's break.
    public boolean lamp = false; //controls the headlight/lamp
    //


    //position values
    public World worldObj;
    public double xPosition;
    public double yPosition;
    public double zPosition;
    public double rollPosition;
    public double yawPosition;



    //railcraft variables
    public String destination = "";  //railcraft destination
    public boolean isLoco = false;  //if this can accept destination tickets, aka is a locomotive
    public GameProfile owner = null;  //universal, get train owner

    public MinecartExtended(World world, double xPos, double yPos, double zPos) {
        super(world, xPos, yPos, zPos);
        this.worldObj = world;
        this.xPosition = xPos;
        this.yawPosition = yPos;
        this.zPosition = zPos;
    }


    /*/
    Core Game Overrides
    /*/
    //technically this is a normal minecart, so return the value for that.
    @Override
    public int getMinecartType() {
        return 0;
    }


    /*/
    Railcraft support
    /*/
    @Override
    public String getDestination() {
        if (destination == null) {
            return "";
        } else {
            return destination;
        }
    }
    @Override
    public boolean doesCartMatchFilter(ItemStack stack, EntityMinecart cart) {
        if (stack == null || cart == null) { return false; }
        ItemStack cartItem = cart.getCartItem();
        return cartItem != null && stack.isItemEqual(cartItem);
    }
    //Only locomotives can receive a destination from a track.
    @Override
    public boolean setDestination(ItemStack ticket) {
        return isLoco;
    }

    //get/set owner is used for far more than just railcraft since it can be used like a normal function to get the game profile even without railcraft.
    @Override
    public GameProfile getOwner(){return this.owner;}
    public void setOwner(GameProfile player){this.owner = player;}

}
