package trains.entities.trains;

import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import trains.entities.EntityTrainCore;

import java.util.UUID;

public class FirstTrain extends EntityTrainCore {

    public FirstTrain(UUID owner, World world, double xPos, double yPos, double zPos, float maxSpeed, float[] acceleration,
                      int type, FluidTank[] tank, int inventoryrows, int inventoryColumns, int GUIid, int minecartNumber, boolean canBeRidden) {
        super(owner, world, xPos, yPos, zPos, maxSpeed, acceleration, type,tank,inventoryrows, inventoryColumns,GUIid,minecartNumber,canBeRidden);

    }

    /**
     * we have to have the constructor for the initial spawn that puts the train in the world, minecraft does this, we don't have to mess with it other than just having it.
     *
     * @param world the world to spawn it in.
     */
    public FirstTrain(World world){
        super(world);
    }
}
