package ebf.tim.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.TrainsInMotion;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.registry.URIRegistry;
import ebf.tim.utility.DebugUtil;
import ebf.tim.utility.RailUtility;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

/**
 * <h1>Core Transport Item</h1>
 * this re-useable item is intended to work as an automation to create items for evert train and rollingstock.
 * @author Eternal Blue Flame
 */
public class ItemTransport extends Item {

    /**the list of strings to use for the item description*/
    private final String[] subtext;
    /**the class for the entity*/
    private final Class<? extends GenericRailTransport> transport;

    /**the main constructor.
     * @param information the list of strings to use for the item description
     * @param cart the class for the entity*/
    public ItemTransport(String[] information, Class<? extends GenericRailTransport> cart) {
        super();
        subtext = information;
        //if we did this anywhere else it would error. why it is fine here I will never know. But I'm gonna abuse that.
        transport = cart;
        setCreativeTab(TrainsInMotion.creativeTab);
    }

    /**
     * <h2>description text</h2>
     * this modifies the functionality of showing description text. in this case we're just adding more.
     * @param par1ItemStack the itemstack of which text is being displayed.
     * @param par2EntityPlayer the player holding said itemstack.
     * @param par3List the list of strings to define the description. rather than using \n like normal people, forge decided it a good idea to make each string in the array a new line.
     * @param par4 I have no idea what this is.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        for(String str : subtext){par3List.add(str);}
    }

    /**
     * <h2>place transport</h2>
     * this will place the transport in world, and decrease, or null, the itemstack if placement actually worked.
     * on the off chance the transport failed to cast to the proper class it will be printed to the log, the issue should ALWAYS be the fault of the transport's class.
     */
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer playerEntity, World worldObj, int posX, int posY, int posZ, int blockSide, float pointToRayX, float pointToRayY, float pointToRayZ) {
        try {
            if(RailUtility.placeOnRail(transport.getConstructor(UUID.class, World.class, double.class, double.class, double.class).newInstance(playerEntity.getGameProfile().getId(), worldObj, posX + 0.5D, posY, posZ + 0.5D), playerEntity, worldObj, posX, posY, posZ)){
                itemStack.stackSize--;
                if (itemStack.stackSize<=0){
                    itemStack=null;
                }
                return true;
            }
            return false;
        } catch (Exception e){
        	if(DebugUtil.dev()){
            	e.printStackTrace();
        	}
        	DebugUtil.log("Failed to cast : " + transport.toString() + "to a new generic transport entity");
        }
        return true;
    }

    /**
     * <h2>Item icon</h2>
     * Sets the icon for the item, this re-uses the unlocalized name defined by the class that instanced this.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(URIRegistry.ITEM_TRANSPORT_ICON.getResource(this.getUnlocalizedName()).toString());
    }

}
