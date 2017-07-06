package ebf.tim.gui;

import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.networking.PacketKeyPress;
import ebf.tim.registry.URIRegistry;
import ebf.tim.utility.TileEntitySlotManager;
import ebf.tim.utility.TransportSlotManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;

import static ebf.tim.TrainsInMotion.transportTypes.PASSENGER;

/**
 * <h1>Transport GUI</h1>
 * used to draw the GUI for trains and rollingstock (the menu with the inventory).
 * @author Eternal Blue Flame
 */
public class GUITransport extends GuiContainer {

    /**a reference to the resource location of the vanilla furnace texture, this also gets overridden by texturepacks*/
    private static final ResourceLocation vanillaInventory = new ResourceLocation("textures/gui/container/furnace.png");
    /**a reference to the resource location of the vanilla chest texture, this also gets overridden by texturepacks*/
    private static final ResourceLocation vanillaChest = new ResourceLocation("textures/gui/container/generic_54.png");
    /**a reference to the host entity that this GUI is for.*/
    private static GenericRailTransport transport;
    /**a reference to the player that opened the GUI.*/
    private EntityPlayer player;
    /**a cached value for the second fuel tank, only used for steam currently*/
    private int secondTankFluid;
    /**the amount to scale the GUI by, same as vanilla*/
    private static final float guiScaler = 0.00390625F;
    /**the center position for the inventory render*/
    private int yCenter=0;

    private static DecimalFormat decimal = new DecimalFormat("#.##");

    /**
     * <h2>GUI initialization</h2>
     * instances the container for the inventory and passes it to the server side management
     * also puts the entity to a variable that can be accessed on client.
     * @see TileEntitySlotManager
     */
    public GUITransport(InventoryPlayer inventoryPlayer, GenericRailTransport entity) {
        super(new TransportSlotManager(inventoryPlayer, entity));
        player = inventoryPlayer.player;
        transport = entity;
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        //draw the text for trains
        if (transport instanceof EntityTrainCore) {
            fontRendererObj.drawString(StatCollector.translateToLocal(transport.getItem().getUnlocalizedName() + ".name"), -94, -30+yCenter, 4210752);
            fontRendererObj.drawString(I18n.format("container.inventory", new Object()), 110, 72, 4210752);
            //draw the text for transports with large inventories
        } else if (transport.getType() != PASSENGER && transport.getInventorySize().getRow()>5) {
            fontRendererObj.drawString(StatCollector.translateToLocal(transport.getItem().getUnlocalizedName() + ".name"), -94, -30+yCenter, 4210752);
            fontRendererObj.drawString(I18n.format("container.inventory", new Object()), 110, 70, 4210752);
            //draw the text for everything but the passenger car
        } else if (transport.getType() != PASSENGER){
            fontRendererObj.drawString(StatCollector.translateToLocal(transport.getItem().getUnlocalizedName() + ".name"), 8, 66-(transport.getInventorySize().getRow()*18), 4210752);
            fontRendererObj.drawString(I18n.format("container.inventory", new Object()), 8, 80, 4210752);
        }
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
    protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY) {
        //draw the gui background color
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (transport.getType().isTrain()){
            renderTrainInventory(guiTop,guiLeft, this.mc);
        } else if (transport != null){
            switch (transport.getType()){
                case PASSENGER:{renderPassengerInventory(guiTop,guiLeft, this.mc); break;}
                case LOGCAR: case FREIGHT: case HOPPER: case COALHOPPER: case GRAINHOPPER: {renderFreightInventory(guiTop,guiLeft, this.mc); break;}
                case TANKER:{renderTankerInventory(guiTop,guiLeft, this.mc);break;}
            }
        }
        GL11.glPopMatrix();

    }


    /**
     * <h2>Draw upper layer</h2>
     * this draws the upper layer of the GUI, and handles things like the tooltips.
     * Most of this is just checking if the cursor position is in the correct place for displaying tooltips.
     * TODO: maps are disabled
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float par3){
        super.drawScreen(mouseX, mouseY, par3);//should be useless with new buttons

        if (transport instanceof EntityTrainCore) {
            secondTankFluid = transport.getDataWatcher().getWatchableObjectInt(14);

            if ((mouseX >= guiLeft + 210 && mouseX <= guiLeft + 226 && mouseY >= guiTop - 14 && mouseY <= guiTop + 50)) {
                drawHoveringText(Arrays.asList(tankType(true), transport.getTankAmount() * 0.001f + StatCollector.translateToLocal("gui.of") + transport.getTankCapacity() * 0.001f, StatCollector.translateToLocal("gui.buckets")), mouseX, mouseY, fontRendererObj);
            }
            //draw the steam tank hover text
            if ((transport.getType() == TrainsInMotion.transportTypes.STEAM || transport.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM)) {
                GL11.glPushMatrix();
                fontRendererObj.drawString(decimal.format(transport.getDataWatcher().getWatchableObjectInt(15)*0.01f) + "C°", guiLeft+205, guiTop+72, 0);
                GL11.glPopMatrix();
                if ((mouseY >= guiTop - 14 && mouseY <= guiTop + 20) &&
                        ((mouseX >= guiLeft + 178 && mouseX <= guiLeft + 196) || (mouseX >= guiLeft + 240 && mouseX <= guiLeft + 258))) {
                    drawHoveringText(Arrays.asList(tankType(false), secondTankFluid * 0.001f + StatCollector.translateToLocal("gui.of") + transport.getTankCapacity() * 0.001f, StatCollector.translateToLocal("gui.buckets")), mouseX, mouseY, fontRendererObj);
                }
            }
        }

        //draw the hover text for the buttons.
        for (Object o : buttonList){
            if(((GUIButton)o).getMouseHover()) {
                drawHoveringText(((GUIButton)o).getDisplayString(transport), mouseX, mouseY, mc.fontRenderer);
            }
        }


    }



    /**
     * <h2>GUI initialization</h2>
     * the super defines the screen size and scale. beyond that we just initialize th buttons and their positions
     */
    @Override
    public void initGui() {
        super.initGui();
        yCenter = (int)((11-transport.getInventorySize().getRow())*0.5f)*18;
        if (!(transport instanceof EntityTrainCore) && transport.getInventorySize().getRow()<6) {
            //generic to all
            if (player.getEntityId() == transport.getOwnerID()) {
                this.buttonList.add(new GUIButton(13, guiLeft + 8, guiTop + 174, 18, 18, "unlink"));
                this.buttonList.add(new GUIButton(12, guiLeft + 62, guiTop + 174, 18, 18, "dropkey"));
            }
            this.buttonList.add(new GUIButton(6, guiLeft + 26, guiTop + 174, 18, 18, "locked"));
            this.buttonList.add(new GUIButton(7, guiLeft + 44, guiTop + 174, 18, 18, "coupler"));
        } else {
            //generic to all
            if (player.getEntityId() == transport.getOwnerID()) {
                this.buttonList.add(new GUIButton(13, guiLeft+112, guiTop + 166, 18, 18, "unlink"));
                this.buttonList.add(new GUIButton(12, guiLeft + 166, guiTop + 166, 18, 18, "dropkey"));
            }
            this.buttonList.add(new GUIButton(6, guiLeft + 130, guiTop + 166, 18, 18, "locked"));
            this.buttonList.add(new GUIButton(7, guiLeft + 148, guiTop + 166, 18, 18, "coupler"));
            if (transport.getLampOffset().yCoord>1) {
                this.buttonList.add(new GUIButton(5, guiLeft + 238, guiTop + 166, 18, 18, "lamp"));
            }
            //train specific
            if (transport instanceof EntityTrainCore) {
                if (player.capabilities.isCreativeMode) {
                    this.buttonList.add(new GUIButton(11, guiLeft + 184, guiTop + 166, 18, 18, "creative"));
                }
                if (transport.getType() != TrainsInMotion.transportTypes.STEAM) {
                    this.buttonList.add(new GUIButton(10, guiLeft + 202, guiTop + 166, 18, 18, "running"));
                }
                this.buttonList.add(new GUIButton(4, guiLeft + 220, guiTop + 166, 18, 18, "brake"));
                this.buttonList.add(new GUIButton(8, guiLeft + 256, guiTop + 166, 18, 18, "horn"));

            }
        }
    }

    /**
     * <h2>button overrides</h2>
     * here we define the overrides for the button functionality.
     */
    @Override
    public void actionPerformed(GuiButton button) {
        TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(button.id, transport.getEntityId()));
    }

    /**
     * <h3>gui Tank name</h3>
     * @return the name of the defined tank dependant in the current transport and the user's language.
     */
    private String tankType(boolean firsttank){
        switch (transport.getType()){
            case STEAM: case NUCLEAR_STEAM:{
                if (firsttank){
                    return StatCollector.translateToLocal("gui.waterboiler") + ":";
                } else {
                    return StatCollector.translateToLocal("menu.item.steam") + ":";
                }
            }
            case DIESEL:{return StatCollector.translateToLocal("gui.diesel") + ":";}
            case ELECTRIC: case MAGLEV:{return StatCollector.translateToLocal("gui.rf") + ":";}
            case HYDROGEN_DIESEL:{return StatCollector.translateToLocal("gui.hydrogencell") + ":";}
            case NUCLEAR_ELECTRIC:{return StatCollector.translateToLocal("gui.coolant") + ":";}
            default:{return "Unknown Tank";}
        }
    }


    /**
     * <h2> render train inventory</h2>
     * due to how many GUI's this class covers, it's easier to just separate them by the type of transport.
     * we make this a static function for efficiency, so we have to make the function call feed the super's variables in.
     * @param guiTop the guiTop variable defined in the super.
     * @param guiLeft the guiLeft variable defined in the super.
     * @param mc the minecraft instance defined in the super.
     */
    private void renderTrainInventory(int guiTop, int guiLeft,Minecraft mc){
        //main background TODO disabled until we actually have an image for it.
        //draw the background
        //drawTexturedRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);

        //bind the inventory image which we use the slot images and inventory image from.
        mc.getTextureManager().bindTexture(vanillaInventory);
        //icon for furnace fuel
        drawTexturedRect(guiLeft+112, guiTop + 1, 56, 36, 20, 20, 16, 16);

        //icon for furnace
        int i1 = transport.getDataWatcher().getWatchableObjectInt(13);
        if (i1>0) {
            drawTexturedRect(guiLeft + 113, guiTop + 16 - i1, 176, 13 - i1, 17, i1+2, 14, i1);
        }

        //slot for fuel
        drawTexturedRect(guiLeft+112, guiTop + 30, 54, 51, 20, 20);
        //slot for water
        drawTexturedRect(guiLeft+148, guiTop + 30, 54, 51, 20, 20);

        //draw the player inventory and toolbar background.
        drawTexturedRect(guiLeft+105, guiTop+ 74, 0, 74, 176, 100);
        drawTexturedRect(guiLeft+105, guiTop+ 68, 0, 0, 176, 6);

        mc.getTextureManager().bindTexture(vanillaChest);
        drawTexturedRect(guiLeft-105, guiTop-37+yCenter, 0, 0, this.xSize, 17);//top
        for(int i=0; i<transport.getInventorySize().getRow(); i++){
            drawTexturedRect(guiLeft-105, i*18+ (guiTop-20)+yCenter, 0, 17, this.xSize, 18);
        }
        drawTexturedRect(guiLeft-105,(transport.getInventorySize().getRow())*18+ (guiTop-20)+yCenter, 0, 215, this.xSize, 8);//bottom



        //draw the tanks
        mc.getTextureManager().bindTexture(URIRegistry.GUI_PREFIX.getResource("gui.png"));
        //liquid fuel tank
        drawTexturedRect(guiLeft + 210, guiTop - 14, 0, 0, 18, 64, 16, 16);
        if (transport.getTankAmount()>0) {
            //draw the water tank
            int liquid = Math.abs((transport.getTankAmount() * 64) / transport.getTankCapacity());
            drawTexturedRect(guiLeft+ 210, guiTop + 50 - liquid, 16,0, 18, liquid, 16, 16);
        }
        //steam tank
        if (transport.getType() == TrainsInMotion.transportTypes.STEAM || transport.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM) {
            drawTexturedRect(guiLeft + 178, guiTop-14, 0, 0, 18, 30, 16, 16);
            drawTexturedRect(guiLeft + 240, guiTop-14, 0, 0, 18, 30, 16, 16);
            if (secondTankFluid>0) {
                int liquid3 = Math.abs((secondTankFluid * 30) / transport.getTankCapacity());
                drawTexturedRect(guiLeft+178, guiTop +18 - liquid3, 32,0, 18, liquid3, 16, 16);
                drawTexturedRect(guiLeft+240, guiTop +18 - liquid3, 32,0, 18, liquid3, 16, 16);
            }
        }
    }


    /**
     * <h2>Render Passenger GUI</h2>
     * basically the same as
     */
    private void renderPassengerInventory(int guiTop, int guiLeft, Minecraft mc){

        int rows=0;
        //draw the character backgrounds.
        mc.getTextureManager().bindTexture(vanillaInventory);
        //make a loop that will make a new row every 5 items
        for (int i=0;i<transport.getRiderOffsets().length; i++) {
            if (i/(rows+1) >=5*(rows+1)){
                rows++;
            }
            drawTexturedRect(guiLeft + 7 + (30*(i-(rows * 5))), guiTop + 30+(30*rows), 54, 51, 27, 27, 20, 20);
        }
        //make a new loop that does the same as above but binds the character's face rather than the inventory slot background.
        for (int i=0;i<transport.getRiderOffsets().length; i++) {
            if (i/(rows+1) >=5*(rows+1)){
                rows++;
            }
            if (i==0 && transport.riddenByEntity instanceof AbstractClientPlayer){
                mc.getTextureManager().bindTexture(((AbstractClientPlayer) transport.riddenByEntity).getLocationSkin());
                drawTexturedRect(guiLeft + 10 + (30*(i-(rows * 5))), guiTop + 32+(30*rows), 30, 70, 22, 22, 36, 56);
            } else if (i>0 && transport.seats.get(i-1).riddenByEntity instanceof AbstractClientPlayer){
                mc.getTextureManager().bindTexture(((AbstractClientPlayer) transport.seats.get(i-1).riddenByEntity).getLocationSkin());
                drawTexturedRect(guiLeft + 10 + (30*(i-(rows * 5))), guiTop + 32+(30*rows), 30, 70, 22, 22, 36, 56);
            }
        }

    }

    /**
     * <h2>Render Freight GUI</h2>
     * NOTE: this is only designed for inventory sized with 9 columns.
     */
    private void renderFreightInventory(int guiTop, int guiLeft, Minecraft mc){
        mc.getTextureManager().bindTexture(vanillaChest);
        //draw the player inventory and toolbar background.
        if (transport.getInventorySize().getRow()<6){
            drawTexturedRect(guiLeft, guiTop+77-(transport.getInventorySize().getRow()*18)-17, 0, 0, this.xSize, transport.getInventorySize().getRow() * 18 + 17);
            drawTexturedRect(guiLeft, guiTop+77, 0, 126, this.xSize, 96);
        } else {
            drawTexturedRect(guiLeft-105, guiTop-37+yCenter, 0, 0, this.xSize, 17);//top
            for(int i=0; i<transport.getInventorySize().getRow(); i++){
                drawTexturedRect(guiLeft-105, i*18+ (guiTop-20)+yCenter, 0, 17, this.xSize, 18);
            }
            drawTexturedRect(guiLeft-105,(transport.getInventorySize().getRow())*18+ (guiTop-20)+yCenter, 0, 215, this.xSize, 8);//bottom

            drawTexturedRect(guiLeft+105, guiTop+64, 0, 0, this.xSize,  16);//top
            drawTexturedRect(guiLeft+105,   guiTop+70, 0, 126, this.xSize, 96);//actual inventory
        }
    }


    /**
     * <h2>Render Tanker GUI</h2>
     * basically the same as
     */
    private void renderTankerInventory(int guiTop, int guiLeft, Minecraft mc){
        //draw the player inventory and toolbar background.
        mc.getTextureManager().bindTexture(vanillaChest);
        drawTexturedRect(guiLeft, guiTop+ 79, 0, 128, xSize, 94);
        drawTexturedRect(guiLeft, guiTop+ 76, 0, 0, xSize, 8);

        mc.getTextureManager().bindTexture(URIRegistry.GUI_PREFIX.getResource("gui.png"));
        drawTexturedRect(guiLeft+28, guiTop, 0, 0, 100, 64, 16, 16);
        if (transport.getTankAmount()>0) {
            //draw the water tank
            int liquid = Math.abs((transport.getTankAmount() * 64) / transport.getTankCapacity());
            drawTexturedRect(guiLeft+ 28, guiTop + 64 - liquid, 16,0, 100, liquid, 16, 16);
        }

        mc.getTextureManager().bindTexture(vanillaInventory);
        drawTexturedRect(guiLeft+70, guiTop - 30, 54, 51, 20,20);
        drawTexturedRect(guiLeft+150, guiTop + 44, 54, 51, 20,20);
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
     * @param widthUV defines the X size of the texture part used
     * @param heightUV defines the X Y size of the texture part used
     */
    public static void drawTexturedRect(int posX, int posY, int posU, int posV, int width, int height, int widthUV, int heightUV) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(posX, posY + height, 0, posU * guiScaler, (posV + heightUV) * guiScaler);
        tessellator.addVertexWithUV(posX + width, posY + height, 0, (posU + widthUV) * guiScaler, (posV + heightUV) * guiScaler);
        tessellator.addVertexWithUV(posX + width, posY, 0, (posU + widthUV) * guiScaler, posV * guiScaler);
        tessellator.addVertexWithUV(posX, posY, 0, posU * guiScaler, posV * guiScaler);
        tessellator.draw();
    }
    public static void drawTexturedRect(int posX, int posY, int posU, int posV, int width, int height) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(posX, posY + height, 0, posU * guiScaler, (posV + height) * guiScaler);
        tessellator.addVertexWithUV(posX + width, posY + height, 0, (posU + width) * guiScaler, (posV + height) * guiScaler);
        tessellator.addVertexWithUV(posX + width, posY, 0, (posU + width) * guiScaler, posV * guiScaler);
        tessellator.addVertexWithUV(posX, posY, 0, posU * guiScaler, posV * guiScaler);
        tessellator.draw();
    }


}