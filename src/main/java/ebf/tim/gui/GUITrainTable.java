package ebf.tim.gui;

import ebf.tim.TrainsInMotion;
import ebf.tim.blocks.TileEntityStorage;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.TileEntitySlotManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import static ebf.tim.gui.GUITransport.drawTexturedRect;

/**
 * <h1>Train crafting table GUI</h1>
 * @author Eternal Blue Flame
 */
public class GUITrainTable extends GuiContainer {
    private static final ResourceLocation craftingTableGuiTextures = ClientProxy.useVanillaInventoryTextures?new ResourceLocation("textures/gui/container/crafting_table.png"):
            new ResourceLocation(TrainsInMotion.MODID, "textures/gui/crafting.png");
    private static final ResourceLocation vanillaInventory =
            ClientProxy.useVanillaInventoryTextures?new ResourceLocation("textures/gui/container/furnace.png"):
                    new ResourceLocation(TrainsInMotion.MODID, "textures/gui/furnace.png");

    private static final ResourceLocation vanillaChest =
            ClientProxy.useVanillaInventoryTextures?new ResourceLocation("textures/gui/container/generic_54.png"):
                    new ResourceLocation(TrainsInMotion.MODID, "textures/gui/chest.png");

    public GUITrainTable(InventoryPlayer inventoryPlayer, World world, int x, int y, int z) {
        super(new TileEntitySlotManager(inventoryPlayer, (TileEntityStorage) world.getTileEntity(x,y,z)));
    }


    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        switch (((TileEntitySlotManager)this.inventorySlots).craftingTable.storageType){
            case 0:{
                this.fontRendererObj.drawString(StatCollector.translateToLocal("container.traincrafting"), 28, 6, 4210752);
                break;
            }case 1:{
                this.fontRendererObj.drawString(StatCollector.translateToLocal("container.railcrafting"), 28, -10, 4210752);
                break;
            }
        }

        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(((TileEntitySlotManager)this.inventorySlots).craftingTable.storageType==1){
            mc.getTextureManager().bindTexture(vanillaInventory);

            drawTexturedRect(guiLeft+28, guiTop, 54, 51, 20, 20);//top input
            drawTexturedRect(guiLeft+28, guiTop+20, 54, 51, 20, 20);//middle input
            drawTexturedRect(guiLeft+28, guiTop+40, 54, 51, 20, 20);//bottom input
            drawTexturedRect(guiLeft+122, guiTop+20, 54, 51, 20, 0);//old Rail input
            //todo: draw down arrow

            drawTexturedRect(guiLeft+122, guiTop+20, 54, 51, 20, 40);//output

            mc.getTextureManager().bindTexture(vanillaChest);
            //draw the player inventory and toolbar background.
            drawTexturedRect(guiLeft, guiTop+64, 0, 0, 176,  16);//top
            drawTexturedRect(guiLeft,   guiTop+78, 0, 134, 176, 88);//actual inventory

        } else {
            this.mc.getTextureManager().bindTexture(craftingTableGuiTextures);
            this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        }
        GL11.glPopMatrix();
    }
}
