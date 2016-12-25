package trains.utility;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import trains.TrainsInMotion;
import trains.entities.EntityBogie;
import trains.entities.EntityTrainCore;
import trains.entities.GenericRailTransport;
import trains.networking.PacketRemove;

import java.util.List;

import static trains.utility.RailUtility.rotatePoint;

public class HitboxHandler {

    /**
     * <h2> basic hitbox class</h2>
     * a generic hitbox class used for adding multiple hitboxes to an entity.
     * requires the entity to override getParts() and extend IEntityMultiPart
     * The hitboxes also need to be moved every onUpdate, for trains this is handled in
     * @see HitboxHandler#getCollision(GenericRailTransport)
     */
    public class multipartHitbox extends EntityDragonPart{
        Entity parent;
        public multipartHitbox(IEntityMultiPart host, Entity parent, double posX, double posY , double posZ){
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
            this.boundingBox.maxY = posY+2;
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
        @Override
        public void onUpdate(){
            if (parent == null){
                worldObj.removeEntity(this);
            }
        }
        @Override
        public boolean canBeCollidedWith() {
            return true;
        }
        @SideOnly(Side.CLIENT)
        public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_) {
            this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
            this.setRotation(p_70056_7_, p_70056_8_);
        }

    }


    /**
     * <h3>Process Entity Collision for train</h3>
     * this checks an area for blocks and other entities and returns true if there is something in that area besides what is supposed to be, (mounted players and bogies).
     */
    public boolean getCollision(GenericRailTransport train){

        for (int iteration =0; iteration<train.getHitboxPositions().length; iteration++) {
            double[] position = rotatePoint(new double[]{train.getHitboxPositions()[iteration], 0, 0}, train.rotationPitch, train.rotationYaw, 0);
            if (train.hitboxList.size() <= iteration) {
                train.hitboxList.add(new multipartHitbox(train, train, position[0] + train.posX, position[1] + train.posY, position[2] + train.posZ));
                if(train.worldObj.isRemote) {
                    train.worldObj.spawnEntityInWorld(train.hitboxList.get(iteration));
                }
            }
            train.hitboxList.get(iteration).onUpdate();
            train.hitboxList.get(iteration).setLocationAndAngles(position[0] + train.posX, position[1] + train.posY, position[2] + train.posZ, 0, 0);
        }


        //detect collisions with blocks.
        for (multipartHitbox box : train.hitboxList){
            int i = MathHelper.floor_double(box.boundingBox.minX);
            int j = MathHelper.floor_double(box.boundingBox.minY);
            int k = MathHelper.floor_double(box.boundingBox.minZ);
            int l = MathHelper.floor_double(box.boundingBox.maxX);
            int i1 = MathHelper.floor_double(box.boundingBox.maxY);
            int j1 = MathHelper.floor_double(box.boundingBox.maxZ);

            if (train.worldObj.checkChunksExist(i, j, k, l, i1, j1)) {
                for (int k1 = i; k1 <= l; ++k1) {
                    for (int l1 = j; l1 <= i1; ++l1) {
                        for (int i2 = k; i2 <= j1; ++i2) {
                            Block block = train.worldObj.getBlock(k1, l1, i2);
                            if (!(block instanceof BlockAir) && !RailUtility.isRailBlockAt(block)){
                                return true;
                            }
                        }
                    }
                }
            }


            //detect collisions with entities.
            List list = train.worldObj.getEntitiesWithinAABBExcludingEntity(train, box.boundingBox);
            if (list != null && !list.isEmpty()) {
                for (Object entity: list) {
                    if (entity instanceof Entity) {
                        if (entity instanceof multipartHitbox && train.hitboxList.contains(entity)){
                            return false;
                        }
                        if (entity != train.riddenByEntity && !(entity instanceof EntityBogie)) {
                            if (entity instanceof EntityLiving || entity instanceof EntityPlayer) {
                                //dependant on velocity, fling it and do damage.
                                if (train.motionX + train.motionZ != 0) {
                                    ((Entity) entity).attackEntityFrom(new EntityDamageSource("Train", train), (float) (train.bogie.get(0).motionX + train.bogie.get(0).motionZ) * 1000);
                                }
                                ((Entity) entity).applyEntityCollision(train);
                                System.out.println(entity.getClass().toString());
                                return false;
                            } else {
                                System.out.println(entity.getClass().toString());
                                return true;
                            }
                        }
                    }
                }
            }
        }
        //returning true stops the train, false lets it move.
        return false;
    }


    /**
     * <h2>Entity Attacked</h2>
     * covers when a player, or anything else for that matter, hits the train.
     * we will need to basically clone this for rollingstock.
     * @param host the train entity that was attacked
     * @param damageSource the thing that hit it
     * @param damage the damage done to it, i think.
     * @return true or false if it was actually destroyed, or its whether or not to play the player punch animation, either way its kinda trivial.
     */
    public static boolean AttackEvent(GenericRailTransport host, DamageSource damageSource, float damage){
        if (damageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer) damageSource.getEntity()).capabilities.isCreativeMode && !damageSource.isProjectile()){
            for (EntityMinecart cart : host.bogie){
                cart.worldObj.removeEntity(cart);
                cart.isDead = true;
                TrainsInMotion.keyChannel.sendToServer(new PacketRemove(cart.getEntityId()));
            }
            for (EntityDragonPart hitbox : host.hitboxList){
                hitbox.worldObj.removeEntity(hitbox);
                hitbox.isDead = true;
                TrainsInMotion.keyChannel.sendToServer(new PacketRemove(hitbox.getEntityId()));
            }

            damageSource.getSourceOfDamage().worldObj.removeEntity(host);
            host.isDead=true;
            TrainsInMotion.keyChannel.sendToServer(new PacketRemove(host.getEntityId()));
            return true;
        }
        return false;
    }

}
