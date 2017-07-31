package ebf.tim.gui;

import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.networking.PacketKeyPress;
import ebf.tim.registry.URIRegistry;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.TileEntitySlotManager;
import ebf.tim.utility.TransportSlotManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.*;

import static ebf.tim.TrainsInMotion.transportTypes.PASSENGER;

/**
 * <h1>Transport GUI</h1>
 * used to draw the GUI for trains and rollingstock (the menu with the inventory).
 * @author Eternal Blue Flame
 */
public class GUITransport extends GuiContainer {

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
            drawTextOutlined(fontRendererObj, StatCollector.translateToLocal(transport.getItem().getUnlocalizedName() + ".name"), -94, -30+yCenter, 16777215);
            drawTextOutlined(fontRendererObj, I18n.format("container.inventory", new Object()), 110, 72, 16777215);
            //draw the text for transports with large inventories
        } else if (transport.getType() != PASSENGER && transport.getInventorySize().getRow()>5) {
            drawTextOutlined(fontRendererObj, StatCollector.translateToLocal(transport.getItem().getUnlocalizedName() + ".name"), -94, -30+yCenter, 16777215);
            drawTextOutlined(fontRendererObj, I18n.format("container.inventory", new Object()), 110, 70, 16777215);
            //draw the text for everything but the passenger car
        } else if (transport.getType() != PASSENGER){
            drawTextOutlined(fontRendererObj, StatCollector.translateToLocal(transport.getItem().getUnlocalizedName() + ".name"), 8, 66-(transport.getInventorySize().getRow()*18), 16777215);
            drawTextOutlined(fontRendererObj, I18n.format("container.inventory", new Object()), 8, 80, 16777215);
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

        if (transport.getType().isTrain()) {
            secondTankFluid = transport.getDataWatcher().getWatchableObjectInt(14);

            if ((mouseX >= guiLeft + 210 && mouseX <= guiLeft + 226 && mouseY >= guiTop - 14 && mouseY <= guiTop + 50)) {
                drawHoveringText(Arrays.asList(tankType(true), transport.getTankAmount() * 0.001f + StatCollector.translateToLocal("gui.of") + transport.getTankCapacity() * 0.001f, StatCollector.translateToLocal("gui.buckets")), mouseX, mouseY, fontRendererObj);
            }
            //draw the steam tank hover text
            if ((transport.getType() == TrainsInMotion.transportTypes.STEAM || transport.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM)) {
                GL11.glPushMatrix();
                drawTextOutlined(fontRendererObj, decimal.format(transport.getDataWatcher().getWatchableObjectInt(15)*0.01f) + "C°", guiLeft+205, guiTop+72, 16777215);
                GL11.glPopMatrix();
                if ((mouseY >= guiTop - 14 && mouseY <= guiTop + 20) &&
                        ((mouseX >= guiLeft + 178 && mouseX <= guiLeft + 196) || (mouseX >= guiLeft + 240 && mouseX <= guiLeft + 258))) {
                    drawHoveringText(Arrays.asList(tankType(false), secondTankFluid * 0.001f + StatCollector.translateToLocal("gui.of") + transport.getTankCapacity() * 0.001f, StatCollector.translateToLocal("gui.buckets")), mouseX, mouseY, fontRendererObj);
                }
            }
        } else if (transport.getType().isTanker()){
            //drawTexturedRect(guiLeft+28, guiTop, 0, 0, 100, 64, 16, 16);
            if(mouseY>guiTop && mouseY<guiTop+64 && mouseX>guiLeft+28 &&mouseX <guiLeft+128){

                drawHoveringText(Arrays.asList(
                        (transport.getTankAmount()==0?
                                "0 " + StatCollector.translateToLocal("gui.buckets"):
                                ((transport.getTankAmount() * 0.001f) + " " + FluidRegistry.getFluid(transport.getDataWatcher().getWatchableObjectInt(14)).getLocalizedName() + " " + StatCollector.translateToLocal("gui.buckets"))
                        ),
                        StatCollector.translateToLocal("gui.of") + (transport.getTankCapacity() * 0.001f) + StatCollector.translateToLocal("gui.buckets")
                ),mouseX, mouseY, fontRendererObj);
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
                this.buttonList.add(new GUIButton(13, guiLeft+112, guiTop + 166, 18, 20, "unlink"));
                this.buttonList.add(new GUIButton(12, guiLeft + 166, guiTop + 166, 18, 20, "dropkey"));
            }
            this.buttonList.add(new GUIButton(6, guiLeft + 130, guiTop + 166, 18, 20, "locked"));
            this.buttonList.add(new GUIButton(7, guiLeft + 148, guiTop + 166, 18, 20, "coupler"));
            if (transport.getLampOffset().yCoord>1) {
                this.buttonList.add(new GUIButton(5, guiLeft + 238, guiTop + 166, 18, 20, "lamp"));
            }
            //train specific
            if (transport instanceof EntityTrainCore) {
                if (player.capabilities.isCreativeMode) {
                    this.buttonList.add(new GUIButton(11, guiLeft + 184, guiTop + 166, 18, 20, "creative"));
                }
                if (transport.getType() != TrainsInMotion.transportTypes.STEAM) {
                    this.buttonList.add(new GUIButton(10, guiLeft + 202, guiTop + 166, 18, 20, "running"));
                }
                this.buttonList.add(new GUIButton(4, guiLeft + 220, guiTop + 166, 18, 20, "brake"));
                this.buttonList.add(new GUIButton(8, guiLeft + 256, guiTop + 166, 18, 20, "horn"));

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
        drawTexturedRect(guiLeft+113, guiTop + 1, 56, 36, 20, 20, 16, 16);

        //icon for furnace
        int i1 = transport.getDataWatcher().getWatchableObjectInt(13);
        if (i1>0) {
            drawTexturedRect(guiLeft + 113, guiTop + 16 - i1, 176, 14 - i1, 17, i1+2, 14, i1);
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
                drawTexturedRect(guiLeft+178, guiTop +16 - liquid3, 32,0, 18, liquid3, 16, 16);
                drawTexturedRect(guiLeft+240, guiTop +16 - liquid3, 32,0, 18, liquid3, 16, 16);
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


    private boolean doubleClick;
    private Slot lastClickSlot;
    private long lastClickTime;
    private int lastClickButton;
    private Slot clickedSlot;
    private ItemStack draggedStack;
    private boolean isRightMouseClick;
    private boolean ignoreMouseUp;
    private boolean dragSplitting;
    private int dragSplittingButton;
    private int dragSplittingLimit;
    private ItemStack shiftClickedSlot = null;
    private Slot currentDragTargetSlot;
    private long dragItemDropDelay;
    private List<Slot> dragSplittingSlots = new ArrayList<>();
    private int dragSplittingRemnant;
    private GuiButton selectedButton;
    private ItemStack returningStack;
    private Long returningStackTime;
    private Slot returningStackDestSlot;

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < this.buttonList.size(); ++i) {
                GuiButton guibutton = (GuiButton) this.buttonList.get(i);

                if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
                    net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre event = new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
                    if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) {
                        break;
                    } else {
                        guibutton = event.button;
                        this.selectedButton = guibutton;
                        guibutton.func_146113_a(this.mc.getSoundHandler());
                        this.actionPerformed(guibutton);
                        if (this.equals(this.mc.currentScreen)) {
                            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent.Post(this, event.button, this.buttonList));
                            //todo: maybe use this to reload the button list?
                            return; //seriously forge? you let this madness continue if it was a button? do you expect 3 buttons and a slot to overlap eachother? why?
                        }
                    }
                }
            }
        }
        System.out.println("okay into the itemstack code");
        boolean flag = this.mc.gameSettings.keyBindPickBlock.isPressed();//mouseButton - 100);
        Slot slot = this.getSlotAtPosition(mouseX, mouseY);
        long i = Minecraft.getSystemTime();
        this.doubleClick = this.lastClickSlot == slot && i - this.lastClickTime < 250L && this.lastClickButton == mouseButton;
        this.ignoreMouseUp = false;

        if (mouseButton == 0 || mouseButton == 1 || flag) {
            //todo: this is probably the source of the bugs...
            boolean flag1 = mouseX < this.guiLeft || mouseY < this.guiTop || mouseX >= this.guiLeft + this.xSize || mouseY >= this.guiTop + this.ySize;
            if (slot != null){ flag1 = false;} // Forge, prevent dropping of items through slots outside of GUI boundaries
            int l = -1;
            System.out.println(flag1 +"please dont be true");

            if (slot != null) {
                l = slot.slotNumber;
            }

            if (flag1) {
                l = -999;
            }
            if (this.mc.gameSettings.touchscreen && flag1 && this.mc.thePlayer.inventory.getItemStack() == null) {
                this.mc.displayGuiScreen(null);
                return;
            }

            System.out.println(l + " and drag was " + dragSplitting);
            if (l != -1) {
                if (this.mc.gameSettings.touchscreen) {
                    if (slot != null && slot.getHasStack()) {
                        this.clickedSlot = slot;
                        this.draggedStack = null;
                        this.isRightMouseClick = mouseButton == 1;
                    } else {
                        this.clickedSlot = null;
                    }
                } else if (!this.dragSplitting) {
                    if (this.mc.thePlayer.inventory.getItemStack() == null) {
                        if (this.mc.gameSettings.keyBindPickBlock.isPressed()){//.isActiveAndMatches(mouseButton - 100)) {
                            this.handleMouseClick(slot, l, mouseButton, 3 /*ClickType.CLONE*/);
                        } else {
                            boolean flag2 = l != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                            int clicktype = 0 /*ClickType.PICKUP*/;

                            if (flag2) {
                                this.shiftClickedSlot = slot.getStack() != null && slot.getHasStack() ? slot.getStack().copy() : null;
                                clicktype = 1 /*ClickType.QUICK_MOVE*/;
                            } else if (l == -999) {
                                clicktype = 4 /*ClickType.THROW*/;
                            }

                            this.handleMouseClick(slot, l, mouseButton, clicktype);
                        }

                        this.ignoreMouseUp = true;
                    } else {
                        this.dragSplitting = true;
                        this.dragSplittingButton = mouseButton;
                        this.dragSplittingSlots.clear();

                        if (mouseButton == 0) {
                            this.dragSplittingLimit = 0;
                        } else if (mouseButton == 1) {
                            this.dragSplittingLimit = 1;
                        } else if (this.mc.gameSettings.keyBindPickBlock.isPressed()){//.isActiveAndMatches(mouseButton - 100)) {
                            this.dragSplittingLimit = 2;
                        }
                    }
                }
            }
        }

        this.lastClickSlot = slot;
        this.lastClickTime = i;
        this.lastClickButton = mouseButton;
        this.dragSplitting = false;
    }



    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        Slot slot = this.getSlotAtPosition(mouseX, mouseY);
        ItemStack itemstack = this.mc.thePlayer.inventory.getItemStack();
        if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
            if (clickedMouseButton == 0 || clickedMouseButton == 1) {
                if (this.draggedStack == null) {
                    if (slot != this.clickedSlot && this.clickedSlot.getStack() !=null) {
                        this.draggedStack = this.clickedSlot.getStack().copy();
                    }
                } else if (this.draggedStack.stackSize > 1 && slot != null && canAddItemToSlot(slot, this.draggedStack, false)) {
                    long i = Minecraft.getSystemTime();

                    if (this.currentDragTargetSlot == slot) {
                        if (i - this.dragItemDropDelay > 500L) {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0 /*ClickType.PICKUP*/);
                            this.handleMouseClick(slot, slot.slotNumber, 1, 0 /*ClickType.PICKUP*/);
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0 /*ClickType.PICKUP*/);
                            this.dragItemDropDelay = i + 750L;
                            this.draggedStack.stackSize--;
                        }
                    } else {
                        this.currentDragTargetSlot = slot;
                        this.dragItemDropDelay = i;
                    }
                }
            }
        }
        else if (this.dragSplitting && slot != null && itemstack != null && itemstack.stackSize !=0 &&
                (itemstack.stackSize > this.dragSplittingSlots.size() || this.dragSplittingLimit == 2) &&
                canAddItemToSlot(slot, itemstack, true) && slot.isItemValid(itemstack) && this.inventorySlots.canDragIntoSlot(slot)) {
            this.dragSplittingSlots.add(slot);
            this.updateDragSplitting();
        }
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int state) {
        //super.mouseReleased(mouseX, mouseY, state); //Forge, Call parent to release buttons
        if (this.selectedButton != null && state == 0) {
            this.selectedButton.mouseReleased(mouseX, mouseY);
            this.selectedButton = null;
        }
        Slot slot = this.getSlotAtPosition(mouseX, mouseY);
        int i = this.guiLeft;
        int j = this.guiTop;
        boolean flag = mouseX < this.guiLeft || mouseY < this.guiTop || mouseX >= this.guiLeft + this.xSize || mouseY >= this.guiTop + this.ySize;
        if (slot != null) flag = false; // Forge, prevent dropping of items through slots outside of GUI boundaries
        int k = -1;

        if (slot != null) {
            k = slot.slotNumber;
        }

        if (flag) {
            k = -999;
        }

        if (this.doubleClick && slot != null && state == 0) {
            if (isShiftKeyDown()) {
                if (this.shiftClickedSlot != null) {
                    Slot slot2;
                    for (Object s : this.inventorySlots.inventorySlots) {
                        if (s instanceof Slot){
                            slot2 = (Slot)s;
                        } else {
                            continue;
                        }
                        if (slot2.canTakeStack(this.mc.thePlayer) && slot2.getHasStack() && slot2.isSlotInInventory(slot.inventory, slot.getSlotIndex()) &&
                                canAddItemToSlot(slot2, this.shiftClickedSlot, true)) {
                            this.handleMouseClick(slot2, slot2.slotNumber, state,  1 /*ClickType.QUICK_MOVE*/);
                        }
                    }
                }
            } else {
                this.handleMouseClick(slot, k, state, 6/*ClickType.PICKUP_ALL*/);
            }

            this.doubleClick = false;
            this.lastClickTime = 0L;
        } else {
            if (this.dragSplitting && this.dragSplittingButton != state) {
                this.dragSplitting = false;
                this.dragSplittingSlots.clear();
                this.ignoreMouseUp = true;
                return;
            }

            if (this.ignoreMouseUp) {
                this.ignoreMouseUp = false;
                return;
            }

            if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
                if (state == 0 || state == 1) {
                    if (this.draggedStack != null && slot != this.clickedSlot) {
                        this.draggedStack = this.clickedSlot.getStack();
                    }

                    boolean flag2 = canAddItemToSlot(slot, this.draggedStack, false);

                    if (k != -1 && this.draggedStack != null && flag2) {
                        this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, state, 0 /*ClickType.PICKUP*/);
                        this.handleMouseClick(slot, k, 0, 0 /*ClickType.PICKUP*/);

                        if (this.mc.thePlayer.inventory.getItemStack() == null) {
                            this.returningStack = null;
                        } else {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, state, 0 /*ClickType.PICKUP*/);
                            //this.touchUpX = mouseX - i;
                            //this.touchUpY = mouseY - j;
                            this.returningStackDestSlot = this.clickedSlot;
                            this.returningStack = this.draggedStack;
                            this.returningStackTime = Minecraft.getSystemTime();
                        }
                    } else if (this.draggedStack != null) {
                        //this.touchUpX = mouseX - i;
                        //this.touchUpY = mouseY - j;
                        this.returningStackDestSlot = this.clickedSlot;
                        this.returningStack = this.draggedStack;
                        this.returningStackTime = Minecraft.getSystemTime();
                    }

                    this.draggedStack = null;
                    this.clickedSlot = null;
                }
            }
            else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
                this.handleMouseClick((Slot)null, -999, ((this.dragSplittingLimit & 3) << 2), 5/*ClickType.QUICK_CRAFT*/);

                for (Slot slot1 : this.dragSplittingSlots) {
                    this.handleMouseClick(slot1, slot1.slotNumber, (1 & 3 | (this.dragSplittingLimit & 3) << 2), 5/*ClickType.QUICK_CRAFT*/);
                }

                this.handleMouseClick(null, -999, (2 & 3 | (this.dragSplittingLimit & 3) << 2), 5/*ClickType.QUICK_CRAFT*/);
            }
            else if (this.mc.thePlayer.inventory.getItemStack() != null) {
                if (this.mc.gameSettings.keyBindPickBlock.isPressed()){//isActiveAndMatches(state - 100)) {
                    this.handleMouseClick(slot, k, state, 3 /*ClickType.CLONE*/);
                } else {
                    boolean flag1 = k != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));

                    if (flag1) {
                        this.shiftClickedSlot = slot != null && slot.getHasStack() ? slot.getStack().copy() : null;
                    }

                    this.handleMouseClick(slot, k, state, flag1 ? 1 /*ClickType.QUICK_MOVE*/ : 0 /*ClickType.PICKUP*/);
                }
            }
        }

        if (this.mc.thePlayer.inventory.getItemStack() == null) {
            this.lastClickTime = 0L;
        }

        this.dragSplitting = false;
    }

    private void updateDragSplitting() {
        ItemStack itemstack = this.mc.thePlayer.inventory.getItemStack();

        if (itemstack != null && this.dragSplitting) {
            if (this.dragSplittingLimit == 2) {
                this.dragSplittingRemnant = itemstack.getMaxStackSize();
            } else {
                this.dragSplittingRemnant = itemstack.stackSize;

                for (Slot slot : this.dragSplittingSlots) {
                    ItemStack itemstack1 = itemstack.copy();
                    ItemStack itemstack2 = slot.getStack();
                    int i = itemstack2!=null?itemstack2.stackSize:0;
                    computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack1, i);
                    int j = Math.min(itemstack1.getMaxStackSize(), slot.getSlotStackLimit());

                    if (itemstack1.stackSize > j) {
                        itemstack1.stackSize =j;
                    }

                    this.dragSplittingRemnant -= itemstack1.stackSize - i;
                }
            }
        }
    }

    public static void computeStackSize(List<Slot> dragSlotsIn, int dragModeIn, ItemStack stack, int slotStackSize) {
        switch (dragModeIn) {
            case 0: {
                stack.stackSize = (stack.stackSize / dragSlotsIn.size());
                break;
            }
            case 1: {
                stack.stackSize = 1;
                break;
            }
            case 2: {
                stack.stackSize = stack.getMaxStackSize();
                break; //really forge? even in 1.12 you NEVER added this break? WHY?
            }
            default:{
                stack.stackSize += slotStackSize;
            }
        }
    }

    public static boolean canAddItemToSlot(@Nullable Slot slotIn, ItemStack stack, boolean stackSizeMatters) {
        System.out.println("i sware im checking");
        boolean flag = slotIn == null || !slotIn.getHasStack();

        if (!flag && stack.isItemEqual(slotIn.getStack()) && ItemStack.areItemStackTagsEqual(slotIn.getStack(), stack)) {
            return slotIn.getStack().stackSize + (stackSizeMatters ? 0 : stack.stackSize) <= stack.getMaxStackSize();
        } else {
            return flag;
        }
    }


    private Slot getSlotAtPosition(int mouseX, int mouseY) {
        for (int k = 0; k < this.inventorySlots.inventorySlots.size(); ++k) {
            Slot slot = (Slot)this.inventorySlots.inventorySlots.get(k);

            if (this.func_146978_c(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, mouseX, mouseY)) {
                return slot;
            }
        }

        return null;
    }









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