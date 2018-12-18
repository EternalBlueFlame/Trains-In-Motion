package ebf.tim.gui;

import ebf.tim.TrainsInMotion;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.items.ItemAdminBook;
import ebf.tim.items.ItemCraftGuide;
import ebf.tim.utility.DebugUtil;
import ebf.tim.utility.ServerLogger;
import fexcraft.tmt.slim.Tessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Transport GUI</h1>
 * used to draw the GUI for trains and rollingstock (the menu with the inventory).
 * @author Eternal Blue Flame
 */
public class GUIGuideBook extends GuiScreen {
    /**the amount to scale the GUI by, same as vanilla*/
    private static final float guiScaler = 0.00390625F;
    private int guiLeft;
    private int guiTop;
    private int page=1;
    List<String> lines = new ArrayList<>();
    ItemCraftGuide itemCraftGuide;
    private List<GUIButton> buttons = new ArrayList<>();

    public GUIGuideBook(ItemCraftGuide item){
        itemCraftGuide=item;
    }
    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }


    @Override
    public void actionPerformed(GuiButton button) {

        switch (button.id) {

        }

    }

    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft = (this.width - 176) / 2;
        this.guiTop = (this.height - 166) / 2;

        itemCraftGuide.init();

        this.buttons.add(new GUIButton(guiLeft +130, guiTop + 140, 18, 18, ">", null, null){

            @Override
            public String getHoverText() {
                if(lines==null || lines.size()<((page+1)*22)){
                    return "";
                }
                return "gui.goToPage" + " " + (page+1);
            }

            @Override
            public void onClick() {
                page++;
            }
        });

        this.buttons.add(new GUIButton(guiLeft - 50, guiTop + 140, 18, 18, ",", null, null){

            @Override
            public String getHoverText() {
                return page==0?"":"gui.goToPage" + " "+ (page-1);
            }

            @Override
            public void onClick() {
                page--;
            }
        });

    }

    @Override
    public void mouseClicked(int mouseButton, int mouseX, int mouseY){
        for(GUIButton b :buttons){
            if(b.getMouseHover()){
                DebugUtil.println("clicking", page);
                b.onClick();
                DebugUtil.println( page);
                return;
            }
        }
        super.mouseClicked(mouseButton, mouseX, mouseY);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3){
        super.drawScreen(mouseX, mouseY, par3);

        lines = new ArrayList<>();
        //add the index pages for the mods
        lines.addAll(itemCraftGuide.indexPages);
        //be sure we end two full pages before continuing to the next part.
        while(lines.size() %22!=0){
            lines.add("");
        }
        //add the data from each corrisponding mod
        for(String modid : itemCraftGuide.processedTransports.keySet()){
            //add the mod info pages, if any
            if(itemCraftGuide.modInfoPages.get(modid)!=null){
                for (String info: itemCraftGuide.modInfoPages.get(modid).split("\n")){
                    lines.add(info);
                }
            }
            //be sure we end two full pages before continuing to the next part.
            while(lines.size() %22!=0){
                lines.add("");
            }

            for(GenericRailTransport transport : itemCraftGuide.processedTransports.get(modid)){
                if((page+1)*22<lines.size()){
                    break;
                }
                lines.add(transport.transportName());
                lines.add(transport.transportYear());
                lines.add(transport.transportcountry());
                lines.add(transport.transportFuelType());
                lines.add(transport.transportTopSpeed() +"km/h   : "+ transport.transportMetricHorsePower()+"MHP");

                lines.add(getItem(transport,0) +" : "+ getItem(transport,1) +" : "+ getItem(transport,2));
                lines.add(getItem(transport,3) +" : "+ getItem(transport,4) +" : "+ getItem(transport,5));
                lines.add(getItem(transport,6) +" : "+ getItem(transport,7) +" : "+ getItem(transport,8));
                while(lines.size() %11!=0){
                    lines.add("");
                }
            }

            while(lines.size() %22!=0){
                lines.add("");
            }
        }

        for (int i=0; i<11;i++) {
            drawTextOutlined(fontRendererObj, lines.get((page * 22)+i), guiLeft - 80, guiTop +(i*15), 0x000000);
        }
        for (int i=0; i<11;i++) {
            drawTextOutlined(fontRendererObj, lines.get((page * 22)+i+11), guiLeft + 90, guiTop + (i*15), 0x000000);
        }


        for (GUIButton b : buttons){
            b.drawButton(mouseX,mouseY);
        }
    }

    public static void drawTextOutlined(FontRenderer font, String string, int x, int y, int color){
        //bottom left
        font.drawString(string, x-1, y+1, 0x999999);
        //bottom
        font.drawString(string, x, y+1, 0x999999);
        //bottom right
        font.drawString(string, x+1, y+1, 0x999999);
        //left
        font.drawString(string, x-1, y, 0x999999);
        //right
        font.drawString(string, x+1, y, 0x999999);
        //top left
        font.drawString(string, x-1, y-1, 0x999999);
        //top
        font.drawString(string, x, y-1, 0x999999);
        //top right
        font.drawString(string, x+1, y-1, 0x999999);


        font.drawString(string,x,y,color);
    }

    public String getItem(GenericRailTransport transport, int slot){
        return transport.getRecipie()[slot]!=null?transport.getRecipie()[slot].getDisplayName():"Empty";
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



    private void func_146977_a(Slot p_146977_1_) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), p_146977_1_.getStack(), p_146977_1_.xDisplayPosition, p_146977_1_.yDisplayPosition);
        itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), p_146977_1_.getStack(), p_146977_1_.xDisplayPosition, p_146977_1_.yDisplayPosition, null);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }


}