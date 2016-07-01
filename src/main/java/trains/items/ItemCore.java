package trains.items;

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
import trains.gui.GUITest;

public class ItemCore extends ItemMinecart implements IMinecart, IMinecartItem {
    //constructor
    public ItemCore() {
        super(0);
        setCreativeTab(TrainsInMotion.creativeTab);
    }
    //if the item can be placed by a block or non-player entity
    @Override
    public boolean canBePlacedByNonPlayer(ItemStack cart) {
        return true;
    }
    //placing the cart
    @Override
    public MinecartExtended placeCart(GameProfile owner, ItemStack cart, World world, int posX, int posY, int posZ) {
        return new MinecartExtended(owner.getId(), world, posX,posY,posZ, 120, new float[]{1,3,1}, null, null, 1,
                new FluidTank[]{new FluidTank(new FluidStack(FluidRegistry.WATER, 0),10),new FluidTank(new FluidStack(FluidRegistry.WATER, 0),2)},
                3,3,GUITest.GUI_ID, 1001, true);
    }
    //trains shouldn't match a cart filter.
    @Override
    public boolean doesCartMatchFilter(ItemStack stack, EntityMinecart cart) {
        return false;
    }
    //create train on use
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer playerEntity, World worldObj, int posX, int posY, int posZ, int blockSide, float pointToRayX, float raypointToRayY, float raypointToRayZ) {
        if (worldObj.isRemote) {
            return false;//returns whether or not to do animation placement.
        } else{

            //UUID owner,
            //World world, double xPos, double yPos, double zPos,
            //float maxSpeed,
            //float[] acceleration, //speed curve for acceleration
            //Item[] storageItemFilter /*/ null for no filter /*/ ,
            //Material[] storageMaterialFilter /*/ null for no filter /*/ ,
            //int type /*1-steam, 2-diesel, 3-electric, 4-hydrogen, 5-nuclear, 0-RollingStock*/,
            //FluidTank[] tank /*/ empty array for no tanks, - steam and nuclear take two tanks. - all other trains take one tank - all tanks besides diesel should use FluidRegistry.WATER /*/,
            //int inventorySlots,
            //int GUIid,
            //int minecartNumber,
            //boolean canBeRidden
            worldObj.spawnEntityInWorld(new MinecartExtended(playerEntity.getGameProfile().getId(), worldObj, posX,posY,posZ, 120, new float[]{1,3,1},
            		null, null, 1,
            		new FluidTank[]{new FluidTank(new FluidStack(FluidRegistry.WATER, 0),10),new FluidTank(new FluidStack(FluidRegistry.WATER, 0),2)},
                    3,3,GUITest.GUI_ID, 1001, true));
          return true;
        }
    }
    //set icon for item
    /*/
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Info.modID.toLowerCase() + ":trains/" + this.iconName);
    }
    /*/

}
