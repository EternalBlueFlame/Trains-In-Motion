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

import java.util.Arrays;
import java.util.Collections;

import static ebf.tim.TrainsInMotion.transportTypes.*;

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
    /**a cached value for the first fluid tank (fuel, water, etc).*/
    private int firstTankFluid;
    /**a cached value for the second fuel tank, only used for steam currently*/
    private int secondTankFluid;
    /**the amount to scale the GUI by, same as vanilla*/
    private static final float guiScaler = 0.00390625F;

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
        fontRendererObj.drawString(StatCollector.translateToLocal(transport.getItem().getUnlocalizedName() + ".name"), -94, -30, 4210752);
        if (transport.getType() != PASSENGER) {
            fontRendererObj.drawString(I18n.format("container.inventory", new Object()), 110, -30, 4210752);
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
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (transport.getType().isTrain()){
            renderTrainInventory(guiTop,guiLeft, this.mc, firstTankFluid, secondTankFluid);
        } else if (transport != null){
            switch (transport.getType()){
                case PASSENGER:{renderPassengerInventory(guiTop,guiLeft, this.mc); break;}
                case LOGCAR: case FREIGHT: case HOPPER: case COALHOPPER: case GRAINHOPPER: {renderFreightInventory(guiTop,guiLeft, this.mc); break;}
                case TANKER:{renderTankerInventory(guiTop,guiLeft, this.zLevel, this.mc);break;}
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
     * @param scaleU defines the X size of the texture part used
     * @param scaleV defines the X Y size of the texture part used
     */
    public static void drawTexturedRect(int posX, int posY, int posU, int posV, int width, int height, int scaleU, int scaleV) {
        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.addVertexWithUV(posX, posY + height, 0, posU * guiScaler, (posV + scaleV) * guiScaler);
        Tessellator.instance.addVertexWithUV(posX + width, posY + height, 0, (posU + scaleU) * guiScaler, (posV + scaleV) * guiScaler);
        Tessellator.instance.addVertexWithUV(posX + width, posY, 0, (posU + scaleU) * guiScaler, posV * guiScaler);
        Tessellator.instance.addVertexWithUV(posX, posY, 0, posU * guiScaler, posV * guiScaler);
        Tessellator.instance.draw();
    }
    public static void drawTexturedRect(int posX, int posY, int posU, int posV, int width, int height) {
        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.addVertexWithUV(posX, posY + height, 0, posU * guiScaler, (posV + height) * guiScaler);
        Tessellator.instance.addVertexWithUV(posX + width, posY + height, 0, (posU + width) * guiScaler, (posV + height) * guiScaler);
        Tessellator.instance.addVertexWithUV(posX + width, posY, 0, (posU + width) * guiScaler, posV * guiScaler);
        Tessellator.instance.addVertexWithUV(posX, posY, 0, posU * guiScaler, posV * guiScaler);
        Tessellator.instance.draw();
    }


    /**
     * <h2>Draw upper layer</h2>
     * this draws the upper layer of the GUI, and handles things like the tooltips.
     * Most of this is just checking if the cursor position is in the correct place for displaying tooltips.
     * TODO: maps are disabled
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float par3){
        super.drawScreen(mouseX, mouseY, par3);
        if (transport.getType() == STEAM || transport.getType() == NUCLEAR_STEAM || transport.getType() == TANKER || transport.getType() == TENDER) {
            firstTankFluid = transport.getDataWatcher().getWatchableObjectInt(20);
        }

        if (transport instanceof EntityTrainCore) {
            secondTankFluid = ((EntityTrainCore) transport).fuelHandler.steamTank;
        }
        //draw the fuel fluid tank hover text
        if (transport instanceof EntityTrainCore) {
            renderTrainOverlay(mouseX, mouseY);
        }

        //generic buttons will render to the left. specific buttons defined in the overlay are to the right.
        if (mouseY > guiTop + 166 && mouseY < guiTop +184) {
            if (player.getEntityId() == transport.getOwnerID() && mouseX > guiLeft && mouseX < guiLeft + 18) {
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.dropkey")), mouseX, mouseY, fontRendererObj);
            } else if (mouseX > guiLeft +18 && mouseX < guiLeft + 36) {
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.locked." + transport.isLocked)), mouseX, mouseY, fontRendererObj);
            } else if (mouseX > guiLeft + 36 && mouseX < guiLeft + 54) {
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.coupler." + transport.isCoupling)), mouseX, mouseY, fontRendererObj);
            } else if (mouseX > guiLeft + 54 && mouseX < guiLeft + 72) {
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.unlink")), mouseX, mouseY, fontRendererObj);
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
        //generic to all
        if (player.getEntityId() == transport.getOwnerID()) {
            this.buttonList.add(new GuiButton(8, guiLeft, guiTop + 166, 18, 18, ""));
        }
        this.buttonList.add(new GuiButton(2, guiLeft+ 18, guiTop + 166, 18, 18,""));
        this.buttonList.add(new GuiButton(3, guiLeft+ 36, guiTop + 166, 18, 18,""));
        this.buttonList.add(new GuiButton(9, guiLeft+ 54, guiTop + 166, 18, 18,""));

        //train specific
        if (transport instanceof EntityTrainCore) {
            if (player.capabilities.isCreativeMode) {
                this.buttonList.add(new GuiButton(7, guiLeft + 72, guiTop + 166, 18, 18, ""));
            }
            this.buttonList.add(new GuiButton(0, guiLeft + 108, guiTop + 166, 18, 18, ""));
            this.buttonList.add(new GuiButton(1, guiLeft + 126, guiTop + 166, 18, 18, ""));
            this.buttonList.add(new GuiButton(4, guiLeft + 144, guiTop + 166, 18, 18, ""));

            if (transport.getType() != TrainsInMotion.transportTypes.STEAM) {
                this.buttonList.add(new GuiButton(6, guiLeft + 90, guiTop + 166, 18, 18, ""));
                //this.buttonList.add(new GuiButton(5, guiLeft + 162, guiTop + 166, 18, 18, ""));
            }
        }
    }

    /**
     * <h2>button overrides</h2>
     * here we define the overrides for the button functionality.
     */
    @Override
    public void actionPerformed(GuiButton button) {
        switch (button.id){
            case 0:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(4, transport.getEntityId()));
                transport.brake = !transport.brake; break;}
            case 1:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(5, transport.getEntityId()));
                transport.lamp.isOn = !transport.lamp.isOn; break;}
            case 2:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(6, transport.getEntityId()));
                transport.isLocked = ! transport.isLocked; break;}
            case 3:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(7, transport.getEntityId()));
                transport.isCoupling = !transport.isCoupling; break;}
            case 4:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(8, transport.getEntityId())); break;}//horn
            case 5:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(9, transport.getEntityId())); break;}//map
            case 6:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(10, transport.getEntityId()));
                ((EntityTrainCore)transport).isRunning = !((EntityTrainCore)transport).isRunning; break;}
            case 7:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(11, transport.getEntityId()));
                transport.isCreative = !transport.isCreative; break;}
            case 8:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(13, transport.getEntityId())); break;}
            case 9:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(12, transport.getEntityId())) ; break;}
        }
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
            default:{return "";}
        }
    }


    /**
     * <h2> render train inventory</h2>
     * due to how many GUI's this class covers, it's easier to just separate them by the type of transport.
     * we make this a static function for efficiency, so we have to make the function call feed the super's variables in.
     * @param guiTop the guiTop variable defined in the super.
     * @param guiLeft the guiLeft variable defined in the super.
     * @param mc the minecraft instance defined in the super.
     * @param firstTankFluid the first fluid variable defined in the initial draw screen function of this class.
     * @param secondTankFluid the second fluid variable defined in the initial draw screen function of this class.
     */
    private void renderTrainInventory(int guiTop, int guiLeft,Minecraft mc, int firstTankFluid, int secondTankFluid){
        //main background TODO disabled until we actually have an image for it.
        //draw the background
        //drawTexturedRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);

        //bind the inventory image which we use the slot images and inventory image from.
        mc.getTextureManager().bindTexture(vanillaInventory);
        //icon for fuel
        drawTexturedRect(guiLeft*2-13, guiTop + 11, 54, 51, 18, 18, 20, 20);
        //icon for furnace
        drawTexturedRect(guiLeft*2-13, guiTop + 11, 54, 51, 18, 18, 20, 20);

        //slot for fuel
        drawTexturedRect(guiLeft*2-13, guiTop + 40, 54, 51, 18, 18, 20, 20);
        //slot for water
        drawTexturedRect(guiLeft*2+22, guiTop + 40, 54, 51, 18, 18, 20, 20);

        //draw the player inventory and toolbar background.
        drawTexturedRect(guiLeft*2-20, guiTop+ 78, 0, 78, 176, 176);
        drawTexturedRect(guiLeft*2-20, guiTop+ 74, 0, 0, 176, 6);

        //draw the tanks
        mc.getTextureManager().bindTexture(URIRegistry.GUI_PREFIX.getResource("gui.png"));
        //liquid fuel tank
        drawTexturedRect(guiLeft*2 + 80, guiTop - 4, 0, 0, 18, 64, 16, 16);
        if (transport.getTankAmount()>0) {
            //draw the water tank
            int liquid = Math.abs((transport.getTankAmount() * 64) / transport.getTankCapacity());
            drawTexturedRect(guiLeft*2+ 80, guiTop + 60 - liquid, 16,0, 18, liquid, 16, 16);
        }
        //steam tank
        if (transport.getType() == TrainsInMotion.transportTypes.STEAM || transport.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM) {
            drawTexturedRect(guiLeft*2 + 50, guiTop-4, 0, 0, 18, 30, 16, 16);
            drawTexturedRect(guiLeft*2 + 110, guiTop-4, 0, 0, 18, 30, 16, 16);
            if (secondTankFluid>0) {
                int liquid3 = Math.abs((secondTankFluid * 30) / transport.getTankCapacity());
                drawTexturedRect(guiLeft*2 + 50, guiTop +40 - liquid3, 32,0, 18, liquid3, 16, 16);
                drawTexturedRect(guiLeft*2 + 110, guiTop +40 - liquid3, 32,0, 18, liquid3, 16, 16);
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
            drawTexturedRect(guiLeft, guiTop*2+40-(transport.getInventorySize().getRow()*18)-17, 0, 0, this.xSize, transport.getInventorySize().getRow() * 18 + 17);
            drawTexturedRect(guiLeft,  guiTop*2+40, 0, 126, this.xSize, 96);
        } else {
            drawTexturedRect(20, 0, 0, 0, this.xSize, 17);//top
            for(int i=0; i<(transport.getInventorySize().getRow()*18); i++){
                drawTexturedRect(20, i*18+17, 0, 17, this.xSize, 18);
            }
            drawTexturedRect(20,(transport.getInventorySize().getRow()*18)*18+17, 0, 215, this.xSize, 8);//bottom

            drawTexturedRect(guiLeft*2-20, 0, 0, 0, this.xSize,  16);//top
            drawTexturedRect(guiLeft*2-20,   4, 0, 126, this.xSize, 96);//actual inventory
        }
    }


    /**
     * <h2>Train GUI overlay</h2>
     * This is only used to draw the hover text for different parts of the train's GUI
     */
    private void renderTrainOverlay(int mouseX, int mouseY){
        if ((mouseX >= guiLeft + 66 && mouseX <= guiLeft + 84 &&
                mouseY >= guiTop + 10 && mouseY <= guiTop +60)) {
            drawHoveringText(Arrays.asList(tankType(true), firstTankFluid*0.001f + StatCollector.translateToLocal("gui.of") + transport.getTankCapacity()*0.001f, StatCollector.translateToLocal("gui.buckets")), mouseX, mouseY, fontRendererObj);
        }
        //draw the steam tank hover text
        if ((transport.getType() == TrainsInMotion.transportTypes.STEAM || transport.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM) &&
                (mouseX >= guiLeft + 34 && mouseX <= guiLeft + 52 &&
                        mouseY >= guiTop + 10 && mouseY <= guiTop +40)) {
            drawHoveringText(Arrays.asList(tankType(false), secondTankFluid*0.001f + StatCollector.translateToLocal("gui.of") + transport.getTankCapacity()*0.001f, StatCollector.translateToLocal("gui.buckets")), mouseX, mouseY, fontRendererObj);
        }
        //draw toggle button hover text
        if (mouseY > guiTop + 166 && mouseY < guiTop +184){
            if (player.capabilities.isCreativeMode &&
                    mouseX > guiLeft + 72 && mouseX < guiLeft + 90) {
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.creativemode") + ((transport.brake)?StatCollector.translateToLocal("gui.on"):StatCollector.translateToLocal("gui.off"))), mouseX, mouseY, fontRendererObj);
            } else if (transport.getType() != TrainsInMotion.transportTypes.STEAM && mouseX > guiLeft + 90 && mouseX < guiLeft + 108){
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.trainisrunning")  + ((((EntityTrainCore)transport).isRunning)?StatCollector.translateToLocal("gui.on"):StatCollector.translateToLocal("gui.off"))), mouseX, mouseY, fontRendererObj);
            } else if (mouseX > guiLeft + 108 && mouseX < guiLeft + 126) {
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.brake")  + ((transport.brake)?StatCollector.translateToLocal("gui.on"):StatCollector.translateToLocal("gui.off"))), mouseX, mouseY, fontRendererObj);
            } else if (mouseX > guiLeft + 126 && mouseX < guiLeft + 144){
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.lamp")  + ((transport.lamp.isOn)?StatCollector.translateToLocal("gui.on"):StatCollector.translateToLocal("gui.off"))), mouseX, mouseY, fontRendererObj);
            } else if (mouseX > guiLeft + 144 && mouseX < guiLeft + 162){
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.horn") ), mouseX, mouseY, fontRendererObj);
            } /*else if(mouseX > guiLeft + 162 && mouseX < guiLeft + 180) {
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.map") ), mouseX, mouseY, fontRendererObj);
                drawHoveringText(Collections.singletonList("Open Map"), mouseX, mouseY, fontRendererObj);
            }*/
        }
    }


    /**
     * <h2>Render Freight GUI</h2>
     * basically the same as
     */
    private void renderTankerInventory(int guiTop, int guiLeft, double zLevel, Minecraft mc){
        mc.getTextureManager().bindTexture(vanillaInventory);
        //draw the player inventory and toolbar background.
        drawTexturedRect(guiLeft, guiTop+ 72, 0, 72, 176, 176, 176, 176);
        drawTexturedRect(guiLeft, guiTop+ 70, 0, 0, xSize, 16,20,20);

        drawTexturedRect( guiLeft + 8 + 18, guiTop +43 + 18, 54, 51, 18, 18, 20, 20);
    }



}