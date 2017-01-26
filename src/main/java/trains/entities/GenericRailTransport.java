package trains.entities;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import trains.TrainsInMotion;
import trains.entities.trains.EntityBrigadelok080;
import trains.models.tmt.Vec3d;
import trains.utility.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.minecraftforge.fluids.FluidStack.loadFluidStackFromNBT;
import static trains.utility.RailUtility.rotatePoint;

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
     * bogieXYZ is the list of known positions for the bogies, this is mostly used to keep track of where the bogies are supposed to be via NBT.
     * motion is the vector angle that the train is facing, we only initialize it here, so we don't need to initialize it every tick.
     * isCreative defines whether or not it should actually remove the liquid/fuel item, this can be toggled from the GUI if the rider is in creative mode.
     * hitboxList and hitboxHandler manage the hitboxes the train has, this is mostly dealt with via getParts() and the hitbox functionality.
     * transportTicks is a simple tick count that allows us to manage functions that don't happen every tick, like fuel consumption in trains.
     * front and back define references to the train/rollingstock connected to the front and back, so that way we can better control links.
     * the front and back unloaded ID's are used as a failsafe in case the front or back connected entities aren't loaded yet.
     * destination is used for routing, railcraft and otherwise.
     * riddenByEntities defines the entities riding this.
     * the last part is the generic entity constructor
     */
    public LiquidManager tanks = new LiquidManager(0,0, new Fluid[]{FluidRegistry.WATER},new Fluid[]{FluidRegistry.WATER},true,true);
    public boolean isLocked = false;
    public boolean brake = false;
    public LampHandler lamp = new LampHandler();
    public int[][] colors = new int[][]{{0,0,0},{0,0,0},{0,0,0}};
    public UUID owner = null;
    public List<EntityBogie> bogie = new ArrayList<EntityBogie>();
    public List<double[]> bogieXYZ = new ArrayList<double[]>();
    public double[] motion = new double[]{0,0,0};
    public boolean isCreative = false;
    public boolean isCoupling = true;//false;
    public List<HitboxHandler.multipartHitbox> hitboxList = new ArrayList<HitboxHandler.multipartHitbox>();
    public HitboxHandler hitboxHandler = new HitboxHandler();
    public int transportTicks =0;
    public GenericRailTransport front;
    public GenericRailTransport back;
    public int frontUnloadedID =0;
    public int backUnloadedID =0;
    public String destination ="";
    public List<Entity> riddenByEntities = new ArrayList<Entity>();
    public GenericRailTransport(World world){
        super(world);
        tanks = getTank();
    }



    /**
     * <h2>base entity overrides</h2>
     * modify basic entity variables to give different uses/values.
     * entity init runs right before the first tick, but we don't use this.
     * collision and bounding box stuff just return the in-built stuff.
     * getParts returns the list of hitboxes so they can be treated as if they are part of this entity.
     * The positionAndRotation2 override is intended to do the same as the super, except for giving a Y offset on collision, we skip that similar to EntityMinecart.
     */
    @Override
    public boolean canBePushed()
    {
        return false;
    }
    @Override
    public void entityInit(){}
    @Override
    public World func_82194_d(){return worldObj;}
    @Override
    public boolean attackEntityFromPart(EntityDragonPart part, DamageSource damageSource, float damage){
        return HitboxHandler.AttackEvent(this,damageSource,damage);
    }
    @Override
    public Entity[] getParts(){
        return hitboxList.toArray(new HitboxHandler.multipartHitbox[hitboxList.size()]);
    }
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
                setRotation((float)Math.toDegrees(Math.atan2(
                    bogie.get(bogieSize).posZ - bogie.get(0).posZ,
                    bogie.get(bogieSize).posX - bogie.get(0).posX)),
                    MathHelper.floor_double(Math.acos(bogie.get(0).posY / bogie.get(bogieSize).posY)));

        } else {
            this.setRotation(p_70056_7_, p_70056_8_);
        }
    }


    /**
     * <h3>add bogies</h3>
     * this is called by the bogie on its spawn to add it to this entity's list of bogies, we only do it on client because thats the only side that seems to lose track.
     * @see EntityBogie#readSpawnData(ByteBuf)
     */
    @SideOnly(Side.CLIENT)
    public void addbogies(EntityBogie cart){
        bogie.add(cart);
    }

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
        //we loop using the offset double length because we expect bogieXYZ to be null.
        for (int i=0; i<getBogieOffsets().size(); i++){
            bogieXYZ.add(new double[]{additionalData.readDouble(), additionalData.readDouble(), additionalData.readDouble()});
        }

    }
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeBoolean(brake);
        buffer.writeBoolean(isCoupling);
        buffer.writeBoolean(isCreative);
        buffer.writeBoolean(lamp.isOn);
        buffer.writeLong(owner.getMostSignificantBits());
        buffer.writeLong(owner.getLeastSignificantBits());
        for (double[] xyz : bogieXYZ) {
            buffer.writeDouble(xyz[0]);
            buffer.writeDouble(xyz[1]);
            buffer.writeDouble(xyz[2]);
        }
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
        //load front link
        int id = tag.getInteger("extended.front");
        if(id !=0){
            Entity getFront = worldObj.getEntityByID(id);
            if (getFront instanceof GenericRailTransport){
                front = (GenericRailTransport) getFront;
            } else if (getFront == null){
                frontUnloadedID = id;
            }
        }
        //load back link
        id = tag.getInteger("extended.back");
        if(id !=0){
            Entity getBack = worldObj.getEntityByID(id);
            if (getBack instanceof GenericRailTransport){
                back = (GenericRailTransport) getBack;
            } else if (getBack == null){
                backUnloadedID = id;
            }
        }
        //more bools
        isCreative = tag.getBoolean("extended.creative");
        brake = tag.getBoolean("extended.handbrake");
        //load owner
        owner = new UUID(tag.getLong("extended.ownerm"),tag.getLong("extended.ownerl"));

        //load tanks
        if (tanks != null) {
            FluidStack tankA = loadFluidStackFromNBT(tag);
            if (tankA.amount != 0) {
                tanks.addFluid(tankA.getFluid(), tankA.amount, true);
            }
            FluidStack tankB = loadFluidStackFromNBT(tag);
            if (tankB.amount != 0) {
                tanks.addFluid(tankB.getFluid(), tankB.amount, false);
            }
        }

        //riders
        NBTTagCompound nbtRiders = tag.getCompoundTag("extended.riders");
        for (int iteration=0; iteration< getRiderOffsets().length-1; iteration++){
            int rider = nbtRiders.getInteger("rider_" + iteration);
            if (rider != 0 && worldObj.getEntityByID(rider) != null){
                if (riddenByEntities.size()-1 < iteration){
                    riddenByEntities.add(worldObj.getEntityByID(rider));
                } else {
                    riddenByEntities.set(iteration, worldObj.getEntityByID(rider));
                }
            }
        }

        //read through the bogie positions
        NBTTagCompound nbtBogiePos = tag.getCompoundTag("extended.bogies");
        for (int i=0; i< getBogieOffsets().size(); i++) {
            double[] pos = new double[]{nbtBogiePos.getDouble("bogieindex.a." + i), nbtBogiePos.getDouble("bogieindex.b." + i), nbtBogiePos.getDouble("bogieindex.c." + i)};
            if (pos[0] !=0 && pos[1] !=0 && pos[2] !=0) {
                bogieXYZ.add(pos);
            }

        }

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
            tag.setInteger("extended.front", front.getEntityId());
        } else {
            tag.setInteger("extended.front", 0);
        }
        if (back != null){
            tag.setInteger("extended.back", back.getEntityId());
        } else {
            tag.setInteger("extended.back", 0);
        }
        //more bools
        tag.setBoolean("extended.creative", isCreative);
        tag.setBoolean("extended.handbrake", brake);
        //owner
        tag.setLong("extended.ownerm", owner.getMostSignificantBits());
        tag.setLong("extended.ownerl", owner.getLeastSignificantBits());

        //tanks
        if (tanks != null) {
            if (tanks.getTank(true).getFluid() != null) {
                tanks.getTank(true).getFluid().writeToNBT(tag);
            } else {
                new FluidStack(FluidRegistry.WATER, 0).writeToNBT(tag);
            }

            if (tanks.getTank(false).getFluid() != null) {
                tanks.getTank(false).getFluid().writeToNBT(tag);
            } else {
                new FluidStack(FluidRegistry.WATER, 0).writeToNBT(tag);
            }
        }

        //riders
        NBTTagCompound nbtRiders = new NBTTagCompound();
        for (int iteration=0; iteration< getRiderOffsets().length-1; iteration++){
            if (riddenByEntities.size()-1 <iteration || riddenByEntities.get(iteration) == null){
                nbtRiders.setInteger("rider_" + iteration, 0);
            } else {
                nbtRiders.setInteger("rider_" + iteration, riddenByEntities.get(iteration).getEntityId());
            }
        }
        tag.setTag("extended.riders", nbtRiders);

        //write the list of bogies
        NBTTagCompound nbtbogies = new NBTTagCompound();

        for (int i = 0; i < getBogieOffsets().size(); ++i) {
            if (bogieXYZ.get(i) != null) {
                nbtbogies.setDouble("bogieindex.a." + i,bogieXYZ.get(i)[0]);
                nbtbogies.setDouble("bogieindex.b." + i,bogieXYZ.get(i)[1]);
                nbtbogies.setDouble("bogieindex.c." + i,bogieXYZ.get(i)[2]);
            } else {
                nbtbogies.setDouble("bogieindex.a." + i,0);
                nbtbogies.setDouble("bogieindex.b." + i,0);
                nbtbogies.setDouble("bogieindex.c." + i,0);
            }
        }
        tag.setTag("extended.bogies", nbtbogies);
    }



    public void addVelocity(double velocityX, double velocityZ){
        //handle movement for trains, this will likely need to be different for rollingstock.
            for (EntityBogie currentBogie : bogie) {
                //motion = rotatePoint(new double[]{this.processMovement(currentBogie.motionX, currentBogie.motionZ), (float) motionY, 0.0f}, 0.0f, rotationYaw, 0.0f);
                motion[0] = velocityX;
                motion[2] = velocityZ;
                motion[1] = currentBogie.motionY;
                currentBogie.setVelocity(motion[0], motion[1], motion[2]);
                currentBogie.minecartMove();
            }
    }


    /**
     * <h2> on entity update </h2>
     *
     * defines what should be done every tick
     * used for:
     * managing the list of bogies which are used for defining position and rotation, respawning them if they disappear.
     * managing speed, acceleration. and direction.
     * managing rotationYaw and rotationPitch.
     * being sure the train is listed in the main class (for lighting management).
     * @see ClientProxy#onTick(TickEvent.ClientTickEvent)
     *
     */
    @Override
    public void onUpdate() {
        //if the cart has fallen out of the map, destroy it.
        if (posY < -64.0D & isDead){
            worldObj.removeEntity(this);
        }
        //be sure bogies exist
        int xyzSize = bogieXYZ.size()-1;
        if (xyzSize > 0) {
            int bogieSize = bogie.size() - 1;
            //always be sure the bogies exist on client and server.
            if (!worldObj.isRemote && bogieSize < 1) {
                for (double[] pos : bogieXYZ) {
                    //it should never be possible for bogieXYZ to be null unless there is severe server data corruption.
                    EntityBogie spawnBogie = new EntityBogie(worldObj, pos[0], pos[1], pos[2], getEntityId());
                    worldObj.spawnEntityInWorld(spawnBogie);
                    bogie.add(spawnBogie);
                }
                bogieSize = xyzSize;
            }

            /**
             *check if the bogies exist, because they may not yet, and if they do, check if they are actually moving or colliding.
             * no point in processing movement if they aren't moving or if the train hit something.
             * if it is clear however, then we need to add velocity to the bogies based on the current state of the train's speed and fuel, and reposition the train.
             * but either way we have to position the bogies around the train, just to be sure they don't accidentally fly off at some point.
             *
             */
            if (bogieSize>0){

                boolean collision = !hitboxHandler.getCollision(this);
                //handle movement for trains, this will likely need to be different for rollingstock.
                for (EntityBogie currentBogie : bogie) {
                    if (collision) {
                        if (currentBogie != null) {
                            motion = rotatePoint(new double[]{processMovement(currentBogie), (float) motionY, 0}, 0.0f, currentBogie.rotationYaw, 0.0f);
                            currentBogie.setVelocity(motion[0], motion[1], motion[2]);
                            currentBogie.minecartMove();
                        }
                    } else {
                        motion = new double[]{0d, 0d, 0d};
                    }
                }

                //position this
                if ((bogie.get(bogieSize).boundingBox.minY + bogie.get(0).boundingBox.minY) != 0) {
                    setPosition(
                            (bogie.get(bogieSize).posX + bogie.get(0).posX) * 0.5D,
                            ((bogie.get(bogieSize).posY + bogie.get(0).posY) * 0.5D),
                            (bogie.get(bogieSize).posZ + bogie.get(0).posZ) * 0.5D);
                }


                setRotation((float)Math.toDegrees(Math.atan2(
                        bogie.get(bogieSize).posZ - bogie.get(0).posZ,
                        bogie.get(bogieSize).posX - bogie.get(0).posX)),
                        MathHelper.floor_double(Math.acos(bogie.get(0).posY / bogie.get(bogieSize).posY)));

                if (brake){
                    for (EntityBogie entityBogie : bogie){
                        entityBogie.setVelocity(entityBogie.cartVelocityX * 0.8d, entityBogie.cartVelocityY, entityBogie.cartVelocityZ * 0.8d);
                    }
                }

                if (transportTicks %2 ==0) {
                    //align bogies
                    for (int i = 0; i < bogie.size(); ) {
                        double[] var = rotatePoint(new double[]{getBogieOffsets().get(i).doubleValue(), 0.0f, 0.0f}, 0.0f, rotationYaw, 0.0f);
                        bogie.get(i).setPosition(var[0] + posX, bogie.get(i).posY, var[2] + posZ);
                        bogieXYZ.set(i, new double[]{bogie.get(i).posX, bogie.get(i).posY, bogie.get(i).posZ});
                        i++;
                    }
                }
                manageLinks();

            }

        }

        /**
         * be sure the client proxy has a reference to this so the lamps can be updated, and then every other tick, attempt to update the lamp position if it's necessary.
         */
        if (xyzSize > 0 && bogieXYZ.get(0)[0] + bogieXYZ.get(0)[1] + bogieXYZ.get(0)[2] != 0.0D) {
            if (worldObj.isRemote && !ClientProxy.carts.contains(this)) {
                ClientProxy.carts.add(this);
            }
            if (worldObj.isRemote && transportTicks %2 ==1){
                lamp.ShouldUpdate(worldObj, RailUtility.rotatePoint(new double[]{this.posX + getLampOffset().xCoord ,this.posY + getLampOffset().yCoord, this.posZ + getLampOffset().zCoord}, rotationPitch, rotationYaw, 0));
            }
            if (transportTicks>20){
                transportTicks = 1;
            }
            transportTicks++;
        }
    }

    public double processMovement(EntityBogie entityBogie){

        double X =0;
        if (MathHelper.floor_double(entityBogie.motionX) !=0){
            X = entityBogie.motionX;
        } else {
            X = entityBogie.motionZ;
        }

        return X * 0.9f;
    }


    /**
     * <h2>Rider offset</h2>
     * this runs every tick to be sure the riders are in the correct positions.
     */
    @Override
    public void updateRiderPosition() {
        super.updateRiderPosition();
        for(int i =0; i< getRiderOffsets().length; i++) {

            if (i>= riddenByEntities.size()){
                return;
            }

            double[] riderOffset = rotatePoint(new double[]{getRiderOffsets()[i][0], getRiderOffsets()[i][1], getRiderOffsets()[i][2]}, rotationPitch, rotationYaw, 0);


            if (riddenByEntities.get(i) != null){
                this.riddenByEntities.get(i).setPosition(riderOffset[0]+this.posX, riderOffset[1]+this.posY, riderOffset[2]+this.posZ);
            }
        }
    }








    public void manageLinks(){
        if(!worldObj.isRemote) {


            if (frontUnloadedID !=0 && worldObj.getEntityByID(frontUnloadedID) instanceof GenericRailTransport){
                front = (GenericRailTransport) worldObj.getEntityByID(frontUnloadedID);
                frontUnloadedID =0;
            }
            if (backUnloadedID !=0 && worldObj.getEntityByID(backUnloadedID) instanceof GenericRailTransport){
                back = (GenericRailTransport) worldObj.getEntityByID(backUnloadedID);
                backUnloadedID =0;
            }



            if (front != null) {

                double[] fromHere = rotatePoint(new double[]{getHitboxPositions()[0]-2.65, 0, 0}, 0, rotationYaw, 0);
                double[] toHere;
                fromHere[0] += posX;
                fromHere[2] += posZ;
                if (front.back == this) {
                    toHere = rotatePoint(new double[]{front.getHitboxPositions()[front.getHitboxPositions().length-1]-1.5, 0, 0}, 0, front.rotationYaw, 0);
                    toHere[0] += front.posX;
                    toHere[2] += front.posZ;
                } else {
                    toHere = rotatePoint(new double[]{front.getHitboxPositions()[0]+1.5, 0, 0}, 0, front.rotationYaw, 0);
                    toHere[0] += front.posX;
                    toHere[2] += front.posZ;
                }
                this.addVelocity(-(fromHere[0] - toHere[0])*0.1, -(fromHere[2] - toHere[2])*0.1);

            } else if (isCoupling) {
                double[] frontCheck = rotatePoint(new double[]{getHitboxPositions()[0] - 2.5, 0, 0}, 0, rotationYaw, 0);
                frontCheck[0] +=posX;
                frontCheck[1] +=posY;
                frontCheck[2] +=posZ;
                List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
                        AxisAlignedBB.getBoundingBox(frontCheck[0] - 0.5d, frontCheck[1], frontCheck[2] - 0.5d,
                                frontCheck[0] + 0.5d, frontCheck[1] + 2, frontCheck[2] +0.5d));

                if (list.size() > 0) {
                    for (Object entity : list) {
                        if (entity instanceof HitboxHandler.multipartHitbox && !hitboxList.contains(entity) && ((HitboxHandler.multipartHitbox) entity).parent.isCoupling) {
                            front = ((HitboxHandler.multipartHitbox) entity).parent;
                            System.out.println(getEntityId() + " : front linked : " + worldObj.isRemote);
                        }
                    }
                }


            }
            if (back != null) {

                double[] fromHere = rotatePoint(new double[]{getHitboxPositions()[getHitboxPositions().length-1]+2.65, 0, 0}, 0, rotationYaw, 0);
                double[] toHere;
                fromHere[0] += posX;
                fromHere[2] += posZ;
                if (back.back == this) {
                    toHere = rotatePoint(new double[]{back.getHitboxPositions()[back.getHitboxPositions().length-1]-1.5, 0, 0}, 0, back.rotationYaw, 0);
                    toHere[0] += back.posX;
                    toHere[2] += back.posZ;
                } else {
                    toHere = rotatePoint(new double[]{back.getHitboxPositions()[0]+1.5, 0, 0}, 0, back.rotationYaw, 0);
                    toHere[0] += back.posX;
                    toHere[2] += back.posZ;
                }
                this.addVelocity(-(fromHere[0] - toHere[0])*0.1, -(fromHere[2] - toHere[2])*0.1);

            } else if (isCoupling) {
                double[] backCheck = rotatePoint(new double[]{getHitboxPositions()[getHitboxPositions().length - 1] + 2.5, 0, 0}, 0, rotationYaw, 0);
                backCheck[0] +=posX;
                backCheck[1] +=posY;
                backCheck[2] +=posZ;
                List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
                        AxisAlignedBB.getBoundingBox(backCheck[0] - 0.5d, backCheck[1], backCheck[2] - 0.5d,
                                backCheck[0] + 0.5d, backCheck[1] + 2, backCheck[2] +0.5d));

                if (list.size() > 0) {
                    for (Object entity : list) {
                        if (entity instanceof HitboxHandler.multipartHitbox && !hitboxList.contains(entity) && ((HitboxHandler.multipartHitbox) entity).parent.isCoupling) {
                            back = ((HitboxHandler.multipartHitbox) entity).parent;
                            System.out.println(getEntityId() + " : back linked : " + worldObj.isRemote);
                        }
                    }
                }


            }
        }
    }


    public boolean toggleBool(int index){
        switch (index){
            case 4:{
                brake = !brake;
                System.out.println(brake);
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


    public boolean getPermissions(EntityPlayer player, boolean driverOnly) {
        //if this requires the player to be the driver, and they aren't, just return false before we even go any further.
        if (driverOnly && player != this.riddenByEntities.get(0)){
            return false;
        }

        //be sure admins and owners can do whatever
        if (player.capabilities.isCreativeMode || owner == player.getUniqueID()) {
            return true;
        }

        //if the key is needed, like for trains and freight
        //if (isLocked && (this instanceof EntityTrainCore || getType() == TrainsInMotion.transportTypes.FREIGHT)) {
            //return player.inventory.hasItem(?KEYITEM?);
        //}
        //if a ticket is needed like for passenger cars
        //if(?TICKETITEM? != null && isLocked){
            //return player.inventory.hasItem(?TICKETITEM?);
        //}

        //all else fails, just return if this is locked.
        return !isLocked;

    }



    /**
     * <h2>Inherited variables</h2>
     * these functions are overridden by classes that extend this so that way the values can be changed indirectly.
     * @see EntityBrigadelok080 for more information
     */
    public List<Float> getBogieOffsets(){return new ArrayList<Float>();}
    public TrainsInMotion.transportTypes getType(){return null;}
    public float[][] getRiderOffsets(){return new float[][]{{0,0}};}
    public float[] getHitboxPositions(){return new float[]{-1,0,1};}
    public Item getItem(){return null;}
    public TrainsInMotion.inventorySizes getInventorySize(){return TrainsInMotion.inventorySizes.THREExTHREE;}
    public LiquidManager getTank(){return null;}
    public Vec3d getLampOffset(){return new Vec3d(0,0,0);}
    public float getPistonOffset(){return 0;}
    public float[][] getSmokeOffset(){return new float[][]{{0,0,0,255}};}

}
