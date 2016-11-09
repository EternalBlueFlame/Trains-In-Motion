package trains.gui;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import trains.entities.EntityTrainCore;
import trains.registry.URIRegistry;
import trains.gui.trainhandler.SteamInventoryHandler;

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
     * @see trains.gui.train.GUISteam
     * The difference is that we override the experience bar render so that we can render the GUI alongside that, like a HUD.
     * @see GuiScreen
     */
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRenderExperienceBar(RenderGameOverlayEvent event) {
        if (game != null && game.thePlayer != null && game.thePlayer.ridingEntity != null && game.thePlayer.ridingEntity instanceof EntityTrainCore) {
            fontRendererObj.drawString("Test Locomotive", 8, 6, 4210752);
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