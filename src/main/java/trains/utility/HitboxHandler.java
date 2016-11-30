package trains.utility;

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
import trains.entities.EntityBogie;
import trains.entities.EntityTrainCore;

import java.util.List;

import static trains.utility.RailUtility.rotatePoint;

public class HitboxHandler {

    public class multipartHitbox extends EntityDragonPart{

        public multipartHitbox(IEntityMultiPart host, double posX, double posY , double posZ){
            super(host, "hitboxGeneric", 2,1);
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.setSize(1,2);
            this.boundingBox.minX = posX-0.5;
            this.boundingBox.minY = posY;
            this.boundingBox.minZ = posZ-0.5;
            this.boundingBox.maxX = posX+0.5;
            this.boundingBox.maxY = posY+2;
            this.boundingBox.maxZ = posZ+0.5;
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
        public boolean canBeCollidedWith() {
            return true;
        }
    }


    /**
     * <h3>Process Entity Collision for train</h3>
     * this checks an area for blocks and other entities and returns true if there is something in that area besides what is supposed to be, (mounted players and bogies).
     */
    public boolean getCollision(EntityTrainCore train){

        for (int iteration =0; iteration<train.getHitboxPositions().length; iteration++) {
            double[] position = rotatePoint(new double[]{train.getHitboxPositions()[iteration], 0, 0}, train.rotationPitch, train.rotationYaw, 0);
            if (train.hitboxList.size() <= iteration) {
                train.hitboxList.add(new multipartHitbox(train, position[0] + train.posX, position[1] + train.posY, position[2] + train.posZ));
                if (train.worldObj.isRemote) {
                    train.worldObj.spawnEntityInWorld(train.hitboxList.get(iteration));
                }
            }
            train.hitboxList.get(iteration).onUpdate();
            train.hitboxList.get(iteration).setLocationAndAngles(position[0] + train.posX, position[1] + train.posY, position[2] + train.posZ, 0, 0);
        }


        for (multipartHitbox box : train.hitboxList){
            int i = MathHelper.floor_double(box.boundingBox.minX + 0.001D);
            int j = MathHelper.floor_double(box.boundingBox.minY -0.501D);
            int k = MathHelper.floor_double(box.boundingBox.minZ + 0.001D);
            int l = MathHelper.floor_double(box.boundingBox.maxX - 0.001D);
            int i1 = MathHelper.floor_double(box.boundingBox.maxY - 0.001D);
            int j1 = MathHelper.floor_double(box.boundingBox.maxZ - 0.001D);

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


            List list = train.worldObj.getEntitiesWithinAABBExcludingEntity(train, box.boundingBox);
            if (list != null && !list.isEmpty()) {
                for (Object entity: list) {
                    if (entity instanceof Entity) {
                        if (entity != train.riddenByEntity && !(entity instanceof EntityBogie)) {
                            if (entity instanceof EntityLiving || entity instanceof EntityPlayer) {
                                //dependant on velocity, fling it and do damage.
                                if (train.motionX + train.motionZ < 0.001) {
                                    ((Entity) entity).attackEntityFrom(new EntityDamageSource("Train", train), (float) (train.bogie.get(0).motionX + train.bogie.get(0).motionZ) * 1000);
                                }
                                ((Entity) entity).applyEntityCollision(train);
                                return false;
                            } else {
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



    public static boolean AttackEvent(EntityDragonPart part, DamageSource damageSource, float damage){
        if (damageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer) damageSource.getEntity()).capabilities.isCreativeMode){
            EntityTrainCore host = ((EntityTrainCore)part.entityDragonObj);
            for (EntityMinecart cart : host.bogie){
                cart.worldObj.removeEntity(cart);
            }
            for (EntityDragonPart hitbox : host.hitboxList){
                hitbox.worldObj.removeEntity(hitbox);
            }

            host.worldObj.removeEntity(host);
            return true;
        }
        return false;
    }

}
