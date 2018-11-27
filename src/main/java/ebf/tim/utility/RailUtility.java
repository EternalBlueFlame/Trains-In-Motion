package ebf.tim.utility;


import ebf.tim.entities.EntityBogie;
import ebf.tim.entities.GenericRailTransport;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import fexcraft.tmt.slim.Vec3d;
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
    public static final float degreesF = (float) (180.0d / Math.PI);


    /**
     * <h2>Vanilla Track  detection Overrrides</h2>
     * a modified version of vanilla track detection so that way it's more efficient and can support rails from other mods.
     * @see EntityBogie#moveBogie(double, double, int, int, int, BlockRailBase)
     */
    public static boolean isRailBlockAt(World world, int x, int y, int z) {
        return (world.getTileEntity(x, y, z) instanceof ITrackBase || world.getBlock(x, y, z) instanceof BlockRailBase);
    }



    public static String translate(String text){
        if (StatCollector.translateToLocal(text).equals(text)){
            DebugUtil.println("Missing lang entry for: " +text);
            return text;
        } else {
            return StatCollector.translateToLocal(text);
        }
    }


    /**
     * replacement for system atan2 function.
     * uses a lookup list of 1024 float entries (4kb roughly).
     * performance is measured at over 15 times more efficient.
     * @param x
     * @param z
     * @return angle in radians
     */
    public static float atan2f(double x, double z) {
        float pi =-3.141592653f;
        float multiplier = 1.0f;

        if (z < 0.0d) {
            if (x < 0.0d) {
                z = -z;
                x = -x;
            } else {
                z = -z;
                multiplier = -1.0f;
            }

        } else {
            if (x < 0.0d) {
                x = -x;
                multiplier = -1.0f;
            }

            pi = 0.0f;
        }

        double invDiv = 1.0D / (((z < x) ? x : z) * (1.0D / (ATAN2_SQRT - 1)));
        return (atan2[(int)(x * invDiv) * ATAN2_SQRT + (int)(z * invDiv)] + pi) * multiplier;
    }

    public static float atan2degreesf(double x, double y){
        return atan2f(x,y)*57.295779514f;
    }

    private static final int ATAN2_SQRT = (int) Math.sqrt(1024);
    private static final float[] atan2 = new float[1024];
    static {
        for (int i = 0; i < ATAN2_SQRT; i++) {
            for (int j = 0; j < ATAN2_SQRT; j++) {
                atan2[j * ATAN2_SQRT + i] = (float) Math.atan2((float) j / ATAN2_SQRT, (float) i / ATAN2_SQRT);
            }
        }
    }


    public static float power(float base, float power){
        float result = 1.0f;
        for(float x = 0; x < power; x++) {
            result *= base;
        }

        return result;
    }


    /**
     * <h3>rotate vector</h3>
     * rotates a given vector based on pitch, yaw, and roll.
     * courtesy of Zora No Densha.
     * There are version for doubles and floats.
     */
    @Deprecated
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

    public static float[] rotatePointF(float[] f, float pitch, float yaw, float roll) {
        float cos;
        float sin;
        float[] xyz = new float[]{f[0],f[1],f[2]};
        //rotate pitch
        if (pitch != 0.0F) {
            pitch *= radianF;
            cos = MathHelper.cos(pitch);
            sin = MathHelper.sin(pitch);

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

    public static Vec3d rotatePoint(Vec3d f, float pitch, float yaw, float roll) {
        double cos;
        double sin;
        Vec3d xyz = f;
        //rotate pitch
        if (pitch != 0.0F) {
            pitch *= radianF;
            cos = Math.cos(pitch);
            sin = Math.sin(pitch);

            xyz.xCoord = (f.yCoord * sin) + (f.xCoord * cos);
            xyz.yCoord = (f.yCoord * cos) - (f.xCoord * sin);
        }
        //rotate yaw
        if (yaw != 0.0F) {
            yaw *= radianF;
            cos = MathHelper.cos(yaw);
            sin = MathHelper.sin(yaw);

            xyz.xCoord = (f.xCoord * cos) - (f.zCoord * sin);
            xyz.zCoord = (f.xCoord * sin) + (f.zCoord * cos);
        }
        //rotate roll
        if (roll != 0.0F) {
            roll *=  radianF;
            cos = MathHelper.cos(roll);
            sin = MathHelper.sin(roll);

            xyz.yCoord = (f.zCoord * cos) - (f.yCoord * sin);
            xyz.zCoord = (f.zCoord * sin) + (f.yCoord * cos);
        }

        return xyz;
    }

    public static Vec3d rotateDistance(double distance, float pitch, float yaw) {
        Vec3d xyz = new Vec3d(distance, 0,0);
        //rotate pitch
        if (pitch != 0.0F) {
            pitch *= radianF;
            xyz.xCoord = distance * Math.cos(pitch);
            xyz.yCoord = distance * Math.sin(pitch);
        }
        //rotate yaw
        if (yaw != 0.0F) {
            yaw *= radianF;
            xyz.xCoord = (distance * MathHelper.cos(yaw));
            xyz.zCoord = (distance * MathHelper.sin(yaw));
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
                    if (!RailUtility.isRailBlockAt(worldObj, posX + MathHelper.floor_double(entity.bogieLengthFromCenter()+ 1.0D ), posY, posZ)
                            && !RailUtility.isRailBlockAt(worldObj, posX + MathHelper.floor_double((-entity.bogieLengthFromCenter())- 1.0D ), posY, posZ)) {
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

                    if (!RailUtility.isRailBlockAt(worldObj, posX - MathHelper.floor_double(entity.bogieLengthFromCenter()+ 1.0D ), posY, posZ)
                            && !RailUtility.isRailBlockAt(worldObj, posX - MathHelper.floor_double((-entity.bogieLengthFromCenter())- 1.0D ), posY, posZ)) {
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

                    if (!RailUtility.isRailBlockAt(worldObj, posX, posY, posZ + MathHelper.floor_double(entity.bogieLengthFromCenter()+ 1.0D ))
                            && !RailUtility.isRailBlockAt(worldObj, posX, posY, posZ + MathHelper.floor_double((-entity.bogieLengthFromCenter())- 1.0D ))) {
                        playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track that is of sufficient length"));
                        return false;
                    }
                    entity.rotationYaw= 90;

                    worldObj.spawnEntityInWorld(entity);
                    return true;
                }
                else if (playerMeta == 2) {

                    if (!RailUtility.isRailBlockAt(worldObj, posX, posY, posZ - MathHelper.floor_double(entity.bogieLengthFromCenter()+ 1.0D ))
                            && !RailUtility.isRailBlockAt(worldObj, posX, posY, posZ - MathHelper.floor_double((-entity.bogieLengthFromCenter())- 1.0D ))) {
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
    
}
