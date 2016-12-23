package trains.gui.train;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import trains.entities.EntityTrainCore;
import trains.gui.trainhandler.SteamInventoryHandler;
import trains.registry.URIRegistry;

public class GUISteam extends GuiContainer {
    private EntityTrainCore train;
    private static final float guiScaler = 0.00390625F;

    /**
     * <h2>GUI initialization</h2>
     * instances the container for the inventory and passes it to the server side management
     * also puts the entity to a variable that can be accessed on client.
     * @see SteamInventoryHandler
     */
    public GUISteam(InventoryPlayer inventoryPlayer, EntityTrainCore entity) {
        super(new SteamInventoryHandler(inventoryPlayer, entity));
        train = entity;
    }

    /**
     * <h2>GUI foreground layer</h2>
     * this draws the topmost layer, closest to screen.
     * things later in the function render closer to the screen
     *
     * StatCollector.translateToLocal is used for translation, this is usually defined in minecraft itself.
     * xSize and ySize are defined in the super
     * @see GuiContainer
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        fontRendererObj.drawString("Test Locomotive", 8, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 94, 4210752);
    }

    /**
     * <h2>GUI Background</h2>
     * this draws the lower layer, good for backgrounds and things to go behind the foreground stuff
     * things later in the function render closer to the screen
     * Textures must be bound to the render, then every texture draw after will re-use that same texture, when you want a new texture, bind a new one.
     * Item scale is defined by: x,y,u,v,width,height
     *
     * xSize, ySize, width, and height are defined in the super
     * @see GuiContainer
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        //draw the gui background color
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int interfaceWidth = (width - xSize) / 2;
        int interfaceHeight = (height - ySize) / 2;

        //main background
        //mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("guilocobackground.png"));
        //drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);

        //icon for fuel
        mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("steam_fuelbackground.png"));
        drawTexturedModalRect(interfaceWidth + 7, interfaceHeight + 25, 0, 0, 18, 18);
        //icon for furnace
        mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("steam_fuelicon.png"));
        drawTexturedModalRect(interfaceWidth + 7, interfaceHeight + 25, 0, 0, 18, 18);

        //set the generic slot icon, which will get re-used for every slot.
        mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("itemslot.png"));

        //slot for fuel
        drawTexturedModalRect(interfaceWidth + 7, interfaceHeight + 52, 0, 0, 18, 18);

        //slot for water
        drawTexturedModalRect(interfaceWidth + 34, interfaceHeight + 52, 0, 0, 18, 18);

        //player inventory slots
        for (int ic = 0; ic < 9; ic++) {
            for (int ir = 0; ir < 3; ir++) {
                drawTexturedModalRect( interfaceWidth + 7 + (ic * 18), interfaceHeight + 83 + (ir * 18), 0, 0, 18, 18);
            }
        }
        //player toolbar slots
        for (int iT = 0; iT < 9; iT++) {
            drawTexturedModalRect(interfaceWidth + 7 + (iT * 18), interfaceHeight + 141, 0, 0, 18, 18);
        }

        int columns=0;
        int rows=0;
        switch (train.getInventorySize()){
            case 1:{columns=2;rows=2;break;}
            case 2:{columns=2;rows=3;break;}
            case 3:{columns=3;rows=3;break;}
            case 4:{columns=3;rows=4;break;}
            case 5:{columns=4;rows=4;break;}
        }
        //train's inventory
        for (int ic = 0; ic < rows; ic++) {
            for (int ir = 0; ir < columns; ir++) {
                drawTexturedModalRect( interfaceWidth + 97 + (ic * 18), interfaceHeight + 7 + (ir * 18), 0, 0, 18, 18);
            }
        }



        //draw the background
        drawColor(0,0);
        drawTexturedModalRect(interfaceWidth + 66, interfaceHeight + 20, 0, 0, 18, 50);
        if (train.getTank().canDrain(1,0)) {
            //draw the water tank
            drawColor(train.getType(),0);
            int liquid = Math.abs((train.getTank().tankFluidAmount(0) * 50) / train.getTank().tankCapacity(0));
            drawTexturedModalRect(interfaceWidth + 66, interfaceHeight + 20 - liquid, 177, 64 - liquid, 18, liquid);
        }

        if (train.getType() == 1 || train.getType() == 5) {
            //draw the background
            drawColor(0,0);
            drawTexturedModalRect(interfaceWidth + 34, interfaceHeight+20, 30, 30, 18, 24);
            if (train.getTank().canDrain(1, 1)) {
                //draw the steam tank
                drawColor(train.getType(), 1);
                int liquid3 = Math.abs((train.getTank().tankFluidAmount(1) * 24) / train.getTank().tankCapacity(0));
                drawTexturedModalRect(interfaceWidth + 34, interfaceHeight +20 - liquid3, 177, 24 - liquid3, 18, liquid3);
            }
        }

    }

    public void drawTexturedModalRect(int posX, int posY, int posU, int posV, int width, int height) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        tessellator.addVertexWithUV(posX, posY + height, this.zLevel, posU * guiScaler, (posV + 256) * guiScaler);

        tessellator.addVertexWithUV(posX + width, posY + height, this.zLevel, (posU + 256) * guiScaler, (posV + 256) * guiScaler);

        tessellator.addVertexWithUV(posX + width, posY, this.zLevel, (posU + 256) * guiScaler, posV * guiScaler);

        tessellator.addVertexWithUV(posX, posY, this.zLevel, posU * guiScaler, posV * guiScaler);
        tessellator.draw();
    }


    private void drawColor(int trainType, int tank){
        switch (trainType){
            case 0:{
                mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("tank_empty.png"));
                break;
            }
            case 1:{
                if (tank==0){
                    mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("tank_water.png"));
                } else {
                    mc.renderEngine.bindTexture(URIRegistry.GUI_PREFIX.getResource("tank_steam.png"));
                }
                break;
            }
        }

    }
}