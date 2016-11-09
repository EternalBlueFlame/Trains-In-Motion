package trains.gui.train;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import trains.entities.EntityTrainCore;
import trains.registry.URIRegistry;
import trains.gui.trainhandler.SteamInventoryHandler;

public class GUISteam extends GuiContainer {
    private EntityTrainCore train;

    /**
     * <h2>GUI initialization</h2>
     * instances the container for the inventory and passes it to the server side management
     * also puts the entity to a variable that can be accessed on client.
     * @see SteamInventoryHandler
     */
    public GUISteam(InventoryPlayer inventoryPlayer, EntityTrainCore entity) {
        super(new SteamInventoryHandler(inventoryPlayer, entity));
        train = entity;
    }

    /**
     * <h2>GUI foreground layer</h2>
     * this draws the topmost layer, closest to screen.
     * things later in the function render closer to the screen
     *
     * StatCollector.translateToLocal is used for translation, this is usually defined in minecraft itself.
     * xSize and ySize are defined in the super
     * @see GuiContainer
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        fontRendererObj.drawString("Test Locomotive", 8, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 94, 4210752);
    }

    /**
     * <h2>GUI Background</h2>
     * this draws the lower layer, good for backgrounds and things to go behind the foreground stuff
     * things later in the function render closer to the screen
     * Textures must be bound to the render, then every texture draw after will re-use that same texture, when you want a new texture, bind a new one.
     *
     * xSize, ySize, width, and height are defined in the super
     * @see GuiContainer
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        //draw the gui background color
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        //main background
        //mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("guilocobackground.png"));
        //drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);

        //icon for fuel
        mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("steam_fuelbackground.png"));
        drawTexturedModalRect(((width - xSize) / 2) + 7, ((height - ySize) / 2) + 25, 0, 0, 18, 18);
        //icon for furnace
        mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("steam_fuelicon.png"));
        drawTexturedModalRect(((width - xSize) / 2) + 7, ((height - ySize) / 2) + 25, 0, 0, 18, 18);

        //set the generic slot icon, which will get re-used for every slot.
        mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("itemslot.png"));

        //slot for fuel
        drawTexturedModalRect(((width - xSize) / 2) + 7, ((height - ySize) / 2) + 52, 0, 0, 18, 18);

        //slot for water
        drawTexturedModalRect(((width - xSize) / 2) + 34, ((height - ySize) / 2) + 52, 0, 0, 18, 18);

        //player inventory slots
        for (int ic = 0; ic < 9; ic++) {
            for (int ir = 0; ir < 3; ir++) {
                drawTexturedModalRect( ((width - xSize) / 2) + 7 + (ic * 18), ((height - ySize) / 2) + 83 + (ir * 18), 0, 0, 18, 18);
            }
        }
        //player toolbar slots
        for (int iT = 0; iT < 9; iT++) {
            drawTexturedModalRect(((width - xSize) / 2) + 7 + (iT * 18), ((height - ySize) / 2) + 141, 0, 0, 18, 18);
        }


        //train's inventory
        for (int ic = 0; ic < 3; ic++) {
            for (int ir = 0; ir < 3; ir++) {
                drawTexturedModalRect( ((width - xSize) / 2) + 97 + (ic * 18), ((height - ySize) / 2) + 7 + (ir * 18), 0, 0, 18, 18);
            }
        }

    }

}