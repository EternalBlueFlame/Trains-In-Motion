package ebf.tim.entities;


import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.utility.RailUtility;
import io.netty.buffer.ByteBuf;
import mods.railcraft.api.carts.IMinecart;
import mods.railcraft.api.carts.IRoutableCart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import zoranodensha.api.structures.tracks.ITrackBase;

/**
 * <h1>Bogie Core</h1>
 * this controls the behavior of the bogies in trains and rollingstock.
 * @author Eternal Blue Flame
 */
public class EntityBogie extends EntityMinecart implements IMinecart, IRoutableCart, IEntityAdditionalSpawnData {

    /** used to keep a reference to the parent train/rollingstock.*/
    private int parentId = 0;
    /**client velocity used to smooth actual movement, this is a replacement for the vanilla velocity variables which have private access.*/
    private double cartVelocityX =0;
    /**client velocity used to smooth actual movement, this is a replacement for the vanilla velocity variables which have private access.*/
    private double cartVelocityY =0;
    /**client velocity used to smooth actual movement, this is a replacement for the vanilla velocity variables which have private access.*/
    private double cartVelocityZ =0;
    /**client velocity multiplier used to smooth actual movement, this is a replacement for the vanilla turnProgress which has private access.*/
    private double motionProgress=0;
    /**defines if this is the front bogie of the transport*/
    private boolean isFront=true;
    /**used to calculate the X/Y/Z velocity based on the direction the rail is facing, similar to how vanilla minecarts work.*/
    private static final int[][][] vanillaRailMatrix = new int[][][] {{{0, 0, -1}, {0, 0, 1}}, {{ -1, 0, 0}, {1, 0, 0}}, {{ -1, -1, 0}, {1, 0, 0}}, {{ -1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, { -1, 0, 0}}, {{0, 0, -1}, { -1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};

    public EntityBogie(World world) {
        super(world);
    }

    /**
     * used to initialize the entity
     * @param parent The EntityID of the parent entity (Must extend GenericRailTransport).
     * @param front whether or not this is the front bogie.
     */
    public EntityBogie(World world, double xPos, double yPos, double zPos, int parent, boolean front) {
        super(world);
        posX = xPos;
        posY = yPos;
        posZ = zPos;
            parentId = parent;
            isFront = front;
    }

    /**Small networking check to add the bogie to the host train/rollingstock. Or to remove the bogie from the world if the host doesn't exist.*/
    @Override
    public void readSpawnData(ByteBuf additionalData) {
        isFront = additionalData.readBoolean();
        parentId = additionalData.readInt();
        if (parentId != 0) {
            GenericRailTransport parent = ((GenericRailTransport) worldObj.getEntityByID(parentId));
            if (parent != null){
                parent.setBogie(this, isFront);
                return;
            }
        }
        worldObj.removeEntity(this);
    }
    /**sends the networking check on spawn/respawn so this can see if it should exist in the first place.*/
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeBoolean(isFront);
        buffer.writeInt(parentId);
    }

    /**used by the game to tell different types of minecarts from eachother, this doesnt effect us, so just use something random*/
    @Override
    public int getMinecartType() {
        return 10001;
    }
    /**returns if the cart can make itself move*/
    @Override
    public boolean isPoweredCart() {
        return true;
    }
    /**returns if the cart can be ridden*/
    @Override
    public boolean canBeRidden() {
        return false;
    }
    /**if the cart can be pushed by an entity*/
    @Override
    public boolean canBePushed() {
        return false;
    }
    /**returns if the rider can interact with this, I don't think this makes any difference given the design of the bogie*/
    @Override
    public boolean canRiderInteract() {
        return false;
    }
    /**defines the max speed the minecart can move on a rail that is not an extension of BlockRailBase*/
    @Override
    public float getMaxCartSpeedOnRail() {
        return 1.2F;
    }
    /**defines the update tick of the entity, in this case we rely on the transport to provide that for us, keeps things synced on the chance entities ever get individualized threads*/
    @Override
    public void onUpdate() {}
    /**returns the bounding box, we dont process that kind of stuff for the bogie because it's only pathfinding.*/
    @Override
    public AxisAlignedBB getBoundingBox(){
        return null;
    }
    /**returns the bounding box, we dont process that kind of stuff for the bogie because it's only pathfinding.*/
    @Override
    public AxisAlignedBB getCollisionBox(Entity collidedWith){
        return null;
    }
    /**returns if this can be collided with, since we don't process collisions, we return false*/
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    /**
     * <h2> movement management</h2>
     * this is modified movement from the super class, should be more efficient, and reliable, but generally does the same thing, minus ability to collide.
     * @see EntityMinecart#onUpdate()
     * Some features are replaced using our own for compatibility with ZoraNoDensha
     * @see RailUtility
     */
    public void minecartMove(float yaw, float pitch, boolean brake)   {
        //define the yaw from the super
        this.setRotation(yaw, pitch);
        //be sure to remove this if the parent is null, or in a different castle, I mean world.
        if (worldObj.getEntityByID(parentId) == null){
            worldObj.removeEntity(this);
        }

        //client only, update position
        if (this.worldObj.isRemote) {
            if (motionProgress > 0) {
                this.posX += (prevPosX - this.posX) / motionProgress;
                this.posY += (((prevPosY - this.posY) + yOffset) / motionProgress);
                this.posZ += (prevPosZ - this.posZ) / motionProgress;
                --motionProgress;
            }
        }
        //server only
        else {
            //update old position, add the gravity, and get the block below this,
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            int floorX = MathHelper.floor_double(this.posX);
            int floorY = MathHelper.floor_double(this.posY);
            int floorZ = MathHelper.floor_double(this.posZ);
            //compensate for y offsets based on current position, just in case the movement is too steep.
            if (!RailUtility.isRailBlockAt(this.worldObj, floorX, floorY, floorZ)) {
                if (RailUtility.isRailBlockAt(this.worldObj, floorX, floorY - 1, floorZ)) {
                    --floorY;
                } else if (worldObj.getBlock(floorX, floorY, floorZ) instanceof BlockAir && worldObj.getBlock(floorX, floorY-1, floorZ) instanceof BlockAir && posY>-64){
                    posY-=0.1D;
                }
            }

            //apply brake
            if (brake){
                this.motionX *= 0.75;
                this.motionZ *= 0.75;
                this.cartVelocityX *= 0.75;
                this.cartVelocityZ *= 0.75;
            }

            //update on normal rails
            if (worldObj.getBlock(floorX, floorY, floorZ) instanceof BlockRailBase) {
                Block block = this.worldObj.getBlock(floorX, floorY, floorZ);
                moveBogie(this.motionX, this.motionZ, floorX, floorY, floorZ, block);

                this.fallDistance = 0.0F;
                if (block == Blocks.activator_rail) {
                    this.onActivatorRailPass(floorX, floorY, floorZ, (worldObj.getBlockMetadata(floorX, floorY, floorZ) & 8) != 0);
                }
                //update on ZnD rails, and ones that don't extend block rail base.
            } else {
                super.onUpdate();
                //update on falling with no rails
            }
        }
    }


    /**
     * <h2>incrementally move the bogie</h2>
     * moves the entity in increments of a quarter block, the calculations are basically the same as vanilla.
     * The main difference is it's vastly simplified, and incremented to quarter blocks so it should be impossible to derail.
     * @param currentMotionX the MotionX value, because this is an indirect modification it won't actually effect the real MotionX.
     * @param currentMotionZ the MotionX value, because this is an indirect modification it won't actually effect the real MotionZ.
     * @param floorX the floored X value of the next position.
     * @param floorY the floored Y value of the next position.
     * @param floorZ the floored Z value of the next position.
     * @param block the block at the next position
     */
    private void moveBogie(double currentMotionX, double currentMotionZ, int floorX, int floorY, int floorZ, Block block) {
        double cachedMotionX = currentMotionX;
        double cachedMotionZ = currentMotionZ;
        //define the incrementation of movement, use the cache to store the real value and increment it down, and then throw it to the next loop, then use current for the clamped to calculate movement'
        if (currentMotionX>0.25){
            currentMotionX= 0.25;
            cachedMotionX -= 0.25;
        } else if (currentMotionX <-0.25){
            currentMotionX = -0.25;
            cachedMotionX += 0.25;
        } else {
            cachedMotionX= Math.copySign(0, currentMotionX);
        }
        if (currentMotionZ>0.25){
            currentMotionZ= 0.25;
            cachedMotionZ -=0.25;
        } else if (currentMotionZ <-0.25){
            currentMotionZ = -0.25;
            cachedMotionZ += 0.25;
        } else {
            cachedMotionZ= Math.copySign(0, currentMotionZ);
        }
        //get the direction of the rail from it's metadata
        int railMetadata = ((BlockRailBase)block).getBasicRailMetadata(worldObj, null, floorX, floorY, floorZ);

        //add the uphill/downhill velocity
        switch (railMetadata){
            case 2:{this.motionX -= 0.0078125D; this.posY = (double)(floorY + 1); break;}
            case 3:{this.motionX += 0.0078125D; this.posY = (double)(floorY + 1); break;}
            case 4:{this.motionZ += 0.0078125D; this.posY = (double)(floorY + 1); break;}
            case 5:{this.motionZ -= 0.0078125D; this.posY = (double)(floorY + 1); break;}
        }

        //beginMagic();

        //figure out the current rail's direction
        double railPathX = (vanillaRailMatrix[railMetadata][1][0] - vanillaRailMatrix[railMetadata][0][0]);
        double railPathZ = (vanillaRailMatrix[railMetadata][1][2] - vanillaRailMatrix[railMetadata][0][2]);
        double railPathSqrt = Math.sqrt(railPathX * railPathX + railPathZ * railPathZ);

        //if it ends up being reverse of what it should be, inverse the motion.
        if (currentMotionX * railPathX + currentMotionZ * railPathZ < 0.0D) {
            railPathX = -railPathX;
            railPathZ = -railPathZ;
        }

        //define the motion for current and overall based on the rail path for the relevant movement, so they are in sync.
        double motionSqrt = Math.sqrt(currentMotionX * currentMotionX + currentMotionZ * currentMotionZ);
        if (motionSqrt > 2.0D) {
            motionSqrt = 2.0D;
        }
        currentMotionX = motionSqrt * railPathX / railPathSqrt;
        currentMotionZ = motionSqrt * railPathZ / railPathSqrt;

        motionSqrt = Math.sqrt(motionX * motionX +motionZ * motionZ);
        if (motionSqrt > 2.0D) {
            motionSqrt = 2.0D;
        }
        motionX = motionSqrt * railPathX / railPathSqrt;
        motionZ = motionSqrt * railPathZ / railPathSqrt;

        if (cachedMotionX !=0 || cachedMotionZ !=0) {
            //define the motion based on the rail path for current movement, and the next, so they are in sync.
            motionSqrt = Math.sqrt(cachedMotionX * cachedMotionX + cachedMotionZ * cachedMotionZ);
            if (motionSqrt > 2.0D) {
                motionSqrt = 2.0D;
            }
            cachedMotionX = motionSqrt * railPathX / railPathSqrt;
            cachedMotionZ = motionSqrt * railPathZ / railPathSqrt;
        }

        //define the rail path again,,, for reasons.....
        double d8 = floorX + 0.5D + vanillaRailMatrix[railMetadata][0][0] * 0.5D;
        double d9 = floorZ + 0.5D + vanillaRailMatrix[railMetadata][0][2] * 0.5D;
        railPathX = (floorX + 0.5D + vanillaRailMatrix[railMetadata][1][0] * 0.5D) - d8;
        railPathZ = (floorZ + 0.5D + vanillaRailMatrix[railMetadata][1][2] * 0.5D) - d9;

        //pick the bigger one
        double d7;
        if (railPathX == 0.0D) {
            this.posX = (double)floorX + 0.5D;
            d7 = this.posZ - (double)floorZ;
        } else if (railPathZ == 0.0D) {
            this.posZ = (double)floorZ + 0.5D;
            d7 = this.posX - (double)floorX;
        } else {
            d7 = ((this.posX - d8) * railPathX + (this.posZ - d9) * railPathZ) * 2.0D;
        }
        //actually set the movement
        this.posX = (d8 + railPathX * d7) + currentMotionX;
        this.posZ = (d9 + railPathZ * d7) + currentMotionZ;
        this.prevPosY =motionY;
        //set the Y position
        if (vanillaRailMatrix[railMetadata][0][1] != 0 && MathHelper.floor_double(this.posX) - floorX == vanillaRailMatrix[railMetadata][0][0] && MathHelper.floor_double(this.posZ) - floorZ == vanillaRailMatrix[railMetadata][0][2]) {
            this.posY+=vanillaRailMatrix[railMetadata][0][1];
        }
        else if (vanillaRailMatrix[railMetadata][1][1] != 0 && MathHelper.floor_double(this.posX) - floorX == vanillaRailMatrix[railMetadata][1][0] && MathHelper.floor_double(this.posZ) - floorZ == vanillaRailMatrix[railMetadata][1][2]) {
            this.posY+=vanillaRailMatrix[railMetadata][1][1];
        }
        //endMagic();

        //do the rail functions.
        if(shouldDoRailFunctions()) {
            ((BlockRailBase)block).onMinecartPass(worldObj, this, floorX, floorY, floorZ);
        }
        //now loop this again for the next increment of movement
        if (cachedMotionX !=0 || cachedMotionZ !=0){

            floorX = MathHelper.floor_double(this.posX);
            floorY = MathHelper.floor_double(this.posY);
            floorZ = MathHelper.floor_double(this.posZ);
            block = this.worldObj.getBlock(floorX, floorY, floorZ);
            if (block instanceof BlockRailBase) {
                moveBogie(cachedMotionX, cachedMotionZ, floorX, floorY, floorZ, block);

                this.fallDistance = 0.0F;
                if (block == Blocks.activator_rail) {
                    this.onActivatorRailPass(floorX, floorY, floorZ, (worldObj.getBlockMetadata(floorX, floorY, floorZ) & 8) != 0);
                }
            }
        }

    }


    /**
     * <h1>Railcraft Functionality</h1>
     */


    /**checks if the parent transport matches the filter for railcraft items, if the filter is null or the parent is null, return false.*/
    @Override
    public boolean doesCartMatchFilter(ItemStack stack, EntityMinecart cart) {
        if (stack == null || worldObj.getEntityByID(parentId) == null) {
            return false;
        } else {
            Item cartItem = ((EntityTrainCore)worldObj.getEntityByID(parentId)).getItem();
            return cartItem != null && stack.getItem() == cartItem;
        }
    }
    /**returns the gameprofile of the owner for railcraft.*/
    @Override
    public GameProfile getOwner(){
        if (worldObj.getEntityByID(parentId) instanceof GenericRailTransport){
            GenericRailTransport parent = (GenericRailTransport) worldObj.getEntityByID(parentId);
            if (parent.getOwner() != null){
                return parent.getOwner();
            }
        }
        return null;
    }
    /**returns the destination of the parent for railcraft*/
    @Override
    public String getDestination() {
        if (worldObj.getEntityByID(parentId) instanceof GenericRailTransport){
            GenericRailTransport parent = (GenericRailTransport) worldObj.getEntityByID(parentId);
            if (parent.getOwner() != null){
                return parent.destination;
            }
        }
        return "";
    }
    /**this is supposed to set the ticket for the railcraft destination, but we don't use that, so we return false and do nothing.*/
    @Override
    public boolean setDestination(ItemStack ticket) {
        return false;
    }


    /**
     * <h2>Client Movement code</h2>
     */

    /**used to update positioning on client, and update the velocity multiplier*/
    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int turnProgress) {
        prevPosX = x;
        prevPosY = y;
        prevPosZ = z;
        motionX = cartVelocityX;
        motionY = cartVelocityY;
        motionZ = cartVelocityZ;
        motionProgress= turnProgress+2;
    }

    @Override
    public void setVelocity(double x, double y, double z) {
        cartVelocityX = motionX = x;
        cartVelocityY = motionY = y;
        cartVelocityZ = motionZ = z;
    }
    /**used to add to the current velocity movement, also sets this as airborne*/
    @Override
    public void addVelocity(double velocityX, double velocityY, double velocityZ){
            setVelocity(motionX + velocityX, motionY + velocityY, motionZ + velocityZ);
            isAirBorne = true;
    }
    /**override of the super method just so we can set the position without updating the hitbox, because we don't need to.*/
    @Override
    public void setPosition(double x, double y, double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }


}
