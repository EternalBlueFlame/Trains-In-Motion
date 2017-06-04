package ebf.tim.utility;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.entities.*;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartCollisionEvent;

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
    public class MultipartHitbox extends EntityDragonPart{
        /**reference to the parent entity of this hitbox*/
        public GenericRailTransport parent;
        /**initializer for entity hitbox*/
        public MultipartHitbox(IEntityMultiPart host, GenericRailTransport parent, double posX, double posY , double posZ){
            super(host, "hitboxGeneric", 2,1);
            this.parent = parent;
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.setSize(1,2);
            this.boundingBox.minX = posX-0.45;
            this.boundingBox.minY = posY;
            this.boundingBox.minZ = posZ-0.45;
            this.boundingBox.maxX = posX+0.45;
            this.boundingBox.maxY = posY+2.5;
            this.boundingBox.maxZ = posZ+0.45;
        }
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

    }


    /**
     * <h3>Process Entity Collision for train</h3>
     * this checks an area for blocks and other entities and returns true if there is something in that area besides what is supposed to be, (mounted players and bogies).
     */
    public boolean getCollision(GenericRailTransport transport){
        //Be sure the transport has hitboxes
        for (int iteration = 0; iteration < transport.getHitboxPositions().length; iteration++) {
            double[] position = RailUtility.rotatePoint(new double[]{transport.getHitboxPositions()[iteration], 0, 0}, transport.rotationPitch, transport.rotationYaw, 0);
            if (hitboxList.size() <= iteration || transport.ticksExisted == 0) {
                hitboxList.add(new MultipartHitbox(transport, transport, position[0] + transport.posX, position[1] + transport.posY, position[2] + transport.posZ));
                transport.worldObj.spawnEntityInWorld(hitboxList.get(iteration));
            } else {
                hitboxList.get(iteration).setLocationAndAngles(position[0] + transport.posX, position[1] + transport.posY, position[2] + transport.posZ, transport.rotationYaw, transport.rotationPitch);
            }
        }
        //initialize the variables before the loop to save some CPU
        int k1;
        int l1;
        int i2;
        int l;
        int i1;
        int j1;
        List list;
        Entity entity;
        double[][] vectorCache = new double[2][3];

        //detect collisions with blocks on X, Y, and Z.
        for (MultipartHitbox box : hitboxList) {
                //define the values as temporary variables first since it will be faster than flooring the variable every loop check
                k1 = MathHelper.floor_double(box.boundingBox.minX);
                l1 = MathHelper.floor_double(box.boundingBox.minY);
                i2 = MathHelper.floor_double(box.boundingBox.minZ);
                l = MathHelper.floor_double(box.boundingBox.maxX);
                i1 = MathHelper.floor_double(box.boundingBox.maxY);
                j1 = MathHelper.floor_double(box.boundingBox.maxZ);
            /*
             * check if the chunk exists, then loop for X, Y, and Z to check for a rail or an air block.
             * if one isnt found return true to stop the movement.
             * @see GenericRailTransport#onUpdate()
             */
                if (transport.worldObj.checkChunksExist(k1, l1, i2, l, i1, j1)) {
                    for (; k1 <= l; ++k1) {
                        for (; l1 <= i1; ++l1) {
                            for (; i2 <= j1; ++i2) {
                                if (!(transport.worldObj.getBlock(k1, l1, i2) instanceof BlockAir) && !RailUtility.isRailBlockAt(transport.worldObj, k1, l1, i2)) {
                                    return true;
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
                    if (entity instanceof EntityBogie || entity instanceof GenericRailTransport ||
                            entity.ridingEntity instanceof EntitySeat || entity.ridingEntity instanceof GenericRailTransport || hitboxList.contains(entity)) {
                        continue;
                    }
                    /*if it's an item, check if we should add it to the inventory, maybe do it, and continue to the next iteration*/
                    if (entity instanceof EntityItem){
                            if(transport.getType().isHopper() &&
                            transport.isItemValidForSlot(0,((EntityItem) entity).getEntityItem()) && ((EntityItem) entity).posY > transport.posY+1){
                            transport.addItem(((EntityItem) entity).getEntityItem());
                            ((EntityItem) entity).worldObj.removeEntity((EntityItem) entity);
                        }
                        continue;
                    }
                    /*if the parent is an Entity Rollingstock, and the other checks were false, we now need to check if we roadkill the entity, or get pushed by it.*/
                    if (transport instanceof EntityRollingStockCore) {
                        if (transport.frontBogie.motionX > 0.5 || transport.frontBogie.motionX < -0.5 || transport.frontBogie.motionZ > 0.5 || transport.frontBogie.motionZ < -0.5) {
                            //in the case of roadkill
                            entity.attackEntityFrom(new EntityDamageSource("rollingstock", transport), (float) (transport.frontBogie.motionX + transport.frontBogie.motionZ) * 1000);
                            if (transport.worldObj.isRemote) {
                                entity.applyEntityCollision(transport);
                            }
                        } else {
                            vectorCache[0][0] = entity.posX - transport.posX;
                            vectorCache[0][2] = entity.posZ - transport.posZ;
                            vectorCache[0][1] = MathHelper.sqrt_double(vectorCache[0][0] * vectorCache[0][0] + vectorCache[0][2] * vectorCache[0][2]);

                            if (vectorCache[0][1] >= 0.00999999987D) {
                                vectorCache[0][0] /= vectorCache[0][1];
                                vectorCache[0][2] /= vectorCache[0][1];

                                vectorCache[0][0] *= 1.0D/vectorCache[0][1];
                                vectorCache[0][2] *= 1.0D/vectorCache[0][1];
                                vectorCache[0][0] *= 0.05000000074444445D;
                                vectorCache[0][2] *= 0.05000000074444445D;

                                transport.frontBogie.addVelocity(-vectorCache[0][0], 0.0D, -vectorCache[0][2]);
                                transport.backBogie.addVelocity(-vectorCache[0][0], 0.0D, -vectorCache[0][2]);
                            }
                            entity.applyEntityCollision(transport);
                        }
                        /*however if this was n entity Train Core, we just have to figure out if we should roadkill it.*/
                    } else if (transport.frontVelocityX > 0.5 || transport.frontVelocityX < -0.5 || transport.frontVelocityZ > 0.5 || transport.frontVelocityZ < -0.5) {
                        entity.attackEntityFrom(new EntityDamageSource("train", transport), (float) (transport.frontVelocityX + transport.frontVelocityZ) * 1000);
                        if (transport.worldObj.isRemote) {
                            entity.applyEntityCollision(transport);
                        }
                    } else {
                        boolean bool = rand.nextBoolean();
                        vectorCache[0][0] = bool?0.05:0;
                        vectorCache[0][2] = bool?0:0.05;
                        vectorCache[1] = RailUtility.rotatePoint(vectorCache[0], 0,transport.rotationYaw,0);
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
