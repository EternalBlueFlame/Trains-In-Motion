package trains.utility;


import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import trains.entities.EntityBogie;
import trains.entities.GenericRailTransport;


public class RailUtility {
    public static final float radianF = (float) Math.PI / 180.0f;
    public static final double radianD = Math.PI / 180.0d;
    /**
     * <h2>Vanilla Track Overrrides</h2>
     * TODO: need ZND API for ITrackBase
     * TODO: other:
     * @see EntityBogie#minecartMove()
     *
     * we override some vanilla track detection so that way it's more efficient and can support rails from other mods.
     */
    public static boolean isRailBlockAt(World world, int x, int y, int z) { // Can later be substituted for BlockPos
        return (/*world.getTileEntity(x, y, z) instanceof ITrackBase ||*/ world.getBlock(x, y, z) instanceof BlockRailBase);
    }
    public static boolean isRailBlockAt(Block block) { // Can later be substituted for BlockPos
        return (/*world.getTileEntity(x, y, z) instanceof ITrackBase ||*/ block instanceof BlockRailBase);
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

        if (pitch != 0.0F) {
            pitch *= radianD;
            cos = Math.cos(pitch);
            sin = Math.sin(pitch);

            xyz[0] = (f[1] * sin) + (f[0] * cos);
            xyz[1] = (f[1] * cos) - (f[0] * sin);
        }

        if (yaw != 0.0F) {
            yaw *= radianD;
            cos = MathHelper.cos(yaw);
            sin = MathHelper.sin(yaw);

            xyz[0] = (f[0] * cos) - (f[2] * sin);
            xyz[2] = (f[0] * sin) + (f[2] * cos);
        }

        if (roll != 0.0F) {
            roll *=  radianD;
            cos = MathHelper.cos(roll);
            sin = MathHelper.sin(roll);

            xyz[1] = (f[2] * cos) - (f[1] * sin);
            xyz[2] = (f[2] * sin) + (f[1] * cos);
        }

        return xyz;
    }


    /**
     * <h2> rail placement from item</h2>
     * basic functionality to place a train or rollingstock on the rails on item use.
     * TODO: there seems to be an issue with train direction on placement
     */
    public static boolean placeOnRail(GenericRailTransport entity, EntityPlayer playerEntity, World worldObj, int posX, int posY, int posZ) {

        if (RailUtility.isRailBlockAt(worldObj, posX,posY,posZ) && !worldObj.isRemote) {

            int playerMeta = MathHelper.floor_double((playerEntity.rotationYaw / 90.0F) + 2.5D) & 3;

            if (((BlockRailBase)worldObj.getBlock(posX,posY,posZ)).getBasicRailMetadata(worldObj, null,posX,posY,posZ) == 1){

                if (playerMeta == 1) {

                    if (!RailUtility.isRailBlockAt(worldObj, posX + MathHelper.floor_double(entity.getBogieOffsets().get(entity.getBogieOffsets().size()-1)+ 1.0D ), posY, posZ)
                            && !RailUtility.isRailBlockAt(worldObj, posX + MathHelper.floor_double(entity.getBogieOffsets().get(0)- 1.0D ), posY, posZ)) {
                        playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track that is of sufficient length"));
                        return false;
                    }

                    for (double offset: entity.getBogieOffsets()){
                        entity.bogieXYZ.add(new double[]{posX + 0.5D + offset, posY, posZ + 0.5D});
                    }

                    worldObj.spawnEntityInWorld(entity);
                    return true;

                }
                else if (playerMeta == 3) {

                    if (!RailUtility.isRailBlockAt(worldObj, posX - MathHelper.floor_double(entity.getBogieOffsets().get(entity.getBogieOffsets().size()-1)+ 1.0D ), posY, posZ)
                            && !RailUtility.isRailBlockAt(worldObj, posX - MathHelper.floor_double(entity.getBogieOffsets().get(0)- 1.0D ), posY, posZ)) {
                        playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track that is of sufficient length"));
                        return false;
                    }

                    for (double offset: entity.getBogieOffsets()){
                        entity.bogieXYZ.add(new double[]{posX + 0.5D - offset, posY, posZ + 0.5D});
                    }

                    worldObj.spawnEntityInWorld(entity);
                    return true;
                }
            }
            else if (((BlockRailBase)worldObj.getBlock(posX,posY,posZ)).getBasicRailMetadata(worldObj, null,posX,posY,posZ) == 0){

                if (playerMeta == 2) {

                    if (!RailUtility.isRailBlockAt(worldObj, posX, posY, posZ + MathHelper.floor_double(entity.getBogieOffsets().get(entity.getBogieOffsets().size()-1)+ 1.0D ))
                            && !RailUtility.isRailBlockAt(worldObj, posX, posY, posZ + MathHelper.floor_double(entity.getBogieOffsets().get(0)- 1.0D ))) {
                        playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track that is of sufficient length"));
                        return false;
                    }

                    for (double offset: entity.getBogieOffsets()){
                        entity.bogieXYZ.add(new double[]{posX + 0.5D, posY, posZ + 0.5D + offset});
                    }

                    worldObj.spawnEntityInWorld(entity);
                    return true;
                }
                else if (playerMeta == 0) {

                    if (!RailUtility.isRailBlockAt(worldObj, posX, posY, posZ - MathHelper.floor_double(entity.getBogieOffsets().get(entity.getBogieOffsets().size()-1)+ 1.0D ))
                            && !RailUtility.isRailBlockAt(worldObj, posX, posY, posZ - MathHelper.floor_double(entity.getBogieOffsets().get(0)- 1.0D ))) {
                        playerEntity.addChatMessage(new ChatComponentText("Place on a straight piece of track that is of sufficient length"));
                        return false;
                    }

                    for (double offset: entity.getBogieOffsets()){
                        entity.bogieXYZ.add(new double[]{posX + 0.5D, posY, posZ + 0.5D - offset});
                    }

                    worldObj.spawnEntityInWorld(entity);
                    return true;
                }
            }

        }

        return false;
    }


}
