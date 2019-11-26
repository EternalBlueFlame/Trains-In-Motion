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

    GenericRailTransport t;

    @Override
    public void doRender(AbstractClientPlayer p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_){
        if (p_76986_1_.ridingEntity instanceof GenericRailTransport) {
            t=(GenericRailTransport) p_76986_1_.ridingEntity;
            GL11.glPushMatrix();
            GL11.glScalef(t.getPlayerScale(), t.getPlayerScale(), t.getPlayerScale());
            super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
            GL11.glPopMatrix();

        } else if (p_76986_1_.ridingEntity instanceof EntitySeat){
            t=(GenericRailTransport) p_76986_1_.worldObj.getEntityByID(((EntitySeat) p_76986_1_.ridingEntity).parentId);
            GL11.glPushMatrix();
            GL11.glScalef(t.getPlayerScale(), t.getPlayerScale(), t.getPlayerScale());
            if(p_76986_1_.ridingEntity.getLookVec() !=null) {
                GL11.glRotated(p_76986_1_.ridingEntity.getLookVec().xCoord, 0, 1, 0);
                GL11.glRotated(p_76986_1_.ridingEntity.getLookVec().yCoord, 0, 0, 1);
                GL11.glRotated(p_76986_1_.ridingEntity.getLookVec().zCoord, 1, 0, 0);
            }
            super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
            GL11.glPopMatrix();

        } else {
            super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        }

    }

}
