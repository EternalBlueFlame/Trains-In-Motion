package ebf.tim.gui;

import ebf.tim.models.ModelBook;
import ebf.tim.utility.CommonProxy;
import ebf.tim.utility.Recipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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

    private static Map<String, List<bookPage>> infoPages=new HashMap<>();
    public static int guiLeft=0,guiTop=0, page=0;
    private static List<Object> pageData = null;
    private static ModelBook book = new ModelBook();
    int frame=0;

    public static @Nullable Object getPage(int current){
        if(pageData==null) {
            List<Object> pages = new ArrayList<>();
            //first add all mods that don't have recipes
            for(String m : infoPages.keySet()){
                if(!CommonProxy.recipesInMods.containsKey(m)){
                    Collections.addAll(pages, infoPages.get(m));
                }
            }

            for (String mod : CommonProxy.recipesInMods.keySet()) {
                if(infoPages.containsKey(mod)) {
                    Collections.addAll(pages, infoPages.get(mod));
                }
                for(Recipe r : CommonProxy.recipesInMods.get(mod)){
                    if(r.getresult()!=null) {//for some unknown reason this must be called for the recipe to initialize at all...
                        pages.add(r);
                    }
                }
            }
            pageData=pages;
        }
        return pageData.size()>current?pageData.get(current):null;
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float par3){
        super.drawScreen(mouseX, mouseY, par3);

        GL11.glPushMatrix();
        //GL11.glTranslatef(,, 300);
        GL11.glColor4f(1,1,1,1);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GUITransport.drawTexturedRect(percentLeft(14),percentTop(15),0,0,percentLeft(72),percentTop(60));

        GL11.glEnable(GL11.GL_TEXTURE_2D);
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

    public static void renderItem(ItemStack itm, int left, int top){
        if(itm!=null && itm.getItem()!=null) {
            GL11.glDisable(GL11.GL_LIGHTING);
            //render the item and the overlay
            itemRender.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(),
                    itm, left, top);
            itemRender.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(),
                    itm, left, top, null);
        }
    }
    public static void renderSlot(int left, int top){
        GL11.glDisable(GL11.GL_LIGHTING);
        GUITransport.drawTexturedRect(left-2, top-2, 54, 51, 20, 20);
    }



    public static int getBookSlotPlacement(boolean x, int index){
        if(x){
            return 32* (index>8?1:index>5?index-6:index>2?index-3:index);
        } else {
            return index>8?-32:index>5?64:index>2?32:0;
        }
    }

    public static void renderpage(boolean leftPage){
        if(getPage(leftPage?page:page+1)==null){return;}
        if(getPage(leftPage?page:page+1) instanceof Recipe) {

            Minecraft.getMinecraft().fontRenderer.drawString(((Recipe) getPage(leftPage?page:page+1)).getresult().get(0).getDisplayName()
                    ,percentLeft(leftPage?18:57), percentTop(19), 0x000000);
            Minecraft.getMinecraft().getTextureManager().bindTexture(GUITransport.vanillaInventory);
            for (int slot = 0; slot < 10; slot++) {
                renderSlot(percentLeft(leftPage?22:60)+ getBookSlotPlacement(true, slot),
                        percentTop(37)+ getBookSlotPlacement(false, slot));
            }
            for (int slot = 0; slot < 10; slot++) {
                renderItem(((Recipe)getPage(leftPage?page:page+1)).getDisplayArray()[slot],
                        percentLeft(leftPage?22:60)+ getBookSlotPlacement(true, slot),
                        percentTop(37)+ getBookSlotPlacement(false, slot));
            }
        } else if(getPage(leftPage?page:page+1) instanceof bookPage){
            String[] disp = ((bookPage)getPage(leftPage?page:page+1)).text.split("\n");
            for (int i=0;i<disp.length;i++){
                Minecraft.getMinecraft().fontRenderer.drawString(disp[i],percentLeft(leftPage?20:55), percentTop(25)+(i*12), 0x000000);
            }

            //todo: draw images from pages

        }
    }

    public GUICraftBook(){}



    @Override
    public void initGui() {
        super.initGui();
        guiLeft=new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight).getScaledWidth();
        guiTop=new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight).getScaledHeight();

        buttonList =new ArrayList();

        buttonList.add(new GuiButton(-1, percentLeft(30)-10,percentTop(80)-10, 20,20,"<"));//left
        buttonList.add(new GuiButton(1,percentLeft(70)-10,percentTop(80)-10,20,20,">"));//right

    }


    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {
        switch (p_146284_1_.id){
            case -1:{
                if(page>0){page-=2;}
                break;
            }
            case 1:{
                if (page+2<pageData.size()){page+=2;}
                break;
            }
        }
    }
    public static int percentTop(int value){return (int)(guiTop*(value*0.01f));}
    public static int percentLeft(int value){return (int)(guiLeft*(value*0.01f));}



    private static class bookPage{
        String text;
        bookImage[] pictures;
        public bookPage(String t, bookImage[] images){
            text=t;
            pictures=images;
        }
    }

    public static void addPage(String modid, String text, bookImage ... images){
        if(infoPages.containsKey(modid)){
            infoPages.get(modid).add(new bookPage(text,images));
        } else {
            infoPages.put(modid, Collections.singletonList(new bookPage(text,images)));
        }
    }
    public static void addPage(String modid, String text){
        if(infoPages.containsKey(modid)){
            infoPages.get(modid).add(new bookPage(text,null));
        } else {
            List<bookPage> pages = new ArrayList<>();
            pages.add(new bookPage(text,null));
            infoPages.put(modid, pages);
        }
    }

    private static class bookImage{
        ResourceLocation texture;
        int x,y,width,height;
    }

    public static bookImage addImage(ResourceLocation texture, int x, int y, int width, int height){
        bookImage img = new bookImage();
        img.texture=texture;
        img.x=x;img.y=y;img.width=width;img.height=height;
        return img;
    }

}