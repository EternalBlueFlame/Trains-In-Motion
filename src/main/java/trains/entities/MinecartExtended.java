package trains.entities;


import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.gameevent.TickEvent;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;
import net.minecraftforge.fluids.FluidTank;
import trains.TrainsInMotion;
import trains.entities.render.RenderCore;
import trains.utility.LampHandler;

import static java.lang.Math.*;

public class MinecartExtended extends EntityMinecart implements IMinecart, IRoutableCart, IInventory, IEntityAdditionalSpawnData {

    //define these ahead of time to improve performance.
    private static final double almostNotMoving= 0.0138888888D;
    private static final double rotationPi = 180 / Math.PI;

    //Main Values
    public int[] colors; //allows certain parts of certain trains to be recolored
    public String name;
    public boolean isLocked = false; //mostly used to lock other players from using/accessing parts of the cart/train
    public boolean brake = false; //bool for the train/rollingstock's break.
    public LampHandler lamp = new LampHandler(); //manages the lamp, or lack there of.
    public int GUIID = 0; //id for the GUI
    public UUID owner = null;  //universal, get train owner
    private int minecartNumber = 0; //used to identify the minecart number so it doesn't interfere with other mods or the base game minecarts,
    public boolean canBeRidden;
    private int ticks = 0; //tick count.

    
    //due to limitations of rotation/position for the minecart, we have to implement them ourselves to a certain degree.
    public boolean isReverse =false;
    public float spawnDirection =0;
    public int cartTurnProgress =0;
    public double cartX =0;
    public double cartY =0;
    public double cartZ =0;
    public float cartPitch =0;
    protected double cartVelocityX =0;
    protected double cartVelocityY =0;
    protected double cartVelocityZ =0;


    //inventory
    public ItemStack[] inventory = new ItemStack[]{};//Inventory, every train will have this to some extent or another, //the first two slots are for crafting
    public FluidTank[] tank = new FluidTank[]{};//depending on the train this is either used for diesel, steam, or redstone flux
    public int rows =0; //defines the inventory width
    public int columns =0;//defines inventory height
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
     * @see EntityRollingStockCore
     *
     * for things generic to  trains:
     * @see EntityTrainCore
     *
     * for things generic to both these classes:
     * @see MinecartExtended
     *
     * default constructor for setting up variables after this is created
     * @param owner the owner profile, used to define owner of the entity,
     * @param world the world to spawn the entity in, used in super.
     * @param xPos the x position to spawn entity at, used in super.
     * @param yPos the y position to spawn entity at, used in super.
     * @param zPos the z position to spawn entity at, used in  super.
     * @param type what kind of rolling stock or train it is.
     * @param tank used to define the fluid tank(s) if there are any
     * @param inventoryrows defines the rows of inventory, inventory size is defined by rows * columns.
     * @param inventoryColumns defines the columns of the inventory.
     * @param craftingSlots defines the number of crafting slots, 1 is for fuel, 2 is for boiler.
     * @param GUIid the ID used to define what GUI the entity uses (0 for no GUI).
     * @param minecartNumber used to define the unique ID of the minecart, this prevents issues with base game and modded minecarts, This also defines the texture
     *                       @see RenderCore
     * @param canBeRidden used to toggle if the player can ride the entity.
     */
    public MinecartExtended(UUID owner, World world, double xPos, double yPos, double zPos, int type, FluidTank[] tank, int inventoryrows,
                            int inventoryColumns, int craftingSlots, int GUIid, int minecartNumber, boolean canBeRidden) {
        super(world,xPos, yPos, zPos);
        this.owner = owner;
        this.minecartNumber = minecartNumber;
        this.canBeRidden = canBeRidden;
        this.tank = tank;
        int slots = craftingSlots + inventoryColumns * inventoryrows;
        inventory = new ItemStack[slots];
        GUIID = GUIid;
        rows = inventoryrows;
        columns = inventoryColumns;

        if(worldObj.isRemote){
            /**
             * add lamp to main class handler when created, so the main thread can deal with updating the lighting, or not.
             * @see TrainsInMotion#onTick(TickEvent.ClientTickEvent)
             */
            TrainsInMotion.carts.add(this);
        }
    }

    /**
     * this is basically NBT for entity spawn, to keep data between client and server in sync because some data is not automatically shared.
     */
    @Override
    public void readSpawnData(ByteBuf additionalData) {
        spawnDirection = additionalData.readFloat();
        isReverse = additionalData.readBoolean();
        owner = new UUID(additionalData.readLong(), additionalData.readLong());
    }
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeFloat(spawnDirection);
        buffer.writeBoolean(isReverse);
        buffer.writeLong(owner.getMostSignificantBits());
        buffer.writeLong(owner.getLeastSignificantBits());
    }

    /**
     * Inventory stuff, most of this is self-explanatory.
     * Occasionally some parts are re-defined so that filters may be applied
     * @see EntityRollingStockCore
     */
    @Override
    public String getInventoryName() {
        return name;
    }
    @Override
    public void openInventory() {}
    @Override
    public void closeInventory() {}
    @Override
    public void markDirty() {}

    @Override
    public ItemStack getStackInSlot(int slot) {
        //be sure the slot exists before trying to return anything,
        if (slot>=0 && slot< inventory.length) {
            return inventory[slot];
        } else {
            return null;
        }
    }
    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        //we return null no matter what, but we want to make sure the slot is properly set as well.
        if (slot>=0 && slot< inventory.length) {
            inventory[slot] = null;
        }
        return null;
    }
    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        //be sure the slot exists before trying to return anything,
        if (slot>=0 && slot< inventory.length) {
            //if subtraction makes slot empty/null then set it to null and return null, otherwise return the stack.
            if (inventory[slot].stackSize <= amount ^ inventory[slot].stackSize <= 0) {
                inventory[slot] = null;
                return null;
            } else {
                return inventory[slot].splitStack(amount);
            }
        } else {
            return null;
        }
    }
    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack) {
        //be sure item stack isn't null, then add the itemstack, and be sure the slot doesn't go over the limit.
        if (itemstack != null && slot >=0 && slot<inventory.length) {
            if (itemstack.stackSize >= getInventoryStackLimit()) {
                itemstack.stackSize = getInventoryStackLimit();
            }
            inventory[slot] = itemstack;
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    //return if the item can be placed in the slot, for this slot it's just a check if the slot exists, but other things may have slots for specific items, this filters that.
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item){
        return (slot>=0 && slot< inventory.length);
    }

    //return the number of inventory slots
    @Override
    public int getSizeInventory(){
        if(inventory != null){
            return inventory.length;
        } else{
            return 0;
        }
    }

    //return if the train can be used by the player, if it's locked, only the owner can use it.
    @Override
    public boolean isUseableByPlayer(EntityPlayer player){
        if (isLocked){
            if(owner.equals(player.getUniqueID())){
                return true;
            } else {
                return false;
            }
        } else{
            return true;
        }
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
        return minecartNumber;
    }
    @Override
    public boolean isPoweredCart() {
        return true;
    }
    @Override
    public boolean canBeRidden() {
        return canBeRidden;
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

    public void setOwner(UUID player){owner = player;}
    public UUID getOwnerUUID(){return owner;}
    public void setDirection(float direction){
        spawnDirection = direction;}

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

        //manage position, pitch, and rotation
        //NOTE: Pitch must not be the default, for no apparent reason it gets overwritten and it breaks the render.
        rotationYaw = (float) (atan2((lastTickPosX - cartX), (lastTickPosZ - cartZ)) * rotationPi)+90F;
        if ((motionX > almostNotMoving || motionX < -almostNotMoving) ^ (motionZ > almostNotMoving || motionZ < -almostNotMoving)) {
            cartPitch = MathHelper.floor_double(Math.toDegrees(acos(lastTickPosY - cartY)) + 90D);
        }
        if (worldObj.isRemote) {
            if (cartTurnProgress > 0) {
                setPosition(
                        posX + (cartX - posX) / cartTurnProgress,
                        posY + (cartY - posY) / cartTurnProgress,
                        posZ + (cartZ - posZ) / cartTurnProgress
                );
                --cartTurnProgress;

            } else {
                setPosition(posX, posY, posZ);
            }

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
        rotationYaw = yaw;
        rotationPitch = pitch;
        cartTurnProgress = turnProgress + 2;
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
     * @see EntityRollingStockCore#readFromNBT(NBTTagCompound)
     * and the super class
     * @see EntityMinecart#readFromNBT(NBTTagCompound)
     */
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        //colors = tag.getIntArray("extended.colors");
        isLocked = tag.getBoolean("extended.isLocked");
        brake = tag.getBoolean("extended.brake");
        lamp.isOn = tag.getBoolean("extended.lamp");
        isReverse = tag.getBoolean("extended.isreverse");
        spawnDirection = tag.getFloat("extended.directon");
        owner = new UUID(tag.getLong("extended.ownerM"),tag.getLong("extended.ownerL"));
        ticks = tag.getInteger("extended.ticks");
        rows = tag.getInteger("extended.rows");
        columns = tag.getInteger("extended.columns");
        //read through the itemstacks
        NBTTagList taglist = tag.getTagList("Items", 10);
        for (int i = 0; i < taglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = taglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < inventory.length) {
                inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        for (int t=0; t<tank.length; t++){
            tank[t].readFromNBT(tag);
        }
        //items with static-esk values that shouldn't need NBT,
        //name, maxSpeed, GUIID, minecartNumber, trainType, acceleration, filters, canBeRidden, isLoco.
    }
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        //tag.setIntArray("extended.colors", colors);
        tag.setBoolean("extended.isLocked", isLocked);
        tag.setBoolean("extended.brake", brake);
        tag.setBoolean("extended.lamp", lamp.isOn);
        tag.setBoolean("extended.isreverse", isReverse);
        tag.setFloat("extended.direction", spawnDirection);
        tag.setLong("extended.ownerM", owner.getMostSignificantBits());
        tag.setLong("extended.ownerL", owner.getLeastSignificantBits());
        tag.setInteger("extended.ticks", ticks);
        tag.setInteger("extended.rows", rows);
        tag.setInteger("extended.columns", columns);
        //write the itemset to a tag list before adding it
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < inventory.length; ++i) {
            if (inventory[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        tag.setTag("Items", nbttaglist);

        for (int t=0; t<tank.length; t++){
            tank[t].writeToNBT(tag);
        }

    }

    /**
     * default settings for railcraft support
     * @see IRoutableCart
     *
     * destination is handled in locomotive, here we just return null as default.
     * filter however we will have to handle here because it is very generic.
     *
     * the owner for railcraft we don't handle at all, because we have our own system for that.
     * @see MinecartExtended#getOwnerUUID()
     */
    @Override
    public String getDestination() {
        return null;
    }
    @Override
    public boolean setDestination(ItemStack ticket) {
        return false;
    }
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


}
