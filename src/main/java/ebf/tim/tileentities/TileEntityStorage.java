package ebf.tim.tileentities;


import ebf.tim.TrainsInMotion;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.utility.ContainerHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ebf.tim.utility.InventoryHandler;

/**
 * <h1>Tile Entity storage container</h1>
 * this is a tile entity for storage, similar to the game's chest, but due to the advanced container class of this mod it can be used for anything from chests to crafting tables.
 * @see ContainerHandler
 * @author Eternal Blue Flame
 */
public class TileEntityStorage extends TileEntity {

    public InventoryHandler inventory = new InventoryHandler(this);

    /**
     * <h2>syncing</h2>
     * syncs data between client and server, also saves the data, for more info:
     * @see GenericRailTransport#readSpawnData(ByteBuf)
     */
    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        inventory.readNBT(p_145839_1_, "items");
    }

    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setTag("items", inventory.writeNBT());
    }

    /**
     * <h2>inventory size</h2>
     * define the size of the inventory of this entity
     */
    public TrainsInMotion.inventorySizes getInventorySize(){return TrainsInMotion.inventorySizes.THREExTHREE;}
}
