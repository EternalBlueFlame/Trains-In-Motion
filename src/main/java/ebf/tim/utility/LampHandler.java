package ebf.tim.utility;

import cpw.mods.fml.common.gameevent.TickEvent;
import ebf.tim.entities.GenericRailTransport;
import net.minecraft.block.BlockAir;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/** <h1>Lamp management</h1>
* stores the lamp variables and updates it's position in world if it should be.
*
* @author Eternal Blue Flame
*/
public class LampHandler {
    /**the last known X position of the lamp*/
    public int X;
    /**the last known Y position of the lamp*/
    public int Y;
    /**the last known Z position of the lamp*/
    public int Z;
    /**a check used to see if an update to the lamp position is even necessary, why update a position that isn't moving?*/
    private boolean shouldUpdate = true;

    /**
     * <h2>Update functionality</h2>
     * the check function for whether or not to update here.
     *
     * this us used by
     * @see GenericRailTransport#onUpdate()
     * @see ClientProxy#onTick(TickEvent.ClientTickEvent)
     * The data for this is saved to NBT in the entity
     * @see GenericRailTransport#writeToNBT(NBTTagCompound)
     *
     * @param worldObj the world to place the lamp.
     * @param position defines the position to move the lamp to.
     */
    public void ShouldUpdate(World worldObj, double[] position){
        if (X != position[0]){
            shouldUpdate = true;
        } else if (Y != position[1]){
            shouldUpdate = true;
        } else if (Z != position[2]){
            shouldUpdate = true;
        }
        if(shouldUpdate){
            //if there was a block placed previously, remove it.
            if (X+Y+Z != 0) {
                worldObj.setBlockToAir(X, Y, Z);
            }
            //set the values
            X = MathHelper.floor_double(position[0]);
            Y = MathHelper.floor_double(position[1]);
            Z = MathHelper.floor_double(position[2]);
            //create the block.
            if (worldObj.getBlock(X,Y,Z) instanceof BlockAir) {
                worldObj.setBlock(X,Y,Z, ClientProxy.lampBlock);
            }
            shouldUpdate = false;

        }
    }


}
