package ebf.tim.gui;


import ebf.tim.TrainsInMotion;
import ebf.tim.api.skin;
import ebf.tim.entities.GenericRailTransport;
import ebf.tim.models.Bogie;
import ebf.tim.networking.PacketPaint;
import fexcraft.tmt.slim.ModelBase;
import fexcraft.tmt.slim.TextureManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 *@author Oskiek
 */
public class GUISkinManager extends GuiScreen {


    public GuiButton buttonLeft;
    public GuiButton buttonRight;
    public GuiButton buttonApply;

    public static skin s;

    protected int xSize = 176;
    protected int ySize = 166;

    public static int guiTop;
    public static int guiLeft;
    public GenericRailTransport entity;

    public GUISkinManager(GenericRailTransport t){
        entity=t;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        guiLeft=new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight).getScaledWidth();
        guiTop=new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight).getScaledHeight();

        Keyboard.enableRepeatEvents(true);

        buttonList =new ArrayList();
        buttonList.add(buttonLeft = new GuiButton(-1, percentLeft(10)-10,percentTop(65), 20,20,"<<"));//left
        buttonList.add(buttonRight = new GuiButton(-1, percentLeft(90)-10,percentTop(65), 20,20,">>"));//right
        buttonList.add(buttonApply = new GuiButton(-1, percentLeft(50)-16,percentTop(80), 32,20,"Apply"));//apply
    }

    @Override
    public void updateScreen()
    {
        buttonApply.visible=true;

        s = entity.getSkinList(Minecraft.getMinecraft().thePlayer, true)
                .get(entity.getSkinList(Minecraft.getMinecraft().thePlayer, true).keySet().toArray()[page]);

    }

    public static int percentTop(int value){return (int)(guiTop*(value*0.01f));}
    public static int percentLeft(int value){return (int)(guiLeft*(value*0.01f));}

    @Override
    public void drawScreen(int parWidth, int parHeight, float p_73863_3_)
    {
        super.drawScreen(parWidth, parHeight, p_73863_3_);

        initGui();
        if(s==null){return;}
        GL11.glPushMatrix();
        GL11.glColor4f(1F, 1F, 1F, 0.5F);
        GL11.glPopMatrix();
        float offsetFromScreenLeft = width * 0.5f;


        fontRendererObj.drawString(s.name,
                (int)(offsetFromScreenLeft - fontRendererObj.getStringWidth(s.name)*0.5f),
                (int)((height*0.1f)*6),0,false);

        if(s.getDescription()!=null) {
            for(int i=0; i<s.getDescription().length;i++) {
                fontRendererObj.drawString(s.getDescription()[i],
                        (int) (offsetFromScreenLeft - fontRendererObj.getStringWidth(s.getDescription()[0]) * 0.5f),
                        (int) ((height * 0.1f) * 7)+(10*i), 0, false);
            }
        }
        renderTransport(entity,page);

    }

    @Override
    protected void actionPerformed(GuiButton parButton)
    {
        if (parButton==buttonApply) {
            applySkin();
            mc.displayGuiScreen(null);//todo make an actual close button
        }
        else if (parButton==buttonLeft) {
            onLeftClick();
        }
        else if (parButton==buttonRight) {
            onRightClick();
        }
    }

    int page = 0;

    void onLeftClick() {
        page = (page <= 0 ? entity.getSkinList(Minecraft.getMinecraft().thePlayer, true).keySet().size() : page - 1);
    }//subtract one from page, loop to end when less than 1

    void onRightClick() {
        page = (page+1 >= entity.getSkinList(Minecraft.getMinecraft().thePlayer, true).keySet().size() ? 0 : page + 1);
    }//add one to page, loop back to 0 when it gets larger than size

    void applySkin(){
        TrainsInMotion.keyChannel.sendToServer(new PacketPaint((String)entity.getSkinList(Minecraft.getMinecraft().thePlayer, true).keySet().toArray()[page], entity.getEntityId()));
        entity.renderData.needsModelUpdate=true;
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return true;
    }

    void renderTransport(GenericRailTransport entity, int i) {

        //get skin from page
        ebf.tim.api.skin s = entity.getSkinList(Minecraft.getMinecraft().thePlayer, true)
                .get(entity.getSkinList(Minecraft.getMinecraft().thePlayer, true).keySet().toArray()[page]);
        //bind skin to render
        TextureManager.bindTexture(s.texture);

        //render models with offsets
        for (ModelBase m : entity.getModel()) {
            GL11.glPushMatrix();
            if (entity.modelOffsets() != null && entity.modelOffsets().length > i) {
                GL11.glTranslated(entity.modelOffsets()[i][0], entity.modelOffsets()[i][1], entity.modelOffsets()[i][2]);
            }
            m.render(null, 0, 0, 0, 0, 0, 0.0625f);
            GL11.glPopMatrix();
        }

        if(entity.bogies()==null){
            return;
        }
        //render bogies with textures and offsets
        int b = 0, sb = 0;
        for (Bogie m : entity.bogies()) {
            TextureManager.bindTexture(s.getBogieSkin(b));
            b++;
            GL11.glPushMatrix();
            GL11.glTranslated(-m.offset[0], -m.offset[1], -m.offset[2]);
            m.bogieModel.render(null, 0, 0, 0, 0, 0, 0.0625f);
            //render the sub bogies with textures if the bogie has any
            if (m.subBogies != null) {

            }
        }
    }
}