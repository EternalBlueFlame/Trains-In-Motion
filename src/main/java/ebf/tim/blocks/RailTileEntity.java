package ebf.tim.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.blocks.rails.RailVanillaShapes;
import ebf.tim.models.rails.ModelRailSegment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.Vec3;
import org.lwjgl.BufferUtils;
import tmt.Tessellator;
import ebf.tim.registry.URIRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import tmt.Vec3d;
import tmt.Vec3f;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;


public class RailTileEntity extends TileEntity {

    private List<?extends ModelRailSegment> path = null;
    private AxisAlignedBB boundingBox = null;
    private List<?extends ModelRailSegment> renderShape[] = null;

    public float getRailSpeed(){
        float speed =0.4f;
        //if (metal == 0){speed+=0.2f;}
        //if (ties == 1){speed+=0.2f;}
        return speed;
    }
    public RailTileEntity setPath(List<?extends ModelRailSegment> newShape){
        path = newShape;
        return this;
    }


    @SafeVarargs
    @Deprecated
    public final void setRenderShape(List<?extends ModelRailSegment> ... newShape){
        if (worldObj.isRemote) {
            renderShape = newShape;
        }
    }

    private static final double[] color = {
            (Blocks.iron_block.getMapColor(0).colorValue >> 16 & 0xFF)* 0.00392156863,
            (Blocks.iron_block.getMapColor(0).colorValue >> 8 & 0xFF)* 0.00392156863,
            (Blocks.iron_block.getMapColor(0).colorValue & 0xFF)* 0.00392156863
    };

    public void func_145828_a(@Nullable CrashReportCategory report)  {
        if (report == null) {
            if (!worldObj.isRemote || renderShape == null) {
                return;
            }
                //Tessellator.bindTexture(texture);
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            //GL11.glDisable(GL11.GL_LIGHTING);
            //GL11.glBindTexture(GL_TEXTURE_2D, TextureUtil.missingTexture.getGlTextureId());

            //RenderHelper.enableGUIStandardItemLighting();
            //int color = Minecraft.getMinecraft().renderEngine.getTexture().getGlTextureId();
            Tessellator tessellator = Tessellator.getInstance();
            //GL11.glColor3d(color[0],color[1],color[2]);
            int i=0;
            for(List<?extends ModelRailSegment> renderData : renderShape) {
                i=0;
                for (ModelRailSegment point : renderData) {
                    //GL11.glTranslated(point.position[0], point.position[1] + 0.05, point.position[2]);
                    //GL11.glRotated(point.position[4], 0, 1, 0);
                    //GL11.glRotated(point.position[3], 0, 0, 1);
                    //GL11.glScaled(1, 0.5, 0.5);

                    for(ModelRailSegment.subModel model : point.models) {
                        model.render(tessellator, 0, i == 0, i == renderData.size() - 2);
                    }

                    //GL11.glScaled(1.25, 2.15, 1.25);
                    //blocks.renderBlockAsItem(Block.getBlockFromItem(grass.getItem()), grass.getItemDamage(), 1.0f);
                    i++;
                }
            }
            //GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPopMatrix();

        } else {super.func_145828_a(report);}
    }

    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
        return oldBlock != newBlock;
    }

    @Override
    public boolean canUpdate(){return true;}

    //todo have block set the nearbymeta when a neighboring block changes
    public int[] nearbymeta;
    public int[] lastnearbymeta;

    private int updateticks=0;
    private static final float[] pathShape={0};
    private static final float[] rendShape={0.3125f, -0.3125f};

    @Override
    public void updateEntity() {
        updateticks++;

        if (path == null || nearbymeta != lastnearbymeta || updateticks %20==0){
            lastnearbymeta = nearbymeta;

            switch (worldObj.getBlockMetadata(xCoord, yCoord, zCoord)){
                //Z straight
                case 0: {
                    setPath(
                            RailVanillaShapes.vanillaZStraight(worldObj, xCoord, yCoord, zCoord, 0)
                    ).setRenderShape(
                            RailVanillaShapes.vanillaZStraight(worldObj, xCoord, yCoord, zCoord, 0.3125f),
                            RailVanillaShapes.vanillaZStraight(worldObj, xCoord, yCoord, zCoord, -0.3125f));
                    return;
                }
                //X straight
                case 1: {
                    setPath(
                            RailVanillaShapes.vanillaXStraight(worldObj, xCoord, yCoord, zCoord, 0)
                    ).setRenderShape(
                            RailVanillaShapes.vanillaXStraight(worldObj, xCoord, yCoord, zCoord, 0.3125f),
                            RailVanillaShapes.vanillaXStraight(worldObj, xCoord, yCoord, zCoord, -0.3125f));
                    return;
                }

                //curves
                case 9: {
                    setPath(
                            RailVanillaShapes.vanillaCurve9(worldObj, xCoord, yCoord, zCoord, 0)
                    ).setRenderShape(
                            RailVanillaShapes.vanillaCurve9(worldObj, xCoord, yCoord, zCoord, 0.3125f),
                            RailVanillaShapes.vanillaCurve9(worldObj, xCoord, yCoord, zCoord, -0.3125f));
                    return;
                }
                case 8: {
                    setPath(
                            RailVanillaShapes.vanillaCurve8(worldObj, xCoord, yCoord, zCoord, 0)
                    ).setRenderShape(
                            RailVanillaShapes.vanillaCurve8(worldObj, xCoord, yCoord, zCoord, 0.3125f),
                            RailVanillaShapes.vanillaCurve8(worldObj, xCoord, yCoord, zCoord, -0.3125f));
                    return;
                }
                case 7: {
                    setPath(
                            RailVanillaShapes.vanillaCurve7(worldObj, xCoord, yCoord, zCoord, 0)
                    ).setRenderShape(
                            RailVanillaShapes.vanillaCurve7(worldObj, xCoord, yCoord, zCoord, 0.3125f),
                            RailVanillaShapes.vanillaCurve7(worldObj, xCoord, yCoord, zCoord, -0.3125f));
                    return;
                }
                case 6: {
                    setPath(
                            RailVanillaShapes.vanillaCurve6(worldObj, xCoord, yCoord, zCoord, pathShape)
                    ).setRenderShape(
                            RailVanillaShapes.vanillaCurve6(worldObj, xCoord, yCoord, zCoord, rendShape));
                    return;
                }
                //Z slopes
                case 5 :{
                    setPath(
                            RailVanillaShapes.vanillaSlopeZ5(worldObj, xCoord, yCoord, zCoord, 0)
                    ).setRenderShape(
                            RailVanillaShapes.vanillaSlopeZ5(worldObj, xCoord, yCoord, zCoord, 0.3125f),
                            RailVanillaShapes.vanillaSlopeZ5(worldObj, xCoord, yCoord, zCoord, -0.3125f));
                    return;
                }
                case 4 :{
                    setPath(
                            RailVanillaShapes.vanillaSlopeZ4(worldObj, xCoord, yCoord, zCoord, 0)
                    ).setRenderShape(
                            RailVanillaShapes.vanillaSlopeZ4(worldObj, xCoord, yCoord, zCoord, 0.3125f),
                            RailVanillaShapes.vanillaSlopeZ4(worldObj, xCoord, yCoord, zCoord, -0.3125f));
                    return;
                }
                //X slopes
                case 2 :{
                    setPath(
                            RailVanillaShapes.vanillaSlopeX2(worldObj, xCoord, yCoord, zCoord, 0)
                    ).setRenderShape(
                            RailVanillaShapes.vanillaSlopeX2(worldObj, xCoord, yCoord, zCoord, 0.3125f),
                            RailVanillaShapes.vanillaSlopeX2(worldObj, xCoord, yCoord, zCoord, -0.3125f));
                    return;
                }
                case 3 :{
                    setPath(
                            RailVanillaShapes.vanillaSlopeX3(worldObj, xCoord, yCoord, zCoord, 0)
                    ).setRenderShape(
                            RailVanillaShapes.vanillaSlopeX3(worldObj, xCoord, yCoord, zCoord, 0.3125f),
                            RailVanillaShapes.vanillaSlopeX3(worldObj, xCoord, yCoord, zCoord, -0.3125f));
                    return;
                }
            }

        }



    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if (boundingBox == null) {
            boundingBox = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
        }
        return boundingBox;
    }


    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
    }

}
