package trains.gui.train;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.gui.trainhandler.SteamInventoryHandler;
import trains.registry.URIRegistry;

public class GUITrain extends GuiContainer {
    private EntityTrainCore train;
    private static final float guiScaler = 0.00390625F;

    /**
     * <h2>GUI initialization</h2>
     * instances the container for the inventory and passes it to the server side management
     * also puts the entity to a variable that can be accessed on client.
     * @see SteamInventoryHandler
     */
    public GUITrain(InventoryPlayer inventoryPlayer, EntityTrainCore entity) {
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
     * Item scale is defined by: x,y,u,v,width,height
     *
     * xSize, ySize, width, and height are defined in the super
     * @see GuiContainer
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        //draw the gui background color
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        //define the width and height of the screen.
        int interfaceWidth = (width - xSize) / 2;
        int interfaceHeight = (height - ySize) / 2;

        //main background TODO disabled until we actually have an image for it.
        //draw the background
        mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("gui.png"));

        //drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);

        //icon for fuel
        drawTexturedModalRect(interfaceWidth + 7, interfaceHeight + 25, 0, 64, 18, 18, 16);
        //icon for furnace
        drawTexturedModalRect(interfaceWidth + 7, interfaceHeight + 25, 0, 64, 18, 18, 16);

        //set the generic slot icon, which will get re-used for every slot.

        //slot for fuel
        drawTexturedModalRect(interfaceWidth + 7, interfaceHeight + 52, 0, 64, 18, 18, 16);
        //slot for water
        drawTexturedModalRect(interfaceWidth + 34, interfaceHeight + 52, 0, 64, 18, 18, 16);
        //player inventory slots
        for (int ic = 0; ic < 9; ic++) {
            for (int ir = 0; ir < 3; ir++) {
                drawTexturedModalRect( interfaceWidth + 7 + (ic * 18), interfaceHeight + 83 + (ir * 18), 0, 64, 18, 18, 16);
            }
        }
        //player toolbar slots
        for (int iT = 0; iT < 9; iT++) {
            drawTexturedModalRect(interfaceWidth + 7 + (iT * 18), interfaceHeight + 141, 0, 64, 18, 18, 16);
        }

        //train inventory
        for (int ic = 0; ic < train.getInventorySize().getRow(); ic++) {
            for (int ir = 0; ir < train.getInventorySize().getCollumn(); ir++) {
                drawTexturedModalRect( interfaceWidth + 97 + (ic * 18), interfaceHeight + 7 + (ir * 18), 0, 64, 18, 18, 16);
            }
        }


        drawTexturedModalRect(interfaceWidth + 66, interfaceHeight + 20, 0, 0, 18, 50, 16);
        if (train.getTank().canDrain(1,0)) {
            //draw the water tank
            int liquid = Math.abs((train.getTank().tankFluidAmount(0) * 50) / train.getTank().tankCapacity(0));
            drawTexturedModalRect(interfaceWidth + 66, interfaceHeight + 20 - liquid, 0,16, 18, liquid, 16);
        }

        if (train.getType() == TrainsInMotion.transportTypes.STEAM || train.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM) {
            //draw the background
            drawTexturedModalRect(interfaceWidth + 34, interfaceHeight+20, 0, 0, 18, 24, 16);
            if (train.getTank().canDrain(1, 1)) {
                //draw the steam tank
                int liquid3 = Math.abs((train.getTank().tankFluidAmount(1) * 24) / train.getTank().tankCapacity(1));
                drawTexturedModalRect(interfaceWidth + 34, interfaceHeight +20 - liquid3, 0,32, 18, liquid3, 16);
            }
        }

    }

    /**
     * <h2>Draw Texture</h2>
     * This replaces the base class and allows us to draw textures that are stretched to the shape defined in a more efficient manner.
     * NOTE: all textures must be divisible by 256x256
     * @param posX the X position on screen to draw at.
     * @param posY the Y position on screen to draw at.
     * @param posU the X position of the texture to start from.
     * @param posV the Y position of the texture to start from.
     * @param width the width of the box.
     * @param height the height of the box.
     * @param scale defines the X and Y size of the texture part used
     */
    public void drawTexturedModalRect(int posX, int posY, int posU, int posV, int width, int height, int scale) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(posX, posY + height, this.zLevel, posU * guiScaler, (posV + scale) * guiScaler);
        tessellator.addVertexWithUV(posX + width, posY + height, this.zLevel, (posU + scale) * guiScaler, (posV + scale) * guiScaler);
        tessellator.addVertexWithUV(posX + width, posY, this.zLevel, (posU + scale) * guiScaler, posV * guiScaler);
        tessellator.addVertexWithUV(posX, posY, this.zLevel, posU * guiScaler, posV * guiScaler);
        tessellator.draw();
    }
}