package ebf.tim.entities;


import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * <h1>Seat Entity</h1>
 * A placeholder entity to serve as the seats (beyond the first one) for trains and rollingstock.
 * in 1.9+ this class is no longer necessary.
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

    /** returns if this can be pushed*/
    @Override
    public boolean canBePushed() {
        return false;
    }
    /**returns if the rider can interact, it shouldn't be necessary, but we'll leave it true just in case*/
    @Override
    public boolean canRiderInteract() {
        return true;
    }
    /**actually useless for this entity*/
    @Override
    public void entityInit(){}
    /**actually useless for this entity*/
    @Override
    public void onUpdate() {}
    /**returns the bounding box, this doesn't handle collisions, soo.. null.*/
    @Override
    public AxisAlignedBB getBoundingBox(){
        return null;
    }
    /**returns the bounding box, this doesn't handle collisions, soo.. null.*/
    @Override
    public AxisAlignedBB getCollisionBox(Entity collidedWith){
        return null;
    }
    /**returns if this can be collided with, but this doesn't handle collisions.*/
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    /**reads from NBT but this entity isn't persistent, so, don't,*/
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {}
    /**writes to NBT but this entity isn't persistent, so, don't,*/
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {}


    /**
     * <h2>Spawn Data</h2>
     * Small networking check to add the seat to the host train/rollingstock. Or to remove the seat from the world if the host doesn't exist.
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
