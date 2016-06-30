package trains.entities.trains;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import trains.entities.MinecartExtended;

import java.util.UUID;

public class FirstTrain extends MinecartExtended {
    //all we really need to do is extend the super class to a new class, and forward all the necessary call variables
    public FirstTrain(UUID owner, World world, double xPos, double yPos, double zPos, float maxSpeed, float[] acceleration, Item[] storageItemFilter, Material[] materialStorageFilter,
                      int type, FluidTank[] tank, int inventoryrows, int inventoryColumns, int GUIid, int minecartNumber, boolean canBeRidden) {
        super(owner, world, xPos, yPos, zPos, maxSpeed, acceleration, storageItemFilter, materialStorageFilter, type,tank,inventoryrows, inventoryColumns,GUIid,minecartNumber,canBeRidden);

    }
}
