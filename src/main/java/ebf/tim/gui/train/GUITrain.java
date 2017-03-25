package ebf.tim.gui.train;

import ebf.tim.registry.URIRegistry;
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
import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.networking.PacketKeyPress;
import ebf.tim.utility.ContainerHandler;

import java.util.Arrays;
import java.util.Collections;

import static ebf.tim.TrainsInMotion.transportTypes.PASSENGER;

/**
 * <h1>Transport GUI</h1>
 * used to draw the GUI for trains and rollingstock (the menu with the inventory).
 * @author Eternal Blue Flame
 */
public class GUITrain extends GuiContainer {
    /**
     * <h2>variables</h2>
     * we re-use the furnace and chest textures so the inventory is at least partially customized by the player's texturepack.
     * the transport and player are just so we have access to them beyond initialization of the GUI.
     * the first and second tank ints are so we don't have to re-initialize them every frame.
     * guiScaler is just a more convenient method to scale, its similar to what the base game does, but with less overhead.
     */
    private static final ResourceLocation vanillaInventory = new ResourceLocation("textures/gui/container/furnace.png");
    private static final ResourceLocation vanillaChest = new ResourceLocation("textures/gui/container/generic_54.png");
    private static GenericRailTransport transport;
    private EntityPlayer player;
    private int firstTankFluid;
    private int secondTankFluid;
    private static final float guiScaler = 0.00390625F;

    /**
     * <h2>GUI initialization</h2>
     * instances the container for the inventory and passes it to the server side management
     * also puts the entity to a variable that can be accessed on client.
     * @see ContainerHandler
     */
    public GUITrain(InventoryPlayer inventoryPlayer, GenericRailTransport entity) {
        super(new ContainerHandler(inventoryPlayer, entity,  false));
        player = inventoryPlayer.player;
        transport = entity;
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
        fontRendererObj.drawString(StatCollector.translateToLocal(transport.getItem().getUnlocalizedName() + ".name"), 8, -18, 4210752);
        if (transport.getType() != PASSENGER) {
            fontRendererObj.drawString(I18n.format("container.inventory", new Object()), 8, 74, 4210752);
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
        if (transport instanceof EntityTrainCore){
            renderTrainInventory(guiTop,guiLeft,this.zLevel, this.mc, firstTankFluid, secondTankFluid);
        } else if (transport != null){
            switch (transport.getType()){
                case PASSENGER:{renderPassengerInventory(guiTop,guiLeft,this.zLevel, this.mc); break;}
                case LOGCAR: case FREIGHT: case HOPPER: case COALHOPPER: case GRAINHOPPER: {renderFreightInventory(guiTop,guiLeft, this.zLevel, this.mc); break;}
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
    public static void drawTexturedModalRect(int posX, int posY, int posU, int posV, int width, int height, int scaleU, int scaleV, double zLevel) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(posX, posY + height, zLevel, posU * guiScaler, (posV + scaleV) * guiScaler);
        tessellator.addVertexWithUV(posX + width, posY + height, zLevel, (posU + scaleU) * guiScaler, (posV + scaleV) * guiScaler);
        tessellator.addVertexWithUV(posX + width, posY, zLevel, (posU + scaleU) * guiScaler, posV * guiScaler);
        tessellator.addVertexWithUV(posX, posY, zLevel, posU * guiScaler, posV * guiScaler);
        tessellator.draw();
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
        firstTankFluid = transport.getTankFluid(true);
        secondTankFluid = transport.getTankFluid(false);
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
     * here we define the overrides for the buttons.
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
            case DIESEL:{
                return StatCollector.translateToLocal("gui.diesel") + ":";
            }
            case ELECTRIC: case MAGLEV:{
                return StatCollector.translateToLocal("gui.rf") + ":";
            }
            case HYDROGEN_DIESEL:{
                return StatCollector.translateToLocal("gui.hydrogencell") + ":";
            }
            case NUCLEAR_ELECTRIC:{
                return StatCollector.translateToLocal("gui.coolant") + ":";
            }
            default:{return "";}
        }
    }


    /**
     * <h2> render train inventory</h2>
     * due to how many GUI's this class covers, it's easier to just separate them by the type of transport.
     * we make this a static function for efficiency, so we have to make the function call feed the super's variables in.
     * @param guiTop the guiTop variable defined in the super.
     * @param guiLeft the guiLeft variable defined in the super.
     * @param zLevel the zLevel variable defined in the super.
     * @param mc the minecraft instance defined in the super.
     * @param firstTankFluid the first fluid variable defined in the initial draw screen function of this class.
     * @param secondTankFluid the second fluid variable defined in the initial draw screen function of this class.
     */
    public static void renderTrainInventory(int guiTop, int guiLeft, double zLevel, Minecraft mc, int firstTankFluid, int secondTankFluid){
        //main background TODO disabled until we actually have an image for it.
        //draw the background
        //drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);


        //bind the inventory image which we use the slot images and inventory image from.
        mc.getTextureManager().bindTexture(vanillaInventory);
        //icon for fuel
        drawTexturedModalRect(guiLeft + 7, guiTop + 25, 54, 51, 18, 18, 20, 20, zLevel);
        //icon for furnace
        drawTexturedModalRect(guiLeft + 7, guiTop + 25, 54, 51, 18, 18, 20, 20, zLevel);

        //slot for fuel
        drawTexturedModalRect(guiLeft + 7, guiTop + 52, 54, 51, 18, 18, 20, 20, zLevel);
        //slot for water
        drawTexturedModalRect(guiLeft + 34, guiTop + 52, 54, 51, 18, 18, 20, 20, zLevel);

        //transport inventory
        for (int ic = 0; ic < transport.getInventorySize().getRow(); ic++) {
            for (int ir = 0; ir > -transport.getInventorySize().getCollumn(); ir--) {
                drawTexturedModalRect( guiLeft + 97 + (ic * 18), guiTop +43 + (ir * 18), 54, 51, 18, 18, 20, 20, zLevel);
            }
        }
        //draw the player inventory and toolbar background.
        drawTexturedModalRect(guiLeft, guiTop+ 82, 0, 82, 176, 176, 176, 176, zLevel);

        //draw the tanks
        mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("gui.png"));
        //liquid fuel tank
        drawTexturedModalRect(guiLeft + 66, guiTop + 10, 0, 0, 18, 50, 16, 16, zLevel);
        if (firstTankFluid>0) {
            //draw the water tank
            int liquid = Math.abs((firstTankFluid * 50) / transport.getTank().getTank(true).getCapacity());
            drawTexturedModalRect(guiLeft + 66, guiTop + 60 - liquid, 16,0, 18, liquid, 16, 16, zLevel);
        }
        //steam tank
        if (transport.getType() == TrainsInMotion.transportTypes.STEAM || transport.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM) {
            drawTexturedModalRect(guiLeft + 34, guiTop+10, 0, 0, 18, 30, 16, 16, zLevel);
            if (secondTankFluid>0) {
                int liquid3 = Math.abs((secondTankFluid * 30) / transport.getTank().getTank(false).getCapacity());
                drawTexturedModalRect(guiLeft + 34, guiTop +40 - liquid3, 32,0, 18, liquid3, 16, 16, zLevel);
            }
        }
    }


    /**
     * <h2>Render Passenger GUI</h2>
     * basically the same as
     * @see #renderTrainInventory(int, int, double, Minecraft, int, int)
     */
    public static void renderPassengerInventory(int guiTop, int guiLeft, double zLevel, Minecraft mc){

        int rows=0;
        //draw the character backgrounds.
        mc.getTextureManager().bindTexture(vanillaInventory);
        //make a loop that will make a new row every 5 items
        for (int i=0;i<transport.getRiderOffsets().length; i++) {
            if (i/(rows+1) >=5*(rows+1)){
                rows++;
            }
            drawTexturedModalRect(guiLeft + 7 + (30*(i-(rows * 5))), guiTop + 30+(30*rows), 54, 51, 27, 27, 20, 20, zLevel);
        }
        //make a new loop that does the same as above but binds the character's face rather than the inventory slot background.
        for (int i=0;i<transport.getRiderOffsets().length; i++) {
            if (i/(rows+1) >=5*(rows+1)){
                rows++;
            }
            if (i==0 && transport.riddenByEntity instanceof AbstractClientPlayer){
                mc.getTextureManager().bindTexture(((AbstractClientPlayer) transport.riddenByEntity).getLocationSkin());
                drawTexturedModalRect(guiLeft + 10 + (30*(i-(rows * 5))), guiTop + 32+(30*rows), 30, 70, 22, 22, 36, 56, zLevel);
            } else if (i>0 && transport.seats.get(i-1).riddenByEntity instanceof AbstractClientPlayer){
                mc.getTextureManager().bindTexture(((AbstractClientPlayer) transport.seats.get(i-1).riddenByEntity).getLocationSkin());
                drawTexturedModalRect(guiLeft + 10 + (30*(i-(rows * 5))), guiTop + 32+(30*rows), 30, 70, 22, 22, 36, 56, zLevel);
            }
        }

    }

    /**
     * <h2>Render Freight GUI</h2>
     * basically the same as
     * @see #renderTrainInventory(int, int, double, Minecraft, int, int)
     */
    public void renderFreightInventory(int guiTop, int guiLeft, double zLevel, Minecraft mc){
        mc.getTextureManager().bindTexture(vanillaInventory);
        //draw the player inventory and toolbar background.
        drawTexturedModalRect(guiLeft, guiTop+ 72, 0, 72, 176, 176, 176, 176, zLevel);
        drawTexturedModalRect(guiLeft, guiTop+ 70, 0, 0, xSize, 16);

        switch (transport.getInventorySize()){
            //draw inventory by rows if they are 9 wide.
            case NINExFOUR: case NINExTHREE:{
                mc.getTextureManager().bindTexture(vanillaChest);
                drawTexturedModalRect(guiLeft, guiTop-24, 0, 0, xSize, (transport.getInventorySize().getRow() * 18) + 17);
                drawTexturedModalRect(guiLeft, guiTop + (transport.getInventorySize().getRow() * 17)-3, 0, 472, xSize, 16);
                break;
            }
            //otherwise draw inventory by individual slots
            default:{
                for (int ic = 0; ic < transport.getInventorySize().getCollumn(); ic++) {
                    for (int ir = 0; ir > -transport.getInventorySize().getRow(); ir--) {
                        drawTexturedModalRect( guiLeft + 8 + (ic * 18), guiTop +43 + (ir * 18), 54, 51, 18, 18, 20, 20, zLevel);
                    }
                }
                break;
            }
        }
    }


    /**
     * <h2>Train GUI overlay</h2>
     * This is only used to draw the hover text for different parts of the train's GUI
     */
    private void renderTrainOverlay(int mouseX, int mouseY){
        if ((mouseX >= guiLeft + 66 && mouseX <= guiLeft + 84 &&
                mouseY >= guiTop + 10 && mouseY <= guiTop +60)) {
            drawHoveringText(Arrays.asList(tankType(true), firstTankFluid*0.001f + StatCollector.translateToLocal("gui.of") + transport.getTank().getTank(true).getCapacity()*0.001f, StatCollector.translateToLocal("gui.buckets")), mouseX, mouseY, fontRendererObj);
        }
        //draw the steam tank hover text
        if ((transport.getType() == TrainsInMotion.transportTypes.STEAM || transport.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM) &&
                (mouseX >= guiLeft + 34 && mouseX <= guiLeft + 52 &&
                        mouseY >= guiTop + 10 && mouseY <= guiTop +40)) {
            drawHoveringText(Arrays.asList(tankType(false), secondTankFluid*0.001f + StatCollector.translateToLocal("gui.of") + transport.getTank().getTank(true).getCapacity()*0.001f, StatCollector.translateToLocal("gui.buckets")), mouseX, mouseY, fontRendererObj);
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







}