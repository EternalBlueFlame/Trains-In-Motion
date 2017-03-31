package ebf.tim.entities;


import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import mods.railcraft.api.carts.IMinecart;
import mods.railcraft.api.carts.IRoutableCart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;
import ebf.tim.utility.RailUtility;

import java.util.List;

/**
 * <h1>Bogie Core</h1>
 * this controls the behavior of the bogies in trains and rollingstock.
 * @author Eternal Blue Flame
 */
public class EntityBogie extends EntityMinecart implements IMinecart, IRoutableCart, IEntityAdditionalSpawnData {

    /**
     * <h2>variables</h2>
     * parentId is used to keep a reference to the parent train/rollingstock.
     * the velocities are to replace the client only velocities in forge that have private access.
     */
    private int parentId = 0;
    protected double cartVelocityX =0;
    protected double cartVelocityY =0;
    protected double cartVelocityZ =0;
    double positionX=0;
    double positionY=0;
    double positionZ=0;
    double motionProgress=0;

    public EntityBogie(World world) {
        super(world);
    }

    public EntityBogie(World world, double xPos, double yPos, double zPos, int parent) {
        super(world,xPos, yPos, zPos);
            parentId = parent;
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
                parent.addbogies(this);
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
     *
     * TODO: Portal stuff needs to be moved into its own function in util so we can use it in train/rollingstock classes too.
     * TODO: all checks on rails and their features need to be moved to our utility class so we can interface them with ZoraNoDensha
     * TODO: worldObj.getEntitiesWithinAABBExcludingEntity(this, box) needs to be reworked using our own functionality that does proper class casting.
     * @see RailUtility#isRailBlockAt(World, int, int, int)
     */
    public void minecartMove(float yaw, float pitch)   {
        this.setRotation(yaw, pitch);
        if (this.getRollingAmplitude() > 0) {
            this.setRollingAmplitude(this.getRollingAmplitude() - 1);
        }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
        }

        if (this.posY < -64.0D) {
            this.kill();
        }

        int floorY_Portal;

        if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
            MinecraftServer minecraftserver = ((WorldServer)this.worldObj).func_73046_m();
            floorY_Portal = this.getMaxInPortalTime();

            if (this.inPortal) {
                if (minecraftserver.getAllowNether()) {
                    if (this.ridingEntity == null && this.portalCounter++ >= floorY_Portal) {
                        this.portalCounter = floorY_Portal;
                        this.timeUntilPortal = this.getPortalCooldown();
                        byte b0;

                        if (this.worldObj.provider.dimensionId == -1) {
                            b0 = 0;
                        } else {
                            b0 = -1;
                        }

                        this.travelToDimension(b0);
                    }

                    this.inPortal = false;
                }
            } else {
                if (this.portalCounter > 0) {
                    this.portalCounter -= 4;
                }

                if (this.portalCounter < 0) {
                    this.portalCounter = 0;
                }
            }

            if (this.timeUntilPortal > 0) {
                --this.timeUntilPortal;
            }
        }
        //client only
        if (this.worldObj.isRemote) {
            if (motionProgress > 0) {
                double d6 = this.posX + (positionX - this.posX) / motionProgress;
                double d7 = this.posY + (positionY - this.posY) / motionProgress;
                double d1 = this.posZ + (positionZ - this.posZ) / motionProgress;
                --motionProgress;
                this.setPosition(d6, d7, d1);
            } else {
                this.setPosition(this.posX, this.posY, this.posZ);
            }
        }
        //server only
        else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= 0.03999999910593033D;
            int floorX = MathHelper.floor_double(this.posX);
            floorY_Portal = MathHelper.floor_double(this.posY);
            int floorZ = MathHelper.floor_double(this.posZ);

            if (BlockRailBase.func_150049_b_(this.worldObj, floorX, floorY_Portal - 1, floorZ)) {
                --floorY_Portal;
            }

            Block block = this.worldObj.getBlock(floorX, floorY_Portal, floorZ);

            if (canUseRail() && RailUtility.isRailBlockAt(worldObj, floorX, floorY_Portal, floorZ)) {
                moveBogie(floorX, floorY_Portal, floorZ, getSlopeAdjustment(), block, ((BlockRailBase)block).getBasicRailMetadata(worldObj, this, floorX, floorY_Portal, floorZ));

                if (block == Blocks.activator_rail) {
                    this.onActivatorRailPass(floorX, floorY_Portal, floorZ, (worldObj.getBlockMetadata(floorX, floorY_Portal, floorZ) & 8) != 0);
                }
            } else {
                this.func_94088_b(onGround ? 0.4D : getMaxSpeedAirLateral());
            }

            this.func_145775_I();

            MinecraftForge.EVENT_BUS.post(new MinecartUpdateEvent(this, floorX, floorY_Portal, floorZ));
        }
    }

    private static final int[][][] matrix = new int[][][] {{{0, 0, -1}, {0, 0, 1}}, {{ -1, 0, 0}, {1, 0, 0}}, {{ -1, -1, 0}, {1, 0, 0}}, {{ -1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, { -1, 0, 0}}, {{0, 0, -1}, { -1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};

    private void moveBogie(int floorX, int floorY, int floorZ, double slopeAdjustment, Block block, int railMetadata) {
        this.fallDistance = 0.0F;
        Vec3 vec3 = this.func_70489_a(this.posX, this.posY, this.posZ);
        this.posY = floorY;

        switch (railMetadata){
            case 2:{this.motionX -= slopeAdjustment; this.posY = (double)(floorY + 1); break;}
            case 3:{this.motionX += slopeAdjustment; this.posY = (double)(floorY + 1);break;}
            case 4:{this.motionZ += slopeAdjustment; this.posY = (double)(floorY + 1);break;}
            case 5:{this.motionZ -= slopeAdjustment; this.posY = (double)(floorY + 1);break;}
        }

        int[][] aint = matrix[railMetadata];
        double d2 = (double)(aint[1][0] - aint[0][0]);
        double d3 = (double)(aint[1][2] - aint[0][2]);
        double d4 = Math.sqrt(d2 * d2 + d3 * d3);
        double d5 = this.motionX * d2 + this.motionZ * d3;

        if (d5 < 0.0D) {
            d2 = -d2;
            d3 = -d3;
        }

        double d6 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

        if (d6 > 2.0D) {
            d6 = 2.0D;
        }

        this.motionX = d6 * d2 / d4;
        this.motionZ = d6 * d3 / d4;
        double d7;
        double d8;
        double d9;
        double d10;

        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
            d7 = (double)((EntityLivingBase)this.riddenByEntity).moveForward;

            if (d7 > 0.0D) {
                d8 = -Math.sin((double)(this.riddenByEntity.rotationYaw * RailUtility.radianF));
                d9 = Math.cos((double)(this.riddenByEntity.rotationYaw * RailUtility.radianF));
                d10 = this.motionX * this.motionX + this.motionZ * this.motionZ;

                if (d10 < 0.01D) {
                    this.motionX += d8 * 0.1D;
                    this.motionZ += d9 * 0.1D;
                }
            }
        }

        d8 = (double)floorX + 0.5D + (double)aint[0][0] * 0.5D;
        d9 = (double)floorZ + 0.5D + (double)aint[0][2] * 0.5D;
        d10 = (double)floorX + 0.5D + (double)aint[1][0] * 0.5D;
        double d11 = (double)floorZ + 0.5D + (double)aint[1][2] * 0.5D;
        d2 = d10 - d8;
        d3 = d11 - d9;
        double d12;
        double d13;

        if (d2 == 0.0D) {
            this.posX = (double)floorX + 0.5D;
            d7 = this.posZ - (double)floorZ;
        }
        else if (d3 == 0.0D) {
            this.posZ = (double)floorZ + 0.5D;
            d7 = this.posX - (double)floorX;
        }
        else {
            d12 = this.posX - d8;
            d13 = this.posZ - d9;
            d7 = (d12 * d2 + d13 * d3) * 2.0D;
        }

        this.posX = d8 + d2 * d7;
        this.posZ = d9 + d3 * d7;
        this.setPosition(this.posX, this.posY + (double)this.yOffset, this.posZ);

        this.moveEntity(this.motionX, 0.0D, this.motionZ);

        if (aint[0][1] != 0 && MathHelper.floor_double(this.posX) - floorX == aint[0][0] && MathHelper.floor_double(this.posZ) - floorZ == aint[0][2]) {
            this.setPosition(this.posX, this.posY + (double)aint[0][1], this.posZ);
        }
        else if (aint[1][1] != 0 && MathHelper.floor_double(this.posX) - floorX == aint[1][0] && MathHelper.floor_double(this.posZ) - floorZ == aint[1][2]) {
            this.setPosition(this.posX, this.posY + (double)aint[1][1], this.posZ);
        }

        Vec3 vec31 = this.func_70489_a(this.posX, this.posY, this.posZ);

        if (vec31 != null && vec3 != null) {
            double d14 = (vec3.yCoord - vec31.yCoord) * 0.05D;
            d6 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

            if (d6 > 0.0D) {
                this.motionX = this.motionX / d6 * (d6 + d14);
                this.motionZ = this.motionZ / d6 * (d6 + d14);
            }

            this.setPosition(this.posX, vec31.yCoord, this.posZ);
        }

        int j1 = MathHelper.floor_double(this.posX);
        int i1 = MathHelper.floor_double(this.posZ);

        if (j1 != floorX || i1 != floorZ) {
            d6 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.motionX = d6 * (double)(j1 - floorX);
            this.motionZ = d6 * (double)(i1 - floorZ);
        }

        if(shouldDoRailFunctions()) {
            ((BlockRailBase)block).onMinecartPass(worldObj, this, floorX, floorY, floorZ);
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
        //handle movement for trains, this will likely need to be different for rollingstock.
            setVelocity(motionX + velocityX, motionY + velocityY, motionZ + velocityZ);
            isAirBorne = true;
    }



}
