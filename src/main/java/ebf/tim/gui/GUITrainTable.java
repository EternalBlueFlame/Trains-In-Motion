package ebf.tim.gui;

import ebf.tim.TrainsInMotion;
import ebf.tim.blocks.TileEntityStorage;
import ebf.tim.utility.ClientProxy;
import ebf.tim.utility.TransportSlotManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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
        super(new TransportSlotManager(inventoryPlayer, (TileEntityStorage) world.getTileEntity(x,y,z)));
    }


    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glPushMatrix();

        //lastly render the background again with a partial alpha
        //GL11.glDisable(GL11.GL_BLEND);
        if(((TileEntityStorage)((TransportSlotManager)this.inventorySlots).hostInventory).storageType==1){

            GUITransport.drawTextOutlined(fontRendererObj,"Rail", guiLeft+8, guiTop+2,0xffffff);
            GUITransport.drawTextOutlined(fontRendererObj,"Ties", guiLeft+6, guiTop+24,0xffffff);
            GUITransport.drawTextOutlined(fontRendererObj,"Ballast", guiLeft-8, guiTop+41,0xffffff);

            GUITransport.drawTextOutlined(fontRendererObj,"Old Shape", guiLeft+108, guiTop+18,0xffffff);

            GUITransport.drawTextOutlined(fontRendererObj,"Output", guiLeft+116, guiTop+53,0xffffff);

            GUITransport.drawTextOutlined(fontRendererObj,"Unused", guiLeft+50, guiTop-4,0xffffff);
            GUITransport.drawTextOutlined(fontRendererObj,"Unused", guiLeft+50, guiTop+46,0xffffff);
            //render the background
            mc.getTextureManager().bindTexture(vanillaInventory);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            drawTexturedRect(guiLeft+28, guiTop-4, 54, 51, 20, 20);//top input
            drawTexturedRect(guiLeft+28, guiTop+16, 54, 51, 20, 20);//middle input
            drawTexturedRect(guiLeft+28, guiTop+35, 54, 51, 20, 20);//bottom input
            drawTexturedRect(guiLeft+122, guiTop-4, 54, 51, 20, 20);//old Rail input
            //todo: draw down arrow

            drawTexturedRect(guiLeft+48, guiTop+25, 54, 51, 20, 20);//wires slot
            drawTexturedRect(guiLeft+48, guiTop+5, 54, 51, 20, 20);//aux slot




            //then render the example items
            GL11.glPushMatrix();
            RenderItem.getInstance().renderItemIntoGUI(fontRendererObj, Minecraft.getMinecraft().getTextureManager(),
                    new ItemStack(Items.iron_ingot),guiLeft+30, guiTop-2);
            RenderItem.getInstance().renderItemIntoGUI(fontRendererObj, Minecraft.getMinecraft().getTextureManager(),
                    new ItemStack(Blocks.log),guiLeft+30, guiTop+18);
            RenderItem.getInstance().renderItemIntoGUI(fontRendererObj, Minecraft.getMinecraft().getTextureManager(),
                    new ItemStack(Blocks.gravel),guiLeft+30, guiTop+37);
            RenderItem.getInstance().renderItemIntoGUI(fontRendererObj, Minecraft.getMinecraft().getTextureManager(),
                    new ItemStack(Blocks.rail),guiLeft+124, guiTop-2);

            GL11.glPopMatrix();

            mc.getTextureManager().bindTexture(vanillaInventory);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);

            drawTexturedRect(guiLeft+28, guiTop-4, 54, 51, 20, 20);//top input
            drawTexturedRect(guiLeft+28, guiTop+16, 54, 51, 20, 20);//middle input
            drawTexturedRect(guiLeft+28, guiTop+35, 54, 51, 20, 20);//bottom input
            drawTexturedRect(guiLeft+122, guiTop-4, 54, 51, 20, 20);//old Rail input
            //todo: draw down arrow

            drawTexturedRect(guiLeft+48, guiTop+25, 54, 51, 20, 20);//wires slot
            drawTexturedRect(guiLeft+48, guiTop+5, 54, 51, 20, 20);//aux slot

            //GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexturedRect(guiLeft+122, guiTop+31, 54, 51, 20, 20);//output


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
