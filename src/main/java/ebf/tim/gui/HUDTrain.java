package ebf.tim.gui;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.entities.trains.EntityBrigadelok080;
import ebf.tim.registry.URIRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

/**
 * <h1>Train HUD</h1>
 * the heads up display which shows the train's stats whenever there is a player riding it.
 * @author Eternal Blue Flame
 */
public class HUDTrain extends GuiScreen {
    private Minecraft game;

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }


    /**
     * <h2>Loco HUD</h2>
     * similar to the GUI, this will draw a HUD for the train when someone is in it.
     * @see GUITransport
     * The difference is that we override the experience bar render so that we can render the GUI alongside that, like a HUD.
     * @see GuiScreen
     * Some IDE's may report this function is unused, but it is actually used indirectly by minecraft's event manager, for this reason the warning was supressed.
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    @SuppressWarnings("unused")
    public void onRenderExperienceBar(RenderGameOverlayEvent event) {
        if (game != null && game.thePlayer != null) {
            if (game.thePlayer.ridingEntity instanceof EntityTrainCore) {
                EntityTrainCore trainEntity = (EntityTrainCore) game.thePlayer.ridingEntity;

                fontRendererObj.drawString(StatCollector.translateToLocal(trainEntity.getItem().getUnlocalizedName()), 8, 6, 4210752);
                fontRendererObj.drawString("DEBUG INFO:", 8, 16, 4210752);
                fontRendererObj.drawString("Accelerator State: " + -trainEntity.getDataWatcher().getWatchableObjectInt(18), 8, 26, 4210752);
                String speed = ""+Math.abs((Math.abs(trainEntity.motionX)>Math.abs(trainEntity.motionZ)?trainEntity.motionX:trainEntity.motionZ) * 27.75);
                fontRendererObj.drawString("speed: " + (speed.length()>5?speed.substring(0,5):speed) + " km/h", 8, 36, 4210752);
                fontRendererObj.drawString((trainEntity instanceof EntityBrigadelok080) ? "Texture State: Incomplete." : "Texture State: Texture Not Implemented.", 8, 46, 4210752);
                //draw the gui background color
                //GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                //set the texture.
                //game.renderEngine.bindTexture(URIRegistry.TEXTURE_GENERIC.getResource("null.png"));
                //draw the texture
                //drawTexturedModalRect(0, 50, 0, 150, 137, 90);
            }
        } else {
            game = mc = Minecraft.getMinecraft();
            fontRendererObj = mc.fontRenderer;
        }
    }

}