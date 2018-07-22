package ebf.tim.models;

import ebf.tim.entities.EntitySeat;
import ebf.tim.entities.GenericRailTransport;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.GL11;

/**
 * <h2>Player render modification</h2>
 * In the case of a player riding one of our entities, we scale down the player model to 75* of normal size so it fits better.
 * @author Eternal Blue Flame
 */
public class RenderScaledPlayer extends RenderPlayer {

    @Override
    public void doRender(AbstractClientPlayer p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_){
        if (p_76986_1_.ridingEntity instanceof GenericRailTransport) {
            GL11.glPushMatrix();
            GL11.glScaled(0.75d, 0.75d, 0.75d);
            super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
            GL11.glPopMatrix();

        } else if (p_76986_1_.ridingEntity instanceof EntitySeat){
            GL11.glPushMatrix();
            GL11.glScaled(0.75d, 0.75d, 0.75d);
            //todo: correct the rotation values and also add support in the core entity, not just the seat
            GL11.glRotatef(p_76986_1_.ridingEntity.rotationPitch, 0,1,0);
            GL11.glRotatef(p_76986_1_.ridingEntity.rotationYaw, 0,1,0);
            GL11.glRotatef(((EntitySeat)p_76986_1_.ridingEntity).rotationRoll, 0,1,0);
            super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
            GL11.glPopMatrix();

        } else {
            super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        }

    }

}
