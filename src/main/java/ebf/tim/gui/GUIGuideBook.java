package ebf.tim.gui;

import ebf.tim.TrainsInMotion;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.items.ItemAdminBook;
import ebf.tim.items.ItemCraftGuide;
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
    private String[] list;
    static boolean isTrainPage = false;
    private int guiLeft;
    private int guiTop;
    private int page=0;
    ItemCraftGuide itemCraftGuide;

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

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3){
        super.drawScreen(mouseX, mouseY, par3);

        List<String> indexes = new ArrayList<>();
        //add the index pages for the mods
        indexes.addAll(itemCraftGuide.indexPages);
        //be sure we end two full pages before continuing to the next part.
        while(indexes.size() %16!=0){
            indexes.add("");
        }
        //add the data from each corrisponding mod
        for(String modid : itemCraftGuide.processedTransports.keySet()){
            //add the mod info pages, if any
            if(itemCraftGuide.modInfoPages.get(modid)!=null){
                for (String info: itemCraftGuide.modInfoPages.get(modid).split("\n")){
                    indexes.add(info);
                }
            }
            //be sure we end two full pages before continuing to the next part.
            while(indexes.size() %16!=0){
                indexes.add("");
            }

            for(GenericRailTransport transport : itemCraftGuide.processedTransports.get(modid)){
                if((page+1)*16<indexes.size()){
                    break;
                }
                indexes.add(transport.transportName());
                indexes.add(transport.transportYear());
                indexes.add(transport.transportcountry());
                indexes.add(transport.transportFuelType());
                indexes.add(transport.transportTopSpeed() +"km/h   : "+ transport.transportMetricHorsePower()+"MHP");

                indexes.add(transport.getRecipie()[0].getDisplayName() +" : "+ transport.getRecipie()[1].getDisplayName() +" : "+ transport.getRecipie()[2].getDisplayName());
                indexes.add(transport.getRecipie()[3].getDisplayName() +" : "+ transport.getRecipie()[4].getDisplayName() +" : "+ transport.getRecipie()[5].getDisplayName());
                indexes.add(transport.getRecipie()[6].getDisplayName() +" : "+ transport.getRecipie()[7].getDisplayName() +" : "+ transport.getRecipie()[8].getDisplayName());
            }
        }


        for(int i=0; i<9;i++){
            drawTextOutlined(fontRendererObj, indexes.get((page*16)+i),guiLeft-50,guiTop-20+(20*i), 0x000000);
        }

        for(int i=0; i<9;i++){
            drawTextOutlined(fontRendererObj, indexes.get((page*16)+i+8),guiLeft+50,guiTop-20+(20*i), 0x000000);
        }


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