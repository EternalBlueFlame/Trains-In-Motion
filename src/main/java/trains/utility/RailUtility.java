package trains.utility;


import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.world.World;
import trains.entities.EntityBogie;


public class RailUtility {
    public static final float radian = (float) Math.PI / 180.0F;
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




}
