package ebf.tim.entities;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.utility.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import ebf.tim.TrainsInMotion;
import ebf.tim.entities.trains.EntityBrigadelok080;
import ebf.tim.items.ItemKey;
import ebf.tim.models.tmt.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.minecraftforge.fluids.FluidStack.loadFluidStackFromNBT;
import static ebf.tim.TrainsInMotion.nullUUID;
import static ebf.tim.TrainsInMotion.proxy;
import static ebf.tim.utility.RailUtility.rotatePoint;

/**
 * <h1>Generic Rail Transport</h1>
 * this is the base for all trains and rollingstock.
 * @author Eternal Blue Flame
 */
public class GenericRailTransport extends Entity implements IEntityAdditionalSpawnData, IEntityMultiPart{

    /**
     * <h2>variables</h2>
     * isLocked is for if the owner has locked it.
     * Brake defines if the handbrake is on.
     * lamp is used for the lamp, assuming this has one.
     * colors define the skin colors in RGB format, because we plan to have multiple recolor points on the trains, dependant on skin, we need multiple sets of RGB values.
     * owner defines the UUID of the current owner (usually the player that spawns it)
     * bogie is the list of bogies this has.
     * isCreative defines whether or not it should actually remove the liquid/fuel item, this can be toggled from the GUI if the rider is in creative mode.
     * hitboxList and hitboxHandler manage the hitboxes the train has, this is mostly dealt with via getParts() and the hitbox functionality.
     * transportTicks is a simple tick count that allows us to manage functions that don't happen every tick, like fuel consumption in trains.
     * front and back define references to the train/rollingstock connected to the front and back, so that way we can better control links.
     * the front and back unloaded ID's are used as a failsafe in case the front or back connected entities aren't loaded yet.
     * destination is used for routing, railcraft and otherwise.
     * the inventory defines the item storage.
     * the key defines the ownership key item, this is used to allow other people access to the transport.
     * the vectorCache is used to initialize all the vector 3's that are used to calculate everything from movement to linking, this is so we don't have to make new variable instances, saves CPU.
     * the last part is the generic entity constructor
     */
    public boolean isLocked = false;
    public boolean brake = false;
    public LampHandler lamp = new LampHandler();
    public int[][] colors = new int[][]{{0,0,0},{0,0,0},{0,0,0}};
    private UUID owner = null;
    public List<EntityBogie> bogie = new ArrayList<EntityBogie>();
    public List<EntitySeat> seats = new ArrayList<EntitySeat>();
    public boolean isCreative = false;
    public boolean isCoupling = false;
    public List<HitboxHandler.multipartHitbox> hitboxList = new ArrayList<HitboxHandler.multipartHitbox>();
    public HitboxHandler hitboxHandler = new HitboxHandler();
    public int transportTicks =0;
    public UUID front = nullUUID;
    public UUID back = nullUUID;
    public int ownerID =0;
    public String destination ="";
    public InventoryHandler inventory = new InventoryHandler(this);
    public ItemStack key;
    private double[][] vectorCache = new double[7][3];
    public GenericRailTransport(World world){
        super(world);
    }
    public GenericRailTransport(UUID owner, World world, double xPos, double yPos, double zPos){
        super(world);

        posY = yPos;
        posX = xPos;
        posZ = zPos;

        this.owner = owner;

        key = new ItemStack(new ItemKey(owner, getItem().getUnlocalizedName()));

        setSize(1f,2);
        this.boundingBox.minX = 0;
        this.boundingBox.minY = 0;
        this.boundingBox.minZ = 0;
        this.boundingBox.maxX = 0;
        this.boundingBox.maxY = 0;
        this.boundingBox.maxZ = 0;
    }


    /**
     * <h2>Entity initialization</h2>
     * Entity init runs right before the first tick.
     * This is useful for registering the datawatchers before we actually use them.
     */
    @Override
    public void entityInit(){
        this.dataWatcher.addObject(19, 0);//owner
        this.dataWatcher.addObject(20, 0);//tankA
        this.dataWatcher.addObject(21, 0);//TankB
    }


    /**
     * <h2>base entity overrides</h2>
     * modify basic entity variables to give different uses/values.
     * collision and bounding box stuff just return the in-built stuff.
     * getParts returns the list of hitboxes so they can be treated as if they are part of this entity.
     * The positionAndRotation2 override is intended to re-calculate the position given the client data during the client tick rather than relying on server.
     */
    @Override
    public boolean canBePushed() {return false;}
    @Override
    public World func_82194_d(){return worldObj;}
    @Override
    public boolean attackEntityFromPart(EntityDragonPart part, DamageSource damageSource, float damage){
        return HitboxHandler.AttackEvent(this,damageSource,damage);
    }
    @Override
    public Entity[] getParts(){return hitboxList.toArray(new HitboxHandler.multipartHitbox[hitboxList.size()]);}
    @Override
    public AxisAlignedBB getBoundingBox(){return boundingBox;}
    @Override
    public AxisAlignedBB getCollisionBox(Entity collidedWith){return boundingBox;}
    @Override
    public boolean canBeCollidedWith() {return false;}
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_) {
        int bogieSize = bogie.size()-1;
        if (bogieSize>0){
            if ((bogie.get(bogieSize).boundingBox.minY + bogie.get(0).boundingBox.minY) != 0) {
                setPosition(
                        (bogie.get(bogieSize).posX + bogie.get(0).posX) * 0.5D,
                        ((bogie.get(bogieSize).posY + bogie.get(0).posY) * 0.5D),
                        (bogie.get(bogieSize).posZ + bogie.get(0).posZ) * 0.5D);
                } else {
                this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
            }
        }
        this.setRotation(p_70056_7_, p_70056_8_);
    }


    /**
     * <h3>add bogies</h3>
     * this is called by the bogies and seats on their spawn to add them to this entity's list of bogies and seats, we only do it on client because that's the only side that seems to lose track.
     * @see EntityBogie#readSpawnData(ByteBuf)
     * TODO remove addbogies in alpha 3.
     */
    @SideOnly(Side.CLIENT)
    public void addbogies(EntityBogie cart){bogie.add(cart);}
    @SideOnly(Side.CLIENT)
    public void addseats(EntitySeat cart){seats.add(cart);}

    /**
     * <h2> Data Syncing and Saving </h2>
     *
     * used for syncing the spawn data with client and server(SpawnData), and saving/loading information from world (NBT)
     * @see IEntityAdditionalSpawnData
     * @see NBTTagCompound
     * the spawn data will make sure variables that don't usually sync on spawn, like from the item, get synced.
     * the NBT will make sure that variables save to the world so it will be there next time you load the world up.
     */
    @Override
    public void readSpawnData(ByteBuf additionalData) {
        brake = additionalData.readBoolean();
        isCoupling = additionalData.readBoolean();
        isCreative = additionalData.readBoolean();
        lamp.isOn = additionalData.readBoolean();
        owner = new UUID(additionalData.readLong(), additionalData.readLong());
        key = new ItemStack(new ItemKey(owner, getItem().getUnlocalizedName()));
        rotationYaw = additionalData.readFloat();
    }
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeBoolean(brake);
        buffer.writeBoolean(isCoupling);
        buffer.writeBoolean(isCreative);
        buffer.writeBoolean(lamp.isOn);
        buffer.writeLong(owner.getMostSignificantBits());
        buffer.writeLong(owner.getLeastSignificantBits());
        buffer.writeFloat(rotationYaw);
    }
    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        isLocked = tag.getBoolean("extended.islocked");
        lamp.isOn = tag.getBoolean("extended.lamp");
        lamp.X = tag.getInteger("extended.lamp.x");
        lamp.Y = tag.getInteger("extended.lamp.y");
        lamp.Z = tag.getInteger("extended.lamp.z");
        isDead = tag.getBoolean("extended.dead");
        isCoupling = tag.getBoolean("extended.coupling");
        //load links
        front = new UUID(tag.getLong("extended.front.most"), tag.getLong("extended.front.least"));
        back = new UUID(tag.getLong("extended.back.most"), tag.getLong("extended.back.least"));
        //more bools
        isCreative = tag.getBoolean("extended.creative");
        brake = tag.getBoolean("extended.handbrake");
        //load owner
        owner = new UUID(tag.getLong("extended.ownerm"),tag.getLong("extended.ownerl"));
//        key.readFromNBT(tag);
        //load tanks
        if (getTank() != null) {
            FluidStack tankA = loadFluidStackFromNBT(tag);
            if (tankA.amount != 0) {
                getTank().addFluid(tankA.getFluid(), tankA.amount, true, this);
            }
            FluidStack tankB = loadFluidStackFromNBT(tag);
            if (tankB.amount != 0) {
                getTank().addFluid(tankB.getFluid(), tankB.amount, false, this);
            }
        }

        inventory.readNBT(tag, "items");

    }
    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        tag.setBoolean("extended.islocked", isLocked);
        tag.setBoolean("extended.lamp", lamp.isOn);
        tag.setInteger("extended.lamp.x", lamp.X);
        tag.setInteger("extended.lamp.y", lamp.Y);
        tag.setInteger("extended.lamp.z", lamp.Z);
        tag.setBoolean("extended.dead", isDead);
        tag.setBoolean("extended.coupling", isCoupling);
        //front and back bogies
        if (front != null){
            tag.setLong("extended.front.most", front.getMostSignificantBits());
            tag.setLong("extended.front.least", front.getLeastSignificantBits());
        } else {
            tag.setLong("extended.front.most", 0);
            tag.setLong("extended.front.least", 0);
        }
        if (back != null){
            tag.setLong("extended.back.most", front.getMostSignificantBits());
            tag.setLong("extended.back.least", front.getLeastSignificantBits());
        } else {
            tag.setLong("extended.back.most", 0);
            tag.setLong("extended.back.least", 0);
        }
        //more bools
        tag.setBoolean("extended.creative", isCreative);
        tag.setBoolean("extended.handbrake", brake);
        //owner
        tag.setLong("extended.ownerm", owner.getMostSignificantBits());
        tag.setLong("extended.ownerl", owner.getLeastSignificantBits());
//        key.writeToNBT(tag);


        //tanks
        if (getTank() != null) {
            if (getTank().getTank(true).getFluid() != null) {
                getTank().getTank(true).getFluid().writeToNBT(tag);
            } else {
                new FluidStack(FluidRegistry.WATER, 0).writeToNBT(tag);
            }

            if (getTank().getTank(false).getFluid() != null) {
                getTank().getTank(false).getFluid().writeToNBT(tag);
            } else {
                new FluidStack(FluidRegistry.WATER, 0).writeToNBT(tag);
            }
        }
        tag.setTag("items", inventory.writeNBT());
    }


    /**
     * <h3>Add velocity to bogies</h3>
     * used to add motion to every bogie at once with a more-simple call.
     * TODO: remove this for alpha 3.
     */
    @Override
    public void addVelocity(double velocityX, double velocityY, double velocityZ){
        //handle movement for trains, this will likely need to be different for rollingstock.
            for (EntityBogie currentBogie : bogie) {
                //motion = rotatePoint(new double[]{this.processMovement(currentBogie.motionX, currentBogie.motionZ), (float) motionY, 0.0f}, 0.0f, rotationYaw, 0.0f);
                currentBogie.cartVelocityX = currentBogie.motionX += velocityX;
                currentBogie.cartVelocityY = currentBogie.motionY += velocityY;
                currentBogie.cartVelocityZ = currentBogie.motionZ += velocityZ;
                currentBogie.isAirBorne = true;
            }
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

        bogie.remove(null);
        seats.remove(null);
        hitboxList.remove(null);

        //be sure bogies exist
        int bogieSize = bogie.size() - 1;
        //always be sure the bogies exist on client and server.
        if (!worldObj.isRemote && bogieSize < 1) {
            for (double pos : getBogieOffsets()) {
                vectorCache[1][0] = pos;
                vectorCache[0] = RailUtility.rotatePoint(vectorCache[1],rotationPitch, rotationYaw,0);
                EntityBogie spawnBogie = new EntityBogie(worldObj, posX + vectorCache[0][0], posY + vectorCache[0][1], posZ + vectorCache[0][2], getEntityId());
                worldObj.spawnEntityInWorld(spawnBogie);
                bogie.add(spawnBogie);
            }
            if (getRiderOffsets() != null) {
                for (int i = 0; i < getRiderOffsets().length - 1; i++) {
                    EntitySeat seat = new EntitySeat(worldObj, posX, posY, posZ, getEntityId(), i);
                    worldObj.spawnEntityInWorld(seat);
                    seats.add(seat);
                }
            }
            bogieSize = bogie.size()-1;
        }

        /**
         *check if the bogies exist, because they may not yet, and if they do, check if they are actually moving or colliding.
         * no point in processing movement if they aren't moving or if the train hit something.
         * if it is clear however, then we need to add velocity to the bogies based on the current state of the train's speed and fuel, and reposition the train.
         * but either way we have to position the bogies around the train, just to be sure they don't accidentally fly off at some point.
         *
         */
        if (bogieSize>0){

            boolean collision = hitboxHandler.getCollision(this);
            //handle movement.
            if (!collision) {
            for (EntityBogie currentBogie : bogie) {
                    if (currentBogie != null) {
                        if (brake) {
                            currentBogie.setVelocity(currentBogie.cartVelocityX * 0.8d, currentBogie.cartVelocityY, currentBogie.cartVelocityZ * 0.8d);
                        }
                        currentBogie.minecartMove();
                    }
                }
            }

            //position this
            if ((bogie.get(bogieSize).boundingBox.minY + bogie.get(0).boundingBox.minY) != 0) {
                setPosition(
                        ((bogie.get(bogieSize).posX + bogie.get(0).posX) * 0.5D),
                        ((bogie.get(bogieSize).posY + bogie.get(0).posY) * 0.5D),
                        ((bogie.get(bogieSize).posZ + bogie.get(0).posZ) * 0.5D));
            }


            setRotation((float)Math.toDegrees(Math.atan2(
                    bogie.get(bogieSize).posZ - bogie.get(0).posZ,
                    bogie.get(bogieSize).posX - bogie.get(0).posX)),
                    MathHelper.floor_double(Math.acos(bogie.get(0).posY / bogie.get(bogieSize).posY)));


            if (transportTicks %2 ==0) {
                //align bogies
                for (int i = 0; i < bogie.size(); i++) {
                    vectorCache[1][0]=getBogieOffsets().get(i);
                    vectorCache[0] = rotatePoint(vectorCache[1], rotationPitch, rotationYaw, 0.0f);
                    bogie.get(i).setPosition(vectorCache[0][0] + posX, bogie.get(i).posY, vectorCache[0][2] + posZ);
                }
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
        if (!worldObj.isRemote && transportTicks %10==0){
            Entity player = proxy.getEntityFromUuid(owner);
            if (player!= null) {
                ownerID = player.getEntityId();
                this.dataWatcher.updateObject(19,ownerID);
            }
        }

        /**
         * be sure the client proxy has a reference to this so the lamps can be updated, and then every other tick, attempt to update the lamp position if it's necessary.
         */
        if (bogie.size() > 1 && posX+posY+posZ != 0.0D && !isDead) {
            if (worldObj.isRemote && ClientProxy.EnableLights && !ClientProxy.carts.contains(this)) {
                ClientProxy.carts.add(this);
            }
            if (lamp.Y >1 && worldObj.isRemote && transportTicks %2 ==1){
                vectorCache[0][0] =this.posX + getLampOffset().xCoord;
                vectorCache[0][1] =this.posY + getLampOffset().yCoord;
                vectorCache[0][2] =this.posZ + getLampOffset().zCoord;
                lamp.ShouldUpdate(worldObj, RailUtility.rotatePoint(vectorCache[0], rotationPitch, rotationYaw, 0));
            }
            if (transportTicks>20){
                transportTicks = 1;
            }
            transportTicks++;
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
    public void manageLinks(){
        if(!worldObj.isRemote) {
            //manage the front link
            if (front != nullUUID) {
                GenericRailTransport frontLink = CommonProxy.getTransportFromUuid(front);
                if (frontLink != null) {
                    //to here
                    vectorCache[3][0] = getHitboxPositions()[0]-1;
                    vectorCache[4] = rotatePoint(vectorCache[3], 0, rotationYaw, 0);
                    vectorCache[4][0] += posX;
                    vectorCache[4][2] += posZ;
                    //from here
                    vectorCache[5][0]=frontLink.getHitboxPositions()[(frontLink.back == this.getPersistentID())?(frontLink.getHitboxPositions().length - 1):0];
                    vectorCache[6] = rotatePoint(vectorCache[5], 0, frontLink.rotationYaw, 0);
                    vectorCache[6][0] += frontLink.posX;
                    vectorCache[6][2] += frontLink.posZ;
                    this.addVelocity(-(vectorCache[4][0] - vectorCache[6][0]) * 0.05,0, -(vectorCache[4][2] - vectorCache[6][2]) * 0.05);
                }
            } else if (isCoupling) {
                vectorCache[3][0] = getHitboxPositions()[0]-2;
                vectorCache[4] = rotatePoint(vectorCache[3], 0, rotationYaw, 0);
                vectorCache[4][0] += posX;
                vectorCache[4][1] += posY;
                vectorCache[4][2] += posZ;
                List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
                        AxisAlignedBB.getBoundingBox(vectorCache[4][0] - 0.5d, vectorCache[4][1], vectorCache[4][2] - 0.5d,
                                vectorCache[4][0] + 0.5d, vectorCache[4][1] + 2, vectorCache[4][2] +0.5d));

                if (list.size() > 0) {
                    for (Object entity : list) {
                        if (entity instanceof HitboxHandler.multipartHitbox && !hitboxList.contains(entity) && ((GenericRailTransport)worldObj.getEntityByID(((HitboxHandler.multipartHitbox) entity).parent.getEntityId())).isCoupling) {
                            front = ((HitboxHandler.multipartHitbox) entity).parent.getPersistentID();
                            System.out.println(getEntityId() + " : rollingstock front linked : ");
                        }
                    }
                }
            }
            //Manage the back link
            if (back != nullUUID) {
                GenericRailTransport backLink = CommonProxy.getTransportFromUuid(back);
                if (backLink != null) {
                    //to here
                    vectorCache[5][0]=getHitboxPositions()[getHitboxPositions().length-1]+1;
                    vectorCache[6] = rotatePoint(vectorCache[5], 0, rotationYaw, 0);
                    vectorCache[6][0] += posX;
                    vectorCache[6][2] += posZ;
                    //from here
                    vectorCache[5][0]=backLink.getHitboxPositions()[(backLink.back == this.getPersistentID())?(backLink.getHitboxPositions().length - 1):0];
                    vectorCache[6] = rotatePoint(vectorCache[5], 0, backLink.rotationYaw, 0);
                    vectorCache[6][0] += backLink.posX;
                    vectorCache[6][2] += backLink.posZ;
                    this.addVelocity(-(vectorCache[6][0] - vectorCache[6][0]) * 0.05,0, -(vectorCache[6][2] -vectorCache[6][2]) * 0.05);
                }
            } else if (isCoupling) {
                vectorCache[3][0] = getHitboxPositions()[getHitboxPositions().length-1] + 2;
                vectorCache[4] = rotatePoint(vectorCache[3], 0, rotationYaw, 0);
                vectorCache[4][0] += posX;
                vectorCache[4][1] += posY;
                vectorCache[4][2] += posZ;
                List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
                        AxisAlignedBB.getBoundingBox(vectorCache[4][0] - 0.5d, vectorCache[4][1], vectorCache[4][2] - 0.5d,
                                vectorCache[4][0] + 0.5d, vectorCache[4][1] + 2, vectorCache[4][2] +0.5d));

                if (list.size() > 0) {
                    for (Object entity : list) {
                        if (entity instanceof HitboxHandler.multipartHitbox && !hitboxList.contains(entity) && ((GenericRailTransport)worldObj.getEntityByID(((HitboxHandler.multipartHitbox) entity).parent.getEntityId())).isCoupling) {
                            back = ((HitboxHandler.multipartHitbox) entity).parent.getPersistentID();
                            System.out.println(getEntityId() + " : rollingstock back linked : ");
                        }
                    }
                }
            }
        }
    }


    /**
     * <h2>Boolean switches</h2>
     * Toggles the defined booleans, this is mostly just used for user input like key press and GUI buttons.
     * Potentially unnecessary, but theoretically more reliable than direct modification of the variables from outside the class.
     */
    public boolean toggleBool(int index){
        switch (index){
            case 4:{
                brake = !brake;
                return true;
            }case 5:{
                lamp.isOn = !lamp.isOn;
                return true;
            }case 6:{
                isLocked = ! isLocked;
                return true;
            }case 7:{
                isCoupling = !isCoupling;
                return true;
            }case 10:{
                isCreative = !isCreative;
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
        if (isLocked && player.inventory.hasItem(key.getItem())) {
            return true;
        }
        //if a ticket is needed like for passenger cars
        if(isLocked && getRiderOffsets().length>1){
            return player.inventory.hasItem(inventory.getTicketSlot().getItem());
        }

        //all else fails, just return if this is locked.
        return !isLocked;

    }



    /**
     * <h2>Inherited variables</h2>
     * these functions are overridden by classes that extend this so that way the values can be changed indirectly.
     * @see EntityBrigadelok080 for more information
     */
    public List<Double> getBogieOffsets(){return new ArrayList<Double>();}
    public TrainsInMotion.transportTypes getType(){return null;}
    public double[][] getRiderOffsets(){return new double[][]{{0,0}};}
    public float[] getHitboxPositions(){return new float[]{-1,0,1};}
    public Item getItem(){return null;}
    public TrainsInMotion.inventorySizes getInventorySize(){return TrainsInMotion.inventorySizes.THREExTHREE;}
    public LiquidManager getTank(){return null;}
    public Vec3d getLampOffset(){return new Vec3d(0,0,0);}
    public float getPistonOffset(){return 0;}
    public float[][] getSmokeOffset(){return new float[][]{{0,0,0,255}};}


    /**
     * <h2>Datawatchers</h2>
     * Similar to packs this is used to transfer values of various things between client and server.
     */

    public int getTankFluid(boolean isFirstTank){
        if (isFirstTank) {
            return this.dataWatcher.getWatchableObjectInt(20);
        } else{
            return this.dataWatcher.getWatchableObjectInt(21);
        }
    }
    public int getOwnerID(){
        return this.dataWatcher.getWatchableObjectInt(19);
    }

}
