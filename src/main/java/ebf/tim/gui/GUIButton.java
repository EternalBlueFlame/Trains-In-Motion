package ebf.tim.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

/**
 * <h2>Gui Button</h2>
 * slight rework of the vanilla GuiButton class.
 * this makes it so the text assigned to the button isn't rendered, that way we can use it as hover text from the GUI render.
 * also makes some small performance enhancements.
 * @author Eternal Blue Flame
 */
public class GUIButton extends GuiButton {


    public GUIButton(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String p_i1021_6_) {
        super(p_i1021_1_,p_i1021_2_,p_i1021_3_,p_i1021_4_,p_i1021_5_,p_i1021_6_);
    }

    public int getHoverState(boolean p_146114_1_) {
        return !this.enabled?0:p_146114_1_?2:1;
    }

    @Override
    public void drawButton(Minecraft p_146112_1_, int mouseX, int mouseY) {
        if (this.visible) {
            GL11.glPushMatrix();
            p_146112_1_.getTextureManager().bindTexture(buttonTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(p_146112_1_, mouseX, mouseY);
            GL11.glPopMatrix();


        }
    }

    public boolean getMouseHover(){
        return field_146123_n;
    }
}
