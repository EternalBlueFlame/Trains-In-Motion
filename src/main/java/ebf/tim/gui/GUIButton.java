package ebf.tim.gui;

import ebf.tim.utility.RailUtility;
import fexcraft.tmt.slim.Tessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.annotation.Nullable;

/**
 * <h2>Gui Button</h2>
 * slight rework of the vanilla GuiButton class.
 * this makes it so the text assigned to the button isn't rendered, that way we can use it as hover text from the GUI render.
 * also makes some small performance enhancements.
 * @author Eternal Blue Flame
 */
public abstract class GUIButton extends GuiButton {

    private ResourceLocation icon = null;
    private int[] iconTexture = null;

    public GUIButton(int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, @Nullable ResourceLocation icon, @Nullable int[] iconUV) {
        super(-1,p_i1021_2_,p_i1021_3_,p_i1021_4_,p_i1021_5_,"");
        this.icon=icon;
        this.iconTexture=iconUV;
    }

    public GUIButton(int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String text, @Nullable ResourceLocation icon, @Nullable int[] iconUV) {
        super(-1,p_i1021_2_,p_i1021_3_,p_i1021_4_,p_i1021_5_,text);
        this.icon=icon;
        this.iconTexture=iconUV;
    }

    @Deprecated
    public int getHoverState(boolean p_146114_1_) {
        return !this.enabled?0:p_146114_1_?2:1;
    }

    public void drawButton(int mouseX, int mouseY) {
        GL11.glPushMatrix();
        Tessellator.bindTexture(buttonTextures);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        int k = field_146123_n?2:1;
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
        this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);

        if(icon!=null) {
            Tessellator.bindTexture(icon);
            if(iconTexture==null){
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 0, this.width / 2, this.height);
            } else {
                //todo: render icon with provided icon UV mapping
            }
        }

        if(field_146123_n) {
            drawHoveringText(RailUtility.translate(getHoverText()), mouseX, mouseY, Minecraft.getMinecraft().fontRenderer);
        }

        GL11.glPopMatrix();
    }

    protected void drawHoveringText(String p_146283_1_, int p_146283_2_, int p_146283_3_, FontRenderer font) {
        if (!p_146283_1_.isEmpty()) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = font.getStringWidth(p_146283_1_);

            int j2 = p_146283_2_ + 12;
            int k2 = p_146283_3_ - 12;
            int i1 = 8;

            if (j2 + k > this.width) {
                j2 -= 28 + k;
            }

            this.zLevel = 300.0F;
            int j1 = -267386864;
            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
            int k1 = 1347420415;
            int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
            this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

            font.drawStringWithShadow(p_146283_1_, j2, k2, -1);


            this.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }


    public boolean getMouseHover(){
        return field_146123_n;
    }

    public abstract String getHoverText();

    public abstract void onClick();

}
