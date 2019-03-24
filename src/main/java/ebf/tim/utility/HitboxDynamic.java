package ebf.tim.utility;

import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntityBogie;
import ebf.tim.entities.EntitySeat;
import ebf.tim.entities.GenericRailTransport;
import fexcraft.tmt.slim.Vec3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;


public class HitboxDynamic {

    public Vec3f[] shape, pos, renderShape;
    float longest=0;


    public HitboxDynamic(float width, float height, float depth){
        width *=0.5f;
        depth *=0.5f;
        shape= new Vec3f[]{
                new Vec3f(-width,0,depth),new Vec3f(width,0,depth),new Vec3f(width,0,-depth),new Vec3f(-width,0,-depth),
                new Vec3f(-width,height,depth),new Vec3f(width,height,depth),new Vec3f(width,height,-depth),new Vec3f(-width,height,-depth)};
        pos=new Vec3f[]{shape[0],shape[1], shape[6]};
        if(TrainsInMotion.proxy.isClient()) {
            renderShape = shape.clone();
        }
        if(Math.abs(width)>longest){
            longest=Math.abs(width);
        }
        if(Math.abs(depth)>longest){
            longest=Math.abs(depth);
        }
    }


    public void position(double x, double y, double z, float pitch, float yaw){
        pos[0] = RailUtility.rotatePoint(shape[0], pitch,  yaw, 0).addVector(f(x), f(y), f(z));
        pos[1] = RailUtility.rotatePoint(shape[1], pitch,  yaw, 0).addVector(f(x), f(y), f(z));
        pos[2] = RailUtility.rotatePoint(shape[6], pitch,  yaw, 0).addVector(f(x), f(y), f(z));
        if (TrainsInMotion.proxy.isClient()) {
            for (int i = 0; i < 8; i++) {
                renderShape[i] = RailUtility.rotatePoint(shape[i], pitch, yaw, 0);
            }
        }
    }
    private static float f(double d){ return (float)d;}


    /**
     * AWT methods
     */

    List<Entity> arraylist = new ArrayList<>();
    List[] entities;
    int i,j,k,l;
    public boolean contains(Entity host){

        arraylist = new ArrayList<>();
        i = MathHelper.floor_double((-longest+host.posX - 0.25) / 16.0D);
        j = MathHelper.floor_double((longest+host.posX + 0.25) / 16.0D);
        k = MathHelper.floor_double((-longest+host.posZ - 0.25) / 16.0D);
        l = MathHelper.floor_double((longest+host.posZ + 0.25) / 16.0D);

        for (int i1 = i; i1 <= j; ++i1) {
            for (int j1 = k; j1 <= l; ++j1) {
                if (host.worldObj.getChunkProvider().chunkExists(i1, j1)) {
                    entities = host.worldObj.getChunkFromChunkCoords(i1, j1).entityLists;
                    for (List olist: entities) {
                        for(Object obj : olist) {
                            if(obj instanceof EntityLiving){
                                arraylist.add((EntityLiving) obj);
                            }
                        }
                    }
                }
            }
        }

        for (Entity o : arraylist) {
            if (o instanceof EntityPlayer) {
                return containsPoint(o.posX,o.posY, o.posZ);
            }
        }
        return false;
    }

    public List<Entity> getCollidingPlayers(GenericRailTransport host){
        arraylist = new ArrayList<>();
        i = MathHelper.floor_double((-longest+host.posX - 0.25) / 16.0D);
        j = MathHelper.floor_double((longest+host.posX + 0.25) / 16.0D);
        k = MathHelper.floor_double((-longest+host.posZ - 0.25) / 16.0D);
        l = MathHelper.floor_double((longest+host.posZ + 0.25) / 16.0D);

        for (int i1 = i; i1 <= j; ++i1) {
            for (int j1 = k; j1 <= l; ++j1) {
                if (host.worldObj.getChunkProvider().chunkExists(i1, j1)) {
                    entities = host.worldObj.getChunkFromChunkCoords(i1, j1).entityLists;
                    for (List olist: entities) {
                        for(Object obj : olist) {
                            if(obj instanceof EntityPlayer && containsPlayer((Entity) obj)){
                                arraylist.add((Entity)obj);
                            }
                        }
                    }
                }
            }
        }

        return arraylist;
    }

    GenericRailTransport stock;

    public List<Entity> getCollidingEntities(GenericRailTransport host){
        arraylist = new ArrayList<>();
        i = MathHelper.floor_double((-longest+host.posX - 0.25) / 16.0D);
        j = MathHelper.floor_double((longest+host.posX + 0.25) / 16.0D);
        k = MathHelper.floor_double((-longest+host.posZ - 0.25) / 16.0D);
        l = MathHelper.floor_double((longest+host.posZ + 0.25) / 16.0D);

        for (int i1 = i; i1 <= j; ++i1) {
            for (int j1 = k; j1 <= l; ++j1) {
                if (host.worldObj.getChunkProvider().chunkExists(i1, j1)) {
                    entities = host.worldObj.getChunkFromChunkCoords(i1, j1).entityLists;
                    for (List olist: entities) {
                        for(Object obj : olist) {
                            if(obj instanceof EntitySeat || obj instanceof EntityBogie || obj ==host){continue;}

                            if(obj instanceof GenericRailTransport) {
                                stock=(GenericRailTransport)obj;
                                if(host.frontLinkedTransport== stock.getUniqueID() || host.backLinkedTransport==stock.getUniqueID()) {
                                    continue;
                                }
                                Vec3 vec;
                                if(host.getBoolean(GenericRailTransport.boolValues.COUPLINGFRONT)) {
                                    vec =RailUtility.rotateDistance(longest+0.25f, host.rotationPitch, host.rotationYaw);
                                    vec.addVector(host.posX, host.posY + 0.25, host.posZ);
                                    if (stock.collisionHandler.containsPoint(vec.xCoord, vec.yCoord, vec.zCoord)) {
                                        if (transportCollide(host, (GenericRailTransport) obj, true)) {
                                            continue;
                                        }
                                    }
                                }
                                if(host.getBoolean(GenericRailTransport.boolValues.COUPLINGBACK)) {
                                    vec = RailUtility.rotateDistance(-longest - 0.25f, host.rotationPitch, host.rotationYaw);
                                    vec.addVector(host.posX, host.posY + 0.25, host.posZ);
                                    if (stock.collisionHandler.containsPoint(vec.xCoord, vec.yCoord, vec.zCoord)) {
                                        if (transportCollide(host, (GenericRailTransport) obj, false)) {
                                            continue;
                                        }
                                    }
                                }
                                continue;

                            }

                            if(containsEntity((Entity) obj)){
                                arraylist.add((Entity)obj);
                            }
                        }
                    }
                }
            }
        }

        return arraylist;
    }

    public boolean transportCollide(GenericRailTransport host, GenericRailTransport target, boolean front){
        if(front){
            Vec3 vec =RailUtility.rotateDistance(target.collisionHandler.longest+0.25f, target.rotationPitch, target.rotationYaw);
            vec.addVector((target).posX,(target).posY+0.25,(target).posZ);
            if(containsPoint(vec.xCoord,vec.yCoord,vec.zCoord)){
                if(target.getBoolean(GenericRailTransport.boolValues.COUPLINGFRONT)){
                    host.frontLinkedTransport=target.getUniqueID();
                    target.frontLinkedTransport=host.getUniqueID();
                    host.frontLinkedID=target.getEntityId();
                    target.frontLinkedID=host.getEntityId();
                    host.setBoolean(GenericRailTransport.boolValues.COUPLINGFRONT,false);
                    target.setBoolean(GenericRailTransport.boolValues.COUPLINGFRONT,false);
                    host.updateConsist();
                    return true;
                }
            } else {
                vec =RailUtility.rotateDistance(-target.collisionHandler.longest-0.25f, target.rotationPitch, target.rotationYaw);
                vec.addVector((target).posX,(target).posY+0.25,(target).posZ);
                if(containsPoint(vec.xCoord,vec.yCoord,vec.zCoord)) {
                    if (target.getBoolean(GenericRailTransport.boolValues.COUPLINGBACK)) {
                        host.frontLinkedTransport=target.getUniqueID();
                        target.backLinkedTransport=host.getUniqueID();
                        host.frontLinkedID=target.getEntityId();
                        target.backLinkedID=host.getEntityId();
                        host.setBoolean(GenericRailTransport.boolValues.COUPLINGFRONT,false);
                        target.setBoolean(GenericRailTransport.boolValues.COUPLINGBACK,false);
                        host.updateConsist();
                        return true;
                    }
                }
            }
        } else {
            Vec3 vec =RailUtility.rotateDistance(target.collisionHandler.longest+0.25f, target.rotationPitch, target.rotationYaw);
            vec.addVector((target).posX,(target).posY+0.25,(target).posZ);
            if(containsPoint(vec.xCoord,vec.yCoord,vec.zCoord)){
                if(target.getBoolean(GenericRailTransport.boolValues.COUPLINGFRONT)){
                    host.backLinkedTransport=target.getUniqueID();
                    target.frontLinkedTransport=host.getUniqueID();
                    host.backLinkedID=target.getEntityId();
                    target.frontLinkedID=host.getEntityId();
                    host.setBoolean(GenericRailTransport.boolValues.COUPLINGBACK,false);
                    target.setBoolean(GenericRailTransport.boolValues.COUPLINGFRONT,false);
                    host.updateConsist();
                    return true;
                }
            } else {
                vec =RailUtility.rotateDistance(-target.collisionHandler.longest-0.25f, target.rotationPitch, target.rotationYaw);
                vec.addVector((target).posX,(target).posY+0.25,(target).posZ);
                if(containsPoint(vec.xCoord,vec.yCoord,vec.zCoord)) {
                    if (target.getBoolean(GenericRailTransport.boolValues.COUPLINGBACK)) {
                        host.backLinkedTransport=target.getUniqueID();
                        target.backLinkedTransport=host.getUniqueID();
                        host.backLinkedID=target.getEntityId();
                        target.backLinkedID=host.getEntityId();
                        host.setBoolean(GenericRailTransport.boolValues.COUPLINGBACK,false);
                        target.setBoolean(GenericRailTransport.boolValues.COUPLINGBACK,false);
                        host.updateConsist();
                        return true;
                    }
                }
            }
        }
        return false;

    }
    public boolean containsPoint(double x, double y, double z){
        return containsPoint(pos[0],pos[1],pos[2], new Vec3f(x,y,z));
    }

    public boolean containsPlayer(Entity e){
        return containsPoint(pos[0],pos[1],pos[2], new Vec3f(e.posX,e.posY+(e.worldObj.isRemote?-1:0.25f),e.posZ));
    }

    public boolean containsEntity(Entity e){
        return containsPoint(pos[0],pos[1],pos[2], new Vec3f(e.posX,e.posY+0.25,e.posZ));
    }

    public static boolean containsPoint(Vec3f O, Vec3f X, Vec3f Z, Vec3f P){
        return P.yCoord>O.yCoord && P.yCoord < Z.yCoord &&
                O.dot2D(O.subtract(X))>P.dot2D(O.subtract(X)) && P.dot2D(O.subtract(X))>X.dot2D(O.subtract(X))
                &&
                X.dot2D(X.subtract(Z))>P.dot2D(X.subtract(Z)) && P.dot2D(X.subtract(Z))>Z.dot2D(X.subtract(Z));
    }
}
