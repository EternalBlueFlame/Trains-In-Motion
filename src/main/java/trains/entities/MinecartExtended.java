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

    //default constructor for registering entity
    public MinecartExtended(World world) {
        super(world);
    }
    //default constructor we actually use
    public MinecartExtended(World world, double xPos, double yPos, double zPos) {
        super(world, xPos, yPos, zPos);
    }


    /*/
    Core Game Overrides
    /*/
    //technically this is a normal minecart, so return the value for that, which isn't in the base game or another mod.
    @Override
    public int getMinecartType() {
        return 1000;
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
    }//TODO this should be false later when it can move on its own.
    @Override
    public void onUpdate() {
        super.onUpdate();
    }
    @Override
    public boolean canRiderInteract()
    {
        return true;
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

    //used by railcraft, this is needed but we'll obsolete this with our own methods because this is just poor.
    @Override
    public GameProfile getOwner(){return null;}

    //methods for getting/setting actual owner
    public void setOwner(UUID player){owner = player;}
    public UUID getOwnerUUID(){return owner;}

}
