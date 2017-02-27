package trains.entities;


import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import trains.utility.RailUtility;

/**
 * <h1>Bogie Core</h1>
 * this controls the behavior of the bogies in trains and rollingstock.
 * @author Eternal Blue Flame
 */
public class EntitySeat extends Entity implements IEntityAdditionalSpawnData {

    /**
     * <h2>variables</h2>
     * parentId is used to keep a reference to the parent train/rollingstock.
     * the velocities are to replace the client only velocities in forge that have private access.
     */
    public int parentId = 0;
    private int seatNumber =0;
    protected double cartVelocityX =0;
    protected double cartVelocityY =0;
    protected double cartVelocityZ =0;

    public EntitySeat(World world) {
        super(world);
    }

    public EntitySeat(World world, double xPos, double yPos, double zPos, int parent, int seatNumber) {
        super(world);
        this.posX = xPos;
        this.posY = yPos;
        this.posZ = zPos;
        parentId = parent;
        this.seatNumber = seatNumber;
    }

    /**
     * <h3>Core Minecart Overrides</h3>
     * technically this is a normal minecart, which is why it works on normal tracks.
     * @see EntityMinecart
     *
     * TODO: getMaxCartSpeedOnRail needs to be reworked in accordance with the max speed the rail block will give, or a fallback for if there is no rail probably something to do in
     * @see RailUtility
     * onUpdate is intentionally empty because we don't want the super running it's own onUpdate method. we define when to run our movement code in the train/rollingstock
     * @see EntityTrainCore#onUpdate()
     */
    @Override
    public boolean canBePushed() {
        return false;
    }
    @Override
    public boolean canRiderInteract() {
        return true;
    }
    @Override
    public void entityInit(){}
    @Override
    public void onUpdate() {}
    @Override
    public AxisAlignedBB getBoundingBox(){
        return null;
    }
    @Override
    public AxisAlignedBB getCollisionBox(Entity collidedWith){
        return null;
    }
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }


    /**
     * <h2>Spawn Data</h2>
     * Small networking check to add the bogie to the host train/rollingstock. Or to remove the bogie from the world if the host doesn't exist.
     */
    @Override
    public void readSpawnData(ByteBuf additionalData) {
        parentId = additionalData.readInt();
        if (parentId != 0) {
            GenericRailTransport parent = ((GenericRailTransport) worldObj.getEntityByID(parentId));
            if (parent != null){
                parent.addseats(this);
            } else {
                worldObj.removeEntity(this);
            }
        } else {
            worldObj.removeEntity(this);
        }
    }
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(parentId);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {}
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {}

    /**
     * <h2>Client Movement code</h2>
     * this is mostly used to sync and smooth movement between client and server.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int turnProgress) {
        posX = x;
        posY = y;
        posZ = z;
        motionX = cartVelocityX;
        motionY = cartVelocityY;
        motionZ = cartVelocityZ;
    }
    @Override
    public void setVelocity(double x, double y, double z) {
        cartVelocityX = motionX = x;
        cartVelocityY = motionY = y;
        cartVelocityZ = motionZ = z;
    }

}
