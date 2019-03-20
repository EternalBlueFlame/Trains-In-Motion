package ebf.tim.gui;

import ebf.tim.utility.Recipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.*;


/**
 * new data storage management, pages as Objects, as either List<ItemStack>[] or String
 *
 * the each entry in the list defines the itemstacks from the ore directory and every x seconds it cycles through them. it's very likely to be only 1 entry.
 * each entry in the array of lists denotes a different item in the recipe, with the 9th being the result.
 *
 * GUI needs a custom inherant class to define things like the dynamic resolution and methods for scaling and rendering geometry.
 * can inherit normal item rendering for the stacks, simplify it to it's own method with an offset so two pages can be done at once
 * if the recipe value for a page is null then just draw a blank page
 *
 *
 * TODO: mod index has been changed to a string rather than an integer, convert accordingly.
 */


public class GUICraftBook extends GuiScreen {

    public static Map<String, List<Recipe>> recipesInMods = new HashMap<>();
    public static Map<String, String[]> infoPages=new HashMap<>();
    public static int guiLeft=0,guiTop=0, page=0;
    private static List<Object> pageData = null;
    int frame=0;

    public static @Nullable Object getPage(int current){
        if(pageData==null) {
            List<Object> pages = new ArrayList<>();
            for (String mod : recipesInMods.keySet()) {
                Collections.addAll(pages, infoPages.get(mod));

                Collections.addAll(pages, recipesInMods.get(mod));
            }
            pageData=pages;
        }
        return pageData.get(current);
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float par3){
        super.drawScreen(mouseX, mouseY, par3);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        renderpage(true);
        renderpage(false);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();

        //change the item displayed every 2 seconds (120 frames)
        frame++;
        if(frame>120){
            if(getPage(page) instanceof Recipe) {
                ((Recipe)getPage(page)).nextDisplayItem();
            }
            if(getPage(page+1) instanceof Recipe) {
                ((Recipe)getPage(page+1)).nextDisplayItem();
            }
        }
    }


    public static void renderpage(boolean leftPage){
        if(getPage(leftPage?page:page+1)==null){return;}
        int x=0; int y=0;
        if(getPage(leftPage?page:page+1) instanceof Recipe) {
            for (int slot = 0; slot < 10; slot++) {
                //todo: render slot background

                //render the item and the overlay
                itemRender.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), ((Recipe)getPage(leftPage?page:page+1)).getDisplayArray()[slot],
                        percentLeft(leftPage?20:55)+ x,percentLeft(35)+ y);
                itemRender.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), ((Recipe)getPage(leftPage?page:page+1)).getDisplayArray()[slot],
                        percentLeft(leftPage?20:55)+x, percentLeft(35)+y, null);

                //add the grid offset
                x += 32;
                if (x > 96 && y <= 64) {
                    x = 0;
                    y += 32;
                } else if (x > 96) {
                    x=32;
                    y=-32;

                }
            }
        } else if(getPage(leftPage?page:page+1) instanceof String){
            String[] disp = ((String)getPage(leftPage?page:page+1)).split("\n");
            for (int i=0;i<disp.length;i++){
                Minecraft.getMinecraft().fontRenderer.drawString(disp[i],percentLeft(leftPage?20:55), percentTop(25)+(i*12), 0x000000);
            }

        }
    }

    public GUICraftBook(){}



    @Override
    public void initGui() {
        super.initGui();
        guiLeft=new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight).getScaledWidth();
        guiTop=new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight).getScaledHeight();

        buttonList =new ArrayList();

        buttonList.add(new GuiButton(-1, percentLeft(30),percentTop(75), 16,16,"<"));//left
        buttonList.add(new GuiButton(1,percentLeft(70),percentTop(75),16,16,">"));//right
    }


    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        switch (p_146284_1_.id){
            case -1:{
                if(page>0){page-=2;}
                break;
            }
            case 1:{
                if (page+1<pageData.size()){page+=2;}
                break;
            }
        }
    }
    public static int percentTop(int value){return (int)(guiTop*(value*0.01f));}
    public static int percentLeft(int value){return (int)(guiLeft*(value*0.01f));}


}
