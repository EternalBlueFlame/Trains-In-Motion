package ebf.tim.utility;


import ebf.tim.entities.EntityBogie;
import ebf.tim.entities.GenericRailTransport;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import zoranodensha.api.structures.tracks.ITrackBase;

import java.util.*;

/**
 * <h1>utilities</h1>
 * used for misc utility functions
 *
 * @author Eternal Blue Flame
 * @author Zora No Densha
 */
public class RailUtility {
    /**converts a degrees float to radians*/
    public static final float radianF = (float) Math.PI / 180.0f;
    /**converts a radians double to degrees*/
    public static final double degreesD = 180.0d / Math.PI;


    /**
     * <h2>Vanilla Track  detection Overrrides</h2>
     * a modified version of vanilla track detection so that way it's more efficient and can support rails from other mods.
     * @see EntityBogie#moveBogie(double, double, int, int, int, BlockRailBase)
     */
    public static boolean isRailBlockAt(World world, int x, int y, int z) {
        return (world.getTileEntity(x, y, z) instanceof ITrackBase || world.getBlock(x, y, z) instanceof BlockRailBase);
    }



    /**
     * <h3>rotate vector</h3>
     * rotates a given vector based on pitch, yaw, and roll.
     * courtesy of Zora No Densha.
     * There are version for doubles and floats.
     */
    public static double[] rotatePoint(double[] f, float pitch, float yaw, float roll) {
        double cos;
        double sin;
        double[] xyz = new double[]{f[0],f[1],f[2]};
        //rotate pitch
        if (pitch != 0.0F) {
            pitch *= radianF;
            cos = Math.cos(pitch);
            sin = Math.sin(pitch);

            xyz[0] = (f[1] * sin) + (f[0] * cos);
            xyz[1] = (f[1] * cos) - (f[0] * sin);
        }
        //rotate yaw
        if (yaw != 0.0F) {
            yaw *= radianF;
            cos = MathHelper.cos(yaw);
            sin = MathHelper.sin(yaw);

            xyz[0] = (f[0] * cos) - (f[2] * sin);
            xyz[2] = (f[0] * sin) + (f[2] * cos);
        }
        //rotate roll
        if (roll != 0.0F) {
            roll *=  radianF;
            cos = MathHelper.cos(roll);
            sin = MathHelper.sin(roll);

            xyz[1] = (f[2] * cos) - (f[1] * sin);
            xyz[2] = (f[2] * sin) + (f[1] * cos);
        }

        return xyz;
    }


    /**
     * <h2>rail placement from item</h2>
     * basic functionality to place a train or rollingstock on the rails on item use.
     */
    public static boolean placeOnRail(GenericRailTransport entity, EntityPlayer playerEntity, World worldObj, int posX, int posY, int posZ) {

        //be sure there is a rail at the location
        if (RailUtility.isRailBlockAt(worldObj, posX,posY,posZ) && !worldObj.isRemote) {
            //define the direction
            int playerMeta = MathHelper.floor_double((playerEntity.rotationYaw / 90.0F) + 2.5D) & 3;
            //check rail axis
            if (((BlockRailBase)worldObj.getBlock(posX,posY,posZ)).getBasicRailMetadata(worldObj, null,posX,posY,posZ) == 1){
                //check player direction
                if (playerMeta == 3) {
                    //check if the transport can be placed in the area
                    if (!RailUtility.isRailBlockAt(worldObj, posX + MathHelper.floor_double(entity.getLengthFromCenter()+ 1.0D ), posY, posZ)
                            && !RailUtility.isRailBlockAt(worldObj, posX + MathHelper.floor_double((-entity.getLengthFromCenter())- 1.0D ), posY, posZ)) {
                        playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track that is of sufficient length"));
                        return false;
                    }
                    entity.rotationYaw= 0;
                    //spawn the entity
                    worldObj.spawnEntityInWorld(entity);
                    return true;

                }
                //same as above, but reverse direction.
                else if (playerMeta == 1) {

                    if (!RailUtility.isRailBlockAt(worldObj, posX - MathHelper.floor_double(entity.getLengthFromCenter()+ 1.0D ), posY, posZ)
                            && !RailUtility.isRailBlockAt(worldObj, posX - MathHelper.floor_double((-entity.getLengthFromCenter())- 1.0D ), posY, posZ)) {
                        playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track that is of sufficient length"));
                        return false;
                    }
                    entity.rotationYaw= 180;

                    worldObj.spawnEntityInWorld(entity);
                    return true;
                }
            }
            //same as above but a different axis.
            else if (((BlockRailBase)worldObj.getBlock(posX,posY,posZ)).getBasicRailMetadata(worldObj, null,posX,posY,posZ) == 0){

                if (playerMeta == 0) {

                    if (!RailUtility.isRailBlockAt(worldObj, posX, posY, posZ + MathHelper.floor_double(entity.getLengthFromCenter()+ 1.0D ))
                            && !RailUtility.isRailBlockAt(worldObj, posX, posY, posZ + MathHelper.floor_double((-entity.getLengthFromCenter())- 1.0D ))) {
                        playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track that is of sufficient length"));
                        return false;
                    }
                    entity.rotationYaw= 90;

                    worldObj.spawnEntityInWorld(entity);
                    return true;
                }
                else if (playerMeta == 2) {

                    if (!RailUtility.isRailBlockAt(worldObj, posX, posY, posZ - MathHelper.floor_double(entity.getLengthFromCenter()+ 1.0D ))
                            && !RailUtility.isRailBlockAt(worldObj, posX, posY, posZ - MathHelper.floor_double((-entity.getLengthFromCenter())- 1.0D ))) {
                        playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track that is of sufficient length"));
                        return false;
                    }

                    entity.rotationYaw= 270;
                    worldObj.spawnEntityInWorld(entity);
                    return true;
                }
            }

        }

        return false;
    }


    /*
     *<h2> Ore Dictionary</h2>
     * we use HashMaps to collect ore directory data because even though it takes longer to collect the data, it's far more efficient to see if an entry exists than a list.
     * TODO: maybe have a post-init function to collect the ore directory data?
     */


    private static LinkedList<String> OREDICT_PLANK = null;
    private static LinkedList<String> OREDICT_LOG = null;
    private static LinkedList<String> OREDICT_COAL = null;

    public static boolean isLog(ItemStack i){
        if (OREDICT_LOG == null){
            OREDICT_LOG = new LinkedList<>();
            for (ItemStack item: OreDictionary.getOres("logWood")) {
                OREDICT_LOG.add(i.getUnlocalizedName());
            }
        }
        return OREDICT_LOG.contains(i.getUnlocalizedName());
    }

    public static boolean isPlank(ItemStack i){
        if (OREDICT_PLANK == null){
            OREDICT_PLANK = new LinkedList<>();
            for (ItemStack item: OreDictionary.getOres("plankWood")) {
                OREDICT_PLANK.add(i.getUnlocalizedName());
            }
            for (ItemStack item: OreDictionary.getOres("slabWood")) {
                OREDICT_PLANK.add(i.getUnlocalizedName());
            }
        }
        return OREDICT_PLANK.contains(i.getUnlocalizedName());
    }

    public static boolean isCoal(ItemStack i){
        if (OREDICT_COAL == null){
            OREDICT_COAL = new LinkedList<>();
            for (ItemStack item: OreDictionary.getOres("coal")) {
                OREDICT_COAL.add(i.getUnlocalizedName());
            }
        }
        return OREDICT_COAL.contains(i.getUnlocalizedName());
    }
	/**
	 * @author Herman Tulleken - http://devmag.org.za/2011/04/05/bzier-curves-a-tutorial/
	 * @param p1 - Point 1 in bezier curve
	 * @param p2 - Point 2 in curve
	 * @param p3 - point 3 in curve
	 * @param SegmentCount - How many segments do you want in the curve
	 * @return
	 */
	public static List<double[]> CalculateBezierCurve(double[] p1, double[] p2, double[] p3, double SegmentCount){
		
		List<double[]> Positions = new ArrayList<double[]>();
		
		for(double i = 0; i < SegmentCount; i++) {
			double Segment = i / SegmentCount;
			
			double Identifier = 1 - Segment;
			double TSquare = Segment * Segment;
			double USquare = Identifier * Identifier;
			double UCube = Math.pow(Identifier, 3);
			double TCube = Math.pow(Segment, 3);
			
			double[] p = {UCube * p1[0], UCube * p1[1], UCube * p1[2]};
			p[0] += 3 * USquare * Segment * p1[0];
			p[1] += 3 * USquare * Segment * p1[1];
			p[2] += 3 * USquare * Segment * p1[2];
			
			p[0] += 3 * Identifier * TSquare * p2[0];
			p[1] += 3 * Identifier * TSquare * p2[1];
			p[2] += 3 * Identifier * TSquare * p2[2];
			
			p[0] += TCube * Segment * p3[0];
			p[1] += TCube * Segment * p3[1];
			p[2] += TCube * Segment * p3[2];
			
			Positions.add(p);
		}
		return Positions;
	}
    
}
