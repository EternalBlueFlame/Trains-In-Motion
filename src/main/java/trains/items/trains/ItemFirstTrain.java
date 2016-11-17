package trains.items.trains;

import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import trains.TrainsInMotion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import trains.entities.trains.FirstTrain;
import trains.utility.Utility;

import java.util.UUID;

//change this to the train class you intend to use.
import static trains.entities.trains.FirstTrain.bogieOffset;

public class ItemFirstTrain extends Item {


    //constructor
    public ItemFirstTrain() {
        super();
        setCreativeTab(TrainsInMotion.creativeTab);
    }



    /**
     * spawns the train when the player/entity tries to use it on a tile.
     *
     * for information on the world spawn see
     * @see World#spawnEntityInWorld(Entity)
     *
     * for information on that the variables used in the spawn functions are doing
     * @see FirstTrain#FirstTrain(UUID, World, double, double, double)
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
	 *
	 * TODO this will likely have to be made significantly more generic so that we can simply extend it for any train/rollingstock
	 * TODO after that, make it into an List array loader from the main class to silplify API use.
     */
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer playerEntity, World worldObj, int posX, int posY, int posZ, int blockSide, float pointToRayX, float pointToRayY, float pointToRayZ) {
        
		if (Utility.isRailBlockAt(worldObj, posX,posY,posZ) && !worldObj.isRemote) {
            
			int playerMeta = MathHelper.floor_double((playerEntity.rotationYaw / 90.0F) + 2.5D) & 3;
            //IMPORTANT this defines the entity used
			FirstTrain entity = new FirstTrain(playerEntity.getGameProfile().getId(), worldObj, posX + 0.5D, posY, posZ + 0.5D);

            if (((BlockRailBase)worldObj.getBlock(posX,posY,posZ)).getBasicRailMetadata(worldObj, null,posX,posY,posZ) == 1){

                if (playerMeta == 1) {

                    if (!Utility.isRailBlockAt(worldObj, posX + MathHelper.floor_double(FirstTrain.bogieOffset.get(bogieOffset.size()-1)+ 1.0D ), posY, posZ)
                            && !Utility.isRailBlockAt(worldObj, posX + MathHelper.floor_double(FirstTrain.bogieOffset.get(0)- 1.0D ), posY, posZ)) {
                        playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track that is of sufficient length"));
                        return false;
                    }

                    for (double offset: FirstTrain.bogieOffset){
                        entity.bogieXYZ.add(new double[]{posX + 0.5D + offset, posY, posZ + 0.5D});
                    }

                    worldObj.spawnEntityInWorld(entity);
                    return true;

                }
				else if (playerMeta == 3) {

                    if (!Utility.isRailBlockAt(worldObj, posX - MathHelper.floor_double(FirstTrain.bogieOffset.get(bogieOffset.size()-1)+ 1.0D ), posY, posZ)
                            && !Utility.isRailBlockAt(worldObj, posX - MathHelper.floor_double(FirstTrain.bogieOffset.get(0)- 1.0D ), posY, posZ)) {
                        playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track that is of sufficient length"));
                        return false;
                    }

                    for (double offset: FirstTrain.bogieOffset){
                        entity.bogieXYZ.add(new double[]{posX + 0.5D - offset, posY, posZ + 0.5D});
                    }

                    worldObj.spawnEntityInWorld(entity);
                    return true;
                }
            }
			else if (((BlockRailBase)worldObj.getBlock(posX,posY,posZ)).getBasicRailMetadata(worldObj, null,posX,posY,posZ) == 0){

                if (playerMeta == 2) {

                    if (!Utility.isRailBlockAt(worldObj, posX, posY, posZ + MathHelper.floor_double(FirstTrain.bogieOffset.get(bogieOffset.size()-1)+ 1.0D ))
                            && !Utility.isRailBlockAt(worldObj, posX, posY, posZ + MathHelper.floor_double(FirstTrain.bogieOffset.get(0)- 1.0D ))) {
                        playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track that is of sufficient length"));
                        return false;
                    }

                    for (double offset: FirstTrain.bogieOffset){
                        entity.bogieXYZ.add(new double[]{posX + 0.5D, posY, posZ + 0.5D + offset});
                    }

                    worldObj.spawnEntityInWorld(entity);
                    return true;
                }
				else if (playerMeta == 0) {

                    if (!Utility.isRailBlockAt(worldObj, posX, posY, posZ - MathHelper.floor_double(FirstTrain.bogieOffset.get(bogieOffset.size()-1)+ 1.0D ))
                            && !Utility.isRailBlockAt(worldObj, posX, posY, posZ - MathHelper.floor_double(FirstTrain.bogieOffset.get(0)- 1.0D ))) {
                        playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track that is of sufficient length"));
                        return false;
                    }

                    for (double offset: FirstTrain.bogieOffset){
                        entity.bogieXYZ.add(new double[]{posX + 0.5D, posY, posZ + 0.5D - offset});
                    }

                    worldObj.spawnEntityInWorld(entity);
                    return true;
                }
            }

        }
		
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
