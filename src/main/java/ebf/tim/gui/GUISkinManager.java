package ebf.tim.gui;


import ebf.tim.entities.GenericRailTransport;
import ebf.tim.models.Bogie;
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

    public static String name;
    public static String[] description;

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
        buttonList.add(new GuiButton(-1, percentLeft(10)-10,percentTop(65), 20,20,"<<"));//left
        buttonList.add(new GuiButton(-1, percentLeft(90)-10,percentTop(65), 20,20,">>"));//right
        buttonList.add(new GuiButton(-1, percentLeft(50)-16,percentTop(80), 32,20,"Apply"));//apply
    }

    @Override
    public void updateScreen()
    {
        buttonLeft.visible=true;
        buttonRight.visible=true;
        buttonApply.visible=true;

        ebf.tim.api.skin s = entity.getSkinList(Minecraft.getMinecraft().thePlayer, true)
                .get(entity.getSkinList(Minecraft.getMinecraft().thePlayer, true).keySet().toArray()[page]);

        name=s.name;

        description=s.getDescription();

    }

    public static int percentTop(int value){return (int)(guiTop*(value*0.01f));}
    public static int percentLeft(int value){return (int)(guiLeft*(value*0.01f));}

    @Override
    public void drawScreen(int parWidth, int parHeight, float p_73863_3_)
    {
        super.drawScreen(parWidth, parHeight, p_73863_3_);

        initGui();
        GL11.glPushMatrix();
        GL11.glColor4f(1F, 1F, 1F, 0.5F);
        GL11.glPopMatrix();
        float offsetFromScreenLeft = width * 0.5f;


        fontRendererObj.drawString(name,
                (int)(offsetFromScreenLeft - fontRendererObj.getStringWidth(name)*0.5f),
                (int)((height*0.1f)*6),0,false);

        //todo: loop for each entry in description. each entry is a new line, so add an additional y offset each iteration.
        fontRendererObj.drawString(description[0],
                (int)(offsetFromScreenLeft - fontRendererObj.getStringWidth(description[0])*0.5f),
                (int)((height*0.1f)*7), 0, false);

        renderTransport(entity,page);

    }


    public GenericRailTransport Transport;

    @Override
    protected void actionPerformed(GuiButton parButton)
    {
        if (parButton==buttonApply) {
            applySkin();
            mc.displayGuiScreen((GuiScreen)null);
        }
        else if (parButton==buttonLeft) {
            onLeftClick();
        }
        else if (parButton==buttonRight) {
            onRightClick();
        }
    }

    int page = 1;
    private EntityPlayer Player;

    void onLeftClick() {
        page = (page <= 1 ? Transport.getSkinList(Player, true).entrySet().size() : page - 1);
    }//subtract one from page, loop to end when less than 1

    void onRightClick() {
        page = (page >= Transport.getSkinList(Player, true).entrySet().size() ? 1 : page + 1);
    }//add one to page, loop back to 0 when it gets larger than size

    void applySkin(){
        ebf.tim.api.skin s = entity.getSkinList(Minecraft.getMinecraft().thePlayer, true)
                .get(entity.getSkinList(Minecraft.getMinecraft().thePlayer, true).keySet().toArray()[page]);
        TextureManager.bindTexture(s.texture);
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