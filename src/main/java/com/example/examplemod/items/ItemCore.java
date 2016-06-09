package com.example.examplemod.items;

import com.example.examplemod.TrainsInMotion;
import com.example.examplemod.entities.EntityTrainCore;
import com.mojang.authlib.GameProfile;
import mods.railcraft.api.carts.IMinecart;
import mods.railcraft.api.core.items.IMinecartItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCore extends ItemMinecart implements IMinecart, IMinecartItem {

    public ItemCore() {
        super(0);
        this.setCreativeTab(TrainsInMotion.creativeTab);
    }

    public EntityMinecart placeCart(EntityPlayer player, ItemStack itemstack, World world, int posX, int posY, int posZ) {
        return new EntityTrainCore(world, posX + 0.5F, posY + 0.5F,posZ + 0.5F, 100f, new float[]{1, 5, 2}, 2, 1);
    }


    @Override
    public boolean canBePlacedByNonPlayer(ItemStack cart) {
        return true;
    }

    @Override
    public EntityMinecart placeCart(GameProfile owner, ItemStack cart, World world, int posX, int posY, int posZ) {
        return placeCart((EntityPlayer) null, cart, world, posX, posY, posZ);
    }

    @Override
    public boolean doesCartMatchFilter(ItemStack stack, EntityMinecart cart) {
        return false;
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer playerEntity, World worldObj, int posX, int posY, int posZ, int blockSide, float pointToRayX, float raypointToRayY, float raypointToRayZ) {
        if (worldObj.isRemote) {
            return false;//returns wether or not to do animation placement.
        } else{
            // public EntityTrainCore(World world, double xPos, double yPos, double zPos, float maxSpeed, float[] acceleration, int inventorySlots, int type /*1-steam, 2-diesel, 3-electric*/)
            worldObj.spawnEntityInWorld(new EntityTrainCore(worldObj, posX,posY,posZ, 120, new float[]{1,3,1},2,1));
          return true;
        }
    }
    /*/
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Info.modID.toLowerCase() + ":trains/" + this.iconName);
    }
    /*/

}
