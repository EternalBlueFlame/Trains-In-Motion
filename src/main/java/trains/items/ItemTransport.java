package trains.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import trains.TrainsInMotion;
import trains.entities.GenericRailTransport;
import trains.registry.URIRegistry;
import trains.utility.RailUtility;

import java.lang.reflect.Constructor;
import java.util.List;
public class ItemTransport extends Item {

    private final String[] subtext;
    private final Constructor<? extends GenericRailTransport> cart;
    public ItemTransport(String[] information, Constructor car) {
        super();
        subtext = information;
        cart =car;
        setCreativeTab(TrainsInMotion.creativeTab);
    }
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        for(String str : subtext){par3List.add(str);}
    }
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer playerEntity, World worldObj, int posX, int posY, int posZ, int blockSide, float pointToRayX, float pointToRayY, float pointToRayZ) {
        try {
            if(RailUtility.placeOnRail(cart.newInstance(playerEntity.getGameProfile().getId(), worldObj, posX + 0.5D, posY, posZ + 0.5D), playerEntity, worldObj, posX, posY, posZ)){
                itemStack.stackSize--;
                if (itemStack.stackSize<=0){
                    itemStack=null;
                }
                return true;
            }
            return false;
        } catch (Exception e){
            System.out.println("Failed to cast : " + cart.toString() + "to a new generic rollingstock entity");
        }
        return true;
    }

    /**
     * <h2>Item icon</h2>
     * Sets the icon for the item, this shouldn't need to be changed.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(URIRegistry.ITEM_TRANSPORT_ICON.getResource(this.getUnlocalizedName()).toString());
    }

}
