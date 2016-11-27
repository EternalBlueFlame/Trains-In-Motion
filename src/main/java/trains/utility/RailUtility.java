package trains.utility;


import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import trains.entities.EntityBogie;


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
    public static float[] rotatePoint(float[] f, float pitch, float yaw, float roll) {
        float cos;
        float sin;
        float[] xyz = new float[]{f[0],f[1],f[2]};

        if (pitch != 0.0F) {
            pitch *= radianF;
            cos = MathHelper.cos(pitch);
            sin = MathHelper.sin(pitch);

            xyz[0] = (f[1] * sin) + (f[0] * cos);
            xyz[1] = (f[1] * cos) - (f[0] * sin);
        }

        if (yaw != 0.0F) {
            yaw *= radianF;
            cos = MathHelper.cos(yaw);
            sin = MathHelper.sin(yaw);

            xyz[0] = (f[0] * cos) - (f[2] * sin);
            xyz[2] = (f[0] * sin) + (f[2] * cos);
        }

        if (roll != 0.0F) {
            roll *=  radianF;
            cos = MathHelper.cos(roll);
            sin = MathHelper.sin(roll);

            xyz[1] = (f[2] * cos) - (f[1] * sin);
            xyz[2] = (f[2] * sin) + (f[1] * cos);
        }

        return xyz;
    }

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



}
