package trains.gui;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import trains.entities.EntityTrainCore;
import trains.entities.GenericRailTransport;
import trains.gui.train.GUITrain;
import trains.registry.URIRegistry;

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
     * @see GUITrain
     * The difference is that we override the experience bar render so that we can render the GUI alongside that, like a HUD.
     * @see GuiScreen
     * Some IDE's may report this function is unused, but it is actually used indirectly by minecraft's event manager, for this reason the warning was supressed.
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    @SuppressWarnings("unused")
    public void onRenderExperienceBar(RenderGameOverlayEvent event) {
        if (game != null && game.thePlayer != null && game.thePlayer.ridingEntity instanceof EntityTrainCore) {
            fontRendererObj.drawString(StatCollector.translateToLocal(((GenericRailTransport)game.thePlayer.ridingEntity).getItem().getUnlocalizedName().replace("item","entity")), 8, 6, 4210752);
            //draw the gui background color
            //GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            //set the texture.
            game.renderEngine.bindTexture(URIRegistry.TEXTURE_GENERIC.getResource("null.png"));
            //draw the texture
            drawTexturedModalRect(0, 50, 0, 150, 137, 90);
        } else {
            game = mc = Minecraft.getMinecraft();
            fontRendererObj = mc.fontRenderer;
        }
    }

}