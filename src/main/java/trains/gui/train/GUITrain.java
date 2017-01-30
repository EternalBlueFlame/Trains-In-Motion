package trains.gui.train;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import trains.TrainsInMotion;
import trains.entities.EntityTrainCore;
import trains.networking.PacketKeyPress;
import trains.registry.URIRegistry;
import trains.utility.ContainerHandler;

import java.util.Arrays;
import java.util.Collections;

/**
 * <h1>Train GUI</h1>
 * used to draw the GUI for trains (the menu with the inventory).
 * @author Eternal Blue Flame
 */
public class GUITrain extends GuiContainer {
    /**
     * <h2>variables</h2>
     * we re-use the furnace texture so the inventory is at least partially customized by the player's texturepack.
     * guiScaler is just a more convenient method to scale, its similar to what the base game does, but with less overhead.
     */
    private static final ResourceLocation vanillaInventory = new ResourceLocation("textures/gui/container/furnace.png");
    private static EntityTrainCore train;
    private int firstTankFluid;
    private int secondTankFluid;
    private static final float guiScaler = 0.00390625F;

    /**
     * <h2>GUI initialization</h2>
     * instances the container for the inventory and passes it to the server side management
     * also puts the entity to a variable that can be accessed on client.
     * @see ContainerHandler
     */
    public GUITrain(InventoryPlayer inventoryPlayer, EntityTrainCore entity) {
        super(new ContainerHandler(inventoryPlayer, entity,  false));
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
        fontRendererObj.drawString(StatCollector.translateToLocal(train.getItem().getUnlocalizedName() + ".name"), 8, -18, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, 74, 4210752);
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
        //drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);


        //bind the inventory image which we use the slot images and inventory image from.
        this.mc.getTextureManager().bindTexture(vanillaInventory);
        //icon for fuel
        drawTexturedModalRect(guiLeft + 7, guiTop + 25, 54, 51, 18, 18, 20);
        //icon for furnace
        drawTexturedModalRect(guiLeft + 7, guiTop + 25, 54, 51, 18, 18, 20);

        //slot for fuel
        drawTexturedModalRect(guiLeft + 7, guiTop + 52, 54, 51, 18, 18, 20);
        //slot for water
        drawTexturedModalRect(guiLeft + 34, guiTop + 52, 54, 51, 18, 18, 20);

        //train inventory
        for (int ic = 0; ic < train.getInventorySize().getRow(); ic++) {
            for (int ir = 0; ir > -train.getInventorySize().getCollumn(); ir--) {
                drawTexturedModalRect( guiLeft + 97 + (ic * 18), guiTop +43 + (ir * 18), 54, 51, 18, 18, 20);
            }
        }
        //draw the player inventory and toolbar background.
        drawTexturedModalRect(guiLeft, guiTop+ 82, 0, 82, 176, 176, 176);

        //draw the tanks
        mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("gui.png"));
        //liquid fuel tank
        drawTexturedModalRect(guiLeft + 66, guiTop + 10, 0, 0, 18, 50, 16);
        if (firstTankFluid>0) {
            //draw the water tank
            int liquid = Math.abs((firstTankFluid * 50) / train.tanks.getTank(true).getCapacity());
            drawTexturedModalRect(guiLeft + 66, guiTop + 60 - liquid, 16,0, 18, liquid, 16);
        }
        //steam tank
        if (train.getType() == TrainsInMotion.transportTypes.STEAM || train.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM) {
            drawTexturedModalRect(guiLeft + 34, guiTop+10, 0, 0, 18, 30, 16);
            if (secondTankFluid>0) {
                int liquid3 = Math.abs((secondTankFluid * 30) / train.tanks.getTank(false).getCapacity());
                drawTexturedModalRect(guiLeft + 34, guiTop +50 - liquid3, 32,0, 18, liquid3, 16);
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


    /**
     * <h2>Draw upper layer</h2>
     * this draws the upper layer of the GUI, and handles things like the tooltips.
     * Most of this is just checking if the cursor position is in the correct place for displaying tooltips.
     * TODO: maps are disabled
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float par3){
        super.drawScreen(mouseX, mouseY, par3);
        firstTankFluid = train.getDataWatcher().getWatchableObjectInt(20);
        secondTankFluid = train.getDataWatcher().getWatchableObjectInt(21);
        //draw the fuel fluid tank hover text
        if ((mouseX >= guiLeft + 66 && mouseX <= guiLeft + 84 &&
                mouseY >= guiTop + 10 && mouseY <= guiTop +60)) {
            drawHoveringText(Arrays.asList(tankType(true), firstTankFluid*0.001f + StatCollector.translateToLocal("gui.of") + train.tanks.getTank(true).getCapacity()*0.001f, StatCollector.translateToLocal("gui.buckets")), mouseX, mouseY, fontRendererObj);
        }
        //draw the steam tank hover text
        if ((train.getType() == TrainsInMotion.transportTypes.STEAM || train.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM) &&
                (mouseX >= guiLeft + 34 && mouseX <= guiLeft + 52 &&
                        mouseY >= guiTop + 10 && mouseY <= guiTop +40)) {
            drawHoveringText(Arrays.asList(tankType(false), secondTankFluid*0.001f + StatCollector.translateToLocal("gui.of") + train.tanks.getTank(true).getCapacity()*0.001f, StatCollector.translateToLocal("gui.buckets")), mouseX, mouseY, fontRendererObj);
        }
        //draw toggle button hover text
        if (mouseY > guiTop + 63 && mouseY < guiTop +81){
            if (train.ridingEntity instanceof EntityPlayer && ((EntityPlayer)train.ridingEntity).capabilities.isCreativeMode &&
                    mouseX > guiLeft + 52 && mouseX < guiLeft + 70) {
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.creativemode") + checkBoolean(train.brake)), mouseX, mouseY, fontRendererObj);
            } else if (mouseX > guiLeft + 70 && mouseX < guiLeft + 88) {
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.brake")  + checkBoolean(train.brake)), mouseX, mouseY, fontRendererObj);
            } else if (mouseX > guiLeft + 88 && mouseX < guiLeft + 106){
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.lamp")  + checkBoolean(train.lamp.isOn)), mouseX, mouseY, fontRendererObj);
            } else if (mouseX > guiLeft + 106 && mouseX < guiLeft + 124){
                drawHoveringText(Collections.singletonList(lockedCheck(train.isLocked)), mouseX, mouseY, fontRendererObj);
            } else if (mouseX > guiLeft + 124 && mouseX < guiLeft + 142){
                drawHoveringText(Collections.singletonList(couplingCheck(train.isCoupling)), mouseX, mouseY, fontRendererObj);
            } else if (mouseX > guiLeft + 142 && mouseX < guiLeft + 160){
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.horn") ), mouseX, mouseY, fontRendererObj);
            } else if(false && mouseX > guiLeft + 160 && mouseX < guiLeft + 178) {
                //drawHoveringText(Collections.singletonList("Open Map"), mouseX, mouseY, fontRendererObj);
            }else if (train.getType() != TrainsInMotion.transportTypes.STEAM && mouseX > guiLeft + 178 && mouseX < guiLeft + 196){
                drawHoveringText(Collections.singletonList(StatCollector.translateToLocal("gui.trainisrunning")  + checkBoolean(train.isRunning)), mouseX, mouseY, fontRendererObj);
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
        if (train.ridingEntity instanceof EntityPlayer && ((EntityPlayer)train.ridingEntity).capabilities.isCreativeMode){
            this.buttonList.add(new GuiButton(7, guiLeft+ 52, guiTop + 63, 18, 18,""));
        }
        this.buttonList.add(new GuiButton(0, guiLeft+ 70, guiTop + 63, 18, 18,""));
        this.buttonList.add(new GuiButton(1, guiLeft+ 88, guiTop + 63, 18, 18,""));
        this.buttonList.add(new GuiButton(2, guiLeft+ 106, guiTop + 63, 18, 18,""));
        this.buttonList.add(new GuiButton(3, guiLeft+ 124, guiTop + 63, 18, 18,""));
        this.buttonList.add(new GuiButton(4, guiLeft+ 142, guiTop + 63, 18, 18,""));
        if (train.getType() != TrainsInMotion.transportTypes.STEAM) {
            this.buttonList.add(new GuiButton(5, guiLeft + 160, guiTop + 63, 18, 18, ""));
        }
        //this.buttonList.add(new GuiButton(6, guiLeft + 178, guiTop + 63, 18, 18, ""));
    }

    /**
     * <h2>button overrides</h2>
     * here we define the overrides for the buttons.
     */
    @Override
    public void actionPerformed(GuiButton button) {
        switch (button.id){
            case 0:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(4, train.getPersistentID()));
                train.brake = !train.brake; break;}
            case 1:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(5, train.getPersistentID()));
                train.lamp.isOn = !train.lamp.isOn; break;}
            case 2:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(6, train.getPersistentID()));
                train.isLocked = ! train.isLocked; break;}
            case 3:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(7, train.getPersistentID()));
                train.isCoupling = !train.isCoupling; break;}
            case 4:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(8, train.getPersistentID())); break;}//horn
            case 5:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(9, train.getPersistentID())); break;}//map
            case 6:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(10, train.getPersistentID()));
                train.isRunning = !train.isRunning; break;}
            case 7:{TrainsInMotion.keyChannel.sendToServer(new PacketKeyPress(11, train.getPersistentID()));
                train.isCreative = !train.isCreative; break;}
        }
    }


    /**
     * <h3>gui bool checks</h3>
     * @return the text from the language files for the proper value, based on the boolean
     */
    private String checkBoolean(boolean bool){
        if (bool){
            return StatCollector.translateToLocal("gui.on");
        } else {
            return StatCollector.translateToLocal("gui.off");
        }
    }
    private String couplingCheck(boolean bool){
        if (bool){
            return StatCollector.translateToLocal("gui.couplersopen");
        } else {
            return StatCollector.translateToLocal("gui.couplersclosed");
        }
    }
    private String lockedCheck(Boolean bool){
        if (bool){
            return StatCollector.translateToLocal("gui.locked");
        } else {
            return StatCollector.translateToLocal("gui.unlocked");
        }
    }

    /**
     * <h3>gui Tank name</h3>
     * @return the name of the defined tank dependant in the current train and the user's language.
     */
    private String tankType(boolean firsttank){
        switch (train.getType()){
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
}