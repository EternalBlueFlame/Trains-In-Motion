package trains.items;

import net.minecraft.entity.Entity;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import trains.TrainsInMotion;
import com.mojang.authlib.GameProfile;
import mods.railcraft.api.carts.IMinecart;
import mods.railcraft.api.core.items.IMinecartItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import trains.entities.MinecartExtended;
import trains.entities.trains.FirstTrain;
import trains.gui.GUITrain;

import java.util.UUID;

public class ItemFirstTrain extends ItemMinecart implements IMinecart, IMinecartItem {
    //constructor
    public ItemFirstTrain() {
        super(0);
        setCreativeTab(TrainsInMotion.creativeTab);
    }
    /**
     *
     * @param owner the name of the player placing the cart or "[trainsinmotion]" with the brackets if it's being placed by a non-player
     * @param cart An ItemStack that contains the cart
     * @param world The World the cart is being placed in
     * @param posX the X position the cart is placed at
     * @param posY the Y position the cart is placed at
     * @param posZ the Z position the cart is placed at
     * @return
     */
    @Override
    public MinecartExtended placeCart(GameProfile owner, ItemStack cart, World world, int posX, int posY, int posZ) {
        /**
         * this will return anything that derrives MinecartExtended, in this case we use
         * @see FirstTrain#FirstTrain(UUID, World, double, double, double, float, float[], int, FluidTank[], int, int, int, int, boolean)
         */
        return new FirstTrain(owner.getId(), world, posX,posY,posZ, 120, new float[]{1,3,1}, 1,
                        new FluidTank[]{new FluidTank(new FluidStack(FluidRegistry.WATER, 0),10),new FluidTank(new FluidStack(FluidRegistry.WATER, 0),2)},
                        3,3, GUITrain.GUI_ID, 1001, true);
    }

    /**
     * spawns the train when the player/entity tries to use it on a tile.
     *
     * for information on the world spawn see
     * @see World#spawnEntityInWorld(Entity)
     *
     * for information on that the variables used in the spawn functions are doing
     * @see FirstTrain#FirstTrain(UUID, World, double, double, double, float, float[], int, FluidTank[], int, int, int, int, boolean)
     *
     * @param itemStack the itemstack that the cart comes from.
     * @param playerEntity the player entity using the item stack.
     * @param worldObj the world the item was used in
     * @param posX the X position that it was used on.
     * @param posY the Y position that it was used on.
     * @param posZ the Z position that it was used on.
     * @param blockSide the side of the block it was used on.
     * @param pointToRayX the X value of the ray trace to the exact position on the block it was used on.
     * @param pointToRayY the Y value of the ray trace to the exact position on the block it was used on.
     * @param pointToRayZ the Z value of the ray trace to the exact position on the block it was used on.
     *
     * @return defines whether or not to play the placing animation, we dont want to do this on server.
     */
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer playerEntity, World worldObj, int posX, int posY, int posZ, int blockSide, float pointToRayX, float pointToRayY, float pointToRayZ) {
        if (worldObj.isRemote) {
            return false;
        } else{
            worldObj.spawnEntityInWorld(
                    new FirstTrain(playerEntity.getGameProfile().getId(), worldObj, posX,posY,posZ, 120, new float[]{1,3,1}, 1,
                            new FluidTank[]{new FluidTank(new FluidStack(FluidRegistry.WATER, 0),10),new FluidTank(new FluidStack(FluidRegistry.WATER, 0),2)},
                            3,3, GUITrain.GUI_ID, 1001, true));
          return true;
        }
    }

    //if the item can be placed by a block or non-player entity
    @Override
    public boolean canBePlacedByNonPlayer(ItemStack cart) {
        return true;
    }
    //trains shouldn't match a cart filter.
    @Override
    public boolean doesCartMatchFilter(ItemStack stack, EntityMinecart cart) {
        return false;
    }

    /**
     * Sets the icon for the item
     */
    /*/
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Info.modID.toLowerCase() + ":trains/" + this.iconName);
    }
    /*/

}
