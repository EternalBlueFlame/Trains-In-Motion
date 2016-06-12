package trains.entities;


import com.mojang.authlib.GameProfile;
import mods.railcraft.api.carts.IMinecart;
import mods.railcraft.api.carts.IRoutableCart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.UUID;

public class MinecartExtended extends EntityMinecart implements IMinecart, IRoutableCart {

    //Main Values
    public int[] colors; //allows certain parts of certain trains to be recolored
    public String name;
    public boolean isLocked = false; //mostly used to lock other players from using/accessing parts of the cart/train
    public boolean brake = false; //bool for the train/rollingstock's break.
    public boolean lamp = false; //controls the headlight/lamp
    //


    //railcraft variables
    public String destination = "";  //railcraft destination
    public boolean isLoco = false;  //if this can accept destination tickets, aka is a locomotive
    public UUID owner = null;  //universal, get train owner

    public MinecartExtended(World world, double xPos, double yPos, double zPos) {
        super(world, xPos, yPos, zPos);
    }


    /*/
    Core Game Overrides
    /*/
    //technically this is a normal minecart, so return the value for that.
    @Override
    public int getMinecartType() {
        return 0;
    }
    //cart management stuff
    @Override
    public boolean isPoweredCart() {
        return true;
    }
    @Override
    public boolean canBeRidden() {
        return true;
    }
    @Override
    public boolean canBePushed() {
        return true;
    }
    @Override
    public void onUpdate() {
        super.onUpdate();
    }



    public void applyEntityCollision(Entity p_70108_1_){
        if (!this.worldObj.isRemote && p_70108_1_ != this.riddenByEntity){
            p_70108_1_.mountEntity(this);
        }
    }

    /*/
    Railcraft support
    /*/
    @Override
    public String getDestination() {
        return destination;
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

    //used by railcraft, we'll obsolete this with our own methods
    @Override
    public GameProfile getOwner(){return null;}

    //methods for getting/setting owner
    public void setOwner(UUID player){this.owner = player;}
    public UUID getOwnerUUID(){return this.owner;}

}
