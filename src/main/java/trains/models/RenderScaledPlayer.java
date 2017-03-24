package trains.models;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.GL11;
import trains.entities.EntitySeat;
import trains.entities.GenericRailTransport;

/**
 * <h2>Player render modification</h2>
 * In the case of a player riding one of our entities, we scale down the player model to fit better.
 * @author Eternal Blue Flame
 */
public class RenderScaledPlayer extends RenderPlayer {

    @Override
    public void doRender(AbstractClientPlayer p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_){
        if (p_76986_1_.ridingEntity instanceof GenericRailTransport || p_76986_1_.ridingEntity instanceof EntitySeat) {
            GL11.glScaled(0.75d, 0.75d, 0.75d);
        }
        super.doRender(p_76986_1_,p_76986_2_,p_76986_4_,p_76986_6_,p_76986_8_,p_76986_9_);

    }

}
