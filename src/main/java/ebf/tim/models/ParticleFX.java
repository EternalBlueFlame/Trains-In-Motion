package ebf.tim.models;

import ebf.tim.entities.GenericRailTransport;
import ebf.tim.models.tmt.Tessellator;
import ebf.tim.utility.RailUtility;
import net.minecraft.util.AxisAlignedBB;
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
    /*the X position of the particle*/
    public double posX;
    /*the Y position of the particle*/
    public double posY;
    /*the Z position of the particle*/
    public double posZ;
    /*returns if the particle should render or not*/
    public boolean shouldRender = false;
    /*the color to render the particle as*/
    private final int color;
    /*the ticks the particle has existed*/
    private int ticksExisted=0;
    /*the offset to tint the particle color*/
    private float colorTint;
    /*the bounding box of the particle to use for rendering and collision*/
    private final AxisAlignedBB boundingBox;
    /*the X motion of the particle*/
    private double motionX=0;
    /*the Y motion of the particle*/
    private double motionY=0;
    /*the Z motion of the particle*/
    private double motionZ=0;
    /*a random to use for variable generating*/
    private static final Random rand = new Random();
    /*the list of objects in the hitbox*/
    private List list = new ArrayList<>();
    /*the cached X motion of the particle*/
    private double oldX;
    /*the cached Y motion of the particle*/
    private double oldY;
    /*the cached Z motion of the particle*/
    private double oldZ;
    /*the host entity*/
    private GenericRailTransport host;
    /*the position offset to move based on the transport's rotation*/
    private double[] offset;

    /**
     * Initialize the particle, basically for spawning it
     * @param color the color of the particle.
     */
    public ParticleFX(GenericRailTransport transport, float color, float[] offset) {
        host = transport;
        this.offset = new double[]{offset[0], offset[1], offset[2]};
        double[] pos = RailUtility.rotatePoint(this.offset, transport.rotationPitch, transport.rotationYaw, 0);
        posX = pos[0] + transport.posX;
        posY = pos[1] + transport.posY;
        posZ = pos[2] + transport.posZ;


        motionX = (rand.nextInt(40) - 20) * 0.001f;
        motionY = this.offset[1] * 0.05;
        motionZ = (rand.nextInt(40) - 20) * 0.001f;

        this.color = (int)color;
        this.boundingBox = AxisAlignedBB.getBoundingBox(posX -0.1, posY -0.1, posZ -0.1, posX +0.1,  posY +0.1, posZ +0.1);
    }

    /**
     * <h2>movement calculations</h2>
     * call this from the host's onUpdate to update the position of the particle.
     */
    public void onUpdate(boolean hostIsRunning){
        posX = (float) host.posX;
        posY = (float) host.posY;
        posZ = (float) host.posZ;
        //if the lifespan is out we reset the information, as if we just spawned a new particle.
        if (hostIsRunning && this.ticksExisted > this.lifespan) {
            colorTint = (rand.nextInt(60) - 30)* 0.005f;
            lifespan = rand.nextInt(60) +100;
            ticksExisted =0;
            double[] pos = RailUtility.rotatePoint(offset, host.rotationPitch, host.rotationYaw, 0);
            this.boundingBox.setBounds(posX+pos[0] -0.1, posY+pos[1] -0.1, posZ+pos[2] -0.1, posX+pos[0] +0.1,  posY+pos[1] +0.1, posZ+pos[2] +0.1);
            motionX = (rand.nextInt(40) - 20) * 0.001f;
            motionY = this.offset[1] * 0.05;
            motionZ = (rand.nextInt(40) - 20) * 0.001f;
            shouldRender = true;
        } else if (this.ticksExisted > this.lifespan) {
            //if the transport isn't running and this has finished it's movement, set it' position to the transport and set that it shouldn't render.
            this.boundingBox.setBounds(posX, posY, posZ , posX,  posY, posZ);
            shouldRender = false;
            return;
        }

        //set the old motion values so we can compare them later.
        oldX = motionX;
        oldY = motionY;
        oldZ = motionZ;
        //instance a bounding box variable now so we won't have to cast as much later.
        AxisAlignedBB box;

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
     * @param posX the x position of the renderer
     * @param posY the y position of the renderer
     * @param posZ the z position of the renderer
     */
    public static void doRender(ParticleFX entity, double posX, double posY, double posZ) {
        if(entity.ticksExisted==0 || !entity.shouldRender){
            return;
        }
        GL11.glPushMatrix();
        //disabling texturing of GL will do some weird stuff.
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.startDrawing(GL11.GL_QUADS);
        //set the color with the tint.
        GL11.glColor3f((((entity.color >> 16 & 0xFF)/255.0F) - entity.colorTint),
                (((entity.color >> 8 & 0xFF)/255.0F) - entity.colorTint),
                (((entity.color & 0xFF)/255.0F) - entity.colorTint));
        //set the position
        GL11.glTranslated(posX - entity.posX, posY - entity.posY, posZ - entity.posZ);
        //now actually render the sides.
        tessellator.setNormal(0, 0, -1);
        tessellator.addVertex(entity.boundingBox.minX, entity.boundingBox.maxY, entity.boundingBox.minZ);
        tessellator.addVertex(entity.boundingBox.maxX, entity.boundingBox.maxY, entity.boundingBox.minZ);
        tessellator.addVertex(entity.boundingBox.maxX, entity.boundingBox.minY, entity.boundingBox.minZ);
        tessellator.addVertex(entity.boundingBox.minX, entity.boundingBox.minY, entity.boundingBox.minZ);
        tessellator.setNormal(0, 0, 1);
        tessellator.addVertex(entity.boundingBox.minX, entity.boundingBox.minY, entity.boundingBox.maxZ);
        tessellator.addVertex(entity.boundingBox.maxX, entity.boundingBox.minY, entity.boundingBox.maxZ);
        tessellator.addVertex(entity.boundingBox.maxX, entity.boundingBox.maxY, entity.boundingBox.maxZ);
        tessellator.addVertex(entity.boundingBox.minX, entity.boundingBox.maxY, entity.boundingBox.maxZ);
        tessellator.setNormal(0, -1, 0);
        tessellator.addVertex(entity.boundingBox.minX, entity.boundingBox.minY, entity.boundingBox.minZ);
        tessellator.addVertex(entity.boundingBox.maxX, entity.boundingBox.minY, entity.boundingBox.minZ);
        tessellator.addVertex(entity.boundingBox.maxX, entity.boundingBox.minY, entity.boundingBox.maxZ);
        tessellator.addVertex(entity.boundingBox.minX, entity.boundingBox.minY, entity.boundingBox.maxZ);
        tessellator.setNormal(0, 1, 0);
        tessellator.addVertex(entity.boundingBox.minX, entity.boundingBox.maxY, entity.boundingBox.maxZ);
        tessellator.addVertex(entity.boundingBox.maxX, entity.boundingBox.maxY, entity.boundingBox.maxZ);
        tessellator.addVertex(entity.boundingBox.maxX, entity.boundingBox.maxY, entity.boundingBox.minZ);
        tessellator.addVertex(entity.boundingBox.minX, entity.boundingBox.maxY, entity.boundingBox.minZ);
        tessellator.setNormal(-1, 0, 0);
        tessellator.addVertex(entity.boundingBox.minX, entity.boundingBox.minY, entity.boundingBox.maxZ);
        tessellator.addVertex(entity.boundingBox.minX, entity.boundingBox.maxY, entity.boundingBox.maxZ);
        tessellator.addVertex(entity.boundingBox.minX, entity.boundingBox.maxY, entity.boundingBox.minZ);
        tessellator.addVertex(entity.boundingBox.minX, entity.boundingBox.minY, entity.boundingBox.minZ);
        tessellator.setNormal(1, 0, 0);
        tessellator.addVertex(entity.boundingBox.maxX, entity.boundingBox.minY, entity.boundingBox.minZ);
        tessellator.addVertex(entity.boundingBox.maxX, entity.boundingBox.maxY, entity.boundingBox.minZ);
        tessellator.addVertex(entity.boundingBox.maxX, entity.boundingBox.maxY, entity.boundingBox.maxZ);
        tessellator.addVertex(entity.boundingBox.maxX, entity.boundingBox.minY, entity.boundingBox.maxZ);
        tessellator.draw();
        //before we end this be sure to re-enabling texturing for other things.
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();

    }
}
