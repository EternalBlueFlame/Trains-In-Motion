package trains.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import trains.TrainsInMotion;
import trains.entities.MinecartExtended;
import trains.utility.InventoryHandler;

public class GUITest extends GuiContainer {
    //id reference for this GUI
    public static final int GUI_ID = 20;
    //gui default construstor
    public GUITest (InventoryPlayer inventoryPlayer, MinecartExtended tileEntity) {
        //the container is instanciated and passed to the superclass for handling
        super(new InventoryHandler(inventoryPlayer, tileEntity));
    }
    //the drawing for the topmost layer
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        //draw text and stuff here
        //the parameters for drawString are: string, x, y, color
        fontRendererObj.drawString("Test Locomotive", 8, 6, 4210752);
        //draws the word "Inventory" or your regional equivalent
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }
    //the drawing for the back layer
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        //draw the gui background color
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        //set the texture.
        mc.renderEngine.bindTexture(new ResourceLocation(TrainsInMotion.enumResources.RESOURCES.getValue(), TrainsInMotion.enumResources.GUI_PREFIX.getValue() + "gui_example.png"));//TODO this image doesn't exist yet
        //set texture bounds.
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        //draw the texture
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

}