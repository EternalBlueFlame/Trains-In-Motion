package ebf.tim.gui;

import ebf.tim.tileentities.TileEntityStorage;
import ebf.tim.utility.ContainerHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 * <h1>Train crafting table GUI</h1>
 * @author Eternal Blue Flame
 */
public class GUITrainTable extends GuiContainer {
    private static final ResourceLocation craftingTableGuiTextures = new ResourceLocation("textures/gui/container/crafting_table.png");

    public GUITrainTable(InventoryPlayer inventoryPlayer, World world, int x, int y, int z) {
        super(new ContainerHandler(inventoryPlayer, (TileEntityStorage) world.getTileEntity(x,y,z), true));
    }


    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.traincrafting"), 28, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(craftingTableGuiTextures);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
    }
}
