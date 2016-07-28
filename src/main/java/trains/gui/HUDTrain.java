package trains.gui;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import trains.entities.EntityTrainCore;
import trains.registry.URIRegistry;
import trains.utility.InventoryHandler;

public class HUDTrain extends GuiScreen {
    //id reference for this GUI
    public static final int GUI_ID = 201;
    private Minecraft game;

    /**
     * instances the container for the inventory and passes it to the super
     * @see InventoryHandler
     *
     */

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
    /**
     * this draws the topmost layer, closest to screen.
     * most of the text will be managed here via draw string.
     * some other things will display here like the boiler box graphic and the fluid tank outlines.
     *
     * StatCollector.translateToLocal("container.inventory") is used to return the word for "inventory" defined by the client.
     *
     * things later in the function render closer to the screen
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
            game = this.mc = Minecraft.getMinecraft();
            this.fontRendererObj = this.mc.fontRenderer;
        }
    }

}