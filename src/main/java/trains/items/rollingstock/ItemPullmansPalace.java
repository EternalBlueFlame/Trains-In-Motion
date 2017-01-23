package trains.items.rollingstock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import trains.TrainsInMotion;
import trains.entities.GenericRailTransport;
import trains.entities.rollingstock.EntityPullmansPalace;
import trains.registry.URIRegistry;
import trains.utility.RailUtility;

import java.util.List;
import java.util.UUID;


/**
 * <h2>Pullman's palace Item</h2>
 * when creating a new train or rollingstock you must make a clone of this class and set the values to match.
 * the constructor is the same for all except for the sub-text .
 * onItemUse only needs the first value in placeOnRail changed to the class for your train/rollingstock entity.
 * @author Eternal Blue Flame
 */

public class ItemPullmansPalace extends Item {

    private static final String weight = "\u00A77" + StatCollector.translateToLocal("menu.item.weight") +": 2" + StatCollector.translateToLocal("menu.item.tons");

    /**
     * <h2>constructor</h2>
     * set the creative tab and call the super
     */
    public ItemPullmansPalace() {
        super();
        setCreativeTab(TrainsInMotion.creativeTab);
    }

    /**
     * <h2>item subtext</h2>
     * add description text to the item. To add a new line, add another entry to the list.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add(weight);
    }


    /**
     * spawns the train when the player/entity tries to use it on a tile.
     *
     * for information on the world spawn see
     * @see World#spawnEntityInWorld(Entity)
     *
     * for information on what the variables used in the spawn functions are doing
     * @see EntityPullmansPalace#EntityPullmansPalace(UUID, World, double, double, double)
     *
     * for the base functionality of how this works
     * @see RailUtility#placeOnRail(GenericRailTransport, EntityPlayer, World, int, int, int)
     *
     * @return defines whether or not to play the placing animation, we dont want to do this on server.
     */
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer playerEntity, World worldObj, int posX, int posY, int posZ, int blockSide, float pointToRayX, float pointToRayY, float pointToRayZ) {
        return RailUtility.placeOnRail(new EntityPullmansPalace(playerEntity.getGameProfile().getId(), worldObj, posX + 0.5D, posY, posZ + 0.5D),playerEntity,worldObj,posX,posY,posZ);
    }

    /**
     * <h2>Item icon</h2>
     * Sets the icon for the item, this shouldn't need to be changed.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(URIRegistry.ITEM_ROLLINGSTOCK_TEXTURE.getResource(this.getUnlocalizedName()).toString());
    }

}
