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

import java.util.Arrays;

public class GUITrain extends GuiContainer {
    private EntityTrainCore train;
    private static final float guiScaler = 0.00390625F;
    private static int interfaceWidth =0;
    private static int interfaceHeight = 0;

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
        fontRendererObj.drawString("Test Locomotive", 8, -18, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, interfaceHeight+33, 4210752);
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
    protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY) {
        //draw the gui background color
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

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
            for (int ir = 0; ir > -train.getInventorySize().getCollumn(); ir--) {
                drawTexturedModalRect( interfaceWidth + 97 + (ic * 18), interfaceHeight +43 + (ir * 18), 0, 64, 18, 18, 16);
            }
        }


        drawTexturedModalRect(interfaceWidth + 66, interfaceHeight + 10, 0, 0, 18, 50, 16);
        if (train.tanks.getTank(true).getFluidAmount()>0) {
            //draw the water tank
            int liquid = Math.abs((train.tanks.getTank(true).getFluidAmount() * 50) / train.tanks.getTank(true).getCapacity());
            drawTexturedModalRect(interfaceWidth + 66, interfaceHeight + 60 - liquid, 16,0, 18, liquid, 16);
        }

        if (train.getType() == TrainsInMotion.transportTypes.STEAM || train.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM) {
            //draw the background
            drawTexturedModalRect(interfaceWidth + 34, interfaceHeight+10, 0, 0, 18, 30, 16);
            if (train.tanks.getTank(false).getFluidAmount()>0) {
                //draw the steam tank
                int liquid3 = Math.abs((train.tanks.getTank(false).getFluidAmount() * 30) / train.tanks.getTank(false).getCapacity());
                drawTexturedModalRect(interfaceWidth + 34, interfaceHeight +50 - liquid3, 32,0, 18, liquid3, 16);
            }
        }

        //train toolbar slots
        for (int iT = 0; iT < 5; iT++) {
            drawTexturedModalRect(interfaceWidth + 70 + (iT * 18), interfaceHeight + 63, 0, 64, 18, 18, 16);
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


    /**
     * <h2>Draw upper layer</h2>
     * this draws the upper layer of the GUI, and handles things like the tooltips.
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float par3){
        //define the width and height of the screen.
        interfaceWidth = (width - xSize) / 2;
        interfaceHeight = (height - ySize) / 2;

        super.drawScreen(mouseX, mouseY, par3);

        if ((mouseX >= interfaceWidth + 66 && mouseX <= interfaceWidth + 84 &&
                mouseY >= interfaceHeight + 10 && mouseY <= interfaceHeight +60)) {
            drawHoveringText(Arrays.asList(tankType(true), train.tanks.getTank(true).getFluidAmount()*0.001f + " of " + train.tanks.getTank(true).getCapacity()*0.001f, "Buckets"), mouseX, mouseY, fontRendererObj);
        }

        if ((train.getType() == TrainsInMotion.transportTypes.STEAM || train.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM) &&
                (mouseX >= interfaceWidth + 34 && mouseX <= interfaceWidth + 52 &&
                        mouseY >= interfaceHeight + 10 && mouseY <= interfaceHeight +40)) {
            drawHoveringText(Arrays.asList(tankType(false), train.tanks.getTank(true).getFluidAmount()*0.001f + " of " + train.tanks.getTank(true).getCapacity()*0.001f, "Buckets"), mouseX, mouseY, fontRendererObj);
        }
    }

    private String tankType(boolean firsttank){
        switch (train.getType()){
            case STEAM: case NUCLEAR_STEAM:{
                if (firsttank){
                    return "Water Boiler:";
                } else {
                    return "Steam:";
                }
            }
            case DIESEL:{
                return "Diesel:";
            }
            case ELECTRIC: case MAGLEV:{
                return "Redstone Flux:";
            }
            case HYDROGEN_DIESEL:{
                return "Hydrogen Cell:";
            }
            case NUCLEAR_ELECTRIC:{
                return "Coolant:";
            }
            default:{return "";}
        }
    }
}