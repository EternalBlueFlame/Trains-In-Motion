package com.example.examplemod.items;

import com.example.examplemod.ExampleMod;
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
        this.setCreativeTab(ExampleMod.creativeTab);
        //BlockDispenser.dispenseBehaviorRegistry.putObject(this, placeCart());
    }

    public EntityMinecart placeCart(EntityPlayer player, ItemStack itemstack, World world, int i, int j, int k) {
        return new EntityTrainCore(world, (float) i + 0.5F, (float) j + 0.5F, (float) k + 0.5F, 100f, new float[]{1, 5, 2}, 2, 1);
    }


    @Override
    public boolean canBePlacedByNonPlayer(ItemStack cart) {
        return true;
    }

    @Override
    public EntityMinecart placeCart(GameProfile owner, ItemStack cart, World world, int i, int j, int k) {
        return placeCart((EntityPlayer) null, cart, world, i, j, k);
    }

    @Override
    public boolean doesCartMatchFilter(ItemStack stack, EntityMinecart cart) {
        return false;
    }
    /*/
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Info.modID.toLowerCase() + ":trains/" + this.iconName);
    }
    /*/

}
