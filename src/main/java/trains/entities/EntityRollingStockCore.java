package trains.entities;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

import java.util.UUID;

public class EntityRollingStockCore extends MinecartExtended {

    public Item[] storageFilter = new Item[]{};//item set to use for filters, storage only accepts items in the filter
    public Material[] storageMaterialFilter = new Material[]{};//same as item filter but works for materials
    public int type=0; //used to define the type of rollingstock.

    /**
     * this class defines the core of the Rolling Stock, in most cases the functionality will be altered by the class that extends this, however whatever is generic to rolling stock specifically
     * for things generic to  trains:
     * @see EntityTrainCore
     *
     * for things generic to both these classes:
     * @see MinecartExtended
     *
     * default constructor for setting up variables after this is created
     * @param owner the owner profile, used to define owner of the entity, used in super.
     * @param world the world to spawn the entity in, used in super's super.
     * @param xPos the x position to spawn entity at, used in super's super.
     * @param yPos the y position to spawn entity at, used in super's super.
     * @param zPos the z position to spawn entity at, used in super's super.
     * @param inventoryrows defines the rows of inventory, inventory size is defined by rows * columns.
     * @param inventoryColumns defines the columns of the inventory.
     * @param craftingSlots defines the number of crafting slots, used for things like furnaces, workbenches, or item slots.
     * @param storageItemFilter used to filter the items that storage accepts, if there is storage. not in super.
     * @param storageMaterialFilter used to filter materials storage accepst, if there is storage. not in super
     * @param type what kind of rolling stock it is.
     */
    public EntityRollingStockCore(UUID owner, World world, double xPos, double yPos, double zPos, Item[] storageItemFilter, Material[] storageMaterialFilter,
                           int type, FluidTank[] tank , int inventoryrows, int inventoryColumns, int craftingSlots, int GUIid, int minecartNumber, boolean canBeRidden) {
        super(owner, world, xPos, yPos, zPos, type, tank, inventoryrows, inventoryColumns, craftingSlots, GUIid, minecartNumber, canBeRidden);
        storageFilter = storageItemFilter;
        this. storageMaterialFilter = storageMaterialFilter;
        this.type = type;
    }

    /**
     * we have to have the constructor for the initial spawn that puts the train in the world, minecraft does this, we don't have to mess with it other than just having it.
     *
     * @param world the world to spawn it in.
     */
    public EntityRollingStockCore(World world){
        super(world);
    }
}
