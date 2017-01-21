package trains.tileentities;


import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import trains.TrainsInMotion;
import trains.utility.InventoryHandler;

public class TileEntityStorage extends TileEntity {

    public InventoryHandler inventory = new InventoryHandler(this);

    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        inventory.readNBT(p_145839_1_, "items");
    }

    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setTag("items", inventory.writeNBT());
    }

    public TrainsInMotion.inventorySizes getInventorySize(){return TrainsInMotion.inventorySizes.NULL;}
}
