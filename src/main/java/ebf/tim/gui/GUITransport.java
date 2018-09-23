package ebf.tim.gui;

import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.networking.PacketInteract;
import ebf.tim.utility.*;
import fexcraft.tmt.slim.Tessellator;
import ebf.tim.registry.URIRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static ebf.tim.TrainsInMotion.transportTypes.PASSENGER;

/**
 * <h1>Transport GUI</h1>
 * used to draw the GUI for trains and rollingstock (the menu with the inventory).
 * @author Eternal Blue Flame
 */
public class GUITransport extends GUIContainerNoNEI {

    /**a reference to the resource location of the vanilla furnace texture, this also gets overridden by texturepacks*/
    private static final ResourceLocation vanillaInventory =
            ClientProxy.useVanillaInventoryTextures?new ResourceLocation("textures/gui/container/furnace.png"):
                    new ResourceLocation(TrainsInMotion.MODID, "textures/gui/furnace.png");
    /**a reference to the resource location of the vanilla chest texture, this also gets overridden by texturepacks*/
    private static final ResourceLocation vanillaChest =
            ClientProxy.useVanillaInventoryTextures?new ResourceLocation("textures/gui/container/generic_54.png"):
                    new ResourceLocation(TrainsInMotion.MODID, "textures/gui/generic_54.png");
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

    public static final DecimalFormat decimal = new DecimalFormat("#.##");
    /**cache a string for more efficient use rather than concurrent adding*/
    private StringBuilder stringCache = new StringBuilder();

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

    /*Anything that uses tooltips needs to be rendered during this phase*/
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        if (transport.getTankCapacity().length>0) {
            renderTankerInventory(mc, mouseX, mouseY);
        }
    }

    /*we draw the GUI here so it's under the item render*/
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY) {
        GL11.glPushMatrix();
        //draw the background
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        //draw inventory slots and whatnot
        if (transport.getInventoryRows()>0){
            renderFreightInventory(mc);
        }
        if (transport instanceof EntityTrainCore){
            //draw train fluid tanks and firebox or whatever
            renderTrainInventory(mc);
        }

        //render custom slots. this can cover everything from the fuel slots to crafting slots.
        mc.getTextureManager().bindTexture(vanillaInventory);
        for(ItemStackSlot s :transport.inventory){
            if (s.getSlotID()>=400){
                drawTexturedRect(s.xDisplayPosition+guiLeft-2, s.yDisplayPosition+guiTop-2, 54, 51, 20, 20);
            }
        }



        //todo:there needs to be some way for custom GUI's from the train's class without breaking it's ability to be defined on server too.



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
        //call super to draw stuff like items
        super.drawScreen(mouseX, mouseY, par3);


        //draw container foreground like fluid tanks


        //draw the text that goes over everything

        drawTextOutlined(fontRendererObj, StatCollector.translateToLocal(transport.getItem().getUnlocalizedName() + ".name"), -94, -30+yCenter, 16777215);
        drawTextOutlined(fontRendererObj, I18n.format("container.inventory", new Object()), guiLeft+120, guiTop+70, 16777215);

        //draw the hover text for the buttons.
        for (Object o : buttonList){
            if(((GUIButton)o).getMouseHover()) {
                drawHoveringText(((GUIButton)o).getDisplayString(transport), mouseX, mouseY, mc.fontRenderer);
            }
        }
    }


    /**
     * <h2>GUI initialization</h2>
     * the super defines the screen size and scdrawCreativeTabHoveringText("0mb/" + transport.getTankInfo(null)[i].capacity + "mb", mouseX, mouseY);ale. beyond that we just initialize th buttons and their positions
     */
    @Override
    public void initGui() {
        super.initGui();

        yCenter = (int)((11-transport.getInventoryRows())*0.5f)*18;
        //generic to all
        if (player.getDisplayName().equals(transport.getOwnerName())) {
            this.buttonList.add(new GUIButton(13, guiLeft +112, guiTop + 166, 18, 18, "unlink"));
            this.buttonList.add(new GUIButton(12, guiLeft + 166, guiTop + 166, 18, 18, "dropkey"));
        }
        this.buttonList.add(new GUIButton(6, guiLeft + 130, guiTop + 166, 18, 18, "locked"));
        this.buttonList.add(new GUIButton(7, guiLeft + 148, guiTop + 166, 18, 18, "coupler"));
        if (transport.getLampOffset().yCoord>1) {
            this.buttonList.add(new GUIButton(5, guiLeft + 238, guiTop + 166, 18, 18, "lamp"));
        }
        this.buttonList.add(new GUIButton(4, guiLeft + 220, guiTop + 166, 18, 18, "brake"));
        //train specific
        if (transport instanceof EntityTrainCore) {
            if (player.capabilities.isCreativeMode) {
                this.buttonList.add(new GUIButton(10, guiLeft + 184, guiTop + 166, 18, 18, "creative"));
            }
            if (transport.getType() != TrainsInMotion.transportTypes.STEAM) {
                this.buttonList.add(new GUIButton(8, guiLeft + 202, guiTop + 166, 18, 18, "running"));
            }
            this.buttonList.add(new GUIButton(9, guiLeft + 256, guiTop + 166, 18, 18, "horn"));

        }
    }

    /**
     * <h2>button overrides</h2>
     * here we define the overrides for the button functionality.
     */
    @Override
    public void actionPerformed(GuiButton button) {
        TrainsInMotion.keyChannel.sendToServer(new PacketInteract(button.id, transport.getEntityId()));
    }


    /**
     * <h3>gui Tank name</h3>
     * @return the name of the defined tank dependant in the current transport and the user's language.
     */
    @Deprecated //just render the fluid name?????
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
            case ELECTRIC:{return StatCollector.translateToLocal("gui.rf") + ":";}
            case HYDROGEN_DIESEL:{return StatCollector.translateToLocal("gui.hydrogencell") + ":";}
            case NUCLEAR_ELECTRIC:{return StatCollector.translateToLocal("gui.coolant") + ":";}
            default:{return "Unknown Tank";}
        }
    }

    /**
     * <h2> render train inventory</h2>
     * due to how many GUI's this class covers, it's easier to just separate them by the type of transport.
     * we make this a static function for efficiency, so we have to make the function call feed the super's variables in.
     * @param mc the minecraft instance defined in the super.
     *           todo: rework this just to render the train's fluid tanks and firebox
     */
    private void renderTrainInventory(Minecraft mc){
        //bind the inventory image which we use the slot images and inventory image from.
        mc.getTextureManager().bindTexture(vanillaInventory);
        //icon for furnace fuel
        drawTexturedRect(guiLeft+113, guiTop + 1, 56, 35, 16, 16);

        //icon for furnace
        int i1 = transport.getDataWatcher().getWatchableObjectInt(13);
        if (i1>0) {
            drawTexturedRect(guiLeft + 113, guiTop + 16 - i1, 176, 14 - i1, 16, i1);
        }

        drawTextOutlined(fontRendererObj, "heat: " + transport.getDataWatcher().getWatchableObjectInt(13), 10, 70, 16777215);
        drawTextOutlined(fontRendererObj, "burn fuel: " + ((EntityTrainCore)transport).fuelHandler.heatC, 10, 80, 16777215);

    }


    /**
     * <h2>Render Passenger GUI</h2>
     * basically the same as
     * todo: set this up just to render the passenger icons and empty seats above the player inventory
     */
    private void renderPassengerInventory(Minecraft mc){

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
    private void renderFreightInventory(Minecraft mc){
        mc.getTextureManager().bindTexture(vanillaChest);
        //draw the player inventory and toolbar background.
        drawTexturedRect(guiLeft-105, guiTop-37+yCenter, 0, 0, 176, 17);//top
        for(int i = 0; i<transport.getInventoryRows(); i++){
            drawTexturedRect(guiLeft-105, i*18+ (guiTop-20)+yCenter, 0, 17, 176, 18);
        }
        drawTexturedRect(guiLeft-105,(transport.getInventoryRows())*18+ (guiTop-20)+yCenter, 0, 215, 176, 8);//bottom

        drawTexturedRect(guiLeft+105, guiTop+64, 0, 0, 176,  16);//top
        drawTexturedRect(guiLeft+105,   guiTop+70, 0, 126, 176, 96);//actual inventory
    }


    /**
     * <h2>Render Tanker GUI</h2>
     * basically the same as
     * todo: have this render the fluids in a column similar to inventory rows.
     */
    private void renderTankerInventory(Minecraft mc, int mouseX, int mouseY){
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        for(int i=0; i<transport.getTankCapacity().length;i++) {
            //System.out.println(transport.getTankInfo(null).length + ":" + transport.getTankCapacity().length +":" +i);
            //draw the player inventory and toolbar background.
            Tessellator.bindTexture(URIRegistry.GUI_PREFIX.getResource("gui.png"));
            drawTexturedRect(186, 40+(-20*i), 16, 0, 90, 18, 16, 16);

            if(transport.getTankInfo(null)[i]!=null && transport.getTankInfo(null)[i].fluid.amount> 0) {
                float liquid = transport.getTankInfo(null)[i].fluid.amount;
                if(liquid!=0){
                    liquid/=transport.getTankInfo(null)[i].capacity;
                }
                GL11.glPushMatrix();

                GL11.glColor4f(1,1,1,0.5f);
                GL11.glTranslatef(186, 40+(-20*i),0);
                GL11.glScalef(0.125f+liquid,1.125f,1);
                //render fluid overlay
                if (!ForgeHooksClient.renderInventoryItem(RenderBlocks.getInstance(), mc.renderEngine,
                        new ItemStack(Item.getItemFromBlock(transport.getTankInfo(null)[i].fluid.getFluid().getBlock())),
                        true, zLevel, 0, 0)) {
                    RenderItem.getInstance().renderItemIntoGUI(fontRendererObj, mc.renderEngine,
                            new ItemStack(Item.getItemFromBlock(transport.getTankInfo(null)[i].fluid.getFluid().getBlock())), 0, 0, true);
                }
                GL11.glTranslatef(16,0,0);
                if (!ForgeHooksClient.renderInventoryItem(RenderBlocks.getInstance(), mc.renderEngine,
                        new ItemStack(Item.getItemFromBlock(transport.getTankInfo(null)[i].fluid.getFluid().getBlock())),
                        true, zLevel, 0, 0)) {
                    RenderItem.getInstance().renderItemIntoGUI(fontRendererObj, mc.renderEngine,
                            new ItemStack(Item.getItemFromBlock(transport.getTankInfo(null)[i].fluid.getFluid().getBlock())), 0, 0, true);
                }
                GL11.glTranslatef(16,0,0);
                if (!ForgeHooksClient.renderInventoryItem(RenderBlocks.getInstance(), mc.renderEngine,
                        new ItemStack(Item.getItemFromBlock(transport.getTankInfo(null)[i].fluid.getFluid().getBlock())),
                        true, zLevel, 0, 0)) {
                    RenderItem.getInstance().renderItemIntoGUI(fontRendererObj, mc.renderEngine,
                            new ItemStack(Item.getItemFromBlock(transport.getTankInfo(null)[i].fluid.getFluid().getBlock())), 0, 0, true);
                }
                GL11.glTranslatef(16,0,0);
                if (!ForgeHooksClient.renderInventoryItem(RenderBlocks.getInstance(), mc.renderEngine,
                        new ItemStack(Item.getItemFromBlock(transport.getTankInfo(null)[i].fluid.getFluid().getBlock())),
                        true, zLevel, 0, 0)) {
                    RenderItem.getInstance().renderItemIntoGUI(fontRendererObj, mc.renderEngine,
                            new ItemStack(Item.getItemFromBlock(transport.getTankInfo(null)[i].fluid.getFluid().getBlock())), 0, 0, true);
                }
                GL11.glTranslatef(16,0,0);
                if (!ForgeHooksClient.renderInventoryItem(RenderBlocks.getInstance(), mc.renderEngine,
                        new ItemStack(Item.getItemFromBlock(transport.getTankInfo(null)[i].fluid.getFluid().getBlock())),
                        true, zLevel, 0, 0)) {
                    RenderItem.getInstance().renderItemIntoGUI(fontRendererObj, mc.renderEngine,
                            new ItemStack(Item.getItemFromBlock(transport.getTankInfo(null)[i].fluid.getFluid().getBlock())), 0, 0, true);
                }

                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glColor4f(1,1,1,1);

                GL11.glPopMatrix();
            }



        }


        for(int i=0; i<transport.getTankInfo(null).length;i++) {
            if(isMouseInRect(mouseX, mouseY,guiLeft+186, guiTop+40+(-20*i), 90, 18)) {
                if (transport.getTankInfo(null)[i].fluid.amount>0) {
                    drawCreativeTabHoveringText(transport.getTankInfo(null)[i].fluid.getLocalizedName() + " " +
                                    transport.getTankInfo(null)[i].fluid.amount+"mb/"+ transport.getTankInfo(null)[i].capacity+"mb", mouseX-guiLeft, mouseY-guiTop);

                } else {
                    if(transport.getTankFilters(i).length>1){
                        drawCreativeTabHoveringText(", 0mb/" + transport.getTankInfo(null)[i].capacity + "mb", mouseX-guiLeft, mouseY-guiTop);

                    } else if (transport.getTankFilters(i).length==1) {
                        drawCreativeTabHoveringText(transport.getTankFilters(i)[0] + ", 0mb/" + transport.getTankInfo(null)[i].capacity + "mb", mouseX-guiLeft, mouseY-guiTop);
                    } else{
                        drawCreativeTabHoveringText("0mb/" + transport.getTankInfo(null)[i].capacity + "mb", mouseX-guiLeft, mouseY-guiTop);
                    }
                }
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glColor4f(1,1,1,1);
            }
        }

        GL11.glDisable(GL11.GL_BLEND);
    }


    public static boolean isMouseInRect(int mouseX, int mouseY, int x, int y, int width, int height){
        return (mouseY >= y && mouseY <= y+height) && (mouseX >= x && mouseX <= x+width);
    }

    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
    }


    //todo: really? this is the best method on hand for outlined font?
    @Deprecated
    public static void drawTextOutlined(FontRenderer font, String string, int x, int y, int color){
        //bottom left
        font.drawString(string, x-1, y+1, 0);
        //bottom
        font.drawString(string, x, y+1, 0);
        //bottom right
        font.drawString(string, x+1, y+1, 0);
        //left
        font.drawString(string, x-1, y, 0);
        //right
        font.drawString(string, x+1, y, 0);
        //top left
        font.drawString(string, x-1, y-1, 0);
        //top
        font.drawString(string, x, y-1, 0);
        //top right
        font.drawString(string, x+1, y-1, 0);


        font.drawString(string,x,y,color);
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
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.addVertexWithUV(posX, posY + height, 0, posU * guiScaler, (posV + heightUV) * guiScaler);
        tessellator.addVertexWithUV(posX + width, posY + height, 0, (posU + widthUV) * guiScaler, (posV + heightUV) * guiScaler);
        tessellator.addVertexWithUV(posX + width, posY, 0, (posU + widthUV) * guiScaler, posV * guiScaler);
        tessellator.addVertexWithUV(posX, posY, 0, posU * guiScaler, posV * guiScaler);
        tessellator.draw();
    }
    public static void drawTexturedRect(int posX, int posY, int posU, int posV, int width, int height) {
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.addVertexWithUV(posX, posY + height, 0, posU * guiScaler, (posV + height) * guiScaler);
        tessellator.addVertexWithUV(posX + width, posY + height, 0, (posU + width) * guiScaler, (posV + height) * guiScaler);
        tessellator.addVertexWithUV(posX + width, posY, 0, (posU + width) * guiScaler, posV * guiScaler);
        tessellator.addVertexWithUV(posX, posY, 0, posU * guiScaler, posV * guiScaler);
        tessellator.draw();
    }


}