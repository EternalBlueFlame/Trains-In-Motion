package trains.items.rollingstock;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import trains.TrainsInMotion;
import trains.entities.GenericRailTransport;
import trains.entities.rollingstock.Entity_Pullmans_Palace;
import trains.entities.trains.FirstTrain;
import trains.utility.RailUtility;

import java.util.UUID;


/**
 * <h2> First Train Item</h2>
 * when creating a new train or rollingstock you must make a clone of this class and set the values to match.
 * the constructor is the same for all except for and sub-text that the new train may or may not have.
 * onItemUse only needs the first value in placeOnRail changed to the class for your train/rollingstock entity.
 * registerIcons is used to change the icon of this item.
 * TODO: register icons may not even be necessary here, i think it can be covered in the train's constructor class for the item. needs testing.
 */

public class Item_Pullmans_Palace extends Item {

    //constructor
    public Item_Pullmans_Palace() {
        super();
        setCreativeTab(TrainsInMotion.creativeTab);
        //we set any sub-text for the item here
    }



    /**
     * spawns the train when the player/entity tries to use it on a tile.
     *
     * for information on the world spawn see
     * @see World#spawnEntityInWorld(Entity)
     *
     * for information on what the variables used in the spawn functions are doing
     * @see FirstTrain#FirstTrain(UUID, World, double, double, double)
     *
     * for the base functionality of how this works
     * @see RailUtility#placeOnRail(GenericRailTransport, EntityPlayer, World, int, int, int)
     *
     * @return defines whether or not to play the placing animation, we dont want to do this on server.
     */
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer playerEntity, World worldObj, int posX, int posY, int posZ, int blockSide, float pointToRayX, float pointToRayY, float pointToRayZ) {
        return RailUtility.placeOnRail(new Entity_Pullmans_Palace(playerEntity.getGameProfile().getId(), worldObj, posX + 0.5D, posY, posZ + 0.5D),playerEntity,worldObj,posX,posY,posZ);
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
