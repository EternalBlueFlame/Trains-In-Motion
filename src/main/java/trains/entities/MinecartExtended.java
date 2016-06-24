package trains.entities;


import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import Movement.Accelerate;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.railcraft.api.carts.IMinecart;
import mods.railcraft.api.carts.IRoutableCart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartUpdateEvent;
import net.minecraftforge.fluids.FluidTank;
import trains.utility.FuelHandler;

public class MinecartExtended extends EntityMinecart implements IMinecart, IRoutableCart, IInventory {

    //Main Values
    public int[] colors; //allows certain parts of certain trains to be recolored
    public String name;
    public boolean isLocked = false; //mostly used to lock other players from using/accessing parts of the cart/train
    public boolean brake = false; //bool for the train/rollingstock's break.
    public boolean lamp = false; //controls the headlight/lamp
    public int[] previousLampPosition = new int[]{0,0,0}; //this is the position of the light previously, only two lights per train will ever exist at one time.
    public float maxSpeed; // the max speed
    public int GUIID = 0; //id for the GUI
    public UUID owner = null;  //universal, get train owner
    private int minecartNumber = 0; //used to identify the minecart number so it doesn't interfere with other mods or the base game minecarts,

    //Movement entities
    
    //due to limitations of rotation/position for the minecart, we have to implement them ourselves to a certain degree.
    public boolean isServerInReverse = false;
    protected int cartTurnProgress;
    protected double cartX;
    protected double cartY;
    protected double cartZ;
    protected float cartYaw;
    protected float cartPitch;
    protected double cartVelocityX;
    protected double cartVelocityY;
    protected double cartVelocityZ;


    //inventory
    public ItemStack[] inventory = new ItemStack[]{};//Inventory, every train will have this to some extent or another, //the first two slots are for crafting
    public FluidTank[] tank = new FluidTank[]{};//depending on the train this is either used for diesel, steam, or redstone flux
    public int rows =0; //defines the inventory width
    public int columns =0;//defines inventory height

    //train values
    public float[] acceleration; //the first 3 values are a point curve, representing 0-35%, 35-70% and >70% to modify how acceleration is handled at each point. //the 4th value defines how much the weight hauled effects acceleration.
    public int trainType=0;//list of train types 0 is null, 1 is steam, 2 is diesel, 3 is electric
    public boolean isRunning = false;// if the train is running/using fuel
    private int ticks = 0; //tick count.
    public int furnaceFuel = 0; //the amount of fuel in the furnace, only used for steam and nuclear trains
    public int maxFuel =0; //the max fuel in the train's furnace.

    //rollingstock values
    public Item[] storageFilter = new Item[]{};//item set to use for filters, storage only accepts items in the filter
    public Material[] storageMaterialFilter = new Material[]{};//same as item filter but works for materials
    public boolean canBeRidden;

    //railcraft variables
    public String destination = "";  //railcraft destination
    public boolean isLoco = false;  //if this can accept destination tickets, aka is a locomotive


    /*/
    Functions
    /*/

    //default constructor for registering entity
    public MinecartExtended(World world) {
        super(world);
    }
    //default constructor we actually use
    public MinecartExtended(UUID owner, World world, double xPos, double yPos, double zPos, float maxSpeed, float[] acceleration,
                            Item[] storageItemFilter /*/ empty array for no filter /*/ , Material[] storageMaterialFilter /*/ empty array for no filter /*/ ,
                            int type /*1-steam, 2-diesel, 3-electric, 4-hydrogen, 5-nuclear, 0-RollingStock*/,
                            FluidTank[] tank /*/ empty array for no tanks, - steam and nuclear take two tanks. - all other trains take one tank - all tanks besides diesel should use FluidRegistry.WATER /*/,
                            int inventoryrows, int inventoryColumns /*/ the inventory is rows(x) * columns(y)/*/,
                            int GUIid, int minecartNumber, boolean canBeRidden) {
        super(world,xPos, yPos, zPos);
        isLoco = true;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.owner = owner;
        this.minecartNumber = minecartNumber;
        this.storageMaterialFilter = storageMaterialFilter;
        this.canBeRidden = canBeRidden;
        this.tank = tank;
        trainType = type;
        int slots = inventoryColumns * inventoryrows;
        if (type == 1 || type ==5){
            slots = slots+2;
        } else if (type != 0){
            slots = slots+1;
        }
        inventory = new ItemStack[slots];
        GUIID = GUIid;
        storageFilter = storageItemFilter;
        rows = inventoryrows;
        columns = inventoryColumns;
        
        
        //Movement test
        if(!worldObj.isRemote)
        	locomote();
    }


    /*/
    *
    * Inventory stuff, most of this is self-explanatory.
    *
    /*/
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
        }
        inventory[slot] = itemstack;
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

    /*/
    *
    * Core Minecart Overrides
    *
    /*/

    //technically this is a normal minecart, so return the value for that, which isn't in the base game or another mod.
    @Override
    public int getMinecartType() {
        return minecartNumber;
    }
    //cart management stuff
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
        return true;
    }//TODO this should be false later when it can move on its own.
    @Override
    public boolean canRiderInteract()
    {
        return true;
    }

    //methods for getting/setting actual owner, not the railcraft one.
    public void setOwner(UUID player){owner = player;}
    public UUID getOwnerUUID(){return owner;}

    /*/
    *
    * Function that runs every tick.
    *
    /*/
    @Override
    public void onUpdate() {
        //handle the core movement for minecarts, skip the first couple ticks so it's less laggy on spawn (tick 0), and in general by skipping 10% of the ticks.
        if (ticks > 1) {
            minecartMove();
            locomote();
        }
        ticks++;
        //create a manager for the ticks, that way we can do something different each tick to lessen the performance hit.
        switch (ticks){
            case 5:{
                //call the class for managing the fuel
                if (isRunning) {
                    new FuelHandler(this);
                }
                break;
            }
            case 6: {
                if (isRunning) {
                    //manage speed
                }
            }
            case 10:{
                /*/ for managing the lamp, will need to implement it better later. Maybe do a client side only to change lighting of individual blocks?
                if(previousLampPosition != new int[]{MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ+2)}){
                    if(previousLampPosition != new int[]{0,0,0}) {
                        worldObj.setBlockToAir(previousLampPosition[0], previousLampPosition[1], previousLampPosition[2]);
                    }
                    previousLampPosition=new int[]{MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ+2)};

                    if (lamp && worldObj.isAirBlock(previousLampPosition[0], previousLampPosition[1], previousLampPosition[2])) {
                        worldObj.setBlock(previousLampPosition[0], previousLampPosition[1], previousLampPosition[2], new BlockLight());
                        System.out.println("created lamp child");
                    }
                }
                /*/
                break;
            }
            //other cases
            default:{
                //if the tick count is higher than the values used, reset it so it can count up again.
                if (ticks>10){
                ticks = 1;
                }
                break;
            }

        }
    }


    /*/
    *
    * Minecart movement functionality
    *
    /*/
    //regular motion
    public void locomote(){
    	Accelerate minecart = new Accelerate(this, worldObj);
    	minecart.moveMinecartOnRail(2000.0);
    }
    
    
    //revamped core minecart movement functionality
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
            MinecraftServer minecraftserver = ((WorldServer)worldObj).func_73046_m();
            i = getMaxInPortalTime();

            if (inPortal) {
                if (minecraftserver.getAllowNether()) {
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
        //manage position and rotation
        if (worldObj.isRemote) {
            if (cartTurnProgress > 0) {
                double d6 = posX + (cartX - posX) / cartTurnProgress;
                double d7 = posY + (cartY - posY) / cartTurnProgress;
                double d1 = posZ + (cartZ - posZ) / cartTurnProgress;
                rotationYaw = (float)(rotationYaw + MathHelper.wrapAngleTo180_double(cartYaw - rotationYaw) / cartTurnProgress);
                rotationPitch = (rotationPitch + (cartPitch - rotationPitch) / cartTurnProgress);
                --cartTurnProgress;
                setPosition(d6, d7, d1);
                setRotation(rotationYaw, rotationPitch);
            } else {
                setPosition(posX, posY, posZ);
                setRotation(rotationYaw, rotationPitch);
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

    //management for position and rotation via local variables since EntityMinecart's variables are private
    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int turnProgress) {
        cartX = x;
        cartY = y;
        cartZ = z;
        cartYaw = yaw;
        cartPitch = pitch;
        cartTurnProgress = turnProgress + 2;
        motionX = cartVelocityX;
        motionY = cartVelocityY;
        motionZ = cartVelocityZ;
    }
    //more of the above
    @Override
    @SideOnly(Side.CLIENT)
    public void setVelocity(double x, double y, double z) {
        cartVelocityX = motionX = x;
        cartVelocityY = motionY = y;
        cartVelocityZ = motionZ = z;
    }



    /*/
    *
    * NBT
    *
    /*/
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        //colors = tag.getIntArray("extended.colors");
        isLocked = tag.getBoolean("extended.isLocked");
        brake = tag.getBoolean("extended.brake");
        lamp = tag.getBoolean("extended.lamp");
        //previousLampPosition = tag.getIntArray("extended.previousLamp");
        owner = new UUID(tag.getLong("extended.ownerM"),tag.getLong("extended.ownerL"));
        isRunning = tag.getBoolean("extended.isRunning");
        ticks = tag.getInteger("extended.ticks");
        destination = tag.getString("extended.destination");
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
        tag.setBoolean("extended.lamp", lamp);
        //tag.setIntArray("extended.previousLamp", previousLampPosition);
        tag.setLong("extended.ownerM", owner.getMostSignificantBits());
        tag.setLong("extended.ownerL", owner.getLeastSignificantBits());
        tag.setBoolean("extended.isRunning",isRunning);
        tag.setInteger("extended.ticks", ticks);
        tag.setString("extended.destination",destination);
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



    /*/
    *
    * Railcraft support
    *
    /*/
    @Override
    public String getDestination() {
        return destination;
    }
    @Override
    public boolean doesCartMatchFilter(ItemStack stack, EntityMinecart cart) {
        if (stack == null || cart == null) { return false; }
        ItemStack cartItem = cart.getCartItem();
        return cartItem != null && stack.isItemEqual(cartItem);
    }
    //Only locomotives can receive a destination from a track.
    @Override
    public boolean setDestination(ItemStack ticket) {
        return isLoco;
    }

    //used by railcraft, this is needed but we'll obsolete this with our own methods because this is just poor.
    @Override
    public GameProfile getOwner(){return null;}

}
