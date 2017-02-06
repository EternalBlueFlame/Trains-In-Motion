package trains.entities;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import trains.utility.InventoryHandler;

import java.util.UUID;

/**
 * <h1>Rollingstock core</h1>
 * this is the management core for all trains.
 * @author Eternal Blue Flame
 */
public class EntityRollingStockCore extends GenericRailTransport {

    /**
     * <h3>variables</h3>
     * the inventory defines the item storage of the train.
     */
    public InventoryHandler inventory = new InventoryHandler(this);



    /**
    * <h2> Base rollingstock Constructor</h2>
    *
    * default constructor for all rollingstock, the first one is server only, the second is client only.
    *
    * @param owner the owner profile, used to define owner of the entity,
    * @param world the world to spawn the entity in, used in super's super.
    * @param xPos the x position to spawn entity at, used in super's super.
    * @param yPos the y position to spawn entity at, used in super's super.
    * @param zPos the z position to spawn entity at, used in super's super.
    */
    public EntityRollingStockCore(UUID owner, World world, double xPos, double yPos, double zPos){
        super(world, owner, xPos, yPos, zPos);
    }
    //this constructor is for client side spawning
    public EntityRollingStockCore(World world){
        super(world);
    }

    /**
     * <h2> Data Syncing and Saving </h2>
     * this is explained in
     * @see GenericRailTransport#readSpawnData(ByteBuf)
     */
    @Override
    public void readSpawnData(ByteBuf additionalData) {
    super.readSpawnData(additionalData);
    }
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        super.writeSpawnData(buffer);
    }
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);

        inventory.readNBT(tag, "items");

    }
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);

        tag.setTag("items", inventory.writeNBT());

    }


}
