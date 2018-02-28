package ebf.tim.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ebf.tim.models.rails.ModelRailSegment;
import ebf.tim.models.rails.RailPointRenderData;
import ebf.tim.utility.CommonProxy;
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

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class RailTileEntity extends TileEntity {

    private int metal =0;
    private int ties =0;
    private List<RailPointRenderData> path = null;
    private ResourceLocation texture = URIRegistry.MODEL_RAIL_TEXTURE.getResource("RailNew.png");
    private AxisAlignedBB boundingBox = null;
    private List<RailPointRenderData> renderShape[] = null;

    public float getRailSpeed(){
        float speed =0.4f;
        if (metal == 0){speed+=0.2f;}
        if (ties == 1){speed+=0.2f;}
        return speed;
    }

    public String getRailTexture(){
        String name;
        switch (metal){
            case 1:{name = "iron"; break;}
            default:{name = "steel"; break;}//0 and fallback
        }
        switch (ties){
            case 1:{name += "concrete"; break;}
            default:{name += "wood"; break;}//0 and fallback
        }
        return name;
    }

    public void setType(int metalType, int tiesType){
        this.metal = metalType;
        this.ties = tiesType;
    }

    public void setPath(List<RailPointRenderData> newShape){
        path = newShape;
    }


    @SafeVarargs
    public final void setRenderShape(List<RailPointRenderData> ... newShape){
        renderShape = newShape;
    }

    private static ItemStack grass = new ItemStack(Blocks.stone, 1);

    @SideOnly(Side.CLIENT)
    protected RenderBlocks blocks = new RenderBlocks();

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

            GL11.glColor3d(((Blocks.iron_block.getMapColor(0).colorValue >> 16 & 0xFF)* 0.00392156863),
                    ((Blocks.iron_block.getMapColor(0).colorValue >> 8 & 0xFF)* 0.00392156863),
                    ((Blocks.iron_block.getMapColor(0).colorValue & 0xFF)* 0.00392156863));
            for(List<RailPointRenderData> renderData : renderShape) {
                for (RailPointRenderData point : renderData) {
                    GL11.glPushMatrix();
                    GL11.glTranslated(point.position[0], point.position[1] + 0.05, point.position[2]);
                    GL11.glRotated(point.position[4], 0, 1, 0);
                    GL11.glRotated(-90+point.position[3], 0, 0, 1);
                    //GL11.glRotated(180, 1, 0, 0);
                    GL11.glScaled(1, 0.5, 0.5);

                    point.segment.render(null, 0, 0, 0, 0, 0, 0.0625f);

                    //GL11.glScaled(1.25, 2.15, 1.25);
                    //blocks.renderBlockAsItem(Block.getBlockFromItem(grass.getItem()), grass.getItemDamage(), 1.0f);
                    GL11.glPopMatrix();
                }
            }
            //GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPopMatrix();

        } else {super.func_145828_a(report);}
    }

    private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
    private static final Vec3 field_82884_b = Vec3.createVectorHelper(0.20000000298023224D, 1.0D, -0.699999988079071D).normalize();
    private static final Vec3 field_82885_c = Vec3.createVectorHelper(-0.20000000298023224D, 1.0D, 0.699999988079071D).normalize();
    /**
     * Update and return colorBuffer with the RGBA values passed as arguments
     */
    private static FloatBuffer setColorBuffer(double p_74517_0_, double p_74517_2_, double p_74517_4_, double p_74517_6_)
    {
        /**
         * Update and return colorBuffer with the RGBA values passed as arguments
         */
        return setColorBuffer((float)p_74517_0_, (float)p_74517_2_, (float)p_74517_4_, (float)p_74517_6_);
    }

    /**
     * Update and return colorBuffer with the RGBA values passed as arguments
     */
    private static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_)
    {
        colorBuffer.clear();
        colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
        colorBuffer.flip();
        /** Float buffer used to set OpenGL material colors */
        return colorBuffer;
    }
    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
        return oldBlock != newBlock;
    }

    @Override
    public boolean canUpdate(){return true;}

    @Override
    public void updateEntity() {
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
        tag.setInteger("metal", metal);
        tag.setInteger("ties", ties);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);
        metal = tag.getInteger("metal");
        ties = tag.getInteger("ties");
    }

}
