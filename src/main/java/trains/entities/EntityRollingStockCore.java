package trains.entities;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import trains.utility.InventoryHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h2> Train core</h2>
 * this is the management core for all trains.
 */
public class EntityRollingStockCore extends GenericRailTransport {

    /**
     * <h3>variables</h3>
     * the inventory defines the item storage of the train.
     */
    public InventoryHandler inventory = new InventoryHandler(this);
    public List<Entity> riddenByEntities = new ArrayList<Entity>();



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
        super(world);
        posY = yPos;
        posX = xPos;
        posZ = zPos;

        this.owner = owner;

        setSize(1f,2);
        this.boundingBox.minX = 0;
        this.boundingBox.minY = 0;
        this.boundingBox.minZ = 0;
        this.boundingBox.maxX = 0;
        this.boundingBox.maxY = 0;
        this.boundingBox.maxZ = 0;
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

        NBTTagList taglist = tag.getTagList("extended.passengers", 10);
        for (int i = 0; i < taglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = taglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("riderindex");

            if (b0 >= 0 && b0 < getRiderOffsets().length) {
                Entity rider = worldObj.getEntityByID(nbttagcompound1.getInteger("rider"));
                if (rider != null) {
                    riddenByEntities.add(rider);
                }
            }
        }

    }
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);

        tag.setTag("items", inventory.writeNBT());

        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < riddenByEntities.size(); ++i) {
            if (riddenByEntities.get(i) != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("riderindex", (byte)i);
                nbttagcompound1.setInteger("rider", riddenByEntities.get(i).getEntityId());
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        tag.setTag("extended.passengers", nbttaglist);

    }



    public float[][] getRiderOffsets(){return new float[][]{{0,0}};}


}
