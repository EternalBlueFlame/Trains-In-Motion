package ebf.tim.utility;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.entities.EntityBogie;
import ebf.tim.entities.EntitySeat;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import zoranodensha.api.structures.tracks.ITrackBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <h1>Collision and hitboxes</h1>
 * here we manage collision detection and hitboxes, most of this is basically halfway between a minecart and the ender dragon.
 * @author Eternal Blue Flame
 */
public class HitboxHandler {

    /**the list of multipart hitboxes used for collision detection*/
    public List<MultipartHitbox> hitboxList = new ArrayList<MultipartHitbox>();
    private static final Random rand = new Random();

    /**
     * <h2> basic hitbox class</h2>
     * a generic hitbox class used for adding multiple hitboxes to an entity.
     * requires the entity to override getParts() and extend IEntityMultiPart
     * The hitboxes also need to be moved every onUpdate, for trains this is handled in
     * @see HitboxHandler#getCollision(GenericRailTransport)
     */
    public class MultipartHitbox extends Entity{
        /**reference to the parent entity of this hitbox*/
        public GenericRailTransport parent;
        /**initializer for entity hitbox*/
        public MultipartHitbox(Entity host, GenericRailTransport parent, double posX, double posY , double posZ, boolean small){
            super(host.worldObj);

            this.setSize(small?0.2f:1, small?0.1f:2);


            this.parent = parent;
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            if(small){
                this.boundingBox.minX = posX - 0.045;
                this.boundingBox.minZ = posZ - 0.045;
                this.boundingBox.maxX = posX + 0.045;
                this.boundingBox.maxZ = posZ + 0.045;
            } else {
                this.boundingBox.minX = posX - 0.45;
                this.boundingBox.minZ = posZ - 0.45;
                this.boundingBox.maxX = posX + 0.45;
                this.boundingBox.maxZ = posZ + 0.45;
            }
        }
        protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {}
        protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {}
        protected void entityInit() {}

        @Override
        public AxisAlignedBB getBoundingBox(){
            return boundingBox;
        }
        @Override
        public AxisAlignedBB getCollisionBox(Entity collidedWith){
                return boundingBox;
        }
        /**checks if this hitbox should still exist or not*/
        @Override
        public void onUpdate(){}
        @Override
        public boolean canBeCollidedWith() {
            return true;
        }
        /**this function is supposed to update position without updating the bounding box position,
         * but since all we use in the first place is a bounding box, we use it as a redirect to the other method*/
        @SideOnly(Side.CLIENT)
        public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_) {
            this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
        }
        @Override
        public boolean interactFirst(EntityPlayer p_130002_1_) {
            DebugUtil.println(worldObj.isRemote, parent==null);
            return this.parent.interactFirst(p_130002_1_);
        }
        /**disables reading from NBT*/
        @Override
        public void readFromNBT(NBTTagCompound tag){}
        /**disables writing to NBT, which kills the entity on game end.*/
        @Override
        public void writeToNBT(NBTTagCompound tag){}
        @Override
        public boolean writeToNBTOptional(NBTTagCompound tagCompound){return false;}
        @Override
        public boolean writeMountToNBT(NBTTagCompound tagCompound){return false;}

        public boolean isFirst(){return parent.hitboxHandler.hitboxList.get(0) == this;}
        public boolean isLast(){return parent.hitboxHandler.hitboxList.get(parent.hitboxHandler.hitboxList.size()-1) == this;}

        public boolean isFront(){return parent.hitboxHandler.hitboxList.get(0) == this || parent.hitboxHandler.hitboxList.get(1) == this;}
        public boolean isBack(){return parent.hitboxHandler.hitboxList.get(parent.hitboxHandler.hitboxList.size()-1) == this ||
                parent.hitboxHandler.hitboxList.get(parent.hitboxHandler.hitboxList.size()-2) == this;}

    }


    /**
     * <h3>Process Entity Collision for train</h3>
     * this checks an area for blocks and other entities and returns true if there is something in that area besides what is supposed to be, (mounted players and bogies).
     */
    public boolean getCollision(GenericRailTransport transport){
        //initialize the variables before the loop to save some CPU
        int k1;
        int l1;
        int i2;
        Block b1;
        double[][] vectorCache = new double[3][3];
        List list;
        Entity entity;
        MultipartHitbox tempBox;
        //Be sure the transport has hitboxes
        for (int iteration = 0; iteration < transport.getHitboxSize()[0]; iteration++) {
            //todo add to a vector cache
            vectorCache[2] = RailUtility.rotatePoint(new double[]{(transport.getHitboxSize()[0]*0.5)-iteration,0,0}, transport.rotationPitch, transport.rotationYaw, 0);
            if (hitboxList.size() <= iteration || transport.ticksExisted == 0) {
                hitboxList.add(new MultipartHitbox(transport, transport, vectorCache[2][0] + transport.posX, vectorCache[2][1] + transport.posY, vectorCache[2][2] + transport.posZ,
                        (iteration==0 || iteration >= transport.getHitboxSize()[0])));
                transport.worldObj.spawnEntityInWorld(hitboxList.get(iteration));
            } else {
                hitboxList.get(iteration).setLocationAndAngles(vectorCache[2][0] + transport.posX, vectorCache[2][1] + transport.posY, vectorCache[2][2] + transport.posZ, transport.rotationYaw, transport.rotationPitch);
            }
        }

        //detect collisions with blocks on X, Y, and Z.
        for (MultipartHitbox box : hitboxList) {
            //skip block checking for first and last hitboxes
            if (!box.isFirst() && !box.isLast()) {
                //define the values as temporary variables first since it will be faster than flooring the variable every loop check
                k1 = MathHelper.floor_double(box.boundingBox.minX);
                l1 = MathHelper.floor_double(box.boundingBox.minY+0.5);
                i2 = MathHelper.floor_double(box.boundingBox.minZ);
            /*
             * check if the chunk exists, then loop for X, Y, and Z to check for a rail or an air block.
             * if one isnt found return true to stop the movement.
             * @see GenericRailTransport#onUpdate()
             */
                if (transport.worldObj.checkChunksExist(k1, l1, i2, MathHelper.floor_double(box.boundingBox.maxX), MathHelper.floor_double(box.boundingBox.maxY), MathHelper.floor_double(box.boundingBox.maxZ))) {
                    for (; k1 <= box.boundingBox.maxX; ++k1) {
                        for (; l1 <= box.boundingBox.maxY; ++l1) {
                            for (; i2 <= box.boundingBox.maxZ; ++i2) {
                                b1 = transport.worldObj.getBlock(k1, l1, i2);
                                if (b1.getCollisionBoundingBoxFromPool(transport.worldObj, k1, l1, i2) != null &&
                                        b1.getCollisionBoundingBoxFromPool(transport.worldObj, k1, l1, i2).intersectsWith(box.getBoundingBox()) &&
                                        !(b1 instanceof BlockRailBase || b1 instanceof ITrackBase)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }

            /*
             * detect collision with entities.
             * we have to create a temporary bounding box that's larger than the current so we can check just a bit past the current box, this gives collision events a bit more time to react.
             * from there we create a list of entities in the new hitbox, and then loop through them to figure out what happens, we can't use the list directly due to concurrent modifications.
             */
            list = transport.worldObj.getEntitiesWithinAABBExcludingEntity(transport, box.boundingBox.copy().expand(0.35,0,0.35));
            if (list != null && list.size()>0) {
                for (Object obj: list) {
                    /*cast the entity ahead of time, so we don't need to cast it over and over later.*/
                    if (obj instanceof Entity){
                        entity = (Entity)obj;
                    } else {
                        continue;
                    }
                    /*if it's something we don't collide with, skip to next iteration.*/
                    if (entity instanceof EntityBogie || entity instanceof GenericRailTransport || entity instanceof EntitySeat ||
                            entity.ridingEntity instanceof EntitySeat || entity.ridingEntity instanceof GenericRailTransport) {
                        continue;
                    }
                    if(entity instanceof HitboxHandler.MultipartHitbox){
                        tempBox = (MultipartHitbox) entity;
                        //if the box is part of a linked transport already, just skip it.
                        if (hitboxList.contains(tempBox) ||
                                (box.parent.frontLinkedID != null && tempBox.parent.getEntityId() == box.parent.frontLinkedID) ||
                                (box.parent.backLinkedID != null && tempBox.parent.getEntityId() == box.parent.backLinkedID)){

                            //if ( transport.worldObj.getEntitiesWithinAABBExcludingEntity(transport, box.boundingBox).contains(entity)) {
                                continue;
                            //}
                        }


                        //check for front coupling if this is the front hitbox
                        if (box.isFront() && transport.getBoolean(GenericRailTransport.boolValues.COUPLINGFRONT) && box.parent.frontLinkedID == null){
                            //check if we're linking to the front of the other
                            if (tempBox.isFirst() && tempBox.parent.frontLinkedID == null && tempBox.parent.getBoolean(GenericRailTransport.boolValues.COUPLINGFRONT)){
                                box.parent.frontLinkedTransport = tempBox.parent.getPersistentID();
                                tempBox.parent.frontLinkedTransport = box.parent.getPersistentID();
                                box.parent.frontLinkedID = tempBox.parent.getEntityId();
                                tempBox.parent.frontLinkedID = box.parent.getEntityId();
                                continue;
                                //check if we're linking to the back of the other.
                            } else if (tempBox.isLast() && tempBox.parent.backLinkedID == null && tempBox.parent.getBoolean(GenericRailTransport.boolValues.COUPLINGBACK)){
                                box.parent.frontLinkedTransport = tempBox.parent.getPersistentID();
                                tempBox.parent.backLinkedTransport = box.parent.getPersistentID();
                                box.parent.frontLinkedID = tempBox.parent.getEntityId();
                                tempBox.parent.backLinkedID = box.parent.getEntityId();
                                continue;
                            }
                            //otherwise check for back coupling if this is the back hitbox
                        } else if (box.isBack() && transport.getBoolean(GenericRailTransport.boolValues.COUPLINGBACK) && box.parent.backLinkedID == null){
                            if (tempBox.isFirst() && tempBox.parent.frontLinkedID == null && tempBox.parent.getBoolean(GenericRailTransport.boolValues.COUPLINGFRONT)){
                                box.parent.backLinkedTransport = tempBox.parent.getPersistentID();
                                tempBox.parent.frontLinkedTransport = box.parent.getPersistentID();
                                box.parent.backLinkedID = tempBox.parent.getEntityId();
                                tempBox.parent.frontLinkedID = box.parent.getEntityId();
                                continue;
                                //check if we're linking to the back of the other.
                            } else if (tempBox.isLast() && tempBox.parent.backLinkedID == null && tempBox.parent.getBoolean(GenericRailTransport.boolValues.COUPLINGBACK)){
                                box.parent.backLinkedTransport = tempBox.parent.getPersistentID();
                                tempBox.parent.backLinkedTransport = box.parent.getPersistentID();
                                box.parent.backLinkedID = tempBox.parent.getEntityId();
                                tempBox.parent.backLinkedID = box.parent.getEntityId();
                                continue;
                            }
                        }
                    }
                    //if it's the first or last box, just skip the rest of the processing.
                    if (box.isFirst() || box.isLast()){
                        continue;
                    }
                    /*if it's an item, check if we should add it to the inventory, maybe do it, and continue to the next iteration*/
                    if (entity instanceof EntityItem){
                        if(transport.getType().isHopper() &&
                                transport.isItemValidForSlot(0,((EntityItem) entity).getEntityItem()) && ((EntityItem) entity).posY > transport.posY+1){
                            transport.addItem(((EntityItem) entity).getEntityItem());
                            ((EntityItem) entity).worldObj.removeEntity(entity);
                        }
                        continue;
                    }
                    if (transport.frontBogie == null || transport.backBogie == null){
                        continue;
                    }
                    if (transport.frontBogie.motionX > 0.5 || transport.frontBogie.motionX < -0.5 || transport.frontBogie.motionZ > 0.5 || transport.frontBogie.motionZ < -0.5) {
                        //in the case of roadkill
                        entity.attackEntityFrom(new EntityDamageSource("rollingstock", transport), (float) (transport.frontBogie.motionX + transport.frontBogie.motionZ) * 1000);
                        if (transport.worldObj.isRemote) {
                            entity.applyEntityCollision(transport);
                        }
                    } else if (!(transport instanceof EntityTrainCore)) {
                        vectorCache[0][0] = entity.posX - transport.posX;
                        vectorCache[0][2] = entity.posZ - transport.posZ;
                        vectorCache[0][1] = MathHelper.sqrt_double(vectorCache[0][0] * vectorCache[0][0] + vectorCache[0][2] * vectorCache[0][2]);

                        if (vectorCache[0][1] >= 0.00999999987D) {
                            vectorCache[0][0] /= vectorCache[0][1];
                            vectorCache[0][2] /= vectorCache[0][1];

                            vectorCache[0][0] *= vectorCache[0][1] *-0.1375D;
                            vectorCache[0][2] *= vectorCache[0][1] *-0.1375D;

                            transport.frontBogie.addVelocity(vectorCache[0][0], 0.0D, vectorCache[0][2]);
                            transport.backBogie.addVelocity(vectorCache[0][0], 0.0D, vectorCache[0][2]);
                        }
                        entity.applyEntityCollision(transport);
                    } else {
                        boolean bool = rand.nextBoolean();
                        vectorCache[0][0] = bool ? 0.05 : 0;
                        vectorCache[0][2] = bool ? 0 : 0.05;
                        vectorCache[1] = RailUtility.rotatePoint(vectorCache[0], 0, transport.rotationYaw, 0);
                        entity.addVelocity(vectorCache[0][0], -0.5, vectorCache[0][2]);
                        entity.applyEntityCollision(transport);
                    }
                }
            }
        }
        //returning true stops the transport, false lets it move.
        return false;
    }


}
