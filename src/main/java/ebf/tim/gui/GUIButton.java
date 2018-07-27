package ebf.tim.gui;

import ebf.tim.entities.GenericRailTransport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import fexcraft.tmt.slim.Tessellator;

import java.util.Collections;
import java.util.List;

/**
 * <h2>Gui Button</h2>
 * slight rework of the vanilla GuiButton class.
 * this makes it so the text assigned to the button isn't rendered, that way we can use it as hover text from the GUI render.
 * also makes some small performance enhancements.
 * @author Eternal Blue Flame
 */
public class GUIButton extends GuiButton {


    public GUIButton(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, String textID) {
        super(p_i1021_1_,p_i1021_2_,p_i1021_3_,p_i1021_4_,p_i1021_5_,textID);
    }

    public int getHoverState(boolean p_146114_1_) {
        return !this.enabled?0:p_146114_1_?2:1;
    }

    @Override
    public void drawButton(Minecraft p_146112_1_, int mouseX, int mouseY) {
        if (this.visible) {
            GL11.glPushMatrix();
            Tessellator.bindTexture(buttonTextures);
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

    public List getDisplayString(GenericRailTransport transport){
        switch (displayString){
            case "unlink": {return Collections.singletonList(StatCollector.translateToLocal("gui.unlink"));}
            case "dropkey": {return Collections.singletonList(StatCollector.translateToLocal("gui.dropkey"));}
            case "locked": {return Collections.singletonList(StatCollector.translateToLocal("gui.locked." + transport.getBoolean(GenericRailTransport.boolValues.LOCKED)));}
            case "coupler": {return Collections.singletonList(StatCollector.translateToLocal("gui.coupler." + transport.getBoolean(GenericRailTransport.boolValues.COUPLINGFRONT)));}
            case "lamp": {return Collections.singletonList(StatCollector.translateToLocal("gui.lamp")  + ((transport.getBoolean(GenericRailTransport.boolValues.LAMP))?StatCollector.translateToLocal("gui.on"):StatCollector.translateToLocal("gui.off")));}
            case "creative":{return Collections.singletonList(StatCollector.translateToLocal("gui.creativemode") + ((transport.getBoolean(GenericRailTransport.boolValues.CREATIVE))?StatCollector.translateToLocal("gui.on"):StatCollector.translateToLocal("gui.off")));}
            case "running":{return Collections.singletonList(StatCollector.translateToLocal("gui.trainisrunning")  + ((transport.getBoolean(GenericRailTransport.boolValues.RUNNING))?StatCollector.translateToLocal("gui.on"):StatCollector.translateToLocal("gui.off")));}
            case "brake": {return Collections.singletonList(StatCollector.translateToLocal("gui.brake")  + ((transport.getBoolean(GenericRailTransport.boolValues.BRAKE))?StatCollector.translateToLocal("gui.on"):StatCollector.translateToLocal("gui.off")));}
            case "horn": {return Collections.singletonList(StatCollector.translateToLocal("gui.horn"));}

            default: {return Collections.singletonList("Missing Statement");}
        }
    }

}
