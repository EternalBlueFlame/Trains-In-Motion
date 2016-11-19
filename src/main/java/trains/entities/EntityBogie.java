package trains.entities;


import java.util.List;

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
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;
import trains.utility.Utility;

public class EntityBogie extends EntityMinecart implements IMinecart, IRoutableCart, IEntityAdditionalSpawnData {

    private int parentId = 0;
    protected double cartVelocityX =0;
    protected double cartVelocityY =0;
    protected double cartVelocityZ =0;

    public EntityBogie(World world) {
        super(world);
    }

    public EntityBogie(World world, double xPos, double yPos, double zPos, int parent) {
        super(world,xPos, yPos, zPos);
            parentId = parent;
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        parentId = additionalData.readInt();
        if (parentId != 0) {
            ((EntityTrainCore) worldObj.getEntityByID(parentId)).addbogies(this);
        } else {
            worldObj.getEntityByID(getEntityId());
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
     * @see Utility
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
    public boolean canRiderInteract()
    {
        return true;
    }
    @Override
    public float getMaxCartSpeedOnRail() {
        return 5F;
    }
    @Override
    public void onUpdate() {}


    /**
     * <h3> movement management</h3>
     * this is modified movement from the super class, should be more efficient, and reliable, but generally does the same thing.
     * @see EntityMinecart#onUpdate()
     * Some features are replaced using our own for compatibility with ZoraNoDensha
     * @see Utility
     *
     * TODO: Portal stuff needs to be moved into its own function in util so we can use it in train/rollingstock classes too.
     * TODO: all checks on rails and their features need to be moved to our utility class so we can interface them with ZoraNoDensha
     * TODO: worldObj.getEntitiesWithinAABBExcludingEntity(this, box) needs to be reworked using our own functionality that does proper class casting.
     * @see Utility#isRailBlockAt(World, int, int, int)
     */
    public void minecartMove(){
        //Why do we even have this?
        if (getRollingAmplitude() > 0) {
            setRollingAmplitude(getRollingAmplitude() - 1);
        }
        //if the cart has fallen out of the map, destroy it.
        if (posY < -64.0D){
            worldObj.removeEntity(this);
        }
        //this manages the modified Y position.
        int i;
        //manage transportation through portals
        if (!worldObj.isRemote && worldObj instanceof WorldServer) {
            i = getMaxInPortalTime();

            if (inPortal) {
                if (((WorldServer)worldObj).func_73046_m().getAllowNether()) {
                    if (ridingEntity == null && portalCounter++ >= i) {
                        portalCounter = i;
                        timeUntilPortal = getPortalCooldown();

                        if (worldObj.provider.dimensionId == -1) {
                            travelToDimension(0);
                        } else {
                            travelToDimension(-1);
                        }
                    }

                    inPortal = false;
                }
            } else {
                if (portalCounter - 4 < 0) {
                    portalCounter = 0;
                } else{
                    portalCounter -= 4;
                }
            }

            if (timeUntilPortal > 0) {
                --timeUntilPortal;
            }

        }
        if (worldObj.isRemote) {
            setPosition(posX, posY, posZ);

        } else {
            prevPosX = posX;
            prevPosY = posY;
            prevPosZ = posZ;
            motionY -= 0.03999999910593033D;
            int l = MathHelper.floor_double(posX);
            i = MathHelper.floor_double(posY);
            int i1 = MathHelper.floor_double(posZ);

            //deal with special rails
            if (Utility.isRailBlockAt(worldObj, l, i - 1, i1)) {
                --i;
            }
            Block block = worldObj.getBlock(l, i, i1);
            if (canUseRail() && Utility.isRailBlockAt(block)) {
                float railMaxSpeed = ((BlockRailBase)block).getRailMaxSpeed(worldObj, this, l, i, i1);
                double maxSpeed = Math.min(railMaxSpeed, getCurrentCartSpeedCapOnRail());
                func_145821_a(l, i, i1, maxSpeed, getSlopeAdjustment(), block, ((BlockRailBase)block).getBasicRailMetadata(worldObj, this, l, i, i1));

                if (block == Blocks.activator_rail) {
                    onActivatorRailPass(l, i, i1, (worldObj.getBlockMetadata(l, i, i1) & 8) != 0);
                }
            } else {
                func_94088_b(onGround ? 0.4D : getMaxSpeedAirLateral());
            }
            //deal with the bounding box and collisions
            func_145775_I();
            AxisAlignedBB box;
            if (getCollisionHandler() != null) {
                box = getCollisionHandler().getMinecartCollisionBox(this);
            } else {
                box = boundingBox.expand(0.2D, 0.0D, 0.2D);
            }

            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, box);

            if (list != null && !list.isEmpty()) {
                for (Entity entity: (List<Entity>)list) {
                    if (entity != riddenByEntity && entity.canBePushed() && entity instanceof EntityMinecart) {
                        entity.applyEntityCollision(this);
                    }
                }
            }

            if (riddenByEntity != null && riddenByEntity.isDead) {
                if (riddenByEntity.ridingEntity == this) {
                    riddenByEntity.ridingEntity = null;
                }

                riddenByEntity = null;
            }
            //finally post a minecart update
            MinecraftForge.EVENT_BUS.post(new MinecartUpdateEvent(this, l, i, i1));
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
            ItemStack cartItem = ((EntityTrainCore)worldObj.getEntityByID(parentId)).getItem();
            return cartItem != null && stack.isItemEqual(cartItem);
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
    @SideOnly(Side.CLIENT)
    public void setVelocity(double x, double y, double z) {
        cartVelocityX = motionX = x;
        cartVelocityY = motionY = y;
        cartVelocityZ = motionZ = z;
    }

    /**
     * this is a fix for the fact there may or may not be a rider entity, this is to serve until we fully replace the functionality of this.
     */
    @Deprecated
    @Override
    protected void func_145821_a(int p_145821_1_, int p_145821_2_, int p_145821_3_, double p_145821_4_, double p_145821_6_, Block p_145821_8_, int p_145821_9_) {
        Entity entity = riddenByEntity;
        riddenByEntity = null;
        super.func_145821_a(p_145821_1_, p_145821_2_, p_145821_3_, p_145821_4_, p_145821_6_, p_145821_8_, p_145821_9_);
        riddenByEntity = entity;
    }



}
