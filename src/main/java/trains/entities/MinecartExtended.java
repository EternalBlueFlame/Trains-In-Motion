package trains.entities;


import java.util.List;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.railcraft.api.carts.IMinecart;
import mods.railcraft.api.carts.IRoutableCart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;
import trains.TrainsInMotion;
import trains.entities.render.RenderCore;

public class MinecartExtended extends EntityMinecart implements IMinecart, IRoutableCart {

    //define these ahead of time to improve performance.
    private static final double almostNotMoving= 0.0138888888D;
    private static final double rotationPi = 180 / Math.PI;


    
    //due to limitations of rotation/position for the minecart, we have to implement them ourselves to a certain degree.
    public double cartX =0;
    public double cartY =0;
    public double cartZ =0;
    protected double cartVelocityX =0;
    protected double cartVelocityY =0;
    protected double cartVelocityZ =0;
    private int ticks = 0; //tick count.
    public String destination = "";  //railcraft destination


    /**
     * we have to have the constructor for the initial spawn that puts the train in the world, minecraft does this, we don't have to mess with it other than just having it.
     *
     * @param world the world to spawn it in.
     */
    public MinecartExtended(World world) {
        super(world);
    }

    /**
     * this class defines the core of all trains and rollingstock, most of the large and messy code is here to make sure it's clean elsewhere.
     *
     * for things generic to rolling stock:
     *
     * for things generic to  trains:
     * @see EntityTrainCore
     *
     * for things generic to both these classes:
     * @see MinecartExtended
     *
     * default constructor for setting up variables after this is created
     * @param world the world to spawn the entity in, used in super.
     * @param xPos the x position to spawn entity at, used in super.
     * @param yPos the y position to spawn entity at, used in super.
     * @param zPos the z position to spawn entity at, used in  super.
     */
    public MinecartExtended(World world, double xPos, double yPos, double zPos) {
        super(world,xPos, yPos, zPos);
    }

    /**
    * Core Minecart Overrides
     * @see EntityMinecart
    *
     * technically this is a normal minecart, which is why it works on normal tracks.
     * aside from that we also use getMinecartType to define the texture for the entity, an odd way to do it, but its simple and works.
     * @see RenderCore
     *
     *
     * The UUID methods are for getting and setting the, non-railcraft, owner of the entity,
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

    /**
     * this runs every tick
     * we don't actually use the super call here
     *
     * every tick it will count up the ticks variable, and if it is above 1, the position and lamp get updates.
     * this allows for the entity to spawn without creating as much lag.
     *
     */
    @Override
    public void onUpdate() {
        //handle the core movement for minecarts, skip the first couple ticks so it's less laggy on spawn (tick 0), and in general by skipping 10% of the ticks.
       if (ticks > 1) {
            minecartMove();
        }
        //add to ticks _after_ we initially define important things
        ticks++;

        //if the tick count is higher than the values used, reset it so it can count up again.
        if (ticks>10){
        ticks = 1;
        }

    }
    /**
     * this is modified movement from the super class, should be more efficient, and reliable, but generally does the same thing
     * @see EntityMinecart#onUpdate()
     */
    public void minecartMove(){
        if (getRollingAmplitude() > 0) {
            setRollingAmplitude(getRollingAmplitude() - 1);
        }

        if (getDamage() > 0.0F) {
            setDamage(getDamage() - 1.0F);
        }
        //if the cart has fallen out of the may, destroy it.
        if (posY < -64.0D){
            kill();
        }
        //this is just randomly recycled.
        int i;
        //manage transportation through portals
        if (!worldObj.isRemote && worldObj instanceof WorldServer) {
            worldObj.theProfiler.startSection("portal");
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

            worldObj.theProfiler.endSection();
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
            if (BlockRailBase.func_150049_b_(worldObj, l, i - 1, i1)) {
                --i;
            }
            Block block = worldObj.getBlock(l, i, i1);
            if (canUseRail() && BlockRailBase.func_150051_a(block)) {
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
                for (int k = 0; k < list.size(); ++k) {
                    Entity entity = (Entity)list.get(k);

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
     * manage the position and rotation values, this also sets the motion, which is the only value the super class actually gets from these calls
     *
     * the following paramaters are actually supposed to go to the super class, but instead we pull them in and put them in this class,
     * because we don't have the super processing this stuff anymore due to inefficient code, and other reasons.
     * @param x
     * @param y
     * @param z
     * @param yaw
     * @param pitch
     * @param turnProgress
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int turnProgress) {
        cartX = x;
        cartY = y;
        cartZ = z;
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
     * this is used to save/load/update information between server/client(s)
     * @param tag the NBT tag provided by the game itself
     *
     * the super class handles most of the variables, Most of the rest is handled either in classes that extend this
     * @see EntityTrainCore#readFromNBT(NBTTagCompound)
     * and the super class
     * @see EntityMinecart#readFromNBT(NBTTagCompound)
     */
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        //colors = tag.getIntArray("extended.colors");
        ticks = tag.getInteger("extended.ticks");
        destination = tag.getString("train.destination");
    }
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        //tag.setIntArray("extended.colors", colors);
        tag.setInteger("extended.ticks", ticks);
        tag.setString("train.destination", destination);

    }

    /**
     * default settings for railcraft support
     * @see IRoutableCart
     *
     * destination is handled in locomotive, here we just return null as default.
     * filter however we will have to handle here because it is very generic.
     *
     * the owner for railcraft we don't handle at all, because we have our own system for that.
     */
    @Override
    public boolean doesCartMatchFilter(ItemStack stack, EntityMinecart cart) {
        //get the type from the given minecart, and if it matches the itemstack return true.
        if (stack == null || cart == null) { return false; }
        ItemStack cartItem = cart.getCartItem();
        return cartItem != null && stack.isItemEqual(cartItem);
    }
    @Override
    public GameProfile getOwner(){return null;}





    @Override
    protected void func_145821_a(int p_145821_1_, int p_145821_2_, int p_145821_3_, double p_145821_4_, double p_145821_6_, Block p_145821_8_, int p_145821_9_) {

        Entity entity = this.riddenByEntity;
        this.riddenByEntity = null;
        super.func_145821_a(p_145821_1_, p_145821_2_, p_145821_3_, p_145821_4_, p_145821_6_, p_145821_8_, p_145821_9_);
        this.riddenByEntity = entity;
    }



    /**
     * this function, and the following one define railcraft support for trains by adding in destination and if you can set one.
     * @see mods.railcraft.api.carts.IRoutableCart
     */
    @Override
    public String getDestination() {
        return destination;
    }
    //Only locomotives can receive a destination from a track.
    @Override
    public boolean setDestination(ItemStack ticket) {
        return true;
    }

}
