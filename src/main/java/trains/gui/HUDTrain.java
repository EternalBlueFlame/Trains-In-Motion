package trains.gui;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.entities.MinecartExtended;
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
            //GL11.glEnable(3042);
            //GL11.glEnable(32826);
            fontRendererObj.drawString("Test Locomotive", 8, 6, 4210752);
            //draw the gui background color
            //GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            //set the texture.
            game.renderEngine.bindTexture(new ResourceLocation(TrainsInMotion.MODID, TrainsInMotion.Resources.TEXTURE.getValue() + "null.png"));
            //draw the texture
            drawTexturedModalRect(0, 50, 0, 150, 137, 90);
            //GL11.glDisable(32826);
            //GL11.glDisable(3042);
        } else {
            game = this.mc = Minecraft.getMinecraft();
            this.fontRendererObj = this.mc.fontRenderer;
        }
        if (game == null){
            System.out.println("NULL");
        }
    }

}