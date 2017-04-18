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

    /**
     * <h2>variables</h2>
     * parentId is used to keep a reference to the parent train/rollingstock.
     * position X/Y/Z is used to smooth out the movement of the bogies on client, a previously known position to move from to current based on the client update tick.
     * the vanillaRailMatrix is used to calculate the X/Y/Z velocity based on the direction the rail is facing, similar to how vanilla minecarts work.
     */
    private int parentId = 0;
    private double positionX=0;
    private double positionY=0;
    private double positionZ=0;
    private double cartVelocityX =0;
    private double cartVelocityY =0;
    private double cartVelocityZ =0;
    private double motionProgress=0;
    private boolean isFront=true;
    private static final int[][][] vanillaRailMatrix = new int[][][] {
            {{0, 0, -1}, {0, 0, 1}},
            {{ -1, 0, 0}, {1, 0, 0}},
            {{ -1, -1, 0}, {1, 0, 0}},
            {{ -1, 0, 0}, {1, -1, 0}},
            {{0, 0, -1}, {0, -1, 1}},
            {{0, -1, -1}, {0, 0, 1}},
            {{0, 0, 1}, {1, 0, 0}},
            {{0, 0, 1}, { -1, 0, 0}},
            {{0, 0, -1}, { -1, 0, 0}},
            {{0, 0, -1}, {1, 0, 0}}};

    public EntityBogie(World world) {
        super(world);
    }

    public EntityBogie(World world, double xPos, double yPos, double zPos, int parent, boolean front) {
        super(world);
        posX = xPos;
        posY = yPos;
        posZ = zPos;
            parentId = parent;
            isFront = front;
    }

    /**
     * <h2>Spawn Data</h2>
     * Small networking check to add the bogie to the host train/rollingstock. Or to remove the bogie from the world if the host doesn't exist.
     */
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
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeBoolean(isFront);
        buffer.writeInt(parentId);
    }

    /**
     * <h3>Core Minecart Overrides</h3>
     * technically this is a normal minecart, which is why it works on normal tracks.
     * @see EntityMinecart
     *
     * @see RailUtility
     * onUpdate is intentionally empty because we don't want the super running it's own onUpdate method. we define when to run our movement code in the train/rollingstock
     * @see EntityTrainCore#onUpdate()
     */
    @Override
    public int getMinecartType() {
        return 10001;
    }
    @Override
    public boolean isPoweredCart() {
        return true;
    }
    @Override
    public boolean canBeRidden() {
        return false;
    }
    @Override
    public boolean canBePushed() {
        return false;
    }
    @Override
    public boolean canRiderInteract() {
        return true;
    }
    @Override
    public float getMaxCartSpeedOnRail() {
        return 5F;
    }
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
                this.posX += (positionX - this.posX) / motionProgress;
                this.posY += (((positionY - this.posY) + yOffset) / motionProgress);
                this.posZ += (positionZ - this.posZ) / motionProgress;
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
                //update on ZnD rails
            } else if(worldObj.getTileEntity(floorX, floorY, floorZ) instanceof ITrackBase) {
                super.onUpdate();
                //update on falling with no rails
            }
            //idk wtf this does.
            //MinecraftForge.EVENT_BUS.post(new MinecartUpdateEvent(this, floorX, floorY, floorZ));
        }
    }


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

        int railMetadata = ((BlockRailBase)block).getBasicRailMetadata(worldObj, null, floorX, floorY, floorZ);

        //add the uphill/downhill velocity
        switch (railMetadata){
            case 2:{this.motionX -= 0.0078125D; this.posY = (double)(floorY + 1); break;}
            case 3:{this.motionX += 0.0078125D; this.posY = (double)(floorY + 1); break;}
            case 4:{this.motionZ += 0.0078125D; this.posY = (double)(floorY + 1); break;}
            case 5:{this.motionZ -= 0.0078125D; this.posY = (double)(floorY + 1); break;}
        }

        //figure out the current rail's direction
        double railPathX = (vanillaRailMatrix[railMetadata][1][0] - vanillaRailMatrix[railMetadata][0][0]);
        double railPathZ = (vanillaRailMatrix[railMetadata][1][2] - vanillaRailMatrix[railMetadata][0][2]);
        double railPathSqrt = Math.sqrt(railPathX * railPathX + railPathZ * railPathZ);

        //if it ends up being reverse of what it should be, inverse the motion.
        if (currentMotionX * railPathX + currentMotionZ * railPathZ < 0.0D) {
            railPathX = -railPathX;
            railPathZ = -railPathZ;
        }

        //define the motion based on the rail path for current movement, and the next, so they are in sync.
        double motionSqrt = Math.sqrt(currentMotionX * currentMotionX + currentMotionZ * currentMotionZ);
        if (motionSqrt > 2.0D) {
            motionSqrt = 2.0D;
        }
        currentMotionX = motionSqrt * railPathX / railPathSqrt;
        currentMotionZ = motionSqrt * railPathZ / railPathSqrt;

        //define the motion based on the rail path for current movement, and the next, so they are in sync.
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
        this.posX = (d8 + railPathX * d7) + currentMotionX;
        this.posZ = (d9 + railPathZ * d7) + currentMotionZ;
        this.positionY =motionY;

        if (vanillaRailMatrix[railMetadata][0][1] != 0 && MathHelper.floor_double(this.posX) - floorX == vanillaRailMatrix[railMetadata][0][0] && MathHelper.floor_double(this.posZ) - floorZ == vanillaRailMatrix[railMetadata][0][2]) {
            this.posY+=vanillaRailMatrix[railMetadata][0][1];
        }
        else if (vanillaRailMatrix[railMetadata][1][1] != 0 && MathHelper.floor_double(this.posX) - floorX == vanillaRailMatrix[railMetadata][1][0] && MathHelper.floor_double(this.posZ) - floorZ == vanillaRailMatrix[railMetadata][1][2]) {
            this.posY+=vanillaRailMatrix[railMetadata][1][1];
        }

        if(shouldDoRailFunctions()) {
            ((BlockRailBase)block).onMinecartPass(worldObj, this, floorX, floorY, floorZ);
        }
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
     * <h3>Railcraft support</h3>
     * @see IRoutableCart
     *
     * doesCartMatchFilter checks if the linked train/rollingstock matches the filter for railcraft items, if the filter is null or the parent is null, return false.
     * Otherwise, get the parent, then get the item from the parent.
     *
     * destination is handled in locomotive and rollingstock, here we just return null as default.
     * filter however we will have to handle here because it is very generic.
     *
     * the owner for railcraft we don't handle at all, because we have our own system for that.
     */
    @Override
    public boolean doesCartMatchFilter(ItemStack stack, EntityMinecart cart) {
        if (stack == null || worldObj.getEntityByID(parentId) == null) {
            return false;
        } else {
            Item cartItem = ((EntityTrainCore)worldObj.getEntityByID(parentId)).getItem();
            return cartItem != null && stack.getItem() == cartItem;
        }
    }
    @Override
    public GameProfile getOwner(){return null;}
    @Override
    public String getDestination() {
        return ((EntityTrainCore) worldObj.getEntityByID(parentId)).destination;
    }
    @Override
    public boolean setDestination(ItemStack ticket) {
        return true;
    }


    /**
     * <h2>Client Movement code</h2>
     * this is mostly used to sync and smooth movement between client and server.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int turnProgress) {
        positionX = x;
        positionY = y;
        positionZ = z;
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

    @Override
    public void addVelocity(double velocityX, double velocityY, double velocityZ){
            setVelocity(motionX + velocityX, motionY + velocityY, motionZ + velocityZ);
            isAirBorne = true;
    }



}
