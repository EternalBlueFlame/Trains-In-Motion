package trains.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import scala.actors.threadpool.Arrays;
import trains.TrainsInMotion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import trains.entities.EntityTrainCore;
import trains.entities.MinecartExtended;
import trains.entities.trains.FirstTrain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemFirstTrain extends Item {
    //constructor
    public ItemFirstTrain() {
        super();
        setCreativeTab(TrainsInMotion.creativeTab);
    }
	
	public static int length = 5;

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
        
		if (BlockRailBase.func_150051_a(worldObj.getBlock(posX,posY,posZ))) {
            
			int playerMeta = MathHelper.floor_double((playerEntity.rotationYaw / 90.0F) + 2.5D) & 3;
			Block block;
			EntityTrainCore entity = new FirstTrain(playerEntity.getGameProfile().getId(), worldObj, posX + 0.5D, posY, posZ + 0.5D);

            if (((BlockRailBase)worldObj.getBlock(posX,posY,posZ)).getBasicRailMetadata(worldObj, null,posX,posY,posZ) == 1){

                if (playerMeta == 1) {
                    
					for (int i = 1; i < length; ++i){
						
						block = worldObj.getBlock(posX + i, posY, posZ);
                        
						if (!BlockRailBase.func_150051_a(block) || ((BlockRailBase) block).getBasicRailMetadata(worldObj, null, posX + i, posY, posZ) != 1) {
							
							playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track"));
                            return false;
                        }
                    }

					entity.setBogies(Arrays.asList(new MinecartExtended[]{new MinecartExtended(worldObj, posX + 0.5D, posY, posZ + 0.5D), new MinecartExtended(worldObj, posX + 0.5D + length, posY, posZ + 0.5D)}));
                }
				else if (playerMeta == 3) {
                    
					for (int i = 1; i < length; ++i){
						
						block = worldObj.getBlock(posX - i, posY, posZ);
                        
						if (!BlockRailBase.func_150051_a(block) || ((BlockRailBase) block).getBasicRailMetadata(worldObj, null, posX - i, posY, posZ) != 1) {
							
							playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track"));
                            return false;
                        }
                    }

					entity.setBogies(Arrays.asList(new MinecartExtended[]{new MinecartExtended(worldObj, posX + 0.5D, posY, posZ + 0.5D), new MinecartExtended(worldObj, posX + 0.5D - length, posY, posZ + 0.5D)}));
                }
            }
			else if (((BlockRailBase)worldObj.getBlock(posX,posY,posZ)).getBasicRailMetadata(worldObj, null,posX,posY,posZ) == 0){

                if (playerMeta == 2) {
                    
					for (int i = 1; i < length; ++i){
						
						block = worldObj.getBlock(posX, posY, posZ + i);
                        
						if (!BlockRailBase.func_150051_a(block) || ((BlockRailBase) block).getBasicRailMetadata(worldObj, null, posX, posY, posZ + i) != 0) {
							
							playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track"));
                            return false;
                        }
                    }

					entity.setBogies(Arrays.asList(new MinecartExtended[]{new MinecartExtended(worldObj, posX + 0.5D, posY, posZ + 0.5D), new MinecartExtended(worldObj, posX + 0.5D, posY, posZ + 0.5D + length)}));
                }
				else if (playerMeta == 0) {
                    
					for (int i = 1; i < length; ++i){
						
						block = worldObj.getBlock(posX, posY, posZ - i);
                        
						if (!BlockRailBase.func_150051_a(block) || ((BlockRailBase) block).getBasicRailMetadata(worldObj, null, posX, posY, posZ - i) != 0) {
							
							playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track"));
                            return false;
                        }
                    }

					entity.setBogies(Arrays.asList(new MinecartExtended[]{new MinecartExtended(worldObj, posX + 0.5D, posY, posZ + 0.5D), new MinecartExtended(worldObj, posX + 0.5D, posY, posZ + 0.5D - length)}));
                }
            }
			
			if (entity.bogie.size()>0) {
                worldObj.spawnEntityInWorld(entity);
                return true;
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
