package ebf.tim.gui;

import ebf.tim.TrainsInMotion;
import ebf.tim.entities.EntityTrainCore;
import ebf.tim.entities.GenericRailTransport;
import fexcraft.tmt.slim.Tessellator;
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
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.fluids.FluidRegistry;
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
public class GUITransport extends GuiScreen {

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

    //todo: inherited from GuiContainer, useless data, depreciate.
    /** Starting X position for the Gui. Inconsistent use for Gui backgrounds. */
    protected int guiLeft;
    /** Starting Y position for the Gui. Inconsistent use for Gui backgrounds. */
    protected int guiTop;
    /** The X size of the inventory window in pixels. */
    protected int xSize = 176;
    /** The Y size of the inventory window in pixels. */
    protected int ySize = 166;
    private Slot theSlot;
    /** Used when touchscreen is enabled. */
    private Slot clickedSlot;
    /** Used when touchscreen is enabled. */
    private boolean isRightMouseClick;
    /** Used when touchscreen is enabled */
    private ItemStack draggedStack;
    private int field_147011_y;
    private int field_147010_z;
    private Slot returningStackDestSlot;
    private long returningStackTime;
    /** Used when touchscreen is enabled */
    private ItemStack returningStack;
    private Slot field_146985_D;
    private long field_146986_E;
    protected final Set field_147008_s = new HashSet();
    protected boolean field_147007_t;
    private int field_146987_F;
    private int field_146988_G;
    private boolean field_146995_H;
    private int field_146996_I;
    private long field_146997_J;
    private Slot field_146998_K;
    private int field_146992_L;
    private boolean field_146993_M;
    private ItemStack field_146994_N;

    public Container inventorySlots;

    public static final DecimalFormat decimal = new DecimalFormat("#.##");

    /**
     * <h2>GUI initialization</h2>
     * instances the container for the inventory and passes it to the server side management
     * also puts the entity to a variable that can be accessed on client.
     * @see TileEntitySlotManager
     */
    public GUITransport(InventoryPlayer inventoryPlayer, GenericRailTransport entity) {
        super();
        inventorySlots =new TransportSlotManager(inventoryPlayer, entity);
        player = inventoryPlayer.player;
        transport = entity;
    }



    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        //draw the text for trains
        if (transport instanceof EntityTrainCore) {
            drawTextOutlined(fontRendererObj, StatCollector.translateToLocal(transport.getItem().getUnlocalizedName() + ".name"), -94, -30+yCenter, 16777215);
            drawTextOutlined(fontRendererObj, I18n.format("container.inventory", new Object()), 110, 72, 16777215);
            //draw the text for transports with large inventories
        } else if (transport.getType() != PASSENGER ) {
            drawTextOutlined(fontRendererObj, StatCollector.translateToLocal(transport.getItem().getUnlocalizedName() + ".name"), -94, -30 + yCenter, 16777215);
            drawTextOutlined(fontRendererObj, I18n.format("container.inventory", new Object()), 110, 70, 16777215);
            //draw the text for everything but the passenger car
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
    protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY) {
        //draw the gui background color
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (transport.getType().isTrain()){
            renderTrainInventory(this.mc);
        } else if (transport != null){
            switch (transport.getType()){
                case PASSENGER:{renderPassengerInventory(this.mc); break;}
                case LOGCAR: case FREIGHT: case HOPPER: case COALHOPPER: case GRAINHOPPER: {renderFreightInventory(this.mc); break;}
                case TANKER:{renderTankerInventory(this.mc);break;}
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
        super.drawScreen(mouseX, mouseY, par3);

        ////todo: inherited from GuiContainer, depreciate due to inefficiency.
        this.drawDefaultBackground();
        int k = this.guiLeft;
        int l = this.guiTop;
        this.drawGuiContainerBackgroundLayer(par3, mouseX, mouseY);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        super.drawScreen(mouseX, mouseY, par3);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)k, (float)l, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        this.theSlot = null;
        short short1 = 240;
        short short2 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)short1 / 1.0F, (float)short2 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int k1;

        for (int i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); ++i1)
        {
            Slot slot = (Slot)this.inventorySlots.inventorySlots.get(i1);
            this.func_146977_a(slot);

            if (this.isMouseOverSlot(slot, mouseX, mouseY) && slot.func_111238_b())
            {
                this.theSlot = slot;
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                int j1 = slot.xDisplayPosition;
                k1 = slot.yDisplayPosition;
                GL11.glColorMask(true, true, true, false);
                this.drawGradientRect(j1, k1, j1 + 16, k1 + 16, -2130706433, -2130706433);
                GL11.glColorMask(true, true, true, true);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }
        }

        //Forge: Force lighting to be disabled as there are some issue where lighting would
        //incorrectly be applied based on items that are in the inventory.
        GL11.glDisable(GL11.GL_LIGHTING);
        this.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GL11.glEnable(GL11.GL_LIGHTING);
        InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
        ItemStack itemstack = this.draggedStack == null ? inventoryplayer.getItemStack() : this.draggedStack;

        if (itemstack != null)
        {
            byte b0 = 8;
            k1 = this.draggedStack == null ? 8 : 16;
            String s = null;

            if (this.draggedStack != null && this.isRightMouseClick)
            {
                itemstack = itemstack.copy();
                itemstack.stackSize = MathHelper.ceiling_float_int((float)itemstack.stackSize / 2.0F);
            }
            else if (this.field_147007_t && this.field_147008_s.size() > 1)
            {
                itemstack = itemstack.copy();
                itemstack.stackSize = this.field_146996_I;

                if (itemstack.stackSize == 0)
                {
                    s = "" + EnumChatFormatting.YELLOW + "0";
                }
            }

            this.drawItemStack(itemstack, mouseX - k - b0, mouseY - l - k1, s);
        }

        if (this.returningStack != null)
        {
            float f1 = (float)(Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;

            if (f1 >= 1.0F)
            {
                f1 = 1.0F;
                this.returningStack = null;
            }

            k1 = this.returningStackDestSlot.xDisplayPosition - this.field_147011_y;
            int j2 = this.returningStackDestSlot.yDisplayPosition - this.field_147010_z;
            int l1 = this.field_147011_y + (int)((float)k1 * f1);
            int i2 = this.field_147010_z + (int)((float)j2 * f1);
            this.drawItemStack(this.returningStack, l1, i2, (String)null);
        }

        GL11.glPopMatrix();

        if (inventoryplayer.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack())
        {
            ItemStack itemstack1 = this.theSlot.getStack();
            this.renderToolTip(itemstack1, mouseX, mouseY);
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
        //end of inherited from GuiContainer




        if (transport.getType().isTrain()) {
            secondTankFluid = transport.getDataWatcher().getWatchableObjectInt(14);

            if ((mouseX >= guiLeft + 210 && mouseX <= guiLeft + 226 && mouseY >= guiTop - 14 && mouseY <= guiTop + 50)) {
                drawHoveringText(Arrays.asList(tankType(true), transport.getTankAmount() * 0.001f + StatCollector.translateToLocal("gui.of") + transport.getTankCapacity() * 0.001f, StatCollector.translateToLocal("gui.buckets")), mouseX, mouseY, fontRendererObj);
            }
            //draw the steam tank hover text
            if ((transport.getType() == TrainsInMotion.transportTypes.STEAM || transport.getType() == TrainsInMotion.transportTypes.NUCLEAR_STEAM)) {
                GL11.glPushMatrix();
                drawTextOutlined(fontRendererObj, decimal.format(transport.getDataWatcher().getWatchableObjectInt(15)*0.01f) + "C°", guiLeft +205, guiTop +72, 16777215);
                GL11.glPopMatrix();
                if ((mouseY >= guiTop - 14 && mouseY <= guiTop + 20) &&
                        ((mouseX >= guiLeft + 178 && mouseX <= guiLeft + 196) || (mouseX >= guiLeft + 240 && mouseX <= guiLeft + 258))) {
                    drawHoveringText(Arrays.asList(tankType(false), secondTankFluid * 0.001f + StatCollector.translateToLocal("gui.of") + transport.getTankCapacity() * 0.001f, StatCollector.translateToLocal("gui.buckets")), mouseX, mouseY, fontRendererObj);
                }
            }
        } else if (transport.getType().isTanker()){
            //drawTexturedRect(guiLeft+28, guiTop, 0, 0, 100, 64, 16, 16);
            if(mouseY> guiTop && mouseY< guiTop +64 && mouseX> guiLeft +28 &&mouseX < guiLeft +128){

                drawHoveringText(Arrays.asList(
                        (transport.getTankAmount()==0?
                                "0 " + StatCollector.translateToLocal("gui.buckets"):
                                ((transport.getTankAmount() * 0.001f) + " " + StatCollector.translateToLocal(FluidRegistry.getFluid(transport.getDataWatcher().getWatchableObjectInt(14)).getUnlocalizedName()) + " " + StatCollector.translateToLocal("gui.buckets"))
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


    @Deprecated //inherited from GuiContainer, inefficient.
    private void func_146977_a(Slot p_146977_1_)
    {
        int i = p_146977_1_.xDisplayPosition;
        int j = p_146977_1_.yDisplayPosition;
        ItemStack itemstack = p_146977_1_.getStack();
        boolean flag = false;
        boolean flag1 = p_146977_1_ == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick;
        ItemStack itemstack1 = this.mc.thePlayer.inventory.getItemStack();
        String s = null;

        if (p_146977_1_ == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && itemstack != null)
        {
            itemstack = itemstack.copy();
            itemstack.stackSize /= 2;
        }
        else if (this.field_147007_t && this.field_147008_s.contains(p_146977_1_) && itemstack1 != null)
        {
            if (this.field_147008_s.size() == 1)
            {
                return;
            }

            if (Container.func_94527_a(p_146977_1_, itemstack1, true) && this.inventorySlots.canDragIntoSlot(p_146977_1_))
            {
                itemstack = itemstack1.copy();
                flag = true;
                Container.func_94525_a(this.field_147008_s, this.field_146987_F, itemstack, p_146977_1_.getStack() == null ? 0 : p_146977_1_.getStack().stackSize);

                if (itemstack.stackSize > itemstack.getMaxStackSize())
                {
                    s = EnumChatFormatting.YELLOW + "" + itemstack.getMaxStackSize();
                    itemstack.stackSize = itemstack.getMaxStackSize();
                }

                if (itemstack.stackSize > p_146977_1_.getSlotStackLimit())
                {
                    s = EnumChatFormatting.YELLOW + "" + p_146977_1_.getSlotStackLimit();
                    itemstack.stackSize = p_146977_1_.getSlotStackLimit();
                }
            }
            else
            {
                this.field_147008_s.remove(p_146977_1_);
                this.func_146980_g();
            }
        }

        this.zLevel = 100.0F;
        itemRender.zLevel = 100.0F;

        if (itemstack == null)
        {
            IIcon iicon = p_146977_1_.getBackgroundIconIndex();

            if (iicon != null)
            {
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND); // Forge: Blending needs to be enabled for this.
                this.mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
                this.drawTexturedModelRectFromIcon(i, j, iicon, 16, 16);
                GL11.glDisable(GL11.GL_BLEND); // Forge: And clean that up
                GL11.glEnable(GL11.GL_LIGHTING);
                flag1 = true;
            }
        }

        if (!flag1)
        {
            if (flag)
            {
                drawRect(i, j, i + 16, j + 16, -2130706433);
            }

            GL11.glEnable(GL11.GL_DEPTH_TEST);
            itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemstack, i, j);
            itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemstack, i, j, s);
        }

        itemRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }


    //todo, for the love of god, why are these two different functions? inherited from GuiContainer, inefficient.
    @Deprecated
    private boolean isMouseOverSlot(Slot p_146981_1_, int p_146981_2_, int p_146981_3_)
    {
        return this.func_146978_c(p_146981_1_.xDisplayPosition, p_146981_1_.yDisplayPosition, 16, 16, p_146981_2_, p_146981_3_);
    }
    @Deprecated
    protected boolean func_146978_c(int p_146978_1_, int p_146978_2_, int p_146978_3_, int p_146978_4_, int p_146978_5_, int p_146978_6_)
    {
        int k1 = this.guiLeft;
        int l1 = this.guiTop;
        p_146978_5_ -= k1;
        p_146978_6_ -= l1;
        return p_146978_5_ >= p_146978_1_ - 1 && p_146978_5_ < p_146978_1_ + p_146978_3_ + 1 && p_146978_6_ >= p_146978_2_ - 1 && p_146978_6_ < p_146978_2_ + p_146978_4_ + 1;
    }


    //todo: inherited from GuiContainer, inefficient.
    @Deprecated
    private void drawItemStack(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_)
    {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        if (p_146982_1_ != null) font = p_146982_1_.getItem().getFontRenderer(p_146982_1_);
        if (font == null) font = fontRendererObj;
        itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_);
        itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_ - (this.draggedStack == null ? 0 : 8), p_146982_4_);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }


    //todo: inherited from GuiContainer, inefficient.
    @Deprecated
    private void func_146980_g()
    {
        ItemStack itemstack = this.mc.thePlayer.inventory.getItemStack();

        if (itemstack != null && this.field_147007_t)
        {
            this.field_146996_I = itemstack.stackSize;
            ItemStack itemstack1;
            int i;

            for (Iterator iterator = this.field_147008_s.iterator(); iterator.hasNext(); this.field_146996_I -= itemstack1.stackSize - i)
            {
                Slot slot = (Slot)iterator.next();
                itemstack1 = itemstack.copy();
                i = slot.getStack() == null ? 0 : slot.getStack().stackSize;
                Container.func_94525_a(this.field_147008_s, this.field_146987_F, itemstack1, i);

                if (itemstack1.stackSize > itemstack1.getMaxStackSize())
                {
                    itemstack1.stackSize = itemstack1.getMaxStackSize();
                }

                if (itemstack1.stackSize > slot.getSlotStackLimit())
                {
                    itemstack1.stackSize = slot.getSlotStackLimit();
                }
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

        this.mc.thePlayer.openContainer = this.inventorySlots;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

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
     */
    private void renderTrainInventory(Minecraft mc){
        //main background TODO disabled until we actually have an image for it.
        //draw the background
        //drawTexturedRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);

        //bind the inventory image which we use the slot images and inventory image from.
        mc.getTextureManager().bindTexture(vanillaInventory);
        //icon for furnace fuel
        drawTexturedRect(guiLeft+113, guiTop + 1, 56, 35, 16, 16);

        //icon for furnace
        int i1 = transport.getDataWatcher().getWatchableObjectInt(13);
        if (i1>0) {
            drawTexturedRect(guiLeft + 113, guiTop + 16 - i1, 176, 14 - i1, 16, i1);
        }

        //slot for fuel
        drawTexturedRect(guiLeft+112, guiTop + 30, 54, 51, 20, 20);
        //slot for water
        drawTexturedRect(guiLeft+148, guiTop + 30, 54, 51, 20, 20);

        //draw the player inventory and toolbar background.
        drawTexturedRect(guiLeft+105, guiTop+ 74, 0, 74, 176, 100);
        drawTexturedRect(guiLeft+105, guiTop+ 68, 0, 0, 176, 6);

        mc.getTextureManager().bindTexture(vanillaChest);
        drawTexturedRect(guiLeft-105, guiTop-37+yCenter, 0, 0, 176, 17);//top
        for(int i = 0; i<transport.getInventoryRows(); i++){
            drawTexturedRect(guiLeft-105, i*18+ (guiTop-20)+yCenter, 0, 17, 176, 18);
        }
        drawTexturedRect(guiLeft-105,(transport.getInventoryRows())*18+ (guiTop-20)+yCenter, 0, 215, 176, 8);//bottom



        //draw the tanks
        Tessellator.bindTexture(URIRegistry.GUI_PREFIX.getResource("gui.png"));
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
     * todo: rework this for just rendering inventory for the transport, clone it for the player.
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
     * todo: make this an optional that renders where the train UI would normally render
     */
    private void renderTankerInventory(Minecraft mc){
        //draw the player inventory and toolbar background.
        mc.getTextureManager().bindTexture(vanillaChest);
        drawTexturedRect(guiLeft, guiTop+ 79, 0, 128, 176, 94);
        drawTexturedRect(guiLeft, guiTop+ 76, 0, 0, 176, 8);

        Tessellator.bindTexture(URIRegistry.GUI_PREFIX.getResource("gui.png"));
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

    //todo: really? this is the best method on hand for outlined font?
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