package trains.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import trains.entities.MinecartExtended;
import trains.registry.URIRegistry;
import trains.utility.InventoryHandler;

public class GUITrain extends GuiContainer {
    //id reference for this GUI
    public static final int GUI_ID = 200;

    /**
     * instances the container for the inventory and passes it to the super
     * @see InventoryHandler
     *
     * @param inventoryPlayer
     * @param tileEntity
     */
    public GUITrain(InventoryPlayer inventoryPlayer, MinecartExtended tileEntity) {
        super(new InventoryHandler(inventoryPlayer, tileEntity));
    }

    /**
     * this draws the topmost layer, closest to screen.
     * most of the text will be managed here via draw string.
     * some other things will display here like the boiler box graphic and the fluid tank outlines.
     *
     * StatCollector.translateToLocal("container.inventory") is used to return the word for "inventory" defined by the client.
     *
     * things later in the function render closer to the screen
     * xSize and ySize are defined in the super
     * @see GuiContainer
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        fontRendererObj.drawString("Test Locomotive", 8, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    /**
     * this draws the lower layer, good for backgrounds and things to go behind the forground stuff
     * TODO this image doesn't exist yet
     *
     * things later in the function render closer to the screen
     *
     * xSize, ySize, width, and height are defined in the super
     * @see GuiContainer
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        //draw the gui background color
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        //set the texture for the background.
        mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("guilocobackground.png"));
        //draw the texture
        drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);

        //repeat for the inventory slots of the train.
        mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("itemslot.png"));
        for (int ic = 0; ic < 9; ic++) {
            for (int ir = 0; ir < 3; ir++) {
                drawTexturedModalRect( 8 + (ic * 18),  84 + (ir * 18), 0, 0, xSize, ySize);
            }
        }
        //since slots maintain the same texture we won't have to bind a new one to draw the slots for the toolbar.
        for (int iT = 0; iT < 9; iT++) {
            drawTexturedModalRect( 8 + (iT * 18), 142, 0, 0, xSize, ySize);
        }
    }

}