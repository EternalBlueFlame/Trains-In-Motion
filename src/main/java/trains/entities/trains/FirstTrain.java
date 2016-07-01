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
}
