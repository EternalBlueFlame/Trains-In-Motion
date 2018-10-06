package ebf.tim.models;

import ebf.tim.TrainsInMotion;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.utility.DebugUtil;
import ebf.tim.utility.RailUtility;
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.ModelRendererTurbo;
import fexcraft.tmt.slim.Tessellator;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <h1>Particle effect</h1>
 * custom particle, fully separate from the vanilla stuff, renders in 3d and still runs significantly better than vanilla.
 * TODO: add support for 2d particles.
 * @author Eternal Blue Flame
 */
public class ParticleFX {
    /*the number of ticks this particle will survive till it needs to reset position.*/
    private int lifespan =-1;
    /*returns if the particle should render or not*/
    public boolean shouldRender = false;
    /*the color to render the particle as*/
    private final int color;
    /*the ticks the particle has existed, float is used so render can divide it into decimals*/
    private Float ticksExisted=null;
    /*the offset to tint the particle color*/
    private float colorTint;
    /*the bounding box of the particle to use for rendering and collision, if it's null we render it as a static particle*/
    private final AxisAlignedBB boundingBox;
    /*the motion of the particle*/
    private double motionX=0, motionY=0, motionZ=0;
    /*a random to use for variable generating*/
    private static final Random rand = new Random();
    /*the list of objects in the hitbox*/
    private List list = new ArrayList<>();
    /*the cached motion of the particle*/
    private double oldX, oldY, oldZ;
    /*the host entity*/
    private GenericRailTransport host;
    /*the position offset to move based on the transport's rotation*/
    private double[] offset, pos;
    private int particleID=0;

    /**
     * Initialize the particle, basically for spawning it
     * @param color the color of the particle.
     */
    public ParticleFX(GenericRailTransport transport, int color, float offsetX, float offsetY, float offsetZ, int id) {
        host = transport;
        particleID=id;
        this.offset = new double[]{offsetX, offsetY, offsetZ};
        pos = RailUtility.rotatePoint(new double[]{offset[0]*0.0625,offset[1]*-0.0625,offset[2]*0.0625}, transport.rotationPitch, transport.rotationYaw, 0);

        switch (particleID) {
            case 0:case 1:{//smoke, steam
                motionX = (rand.nextInt(40) - 20) * 0.001f;
                motionY = particleID==0?0.15:0.05;
                motionZ = (rand.nextInt(40) - 20) * 0.001f;


                this.boundingBox = AxisAlignedBB.getBoundingBox(pos[0] + transport.posX - 0.1, pos[1] + transport.posY - 0.1, pos[2] + transport.posZ - 0.1, pos[0] + transport.posX + 0.1, pos[1] + transport.posY + 0.1, pos[2] + transport.posZ + 0.1);
                break;
            }
            case 2:case 3: default:{
                motionX = 1;
                this.boundingBox = null;
                break;
            }
        }
        this.color = color;
    }

    public static int getParticleIDFronName(String name){
        if(name.contains("smoke")){
            return 0;
        } else if(name.contains("steam")){
            return 1;
        }else if(name.contains("cone")){
            return 2;
        }else if(name.contains("sphere")){
            return 3;
        } else if (name.contains("wheel")){
            return 4;
        }

        return -1;//invalid part
    }

    public static List<ParticleFX> newParticleItterator(int strength, int color, float offsetX, float offsetY, float offsetZ, GenericRailTransport host, String partname){
        List<ParticleFX> list = new ArrayList<>();
        for (int i=0; i<strength; i++){
            list.add(new ParticleFX(host, color, offsetX, offsetY, offsetZ, getParticleIDFronName(partname)));
        }
        return list;
    }

    public static void updateParticleItterator(List<ParticleFX> particles, boolean hostIsRunning){
        int index=0;
        for (ParticleFX p : particles){
            p.onUpdate(hostIsRunning, index*(150f/particles.size()));
            index++;
        }
    }

    public static String[] parseData(String s){
        if (s.contains("smoke")) {
            return s.substring(s.indexOf("smoke ")+6).split(" ");
        } else if (s.contains("wheel")||s.contains("lamp")){
            return new String[]{"0", "CCCC00"};
        } else {
            return s.substring(s.indexOf("steam ")+6).split(" ");
        }
    }

    /**
     * <h2>movement calculations</h2>
     * call this from the host's onUpdate to update the position of the particle.
     */
    public void onUpdate(boolean hostIsRunning, float count){

        if(ticksExisted==null){
            ticksExisted = -count;
        } else if (ticksExisted<=1){
            ticksExisted++;
            return;
        }


        if (boundingBox==null){
            if(motionX==1) {
                shouldRender = host.getBoolean(GenericRailTransport.boolValues.LAMP);
                if (ticksExisted > 20f) {
                    //todo change color to simulate flicker?
                }
            }
            return;
        }

        //if the lifespan is out we reset the information, as if we just spawned a new particle.
        if (hostIsRunning && this.ticksExisted > this.lifespan) {
            colorTint = (rand.nextInt(60) - 30)* 0.005f;
            lifespan = rand.nextInt(80) +140;
            ticksExisted =0f;
            pos = RailUtility.rotatePoint(new double[]{offset[0]*0.0625,offset[1]*-0.0625,offset[2]*0.0625}, host.rotationPitch, host.rotationYaw, 0);
            this.boundingBox.setBounds(host.posX+pos[0] -0.1, host.posY+pos[1] -0.1, host.posZ+pos[2] -0.1, host.posX+pos[0] +0.1,  host.posY+pos[1] +0.1, host.posZ+pos[2] +0.1);
            motionX = (rand.nextInt(40) - 20) * 0.001f;
            if(particleID==0) {
                motionY = rand.nextInt(15)*0.01;
            } else if (particleID==1){
                motionY = rand.nextInt(15)*0.003;
            }
            motionZ = (rand.nextInt(40) - 20) * 0.001f;
            shouldRender = true;
        } else if (this.ticksExisted > this.lifespan) {
            //if the transport isn't running and this has finished it's movement, set it' position to the transport and set that it shouldn't render.
            this.boundingBox.setBounds(host.posX, host.posY, host.posZ, host.posX,  host.posY, host.posZ);
            shouldRender = false;
            return;
        }

        //set the old motion values so we can compare them later.
        oldX = motionX;
        oldY = motionY;
        oldZ = motionZ;
        //instance a bounding box variable now so we won't have to cast as much later.
        AxisAlignedBB box;

        //todo: should be able to just check movement and replicate bounding box functions without bounding box, use pos for the temp
        list = host.worldObj.getCollidingBoundingBoxes(host, this.boundingBox.addCoord(motionX, motionY, motionZ));
        //iterate the list and check for collisions
        for (Object obj : list) {
            box = ((AxisAlignedBB) obj);
            if (motionY <0.001 || motionY >-0.001) {
                motionY = box.calculateYOffset(this.boundingBox, motionY);
            }
            if (motionX <0.0001 || motionX >-0.0001) {
                motionX = box.calculateXOffset(this.boundingBox, motionX);
            }
            if (motionZ <0.0001 || motionZ >-0.0001) {
                motionZ = box.calculateZOffset(this.boundingBox, motionZ);
            }
        }

        //check for collisions on the Y vector and apply movement accordingly, also always keep it attempting to float up.
        if (motionY <0.001 || motionY >-0.001) {
            this.boundingBox.offset(0.0D, motionY, 0.0D);

            if (oldY != motionY) {
                motionZ *=1.5d; motionZ +=rand.nextBoolean()?oldY:rand.nextBoolean()?0:-oldY;
                motionX *=1.5d; motionX +=rand.nextBoolean()?oldY:rand.nextBoolean()?0:-oldY;
                motionY = oldY * -0.4d;
            }
            if (motionY<0.005){
                motionY += 0.00075;
            }
        }else {
            motionY += 0.00005;
        }

        //check for collisions on the x axis.
        if (motionX <0.0001 || motionX >-0.0001) {
            this.boundingBox.offset(motionX, 0.0D, 0.0D);
            if (oldX != motionX) {
                motionX = this.motionX*0.75d;
            }
            motionX *=0.975;
        }

        //check for collisions on the Z axis.
        if (motionZ <0.0001 || motionZ >-0.0001) {
            this.boundingBox.offset(0.0D, 0.0D, motionZ);
            if (oldZ != motionZ) {
                motionZ = this.motionZ*0.75d;
            }
            motionZ *=0.975;
        }

        ticksExisted++;
    }

    /**
     * <h2>Render particle</h2>
     * actually renders the particle, unless it's on tick 0, we skip rendering that tick since it won't have a motion yet.
     * @param entity the particle variable to render
     * @param x the x position of the renderer
     * @param y the y position of the renderer
     * @param z the z position of the renderer
     */
    public static void doRender(ParticleFX entity, double x, double y, double z) {
        if(entity.ticksExisted==null || entity.ticksExisted<1 || !entity.shouldRender){
            return;
        }

        if(entity.boundingBox==null ){

            GL11.glPushMatrix();
            GL11.glTranslated(x, y , z);
            if (entity.motionX==1) {
                //use the texture to simulate it's fade with distance, rather than having the entire thing a set color.
                //Tessellator.bindTexture(new ResourceLocation(TrainsInMotion.MODID, "cyltest.png"));
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glColor4f(((entity.color >> 16 & 0xFF) * 0.00392156863f) - entity.colorTint,
                        ((entity.color >> 8 & 0xFF) * 0.00392156863f) - entity.colorTint,
                        ((entity.color & 0xFF) * 0.00392156863f) - entity.colorTint,
                        1f);
                lampCone.render(0.0625f);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            } else {
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glColor4f(((entity.color >> 16 & 0xFF) * 0.00392156863f) - entity.colorTint,
                        ((entity.color >> 8 & 0xFF) * 0.00392156863f) - entity.colorTint,
                        ((entity.color & 0xFF) * 0.00392156863f) - entity.colorTint,
                        0.1f);
                lampSphere.render(0.0625f);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }
            //todo loop for a detail intensity going from outside to inside using GLScaleF to shrink it from 1 to about 0.25

            GL11.glPopMatrix();
        } else {

            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            //set the color with the tint.   * 0.00392156863 is the same as /255, but multiplication is more efficient than division.
            GL11.glColor4f(((entity.color >> 16 & 0xFF)* 0.00392156863f) - entity.colorTint,
                    ((entity.color >> 8 & 0xFF)* 0.00392156863f) - entity.colorTint,
                    ((entity.color & 0xFF)* 0.00392156863f) - entity.colorTint,
                    1f-(entity.ticksExisted/entity.lifespan));
            //set the position
            GL11.glTranslated( x + entity.boundingBox.minX - entity.host.posX, y+ entity.boundingBox.minY-entity.host.posY, z+ entity.boundingBox.minZ - entity.host.posZ);
            particle.render(0.0625f);

            //before we end this be sure to re-enabling texturing for other things.
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPopMatrix();
        }

    }

    public static ModelRendererTurbo particle = new ModelRendererTurbo(null, 0, 0, 32, 32)
            .addBox(0,0,0, 4, 4, 4).setRotationPoint(-2F, 2F, -1F);
    public static ModelRendererTurbo lampCone = new ModelRendererTurbo(null, 0, 0, 32, 32)
            .addCone(0,0,0,3,7,16);
    public static ModelRendererTurbo lampSphere = new ModelRendererTurbo(null, 0, 0, 32, 32)
            .addSphere(0,0,0, 2, 6, 6,1,1);

}
