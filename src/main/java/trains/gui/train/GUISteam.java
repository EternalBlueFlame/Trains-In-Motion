package trains.gui.train;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import trains.entities.EntityTrainCore;
import trains.registry.URIRegistry;
import trains.gui.trainhandler.SteamInventoryHandler;

public class GUISteam extends GuiContainer {
    //id reference for this GUI
    public static final int GUI_ID = 200;
    private static EntityTrainCore train;

    /**
     * instances the container for the inventory and passes it to the super
     * @see SteamInventoryHandler
     *
     * @param inventoryPlayer
     * @param entity
     */
    public GUISteam(InventoryPlayer inventoryPlayer, EntityTrainCore entity) {
        super(new SteamInventoryHandler(inventoryPlayer, entity));
        train = entity;
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
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 94, 4210752);
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
        //mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("guilocobackground.png"));
        //draw the texture
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