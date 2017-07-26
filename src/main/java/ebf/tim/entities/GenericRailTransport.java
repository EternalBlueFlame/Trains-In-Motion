package ebf.tim.entities;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.TrainsInMotion;
import ebf.tim.items.ItemKey;
import ebf.tim.models.Bogie;
import ebf.tim.models.ParticleFX;
import ebf.tim.models.TransportRenderData;
import ebf.tim.models.tmt.ModelBase;
import ebf.tim.models.tmt.Vec3d;
import ebf.tim.networking.PacketRemove;
import ebf.tim.registry.NBTKeys;
import ebf.tim.utility.*;
import io.netty.buffer.ByteBuf;
import mods.railcraft.api.carts.IFluidCart;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ebf.tim.utility.RailUtility.rotatePoint;

/**
 * <h1>Generic Rail Transport</h1>
 * this is the base for all trains and rollingstock.
 * @author Eternal Blue Flame
 */
public class GenericRailTransport extends EntityMinecart implements IEntityAdditionalSpawnData, IEntityMultiPart, IInventory, IFluidHandler, IFluidCart {

    /*
     * <h2>variables</h2>
     */
    /**defines the lamp, and it's management*/
    public LampHandler lamp = new LampHandler();
    /**defines the colors, the outer array is for each different color, and the inner int[] is for the RGB color*/
    public int[][] colors = new int[][]{{0,0,0},{0,0,0},{0,0,0}};
    /**the server-sided persistent UUID of the owner*/
    private UUID owner = null;
    /**the front entity bogie*/
    public EntityBogie frontBogie = null;
    /**the back entity bogie*/
    public EntityBogie backBogie = null;
    /**the list of seat entities*/
    public List<EntitySeat> seats = new ArrayList<>();
    /**the list of hitboxes and the class that manages them*/
    public HitboxHandler hitboxHandler = new HitboxHandler();
    /**the server-sided persistent UUID of the transport linked to the front of this,*/
    public UUID frontLinkedTransport = null;
    /**the id of the rollingstock linked to the front*/
    public Integer frontLinkedID =null;
    /**the server-sided persistent UUID of the transport linked to the back of this,*/
    public UUID backLinkedTransport = null;
    /**the id of the rollingstock linked to the back*/
    public Integer backLinkedID = null;
    /**the ID of the owner*/
    public int ownerID =0;
    /**the destination for routing*/
    public String destination ="";
    /**the key item for the entity*/
    public ItemStack key;
    /**used to initialize a laege number of variables that are used to calculate everything from movement to linking.
     * this is so we don't have to initialize each of these variables every tick, saves CPU.*/
    private double[][] vectorCache = new double[9][3];
    /**the health of the entity, similar to that of EntityLiving*/
    private int health = 20;
    /**the fluidTank tank*/
    private FluidStack fluidTank = null;
    /**the list of items used for the inventory and crafting slots.*/
    private List<ItemStack> items;
    /**the lst of unlocalized names for the itemFilter*/
    private List<String> itemFilter = new ArrayList<>();
    /**whether or not this needs to update the datawatchers*/
    public boolean updateWatchers = false;
    /**the RF battery for rollingstock.*/
    public int battery=0;
    /**the ticket that gives the entity permission to load chunks.*/
    private ForgeChunkManager.Ticket chunkTicket;
    /**a cached list of the loaded chunks*/
    public List<ChunkCoordIntPair> chunkLocations = new ArrayList<>();
    /**The X velocity of the front bogie*/
    public double frontVelocityX=0;
    /**The Z velocity of the front bogie*/
    public double frontVelocityZ=0;
    /**The X velocity of the back bogie*/
    public double backVelocityX=0;
    /**The Z velocity of the back bogie*/
    public double backVelocityZ=0;
    /**a list of the particles the transport is managing*/
    public List<ParticleFX> particles = new ArrayList<>();
    /**Used to be sure we only say once that the transport has been derailed*/
    private boolean displayDerail = false;

    @SideOnly(Side.CLIENT)
    public TransportRenderData renderData = new TransportRenderData();

    /**the array of booleans, defined as bits
     * 0- brake: defines the brake
     * 1- locked: defines if transport is locked to owner and key holders
     * 2- lamp: defines if lamp is on
     * 3- creative: defines if the transport should consume fuels and be able to derail.
     * 4- coupling: defines if the transport is looking to couple with other transports.
     * 5- inventory whitelist: defines if the inventory is a whitelist
     * 6- running: defines if te transport is running (usually only on trains).
     * 7-15 are unused.
     * for use see
     * @see #getBoolean(boolValues)
     * @see #setBoolean(boolValues, boolean)
     */
    private BitList bools = new BitList();
    public enum boolValues{BRAKE(0), LOCKED(1), LAMP(2), CREATIVE(3), COUPLINGFRONT(4), COUPLINGBACK(5), WHITELIST(6), RUNNING(7), DERAILED(8);
        public int index;
        boolValues(int index){this.index = index;}
    }

    public boolean getBoolean(boolValues var){
        if(worldObj.isRemote) {
            return bools.getFromInt(var.index, this.dataWatcher.getWatchableObjectInt(17));
        } else {
            return bools.get(var.index);
        }
    }

    public void setBoolean(boolValues var, boolean value){
        if (getBoolean(var) != value) {
            bools.set(var.index, value);
            updateWatchers = true;
        }
    }


    public GenericRailTransport(World world){
        super(world);
        setSize(1,2);
        ignoreFrustumCheck = true;
    }
    public GenericRailTransport(UUID owner, World world, double xPos, double yPos, double zPos){
        super(world);

        posY = yPos;
        posX = xPos;
        posZ = zPos;
        this.owner = owner;
        key = new ItemStack(new ItemKey(owner, getItem().getUnlocalizedName()));
        setSize(1,2);
        ignoreFrustumCheck = true;
    }

    /**
     * <h2>Entity initialization</h2>
     * Entity init runs right before the first tick.
     * This is useful for registering the datawatchers and inventory before we actually use them.
     * NOTE: slot 0 and 1 of the datawatcher are used by vanilla. It may be wise to leave the first 5 empty.
     */
    @Override
    public void entityInit(){
        this.dataWatcher.addObject(13, 0);//train fuel consumption current
        this.dataWatcher.addObject(14, 0);//train steam, or rollingstock fluid ID (so client can show fluid name)
        this.dataWatcher.addObject(15, 0);//train heat
        this.dataWatcher.addObject(17, 0);//booleans
        //19 is used by the core minecart
        this.dataWatcher.addObject(23, 0);//owner
        this.dataWatcher.addObject(20, 0);//tankA
        this.dataWatcher.addObject(21, 0);//front linked transport
        this.dataWatcher.addObject(22, 0);//back linked transport
        if (getSizeInventory()>0 && items == null) {
            items = new ArrayList<>();
            while (items.size() < getSizeInventory()) {
                items.add(null);
            }
            if (getRiderOffsets() != null && getRiderOffsets().length > 1) {
                items.add(null);
            }
        }
    }



    /*
     * <h2>base entity overrides</h2>
     * modify basic entity variables to give different uses/values.
     */
     /**returns if the player can push this, we actually use our own systems for this, so we return false*/
    @Override
    public boolean canBePushed() {return false;}
    @Override
    public int getMinecartType(){return 10002;}
    /**returns the world object for IEntityMultipart*/
    @Override
    public World func_82194_d(){return worldObj;}
    /**returns the hitboxes of the entity as an array rather than a list*/
    @Override
    public Entity[] getParts(){return hitboxHandler.hitboxList.toArray(new HitboxHandler.MultipartHitbox[hitboxHandler.hitboxList.size()]);}
    /**returns the hitbox of this entity, we dont need that so return null*/
    @Override
    public AxisAlignedBB getBoundingBox(){return null;}
    /**returns the hitbox of this entity, we dont need that so return null*/
    @Override
    public AxisAlignedBB getCollisionBox(Entity collidedWith){return null;}
    /**returns if this can be collided with, we don't use this so return false*/
    @Override
    public boolean canBeCollidedWith() {return false;}
    /**client only positioning of the transport, this should help to smooth the movement*/
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_) {
        if (frontBogie!=null && backBogie!= null){
            setPosition(
                    (frontBogie.posX + backBogie.posX) * 0.5D,
                    (frontBogie.posY + backBogie.posY) * 0.5D,
                    (frontBogie.posZ + backBogie.posZ) * 0.5D);

            setRotation((float)Math.toDegrees(Math.atan2(
                    frontBogie.posZ - backBogie.posZ,
                    frontBogie.posX - backBogie.posX)),
                    MathHelper.floor_double(Math.acos(frontBogie.posY / backBogie.posY)*RailUtility.degreesD));
        }else {
            this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
        }
        this.setRotation(p_70056_7_, p_70056_8_);
    }


    /**
     * <h2>damage and destruction</h2>
     * attackEntityFromPart is called when one of the hitboxes of the entity has taken damage of some form.
     * the damage done is handled manually so we can compensate for basically everything, and if health is 0 or lower, we destroy the entity part by part, leaving the main part of the entity for last.
     */
    @Override
    public boolean attackEntityFromPart(EntityDragonPart part, DamageSource damageSource, float damage){
        return attackEntityFrom(damageSource, damage);
    }
    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float p_70097_2_){
        //if its a creative player, destroy instantly
        if (damageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer) damageSource.getEntity()).capabilities.isCreativeMode && !damageSource.isProjectile()){
            health -=20;
            //if its reinforced and its not an explosion
        } else if (isReinforced() && !damageSource.isProjectile() && !damageSource.isExplosion()){
            health -=1;
            //if it is an explosion and it's reinforced, or it's not an explosion and isn't reinforced
        } else if ((damageSource.isExplosion() && isReinforced()) || (!isReinforced() && !damageSource.isProjectile())){
            health -=5;
            //if it isn't reinforced and is an explosion
        } else if (damageSource.isExplosion() && !isReinforced()){
            health-=20;
        }
        //cover overheating, or other damage to self.
        if (damageSource.getSourceOfDamage() == this){
            health-=20;
        }

        //on Destruction
        if (health<1){
            //remove bogies
            frontBogie.isDead = true;
            TrainsInMotion.keyChannel.sendToServer(new PacketRemove(frontBogie.getEntityId(),false));
            worldObj.removeEntity(frontBogie);
            backBogie.isDead = true;
            TrainsInMotion.keyChannel.sendToServer(new PacketRemove(backBogie.getEntityId(),false));
            worldObj.removeEntity(backBogie);
            //remove seats
            for (EntitySeat seat : seats) {
                seat.isDead = true;
                TrainsInMotion.keyChannel.sendToServer(new PacketRemove(seat.getEntityId(),false));
                seat.worldObj.removeEntity(seat);
            }
            //remove hitboxes
            for (EntityDragonPart hitbox : hitboxHandler.hitboxList){
                hitbox.isDead = true;
                TrainsInMotion.keyChannel.sendToServer(new PacketRemove(hitbox.getEntityId(),false));
                hitbox.worldObj.removeEntity(hitbox);
            }
            //if the damage source is an explosion, or this (from overheating as an example), we circumstantially blow up.
            //radius is defined by whether or not it's a nuclear train, and fire spread creation is defined by whether or not it's diesel.

            //remove this
            isDead=true;
            if (damageSource.getEntity() instanceof EntityPlayer) {
                TrainsInMotion.keyChannel.sendToServer(new PacketRemove(getEntityId(), !((EntityPlayer) damageSource.getEntity()).capabilities.isCreativeMode));
            } else {
                TrainsInMotion.keyChannel.sendToServer(new PacketRemove(getEntityId(),false));
            }
            worldObj.removeEntity(this);
        }
        return false;
    }


    /*
     * <h3>add bogies and seats</h3>
     */
    /** this is called by the seats and seats on their spawn to add them to this entity's list of seats, we only do it on client because that's the only side that seems to lose track.
     * @see EntitySeat#readSpawnData(ByteBuf)*/
    @SideOnly(Side.CLIENT)
    public void setseats(EntitySeat seat, int seatNumber){
        if (seats.size() <= seatNumber) {
            seats.add(seat);
        } else {
            seats.set(seatNumber, seat);
        }
    }

    /** this is called by the bogies on their spawn to add them to this entity's list of bogies, we only do it on client because that's the only side that seems to lose track.
     * @see EntityBogie#readSpawnData(ByteBuf)*/
    @SideOnly(Side.CLIENT)
    public void setBogie(EntityBogie cart, boolean isFront){
        if(isFront){
            frontBogie = cart;
        } else {
            backBogie = cart;
        }
    }

    /*
     * <h2> Data Syncing and Saving </h2>
     * SpawnData is mainly used for data that has to be created on client then sent to the server, like data processed on item use.
     * NBT is save data, which only happens on server.
     */

    /**reads the data sent from client on entity spawn*/
    @Override
    public void readSpawnData(ByteBuf additionalData) {
        owner = new UUID(additionalData.readLong(), additionalData.readLong());
        key = new ItemStack(new ItemKey(owner, getItem().getUnlocalizedName()));
        rotationYaw = additionalData.readFloat();
    }
    /**sends the data to server from client*/
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeLong(owner.getMostSignificantBits());
        buffer.writeLong(owner.getLeastSignificantBits());
        buffer.writeFloat(rotationYaw);
    }
    /**loads the entity's save file*/
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        bools.set(tag.getInteger(NBTKeys.bools));
        isDead = tag.getBoolean(NBTKeys.dead);
        //load links
        if (tag.hasKey(NBTKeys.frontLinkMost)) {
            frontLinkedTransport = new UUID(tag.getLong(NBTKeys.frontLinkMost), tag.getLong(NBTKeys.frontLinkLeast));
        }
        if (tag.hasKey(NBTKeys.backLinkMost)) {
            backLinkedTransport = new UUID(tag.getLong(NBTKeys.backLinkMost), tag.getLong(NBTKeys.backLinkLeast));
        }
        //load owner
        owner = new UUID(tag.getLong(NBTKeys.ownerMost),tag.getLong(NBTKeys.ownerLeast));
        //load bogie velocities
        tag.setDouble(NBTKeys.frontBogieX, frontVelocityX);
        tag.setDouble(NBTKeys.frontBogieZ, frontVelocityZ);
        tag.setDouble(NBTKeys.backBogieX, backVelocityX);
        tag.setDouble(NBTKeys.backBogieZ, backVelocityZ);

        //load tanks
        if (getTankCapacity() >0) {
            if(tag.hasKey("FluidName")) {
                fluidTank = FluidStack.loadFluidStackFromNBT(tag);
            } else {
                fluidTank = null;
            }
        }

        if (getSizeInventory()>0) {
            for (int i = 0; i < getSizeInventory(); i++) {
                NBTTagCompound tagCompound = tag.getCompoundTag(NBTKeys.inventoryItem +i);
                if (tagCompound != null){
                    setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(tagCompound));
                }
            }

            int length = tag.getInteger(NBTKeys.filterLength);
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    itemFilter.add(tag.getString(NBTKeys.filterItem + i));
                }
            }
        }

        updateWatchers = true;
    }
    /**saves the entity to server world*/
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        tag.setInteger(NBTKeys.bools, bools.toInt());
        tag.setBoolean(NBTKeys.dead, isDead);
        //frontLinkedTransport and backLinkedTransport bogies
        if (frontLinkedTransport != null){
            tag.setLong(NBTKeys.frontLinkMost, frontLinkedTransport.getMostSignificantBits());
            tag.setLong(NBTKeys.frontLinkLeast, frontLinkedTransport.getLeastSignificantBits());
        }
        if (backLinkedTransport != null){
            tag.setLong(NBTKeys.backLinkMost, backLinkedTransport.getMostSignificantBits());
            tag.setLong(NBTKeys.backLinkLeast, backLinkedTransport.getLeastSignificantBits());
        }
        //owner
        tag.setLong(NBTKeys.ownerMost, owner.getMostSignificantBits());
        tag.setLong(NBTKeys.ownerLeast, owner.getLeastSignificantBits());

        //bogie velocities
        tag.setDouble(NBTKeys.frontBogieX, frontVelocityX);
        tag.setDouble(NBTKeys.frontBogieZ, frontVelocityZ);
        tag.setDouble(NBTKeys.backBogieX, backVelocityX);
        tag.setDouble(NBTKeys.backBogieZ, backVelocityZ);



        //tanks
        if (getTankCapacity() >0){
            if (fluidTank != null) {
                fluidTank.writeToNBT(tag);
            }
        }
        if (getSizeInventory()>0 && items.size()>0) {
            for (int i =0; i< getSizeInventory(); i++) {
                if (items.get(i) != null) {
                    tag.setTag(NBTKeys.inventoryItem + i, items.get(i).writeToNBT(new NBTTagCompound()));
                }
            }


            tag.setInteger(NBTKeys.filterLength, itemFilter !=null? itemFilter.size():0);
            if (itemFilter != null && itemFilter.size() > 0) {
                for (int i = 0; i < itemFilter.size(); i++) {
                    tag.setString(NBTKeys.filterItem + i, itemFilter.get(i));
                }
            }
        }

    }

    /**plays a sound during entity movement*/
    @Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
    }


    /**
     * <h2> on entity update </h2>
     *
     * defines what should be done every tick
     * used for:
     * managing the list of bogies and seats, respawning them if they disappear.
     * managing speed, acceleration. and direction.
     * managing rotationYaw and rotationPitch.
     * updating rider entity positions if there is no one riding the core seat.
     * calling on link management.
     * @see #manageLinks()
     * syncing the owner entity ID with client.
     * being sure the transport is listed in the main class (for lighting management).
     * @see ClientProxy#onTick(TickEvent.ClientTickEvent)
     * and updating the lighting block.
     */
    @Override
    public void onUpdate() {
        //if the cart has fallen out of the map, destroy it.
        if (posY < -64.0D & isDead){
            worldObj.removeEntity(this);
        }

        if(this.chunkTicket == null) {
            this.requestTicket();
        }

        //be sure bogies exist

        //always be sure the bogies exist on client and server.
        if (!worldObj.isRemote && (frontBogie == null || backBogie == null)) {
            //spawn frontLinkedTransport bogie
            vectorCache[1][0] = getLengthFromCenter();
            vectorCache[0] = RailUtility.rotatePoint(vectorCache[1],rotationPitch, rotationYaw,0);
            frontBogie = new EntityBogie(worldObj, posX + vectorCache[0][0], posY + vectorCache[0][1], posZ + vectorCache[0][2], getEntityId(), true);
            frontBogie.setVelocity(frontVelocityX,0,frontVelocityZ);
            //spawn backLinkedTransport bogie
            vectorCache[1][0] = -getLengthFromCenter();
            vectorCache[0] = RailUtility.rotatePoint(vectorCache[1],rotationPitch, rotationYaw,0);
            backBogie = new EntityBogie(worldObj, posX + vectorCache[0][0], posY + vectorCache[0][1], posZ + vectorCache[0][2], getEntityId(), false);
            backBogie.setVelocity(backVelocityX, 0, backVelocityZ);
            worldObj.spawnEntityInWorld(frontBogie);
            worldObj.spawnEntityInWorld(backBogie);

            if (getRiderOffsets() != null && getRiderOffsets().length >1 && seats.size()<getRiderOffsets().length) {
                for (int i = 0; i < getRiderOffsets().length - 1; i++) {
                    EntitySeat seat = new EntitySeat(worldObj, posX, posY, posZ, getEntityId(), i);
                    worldObj.spawnEntityInWorld(seat);
                    seats.add(seat);
                }
            }
        }

        /*
         * run the hitbox check whether or not the bogies exist so we can ensure interaction even during severe client-sided error.
         *check if the bogies exist, because they may not yet, and if they do, check if they are actually moving or colliding.
         * no point in processing movement if they aren't moving or if the train hit something.
         * if it is clear however, then we need to add velocity to the bogies based on the current state of the train's speed and fuel, and reposition the train.
         * but either way we have to position the bogies around the train, just to be sure they don't accidentally fly off at some point.
         *
         * this stops updating if the transport derails. Why update positions of something that doesn't move? We compensate for first tick to be sure hitboxes, bogies, etc, spawn on join.
         */
        if (frontBogie!=null && backBogie != null && (!getBoolean(boolValues.DERAILED) || ticksExisted==1)){
            //handle movement.
            if (!hitboxHandler.getCollision(this)) {
                setBoolean(boolValues.DERAILED, frontBogie.minecartMove(rotationPitch, rotationYaw, getBoolean(boolValues.BRAKE), getBoolean(boolValues.RUNNING), getType().isTrain(), weightKg()));
                setBoolean(boolValues.DERAILED, backBogie.minecartMove(rotationPitch, rotationYaw, getBoolean(boolValues.BRAKE), getBoolean(boolValues.RUNNING), getType().isTrain(), weightKg()));
            } else {
                frontBogie.addVelocity(-frontBogie.motionX,-frontBogie.motionY,-frontBogie.motionZ);
                backBogie.addVelocity(-backBogie.motionX,-backBogie.motionY,-backBogie.motionZ);
                setBoolean(boolValues.DERAILED, frontBogie.minecartMove(rotationPitch, rotationYaw, getBoolean(boolValues.BRAKE), getBoolean(boolValues.RUNNING), getType().isTrain(), weightKg()));
                setBoolean(boolValues.DERAILED, backBogie.minecartMove(rotationPitch, rotationYaw, getBoolean(boolValues.BRAKE), getBoolean(boolValues.RUNNING), getType().isTrain(), weightKg()));
            }
            frontVelocityX = frontBogie.motionX;
            frontVelocityZ = frontBogie.motionZ;
            backVelocityX = backBogie.motionX;
            backVelocityZ = backBogie.motionZ;

            //position this
            setPosition(
                    ((frontBogie.posX + backBogie.posX) * 0.5D),
                    ((frontBogie.posY + backBogie.posY) * 0.5D),
                    ((frontBogie.posZ + backBogie.posZ) * 0.5D));


            setRotation((float)Math.toDegrees(Math.atan2(
                    frontBogie.posZ - backBogie.posZ,
                    frontBogie.posX - backBogie.posX)),
                    MathHelper.floor_double(Math.acos(frontBogie.posY / backBogie.posY)*RailUtility.degreesD));


            if (ticksExisted %2 ==0) {
                //align bogies
                vectorCache[1][0]= getLengthFromCenter();
                vectorCache[0] = rotatePoint(vectorCache[1], rotationPitch, rotationYaw, 0.0f);
                frontBogie.setPosition(vectorCache[0][0] + posX, frontBogie.posY, vectorCache[0][2] + posZ);
                vectorCache[1][0]= -getLengthFromCenter();
                vectorCache[0] = rotatePoint(vectorCache[1], rotationPitch, rotationYaw, 0.0f);
                backBogie.setPosition(vectorCache[0][0] + posX, backBogie.posY, vectorCache[0][2] + posZ);
            }
            manageLinks();
        }

        //rider updating isn't called if there's no driver/conductor, so just in case of that, we reposition the seats here too.
        if (riddenByEntity == null && getRiderOffsets() != null) {
            for (int i = 0; i < seats.size(); i++) {
                vectorCache[0] = rotatePoint(getRiderOffsets()[i], rotationPitch, rotationYaw, 0);
                vectorCache[0][0] += posX;
                vectorCache[0][1] += posY;
                vectorCache[0][2] += posZ;
                seats.get(i).setPosition(vectorCache[0][0], vectorCache[0][1], vectorCache[0][2]);
            }
        }

        //be sure the owner entityID is currently loaded, this variable is dynamic so we don't save it to NBT.
        if (!worldObj.isRemote &&ticksExisted %10==0){

            manageFuel();

            @Nullable
            Entity player = CommonProxy.getEntityFromUuid(owner);
            if (player!= null) {
                ownerID = player.getEntityId();
            }
            //sync the linked transports with client, and on server, easier to use an ID than a UUID.
            Entity linkedTransport = CommonProxy.getEntityFromUuid(frontLinkedTransport);
            if (linkedTransport instanceof GenericRailTransport && (frontLinkedID == null || linkedTransport.getEntityId() != frontLinkedID)) {
                frontLinkedID = linkedTransport.getEntityId();
                updateWatchers = true;
            }
            linkedTransport = CommonProxy.getEntityFromUuid(backLinkedTransport);
            if (linkedTransport instanceof GenericRailTransport && (backLinkedID == null || linkedTransport.getEntityId() != backLinkedID)) {
                backLinkedID = linkedTransport.getEntityId();
                updateWatchers = true;
            }

            if (!worldObj.isRemote && getBoolean(boolValues.DERAILED) && !displayDerail){
                //MinecraftServer.getServer().addChatMessage(new ChatComponentText(getOwner().getName()+"'s " + StatCollector.translateToLocal(getItem().getUnlocalizedName()) + " has derailed!"));
                displayDerail = true;
            }

            if(updateWatchers){
                dataWatcher.updateObject(20, fluidTank==null?0:fluidTank.amount);
                if (!getType().isTrain()) {
                    dataWatcher.updateObject(14, fluidTank == null ? -1 : fluidTank.getFluidID());
                }
                this.dataWatcher.updateObject(23,ownerID);
                this.dataWatcher.updateObject(17, bools.toInt());
                this.dataWatcher.updateObject(21, linkedTransport instanceof GenericRailTransport?linkedTransport.getEntityId():0);
                this.dataWatcher.updateObject(22,linkedTransport instanceof GenericRailTransport?linkedTransport.getEntityId():0);
            }
        }

        /*
         * be sure the client proxy has a reference to this so the lamps can be updated, and then every other tick, attempt to update the lamp position if it's necessary.
         */
        if (backBogie!=null && !isDead && worldObj.isRemote) {
            if (ClientProxy.EnableLights && !ClientProxy.carts.contains(this)) {
                ClientProxy.carts.add(this);
            }
            if (lamp.Y >1 && ticksExisted %2 ==1){
                vectorCache[0][0] =this.posX + getLampOffset().xCoord;
                vectorCache[0][1] =this.posY + getLampOffset().yCoord;
                vectorCache[0][2] =this.posZ + getLampOffset().zCoord;
                lamp.ShouldUpdate(worldObj, RailUtility.rotatePoint(vectorCache[0], rotationPitch, rotationYaw, 0));
            }

            //update the particles here rather than on render tick, it's not as smooth, but close enough and far less CPU use.
            if (ClientProxy.EnableSmokeAndSteam) {
                int itteration = 0;
                int maxSpawnThisTick = 0;
                for (float[] smoke : getSmokeOffset()) {
                    for (int i = 0; i < smoke[4]; i++) {
                        //we only want to spawn at most 5 particles per tick.
                        //this helps make them spawn more evenly rather than all at once. It also helps prevent a lot of lag.
                        if (getBoolean(boolValues.RUNNING) && particles.size() <= itteration && maxSpawnThisTick < 5) {
                            particles.add(new ParticleFX(this, smoke[3], smoke));
                            maxSpawnThisTick++;
                        } else if (maxSpawnThisTick == 0 && particles.size() > itteration) {
                            //if the particles have finished spawning in, move them.
                            particles.get(itteration).onUpdate(getBoolean(boolValues.RUNNING));
                        }
                        itteration++;
                    }
                }
            }
        }
    }


    /**
     * <h2>Rider offset</h2>
     * this runs every tick to be sure the riders, and seats, are in the correct positions.
     * NOTE: this only happens while there is an entity riding this, entities riding seats do not activate this function.
     */
    @Override
    public void updateRiderPosition() {
        if (getRiderOffsets() != null) {
            if (riddenByEntity != null) {
                vectorCache[2] = rotatePoint(getRiderOffsets()[0], rotationPitch, rotationYaw, 0);
                riddenByEntity.setPosition(vectorCache[2][0] + this.posX, vectorCache[2][1] + this.posY, vectorCache[2][2] + this.posZ);
            }

            for (int i = 0; i < seats.size(); i++) {
                vectorCache[2] = rotatePoint(getRiderOffsets()[i], rotationPitch, rotationYaw, 0);
                vectorCache[2][0] += posX;
                vectorCache[2][1] += posY;
                vectorCache[2][2] += posZ;
                seats.get(i).setPosition(vectorCache[2][0], vectorCache[2][1], vectorCache[2][2]);
            }
        }

    }


    /**
     * <h2>manage links</h2>
     * this is used to reposition the transport based on the linked transports.
     * If coupling is on then it will check sides without linked transports for anything to link to.
     */
    private GenericRailTransport linkChecker = null;
    public void manageLinks() {

        if (!worldObj.isRemote) {
            //manage the frontLinkedTransport link
            if (frontLinkedID != null) {
                Entity frontLink = worldObj.getEntityByID(frontLinkedID);
                if (frontLink instanceof GenericRailTransport) {
                    linkChecker = (GenericRailTransport) frontLink;
                    //to here
                    vectorCache[3][0] = getHitboxPositions()[0] - 1;
                    vectorCache[4] = rotatePoint(vectorCache[3], 0, rotationYaw, 0);
                    vectorCache[4][0] += posX;
                    vectorCache[4][2] += posZ;
                    //from here
                    vectorCache[5][0] = linkChecker.getHitboxPositions()[
                            (linkChecker.backLinkedTransport == this.getPersistentID()) ? (linkChecker.getHitboxPositions().length - 1) : 0
                            ];
                    vectorCache[6] = rotatePoint(vectorCache[5], 0, linkChecker.rotationYaw, 0);
                    vectorCache[6][0] += linkChecker.posX;
                    vectorCache[6][2] += linkChecker.posZ;
                    frontBogie.addVelocity(-(vectorCache[4][0] - vectorCache[6][0]) * 0.05, 0, -(vectorCache[4][2] - vectorCache[6][2]) * 0.05);
                    backBogie.addVelocity(-(vectorCache[4][0] - vectorCache[6][0]) * 0.05, 0, -(vectorCache[4][2] - vectorCache[6][2]) * 0.05);
                }
            }
            //Manage the backLinkedTransport link
            if (backLinkedID != null) {
                Entity backLink = worldObj.getEntityByID(backLinkedID);
                if (backLink instanceof GenericRailTransport) {
                    linkChecker = (GenericRailTransport) backLink;
                    //to here
                    vectorCache[3][0] = getHitboxPositions()[getHitboxPositions().length - 1] + 1;
                    vectorCache[4] = rotatePoint(vectorCache[3], 0, rotationYaw, 0);
                    vectorCache[4][0] += posX;
                    vectorCache[4][2] += posZ;
                    //from here
                    vectorCache[5][0] = ((GenericRailTransport) backLink).getHitboxPositions()[
                            (linkChecker.backLinkedTransport == this.getPersistentID()) ? (linkChecker.getHitboxPositions().length - 1) : 0
                            ];
                    vectorCache[6] = rotatePoint(vectorCache[5], 0, linkChecker.rotationYaw, 0);
                    vectorCache[6][0] += linkChecker.posX;
                    vectorCache[6][2] +=linkChecker.posZ;
                    frontBogie.addVelocity(-(vectorCache[4][0] - vectorCache[6][0]) * 0.05, 0, -(vectorCache[4][2] - vectorCache[6][2]) * 0.05);
                    backBogie.addVelocity(-(vectorCache[4][0] - vectorCache[6][0]) * 0.05, 0, -(vectorCache[4][2] - vectorCache[6][2]) * 0.05);
                }
            }
            linkChecker = null;
        }
    }

    /**
     * <h2>RF storage transfer</h2>
     * this is used to figure out how much RF this transport can take from another transport.
     * this is intended for rollingstock that can store power for an electric train.
     * @return the amount of RF this can accept
     */
    public int RequestPower(){
        if(getRFCapacity() - battery >20){
            return 20;
        } else if (getRFCapacity() - battery >0){
            return getRFCapacity() - battery;
        }
        return 0;
    }

    /**
     * <h2>Packet use</h2>
     * used to run functionality on server defined by a key press on the client.
     * sent to server by
     * @see ebf.tim.networking.PacketKeyPress
     * through
     * @see EventManager#onClientKeyPress(InputEvent.KeyInputEvent)
     */
    public boolean ProcessPacket(int functionID){
        switch (functionID){
            case 4:{ //Toggle brake
                setBoolean(boolValues.BRAKE, !bools.get(0));
                return true;
            }case 5:{ //Toggle lamp
                setBoolean(boolValues.LAMP, !bools.get(2));
                return true;
            }case 6:{ //Toggle locked
                setBoolean(boolValues.LOCKED, !bools.get(1));
                return true;
            }case 7:{ //Toggle coupling
                boolean toset = !bools.get(4);
                setBoolean(boolValues.COUPLINGFRONT, toset);
                setBoolean(boolValues.COUPLINGBACK, toset);
                return true;
            }case 10:{ //Toggle transport creative mode
                setBoolean(boolValues.CREATIVE, !bools.get(3));
                return true;
            }case 12:{ //drop key to transport
                entityDropItem(key, 1);
                return true;
            }case 13:{ //unlink transports
                Entity transport;
                //frontLinkedTransport
                if (frontLinkedID != null){
                    transport = worldObj.getEntityByID(frontLinkedID);
                    if (transport instanceof GenericRailTransport){
                        Entity transportTemp = worldObj.getEntityByID(frontLinkedID);
                        if (transportTemp instanceof GenericRailTransport && transport.getEntityId() == transportTemp.getEntityId()){
                            ((GenericRailTransport) transportTemp).frontLinkedTransport = null;
                            ((GenericRailTransport) transportTemp).updateWatchers = true;
                        }
                        transportTemp = worldObj.getEntityByID(backLinkedID);
                        if (transportTemp instanceof GenericRailTransport && transport.getEntityId() == transportTemp.getEntityId()){
                            ((GenericRailTransport) transportTemp).backLinkedTransport = null;
                            ((GenericRailTransport) transportTemp).updateWatchers = true;
                        }
                        ((GenericRailTransport) transport).frontLinkedTransport = null;
                        ((GenericRailTransport) transport).updateWatchers = true;
                    }
                }
                //backLinkedTransport
                if (backLinkedID != null){
                    transport = worldObj.getEntityByID(backLinkedID);
                    if (transport instanceof GenericRailTransport){
                        Entity transportTemp = worldObj.getEntityByID(frontLinkedID);
                        if (transportTemp instanceof GenericRailTransport && transport.getEntityId() == transportTemp.getEntityId()){
                            ((GenericRailTransport) transportTemp).frontLinkedTransport = null;
                            ((GenericRailTransport) transportTemp).updateWatchers = true;
                        }
                        transportTemp = worldObj.getEntityByID(backLinkedID);
                        if (transportTemp instanceof GenericRailTransport && transport.getEntityId() == transportTemp.getEntityId()){
                            ((GenericRailTransport) transportTemp).backLinkedTransport = null;
                            ((GenericRailTransport) transportTemp).updateWatchers = true;
                        }
                        ((GenericRailTransport) transport).backLinkedTransport = null;
                        ((GenericRailTransport) transport).updateWatchers = true;
                    }
                }
                //double check and be sure both are null
                frontLinkedTransport = null; backLinkedTransport = null;
                return true;
            }
        }
        return false;
    }

    /**
     * <h2>Permissions handler</h2>
     * Used to check if the player has permission to do whatever it is the player is trying to do. Yes I could be more vague with that.
     *
     * @param player the player attenpting to interact.
     * @param driverOnly can this action only be done by the driver/conductor?
     * @return if the player has permission to continue
     */
    public boolean getPermissions(EntityPlayer player, boolean driverOnly) {
        //if this requires the player to be the driver, and they aren't, just return false before we even go any further.
        if (driverOnly && player.getEntityId() != this.riddenByEntity.getEntityId()){
            return false;
        }

        //be sure admins and owners can do whatever
        if (player.capabilities.isCreativeMode || owner == player.getUniqueID()) {
            return true;
        }

        //if the key is needed, like for trains and freight
        if (getBoolean(boolValues.LOCKED) && player.inventory.hasItem(key.getItem())) {
            return true;
        }
        //if a ticket is needed like for passenger cars
        if(getBoolean(boolValues.LOCKED) && getRiderOffsets().length>1){
            return player.inventory.hasItem(getTicketSlot().getItem());
        }

        //all else fails, just return if this is locked.
        return !getBoolean(boolValues.LOCKED);

    }

    @Override
    protected void setRotation(float p_70101_1_, float p_70101_2_) {
        this.prevRotationYaw = this.rotationYaw = p_70101_1_;
        this. prevRotationPitch = this.rotationPitch = p_70101_2_;
    }


    public GameProfile getOwner(){
        if (ownerID != 0 && worldObj.getEntityByID(ownerID) instanceof EntityPlayer){
            return ((EntityPlayer) worldObj.getEntityByID(ownerID)).getGameProfile();
        }
        return null;
    }


    @Override
    public boolean canPassFluidRequests(Fluid fluid){
        return getTankCapacity() !=0;
    }

    @Override
    public boolean canAcceptPushedFluid(EntityMinecart requester, Fluid fluid){
        return false;
    }

    @Override
    public boolean canProvidePulledFluid(EntityMinecart requester, Fluid fluid){
        return getTankCapacity()!=0 && canDrain(ForgeDirection.UNKNOWN, fluid);
    }

    @Override
    public void setFilling(boolean filling){}

    /*
     * <h2>Inherited variables</h2>
     * these functions are overridden by classes that extend this so that way the values can be changed indirectly.
     */
    /**returns the lengths from center that represent the offset each bogie should render at*/
    public List<Double> getRenderBogieOffsets(){return new ArrayList<>();}
    /**returns the type of transport, for a list of options:
     * @see TrainsInMotion.transportTypes */
    public TrainsInMotion.transportTypes getType(){return null;}
    /**returns the rider offsets, each of the outer arrays represents a new rider seat,
     * the first value of the double[] inside that represents length from center in blocks.
     * the second represents height offset in blocks
     * the third value is for the horizontal offset*/
    public double[][] getRiderOffsets(){return new double[][]{{0,0,0}};}
    /**returns the positions for the hitbox, they are defined by length from center.*/
    public double[] getHitboxPositions(){return new double[]{-1,0,1};}
    /**returns the item of the transport, this should be a static value in the transport's class.*/
    public Item getItem(){return null;}
    /**defines the size of the inventory, not counting any special slots like for fuel.
     * @see TrainsInMotion.inventorySizes*/
    public TrainsInMotion.inventorySizes getInventorySize(){return TrainsInMotion.inventorySizes.FREIGHT_ONE;}
    /**defines the offset for the lamp in X/Y/Z*/
    public Vec3d getLampOffset(){return new Vec3d(0,0,0);}
    /**defines the radius in microblocks that the pistons animate*/
    public float getPistonOffset(){return 0;}
    /**defines smoke positions, the outer array defines each new smoke point, the inner arrays define the X/Y/Z*/
    public float[][] getSmokeOffset(){return new float[0][0];}
    /**defines the length from center of the transport, thus is used for the motion calculation*/
    public double getLengthFromCenter(){return 1d;}
    /**defines the render scale, minecraft's default is 0.0625*/
    public float getRenderScale(){return 0.0625f;}
    /**defines if the transport is explosion resistant*/
    public boolean isReinforced(){return false;}
    /**defines the capacity of the fluidTank tank.
     * Usually value is 10,000 *the cubic meter capacity, so 242 gallons, is 0.9161 cubic meters, which is 9161 tank capacity
     *NOTE if this is used for a train, minimum value should be 1100, which is just a little over a single bucket to allow prevention of overheating.*/
    public int getTankCapacity(){return 0;}
    /**defines the capacity of the RF storage, intended for electric rollingstock that store power for the train.*/
    public int getRFCapacity(){return 0;}
    /**defines the ID of the owner*/
    public int getOwnerID(){return this.dataWatcher.getWatchableObjectInt(23);}
    /**this function allows individual trains and rollingstock to implement custom fuel consumption and management
     * @see FuelHandler#manageSteam(EntityTrainCore) */
    public void manageFuel(){}
    /**defines the weight of the transport.*/
    public float weightKg(){return 907.1847f;}
    /** returns the max fuel.
     * for steam trains this is cubic meters of the firebox size. (1.5 on average)
     * for diesel this is cubic meters. (11.3 on average)
     * for electric this is Kw. (400 on average)
     * for nuclear this is the number of fusion cores, rounded down. (usually 1)*/
    public float getMaxFuel(){return 1;}

    public Bogie[] getBogieModels(){return null;}

    public ResourceLocation getTexture(){return null;}

    public ModelBase getModel(){return null;}


    /*
     * <h1>Inventory management</h1>
     */

    /**
     * <h2>define filters</h2>
     * this is called on the creation of an entity that need it's inventory filtered, or on the event that the entity's itemFilter is set, like from the GUI.
     * whitelist as true will allow only the defined types or items. while as false will allow anything except the defined types or items.
     * types in most cases will override items because items are always checked last, if at all.
     * itemTypes.ALL is basically just ignored, this is only called when you are not going to itemFilter by type.
     */
    public void setFilter(boolean isWhitelist, Item[] items){
        setBoolean(boolValues.WHITELIST, isWhitelist);
        if (items != null) {
            itemFilter.clear();
            for (Item itm : items){
                itemFilter.add(itm.getUnlocalizedName());
            }
        }
    }

    /**
     * <h2>inventory size</h2>
     * @return the number of slots the inventory should have.
     * if it's a train we have to calculate the size based on the type and the size of inventory its supposed to have.
     * trains get 1 extra slot by default for fuel, steam and nuclear steam get another slot, and if it can take passengers there's another slot, this is added to the base inventory size.
     * if it's not a train or rollingstock, then just return the base amount for a crafting table.
     */
    @Override
    public int getSizeInventory() {
        int size =0;
        switch (getType()){
            case NUCLEAR_STEAM: case STEAM:case TANKER:{
                size=2;break;
            }
            case DIESEL:case ELECTRIC:case NUCLEAR_ELECTRIC:case MAGLEV:case TENDER:{
                size=1;break;
            }
        }
        if (getRiderOffsets() != null && getRiderOffsets().length >1){
            size++;
        }
        return 1+ size+ (9 * getInventorySize().getRow());
    }

    /**
     * <h2>get item</h2>
     * @return the item in the requested slot
     */
    @Override
    public ItemStack getStackInSlot(int slot) {
        if (items == null || slot <0 || slot >= items.size()){
            return null;
        } else {
            return items.get(slot);
        }
    }

    /**
     * <h2>decrease stack size</h2>
     * @return the itemstack with the decreased size. If the decreased size is equal to or less than the current stack size it returns null.
     */
    @Override
    public ItemStack decrStackSize(int slot, int stackSize) {
        if (items!= null && getSizeInventory()>=slot && items.get(slot) != null) {
            ItemStack itemstack;

            if (items.get(slot).stackSize <= stackSize) {
                itemstack = items.get(slot).copy();
                items.set(slot, null);

                return itemstack;
            } else {
                itemstack = items.get(slot).splitStack(stackSize);
                if (items.get(slot).stackSize == 0) {
                    items.set(slot, null);
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    /**
     * <h2>Set slot</h2>
     * sets the slot contents, this is a direct override so we don't have to compensate for anything.
     */
    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        if (items != null && slot >=0 && slot <= getSizeInventory()) {
            items.set(slot, itemStack);
        }
    }

    /**
     * <h2>name and stack limit</h2>
     * These are grouped together because they are pretty self-explanatory.
     */
    @Override
    public String getInventoryName() {return getItem().getUnlocalizedName() + ".storage";}
    @Override
    public boolean hasCustomInventoryName() {return items != null;}
    @Override
    public int getInventoryStackLimit() {return getType().isTanker()?1:items!=null?64:0;}

    /**
     * <h2>is Locked</h2>
     * returns if the entity is locked, and if it is, if the player is the owner.
     * This makes sure the inventory can be accessed by anyone if its unlocked and only by the owner when it is locked.
     * if it's a tile entity, it's just another null check to be sure no one crashes.
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {return getPermissions(p_70300_1_, false);}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        //compensate for specific rollingstock
        switch (getType()) {
            case LOGCAR: {
                return RailUtility.isLog(itemStack) || RailUtility.isPlank(itemStack);
            }
            case COALHOPPER: {
                return RailUtility.isCoal(itemStack);
            }
            case TANKER: {
                return itemStack.getItem() instanceof ItemBucket;
            }
        }


        //before we even bother to try and check everything else, check if it's filtered in the first place.
        if (itemStack == null || itemFilter.size() == 0) {
            return true;
        }
        //if we use a whitelist, only return true if the item is in the list.
        if (getBoolean(boolValues.WHITELIST)) {
            return itemFilter.size() != 0 && itemFilter.contains(itemStack.getItem().getUnlocalizedName());
        } else {
            //if it's a blacklist do exactly the same as above but return the opposite value.
            return itemFilter.size() == 0 || !itemFilter.contains(itemStack.getItem().getUnlocalizedName());
        }
    }


    /**
     * <h2>Get Ticket Slot</h2>
     * this just simply returns the itemstack in the ticket slot, assuming there is one.
     */
    public ItemStack getTicketSlot(){
        //if it's a train with a boiler, send backLinkedTransport slot 2.
        if ((getType() == TrainsInMotion.transportTypes.STEAM || getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM) &&
                getRiderOffsets().length > 1) {
            return items.get(2);
        } else if ((getType() == TrainsInMotion.transportTypes.DIESEL || getType() == TrainsInMotion.transportTypes.ELECTRIC || getType() == TrainsInMotion.transportTypes.NUCLEAR_ELECTRIC || getType() == TrainsInMotion.transportTypes.MAGLEV)
                && getRiderOffsets().length > 1){
            //if it's a train without a boiler, send backLinkedTransport slot 1
            return items.get(1);
        } else if (getRiderOffsets().length > 1){
            //if it's not a train, send backLinkedTransport slot 0
            return items.get(0);
        }
        //if it's not meant to have multiple passengers, send backLinkedTransport null because there is no ticket slot.
        return null;
    }
    /**
     * <h2>Add item to train inventory</h2>
     * custom function for adding items to the train's inventory.
     * similar to a container's TransferStackInSlot function, this will automatically sort an item into the inventory.
     * if there is no room in the inventory for the item, it will drop on the ground.
     */
    public void addItem(ItemStack item){
        for (int i=1; i<getSizeInventory();i++){
            if (i==1){
                if (getType() == TrainsInMotion.transportTypes.STEAM || getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM){
                    i++;
                }
                if (getRiderOffsets() != null && getRiderOffsets().length>1){
                    i++;
                }
            }

            if (getStackInSlot(i) ==null){
                setInventorySlotContents(i, item);
                return;
            } else if (getStackInSlot(i).getItem() == item.getItem() &&
                    item.stackSize + items.get(i).stackSize <= item.getMaxStackSize()){
                setInventorySlotContents(i, new ItemStack(item.getItem(), item.stackSize + items.get(i).stackSize));
                return;
            }
        }
        entityDropItem(item, item.stackSize);
    }

    /**
     * <h2>inventory percentage count</h2>
     * calculates percentage of inventory used then returns a value based on the intervals.
     * for example if the inventory is half full and the intervals are 100, it returns 50. or if the intervals were 90 it would return 45.
     */
    public int calculatePercentageUsed(int indexes){
        if (items == null){
            return 0;
        }
        float i=0;
        for (ItemStack item : items){
            if (item != null && item.stackSize >0){
                i++;
            }
        }
        return i>0?MathHelper.floor_double(((i / getSizeInventory()) *indexes)+0.5):0;
    }


    /**
     * <h2>get an item from inventory to render</h2>
     * cycles through the items in the inventory and returns the first non-null item that's index is greater than the provided number.
     * if it fails to find one it subtracts one from the index and tries again, and keeps trying until the index is negative, in which case it returns 0.
     */
    public ItemStack getFirstBlock(int index){
        for (int i=0; i<getSizeInventory(); i++){
            if (i>= index && items.get(i) != null && items.get(i).stackSize>0){
                return items.get(i);
            }
        }
        return getFirstBlock(index>0?index-1:0);
    }

    /*
     * <h2>unused</h2>
     * we have to initialize these values, but due to the design of the entity we don't actually use them.
     */
    /**used to sync the inventory on close.*/
    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        List<ItemStack> aitemstack = this.items;

        if (p_70304_1_ >= this.items.size()) {
            p_70304_1_ -= this.items.size();
        }

        if (aitemstack.get(p_70304_1_) != null) {
            ItemStack itemstack = aitemstack.get(p_70304_1_);
            aitemstack.set(p_70304_1_, null);
            return itemstack;
        } else {
            return null;
        }
    }
    @Override
    public void markDirty() {}
    /**called when the inventory GUI is opened*/
    @Override
    public void openInventory() {}
    /**called when the inventory GUI is closed*/
    @Override
    public void closeInventory() {}

    public void dropAllItems() {
        for (int i = 0; i < this.items.size(); ++i) {
            if (this.items.get(i) != null) {
                this.entityDropItem(this.items.get(i),this.items.get(i).stackSize);
                this.items.set(i, null);
            }
        }
    }


    /*
     * <h1>Fluid Management</h1>
     */
    /**Returns true if the given fluid can be extracted.*/
    @Override
    public boolean canDrain(@Nullable ForgeDirection from, Fluid resource){return fluidTank != null && getTankAmount()>0 && (fluidTank.getFluid() == resource || resource == null);}
    /**Returns true if the given fluid can be inserted into the fluid tank.*/
    @Override
    public boolean canFill(@Nullable ForgeDirection from, Fluid resource){return getTankCapacity()>0 && resource!=null && (fluidTank == null || fluidTank.getFluid() == resource);}
    //attempt to drain a set amount
    @Override
    public FluidStack drain(@Nullable ForgeDirection from, int drain, boolean doDrain){
        if (!canDrain(null,null)){
            return null;
        } else {
            int amountToDrain = getTankAmount() < drain?getTankAmount():drain;
            if (doDrain){
                if (amountToDrain == getTankAmount()) {
                    fluidTank = null;
                    updateWatchers=true;
                } else {
                    fluidTank.amount -= amountToDrain;
                    updateWatchers=true;
                }
            }
            return fluidTank!=null?new FluidStack(fluidTank.getFluid(), amountToDrain):null;
        }
    }

    /**drain with a fluidStack, this is mostly a redirect to
     * @see #drain(ForgeDirection, int, boolean) but with added filtering for fluid type.
     */
    @Override
    public FluidStack drain(@Nullable ForgeDirection from, FluidStack resource, boolean doDrain){
        return drain(from, resource.amount,doDrain);
    }
    /**returns the amount of fluid in the tank. 0 if the tank is null*/
    public int getTankAmount(){
        if(worldObj.isRemote){
            return this.dataWatcher.getWatchableObjectInt(20);
        } else {
            return fluidTank != null ? fluidTank.amount : 0;
        }
    }

    /**checks if the fluid can be put into the tank, and if doFill is true, will actually attempt to add the fluid to the tank.
     * @return the amount of fluid that was or could be put into the tank.*/
    @Override
    public int fill(@Nullable ForgeDirection from, FluidStack resource, boolean doFill){
        //if the tank has no capacity, or the filter prevents this fluid, or the fluid in the tank already isn't the same.
        if (resource==null || !canFill(null, resource.getFluid())){
            return 0;
        }
        int amountToFill;
        //if the tank is null, figure out how much fluid to add based on tank capacity.
        if (fluidTank == null){
            amountToFill = getTankCapacity() < resource.amount?getTankCapacity():resource.amount;
            if (doFill) {
                fluidTank = new FluidStack(resource.getFluid(), amountToFill);
                updateWatchers=true;
            }
            //if the tank isn't null, we also have to check the amount already in the tank
        } else {
            amountToFill = getTankCapacity() -getTankAmount() < resource.amount?getTankCapacity()-getTankAmount():resource.amount;
            if (doFill){
                fluidTank.amount += amountToFill;
                updateWatchers=true;
            }
        }
        return amountToFill;
    }
    /**returns the list of fluid tanks and their capacity.*/
    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from){
        return getTankCapacity()<1?null:new FluidTankInfo[]{new FluidTankInfo(fluidTank, getTankCapacity())};
    }

    /*
     * <h3> chunk management</h3>
     * small chunk management for the entity, most all of it is handled in
     * @see ChunkHandler
     */

    /**@return the chunk ticket of this entity*/
    public ForgeChunkManager.Ticket getChunkTicket(){return chunkTicket;}
    /**sets the chunk ticket of this entity to the one provided.*/
    public void setChunkTicket(ForgeChunkManager.Ticket ticket){chunkTicket = ticket;}

    /**attempts to get a ticket for chunkloading, sets the ticket's values*/
    private void requestTicket() {
        ForgeChunkManager.Ticket ticket = ForgeChunkManager.requestTicket(TrainsInMotion.instance, worldObj , ForgeChunkManager.Type.ENTITY);
        if(ticket != null) {
            ticket.bindEntity(this);
            setChunkTicket(ticket);
        }
    }

}
